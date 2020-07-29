package com.thinkerwolf.frameworkstudy.autoconfig.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("rpc.server")
public class RpcServerProperties {
	/**
	 * RPC 端口
	 */
	private int port;
	/**
	 * RPC 注册中心
	 */
	private String registerAddress;
	/**
	 * RPC 是否可以使用
	 */
	private boolean enabled;
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getRegisterAddress() {
		return registerAddress;
	}

	public void setRegisterAddress(String registerAddress) {
		this.registerAddress = registerAddress;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	
	
	
}
