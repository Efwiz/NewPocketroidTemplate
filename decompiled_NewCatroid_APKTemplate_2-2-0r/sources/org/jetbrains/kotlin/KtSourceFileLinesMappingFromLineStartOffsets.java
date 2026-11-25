package org.jetbrains.kotlin;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: KtSourceFileLinesMapping.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0016\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001c\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u000e2\u0006\u0010\u000f\u001a\u00020\u0005H\u0016J\u0010\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0005H\u0016J\u0010\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u0005H\u0016R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\b¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/KtSourceFileLinesMappingFromLineStartOffsets;", "Lorg/jetbrains/kotlin/KtSourceFileLinesMapping;", "lineStartOffsets", "", "lastOffset", "", "([II)V", "getLastOffset", "()I", "getLineStartOffsets", "()[I", "linesCount", "getLinesCount", "getLineAndColumnByOffset", "Lkotlin/Pair;", TypedValues.CycleType.S_WAVE_OFFSET, "getLineByOffset", "getLineStartOffset", Constant.LINE, "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class KtSourceFileLinesMappingFromLineStartOffsets implements KtSourceFileLinesMapping {
    private final int lastOffset;
    private final int[] lineStartOffsets;

    public KtSourceFileLinesMappingFromLineStartOffsets(int[] lineStartOffsets, int i) {
        Intrinsics.checkNotNullParameter(lineStartOffsets, "lineStartOffsets");
        this.lineStartOffsets = lineStartOffsets;
        this.lastOffset = i;
    }

    @Override // org.jetbrains.kotlin.KtSourceFileLinesMapping
    public int getLastOffset() {
        return this.lastOffset;
    }

    public final int[] getLineStartOffsets() {
        return this.lineStartOffsets;
    }

    @Override // org.jetbrains.kotlin.KtSourceFileLinesMapping
    public int getLineStartOffset(int line) {
        return this.lineStartOffsets[line];
    }

    @Override // org.jetbrains.kotlin.KtSourceFileLinesMapping
    public Pair<Integer, Integer> getLineAndColumnByOffset(int offset) {
        int lineByOffset = getLineByOffset(offset);
        if (lineByOffset < 0) {
            return TuplesKt.to(-1, -1);
        }
        return TuplesKt.to(Integer.valueOf(lineByOffset), Integer.valueOf(offset - this.lineStartOffsets[lineByOffset]));
    }

    @Override // org.jetbrains.kotlin.KtSourceFileLinesMapping
    public int getLineByOffset(int offset) {
        int iBinarySearch$default = ArraysKt.binarySearch$default(this.lineStartOffsets, offset, 0, 0, 6, (Object) null);
        return iBinarySearch$default >= 0 ? iBinarySearch$default : (-iBinarySearch$default) - 2;
    }

    @Override // org.jetbrains.kotlin.KtSourceFileLinesMapping
    public int getLinesCount() {
        return this.lineStartOffsets.length;
    }
}
