package com.thinkerwolf.frameworkstudy.dwr;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

@RemoteProxy
public class RemoteFunctions {
    @RemoteMethod
    public int calculateFoo() {
        return 42;
    }
}
