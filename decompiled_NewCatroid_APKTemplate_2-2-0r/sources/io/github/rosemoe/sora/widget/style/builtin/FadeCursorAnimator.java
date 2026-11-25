package io.github.rosemoe.sora.widget.style.builtin;

import android.animation.Animator;
import android.animation.ValueAnimator;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.style.CursorAnimator;

/* JADX INFO: loaded from: classes11.dex */
public class FadeCursorAnimator implements CursorAnimator, ValueAnimator.AnimatorUpdateListener {
    private final CodeEditor editor;
    private float endX;
    private float endY;
    private long lastAnimateTime;
    private float lineBottom;
    private float lineHeight;
    private boolean phaseEnded;
    private float startX;
    private float startY;
    private ValueAnimator fadeInAnimator = new ValueAnimator();
    private ValueAnimator fadeOutAnimator = new ValueAnimator();
    private final long duration = 200;

    public FadeCursorAnimator(CodeEditor codeEditor) {
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
        return this.fadeInAnimator.isRunning() || this.fadeOutAnimator.isRunning();
    }

    @Override // io.github.rosemoe.sora.widget.style.CursorAnimator
    public void cancel() {
        this.fadeOutAnimator.cancel();
        this.fadeInAnimator.cancel();
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
            this.fadeOutAnimator.removeAllUpdateListeners();
            this.fadeInAnimator.removeAllUpdateListeners();
            int leftLine = this.editor.getCursor().getLeftLine();
            this.lineHeight = this.editor.getLayout().getRowCountForLine(leftLine) * this.editor.getRowHeight();
            this.lineBottom = this.editor.getLayout().getCharLayoutOffset(leftLine, this.editor.getText().getColumnCount(leftLine))[0];
            float[] charLayoutOffset = this.editor.getLayout().getCharLayoutOffset(this.editor.getCursor().getLeftLine(), this.editor.getCursor().getLeftColumn());
            this.endX = charLayoutOffset[1] + this.editor.measureTextRegionOffset();
            this.endY = charLayoutOffset[0];
            ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(255, 0);
            this.fadeOutAnimator = valueAnimatorOfInt;
            valueAnimatorOfInt.addListener(new Animator.AnimatorListener() { // from class: io.github.rosemoe.sora.widget.style.builtin.FadeCursorAnimator.1
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationRepeat(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    FadeCursorAnimator.this.phaseEnded = false;
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    FadeCursorAnimator.this.phaseEnded = true;
                }
            });
            this.fadeOutAnimator.addUpdateListener(this);
            this.fadeOutAnimator.setDuration(this.duration);
            ValueAnimator valueAnimatorOfInt2 = ValueAnimator.ofInt(0, 255);
            this.fadeInAnimator = valueAnimatorOfInt2;
            valueAnimatorOfInt2.addUpdateListener(this);
            this.fadeInAnimator.setStartDelay(this.duration);
            this.fadeInAnimator.setDuration(this.duration);
        }
    }

    @Override // io.github.rosemoe.sora.widget.style.CursorAnimator
    public void start() {
        if (!this.editor.isCursorAnimationEnabled() || System.currentTimeMillis() - this.lastAnimateTime < 100) {
            this.lastAnimateTime = System.currentTimeMillis();
            return;
        }
        this.fadeOutAnimator.start();
        this.fadeInAnimator.start();
        this.lastAnimateTime = System.currentTimeMillis();
    }

    @Override // io.github.rosemoe.sora.widget.style.CursorAnimator
    public float animatedX() {
        if (this.phaseEnded || this.editor.getInsertHandleDescriptor().position.isEmpty()) {
            return this.endX;
        }
        return this.startX;
    }

    @Override // io.github.rosemoe.sora.widget.style.CursorAnimator
    public float animatedY() {
        if (this.phaseEnded || this.editor.getInsertHandleDescriptor().position.isEmpty()) {
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
        this.editor.getHandleStyle().setAlpha(((Integer) valueAnimator.getAnimatedValue()).intValue());
        this.editor.postInvalidateOnAnimation();
    }
}
