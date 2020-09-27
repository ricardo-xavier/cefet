package xavier.ricardo.myfuzzy.utils;

import android.util.Log;

import xavier.ricardo.myfuzzy.tipos.Termo;

public class Triangulo {

    public static double getY(Termo termo, int crisp) {

        Log.i("simulador", "triangulo getY:" + termo.getNome());

        if (crisp == termo.getB()) {
            return 1.;
        }

        if (crisp == termo.getA() || crisp == termo.getC()) {
            return 0.;
        }

        double x1;
        double x2;
        double y1;
        double y2;

        double b;
        if (crisp < termo.getB()) {
            x1 = termo.getA();
            y1 = 0;
            x2 = termo.getB();
            y2 = 1;
            b = termo.getA();
        } else {
            x1 = termo.getB();
            y1 = 1;
            x2 = termo.getC();
            y2 = 0;
            b = termo.getC();
        }

        double a = (y2 - y1) / (x2 - x1);

        double y = a * ( crisp - b);
        Log.i("simulador", "y:" + y);
        return y;
    }

    public static double getX1(Termo termo, double y) {

        Log.i("simulador", "triangulo gwtX1:" + termo.getNome());

        if (termo.getA() == termo.getB()) {
            return termo.getA();
        }

        double x1 = termo.getA();
        double y1 = 0;
        double x2 = termo.getB();
        double y2 = 1;

        double a = (y2 - y1) / (x2 - x1);
        double x = y / a + termo.getA();
        Log.i("simulador", "x:" + x);
        return x;
    }

    public static double getX2(Termo termo, double y) {

        Log.i("simulador", "triangulo gwtX1:" + termo.getNome());

        if (termo.getB() == termo.getC()) {
            return termo.getB();
        }

        double x1 = termo.getB();
        double y1 = 1;
        double x2 = termo.getC();
        double y2 = 0;

        double a = (y2 - y1) / (x2 - x1);
        double x = y / a + termo.getC();
        Log.i("simulador", "x:" + x);
        return x;
    }

}
