package com.macro.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @auther macro
 * @description
 * @date 2023/11/29 15:05
 */
public interface OssService
{
	String uploadFileAvatar(MultipartFile file);
}
