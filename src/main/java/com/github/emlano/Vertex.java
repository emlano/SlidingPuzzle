package com.github.emlano;

public class Vertex {
    final int x;
    final int y;
    final String value;
    Vertex parent;

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
        return "(%d,%d)".formatted(y, x);
    }
}
