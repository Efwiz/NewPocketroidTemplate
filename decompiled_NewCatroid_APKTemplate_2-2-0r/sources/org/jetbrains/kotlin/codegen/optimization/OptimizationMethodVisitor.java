package org.jetbrains.kotlin.codegen.optimization;

import com.reandroid.dex.sections.Marker;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.codegen.TransformationMethodVisitor;
import org.jetbrains.kotlin.codegen.coroutines.UninitializedStoresProcessor;
import org.jetbrains.kotlin.codegen.inline.InplaceArgumentsMethodTransformer;
import org.jetbrains.kotlin.codegen.optimization.boxing.PopBackwardPropagationTransformer;
import org.jetbrains.kotlin.codegen.optimization.boxing.RedundantBoxingMethodTransformer;
import org.jetbrains.kotlin.codegen.optimization.boxing.StackPeepholeOptimizationsTransformer;
import org.jetbrains.kotlin.codegen.optimization.common.UtilKt;
import org.jetbrains.kotlin.codegen.optimization.nullCheck.RedundantNullCheckMethodTransformer;
import org.jetbrains.kotlin.codegen.optimization.temporaryVals.TemporaryVariablesEliminationTransformer;
import org.jetbrains.kotlin.codegen.optimization.transformer.CompositeMethodTransformer;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.TryCatchBlockNode;

/* JADX INFO: compiled from: OptimizationMethodVisitor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bBQ\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\u000b\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u000b\u0012\u0010\u0010\u000e\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u000b\u0018\u00010\u000f¢\u0006\u0002\u0010\u0010J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0014R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0011\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0015\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0014¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/OptimizationMethodVisitor;", "Lorg/jetbrains/kotlin/codegen/TransformationMethodVisitor;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", "mandatoryTransformationsOnly", "", "generationState", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "access", "", "name", "", "desc", "signature", "exceptions", "", "(Lorg/jetbrains/org/objectweb/asm/MethodVisitor;ZLorg/jetbrains/kotlin/codegen/state/GenerationState;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)V", "normalizationMethodTransformer", "Lorg/jetbrains/kotlin/codegen/optimization/transformer/CompositeMethodTransformer;", "getNormalizationMethodTransformer", "()Lorg/jetbrains/kotlin/codegen/optimization/transformer/CompositeMethodTransformer;", "optimizationTransformer", "getOptimizationTransformer", "performTransformations", "", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class OptimizationMethodVisitor extends TransformationMethodVisitor {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final int MEMORY_LIMIT_BY_METHOD_MB = 50;
    private final GenerationState generationState;
    private final boolean mandatoryTransformationsOnly;
    private final CompositeMethodTransformer normalizationMethodTransformer;
    private final CompositeMethodTransformer optimizationTransformer;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public OptimizationMethodVisitor(MethodVisitor delegate, boolean z, GenerationState generationState, int i, String name2, String desc, String str, String[] strArr) {
        super(delegate, i, name2, desc, str, strArr, 0, 64, null);
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        Intrinsics.checkNotNullParameter(generationState, "generationState");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(desc, "desc");
        this.mandatoryTransformationsOnly = z;
        this.generationState = generationState;
        this.normalizationMethodTransformer = new CompositeMethodTransformer(new InplaceArgumentsMethodTransformer(), new FixStackWithLabelNormalizationMethodTransformer(), new MethodVerifier("AFTER mandatory stack transformations", generationState));
        this.optimizationTransformer = new CompositeMethodTransformer(new CapturedVarsOptimizationMethodTransformer(), new RedundantNullCheckMethodTransformer(generationState), new RedundantCheckCastEliminationMethodTransformer(), new ConstantConditionEliminationMethodTransformer(), new RedundantBoxingMethodTransformer(generationState), new TemporaryVariablesEliminationTransformer(generationState), new StackPeepholeOptimizationsTransformer(), new PopBackwardPropagationTransformer(), new DeadCodeEliminationMethodTransformer(), new RedundantGotoMethodTransformer(), new RedundantNopsCleanupMethodTransformer(), new NegatedJumpsMethodTransformer(), RedundantCheckcastsBeforeAastoreMethodTransformer.INSTANCE, new MethodVerifier("AFTER optimizations", generationState));
    }

    public final CompositeMethodTransformer getNormalizationMethodTransformer() {
        return this.normalizationMethodTransformer;
    }

    public final CompositeMethodTransformer getOptimizationTransformer() {
        return this.optimizationTransformer;
    }

    @Override // org.jetbrains.kotlin.codegen.TransformationMethodVisitor
    protected void performTransformations(MethodNode methodNode) {
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        this.normalizationMethodTransformer.transform("fake", methodNode);
        new UninitializedStoresProcessor(methodNode).run();
        if (!this.mandatoryTransformationsOnly && INSTANCE.canBeOptimized(methodNode) && !this.generationState.getConfig().getDisableOptimization()) {
            this.optimizationTransformer.transform("fake", methodNode);
        }
        new DeadCodeEliminationMethodTransformer().transform("fake", methodNode);
        UtilKt.prepareForEmitting(methodNode);
    }

    /* JADX INFO: compiled from: OptimizationMethodVisitor.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u000e\u0010\n\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0002J\u0010\u0010\u000e\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0002J\u0018\u0010\u000f\u001a\u00020\u0004*\u0004\u0018\u00010\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0010H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/OptimizationMethodVisitor$Companion;", "", "()V", "MEMORY_LIMIT_BY_METHOD_MB", "", "TRY_CATCH_BLOCKS_SOFT_LIMIT", "canBeOptimized", "", "node", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "canBeOptimizedUsingSourceInterpreter", "getTotalFramesWeight", "", "size", "getTotalTcbSize", "countInsnsWithFramesUntil", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "end", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean canBeOptimized(MethodNode node) {
            Intrinsics.checkNotNullParameter(node, "node");
            return (node.tryCatchBlocks.size() <= 16 || getTotalFramesWeight(getTotalTcbSize(node), node) <= 50) && getTotalFramesWeight(countInsnsWithFramesUntil(node.instructions.getFirst(), null), node) < 50;
        }

        public final boolean canBeOptimizedUsingSourceInterpreter(MethodNode node) {
            Intrinsics.checkNotNullParameter(node, "node");
            int iCountInsnsWithFramesUntil = countInsnsWithFramesUntil(node.instructions.getFirst(), null);
            return (node.tryCatchBlocks.size() <= 16 || getTotalFramesWeight(getTotalTcbSize(node) * iCountInsnsWithFramesUntil, node) <= 50) && getTotalFramesWeight(iCountInsnsWithFramesUntil * iCountInsnsWithFramesUntil, node) < 50;
        }

        private final int countInsnsWithFramesUntil(AbstractInsnNode abstractInsnNode, AbstractInsnNode abstractInsnNode2) {
            AbstractInsnNode next;
            int i = 0;
            while (!Intrinsics.areEqual(abstractInsnNode, abstractInsnNode2) && abstractInsnNode != null) {
                if ((abstractInsnNode.getType() != 15 && abstractInsnNode.getType() != 14 && abstractInsnNode.getType() != 8 && abstractInsnNode.getOpcode() != 0) || ((next = abstractInsnNode.getNext()) != null && next.getType() == 8)) {
                    i++;
                }
                abstractInsnNode = abstractInsnNode.getNext();
            }
            return i;
        }

        private final long getTotalFramesWeight(int size, MethodNode node) {
            return (((long) size) * ((long) (node.maxLocals + node.maxStack))) / ((long) 1048576);
        }

        private final int getTotalTcbSize(MethodNode node) {
            List<TryCatchBlockNode> tryCatchBlocks = node.tryCatchBlocks;
            Intrinsics.checkNotNullExpressionValue(tryCatchBlocks, "tryCatchBlocks");
            int iCountInsnsWithFramesUntil = 0;
            for (TryCatchBlockNode tryCatchBlockNode : tryCatchBlocks) {
                iCountInsnsWithFramesUntil += OptimizationMethodVisitor.INSTANCE.countInsnsWithFramesUntil(tryCatchBlockNode.start, tryCatchBlockNode.end);
            }
            return iCountInsnsWithFramesUntil;
        }
    }
}
