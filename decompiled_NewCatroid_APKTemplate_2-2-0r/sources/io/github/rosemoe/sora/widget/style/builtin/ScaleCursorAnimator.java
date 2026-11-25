package io.github.rosemoe.sora.widget.style.builtin;

import android.animation.ValueAnimator;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.style.CursorAnimator;

/* JADX INFO: loaded from: classes11.dex */
public class ScaleCursorAnimator implements CursorAnimator, ValueAnimator.AnimatorUpdateListener {
    private final CodeEditor editor;
    private float endX;
    private float endY;
    private long lastAnimateTime;
    private float lineBottom;
    private float lineHeight;
    private float startX;
    private float startY;
    private ValueAnimator scaleAnimator = new ValueAnimator();
    private final long duration = 180;

    public ScaleCursorAnimator(CodeEditor codeEditor) {
        this.editor = codeEditor;
    }

    @Override // io.github.rosemoe.sora.widget.style.CursorAnimator
    public void markStartPos() {
        int leftLine = this.editor.getCursor().getLeftLine();
        this.lineHeight = this.editor.getLayout().getRowCountForLine(leftLine) * this.editor.getRowHeight();
        this.lineBottom = this.editor.getLayout().getCharLayoutOffset(leftLine, this.editor.getText().getColumnCount(leftLine))[0];
        float[] charLayoutOffset = this.editor.getLayout().getCharLayoutOffset(this.editor.getCursor().getLeftLine(), this.editor.getCursor().getLeftColumn());
        this.startX = charLayoutOffset[1] + this.editor.measureTextRegionOffset();
        this.startY = charLayoutOffset[0];
    }

    @Override // io.github.rosemoe.sora.widget.style.CursorAnimator
    public boolean isRunning() {
        return this.scaleAnimator.isRunning();
    }

    @Override // io.github.rosemoe.sora.widget.style.CursorAnimator
    public void cancel() {
        this.scaleAnimator.cancel();
    }

    @Override // io.github.rosemoe.sora.widget.style.CursorAnimator
    public void markEndPos() {
        if (this.editor.isCursorAnimationEnabled()) {
            if (isRunning()) {
                cancel();
            }
            if (System.currentTimeMillis() - this.lastAnimateTime < 100) {
                return;
            }
            this.scaleAnimator.removeAllUpdateListeners();
            int leftLine = this.editor.getCursor().getLeftLine();
            this.lineHeight = this.editor.getLayout().getRowCountForLine(leftLine) * this.editor.getRowHeight();
            this.lineBottom = this.editor.getLayout().getCharLayoutOffset(leftLine, this.editor.getText().getColumnCount(leftLine))[0];
            float[] charLayoutOffset = this.editor.getLayout().getCharLayoutOffset(this.editor.getCursor().getLeftLine(), this.editor.getCursor().getLeftColumn());
            this.endX = charLayoutOffset[1] + this.editor.measureTextRegionOffset();
            this.endY = charLayoutOffset[0];
            if (this.editor.getInsertHandleDescriptor().position.isEmpty()) {
                ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                this.scaleAnimator = valueAnimatorOfFloat;
                valueAnimatorOfFloat.setDuration(this.duration);
            } else {
                ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(1.0f, 0.0f, 1.0f);
                this.scaleAnimator = valueAnimatorOfFloat2;
                valueAnimatorOfFloat2.setDuration(this.duration * 2);
            }
            this.scaleAnimator.addUpdateListener(this);
        }
    }

    @Override // io.github.rosemoe.sora.widget.style.CursorAnimator
    public void start() {
        if (!this.editor.isCursorAnimationEnabled() || System.currentTimeMillis() - this.lastAnimateTime < 100 || this.editor.getInsertHandleDescriptor().position.isEmpty()) {
            this.lastAnimateTime = System.currentTimeMillis();
        } else {
            if (this.startX == this.endX && this.startY == this.endY && !this.editor.getInsertHandleDescriptor().position.isEmpty()) {
                return;
            }
            this.scaleAnimator.start();
            this.lastAnimateTime = System.currentTimeMillis();
        }
    }

    private boolean shouldReturnEndValue() {
        return !this.scaleAnimator.isRunning() || this.editor.getInsertHandleDescriptor().position.isEmpty() || this.scaleAnimator.getDuration() == this.duration || this.scaleAnimator.getCurrentPlayTime() > this.duration;
    }

    @Override // io.github.rosemoe.sora.widget.style.CursorAnimator
    public float animatedX() {
        if (shouldReturnEndValue()) {
            return this.endX;
        }
        return this.startX;
    }

    @Override // io.github.rosemoe.sora.widget.style.CursorAnimator
    public float animatedY() {
        if (shouldReturnEndValue()) {
            return this.endY;
        }
        return this.startY;
    }

    @Override // io.github.rosemoe.sora.widget.style.CursorAnimator
    public float animatedLineHeight() {
        return this.lineHeight;
    }

    @Override // io.github.rosemoe.sora.widget.style.CursorAnimator
    public float animatedLineBottom() {
        return this.lineBottom;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.editor.getHandleStyle().setScale(((Float) valueAnimator.getAnimatedValue()).floatValue());
        this.editor.postInvalidateOnAnimation();
    }
}
