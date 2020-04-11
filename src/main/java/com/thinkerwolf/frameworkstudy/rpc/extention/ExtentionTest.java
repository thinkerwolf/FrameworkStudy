package com.thinkerwolf.frameworkstudy.rpc.extention;

import com.alibaba.dubbo.common.extension.ExtensionLoader;

public class ExtentionTest {
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		ExtensionLoader<IWheel> loader = ExtensionLoader.getExtensionLoader(IWheel.class);
		//loader.getActivateExtension(url, key)
		//loader.addExtension(BigWheel.class.getName(), BigWheel.class);
		IWheel wheel = ExtensionLoader.getExtensionLoader(IWheel.class).getAdaptiveExtension();
		
		
		
		int t = 1;
		
		
	}
}


