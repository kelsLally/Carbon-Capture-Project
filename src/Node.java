
public class Node {
    private double x;
    private double y;
    private NodeType node_type;

    // basic constructor
    public Node(double x, double y, NodeType node_type) {
        this.x = x;
        this.y = y;
        this.node_type = node_type;
    }


    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public NodeType getNode_type() {
        return node_type;
    }

    @Override
    public String toString() {
        return "Node{" +
                "x=" + x +
                ", y=" + y +
                ", node_type=" + node_type +
                '}';
    }
}


