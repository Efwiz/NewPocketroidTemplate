package org.jetbrains.kotlin.codegen.optimization.common;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.arsc.value.CompoundEntry;
import com.reandroid.dex.sections.Marker;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue;
import org.jetbrains.org.objectweb.asm.tree.analysis.Value;

/* JADX INFO: compiled from: ReferenceTrackingInterpreter.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\"\u0010\t\u001a\u0004\u0018\u00010\u00062\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u0006H\u0016J\u001e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00060\u0011H\u0014J\u001a\u0010\u0012\u001a\u0004\u0018\u00010\u00062\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u0006H\u0016J\u0018\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u0015H\u0004J\u0018\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u0015H\u0014J\u0018\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0004J\u001c\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001b2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001bH\u0004J\u0018\u0010\u001e\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0016J\u001e\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0002J \u0010 \u001a\u0004\u0018\u00010\u00062\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00060\u0011H\u0016J \u0010!\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u00152\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\"\u001a\u00020#H$J*\u0010$\u001a\u0004\u0018\u00010\u00062\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010%\u001a\u00020\u0006H\u0016J\u001a\u0010&\u001a\u0004\u0018\u00010\u00062\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u0006H\u0016R\u001e\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004*\u00020\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/common/ReferenceTrackingInterpreter;", "Lorg/jetbrains/kotlin/codegen/optimization/common/OptimizationBasicInterpreter;", "()V", "referenceValueDescriptors", "", "Lorg/jetbrains/kotlin/codegen/optimization/common/ReferenceValueDescriptor;", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicValue;", "getReferenceValueDescriptors", "(Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicValue;)Ljava/util/Set;", "binaryOperation", "insn", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "value1", "value2", "checkRefValuesUsages", "", CompoundEntry.NAME_values, "", "copyOperation", "value", "createMergedValue", "Lorg/jetbrains/kotlin/codegen/optimization/common/TrackedReferenceValue;", RegisterSpec.PREFIX, "w", "createPossiblyMergedValue", "createTaintedValue", "getMergedValueType", "Lorg/jetbrains/org/objectweb/asm/Type;", "type1", "type2", "merge", "mergeDescriptors", "naryOperation", "processRefValueUsage", "position", "", "ternaryOperation", "value3", "unaryOperation", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ReferenceTrackingInterpreter extends OptimizationBasicInterpreter {
    protected abstract void processRefValueUsage(TrackedReferenceValue value, AbstractInsnNode insn, int position);

    @Override // org.jetbrains.kotlin.codegen.optimization.common.OptimizationBasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public /* bridge */ /* synthetic */ Value naryOperation(AbstractInsnNode abstractInsnNode, List list) {
        return naryOperation(abstractInsnNode, (List<? extends BasicValue>) list);
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.common.OptimizationBasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue merge(BasicValue v, BasicValue w) {
        TrackedReferenceValue trackedReferenceValueCreateTaintedValue;
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(w, "w");
        if ((v instanceof ProperTrackedReferenceValue) && (w instanceof ProperTrackedReferenceValue)) {
            if (Intrinsics.areEqual(((ProperTrackedReferenceValue) v).getDescriptor(), ((ProperTrackedReferenceValue) w).getDescriptor())) {
                trackedReferenceValueCreateTaintedValue = (TrackedReferenceValue) v;
            } else {
                trackedReferenceValueCreateTaintedValue = createTaintedValue(v, w);
            }
            return trackedReferenceValueCreateTaintedValue;
        }
        boolean z = v instanceof TrackedReferenceValue;
        if (z && (w instanceof TrackedReferenceValue)) {
            return createPossiblyMergedValue((TrackedReferenceValue) v, (TrackedReferenceValue) w);
        }
        if (z || (w instanceof TrackedReferenceValue)) {
            return createTaintedValue(v, w);
        }
        BasicValue basicValueMerge = super.merge(v, w);
        Intrinsics.checkNotNullExpressionValue(basicValueMerge, "merge(...)");
        return basicValueMerge;
    }

    protected final TrackedReferenceValue createTaintedValue(BasicValue v, BasicValue w) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(w, "w");
        Type mergedValueType = getMergedValueType(v.getType(), w.getType());
        Set<ReferenceValueDescriptor> setMergeDescriptors = mergeDescriptors(v, w);
        setMergeDescriptors.isEmpty();
        return new TaintedTrackedReferenceValue(mergedValueType, setMergeDescriptors);
    }

    protected final TrackedReferenceValue createMergedValue(TrackedReferenceValue v, TrackedReferenceValue w) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(w, "w");
        if ((v instanceof TaintedTrackedReferenceValue) || (w instanceof TaintedTrackedReferenceValue)) {
            return createTaintedValue(v, w);
        }
        return new MergedTrackedReferenceValue(getMergedValueType(v.getType(), w.getType()), mergeDescriptors(v, w));
    }

    protected TrackedReferenceValue createPossiblyMergedValue(TrackedReferenceValue v, TrackedReferenceValue w) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(w, "w");
        return createTaintedValue(v, w);
    }

    private final Set<ReferenceValueDescriptor> mergeDescriptors(BasicValue v, BasicValue w) {
        return SetsKt.plus((Set) getReferenceValueDescriptors(v), (Iterable) getReferenceValueDescriptors(w));
    }

    private final Set<ReferenceValueDescriptor> getReferenceValueDescriptors(BasicValue basicValue) {
        return basicValue instanceof TrackedReferenceValue ? ((TrackedReferenceValue) basicValue).getDescriptors() : SetsKt.emptySet();
    }

    protected final Type getMergedValueType(Type type1, Type type2) {
        if (type1 == null || type2 == null) {
            Type OBJECT_TYPE = AsmTypes.OBJECT_TYPE;
            Intrinsics.checkNotNullExpressionValue(OBJECT_TYPE, "OBJECT_TYPE");
            return OBJECT_TYPE;
        }
        if (Intrinsics.areEqual(type1, type2)) {
            return type1;
        }
        Type OBJECT_TYPE2 = AsmTypes.OBJECT_TYPE;
        Intrinsics.checkNotNullExpressionValue(OBJECT_TYPE2, "OBJECT_TYPE");
        return OBJECT_TYPE2;
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.common.OptimizationBasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue copyOperation(AbstractInsnNode insn, BasicValue value) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        Intrinsics.checkNotNullParameter(value, "value");
        if (value instanceof TrackedReferenceValue) {
            checkRefValuesUsages(insn, CollectionsKt.listOf(value));
            return value;
        }
        return super.copyOperation(insn, value);
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.common.OptimizationBasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue unaryOperation(AbstractInsnNode insn, BasicValue value) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        Intrinsics.checkNotNullParameter(value, "value");
        checkRefValuesUsages(insn, CollectionsKt.listOf(value));
        return super.unaryOperation(insn, value);
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.common.OptimizationBasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue binaryOperation(AbstractInsnNode insn, BasicValue value1, BasicValue value2) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        Intrinsics.checkNotNullParameter(value1, "value1");
        Intrinsics.checkNotNullParameter(value2, "value2");
        checkRefValuesUsages(insn, CollectionsKt.listOf((Object[]) new BasicValue[]{value1, value2}));
        return super.binaryOperation(insn, value1, value2);
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.common.OptimizationBasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue ternaryOperation(AbstractInsnNode insn, BasicValue value1, BasicValue value2, BasicValue value3) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        Intrinsics.checkNotNullParameter(value1, "value1");
        Intrinsics.checkNotNullParameter(value2, "value2");
        Intrinsics.checkNotNullParameter(value3, "value3");
        checkRefValuesUsages(insn, CollectionsKt.listOf((Object[]) new BasicValue[]{value1, value2, value3}));
        return super.ternaryOperation(insn, value1, value2, value3);
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.common.OptimizationBasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue naryOperation(AbstractInsnNode insn, List<? extends BasicValue> values) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        Intrinsics.checkNotNullParameter(values, "values");
        checkRefValuesUsages(insn, values);
        return super.naryOperation(insn, values);
    }

    protected void checkRefValuesUsages(AbstractInsnNode insn, List<? extends BasicValue> values) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        Intrinsics.checkNotNullParameter(values, "values");
        List<? extends BasicValue> list = values;
        for (BasicValue basicValue : list) {
            if (basicValue instanceof TaintedTrackedReferenceValue) {
                Iterator<T> it = ((TaintedTrackedReferenceValue) basicValue).getDescriptors().iterator();
                while (it.hasNext()) {
                    ((ReferenceValueDescriptor) it.next()).onUseAsTainted();
                }
            }
        }
        int i = 0;
        for (Object obj : list) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            BasicValue basicValue2 = (BasicValue) obj;
            if (basicValue2 instanceof TrackedReferenceValue) {
                processRefValueUsage((TrackedReferenceValue) basicValue2, insn, i);
            }
            i = i2;
        }
    }
}
