package com.echoworx.utility;

public class Message {
	private String[] receipient;
	private String sender;
	private String subject;
	private String body;

	public String[] getReceipient() {
		return receipient;
	}

	public void setReceipient(String[] receipient) {
		this.receipient = receipient;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public boolean isValid() {
		return receipient != null && sender != null && subject != null && body != null;
	}
}
