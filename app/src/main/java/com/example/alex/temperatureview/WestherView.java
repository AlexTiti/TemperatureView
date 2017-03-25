package com.example.alex.temperatureview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.alex.customcontrols.utils.L;


/**
 * Created by JRD on 2017/3/23.
 */
public class WestherView extends View {

    private String TAG = "WestherView";
    private Paint mPaint;
    private Paint paint_circle;
    private Rect rect;
    private int temperature = 28;

    public void setTemperature(int temperature) {
        this.temperature = temperature;
        invalidate();
    }

    public WestherView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_circle = new Paint();
        rect = new Rect();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
        paint_circle.setStyle(Paint.Style.STROKE);
        paint_circle.setStrokeWidth(5);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width_set = 500;
        int width = measure_size(width_set, widthMeasureSpec);
        int heigh = measure_size(width_set, heightMeasureSpec);
        int brand = Math.min(width, heigh);
        setMeasuredDimension(brand, brand);
    }

    private int measure_size(int wid, int measureSpec) {
        int size = MeasureSpec.getSize(measureSpec);
        int mode = MeasureSpec.getMode(measureSpec);
        if (mode == MeasureSpec.EXACTLY)
            return size;
        else {
            if (mode == MeasureSpec.AT_MOST)
                return Math.min(wid, size);
        }
        return wid;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint_circle.setColor(Color.WHITE);
        String text = String.valueOf(temperature) + "°";
        int width = getWidth();
        int height = getHeight();
        int radiu = (width - getPaddingLeft() - getPaddingRight()) / 2 - 100;
        L.e(TAG, "不画圆？" + radiu);
        canvas.translate(width / 2, height / 2);
        RectF rectF = new RectF(-radiu, -radiu, radiu, radiu);
        canvas.drawArc(rectF, 120, 300, false, paint_circle);
        mPaint.setTextSize(100);
        mPaint.getTextBounds(text, 0, text.length(), rect);
        canvas.drawText(text, -rect.width() / 2, rect.height() / 2, mPaint);
        drawItem(canvas, radiu);
        drawText(canvas, radiu);

    }

    private void drawItem(Canvas canvas, int radius) {
        mPaint.setTextSize(10);
        mPaint.setStrokeWidth(5);
        int weather = -20;
        canvas.save();
        int number = 300 / 5;
        canvas.rotate(-60);
        for (int i = 0; i <= number; i++) {
            if (weather <= temperature)
                paint_circle.setColor(getPaintColor(weather));
            else
                paint_circle.setColor(Color.WHITE);

            if (i == 0 || i == number) {
                paint_circle.setStrokeWidth(5);
                canvas.drawLine(-radius, 0, -radius + 40, 0, paint_circle);
            } else {
                paint_circle.setStrokeWidth(3);
                canvas.drawLine(-radius, 0, -radius + 30, 0, paint_circle);
            }
            weather++;
            canvas.rotate(5);
        }
        canvas.restore();
    }

    private int getPaintColor(int weath) {
        if (weath >= -20 && weath < 0) {
            return Color.parseColor("#00008B");
        }
        if (weath >= 0 && weath < 15)
            return Color.parseColor("#40E0D0");
        if (weath >= 15 && weath < 30)
            return Color.parseColor("#00FF00");
        else
            return Color.parseColor("#CD5C5C");
    }

    Rect rect1 = new Rect();

    private void drawText(Canvas canvas, int radius) {
        int textDegree = 120;
        int weather = -20;

        for (int i = 0; i <= 12; i++) {
            String string = String.valueOf(weather) + "°";
            mPaint.setTextSize(40);
            mPaint.getTextBounds(string, 0, string.length(), rect1);
            float x = (float) getX(radius + 20, textDegree * Math.PI / 180);
            float y = (float) getY(radius + 20, textDegree * Math.PI / 180);
            canvas.drawText(string, x, y - rect1.height() / 2, mPaint);
            weather += 5;
            textDegree += 25;
        }
    }

    private double getX(int radius, double degree) {
        return getTextX(radius, degree);
    }

    private double getY(int radius, double degree) {
        return getTextY(radius, degree);
    }

    private double getTextX(int radius, double degree) {
        double x = 0;
        double d = Math.PI;
        x = radius * Math.cos(degree);
        if (degree > d / 2 && degree < 3 * d / 2)
            x = x - rect1.width();
        return x;
    }

    private double getTextY(int radius, double degree) {
        double y = radius * Math.sin(degree);
        double d = Math.PI;
        if (degree < d || degree > 2d)
            y += rect1.height();
        return y;
    }


}
