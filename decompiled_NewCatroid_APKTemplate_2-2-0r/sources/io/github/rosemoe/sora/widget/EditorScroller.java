package io.github.rosemoe.sora.widget;

import android.widget.OverScroller;

/* JADX INFO: loaded from: classes11.dex */
public class EditorScroller {
    private final CodeEditor editor;
    private final OverScroller scroller;

    public EditorScroller(CodeEditor codeEditor) {
        this.scroller = new OverScroller(codeEditor.getContext());
        this.editor = codeEditor;
    }

    public void setEditorOffsets() {
        this.editor.setScrollX(this.scroller.getCurrX());
        this.editor.setScrollY(this.scroller.getCurrY());
    }

    public void startScroll(int i, int i2, int i3, int i4) {
        this.scroller.startScroll(i, i2, i3, i4);
        setEditorOffsets();
    }

    public void startScroll(int i, int i2, int i3, int i4, int i5) {
        this.scroller.startScroll(i, i2, i3, i4, i5);
        setEditorOffsets();
    }

    public void forceFinished(boolean z) {
        this.scroller.forceFinished(z);
        setEditorOffsets();
    }

    public void abortAnimation() {
        this.scroller.abortAnimation();
        setEditorOffsets();
    }

    public boolean isFinished() {
        return this.scroller.isFinished();
    }

    public int getCurrX() {
        return this.scroller.getCurrX();
    }

    public int getCurrY() {
        return this.scroller.getCurrY();
    }

    public int getFinalX() {
        return this.scroller.getFinalX();
    }

    public int getFinalY() {
        return this.scroller.getFinalY();
    }

    public int getStartX() {
        return this.scroller.getStartX();
    }

    public int getStartY() {
        return this.scroller.getStartY();
    }

    public float getCurrVelocity() {
        return this.scroller.getCurrVelocity();
    }

    public boolean computeScrollOffset() {
        boolean zComputeScrollOffset = this.scroller.computeScrollOffset();
        if (zComputeScrollOffset) {
            setEditorOffsets();
        }
        return zComputeScrollOffset;
    }

    public void fling(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
        this.scroller.fling(i, i2, i3, i4, i5, i6, i7, i8, i9, i10);
        setEditorOffsets();
    }

    public boolean isOverScrolled() {
        return this.scroller.isOverScrolled();
    }

    public OverScroller getImplScroller() {
        return this.scroller;
    }
}
