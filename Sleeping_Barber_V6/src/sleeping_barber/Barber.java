package sleeping_barber;

import java.awt.Button;
import java.awt.Color;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import gui.BarberGui;

public class Barber implements Runnable{
	
	private Button barberIcon = new Button("B");
	private Integer shaves = 0;
	public  AtomicInteger waitList = new AtomicInteger(0);
	private static Random rand = new Random();
	
	public Barber(BarberGui gui) {
		barberIcon.setBackground(Color.GREEN);
		this.work();
		gui.add(this.barberIcon);
	}
	
	public void run() {
		while(true) {
			try {
				Thread.sleep(0);
				if(this.shaves == 4) {
					this.rest();
					this.work();
				}
				else if(this.waitList.get() == 0) {
					this.rest();
					this.work();
				}
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}			

	public void rest() throws InterruptedException {
		Sleeping_B.Shaving.drainPermits();
		this.shaves = 0;
		this.barberIcon.setBounds(215,200, 20, 40); 
		Thread.sleep(Barber.rand.nextInt(1500) + 1000);
		Sleeping_B.Shaving.release();
	}
	
	public void work() {
		this.barberIcon.setBounds(271,57, 30, 30);
	}

	public int getShaves() {
		return this.shaves;
	}

	public void incShaves() {
		this.shaves++;
	}	
}
