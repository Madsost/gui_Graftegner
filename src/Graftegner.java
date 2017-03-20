
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.util.Timer;

public class Graftegner extends JPanel {

	private int[][] data;
	private static int MAXX = 600;
	private static int MINX = -1;
	private static int MINY = -1;
	private static int MAXY = 1050;
	private static int STEP = 200;
	// private int count = 0;
	DBSimulering inddata;

	public Graftegner() {
		inddata = new DBSimulering();
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				data = inddata.hentData(600);
				repaint();
			}
		}, 0, 3000);
	}

	public static void setMAXX(int maxNy) {
		MAXX = maxNy;
	}

	public static void setMINX(int minxNy) {
		MINX = minxNy;
	}

	public static void setMAXY(int maxyNy) {
		MAXY = maxyNy;
	}

	public static void setMINY(int minyNy) {
		MINY = minyNy;
	}

	public static int getStep() {
		return STEP;
	}

	public void opdater() {
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// int start = 200 * ++count;
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform af = new AffineTransform();

		double h = getHeight() - 2;
		double b = getWidth() - 2;
		double deltaX = b / (MAXX - MINX);
		double deltaY = h / (MAXY - MINY);
		double origoX = -(double) MINX * deltaX;
		double origoY = h + (double) (MINY) * deltaY;

		af.setToTranslation(origoX, origoY);
		g2.transform(af);
		af.setToScale(deltaX, -deltaY);
		g2.transform(af);

		g2.setColor(Color.BLUE);
		for (int i = (MINX / STEP) * STEP; i <= MAXX; i += STEP) {
			g2.drawLine(i, -5, i, 5);
		}
		for (int i = (MINY / STEP) * STEP; i <= MAXY; i += STEP) {
			g.drawLine(-5, i, 5, i);
		}
		g2.drawLine(MINX, 0, MAXX, 0);
		g2.drawLine(0, MINY, 0, MAXY);
		g2.setColor(Color.BLACK);
		for (int j = 1; j < data.length - 1; j++) {
			g2.drawLine((int) ((double) data[j - 1][0]), (int) (((double) data[j - 1][1])), (int) ((double) data[j][0]),
					(int) (((double) data[j][1])));
		}
	}

}
