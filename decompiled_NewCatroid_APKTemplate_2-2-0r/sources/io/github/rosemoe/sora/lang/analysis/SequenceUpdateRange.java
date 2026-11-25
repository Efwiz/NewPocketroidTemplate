package io.github.rosemoe.sora.lang.analysis;

import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: SequenceUpdateRange.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0003H\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0003H\u0016R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u000f"}, d2 = {"Lio/github/rosemoe/sora/lang/analysis/SequenceUpdateRange;", "Lio/github/rosemoe/sora/lang/analysis/StyleUpdateRange;", "startLine", "", "endLine", "(II)V", "getEndLine", "()I", "getStartLine", "isInRange", "", Constant.LINE, "lineIndexIterator", "Lkotlin/collections/IntIterator;", "maxLineIndex", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SequenceUpdateRange implements StyleUpdateRange {
    private final int endLine;
    private final int startLine;

    public SequenceUpdateRange(int i, int i2) {
        this.startLine = i;
        this.endLine = i2;
    }

    public /* synthetic */ SequenceUpdateRange(int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i3 & 2) != 0 ? Integer.MAX_VALUE : i2);
    }

    public final int getEndLine() {
        return this.endLine;
    }

    public final int getStartLine() {
        return this.startLine;
    }

    @Override // io.github.rosemoe.sora.lang.analysis.StyleUpdateRange
    public boolean isInRange(int line) {
        return line <= this.endLine && this.startLine <= line;
    }

    @Override // io.github.rosemoe.sora.lang.analysis.StyleUpdateRange
    public IntIterator lineIndexIterator(final int maxLineIndex) {
        return new IntIterator() { // from class: io.github.rosemoe.sora.lang.analysis.SequenceUpdateRange.lineIndexIterator.1
            private int currentLine;

            {
                this.currentLine = SequenceUpdateRange.this.getStartLine();
            }

            public final int getCurrentLine() {
                return this.currentLine;
            }

            public final void setCurrentLine(int i) {
                this.currentLine = i;
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.currentLine <= Integer.min(SequenceUpdateRange.this.getEndLine(), maxLineIndex);
            }

            @Override // kotlin.collections.IntIterator
            public int nextInt() {
                int i = this.currentLine;
                this.currentLine = i + 1;
                return i;
            }
        };
    }
}
