package com.thinkerwolf.frameworkstudy.alogrithm.astar;

import java.util.Objects;

public class Pos {
    int y;
    int x;
    boolean block;

    public Pos(int y, int x, boolean block) {
        this.y = y;
        this.x = x;
        this.block = block;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public boolean isBlock() {
        return block;
    }

    public int price(Pos p) {
        int dy = Math.abs(p.y - y);
        int dx = Math.abs(p.x - x);
        if (dx > dy) {
            return dy * 141 + Math.abs(dy - dx) * 100;
        }
        return dx * 141 + Math.abs(dy - dx) * 100;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pos pos = (Pos) o;
        return y == pos.y &&
                x == pos.x;
    }

    @Override
    public int hashCode() {
        return Objects.hash(y, x);
    }
}
