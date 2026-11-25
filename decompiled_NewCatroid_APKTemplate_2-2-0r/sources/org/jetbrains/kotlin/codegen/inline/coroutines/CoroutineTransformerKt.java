package org.jetbrains.kotlin.codegen.inline.coroutines;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.codegen.inline.FunctionalArgument;
import org.jetbrains.kotlin.codegen.inline.InlineCodegenUtilsKt;
import org.jetbrains.kotlin.codegen.inline.NonInlineArgumentForInlineSuspendParameter;
import org.jetbrains.kotlin.codegen.optimization.common.UtilKt;
import org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.MethodInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue;
import org.jetbrains.org.objectweb.asm.tree.analysis.Frame;
import org.jetbrains.org.objectweb.asm.tree.analysis.Value;

/* JADX INFO: compiled from: CoroutineTransformer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00008\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\u001a\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u001a\u000e\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000b\u001a\u001c\u0010\f\u001a\u0004\u0018\u00010\r*\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"FOR_INLINE_SUFFIX", "", "NOINLINE_CALL_MARKER", "markNoinlineLambdaIfSuspend", "", JvmAnnotationNames.METADATA_VERSION_FIELD_NAME, "Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", CompilerOptions.INFO, "Lorg/jetbrains/kotlin/codegen/inline/FunctionalArgument;", "surroundInvokesWithSuspendMarkersIfNeeded", "node", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "getSource", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/Frame;", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicValue;", TypedValues.CycleType.S_WAVE_OFFSET, "", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CoroutineTransformerKt {
    public static final String FOR_INLINE_SUFFIX = "$$forInline";

    public static final void markNoinlineLambdaIfSuspend(MethodVisitor mv, FunctionalArgument functionalArgument) {
        Intrinsics.checkNotNullParameter(mv, "mv");
        if (functionalArgument == NonInlineArgumentForInlineSuspendParameter.OTHER) {
            mv.visitMethodInsn(184, "$$$$$NOINLINE_CALL_MARKER$$$$$", "always", "()V", false);
        } else if (functionalArgument == NonInlineArgumentForInlineSuspendParameter.INLINE_LAMBDA_AS_VARIABLE) {
            mv.visitMethodInsn(184, "$$$$$NOINLINE_CALL_MARKER$$$$$", "conditional", "()V", false);
        }
    }

    private static final AbstractInsnNode getSource(Frame<BasicValue> frame, int i) {
        Value stack = frame.getStack((frame.getStackSize() - i) - 1);
        PossibleLambdaLoad possibleLambdaLoad = stack instanceof PossibleLambdaLoad ? (PossibleLambdaLoad) stack : null;
        if (possibleLambdaLoad != null) {
            return possibleLambdaLoad.getInsn();
        }
        return null;
    }

    public static final void surroundInvokesWithSuspendMarkersIfNeeded(MethodNode node) {
        AbstractInsnNode previous;
        Intrinsics.checkNotNullParameter(node, "node");
        InsnList instructions = node.instructions;
        Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
        List list = SequencesKt.toList(SequencesKt.filter(UtilKt.asSequence(instructions), new Function1<AbstractInsnNode, Boolean>() { // from class: org.jetbrains.kotlin.codegen.inline.coroutines.CoroutineTransformerKt$surroundInvokesWithSuspendMarkersIfNeeded$markers$1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(AbstractInsnNode it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(it.getOpcode() == 184 && Intrinsics.areEqual(((MethodInsnNode) it).owner, "$$$$$NOINLINE_CALL_MARKER$$$$$"));
            }
        }));
        if (list.isEmpty()) {
            return;
        }
        Frame[] frameArrAnalyze = MethodTransformer.analyze("fake", node, new CapturedLambdaInterpreter());
        Intrinsics.checkNotNullExpressionValue(frameArrAnalyze, "analyze(...)");
        List<AbstractInsnNode> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        for (AbstractInsnNode abstractInsnNode : list2) {
            AbstractInsnNode next = abstractInsnNode.getNext();
            Intrinsics.checkNotNull(next, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.MethodInsnNode");
            String owner = ((MethodInsnNode) next).owner;
            Intrinsics.checkNotNullExpressionValue(owner, "owner");
            int i = Integer.parseInt(StringsKt.removePrefix(owner, (CharSequence) InlineCodegenUtilsKt.NUMBERED_FUNCTION_PREFIX));
            Frame frame = frameArrAnalyze[node.instructions.indexOf(abstractInsnNode) + 1];
            if (frame != null) {
                Intrinsics.checkNotNull(frame);
                previous = getSource(frame, i);
            } else {
                previous = null;
            }
            while (previous != null && previous.getOpcode() == 180) {
                previous = previous.getPrevious();
            }
            arrayList.add(previous);
        }
        for (Pair pair : CollectionsKt.zip(list2, arrayList)) {
            AbstractInsnNode abstractInsnNode2 = (AbstractInsnNode) pair.component1();
            AbstractInsnNode abstractInsnNode3 = (AbstractInsnNode) pair.component2();
            Intrinsics.checkNotNull(abstractInsnNode2, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.MethodInsnNode");
            MethodInsnNode methodInsnNode = (MethodInsnNode) abstractInsnNode2;
            boolean zAreEqual = Intrinsics.areEqual(methodInsnNode.f1015name, "conditional");
            AbstractInsnNode next2 = methodInsnNode.getNext();
            Intrinsics.checkNotNull(next2, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.MethodInsnNode");
            MethodInsnNode methodInsnNode2 = (MethodInsnNode) next2;
            node.instructions.remove(abstractInsnNode2);
            if (abstractInsnNode3 != null) {
                InsnList insnList = node.instructions;
                MethodNode methodNode = new MethodNode();
                InlineCodegenUtilsKt.addInlineMarker(new InstructionAdapter(methodNode), true);
                Unit unit = Unit.INSTANCE;
                InsnList instructions2 = methodNode.instructions;
                Intrinsics.checkNotNullExpressionValue(instructions2, "instructions");
                insnList.insertBefore(abstractInsnNode3, instructions2);
                InsnList insnList2 = node.instructions;
                MethodInsnNode methodInsnNode3 = methodInsnNode2;
                MethodNode methodNode2 = new MethodNode();
                InlineCodegenUtilsKt.addSuspendMarker(new InstructionAdapter(methodNode2), true, zAreEqual);
                Unit unit2 = Unit.INSTANCE;
                InsnList instructions3 = methodNode2.instructions;
                Intrinsics.checkNotNullExpressionValue(instructions3, "instructions");
                insnList2.insertBefore(methodInsnNode3, instructions3);
                InsnList insnList3 = node.instructions;
                MethodNode methodNode3 = new MethodNode();
                InstructionAdapter instructionAdapter = new InstructionAdapter(methodNode3);
                InlineCodegenUtilsKt.addSuspendMarker(instructionAdapter, false, zAreEqual);
                InlineCodegenUtilsKt.addInlineMarker(instructionAdapter, false);
                Unit unit3 = Unit.INSTANCE;
                InsnList instructions4 = methodNode3.instructions;
                Intrinsics.checkNotNullExpressionValue(instructions4, "instructions");
                insnList3.insert(methodInsnNode3, instructions4);
            }
        }
    }
}
