package com.thinkerwolf.frameworkstudy.alogrithm;

import com.thinkerwolf.frameworkstudy.alogrithm.util.Util;

/**
 * 版本
 * @author wukai
 */
public class Version implements Comparable<Version> {

    private String v;

    public Version(String v) {
        this.v = v;
    }

    @Override
    public int compareTo(Version o) {
        // version compile
        String[] tiv = o.v.split("\\.");
        String[] ttv = o.v.split("\\.");
        int len = Math.min(tiv.length, ttv.length);
        for (int i = 0; i < len; i++) {
            int m = Integer.parseInt(tiv[i]);
            int n = Integer.parseInt(ttv[i]);
            if (m != n) {
                return m - n;
            }
        }
        return tiv.length - ttv.length;
    }

    public static void main(String[] args) {
        Version v1 = new Version("151.10.1");
        Version v2 = new Version("152.1.3");
        System.out.println(Util.less(v1, v2));
    }

}
