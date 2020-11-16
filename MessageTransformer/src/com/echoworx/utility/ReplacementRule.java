package com.echoworx.utility;

public class ReplacementRule implements Rule {

	@Override
	public void runRuleAgainstBody(Message m) {
		if (m.isValid()) {
			StringBuilder sb = new StringBuilder(m.getBody());
			for (int index = 0; index < sb.length(); index++) {
				if (sb.charAt(index) == '$') {
					sb.setCharAt(index, 'e');
				} else if (sb.charAt(index) == '^') {
					sb.setCharAt(index, 'y');
				} else if (sb.charAt(index) == '&') {
					sb.setCharAt(index, 'u');
				}
			}
			m.setBody(sb.toString());
		}
	}

	@Override
	public int getPriority() {
		return 1;
	}

}
