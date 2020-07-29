package com.thinkerwolf.frameworkstudy.alogrithm.astar;

import java.util.Objects;

public class GP implements Comparable<GP> {

    Pos pos;

    int G;
    int H;


    public GP(Pos pos, int G, int H) {
        this.pos = pos;
        this.G = G;
        this.H = H;
    }

    public int getY() {
        return pos.getY();
    }

    public int getX() {
        return pos.getX();
    }

    public int getG() {
        return G;
    }

    public int getH() {
        return H;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GP gp = (GP) o;
        return Objects.equals(pos, gp.pos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pos);
    }


    @Override
    public int compareTo(GP o) {
        int f1 = this.G + this.H;
        int f2 = o.G + o.H;
        if (f1 > f2) {
            return 1;
        } else if (f1 < f2) {
            return -1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "{" +
                "y:" + pos.getY() +
                ", x:" + pos.getX() +
                '}';
    }
}
