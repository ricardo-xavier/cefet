package xavier.ricardo.myfuzzy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import xavier.ricardo.myfuzzy.tipos.Problema;
import xavier.ricardo.myfuzzy.tipos.Regra;
import xavier.ricardo.myfuzzy.tipos.Termo;
import xavier.ricardo.myfuzzy.tipos.TipoVariavel;
import xavier.ricardo.myfuzzy.tipos.Variavel;
import xavier.ricardo.myfuzzy.utils.Triangulo;

public class DrawResultView extends View {

    private int width;
    private int height;
    private int xMargin = 100;
    private int yMargin = 100;

    public DrawResultView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        width = MeasureSpec.getSize(widthMeasureSpec);
        height = width;
        setMeasuredDimension(width, MeasureSpec.getSize(heightMeasureSpec));

    }

    private float _x(float x) {
        return x + xMargin;
    }
    private float _y(float y) {
        return height - y - yMargin;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Paint paint = new Paint();
        paint.setColor(Color.rgb(255, 255, 255));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), paint);

        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(1);

        // eixo Y
        float y1 = 0f;
        float y2 = height - yMargin * 2;
        float x1 = 0f;
        float x2 = 0f;
        canvas.drawLine(_x(x1), _y(y1), _x(x2), _y(y2), paint);

        // marcadores do eixo Y
        paint.setTextSize(20);
        float dy = ( y2 - y1 ) / 10f;
        float y = dy;
        float x = 0f;
        float v = 0.1f;
        for (int i=0; i<10; i++) {
            canvas.drawLine(_x(x-10), _y(y), _x(x+10), _y(y), paint);
            canvas.drawText(String.format("%.1f", v), 30, _y(y), paint);
            y += dy;
            v += 0.1f;
        }

        // eixo X
        y1 = 0f;
        y2 = 0f;
        x1 = 0f;
        x2 = width - xMargin * 2;
        canvas.drawLine(_x(x1), _y(y1), _x(x2), _y(y2), paint);

        Problema problema = ProblemasFragment.getProblema();
        double resultado = problema.getResultado();

        Variavel variavel = null;
        for (Variavel var : problema.getVariaveis()) {
            if (var.getTipo() == TipoVariavel.CONSEQUENTE) {
                variavel = var;
                break;
            }
        }

        // marcadores do eixo X
        float dx = ( x2 - x1 ) / 10f;
        y = 0f;
        x = 0f;
        v = variavel.getInicio();
        float dv = (variavel.getFim() - variavel.getInicio()) / 10f;
        for (int i=0; i<=10; i++) {
            canvas.drawLine(_x(x), _y(y-10), _x(x), _y(y+10), paint);
            canvas.drawText(String.format("%02.0f", v), _x(x-10), _y(y-40), paint);
            x += dx;
            v += dv;
        }

        // pinta a area do resultado
        paint.setColor(Color.rgb(210, 230, 230));
        paint.setStyle(Paint.Style.FILL);
        for (Termo termo : variavel.getTermos()) {
            float xA = termo.getA() / dv * dx;
            float yA = 0;

            float xB = (float) Triangulo.getX1(termo, termo.getPertinencia()) / dv * dx;
            float yB = (float) termo.getPertinencia() * (height - yMargin * 2);

            float xC = (float) Triangulo.getX2(termo, termo.getPertinencia()) / dv * dx;
            float yC = (float) termo.getPertinencia() * (height - yMargin * 2);

            float xD = termo.getC() / dv * dx;
            float yD = 0;

            Path path = new Path();
            path.moveTo(_x(xA), _y(yA));
            path.lineTo(_x(xB), _y(yB));
            path.lineTo(_x(xC), _y(yC));
            path.lineTo(_x(xD), _y(yD));
            path.moveTo(_x(xA), _y(yA));
            canvas.drawPath(path, paint);
        }

        // delimita as areas dos termos
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);
        int[] colors = { Color.RED, Color.rgb(0, 128, 0), Color.BLUE, Color.MAGENTA, Color.GRAY } ;

        int c = 0;
        for (Termo termo : variavel.getTermos()) {

            paint.setColor(colors[c++]);

            if (termo.getD() == null) {
                // triangular

                y1 = 0f;
                y2 = height - yMargin * 2;
                x1 = termo.getA() / dv * dx;
                x2 = termo.getB() / dv * dx;
                canvas.drawLine(_x(x1), _y(y1), _x(x2), _y(y2), paint);

                y1 = height - yMargin * 2;
                y2 = 0f;
                x1 = termo.getB() / dv * dx;
                x2 = termo.getC() / dv * dx;
                canvas.drawLine(_x(x1), _y(y1), _x(x2), _y(y2), paint);
            }

            canvas.drawLine(_x(0), height + 50 * (c + 1), _x(100), height + 50 * (c + 1), paint);

            paint.setColor(Color.BLACK);
            paint.setTextSize(30);
            canvas.drawText(termo.getNome(), _x(150), height + 50 * (c + 1) + 10, paint);

        }

        // marca o resultado
        for (Termo termo : variavel.getTermos()) {
            mostraResultado(canvas, termo, resultado, dv, dx);
        }

    }

    private void mostraResultado(Canvas canvas, Termo termo, double resultado, float dv, float dx) {

        if (resultado < termo.getA() || resultado > termo.getC()) {
            return;
        }

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(2);

        float xR = (float) resultado / dv * dx;
        double aux = Triangulo.getY(termo, resultado);
        if (aux > termo.getPertinencia()) {
            aux = termo.getPertinencia();
        }
        float yR = (float) aux * (height - yMargin * 2);

        canvas.drawLine(_x(xR), _y(0), _x(xR), _y(yR), paint);
    }

}
