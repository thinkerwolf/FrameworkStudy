package com.thinkerwolf.frameworkstudy.alogrithm.astar;

public final class MapFactory {

    public static Map createMap(int[][] m) {
        int y = m.length;
        int x = m[0].length;
        Pos[][] poses = new Pos[y][x];
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                int v = m[i][j];
                Pos pos = new Pos(i, j, v > 0);
                poses[i][j] = pos;
            }
        }
        return new Map(poses);
    }

}
