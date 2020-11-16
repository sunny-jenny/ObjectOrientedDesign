package com.elevator;

import java.util.PriorityQueue;

import com.elevator.controller.MachineController;
import com.elevator.exception.OverweightException;
import com.elevator.request.ExternalRequest;
import com.elevator.request.InternalRequest;

public class Elevator {
	private int elevatorId;
	private PriorityQueue<Integer> upStops;
	private PriorityQueue<Integer> downStops;
	public int currentLevel;
	private Status currentStatus;
	private int currentWeight;
	private int maxWeight;
	
	public Elevator(int maxWeight){
		this.maxWeight = maxWeight;
	}
	
	public void handleExternalRequest(ExternalRequest externalRequest) {
		if(externalRequest.direction == Direction.UP){
			upStops.offer(externalRequest.level);
		} else {
			downStops.offer(externalRequest.level);
		}
		run();
	}
	
	public void handleInternalRequest(InternalRequest internalRequest) {
		if(internalRequest.level > currentLevel) upStops.offer(internalRequest.level);
		else downStops.offer(internalRequest.level);
		run();
	}
	
	private void run(){
		int nextDestination = 0;
		if(currentStatus == Status.UP) {
			if(upStops.size() > 0){
				nextDestination = upStops.peek();
			} else if (downStops.size() > 0){
				nextDestination = downStops.peek();
				currentStatus = Status.DOWN;
			} else currentStatus = Status.IDLE;
		} else {
			if(downStops.size() > 0){
				nextDestination = downStops.peek();
			} else if (upStops.size() > 0){
				nextDestination = upStops.peek();
				currentStatus = Status.UP;
			} else currentStatus = Status.IDLE;
		}
		
		if(currentStatus != Status.IDLE ) {
			MachineController.goToNextDestination(currentStatus, nextDestination, this);
		}
	}
	
	private void onDoorOpen(){
		if(currentStatus == Status.UP){
			currentLevel = upStops.poll();
		} else {
			currentLevel = downStops.poll();
		}
	}
	
	private void onDoorClosed() throws OverweightException{
		if(isElevatorOverweighted()) {
			run();
		} else {
			throw new OverweightException("Overweighted alert!");
		}
	}
	
	private void pressButton(ElevatorButton button) {
		button.isPressed = true;
		handleInternalRequest(button.generateRequest());
	}
	
	private boolean isElevatorOverweighted() {
		return currentWeight > maxWeight;
	}
}
