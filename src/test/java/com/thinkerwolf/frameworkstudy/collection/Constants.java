package com.thinkerwolf.frameworkstudy.collection;

public class Constants {

    public static final int MAP_TOTAL_DATA_NUM = 1000000;
    public static final int MAP_SINGLE_DATA_NUM = 10000;
    public static final int MAP_THREAD_NUM = MAP_TOTAL_DATA_NUM / MAP_SINGLE_DATA_NUM +
            (MAP_TOTAL_DATA_NUM % MAP_SINGLE_DATA_NUM != 0 ? 1 : 0);


}
