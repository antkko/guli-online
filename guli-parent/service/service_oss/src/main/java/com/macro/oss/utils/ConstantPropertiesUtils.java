package com.macro.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @auther macro
 * @description InitializingBean:当项目启动,spring接口,spring加载之后,执行的一个接口方法
 * @date 2023/11/29 14:53
 */
@Component
public class ConstantPropertiesUtils implements InitializingBean
{
	@Value("${aliyun.oss.file.endpoint}")
	private String endpoint;

	@Value("${aliyun.oss.file.keyId}")
	private String keyId;

	@Value("${aliyun.oss.file.keySecret}")
	private String keySecret;

	@Value("${aliyun.oss.file.bucketName}")
	private String bucketName;

	public static String END_POINT;
	public static String ACCESS_KEY_ID;
	public static String ACCESS_KEY_SECRET;
	public static String BUCKET_NAME;


	@Override
	public void afterPropertiesSet()
	{
		ConstantPropertiesUtils.END_POINT = endpoint;
		ConstantPropertiesUtils.ACCESS_KEY_ID = keyId;
		ConstantPropertiesUtils.ACCESS_KEY_SECRET = keySecret;
		ConstantPropertiesUtils.BUCKET_NAME = bucketName;
	}
}
