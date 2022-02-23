/*
    @author: Simone Nicol <en0mia.dev@gmail.com>
    @created: 23/02/22
    @copyright: Check the repository license.
*/

package entities;

public class Node implements Comparable<Node>{
    final Board b;
    final int moves;
    final int cost;
    final Node parent;

    public Node(Board b, int moves, Node parent) throws IllegalArgumentException {
        if (b == null) {
            throw new IllegalArgumentException();
        }

        this.b = b;
        this.moves = moves;
        this.cost = (this.moves + this.b.manhattan());
        this.parent = parent;
    }

    @Override
    public int compareTo(Node o) {
        return this.cost - o.cost;
    }

    public Board getB() {
        return this.b;
    }

    public Node getParent() {
        return this.parent;
    }

    public int getMoves() {
        return this.moves;
    }
}
