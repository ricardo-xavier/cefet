package xavier.ricardo.myfuzzy.utils;

import android.util.Log;

import xavier.ricardo.myfuzzy.tipos.Termo;

public class Triangulo {

    public static double getY(Termo termo, int crisp) {

        Log.i("simulador", "triangulo:" + termo.getNome());

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
}
