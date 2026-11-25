package io.github.rosemoe.sora.widget;

import android.content.res.Resources;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Build;
import android.os.SystemClock;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ViewConfiguration;
import android.widget.EdgeEffect;
import io.github.rosemoe.sora.event.ClickEvent;
import io.github.rosemoe.sora.event.ContextClickEvent;
import io.github.rosemoe.sora.event.DoubleClickEvent;
import io.github.rosemoe.sora.event.EditorMotionEvent;
import io.github.rosemoe.sora.event.HandleStateChangeEvent;
import io.github.rosemoe.sora.event.LongPressEvent;
import io.github.rosemoe.sora.event.ScrollEvent;
import io.github.rosemoe.sora.event.SideIconClickEvent;
import io.github.rosemoe.sora.graphics.RectUtils;
import io.github.rosemoe.sora.lang.styling.Span;
import io.github.rosemoe.sora.lang.styling.Styles;
import io.github.rosemoe.sora.lang.styling.StylesUtils;
import io.github.rosemoe.sora.lang.styling.line.LineSideIcon;
import io.github.rosemoe.sora.text.CharPosition;
import io.github.rosemoe.sora.text.Content;
import io.github.rosemoe.sora.text.Cursor;
import io.github.rosemoe.sora.text.TextRange;
import io.github.rosemoe.sora.util.IntPair;
import io.github.rosemoe.sora.util.Numbers;
import io.github.rosemoe.sora.widget.component.Magnifier;
import io.github.rosemoe.sora.widget.layout.Row;
import io.github.rosemoe.sora.widget.style.SelectionHandleStyle;
import kotlin.jvm.functions.Function5;
import org.jetbrains.kotlin.konan.util.DependencyDownloader;

/* JADX INFO: loaded from: classes11.dex */
public final class EditorTouchEventHandler implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, ScaleGestureDetector.OnScaleGestureListener {
    private static final int BOTTOM_EDGE = 8;
    private static final int HIDE_DELAY = 3000;
    private static final int HIDE_DELAY_HANDLE = 3500;
    private static final int LEFT_EDGE = 1;
    private static final int MAGNIFIER_TOUCH_SLOP = 4;
    private static final int RIGHT_EDGE = 2;
    private static final int SCROLLBAR_FADE_ANIMATION_TIME = 200;
    private static final int TOP_EDGE = 4;
    CharPosition draggingSelection;
    private float edgeFieldSize;
    private int edgeFlags;
    private final CodeEditor editor;
    float focusY;
    boolean glowLeftOrRight;
    boolean glowTopOrBottom;
    private PointF lastContextClickPosition;
    private long lastTimeMousePrimaryClickUp;
    Magnifier magnifier;
    long memoryPosition;
    float motionX;
    float motionY;
    boolean mouseCanMoveText;
    boolean mouseClick;
    private boolean mouseDoubleClick;
    private int mouseDownButtonState;
    private float mouseDownX;
    private float mouseDownY;
    boolean positionNotApplied;
    private final EditorScroller scroller;
    private float textSizeStart;
    private MotionEvent thumbMotionRecord;
    private final int touchSlop;
    int selHandleType = -1;
    public boolean isScaling = false;
    private long timeLastScroll = 0;
    private long timeLastSetSelection = 0;
    private boolean holdingScrollbarVertical = false;
    private boolean holdingScrollbarHorizontal = false;
    private boolean holdingInsertHandle = false;
    private float thumbDownY = 0.0f;
    private float thumbDownX = 0.0f;
    private int touchedHandleType = -1;
    float scaleMaxSize = TypedValue.applyDimension(2, 26.0f, Resources.getSystem().getDisplayMetrics());
    float scaleMinSize = TypedValue.applyDimension(2, 8.0f, Resources.getSystem().getDisplayMetrics());
    private SelectionHandle leftHandle = new SelectionHandle(1);
    private SelectionHandle rightHandle = new SelectionHandle(2);
    private final SelectionHandle insertHandle = new SelectionHandle(0);

    @Override // android.view.GestureDetector.OnDoubleTapListener
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return true;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public void onShowPress(MotionEvent motionEvent) {
    }

    @Override // android.view.GestureDetector.OnDoubleTapListener
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return true;
    }

    public EditorTouchEventHandler(CodeEditor codeEditor) {
        this.editor = codeEditor;
        this.edgeFieldSize = codeEditor.getDpUnit() * 18.0f;
        this.scroller = new EditorScroller(codeEditor);
        this.magnifier = new Magnifier(codeEditor);
        this.touchSlop = ViewConfiguration.get(codeEditor.getContext()).getScaledTouchSlop();
    }

    public boolean hasAnyHeldHandle() {
        return holdInsertHandle() || this.selHandleType != -1;
    }

    public boolean shouldDrawScrollBar() {
        return System.currentTimeMillis() - this.timeLastScroll < 3200 || this.holdingScrollbarVertical || this.holdingScrollbarHorizontal;
    }

    public float getScrollBarMovementPercentage() {
        if (System.currentTimeMillis() - this.timeLastScroll < DependencyDownloader.DEFAULT_ATTEMPT_INTERVAL_MS || this.holdingScrollbarVertical || this.holdingScrollbarHorizontal) {
            return 0.0f;
        }
        if (System.currentTimeMillis() - this.timeLastScroll < DependencyDownloader.DEFAULT_ATTEMPT_INTERVAL_MS || System.currentTimeMillis() - this.timeLastScroll >= 3200) {
            return 1.0f;
        }
        this.editor.postInvalidateOnAnimation();
        return (((System.currentTimeMillis() - this.timeLastScroll) - DependencyDownloader.DEFAULT_ATTEMPT_INTERVAL_MS) * 1.0f) / 200.0f;
    }

    public void hideInsertHandle() {
        if (shouldDrawInsertHandle()) {
            this.timeLastSetSelection = 0L;
            this.editor.invalidate();
        }
    }

    public boolean holdVerticalScrollBar() {
        return this.holdingScrollbarVertical;
    }

    public boolean holdHorizontalScrollBar() {
        return this.holdingScrollbarHorizontal;
    }

    public boolean holdInsertHandle() {
        return this.holdingInsertHandle;
    }

    public boolean shouldDrawInsertHandle() {
        return System.currentTimeMillis() - this.timeLastSetSelection < 3500 || this.holdingInsertHandle;
    }

    public void notifyScrolled() {
        this.timeLastScroll = System.currentTimeMillis();
        this.editor.postDelayedInLifecycle(new Runnable() { // from class: io.github.rosemoe.sora.widget.EditorTouchEventHandler.1ScrollNotifier
            @Override // java.lang.Runnable
            public void run() {
                if (System.currentTimeMillis() - EditorTouchEventHandler.this.timeLastScroll >= DependencyDownloader.DEFAULT_ATTEMPT_INTERVAL_MS) {
                    EditorTouchEventHandler.this.editor.invalidate();
                }
            }
        }, DependencyDownloader.DEFAULT_ATTEMPT_INTERVAL_MS);
    }

    public void notifyLater() {
        this.timeLastSetSelection = System.currentTimeMillis();
        this.editor.postDelayedInLifecycle(new Runnable() { // from class: io.github.rosemoe.sora.widget.EditorTouchEventHandler.1InvalidateNotifier
            @Override // java.lang.Runnable
            public void run() {
                if (System.currentTimeMillis() - EditorTouchEventHandler.this.timeLastSetSelection >= 3500) {
                    EditorTouchEventHandler.this.editor.invalidate();
                }
            }
        }, 3500L);
    }

    public boolean handlingMotions() {
        return holdHorizontalScrollBar() || holdVerticalScrollBar() || holdInsertHandle() || this.selHandleType != -1;
    }

    public EditorScroller getScroller() {
        return this.scroller;
    }

    public void reset() {
        this.scroller.startScroll(0, 0, 0, 0, 0);
        reset2();
    }

    public void reset2() {
        this.holdingScrollbarVertical = false;
        this.holdingScrollbarHorizontal = false;
        this.holdingInsertHandle = false;
        dismissMagnifier();
    }

    public void updateMagnifier(MotionEvent motionEvent) {
        float x;
        float f;
        if (this.edgeFlags != 0) {
            dismissMagnifier();
            return;
        }
        if (this.magnifier.isEnabled()) {
            RectF rectF = this.editor.getInsertHandleDescriptor().position;
            RectF rectF2 = this.editor.getLeftHandleDescriptor().position;
            RectF rectF3 = this.editor.getRightHandleDescriptor().position;
            if (this.editor.isStickyTextSelection()) {
                int i = this.selHandleType;
                boolean z = i == 1;
                boolean z2 = i == 2;
                float fMax = Math.max(Math.max(rectF.height(), rectF2.height()), rectF3.height());
                if (holdInsertHandle()) {
                    x = Math.abs(rectF.left - motionEvent.getX()) > ((float) this.editor.getRowHeight()) ? rectF.left : motionEvent.getX();
                    f = rectF.top;
                } else if (z) {
                    x = Math.abs(rectF2.left - motionEvent.getX()) > ((float) this.editor.getRowHeight()) ? rectF2.left : motionEvent.getX();
                    f = rectF2.top;
                } else if (z2) {
                    x = Math.abs(rectF3.left - motionEvent.getX()) > ((float) this.editor.getRowHeight()) ? rectF3.left : motionEvent.getX();
                    f = rectF3.top;
                } else {
                    x = 0.0f;
                    f = 0.0f;
                }
                this.magnifier.show((int) x, (int) (f - (fMax / 2.0f)));
                return;
            }
            this.magnifier.show((int) motionEvent.getX(), (int) ((motionEvent.getY() - (Math.max(Math.max(rectF.height(), rectF2.height()), rectF3.height()) / 2.0f)) - this.editor.getRowHeight()));
        }
    }

    public void dismissMagnifier() {
        this.magnifier.dismiss();
    }

    /* JADX WARN: Code duplicated, block: B:36:0x00fe  */
    /* JADX WARN: Code duplicated, block: B:38:0x0102  */
    /* JADX WARN: Code duplicated, block: B:41:0x0116  */
    /* JADX WARN: Code duplicated, block: B:44:0x012a  */
    /* JADX WARN: Code duplicated, block: B:47:0x013b  */
    /* JADX WARN: Code duplicated, block: B:50:0x0149  */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int i;
        float width;
        this.motionY = motionEvent.getY();
        this.motionX = motionEvent.getX();
        int action = motionEvent.getAction();
        if (action == 0) {
            this.holdingScrollbarHorizontal = false;
            this.holdingScrollbarVertical = false;
            if (RectUtils.contains(this.editor.getRenderer().getVerticalScrollBarRect(), motionEvent.getX(), motionEvent.getY(), this.editor.getDpUnit() * 10.0f)) {
                this.holdingScrollbarVertical = true;
                this.thumbDownY = motionEvent.getY();
            }
            if (this.editor.getRenderer().getHorizontalScrollBarRect().contains(motionEvent.getX(), motionEvent.getY())) {
                this.holdingScrollbarHorizontal = true;
                this.thumbDownX = motionEvent.getX();
            }
            boolean z = this.holdingScrollbarVertical;
            if (z && this.holdingScrollbarHorizontal) {
                this.holdingScrollbarHorizontal = false;
            }
            if (z || this.holdingScrollbarHorizontal) {
                this.editor.invalidate();
            }
            float dpUnit = this.editor.getDpUnit() * 7.0f;
            if (shouldDrawInsertHandle() && RectUtils.almostContains(this.editor.getInsertHandleDescriptor().position, motionEvent.getX(), motionEvent.getY(), dpUnit)) {
                this.holdingInsertHandle = true;
                dispatchHandleStateChange(0, true);
                this.thumbDownY = motionEvent.getY();
                this.thumbDownX = motionEvent.getX();
            }
            boolean zAlmostContains = RectUtils.almostContains(this.editor.getLeftHandleDescriptor().position, motionEvent.getX(), motionEvent.getY(), dpUnit);
            boolean zAlmostContains2 = RectUtils.almostContains(this.editor.getRightHandleDescriptor().position, motionEvent.getX(), motionEvent.getY(), dpUnit);
            if (zAlmostContains || zAlmostContains2) {
                if (zAlmostContains) {
                    this.selHandleType = 1;
                    this.touchedHandleType = 1;
                } else {
                    this.selHandleType = 2;
                    this.touchedHandleType = 2;
                }
                dispatchHandleStateChange(this.selHandleType, true);
                this.thumbDownY = motionEvent.getY();
                this.thumbDownX = motionEvent.getX();
            }
            return true;
        }
        if (action == 1) {
            if (this.holdingScrollbarVertical) {
                this.holdingScrollbarVertical = false;
                this.editor.invalidate();
                this.timeLastScroll = System.currentTimeMillis();
                notifyScrolled();
            }
            if (this.holdingScrollbarHorizontal) {
                this.holdingScrollbarHorizontal = false;
                this.editor.invalidate();
                this.timeLastScroll = System.currentTimeMillis();
                notifyScrolled();
            }
            if (this.holdingInsertHandle) {
                this.holdingInsertHandle = false;
                this.editor.invalidate();
                notifyLater();
                dispatchHandleStateChange(0, false);
            }
            i = this.selHandleType;
            if (i != -1) {
                dispatchHandleStateChange(i, false);
                this.selHandleType = -1;
            }
            this.editor.invalidate();
            if (this.touchedHandleType > -1) {
                this.touchedHandleType = -1;
            }
            stopEdgeScroll();
            dismissMagnifier();
        } else {
            if (action == 2) {
                if (this.holdingScrollbarVertical) {
                    float y = motionEvent.getY() - this.thumbDownY;
                    this.thumbDownY = motionEvent.getY();
                    scrollBy(0.0f, (y / (this.editor.getHeight() - this.editor.getRenderer().getVerticalScrollBarRect().height())) * this.editor.getScrollMaxY());
                    return true;
                }
                if (this.holdingScrollbarHorizontal) {
                    float x = motionEvent.getX() - this.thumbDownX;
                    this.thumbDownX = motionEvent.getX();
                    float scrollMaxX = this.editor.getScrollMaxX() + this.editor.getWidth();
                    if (this.editor.getRenderer().getHorizontalScrollBarRect().width() <= this.editor.getDpUnit() * 60.0f) {
                        width = this.editor.getWidth() - this.editor.getRenderer().getHorizontalScrollBarRect().width();
                    } else {
                        width = this.editor.getWidth();
                    }
                    scrollBy((x / width) * scrollMaxX, 0.0f);
                    return true;
                }
                if (!handleSelectionChange(motionEvent)) {
                    return false;
                }
                if (this.magnifier.isShowing() || Math.sqrt(((motionEvent.getX() - this.thumbDownX) * (motionEvent.getX() - this.thumbDownX)) + ((motionEvent.getY() - this.thumbDownY) * (motionEvent.getY() - this.thumbDownY))) >= 4.0d) {
                    updateMagnifier(motionEvent);
                }
                if (this.touchedHandleType != -1 || holdInsertHandle()) {
                    this.editor.invalidate();
                }
                return true;
            }
            if (action == 3) {
                if (this.holdingScrollbarVertical) {
                    this.holdingScrollbarVertical = false;
                    this.editor.invalidate();
                    this.timeLastScroll = System.currentTimeMillis();
                    notifyScrolled();
                }
                if (this.holdingScrollbarHorizontal) {
                    this.holdingScrollbarHorizontal = false;
                    this.editor.invalidate();
                    this.timeLastScroll = System.currentTimeMillis();
                    notifyScrolled();
                }
                if (this.holdingInsertHandle) {
                    this.holdingInsertHandle = false;
                    this.editor.invalidate();
                    notifyLater();
                    dispatchHandleStateChange(0, false);
                }
                i = this.selHandleType;
                if (i != -1) {
                    dispatchHandleStateChange(i, false);
                    this.selHandleType = -1;
                }
                this.editor.invalidate();
                if (this.touchedHandleType > -1) {
                    this.touchedHandleType = -1;
                }
                stopEdgeScroll();
                dismissMagnifier();
            }
        }
        return false;
    }

    public boolean onMouseEvent(MotionEvent motionEvent) {
        if (this.editor.isFormatting()) {
            resetMouse();
            return false;
        }
        this.lastContextClickPosition = null;
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mouseDownX = motionEvent.getX();
            this.mouseDownY = motionEvent.getY();
            int buttonState = motionEvent.getButtonState();
            this.mouseDownButtonState = buttonState;
            this.mouseClick = true;
            if ((buttonState & 1) != 0) {
                if (SystemClock.uptimeMillis() - this.lastTimeMousePrimaryClickUp < ViewConfiguration.getDoubleTapTimeout()) {
                    this.mouseDoubleClick = true;
                    onDoubleTap(motionEvent);
                    return true;
                }
                long pointPositionOnScreen = this.editor.getPointPositionOnScreen(this.mouseDownX, this.mouseDownY);
                int first = IntPair.getFirst(pointPositionOnScreen);
                int second = IntPair.getSecond(pointPositionOnScreen);
                CharPosition charPosition = this.editor.getText().getIndexer().getCharPosition(first, second);
                if (this.editor.isTextSelected() && this.editor.getCursorRange().isPositionInside(charPosition) && this.editor.isScreenPointOnText(this.mouseDownX, this.mouseDownY)) {
                    this.mouseCanMoveText = true;
                } else {
                    this.mouseCanMoveText = false;
                    this.editor.setSelection(first, second, 8);
                    this.editor.requestFocus();
                }
                this.draggingSelection = charPosition;
                this.editor.postInvalidate();
            }
        } else if (actionMasked == 1) {
            if (motionEvent.getEventTime() - motionEvent.getDownTime() > ViewConfiguration.getTapTimeout() * 2.0f) {
                this.mouseClick = false;
            }
            if (!this.mouseDoubleClick) {
                if (this.mouseCanMoveText && !this.mouseClick && (this.mouseDownButtonState & 1) != 0) {
                    long pointPositionOnScreen2 = this.editor.getPointPositionOnScreen(motionEvent.getX(), motionEvent.getY());
                    CharPosition charPosition2 = this.editor.getText().getIndexer().getCharPosition(IntPair.getFirst(pointPositionOnScreen2), IntPair.getSecond(pointPositionOnScreen2));
                    TextRange cursorRange = this.editor.getCursorRange();
                    if (!cursorRange.isPositionInside(charPosition2) && (this.editor.getKeyMetaStates().isCtrlPressed() || !cursorRange.getEnd().equals(charPosition2))) {
                        int endIndex = cursorRange.getEndIndex() - cursorRange.getStartIndex();
                        int i = (!this.editor.getKeyMetaStates().isCtrlPressed() && charPosition2.index >= cursorRange.getStartIndex()) ? charPosition2.index - endIndex : charPosition2.index;
                        Content text = this.editor.getText();
                        String strSubstring = text.substring(cursorRange.getStartIndex(), cursorRange.getEndIndex());
                        if (this.editor.getKeyMetaStates().isCtrlPressed()) {
                            text.insert(charPosition2.line, charPosition2.column, strSubstring);
                        } else {
                            text.beginBatchEdit();
                            this.editor.deleteText();
                            charPosition2 = text.getIndexer().getCharPosition(i);
                            text.insert(charPosition2.line, charPosition2.column, strSubstring);
                            text.endBatchEdit();
                        }
                        CharPosition charPosition3 = text.getIndexer().getCharPosition(i + endIndex);
                        this.editor.setSelectionRegion(charPosition2.getLine(), charPosition2.getColumn(), charPosition3.getLine(), charPosition3.getColumn(), 8);
                    }
                }
                if (this.mouseClick) {
                    int i2 = this.mouseDownButtonState;
                    if ((i2 & 1) != 0) {
                        onSingleTapUp(motionEvent);
                        this.lastTimeMousePrimaryClickUp = motionEvent.getEventTime();
                    } else if ((i2 & 2) != 0) {
                        onContextClick(motionEvent);
                    }
                }
            }
            resetMouse();
        } else if (actionMasked != 2) {
            if (actionMasked == 3) {
                resetMouse();
            }
        } else {
            if (this.mouseDoubleClick) {
                return true;
            }
            if (Math.abs(motionEvent.getX() - this.mouseDownX) > this.touchSlop || Math.abs(motionEvent.getY() - this.mouseDownY) > this.touchSlop) {
                this.mouseClick = false;
            }
            if ((this.mouseDownButtonState & 1) != 0) {
                long pointPositionOnScreen3 = this.editor.getPointPositionOnScreen(motionEvent.getX(), motionEvent.getY());
                int first2 = IntPair.getFirst(pointPositionOnScreen3);
                int second2 = IntPair.getSecond(pointPositionOnScreen3);
                CharPosition charPosition4 = this.editor.getText().getIndexer().getCharPosition(first2, second2);
                if (!this.mouseClick && !this.mouseCanMoveText) {
                    CharPosition charPosition5 = this.editor.selectionAnchor;
                    this.editor.setSelectionRegion(charPosition5.line, charPosition5.column, first2, second2, 8);
                }
                this.draggingSelection = charPosition4;
                this.editor.postInvalidate();
                scrollIfThumbReachesEdge(motionEvent);
            }
        }
        return true;
    }

    public void resetMouse() {
        this.mouseDownY = 0.0f;
        this.mouseDownX = 0.0f;
        this.mouseClick = false;
        this.mouseCanMoveText = false;
        this.draggingSelection = null;
        if (this.mouseDoubleClick) {
            this.mouseDoubleClick = false;
            this.lastTimeMousePrimaryClickUp = 0L;
        }
    }

    public void onContextClick(MotionEvent motionEvent) {
        this.lastContextClickPosition = new PointF(motionEvent.getX(), motionEvent.getY());
        if ((dispatchEditorMotionEvent(new Function5() { // from class: io.github.rosemoe.sora.widget.EditorTouchEventHandler$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function5
            public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                return new ContextClickEvent((CodeEditor) obj, (CharPosition) obj2, (MotionEvent) obj3, (Span) obj4, (TextRange) obj5);
            }
        }, null, motionEvent) & 2) != 0) {
            return;
        }
        this.editor.performContextClick(motionEvent.getX(), motionEvent.getY());
        if (this.editor.getProps().mouseContextMenu) {
            this.editor.showContextMenu(motionEvent.getX(), motionEvent.getY());
        }
    }

    public PointF getLastContextClickPosition() {
        return this.lastContextClickPosition;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchHandleStateChange(int i, boolean z) {
        this.editor.dispatchEvent(new HandleStateChangeEvent(this.editor, i, z));
    }

    int dispatchEditorMotionEvent(Function5<CodeEditor, CharPosition, MotionEvent, Span, TextRange, EditorMotionEvent> function5, CharPosition charPosition, MotionEvent motionEvent) {
        TextRange textRange;
        CharPosition charPosition2;
        if (charPosition == null) {
            long pointPositionOnScreen = this.editor.getPointPositionOnScreen(motionEvent.getX(), motionEvent.getY());
            charPosition = this.editor.getText().getIndexer().getCharPosition(IntPair.getFirst(pointPositionOnScreen), IntPair.getSecond(pointPositionOnScreen));
        }
        CharPosition charPosition3 = charPosition;
        Styles styles = this.editor.getStyles();
        Content text = this.editor.getText();
        Span spanForPosition = StylesUtils.getSpanForPosition(styles, charPosition3);
        Span followingSpanForPosition = StylesUtils.getFollowingSpanForPosition(styles, charPosition3);
        if (spanForPosition != null) {
            CharPosition charPosition4 = text.getIndexer().getCharPosition(charPosition3.line, Numbers.coerceIn(spanForPosition.getColumn(), 0, text.getColumnCount(charPosition3.line)));
            if (followingSpanForPosition != null) {
                charPosition2 = text.getIndexer().getCharPosition(charPosition3.line, Numbers.coerceIn(followingSpanForPosition.getColumn(), 0, text.getColumnCount(charPosition3.line)));
            } else {
                charPosition2 = text.getIndexer().getCharPosition(charPosition3.line, text.getColumnCount(charPosition3.line));
            }
            textRange = new TextRange(charPosition4, charPosition2);
        } else {
            textRange = null;
        }
        CodeEditor codeEditor = this.editor;
        return codeEditor.dispatchEvent(function5.invoke(codeEditor, charPosition3, motionEvent, spanForPosition, textRange));
    }

    private boolean handleSelectionChange(MotionEvent motionEvent) {
        if (this.holdingInsertHandle) {
            this.insertHandle.applyPosition(motionEvent);
            scrollIfThumbReachesEdge(motionEvent);
            return true;
        }
        int i = this.selHandleType;
        if (i == 1) {
            CodeEditor codeEditor = this.editor;
            codeEditor.selectionAnchor = codeEditor.getCursor().right();
            this.leftHandle.applyPosition(motionEvent);
            scrollIfThumbReachesEdge(motionEvent);
            return true;
        }
        if (i != 2) {
            return false;
        }
        CodeEditor codeEditor2 = this.editor;
        codeEditor2.selectionAnchor = codeEditor2.getCursor().left();
        this.rightHandle.applyPosition(motionEvent);
        scrollIfThumbReachesEdge(motionEvent);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSelectionChange2(MotionEvent motionEvent) {
        if (this.holdingInsertHandle) {
            this.insertHandle.applyPosition(motionEvent);
            return;
        }
        int i = this.selHandleType;
        if (i == 1) {
            this.leftHandle.applyPosition(motionEvent);
        } else {
            if (i != 2) {
                return;
            }
            this.rightHandle.applyPosition(motionEvent);
        }
    }

    private int computeEdgeFlags(float f, float f2) {
        float f3 = this.edgeFieldSize;
        int i = f < f3 ? 1 : 0;
        if (f2 < f3) {
            i |= 4;
        }
        if (f > this.editor.getWidth() - this.edgeFieldSize) {
            i |= 2;
        }
        return f2 > ((float) this.editor.getHeight()) - this.edgeFieldSize ? i | 8 : i;
    }

    public void scrollIfThumbReachesEdge(MotionEvent motionEvent) {
        scrollIfReachesEdge(motionEvent, 0.0f, 0.0f);
    }

    public void scrollIfReachesEdge(MotionEvent motionEvent, float f, float f2) {
        if (motionEvent != null) {
            f = motionEvent.getX();
            f2 = motionEvent.getY();
        }
        int iComputeEdgeFlags = computeEdgeFlags(f, f2);
        if (iComputeEdgeFlags != 0) {
            int i = this.edgeFlags;
            this.edgeFlags = iComputeEdgeFlags;
            this.thumbMotionRecord = motionEvent == null ? null : MotionEvent.obtain(motionEvent);
            if (i == 0) {
                this.editor.postInLifecycle(new EdgeScrollRunnable((int) (this.editor.getDpUnit() * 8.0f)));
                return;
            }
            return;
        }
        stopEdgeScroll();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isSameSign(float f, float f2) {
        if (Math.abs(f) < 100000.0d || Math.abs(f2) < 100000.0d) {
            return false;
        }
        return (f < 0.0f && f2 < 0.0f) || (f > 0.0f && f2 > 0.0f);
    }

    public void stopEdgeScroll() {
        this.edgeFlags = 0;
    }

    public void scrollBy(float f, float f2) {
        scrollBy(f, f2, false);
    }

    public void scrollBy(float f, float f2, boolean z) {
        int currX = this.scroller.getCurrX() + ((int) f);
        int currY = this.scroller.getCurrY() + ((int) f2);
        int iMax = Math.max(currX, 0);
        int iMin = Math.min(Math.max(currY, 0), this.editor.getScrollMaxY());
        int iMin2 = Math.min(iMax, this.editor.getScrollMaxX());
        this.editor.dispatchEvent(new ScrollEvent(this.editor, this.scroller.getCurrX(), this.scroller.getCurrY(), iMin2, iMin, 1));
        if (z) {
            EditorScroller editorScroller = this.scroller;
            editorScroller.startScroll(editorScroller.getCurrX(), this.scroller.getCurrY(), iMin2 - this.scroller.getCurrX(), iMin - this.scroller.getCurrY());
        } else {
            EditorScroller editorScroller2 = this.scroller;
            editorScroller2.startScroll(editorScroller2.getCurrX(), this.scroller.getCurrY(), iMin2 - this.scroller.getCurrX(), iMin - this.scroller.getCurrY(), 0);
            this.scroller.abortAnimation();
        }
        this.editor.invalidate();
    }

    public int getTouchedHandleType() {
        return this.touchedHandleType;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        LineSideIcon lineSideIcon;
        this.scroller.forceFinished(true);
        if (this.editor.isFormatting()) {
            return true;
        }
        int first = IntPair.getFirst(RegionResolverKt.resolveTouchRegion(this.editor, motionEvent));
        long pointPositionOnScreen = this.editor.getPointPositionOnScreen(motionEvent.getX(), motionEvent.getY());
        int first2 = IntPair.getFirst(pointPositionOnScreen);
        int second = IntPair.getSecond(pointPositionOnScreen);
        this.editor.performClick();
        if (first == 2) {
            Row rowAt = this.editor.getLayout().getRowAt(Math.max(0, Math.min(((int) (motionEvent.getY() + this.editor.getOffsetX())) / this.editor.getRowHeight(), this.editor.getLayout().getRowCount() - 1)));
            if (rowAt.isLeadingRow && (lineSideIcon = (LineSideIcon) this.editor.getRenderer().getLineStyle(rowAt.lineIndex, LineSideIcon.class)) != null && (this.editor.dispatchEvent(new SideIconClickEvent(this.editor, lineSideIcon)) & 2) != 0) {
                return true;
            }
        }
        if ((dispatchEditorMotionEvent(new Function5() { // from class: io.github.rosemoe.sora.widget.EditorTouchEventHandler$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function5
            public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                return new ClickEvent((CodeEditor) obj, (CharPosition) obj2, (MotionEvent) obj3, (Span) obj4, (TextRange) obj5);
            }
        }, this.editor.getText().getIndexer().getCharPosition(first2, second), motionEvent) & 2) != 0) {
            return true;
        }
        this.editor.showSoftInput();
        notifyLater();
        int i = this.editor.getProps().actionWhenLineNumberClicked;
        if (first == 5) {
            if (this.editor.isInLongSelect()) {
                Cursor cursor = this.editor.getCursor();
                this.editor.setSelectionRegion(cursor.getLeftLine(), cursor.getLeftColumn(), first2, second, false, 3);
                this.editor.endLongSelect();
            } else {
                this.editor.setSelection(first2, second, 3);
            }
        } else if (first == 1) {
            if (i == 1) {
                CodeEditor codeEditor = this.editor;
                codeEditor.setSelectionRegion(first2, 0, first2, codeEditor.getText().getColumnCount(first2), false, 3);
            } else if (i == 2) {
                this.editor.setSelection(first2, second, 3);
            }
        }
        return true;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public void onLongPress(MotionEvent motionEvent) {
        if (this.editor.isFormatting()) {
            return;
        }
        long pointPositionOnScreen = this.editor.getPointPositionOnScreen(motionEvent.getX(), motionEvent.getY());
        int first = IntPair.getFirst(pointPositionOnScreen);
        int second = IntPair.getSecond(pointPositionOnScreen);
        if ((dispatchEditorMotionEvent(new Function5() { // from class: io.github.rosemoe.sora.widget.EditorTouchEventHandler$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function5
            public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                return new LongPressEvent((CodeEditor) obj, (CharPosition) obj2, (MotionEvent) obj3, (Span) obj4, (TextRange) obj5);
            }
        }, this.editor.getText().getIndexer().getCharPosition(first, second), motionEvent) & 2) != 0) {
            return;
        }
        if ((this.editor.getProps().reselectOnLongPress || !this.editor.getCursor().isSelected()) && motionEvent.getPointerCount() == 1) {
            this.editor.performHapticFeedback(0);
            this.editor.selectWord(first, second);
        }
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        int i;
        boolean z;
        int i2;
        boolean z2;
        int currX = this.scroller.getCurrX() + ((int) f);
        int currY = this.scroller.getCurrY() + ((int) f2);
        int iMax = Math.max(currX, 0);
        int iMin = Math.min(Math.max(currY, 0), this.editor.getScrollMaxY());
        int iMin2 = Math.min(iMax, this.editor.getScrollMaxX());
        if (this.editor.getVerticalEdgeEffect().isFinished()) {
            i = iMin;
            z = true;
        } else {
            float fMax = Math.max(0.0f, Math.min(1.0f, motionEvent2.getX() / this.editor.getWidth()));
            float measuredHeight = (this.glowTopOrBottom ? f2 : -f2) / this.editor.getMeasuredHeight();
            if (measuredHeight > 0.0f) {
                iMin = this.scroller.getCurrY();
                EdgeEffect verticalEdgeEffect = this.editor.getVerticalEdgeEffect();
                if (this.glowTopOrBottom) {
                    fMax = 1.0f - fMax;
                }
                verticalEdgeEffect.onPull(measuredHeight, fMax);
            } else if (Build.VERSION.SDK_INT >= 31) {
                EdgeEffect verticalEdgeEffect2 = this.editor.getVerticalEdgeEffect();
                if (this.glowTopOrBottom) {
                    fMax = 1.0f - fMax;
                }
                verticalEdgeEffect2.onPullDistance(measuredHeight, fMax);
                if (verticalEdgeEffect2.getDistance() != 0.0f) {
                    iMin = this.scroller.getCurrY();
                }
            } else {
                this.editor.getVerticalEdgeEffect().finish();
            }
            i = iMin;
            z = false;
        }
        if (this.editor.getHorizontalEdgeEffect().isFinished()) {
            i2 = iMin2;
            z2 = true;
        } else {
            float fMax2 = Math.max(0.0f, Math.min(1.0f, motionEvent2.getY() / this.editor.getHeight()));
            float measuredWidth = (this.glowLeftOrRight ? f : -f) / this.editor.getMeasuredWidth();
            if (measuredWidth > 0.0f) {
                iMin2 = this.scroller.getCurrX();
                EdgeEffect horizontalEdgeEffect = this.editor.getHorizontalEdgeEffect();
                if (!this.glowLeftOrRight) {
                    fMax2 = 1.0f - fMax2;
                }
                horizontalEdgeEffect.onPull(measuredWidth, fMax2);
            } else if (Build.VERSION.SDK_INT >= 31) {
                EdgeEffect horizontalEdgeEffect2 = this.editor.getHorizontalEdgeEffect();
                if (!this.glowLeftOrRight) {
                    fMax2 = 1.0f - fMax2;
                }
                horizontalEdgeEffect2.onPullDistance(measuredWidth, fMax2);
                if (horizontalEdgeEffect2.getDistance() != 0.0f) {
                    iMin2 = this.scroller.getCurrX();
                }
            } else {
                this.editor.getHorizontalEdgeEffect().finish();
            }
            i2 = iMin2;
            z2 = false;
        }
        EditorScroller editorScroller = this.scroller;
        editorScroller.startScroll(editorScroller.getCurrX(), this.scroller.getCurrY(), i2 - this.scroller.getCurrX(), i - this.scroller.getCurrY(), 0);
        if (z && this.scroller.getCurrY() + f2 < -2.0f) {
            this.editor.getVerticalEdgeEffect().onPull((-f2) / this.editor.getMeasuredHeight(), Math.max(0.0f, Math.min(1.0f, motionEvent2.getX() / this.editor.getWidth())));
            this.glowTopOrBottom = false;
        }
        if (z && this.scroller.getCurrY() + f2 > this.editor.getScrollMaxY() + 2.0f) {
            this.editor.getVerticalEdgeEffect().onPull(f2 / this.editor.getMeasuredHeight(), Math.max(0.0f, Math.min(1.0f, motionEvent2.getX() / this.editor.getWidth())));
            this.glowTopOrBottom = true;
        }
        if (z2 && this.scroller.getCurrX() + f < -2.0f) {
            this.editor.getHorizontalEdgeEffect().onPull((-f) / this.editor.getMeasuredWidth(), Math.max(0.0f, Math.min(1.0f, motionEvent2.getY() / this.editor.getHeight())));
            this.glowLeftOrRight = false;
        }
        if (z2 && this.scroller.getCurrX() + f > this.editor.getScrollMaxX() + 2.0f) {
            this.editor.getHorizontalEdgeEffect().onPull(f / this.editor.getMeasuredWidth(), Math.max(0.0f, Math.min(1.0f, motionEvent2.getY() / this.editor.getHeight())));
            this.glowLeftOrRight = true;
        }
        this.editor.invalidate();
        this.editor.dispatchEvent(new ScrollEvent(this.editor, this.scroller.getCurrX(), this.scroller.getCurrY(), i2, i, 1));
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:11:0x002c A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:12:0x002d  */
    /* JADX WARN: Code duplicated, block: B:17:0x006d  */
    /* JADX WARN: Code duplicated, block: B:20:0x0078  */
    /* JADX WARN: Code duplicated, block: B:21:0x0083  */
    /* JADX WARN: Code duplicated, block: B:26:0x00a3  */
    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        float f3;
        float f4;
        int dpUnit;
        int dpUnit2;
        float dpUnit3;
        if (this.editor.getProps().singleDirectionFling) {
            f3 = 0.0f;
            if (Math.abs(f) > Math.abs(f2)) {
                f4 = 0.0f;
                f3 = f;
            }
            if (!this.editor.getProps().scrollFling) {
                return false;
            }
            this.scroller.forceFinished(true);
            EditorScroller editorScroller = this.scroller;
            int currX = editorScroller.getCurrX();
            int currY = this.scroller.getCurrY();
            int i = (int) (-f3);
            int i2 = (int) (-f4);
            int scrollMaxX = this.editor.getScrollMaxX();
            int scrollMaxY = this.editor.getScrollMaxY();
            if (this.editor.getProps().overScrollEnabled || this.editor.isWordwrap()) {
                dpUnit = 0;
            } else {
                dpUnit = (int) (this.editor.getDpUnit() * 20.0f);
            }
            if (this.editor.getProps().overScrollEnabled) {
                dpUnit2 = (int) (this.editor.getDpUnit() * 20.0f);
            } else {
                dpUnit2 = 0;
            }
            editorScroller.fling(currX, currY, i, i2, 0, scrollMaxX, 0, scrollMaxY, dpUnit, dpUnit2);
            dpUnit3 = this.editor.getDpUnit() * 2000.0f;
            if (Math.abs(f3) < dpUnit3 || Math.abs(f4) >= dpUnit3) {
                notifyScrolled();
            }
            this.editor.releaseEdgeEffects();
            this.editor.dispatchEvent(new ScrollEvent(this.editor, this.scroller.getCurrX(), this.scroller.getCurrY(), this.scroller.getFinalX(), this.scroller.getFinalY(), 2));
            this.editor.postInvalidateOnAnimation();
            return false;
        }
        f3 = f;
        f4 = f2;
        if (!this.editor.getProps().scrollFling) {
            return false;
        }
        this.scroller.forceFinished(true);
        EditorScroller editorScroller2 = this.scroller;
        int currX2 = editorScroller2.getCurrX();
        int currY2 = this.scroller.getCurrY();
        int i3 = (int) (-f3);
        int i4 = (int) (-f4);
        int scrollMaxX2 = this.editor.getScrollMaxX();
        int scrollMaxY2 = this.editor.getScrollMaxY();
        if (this.editor.getProps().overScrollEnabled) {
            dpUnit = 0;
        } else {
            dpUnit = 0;
        }
        if (this.editor.getProps().overScrollEnabled) {
            dpUnit2 = (int) (this.editor.getDpUnit() * 20.0f);
        } else {
            dpUnit2 = 0;
        }
        editorScroller2.fling(currX2, currY2, i3, i4, 0, scrollMaxX2, 0, scrollMaxY2, dpUnit, dpUnit2);
        dpUnit3 = this.editor.getDpUnit() * 2000.0f;
        if (Math.abs(f3) < dpUnit3) {
            notifyScrolled();
        } else {
            notifyScrolled();
        }
        this.editor.releaseEdgeEffects();
        this.editor.dispatchEvent(new ScrollEvent(this.editor, this.scroller.getCurrX(), this.scroller.getCurrY(), this.scroller.getFinalX(), this.scroller.getFinalY(), 2));
        this.editor.postInvalidateOnAnimation();
        return false;
    }

    @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
    public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
        if (this.editor.isFormatting()) {
            return true;
        }
        if (!this.editor.isScalable()) {
            return false;
        }
        float textSizePx = this.editor.getTextSizePx() * scaleGestureDetector.getScaleFactor();
        if (textSizePx >= this.scaleMinSize && textSizePx <= this.scaleMaxSize) {
            float focusX = scaleGestureDetector.getFocusX();
            float focusY = scaleGestureDetector.getFocusY();
            int rowHeight = this.editor.getRowHeight();
            this.editor.setTextSizePxDirect(textSizePx);
            float currY = ((this.scroller.getCurrY() + focusY) * ((this.editor.getRowHeight() * 1.0f) / rowHeight)) - focusY;
            int iMax = (int) Math.max(0.0f, Math.min(((this.scroller.getCurrX() + focusX) * scaleGestureDetector.getScaleFactor()) - focusX, this.editor.getScrollMaxX()));
            int iMax2 = (int) Math.max(0.0f, Math.min(currY, this.editor.getScrollMaxY()));
            this.editor.dispatchEvent(new ScrollEvent(this.editor, this.scroller.getCurrX(), this.scroller.getCurrY(), iMax, iMax2, 5));
            this.scroller.startScroll(iMax, iMax2, 0, 0, 0);
            this.scroller.abortAnimation();
            this.isScaling = true;
            this.editor.invalidate();
        }
        return true;
    }

    @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
    public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
        this.scroller.forceFinished(true);
        this.textSizeStart = this.editor.getTextSizePx();
        return this.editor.isScalable() && !this.editor.isFormatting() && !this.holdingInsertHandle && this.touchedHandleType == -1;
    }

    @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
    public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
        this.isScaling = false;
        if (this.textSizeStart == this.editor.getTextSizePx()) {
            return;
        }
        this.editor.getRenderer().forcedRecreateLayout = true;
        if (this.editor.isWordwrap()) {
            this.focusY = scaleGestureDetector.getFocusY();
            this.memoryPosition = this.editor.getPointPositionOnScreen(scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY());
            this.positionNotApplied = true;
        } else {
            this.positionNotApplied = false;
        }
        this.editor.getRenderer().invalidateRenderNodes();
        this.editor.getRenderer().updateTimestamp();
        this.editor.invalidate();
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onDown(MotionEvent motionEvent) {
        return this.editor.isEnabled();
    }

    @Override // android.view.GestureDetector.OnDoubleTapListener
    public boolean onDoubleTap(MotionEvent motionEvent) {
        if (this.editor.isFormatting()) {
            return true;
        }
        long pointPositionOnScreen = this.editor.getPointPositionOnScreen(motionEvent.getX(), motionEvent.getY());
        int first = IntPair.getFirst(pointPositionOnScreen);
        int second = IntPair.getSecond(pointPositionOnScreen);
        if ((dispatchEditorMotionEvent(new Function5() { // from class: io.github.rosemoe.sora.widget.EditorTouchEventHandler$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function5
            public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                return new DoubleClickEvent((CodeEditor) obj, (CharPosition) obj2, (MotionEvent) obj3, (Span) obj4, (TextRange) obj5);
            }
        }, this.editor.getText().getIndexer().getCharPosition(first, second), motionEvent) & 2) == 0 && !this.editor.getCursor().isSelected() && motionEvent.getPointerCount() == 1) {
            this.editor.selectWord(first, second);
        }
        return true;
    }

    final class SelectionHandle {
        public static final int BOTH = 0;
        public static final int LEFT = 1;
        public static final int RIGHT = 2;
        public int type;

        public SelectionHandle(int i) {
            this.type = i;
        }

        private boolean checkNoIntersection(SelectionHandleStyle.HandleDescriptor handleDescriptor, SelectionHandleStyle.HandleDescriptor handleDescriptor2) {
            return !RectF.intersects(handleDescriptor.position, handleDescriptor2.position);
        }

        public void applyPosition(MotionEvent motionEvent) {
            SelectionHandleStyle.HandleDescriptor leftHandleDescriptor;
            int i = this.type;
            if (i == 1) {
                leftHandleDescriptor = EditorTouchEventHandler.this.editor.getLeftHandleDescriptor();
            } else if (i == 2) {
                leftHandleDescriptor = EditorTouchEventHandler.this.editor.getRightHandleDescriptor();
            } else {
                leftHandleDescriptor = EditorTouchEventHandler.this.editor.getInsertHandleDescriptor();
            }
            SelectionHandleStyle.HandleDescriptor rightHandleDescriptor = this.type == 1 ? EditorTouchEventHandler.this.editor.getRightHandleDescriptor() : EditorTouchEventHandler.this.editor.getLeftHandleDescriptor();
            float currX = EditorTouchEventHandler.this.scroller.getCurrX() + motionEvent.getX() + ((leftHandleDescriptor.alignment != 0 ? leftHandleDescriptor.position.width() : 0.0f) * (leftHandleDescriptor.alignment == 1 ? 1 : -1));
            float currY = (EditorTouchEventHandler.this.scroller.getCurrY() + motionEvent.getY()) - leftHandleDescriptor.position.height();
            int first = IntPair.getFirst(EditorTouchEventHandler.this.editor.getPointPosition(0.0f, currY));
            if (first < 0 || first >= EditorTouchEventHandler.this.editor.getLineCount()) {
                return;
            }
            int second = IntPair.getSecond(EditorTouchEventHandler.this.editor.getPointPosition(currX, currY));
            int rightLine = this.type == 2 ? EditorTouchEventHandler.this.editor.getCursor().getRightLine() : EditorTouchEventHandler.this.editor.getCursor().getLeftLine();
            int rightColumn = this.type == 2 ? EditorTouchEventHandler.this.editor.getCursor().getRightColumn() : EditorTouchEventHandler.this.editor.getCursor().getLeftColumn();
            int rightLine2 = this.type != 2 ? EditorTouchEventHandler.this.editor.getCursor().getRightLine() : EditorTouchEventHandler.this.editor.getCursor().getLeftLine();
            int rightColumn2 = this.type != 2 ? EditorTouchEventHandler.this.editor.getCursor().getRightColumn() : EditorTouchEventHandler.this.editor.getCursor().getLeftColumn();
            if (first == rightLine && second == rightColumn) {
                return;
            }
            int i2 = this.type;
            if (i2 != 0 && first == rightLine2 && second == rightColumn2) {
                return;
            }
            if (i2 == 0) {
                EditorTouchEventHandler.this.editor.cancelAnimation();
                EditorTouchEventHandler.this.editor.setSelection(first, second, false, 2);
                return;
            }
            if (i2 == 1) {
                if (rightLine2 < first || (rightLine2 == first && rightColumn2 < second)) {
                    if (checkNoIntersection(leftHandleDescriptor, rightHandleDescriptor)) {
                        EditorTouchEventHandler editorTouchEventHandler = EditorTouchEventHandler.this;
                        editorTouchEventHandler.dispatchHandleStateChange(editorTouchEventHandler.selHandleType, false);
                        EditorTouchEventHandler.this.selHandleType = 2;
                        EditorTouchEventHandler editorTouchEventHandler2 = EditorTouchEventHandler.this;
                        editorTouchEventHandler2.dispatchHandleStateChange(editorTouchEventHandler2.selHandleType, true);
                        this.type = 2;
                        EditorTouchEventHandler.this.rightHandle.type = 1;
                        SelectionHandle selectionHandle = EditorTouchEventHandler.this.rightHandle;
                        EditorTouchEventHandler editorTouchEventHandler3 = EditorTouchEventHandler.this;
                        editorTouchEventHandler3.rightHandle = editorTouchEventHandler3.leftHandle;
                        EditorTouchEventHandler.this.leftHandle = selectionHandle;
                        EditorTouchEventHandler.this.editor.setSelectionRegion(rightLine2, rightColumn2, first, second, false, 2);
                        return;
                    }
                    return;
                }
                EditorTouchEventHandler.this.editor.setSelectionRegion(first, second, rightLine2, rightColumn2, false, 2);
                return;
            }
            if (i2 != 2) {
                return;
            }
            if (rightLine2 > first || (rightLine2 == first && rightColumn2 > second)) {
                if (checkNoIntersection(leftHandleDescriptor, rightHandleDescriptor)) {
                    EditorTouchEventHandler editorTouchEventHandler4 = EditorTouchEventHandler.this;
                    editorTouchEventHandler4.dispatchHandleStateChange(editorTouchEventHandler4.selHandleType, false);
                    EditorTouchEventHandler.this.selHandleType = 1;
                    EditorTouchEventHandler editorTouchEventHandler5 = EditorTouchEventHandler.this;
                    editorTouchEventHandler5.dispatchHandleStateChange(editorTouchEventHandler5.selHandleType, true);
                    this.type = 1;
                    EditorTouchEventHandler.this.leftHandle.type = 2;
                    SelectionHandle selectionHandle2 = EditorTouchEventHandler.this.rightHandle;
                    EditorTouchEventHandler editorTouchEventHandler6 = EditorTouchEventHandler.this;
                    editorTouchEventHandler6.rightHandle = editorTouchEventHandler6.leftHandle;
                    EditorTouchEventHandler.this.leftHandle = selectionHandle2;
                    EditorTouchEventHandler.this.editor.setSelectionRegion(first, second, rightLine2, rightColumn2, false, 2);
                    return;
                }
                return;
            }
            EditorTouchEventHandler.this.editor.setSelectionRegion(rightLine2, rightColumn2, first, second, false, 2);
        }
    }

    private class EdgeScrollRunnable implements Runnable {
        private static final float INCREASE_FACTOR = 1.06f;
        private static final int MAX_FACTOR = 32;
        private float deltaHorizontal;
        private float deltaVertical;
        private float factorX;
        private float factorY;
        private final float initialDelta;
        private float lastDx;
        private float lastDy;
        private long postTimes = 0;

        public EdgeScrollRunnable(int i) {
            float f = i;
            this.deltaVertical = f;
            this.deltaHorizontal = f;
            this.initialDelta = f;
        }

        @Override // java.lang.Runnable
        public void run() {
            int leftLine;
            float f = ((EditorTouchEventHandler.this.edgeFlags & 1) != 0 ? -this.deltaHorizontal : 0.0f) + ((EditorTouchEventHandler.this.edgeFlags & 2) != 0 ? this.deltaHorizontal : 0.0f);
            float f2 = ((EditorTouchEventHandler.this.edgeFlags & 4) != 0 ? -this.deltaVertical : 0.0f) + ((EditorTouchEventHandler.this.edgeFlags & 8) != 0 ? this.deltaVertical : 0.0f);
            if (f > 0.0f) {
                if (EditorTouchEventHandler.this.holdingInsertHandle || EditorTouchEventHandler.this.selHandleType == 1) {
                    leftLine = EditorTouchEventHandler.this.editor.getCursor().getLeftLine();
                } else {
                    leftLine = EditorTouchEventHandler.this.editor.getCursor().getRightLine();
                }
                if (EditorTouchEventHandler.this.scroller.getCurrX() > (EditorTouchEventHandler.this.editor.measureTextRegionOffset() + EditorTouchEventHandler.this.editor.layout.getCharLayoutOffset(leftLine, EditorTouchEventHandler.this.editor.getText().getColumnCount(leftLine))[1]) - (EditorTouchEventHandler.this.editor.getWidth() * 0.85f)) {
                    f = 0.0f;
                }
            }
            EditorTouchEventHandler.this.scrollBy(f, f2);
            if (EditorTouchEventHandler.this.magnifier.isShowing()) {
                EditorTouchEventHandler.this.magnifier.dismiss();
            }
            if (EditorTouchEventHandler.this.isSameSign(f, this.lastDx)) {
                float f3 = this.factorX;
                if (f3 < 32.0f && (this.postTimes & 1) == 0) {
                    this.factorX = f3 + 1.0f;
                    this.deltaHorizontal *= INCREASE_FACTOR;
                }
            } else {
                this.deltaHorizontal = this.initialDelta;
                this.factorX = 0.0f;
            }
            if (EditorTouchEventHandler.this.isSameSign(f2, this.lastDy)) {
                float f4 = this.factorY;
                if (f4 < 32.0f && (this.postTimes & 1) == 0) {
                    this.factorY = f4 + 1.0f;
                    this.deltaVertical *= INCREASE_FACTOR;
                }
            } else {
                this.deltaVertical = this.initialDelta;
                this.factorY = 0.0f;
            }
            this.lastDx = f;
            this.lastDy = f2;
            if (EditorTouchEventHandler.this.thumbMotionRecord != null) {
                EditorTouchEventHandler editorTouchEventHandler = EditorTouchEventHandler.this;
                editorTouchEventHandler.handleSelectionChange2(editorTouchEventHandler.thumbMotionRecord);
            }
            this.postTimes++;
            if (EditorTouchEventHandler.this.edgeFlags != 0) {
                EditorTouchEventHandler.this.editor.postDelayedInLifecycle(this, 10L);
            }
        }
    }
}
