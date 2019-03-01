package hfuu.mao.widghtstudy.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

//时光轴的ItemDecoration
public class TimelineItemDecoration extends RecyclerView.ItemDecoration {

    private int mTopOffset=10;
    private int mLeftOffset=70;
    private Paint mPaint;
    private int mCircleRadius;
    private int mSplitLineCorlor;
    private Paint mSplitLinePaint;

    public TimelineItemDecoration(){
        this(10,90,20,Color.RED);
    }
    public TimelineItemDecoration(int topOffset,int leftOffset,int circleRadius,int corlor){
        mTopOffset=topOffset;
        mLeftOffset=leftOffset;
        mCircleRadius=circleRadius;
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(corlor);
    }
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildAdapterPosition(view)==0){
            outRect.top=0;
        }
        else {
            outRect.top=mTopOffset;
        }
        outRect.left=mLeftOffset;
    }

    public void setSplitLineCorlor(int corlor){
        mSplitLineCorlor=corlor;
        mSplitLinePaint=new Paint();
        mSplitLinePaint.setColor(corlor);
        mSplitLinePaint.setAntiAlias(true);
    }



    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int count=parent.getChildCount();
        for (int i=0;i<count;i++){
            View child=parent.getChildAt(i);
            int position=parent.getChildAdapterPosition(child);
            int top=child.getTop()-mTopOffset;
            if (position==0){
                top=child.getTop();
            }
            int left=parent.getPaddingLeft();
            int bottom=child.getBottom();
            int dividerHeight=bottom-top;

            int timeLineLeft=left+mLeftOffset/2;
            int upTimeLineTop=top;
            int upTimeLineBottom=top+dividerHeight/2-mCircleRadius;
            c.drawLine(timeLineLeft,upTimeLineTop,timeLineLeft,upTimeLineBottom,mPaint);

            int circleX=timeLineLeft;
            int circleY=upTimeLineBottom+mCircleRadius;
            mPaint.setStyle(Paint.Style.STROKE);
            c.drawCircle(circleX,circleY,mCircleRadius,mPaint);

            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            int downTimeLineTop=circleY+mCircleRadius;
            int downTiileLineBottom=child.getBottom();
            c.drawLine(timeLineLeft,downTimeLineTop,timeLineLeft,downTiileLineBottom,mPaint);
            if (mSplitLinePaint!=null){
                c.drawRect(child.getLeft(),top,parent.getRight()-parent.getPaddingRight(),child.getTop(),mSplitLinePaint);
            }

        }
    }
}
