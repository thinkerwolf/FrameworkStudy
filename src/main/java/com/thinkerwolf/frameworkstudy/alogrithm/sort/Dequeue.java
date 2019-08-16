package com.thinkerwolf.frameworkstudy.alogrithm.sort;

import io.netty.util.CharsetUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;

import static com.thinkerwolf.frameworkstudy.alogrithm.Util.*;

/**
 * 出列排序
 *
 * @param
 * @author wukai
 * @date 2019/8/13 13:35
 * @return
 */
public class Dequeue {

    public static void sort(Comparable[] a) {
        int N = a.length;
        for (int i = 0; i < N - 1; i++) {
            for (int j = i; j < N - 1; j++) {
                if (less(a[i], a[i + 1])) {
                    exch(a, i + 1, i);
                }
                moveQueue(a, i);
            }
            int t = 1;
            t++;
        }
    }

    private static void moveQueue(Comparable[] a, int start) {
        int N = a.length;
        Comparable temp = a[start];
        for (int i = start + 1; i < N; i++) {
            a[i - 1] = a[i];
        }
        a[N - 1] = temp;
    }

    public static void main(String[] args) {
        ClassPathResource r = new ClassPathResource("words.txt");
        try {
            List<String> ls = IOUtils.readLines(r.getInputStream(), CharsetUtil.UTF_8);
            String[] ss = new String[ls.size()];
            ls.toArray(ss);
            sort(ss);
            show(ss);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
