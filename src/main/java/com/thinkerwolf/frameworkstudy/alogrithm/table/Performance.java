package com.thinkerwolf.frameworkstudy.alogrithm.table;

import com.thinkerwolf.frameworkstudy.alogrithm.ST;
import com.thinkerwolf.frameworkstudy.alogrithm.util.Util;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 性能测试
 *
 * @author wukai
 */
public class Performance {

    static Pattern pattern = Pattern.compile("[a-zA-Z0-9]+");

    public static List<String> getKeys() {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("book.txt");
        List<String> list = new LinkedList<>();
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

    public static List<String> getRandomKeys(int size) {
        List<String> list = new LinkedList<String>();
        for (int i = 0; i < size; i++) {
            list.add(Util.nextString(Util.nextInt(4, 20)));
        }
        return list;
    }

    public static List<Integer> getRandomIntKeys(int size) {
        Set<Integer> set = new HashSet<>();
        int min = 1;
        int max = size + 100;
        for (int i = 0; i < size; i++) {
            int key = Util.nextInt(min, max);
            for (; set.contains(key); ) {
                key = Util.nextInt(min, max);
            }
            set.add(key);
        }
        return new ArrayList<>(set);
    }

    public static void main(String[] args) {
        ST<String, Integer> st = new BTreeST<>(5);
        List<String> list = getKeys();

        StopWatch sw = new StopWatch();
        sw.start();
        int pos = 1;
        for (String s : list) {
            st.put(s, pos++);
        }
        sw.stop();
        System.out.println("Put: " + st.getClass().getSimpleName() + ", num = " + list.size() + ", spendTime = " + sw.getTime() + ", kvsize = " + st.size());

        List<String> keys = new ArrayList<>(list.subList(list.size() / 8, list.size() / 2));
        int getAndDeleteKeysSize = keys.size();
        for (int i = 0, max = keys.size(); i < max; i++) {
            keys.add(Util.nextString(4));
        }
        Collections.shuffle(keys);

        sw.reset();
        sw.start();
        for (int i = 0, max = keys.size() ; i < max; i++) {
            st.get(keys.get(i % keys.size()));
        }
        sw.stop();
        System.out.println("Get: " + st.getClass().getSimpleName() + ", num = " + getAndDeleteKeysSize + ", spendTime = " + sw.getTime());


        sw.reset();
        sw.start();
        for (int i = 0, max = keys.size(); i < max; i++) {
            st.delete(keys.get(i % keys.size()));
        }
        sw.stop();

        System.out.println("Delete: " + st.getClass().getSimpleName() + ", num = " + getAndDeleteKeysSize + ", spendTime = " + sw.getTime());
    }
}
