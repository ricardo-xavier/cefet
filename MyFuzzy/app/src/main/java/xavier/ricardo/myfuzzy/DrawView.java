package xavier.ricardo.myfuzzy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import xavier.ricardo.myfuzzy.tipos.Valor;
import xavier.ricardo.myfuzzy.tipos.Variavel;

public class DrawView extends View {

    private int width;
    private int height;
    private int xMargin = 100;
    private int yMargin = 100;
    private Variavel variavel;

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        width = MeasureSpec.getSize(widthMeasureSpec);
        height = width; // MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, MeasureSpec.getSize(heightMeasureSpec));

    }

    private int _x(float x) {
        return (int) x + xMargin;
    }
    private int _y(float y) {
        return height - (int) y - yMargin;
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

        paint.setStrokeWidth(2);
        int[] colors = { Color.RED, Color.rgb(0, 128, 0), Color.BLUE } ;

        int c = 0;
        for (Valor valor : variavel.getValores()) {

            paint.setColor(colors[c++]);

            if (valor.getInicio() != valor.getMaximo()) {
                y1 = 0f;
                y2 = height - yMargin * 2;
                x1 = valor.getInicio() / dv * dx;
                x2 = valor.getMaximo() / dv * dx;
                canvas.drawLine(_x(x1), _y(y1), _x(x2), _y(y2), paint);
            }

            if (valor.getFim() != valor.getMaximo()) {
                y1 = height - yMargin * 2;
                y2 = 0f;
                x1 = valor.getMaximo() / dv * dx;
                x2 = valor.getFim() / dv * dx;
                canvas.drawLine(_x(x1), _y(y1), _x(x2), _y(y2), paint);
            }

            canvas.drawLine(_x(0), height + 50 * (c + 1), _x(100), height + 50 * (c + 1), paint);

            paint.setColor(Color.BLACK);
            paint.setTextSize(30);
            canvas.drawText(valor.getNome(), _x(150), height + 50 * (c + 1) + 10, paint);

        }
    }

    public Variavel getVariavel() {
        return variavel;
    }

    public void setVariavel(Variavel variavel) {
        this.variavel = variavel;
    }

}
