package org.jetbrains.kotlin.codegen.optimization.nullCheck;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.arsc.value.CompoundEntry;
import com.reandroid.dex.sections.Marker;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInlinerKt;
import org.jetbrains.kotlin.codegen.optimization.boxing.BoxingInterpreterKt;
import org.jetbrains.kotlin.codegen.optimization.boxing.PopBackwardPropagationTransformerKt;
import org.jetbrains.kotlin.codegen.optimization.boxing.ProgressionIteratorBasicValue;
import org.jetbrains.kotlin.codegen.optimization.common.OptimizationBasicInterpreter;
import org.jetbrains.kotlin.codegen.optimization.common.StrictBasicValue;
import org.jetbrains.kotlin.codegen.pseudoInsns.PseudoInsn;
import org.jetbrains.kotlin.codegen.pseudoInsns.PseudoInsnsKt;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodInsnNode;
import org.jetbrains.org.objectweb.asm.tree.analysis.AnalyzerException;
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue;
import org.jetbrains.org.objectweb.asm.tree.analysis.Value;

/* JADX INFO: compiled from: NullabilityInterpreter.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006H\u0016J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\n2\u0006\u0010\b\u001a\u00020\nH\u0002J \u0010\u000b\u001a\u0004\u0018\u00010\u00062\u0006\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00060\u000fH\u0016J\u0012\u0010\u0010\u001a\u0004\u0018\u00010\u00062\u0006\u0010\f\u001a\u00020\rH\u0016J\u001c\u0010\u0011\u001a\u0004\u0018\u00010\u00062\u0006\u0010\f\u001a\u00020\r2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0006H\u0016J\u000e\u0010\u0013\u001a\u00020\u0014*\u0004\u0018\u00010\u0015H\u0002J\f\u0010\u0016\u001a\u00020\u0014*\u00020\rH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/nullCheck/NullabilityInterpreter;", "Lorg/jetbrains/kotlin/codegen/optimization/common/OptimizationBasicInterpreter;", "generationState", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "(Lorg/jetbrains/kotlin/codegen/state/GenerationState;)V", "merge", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicValue;", RegisterSpec.PREFIX, "w", "mergeNotNullValuesOfSameKind", "Lorg/jetbrains/kotlin/codegen/optimization/common/StrictBasicValue;", "naryOperation", "insn", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", CompoundEntry.NAME_values, "", "newOperation", "unaryOperation", "value", "isReferenceType", "", "Lorg/jetbrains/org/objectweb/asm/Type;", "isReifiedSafeAs", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NullabilityInterpreter extends OptimizationBasicInterpreter {
    private final GenerationState generationState;

    public NullabilityInterpreter(GenerationState generationState) {
        Intrinsics.checkNotNullParameter(generationState, "generationState");
        this.generationState = generationState;
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.common.OptimizationBasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public /* bridge */ /* synthetic */ Value naryOperation(AbstractInsnNode abstractInsnNode, List list) {
        return naryOperation(abstractInsnNode, (List<? extends BasicValue>) list);
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.common.OptimizationBasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue newOperation(AbstractInsnNode insn) throws AnalyzerException {
        Intrinsics.checkNotNullParameter(insn, "insn");
        BasicValue basicValueNewOperation = super.newOperation(insn);
        Type type = basicValueNewOperation != null ? basicValueNewOperation.getType() : null;
        if (insn.getOpcode() == 1) {
            return NullBasicValue.INSTANCE;
        }
        if (insn.getOpcode() == 187) {
            return new NotNullBasicValue(type);
        }
        if (insn.getOpcode() == 18 && isReferenceType(type)) {
            return new NotNullBasicValue(type);
        }
        return PopBackwardPropagationTransformerKt.isUnitInstance(insn) ? new NotNullBasicValue(type) : basicValueNewOperation;
    }

    private final boolean isReferenceType(Type type) {
        Integer numValueOf = type != null ? Integer.valueOf(type.getSort()) : null;
        return (numValueOf != null && numValueOf.intValue() == 10) || (numValueOf != null && numValueOf.intValue() == 9);
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.common.OptimizationBasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue unaryOperation(AbstractInsnNode insn, BasicValue value) throws AnalyzerException {
        Intrinsics.checkNotNullParameter(insn, "insn");
        BasicValue basicValueUnaryOperation = super.unaryOperation(insn, value);
        Type type = basicValueUnaryOperation != null ? basicValueUnaryOperation.getType() : null;
        int opcode = insn.getOpcode();
        if (opcode == 188 || opcode == 189) {
            return new NotNullBasicValue(type);
        }
        if (opcode != 192) {
            return basicValueUnaryOperation;
        }
        return isReifiedSafeAs(insn) ? new StrictBasicValue(type) : value;
    }

    private final boolean isReifiedSafeAs(AbstractInsnNode abstractInsnNode) {
        AbstractInsnNode previous = abstractInsnNode.getPrevious();
        MethodInsnNode methodInsnNode = previous instanceof MethodInsnNode ? (MethodInsnNode) previous : null;
        return methodInsnNode != null && ReifiedTypeInliner.INSTANCE.isOperationReifiedMarker(methodInsnNode) && ReifiedTypeInlinerKt.getOperationKind(methodInsnNode) == ReifiedTypeInliner.OperationKind.SAFE_AS;
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.common.OptimizationBasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue naryOperation(AbstractInsnNode insn, List<? extends BasicValue> values) throws AnalyzerException {
        Intrinsics.checkNotNullParameter(insn, "insn");
        Intrinsics.checkNotNullParameter(values, "values");
        BasicValue basicValueNaryOperation = super.naryOperation(insn, values);
        Type type = basicValueNaryOperation != null ? basicValueNaryOperation.getType() : null;
        if (BoxingInterpreterKt.isBoxing(insn, this.generationState)) {
            return new NotNullBasicValue(type);
        }
        if (BoxingInterpreterKt.isIteratorMethodCallOfProgression(insn, values)) {
            ProgressionIteratorBasicValue.Companion companion = ProgressionIteratorBasicValue.INSTANCE;
            Type type2 = values.get(0).getType();
            Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
            return companion.byProgressionClassType(insn, type2);
        }
        if (BoxingInterpreterKt.isNextMethodCallOfProgressionIterator(insn, values)) {
            return new NotNullBasicValue(type);
        }
        return PseudoInsnsKt.isPseudo(insn, PseudoInsn.AS_NOT_NULL) ? new NotNullBasicValue(values.get(0).getType()) : basicValueNaryOperation;
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.common.OptimizationBasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue merge(BasicValue v, BasicValue w) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(w, "w");
        boolean z = v instanceof NullBasicValue;
        if (z && (w instanceof NullBasicValue)) {
            return NullBasicValue.INSTANCE;
        }
        if (z || (w instanceof NullBasicValue)) {
            return StrictBasicValue.REFERENCE_VALUE;
        }
        boolean z2 = v instanceof ProgressionIteratorBasicValue;
        if (z2 && (w instanceof ProgressionIteratorBasicValue)) {
            return mergeNotNullValuesOfSameKind((StrictBasicValue) v, (StrictBasicValue) w);
        }
        if (z2 && (w instanceof NotNullBasicValue)) {
            return NotNullBasicValue.INSTANCE.getNOT_NULL_REFERENCE_VALUE();
        }
        if ((w instanceof ProgressionIteratorBasicValue) && (v instanceof NotNullBasicValue)) {
            return NotNullBasicValue.INSTANCE.getNOT_NULL_REFERENCE_VALUE();
        }
        if ((v instanceof NotNullBasicValue) && (w instanceof NotNullBasicValue)) {
            return mergeNotNullValuesOfSameKind((StrictBasicValue) v, (StrictBasicValue) w);
        }
        BasicValue basicValueMerge = super.merge(v, w);
        Intrinsics.checkNotNullExpressionValue(basicValueMerge, "merge(...)");
        return basicValueMerge;
    }

    private final StrictBasicValue mergeNotNullValuesOfSameKind(StrictBasicValue v, StrictBasicValue w) {
        return Intrinsics.areEqual(v.getType(), w.getType()) ? v : NotNullBasicValue.INSTANCE.getNOT_NULL_REFERENCE_VALUE();
    }
}
