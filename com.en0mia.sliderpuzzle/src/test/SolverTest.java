/*
    @author: Simone Nicol <en0mia.dev@gmail.com>
    @created: 23/02/22
    @copyright: Check the repository license.
*/

package test;

import entities.Board;
import solver.Solver;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SolverTest {
    Board solvable = null;
    Board unsolvable = null;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        int[][] tiles = new int[][] {
                {0, 1, 3},
                {4, 2, 5},
                {7, 8, 6}
        };

        this.solvable = new Board(tiles);

        tiles = new int[][] {
                {1, 2, 3},
                {4, 5, 6},
                {8, 7, 0}
        };

        this.unsolvable = new Board(tiles);
    }

    @org.junit.jupiter.api.Test
    void testNullException() {
        assertThrows(IllegalArgumentException.class, () -> {Solver s = new Solver(null);});
    }

    @org.junit.jupiter.api.Test
    void testSolvable() {
        Solver s = new Solver(this.solvable);
        assertTrue(s.isSolvable());
    }

    @org.junit.jupiter.api.Test
    void testUnsolvable() {
        Solver s = new Solver(this.unsolvable);
        assertFalse(s.isSolvable());
    }

    @org.junit.jupiter.api.Test
    void testMoves() {
        Solver s = new Solver(this.solvable);
        assertEquals(4, s.moves());
    }

    @org.junit.jupiter.api.Test
    void testMovesUnsolvable() {
        Solver s = new Solver(this.unsolvable);
        assertEquals(-1, s.moves());
    }

    @org.junit.jupiter.api.Test
    void testSolution() {
        ArrayList<Board> correct = new ArrayList<>();

        int[][] tiles = new int[][] {
                {0, 1, 3},
                {4, 2, 5},
                {7, 8, 6}
        };
        correct.add(new Board(tiles));

        tiles = new int[][] {
                {1, 0, 3},
                {4, 2, 5},
                {7, 8, 6}
        };
        correct.add(new Board(tiles));

        tiles = new int[][] {
                {1, 2, 3},
                {4, 0, 5},
                {7, 8, 6}
        };
        correct.add(new Board(tiles));

        tiles = new int[][] {
                {1, 2, 3},
                {4, 5, 0},
                {7, 8, 6}
        };
        correct.add(new Board(tiles));

        tiles = new int[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };
        correct.add(new Board(tiles));

        Solver s = new Solver(this.solvable);

        int count = 0;
        boolean valid = true;

        for (Board tmp : s.solution()) {
            if (!tmp.equals(correct.get(count++))) {
                valid = false;
            }
        }

        assertTrue(valid);
    }
}
