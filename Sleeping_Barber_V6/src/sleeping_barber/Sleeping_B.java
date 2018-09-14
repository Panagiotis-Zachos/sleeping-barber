package sleeping_barber;


import java.util.concurrent.Semaphore;

import gui.BarberGui;

public class Sleeping_B {
	
	public static Semaphore Shaving = new Semaphore(1);
	public static Semaphore Waiting = new Semaphore(4);
	
	public static void main(String[] args) {
		
		
		BarberGui gui = new BarberGui();
		Barber barber = new Barber(gui);
		Weakling[] weakling = new Weakling[10];
		Thread[] threads = new Thread[11];
		
		for(int i=0; i < 10; i++){
			weakling[i] = new Weakling(gui,i, barber);
			threads[i] = new Thread(weakling[i]);
			threads[i].start();
		}
		threads[10] = new Thread(barber);
		threads[10].start();
	}
}
