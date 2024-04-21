package com.github.emlano;

public class Vertex {
    final int x; // Position of containing Row
    final int y; // Position of Column
    final String value;
    Vertex parent; // Used to set parent node when path is build, unused otherwise

    public Vertex(int x, int y, String value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj.getClass() != Vertex.class) return false;

        Vertex other = (Vertex) obj;

        return other.x == this.x && other.y == this.y && this.value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return this.x + this.y + this.value.hashCode();
    }

    @Override
    public String toString() {
        return "(%d,%d)".formatted(y, x); // Ordered as such to match spec
    }
}
