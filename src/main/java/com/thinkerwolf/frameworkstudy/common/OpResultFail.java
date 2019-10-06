package com.thinkerwolf.frameworkstudy.common;

public class OpResultFail extends OpResult {
    public OpResultFail() {
        super(false);
    }

    public OpResultFail(String msg) {
        super(false, msg);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("suc:").append(isOk()).append(' ');
        sb.append("msg:").append(getMsg());
        return sb.toString();
    }
}
