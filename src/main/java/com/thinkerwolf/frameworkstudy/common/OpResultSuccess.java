package com.thinkerwolf.frameworkstudy.common;

public class OpResultSuccess extends OpResult {
    public OpResultSuccess() {
        super(true);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("suc:").append(isOk()).append(' ');
        return sb.toString();
    }
}
