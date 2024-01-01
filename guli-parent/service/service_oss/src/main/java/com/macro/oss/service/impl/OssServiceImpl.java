package com.macro.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.macro.oss.service.OssService;
import com.macro.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

/**
 * @auther macro
 * @description
 * @date 2023/11/29 15:06
 */
@Service
public class OssServiceImpl implements OssService
{

	@Override
	public String uploadFileAvatar(final MultipartFile file)
	{
		final String endpoint = ConstantPropertiesUtils.END_POINT;
		final String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
		final String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
		final String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;

		try {
			// 创建OSSClient实例。
			final OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
			// 获取上传文件的输入流
			final InputStream inputStream = file.getInputStream();

			// 获取文件名称
			String fileName = file.getOriginalFilename();

			// 1.在文件名称前面添加随机值
			final String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			fileName = uuid + fileName;

			// 把文件按照日期进行分类
			final String datePath = new DateTime().toString("yyyy/MM/dd");
			fileName = datePath + "/" + fileName;

			/*
				调用oss方法实现上传(以下是参数介绍)
					1.Bucket名称
					2.上传到oss文件路径和文件名称 /aa/bb/1.jpg
					3.上传文件输入流
			 */
			ossClient.putObject(bucketName, fileName, inputStream);

			// 关闭oss
			ossClient.shutdown();

			// 把上传之后的文件路径返回
			// 需要把上传到阿里云oss路径手动拼接起来
			return "https://" + bucketName + "." + endpoint + "/" + fileName;
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}