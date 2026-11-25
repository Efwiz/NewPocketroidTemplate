package org.jetbrains.kotlin.codegen.optimization.boxing;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.arsc.value.CompoundEntry;
import com.reandroid.dex.sections.Marker;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.common.OptimizationBasicInterpreter;
import org.jetbrains.kotlin.codegen.optimization.common.StrictBasicValue;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.TypeInsnNode;
import org.jetbrains.org.objectweb.asm.tree.analysis.AnalyzerException;
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue;
import org.jetbrains.org.objectweb.asm.tree.analysis.Value;

/* JADX INFO: compiled from: BoxingInterpreter.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010 \n\u0002\b\r\b\u0016\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0004J\"\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u000eH\u0014J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0014\u001a\u00020\rH\u0002J\u0010\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u0011\u001a\u00020\u0012H\u0014J\u0018\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u001c\u001a\u00020\u00122\u0006\u0010\u001d\u001a\u00020\u0012H\u0016J \u0010\u001b\u001a\u00020\u00122\u0006\u0010\u001c\u001a\u00020\u00122\u0006\u0010\u001d\u001a\u00020\u00122\u0006\u0010\u001e\u001a\u00020\u0019H\u0002J \u0010\u001f\u001a\u00020\u00122\u0006\u0010 \u001a\u00020\n2\u0006\u0010!\u001a\u00020\u00122\u0006\u0010\u001e\u001a\u00020\u0019H\u0002J\u0016\u0010\"\u001a\u00020\u00122\u0006\u0010\u001c\u001a\u00020\u00122\u0006\u0010\u001d\u001a\u00020\u0012J\u0016\u0010#\u001a\u00020\u00122\u0006\u0010\u001c\u001a\u00020\u00122\u0006\u0010\u001d\u001a\u00020\u0012J \u0010$\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0014\u001a\u00020\r2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00120&H\u0016J \u0010'\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\r2\u0006\u0010(\u001a\u00020\n2\u0006\u0010)\u001a\u00020\nH\u0014J \u0010*\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\r2\u0006\u0010(\u001a\u00020\n2\u0006\u0010)\u001a\u00020\nH\u0014J\u0010\u0010+\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\nH\u0014J\u0018\u0010,\u001a\u00020\u00102\u0006\u0010\u001c\u001a\u00020\n2\u0006\u0010\u001d\u001a\u00020\nH\u0014J\u0010\u0010-\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\nH\u0014J\u0010\u0010.\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\nH\u0014J \u0010/\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\n2\u0006\u00100\u001a\u00020\u0016H\u0014J\u001a\u00101\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0012\u00102\u001a\u00020\u0010*\b\u0012\u0004\u0012\u00020\u00120&H\u0002R*\u0010\u0007\u001a\u001e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bj\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n`\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\f\u001a\u001e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\bj\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e`\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u00063"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/boxing/BoxingInterpreter;", "Lorg/jetbrains/kotlin/codegen/optimization/common/OptimizationBasicInterpreter;", "insnList", "Lorg/jetbrains/org/objectweb/asm/tree/InsnList;", "generationState", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "(Lorg/jetbrains/org/objectweb/asm/tree/InsnList;Lorg/jetbrains/kotlin/codegen/state/GenerationState;)V", "boxingPlaces", "Ljava/util/HashMap;", "", "Lorg/jetbrains/kotlin/codegen/optimization/boxing/BoxedBasicValue;", "Lkotlin/collections/HashMap;", "progressionIterators", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "Lorg/jetbrains/kotlin/codegen/optimization/boxing/ProgressionIteratorBasicValue;", "checkUsedValue", "", "value", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicValue;", "createNewBoxing", "insn", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", "progressionIterator", "isCastToProgression", "", "isExactValue", "merge", RegisterSpec.PREFIX, "w", "isLocalVariable", "mergeBoxedHazardous", "boxed", "other", "mergeLocalVariableValues", "mergeStackValues", "naryOperation", CompoundEntry.NAME_values, "", "onAreEqual", "value1", "value2", "onCompareTo", "onMergeFail", "onMergeSuccess", "onMethodCallWithBoxedValue", "onNewBoxedValue", "onUnboxing", "resultType", "unaryOperation", "markBoxedArgumentValues", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public class BoxingInterpreter extends OptimizationBasicInterpreter {
    private final HashMap<Integer, BoxedBasicValue> boxingPlaces;
    private final GenerationState generationState;
    private final InsnList insnList;
    private final HashMap<AbstractInsnNode, ProgressionIteratorBasicValue> progressionIterators;

    protected void onAreEqual(AbstractInsnNode insn, BoxedBasicValue value1, BoxedBasicValue value2) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        Intrinsics.checkNotNullParameter(value1, "value1");
        Intrinsics.checkNotNullParameter(value2, "value2");
    }

    protected void onCompareTo(AbstractInsnNode insn, BoxedBasicValue value1, BoxedBasicValue value2) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        Intrinsics.checkNotNullParameter(value1, "value1");
        Intrinsics.checkNotNullParameter(value2, "value2");
    }

    protected void onMergeFail(BoxedBasicValue value) {
        Intrinsics.checkNotNullParameter(value, "value");
    }

    protected void onMergeSuccess(BoxedBasicValue v, BoxedBasicValue w) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(w, "w");
    }

    protected void onMethodCallWithBoxedValue(BoxedBasicValue value) {
        Intrinsics.checkNotNullParameter(value, "value");
    }

    protected void onNewBoxedValue(BoxedBasicValue value) {
        Intrinsics.checkNotNullParameter(value, "value");
    }

    protected void onUnboxing(AbstractInsnNode insn, BoxedBasicValue value, Type resultType) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(resultType, "resultType");
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.common.OptimizationBasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public /* bridge */ /* synthetic */ Value naryOperation(AbstractInsnNode abstractInsnNode, List list) {
        return naryOperation(abstractInsnNode, (List<? extends BasicValue>) list);
    }

    public BoxingInterpreter(InsnList insnList, GenerationState generationState) {
        Intrinsics.checkNotNullParameter(insnList, "insnList");
        Intrinsics.checkNotNullParameter(generationState, "generationState");
        this.insnList = insnList;
        this.generationState = generationState;
        this.boxingPlaces = new HashMap<>();
        this.progressionIterators = new HashMap<>();
    }

    protected BasicValue createNewBoxing(AbstractInsnNode insn, Type type, ProgressionIteratorBasicValue progressionIterator) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        Intrinsics.checkNotNullParameter(type, "type");
        HashMap<Integer, BoxedBasicValue> map = this.boxingPlaces;
        Integer numValueOf = Integer.valueOf(this.insnList.indexOf(insn));
        BoxedBasicValue cleanBoxedValue = map.get(numValueOf);
        if (cleanBoxedValue == null) {
            cleanBoxedValue = new CleanBoxedValue(type, insn, progressionIterator, this.generationState);
            onNewBoxedValue(cleanBoxedValue);
            map.put(numValueOf, cleanBoxedValue);
        }
        return cleanBoxedValue;
    }

    protected final void checkUsedValue(BasicValue value) {
        Intrinsics.checkNotNullParameter(value, "value");
        if (value instanceof TaintedBoxedValue) {
            onMergeFail((BoxedBasicValue) value);
        }
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.common.OptimizationBasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue naryOperation(AbstractInsnNode insn, List<? extends BasicValue> values) throws AnalyzerException {
        Intrinsics.checkNotNullParameter(insn, "insn");
        Intrinsics.checkNotNullParameter(values, "values");
        Iterator<T> it = values.iterator();
        while (it.hasNext()) {
            checkUsedValue((BasicValue) it.next());
        }
        BasicValue basicValueNaryOperation = super.naryOperation(insn, values);
        BasicValue basicValue = (BasicValue) CollectionsKt.firstOrNull((List) values);
        if (basicValue == null) {
            return basicValueNaryOperation;
        }
        if (BoxingInterpreterKt.isBoxing(insn, this.generationState)) {
            markBoxedArgumentValues(values);
            Type type = basicValueNaryOperation.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            return createNewBoxing(insn, type, null);
        }
        if (BoxingInterpreterKt.isUnboxing(insn, this.generationState) && (basicValue instanceof BoxedBasicValue)) {
            Type type2 = basicValueNaryOperation.getType();
            Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
            onUnboxing(insn, (BoxedBasicValue) basicValue, type2);
            return basicValueNaryOperation;
        }
        if (BoxingInterpreterKt.isIteratorMethodCall(insn)) {
            markBoxedArgumentValues(values);
            Type type3 = basicValue.getType();
            Intrinsics.checkNotNull(type3);
            if (BoxingInterpreterKt.isProgressionClass(type3)) {
                HashMap<AbstractInsnNode, ProgressionIteratorBasicValue> map = this.progressionIterators;
                ProgressionIteratorBasicValue progressionIteratorBasicValueByProgressionClassType = map.get(insn);
                if (progressionIteratorBasicValueByProgressionClassType == null) {
                    progressionIteratorBasicValueByProgressionClassType = ProgressionIteratorBasicValue.INSTANCE.byProgressionClassType(insn, type3);
                    Intrinsics.checkNotNull(progressionIteratorBasicValueByProgressionClassType);
                    map.put(insn, progressionIteratorBasicValueByProgressionClassType);
                }
                return progressionIteratorBasicValueByProgressionClassType;
            }
            ProgressionIteratorBasicValue progressionIteratorBasicValue = this.progressionIterators.get(insn);
            if (progressionIteratorBasicValue == null) {
                return basicValueNaryOperation;
            }
            progressionIteratorBasicValue.taint();
            return basicValueNaryOperation;
        }
        if (BoxingInterpreterKt.isNextMethodCallOfProgressionIterator(insn, values)) {
            ProgressionIteratorBasicValue progressionIteratorBasicValue2 = basicValue instanceof ProgressionIteratorBasicValue ? (ProgressionIteratorBasicValue) basicValue : null;
            if (progressionIteratorBasicValue2 == null) {
                throw new AssertionError("firstArg should be progression iterator");
            }
            return createNewBoxing(insn, progressionIteratorBasicValue2.getBoxedElementType(), progressionIteratorBasicValue2);
        }
        if (BoxingInterpreterKt.isAreEqualIntrinsicForSameTypedBoxedValues(insn, values) && BoxingInterpreterKt.canValuesBeUnboxedForAreEqual(values, this.generationState)) {
            BasicValue basicValue2 = values.get(0);
            Intrinsics.checkNotNull(basicValue2, "null cannot be cast to non-null type org.jetbrains.kotlin.codegen.optimization.boxing.BoxedBasicValue");
            BasicValue basicValue3 = values.get(1);
            Intrinsics.checkNotNull(basicValue3, "null cannot be cast to non-null type org.jetbrains.kotlin.codegen.optimization.boxing.BoxedBasicValue");
            onAreEqual(insn, (BoxedBasicValue) basicValue2, (BoxedBasicValue) basicValue3);
            return basicValueNaryOperation;
        }
        if (BoxingInterpreterKt.isJavaLangComparableCompareToForSameTypedBoxedValues(insn, values)) {
            BasicValue basicValue4 = values.get(0);
            Intrinsics.checkNotNull(basicValue4, "null cannot be cast to non-null type org.jetbrains.kotlin.codegen.optimization.boxing.BoxedBasicValue");
            BasicValue basicValue5 = values.get(1);
            Intrinsics.checkNotNull(basicValue5, "null cannot be cast to non-null type org.jetbrains.kotlin.codegen.optimization.boxing.BoxedBasicValue");
            onCompareTo(insn, (BoxedBasicValue) basicValue4, (BoxedBasicValue) basicValue5);
            return basicValueNaryOperation;
        }
        markBoxedArgumentValues(values);
        return basicValueNaryOperation;
    }

    private final void markBoxedArgumentValues(List<? extends BasicValue> list) {
        for (BasicValue basicValue : list) {
            if (basicValue instanceof BoxedBasicValue) {
                onMethodCallWithBoxedValue((BoxedBasicValue) basicValue);
            }
        }
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.common.OptimizationBasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue unaryOperation(AbstractInsnNode insn, BasicValue value) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        Intrinsics.checkNotNullParameter(value, "value");
        checkUsedValue(value);
        return (insn.getOpcode() == 192 && isExactValue(value) && !isCastToProgression(insn)) ? value : super.unaryOperation(insn, value);
    }

    protected boolean isExactValue(BasicValue value) {
        Intrinsics.checkNotNullParameter(value, "value");
        if (!(value instanceof ProgressionIteratorBasicValue) && !(value instanceof CleanBoxedValue)) {
            if (value.getType() != null) {
                Type type = value.getType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                if (BoxingInterpreterKt.isProgressionClass(type)) {
                }
            }
            return false;
        }
        return true;
    }

    private final boolean isCastToProgression(AbstractInsnNode insn) {
        insn.getOpcode();
        Intrinsics.checkNotNull(insn, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.TypeInsnNode");
        return SetsKt.setOf((Object[]) new String[]{"kotlin/ranges/CharProgression", "kotlin/ranges/IntProgression", "kotlin/ranges/LongProgression"}).contains(((TypeInsnNode) insn).desc);
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.common.OptimizationBasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue merge(BasicValue v, BasicValue w) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(w, "w");
        return mergeStackValues(v, w);
    }

    public final BasicValue mergeLocalVariableValues(BasicValue v, BasicValue w) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(w, "w");
        return merge(v, w, true);
    }

    public final BasicValue mergeStackValues(BasicValue v, BasicValue w) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(w, "w");
        return merge(v, w, false);
    }

    private final BasicValue merge(BasicValue v, BasicValue w, boolean isLocalVariable) {
        if (Intrinsics.areEqual(v, StrictBasicValue.UNINITIALIZED_VALUE) || Intrinsics.areEqual(w, StrictBasicValue.UNINITIALIZED_VALUE)) {
            return StrictBasicValue.UNINITIALIZED_VALUE;
        }
        boolean z = v instanceof BoxedBasicValue;
        if (!z || !(w instanceof BoxedBasicValue)) {
            if (z) {
                return mergeBoxedHazardous((BoxedBasicValue) v, w, isLocalVariable);
            }
            if (w instanceof BoxedBasicValue) {
                return mergeBoxedHazardous((BoxedBasicValue) w, v, isLocalVariable);
            }
            BasicValue basicValueMerge = super.merge(v, w);
            Intrinsics.checkNotNullExpressionValue(basicValueMerge, "merge(...)");
            return basicValueMerge;
        }
        BoxedBasicValue boxedBasicValue = (BoxedBasicValue) v;
        BoxedBasicValue boxedBasicValue2 = (BoxedBasicValue) w;
        onMergeSuccess(boxedBasicValue, boxedBasicValue2);
        if (v instanceof TaintedBoxedValue) {
            return v;
        }
        if (w instanceof TaintedBoxedValue) {
            return w;
        }
        return !Intrinsics.areEqual(boxedBasicValue.getType(), boxedBasicValue2.getType()) ? mergeBoxedHazardous(boxedBasicValue, w, isLocalVariable) : v;
    }

    private final BasicValue mergeBoxedHazardous(BoxedBasicValue boxed, BasicValue other, boolean isLocalVariable) {
        if (isLocalVariable) {
            return boxed.taint();
        }
        onMergeFail(boxed);
        if (other instanceof BoxedBasicValue) {
            onMergeFail((BoxedBasicValue) other);
        }
        return boxed;
    }
}
