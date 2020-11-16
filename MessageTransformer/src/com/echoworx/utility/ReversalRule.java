package com.echoworx.utility;

public class ReversalRule implements Rule {

	@Override
	public void runRuleAgainstBody(Message m) {
		if (m.isValid()) {
			StringBuilder sb = new StringBuilder();
			for (String line : m.getBody().split("\n")) {
				for (String part : line.split(" ")) {
					sb.append(new StringBuilder(part).reverse().toString()).append(" ");
				}
				sb.append("\n");
			}
			m.setBody(sb.toString());
		}
	}

	@Override
	public int getPriority() {
		return 2;
	}

}
