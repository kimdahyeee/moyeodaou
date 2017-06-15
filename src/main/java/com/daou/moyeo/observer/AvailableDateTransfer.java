package com.daou.moyeo.observer;

import java.util.Observable;
import java.util.Observer;

public class AvailableDateTransfer implements Observer, TransferElement{
	Observable observable;
	
	private int[][] availableDate;
	
	public AvailableDateTransfer(Observable observable) {
		this.observable = observable;
		observable.addObserver(this);
	}
	
	@Override
	public void update(Observable obs, Object arg) {
		if(obs instanceof CalculateSchedule) {
			CalculateSchedule calculateSchedule = (CalculateSchedule)obs;
			this.availableDate = calculateSchedule.getAvailableDate();
			transfer();
		}
	}
	
	@Override
	public void transfer() {
		System.out.println("Available Date : " + availableDate);
		String[] day = {"S","M","T","W","T","F","S"};
		for(int j = 0; j < 7; j++) {
			System.out.print(" [");
			System.out.print(day[j]);
			System.out.print("]");
		}
		System.out.println("");
		for(int i = 0; i < 15; i++)  {
			
			for(int j = 0; j < 7; j++) {
				System.out.print(" [");
				System.out.print(availableDate[i][j]);
				System.out.print("]");
			}
			System.out.println("");
		}
	}
}
