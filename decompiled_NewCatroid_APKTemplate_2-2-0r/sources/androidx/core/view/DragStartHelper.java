package androidx.core.view;

import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/view/DragStartHelper.class
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/view/DragStartHelper.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
public class DragStartHelper {
    private boolean mDragging;
    private int mLastTouchX;
    private int mLastTouchY;
    private final OnDragStartListener mListener;
    private final View.OnLongClickListener mLongClickListener = new View.OnLongClickListener() { // from class: androidx.core.view.DragStartHelper$$ExternalSyntheticLambda0
        @Override // android.view.View.OnLongClickListener
        public final boolean onLongClick(View view) {
            return this.f$0.onLongClick(view);
        }
    };
    private final View.OnTouchListener mTouchListener = new View.OnTouchListener() { // from class: androidx.core.view.DragStartHelper$$ExternalSyntheticLambda1
        @Override // android.view.View.OnTouchListener
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            return this.f$0.onTouch(view, motionEvent);
        }
    };
    private final View mView;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/view/DragStartHelper$OnDragStartListener.class
      NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/view/DragStartHelper$OnDragStartListener.class
      classes.dex
     */
    public interface OnDragStartListener {
        boolean onDragStart(View view, DragStartHelper dragStartHelper);
    }

    public DragStartHelper(View view, OnDragStartListener onDragStartListener) {
        this.mView = view;
        this.mListener = onDragStartListener;
    }

    public void attach() {
        this.mView.setOnLongClickListener(this.mLongClickListener);
        this.mView.setOnTouchListener(this.mTouchListener);
    }

    public void detach() {
        this.mView.setOnLongClickListener(null);
        this.mView.setOnTouchListener(null);
    }

    /* JADX WARN: Code duplicated, block: B:26:0x0046  */
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        int action = motionEvent.getAction();
        if (action == 0) {
            this.mLastTouchX = x;
            this.mLastTouchY = y;
        } else if (action == 1) {
            this.mDragging = false;
        } else if (action != 2) {
            if (action == 3) {
                this.mDragging = false;
            }
        } else if (MotionEventCompat.isFromSource(motionEvent, 8194) && (motionEvent.getButtonState() & 1) != 0 && !this.mDragging && (this.mLastTouchX != x || this.mLastTouchY != y)) {
            this.mLastTouchX = x;
            this.mLastTouchY = y;
            boolean zOnDragStart = this.mListener.onDragStart(view, this);
            this.mDragging = zOnDragStart;
            return zOnDragStart;
        }
        return false;
    }

    public boolean onLongClick(View view) {
        if (this.mDragging) {
            return true;
        }
        boolean zOnDragStart = this.mListener.onDragStart(view, this);
        this.mDragging = zOnDragStart;
        return zOnDragStart;
    }

    /* JADX INFO: renamed from: androidx.core.view.DragStartHelper$1, reason: invalid class name */
    /* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/view/DragStartHelper$1.class */
    class AnonymousClass1 implements View.OnLongClickListener {
        AnonymousClass1() {
        }

        @Override // android.view.View.OnLongClickListener
        public boolean onLongClick(View v) {
            return DragStartHelper.this.onLongClick(v);
        }
    }

    public void getTouchPosition(Point point) {
        point.set(this.mLastTouchX, this.mLastTouchY);
    }

    /* JADX INFO: renamed from: androidx.core.view.DragStartHelper$2, reason: invalid class name */
    /* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/view/DragStartHelper$2.class */
    class AnonymousClass2 implements View.OnTouchListener {
        AnonymousClass2() {
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View v, MotionEvent event) {
            return DragStartHelper.this.onTouch(v, event);
        }
    }
}
