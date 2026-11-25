package org.jetbrains.kotlin.codegen.optimization;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer;
import org.jetbrains.kotlin.config.ApiVersion;
import org.jetbrains.kotlin.config.MavenComparableVersion;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.InsnNode;
import org.jetbrains.org.objectweb.asm.tree.IntInsnNode;
import org.jetbrains.org.objectweb.asm.tree.LdcInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;

/* JADX INFO: compiled from: ApiVersionCallsPreprocessingMethodTransformer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0013\u0010\r\u001a\u0004\u0018\u00010\u000e*\u00020\u000fH\u0002¢\u0006\u0002\u0010\u0010J\f\u0010\u0011\u001a\u00020\u0012*\u00020\u000fH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/ApiVersionCallsPreprocessingMethodTransformer;", "Lorg/jetbrains/kotlin/codegen/optimization/transformer/MethodTransformer;", "targetApiVersion", "Lorg/jetbrains/kotlin/config/ApiVersion;", "(Lorg/jetbrains/kotlin/config/ApiVersion;)V", "constantConditionElimination", "Lorg/jetbrains/kotlin/codegen/optimization/ConstantConditionEliminationMethodTransformer;", "transform", "", "internalClassName", "", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "getIntConstValue", "", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "(Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;)Ljava/lang/Integer;", "isApiVersionIsAtLeastCall", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ApiVersionCallsPreprocessingMethodTransformer extends MethodTransformer {
    private final ConstantConditionEliminationMethodTransformer constantConditionElimination;
    private final ApiVersion targetApiVersion;

    public ApiVersionCallsPreprocessingMethodTransformer(ApiVersion targetApiVersion) {
        Intrinsics.checkNotNullParameter(targetApiVersion, "targetApiVersion");
        this.targetApiVersion = targetApiVersion;
        this.constantConditionElimination = new ConstantConditionEliminationMethodTransformer();
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer
    public void transform(String internalClassName, MethodNode methodNode) {
        AbstractInsnNode previous;
        Integer intConstValue;
        Integer intConstValue2;
        Integer intConstValue3;
        InsnNode insnNode;
        Intrinsics.checkNotNullParameter(internalClassName, "internalClassName");
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        AbstractInsnNode[] array = methodNode.instructions.toArray();
        Intrinsics.checkNotNullExpressionValue(array, "toArray(...)");
        boolean z = false;
        for (AbstractInsnNode abstractInsnNode : array) {
            Intrinsics.checkNotNull(abstractInsnNode);
            if (isApiVersionIsAtLeastCall(abstractInsnNode) && (previous = abstractInsnNode.getPrevious()) != null && (intConstValue = getIntConstValue(previous)) != null) {
                int iIntValue = intConstValue.intValue();
                AbstractInsnNode previous2 = previous.getPrevious();
                if (previous2 != null && (intConstValue2 = getIntConstValue(previous2)) != null) {
                    int iIntValue2 = intConstValue2.intValue();
                    AbstractInsnNode previous3 = previous2.getPrevious();
                    if (previous3 != null && (intConstValue3 = getIntConstValue(previous3)) != null) {
                        if (this.targetApiVersion.getVersion().compareTo(new MavenComparableVersion(new StringBuilder().append(intConstValue3.intValue()).append('.').append(iIntValue2).append('.').append(iIntValue).toString())) >= 0) {
                            insnNode = new InsnNode(4);
                        } else {
                            insnNode = new InsnNode(3);
                        }
                        InsnList insnList = methodNode.instructions;
                        insnList.remove(previous3);
                        insnList.remove(previous2);
                        insnList.remove(previous);
                        insnList.set(abstractInsnNode, insnNode);
                        z = true;
                    }
                }
            }
        }
        if (z) {
            this.constantConditionElimination.transform(internalClassName, methodNode);
        }
    }

    private final Integer getIntConstValue(AbstractInsnNode abstractInsnNode) {
        if (abstractInsnNode instanceof InsnNode) {
            InsnNode insnNode = (InsnNode) abstractInsnNode;
            int opcode = insnNode.getOpcode();
            if (2 > opcode || opcode >= 9) {
                return null;
            }
            return Integer.valueOf(insnNode.getOpcode() - 3);
        }
        if (abstractInsnNode instanceof IntInsnNode) {
            IntInsnNode intInsnNode = (IntInsnNode) abstractInsnNode;
            int opcode2 = intInsnNode.getOpcode();
            if (opcode2 == 16) {
                return Integer.valueOf(intInsnNode.operand);
            }
            if (opcode2 != 17) {
                return null;
            }
            return Integer.valueOf(intInsnNode.operand);
        }
        if (!(abstractInsnNode instanceof LdcInsnNode)) {
            return null;
        }
        Object obj = ((LdcInsnNode) abstractInsnNode).cst;
        if (obj instanceof Integer) {
            return (Integer) obj;
        }
        return null;
    }

    private final boolean isApiVersionIsAtLeastCall(AbstractInsnNode abstractInsnNode) {
        if (abstractInsnNode.getOpcode() != 184 || !(abstractInsnNode instanceof MethodInsnNode)) {
            return false;
        }
        MethodInsnNode methodInsnNode = (MethodInsnNode) abstractInsnNode;
        String owner = methodInsnNode.owner;
        Intrinsics.checkNotNullExpressionValue(owner, "owner");
        return StringsKt.startsWith$default(owner, "kotlin/internal", false, 2, (Object) null) && Intrinsics.areEqual(methodInsnNode.f1015name, "apiVersionIsAtLeast") && Intrinsics.areEqual(methodInsnNode.desc, "(III)Z");
    }
}
