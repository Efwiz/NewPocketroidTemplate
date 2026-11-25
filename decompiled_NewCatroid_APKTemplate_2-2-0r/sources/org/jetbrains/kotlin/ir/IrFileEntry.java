package org.jetbrains.kotlin.ir;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;

/* JADX INFO: compiled from: IrFileEntry.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0003H&J\u0010\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0003H&J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u0003H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/ir/IrFileEntry;", "", "maxOffset", "", "getMaxOffset", "()I", "name", "", "getName", "()Ljava/lang/String;", "supportsDebugInfo", "", "getSupportsDebugInfo", "()Z", "getColumnNumber", TypedValues.CycleType.S_WAVE_OFFSET, "getLineNumber", "getSourceRangeInfo", "Lorg/jetbrains/kotlin/ir/SourceRangeInfo;", "beginOffset", "endOffset", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrFileEntry {

    /* JADX INFO: compiled from: IrFileEntry.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static boolean getSupportsDebugInfo(IrFileEntry irFileEntry) {
            return true;
        }
    }

    int getColumnNumber(int offset);

    int getLineNumber(int offset);

    int getMaxOffset();

    String getName();

    SourceRangeInfo getSourceRangeInfo(int beginOffset, int endOffset);

    boolean getSupportsDebugInfo();
}
