package com.thinkerwolf.frameworkstudy.common;

public abstract class OpResult {

    /**
     * 是否成功
     */
    protected boolean ok;
    /**
     * 信息
     */
    protected String msg;

    public OpResult(boolean ok) {
        this.ok = ok;
    }

    public OpResult(boolean ok, String msg) {
        this.ok = ok;
        this.msg = msg;
    }

    public boolean isOk() {
        return ok;
    }

    public String getMsg() {
        return msg;
    }

    private static OpResultSuccess success = new OpResultSuccess();

    public static OpResult ok() {
        return success;
    }

    public static OpResult fail(String msg) {
        return new OpResultFail(msg);
    }

}
