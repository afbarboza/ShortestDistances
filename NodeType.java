package com.learning;

import java.util.Objects;

public class NodeType {
    private String label;
    private boolean isExplored;
    private int distanceFromStartVertex;

    public NodeType(String label) {
        this.label = label;
        this.isExplored = false;
        this.distanceFromStartVertex = Integer.MAX_VALUE;
    }

    public String getLabel() {
        return label;
    }

    public boolean isExplored() {
        return isExplored;
    }

    public void setExplored(boolean explored) {
        isExplored = explored;
    }

    public int getDistanceFromStartVertex() {
        return distanceFromStartVertex;
    }

    public void setDistanceFromStartVertex(int distanceFromStartVertex) {
        this.distanceFromStartVertex = distanceFromStartVertex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        NodeType nodeType = (NodeType) o;
        return label.equals(nodeType.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }
}
