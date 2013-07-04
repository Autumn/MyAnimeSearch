package uguu.gao.wafu.myanimesearch;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by aki on 4/07/13.
 */
public class ResultCardView extends View {

    String title;
    int results = 0;

    private float paddingTop = 0;
    private float paddingLeft = 0;
    private float paddingBottom = 5;
    private float paddingRight = 0;

    private Paint titleTextPaint;
    private float titleTextX = 0.0f;
    private float titleTextY = 0.0f;
    private float titleTextWidth = 0.0f;
    private float titleTextHeight = 30.0f;

    private Paint resultTextPaint;
    private float resultTextX = 0.0f;
    private float resultTextY = 0.0f;
    private float resultTextWidth = 0.0f;
    private float resultTextHeight = 30.0f;
    private String resultString;

    private Paint linePaint;
    private float lineStartX;
    private float lineStartY;
    private float lineStopX;
    private float lineStopY;

    private Paint cardPaint;
    private float mCardWidth = 0.0f;
    private float mCardHeight = 0.0f;

    private Paint shadowPaint;
    private float mShadowTop;
    private float mShadowBottom;
    private float mShadowLeft;
    private float mShadowRight;

    public ResultCardView(Context context) {
        super(context);
        init();
    }


    public ResultCardView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CardView, 0, 0);

        try {
            title = a.getString(R.styleable.CardView_title);
        } finally {
            a.recycle();
        }
        init();
    }

    private void init() {
        titleTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        titleTextPaint.setTextAlign(Paint.Align.LEFT);
        titleTextPaint.setARGB(255, 0, 0, 0);
        titleTextPaint.setTypeface(Typeface.DEFAULT);

        if (titleTextHeight == 0) {
            titleTextHeight = titleTextPaint.getTextSize();
        } else {
            titleTextPaint.setTextSize(titleTextHeight);
        }

        resultTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        resultTextPaint.setTextAlign(Paint.Align.LEFT);
        resultTextPaint.setARGB(255, 160, 160, 160);
        resultTextPaint.setTypeface(Typeface.DEFAULT);

        linePaint = new Paint();
        linePaint.setARGB(255, 205, 193, 197);


        if (resultTextHeight == 0) {
            resultTextHeight = resultTextPaint.getTextSize();
        } else {
            resultTextPaint.setTextSize(resultTextHeight);
        }


        cardPaint = new Paint();
        cardPaint.setARGB(255, 255, 255, 255);
        cardPaint.setStyle(Paint.Style.FILL);


        shadowPaint = new Paint();
        shadowPaint.setARGB(255, 205, 193, 197);
        cardPaint.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /*int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        int w = resolveSizeAndState(minw, widthMeasureSpec, 1);

        int minh = MeasureSpec.getSize(w) - getPaddingBottom() + getPaddingTop();
        int h = resolveSizeAndState(minh, heightMeasureSpec, 0);
        setMeasuredDimension(w, h);*/
        setMeasuredDimension(widthMeasureSpec, 70);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        float xpad = (float) (getPaddingLeft() + getPaddingRight());
        float ypad = (float) (getPaddingTop() + getPaddingBottom());

        titleTextX = 10;
        titleTextY = 10;

        resultTextX = 10;
        resultTextY = 10;

        lineStartX = 10;
        lineStartY = titleTextHeight + 30;
        lineStopX = w - 10;
        lineStopY = lineStartY;

        mCardHeight = h - paddingTop - paddingBottom;
        mCardWidth = w - paddingLeft - paddingRight;

        mShadowBottom = h - paddingBottom - paddingTop;
        mShadowTop = h - paddingBottom - paddingTop - 6;

        mShadowLeft = paddingLeft;
        mShadowRight = w - paddingRight - paddingLeft;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        titleTextWidth = titleTextPaint.measureText(title);
        resultTextWidth = resultTextPaint.measureText(resultString);

        resultTextX = mCardWidth - resultTextWidth - 10;

        LinearGradient shadowGradient = new LinearGradient(mShadowLeft, mShadowTop, mShadowLeft, mShadowBottom, Color.rgb(205, 193, 197), Color.rgb(243, 243, 243), Shader.TileMode.CLAMP);
        shadowPaint.setShader(shadowGradient);

        canvas.drawRect(paddingLeft, paddingTop, mCardWidth, mCardHeight, cardPaint);
        canvas.drawRect(mShadowLeft, mShadowTop, mShadowRight, mShadowBottom, shadowPaint);
        canvas.drawText(title, titleTextX, titleTextY + titleTextHeight, titleTextPaint);
        canvas.drawText(resultString, resultTextX, resultTextY + resultTextHeight, resultTextPaint);
        //canvas.drawLine(lineStartX, lineStartY, lineStopX, lineStopY, linePaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println(title + " touched ");
        return super.onTouchEvent(event);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        invalidate();
    }

    public void setResults(int resultCount) {
        this.results = resultCount;
        resultString = Integer.toString(this.results) + " results";
        invalidate();
    }
}
