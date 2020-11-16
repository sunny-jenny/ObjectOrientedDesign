package com.echoworx.utility;

public interface Rule {
	public int getPriority();

	public void runRuleAgainstBody(Message m);

}
