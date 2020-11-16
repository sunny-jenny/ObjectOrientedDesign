package com.echoworx.service;

import java.util.Set;
import java.util.TreeSet;

import com.echoworx.utility.Message;
import com.echoworx.utility.ReplacementRule;
import com.echoworx.utility.ReversalRule;
import com.echoworx.utility.Rule;

public class RuleService {
	public static Set<Rule> selectRule(Message m) {
		Set<Rule> rules = new TreeSet<>((a, b) -> a.getPriority() - b.getPriority());
		for (String r : m.getReceipient()) {
			if (r.indexOf("domain.com") != -1) {
				rules.add(new ReplacementRule());
				break;
			}
		}

		if (m.getSubject().startsWith("SECURE")) {
			rules.add(new ReversalRule());
		}

		if (m.getBody().matches("(?s).*[0-9]{10}(?s).*")) {
			rules.add(new ReplacementRule());
			rules.add(new ReversalRule());
		}

		return rules;
	}
}
