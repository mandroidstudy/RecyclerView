package hfuu.mao.widghtstudy.decoration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

public class HorizontalItemDecoration extends RecyclerView.ItemDecoration {
    private int mSplitLineHeight;
    private Paint mPaint;
    private static final int[] ATTRS = new int[]{16843284};
    private Drawable mDivider;
    public HorizontalItemDecoration(){
        this(Color.GRAY,7);
    }
    public HorizontalItemDecoration(int corlor,int height){
        mSplitLineHeight=height;
        mPaint=new Paint();
        mPaint.setColor(corlor);
        mPaint.setAntiAlias(true);
    }
    public HorizontalItemDecoration(Context context,int height){
        TypedArray a = context.obtainStyledAttributes(ATTRS);
        this.mDivider = a.getDrawable(0);
        if (this.mDivider == null) {
            Log.w("DividerItem", "@android:attr/listDivider was not set in the theme used for this DividerItemDecoration. Please set that attribute all call setDrawable()");
        }
        mSplitLineHeight=height;
        a.recycle();
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildAdapterPosition(view)!=0){
            outRect.top=mSplitLineHeight;
        }
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int count=parent.getChildCount();
        for (int i=0;i<count;i++){
            View item=parent.getChildAt(i);
            int index=parent.getChildAdapterPosition(item);
            if (index==0){
                continue;
            }
            int itemTop=item.getTop();
            int splitLineTop=itemTop-mSplitLineHeight;
            int splitLineLeft=parent.getLeft()-parent.getPaddingLeft();
            int splitLineRight=parent.getRight()-parent.getPaddingRight();
            int splitLineButtom=itemTop;
            if (mDivider!=null){
                this.mDivider.setBounds(splitLineLeft,splitLineTop,splitLineRight,splitLineButtom);
                this.mDivider.draw(c);
            }else {
                c.drawRect(splitLineLeft,splitLineTop,splitLineRight,splitLineButtom,mPaint);
            }
        }

    }

    public void setDrawable(@NonNull Drawable drawable) {
        if (drawable == null) {
            throw new IllegalArgumentException("Drawable cannot be null.");
        } else {
            this.mDivider = drawable;
        }
    }
}
