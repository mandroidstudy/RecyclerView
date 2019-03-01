package hfuu.mao.widghtstudy.decoration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

//带角标的装饰器
public class CornerMarkerItemDecoration extends RecyclerView.ItemDecoration {
    private int mSplitLineHeight;
    private static final int[] ATTRS = new int[]{16843284};
    private Drawable mDivider;
    private int mCornerMarkerNum;
    private int mOffsetLeft;
    private Drawable mCornerMarker;
    public CornerMarkerItemDecoration(Context context,int splitLineHeight,int cornerMarkerNum,int offsetLeft,Drawable cornerMarker){
        TypedArray a = context.obtainStyledAttributes(ATTRS);
        this.mDivider = a.getDrawable(0);
        if (this.mDivider == null) {
            Log.w("DividerItem", "@android:attr/listDivider was not set in the theme used for this DividerItemDecoration. Please set that attribute all call setDrawable()");
        }
        mSplitLineHeight=splitLineHeight;
        mCornerMarkerNum=cornerMarkerNum;
        mOffsetLeft=offsetLeft;
        mCornerMarker=cornerMarker;
        a.recycle();
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildAdapterPosition(view)==0){
            outRect.top=0;
        }else {
            outRect.top=mSplitLineHeight;
        }
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int count=parent.getChildCount();
        for (int i=0;i<count;i++){
            View child=parent.getChildAt(i);
            int position=parent.getChildAdapterPosition(child);
            int left=parent.getLeft()-parent.getPaddingLeft();
            int top=child.getTop()-mSplitLineHeight;
            if (parent.getChildAdapterPosition(child)==0){
                top=child.getTop();
            }
            int right=parent.getRight()-parent.getPaddingRight();
            int bottom=child.getTop();
            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int count=parent.getChildCount();
        for (int i=0;i<count;i++){
            View child=parent.getChildAt(i);
            int position=parent.getChildAdapterPosition(child);
            if (position<mCornerMarkerNum){
                int top=child.getTop();
                mCornerMarker.setBounds(mOffsetLeft,top,200,top+100);
                mCornerMarker.draw(c);
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
