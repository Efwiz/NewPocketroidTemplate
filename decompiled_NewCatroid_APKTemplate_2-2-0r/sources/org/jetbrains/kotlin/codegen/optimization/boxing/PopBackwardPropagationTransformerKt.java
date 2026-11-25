package org.jetbrains.kotlin.codegen.optimization.boxing;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.common.UtilKt;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.FieldInsnNode;

/* JADX INFO: compiled from: PopBackwardPropagationTransformer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0003\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0004\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0005\u001a\u00020\u0001*\u00020\u0002*$\b\u0002\u0010\u0006\"\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\b0\u00072\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\b0\u0007¨\u0006\t"}, d2 = {"isPop", "", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "isPrimitiveTypeConversion", "isPurePush", "isUnitInstance", "Transformation", "Lkotlin/Function1;", "", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PopBackwardPropagationTransformerKt {
    public static final boolean isPurePush(AbstractInsnNode abstractInsnNode) {
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        if (UtilKt.isLoadOperation(abstractInsnNode)) {
            return true;
        }
        int opcode = abstractInsnNode.getOpcode();
        return (1 <= opcode && opcode < 21) || isUnitInstance(abstractInsnNode);
    }

    public static final boolean isPop(AbstractInsnNode abstractInsnNode) {
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        return abstractInsnNode.getOpcode() == 87 || abstractInsnNode.getOpcode() == 88;
    }

    public static final boolean isUnitInstance(AbstractInsnNode abstractInsnNode) {
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        if (abstractInsnNode.getOpcode() == 178 && (abstractInsnNode instanceof FieldInsnNode)) {
            FieldInsnNode fieldInsnNode = (FieldInsnNode) abstractInsnNode;
            if (Intrinsics.areEqual(fieldInsnNode.owner, "kotlin/Unit") && Intrinsics.areEqual(fieldInsnNode.f1010name, JvmAbi.INSTANCE_FIELD)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isPrimitiveTypeConversion(AbstractInsnNode abstractInsnNode) {
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        int opcode = abstractInsnNode.getOpcode();
        return 133 <= opcode && opcode < 148;
    }
}
