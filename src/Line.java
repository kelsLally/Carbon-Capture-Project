public class Line {
    private int start;
    private int end;
    private double length;

    public Line(int start, int end, double length) {
        this.start = start;
        this.end = end;
        this.length = length;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public double getLength() {
        return length;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Line){
            if ((start == (((Line) obj).getStart()) && end == ((Line) obj).getEnd()) ||
                    (start == (((Line) obj).getEnd()) && end == ((Line) obj).getStart())) {
                return true;
            }
        }
        return false;
    }
}
