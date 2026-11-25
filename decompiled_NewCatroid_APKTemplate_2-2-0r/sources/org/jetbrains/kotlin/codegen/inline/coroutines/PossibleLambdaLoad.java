package org.jetbrains.kotlin.codegen.inline.coroutines;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue;

/* JADX INFO: compiled from: CoroutineTransformer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/coroutines/PossibleLambdaLoad;", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicValue;", "insn", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "(Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;)V", "getInsn", "()Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
final class PossibleLambdaLoad extends BasicValue {
    private final AbstractInsnNode insn;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PossibleLambdaLoad(AbstractInsnNode insn) {
        super(AsmTypes.OBJECT_TYPE);
        Intrinsics.checkNotNullParameter(insn, "insn");
        this.insn = insn;
    }

    public final AbstractInsnNode getInsn() {
        return this.insn;
    }
}
