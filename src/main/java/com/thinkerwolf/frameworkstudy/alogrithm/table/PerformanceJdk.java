package com.thinkerwolf.frameworkstudy.alogrithm.table;

import com.thinkerwolf.frameworkstudy.alogrithm.Util;
import org.apache.commons.lang.time.StopWatch;

import java.util.*;
import java.util.regex.Pattern;

/**
 * JDK map性能测试
 *
 * @author wukai
 */
public class PerformanceJdk {

    static Pattern pattern = Pattern.compile("[a-zA-Z0-9]+");

    public static void main(String[] args) {
        Map <String, Integer> st = new TreeMap <>();
        List <String> list = Performance.getRandomKeys(100000);

        StopWatch sw = new StopWatch();
        sw.start();
        int pos = 1;
        for (String s : list) {
            st.put(s, pos++);
        }
        sw.stop();
        System.out.println("Put: " + st.getClass().getSimpleName() + ", num = " + list.size() + ", spendTime = " + sw.getTime() + ", kvsize = " + st.size());

        List <String> getKeys = new ArrayList <>(list.subList(list.size() / 8, list.size() / 2));
        for (int i = 0, max = getKeys.size(); i < max; i++) {
            getKeys.add(Util.nextString(4));
        }

        Collections.shuffle(getKeys);
        Collections.sort(getKeys);

        sw.reset();
        sw.start();
        for (int i = 0, max = getKeys.size() * 10; i < max; i++) {
            pos = i % getKeys.size();
            getKeys.get(pos);
        }
        sw.stop();
        System.out.println("Get: " + st.getClass().getSimpleName() + ", num = " + getKeys.size() + ", spendTime = " + sw.getTime());

    }

}
