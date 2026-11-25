package org.jetbrains.kotlin.codegen.optimization.common;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.codegen.inline.InlineCodegenUtilsKt;
import org.jetbrains.kotlin.codegen.inline.MaxStackFrameSizeAndLocalsCalculator;
import org.jetbrains.kotlin.codegen.optimization.LabelNormalizationMethodTransformerKt;
import org.jetbrains.kotlin.codegen.pseudoInsns.PseudoInsn;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.IincInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.IntInsnNode;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;
import org.jetbrains.org.objectweb.asm.tree.LdcInsnNode;
import org.jetbrains.org.objectweb.asm.tree.LocalVariableNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.TryCatchBlockNode;
import org.jetbrains.org.objectweb.asm.tree.VarInsnNode;

/* JADX INFO: compiled from: Util.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000h\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0015\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0007\u001a\u001f\u0010\r\u001a\u00020\u000e2\u0012\u0010\u000f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0010\"\u00020\u0002¢\u0006\u0002\u0010\u0011\u001a\u0010\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\u0002H\u0002\u001a\u0010\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00020\u0015*\u00020\u000e\u001a&\u0010\u0016\u001a\u0004\u0018\u00010\u0002*\u00020\u00022\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\n0\u0018H\u0086\bø\u0001\u0000\u001a&\u0010\u0019\u001a\u0004\u0018\u00010\u0002*\u00020\u00022\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\n0\u0018H\u0086\bø\u0001\u0000\u001a\n\u0010\u001a\u001a\u00020\n*\u00020\u0002\u001a=\u0010\u001b\u001a\u00020\n\"\n\b\u0000\u0010\u001c\u0018\u0001*\u00020\u0002*\u00020\u00022\u0006\u0010\u001d\u001a\u00020\u00062\u0017\u0010\u001e\u001a\u0013\u0012\u0004\u0012\u0002H\u001c\u0012\u0004\u0012\u00020\n0\u0018¢\u0006\u0002\b\u001fH\u0080\bø\u0001\u0000\u001a\n\u0010 \u001a\u00020\n*\u00020\u0002\u001a\f\u0010!\u001a\u00020\n*\u00020\"H\u0002\u001a\n\u0010#\u001a\u00020\n*\u00020\u0002\u001a\n\u0010$\u001a\u00020%*\u00020&\u001a\u0012\u0010'\u001a\u00020%*\u00020&2\u0006\u0010(\u001a\u00020)\u001a\u0018\u0010*\u001a\u00020%*\u00020\u000e2\f\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00020,\u001a\n\u0010-\u001a\u00020%*\u00020&\u001a\n\u0010.\u001a\u00020%*\u00020&\u001a\n\u0010/\u001a\u00020%*\u00020&\u001aD\u00100\u001a\u0004\u0018\u0001H\u001c\"\n\b\u0000\u0010\u001c\u0018\u0001*\u00020\u0002*\u00020\u00022\u0006\u0010\u001d\u001a\u00020\u00062\u0017\u0010\u001e\u001a\u0013\u0012\u0004\u0012\u0002H\u001c\u0012\u0004\u0012\u00020\n0\u0018¢\u0006\u0002\b\u001fH\u0080\bø\u0001\u0000¢\u0006\u0002\u00101\u001a\n\u00102\u001a\u00020%*\u00020&\"\u0017\u0010\u0000\u001a\u00020\u0001*\u0004\u0018\u00010\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0017\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b\"\u0015\u0010\t\u001a\u00020\n*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\t\u0010\u000b\"\u0015\u0010\f\u001a\u00020\n*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\f\u0010\u000b\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u00063"}, d2 = {"debugText", "", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "getDebugText", "(Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;)Ljava/lang/String;", "intConstant", "", "getIntConstant", "(Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;)Ljava/lang/Integer;", "isBranchOrCall", "", "(Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;)Z", "isMeaningful", "insnListOf", "Lorg/jetbrains/org/objectweb/asm/tree/InsnList;", "insns", "", "([Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;)Lorg/jetbrains/org/objectweb/asm/tree/InsnList;", "isOptimizationMarker", "insn", "asSequence", "Lkotlin/sequences/Sequence;", "findNextOrNull", "predicate", "Lkotlin/Function1;", "findPreviousOrNull", "hasOpcode", "isInsn", ExifInterface.GPS_DIRECTION_TRUE, "opcode", "condition", "Lkotlin/ExtensionFunctionType;", "isLoadOperation", "isSize2LoadStoreOperation", "Lorg/jetbrains/org/objectweb/asm/tree/VarInsnNode;", "isStoreOperation", "prepareForEmitting", "", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "remapLocalVariables", "remapping", "", "removeAll", "nodes", "", "removeEmptyCatchBlocks", "removeUnusedLocalVariables", "stripOptimizationMarkers", "takeInsnIf", "(Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;ILkotlin/jvm/functions/Function1;)Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "updateMaxStack", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class UtilKt {
    public static final boolean isMeaningful(AbstractInsnNode abstractInsnNode) {
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        int type = abstractInsnNode.getType();
        return (type == 8 || type == 14 || type == 15) ? false : true;
    }

    public static final boolean isBranchOrCall(AbstractInsnNode abstractInsnNode) {
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        int type = abstractInsnNode.getType();
        return type == 5 || type == 7 || type == 11 || type == 12;
    }

    public static final Sequence<AbstractInsnNode> asSequence(InsnList insnList) {
        Intrinsics.checkNotNullParameter(insnList, "<this>");
        return insnList.size() == 0 ? SequencesKt.emptySequence() : new InsnSequence(insnList);
    }

    public static final void prepareForEmitting(MethodNode methodNode) {
        Intrinsics.checkNotNullParameter(methodNode, "<this>");
        stripOptimizationMarkers(methodNode);
        removeEmptyCatchBlocks(methodNode);
        List<LocalVariableNode> localVariables = methodNode.localVariables;
        Intrinsics.checkNotNullExpressionValue(localVariables, "localVariables");
        ArrayList arrayList = new ArrayList();
        for (Object obj : localVariables) {
            LocalVariableNode localVariableNode = (LocalVariableNode) obj;
            LabelNode start = localVariableNode.start;
            Intrinsics.checkNotNullExpressionValue(start, "start");
            Iterator<AbstractInsnNode> it = new InsnSequence(start, localVariableNode.end).iterator();
            while (it.hasNext()) {
                if (isMeaningful(it.next())) {
                    arrayList.add(obj);
                    break;
                }
            }
        }
        methodNode.localVariables = arrayList;
        AbstractInsnNode last = methodNode.instructions.getLast();
        while (true) {
            Intrinsics.checkNotNull(last);
            if (!isMeaningful(last)) {
                AbstractInsnNode previous = last.getPrevious();
                if (last.getType() == 15) {
                    methodNode.instructions.remove(last);
                }
                last = previous;
            } else {
                updateMaxStack(methodNode);
                return;
            }
        }
    }

    public static final void updateMaxStack(final MethodNode methodNode) {
        Intrinsics.checkNotNullParameter(methodNode, "<this>");
        methodNode.maxStack = -1;
        methodNode.accept(new MaxStackFrameSizeAndLocalsCalculator(589824, methodNode.access, methodNode.desc, new MethodVisitor() { // from class: org.jetbrains.kotlin.codegen.optimization.common.UtilKt.updateMaxStack.1
            {
                super(589824);
            }

            @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
            public void visitMaxs(int maxStack, int maxLocals) {
                methodNode.maxStack = maxStack;
            }
        }));
    }

    public static final void stripOptimizationMarkers(MethodNode methodNode) {
        Intrinsics.checkNotNullParameter(methodNode, "<this>");
        AbstractInsnNode first = methodNode.instructions.getFirst();
        while (first != null) {
            if (isOptimizationMarker(first)) {
                InsnList instructions = methodNode.instructions;
                Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
                first = LabelNormalizationMethodTransformerKt.removeNodeGetNext(instructions, first);
            } else {
                first = first.getNext();
            }
        }
    }

    private static final boolean isOptimizationMarker(AbstractInsnNode abstractInsnNode) {
        return PseudoInsn.STORE_NOT_NULL.isa(abstractInsnNode);
    }

    public static final void removeEmptyCatchBlocks(MethodNode methodNode) {
        Intrinsics.checkNotNullParameter(methodNode, "<this>");
        List<TryCatchBlockNode> tryCatchBlocks = methodNode.tryCatchBlocks;
        Intrinsics.checkNotNullExpressionValue(tryCatchBlocks, "tryCatchBlocks");
        ArrayList arrayList = new ArrayList();
        for (Object obj : tryCatchBlocks) {
            TryCatchBlockNode tryCatchBlockNode = (TryCatchBlockNode) obj;
            LabelNode start = tryCatchBlockNode.start;
            Intrinsics.checkNotNullExpressionValue(start, "start");
            Iterator<AbstractInsnNode> it = new InsnSequence(start, tryCatchBlockNode.end).iterator();
            while (it.hasNext()) {
                if (isMeaningful(it.next())) {
                    arrayList.add(obj);
                    break;
                }
            }
        }
        methodNode.tryCatchBlocks = arrayList;
    }

    /* JADX WARN: Type inference failed for: r3v8, types: [java.util.ListIterator] */
    public static final void removeUnusedLocalVariables(MethodNode methodNode) {
        int i;
        Intrinsics.checkNotNullParameter(methodNode, "<this>");
        int i2 = methodNode.maxLocals;
        boolean[] zArr = new boolean[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            zArr[i3] = false;
        }
        if ((methodNode.access & 8) != 0) {
            i = 0;
        } else {
            zArr[0] = true;
            i = 1;
        }
        Type[] argumentTypes = Type.getArgumentTypes(methodNode.desc);
        Intrinsics.checkNotNullExpressionValue(argumentTypes, "getArgumentTypes(...)");
        for (Type type : argumentTypes) {
            int size = type.getSize();
            int i4 = 0;
            while (i4 < size) {
                zArr[i] = true;
                i4++;
                i++;
            }
        }
        ?? it = methodNode.instructions.iterator();
        while (it.hasNext()) {
            AbstractInsnNode abstractInsnNode = (AbstractInsnNode) it.next();
            if (abstractInsnNode instanceof VarInsnNode) {
                VarInsnNode varInsnNode = (VarInsnNode) abstractInsnNode;
                int i5 = varInsnNode.var;
                zArr[i5] = true;
                Intrinsics.checkNotNull(abstractInsnNode);
                if (isSize2LoadStoreOperation(varInsnNode)) {
                    zArr[i5 + 1] = true;
                }
            } else if (abstractInsnNode instanceof IincInsnNode) {
                zArr[((IincInsnNode) abstractInsnNode).var] = true;
            }
        }
        for (LocalVariableNode localVariableNode : methodNode.localVariables) {
            int i6 = localVariableNode.index;
            zArr[i6] = true;
            if (Type.getType(localVariableNode.desc).getSize() == 2) {
                zArr[i6 + 1] = true;
            }
        }
        for (int i7 = 0; i7 < i2; i7++) {
            if (!zArr[i7]) {
                int i8 = methodNode.maxLocals;
                int[] iArr = new int[i8];
                for (int i9 = 0; i9 < i8; i9++) {
                    iArr[i9] = 0;
                }
                int i10 = 0;
                for (int i11 = 0; i11 < i8; i11++) {
                    iArr[i11] = i10;
                    if (zArr[i11]) {
                        i10++;
                    }
                }
                remapLocalVariables(methodNode, iArr);
                return;
            }
        }
    }

    private static final boolean isSize2LoadStoreOperation(VarInsnNode varInsnNode) {
        return varInsnNode.getOpcode() == 22 || varInsnNode.getOpcode() == 24 || varInsnNode.getOpcode() == 55 || varInsnNode.getOpcode() == 57;
    }

    public static final void remapLocalVariables(MethodNode methodNode, int[] remapping) {
        Intrinsics.checkNotNullParameter(methodNode, "<this>");
        Intrinsics.checkNotNullParameter(remapping, "remapping");
        AbstractInsnNode[] array = methodNode.instructions.toArray();
        Intrinsics.checkNotNullExpressionValue(array, "toArray(...)");
        for (AbstractInsnNode abstractInsnNode : array) {
            if (abstractInsnNode instanceof VarInsnNode) {
                VarInsnNode varInsnNode = (VarInsnNode) abstractInsnNode;
                varInsnNode.var = remapping[varInsnNode.var];
            } else if (abstractInsnNode instanceof IincInsnNode) {
                IincInsnNode iincInsnNode = (IincInsnNode) abstractInsnNode;
                iincInsnNode.var = remapping[iincInsnNode.var];
            }
        }
        for (LocalVariableNode localVariableNode : methodNode.localVariables) {
            localVariableNode.index = remapping[localVariableNode.index];
        }
    }

    public static final AbstractInsnNode findNextOrNull(AbstractInsnNode abstractInsnNode, Function1<? super AbstractInsnNode, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        AbstractInsnNode next = abstractInsnNode.getNext();
        while (next != null && !predicate.invoke(next).booleanValue()) {
            next = next.getNext();
        }
        return next;
    }

    public static final AbstractInsnNode findPreviousOrNull(AbstractInsnNode abstractInsnNode, Function1<? super AbstractInsnNode, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        AbstractInsnNode previous = abstractInsnNode.getPrevious();
        while (previous != null && !predicate.invoke(previous).booleanValue()) {
            previous = previous.getPrevious();
        }
        return previous;
    }

    public static final boolean hasOpcode(AbstractInsnNode abstractInsnNode) {
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        return abstractInsnNode.getOpcode() >= 0;
    }

    public static final Integer getIntConstant(AbstractInsnNode abstractInsnNode) {
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        int opcode = abstractInsnNode.getOpcode();
        if (2 <= opcode && opcode < 9) {
            return Integer.valueOf(abstractInsnNode.getOpcode() - 3);
        }
        if (opcode == 16 || opcode == 17) {
            return Integer.valueOf(((IntInsnNode) abstractInsnNode).operand);
        }
        if (opcode == 18) {
            Object obj = ((LdcInsnNode) abstractInsnNode).cst;
            if (obj instanceof Integer) {
                return (Integer) obj;
            }
        }
        return null;
    }

    public static final InsnList insnListOf(AbstractInsnNode... insns) {
        Intrinsics.checkNotNullParameter(insns, "insns");
        InsnList insnList = new InsnList();
        for (AbstractInsnNode abstractInsnNode : insns) {
            insnList.add(abstractInsnNode);
        }
        return insnList;
    }

    public static final boolean isStoreOperation(AbstractInsnNode abstractInsnNode) {
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        int opcode = abstractInsnNode.getOpcode();
        return 54 <= opcode && opcode < 59;
    }

    public static final boolean isLoadOperation(AbstractInsnNode abstractInsnNode) {
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        int opcode = abstractInsnNode.getOpcode();
        return 21 <= opcode && opcode < 26;
    }

    public static final String getDebugText(AbstractInsnNode abstractInsnNode) {
        return abstractInsnNode == null ? "<null>" : abstractInsnNode.getClass().getSimpleName() + ": " + InlineCodegenUtilsKt.getInsnText(abstractInsnNode);
    }

    public static final /* synthetic */ <T extends AbstractInsnNode> T takeInsnIf(AbstractInsnNode abstractInsnNode, int i, Function1<? super T, Boolean> condition) {
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        Intrinsics.checkNotNullParameter(condition, "condition");
        T t = null;
        if (abstractInsnNode.getOpcode() != i) {
            abstractInsnNode = null;
        }
        Intrinsics.reifiedOperationMarker(2, ExifInterface.GPS_DIRECTION_TRUE);
        if (abstractInsnNode != null) {
            if (condition.invoke(abstractInsnNode).booleanValue()) {
                t = (T) abstractInsnNode;
            }
        }
        return t;
    }

    public static final void removeAll(InsnList insnList, Collection<? extends AbstractInsnNode> nodes) {
        Intrinsics.checkNotNullParameter(insnList, "<this>");
        Intrinsics.checkNotNullParameter(nodes, "nodes");
        Iterator<? extends AbstractInsnNode> it = nodes.iterator();
        while (it.hasNext()) {
            insnList.remove(it.next());
        }
    }

    public static final /* synthetic */ <T extends AbstractInsnNode> boolean isInsn(AbstractInsnNode abstractInsnNode, int i, Function1<? super T, Boolean> condition) {
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        Intrinsics.checkNotNullParameter(condition, "condition");
        AbstractInsnNode abstractInsnNode2 = null;
        if (abstractInsnNode.getOpcode() != i) {
            abstractInsnNode = null;
        }
        Intrinsics.reifiedOperationMarker(2, ExifInterface.GPS_DIRECTION_TRUE);
        if (abstractInsnNode != null) {
            abstractInsnNode2 = condition.invoke(abstractInsnNode).booleanValue() ? abstractInsnNode : null;
        }
        return abstractInsnNode2 != null;
    }
}
