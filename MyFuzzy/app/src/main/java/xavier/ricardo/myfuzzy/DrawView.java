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
        setMeasuredDimension(width, height);

    }

    private int _x(int x) {
        return x + xMargin;
    }
    private int _y(int y) {
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
        int y1 = 0;
        int y2 = height - yMargin * 2;
        int x1 = 0;
        int x2 = 0;
        canvas.drawLine(_x(x1), _y(y1), _x(x2), _y(y2), paint);

        // marcadores do eixo Y
        int dy = ( y2 - y1 ) / 10;
        int y = dy;
        int x = 0;
        for (int i=0; i<10; i++) {
            canvas.drawLine(_x(x-10), _y(y), _x(x+10), _y(y), paint);
            y += dy;
        }

        // eixo X
        y1 = 0;
        y2 = 0;
        x1 = 0;
        x2 = width - xMargin * 2;
        canvas.drawLine(_x(x1), _y(y1), _x(x2), _y(y2), paint);

        // marcadores do eixo X
        int dx = ( x2 - x1 ) / 10;
        y = 0;
        x = dx;
        for (int i=0; i<10; i++) {
            canvas.drawLine(_x(x), _y(y-10), _x(x), _y(y+10), paint);
            x += dx;
        }

        int[] colors = { Color.RED, Color.GREEN, Color.BLUE } ;

        int c = 0;
        for (Valor valor : variavel.getValores()) {

            paint.setColor(colors[c++]);

            if (valor.getInicio() != valor.getMaximo()) {
                y1 = 0;
                y2 = height - yMargin * 2;
                x1 = valor.getInicio() * dx;
                x2 = valor.getMaximo() * dx;
                canvas.drawLine(_x(x1), _y(y1), _x(x2), _y(y2), paint);
            }

            if (valor.getFim() != valor.getMaximo()) {
                y1 = height - yMargin * 2;
                y2 = 0;
                x1 = valor.getMaximo() * dx;
                x2 = valor.getFim() * dx;
                canvas.drawLine(_x(x1), _y(y1), _x(x2), _y(y2), paint);
            }

        }
    }

    public Variavel getVariavel() {
        return variavel;
    }

    public void setVariavel(Variavel variavel) {
        this.variavel = variavel;
    }

}
