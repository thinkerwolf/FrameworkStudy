package com.thinkerwolf.frameworkstudy.rpc.extention;

import com.alibaba.dubbo.common.extension.Adaptive;
import com.alibaba.dubbo.common.extension.SPI;

@SPI
public interface IWheel {
	@Adaptive({"make", "wheel"})
	String make();
	
	@Adaptive({"destory", "wheel"})
	String destory();
}
