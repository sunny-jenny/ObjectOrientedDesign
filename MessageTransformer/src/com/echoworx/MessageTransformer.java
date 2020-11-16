package com.echoworx;

import java.util.List;
import java.util.Map;

import com.echoworx.service.FileService;
import com.echoworx.service.TransformationService;
import com.echoworx.utility.Message;

public class MessageTransformer {

	public static void main(String[] args) {
		if (args.length >= 1) {
			String inputPath = args[0], outputPath = args[0] + "\\out";
			if (args.length > 1) {
				outputPath = args[1];
			}
			Map<String, Message> messages = FileService.read(inputPath);
			TransformationService.transferMessage(messages);
			List<String> result = FileService.write(messages, outputPath);
			result.forEach(System.out::println);
		} else {
			System.out.println("Please provide input and output file(s) path...");
			System.out.println("If only input path is given, output will be generated under the same directory...");
		}
	}

}
