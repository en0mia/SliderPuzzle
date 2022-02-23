/*
    @author: Simone Nicol <en0mia.dev@gmail.com>
    @created: 23/02/22
    @copyright: Check the repository license.
*/

package solver;

import entities.Board;
import entities.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

public class Solver {
    private final Board initial;
    private boolean solvable;
    // The last node in the solution.
    private Node solution;
    private ArrayList<Board> steps;

    public Solver(Board initial) throws IllegalArgumentException {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        this.initial = initial;
        this.steps = null;

        Board twin = this.initial.twin();
        PriorityQueue<Node> queue = new PriorityQueue<>();
        PriorityQueue<Node> twinQueue = new PriorityQueue<>();

        this.solvable = false;

        Node node = new Node(initial, 0, null);
        Node twinNode = new Node(twin, 0, null);

        queue.add(node);
        twinQueue.add(twinNode);

        while (!queue.isEmpty() && !twinQueue.isEmpty()) {
            node = queue.poll();
            twinNode = twinQueue.poll();

            if (node.getB().isGoal()) {
                this.solvable = true;
                break;
            }

            if (twinNode.getB().isGoal()) {
                this.solvable = false;
                break;
            }

            for (Board b : node.getB().neighbors()) {
                if (node.getParent() == null || !b.equals(node.getParent().getB())) {
                    queue.add(new Node(b, node.getMoves() + 1, node));
                }
            }

            for (Board b : twinNode.getB().neighbors()) {
                if (twinNode.getParent() == null || !b.equals(twinNode.getParent().getB())) {
                    twinQueue.add(new Node(b, twinNode.getMoves() + 1, twinNode));
                }
            }
        }

        if (this.solvable) {
            this.solution = node;
        }
    }

    // Is the initial board solvable?
    public boolean isSolvable() {
        return this.solvable;
    }

    // Min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!this.isSolvable()) {
            return -1;
        }

        return this.solution.getMoves();
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!this.isSolvable() || this.solution == null) {
            return null;
        }

        if (this.steps != null) {
            // Safe return.
            return new ArrayList<>(this.steps);
        }

        this.steps = new ArrayList<>();
        Node p = this.solution;

        while (p.getParent() != null) {
            this.steps.add(p.getB());
            p = p.getParent();
        }

        this.steps.add(this.initial);
        Collections.reverse(this.steps);

        // Safe return.
        return new ArrayList<>(this.steps);
    }
}
