package io.github.rosemoe.sora.widget.layout;

import android.util.SparseArray;
import io.github.rosemoe.sora.graphics.Paint;
import io.github.rosemoe.sora.graphics.SingleCharacterWidths;
import io.github.rosemoe.sora.text.Content;
import io.github.rosemoe.sora.text.ContentLine;
import io.github.rosemoe.sora.util.BlockIntList;
import io.github.rosemoe.sora.util.IntPair;
import io.github.rosemoe.sora.widget.CodeEditor;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes11.dex */
public class LineBreakLayout extends AbstractLayout {
    private SingleCharacterWidths measurer;
    private final AtomicInteger reuseCount;
    private BlockIntList widthMaintainer;

    @Override // io.github.rosemoe.sora.text.ContentListener
    public void beforeReplace(Content content) {
    }

    @Override // io.github.rosemoe.sora.widget.layout.Layout
    public int getRowCountForLine(int i) {
        return 1;
    }

    public LineBreakLayout(CodeEditor codeEditor, Content content) {
        super(codeEditor, content);
        this.reuseCount = new AtomicInteger(0);
        SingleCharacterWidths singleCharacterWidths = new SingleCharacterWidths(codeEditor.getTabWidth());
        this.measurer = singleCharacterWidths;
        singleCharacterWidths.setHandleFunctionCharacters(codeEditor.isRenderFunctionCharacters());
        BlockIntList blockIntList = new BlockIntList();
        this.widthMaintainer = blockIntList;
        measureAllLines(blockIntList);
    }

    private void measureAllLines(BlockIntList blockIntList) {
        if (this.text == null) {
            return;
        }
        Paint paint = new Paint(this.editor.isRenderFunctionCharacters());
        paint.set(this.editor.getTextPaint());
        paint.onAttributeUpdate();
        final int i = this.reuseCount.get();
        submitTask(new AnonymousClass1(new AbstractLayout.TaskMonitor(1, new AbstractLayout.TaskMonitor.Callback() { // from class: io.github.rosemoe.sora.widget.layout.LineBreakLayout$$ExternalSyntheticLambda1
            @Override // io.github.rosemoe.sora.widget.layout.AbstractLayout.TaskMonitor.Callback
            public final void onCompleted(Object[] objArr, int i2) {
                this.f$0.lambda$measureAllLines$1(i, objArr, i2);
            }
        }), blockIntList, this.measurer, paint, i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$measureAllLines$1(final int i, Object[] objArr, int i2) {
        final CodeEditor codeEditor = this.editor;
        if (codeEditor == null || i2 > 0) {
            return;
        }
        codeEditor.postInLifecycle(new Runnable() { // from class: io.github.rosemoe.sora.widget.layout.LineBreakLayout$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$measureAllLines$0(codeEditor, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$measureAllLines$0(CodeEditor codeEditor, int i) {
        if (this.editor == codeEditor && i == this.reuseCount.get()) {
            codeEditor.setLayoutBusy(false);
            codeEditor.getEventHandler().scrollBy(0.0f, 0.0f);
        }
    }

    /* JADX INFO: renamed from: io.github.rosemoe.sora.widget.layout.LineBreakLayout$1, reason: invalid class name */
    class AnonymousClass1 extends AbstractLayout.LayoutTask<Void> {
        final /* synthetic */ SingleCharacterWidths val$measurerLocal;
        final /* synthetic */ int val$reuseCountLocal;
        final /* synthetic */ Paint val$shadowPaint;
        final /* synthetic */ BlockIntList val$widthMaintainer;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(AbstractLayout.TaskMonitor taskMonitor, BlockIntList blockIntList, SingleCharacterWidths singleCharacterWidths, Paint paint, int i) {
            super(taskMonitor);
            this.val$widthMaintainer = blockIntList;
            this.val$measurerLocal = singleCharacterWidths;
            this.val$shadowPaint = paint;
            this.val$reuseCountLocal = i;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // io.github.rosemoe.sora.widget.layout.AbstractLayout.LayoutTask
        public Void compute() {
            this.val$widthMaintainer.lock.lock();
            try {
                LineBreakLayout.this.editor.setLayoutBusy(true);
                Content content = LineBreakLayout.this.text;
                int lineCount = LineBreakLayout.this.text.getLineCount() - 1;
                final SingleCharacterWidths singleCharacterWidths = this.val$measurerLocal;
                final Paint paint = this.val$shadowPaint;
                final BlockIntList blockIntList = this.val$widthMaintainer;
                content.runReadActionsOnLines(0, lineCount, new Content.ContentLineConsumer2() { // from class: io.github.rosemoe.sora.widget.layout.LineBreakLayout$1$$ExternalSyntheticLambda0
                    @Override // io.github.rosemoe.sora.text.Content.ContentLineConsumer2
                    public final void accept(int i, ContentLine contentLine, Content.ContentLineConsumer2.AbortFlag abortFlag) {
                        this.f$0.lambda$compute$0(singleCharacterWidths, paint, blockIntList, i, contentLine, abortFlag);
                    }
                });
                return null;
            } finally {
                this.val$widthMaintainer.lock.unlock();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$compute$0(SingleCharacterWidths singleCharacterWidths, Paint paint, BlockIntList blockIntList, int i, ContentLine contentLine, Content.ContentLineConsumer2.AbortFlag abortFlag) {
            int iMeasureText = (int) singleCharacterWidths.measureText(contentLine, 0, contentLine.length(), paint);
            if (shouldRun()) {
                blockIntList.add(iMeasureText);
            } else {
                abortFlag.set = true;
            }
        }

        @Override // io.github.rosemoe.sora.widget.layout.AbstractLayout.LayoutTask
        protected boolean shouldRun() {
            return super.shouldRun() && LineBreakLayout.this.reuseCount.get() == this.val$reuseCountLocal;
        }
    }

    private int measureLine(int i) {
        ContentLine line = this.text.getLine(i);
        return (int) this.measurer.measureText(line, 0, line.length(), this.editor.getTextPaint());
    }

    private int measureRegion(int i, int i2, int i3) {
        return (int) this.measurer.measureText(this.text.getLine(i), i2, i3, this.editor.getTextPaint());
    }

    @Override // io.github.rosemoe.sora.widget.layout.Layout
    public RowIterator obtainRowIterator(int i, SparseArray<ContentLine> sparseArray) {
        return new LineBreakLayoutRowItr(this.text, i, sparseArray);
    }

    @Override // io.github.rosemoe.sora.widget.layout.Layout
    public int getRowCount() {
        return this.text.getLineCount();
    }

    @Override // io.github.rosemoe.sora.widget.layout.AbstractLayout, io.github.rosemoe.sora.text.ContentListener
    public void afterInsert(Content content, int i, int i2, int i3, int i4, CharSequence charSequence) {
        super.afterInsert(content, i, i2, i3, i4, charSequence);
        for (int i5 = i; i5 <= i3; i5++) {
            if (i5 != i) {
                this.widthMaintainer.add(i5, measureLine(i5));
            } else if (i3 == i) {
                BlockIntList blockIntList = this.widthMaintainer;
                blockIntList.set(i5, blockIntList.get(i5) + measureRegion(i5, i2, i4));
            } else {
                this.widthMaintainer.set(i5, measureLine(i5));
            }
        }
    }

    @Override // io.github.rosemoe.sora.widget.layout.AbstractLayout, io.github.rosemoe.sora.text.ContentListener
    public void afterDelete(Content content, int i, int i2, int i3, int i4, CharSequence charSequence) {
        super.afterDelete(content, i, i2, i3, i4, charSequence);
        if (i < i3) {
            this.widthMaintainer.removeRange(i + 1, i3 + 1);
        }
        if (i == i3) {
            BlockIntList blockIntList = this.widthMaintainer;
            blockIntList.set(i, blockIntList.get(i) - ((int) this.measurer.measureText(charSequence, 0, i4 - i2, this.editor.getTextPaint())));
        } else {
            this.widthMaintainer.set(i, measureLine(i));
        }
    }

    @Override // io.github.rosemoe.sora.widget.layout.Layout
    public Row getRowAt(int i) {
        Row row = new Row();
        row.lineIndex = i;
        row.startColumn = 0;
        row.isLeadingRow = true;
        row.endColumn = this.text.getColumnCount(i);
        return row;
    }

    @Override // io.github.rosemoe.sora.widget.layout.Layout
    public int getRowIndexForPosition(int i) {
        return this.editor.getText().getIndexer().getCharPosition(i).line;
    }

    @Override // io.github.rosemoe.sora.widget.layout.AbstractLayout, io.github.rosemoe.sora.widget.layout.Layout
    public void destroyLayout() {
        super.destroyLayout();
        this.widthMaintainer = null;
    }

    @Override // io.github.rosemoe.sora.widget.layout.Layout
    public int getLineNumberForRow(int i) {
        return Math.max(0, Math.min(i, this.text.getLineCount() - 1));
    }

    @Override // io.github.rosemoe.sora.widget.layout.Layout
    public int getLayoutWidth() {
        if (this.widthMaintainer.size() == 0) {
            return 214748364;
        }
        return this.widthMaintainer.getMax();
    }

    @Override // io.github.rosemoe.sora.widget.layout.Layout
    public int getLayoutHeight() {
        return this.text.getLineCount() * this.editor.getRowHeight();
    }

    @Override // io.github.rosemoe.sora.widget.layout.Layout
    public long getCharPositionForLayoutOffset(float f, float f2) {
        int iMin = Math.min(this.text.getLineCount() - 1, Math.max((int) (f2 / this.editor.getRowHeight()), 0));
        return IntPair.pack(iMin, BidiLayout.horizontalIndex(this.editor, this, this.text, iMin, 0, this.text.getColumnCount(iMin), f));
    }

    @Override // io.github.rosemoe.sora.widget.layout.Layout
    public float[] getCharLayoutOffset(int i, int i2, float[] fArr) {
        if (fArr == null || fArr.length < 2) {
            fArr = new float[2];
        }
        fArr[0] = this.editor.getRowBottom(i);
        fArr[1] = BidiLayout.horizontalOffset(this.editor, this, this.text, i, 0, this.text.getColumnCount(i), i2);
        return fArr;
    }

    @Override // io.github.rosemoe.sora.widget.layout.Layout
    public long getDownPosition(int i, int i2) {
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

    @Override // io.github.rosemoe.sora.widget.layout.Layout
    public long getUpPosition(int i, int i2) {
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

    public void reuse(Content content) {
        this.text = content;
        this.reuseCount.getAndIncrement();
        SingleCharacterWidths singleCharacterWidths = new SingleCharacterWidths(this.editor.getTabWidth());
        this.measurer = singleCharacterWidths;
        singleCharacterWidths.setHandleFunctionCharacters(this.editor.isRenderFunctionCharacters());
        try {
            if (this.widthMaintainer.lock.tryLock(5L, TimeUnit.MILLISECONDS)) {
                this.widthMaintainer.lock.unlock();
                this.widthMaintainer.clear();
                measureAllLines(this.widthMaintainer);
            } else {
                BlockIntList blockIntList = new BlockIntList();
                this.widthMaintainer = blockIntList;
                measureAllLines(blockIntList);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Unable to wait for lock", e);
        }
    }

    static class LineBreakLayoutRowItr implements RowIterator {
        private int currentRow;
        private final int initRow;
        private final SparseArray<ContentLine> preloadedLines;
        private final Row result;
        private final Content text;

        LineBreakLayoutRowItr(Content content, int i, SparseArray<ContentLine> sparseArray) {
            this.currentRow = i;
            this.initRow = i;
            Row row = new Row();
            this.result = row;
            this.text = content;
            row.isLeadingRow = true;
            row.startColumn = 0;
            this.preloadedLines = sparseArray;
        }

        @Override // io.github.rosemoe.sora.widget.layout.RowIterator
        public Row next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.result.lineIndex = this.currentRow;
            SparseArray<ContentLine> sparseArray = this.preloadedLines;
            ContentLine line = sparseArray != null ? sparseArray.get(this.currentRow) : null;
            if (line == null) {
                line = this.text.getLine(this.currentRow);
            }
            this.result.endColumn = line.length();
            this.currentRow++;
            return this.result;
        }

        @Override // io.github.rosemoe.sora.widget.layout.RowIterator
        public boolean hasNext() {
            int i = this.currentRow;
            return i >= 0 && i < this.text.getLineCount();
        }

        @Override // io.github.rosemoe.sora.widget.layout.RowIterator
        public void reset() {
            this.currentRow = this.initRow;
        }
    }
}
