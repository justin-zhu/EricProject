package com.tencent.test;

import java.io.File;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;

public class test {
	public static void main(String[] args) {
		String id = "3285741";
		String now = DateUtil.now().replaceAll("[\\pP\\pS\\pZ]", "_");
		System.out.println(now);
		File[] roots = File.listRoots();
		int index = roots.length;
		String partition = roots[(index - 1)].toString();
		File file = new File(partition + "\\log", id + "_" + now + ".txt");
		FileUtil.touch(file);
		System.out.println(file.getAbsolutePath());
	}
}
