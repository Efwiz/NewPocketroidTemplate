package io.github.rosemoe.sora.widget.layout;

import android.util.SparseArray;
import io.github.rosemoe.sora.graphics.CharPosDesc;
import io.github.rosemoe.sora.graphics.Paint;
import io.github.rosemoe.sora.text.CharPosition;
import io.github.rosemoe.sora.text.Content;
import io.github.rosemoe.sora.text.ContentLine;
import io.github.rosemoe.sora.util.IntPair;
import io.github.rosemoe.sora.util.MyCharacter;
import io.github.rosemoe.sora.widget.CodeEditor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes11.dex */
public class WordwrapLayout extends AbstractLayout {
    private final boolean antiWordBreaking;
    private List<RowRegion> rowTable;
    private final int width;

    @Override // io.github.rosemoe.sora.text.ContentListener
    public void beforeReplace(Content content) {
    }

    @Override // io.github.rosemoe.sora.widget.layout.Layout
    public int getLayoutWidth() {
        return 0;
    }

    public WordwrapLayout(CodeEditor codeEditor, Content content, boolean z, List<RowRegion> list, boolean z2) {
        super(codeEditor, content);
        this.antiWordBreaking = z;
        list = list == null ? new ArrayList<>() : list;
        this.rowTable = list;
        if (z2) {
            list.clear();
        }
        this.width = codeEditor.getWidth() - ((int) (codeEditor.measureTextRegionOffset() + codeEditor.getTextPaint().measureText("a")));
        breakAllLines();
    }

    public List<RowRegion> getRowTable() {
        return this.rowTable;
    }

    private void breakAllLines() {
        int iMin = Math.min(8, (int) Math.ceil(this.text.getLineCount() / 3000.0f));
        int lineCount = this.text.getLineCount() / iMin;
        AbstractLayout.TaskMonitor taskMonitor = new AbstractLayout.TaskMonitor(iMin, new AbstractLayout.TaskMonitor.Callback() { // from class: io.github.rosemoe.sora.widget.layout.WordwrapLayout$$ExternalSyntheticLambda1
            @Override // io.github.rosemoe.sora.widget.layout.AbstractLayout.TaskMonitor.Callback
            public final void onCompleted(Object[] objArr, int i) {
                this.f$0.lambda$breakAllLines$1(objArr, i);
            }
        });
        int i = 0;
        while (i < iMin) {
            int i2 = i + 1;
            submitTask(new WordwrapAnalyzeTask(taskMonitor, i, lineCount * i, (i2 == iMin ? this.text.getLineCount() : lineCount * i2) - 1));
            i = i2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$breakAllLines$1(Object[] objArr, int i) {
        final CodeEditor codeEditor = this.editor;
        if (codeEditor != null) {
            final ArrayList arrayList = new ArrayList();
            for (Object obj : objArr) {
                arrayList.add((WordwrapResult) obj);
            }
            Collections.sort(arrayList);
            codeEditor.postInLifecycle(new Runnable() { // from class: io.github.rosemoe.sora.widget.layout.WordwrapLayout$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$breakAllLines$0(codeEditor, arrayList);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$breakAllLines$0(CodeEditor codeEditor, List list) {
        if (this.editor != codeEditor) {
            return;
        }
        List<RowRegion> list2 = this.rowTable;
        if (list2 != null) {
            list2.clear();
        } else {
            this.rowTable = new ArrayList();
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            this.rowTable.addAll(((WordwrapResult) it.next()).regions);
        }
        codeEditor.setLayoutBusy(false);
        codeEditor.getEventHandler().scrollBy(0.0f, 0.0f);
    }

    private int findRow(int i) {
        int size = this.rowTable.size();
        int iMax = 0;
        while (iMax <= size) {
            int i2 = (iMax + size) / 2;
            if (i2 < 0 || i2 >= this.rowTable.size()) {
                iMax = Math.max(0, Math.min(this.rowTable.size() - 1, i2));
                break;
            }
            int i3 = this.rowTable.get(i2).line;
            if (i3 >= i) {
                if (i3 <= i) {
                    iMax = i2;
                    break;
                }
                size = i2 - 1;
            } else {
                iMax = i2 + 1;
            }
        }
        while (iMax > 0 && this.rowTable.get(iMax).startColumn > 0) {
            iMax--;
        }
        return iMax;
    }

    public int findRow(int i, int i2) {
        int i3;
        int iFindRow = findRow(i);
        while (this.rowTable.get(iFindRow).endColumn <= i2 && (i3 = iFindRow + 1) < this.rowTable.size() && this.rowTable.get(i3).line == i) {
            iFindRow = i3;
        }
        return iFindRow;
    }

    private void breakLines(int i, int i2) {
        int i3;
        int i4 = 0;
        while (i4 < this.rowTable.size() && this.rowTable.get(i4).line < i) {
            i4++;
        }
        while (i4 < this.rowTable.size() && (i3 = this.rowTable.get(i4).line) >= i && i3 <= i2) {
            this.rowTable.remove(i4);
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        while (i <= i2) {
            breakLine(i, this.text.getLine(i), arrayList, null);
            int i5 = -1;
            while (i5 < arrayList.size()) {
                int iIntValue = i5 == -1 ? 0 : arrayList.get(i5).intValue();
                i5++;
                arrayList2.add(new RowRegion(i, iIntValue, i5 < arrayList.size() ? arrayList.get(i5).intValue() : this.text.getColumnCount(i)));
            }
            arrayList.clear();
            i++;
        }
        this.rowTable.addAll(i4, arrayList2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:26:0x005f  */
    public void breakLine(int i, ContentLine contentLine, List<Integer> list, Paint paint) {
        int length = contentLine.length();
        char[] cArr = contentLine.value;
        int i2 = 0;
        while (i2 < length) {
            int textOffset = CharPosDesc.getTextOffset(this.editor.getRenderer().findFirstVisibleCharForWordwrap(this.width, i, i2, length, 0, paint == null ? this.editor.getTextPaint() : paint));
            if (textOffset == i2) {
                textOffset++;
            }
            if (this.antiWordBreaking) {
                int i3 = textOffset - 1;
                if (MyCharacter.isAlpha(cArr[i3]) && textOffset < length && (MyCharacter.isAlpha(cArr[textOffset]) || cArr[textOffset] == '-')) {
                    while (i3 > i2 && MyCharacter.isAlpha(cArr[i3 - 1])) {
                        i3--;
                    }
                    if (i3 > i2) {
                        i2 = i3;
                    } else {
                        i2 = textOffset;
                    }
                } else {
                    i2 = textOffset;
                }
            } else {
                i2 = textOffset;
            }
            list.add(Integer.valueOf(i2));
        }
        if (list.isEmpty() || list.get(list.size() - 1).intValue() != contentLine.length()) {
            return;
        }
        list.remove(list.size() - 1);
    }

    @Override // io.github.rosemoe.sora.widget.layout.AbstractLayout, io.github.rosemoe.sora.text.ContentListener
    public void afterInsert(Content content, int i, int i2, int i3, int i4, CharSequence charSequence) {
        super.afterInsert(content, i, i2, i3, i4, charSequence);
        int i5 = i3 - i;
        if (i5 != 0) {
            for (int iFindRow = findRow(i + 1); iFindRow < this.rowTable.size(); iFindRow++) {
                this.rowTable.get(iFindRow).line += i5;
            }
        }
        breakLines(i, i3);
    }

    @Override // io.github.rosemoe.sora.widget.layout.AbstractLayout, io.github.rosemoe.sora.text.ContentListener
    public void afterDelete(Content content, int i, int i2, int i3, int i4, CharSequence charSequence) {
        int i5;
        super.afterDelete(content, i, i2, i3, i4, charSequence);
        int i6 = i3 - i;
        if (i6 != 0) {
            int iFindRow = findRow(i);
            while (iFindRow < this.rowTable.size() && (i5 = this.rowTable.get(iFindRow).line) >= i && i5 <= i3) {
                this.rowTable.remove(iFindRow);
            }
            for (int iFindRow2 = findRow(i3 + 1); iFindRow2 < this.rowTable.size(); iFindRow2++) {
                RowRegion rowRegion = this.rowTable.get(iFindRow2);
                if (rowRegion.line >= i3) {
                    rowRegion.line -= i6;
                }
            }
        }
        breakLines(i, i);
    }

    @Override // io.github.rosemoe.sora.widget.layout.AbstractLayout, io.github.rosemoe.sora.widget.layout.Layout
    public void destroyLayout() {
        super.destroyLayout();
        this.rowTable = null;
    }

    @Override // io.github.rosemoe.sora.widget.layout.Layout
    public Row getRowAt(int i) {
        if (this.rowTable.isEmpty()) {
            Row row = new Row();
            row.startColumn = 0;
            row.endColumn = this.text.getColumnCount(i);
            row.isLeadingRow = true;
            row.lineIndex = i;
            return row;
        }
        return this.rowTable.get(i).toRow();
    }

    @Override // io.github.rosemoe.sora.widget.layout.Layout
    public int getLineNumberForRow(int i) {
        RowRegion rowRegion;
        if (this.rowTable.isEmpty()) {
            return Math.max(0, Math.min(i, this.text.getLineCount() - 1));
        }
        if (i >= this.rowTable.size()) {
            List<RowRegion> list = this.rowTable;
            rowRegion = list.get(list.size() - 1);
        } else {
            rowRegion = this.rowTable.get(i);
        }
        return rowRegion.line;
    }

    @Override // io.github.rosemoe.sora.widget.layout.Layout
    public RowIterator obtainRowIterator(int i, SparseArray<ContentLine> sparseArray) {
        return this.rowTable.isEmpty() ? new LineBreakLayout.LineBreakLayoutRowItr(this.text, i, sparseArray) : new WordwrapLayoutRowItr(i);
    }

    @Override // io.github.rosemoe.sora.widget.layout.Layout
    public long getUpPosition(int i, int i2) {
        if (this.rowTable.isEmpty()) {
            int i3 = i - 1;
            if (i3 < 0) {
                return IntPair.pack(0, 0);
            }
            int columnCount = this.text.getColumnCount(i3);
            if (i2 > columnCount) {
                i2 = columnCount;
            }
            return IntPair.pack(i3, i2);
        }
        int iFindRow = findRow(i, i2);
        if (iFindRow > 0) {
            int i4 = i2 - this.rowTable.get(iFindRow).startColumn;
            RowRegion rowRegion = this.rowTable.get(iFindRow - 1);
            return IntPair.pack(rowRegion.line, rowRegion.startColumn + Math.min(i4, rowRegion.endColumn - rowRegion.startColumn));
        }
        return IntPair.pack(0, 0);
    }

    @Override // io.github.rosemoe.sora.widget.layout.Layout
    public long getDownPosition(int i, int i2) {
        if (this.rowTable.isEmpty()) {
            int i3 = i + 1;
            if (i3 >= this.text.getLineCount()) {
                return IntPair.pack(i, this.text.getColumnCount(i));
            }
            int columnCount = this.text.getColumnCount(i3);
            if (i2 > columnCount) {
                i2 = columnCount;
            }
            return IntPair.pack(i3, i2);
        }
        int iFindRow = findRow(i, i2);
        int i4 = iFindRow + 1;
        if (i4 < this.rowTable.size()) {
            int i5 = i2 - this.rowTable.get(iFindRow).startColumn;
            RowRegion rowRegion = this.rowTable.get(i4);
            return IntPair.pack(rowRegion.line, rowRegion.startColumn + Math.min(i5, rowRegion.endColumn - rowRegion.startColumn));
        }
        return IntPair.pack(i, this.text.getColumnCount(i));
    }

    @Override // io.github.rosemoe.sora.widget.layout.Layout
    public int getLayoutHeight() {
        int size;
        int rowHeight;
        if (this.rowTable.isEmpty()) {
            size = this.editor.getRowHeight();
            rowHeight = this.text.getLineCount();
        } else {
            size = this.rowTable.size();
            rowHeight = this.editor.getRowHeight();
        }
        return size * rowHeight;
    }

    @Override // io.github.rosemoe.sora.widget.layout.Layout
    public int getRowIndexForPosition(int i) {
        int i2;
        CharPosition charPosition = this.editor.getText().getIndexer().getCharPosition(i);
        int i3 = charPosition.line;
        if (this.rowTable.isEmpty()) {
            return i3;
        }
        int i4 = charPosition.column;
        int iFindRow = findRow(i3);
        if (iFindRow >= this.rowTable.size()) {
            return 0;
        }
        RowRegion rowRegion = this.rowTable.get(iFindRow);
        if (rowRegion.line != i3) {
            return 0;
        }
        while (rowRegion.startColumn < i4 && (i2 = iFindRow + 1) < this.rowTable.size()) {
            RowRegion rowRegion2 = this.rowTable.get(i2);
            if (rowRegion2.line != i3 || rowRegion2.startColumn > i4) {
                break;
            }
            iFindRow = i2;
            rowRegion = rowRegion2;
        }
        return iFindRow;
    }

    @Override // io.github.rosemoe.sora.widget.layout.Layout
    public long getCharPositionForLayoutOffset(float f, float f2) {
        if (this.rowTable.isEmpty()) {
            int iMin = Math.min(this.text.getLineCount() - 1, Math.max((int) (f2 / this.editor.getRowHeight()), 0));
            return IntPair.pack(iMin, BidiLayout.horizontalIndex(this.editor, this, this.text, iMin, 0, this.text.getColumnCount(iMin), f));
        }
        RowRegion rowRegion = this.rowTable.get(Math.max(0, Math.min((int) (f2 / this.editor.getRowHeight()), this.rowTable.size() - 1)));
        return IntPair.pack(rowRegion.line, BidiLayout.horizontalIndex(this.editor, this, this.text, rowRegion.line, rowRegion.startColumn, rowRegion.endColumn, f));
    }

    @Override // io.github.rosemoe.sora.widget.layout.Layout
    public float[] getCharLayoutOffset(int i, int i2, float[] fArr) {
        int i3;
        if (fArr == null || fArr.length < 2) {
            fArr = new float[2];
        }
        if (this.rowTable.isEmpty()) {
            fArr[0] = this.editor.getRowBottom(i);
            fArr[1] = BidiLayout.horizontalOffset(this.editor, this, this.text, i, 0, this.text.getColumnCount(i), i2);
            return fArr;
        }
        int iFindRow = findRow(i);
        if (iFindRow < this.rowTable.size()) {
            RowRegion rowRegion = this.rowTable.get(iFindRow);
            if (rowRegion.line != i) {
                fArr[1] = 0.0f;
                fArr[0] = 0.0f;
                return fArr;
            }
            while (rowRegion.startColumn < i2 && (i3 = iFindRow + 1) < this.rowTable.size()) {
                rowRegion = this.rowTable.get(i3);
                if (rowRegion.line != i || rowRegion.startColumn > i2) {
                    rowRegion = this.rowTable.get(iFindRow);
                    break;
                }
                iFindRow = i3;
            }
            fArr[0] = this.editor.getRowBottom(iFindRow);
            fArr[1] = BidiLayout.horizontalOffset(this.editor, this, this.text, rowRegion.line, rowRegion.startColumn, rowRegion.endColumn, i2);
        } else {
            fArr[1] = 0.0f;
            fArr[0] = 0.0f;
        }
        return fArr;
    }

    @Override // io.github.rosemoe.sora.widget.layout.Layout
    public int getRowCountForLine(int i) {
        if (this.rowTable.isEmpty()) {
            return 1;
        }
        int i2 = 0;
        for (int iFindRow = findRow(i); iFindRow < this.rowTable.size() && this.rowTable.get(iFindRow).line == i; iFindRow++) {
            i2++;
        }
        return i2;
    }

    public List<Integer> getSoftBreaksForLine(int i) {
        if (this.rowTable.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (int iFindRow = findRow(i); iFindRow < this.rowTable.size() && this.rowTable.get(iFindRow).line == i; iFindRow++) {
            int i2 = this.rowTable.get(iFindRow).startColumn;
            if (i2 != 0) {
                arrayList.add(Integer.valueOf(i2));
            }
        }
        return arrayList;
    }

    @Override // io.github.rosemoe.sora.widget.layout.Layout
    public int getRowCount() {
        if (this.rowTable.isEmpty()) {
            return this.text.getLineCount();
        }
        return this.rowTable.size();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static class RowRegion {
        final int endColumn;
        int line;
        final int startColumn;

        RowRegion(int i, int i2, int i3) {
            this.line = i;
            this.startColumn = i2;
            this.endColumn = i3;
        }

        public Row toRow() {
            Row row = new Row();
            row.isLeadingRow = this.startColumn == 0;
            row.startColumn = this.startColumn;
            row.endColumn = this.endColumn;
            row.lineIndex = this.line;
            return row;
        }

        public String toString() {
            return "RowRegion{startColumn=" + this.startColumn + ", endColumn=" + this.endColumn + ", line=" + this.line + '}';
        }
    }

    private static class WordwrapResult implements Comparable<WordwrapResult> {
        int index;
        List<RowRegion> regions;

        public WordwrapResult(int i, List<RowRegion> list) {
            this.index = i;
            this.regions = list;
        }

        @Override // java.lang.Comparable
        public int compareTo(WordwrapResult wordwrapResult) {
            return Integer.compare(this.index, wordwrapResult.index);
        }
    }

    class WordwrapLayoutRowItr implements RowIterator {
        private int currentRow;
        private final int initRow;
        private final Row result = new Row();

        WordwrapLayoutRowItr(int i) {
            this.currentRow = i;
            this.initRow = i;
        }

        @Override // io.github.rosemoe.sora.widget.layout.RowIterator
        public Row next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            RowRegion rowRegion = (RowRegion) WordwrapLayout.this.rowTable.get(this.currentRow);
            this.result.lineIndex = rowRegion.line;
            this.result.startColumn = rowRegion.startColumn;
            this.result.endColumn = rowRegion.endColumn;
            this.result.isLeadingRow = this.currentRow <= 0 || ((RowRegion) WordwrapLayout.this.rowTable.get(this.currentRow - 1)).line != rowRegion.line;
            this.currentRow++;
            return this.result;
        }

        @Override // io.github.rosemoe.sora.widget.layout.RowIterator
        public boolean hasNext() {
            int i = this.currentRow;
            return i >= 0 && i < WordwrapLayout.this.rowTable.size();
        }

        @Override // io.github.rosemoe.sora.widget.layout.RowIterator
        public void reset() {
            this.currentRow = this.initRow;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class WordwrapAnalyzeTask extends AbstractLayout.LayoutTask<WordwrapResult> {
        private final int end;

        /* JADX INFO: renamed from: id, reason: collision with root package name */
        private final int f274id;
        private final Paint paint;
        private final int start;

        WordwrapAnalyzeTask(AbstractLayout.TaskMonitor taskMonitor, int i, int i2, int i3) {
            super(taskMonitor);
            this.start = i2;
            this.f274id = i;
            this.end = i3;
            Paint paint = new Paint(WordwrapLayout.this.editor.isRenderFunctionCharacters());
            this.paint = paint;
            paint.set(WordwrapLayout.this.editor.getTextPaint());
            paint.onAttributeUpdate();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // io.github.rosemoe.sora.widget.layout.AbstractLayout.LayoutTask
        public WordwrapResult compute() {
            WordwrapLayout.this.editor.setLayoutBusy(true);
            final ArrayList arrayList = new ArrayList();
            final ArrayList arrayList2 = new ArrayList();
            WordwrapLayout.this.text.runReadActionsOnLines(this.start, this.end, new Content.ContentLineConsumer2() { // from class: io.github.rosemoe.sora.widget.layout.WordwrapLayout$WordwrapAnalyzeTask$$ExternalSyntheticLambda0
                @Override // io.github.rosemoe.sora.text.Content.ContentLineConsumer2
                public final void accept(int i, ContentLine contentLine, Content.ContentLineConsumer2.AbortFlag abortFlag) {
                    this.f$0.lambda$compute$0(arrayList2, arrayList, i, contentLine, abortFlag);
                }
            });
            return new WordwrapResult(this.f274id, arrayList);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$compute$0(ArrayList arrayList, ArrayList arrayList2, int i, ContentLine contentLine, Content.ContentLineConsumer2.AbortFlag abortFlag) {
            WordwrapLayout.this.breakLine(i, contentLine, arrayList, this.paint);
            int i2 = -1;
            while (i2 < arrayList.size()) {
                int iIntValue = i2 == -1 ? 0 : ((Integer) arrayList.get(i2)).intValue();
                i2++;
                arrayList2.add(new RowRegion(i, iIntValue, i2 < arrayList.size() ? ((Integer) arrayList.get(i2)).intValue() : contentLine.length()));
            }
            if (!shouldRun()) {
                abortFlag.set = true;
            }
            arrayList.clear();
        }
    }
}
