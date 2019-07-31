enum NodeType {
    SOURCE, SINK, JUNCTION, SUPER_SOURCE, SUPER_SINK;

    @Override
    public String toString() {
        switch (this) {
            case SOURCE: return "source";
            case SINK: return "sink";
            case JUNCTION: return "junction";
            case SUPER_SOURCE: return "super source";
            case SUPER_SINK: return "super sink";
            default: return "error: no type";
        }
    }
}
