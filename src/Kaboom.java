import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Kaboom extends Applet implements Runnable, MouseMotionListener {
	int bomberX = 20;
	int[] bombX = {bomberX, bomberX, bomberX};
	int[] bombY = {30, 30, 30};
	int playerX = 20;
	int lives = 5;
	int score = 0;
	boolean gameRunning = true;
	int level = 1;
	int timer = 0;
	@Override
	public void init() {
		setBackground(Color.WHITE);
		setSize(1200,600);
		addMouseMotionListener(this);
	}
	@Override
	public void start() {
		Thread th = new Thread(this);
		th.start();
	}
	@Override
	public void stop() {}
	@Override
	public void paint(Graphics g) {
		g.setFont(new Font("Press Start K", Font.PLAIN, 15));
		if (gameRunning) {
			g.setColor(Color.BLACK);
			g.fillRect(bomberX, 20, 50, 60);
			g.drawString("Lives: " + lives, 1062, 540);
			g.drawString("Score: " + score, 1060, 560);
			g.drawString("Level: " + level, 1060, 580);
			g.setColor(Color.RED);
			g.fillOval(bombX[0], bombY[0], 30, 40);
			g.fillOval(bombX[1], bombY[1], 30, 40);
			g.fillOval(bombX[2], bombY[2], 30, 40);
			g.setColor(Color.BLUE);
			g.fillRect(playerX, 500, 100, 25);
		} else {
			g.setColor(Color.RED);
			setBackground(Color.BLACK);
			g.setFont(new Font("Press Start K", Font.PLAIN, 50));
			g.drawString("Game Over", 370, 320);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Press Start K", Font.PLAIN, 20));
			g.drawString("You reached level " + level + ".", 400, 360);
		}
	}
	@Override
	public void run() {
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		while(gameRunning) {
			if (bomberX < 30) {
				bomberX++;
			}
			else if (bomberX > 1120) {
				bomberX--;
			}
			else {
				bomberX += (int)(Math.random() * (20 * level) - (10 * level));
			}
			for (int i = 0; i < 3; i++) {
				if (timer % 100 == 0 && bombY[i] < 0) {
					bombY[i] = 30;
					bombX[i] = bomberX;
					break;
				} else {
					if (bombY[i] > 600 ) {
						bombY[i] = 30;
						bombX[i] = bomberX;
						lives--;
					}
					if (Math.abs(bombX[i] + 15 - (playerX + 50)) < 50 && Math.abs(500 - bombY[i]) < 40) {
						bombY[i] = -100;
						score++;
					}
					if (bombY[i] > 0) {
						bombY[i] += 1 + level / 5;
					}
				}
			}
			if (timer > 3000) {
				level++;
				timer = 0;
			}
			timer++;
			if (lives <= 0) {
				gameRunning = false;
			}
			repaint();
			try {
				Thread.sleep(3);
			} catch (InterruptedException Ex) {}
		}
	}
	@Override
	public void mouseDragged(MouseEvent e) {}
	@Override
	public void mouseMoved(MouseEvent e) {
		playerX = e.getX() - 50;
	}
}
