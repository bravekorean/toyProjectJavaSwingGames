package games;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ballonClass extends JFrame {
	
	ballonClass() {
		setTitle("풍선터트리기");
		setSize(500,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GamePanel p = new GamePanel();
		setContentPane(p);
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new ballonClass();
	}
	
class GamePanel extends JPanel {
	 JButton stopBtn;
     JButton startBtn;
     JLabel score;
     int scoreData = 0;
     JLabel timeLabel;
     int timeLimit = 10;
     Timer time;
     Thread balloonThread;
	public GamePanel() {
		setLayout(null);
		  stopBtn = new JButton("Stop");
	       startBtn = new JButton("Start");

	        // Set the position and size of the buttons
	        startBtn.setBounds(10, 10, 80, 30);
	        stopBtn.setBounds(100, 10, 80, 30);
	        startBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	timeLimit = 10;
                    timeLabel.setText("Seconds: " + timeLimit);
                    scoreData = 0;
                    score.setText("Score: 0");

                    
                    time.start();
                    createBalloonThread();
                }
            });
	        startBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    Component[] components = getComponents();
                    for (Component comp : components) {
                        if (comp instanceof JLabel) {
                            if (comp.getBounds().contains(e.getPoint())) {
                                // A balloon is clicked
                                remove(comp); // Remove the clicked balloon
                                scoreData++; // Increase the score
                                score.setText("Score: " + scoreData);
                                repaint();
                                return;
                            }
                        }
                    }
                }
            });
	        
	        stopBtn.addActionListener(new ActionListener() {

	        	@Override
                public void actionPerformed(ActionEvent e) {
	        		timeLimit = 0;
                    timeLabel.setText("Seconds: " + timeLimit);
                    time.stop();
                    if (balloonThread != null && balloonThread.isAlive()) {
                        balloonThread.interrupt();
                    }
                    
                    for (Component comp : getComponents()) {
                        if (comp instanceof JLabel) {
                            remove(comp);
                        }
                    }
                    repaint();
                }
	        	
	        });
	        
	        score = new JLabel("Score : 0");
	        timeLabel = new JLabel("seconds : " + timeLimit);
	        
	        time = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    timeLimit--;
                    timeLabel.setText("Seconds: " + timeLimit);
                    if (timeLimit == 0) {
                        time.stop();
                        if (balloonThread != null && balloonThread.isAlive()) {
                            balloonThread.interrupt();
                        }
                    }
                }
            });
	        
	       
	        
	        score.setBounds(200, 10, 80, 50);
	        timeLabel.setBounds(200, 10, 80, 30);
	        
	        
		this.add(startBtn);
		this.add(stopBtn);	
		this.add(timeLabel);
		this.add(score);
		
	}
	
	class BallonThread extends Thread {
		private JLabel jb;
		
		public BallonThread(int ballonX, int ballonY) {
			
			ImageIcon img = new ImageIcon("images/ballon.jpg");
			jb = new JLabel(img);
			jb.setSize(img.getIconWidth(),img.getIconWidth());
			jb.setLocation(ballonX,ballonY);
			jb.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // 클릭 시 풍선 제거 및 점수 증가
                    GamePanel.this.remove(jb);
                    GamePanel.this.repaint();
                    scoreData++;
                    score.setText("Score: " + scoreData);
                }
            });
			GamePanel.this.add(jb);
			GamePanel.this.repaint();
		}
		@Override
		public void run() {
			while(true) {
				Random random = new Random();
				
				int x = random.nextInt(GamePanel.this.getWidth() - 50);
				int y = random.nextInt(GamePanel.this.getHeight() - 50);
//				int x = jb.getX();
//				int y = jb.getY() - 5 ;
				if (y < 0) {
					GamePanel.this.remove(jb);
					GamePanel.this.repaint();
					return;
				}
				jb.setLocation(x,y);
				GamePanel.this.repaint();
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
	}
	
	private void createBalloonThread() {
        balloonThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                while (timeLimit > 0) {
                    int randomX = random.nextInt(getWidth() - 50);
                    int randomY = getHeight() - 50; // Start balloons at the bottom
                    BallonThread bcl = new BallonThread(randomX, randomY);
                    bcl.start();
                    try {
                        Thread.sleep(1000); // Adjust the interval as needed
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                
                
            }
        });
        balloonThread.start();
    }
	
}


	

}
