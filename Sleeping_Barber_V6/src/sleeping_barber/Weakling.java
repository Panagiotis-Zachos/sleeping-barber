package sleeping_barber;

import java.awt.Button;
import java.awt.Color;
import java.util.Random;

import gui.BarberGui;


public class Weakling implements Runnable{
	
	private Button b;
	private int weakling_no;
	private static Color col[] = {Color.MAGENTA, Color.RED, Color.YELLOW, Color.ORANGE, Color.CYAN, Color.BLACK, Color.DARK_GRAY, Color.PINK, Color.PINK, Color.GRAY};
	private int posX = 0;
	private static Barber barber;
	private static Random rand = new Random();
	
	public Weakling(BarberGui gui, int weakling_no, Barber barber){
		
		Weakling.barber = barber;
		this.weakling_no = weakling_no;
		this.b = new Button(Integer.toString(this.weakling_no));
		this.b.setBounds(BarberGui.pos[this.weakling_no][0].x, BarberGui.pos[this.weakling_no][0].y, 20, 20);
		this.b.setBackground(col[weakling_no]);
		gui.add(b);
	}

	public void run() {
		
		try {
			while(true){
				
				switch(this.posX) {
				
				case 0:
					exit();
					break;
				case 1:
					enter();
					break;
				case 2:
					sitAndWait();
					break;
				case 3:
					shave();
					break;
				}		
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	private void enter() throws InterruptedException{
		
		Sleeping_B.Waiting.acquire();
		this.b.setLocation(BarberGui.pos[this.weakling_no][this.posX]);
		this.posX++;	//posX = 2
		Thread.sleep(Weakling.rand.nextInt(2000) + 500);
	}
	
	private void sitAndWait() throws InterruptedException{
		
		this.b.setLocation(BarberGui.pos[this.weakling_no][this.posX]);
		this.posX++;	//posX = 3
		Weakling.barber.waitList.incrementAndGet();
		Thread.sleep(Weakling.rand.nextInt(2000) + 500);
		Sleeping_B.Shaving.acquire();
		Sleeping_B.Waiting.release();
	}
	
	private void shave() throws InterruptedException{
		
		Weakling.barber.waitList.decrementAndGet();
		this.b.setLocation(BarberGui.pos[this.weakling_no][this.posX]);
		this.posX = 0;	//posX = 0
		Thread.sleep(Weakling.rand.nextInt(2000) + 500);
		
		Weakling.barber.incShaves();
		if(Weakling.barber.getShaves() != 4){
			Sleeping_B.Shaving.release();
		}
	}
	
	private void exit() throws InterruptedException{
		
		this.b.setLocation(BarberGui.pos[this.weakling_no][this.posX]);
		this.posX++;
		Thread.sleep(Weakling.rand.nextInt(2000) + 500);
		
	}
}
