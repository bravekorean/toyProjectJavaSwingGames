package games;

import java.awt.Color;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RaceThread extends JFrame	{
	
	static JLabel topCar;
	  private JLabel finishLine;
	  boolean stop = false;
	
	RaceThread() {
		  setTitle("CarGame");
	      setSize(600, 200);
	      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      setLayout(null);
	      	MyThread car1 = new MyThread("Car 1", "./images/car1.png", 100, 0);
	        MyThread car2 = new MyThread("Car 2", "./images/car2.png", 100, 50);
	        MyThread car3 = new MyThread("Car 3", "./images/car3.png", 100, 100);
	        car1.start();
	        car2.start();
	        car3.start();
	      
	      finishLine = new JLabel("Finish Line");
	      finishLine.setBounds(550, 0, 10, this.getHeight());
	      finishLine.setBorder(BorderFactory.createLineBorder(Color.red));
	      add(finishLine);
	      
	      
	      topCar = new JLabel();
	      topCar.setBounds(250, 50, 500, 30);
	      topCar.setForeground(Color.BLUE);
	      add(topCar);

	      
	      setVisible(true);
	}
	
class MyThread extends Thread {
	private JLabel label;
	private int x, y;
	private String carName;
	
	public MyThread(String carName, String fname, int x, int y) {
		this.carName = carName;
		this.x = x;
		this.y = y;
		label = new JLabel();
		label.setIcon(new ImageIcon(fname));
		label.setBounds(x, y, 100, 100);
		add(label);
	}

	public void run() {
		
		 for (int i = 0; i < 200; i++) {
             if (stop) {
                 break;
             }

             x += 10 * Math.random();
             label.setBounds(x, y, 100, 100);
             repaint();

             if (x >= 530) {
                 stop = true;
                 displayWinner(carName);
                 break;
             }

             try {
                 Thread.sleep(100);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         }
     }
	
	 private void displayWinner(String winnerName) {
	        if (topCar.getText().isEmpty()) {
	            topCar.setText("Winner: " + winnerName);
	        }
	    }

	
}

	public static void main(String[] args) {
		RaceThread race = new RaceThread();
	}
}
