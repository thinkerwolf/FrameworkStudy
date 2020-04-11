package com.thinkerwolf.frameworkstudy.rpc.extention;

import com.alibaba.dubbo.common.extension.Activate;

@Activate
public class BigWheel implements IWheel{
	@Override
	public String make() {
		return "big wheel make";
	}
	@Override
	public String destory() {
		return "big wheel destory";
	}

}
