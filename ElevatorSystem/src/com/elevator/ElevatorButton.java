package com.elevator;

import com.elevator.request.InternalRequest;

public class ElevatorButton {
	private int level;
	public boolean isPressed;
	public InternalRequest generateRequest() {
		return new InternalRequest(level);
	}
}
