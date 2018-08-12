package wn.streamer_lineargradient;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class LinearGradientView extends View {

    private Path shaderPath ;
    private Paint shaderPaint = new Paint();
    private int mSuccessAnimOffset ;
    private ValueAnimator valueAnimator;
    private int mHeight ,mWidth ;
    private int width ;
    public LinearGradientView(Context context) {
        super(context);
    }

    public LinearGradientView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LinearGradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(150*2,48*2);
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(this.shaderPath != null){
            canvas.translate((float)this.mSuccessAnimOffset,0.0f);
            //矩形旋转角度135[-45]度,与平行四边形效果一致
            //canvas.rotate(135,width/2,mHeight/2);
            canvas.drawPath(this.shaderPath,this.shaderPaint);

        }
    }



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mHeight = h;
        this.mWidth = w;
        this.setBackgroundResource(R.drawable.streamer_night);
        init();
        startAnimation();
    }


    private void init() {
        width = (int) TypedValue.applyDimension(1, 40.0F, this.getResources().getDisplayMetrics());
        //白色主题   透明白色---->白色---->透明白色
        //黑色主题   透明黑色---->黑色---->透明黑色
        this.shaderPaint.setShader(new LinearGradient(0, 0, width, 0,
                //new int[] { getResources().getColor(android.R.color.transparent), getResources().getColor(R.color.colorAccent)},
                //null, Shader.TileMode.CLAMP));
                new int[]{getContext().getResources().getColor(R.color.touming_black), Color.WHITE,getContext().getResources().getColor(R.color.touming_black)},
                new float[]{0,.5f,1f},
                Shader.TileMode.CLAMP));
        //透明度  0：全透明，什么都看不到      1：不透明，看得到
        // 黑色背景 20
        // 白色背景 80
        shaderPaint.setAlpha(20);
        this.shaderPath = new Path();
        this.shaderPath.moveTo(0.0f,0.0f);
        this.shaderPath.lineTo(width,0.0f);
        this.shaderPath.lineTo(width,this.mHeight);
        this.shaderPath.lineTo(0,this.mHeight);

        this.shaderPath.close();

        valueAnimator = ValueAnimator.ofInt(-width,this.mWidth).setDuration(1000L);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new FastOutLinearInInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mSuccessAnimOffset = (Integer) animation.getAnimatedValue();
                invalidate();
            }
        });
    }

    public  void startAnimation(){
        valueAnimator.start();
    }
    public void stopAnimation(){
        if (valueAnimator == null)
            return;
        valueAnimator.cancel();
        //valueAnimator.pause();
        valueAnimator.removeAllUpdateListeners();
    }
}
