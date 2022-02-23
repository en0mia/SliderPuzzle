/*
    @author: Simone Nicol <en0mia.dev@gmail.com>
    @created: 23/02/22
    @copyright: Check the repository license.
*/

package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Board {
    private final int[][] board;
    private int manhattan = -1;
    private int hamming = -1;
    private int zeroX;
    private int zeroY;
    private Board twin = null;

    public Board(int[][] tiles) {
        this.board = new int[tiles.length][tiles.length];

        // Safe copy.
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tiles[i][j] == 0) {
                    this.zeroX = i;
                    this.zeroY = j;
                }
                this.board[i][j] = tiles[i][j];
            }
        }

        // Since a board object is immutable, we can cache the hamming and manhattan distances.
        this.manhattan = this.manhattan();
        this.hamming = this.hamming();
    }

    // Number of tiles out of place
    public int hamming() {
        // If we already cached the distance, just return it.
        if (this.hamming != -1) {
            return this.hamming;
        }

        int outOfPlace = 0;

        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board.length; j++) {
                // In the hamming distance, we should not consider the empty tile.
                if (this.board[i][j] != 0 && this.board[i][j] != this.linearize(i, j)) {
                    outOfPlace ++;
                }
            }
        }

        return outOfPlace;
    }

    // Sum of Manhattan distances between tiles and goal
    public int manhattan() {
        // If we already cached the distance, just return it.
        if (this.manhattan != -1) {
            return this.manhattan;
        }

        int distance = 0;

        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board.length; j++) {
                if (this.board[i][j] == 0) {
                    // Do not count the distance if it's the empty tile.
                    continue;
                }

                int[] goal = this.delinearize(this.board[i][j]);
                int goalX = goal[0];
                int goalY = goal[1];

                distance += Math.abs(goalX - i);
                distance += Math.abs(goalY - j);
            }
        }

        return distance;
    }

    // Board dimension n
    public int dimension() {
        return this.board.length;
    }

    // Is this board the goal board?
    public boolean isGoal() {
        return this.hamming() == 0;
    }

    // Does this board equal y?
    public boolean equals(Object y) {
        if (!(y instanceof Board other)) {
            return false;
        }

        if (this.board.length != other.board.length) {
            return false;
        }

        return Arrays.deepEquals(this.board, other.board);
    }

    // All neighboring boards
    public Iterable<Board> neighbors() {
        int[] coordinates = this.getZero();
        int x = coordinates[0];
        int y = coordinates[1];

        ArrayList<Board> list = new ArrayList<>();

        if (x > 0) {
            // Left
            this.swap(x, y, x-1, y);
            list.add(new Board(this.board));
            // UnSwap
            this.swap(x-1, y, x, y);
        }

        if (x < this.board.length - 1) {
            // Right
            this.swap(x, y, x+1, y);
            list.add(new Board(this.board));
            // UnSwap
            this.swap(x+1, y, x, y);
        }

        if (y > 0) {
            // Up
            this.swap(x, y, x, y-1);
            list.add(new Board(this.board));
            // UnSwap
            this.swap(x, y-1, x, y);
        }

        if (y < this.board.length - 1) {
            // Down
            this.swap(x, y, x, y+1);
            list.add(new Board(this.board));
            // UnSwap
            this.swap(x, y+1, x, y);
        }

        return list;
    }

    // A board that is obtained by exchanging any pair of tiles
    public Board twin() {
        if (this.twin != null) {
            return new Board(this.twin.board);
        }

        this.twin = new Board(this.board);

        boolean ok = false;
        int x1 = 0, y1 = 0, x2, y2;
        Random r = new Random();

        while (!ok) {
            x1 = r.nextInt(this.board.length);
            y1 = r.nextInt(this.board.length);

            // The 0 tile is not a valid one
            if (this.twin.board[x1][y1] != 0) {
                ok = true;
            }
        }

        x2 = x1;
        y2 = y1;

        if (x1 < this.board.length - 1) {
            // I can swap with the right tile
            if (this.board[x1 + 1][y1] != 0) {
                x2 = x1 + 1;
            }
        } else {
            // I can swap with the left tile
            if (this.board[x1 - 1][y1] != 0) {
                x2 = x1 - 1;
            }
        }

        // I can't swap horizontally, let's try vertically
        if (x1 == x2) {
            if (y1 < this.board.length - 1) {
                y2 = y1 + 1;
            } else {
                y2 = y1 - 1;
            }
        }

        this.twin.swap(x1, y1, x2, y2);

        return new Board(this.twin.board);
    }

    public int get(int x, int y) {
        return this.board[x][y];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.board.length);

        for (int[] ints : this.board) {
            sb.append("\n");
            for (int j = 0; j < this.board.length; j++) {
                sb.append(" ").append(ints[j]);
            }
        }
        return sb.toString();
    }

    // Given the coordinates, return the goal tile value.
    private int linearize(int row, int col) {
        return row * this.board.length + col + 1;
    }

    // Given a number, return its goal coordinates
    private int[] delinearize(int n) throws IllegalArgumentException {
        if (n > this.dimension() * this.dimension() - 1) {
            throw new IllegalArgumentException();
        }

        int[] res = {
                n / this.board.length,
                n % this.board.length - 1
        };

        if (n == 0) {
            res[0] = res[1] = this.board.length - 1;
        }

        if (res[1] < 0) {
            res[0]--;
            res[1] = this.board.length - 1;
        }

        return res;
    }

    private int[] getZero() {
        return new int[]{
                this.zeroX,
                this.zeroY
        };
    }

    private void swap(int x1, int y1, int x2, int y2) {
        int support = this.board[x1][y1];
        this.board[x1][y1] = this.board[x2][y2];
        this.board[x2][y2] = support;
    }
}
