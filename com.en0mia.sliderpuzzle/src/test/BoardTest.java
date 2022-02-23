/*
    @author: Simone Nicol <en0mia.dev@gmail.com>
    @created: 23/02/22
    @copyright: Check the repository license.
*/

package test;

import entities.Board;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    Board b = null;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        int[][] tiles = new int[][] {
                {0, 1, 3},
                {4, 2, 5},
                {7, 8, 6}
        };

        this.b = new Board(tiles);
    }

    @org.junit.jupiter.api.Test
    void testHamming() {
        assertEquals(4, this.b.hamming());
    }

    @org.junit.jupiter.api.Test
    void testManhattan() {
        assertEquals(4, this.b.manhattan());
    }

    @org.junit.jupiter.api.Test
    void testDimension() {
        assertEquals(3, this.b.dimension());
    }

    @org.junit.jupiter.api.Test
    void testIsGoal() {
        assertFalse(this.b.isGoal());
    }

    @org.junit.jupiter.api.Test
    void testEquals() {
        int[][] tiles = new int[][] {
                {0, 1, 3},
                {4, 2, 5},
                {7, 8, 6}
        };

        Board c = new Board(tiles);

        assertEquals(this.b, c);

        tiles = new int[][] {
                {0, 1, 2},
                {4, 2, 5},
                {7, 8, 6}
        };

        c = new Board(tiles);

        assertNotEquals(this.b, c);

        tiles = new int[][] {
                {0, 1, 3, 9},
                {4, 2, 5, 10},
                {7, 8, 6, 11},
                {12, 13, 14, 15}
        };

        c = new Board(tiles);

        assertNotEquals(this.b, c);
    }

    @org.junit.jupiter.api.Test
    void testNeighbors() {
        int count = 0;
        boolean valid = true;
        ArrayList<Board> validNeighbors = new ArrayList<>();
        int[][] tiles = new int[][] {
                {1, 0, 3},
                {4, 2, 5},
                {7, 8, 6}
        };
        validNeighbors.add(new Board(tiles));

        tiles = new int[][] {
                {4, 1, 3},
                {0, 2, 5},
                {7, 8, 6}
        };
        validNeighbors.add(new Board(tiles));

        for (Board n : this.b.neighbors()) {
            if (!validNeighbors.contains(n)) {
                valid = false;
            }

            count ++;
        }

        assertTrue(valid);
        assertEquals(2, count);
    }

    @org.junit.jupiter.api.Test
    void testTwin() {
        int differences = 0;
        int[] x = new int[2];
        int[] y = new int[2];

        Board twin = this.b.twin();

        for (int i = 0; i < twin.dimension(); i++) {
            for (int j = 0; j < twin.dimension(); j++) {
                if (this.b.get(i, j) != twin.get(i, j)) {
                    x[differences] = i;
                    y[differences] = j;

                    differences ++;
                }
            }
        }

        assertEquals(2, differences);
        assertEquals(1, Math.abs(x[0] - x[1]) + Math.abs(y[0] - y[1]));
    }

    @org.junit.jupiter.api.Test
    void testToString() {
        String correct = "3\n 0 1 3\n 4 2 5\n 7 8 6";
        assertEquals(correct, this.b.toString());
    }
}
