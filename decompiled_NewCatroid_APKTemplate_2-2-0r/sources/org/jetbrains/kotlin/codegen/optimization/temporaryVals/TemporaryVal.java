package org.jetbrains.kotlin.codegen.optimization.temporaryVals;

import com.reandroid.dex.sections.Marker;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.org.objectweb.asm.tree.VarInsnNode;

/* JADX INFO: compiled from: TemporaryVals.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\b\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/TemporaryVal;", "", "index", "", "storeInsn", "Lorg/jetbrains/org/objectweb/asm/tree/VarInsnNode;", "loadInsns", "", "(ILorg/jetbrains/org/objectweb/asm/tree/VarInsnNode;Ljava/util/List;)V", "getIndex", "()I", "getLoadInsns", "()Ljava/util/List;", "getStoreInsn", "()Lorg/jetbrains/org/objectweb/asm/tree/VarInsnNode;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TemporaryVal {
    private final int index;
    private final List<VarInsnNode> loadInsns;
    private final VarInsnNode storeInsn;

    /* JADX WARN: Multi-variable type inference failed */
    public TemporaryVal(int i, VarInsnNode storeInsn, List<? extends VarInsnNode> loadInsns) {
        Intrinsics.checkNotNullParameter(storeInsn, "storeInsn");
        Intrinsics.checkNotNullParameter(loadInsns, "loadInsns");
        this.index = i;
        this.storeInsn = storeInsn;
        this.loadInsns = loadInsns;
    }

    public final int getIndex() {
        return this.index;
    }

    public final VarInsnNode getStoreInsn() {
        return this.storeInsn;
    }

    public final List<VarInsnNode> getLoadInsns() {
        return this.loadInsns;
    }
}
