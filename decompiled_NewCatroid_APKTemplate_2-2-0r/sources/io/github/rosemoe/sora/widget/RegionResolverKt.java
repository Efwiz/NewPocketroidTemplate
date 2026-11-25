package io.github.rosemoe.sora.widget;

import android.view.MotionEvent;
import io.github.rosemoe.sora.util.IntPair;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: RegionResolver.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0012\u0010\t\u001a\u00020\n*\u00020\u000b2\u0006\u0010\f\u001a\u00020\r\u001a\u001c\u0010\t\u001a\u00020\n*\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u0001\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0007\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"IN_BOUND", "", "OUT_BOUND", "REGION_DIVIDER", "REGION_DIVIDER_MARGIN", "REGION_LINE_NUMBER", "REGION_OUTBOUND", "REGION_SIDE_ICON", "REGION_TEXT", "resolveTouchRegion", "", "Lio/github/rosemoe/sora/widget/CodeEditor;", "event", "Landroid/view/MotionEvent;", "pointerIndex", "editor_release"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class RegionResolverKt {
    public static final int IN_BOUND = 0;
    public static final int OUT_BOUND = 1;
    public static final int REGION_DIVIDER = 4;
    public static final int REGION_DIVIDER_MARGIN = 3;
    public static final int REGION_LINE_NUMBER = 1;
    public static final int REGION_OUTBOUND = 0;
    public static final int REGION_SIDE_ICON = 2;
    public static final int REGION_TEXT = 5;

    public static final long resolveTouchRegion(CodeEditor codeEditor, MotionEvent event) {
        Intrinsics.checkNotNullParameter(codeEditor, "<this>");
        Intrinsics.checkNotNullParameter(event, "event");
        return resolveTouchRegion(codeEditor, event, -1);
    }

    public static /* synthetic */ long resolveTouchRegion$default(CodeEditor codeEditor, MotionEvent motionEvent, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = -1;
        }
        return resolveTouchRegion(codeEditor, motionEvent, i);
    }

    /* JADX WARN: Code duplicated, block: B:16:0x004d  */
    /* JADX WARN: Code duplicated, block: B:36:0x0099  */
    public static final long resolveTouchRegion(CodeEditor codeEditor, MotionEvent event, int i) {
        int i2;
        Intrinsics.checkNotNullParameter(codeEditor, "<this>");
        Intrinsics.checkNotNullParameter(event, "event");
        float x = (i == -1 ? event.getX() : event.getX(i)) + codeEditor.getOffsetX();
        float y = (i == -1 ? event.getY() : event.getY(i)) + codeEditor.getOffsetY();
        float fMeasureLineNumber = codeEditor.measureLineNumber();
        int rowHeight = codeEditor.getRenderer().hasSideHintIcons() ? codeEditor.getRowHeight() : 0;
        float fMeasureTextRegionOffset = codeEditor.measureTextRegionOffset();
        if (x < 0.0f) {
            i2 = 0;
        } else if (0.0f <= x && x <= fMeasureLineNumber) {
            i2 = 1;
        } else if (fMeasureLineNumber > x || x > rowHeight + fMeasureLineNumber) {
            float f = fMeasureLineNumber + rowHeight;
            if (x > codeEditor.getDividerMarginLeft() + f || f > x) {
                float dividerMarginLeft = codeEditor.getDividerMarginLeft() + f + codeEditor.getDividerWidth();
                if (x > codeEditor.getDividerMarginLeft() + f + codeEditor.getDividerMarginRight() + codeEditor.getDividerWidth() || dividerMarginLeft > x) {
                    float dividerMarginLeft2 = codeEditor.getDividerMarginLeft() + f;
                    if (x <= f + codeEditor.getDividerMarginLeft() + codeEditor.getDividerWidth() && dividerMarginLeft2 <= x) {
                        i2 = 4;
                    } else if ((fMeasureTextRegionOffset > x || x > codeEditor.getScrollMaxX() + codeEditor.getWidth()) && (!codeEditor.isWordwrap() || 0.0f > x || x > codeEditor.getWidth())) {
                        i2 = 0;
                    } else {
                        i2 = 5;
                    }
                } else {
                    i2 = 3;
                }
            } else {
                i2 = 3;
            }
        } else {
            i2 = 2;
        }
        return IntPair.pack(i2, (y < 0.0f || y > ((float) (codeEditor.getScrollMaxY() + (codeEditor.getHeight() / 2)))) ? 1 : 0);
    }
}
