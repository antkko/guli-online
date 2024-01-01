package com.macro.oss.controller;

import com.macro.commonutils.R;
import com.macro.oss.service.OssService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @auther macro
 * @description
 * @date 2023/11/29 15:05
 */
@CrossOrigin
@RestController
@RequestMapping("eduoss/fileoss")
public class OssController
{
	@Resource
	private OssService ossService;

	/**
	 * 上传头像的方法
	 *
	 * @param file 上传的文件
	 * @return
	 */
	@PostMapping("upload")
	public R uploadOssFile(final MultipartFile file)
	{
		final String url = ossService.uploadFileAvatar(file);
		return R.ok().data("url", url);
	}
}
