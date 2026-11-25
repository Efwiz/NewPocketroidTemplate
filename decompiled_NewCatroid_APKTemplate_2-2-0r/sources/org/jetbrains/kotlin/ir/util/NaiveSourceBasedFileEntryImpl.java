package org.jetbrains.kotlin.ir.util;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.util.containers.SLRUCache;
import org.jetbrains.kotlin.ir.IrFileEntry;
import org.jetbrains.kotlin.ir.SourceRangeInfo;

/* JADX INFO: compiled from: AdditionalIrUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000;\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003*\u0001\u000b\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\u0017\u001a\u00020\u00072\u0006\u0010\u0018\u001a\u00020\u0007H\u0016J\u0010\u0010\u0019\u001a\u00020\u00072\u0006\u0010\u0018\u001a\u00020\u0007H\u0016J\u0018\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u00072\u0006\u0010\u001d\u001a\u00020\u0007H\u0016R\u000e\u0010\t\u001a\u00020\u0007X\u0082D¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u000f\u001a\u00020\u00108F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/NaiveSourceBasedFileEntryImpl;", "Lorg/jetbrains/kotlin/ir/IrFileEntry;", "name", "", "lineStartOffsets", "", "maxOffset", "", "(Ljava/lang/String;[II)V", "MAX_SAVED_LINE_NUMBERS", "calculatedBeforeLineNumbers", "org/jetbrains/kotlin/ir/util/NaiveSourceBasedFileEntryImpl$calculatedBeforeLineNumbers$1", "Lorg/jetbrains/kotlin/ir/util/NaiveSourceBasedFileEntryImpl$calculatedBeforeLineNumbers$1;", "lineNumberLock", "", "lineStartOffsetsAreEmpty", "", "getLineStartOffsetsAreEmpty", "()Z", "getMaxOffset", "()I", "getName", "()Ljava/lang/String;", "getColumnNumber", TypedValues.CycleType.S_WAVE_OFFSET, "getLineNumber", "getSourceRangeInfo", "Lorg/jetbrains/kotlin/ir/SourceRangeInfo;", "beginOffset", "endOffset", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NaiveSourceBasedFileEntryImpl implements IrFileEntry {
    private final int MAX_SAVED_LINE_NUMBERS;
    private final NaiveSourceBasedFileEntryImpl$calculatedBeforeLineNumbers$1 calculatedBeforeLineNumbers;
    private final Object lineNumberLock;
    private final int[] lineStartOffsets;
    private final int maxOffset;
    private final String name;

    /* JADX WARN: Type inference failed for: r4v1, types: [org.jetbrains.kotlin.ir.util.NaiveSourceBasedFileEntryImpl$calculatedBeforeLineNumbers$1] */
    public NaiveSourceBasedFileEntryImpl(String name2, int[] lineStartOffsets, int i) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(lineStartOffsets, "lineStartOffsets");
        this.name = name2;
        this.lineStartOffsets = lineStartOffsets;
        this.maxOffset = i;
        this.MAX_SAVED_LINE_NUMBERS = 50;
        final int i2 = 50 / 2;
        final int i3 = 50 / 2;
        this.calculatedBeforeLineNumbers = new SLRUCache<Integer, Integer>(i2, i3) { // from class: org.jetbrains.kotlin.ir.util.NaiveSourceBasedFileEntryImpl$calculatedBeforeLineNumbers$1
            @Override // org.jetbrains.kotlin.com.intellij.util.containers.SLRUCache
            public /* bridge */ /* synthetic */ Integer createValue(Integer num) {
                return createValue(num.intValue());
            }

            public Integer createValue(int key) {
                int iBinarySearch$default = ArraysKt.binarySearch$default(this.this$0.lineStartOffsets, key, 0, 0, 6, (Object) null);
                if (iBinarySearch$default < 0) {
                    iBinarySearch$default = (-iBinarySearch$default) - 2;
                }
                return Integer.valueOf(iBinarySearch$default);
            }
        };
        this.lineNumberLock = new Object();
    }

    public /* synthetic */ NaiveSourceBasedFileEntryImpl(String str, int[] iArr, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i2 & 2) != 0 ? new int[0] : iArr, (i2 & 4) != 0 ? -1 : i);
    }

    @Override // org.jetbrains.kotlin.ir.IrFileEntry
    public boolean getSupportsDebugInfo() {
        return IrFileEntry.DefaultImpls.getSupportsDebugInfo(this);
    }

    @Override // org.jetbrains.kotlin.ir.IrFileEntry
    public String getName() {
        return this.name;
    }

    @Override // org.jetbrains.kotlin.ir.IrFileEntry
    public int getMaxOffset() {
        return this.maxOffset;
    }

    public final boolean getLineStartOffsetsAreEmpty() {
        return this.lineStartOffsets.length == 0;
    }

    @Override // org.jetbrains.kotlin.ir.IrFileEntry
    public int getLineNumber(int offset) {
        Integer num;
        if (offset == -2) {
            return 0;
        }
        if (offset < 0) {
            return -1;
        }
        synchronized (this.lineNumberLock) {
            num = get(Integer.valueOf(offset));
        }
        Intrinsics.checkNotNullExpressionValue(num, "synchronized(...)");
        return num.intValue();
    }

    @Override // org.jetbrains.kotlin.ir.IrFileEntry
    public int getColumnNumber(int offset) {
        int lineNumber;
        if (offset == -2) {
            return 0;
        }
        if (offset >= 0 && (lineNumber = getLineNumber(offset)) >= 0) {
            return offset - this.lineStartOffsets[lineNumber];
        }
        return -1;
    }

    @Override // org.jetbrains.kotlin.ir.IrFileEntry
    public SourceRangeInfo getSourceRangeInfo(int beginOffset, int endOffset) {
        return new SourceRangeInfo(getName(), beginOffset, getLineNumber(beginOffset), getColumnNumber(beginOffset), endOffset, getLineNumber(endOffset), getColumnNumber(endOffset));
    }
}
