package io.github.rosemoe.sora.lang.styling.inlayHint;

import android.util.SparseBooleanArray;
import com.huawei.hms.ml.language.common.utils.Constant;
import io.github.rosemoe.sora.lang.analysis.StyleUpdateRange;
import kotlin.Metadata;
import kotlin.collections.IntIterator;

/* JADX INFO: compiled from: SparseUpdateRange.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\nH\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lio/github/rosemoe/sora/lang/styling/inlayHint/SparseUpdateRange;", "Lio/github/rosemoe/sora/lang/analysis/StyleUpdateRange;", "()V", "array", "Landroid/util/SparseBooleanArray;", "getArray", "()Landroid/util/SparseBooleanArray;", "addLine", "", Constant.LINE, "", "isInRange", "", "lineIndexIterator", "Lkotlin/collections/IntIterator;", "maxLineIndex", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SparseUpdateRange implements StyleUpdateRange {
    private final SparseBooleanArray array = new SparseBooleanArray();

    public final SparseBooleanArray getArray() {
        return this.array;
    }

    public final void addLine(int line) {
        this.array.put(line, true);
    }

    @Override // io.github.rosemoe.sora.lang.analysis.StyleUpdateRange
    public boolean isInRange(int line) {
        return this.array.get(line);
    }

    @Override // io.github.rosemoe.sora.lang.analysis.StyleUpdateRange
    public IntIterator lineIndexIterator(final int maxLineIndex) {
        return new IntIterator() { // from class: io.github.rosemoe.sora.lang.styling.inlayHint.SparseUpdateRange.lineIndexIterator.1
            private int index;

            public final int getIndex() {
                return this.index;
            }

            public final void setIndex(int i) {
                this.index = i;
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.index < SparseUpdateRange.this.getArray().size();
            }

            @Override // kotlin.collections.IntIterator
            public int nextInt() {
                int i = maxLineIndex;
                SparseBooleanArray array = SparseUpdateRange.this.getArray();
                int i2 = this.index;
                this.index = i2 + 1;
                return Integer.min(i, array.keyAt(i2));
            }
        };
    }
}
