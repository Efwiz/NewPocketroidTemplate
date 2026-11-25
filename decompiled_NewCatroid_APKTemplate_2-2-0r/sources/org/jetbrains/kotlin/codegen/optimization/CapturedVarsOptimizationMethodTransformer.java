package org.jetbrains.kotlin.codegen.optimization;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.optimization.common.InsnSequence;
import org.jetbrains.kotlin.codegen.optimization.common.ProperTrackedReferenceValue;
import org.jetbrains.kotlin.codegen.optimization.common.ReferenceTrackingInterpreter;
import org.jetbrains.kotlin.codegen.optimization.common.ReferenceValueDescriptor;
import org.jetbrains.kotlin.codegen.optimization.common.TrackedReferenceValue;
import org.jetbrains.kotlin.codegen.optimization.common.UtilKt;
import org.jetbrains.kotlin.codegen.optimization.fixStack.StackTransformationUtilsKt;
import org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.FieldInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.InsnNode;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;
import org.jetbrains.org.objectweb.asm.tree.LineNumberNode;
import org.jetbrains.org.objectweb.asm.tree.LocalVariableNode;
import org.jetbrains.org.objectweb.asm.tree.MethodInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.TypeInsnNode;
import org.jetbrains.org.objectweb.asm.tree.VarInsnNode;
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue;
import org.jetbrains.org.objectweb.asm.tree.analysis.Frame;
import org.jetbrains.org.objectweb.asm.tree.analysis.Value;

/* JADX INFO: compiled from: CapturedVarsOptimizationMethodTransformer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0002\t\nB\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/CapturedVarsOptimizationMethodTransformer;", "Lorg/jetbrains/kotlin/codegen/optimization/transformer/MethodTransformer;", "()V", "transform", "", "internalClassName", "", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "CapturedVarDescriptor", "Transformer", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CapturedVarsOptimizationMethodTransformer extends MethodTransformer {
    @Override // org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer
    public void transform(String internalClassName, MethodNode methodNode) {
        Intrinsics.checkNotNullParameter(internalClassName, "internalClassName");
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        new Transformer(internalClassName, methodNode).run();
    }

    /* JADX INFO: compiled from: CapturedVarsOptimizationMethodTransformer.kt */
    @Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\u0006\u0010/\u001a\u00020\u000eJ\b\u00100\u001a\u000201H\u0016R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001c\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001c\u0010\u0019\u001a\u0004\u0018\u00010\u001aX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u001a\u0010\u001f\u001a\u00020 X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0017\u0010'\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\b\n\u0000\u001a\u0004\b(\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b+\u0010*R\u0017\u0010,\u001a\b\u0012\u0004\u0012\u00020-0\t¢\u0006\b\n\u0000\u001a\u0004\b.\u0010\f¨\u00062"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/CapturedVarsOptimizationMethodTransformer$CapturedVarDescriptor;", "Lorg/jetbrains/kotlin/codegen/optimization/common/ReferenceValueDescriptor;", "newInsn", "Lorg/jetbrains/org/objectweb/asm/tree/TypeInsnNode;", "refType", "Lorg/jetbrains/org/objectweb/asm/Type;", "valueType", "(Lorg/jetbrains/org/objectweb/asm/tree/TypeInsnNode;Lorg/jetbrains/org/objectweb/asm/Type;Lorg/jetbrains/org/objectweb/asm/Type;)V", "getFieldInsns", "", "Lorg/jetbrains/org/objectweb/asm/tree/FieldInsnNode;", "getGetFieldInsns", "()Ljava/util/Collection;", "hazard", "", "getHazard", "()Z", "setHazard", "(Z)V", "initCallInsn", "Lorg/jetbrains/org/objectweb/asm/tree/MethodInsnNode;", "getInitCallInsn", "()Lorg/jetbrains/org/objectweb/asm/tree/MethodInsnNode;", "setInitCallInsn", "(Lorg/jetbrains/org/objectweb/asm/tree/MethodInsnNode;)V", "localVar", "Lorg/jetbrains/org/objectweb/asm/tree/LocalVariableNode;", "getLocalVar", "()Lorg/jetbrains/org/objectweb/asm/tree/LocalVariableNode;", "setLocalVar", "(Lorg/jetbrains/org/objectweb/asm/tree/LocalVariableNode;)V", "localVarIndex", "", "getLocalVarIndex", "()I", "setLocalVarIndex", "(I)V", "getNewInsn", "()Lorg/jetbrains/org/objectweb/asm/tree/TypeInsnNode;", "putFieldInsns", "getPutFieldInsns", "getRefType", "()Lorg/jetbrains/org/objectweb/asm/Type;", "getValueType", "wrapperInsns", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "getWrapperInsns", "canRewrite", "onUseAsTainted", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class CapturedVarDescriptor implements ReferenceValueDescriptor {
        private final Collection<FieldInsnNode> getFieldInsns;
        private boolean hazard;
        private MethodInsnNode initCallInsn;
        private LocalVariableNode localVar;
        private int localVarIndex;
        private final TypeInsnNode newInsn;
        private final Collection<FieldInsnNode> putFieldInsns;
        private final Type refType;
        private final Type valueType;
        private final Collection<AbstractInsnNode> wrapperInsns;

        public CapturedVarDescriptor(TypeInsnNode newInsn, Type refType, Type valueType) {
            Intrinsics.checkNotNullParameter(newInsn, "newInsn");
            Intrinsics.checkNotNullParameter(refType, "refType");
            Intrinsics.checkNotNullParameter(valueType, "valueType");
            this.newInsn = newInsn;
            this.refType = refType;
            this.valueType = valueType;
            this.localVarIndex = -1;
            this.wrapperInsns = new LinkedHashSet();
            this.getFieldInsns = new LinkedHashSet();
            this.putFieldInsns = new LinkedHashSet();
        }

        public final TypeInsnNode getNewInsn() {
            return this.newInsn;
        }

        public final Type getRefType() {
            return this.refType;
        }

        public final Type getValueType() {
            return this.valueType;
        }

        public final boolean getHazard() {
            return this.hazard;
        }

        public final void setHazard(boolean z) {
            this.hazard = z;
        }

        public final MethodInsnNode getInitCallInsn() {
            return this.initCallInsn;
        }

        public final void setInitCallInsn(MethodInsnNode methodInsnNode) {
            this.initCallInsn = methodInsnNode;
        }

        public final LocalVariableNode getLocalVar() {
            return this.localVar;
        }

        public final void setLocalVar(LocalVariableNode localVariableNode) {
            this.localVar = localVariableNode;
        }

        public final int getLocalVarIndex() {
            return this.localVarIndex;
        }

        public final void setLocalVarIndex(int i) {
            this.localVarIndex = i;
        }

        public final Collection<AbstractInsnNode> getWrapperInsns() {
            return this.wrapperInsns;
        }

        public final Collection<FieldInsnNode> getGetFieldInsns() {
            return this.getFieldInsns;
        }

        public final Collection<FieldInsnNode> getPutFieldInsns() {
            return this.putFieldInsns;
        }

        @Override // org.jetbrains.kotlin.codegen.optimization.common.ReferenceValueDescriptor
        public void onUseAsTainted() {
            this.hazard = true;
        }

        public final boolean canRewrite() {
            return (this.hazard || this.initCallInsn == null) ? false : true;
        }
    }

    /* JADX INFO: compiled from: CapturedVarsOptimizationMethodTransformer.kt */
    @Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001:\u0001%B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J%\u0010\u000f\u001a\u00020\u00102\u0016\u0010\u0011\u001a\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0004\u0012\u00020\u0014\u0018\u00010\u00130\u0012H\u0002¢\u0006\u0002\u0010\u0015J\b\u0010\u0016\u001a\u00020\u0010H\u0002J\u0010\u0010\u0017\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\tH\u0002J\u0006\u0010\u0019\u001a\u00020\u0010J%\u0010\u001a\u001a\u00020\u00102\u0016\u0010\u0011\u001a\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0004\u0012\u00020\u0014\u0018\u00010\u00130\u0012H\u0002¢\u0006\u0002\u0010\u0015J\u0012\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c*\u00020\u001eH\u0002J\u000e\u0010\u001f\u001a\u0004\u0018\u00010\t*\u00020\u0014H\u0002J\f\u0010 \u001a\u00020!*\u00020\u001dH\u0002J\u0014\u0010\"\u001a\u00020\u0010*\u00020#2\u0006\u0010$\u001a\u00020\u001dH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0007\u001a\u0012\u0012\u0004\u0012\u00020\t0\bj\b\u0012\u0004\u0012\u00020\t`\nX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u000b\u001a\u001e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\t0\fj\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\t`\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/CapturedVarsOptimizationMethodTransformer$Transformer;", "", "internalClassName", "", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "(Ljava/lang/String;Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;)V", "refValues", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/codegen/optimization/CapturedVarsOptimizationMethodTransformer$CapturedVarDescriptor;", "Lkotlin/collections/ArrayList;", "refValuesByNewInsn", "Ljava/util/LinkedHashMap;", "Lorg/jetbrains/org/objectweb/asm/tree/TypeInsnNode;", "Lkotlin/collections/LinkedHashMap;", "assignLocalVars", "", "frames", "", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/Frame;", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicValue;", "([Lorg/jetbrains/org/objectweb/asm/tree/analysis/Frame;)V", "createRefValues", "rewriteRefValue", "capturedVar", "run", "trackPops", "findCleanInstructions", "Lkotlin/sequences/Sequence;", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "Lorg/jetbrains/org/objectweb/asm/tree/LocalVariableNode;", "getCapturedVarOrNull", "getIndex", "", "removeOrReplaceByNop", "Lorg/jetbrains/org/objectweb/asm/tree/InsnList;", "insn", "Interpreter", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class Transformer {
        private final String internalClassName;
        private final MethodNode methodNode;
        private final ArrayList<CapturedVarDescriptor> refValues;
        private final LinkedHashMap<TypeInsnNode, CapturedVarDescriptor> refValuesByNewInsn;

        public Transformer(String internalClassName, MethodNode methodNode) {
            Intrinsics.checkNotNullParameter(internalClassName, "internalClassName");
            Intrinsics.checkNotNullParameter(methodNode, "methodNode");
            this.internalClassName = internalClassName;
            this.methodNode = methodNode;
            this.refValues = new ArrayList<>();
            this.refValuesByNewInsn = new LinkedHashMap<>();
        }

        public final void run() {
            createRefValues();
            if (this.refValues.isEmpty()) {
                return;
            }
            Frame<BasicValue>[] frameArrAnalyze = MethodTransformer.analyze(this.internalClassName, this.methodNode, new Interpreter());
            Intrinsics.checkNotNullExpressionValue(frameArrAnalyze, "analyze(...)");
            trackPops(frameArrAnalyze);
            assignLocalVars(frameArrAnalyze);
            for (CapturedVarDescriptor capturedVarDescriptor : this.refValues) {
                if (capturedVarDescriptor.canRewrite()) {
                    Intrinsics.checkNotNull(capturedVarDescriptor);
                    rewriteRefValue(capturedVarDescriptor);
                }
            }
            UtilKt.removeEmptyCatchBlocks(this.methodNode);
            UtilKt.removeUnusedLocalVariables(this.methodNode);
        }

        private final int getIndex(AbstractInsnNode abstractInsnNode) {
            return this.methodNode.instructions.indexOf(abstractInsnNode);
        }

        private final void createRefValues() {
            Type type;
            InsnList instructions = this.methodNode.instructions;
            Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
            for (AbstractInsnNode abstractInsnNode : UtilKt.asSequence(instructions)) {
                if (abstractInsnNode.getOpcode() == 187 && (abstractInsnNode instanceof TypeInsnNode)) {
                    TypeInsnNode typeInsnNode = (TypeInsnNode) abstractInsnNode;
                    Type objectType = Type.getObjectType(typeInsnNode.desc);
                    if (AsmTypes.isSharedVarType(objectType) && (type = CapturedVarsOptimizationMethodTransformerKt.getREF_TYPE_TO_ELEMENT_TYPE().get(objectType.getInternalName())) != null) {
                        Intrinsics.checkNotNull(objectType);
                        CapturedVarDescriptor capturedVarDescriptor = new CapturedVarDescriptor(typeInsnNode, objectType, type);
                        this.refValues.add(capturedVarDescriptor);
                        this.refValuesByNewInsn.put(abstractInsnNode, capturedVarDescriptor);
                    }
                }
            }
        }

        /* JADX INFO: compiled from: CapturedVarsOptimizationMethodTransformer.kt */
        @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J \u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\fH\u0014¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/CapturedVarsOptimizationMethodTransformer$Transformer$Interpreter;", "Lorg/jetbrains/kotlin/codegen/optimization/common/ReferenceTrackingInterpreter;", "(Lorg/jetbrains/kotlin/codegen/optimization/CapturedVarsOptimizationMethodTransformer$Transformer;)V", "newOperation", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicValue;", "insn", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "processRefValueUsage", "", "value", "Lorg/jetbrains/kotlin/codegen/optimization/common/TrackedReferenceValue;", "position", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
        private final class Interpreter extends ReferenceTrackingInterpreter {
            public Interpreter() {
            }

            @Override // org.jetbrains.kotlin.codegen.optimization.common.OptimizationBasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
            public BasicValue newOperation(AbstractInsnNode insn) {
                Intrinsics.checkNotNullParameter(insn, "insn");
                CapturedVarDescriptor capturedVarDescriptor = (CapturedVarDescriptor) Transformer.this.refValuesByNewInsn.get(insn);
                if (capturedVarDescriptor != null) {
                    return new ProperTrackedReferenceValue(capturedVarDescriptor.getRefType(), capturedVarDescriptor);
                }
                BasicValue basicValueNewOperation = super.newOperation(insn);
                Intrinsics.checkNotNullExpressionValue(basicValueNewOperation, "newOperation(...)");
                return basicValueNewOperation;
            }

            @Override // org.jetbrains.kotlin.codegen.optimization.common.ReferenceTrackingInterpreter
            protected void processRefValueUsage(TrackedReferenceValue value, AbstractInsnNode insn, int position) {
                Intrinsics.checkNotNullParameter(value, "value");
                Intrinsics.checkNotNullParameter(insn, "insn");
                for (ReferenceValueDescriptor referenceValueDescriptor : value.getDescriptors()) {
                    if (!(referenceValueDescriptor instanceof CapturedVarDescriptor)) {
                        throw new AssertionError("Unexpected descriptor: " + referenceValueDescriptor);
                    }
                    if (insn.getOpcode() == 89) {
                        ((CapturedVarDescriptor) referenceValueDescriptor).getWrapperInsns().add(insn);
                    } else if (insn.getOpcode() == 25) {
                        ((CapturedVarDescriptor) referenceValueDescriptor).getWrapperInsns().add(insn);
                    } else if (insn.getOpcode() == 58) {
                        ((CapturedVarDescriptor) referenceValueDescriptor).getWrapperInsns().add(insn);
                    } else if (insn.getOpcode() == 180 && (insn instanceof FieldInsnNode) && Intrinsics.areEqual(((FieldInsnNode) insn).f1010name, CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD) && position == 0) {
                        ((CapturedVarDescriptor) referenceValueDescriptor).getGetFieldInsns().add(insn);
                    } else if (insn.getOpcode() == 181 && (insn instanceof FieldInsnNode) && Intrinsics.areEqual(((FieldInsnNode) insn).f1010name, CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD) && position == 0) {
                        ((CapturedVarDescriptor) referenceValueDescriptor).getPutFieldInsns().add(insn);
                    } else {
                        if (insn.getOpcode() == 183 && (insn instanceof MethodInsnNode)) {
                            MethodInsnNode methodInsnNode = (MethodInsnNode) insn;
                            if (Intrinsics.areEqual(methodInsnNode.f1015name, "<init>") && position == 0) {
                                CapturedVarDescriptor capturedVarDescriptor = (CapturedVarDescriptor) referenceValueDescriptor;
                                if (capturedVarDescriptor.getInitCallInsn() != null && !Intrinsics.areEqual(capturedVarDescriptor.getInitCallInsn(), insn)) {
                                    capturedVarDescriptor.setHazard(true);
                                } else {
                                    capturedVarDescriptor.setInitCallInsn(methodInsnNode);
                                }
                            }
                        }
                        ((CapturedVarDescriptor) referenceValueDescriptor).setHazard(true);
                    }
                }
            }
        }

        private final void trackPops(Frame<BasicValue>[] frames) {
            CapturedVarDescriptor capturedVarOrNull;
            BasicValue basicValue;
            InsnList instructions = this.methodNode.instructions;
            Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
            int i = 0;
            for (AbstractInsnNode abstractInsnNode : UtilKt.asSequence(instructions)) {
                int i2 = i + 1;
                Frame<BasicValue> frame = frames[i];
                if (frame != null) {
                    int opcode = abstractInsnNode.getOpcode();
                    if (opcode == 87) {
                        BasicValue basicValue2 = (BasicValue) StackTransformationUtilsKt.top(frame);
                        if (basicValue2 != null && (capturedVarOrNull = getCapturedVarOrNull(basicValue2)) != null) {
                            capturedVarOrNull.getWrapperInsns().add(abstractInsnNode);
                        }
                    } else if (opcode == 88 && (basicValue = (BasicValue) StackTransformationUtilsKt.top(frame)) != null && basicValue.get_size() == 1) {
                        CapturedVarDescriptor capturedVarOrNull2 = getCapturedVarOrNull(basicValue);
                        if (capturedVarOrNull2 != null) {
                            capturedVarOrNull2.setHazard(true);
                        }
                        BasicValue basicValue3 = (BasicValue) StackTransformationUtilsKt.peek(frame, 1);
                        CapturedVarDescriptor capturedVarOrNull3 = basicValue3 != null ? getCapturedVarOrNull(basicValue3) : null;
                        if (capturedVarOrNull3 != null) {
                            capturedVarOrNull3.setHazard(true);
                        }
                    }
                }
                i = i2;
            }
        }

        private final CapturedVarDescriptor getCapturedVarOrNull(BasicValue basicValue) {
            ProperTrackedReferenceValue properTrackedReferenceValue = basicValue instanceof ProperTrackedReferenceValue ? (ProperTrackedReferenceValue) basicValue : null;
            ReferenceValueDescriptor descriptor = properTrackedReferenceValue != null ? properTrackedReferenceValue.getDescriptor() : null;
            if (descriptor instanceof CapturedVarDescriptor) {
                return (CapturedVarDescriptor) descriptor;
            }
            return null;
        }

        private final void assignLocalVars(Frame<BasicValue>[] frames) {
            for (LocalVariableNode localVariableNode : this.methodNode.localVariables) {
                if (AsmTypes.isSharedVarType(Type.getType(localVariableNode.desc))) {
                    LabelNode start = localVariableNode.start;
                    Intrinsics.checkNotNullExpressionValue(start, "start");
                    Frame<BasicValue> frame = frames[getIndex(start)];
                    if (frame != null) {
                        Value local = frame.getLocal(localVariableNode.index);
                        ProperTrackedReferenceValue properTrackedReferenceValue = local instanceof ProperTrackedReferenceValue ? (ProperTrackedReferenceValue) local : null;
                        if (properTrackedReferenceValue != null) {
                            ReferenceValueDescriptor descriptor = properTrackedReferenceValue.getDescriptor();
                            CapturedVarDescriptor capturedVarDescriptor = descriptor instanceof CapturedVarDescriptor ? (CapturedVarDescriptor) descriptor : null;
                            if (capturedVarDescriptor != null && !capturedVarDescriptor.getHazard()) {
                                if (capturedVarDescriptor.getLocalVar() == null) {
                                    capturedVarDescriptor.setLocalVar(localVariableNode);
                                } else {
                                    capturedVarDescriptor.setHazard(true);
                                }
                            }
                        }
                    }
                }
            }
            for (CapturedVarDescriptor capturedVarDescriptor2 : this.refValues) {
                if (!capturedVarDescriptor2.getHazard()) {
                    if (capturedVarDescriptor2.getLocalVar() == null || capturedVarDescriptor2.getValueType().getSize() != 1) {
                        capturedVarDescriptor2.setLocalVarIndex(this.methodNode.maxLocals);
                        this.methodNode.maxLocals += capturedVarDescriptor2.getValueType().getSize();
                    } else {
                        LocalVariableNode localVar = capturedVarDescriptor2.getLocalVar();
                        Intrinsics.checkNotNull(localVar);
                        capturedVarDescriptor2.setLocalVarIndex(localVar.index);
                    }
                }
            }
        }

        private final Sequence<AbstractInsnNode> findCleanInstructions(final LocalVariableNode localVariableNode) {
            InsnList instructions = this.methodNode.instructions;
            Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
            return SequencesKt.filter(SequencesKt.takeWhile(SequencesKt.dropWhile(new InsnSequence(instructions), new Function1<AbstractInsnNode, Boolean>() { // from class: org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformer$Transformer$findCleanInstructions$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(AbstractInsnNode it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(!Intrinsics.areEqual(it, localVariableNode.start));
                }
            }), new Function1<AbstractInsnNode, Boolean>() { // from class: org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformer$Transformer$findCleanInstructions$2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(AbstractInsnNode it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(!Intrinsics.areEqual(it, localVariableNode.end));
                }
            }), new Function1<AbstractInsnNode, Boolean>() { // from class: org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformer$Transformer$findCleanInstructions$3
                {
                    super(1);
                }

                /* JADX WARN: Code duplicated, block: B:13:0x0029  */
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(AbstractInsnNode it) {
                    boolean z;
                    AbstractInsnNode previous;
                    Intrinsics.checkNotNullParameter(it, "it");
                    if (it instanceof VarInsnNode) {
                        VarInsnNode varInsnNode = (VarInsnNode) it;
                        if (varInsnNode.getOpcode() == 58 && varInsnNode.var == localVariableNode.index && (previous = varInsnNode.getPrevious()) != null) {
                            z = previous.getOpcode() == 1;
                        }
                    }
                    return Boolean.valueOf(z);
                }
            });
        }

        private final void removeOrReplaceByNop(InsnList insnList, AbstractInsnNode abstractInsnNode) {
            if ((abstractInsnNode.getPrevious() instanceof LineNumberNode) && (abstractInsnNode.getNext() instanceof LabelNode) && (abstractInsnNode.getNext().getNext() instanceof LineNumberNode)) {
                insnList.set(abstractInsnNode, new InsnNode(0));
            } else {
                insnList.remove(abstractInsnNode);
            }
        }

        private final void rewriteRefValue(CapturedVarDescriptor capturedVar) {
            int index;
            LabelNode start;
            InsnList insnList = this.methodNode.instructions;
            int opcode = capturedVar.getValueType().getOpcode(21);
            int opcode2 = capturedVar.getValueType().getOpcode(54);
            LocalVariableNode localVar = capturedVar.getLocalVar();
            if (localVar != null) {
                Collection<FieldInsnNode> putFieldInsns = capturedVar.getPutFieldInsns();
                if ((putFieldInsns instanceof Collection) && putFieldInsns.isEmpty()) {
                    insnList.insertBefore(capturedVar.getNewInsn(), new InsnNode(AsmUtil.defaultValueOpcode(capturedVar.getValueType())));
                    insnList.insertBefore(capturedVar.getNewInsn(), new VarInsnNode(opcode2, capturedVar.getLocalVarIndex()));
                    break;
                }
                Iterator<T> it = putFieldInsns.iterator();
                do {
                    if (it.hasNext()) {
                        index = getIndex((FieldInsnNode) it.next());
                        start = localVar.start;
                        Intrinsics.checkNotNullExpressionValue(start, "start");
                    } else {
                        insnList.insertBefore(capturedVar.getNewInsn(), new InsnNode(AsmUtil.defaultValueOpcode(capturedVar.getValueType())));
                        insnList.insertBefore(capturedVar.getNewInsn(), new VarInsnNode(opcode2, capturedVar.getLocalVarIndex()));
                        break;
                    }
                } while (index >= getIndex(start));
                for (AbstractInsnNode abstractInsnNode : findCleanInstructions(localVar)) {
                    if (opcode2 == 58) {
                        insnList.set(abstractInsnNode.getPrevious(), new InsnNode(AsmUtil.defaultValueOpcode(capturedVar.getValueType())));
                    } else {
                        insnList.remove(abstractInsnNode.getPrevious());
                        insnList.remove(abstractInsnNode);
                    }
                }
                localVar.index = capturedVar.getLocalVarIndex();
                localVar.desc = capturedVar.getValueType().getDescriptor();
                localVar.signature = null;
            }
            insnList.remove(capturedVar.getNewInsn());
            MethodInsnNode initCallInsn = capturedVar.getInitCallInsn();
            Intrinsics.checkNotNull(initCallInsn);
            insnList.remove(initCallInsn);
            for (AbstractInsnNode abstractInsnNode2 : capturedVar.getWrapperInsns()) {
                Intrinsics.checkNotNull(insnList);
                removeOrReplaceByNop(insnList, abstractInsnNode2);
            }
            Iterator<T> it2 = capturedVar.getGetFieldInsns().iterator();
            while (it2.hasNext()) {
                insnList.set((FieldInsnNode) it2.next(), new VarInsnNode(opcode, capturedVar.getLocalVarIndex()));
            }
            Iterator<T> it3 = capturedVar.getPutFieldInsns().iterator();
            while (it3.hasNext()) {
                insnList.set((FieldInsnNode) it3.next(), new VarInsnNode(opcode2, capturedVar.getLocalVarIndex()));
            }
        }
    }
}
