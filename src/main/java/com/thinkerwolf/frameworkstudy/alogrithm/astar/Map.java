package com.thinkerwolf.frameworkstudy.alogrithm.astar;

public class Map {

    int height;
    int width;
    Pos[][] poses;

    public Map(Pos[][] poses) {
        this.poses = poses;
        this.height = poses.length;
        this.width = poses[0].length;
    }

    public Pos getPos(int y, int x) {
        if (y < 0 || x < 0 || y >= height || x >= width) {
            return null;
        }
        return poses[y][x];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
