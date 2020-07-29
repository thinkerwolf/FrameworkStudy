package com.thinkerwolf.frameworkstudy.alogrithm.astar;

import java.util.*;

public class AStar {

    public static int[] dx = new int[]{-1, -1, -1, 0, 0, 1, 1, 1};
    public static int[] dy = new int[]{-1, 0, 1, -1, 1, -1, 0, 1};

    private final Queue<GP> opens = new PriorityQueue<>();

    private boolean[][] closeSet;

    private static final int MAX_STEP = 1000;

    public static List<GP> calRoad(Map map, Pos start, Pos end) {
        AStar star = new AStar();
        star.closeSet = new boolean[map.getHeight()][map.getWidth()];
        star.opens.add(new GP(start, 0, 0));
        List<GP> roads = new LinkedList<>();
        for (int step = 0; step < MAX_STEP; step++) {
            GP n = star.opens.poll();
            if (n == null) {
                break;
            }
            star.closeSet[n.getY()][n.getX()] = true;
            boolean find = false;
            roads.add(n);
            for (int i = 0; i < 8; i++) {
                int ny = n.getY() + dy[i];
                int nx = n.getX() + dx[i];
                Pos p = map.getPos(ny, nx);
                if (p == null) {
                    continue;
                }
                if (star.closeSet[p.getY()][p.getX()]) {
                    continue;
                }
                if (p.isBlock()) {
                    continue;
                }
                if (p.equals(end)) {
                    find = true;
                    break;
                }
                int g = n.getG() + p.price(n.pos);
                int h = p.price(end);
                GP gp = new GP(p, g, h);
                star.opens.remove(gp);
                star.opens.add(gp);
            }
            if (find) {
                break;
            }
        }
        return roads;
    }

    public static void main(String[] args) {
        int[][] points = new int[10][10];
        Map map = MapFactory.createMap(points);
        Pos start = map.getPos(0, 0);
        Pos end = map.getPos(2, 3);

        List<GP> roads = AStar.calRoad(map, start, end);
        System.out.println(roads);
    }


}
