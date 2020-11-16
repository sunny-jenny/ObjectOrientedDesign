package com.elevator;

import java.util.ArrayList;
import java.util.List;

import com.elevator.request.ExternalRequest;

public class ElevatorSystem {
	
	private List<Elevator> elevators;
	private static int NUM_OF_ELEVATORS = 3;
	private static int MAX_WEIGHT = 1400;
	
	public ElevatorSystem() {
		elevators = new ArrayList<>();
		for(int i = 0; i < NUM_OF_ELEVATORS; i++){
			Elevator e = new Elevator(MAX_WEIGHT);
			elevators.add(e);
		}
	}
	
	public void sendExternalRequest(ExternalRequest externalRequest) {
		Elevator closest = null;
		int minDistance = Integer.MAX_VALUE;
		for(Elevator elevator: elevators){
			if(Math.abs(elevator.currentLevel - externalRequest.level) < minDistance){
				minDistance = Math.abs(elevator.currentLevel - externalRequest.level);
				closest = elevator;
			}
		}
		closest.handleExternalRequest(externalRequest);
	}	
}
