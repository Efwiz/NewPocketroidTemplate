package org.jetbrains.kotlin.com.intellij.openapi.editor.impl;

import com.beust.jcommander.Parameters;
import com.huawei.hms.ml.language.common.utils.Constant;
import org.jetbrains.kotlin.com.intellij.openapi.application.ReadAction;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.editor.event.DocumentEvent;
import org.jetbrains.kotlin.com.intellij.openapi.editor.impl.event.DocumentEventImpl;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.openapi.util.Segment;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable;
import org.jetbrains.kotlin.com.intellij.util.diff.FilesTooBigForDiffException;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: loaded from: classes3.dex */
public class PersistentRangeMarker extends RangeMarkerImpl {
    private volatile boolean documentLoaded;
    private LinesCols myLinesCols;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
                objArr[0] = "virtualFile";
                break;
            case 2:
                objArr[0] = "myDocument";
                break;
            case 3:
            default:
                objArr[0] = "document";
                break;
            case 4:
            case 7:
                objArr[0] = "event";
                break;
            case 5:
            case 9:
                objArr[0] = "linesCols";
                break;
            case 6:
                objArr[0] = "e";
                break;
            case 8:
                objArr[0] = "range";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/editor/impl/PersistentRangeMarker";
        switch (i) {
            case 2:
                objArr[2] = "storeLinesAndCols";
                break;
            case 3:
                objArr[2] = "calcLineCol";
                break;
            case 4:
            case 5:
                objArr[2] = "translateViaDiff";
                break;
            case 6:
                objArr[2] = "changedUpdateImpl";
                break;
            case 7:
            case 8:
            case 9:
                objArr[2] = "applyChange";
                break;
            case 10:
                objArr[2] = "reRegister";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    static LinesCols storeLinesAndCols(Document document, int i, int i2) {
        if (document == null) {
            $$$reportNull$$$0(2);
        }
        LineCol lineColCalcLineCol = calcLineCol(document, i);
        LineCol lineColCalcLineCol2 = calcLineCol(document, i2);
        if (lineColCalcLineCol == null || lineColCalcLineCol2 == null) {
            return null;
        }
        return new LinesCols(lineColCalcLineCol.line, lineColCalcLineCol.col, lineColCalcLineCol2.line, lineColCalcLineCol2.col);
    }

    private static LineCol calcLineCol(Document document, int i) {
        if (document == null) {
            $$$reportNull$$$0(3);
        }
        if (i > document.getTextLength()) {
            return null;
        }
        int lineNumber = document.getLineNumber(i);
        int lineStartOffset = i - document.getLineStartOffset(lineNumber);
        if (lineStartOffset < 0) {
            return null;
        }
        return new LineCol(lineNumber, lineStartOffset);
    }

    private static class LineCol {
        private final int col;
        private final int line;

        LineCol(int i, int i2) {
            this.line = i;
            this.col = i2;
        }
    }

    static Pair.NonNull<TextRange, LinesCols> translateViaDiff(DocumentEventImpl documentEventImpl, LinesCols linesCols) {
        int lineStartOffset;
        int iTranslateLineViaDiffStrict;
        int lineStartOffset2;
        if (documentEventImpl == null) {
            $$$reportNull$$$0(4);
        }
        if (linesCols == null) {
            $$$reportNull$$$0(5);
        }
        try {
            int iTranslateLineViaDiffStrict2 = documentEventImpl.translateLineViaDiffStrict(linesCols.myStartLine);
            Document document = documentEventImpl.getDocument();
            if (iTranslateLineViaDiffStrict2 >= 0 && iTranslateLineViaDiffStrict2 < document.getLineCount() && (lineStartOffset = document.getLineStartOffset(iTranslateLineViaDiffStrict2) + linesCols.myStartColumn) < document.getTextLength() && (iTranslateLineViaDiffStrict = documentEventImpl.translateLineViaDiffStrict(linesCols.myEndLine)) >= 0 && iTranslateLineViaDiffStrict < document.getLineCount() && (lineStartOffset2 = document.getLineStartOffset(iTranslateLineViaDiffStrict) + linesCols.myEndColumn) <= document.getTextLength() && lineStartOffset2 >= lineStartOffset && lineStartOffset2 <= documentEventImpl.getDocument().getTextLength() && iTranslateLineViaDiffStrict >= iTranslateLineViaDiffStrict2 && ((iTranslateLineViaDiffStrict2 != iTranslateLineViaDiffStrict || linesCols.myEndColumn >= linesCols.myStartColumn) && documentEventImpl.getDocument().getLineCount() >= iTranslateLineViaDiffStrict)) {
                return Pair.createNonNull(new TextRange(lineStartOffset, lineStartOffset2), new LinesCols(iTranslateLineViaDiffStrict2, linesCols.myStartColumn, iTranslateLineViaDiffStrict, linesCols.myEndColumn));
            }
        } catch (FilesTooBigForDiffException unused) {
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.impl.RangeMarkerImpl
    protected void changedUpdateImpl(DocumentEvent documentEvent) {
        if (documentEvent == null) {
            $$$reportNull$$$0(6);
        }
        if (isValid()) {
            Pair.NonNull<TextRange, LinesCols> nonNullApplyChange = applyChange(documentEvent, this, intervalStart(), intervalEnd(), isGreedyToLeft(), isGreedyToRight(), isStickingToRight(), this.myLinesCols);
            if (nonNullApplyChange == null) {
                invalidate(documentEvent);
                return;
            }
            setIntervalStart(nonNullApplyChange.first.getStartOffset());
            setIntervalEnd(nonNullApplyChange.first.getEndOffset());
            this.myLinesCols = nonNullApplyChange.second;
        }
    }

    private static Pair.NonNull<TextRange, LinesCols> applyChange(DocumentEvent documentEvent, Segment segment, int i, int i2, boolean z, boolean z2, boolean z3, LinesCols linesCols) {
        LinesCols linesColsStoreLinesAndCols;
        if (documentEvent == null) {
            $$$reportNull$$$0(7);
        }
        if (segment == null) {
            $$$reportNull$$$0(8);
        }
        if (linesCols == null) {
            $$$reportNull$$$0(9);
        }
        Pair.NonNull<TextRange, LinesCols> nonNullTranslateViaDiff = PersistentRangeMarkerUtil.shouldTranslateViaDiff(documentEvent, segment.getStartOffset(), segment.getEndOffset()) ? translateViaDiff((DocumentEventImpl) documentEvent, linesCols) : null;
        if (nonNullTranslateViaDiff != null) {
            return nonNullTranslateViaDiff;
        }
        TextRange textRangeApplyChange = applyChange(documentEvent, i, i2, z, z2, z3);
        if (textRangeApplyChange == null || (linesColsStoreLinesAndCols = storeLinesAndCols(documentEvent.getDocument(), textRangeApplyChange.getStartOffset(), textRangeApplyChange.getEndOffset())) == null) {
            return null;
        }
        return Pair.createNonNull(textRangeApplyChange, linesColsStoreLinesAndCols);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.impl.RangeMarkerImpl, java.util.concurrent.atomic.AtomicReference
    public String toString() {
        return "PersistentRangeMarker" + (isGreedyToLeft() ? "[" : "(") + (isValid() ? "valid" : "invalid") + "," + getStartOffset() + "," + getEndOffset() + " " + this.myLinesCols + (isGreedyToRight() ? "]" : Constant.AFTER_QUTO);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final class LinesCols {
        private final int myEndColumn;
        private final int myEndLine;
        private final int myStartColumn;
        private final int myStartLine;

        private LinesCols(int i, int i2, int i3, int i4) {
            this.myStartLine = i;
            this.myStartColumn = i2;
            this.myEndLine = i3;
            this.myEndColumn = i4;
        }

        public String toString() {
            return this.myStartLine + ":" + this.myStartColumn + Parameters.DEFAULT_OPTION_PREFIXES + this.myEndLine + ":" + this.myEndColumn;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.impl.RangeMarkerImpl, org.jetbrains.kotlin.com.intellij.openapi.editor.RangeMarker, org.jetbrains.kotlin.com.intellij.openapi.util.Segment
    public int getStartOffset() throws Throwable {
        if (!isDocumentLoaded()) {
            ReadAction.run(new ThrowableRunnable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.editor.impl.PersistentRangeMarker$$ExternalSyntheticLambda1
                @Override // org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable
                public final void run() {
                    this.f$0.m6476xa259c63c();
                }
            });
        }
        return super.getStartOffset();
    }

    /* JADX INFO: renamed from: lambda$getStartOffset$0$org-jetbrains-kotlin-com-intellij-openapi-editor-impl-PersistentRangeMarker, reason: not valid java name */
    /* synthetic */ void m6476xa259c63c() throws RuntimeException {
        getDocument();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.impl.RangeMarkerImpl, org.jetbrains.kotlin.com.intellij.openapi.editor.RangeMarker, org.jetbrains.kotlin.com.intellij.openapi.util.Segment
    public int getEndOffset() throws Throwable {
        if (!isDocumentLoaded()) {
            ReadAction.run(new ThrowableRunnable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.editor.impl.PersistentRangeMarker$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable
                public final void run() {
                    this.f$0.m6475x5c0e6076();
                }
            });
        }
        return super.getEndOffset();
    }

    /* JADX INFO: renamed from: lambda$getEndOffset$1$org-jetbrains-kotlin-com-intellij-openapi-editor-impl-PersistentRangeMarker, reason: not valid java name */
    /* synthetic */ void m6475x5c0e6076() throws RuntimeException {
        getDocument();
    }

    private boolean isDocumentLoaded() {
        return this.documentLoaded;
    }
}
