
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 创建日期:2019/8/5
 * 创建者: mao
 * 功能描述:RecyclerView item装饰器 时间轴效果
 */

public class TimelineDecoration extends RecyclerView.ItemDecoration {

    private int mLeftOffset;
    private Paint mCirclePaint;
    private int mCircleRadius;
    private Paint mlinePaint;
    //是否允许绘制最后一个item的线条
    private boolean mLastLineVisibility=false;

    public TimelineDecoration(){
        this(50,10);
    }
    public TimelineDecoration(int leftOffset,int circleRadius){
        mLeftOffset=leftOffset;
        mCircleRadius=circleRadius;

        initCirclePaint();
        initLinePaint();
    }

    //初始化绘制线条的画笔
    private void initLinePaint() {
        mlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mlinePaint.setStyle(Paint.Style.STROKE);
        mlinePaint.setColor(Color.BLACK);
        mlinePaint.setStrokeWidth(1);
        PathEffect path = new DashPathEffect(new float[] { 16, 2}, 1);
        mlinePaint.setPathEffect(path);
    }

    //初始化绘制圆的画笔
    private void initCirclePaint() {
        mCirclePaint=new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(Color.BLUE);
    }

    //设置绘制线条的画笔的颜色
    public TimelineDecoration setLinePaintColor(int color){
        mlinePaint.setColor(color);
        return this;
    }

    //设置绘制线条的宽度
    public TimelineDecoration setLinePaintStrokeWidth(float width){
        mlinePaint.setStrokeWidth(width);
        return this;
    }

    //设置绘制线条的画笔的虚线效果
    public TimelineDecoration setLinePaintPathEffect(PathEffect path){
        mlinePaint.setPathEffect(path);
        return this;
    }

    public TimelineDecoration setLastLineVisibility(boolean visible){
        mLastLineVisibility=visible;
        return this;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.top=0;
        outRect.left=mLeftOffset;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int count=parent.getChildCount();
        for (int i=0;i<count;i++){
            View child=parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int left=parent.getPaddingLeft();
            int top=child.getTop()+child.getPaddingTop();
            int right=child.getLeft();
            int bottom=child.getBottom()+params.bottomMargin+child.getPaddingTop();
            int rectWidth=right-left;

            int timeLineLeft=left+rectWidth/2;

            //绘制圆
            int circleX=timeLineLeft;
            int circleY=top+mCircleRadius;
            mCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
            c.drawCircle(circleX,circleY,mCircleRadius,mCirclePaint);

            //绘制下半的线
            if (!isLastItem(parent,child)){
                int downTimeLineTop=circleY+mCircleRadius;
                int downTimeLineBottom=bottom+params.topMargin;
                c.drawLine(timeLineLeft, downTimeLineTop, timeLineLeft, downTimeLineBottom, mlinePaint);
            }

            if (isLastItem(parent,child)&&mLastLineVisibility){
                int downTimeLineTop=circleY+mCircleRadius;
                int downTimeLineBottom=bottom;
                c.drawLine(timeLineLeft, downTimeLineTop, timeLineLeft, downTimeLineBottom, mlinePaint);
            }
        }
    }

    //是否是最后一个item
    private boolean isLastItem(RecyclerView parent, View child) {
        return parent.getAdapter().getItemCount()==parent.getChildAdapterPosition(child)+1;
    }
}
