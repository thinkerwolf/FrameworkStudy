package com.thinkerwolf.frameworkstudy.concurrent;

public abstract class IntGenerator {

    private volatile boolean canceled;

    public abstract int next();

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
}
