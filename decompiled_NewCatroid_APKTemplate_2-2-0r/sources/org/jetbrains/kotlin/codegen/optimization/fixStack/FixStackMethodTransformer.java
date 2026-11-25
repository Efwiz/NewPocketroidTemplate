package org.jetbrains.kotlin.codegen.optimization.fixStack;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.inline.InlineCodegenUtilsKt;
import org.jetbrains.kotlin.codegen.optimization.common.InsnSequence;
import org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer;
import org.jetbrains.kotlin.codegen.pseudoInsns.PseudoInsn;
import org.jetbrains.kotlin.codegen.pseudoInsns.PseudoInsnsKt;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.JumpInsnNode;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;

/* JADX INFO: compiled from: FixStackMethodTransformer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J \u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0018\u0010\f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\nH\u0002J\u0018\u0010\r\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\nH\u0002J\u0018\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J<\u0010\u000f\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\n2\u0012\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00120\u00112\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J<\u0010\u0019\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\n2\u0012\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00120\u00112\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J4\u0010\u001a\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u001b\u001a\u00020\u00062\u0012\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00120\u00112\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J4\u0010\u001c\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\n2\u0012\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00120\u00112\u0006\u0010\u001d\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J4\u0010\u001e\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00062\u0012\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00120\u00112\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J<\u0010\u001f\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\n2\u0012\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00120\u00112\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u001d\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/fixStack/FixStackMethodTransformer;", "Lorg/jetbrains/kotlin/codegen/optimization/transformer/MethodTransformer;", "()V", "analyzeAndTransformBreakContinueGotos", "", "context", "Lorg/jetbrains/kotlin/codegen/optimization/fixStack/FixStackContext;", "internalClassName", "", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "analyzeAndTransformSaveRestoreStack", "removeAlwaysFalseIfeqMarkers", "removeAlwaysTrueIfeqMarkers", "transform", "transformAfterInlineCallMarker", "actions", "", "Lkotlin/Function0;", "analyzer", "Lorg/jetbrains/kotlin/codegen/optimization/fixStack/FixStackAnalyzer;", "inlineMarker", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "localVariablesManager", "Lorg/jetbrains/kotlin/codegen/optimization/fixStack/LocalVariablesManager;", "transformBeforeInlineCallMarker", "transformBreakContinueGotos", "fixStackContext", "transformRestoreStackMarker", "marker", "transformSaveRestoreStackMarkers", "transformSaveStackMarker", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FixStackMethodTransformer extends MethodTransformer {
    @Override // org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer
    public void transform(String internalClassName, MethodNode methodNode) {
        Intrinsics.checkNotNullParameter(internalClassName, "internalClassName");
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        FixStackContext fixStackContext = new FixStackContext(methodNode);
        if (fixStackContext.hasAnyMarkers()) {
            if (!fixStackContext.getConsistentInlineMarkers()) {
                InsnList instructions = methodNode.instructions;
                Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
                for (AbstractInsnNode abstractInsnNode : new InsnSequence(instructions)) {
                    if (InlineCodegenUtilsKt.isInlineMarker(abstractInsnNode)) {
                        methodNode.instructions.remove(abstractInsnNode);
                    }
                }
            }
            if (fixStackContext.isAnalysisRequired()) {
                analyzeAndTransformBreakContinueGotos(fixStackContext, internalClassName, methodNode);
                removeAlwaysFalseIfeqMarkers(fixStackContext, methodNode);
                analyzeAndTransformSaveRestoreStack(fixStackContext, internalClassName, methodNode);
            }
            removeAlwaysTrueIfeqMarkers(fixStackContext, methodNode);
            removeAlwaysFalseIfeqMarkers(fixStackContext, methodNode);
        }
    }

    private final void analyzeAndTransformBreakContinueGotos(FixStackContext context, String internalClassName, MethodNode methodNode) {
        FixStackAnalyzer fixStackAnalyzer = new FixStackAnalyzer(internalClassName, methodNode, context, false, 8, null);
        fixStackAnalyzer.analyze();
        methodNode.maxStack += fixStackAnalyzer.getMaxExtraStackSize();
        ArrayList arrayList = new ArrayList();
        transformBreakContinueGotos(methodNode, context, arrayList, fixStackAnalyzer);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((Function0) it.next()).invoke();
        }
    }

    private final void analyzeAndTransformSaveRestoreStack(FixStackContext context, String internalClassName, MethodNode methodNode) {
        FixStackAnalyzer fixStackAnalyzer = new FixStackAnalyzer(internalClassName, methodNode, context, false);
        fixStackAnalyzer.analyze();
        ArrayList arrayList = new ArrayList();
        transformSaveRestoreStackMarkers(methodNode, context, arrayList, fixStackAnalyzer);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((Function0) it.next()).invoke();
        }
    }

    private final void removeAlwaysFalseIfeqMarkers(FixStackContext context, MethodNode methodNode) {
        Iterator<T> it = context.getFakeAlwaysFalseIfeqMarkers().iterator();
        while (it.hasNext()) {
            StackTransformationUtilsKt.removeAlwaysFalseIfeq(methodNode, (AbstractInsnNode) it.next());
        }
        context.getFakeAlwaysFalseIfeqMarkers().clear();
    }

    private final void removeAlwaysTrueIfeqMarkers(FixStackContext context, MethodNode methodNode) {
        Iterator<T> it = context.getFakeAlwaysTrueIfeqMarkers().iterator();
        while (it.hasNext()) {
            StackTransformationUtilsKt.replaceAlwaysTrueIfeqWithGoto(methodNode, (AbstractInsnNode) it.next());
        }
        context.getFakeAlwaysTrueIfeqMarkers().clear();
    }

    private final void transformBreakContinueGotos(final MethodNode methodNode, FixStackContext fixStackContext, List<Function0<Unit>> actions, FixStackAnalyzer analyzer) {
        for (final JumpInsnNode jumpInsnNode : fixStackContext.getBreakContinueGotoNodes()) {
            JumpInsnNode jumpInsnNode2 = jumpInsnNode;
            int iIndexOf = methodNode.instructions.indexOf(jumpInsnNode2);
            int iIndexOf2 = methodNode.instructions.indexOf(jumpInsnNode.label);
            int actualStackSize = analyzer.getActualStackSize(jumpInsnNode2);
            LabelNode label = jumpInsnNode.label;
            Intrinsics.checkNotNullExpressionValue(label, "label");
            final int expectedStackSize = analyzer.getExpectedStackSize(label);
            if (actualStackSize >= 0 && expectedStackSize >= 0) {
                final List<FixStackValue> actualStack = analyzer.getActualStack(jumpInsnNode2);
                if (actualStack == null) {
                    throw new AssertionError("Jump at " + iIndexOf + " should be alive");
                }
                actions.add(new Function0<Unit>() { // from class: org.jetbrains.kotlin.codegen.optimization.fixStack.FixStackMethodTransformer$transformBreakContinueGotos$1$2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        MethodNode methodNode2 = methodNode;
                        AbstractInsnNode previous = jumpInsnNode.getPrevious();
                        Intrinsics.checkNotNullExpressionValue(previous, "getPrevious(...)");
                        StackTransformationUtilsKt.replaceMarkerWithPops(methodNode2, previous, expectedStackSize, actualStack);
                    }
                });
            } else {
                if (actualStackSize >= 0 && expectedStackSize < 0) {
                    throw new AssertionError("Live jump " + iIndexOf + " to dead label " + iIndexOf2);
                }
                final AbstractInsnNode previous = jumpInsnNode.getPrevious();
                actions.add(new Function0<Unit>() { // from class: org.jetbrains.kotlin.codegen.optimization.fixStack.FixStackMethodTransformer$transformBreakContinueGotos$1$3
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        methodNode.instructions.remove(previous);
                    }
                });
            }
        }
    }

    private final void transformSaveRestoreStackMarkers(MethodNode methodNode, FixStackContext context, List<Function0<Unit>> actions, FixStackAnalyzer analyzer) {
        LocalVariablesManager localVariablesManager = new LocalVariablesManager(context, methodNode);
        InsnList instructions = methodNode.instructions;
        Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
        for (AbstractInsnNode abstractInsnNode : new InsnSequence(instructions)) {
            PseudoInsn pseudoInsnOrNull = PseudoInsnsKt.parsePseudoInsnOrNull(abstractInsnNode);
            if (pseudoInsnOrNull == PseudoInsn.SAVE_STACK_BEFORE_TRY) {
                transformSaveStackMarker(methodNode, actions, analyzer, abstractInsnNode, localVariablesManager);
            } else if (pseudoInsnOrNull == PseudoInsn.RESTORE_STACK_IN_TRY_CATCH) {
                transformRestoreStackMarker(methodNode, actions, abstractInsnNode, localVariablesManager);
            } else if (InlineCodegenUtilsKt.isBeforeInlineMarker(abstractInsnNode)) {
                transformBeforeInlineCallMarker(methodNode, actions, analyzer, abstractInsnNode, localVariablesManager);
            } else if (InlineCodegenUtilsKt.isAfterInlineMarker(abstractInsnNode)) {
                transformAfterInlineCallMarker(methodNode, actions, analyzer, abstractInsnNode, localVariablesManager);
            }
        }
    }

    private final void transformSaveStackMarker(final MethodNode methodNode, List<Function0<Unit>> actions, FixStackAnalyzer analyzer, final AbstractInsnNode marker, LocalVariablesManager localVariablesManager) {
        List<FixStackValue> stackToSpill = analyzer.getStackToSpill(marker);
        if (stackToSpill != null) {
            final SavedStackDescriptor savedStackDescriptorAllocateVariablesForSaveStackMarker = localVariablesManager.allocateVariablesForSaveStackMarker(marker, stackToSpill);
            actions.add(new Function0<Unit>() { // from class: org.jetbrains.kotlin.codegen.optimization.fixStack.FixStackMethodTransformer.transformSaveStackMarker.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    StackTransformationUtilsKt.saveStack(methodNode, marker, savedStackDescriptorAllocateVariablesForSaveStackMarker);
                }
            });
        } else {
            localVariablesManager.allocateVariablesForSaveStackMarker(marker, CollectionsKt.emptyList());
            actions.add(new Function0<Unit>() { // from class: org.jetbrains.kotlin.codegen.optimization.fixStack.FixStackMethodTransformer.transformSaveStackMarker.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    methodNode.instructions.remove(marker);
                }
            });
        }
    }

    private final void transformRestoreStackMarker(final MethodNode methodNode, List<Function0<Unit>> actions, final AbstractInsnNode marker, LocalVariablesManager localVariablesManager) {
        final SavedStackDescriptor savedStackDescriptor = localVariablesManager.getSavedStackDescriptor(marker);
        actions.add(new Function0<Unit>() { // from class: org.jetbrains.kotlin.codegen.optimization.fixStack.FixStackMethodTransformer.transformRestoreStackMarker.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                StackTransformationUtilsKt.restoreStack(methodNode, marker, savedStackDescriptor);
            }
        });
        localVariablesManager.markRestoreStackMarkerEmitted(marker);
    }

    private final void transformAfterInlineCallMarker(final MethodNode methodNode, List<Function0<Unit>> actions, FixStackAnalyzer analyzer, final AbstractInsnNode inlineMarker, LocalVariablesManager localVariablesManager) {
        final SavedStackDescriptor beforeInlineDescriptor = localVariablesManager.getBeforeInlineDescriptor(inlineMarker);
        List<FixStackValue> actualStack = analyzer.getActualStack(inlineMarker);
        if (actualStack != null && beforeInlineDescriptor.isNotEmpty()) {
            int size = actualStack.size();
            if (size == 0) {
                actions.add(new Function0<Unit>() { // from class: org.jetbrains.kotlin.codegen.optimization.fixStack.FixStackMethodTransformer.transformAfterInlineCallMarker.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        StackTransformationUtilsKt.restoreStack(methodNode, inlineMarker, beforeInlineDescriptor);
                    }
                });
            } else if (size == 1) {
                final FixStackValue fixStackValue = (FixStackValue) CollectionsKt.last((List) actualStack);
                final int iCreateReturnValueVariable = localVariablesManager.createReturnValueVariable(fixStackValue);
                actions.add(new Function0<Unit>() { // from class: org.jetbrains.kotlin.codegen.optimization.fixStack.FixStackMethodTransformer.transformAfterInlineCallMarker.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        StackTransformationUtilsKt.restoreStackWithReturnValue(methodNode, inlineMarker, beforeInlineDescriptor, fixStackValue, iCreateReturnValueVariable);
                    }
                });
            } else {
                throw new AssertionError("Inline method should not leave more than 1 value on stack");
            }
        } else {
            actions.add(new Function0<Unit>() { // from class: org.jetbrains.kotlin.codegen.optimization.fixStack.FixStackMethodTransformer.transformAfterInlineCallMarker.3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    methodNode.instructions.remove(inlineMarker);
                }
            });
        }
        localVariablesManager.markAfterInlineMarkerEmitted(inlineMarker);
    }

    private final void transformBeforeInlineCallMarker(final MethodNode methodNode, List<Function0<Unit>> actions, FixStackAnalyzer analyzer, final AbstractInsnNode inlineMarker, LocalVariablesManager localVariablesManager) {
        List<FixStackValue> stackToSpill = analyzer.getStackToSpill(inlineMarker);
        if (stackToSpill != null) {
            final SavedStackDescriptor savedStackDescriptorAllocateVariablesForBeforeInlineMarker = localVariablesManager.allocateVariablesForBeforeInlineMarker(inlineMarker, stackToSpill);
            actions.add(new Function0<Unit>() { // from class: org.jetbrains.kotlin.codegen.optimization.fixStack.FixStackMethodTransformer.transformBeforeInlineCallMarker.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    StackTransformationUtilsKt.saveStack(methodNode, inlineMarker, savedStackDescriptorAllocateVariablesForBeforeInlineMarker);
                }
            });
        } else {
            localVariablesManager.allocateVariablesForBeforeInlineMarker(inlineMarker, CollectionsKt.emptyList());
            actions.add(new Function0<Unit>() { // from class: org.jetbrains.kotlin.codegen.optimization.fixStack.FixStackMethodTransformer.transformBeforeInlineCallMarker.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    methodNode.instructions.remove(inlineMarker);
                }
            });
        }
    }
}
