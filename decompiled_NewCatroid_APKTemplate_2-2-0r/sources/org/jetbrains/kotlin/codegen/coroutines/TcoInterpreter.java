package org.jetbrains.kotlin.codegen.coroutines;

import com.reandroid.arsc.value.CompoundEntry;
import com.reandroid.dex.sections.Marker;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicInterpreter;
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue;
import org.jetbrains.org.objectweb.asm.tree.analysis.Value;

/* JADX INFO: compiled from: TailCallOptimization.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010!\n\u0002\b\u0006\b\u0002\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J&\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u00072\b\u0010\u000b\u001a\u0004\u0018\u00010\u0007H\u0016J\u001c\u0010\f\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\t2\b\u0010\r\u001a\u0004\u0018\u00010\u0007H\u0016J\u001c\u0010\u000e\u001a\u00020\u00072\b\u0010\n\u001a\u0004\u0018\u00010\u00072\b\u0010\u000b\u001a\u0004\u0018\u00010\u0007H\u0016J&\u0010\u000f\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\t2\u0012\u0010\u0010\u001a\u000e\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u0007\u0018\u00010\u0011H\u0016J\u0012\u0010\u0012\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J0\u0010\u0013\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u00072\b\u0010\u000b\u001a\u0004\u0018\u00010\u00072\b\u0010\u0014\u001a\u0004\u0018\u00010\u0007H\u0016J\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\t2\b\u0010\r\u001a\u0004\u0018\u00010\u0007H\u0016J\u0018\u0010\u0016\u001a\u0004\u0018\u00010\u0007*\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\tH\u0002R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/codegen/coroutines/TcoInterpreter;", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicInterpreter;", "suspensionPoints", "", "Lorg/jetbrains/kotlin/codegen/coroutines/SuspensionPoint;", "(Ljava/util/List;)V", "binaryOperation", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicValue;", "insn", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "value1", "value2", "copyOperation", "value", "merge", "naryOperation", CompoundEntry.NAME_values, "", "newOperation", "ternaryOperation", "value3", "unaryOperation", "convert", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
final class TcoInterpreter extends BasicInterpreter {
    private final List<SuspensionPoint> suspensionPoints;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TcoInterpreter(List<SuspensionPoint> suspensionPoints) {
        super(589824);
        Intrinsics.checkNotNullParameter(suspensionPoints, "suspensionPoints");
        this.suspensionPoints = suspensionPoints;
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.BasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public /* bridge */ /* synthetic */ Value naryOperation(AbstractInsnNode abstractInsnNode, List list) {
        return naryOperation(abstractInsnNode, (List<? extends BasicValue>) list);
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.BasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue copyOperation(AbstractInsnNode insn, BasicValue value) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        return convert(super.copyOperation(insn, value), insn);
    }

    private final BasicValue convert(BasicValue basicValue, AbstractInsnNode abstractInsnNode) {
        return CoroutineTransformerMethodVisitorKt.contains(this.suspensionPoints, abstractInsnNode) ? TailCallOptimizationKt.toFromSuspensionPoint(basicValue) : basicValue;
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.BasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue naryOperation(AbstractInsnNode insn, List<? extends BasicValue> values) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        return convert(super.naryOperation(insn, values), insn);
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.BasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue ternaryOperation(AbstractInsnNode insn, BasicValue value1, BasicValue value2, BasicValue value3) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        return convert(super.ternaryOperation(insn, value1, value2, value3), insn);
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.BasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue merge(BasicValue value1, BasicValue value2) {
        if ((value1 instanceof FromSuspensionPointValue) || (value2 instanceof FromSuspensionPointValue)) {
            return FromSuspensionPointValue.INSTANCE;
        }
        BasicValue basicValueMerge = super.merge(value1, value2);
        Intrinsics.checkNotNullExpressionValue(basicValueMerge, "merge(...)");
        return basicValueMerge;
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.BasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue unaryOperation(AbstractInsnNode insn, BasicValue value) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        return ((value instanceof FromSuspensionPointValue) && insn.getOpcode() == 192) ? value : convert(super.unaryOperation(insn, value), insn);
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.BasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue binaryOperation(AbstractInsnNode insn, BasicValue value1, BasicValue value2) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        return convert(super.binaryOperation(insn, value1, value2), insn);
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.BasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue newOperation(AbstractInsnNode insn) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        return convert(super.newOperation(insn), insn);
    }
}
