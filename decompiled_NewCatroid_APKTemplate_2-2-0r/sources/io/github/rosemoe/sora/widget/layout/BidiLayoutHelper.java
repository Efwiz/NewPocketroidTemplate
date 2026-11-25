package io.github.rosemoe.sora.widget.layout;

import com.huawei.hms.ml.language.common.utils.Constant;
import io.github.rosemoe.sora.graphics.CharPosDesc;
import io.github.rosemoe.sora.graphics.GraphicTextRow;
import io.github.rosemoe.sora.text.Content;
import io.github.rosemoe.sora.text.ContentLine;
import io.github.rosemoe.sora.text.bidi.Directions;
import io.github.rosemoe.sora.widget.CodeEditor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: BidiLayoutHelper.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J>\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u000fJ>\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u0004¨\u0006\u0012"}, d2 = {"Lio/github/rosemoe/sora/widget/layout/BidiLayoutHelper;", "", "()V", "horizontalIndex", "", "editor", "Lio/github/rosemoe/sora/widget/CodeEditor;", "layout", "Lio/github/rosemoe/sora/widget/layout/AbstractLayout;", "text", "Lio/github/rosemoe/sora/text/Content;", Constant.LINE, "rowStart", "rowEnd", "targetOffset", "", "horizontalOffset", "targetColumn", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BidiLayoutHelper {
    public static final BidiLayoutHelper INSTANCE = new BidiLayoutHelper();

    private BidiLayoutHelper() {
    }

    public final float horizontalOffset(CodeEditor editor, AbstractLayout layout, Content text, int line, int rowStart, int rowEnd, int targetColumn) {
        float fMeasureText;
        Intrinsics.checkNotNullParameter(editor, "editor");
        Intrinsics.checkNotNullParameter(layout, "layout");
        Intrinsics.checkNotNullParameter(text, "text");
        Directions lineDirections = text.getLineDirections(line);
        Intrinsics.checkNotNullExpressionValue(lineDirections, "getLineDirections(...)");
        ContentLine line2 = text.getLine(line);
        GraphicTextRow graphicTextRowObtain = GraphicTextRow.obtain(editor.isBasicDisplayMode());
        graphicTextRowObtain.set(text, line, 0, line2.length(), editor.getTabWidth(), layout.getSpans(line), editor.getTextPaint());
        if (layout instanceof WordwrapLayout) {
            graphicTextRowObtain.setSoftBreaks(((WordwrapLayout) layout).getSoftBreaksForLine(line));
        }
        int iCoerceIn = RangesKt.coerceIn(targetColumn, rowStart, rowEnd);
        int runCount = lineDirections.getRunCount();
        float f = 0.0f;
        for (int i = 0; i < runCount; i++) {
            int iCoerceIn2 = RangesKt.coerceIn(lineDirections.getRunStart(i), rowStart, rowEnd);
            int iCoerceIn3 = RangesKt.coerceIn(lineDirections.getRunEnd(i), rowStart, rowEnd);
            if (iCoerceIn2 > iCoerceIn || iCoerceIn2 > iCoerceIn3) {
                break;
            }
            if (iCoerceIn3 < iCoerceIn) {
                fMeasureText = graphicTextRowObtain.measureText(iCoerceIn2, iCoerceIn3);
            } else if (lineDirections.isRunRtl(i)) {
                fMeasureText = graphicTextRowObtain.measureText(targetColumn, iCoerceIn3);
            } else {
                fMeasureText = graphicTextRowObtain.measureText(iCoerceIn2, iCoerceIn);
            }
            f += fMeasureText;
        }
        graphicTextRowObtain.recycle();
        return f;
    }

    public final int horizontalIndex(CodeEditor editor, AbstractLayout layout, Content text, int line, int rowStart, int rowEnd, float targetOffset) {
        int textOffset;
        Intrinsics.checkNotNullParameter(editor, "editor");
        Intrinsics.checkNotNullParameter(layout, "layout");
        Intrinsics.checkNotNullParameter(text, "text");
        Directions lineDirections = text.getLineDirections(line);
        Intrinsics.checkNotNullExpressionValue(lineDirections, "getLineDirections(...)");
        ContentLine line2 = text.getLine(line);
        GraphicTextRow graphicTextRowObtain = GraphicTextRow.obtain(editor.isBasicDisplayMode());
        graphicTextRowObtain.set(text, line, 0, line2.length(), editor.getTabWidth(), layout.getSpans(line), editor.getTextPaint());
        if (layout instanceof WordwrapLayout) {
            graphicTextRowObtain.setSoftBreaks(((WordwrapLayout) layout).getSoftBreaksForLine(line));
        }
        int runCount = lineDirections.getRunCount();
        float f = 0.0f;
        int i = 0;
        while (i < runCount) {
            int iCoerceIn = RangesKt.coerceIn(lineDirections.getRunStart(i), rowStart, rowEnd);
            int iCoerceIn2 = RangesKt.coerceIn(lineDirections.getRunEnd(i), rowStart, rowEnd);
            if (iCoerceIn2 != rowStart) {
                if (iCoerceIn == rowEnd) {
                    int i2 = i > 0 ? i - 1 : 0;
                    if (lineDirections.isRunRtl(i2)) {
                        return RangesKt.coerceIn(lineDirections.getRunStart(i2), rowStart, rowEnd);
                    }
                    return RangesKt.coerceIn(lineDirections.getRunEnd(i2), rowStart, rowEnd);
                }
                float fMeasureText = graphicTextRowObtain.measureText(iCoerceIn, iCoerceIn2) + f;
                if (fMeasureText >= targetOffset) {
                    if (lineDirections.isRunRtl(i)) {
                        textOffset = CharPosDesc.getTextOffset(graphicTextRowObtain.findOffsetByAdvance(iCoerceIn, fMeasureText - targetOffset));
                    } else {
                        textOffset = CharPosDesc.getTextOffset(graphicTextRowObtain.findOffsetByAdvance(iCoerceIn, targetOffset - f));
                    }
                    graphicTextRowObtain.recycle();
                    return textOffset;
                }
                f = fMeasureText;
            }
            i++;
        }
        graphicTextRowObtain.recycle();
        int runCount2 = lineDirections.getRunCount() - 1;
        if (lineDirections.isRunRtl(runCount2)) {
            return RangesKt.coerceIn(lineDirections.getRunStart(runCount2), rowStart, rowEnd);
        }
        return RangesKt.coerceIn(lineDirections.getRunEnd(runCount2), rowStart, rowEnd);
    }
}
