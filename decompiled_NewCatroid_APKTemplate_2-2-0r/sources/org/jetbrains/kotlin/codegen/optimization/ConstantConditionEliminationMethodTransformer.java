package org.jetbrains.kotlin.codegen.optimization;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.inline.InlineCodegenUtilsKt;
import org.jetbrains.kotlin.codegen.optimization.common.OptimizationBasicInterpreter;
import org.jetbrains.kotlin.codegen.optimization.common.StrictBasicValue;
import org.jetbrains.kotlin.codegen.optimization.common.UtilKt;
import org.jetbrains.kotlin.codegen.optimization.fixStack.StackTransformationUtilsKt;
import org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.InsnNode;
import org.jetbrains.org.objectweb.asm.tree.IntInsnNode;
import org.jetbrains.org.objectweb.asm.tree.JumpInsnNode;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;
import org.jetbrains.org.objectweb.asm.tree.LdcInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.analysis.AnalyzerException;
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue;
import org.jetbrains.org.objectweb.asm.tree.analysis.Frame;
import org.jetbrains.org.objectweb.asm.tree.analysis.Value;

/* JADX INFO: compiled from: ConstantConditionEliminationMethodTransformer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u0003\u000e\u000f\u0010B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\f\u0010\t\u001a\u00020\n*\u00020\bH\u0002J\f\u0010\u000b\u001a\u00020\n*\u00020\fH\u0002J\f\u0010\r\u001a\u00020\n*\u00020\fH\u0002¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/ConstantConditionEliminationMethodTransformer;", "Lorg/jetbrains/kotlin/codegen/optimization/transformer/MethodTransformer;", "()V", "transform", "", "internalClassName", "", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "hasOptimizableConditions", "", "isIntConst", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "isIntJump", "ConstantConditionsOptimization", "ConstantPropagationInterpreter", "IConstValue", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConstantConditionEliminationMethodTransformer extends MethodTransformer {
    @Override // org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer
    public void transform(String internalClassName, MethodNode methodNode) {
        Intrinsics.checkNotNullParameter(internalClassName, "internalClassName");
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        if (hasOptimizableConditions(methodNode)) {
            while (new ConstantConditionsOptimization(internalClassName, methodNode).run()) {
            }
        }
    }

    private final boolean hasOptimizableConditions(MethodNode methodNode) {
        AbstractInsnNode[] array = methodNode.instructions.toArray();
        Intrinsics.checkNotNull(array);
        for (AbstractInsnNode abstractInsnNode : array) {
            Intrinsics.checkNotNull(abstractInsnNode);
            if (isIntJump(abstractInsnNode)) {
                for (AbstractInsnNode abstractInsnNode2 : array) {
                    Intrinsics.checkNotNull(abstractInsnNode2);
                    if (isIntConst(abstractInsnNode2)) {
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    private final boolean isIntConst(AbstractInsnNode abstractInsnNode) {
        int opcode = abstractInsnNode.getOpcode();
        return (2 <= opcode && opcode < 9) || abstractInsnNode.getOpcode() == 16 || abstractInsnNode.getOpcode() == 17 || (abstractInsnNode.getOpcode() == 18 && (abstractInsnNode instanceof LdcInsnNode) && (((LdcInsnNode) abstractInsnNode).cst instanceof Integer));
    }

    private final boolean isIntJump(AbstractInsnNode abstractInsnNode) {
        int opcode;
        int opcode2 = abstractInsnNode.getOpcode();
        return (153 <= opcode2 && opcode2 < 159) || (159 <= (opcode = abstractInsnNode.getOpcode()) && opcode < 165);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: ConstantConditionEliminationMethodTransformer.kt */
    @Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0014\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\fH\u0002JD\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00132\"\u0010\u0015\u001a\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\u0016j\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r`\u0017H\u0002J4\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u00112\"\u0010\u0015\u001a\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\u0016j\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r`\u0017H\u0002J\u0006\u0010\u0019\u001a\u00020\u001aJB\u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u00112\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001d2\"\u0010\u0015\u001a\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\u0016j\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r`\u0017H\u0002JB\u0010\u001f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u00112\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001d2\"\u0010\u0015\u001a\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\u0016j\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r`\u0017H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/ConstantConditionEliminationMethodTransformer$ConstantConditionsOptimization;", "", "internalClassName", "", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "(Ljava/lang/String;Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;)V", "getInternalClassName", "()Ljava/lang/String;", "getMethodNode", "()Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "collectRewriteActions", "", "Lkotlin/Function0;", "", "rewriteBinaryComparisonOfConsts", "insn", "Lorg/jetbrains/org/objectweb/asm/tree/JumpInsnNode;", "value1", "", "value2", "actions", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "rewriteBinaryComparisonWith0", "run", "", "tryRewriteBinaryComparison", TypedValues.AttributesType.S_FRAME, "Lorg/jetbrains/org/objectweb/asm/tree/analysis/Frame;", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicValue;", "tryRewriteComparisonWithZero", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class ConstantConditionsOptimization {
        private final String internalClassName;
        private final MethodNode methodNode;

        public ConstantConditionsOptimization(String internalClassName, MethodNode methodNode) {
            Intrinsics.checkNotNullParameter(internalClassName, "internalClassName");
            Intrinsics.checkNotNullParameter(methodNode, "methodNode");
            this.internalClassName = internalClassName;
            this.methodNode = methodNode;
        }

        public final String getInternalClassName() {
            return this.internalClassName;
        }

        public final MethodNode getMethodNode() {
            return this.methodNode;
        }

        public final boolean run() {
            List<Function0<Unit>> listCollectRewriteActions = collectRewriteActions();
            Iterator<T> it = listCollectRewriteActions.iterator();
            while (it.hasNext()) {
                ((Function0) it.next()).invoke();
            }
            return !listCollectRewriteActions.isEmpty();
        }

        private final List<Function0<Unit>> collectRewriteActions() {
            ArrayList<Function0<Unit>> arrayList = new ArrayList<>();
            final ArrayList arrayList2 = new ArrayList();
            Frame<BasicValue>[] frameArrAnalyze = MethodTransformer.analyze(this.internalClassName, this.methodNode, new ConstantPropagationInterpreter());
            Intrinsics.checkNotNullExpressionValue(frameArrAnalyze, "analyze(...)");
            AbstractInsnNode[] array = this.methodNode.instructions.toArray();
            int length = frameArrAnalyze.length;
            for (int i = 0; i < length; i++) {
                AbstractInsnNode abstractInsnNode = array[i];
                Frame<BasicValue> frame = frameArrAnalyze[i];
                if (frame == null) {
                    if (!(abstractInsnNode instanceof LabelNode)) {
                        arrayList2.add(abstractInsnNode);
                    }
                } else if (abstractInsnNode instanceof JumpInsnNode) {
                    JumpInsnNode jumpInsnNode = (JumpInsnNode) abstractInsnNode;
                    int opcode = jumpInsnNode.getOpcode();
                    if (153 <= opcode && opcode < 159) {
                        tryRewriteComparisonWithZero(jumpInsnNode, frame, arrayList);
                    } else if (159 <= opcode && opcode < 165) {
                        tryRewriteBinaryComparison(jumpInsnNode, frame, arrayList);
                    }
                }
            }
            if (!arrayList2.isEmpty()) {
                arrayList.add(new Function0<Unit>() { // from class: org.jetbrains.kotlin.codegen.optimization.ConstantConditionEliminationMethodTransformer$ConstantConditionsOptimization$collectRewriteActions$1$1
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
                        InsnList instructions = this.this$0.getMethodNode().instructions;
                        Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
                        UtilKt.removeAll(instructions, arrayList2);
                    }
                });
            }
            return arrayList;
        }

        /* JADX WARN: Code duplicated, block: B:30:0x0060  */
        private final void tryRewriteComparisonWithZero(final JumpInsnNode insn, Frame<BasicValue> frame, ArrayList<Function0<Unit>> actions) {
            Value pVar = StackTransformationUtilsKt.top(frame);
            Intrinsics.checkNotNull(pVar);
            IConstValue iConstValue = pVar instanceof IConstValue ? (IConstValue) pVar : null;
            if (iConstValue == null) {
                return;
            }
            final boolean z = true;
            switch (insn.getOpcode()) {
                case 153:
                    if (iConstValue.getValue() != 0) {
                        z = false;
                    }
                    actions.add(new Function0<Unit>() { // from class: org.jetbrains.kotlin.codegen.optimization.ConstantConditionEliminationMethodTransformer$ConstantConditionsOptimization$tryRewriteComparisonWithZero$1
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
                            InsnList insnList = this.this$0.getMethodNode().instructions;
                            JumpInsnNode jumpInsnNode = insn;
                            boolean z2 = z;
                            JumpInsnNode jumpInsnNode2 = jumpInsnNode;
                            insnList.insertBefore(jumpInsnNode2, new InsnNode(87));
                            if (z2) {
                                insnList.set(jumpInsnNode2, new JumpInsnNode(167, jumpInsnNode.label));
                            } else {
                                insnList.remove(jumpInsnNode2);
                            }
                        }
                    });
                    return;
                case 154:
                    if (iConstValue.getValue() == 0) {
                        z = false;
                    }
                    actions.add(new Function0<Unit>() { // from class: org.jetbrains.kotlin.codegen.optimization.ConstantConditionEliminationMethodTransformer$ConstantConditionsOptimization$tryRewriteComparisonWithZero$1
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
                            InsnList insnList = this.this$0.getMethodNode().instructions;
                            JumpInsnNode jumpInsnNode = insn;
                            boolean z2 = z;
                            JumpInsnNode jumpInsnNode2 = jumpInsnNode;
                            insnList.insertBefore(jumpInsnNode2, new InsnNode(87));
                            if (z2) {
                                insnList.set(jumpInsnNode2, new JumpInsnNode(167, jumpInsnNode.label));
                            } else {
                                insnList.remove(jumpInsnNode2);
                            }
                        }
                    });
                    return;
                case 155:
                    if (iConstValue.getValue() >= 0) {
                        z = false;
                    }
                    actions.add(new Function0<Unit>() { // from class: org.jetbrains.kotlin.codegen.optimization.ConstantConditionEliminationMethodTransformer$ConstantConditionsOptimization$tryRewriteComparisonWithZero$1
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
                            InsnList insnList = this.this$0.getMethodNode().instructions;
                            JumpInsnNode jumpInsnNode = insn;
                            boolean z2 = z;
                            JumpInsnNode jumpInsnNode2 = jumpInsnNode;
                            insnList.insertBefore(jumpInsnNode2, new InsnNode(87));
                            if (z2) {
                                insnList.set(jumpInsnNode2, new JumpInsnNode(167, jumpInsnNode.label));
                            } else {
                                insnList.remove(jumpInsnNode2);
                            }
                        }
                    });
                    return;
                case 156:
                    if (iConstValue.getValue() < 0) {
                        z = false;
                    }
                    actions.add(new Function0<Unit>() { // from class: org.jetbrains.kotlin.codegen.optimization.ConstantConditionEliminationMethodTransformer$ConstantConditionsOptimization$tryRewriteComparisonWithZero$1
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
                            InsnList insnList = this.this$0.getMethodNode().instructions;
                            JumpInsnNode jumpInsnNode = insn;
                            boolean z2 = z;
                            JumpInsnNode jumpInsnNode2 = jumpInsnNode;
                            insnList.insertBefore(jumpInsnNode2, new InsnNode(87));
                            if (z2) {
                                insnList.set(jumpInsnNode2, new JumpInsnNode(167, jumpInsnNode.label));
                            } else {
                                insnList.remove(jumpInsnNode2);
                            }
                        }
                    });
                    return;
                case 157:
                    if (iConstValue.getValue() <= 0) {
                        z = false;
                    }
                    actions.add(new Function0<Unit>() { // from class: org.jetbrains.kotlin.codegen.optimization.ConstantConditionEliminationMethodTransformer$ConstantConditionsOptimization$tryRewriteComparisonWithZero$1
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
                            InsnList insnList = this.this$0.getMethodNode().instructions;
                            JumpInsnNode jumpInsnNode = insn;
                            boolean z2 = z;
                            JumpInsnNode jumpInsnNode2 = jumpInsnNode;
                            insnList.insertBefore(jumpInsnNode2, new InsnNode(87));
                            if (z2) {
                                insnList.set(jumpInsnNode2, new JumpInsnNode(167, jumpInsnNode.label));
                            } else {
                                insnList.remove(jumpInsnNode2);
                            }
                        }
                    });
                    return;
                case 158:
                    if (iConstValue.getValue() > 0) {
                        z = false;
                    }
                    actions.add(new Function0<Unit>() { // from class: org.jetbrains.kotlin.codegen.optimization.ConstantConditionEliminationMethodTransformer$ConstantConditionsOptimization$tryRewriteComparisonWithZero$1
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
                            InsnList insnList = this.this$0.getMethodNode().instructions;
                            JumpInsnNode jumpInsnNode = insn;
                            boolean z2 = z;
                            JumpInsnNode jumpInsnNode2 = jumpInsnNode;
                            insnList.insertBefore(jumpInsnNode2, new InsnNode(87));
                            if (z2) {
                                insnList.set(jumpInsnNode2, new JumpInsnNode(167, jumpInsnNode.label));
                            } else {
                                insnList.remove(jumpInsnNode2);
                            }
                        }
                    });
                    return;
                default:
                    throw new AssertionError("Unexpected instruction: " + InlineCodegenUtilsKt.getInsnText(insn));
            }
        }

        private final void tryRewriteBinaryComparison(JumpInsnNode insn, Frame<BasicValue> frame, ArrayList<Function0<Unit>> actions) {
            Value valuePeek = StackTransformationUtilsKt.peek(frame, 1);
            Intrinsics.checkNotNull(valuePeek);
            BasicValue basicValue = (BasicValue) valuePeek;
            Value valuePeek2 = StackTransformationUtilsKt.peek(frame, 0);
            Intrinsics.checkNotNull(valuePeek2);
            BasicValue basicValue2 = (BasicValue) valuePeek2;
            if ((basicValue instanceof IConstValue) && (basicValue2 instanceof IConstValue)) {
                rewriteBinaryComparisonOfConsts(insn, ((IConstValue) basicValue).getValue(), ((IConstValue) basicValue2).getValue(), actions);
            } else if ((basicValue2 instanceof IConstValue) && ((IConstValue) basicValue2).getValue() == 0) {
                rewriteBinaryComparisonWith0(insn, actions);
            }
        }

        /* JADX WARN: Code duplicated, block: B:18:0x0036  */
        private final void rewriteBinaryComparisonOfConsts(final JumpInsnNode insn, int value1, int value2, ArrayList<Function0<Unit>> actions) {
            final boolean z = true;
            switch (insn.getOpcode()) {
                case 159:
                    if (value1 != value2) {
                        z = false;
                    }
                    actions.add(new Function0<Unit>() { // from class: org.jetbrains.kotlin.codegen.optimization.ConstantConditionEliminationMethodTransformer$ConstantConditionsOptimization$rewriteBinaryComparisonOfConsts$1
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
                            InsnList insnList = this.this$0.getMethodNode().instructions;
                            JumpInsnNode jumpInsnNode = insn;
                            boolean z2 = z;
                            JumpInsnNode jumpInsnNode2 = jumpInsnNode;
                            insnList.insertBefore(jumpInsnNode2, new InsnNode(87));
                            insnList.insertBefore(jumpInsnNode2, new InsnNode(87));
                            if (z2) {
                                insnList.set(jumpInsnNode2, new JumpInsnNode(167, jumpInsnNode.label));
                            } else {
                                insnList.remove(jumpInsnNode2);
                            }
                        }
                    });
                    return;
                case 160:
                    if (value1 == value2) {
                        z = false;
                    }
                    actions.add(new Function0<Unit>() { // from class: org.jetbrains.kotlin.codegen.optimization.ConstantConditionEliminationMethodTransformer$ConstantConditionsOptimization$rewriteBinaryComparisonOfConsts$1
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
                            InsnList insnList = this.this$0.getMethodNode().instructions;
                            JumpInsnNode jumpInsnNode = insn;
                            boolean z2 = z;
                            JumpInsnNode jumpInsnNode2 = jumpInsnNode;
                            insnList.insertBefore(jumpInsnNode2, new InsnNode(87));
                            insnList.insertBefore(jumpInsnNode2, new InsnNode(87));
                            if (z2) {
                                insnList.set(jumpInsnNode2, new JumpInsnNode(167, jumpInsnNode.label));
                            } else {
                                insnList.remove(jumpInsnNode2);
                            }
                        }
                    });
                    return;
                case 161:
                    if (value1 >= value2) {
                        z = false;
                    }
                    actions.add(new Function0<Unit>() { // from class: org.jetbrains.kotlin.codegen.optimization.ConstantConditionEliminationMethodTransformer$ConstantConditionsOptimization$rewriteBinaryComparisonOfConsts$1
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
                            InsnList insnList = this.this$0.getMethodNode().instructions;
                            JumpInsnNode jumpInsnNode = insn;
                            boolean z2 = z;
                            JumpInsnNode jumpInsnNode2 = jumpInsnNode;
                            insnList.insertBefore(jumpInsnNode2, new InsnNode(87));
                            insnList.insertBefore(jumpInsnNode2, new InsnNode(87));
                            if (z2) {
                                insnList.set(jumpInsnNode2, new JumpInsnNode(167, jumpInsnNode.label));
                            } else {
                                insnList.remove(jumpInsnNode2);
                            }
                        }
                    });
                    return;
                case 162:
                    if (value1 < value2) {
                        z = false;
                    }
                    actions.add(new Function0<Unit>() { // from class: org.jetbrains.kotlin.codegen.optimization.ConstantConditionEliminationMethodTransformer$ConstantConditionsOptimization$rewriteBinaryComparisonOfConsts$1
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
                            InsnList insnList = this.this$0.getMethodNode().instructions;
                            JumpInsnNode jumpInsnNode = insn;
                            boolean z2 = z;
                            JumpInsnNode jumpInsnNode2 = jumpInsnNode;
                            insnList.insertBefore(jumpInsnNode2, new InsnNode(87));
                            insnList.insertBefore(jumpInsnNode2, new InsnNode(87));
                            if (z2) {
                                insnList.set(jumpInsnNode2, new JumpInsnNode(167, jumpInsnNode.label));
                            } else {
                                insnList.remove(jumpInsnNode2);
                            }
                        }
                    });
                    return;
                case 163:
                    if (value1 <= value2) {
                        z = false;
                    }
                    actions.add(new Function0<Unit>() { // from class: org.jetbrains.kotlin.codegen.optimization.ConstantConditionEliminationMethodTransformer$ConstantConditionsOptimization$rewriteBinaryComparisonOfConsts$1
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
                            InsnList insnList = this.this$0.getMethodNode().instructions;
                            JumpInsnNode jumpInsnNode = insn;
                            boolean z2 = z;
                            JumpInsnNode jumpInsnNode2 = jumpInsnNode;
                            insnList.insertBefore(jumpInsnNode2, new InsnNode(87));
                            insnList.insertBefore(jumpInsnNode2, new InsnNode(87));
                            if (z2) {
                                insnList.set(jumpInsnNode2, new JumpInsnNode(167, jumpInsnNode.label));
                            } else {
                                insnList.remove(jumpInsnNode2);
                            }
                        }
                    });
                    return;
                case 164:
                    if (value1 > value2) {
                        z = false;
                    }
                    actions.add(new Function0<Unit>() { // from class: org.jetbrains.kotlin.codegen.optimization.ConstantConditionEliminationMethodTransformer$ConstantConditionsOptimization$rewriteBinaryComparisonOfConsts$1
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
                            InsnList insnList = this.this$0.getMethodNode().instructions;
                            JumpInsnNode jumpInsnNode = insn;
                            boolean z2 = z;
                            JumpInsnNode jumpInsnNode2 = jumpInsnNode;
                            insnList.insertBefore(jumpInsnNode2, new InsnNode(87));
                            insnList.insertBefore(jumpInsnNode2, new InsnNode(87));
                            if (z2) {
                                insnList.set(jumpInsnNode2, new JumpInsnNode(167, jumpInsnNode.label));
                            } else {
                                insnList.remove(jumpInsnNode2);
                            }
                        }
                    });
                    return;
                default:
                    throw new AssertionError("Unexpected instruction: " + InlineCodegenUtilsKt.getInsnText(insn));
            }
        }

        private final void rewriteBinaryComparisonWith0(final JumpInsnNode insn, ArrayList<Function0<Unit>> actions) {
            actions.add(new Function0<Unit>() { // from class: org.jetbrains.kotlin.codegen.optimization.ConstantConditionEliminationMethodTransformer$ConstantConditionsOptimization$rewriteBinaryComparisonWith0$1
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
                    int i;
                    InsnList insnList = this.this$0.getMethodNode().instructions;
                    JumpInsnNode jumpInsnNode = insn;
                    JumpInsnNode jumpInsnNode2 = jumpInsnNode;
                    insnList.insertBefore(jumpInsnNode2, new InsnNode(87));
                    switch (jumpInsnNode.getOpcode()) {
                        case 159:
                            i = 153;
                            break;
                        case 160:
                            i = 154;
                            break;
                        case 161:
                            i = 155;
                            break;
                        case 162:
                            i = 156;
                            break;
                        case 163:
                            i = 157;
                            break;
                        case 164:
                            i = 158;
                            break;
                        default:
                            throw new AssertionError("Unexpected instruction: " + InlineCodegenUtilsKt.getInsnText(jumpInsnNode2));
                    }
                    insnList.set(jumpInsnNode2, new JumpInsnNode(i, jumpInsnNode.label));
                }
            });
        }
    }

    /* JADX INFO: compiled from: ConstantConditionEliminationMethodTransformer.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0002\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0096\u0002J\b\u0010\u000b\u001a\u00020\u0003H\u0016J\b\u0010\f\u001a\u00020\rH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/ConstantConditionEliminationMethodTransformer$IConstValue;", "Lorg/jetbrains/kotlin/codegen/optimization/common/StrictBasicValue;", "value", "", "(I)V", "getValue", "()I", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "toString", "", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class IConstValue extends StrictBasicValue {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private static final IConstValue[] ICONST_CACHE;
        private final int value;

        public /* synthetic */ IConstValue(int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(i);
        }

        private IConstValue(int i) {
            super(Type.INT_TYPE);
            this.value = i;
        }

        public final int getValue() {
            return this.value;
        }

        @Override // org.jetbrains.kotlin.codegen.optimization.common.StrictBasicValue, org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue
        public boolean equals(Object other) {
            return other == this || ((other instanceof IConstValue) && ((IConstValue) other).value == this.value);
        }

        @Override // org.jetbrains.kotlin.codegen.optimization.common.StrictBasicValue, org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue
        public int hashCode() {
            return this.value;
        }

        @Override // org.jetbrains.kotlin.codegen.optimization.common.StrictBasicValue, org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue
        public String toString() {
            return "IConst(" + this.value + ')';
        }

        /* JADX INFO: compiled from: ConstantConditionEliminationMethodTransformer.kt */
        @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\tR\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0006¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/ConstantConditionEliminationMethodTransformer$IConstValue$Companion;", "", "()V", "ICONST_CACHE", "", "Lorg/jetbrains/kotlin/codegen/optimization/ConstantConditionEliminationMethodTransformer$IConstValue;", "[Lorg/jetbrains/kotlin/codegen/optimization/ConstantConditionEliminationMethodTransformer$IConstValue;", "of", "value", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final IConstValue of(int value) {
                if (-1 <= value && value < 6) {
                    return IConstValue.ICONST_CACHE[value + 1];
                }
                return new IConstValue(value, null);
            }
        }

        static {
            IConstValue[] iConstValueArr = new IConstValue[7];
            for (int i = 0; i < 7; i++) {
                iConstValueArr[i] = new IConstValue(i - 1);
            }
            ICONST_CACHE = iConstValueArr;
        }
    }

    /* JADX INFO: compiled from: ConstantConditionEliminationMethodTransformer.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0004H\u0016J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0016¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/ConstantConditionEliminationMethodTransformer$ConstantPropagationInterpreter;", "Lorg/jetbrains/kotlin/codegen/optimization/common/OptimizationBasicInterpreter;", "()V", "merge", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicValue;", RegisterSpec.PREFIX, "w", "newOperation", "insn", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class ConstantPropagationInterpreter extends OptimizationBasicInterpreter {
        @Override // org.jetbrains.kotlin.codegen.optimization.common.OptimizationBasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
        public BasicValue newOperation(AbstractInsnNode insn) throws AnalyzerException {
            IConstValue iConstValueNewOperation;
            Intrinsics.checkNotNullParameter(insn, "insn");
            int opcode = insn.getOpcode();
            if (2 <= opcode && opcode < 9) {
                return IConstValue.INSTANCE.of(insn.getOpcode() - 3);
            }
            if (opcode == 16 || opcode == 17) {
                return IConstValue.INSTANCE.of(((IntInsnNode) insn).operand);
            }
            if (opcode == 18) {
                Object obj = ((LdcInsnNode) insn).cst;
                if (obj instanceof Integer) {
                    iConstValueNewOperation = IConstValue.INSTANCE.of(((Number) obj).intValue());
                } else {
                    iConstValueNewOperation = super.newOperation(insn);
                }
                Intrinsics.checkNotNull(iConstValueNewOperation);
                return iConstValueNewOperation;
            }
            BasicValue basicValueNewOperation = super.newOperation(insn);
            Intrinsics.checkNotNullExpressionValue(basicValueNewOperation, "newOperation(...)");
            return basicValueNewOperation;
        }

        @Override // org.jetbrains.kotlin.codegen.optimization.common.OptimizationBasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
        public BasicValue merge(BasicValue v, BasicValue w) {
            Intrinsics.checkNotNullParameter(v, "v");
            Intrinsics.checkNotNullParameter(w, "w");
            if ((v instanceof IConstValue) && (w instanceof IConstValue) && Intrinsics.areEqual(v, w)) {
                return v;
            }
            BasicValue basicValueMerge = super.merge(v, w);
            Intrinsics.checkNotNullExpressionValue(basicValueMerge, "merge(...)");
            return basicValueMerge;
        }
    }
}
