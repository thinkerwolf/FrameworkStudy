package com.thinkerwolf.frameworkstudy.alogrithm.table;

import com.thinkerwolf.frameworkstudy.alogrithm.ST;
import com.thinkerwolf.frameworkstudy.alogrithm.Util;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 性能测试
 *
 * @author wukai
 */
public class Performance {

    static Pattern pattern = Pattern.compile("[a-zA-Z0-9]+");

    public static List <String> getKeys() {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("book.txt");
        List <String> list = new LinkedList <>();
        try {
            for (String l : IOUtils.readLines(in, "UTF-8")) {
                Matcher m = pattern.matcher(l);
                while (m.find()) {
                    String s = m.group();
                    if (StringUtils.isNotBlank(s) && s.length() > 1) {
                        list.add(s);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(in);
        }
        return list;
    }

    public static List <String> getRandomKeys(int size) {
        List <String> list = new LinkedList <String>();
        for (int i = 0; i < size; i++) {
            list.add(Util.nextString(Util.nextInt(5, 10)));
        }
        return list;
    }

    public static void main(String[] args) {
        ST <String, Integer> st = new BinaryTreeST <>();
        List <String> list = getRandomKeys(100000);

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
//        Collections.sort(getKeys);

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
