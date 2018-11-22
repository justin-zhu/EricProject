package com.tencent.erci;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class GetDevices {

	public static HashSet<String> getDevices() {
		Runtime runtime = Runtime.getRuntime();
		Process process = null;
		try {
			process = runtime.exec("cmd /c adb devices");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Set<String> set = new HashSet<>();
		BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				if (line.endsWith("device")) {
					String[] str = line.split("device");
					for (int i = 0; i < str.length; i++) {
						set.add(str[0].toString());
						// JOptionPane.showMessageDialog(null,
						// str[0].toString());
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		process.destroy();
		return (HashSet<String>) set;
	}
}
