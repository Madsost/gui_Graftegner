import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.util.Timer;

public class Graftegner3 extends JPanel {

	private static int MAXX = 60;
	private static int MINX = 0;
	private static int MINY = 0;
	private static int MAXY = 500;
	private static int STEP = 10;
	int tael = 0;
	DBSimulering inddata;
	ArrayList<Integer> maalinger;
	int sampleSize = 0;

	public Graftegner3(int updateTime, int sampleSize) {
		maalinger = new ArrayList<>();
		inddata = new DBSimulering();
		this.sampleSize = sampleSize;
		MAXX = sampleSize;
		STEP = sampleSize / 3;
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				repaint();
			}

		}, 0, updateTime);
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
			g2.drawLine(i, -2, i, 2);
		}
		/*for (int i = (MINY / STEP) * STEP; i <= MAXY; i += STEP) {
			g.drawLine(-2, i, 2, i);
		}*/
		g2.drawLine(MINX, 0, MAXX, 0);
		g2.drawLine(0, MINY, 0, MAXY);

		g2.setColor(Color.RED);
		int punkt = inddata.hentMaalingTemp();
		maalinger.add(punkt);
		if (maalinger.size() >= sampleSize)
			maalinger = new ArrayList<>();
		if (maalinger.size() >= 2) {
			for (int j = 1; j < maalinger.size() - 1; j++) {
				g2.drawLine(j - 1, maalinger.get(j - 1), j, maalinger.get(j));
			}
		}
	}

}
