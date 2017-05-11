package com.dai;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = new CustomView1(this);
        view.setBackgroundColor(Color.GRAY);
        TextView textView =  new TextView(getApplicationContext());
        textView.setText("ceshi");
        ObjectAnimator animator = ObjectAnimator.ofFloat(textView, "rotation", 0f, 360f);
        animator.setDuration(5000);
        animator.start();
        setContentView(view);
    }

    /**
     * 使用内部类 自定义一个简单的View
     *
     * @author Administrator
     */
    class CustomView1 extends View implements View.OnTouchListener {

        Paint paint;
        private ArrayList<PointF> graphics = new ArrayList<PointF>();
        private ArrayList<Float> pts = new ArrayList<>();
        PointF point;

        public CustomView1(Context context) {
            super(context);
            paint = new Paint(); //设置一个笔刷大小是3的黄色的画笔
            paint.setColor(Color.YELLOW);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeWidth(1);


        }

        //在这里我们将测试canvas提供的绘制图形方法
        @Override
        protected void onDraw(Canvas canvas) {
            drawTimer(canvas,paint);
//            handlePoint(canvas);

        }

        private void handlePoint(Canvas canvas) {
//            for (PointF point : graphics) {
//                canvas.drawPoint(point.x, point.y, paint);
            System.out.println("CustomView1.handlePoint");

            if (pts.size() == 0) {
                return;
            }
            System.out.println("pts.size() = " + pts.size());
            if (pts.size() == 2) {
                canvas.drawLine(pts.get(0), pts.get(1), pts.get(0), pts.get(1), paint);
            } else {
                int length = pts.size();
                canvas.drawLine(pts.get(length - 4), pts.get(length - 3), pts.get(length - 2), pts.get(length - 1), paint);
                canvas.save();
            }
//            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
//            graphics.add(new PointF(event.getX(), event.getY()));
            System.out.println("pts = " + event.getX() + event.getY());

            pts.add(event.getX());
            pts.add(event.getX());

            invalidate(); //重新绘制区域
            System.out.println("CustomView1.onTouchEvent");
            return true;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            System.out.println("CustomView1.onTouch");
            return false;
        }
    }

    /**
     * 画时钟
     *
     * @param canvas 画布
     * @param paint  画笔
     */
    private void drawTimer(Canvas canvas, Paint paint) {
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        canvas.translate(canvas.getWidth() / 2, 400); //将位置移动画纸的坐标点:150,150
        canvas.drawCircle(0, 0, 200, paint); //画圆圈

        //使用path绘制路径文字
        canvas.save();
//            canvas.translate(-150, -150);
        RectF rectF = new RectF(-150, -150, 150, 150);
        Path path = new Path();
        path.addArc(rectF, 0, 359);
//            canvas.drawRect(rectF,paint);
        Paint citePaint = new Paint(paint);
        citePaint.setTextSize(16);
        citePaint.setStrokeWidth(1);
        canvas.drawTextOnPath("http://www.android777.com,www.android777.comwww.android777.comwww.android777.com,comwww.android777.comcomwww.android777.com", path, 0, 0, citePaint);
//            canvas.restore();

        Paint tmpPaint = new Paint(paint); //小刻度画笔对象
        tmpPaint.setStrokeWidth(1);
        tmpPaint.setTextSize(16);

        float y = 200;
        int count = 60; //总刻度数

        for (int i = 0; i < count; i++) {
            if (i % 5 == 0) {
                canvas.drawLine(0f, y, 0, y + 12f, paint);
                canvas.drawText(String.valueOf(i / 5 + 1), -4f, y + 28f, tmpPaint);

            } else {
                canvas.drawLine(0f, y, 0f, y + 5f, tmpPaint);
            }
            canvas.rotate(360 / count, 0f, 0f); //旋转画纸
        }

        //绘制指针
        tmpPaint.setColor(Color.GRAY);
        tmpPaint.setStrokeWidth(4);
        canvas.drawCircle(0, 0, 7, tmpPaint);
        tmpPaint.setStyle(Paint.Style.FILL);
        tmpPaint.setColor(Color.YELLOW);
        canvas.drawCircle(0, 0, 5, tmpPaint);
        canvas.drawLine(0, 10, 0, -65, paint);
    }


}
