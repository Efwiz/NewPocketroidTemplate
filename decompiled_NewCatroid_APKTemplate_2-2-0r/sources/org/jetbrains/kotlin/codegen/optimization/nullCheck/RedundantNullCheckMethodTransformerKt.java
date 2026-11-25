package org.jetbrains.kotlin.codegen.optimization.nullCheck;

import com.reandroid.dex.sections.Marker;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.InsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.VarInsnNode;

/* JADX INFO: compiled from: RedundantNullCheckMethodTransformer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00002\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\u001a\f\u0010\u0005\u001a\u00020\u0006*\u00020\u0007H\u0000\u001a\f\u0010\b\u001a\u00020\u0006*\u00020\u0007H\u0000\u001a\f\u0010\t\u001a\u00020\u0006*\u00020\u0007H\u0000\u001a\f\u0010\n\u001a\u00020\u0006*\u00020\u0007H\u0000\u001a\f\u0010\u000b\u001a\u00020\u0006*\u00020\u0007H\u0000\u001a\n\u0010\f\u001a\u00020\u0006*\u00020\u0007\u001a\f\u0010\r\u001a\u00020\u0006*\u00020\u0007H\u0000\u001a\u0014\u0010\u000e\u001a\u00020\u000f*\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0007H\u0000\u001a\u0012\u0010\u0012\u001a\u00020\u0006*\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015\"\u001a\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0016"}, d2 = {"THROW_INTRINSIC_METHOD_NAMES", "", "", "getTHROW_INTRINSIC_METHOD_NAMES", "()Ljava/util/Set;", "isCheckExpressionValueIsNotNull", "", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "isCheckNotNull", "isCheckNotNullWithMessage", "isCheckParameterIsNotNull", "isInstanceOfOrNullCheck", "isParameterCheckedForNull", "isThrowIntrinsic", "popReferenceValueBefore", "", "Lorg/jetbrains/org/objectweb/asm/tree/InsnList;", "insn", "usesLocalExceptParameterNullCheck", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "index", "", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class RedundantNullCheckMethodTransformerKt {
    private static final Set<String> THROW_INTRINSIC_METHOD_NAMES = SetsKt.setOf((Object[]) new String[]{"throwNpe", "throwUninitializedProperty", "throwUninitializedPropertyAccessException", "throwAssert", "throwIllegalArgument", "throwIllegalState", "throwParameterIsNullException", "throwUndefinedForReified"});

    public static final boolean isInstanceOfOrNullCheck(AbstractInsnNode abstractInsnNode) {
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        return abstractInsnNode.getOpcode() == 193 || abstractInsnNode.getOpcode() == 198 || abstractInsnNode.getOpcode() == 199;
    }

    public static final boolean usesLocalExceptParameterNullCheck(MethodNode methodNode, int i) {
        Intrinsics.checkNotNullParameter(methodNode, "<this>");
        AbstractInsnNode[] array = methodNode.instructions.toArray();
        Intrinsics.checkNotNullExpressionValue(array, "toArray(...)");
        for (AbstractInsnNode abstractInsnNode : array) {
            if (abstractInsnNode instanceof VarInsnNode) {
                VarInsnNode varInsnNode = (VarInsnNode) abstractInsnNode;
                if (varInsnNode.getOpcode() == 25 && varInsnNode.var == i && !isParameterCheckedForNull(abstractInsnNode)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static final boolean isParameterCheckedForNull(AbstractInsnNode abstractInsnNode) {
        AbstractInsnNode next;
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        AbstractInsnNode next2 = abstractInsnNode.getNext();
        if (next2 == null) {
            return false;
        }
        if (next2.getOpcode() != 18) {
            next2 = null;
        }
        return (next2 == null || (next = next2.getNext()) == null || !isCheckParameterIsNotNull(next)) ? false : true;
    }

    public static final Set<String> getTHROW_INTRINSIC_METHOD_NAMES() {
        return THROW_INTRINSIC_METHOD_NAMES;
    }

    public static final void popReferenceValueBefore(InsnList insnList, AbstractInsnNode insn) {
        Intrinsics.checkNotNullParameter(insnList, "<this>");
        Intrinsics.checkNotNullParameter(insn, "insn");
        AbstractInsnNode previous = insn.getPrevious();
        Integer numValueOf = previous != null ? Integer.valueOf(previous.getOpcode()) : null;
        if ((numValueOf != null && numValueOf.intValue() == 1) || ((numValueOf != null && numValueOf.intValue() == 89) || (numValueOf != null && numValueOf.intValue() == 25))) {
            insnList.remove(previous);
        } else {
            insnList.insertBefore(insn, new InsnNode(87));
        }
    }

    public static final boolean isCheckNotNull(AbstractInsnNode abstractInsnNode) {
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        MethodInsnNode methodInsnNode = null;
        if (abstractInsnNode.getOpcode() != 184) {
            abstractInsnNode = null;
        }
        if (!(abstractInsnNode instanceof MethodInsnNode)) {
            abstractInsnNode = null;
        }
        MethodInsnNode methodInsnNode2 = (MethodInsnNode) abstractInsnNode;
        if (methodInsnNode2 != null) {
            MethodInsnNode methodInsnNode3 = methodInsnNode2;
            if (Intrinsics.areEqual(methodInsnNode3.owner, "kotlin/jvm/internal/Intrinsics") && Intrinsics.areEqual(methodInsnNode3.f1015name, "checkNotNull") && Intrinsics.areEqual(methodInsnNode3.desc, ReifiedTypeInliner.pluginIntrinsicsMarkerSignature)) {
                methodInsnNode = methodInsnNode2;
            }
        }
        return methodInsnNode != null;
    }

    public static final boolean isCheckNotNullWithMessage(AbstractInsnNode abstractInsnNode) {
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        MethodInsnNode methodInsnNode = null;
        if (abstractInsnNode.getOpcode() != 184) {
            abstractInsnNode = null;
        }
        if (!(abstractInsnNode instanceof MethodInsnNode)) {
            abstractInsnNode = null;
        }
        MethodInsnNode methodInsnNode2 = (MethodInsnNode) abstractInsnNode;
        if (methodInsnNode2 != null) {
            MethodInsnNode methodInsnNode3 = methodInsnNode2;
            if (Intrinsics.areEqual(methodInsnNode3.owner, "kotlin/jvm/internal/Intrinsics") && Intrinsics.areEqual(methodInsnNode3.f1015name, "checkNotNull") && Intrinsics.areEqual(methodInsnNode3.desc, "(Ljava/lang/Object;Ljava/lang/String;)V")) {
                methodInsnNode = methodInsnNode2;
            }
        }
        return methodInsnNode != null;
    }

    public static final boolean isCheckParameterIsNotNull(AbstractInsnNode abstractInsnNode) {
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        MethodInsnNode methodInsnNode = null;
        if (abstractInsnNode.getOpcode() != 184) {
            abstractInsnNode = null;
        }
        if (!(abstractInsnNode instanceof MethodInsnNode)) {
            abstractInsnNode = null;
        }
        MethodInsnNode methodInsnNode2 = (MethodInsnNode) abstractInsnNode;
        if (methodInsnNode2 != null) {
            MethodInsnNode methodInsnNode3 = methodInsnNode2;
            if (Intrinsics.areEqual(methodInsnNode3.owner, "kotlin/jvm/internal/Intrinsics") && ((Intrinsics.areEqual(methodInsnNode3.f1015name, "checkParameterIsNotNull") || Intrinsics.areEqual(methodInsnNode3.f1015name, "checkNotNullParameter")) && Intrinsics.areEqual(methodInsnNode3.desc, "(Ljava/lang/Object;Ljava/lang/String;)V"))) {
                methodInsnNode = methodInsnNode2;
            }
        }
        return methodInsnNode != null;
    }

    public static final boolean isCheckExpressionValueIsNotNull(AbstractInsnNode abstractInsnNode) {
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        MethodInsnNode methodInsnNode = null;
        if (abstractInsnNode.getOpcode() != 184) {
            abstractInsnNode = null;
        }
        if (!(abstractInsnNode instanceof MethodInsnNode)) {
            abstractInsnNode = null;
        }
        MethodInsnNode methodInsnNode2 = (MethodInsnNode) abstractInsnNode;
        if (methodInsnNode2 != null) {
            MethodInsnNode methodInsnNode3 = methodInsnNode2;
            if (Intrinsics.areEqual(methodInsnNode3.owner, "kotlin/jvm/internal/Intrinsics") && ((Intrinsics.areEqual(methodInsnNode3.f1015name, "checkExpressionValueIsNotNull") || Intrinsics.areEqual(methodInsnNode3.f1015name, "checkNotNullExpressionValue")) && Intrinsics.areEqual(methodInsnNode3.desc, "(Ljava/lang/Object;Ljava/lang/String;)V"))) {
                methodInsnNode = methodInsnNode2;
            }
        }
        return methodInsnNode != null;
    }

    public static final boolean isThrowIntrinsic(AbstractInsnNode abstractInsnNode) {
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        MethodInsnNode methodInsnNode = null;
        if (abstractInsnNode.getOpcode() != 184) {
            abstractInsnNode = null;
        }
        if (!(abstractInsnNode instanceof MethodInsnNode)) {
            abstractInsnNode = null;
        }
        MethodInsnNode methodInsnNode2 = (MethodInsnNode) abstractInsnNode;
        if (methodInsnNode2 != null) {
            MethodInsnNode methodInsnNode3 = methodInsnNode2;
            if (Intrinsics.areEqual(methodInsnNode3.owner, "kotlin/jvm/internal/Intrinsics") && THROW_INTRINSIC_METHOD_NAMES.contains(methodInsnNode3.f1015name)) {
                methodInsnNode = methodInsnNode2;
            }
        }
        return methodInsnNode != null;
    }
}
