package io.github.rosemoe.sora.event;

import io.github.rosemoe.sora.widget.CodeEditor;

/* JADX INFO: loaded from: classes11.dex */
public class ScrollEvent extends Event {
    public static final int CAUSE_MAKE_POSITION_VISIBLE = 3;
    public static final int CAUSE_SCALE_TEXT = 5;
    public static final int CAUSE_TEXT_SELECTING = 4;
    public static final int CAUSE_USER_DRAG = 1;
    public static final int CAUSE_USER_FLING = 2;
    private final int cause;
    private final int endX;
    private final int endY;
    private float flingVelocityX;
    private float flingVelocityY;
    private final int startX;
    private final int startY;

    public ScrollEvent(CodeEditor codeEditor, int i, int i2, int i3, int i4, int i5) {
        this(codeEditor, i, i2, i3, i4, i5, 0.0f, 0.0f);
    }

    public ScrollEvent(CodeEditor codeEditor, int i, int i2, int i3, int i4, int i5, float f, float f2) {
        super(codeEditor);
        this.startX = i;
        this.startY = i2;
        this.endX = i3;
        this.endY = i4;
        this.cause = i5;
        this.flingVelocityX = f;
        this.flingVelocityY = f2;
    }

    public int getStartX() {
        return this.startX;
    }

    public int getStartY() {
        return this.startY;
    }

    public int getEndX() {
        return this.endX;
    }

    public int getEndY() {
        return this.endY;
    }

    public int getCause() {
        return this.cause;
    }

    public float getFlingVelocityX() {
        return this.flingVelocityX;
    }

    public float getFlingVelocityY() {
        return this.flingVelocityY;
    }
}
