package org.jetbrains.kotlin.codegen.optimization.boxing;

import com.reandroid.arsc.value.CompoundEntry;
import com.reandroid.dex.sections.Marker;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.OptimizationMethodVisitor;
import org.jetbrains.kotlin.codegen.optimization.common.FastMethodAnalyzer;
import org.jetbrains.kotlin.codegen.optimization.fixStack.StackTransformationUtilsKt;
import org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.InsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.analysis.AnalyzerException;
import org.jetbrains.org.objectweb.asm.tree.analysis.Frame;
import org.jetbrains.org.objectweb.asm.tree.analysis.SourceInterpreter;
import org.jetbrains.org.objectweb.asm.tree.analysis.SourceValue;
import org.jetbrains.org.objectweb.asm.tree.analysis.Value;

/* JADX INFO: compiled from: PopBackwardPropagationTransformer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\tB\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/boxing/PopBackwardPropagationTransformer;", "Lorg/jetbrains/kotlin/codegen/optimization/transformer/MethodTransformer;", "()V", "transform", "", "internalClassName", "", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "Transformer", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PopBackwardPropagationTransformer extends MethodTransformer {
    @Override // org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer
    public void transform(String internalClassName, MethodNode methodNode) throws AnalyzerException {
        Intrinsics.checkNotNullParameter(internalClassName, "internalClassName");
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        if (OptimizationMethodVisitor.INSTANCE.canBeOptimizedUsingSourceInterpreter(methodNode)) {
            Iterable instructions = methodNode.instructions;
            Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
            Iterable<AbstractInsnNode> iterable = instructions;
            if ((iterable instanceof Collection) && ((Collection) iterable).isEmpty()) {
                return;
            }
            for (AbstractInsnNode abstractInsnNode : iterable) {
                Intrinsics.checkNotNull(abstractInsnNode);
                if (PopBackwardPropagationTransformerKt.isPop(abstractInsnNode) || PopBackwardPropagationTransformerKt.isPurePush(abstractInsnNode)) {
                    new Transformer(methodNode).transform();
                    return;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: PopBackwardPropagationTransformer.kt */
    @Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001:\u0001%B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0018\u001a\u00020\bJA\u0010\u0019\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\t*\u00020\u00072\u0016\u0010\u001a\u001a\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0004\u0012\u00020\u001c\u0018\u00010\u001b0\u00142\u0006\u0010\u001d\u001a\u00020\u001eH\u0002¢\u0006\u0002\u0010\u001fJ\f\u0010 \u001a\u00020!*\u00020\u001cH\u0002J\u0012\u0010\"\u001a\u00020\b*\b\u0012\u0004\u0012\u00020\u00070#H\u0002J\f\u0010$\u001a\u00020!*\u00020\u0007H\u0002R\u001e\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u000b\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\f\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\r\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0010\u001a\n \u0012*\u0004\u0018\u00010\u00110\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R6\u0010\u0013\u001a(\u0012\f\u0012\n \u0012*\u0004\u0018\u00010\u00070\u0007 \u0012*\u0014\u0012\u000e\b\u0001\u0012\n \u0012*\u0004\u0018\u00010\u00070\u0007\u0018\u00010\u00140\u0014X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0015R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/boxing/PopBackwardPropagationTransformer$Transformer;", "", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "(Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;)V", "INSERT_POP1_AFTER", "Lkotlin/Function1;", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "", "Lorg/jetbrains/kotlin/codegen/optimization/boxing/Transformation;", "INSERT_POP2_AFTER", "REPLACE_WITH_NOP", "REPLACE_WITH_POP1", "REPLACE_WITH_POP2", "dontTouchInsnIndices", "Ljava/util/BitSet;", "insnList", "Lorg/jetbrains/org/objectweb/asm/tree/InsnList;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "insns", "", "[Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "getMethodNode", "()Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "transform", "combineWithPop", "frames", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/Frame;", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/SourceValue;", "resultSize", "", "(Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;[Lorg/jetbrains/org/objectweb/asm/tree/analysis/Frame;I)Lkotlin/jvm/functions/Function1;", "longerWhenFusedWithPop", "", "markAsDontTouch", "", "shouldKeep", "HazardsTrackingInterpreter", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class Transformer {
        private final Function1<AbstractInsnNode, Unit> INSERT_POP1_AFTER;
        private final Function1<AbstractInsnNode, Unit> INSERT_POP2_AFTER;
        private final Function1<AbstractInsnNode, Unit> REPLACE_WITH_NOP;
        private final Function1<AbstractInsnNode, Unit> REPLACE_WITH_POP1;
        private final Function1<AbstractInsnNode, Unit> REPLACE_WITH_POP2;
        private final BitSet dontTouchInsnIndices;
        private final InsnList insnList;
        private final AbstractInsnNode[] insns;
        private final MethodNode methodNode;

        public Transformer(MethodNode methodNode) {
            Intrinsics.checkNotNullParameter(methodNode, "methodNode");
            this.methodNode = methodNode;
            this.REPLACE_WITH_NOP = new Function1<AbstractInsnNode, Unit>() { // from class: org.jetbrains.kotlin.codegen.optimization.boxing.PopBackwardPropagationTransformer$Transformer$REPLACE_WITH_NOP$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(AbstractInsnNode abstractInsnNode) {
                    invoke2(abstractInsnNode);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(AbstractInsnNode it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    this.this$0.insnList.set(it, new InsnNode(0));
                }
            };
            this.REPLACE_WITH_POP1 = new Function1<AbstractInsnNode, Unit>() { // from class: org.jetbrains.kotlin.codegen.optimization.boxing.PopBackwardPropagationTransformer$Transformer$REPLACE_WITH_POP1$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(AbstractInsnNode abstractInsnNode) {
                    invoke2(abstractInsnNode);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(AbstractInsnNode it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    this.this$0.insnList.set(it, new InsnNode(87));
                }
            };
            this.REPLACE_WITH_POP2 = new Function1<AbstractInsnNode, Unit>() { // from class: org.jetbrains.kotlin.codegen.optimization.boxing.PopBackwardPropagationTransformer$Transformer$REPLACE_WITH_POP2$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(AbstractInsnNode abstractInsnNode) {
                    invoke2(abstractInsnNode);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(AbstractInsnNode it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    this.this$0.insnList.set(it, new InsnNode(88));
                }
            };
            this.INSERT_POP1_AFTER = new Function1<AbstractInsnNode, Unit>() { // from class: org.jetbrains.kotlin.codegen.optimization.boxing.PopBackwardPropagationTransformer$Transformer$INSERT_POP1_AFTER$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(AbstractInsnNode abstractInsnNode) {
                    invoke2(abstractInsnNode);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(AbstractInsnNode it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    this.this$0.insnList.insert(it, new InsnNode(87));
                }
            };
            this.INSERT_POP2_AFTER = new Function1<AbstractInsnNode, Unit>() { // from class: org.jetbrains.kotlin.codegen.optimization.boxing.PopBackwardPropagationTransformer$Transformer$INSERT_POP2_AFTER$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(AbstractInsnNode abstractInsnNode) {
                    invoke2(abstractInsnNode);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(AbstractInsnNode it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    this.this$0.insnList.insert(it, new InsnNode(88));
                }
            };
            InsnList insnList = methodNode.instructions;
            this.insnList = insnList;
            AbstractInsnNode[] array = insnList.toArray();
            this.insns = array;
            this.dontTouchInsnIndices = new BitSet(array.length);
        }

        public final MethodNode getMethodNode() {
            return this.methodNode;
        }

        /* JADX WARN: Code duplicated, block: B:103:0x012e A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:115:0x01af A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:117:0x019d A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:81:0x01a3  */
        public final void transform() throws AnalyzerException {
            SourceValue sourceValue;
            HashMap map;
            Frame<SourceValue>[] frameArrAnalyze = new FastMethodAnalyzer("fake", this.methodNode, new HazardsTrackingInterpreter(), false, 8, null).analyze();
            AbstractInsnNode[] insns = this.insns;
            Intrinsics.checkNotNullExpressionValue(insns, "insns");
            int length = insns.length;
            for (int i = 0; i < length; i++) {
                AbstractInsnNode abstractInsnNode = insns[i];
                Frame<SourceValue> frame = frameArrAnalyze[i];
                if (frame != null) {
                    switch (abstractInsnNode.getOpcode()) {
                        case 87:
                            SourceValue sourceValue2 = (SourceValue) StackTransformationUtilsKt.top(frame);
                            if (sourceValue2 != null) {
                                Set<AbstractInsnNode> insns2 = sourceValue2.insns;
                                Intrinsics.checkNotNullExpressionValue(insns2, "insns");
                                Set<AbstractInsnNode> set = insns2;
                                if (!(set instanceof Collection) || !set.isEmpty()) {
                                    Iterator<T> it = set.iterator();
                                    while (true) {
                                        if (it.hasNext()) {
                                            AbstractInsnNode abstractInsnNode2 = (AbstractInsnNode) it.next();
                                            Intrinsics.checkNotNull(abstractInsnNode2);
                                            if (shouldKeep(abstractInsnNode2)) {
                                            }
                                        } else if (!longerWhenFusedWithPop(sourceValue2)) {
                                            break;
                                        }
                                        Set<AbstractInsnNode> insns3 = sourceValue2.insns;
                                        Intrinsics.checkNotNullExpressionValue(insns3, "insns");
                                        markAsDontTouch(insns3);
                                    }
                                } else if (!longerWhenFusedWithPop(sourceValue2)) {
                                    Set<AbstractInsnNode> insns4 = sourceValue2.insns;
                                    Intrinsics.checkNotNullExpressionValue(insns4, "insns");
                                    markAsDontTouch(insns4);
                                }
                            }
                            break;
                        case 88:
                            List listPeekWords = StackTransformationUtilsKt.peekWords(frame, 2);
                            if (listPeekWords != null) {
                                Iterator it2 = listPeekWords.iterator();
                                while (it2.hasNext()) {
                                    Set<AbstractInsnNode> insns5 = ((SourceValue) it2.next()).insns;
                                    Intrinsics.checkNotNullExpressionValue(insns5, "insns");
                                    markAsDontTouch(insns5);
                                }
                            }
                            break;
                        case 90:
                            List listPeekWords2 = StackTransformationUtilsKt.peekWords(frame, 1, 1);
                            if (listPeekWords2 != null) {
                                Iterator it3 = listPeekWords2.iterator();
                                while (it3.hasNext()) {
                                    Set<AbstractInsnNode> insns6 = ((SourceValue) it3.next()).insns;
                                    Intrinsics.checkNotNullExpressionValue(insns6, "insns");
                                    markAsDontTouch(insns6);
                                }
                            }
                            break;
                        case 91:
                            List listPeekWords3 = StackTransformationUtilsKt.peekWords(frame, 1, 2);
                            if (listPeekWords3 != null) {
                                Iterator it4 = listPeekWords3.iterator();
                                while (it4.hasNext()) {
                                    Set<AbstractInsnNode> insns7 = ((SourceValue) it4.next()).insns;
                                    Intrinsics.checkNotNullExpressionValue(insns7, "insns");
                                    markAsDontTouch(insns7);
                                }
                            }
                            break;
                        case 93:
                            List listPeekWords4 = StackTransformationUtilsKt.peekWords(frame, 2, 1);
                            if (listPeekWords4 != null) {
                                Iterator it5 = listPeekWords4.iterator();
                                while (it5.hasNext()) {
                                    Set<AbstractInsnNode> insns8 = ((SourceValue) it5.next()).insns;
                                    Intrinsics.checkNotNullExpressionValue(insns8, "insns");
                                    markAsDontTouch(insns8);
                                }
                            }
                            break;
                        case 94:
                            List listPeekWords5 = StackTransformationUtilsKt.peekWords(frame, 2, 2);
                            if (listPeekWords5 != null) {
                                Iterator it6 = listPeekWords5.iterator();
                                while (it6.hasNext()) {
                                    Set<AbstractInsnNode> insns9 = ((SourceValue) it6.next()).insns;
                                    Intrinsics.checkNotNullExpressionValue(insns9, "insns");
                                    markAsDontTouch(insns9);
                                }
                            }
                            break;
                    }
                }
            }
            HashMap map2 = new HashMap();
            AbstractInsnNode[] insns10 = this.insns;
            Intrinsics.checkNotNullExpressionValue(insns10, "insns");
            int length2 = insns10.length;
            for (int i2 = 0; i2 < length2; i2++) {
                AbstractInsnNode abstractInsnNode3 = insns10[i2];
                Frame<SourceValue> frame2 = frameArrAnalyze[i2];
                if (frame2 != null && abstractInsnNode3.getOpcode() == 87 && (sourceValue = (SourceValue) StackTransformationUtilsKt.top(frame2)) != null) {
                    Set<AbstractInsnNode> insns11 = sourceValue.insns;
                    Intrinsics.checkNotNullExpressionValue(insns11, "insns");
                    Set<AbstractInsnNode> set2 = insns11;
                    if ((set2 instanceof Collection) && set2.isEmpty()) {
                        map = map2;
                        Intrinsics.checkNotNull(abstractInsnNode3);
                        map.put(abstractInsnNode3, this.REPLACE_WITH_NOP);
                        Set<AbstractInsnNode> insns12 = sourceValue.insns;
                        Intrinsics.checkNotNullExpressionValue(insns12, "insns");
                        for (AbstractInsnNode abstractInsnNode4 : insns12) {
                            if (!map.containsKey(abstractInsnNode4)) {
                                Intrinsics.checkNotNull(abstractInsnNode4);
                                map.put(abstractInsnNode4, combineWithPop(abstractInsnNode4, frameArrAnalyze, sourceValue.size));
                            }
                        }
                    } else {
                        Iterator<T> it7 = set2.iterator();
                        while (true) {
                            if (it7.hasNext()) {
                                AbstractInsnNode abstractInsnNode5 = (AbstractInsnNode) it7.next();
                                Intrinsics.checkNotNull(abstractInsnNode5);
                                if (shouldKeep(abstractInsnNode5)) {
                                }
                            } else {
                                map = map2;
                                Intrinsics.checkNotNull(abstractInsnNode3);
                                map.put(abstractInsnNode3, this.REPLACE_WITH_NOP);
                                Set<AbstractInsnNode> insns13 = sourceValue.insns;
                                Intrinsics.checkNotNullExpressionValue(insns13, "insns");
                                while (r6.hasNext()) {
                                    if (!map.containsKey(abstractInsnNode4)) {
                                        Intrinsics.checkNotNull(abstractInsnNode4);
                                        map.put(abstractInsnNode4, combineWithPop(abstractInsnNode4, frameArrAnalyze, sourceValue.size));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            for (Map.Entry entry : map2.entrySet()) {
                Intrinsics.checkNotNull(entry);
                AbstractInsnNode abstractInsnNode6 = (AbstractInsnNode) entry.getKey();
                Function1 function1 = (Function1) entry.getValue();
                Intrinsics.checkNotNull(abstractInsnNode6);
                function1.invoke(abstractInsnNode6);
            }
        }

        /* JADX INFO: compiled from: PopBackwardPropagationTransformer.kt */
        @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010!\n\u0002\b\u0004\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0004H\u0016J\u0018\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u0004H\u0016J \u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u000e\u0010\f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\rH\u0016J(\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u0004H\u0016J\u0018\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u0004H\u0016¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/boxing/PopBackwardPropagationTransformer$Transformer$HazardsTrackingInterpreter;", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/SourceInterpreter;", "(Lorg/jetbrains/kotlin/codegen/optimization/boxing/PopBackwardPropagationTransformer$Transformer;)V", "binaryOperation", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/SourceValue;", "insn", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "value1", "value2", "copyOperation", "value", "naryOperation", CompoundEntry.NAME_values, "", "ternaryOperation", "value3", "unaryOperation", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
        private final class HazardsTrackingInterpreter extends SourceInterpreter {
            public HazardsTrackingInterpreter() {
                super(589824);
            }

            @Override // org.jetbrains.org.objectweb.asm.tree.analysis.SourceInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
            public /* bridge */ /* synthetic */ Value naryOperation(AbstractInsnNode abstractInsnNode, List list) {
                return naryOperation(abstractInsnNode, (List<? extends SourceValue>) list);
            }

            @Override // org.jetbrains.org.objectweb.asm.tree.analysis.SourceInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
            public SourceValue naryOperation(AbstractInsnNode insn, List<? extends SourceValue> values) {
                Intrinsics.checkNotNullParameter(insn, "insn");
                Intrinsics.checkNotNullParameter(values, "values");
                for (SourceValue sourceValue : values) {
                    Transformer transformer = Transformer.this;
                    Set<AbstractInsnNode> insns = sourceValue.insns;
                    Intrinsics.checkNotNullExpressionValue(insns, "insns");
                    transformer.markAsDontTouch(insns);
                }
                SourceValue sourceValueNaryOperation = super.naryOperation(insn, values);
                Intrinsics.checkNotNullExpressionValue(sourceValueNaryOperation, "naryOperation(...)");
                return sourceValueNaryOperation;
            }

            @Override // org.jetbrains.org.objectweb.asm.tree.analysis.SourceInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
            public SourceValue copyOperation(AbstractInsnNode insn, SourceValue value) {
                Intrinsics.checkNotNullParameter(insn, "insn");
                Intrinsics.checkNotNullParameter(value, "value");
                Transformer transformer = Transformer.this;
                Set<AbstractInsnNode> insns = value.insns;
                Intrinsics.checkNotNullExpressionValue(insns, "insns");
                transformer.markAsDontTouch(insns);
                SourceValue sourceValueCopyOperation = super.copyOperation(insn, value);
                Intrinsics.checkNotNullExpressionValue(sourceValueCopyOperation, "copyOperation(...)");
                return sourceValueCopyOperation;
            }

            @Override // org.jetbrains.org.objectweb.asm.tree.analysis.SourceInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
            public SourceValue unaryOperation(AbstractInsnNode insn, SourceValue value) {
                Intrinsics.checkNotNullParameter(insn, "insn");
                Intrinsics.checkNotNullParameter(value, "value");
                Transformer transformer = Transformer.this;
                Set<AbstractInsnNode> insns = value.insns;
                Intrinsics.checkNotNullExpressionValue(insns, "insns");
                transformer.markAsDontTouch(insns);
                SourceValue sourceValueUnaryOperation = super.unaryOperation(insn, value);
                Intrinsics.checkNotNullExpressionValue(sourceValueUnaryOperation, "unaryOperation(...)");
                return sourceValueUnaryOperation;
            }

            @Override // org.jetbrains.org.objectweb.asm.tree.analysis.SourceInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
            public SourceValue binaryOperation(AbstractInsnNode insn, SourceValue value1, SourceValue value2) {
                Intrinsics.checkNotNullParameter(insn, "insn");
                Intrinsics.checkNotNullParameter(value1, "value1");
                Intrinsics.checkNotNullParameter(value2, "value2");
                Transformer transformer = Transformer.this;
                Set<AbstractInsnNode> insns = value1.insns;
                Intrinsics.checkNotNullExpressionValue(insns, "insns");
                transformer.markAsDontTouch(insns);
                Transformer transformer2 = Transformer.this;
                Set<AbstractInsnNode> insns2 = value2.insns;
                Intrinsics.checkNotNullExpressionValue(insns2, "insns");
                transformer2.markAsDontTouch(insns2);
                SourceValue sourceValueBinaryOperation = super.binaryOperation(insn, value1, value2);
                Intrinsics.checkNotNullExpressionValue(sourceValueBinaryOperation, "binaryOperation(...)");
                return sourceValueBinaryOperation;
            }

            @Override // org.jetbrains.org.objectweb.asm.tree.analysis.SourceInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
            public SourceValue ternaryOperation(AbstractInsnNode insn, SourceValue value1, SourceValue value2, SourceValue value3) {
                Intrinsics.checkNotNullParameter(insn, "insn");
                Intrinsics.checkNotNullParameter(value1, "value1");
                Intrinsics.checkNotNullParameter(value2, "value2");
                Intrinsics.checkNotNullParameter(value3, "value3");
                Transformer transformer = Transformer.this;
                Set<AbstractInsnNode> insns = value1.insns;
                Intrinsics.checkNotNullExpressionValue(insns, "insns");
                transformer.markAsDontTouch(insns);
                Transformer transformer2 = Transformer.this;
                Set<AbstractInsnNode> insns2 = value2.insns;
                Intrinsics.checkNotNullExpressionValue(insns2, "insns");
                transformer2.markAsDontTouch(insns2);
                Transformer transformer3 = Transformer.this;
                Set<AbstractInsnNode> insns3 = value3.insns;
                Intrinsics.checkNotNullExpressionValue(insns3, "insns");
                transformer3.markAsDontTouch(insns3);
                SourceValue sourceValueTernaryOperation = super.ternaryOperation(insn, value1, value2, value3);
                Intrinsics.checkNotNullExpressionValue(sourceValueTernaryOperation, "ternaryOperation(...)");
                return sourceValueTernaryOperation;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void markAsDontTouch(Collection<? extends AbstractInsnNode> collection) {
            Iterator<T> it = collection.iterator();
            while (it.hasNext()) {
                this.dontTouchInsnIndices.set(this.insnList.indexOf((AbstractInsnNode) it.next()), true);
            }
        }

        private final boolean longerWhenFusedWithPop(SourceValue sourceValue) {
            Set<AbstractInsnNode> insns = sourceValue.insns;
            Intrinsics.checkNotNullExpressionValue(insns, "insns");
            int i = 0;
            for (AbstractInsnNode abstractInsnNode : insns) {
                Intrinsics.checkNotNull(abstractInsnNode);
                if (PopBackwardPropagationTransformerKt.isPurePush(abstractInsnNode)) {
                    i--;
                } else if (!BoxingInterpreterKt.isPrimitiveBoxing(abstractInsnNode) && !PopBackwardPropagationTransformerKt.isPrimitiveTypeConversion(abstractInsnNode)) {
                    i++;
                }
            }
            return i > 0;
        }

        private final Function1<AbstractInsnNode, Unit> combineWithPop(AbstractInsnNode abstractInsnNode, Frame<SourceValue>[] frameArr, int i) {
            if (PopBackwardPropagationTransformerKt.isPurePush(abstractInsnNode)) {
                return this.REPLACE_WITH_NOP;
            }
            if (!BoxingInterpreterKt.isPrimitiveBoxing(abstractInsnNode) && !PopBackwardPropagationTransformerKt.isPrimitiveTypeConversion(abstractInsnNode)) {
                if (i == 1) {
                    return this.INSERT_POP1_AFTER;
                }
                if (i == 2) {
                    return this.INSERT_POP2_AFTER;
                }
                throw new AssertionError("Unexpected pop value size: " + i);
            }
            int iIndexOf = this.insnList.indexOf(abstractInsnNode);
            Frame<SourceValue> frame = frameArr[iIndexOf];
            if (frame == null) {
                throw new AssertionError("dead instruction #" + iIndexOf + " used by non-dead instruction");
            }
            SourceValue sourceValue = (SourceValue) StackTransformationUtilsKt.top(frame);
            if (sourceValue == null) {
                throw new AssertionError("coercion instruction at #" + iIndexOf + " has no input");
            }
            int i2 = sourceValue.size;
            if (i2 == 1) {
                return this.REPLACE_WITH_POP1;
            }
            if (i2 == 2) {
                return this.REPLACE_WITH_POP2;
            }
            throw new AssertionError("Unexpected pop value size: " + sourceValue.size);
        }

        private final boolean shouldKeep(AbstractInsnNode abstractInsnNode) {
            return this.dontTouchInsnIndices.get(this.insnList.indexOf(abstractInsnNode));
        }
    }
}
