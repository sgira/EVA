package com.wellness.eva.feedback;

/**
 * Created by Emmanuel on 7/22/2016.
 */
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import com.wellness.eva.R;

import java.io.InputStream;

/**
 * Created by Emmanuel on 7/20/2016.
 */
public class GifView extends View {

    private InputStream gifInputStream;
    private Movie gifMovie;
    private int movieWidth, movieHiegth;
    private long movieDuration;
    private long movieStart;

    public GifView(Context context) {
        super(context);

        init(context);
    }

    public GifView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GifView(Context context, AttributeSet attrs, int deStyleAttr) {
        super(context, attrs, deStyleAttr);
    }

    private void init(Context context){
        setFocusable(true);
        gifInputStream = context.getResources().openRawResource(R.drawable.chest_compressions);
        gifMovie = Movie.decodeStream(gifInputStream);

        movieWidth = gifMovie.width();
        movieHiegth = gifMovie.height();
        movieDuration = gifMovie.duration();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(movieWidth, movieHiegth);
    }

    public int getMovieWidth(){
        return movieWidth;
    }

    public int getMovieHiegth(){
        return movieHiegth;
    }

    public long getMovieDuration() {
        return movieDuration;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        long now = SystemClock.uptimeMillis();

        if(movieStart == 0){
            movieStart = now;
        }

        if(gifMovie != null){

            int dur = gifMovie.duration();
            if(dur == 0){
                dur = 1000;
            }

            int relTime = (int)((now - movieStart)%dur);

            gifMovie.setTime(relTime);
            gifMovie.draw(canvas,0,0);
            invalidate();
        }
    }
}