package xavier.ricardo.myfuzzy.utils;

public class Centroid {
	
	public static double centroid(double[] x, double[] y) {
		
		double sumMomentArea = 0.;
		double sumArea = 0.;
		
		for (int i=1; i<x.length; i++) {
			double x1 = x[i-1];
			double x2 = x[i];
			double y1 = y[i-1];
			double y2 = y[i];
			
			if ((y1 == 0.) && (y2 == 0.)) {
				continue;
			}
			
			double moment = 0.;
			double area = 0.;
			
			double b = x2 - x1;
			double h;
			if (y1 == y2) {
				// reta
				moment = (x1 + x2) / 2.;
				h = y1;
				area = b * h;
			
			} else if ((y1 == 0.) && (y2 != 0.)) {
				// triangulo /|
				
				moment = 2.0 / 3.0 * (x2 - x1) + x1;
				h = y2;
				area = b * h / 2.;
				
			} else if ((y1 == 0.) && (y2 != 0.)) {
				// triangulo |\
				moment = 1.0 / 3.0 * (x2 - x1) + x1;
				h = y1;
				area = b * h / 2.;
			
			} else {
				moment = (2.0 / 3.0 * (x2 - x1) * (y2 + y1 / 2.0)) / (y1 + y2) + x1; // ???
				area = b * (y1 + y2) / 2.;
			}

			sumMomentArea += moment * area;
			sumArea += area;
			
			//System.out.printf("%d-%s %.0f-%.0f %.1f-%.1f%n", i-1, i, x1, x2, y1, y2);
			//System.out.println(moment + " " + area);
		}
		
		double centroid = sumMomentArea / sumArea;
		return centroid;
	}
	
	public static void main(String[] args) {
		
		double[] x = new double[26];
		double[] y = new double[26];
		
		for (int i=0; i<26; i++) {
			x[i] = i;
			y[i] = 0;
		}
		for (int i=0; i<6; i++) {
			y[i] = 1;
		}
		y[6] = 0.8;
		y[7] = 0.6;
		y[8] = 0.4;
		y[9] = 0.2;
		
		double c = centroid(x, y);
		System.out.println("c=" + c);
	}

}
