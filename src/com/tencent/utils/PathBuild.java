package com.tencent.utils;

import java.io.File;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;

public class PathBuild {

	public static String getPath(String cmd) {

		String now = DateUtil.now().replaceAll("[\\pP\\pS\\pZ]", "_");
		System.out.println(now);
		String path = "D:\\testtemp\\" + now + ".bat";
		FileUtil.touch(path);
		FileUtil.appendString("@echo off\r\n", new File(path), "UTF-8");
		FileUtil.appendString(cmd + "\r\n", new File(path), "UTF-8");
		// FileUtil.appendString("pause", new File(path), "UTF-8");
		System.out.println("path:" + path);
		return path;
	}

}
