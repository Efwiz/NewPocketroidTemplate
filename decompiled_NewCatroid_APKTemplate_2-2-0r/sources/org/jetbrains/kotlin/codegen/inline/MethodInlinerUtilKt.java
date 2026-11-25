package org.jetbrains.kotlin.codegen.inline;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.reandroid.dex.sections.Marker;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.codegen.optimization.common.FastMethodAnalyzer;
import org.jetbrains.kotlin.codegen.optimization.common.InsnSequence;
import org.jetbrains.kotlin.codegen.optimization.common.UtilKt;
import org.jetbrains.kotlin.codegen.optimization.nullCheck.RedundantNullCheckMethodTransformerKt;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.FieldInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.VarInsnNode;
import org.jetbrains.org.objectweb.asm.tree.analysis.AnalyzerException;
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicInterpreter;
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue;
import org.jetbrains.org.objectweb.asm.tree.analysis.Frame;
import org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter;

/* JADX INFO: compiled from: MethodInlinerUtil.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000J\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\u001a-\u0010\u0005\u001a\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u00070\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0000¢\u0006\u0002\u0010\f\u001a\u0010\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e*\u00020\t\u001a\f\u0010\u0010\u001a\u0004\u0018\u00010\u0011*\u00020\u0011\u001a\f\u0010\u0012\u001a\u00020\u0013*\u00020\u0011H\u0000\u001a\f\u0010\u0014\u001a\u00020\u0013*\u00020\u0011H\u0000\u001a\u0018\u0010\u0015\u001a\u00020\u0016*\u00020\t2\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00110\u0018\u001a\u0018\u0010\u0015\u001a\u00020\u0016*\u00020\t2\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00110\u000e\"\u0019\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u0004\u0018\u00010\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0019"}, d2 = {"functionalArgument", "Lorg/jetbrains/kotlin/codegen/inline/FunctionalArgument;", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicValue;", "getFunctionalArgument", "(Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicValue;)Lorg/jetbrains/kotlin/codegen/inline/FunctionalArgument;", "analyzeMethodNodeWithInterpreter", "", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/Frame;", "node", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "interpreter", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicInterpreter;", "(Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicInterpreter;)[Lorg/jetbrains/org/objectweb/asm/tree/analysis/Frame;", "findCapturedFieldAssignmentInstructions", "Lkotlin/sequences/Sequence;", "Lorg/jetbrains/org/objectweb/asm/tree/FieldInsnNode;", "getNextMeaningful", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "isAload0", "", "isAloadBeforeCheckParameterIsNotNull", ProductAction.ACTION_REMOVE, "", "instructions", "", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class MethodInlinerUtilKt {
    public static final void remove(MethodNode methodNode, Collection<? extends AbstractInsnNode> instructions) {
        Intrinsics.checkNotNullParameter(methodNode, "<this>");
        Intrinsics.checkNotNullParameter(instructions, "instructions");
        Iterator<T> it = instructions.iterator();
        while (it.hasNext()) {
            methodNode.instructions.remove((AbstractInsnNode) it.next());
        }
    }

    public static final Sequence<FieldInsnNode> findCapturedFieldAssignmentInstructions(MethodNode methodNode) {
        Intrinsics.checkNotNullParameter(methodNode, "<this>");
        InsnList instructions = methodNode.instructions;
        Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
        Sequence sequenceFilter = SequencesKt.filter(new InsnSequence(instructions), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.codegen.inline.MethodInlinerUtilKt$findCapturedFieldAssignmentInstructions$$inlined$filterIsInstance$1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                return Boolean.valueOf(obj instanceof FieldInsnNode);
            }
        });
        Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
        return SequencesKt.filter(sequenceFilter, new Function1<FieldInsnNode, Boolean>() { // from class: org.jetbrains.kotlin.codegen.inline.MethodInlinerUtilKt.findCapturedFieldAssignmentInstructions.1
            /* JADX WARN: Code duplicated, block: B:19:0x003e  */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(FieldInsnNode fieldNode) {
                boolean z;
                Intrinsics.checkNotNullParameter(fieldNode, "fieldNode");
                AbstractInsnNode previous = fieldNode.getPrevious();
                AbstractInsnNode previous2 = previous != null ? previous.getPrevious() : null;
                VarInsnNode varInsnNode = previous2 instanceof VarInsnNode ? (VarInsnNode) previous2 : null;
                if (fieldNode.getOpcode() == 181) {
                    String name2 = fieldNode.f1010name;
                    Intrinsics.checkNotNullExpressionValue(name2, "name");
                    if (InlineCodegenUtilsKt.isCapturedFieldName(name2) && (fieldNode.getPrevious() instanceof VarInsnNode) && varInsnNode != null && varInsnNode.var == 0) {
                        z = true;
                    } else {
                        z = false;
                    }
                } else {
                    z = false;
                }
                return Boolean.valueOf(z);
            }
        });
    }

    public static final AbstractInsnNode getNextMeaningful(AbstractInsnNode abstractInsnNode) {
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        AbstractInsnNode next = abstractInsnNode.getNext();
        while (next != null && !UtilKt.isMeaningful(next)) {
            next = next.getNext();
        }
        return next;
    }

    public static final FunctionalArgument getFunctionalArgument(BasicValue basicValue) {
        FunctionalArgumentValue functionalArgumentValue = basicValue instanceof FunctionalArgumentValue ? (FunctionalArgumentValue) basicValue : null;
        if (functionalArgumentValue != null) {
            return functionalArgumentValue.getFunctionalArgument();
        }
        return null;
    }

    public static final boolean isAloadBeforeCheckParameterIsNotNull(AbstractInsnNode abstractInsnNode) {
        AbstractInsnNode next;
        AbstractInsnNode next2;
        AbstractInsnNode next3;
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        return abstractInsnNode.getOpcode() == 25 && (next = abstractInsnNode.getNext()) != null && next.getOpcode() == 18 && (next2 = abstractInsnNode.getNext()) != null && (next3 = next2.getNext()) != null && RedundantNullCheckMethodTransformerKt.isCheckParameterIsNotNull(next3);
    }

    public static final boolean isAload0(AbstractInsnNode abstractInsnNode) {
        Intrinsics.checkNotNullParameter(abstractInsnNode, "<this>");
        return abstractInsnNode.getOpcode() == 25 && ((VarInsnNode) abstractInsnNode).var == 0;
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [org.jetbrains.org.objectweb.asm.tree.analysis.Frame<org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue>[], org.jetbrains.org.objectweb.asm.tree.analysis.Frame[]] */
    public static final Frame<BasicValue>[] analyzeMethodNodeWithInterpreter(final MethodNode node, final BasicInterpreter interpreter) {
        Intrinsics.checkNotNullParameter(node, "node");
        Intrinsics.checkNotNullParameter(interpreter, "interpreter");
        try {
            return new FastMethodAnalyzer<BasicValue>(node, interpreter) { // from class: org.jetbrains.kotlin.codegen.inline.MethodInlinerUtilKt$analyzeMethodNodeWithInterpreter$analyzer$1
                {
                    BasicInterpreter basicInterpreter = interpreter;
                }

                @Override // org.jetbrains.kotlin.codegen.optimization.common.FastMethodAnalyzer
                protected Frame<BasicValue> newFrame(final int nLocals, final int nStack) {
                    return new Frame<BasicValue>(nLocals, nStack) { // from class: org.jetbrains.kotlin.codegen.inline.MethodInlinerUtilKt$analyzeMethodNodeWithInterpreter$analyzer$1$newFrame$1
                        @Override // org.jetbrains.org.objectweb.asm.tree.analysis.Frame
                        public void execute(AbstractInsnNode insn, Interpreter<BasicValue> interpreter2) throws AnalyzerException {
                            Intrinsics.checkNotNullParameter(insn, "insn");
                            Intrinsics.checkNotNullParameter(interpreter2, "interpreter");
                            if (insn.getOpcode() == 177) {
                                return;
                            }
                            super.execute(insn, interpreter2);
                        }
                    };
                }
            }.analyze();
        } catch (AnalyzerException e) {
            throw new RuntimeException(e);
        }
    }

    public static final void remove(MethodNode methodNode, Sequence<? extends AbstractInsnNode> instructions) {
        Intrinsics.checkNotNullParameter(methodNode, "<this>");
        Intrinsics.checkNotNullParameter(instructions, "instructions");
        Iterator<? extends AbstractInsnNode> it = instructions.iterator();
        while (it.hasNext()) {
            methodNode.instructions.remove(it.next());
        }
    }
}
