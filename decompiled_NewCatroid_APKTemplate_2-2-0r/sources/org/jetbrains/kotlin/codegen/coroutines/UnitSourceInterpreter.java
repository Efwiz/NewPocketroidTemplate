package org.jetbrains.kotlin.codegen.coroutines;

import com.reandroid.arsc.value.CompoundEntry;
import com.reandroid.dex.sections.Marker;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.codegen.optimization.boxing.PopBackwardPropagationTransformerKt;
import org.jetbrains.kotlin.codegen.optimization.common.FastMethodAnalyzer;
import org.jetbrains.kotlin.codegen.optimization.common.UtilKt;
import org.jetbrains.kotlin.codegen.optimization.fixStack.StackTransformationUtilsKt;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.VarInsnNode;
import org.jetbrains.org.objectweb.asm.tree.analysis.AnalyzerException;
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicInterpreter;
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue;
import org.jetbrains.org.objectweb.asm.tree.analysis.Frame;
import org.jetbrains.org.objectweb.asm.tree.analysis.Value;

/* JADX INFO: compiled from: RedundantLocalsEliminationMethodTransformer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010#\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0002\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J&\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\b2\b\u0010\u0012\u001a\u0004\u0018\u00010\u00102\b\u0010\u0013\u001a\u0004\u0018\u00010\u0010H\u0016J\u0018\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u001c\u0010\u0019\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\b2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0010H\u0016J\u0012\u0010\u001a\u001a\u00020\u00152\b\u0010\u0017\u001a\u0004\u0018\u00010\u0010H\u0002J\u001e\u0010\u001b\u001a\u0004\u0018\u00010\u00102\b\u0010\u0012\u001a\u0004\u0018\u00010\u00102\b\u0010\u0013\u001a\u0004\u0018\u00010\u0010H\u0016J\"\u0010\u001c\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\b2\u000e\u0010\u001d\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u001eH\u0016J\u0012\u0010\u001f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\bH\u0016J)\u0010 \u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\"0!2\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&¢\u0006\u0002\u0010'J0\u0010(\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\b2\b\u0010\u0012\u001a\u0004\u0018\u00010\u00102\b\u0010\u0013\u001a\u0004\u0018\u00010\u00102\b\u0010)\u001a\u0004\u0018\u00010\u0010H\u0016J\u001c\u0010*\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\b2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0010H\u0016R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R#\u0010\u0006\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\t0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\b0\t¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/codegen/coroutines/UnitSourceInterpreter;", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicInterpreter;", "localVariables", "", "", "(Ljava/util/Set;)V", "unitUsageInformation", "", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "", "getUnitUsageInformation", "()Ljava/util/Map;", "unspillableUnitValues", "getUnspillableUnitValues", "()Ljava/util/Set;", "binaryOperation", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicValue;", "insn", "value1", "value2", "collectUnitUsage", "", "use", "value", "Lorg/jetbrains/kotlin/codegen/coroutines/UnitValue;", "copyOperation", "markUnspillable", "merge", "naryOperation", CompoundEntry.NAME_values, "", "newOperation", "run", "", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/Frame;", "internalClassName", "", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "(Ljava/lang/String;Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;)[Lorg/jetbrains/org/objectweb/asm/tree/analysis/Frame;", "ternaryOperation", "value3", "unaryOperation", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
final class UnitSourceInterpreter extends BasicInterpreter {
    private final Set<Integer> localVariables;
    private final Map<AbstractInsnNode, Set<AbstractInsnNode>> unitUsageInformation;
    private final Set<AbstractInsnNode> unspillableUnitValues;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UnitSourceInterpreter(Set<Integer> localVariables) {
        super(589824);
        Intrinsics.checkNotNullParameter(localVariables, "localVariables");
        this.localVariables = localVariables;
        this.unspillableUnitValues = new LinkedHashSet();
        this.unitUsageInformation = new LinkedHashMap();
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.BasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public /* bridge */ /* synthetic */ Value naryOperation(AbstractInsnNode abstractInsnNode, List list) {
        return naryOperation(abstractInsnNode, (List<? extends BasicValue>) list);
    }

    public final Set<AbstractInsnNode> getUnspillableUnitValues() {
        return this.unspillableUnitValues;
    }

    public final Map<AbstractInsnNode, Set<AbstractInsnNode>> getUnitUsageInformation() {
        return this.unitUsageInformation;
    }

    private final void markUnspillable(BasicValue value) {
        UnitValue unitValue = value instanceof UnitValue ? (UnitValue) value : null;
        if (unitValue != null) {
            CollectionsKt.addAll(this.unspillableUnitValues, unitValue.getInsns());
        }
    }

    private final void collectUnitUsage(AbstractInsnNode use, UnitValue value) {
        for (AbstractInsnNode abstractInsnNode : value.getInsns()) {
            if (!this.unspillableUnitValues.contains(abstractInsnNode)) {
                Map<AbstractInsnNode, Set<AbstractInsnNode>> map = this.unitUsageInformation;
                LinkedHashSet linkedHashSet = map.get(abstractInsnNode);
                if (linkedHashSet == null) {
                    linkedHashSet = new LinkedHashSet();
                    map.put(abstractInsnNode, linkedHashSet);
                }
                linkedHashSet.add(use);
            }
        }
    }

    public final Frame<BasicValue>[] run(String internalClassName, MethodNode methodNode) throws AnalyzerException {
        Intrinsics.checkNotNullParameter(internalClassName, "internalClassName");
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        Frame<BasicValue>[] frameArrAnalyze = new FastMethodAnalyzer(internalClassName, methodNode, this, false, 8, null).analyze();
        InsnList instructions = methodNode.instructions;
        Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
        for (Pair pair : SequencesKt.zip(UtilKt.asSequence(instructions), ArraysKt.asSequence(frameArrAnalyze))) {
            AbstractInsnNode abstractInsnNode = (AbstractInsnNode) pair.component1();
            Frame frame = (Frame) pair.component2();
            if (frame != null && abstractInsnNode.getOpcode() == 87) {
                BasicValue basicValue = (BasicValue) StackTransformationUtilsKt.top(frame);
                if (basicValue instanceof UnitValue) {
                    collectUnitUsage(abstractInsnNode, (UnitValue) basicValue);
                }
            }
        }
        return frameArrAnalyze;
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.BasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue newOperation(AbstractInsnNode insn) throws AnalyzerException {
        if (insn != null && PopBackwardPropagationTransformerKt.isUnitInstance(insn)) {
            return new UnitValue(insn);
        }
        BasicValue basicValueNewOperation = super.newOperation(insn);
        Intrinsics.checkNotNullExpressionValue(basicValueNewOperation, "newOperation(...)");
        return basicValueNewOperation;
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.BasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue copyOperation(AbstractInsnNode insn, BasicValue value) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        if (value instanceof UnitValue) {
            if (insn instanceof VarInsnNode) {
                VarInsnNode varInsnNode = (VarInsnNode) insn;
                if (varInsnNode.getOpcode() == 58 && !this.localVariables.contains(Integer.valueOf(varInsnNode.var))) {
                    collectUnitUsage(insn, (UnitValue) value);
                    return value;
                }
            }
            CollectionsKt.addAll(this.unspillableUnitValues, ((UnitValue) value).getInsns());
        }
        return super.copyOperation(insn, value);
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.BasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue unaryOperation(AbstractInsnNode insn, BasicValue value) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        markUnspillable(value);
        return super.unaryOperation(insn, value);
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.BasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue binaryOperation(AbstractInsnNode insn, BasicValue value1, BasicValue value2) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        markUnspillable(value1);
        markUnspillable(value2);
        return super.binaryOperation(insn, value1, value2);
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.BasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue ternaryOperation(AbstractInsnNode insn, BasicValue value1, BasicValue value2, BasicValue value3) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        markUnspillable(value1);
        markUnspillable(value2);
        markUnspillable(value3);
        return super.ternaryOperation(insn, value1, value2, value3);
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.BasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue naryOperation(AbstractInsnNode insn, List<? extends BasicValue> values) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        if (values != null) {
            Iterator<T> it = values.iterator();
            while (it.hasNext()) {
                markUnspillable((BasicValue) it.next());
            }
        }
        return super.naryOperation(insn, values);
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.BasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue merge(BasicValue value1, BasicValue value2) {
        if ((value1 instanceof UnitValue) && (value2 instanceof UnitValue)) {
            UnitValue unitValue = new UnitValue((Set<? extends AbstractInsnNode>) CollectionsKt.union(((UnitValue) value1).getInsns(), ((UnitValue) value2).getInsns()));
            Set<AbstractInsnNode> insns = unitValue.getInsns();
            if (!(insns instanceof Collection) || !insns.isEmpty()) {
                Iterator<T> it = insns.iterator();
                while (it.hasNext()) {
                    if (this.unspillableUnitValues.contains((AbstractInsnNode) it.next())) {
                        markUnspillable(unitValue);
                        break;
                    }
                }
            }
            return unitValue;
        }
        markUnspillable(value1);
        markUnspillable(value2);
        return super.merge(value1, value2);
    }
}
