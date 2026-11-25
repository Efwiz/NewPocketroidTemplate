package org.jetbrains.kotlin.codegen.optimization.boxing;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.dex.sections.Marker;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.com.google.common.collect.ImmutableSet;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.MethodInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.TypeInsnNode;
import org.jetbrains.org.objectweb.asm.tree.VarInsnNode;
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue;

/* JADX INFO: compiled from: RedundantBoxingInterpreter.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\b\b\u0000\u0018\u0000 '2\u00020\u0001:\u0001'B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\"\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\fH\u0016J\u0018\u0010\u0011\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\fH\u0016J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0012\u001a\u00020\u0015H\u0002J \u0010\u0016\u001a\u00020\u00142\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00152\u0006\u0010\u0010\u001a\u00020\u0015H\u0014J \u0010\u0017\u001a\u00020\u00142\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00152\u0006\u0010\u0010\u001a\u00020\u0015H\u0014J\u0010\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0012\u001a\u00020\u0015H\u0014J\u0018\u0010\u0019\u001a\u00020\u00142\u0006\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u0015H\u0014J\u0010\u0010\u001c\u001a\u00020\u00142\u0006\u0010\u0012\u001a\u00020\u0015H\u0014J\u0010\u0010\u001d\u001a\u00020\u00142\u0006\u0010\u0012\u001a\u00020\u0015H\u0014J \u0010\u001e\u001a\u00020\u00142\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u00152\u0006\u0010\u001f\u001a\u00020 H\u0014J\u001a\u0010!\u001a\u00020\u00142\b\u0010\u0012\u001a\u0004\u0018\u00010\f2\u0006\u0010\"\u001a\u00020\u000eH\u0002J\u0016\u0010#\u001a\u00020\u00142\u0006\u0010\"\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\fJ*\u0010$\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\f2\u0006\u0010%\u001a\u00020\fH\u0016J\u001a\u0010&\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\fH\u0016R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/boxing/RedundantBoxingInterpreter;", "Lorg/jetbrains/kotlin/codegen/optimization/boxing/BoxingInterpreter;", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "generationState", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "(Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;Lorg/jetbrains/kotlin/codegen/state/GenerationState;)V", "candidatesBoxedValues", "Lorg/jetbrains/kotlin/codegen/optimization/boxing/RedundantBoxedValuesCollection;", "getCandidatesBoxedValues", "()Lorg/jetbrains/kotlin/codegen/optimization/boxing/RedundantBoxedValuesCollection;", "binaryOperation", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicValue;", "insn", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "value1", "value2", "copyOperation", "value", "markValueAsDirty", "", "Lorg/jetbrains/kotlin/codegen/optimization/boxing/BoxedBasicValue;", "onAreEqual", "onCompareTo", "onMergeFail", "onMergeSuccess", RegisterSpec.PREFIX, "w", "onMethodCallWithBoxedValue", "onNewBoxedValue", "onUnboxing", "resultType", "Lorg/jetbrains/org/objectweb/asm/Type;", "processOperationWithBoxedValue", "insnNode", "processPopInstruction", "ternaryOperation", "value3", "unaryOperation", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RedundantBoxingInterpreter extends BoxingInterpreter {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final ImmutableSet<Integer> PERMITTED_OPERATIONS_OPCODES = ImmutableSet.of(58, 25, 87, 89, 192, 193, (int[]) new Integer[0]);
    private static final ImmutableSet<Integer> PRIMITIVE_TYPES_SORTS_WITH_WRAPPER_EXTENDS_NUMBER = ImmutableSet.of(3, 4, 5, 6, 7, 8, (int[]) new Integer[0]);
    private final RedundantBoxedValuesCollection candidatesBoxedValues;

    /* JADX WARN: Illegal instructions before constructor call */
    public RedundantBoxingInterpreter(MethodNode methodNode, GenerationState generationState) {
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        Intrinsics.checkNotNullParameter(generationState, "generationState");
        InsnList instructions = methodNode.instructions;
        Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
        super(instructions, generationState);
        this.candidatesBoxedValues = new RedundantBoxedValuesCollection();
    }

    public final RedundantBoxedValuesCollection getCandidatesBoxedValues() {
        return this.candidatesBoxedValues;
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.boxing.BoxingInterpreter, org.jetbrains.kotlin.codegen.optimization.common.OptimizationBasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue unaryOperation(AbstractInsnNode insn, BasicValue value) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        Intrinsics.checkNotNullParameter(value, "value");
        if ((insn.getOpcode() == 192 || insn.getOpcode() == 193) && (value instanceof BoxedBasicValue)) {
            Companion companion = INSTANCE;
            BoxedBasicValue boxedBasicValue = (BoxedBasicValue) value;
            String desc = ((TypeInsnNode) insn).desc;
            Intrinsics.checkNotNullExpressionValue(desc, "desc");
            if (!companion.isSafeCast(boxedBasicValue, desc)) {
                markValueAsDirty(boxedBasicValue);
            }
        }
        processOperationWithBoxedValue(value, insn);
        return super.unaryOperation(insn, value);
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.common.OptimizationBasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue binaryOperation(AbstractInsnNode insn, BasicValue value1, BasicValue value2) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        Intrinsics.checkNotNullParameter(value1, "value1");
        Intrinsics.checkNotNullParameter(value2, "value2");
        processOperationWithBoxedValue(value1, insn);
        processOperationWithBoxedValue(value2, insn);
        return super.binaryOperation(insn, value1, value2);
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.common.OptimizationBasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue ternaryOperation(AbstractInsnNode insn, BasicValue value1, BasicValue value2, BasicValue value3) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        Intrinsics.checkNotNullParameter(value1, "value1");
        Intrinsics.checkNotNullParameter(value2, "value2");
        Intrinsics.checkNotNullParameter(value3, "value3");
        processOperationWithBoxedValue(value3, insn);
        return super.ternaryOperation(insn, value1, value2, value3);
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.common.OptimizationBasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue copyOperation(AbstractInsnNode insn, BasicValue value) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        Intrinsics.checkNotNullParameter(value, "value");
        if ((value instanceof BoxedBasicValue) && insn.getOpcode() == 58) {
            ((BoxedBasicValue) value).getDescriptor().addVariableIndex(((VarInsnNode) insn).var);
        }
        processOperationWithBoxedValue(value, insn);
        BasicValue basicValueCopyOperation = super.copyOperation(insn, value);
        Intrinsics.checkNotNullExpressionValue(basicValueCopyOperation, "copyOperation(...)");
        return basicValueCopyOperation;
    }

    public final void processPopInstruction(AbstractInsnNode insnNode, BasicValue value) {
        Intrinsics.checkNotNullParameter(insnNode, "insnNode");
        Intrinsics.checkNotNullParameter(value, "value");
        processOperationWithBoxedValue(value, insnNode);
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.boxing.BoxingInterpreter
    protected void onNewBoxedValue(BoxedBasicValue value) {
        Intrinsics.checkNotNullParameter(value, "value");
        this.candidatesBoxedValues.add(value.getDescriptor());
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.boxing.BoxingInterpreter
    protected void onUnboxing(AbstractInsnNode insn, BoxedBasicValue value, Type resultType) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(resultType, "resultType");
        BoxedValueDescriptor descriptor = value.getDescriptor();
        if (!Intrinsics.areEqual(descriptor.getUnboxTypeOrOtherwiseMethodReturnType(insn instanceof MethodInsnNode ? (MethodInsnNode) insn : null), resultType)) {
            descriptor.addUnboxingWithCastTo(insn, resultType);
        } else {
            INSTANCE.addAssociatedInsn(value, insn);
        }
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.boxing.BoxingInterpreter
    protected void onAreEqual(AbstractInsnNode insn, BoxedBasicValue value1, BoxedBasicValue value2) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        Intrinsics.checkNotNullParameter(value1, "value1");
        Intrinsics.checkNotNullParameter(value2, "value2");
        BoxedValueDescriptor descriptor = value1.getDescriptor();
        this.candidatesBoxedValues.merge(descriptor, value2.getDescriptor());
        descriptor.addInsn(insn);
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.boxing.BoxingInterpreter
    protected void onCompareTo(AbstractInsnNode insn, BoxedBasicValue value1, BoxedBasicValue value2) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        Intrinsics.checkNotNullParameter(value1, "value1");
        Intrinsics.checkNotNullParameter(value2, "value2");
        BoxedValueDescriptor descriptor = value1.getDescriptor();
        this.candidatesBoxedValues.merge(descriptor, value2.getDescriptor());
        descriptor.addInsn(insn);
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.boxing.BoxingInterpreter
    protected void onMethodCallWithBoxedValue(BoxedBasicValue value) {
        Intrinsics.checkNotNullParameter(value, "value");
        markValueAsDirty(value);
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.boxing.BoxingInterpreter
    protected void onMergeFail(BoxedBasicValue value) {
        Intrinsics.checkNotNullParameter(value, "value");
        markValueAsDirty(value);
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.boxing.BoxingInterpreter
    protected void onMergeSuccess(BoxedBasicValue v, BoxedBasicValue w) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(w, "w");
        this.candidatesBoxedValues.merge(v.getDescriptor(), w.getDescriptor());
    }

    private final void processOperationWithBoxedValue(BasicValue value, AbstractInsnNode insnNode) {
        if (value instanceof BoxedBasicValue) {
            checkUsedValue(value);
            if (!PERMITTED_OPERATIONS_OPCODES.contains(Integer.valueOf(insnNode.getOpcode()))) {
                markValueAsDirty((BoxedBasicValue) value);
            } else {
                INSTANCE.addAssociatedInsn((BoxedBasicValue) value, insnNode);
            }
        }
    }

    private final void markValueAsDirty(BoxedBasicValue value) {
        this.candidatesBoxedValues.remove(value.getDescriptor());
    }

    /* JADX INFO: compiled from: RedundantBoxingInterpreter.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002R2\u0010\u0003\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R2\u0010\u0007\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/boxing/RedundantBoxingInterpreter$Companion;", "", "()V", "PERMITTED_OPERATIONS_OPCODES", "Lorg/jetbrains/kotlin/com/google/common/collect/ImmutableSet;", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "PRIMITIVE_TYPES_SORTS_WITH_WRAPPER_EXTENDS_NUMBER", "addAssociatedInsn", "", "value", "Lorg/jetbrains/kotlin/codegen/optimization/boxing/BoxedBasicValue;", "insn", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "isSafeCast", "", "targetInternalName", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean isSafeCast(BoxedBasicValue value, String targetInternalName) {
            if (Intrinsics.areEqual(targetInternalName, Type.getInternalName(Object.class))) {
                return true;
            }
            if (Intrinsics.areEqual(targetInternalName, Type.getInternalName(Number.class))) {
                Type type = (Type) CollectionsKt.singleOrNull((List) value.getDescriptor().getUnboxedTypes());
                if (type != null) {
                    if (RedundantBoxingInterpreter.PRIMITIVE_TYPES_SORTS_WITH_WRAPPER_EXTENDS_NUMBER.contains(Integer.valueOf(type.getSort()))) {
                        return true;
                    }
                }
                return false;
            }
            if (Intrinsics.areEqual(targetInternalName, "java/lang/Comparable")) {
                return true;
            }
            return Intrinsics.areEqual(value.getType().getInternalName(), targetInternalName);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void addAssociatedInsn(BoxedBasicValue value, AbstractInsnNode insn) {
            BoxedValueDescriptor descriptor = value.getDescriptor();
            if (descriptor.getIsSafeToRemove()) {
                descriptor.addInsn(insn);
            }
        }
    }
}
