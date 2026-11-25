package org.jetbrains.kotlin.codegen.coroutines;

import com.reandroid.dex.sections.Marker;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.tree.VarInsnNode;
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue;

/* JADX INFO: compiled from: SpilledVariableFieldTypesAnalysis.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/codegen/coroutines/IloadedValue;", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicValue;", "insns", "", "Lorg/jetbrains/org/objectweb/asm/tree/VarInsnNode;", "(Ljava/util/Set;)V", "getInsns", "()Ljava/util/Set;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IloadedValue extends BasicValue {
    private final Set<VarInsnNode> insns;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public IloadedValue(Set<? extends VarInsnNode> insns) {
        super(Type.INT_TYPE);
        Intrinsics.checkNotNullParameter(insns, "insns");
        this.insns = insns;
    }

    public final Set<VarInsnNode> getInsns() {
        return this.insns;
    }
}
