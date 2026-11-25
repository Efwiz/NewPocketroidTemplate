package org.jetbrains.kotlin.codegen.coroutines;

import com.reandroid.dex.sections.Marker;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue;

/* JADX INFO: compiled from: RedundantLocalsEliminationMethodTransformer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0013\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006¢\u0006\u0002\u0010\u0007J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0096\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/codegen/coroutines/UnitValue;", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicValue;", "insn", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "(Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;)V", "insns", "", "(Ljava/util/Set;)V", "getInsns", "()Ljava/util/Set;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
final class UnitValue extends BasicValue {
    private final Set<AbstractInsnNode> insns;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public UnitValue(Set<? extends AbstractInsnNode> insns) {
        super(AsmTypes.OBJECT_TYPE);
        Intrinsics.checkNotNullParameter(insns, "insns");
        this.insns = insns;
    }

    public final Set<AbstractInsnNode> getInsns() {
        return this.insns;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public UnitValue(AbstractInsnNode insn) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        Set setSingleton = Collections.singleton(insn);
        Intrinsics.checkNotNullExpressionValue(setSingleton, "singleton(...)");
        this((Set<? extends AbstractInsnNode>) setSingleton);
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue
    public boolean equals(Object other) {
        return (other instanceof UnitValue) && Intrinsics.areEqual(this.insns, ((UnitValue) other).insns);
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue
    public int hashCode() {
        return Objects.hash(this.insns);
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue
    public String toString() {
        return "U";
    }
}
