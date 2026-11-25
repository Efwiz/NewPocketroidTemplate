package org.jetbrains.kotlin.com.intellij.openapi.editor.impl;

import org.jetbrains.kotlin.com.intellij.openapi.editor.event.DocumentEvent;
import org.jetbrains.kotlin.com.intellij.openapi.editor.impl.event.DocumentEventImpl;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.openapi.util.Segment;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.util.DocumentEventUtil;

/* JADX INFO: loaded from: classes3.dex */
public class ManualRangeMarker implements Segment {
    private final int myEnd;
    private final boolean myGreedyLeft;
    private final boolean myGreedyRight;
    private final PersistentRangeMarker.LinesCols myLinesCols;
    private final int myStart;
    private final boolean mySurviveOnExternalChange;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "event";
        } else {
            objArr[0] = "documentBefore";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/editor/impl/ManualRangeMarker";
        objArr[2] = "getUpdatedRange";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public ManualRangeMarker(int i, int i2, boolean z, boolean z2, boolean z3, PersistentRangeMarker.LinesCols linesCols) {
        this.myStart = i;
        this.myEnd = i2;
        this.myGreedyLeft = z;
        this.myGreedyRight = z2;
        this.mySurviveOnExternalChange = z3;
        this.myLinesCols = linesCols;
    }

    public ManualRangeMarker getUpdatedRange(DocumentEvent documentEvent, FrozenDocument frozenDocument) {
        int moveOffset;
        int offset = 0;
        if (documentEvent == null) {
            $$$reportNull$$$0(0);
        }
        if (frozenDocument == null) {
            $$$reportNull$$$0(1);
        }
        if (this.mySurviveOnExternalChange && PersistentRangeMarkerUtil.shouldTranslateViaDiff(documentEvent, this.myStart, this.myEnd)) {
            PersistentRangeMarker.LinesCols linesColsStoreLinesAndCols = this.myLinesCols;
            if (linesColsStoreLinesAndCols == null) {
                linesColsStoreLinesAndCols = PersistentRangeMarker.storeLinesAndCols(frozenDocument, this.myStart, this.myEnd);
            }
            Pair.NonNull<TextRange, PersistentRangeMarker.LinesCols> nonNullTranslateViaDiff = linesColsStoreLinesAndCols == null ? null : PersistentRangeMarker.translateViaDiff((DocumentEventImpl) documentEvent, linesColsStoreLinesAndCols);
            if (nonNullTranslateViaDiff != null) {
                return new ManualRangeMarker(nonNullTranslateViaDiff.first.getStartOffset(), nonNullTranslateViaDiff.first.getEndOffset(), this.myGreedyLeft, this.myGreedyRight, true, nonNullTranslateViaDiff.second);
            }
        }
        TextRange textRangeApplyChange = RangeMarkerImpl.applyChange(documentEvent, this.myStart, this.myEnd, this.myGreedyLeft, this.myGreedyRight, false);
        if (textRangeApplyChange == null) {
            return null;
        }
        if (DocumentEventUtil.isMoveInsertion(documentEvent) && (moveOffset = documentEvent.getMoveOffset()) <= textRangeApplyChange.getStartOffset() && textRangeApplyChange.getEndOffset() <= documentEvent.getNewLength() + moveOffset) {
            offset = documentEvent.getOffset() - moveOffset;
        }
        return new ManualRangeMarker(textRangeApplyChange.getStartOffset() + offset, textRangeApplyChange.getEndOffset() + offset, this.myGreedyLeft, this.myGreedyRight, this.mySurviveOnExternalChange, null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Segment
    public int getStartOffset() {
        return this.myStart;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Segment
    public int getEndOffset() {
        return this.myEnd;
    }

    public String toString() {
        return "ManualRangeMarker" + TextRange.create(this);
    }
}
