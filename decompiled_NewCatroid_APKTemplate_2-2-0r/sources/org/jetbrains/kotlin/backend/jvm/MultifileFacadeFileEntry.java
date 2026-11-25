package org.jetbrains.kotlin.backend.jvm;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrFileEntry;
import org.jetbrains.kotlin.ir.SourceRangeInfo;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;

/* JADX INFO: compiled from: MultifileFacades.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\u0010\u0010\u0016\u001a\u00020\t2\u0006\u0010\u0017\u001a\u00020\tH\u0016J\u0010\u0010\u0018\u001a\u00020\t2\u0006\u0010\u0017\u001a\u00020\tH\u0016J\u0018\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\t2\u0006\u0010\u001c\u001a\u00020\tH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/MultifileFacadeFileEntry;", "Lorg/jetbrains/kotlin/ir/IrFileEntry;", "className", "Lorg/jetbrains/kotlin/resolve/jvm/JvmClassName;", "partFiles", "", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "(Lorg/jetbrains/kotlin/resolve/jvm/JvmClassName;Ljava/util/List;)V", "maxOffset", "", "getMaxOffset", "()I", "name", "", "getName", "()Ljava/lang/String;", "getPartFiles", "()Ljava/util/List;", "supportsDebugInfo", "", "getSupportsDebugInfo", "()Z", "getColumnNumber", TypedValues.CycleType.S_WAVE_OFFSET, "getLineNumber", "getSourceRangeInfo", "Lorg/jetbrains/kotlin/ir/SourceRangeInfo;", "beginOffset", "endOffset", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MultifileFacadeFileEntry implements IrFileEntry {
    private final JvmClassName className;
    private final List<IrFile> partFiles;

    @Override // org.jetbrains.kotlin.ir.IrFileEntry
    public int getMaxOffset() {
        return -1;
    }

    @Override // org.jetbrains.kotlin.ir.IrFileEntry
    public boolean getSupportsDebugInfo() {
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public MultifileFacadeFileEntry(JvmClassName className, List<? extends IrFile> partFiles) {
        Intrinsics.checkNotNullParameter(className, "className");
        Intrinsics.checkNotNullParameter(partFiles, "partFiles");
        this.className = className;
        this.partFiles = partFiles;
    }

    public final List<IrFile> getPartFiles() {
        return this.partFiles;
    }

    @Override // org.jetbrains.kotlin.ir.IrFileEntry
    public String getName() {
        return "<multi-file facade " + this.className + '>';
    }

    @Override // org.jetbrains.kotlin.ir.IrFileEntry
    public SourceRangeInfo getSourceRangeInfo(int beginOffset, int endOffset) {
        throw new IllegalStateException(("Multifile facade doesn't support debug info: " + this.className).toString());
    }

    @Override // org.jetbrains.kotlin.ir.IrFileEntry
    public int getLineNumber(int offset) {
        throw new IllegalStateException(("Multifile facade doesn't support debug info: " + this.className).toString());
    }

    @Override // org.jetbrains.kotlin.ir.IrFileEntry
    public int getColumnNumber(int offset) {
        throw new IllegalStateException(("Multifile facade doesn't support debug info: " + this.className).toString());
    }
}
