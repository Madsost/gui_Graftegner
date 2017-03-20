import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DBSimulering {
	private ArrayList<Double> inddata;
	private int kald = 0;
	private int muligeKald = 0;
	private final int START = 168;
	private final double DELTAMAX = 0.25;
	private double value = START;

	public DBSimulering() {
		File ind = new File("C:/Users/madso/Dropbox/Semesterprojekt 1/Datafiler/CaseC/Rå data.txt");
		try {
			Scanner input = new Scanner(new FileReader(ind));
			inddata = new ArrayList<>();
			while (input.hasNext()) {
				inddata.add(Double.parseDouble(input.nextLine()));
			}
			input.close();
			muligeKald = inddata.size() / 600;
			// System.out.println(muligeKald);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int hentMaaling() {
		if (kald < inddata.size()) {
			int maaling = (int) (inddata.get(kald) / 5 * 1024);
			kald++;
			return maaling;
		} else {
			kald = 0;
			int maaling = (int) (inddata.get(kald) / 5 * 1024);
			return 0;
		}
	}
	
	public int hentMaalingTemp(){
		value += (2*Math.random()-1.0) * DELTAMAX;
			if ( value < 0.0 ) value = 0.0;
			if ( value > 255.0 ) value = 255.0;
		return (int) value;
	}

	public int[][] hentData(int størrelse) {
		int[][] data = new int[størrelse][2];
		int start;
		if (kald == 0) {
			start = 1;
		} else {
			start = størrelse * kald + 1;
		}
		int i = start;
		if ((i + 600) >= inddata.size())
			i--;
		for (int j = 1; j < data.length; j++) {
			data[j - 1][0] = j - 1;
			data[j - 1][1] = (int) (inddata.get(i - 1) / 5 * 1024);
			i++;
		}
		if (kald++ >= muligeKald)
			kald = 0;
		return data;
	}
}