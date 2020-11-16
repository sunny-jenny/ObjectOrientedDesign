package com.echoworx.service;

import java.util.Map;
import java.util.Set;

import com.echoworx.utility.Message;
import com.echoworx.utility.Rule;

public class TransformationService {
	public static void transferMessage(Map<String, Message> messages) {
		for (Message m : messages.values()) {
			Set<Rule> rules = RuleService.selectRule(m);
			rules.forEach(r -> r.runRuleAgainstBody(m));
		}
	}
}
