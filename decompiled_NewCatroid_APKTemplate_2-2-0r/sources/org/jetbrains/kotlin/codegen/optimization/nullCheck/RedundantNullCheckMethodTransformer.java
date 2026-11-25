package org.jetbrains.kotlin.codegen.optimization.nullCheck;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.dex.sections.Marker;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.CodegenUtilKt;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.codegen.optimization.common.StrictBasicValue;
import org.jetbrains.kotlin.codegen.optimization.common.UtilKt;
import org.jetbrains.kotlin.codegen.optimization.fixStack.StackTransformationUtilsKt;
import org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer;
import org.jetbrains.kotlin.codegen.pseudoInsns.PseudoInsn;
import org.jetbrains.kotlin.codegen.pseudoInsns.PseudoInsnsKt;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.utils.SmartList;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.InsnNode;
import org.jetbrains.org.objectweb.asm.tree.JumpInsnNode;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.TypeInsnNode;
import org.jetbrains.org.objectweb.asm.tree.VarInsnNode;
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue;
import org.jetbrains.org.objectweb.asm.tree.analysis.Frame;

/* JADX INFO: compiled from: RedundantNullCheckMethodTransformer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u000bB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/nullCheck/RedundantNullCheckMethodTransformer;", "Lorg/jetbrains/kotlin/codegen/optimization/transformer/MethodTransformer;", "generationState", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "(Lorg/jetbrains/kotlin/codegen/state/GenerationState;)V", "transform", "", "internalClassName", "", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "TransformerPass", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RedundantNullCheckMethodTransformer extends MethodTransformer {
    private final GenerationState generationState;

    public RedundantNullCheckMethodTransformer(GenerationState generationState) {
        Intrinsics.checkNotNullParameter(generationState, "generationState");
        this.generationState = generationState;
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer
    public void transform(String internalClassName, MethodNode methodNode) {
        Intrinsics.checkNotNullParameter(internalClassName, "internalClassName");
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        while (new TransformerPass(internalClassName, methodNode, this.generationState).run()) {
        }
    }

    /* JADX INFO: compiled from: RedundantNullCheckMethodTransformer.kt */
    @Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0002\u0018\u00002\u00020\u0001:\u0002()B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0014\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00140\u0012H\u0002J\u0006\u0010\u0015\u001a\u00020\nJ \u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0014H\u0002J\u0018\u0010\u001d\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u0018\u0010\u001e\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u0018\u0010\u001f\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u001c\u0010 \u001a\u00020\u00172\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00140\u0012H\u0002J\u0018\u0010\"\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00132\u0006\u0010#\u001a\u00020\nH\u0002J\u0018\u0010$\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020%2\u0006\u0010&\u001a\u00020\nH\u0002J\f\u0010'\u001a\u00020\n*\u00020\u0013H\u0002R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/nullCheck/RedundantNullCheckMethodTransformer$TransformerPass;", "", "internalClassName", "", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "generationState", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "(Ljava/lang/String;Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;Lorg/jetbrains/kotlin/codegen/state/GenerationState;)V", "changes", "", "getGenerationState", "()Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "getInternalClassName", "()Ljava/lang/String;", "getMethodNode", "()Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "analyzeNullabilities", "", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "Lorg/jetbrains/kotlin/codegen/optimization/common/StrictBasicValue;", "run", "transformInstanceOf", "", "insn", "Lorg/jetbrains/org/objectweb/asm/tree/TypeInsnNode;", "nullability", "Lorg/jetbrains/kotlin/codegen/optimization/nullCheck/Nullability;", "value", "transformTrivialCheckExpressionValueIsNotNull", "transformTrivialCheckNotNull", "transformTrivialCheckNotNullWithMessage", "transformTrivialChecks", "nullabilityMap", "transformTrivialInstanceOf", "constValue", "transformTrivialNullJump", "Lorg/jetbrains/org/objectweb/asm/tree/JumpInsnNode;", "alwaysTrue", "isOptimizable", "NullabilityAssumptions", "NullabilityAssumptionsBuilder", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class TransformerPass {
        private boolean changes;
        private final GenerationState generationState;
        private final String internalClassName;
        private final MethodNode methodNode;

        public TransformerPass(String internalClassName, MethodNode methodNode, GenerationState generationState) {
            Intrinsics.checkNotNullParameter(internalClassName, "internalClassName");
            Intrinsics.checkNotNullParameter(methodNode, "methodNode");
            Intrinsics.checkNotNullParameter(generationState, "generationState");
            this.internalClassName = internalClassName;
            this.methodNode = methodNode;
            this.generationState = generationState;
        }

        public final GenerationState getGenerationState() {
            return this.generationState;
        }

        public final String getInternalClassName() {
            return this.internalClassName;
        }

        public final MethodNode getMethodNode() {
            return this.methodNode;
        }

        public final boolean run() {
            AbstractInsnNode[] array = this.methodNode.instructions.toArray();
            Intrinsics.checkNotNullExpressionValue(array, "toArray(...)");
            for (AbstractInsnNode abstractInsnNode : array) {
                Intrinsics.checkNotNull(abstractInsnNode);
                if (isOptimizable(abstractInsnNode)) {
                    NullabilityAssumptions nullabilityAssumptionsInjectNullabilityAssumptions = new NullabilityAssumptionsBuilder().injectNullabilityAssumptions();
                    Map<AbstractInsnNode, StrictBasicValue> mapAnalyzeNullabilities = analyzeNullabilities();
                    nullabilityAssumptionsInjectNullabilityAssumptions.revert();
                    transformTrivialChecks(mapAnalyzeNullabilities);
                    return this.changes;
                }
            }
            return false;
        }

        private final Map<AbstractInsnNode, StrictBasicValue> analyzeNullabilities() {
            BasicValue basicValue;
            Frame[] frameArrAnalyze = MethodTransformer.analyze(this.internalClassName, this.methodNode, new NullabilityInterpreter(this.generationState));
            Intrinsics.checkNotNullExpressionValue(frameArrAnalyze, "analyze(...)");
            AbstractInsnNode[] array = this.methodNode.instructions.toArray();
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            int length = array.length;
            for (int i = 0; i < length; i++) {
                Frame frame = frameArrAnalyze[i];
                if (frame != null) {
                    AbstractInsnNode abstractInsnNode = array[i];
                    Intrinsics.checkNotNull(abstractInsnNode);
                    if (RedundantNullCheckMethodTransformerKt.isInstanceOfOrNullCheck(abstractInsnNode) || RedundantNullCheckMethodTransformerKt.isCheckNotNull(abstractInsnNode)) {
                        basicValue = (BasicValue) StackTransformationUtilsKt.top(frame);
                    } else {
                        basicValue = (RedundantNullCheckMethodTransformerKt.isCheckNotNullWithMessage(abstractInsnNode) || RedundantNullCheckMethodTransformerKt.isCheckExpressionValueIsNotNull(abstractInsnNode)) ? (BasicValue) StackTransformationUtilsKt.peek(frame, 1) : null;
                    }
                    StrictBasicValue strictBasicValue = basicValue instanceof StrictBasicValue ? (StrictBasicValue) basicValue : null;
                    if (strictBasicValue != null && NullabilityValuesKt.getNullability(strictBasicValue) != Nullability.NULLABLE) {
                        linkedHashMap.put(abstractInsnNode, strictBasicValue);
                    }
                }
            }
            return linkedHashMap;
        }

        private final boolean isOptimizable(AbstractInsnNode abstractInsnNode) {
            return abstractInsnNode.getOpcode() == 198 || abstractInsnNode.getOpcode() == 199 || abstractInsnNode.getOpcode() == 193 || RedundantNullCheckMethodTransformerKt.isCheckNotNull(abstractInsnNode) || RedundantNullCheckMethodTransformerKt.isCheckNotNullWithMessage(abstractInsnNode) || RedundantNullCheckMethodTransformerKt.isCheckExpressionValueIsNotNull(abstractInsnNode);
        }

        private final void transformTrivialChecks(Map<AbstractInsnNode, ? extends StrictBasicValue> nullabilityMap) {
            for (Map.Entry<AbstractInsnNode, ? extends StrictBasicValue> entry : nullabilityMap.entrySet()) {
                AbstractInsnNode key = entry.getKey();
                StrictBasicValue value = entry.getValue();
                Nullability nullability = NullabilityValuesKt.getNullability(value);
                int opcode = key.getOpcode();
                if (opcode != 184) {
                    if (opcode == 193) {
                        Intrinsics.checkNotNull(key, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.TypeInsnNode");
                        transformInstanceOf((TypeInsnNode) key, nullability, value);
                    } else if (opcode == 198) {
                        Intrinsics.checkNotNull(key, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.JumpInsnNode");
                        transformTrivialNullJump((JumpInsnNode) key, nullability == Nullability.NULL);
                    } else if (opcode == 199) {
                        Intrinsics.checkNotNull(key, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.JumpInsnNode");
                        transformTrivialNullJump((JumpInsnNode) key, nullability == Nullability.NOT_NULL);
                    }
                } else if (RedundantNullCheckMethodTransformerKt.isCheckNotNull(key)) {
                    transformTrivialCheckNotNull(key, nullability);
                } else if (RedundantNullCheckMethodTransformerKt.isCheckNotNullWithMessage(key)) {
                    transformTrivialCheckNotNullWithMessage(key, nullability);
                } else if (RedundantNullCheckMethodTransformerKt.isCheckExpressionValueIsNotNull(key)) {
                    transformTrivialCheckExpressionValueIsNotNull(key, nullability);
                }
            }
        }

        private final void transformTrivialNullJump(JumpInsnNode insn, boolean alwaysTrue) {
            this.changes = true;
            InsnList insnList = this.methodNode.instructions;
            Intrinsics.checkNotNull(insnList);
            JumpInsnNode jumpInsnNode = insn;
            RedundantNullCheckMethodTransformerKt.popReferenceValueBefore(insnList, jumpInsnNode);
            if (alwaysTrue) {
                insnList.set(jumpInsnNode, new JumpInsnNode(167, insn.label));
            } else {
                insnList.remove(jumpInsnNode);
            }
        }

        private final void transformInstanceOf(TypeInsnNode insn, Nullability nullability, StrictBasicValue value) {
            ReifiedTypeInliner.Companion companion = ReifiedTypeInliner.INSTANCE;
            AbstractInsnNode previous = insn.getPrevious();
            Intrinsics.checkNotNullExpressionValue(previous, "getPrevious(...)");
            if (companion.isOperationReifiedMarker(previous)) {
                return;
            }
            if (nullability == Nullability.NULL) {
                this.changes = true;
                transformTrivialInstanceOf(insn, false);
            } else if (nullability == Nullability.NOT_NULL && Intrinsics.areEqual(value.getType().getInternalName(), insn.desc)) {
                this.changes = true;
                transformTrivialInstanceOf(insn, true);
            }
        }

        private final void transformTrivialInstanceOf(AbstractInsnNode insn, boolean constValue) {
            InsnList insnList = this.methodNode.instructions;
            Intrinsics.checkNotNull(insnList);
            RedundantNullCheckMethodTransformerKt.popReferenceValueBefore(insnList, insn);
            insnList.set(insn, constValue ? new InsnNode(4) : new InsnNode(3));
        }

        private final void transformTrivialCheckNotNull(AbstractInsnNode insn, Nullability nullability) {
            AbstractInsnNode previous;
            if (nullability == Nullability.NOT_NULL && (previous = insn.getPrevious()) != null) {
                if (previous.getOpcode() != 89 && previous.getOpcode() != 25) {
                    previous = null;
                }
                if (previous == null) {
                    return;
                }
                InsnList insnList = this.methodNode.instructions;
                insnList.remove(previous);
                insnList.remove(insn);
            }
        }

        private final void transformTrivialCheckNotNullWithMessage(AbstractInsnNode insn, Nullability nullability) {
            AbstractInsnNode previous;
            AbstractInsnNode previous2;
            if (nullability == Nullability.NOT_NULL && (previous = insn.getPrevious()) != null) {
                if (previous.getOpcode() != 18) {
                    previous = null;
                }
                if (previous == null || (previous2 = previous.getPrevious()) == null) {
                    return;
                }
                AbstractInsnNode abstractInsnNode = (previous2.getOpcode() == 89 || previous2.getOpcode() == 25) ? previous2 : null;
                if (abstractInsnNode == null) {
                    return;
                }
                InsnList insnList = this.methodNode.instructions;
                insnList.remove(abstractInsnNode);
                insnList.remove(previous);
                insnList.remove(insn);
            }
        }

        private final void transformTrivialCheckExpressionValueIsNotNull(AbstractInsnNode insn, Nullability nullability) {
            AbstractInsnNode previous;
            if (nullability == Nullability.NOT_NULL && (previous = insn.getPrevious()) != null) {
                if (previous.getOpcode() != 18) {
                    previous = null;
                }
                if (previous == null) {
                    return;
                }
                InsnList insnList = this.methodNode.instructions;
                Intrinsics.checkNotNull(insnList);
                RedundantNullCheckMethodTransformerKt.popReferenceValueBefore(insnList, previous);
                insnList.remove(previous);
                insnList.remove(insn);
            }
        }

        /* JADX INFO: compiled from: RedundantNullCheckMethodTransformer.kt */
        @Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\rH\u0002J\u0018\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u0007H\u0002J\b\u0010\u0010\u001a\u00020\nH\u0002J\f\u0010\u0011\u001a\u00060\u0012R\u00020\u0013H\u0002J\n\u0010\u0014\u001a\u00060\u0012R\u00020\u0013J \u0010\u0015\u001a\u00020\n*\u00060\u0012R\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u0007H\u0002J \u0010\u0017\u001a\u00020\n*\u00060\u0012R\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u0007H\u0002J \u0010\u0018\u001a\u00020\n*\u00060\u0012R\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u0007H\u0002J \u0010\u0019\u001a\u00020\n*\u00060\u0012R\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u001aH\u0002J\u0018\u0010\u001b\u001a\u00020\n*\u00060\u0012R\u00020\u00132\u0006\u0010\u000b\u001a\u00020\u0007H\u0002J\u0018\u0010\u001c\u001a\u00020\n*\u00060\u0012R\u00020\u00132\u0006\u0010\u000b\u001a\u00020\u0007H\u0002R6\u0010\u0003\u001a*\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0004j\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0006`\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/nullCheck/RedundantNullCheckMethodTransformer$TransformerPass$NullabilityAssumptionsBuilder;", "", "(Lorg/jetbrains/kotlin/codegen/optimization/nullCheck/RedundantNullCheckMethodTransformer$TransformerPass;)V", "checksDependingOnVariable", "Ljava/util/HashMap;", "", "", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "Lkotlin/collections/HashMap;", "addDependentCheck", "", "insn", "aLoadInsn", "Lorg/jetbrains/org/objectweb/asm/tree/VarInsnNode;", "addDependentCheckForCheckNotNull", "checkedValueInsn", "collectVariableDependentChecks", "injectAssumptions", "Lorg/jetbrains/kotlin/codegen/optimization/nullCheck/RedundantNullCheckMethodTransformer$TransformerPass$NullabilityAssumptions;", "Lorg/jetbrains/kotlin/codegen/optimization/nullCheck/RedundantNullCheckMethodTransformer$TransformerPass;", "injectNullabilityAssumptions", "injectAssumptionsForInsn", "varIndex", "injectAssumptionsForInstanceOfCheck", "injectAssumptionsForNotNullAssertion", "injectAssumptionsForNullCheck", "Lorg/jetbrains/org/objectweb/asm/tree/JumpInsnNode;", "injectCodeForStoreNotNull", "injectCodeForThrowIntrinsic", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
        private final class NullabilityAssumptionsBuilder {
            private final HashMap<Integer, List<AbstractInsnNode>> checksDependingOnVariable = new HashMap<>();

            public NullabilityAssumptionsBuilder() {
            }

            public final NullabilityAssumptions injectNullabilityAssumptions() {
                collectVariableDependentChecks();
                return injectAssumptions();
            }

            /* JADX WARN: Type inference failed for: r0v3, types: [java.util.ListIterator] */
            private final void collectVariableDependentChecks() {
                AbstractInsnNode previous;
                AbstractInsnNode previous2;
                AbstractInsnNode previous3;
                AbstractInsnNode previous4;
                AbstractInsnNode previous5;
                ?? it = TransformerPass.this.getMethodNode().instructions.iterator();
                while (it.hasNext()) {
                    AbstractInsnNode abstractInsnNode = (AbstractInsnNode) it.next();
                    Intrinsics.checkNotNull(abstractInsnNode);
                    if (RedundantNullCheckMethodTransformerKt.isInstanceOfOrNullCheck(abstractInsnNode)) {
                        AbstractInsnNode previous6 = abstractInsnNode.getPrevious();
                        if (previous6 != null) {
                            if (previous6.getOpcode() == 25) {
                                addDependentCheck(abstractInsnNode, (VarInsnNode) previous6);
                            } else if (previous6.getOpcode() == 89 && (previous = previous6.getPrevious()) != null && previous.getOpcode() == 25) {
                                addDependentCheck(abstractInsnNode, (VarInsnNode) previous);
                            }
                        }
                    } else if (RedundantNullCheckMethodTransformerKt.isCheckNotNull(abstractInsnNode)) {
                        AbstractInsnNode previous7 = abstractInsnNode.getPrevious();
                        if (previous7 != null) {
                            addDependentCheckForCheckNotNull(abstractInsnNode, previous7);
                        }
                    } else {
                        VarInsnNode varInsnNode = null;
                        if (RedundantNullCheckMethodTransformerKt.isCheckNotNullWithMessage(abstractInsnNode)) {
                            AbstractInsnNode previous8 = abstractInsnNode.getPrevious();
                            if (previous8 != null) {
                                varInsnNode = previous8.getOpcode() == 18 ? previous8 : null;
                                if (varInsnNode != null && (previous2 = varInsnNode.getPrevious()) != null) {
                                    addDependentCheckForCheckNotNull(abstractInsnNode, previous2);
                                }
                            }
                        } else if (RedundantNullCheckMethodTransformerKt.isCheckParameterIsNotNull(abstractInsnNode)) {
                            AbstractInsnNode previous9 = abstractInsnNode.getPrevious();
                            if (previous9 != null && previous9.getOpcode() == 18 && (previous3 = previous9.getPrevious()) != null && previous3.getOpcode() == 25) {
                                addDependentCheck(abstractInsnNode, (VarInsnNode) previous3);
                            }
                        } else if (RedundantNullCheckMethodTransformerKt.isCheckExpressionValueIsNotNull(abstractInsnNode) && (previous4 = abstractInsnNode.getPrevious()) != null && previous4.getOpcode() == 18 && (previous5 = previous4.getPrevious()) != null) {
                            if (previous5.getOpcode() == 25) {
                                varInsnNode = (VarInsnNode) previous5;
                            } else if (previous5.getOpcode() == 89) {
                                AbstractInsnNode previous10 = previous5.getPrevious();
                                if (previous10 != null) {
                                    if (previous10.getOpcode() == 25) {
                                        varInsnNode = (VarInsnNode) previous10;
                                    }
                                }
                            }
                            if (varInsnNode != null) {
                                addDependentCheck(abstractInsnNode, varInsnNode);
                            }
                        }
                    }
                }
            }

            private final void addDependentCheckForCheckNotNull(AbstractInsnNode insn, AbstractInsnNode checkedValueInsn) {
                if (!(checkedValueInsn.getOpcode() == 89 && (checkedValueInsn = checkedValueInsn.getPrevious()) == null) && checkedValueInsn.getOpcode() == 25) {
                    Intrinsics.checkNotNull(checkedValueInsn, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.VarInsnNode");
                    addDependentCheck(insn, (VarInsnNode) checkedValueInsn);
                }
            }

            private final void addDependentCheck(AbstractInsnNode insn, VarInsnNode aLoadInsn) {
                HashMap<Integer, List<AbstractInsnNode>> map = this.checksDependingOnVariable;
                Integer numValueOf = Integer.valueOf(aLoadInsn.var);
                SmartList smartList = map.get(numValueOf);
                if (smartList == null) {
                    smartList = new SmartList();
                    map.put(numValueOf, smartList);
                }
                smartList.add(insn);
            }

            /* JADX WARN: Type inference failed for: r1v8, types: [java.util.ListIterator] */
            private final NullabilityAssumptions injectAssumptions() {
                NullabilityAssumptions nullabilityAssumptions = TransformerPass.this.new NullabilityAssumptions();
                for (Map.Entry<Integer, List<AbstractInsnNode>> entry : this.checksDependingOnVariable.entrySet()) {
                    int iIntValue = entry.getKey().intValue();
                    Iterator<AbstractInsnNode> it = entry.getValue().iterator();
                    while (it.hasNext()) {
                        injectAssumptionsForInsn(nullabilityAssumptions, iIntValue, it.next());
                    }
                }
                ?? it2 = TransformerPass.this.getMethodNode().instructions.iterator();
                while (it2.hasNext()) {
                    AbstractInsnNode abstractInsnNode = (AbstractInsnNode) it2.next();
                    Intrinsics.checkNotNull(abstractInsnNode);
                    if (RedundantNullCheckMethodTransformerKt.isThrowIntrinsic(abstractInsnNode)) {
                        injectCodeForThrowIntrinsic(nullabilityAssumptions, abstractInsnNode);
                    }
                }
                return nullabilityAssumptions;
            }

            private final void injectAssumptionsForInsn(NullabilityAssumptions nullabilityAssumptions, int i, AbstractInsnNode abstractInsnNode) {
                int opcode = abstractInsnNode.getOpcode();
                if (opcode != 184) {
                    if (opcode == 193) {
                        injectAssumptionsForInstanceOfCheck(nullabilityAssumptions, i, abstractInsnNode);
                        return;
                    } else {
                        if (opcode == 198 || opcode == 199) {
                            Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.JumpInsnNode");
                            injectAssumptionsForNullCheck(nullabilityAssumptions, i, (JumpInsnNode) abstractInsnNode);
                            return;
                        }
                        return;
                    }
                }
                if (RedundantNullCheckMethodTransformerKt.isCheckNotNull(abstractInsnNode) || RedundantNullCheckMethodTransformerKt.isCheckNotNullWithMessage(abstractInsnNode) || RedundantNullCheckMethodTransformerKt.isCheckParameterIsNotNull(abstractInsnNode) || RedundantNullCheckMethodTransformerKt.isCheckExpressionValueIsNotNull(abstractInsnNode)) {
                    injectAssumptionsForNotNullAssertion(nullabilityAssumptions, i, abstractInsnNode);
                } else {
                    if (PseudoInsnsKt.isPseudo(abstractInsnNode, PseudoInsn.STORE_NOT_NULL)) {
                        injectCodeForStoreNotNull(nullabilityAssumptions, abstractInsnNode);
                        return;
                    }
                    throw new AssertionError("Expected non-null assertion: " + UtilKt.getDebugText(abstractInsnNode));
                }
            }

            /* JADX WARN: Type inference failed for: r11v6, types: [java.util.ListIterator] */
            /* JADX WARN: Type inference failed for: r6v4, types: [java.util.ListIterator] */
            private final void injectAssumptionsForNullCheck(NullabilityAssumptions nullabilityAssumptions, int i, JumpInsnNode jumpInsnNode) {
                boolean z = jumpInsnNode.getOpcode() == 198;
                LabelNode label = jumpInsnNode.label;
                Intrinsics.checkNotNullExpressionValue(label, "label");
                LabelNode labelNodeLinkWithLabel = CodegenUtilKt.linkWithLabel(label);
                nullabilityAssumptions.getOriginalLabels().put(jumpInsnNode, labelNodeLinkWithLabel);
                jumpInsnNode.label = (LabelNode) nullabilityAssumptions.synthetic(new LabelNode(new Label()));
                JumpInsnNode jumpInsnNode2 = z ? jumpInsnNode.label : jumpInsnNode;
                LabelNode labelNode = z ? jumpInsnNode : jumpInsnNode.label;
                InsnList insnList = TransformerPass.this.getMethodNode().instructions;
                insnList.add(jumpInsnNode.label);
                MethodNode methodNode = new MethodNode();
                InstructionAdapter instructionAdapter = new InstructionAdapter(methodNode);
                instructionAdapter.aconst(null);
                instructionAdapter.store(i, AsmTypes.OBJECT_TYPE);
                if (z) {
                    instructionAdapter.goTo(labelNodeLinkWithLabel.getLabel());
                }
                InsnList instructions = methodNode.instructions;
                Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
                ?? it = instructions.iterator();
                while (it.hasNext()) {
                    nullabilityAssumptions.synthetic((AbstractInsnNode) it.next());
                }
                insnList.insert(jumpInsnNode2, instructions);
                MethodNode methodNode2 = new MethodNode();
                InstructionAdapter instructionAdapter2 = new InstructionAdapter(methodNode2);
                instructionAdapter2.load(i, AsmTypes.OBJECT_TYPE);
                PseudoInsnsKt.asNotNull(instructionAdapter2);
                instructionAdapter2.store(i, AsmTypes.OBJECT_TYPE);
                if (!z) {
                    instructionAdapter2.goTo(labelNodeLinkWithLabel.getLabel());
                }
                InsnList instructions2 = methodNode2.instructions;
                Intrinsics.checkNotNullExpressionValue(instructions2, "instructions");
                ?? it2 = instructions2.iterator();
                while (it2.hasNext()) {
                    nullabilityAssumptions.synthetic((AbstractInsnNode) it2.next());
                }
                insnList.insert(labelNode, instructions2);
            }

            /* JADX WARN: Type inference failed for: r1v2, types: [java.util.ListIterator] */
            private final void injectAssumptionsForNotNullAssertion(NullabilityAssumptions nullabilityAssumptions, int i, AbstractInsnNode abstractInsnNode) {
                InsnList insnList = TransformerPass.this.getMethodNode().instructions;
                MethodNode methodNode = new MethodNode();
                InstructionAdapter instructionAdapter = new InstructionAdapter(methodNode);
                instructionAdapter.load(i, AsmTypes.OBJECT_TYPE);
                PseudoInsnsKt.asNotNull(instructionAdapter);
                instructionAdapter.store(i, AsmTypes.OBJECT_TYPE);
                InsnList instructions = methodNode.instructions;
                Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
                ?? it = instructions.iterator();
                while (it.hasNext()) {
                    nullabilityAssumptions.synthetic((AbstractInsnNode) it.next());
                }
                insnList.insert(abstractInsnNode, instructions);
            }

            /* JADX WARN: Type inference failed for: r1v7, types: [java.util.ListIterator] */
            private final void injectAssumptionsForInstanceOfCheck(NullabilityAssumptions nullabilityAssumptions, int i, AbstractInsnNode abstractInsnNode) {
                LabelNode labelNodeLinkWithLabel;
                AbstractInsnNode next = abstractInsnNode.getNext();
                if (next == null) {
                    return;
                }
                if ((next.getOpcode() == 153 || next.getOpcode() == 154) && (next instanceof JumpInsnNode)) {
                    JumpInsnNode jumpInsnNode = (JumpInsnNode) next;
                    if (jumpInsnNode.getOpcode() == 154) {
                        LabelNode label = jumpInsnNode.label;
                        Intrinsics.checkNotNullExpressionValue(label, "label");
                        labelNodeLinkWithLabel = CodegenUtilKt.linkWithLabel(label);
                        AbstractMap originalLabels = nullabilityAssumptions.getOriginalLabels();
                        LabelNode label2 = jumpInsnNode.label;
                        Intrinsics.checkNotNullExpressionValue(label2, "label");
                        originalLabels.put(next, label2);
                        LabelNode labelNode = (LabelNode) nullabilityAssumptions.synthetic(new LabelNode(new Label()));
                        LabelNode labelNode2 = labelNode;
                        TransformerPass.this.getMethodNode().instructions.add(labelNode2);
                        jumpInsnNode.label = labelNode;
                        next = labelNode2;
                    } else {
                        labelNodeLinkWithLabel = null;
                    }
                    InsnList insnList = TransformerPass.this.getMethodNode().instructions;
                    MethodNode methodNode = new MethodNode();
                    InstructionAdapter instructionAdapter = new InstructionAdapter(methodNode);
                    instructionAdapter.load(i, AsmTypes.OBJECT_TYPE);
                    PseudoInsnsKt.asNotNull(instructionAdapter);
                    instructionAdapter.store(i, AsmTypes.OBJECT_TYPE);
                    if (labelNodeLinkWithLabel != null) {
                        instructionAdapter.goTo(labelNodeLinkWithLabel.getLabel());
                    }
                    InsnList instructions = methodNode.instructions;
                    Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
                    ?? it = instructions.iterator();
                    while (it.hasNext()) {
                        nullabilityAssumptions.synthetic((AbstractInsnNode) it.next());
                    }
                    insnList.insert(next, instructions);
                }
            }

            /* JADX WARN: Type inference failed for: r2v2, types: [java.util.ListIterator] */
            private final void injectCodeForThrowIntrinsic(NullabilityAssumptions nullabilityAssumptions, AbstractInsnNode abstractInsnNode) {
                InsnList insnList = TransformerPass.this.getMethodNode().instructions;
                MethodNode methodNode = new MethodNode();
                InstructionAdapter instructionAdapter = new InstructionAdapter(methodNode);
                instructionAdapter.aconst(null);
                instructionAdapter.athrow();
                InsnList instructions = methodNode.instructions;
                Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
                ?? it = instructions.iterator();
                while (it.hasNext()) {
                    nullabilityAssumptions.synthetic((AbstractInsnNode) it.next());
                }
                insnList.insert(abstractInsnNode, instructions);
                TransformerPass.this.getMethodNode().maxStack++;
            }

            /* JADX WARN: Type inference failed for: r2v3, types: [java.util.ListIterator] */
            private final void injectCodeForStoreNotNull(NullabilityAssumptions nullabilityAssumptions, AbstractInsnNode abstractInsnNode) {
                AbstractInsnNode previous = abstractInsnNode.getPrevious();
                if (previous.getOpcode() != 58) {
                    return;
                }
                InsnList insnList = TransformerPass.this.getMethodNode().instructions;
                MethodNode methodNode = new MethodNode();
                InstructionAdapter instructionAdapter = new InstructionAdapter(methodNode);
                Intrinsics.checkNotNull(previous, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.VarInsnNode");
                int i = ((VarInsnNode) previous).var;
                instructionAdapter.load(i, AsmTypes.OBJECT_TYPE);
                PseudoInsnsKt.asNotNull(instructionAdapter);
                instructionAdapter.store(i, AsmTypes.OBJECT_TYPE);
                InsnList instructions = methodNode.instructions;
                Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
                ?? it = instructions.iterator();
                while (it.hasNext()) {
                    nullabilityAssumptions.synthetic((AbstractInsnNode) it.next());
                }
                insnList.insert(abstractInsnNode, instructions);
            }
        }

        /* JADX INFO: compiled from: RedundantNullCheckMethodTransformer.kt */
        @Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J%\u0010\u0010\u001a\u00020\u00112\u0017\u0010\u0012\u001a\u0013\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00150\u0013¢\u0006\u0002\b\u0016H\u0086\bø\u0001\u0000J\u0006\u0010\u0017\u001a\u00020\u0015J\u001d\u0010\u0018\u001a\u0002H\u0019\"\b\b\u0000\u0010\u0019*\u00020\f2\u0006\u0010\u001a\u001a\u0002H\u0019¢\u0006\u0002\u0010\u001bR-\u0010\u0003\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006`\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR!\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\f0\u000bj\b\u0012\u0004\u0012\u00020\f`\r¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/nullCheck/RedundantNullCheckMethodTransformer$TransformerPass$NullabilityAssumptions;", "", "(Lorg/jetbrains/kotlin/codegen/optimization/nullCheck/RedundantNullCheckMethodTransformer$TransformerPass;)V", "originalLabels", "Ljava/util/HashMap;", "Lorg/jetbrains/org/objectweb/asm/tree/JumpInsnNode;", "Lorg/jetbrains/org/objectweb/asm/tree/LabelNode;", "Lkotlin/collections/HashMap;", "getOriginalLabels", "()Ljava/util/HashMap;", "syntheticInstructions", "Ljava/util/ArrayList;", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "Lkotlin/collections/ArrayList;", "getSyntheticInstructions", "()Ljava/util/ArrayList;", "listOfSynthetics", "Lorg/jetbrains/org/objectweb/asm/tree/InsnList;", "block", "Lkotlin/Function1;", "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "", "Lkotlin/ExtensionFunctionType;", "revert", "synthetic", ExifInterface.GPS_DIRECTION_TRUE, "insn", "(Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;)Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
        public final class NullabilityAssumptions {
            private final HashMap<JumpInsnNode, LabelNode> originalLabels = new HashMap<>();
            private final ArrayList<AbstractInsnNode> syntheticInstructions = new ArrayList<>();

            public NullabilityAssumptions() {
            }

            public final HashMap<JumpInsnNode, LabelNode> getOriginalLabels() {
                return this.originalLabels;
            }

            public final ArrayList<AbstractInsnNode> getSyntheticInstructions() {
                return this.syntheticInstructions;
            }

            public final <T extends AbstractInsnNode> T synthetic(T insn) {
                Intrinsics.checkNotNullParameter(insn, "insn");
                this.syntheticInstructions.add(insn);
                return insn;
            }

            public final void revert() {
                InsnList insnList = TransformerPass.this.getMethodNode().instructions;
                Iterator<T> it = this.syntheticInstructions.iterator();
                while (it.hasNext()) {
                    insnList.remove((AbstractInsnNode) it.next());
                }
                for (Map.Entry<JumpInsnNode, LabelNode> entry : this.originalLabels.entrySet()) {
                    entry.getKey().label = entry.getValue();
                }
            }

            /* JADX WARN: Type inference failed for: r0v3, types: [java.util.ListIterator] */
            public final InsnList listOfSynthetics(Function1<? super InstructionAdapter, Unit> block) {
                Intrinsics.checkNotNullParameter(block, "block");
                MethodNode methodNode = new MethodNode();
                block.invoke(new InstructionAdapter(methodNode));
                InsnList instructions = methodNode.instructions;
                Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
                ?? it = instructions.iterator();
                while (it.hasNext()) {
                    synthetic((AbstractInsnNode) it.next());
                }
                return instructions;
            }
        }
    }
}
