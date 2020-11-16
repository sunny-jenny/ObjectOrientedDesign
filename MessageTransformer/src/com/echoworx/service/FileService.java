package com.echoworx.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.echoworx.utility.Message;

public class FileService {
	public static Map<String, Message> read(String path) {
		File folder = new File(path);
		if (folder.isDirectory()) {
			File[] allFiles = folder.listFiles();
			Map<String, Message> result = parseFiles(allFiles);
			return result;
		} else {
			return null;
		}
	}

	private static Map<String, Message> parseFiles(File[] allFiles) {
		Map<String, Message> result = new HashMap<>();
		for (File f : allFiles) {
			if (f.isFile()) {
				Message m = new Message();
				try (BufferedReader br = new BufferedReader(new FileReader(f))) {
					String line;
					while ((line = br.readLine()) != null) {
						if (!line.trim().isEmpty()) {
							if (line.startsWith("To:")) {
								String toString = line.substring(line.indexOf("To:") + 3).trim();
								m.setReceipient(toString.split(","));
							} else if (line.startsWith("From:")) {
								String fromString = line.substring(line.indexOf("From:") + 5).trim();
								m.setSender(fromString);
							} else if (line.startsWith("Subject:")) {
								String subjectString = line.substring(line.indexOf("Subject:") + 8).trim();
								m.setSubject(subjectString);
							} else if (line.startsWith("Body:")) {
								StringBuilder sb = new StringBuilder();
								while ((line = br.readLine()) != null) {
									sb.append(line).append("\n");
								}
								m.setBody(sb.toString());
							}
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				result.put(f.getName(), m);
			}
		}
		return result;
	}

	public static List<String> write(Map<String, Message> messages, String path) {
		List<String> result = new ArrayList<>();
		File folder = new File(path);
		if (!folder.isDirectory()) {
			folder.mkdirs();
		}
		for (String inputName : messages.keySet()) {
			String outputName = (new StringBuilder(inputName)).insert(inputName.lastIndexOf("."), "_transformed")
					.toString();
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(path + "\\" + outputName))) {
				Message m = messages.get(inputName);
				if (m.isValid()) {
					bw.write("To:" + String.join(",", m.getReceipient()) + "\n");
					bw.write("From:" + m.getSender() + "\n");
					bw.write("Subject:" + m.getSubject() + "\n");
					bw.write("Body:" + "\n");
					bw.write(m.getBody());
					result.add("File " + inputName + " transformed...");
				} else {
					bw.write("Input file is invalid, no output can be generated!");
					result.add("File " + inputName + " processing failed!!!");
				}
				bw.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
}
