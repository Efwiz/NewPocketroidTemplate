package io.github.rosemoe.sora.widget.style.builtin;

import android.animation.ValueAnimator;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.style.CursorAnimator;

/* JADX INFO: loaded from: classes11.dex */
public class MoveCursorAnimator implements CursorAnimator, ValueAnimator.AnimatorUpdateListener {
    private final CodeEditor editor;
    private long lastAnimateTime;
    private float startBottom;
    private float startSize;
    private float startX;
    private float startY;
    private ValueAnimator animatorX = new ValueAnimator();
    private ValueAnimator animatorY = new ValueAnimator();
    private ValueAnimator animatorBackground = new ValueAnimator();
    private ValueAnimator animatorBgBottom = new ValueAnimator();
    private final long duration = 120;

    public MoveCursorAnimator(CodeEditor codeEditor) {
        this.editor = codeEditor;
    }

    private int getHeightOfRows(int i) {
        return this.editor.getRowHeight() * i;
    }

    @Override // io.github.rosemoe.sora.widget.style.CursorAnimator
    public void markStartPos() {
        int leftLine = this.editor.getCursor().getLeftLine();
        float[] charLayoutOffset = this.editor.getLayout().getCharLayoutOffset(leftLine, this.editor.getCursor().getLeftColumn());
        this.startX = this.editor.measureTextRegionOffset() + charLayoutOffset[1];
        this.startY = charLayoutOffset[0] - minusHeight();
        this.startSize = getHeightOfRows(this.editor.getLayout().getRowCountForLine(leftLine));
        this.startBottom = this.editor.getLayout().getCharLayoutOffset(leftLine, this.editor.getText().getColumnCount(leftLine))[0];
    }

    @Override // io.github.rosemoe.sora.widget.style.CursorAnimator
    public boolean isRunning() {
        return this.animatorX.isRunning() || this.animatorY.isRunning() || this.animatorBackground.isRunning() || this.animatorBgBottom.isRunning();
    }

    @Override // io.github.rosemoe.sora.widget.style.CursorAnimator
    public void cancel() {
        this.animatorX.cancel();
        this.animatorY.cancel();
        this.animatorBackground.cancel();
        this.animatorBgBottom.cancel();
    }

    private float minusHeight() {
        if (this.editor.getProps().textBackgroundWrapTextOnly) {
            return this.editor.getLineSpacingPixels() / 2.0f;
        }
        return 0.0f;
    }

    @Override // io.github.rosemoe.sora.widget.style.CursorAnimator
    public void markEndPos() {
        if (this.editor.isCursorAnimationEnabled()) {
            if (isRunning()) {
                this.startX = animatedX();
                this.startY = animatedY();
                this.startSize = ((Float) this.animatorBackground.getAnimatedValue()).floatValue();
                this.startBottom = ((Float) this.animatorBgBottom.getAnimatedValue()).floatValue();
                cancel();
            }
            if (System.currentTimeMillis() - this.lastAnimateTime < 100) {
                return;
            }
            int leftLine = this.editor.getCursor().getLeftLine();
            this.animatorX.removeAllUpdateListeners();
            float[] charLayoutOffset = this.editor.getLayout().getCharLayoutOffset(this.editor.getCursor().getLeftLine(), this.editor.getCursor().getLeftColumn());
            this.animatorX = ValueAnimator.ofFloat(this.startX, charLayoutOffset[1] + this.editor.measureTextRegionOffset());
            this.animatorY = ValueAnimator.ofFloat(this.startY, charLayoutOffset[0] - minusHeight());
            this.animatorBackground = ValueAnimator.ofFloat(this.startSize, getHeightOfRows(this.editor.getLayout().getRowCountForLine(this.editor.getCursor().getLeftLine())));
            this.animatorBgBottom = ValueAnimator.ofFloat(this.startBottom, this.editor.getLayout().getCharLayoutOffset(leftLine, this.editor.getText().getColumnCount(leftLine))[0]);
            this.animatorX.addUpdateListener(this);
            this.animatorX.setDuration(this.duration);
            this.animatorY.setDuration(this.duration);
            this.animatorBackground.setDuration(this.duration);
            this.animatorBgBottom.setDuration(this.duration);
        }
    }

    @Override // io.github.rosemoe.sora.widget.style.CursorAnimator
    public void start() {
        if (!this.editor.isCursorAnimationEnabled() || System.currentTimeMillis() - this.lastAnimateTime < 100) {
            this.lastAnimateTime = System.currentTimeMillis();
            return;
        }
        this.animatorX.start();
        this.animatorY.start();
        this.animatorBackground.start();
        this.animatorBgBottom.start();
        this.lastAnimateTime = System.currentTimeMillis();
    }

    @Override // io.github.rosemoe.sora.widget.style.CursorAnimator
    public float animatedX() {
        return ((Float) this.animatorX.getAnimatedValue()).floatValue();
    }

    @Override // io.github.rosemoe.sora.widget.style.CursorAnimator
    public float animatedY() {
        return ((Float) this.animatorY.getAnimatedValue()).floatValue();
    }

    @Override // io.github.rosemoe.sora.widget.style.CursorAnimator
    public float animatedLineHeight() {
        return ((Float) this.animatorBackground.getAnimatedValue()).floatValue();
    }

    @Override // io.github.rosemoe.sora.widget.style.CursorAnimator
    public float animatedLineBottom() {
        return ((Float) this.animatorBgBottom.getAnimatedValue()).floatValue();
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.editor.postInvalidateOnAnimation();
    }
}
