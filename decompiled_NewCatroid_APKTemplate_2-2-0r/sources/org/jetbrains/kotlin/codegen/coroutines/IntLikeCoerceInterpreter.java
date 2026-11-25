package org.jetbrains.kotlin.codegen.coroutines;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.arsc.value.CompoundEntry;
import com.reandroid.dex.sections.Marker;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.common.OptimizationBasicInterpreter;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.FieldInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InvokeDynamicInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodInsnNode;
import org.jetbrains.org.objectweb.asm.tree.VarInsnNode;
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue;
import org.jetbrains.org.objectweb.asm.tree.analysis.Value;

/* JADX INFO: compiled from: SpilledVariableFieldTypesAnalysis.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\b\u0005\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\"\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\nH\u0016J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0006H\u0002J\u001c\u0010\u0014\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\u0011\u001a\u0004\u0018\u00010\nH\u0016J\u0018\u0010\u0015\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\nH\u0016J$\u0010\u0016\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\u0010\u0010\u0017\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\n0\u0018H\u0016J0\u0010\u0019\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\u001a\u001a\u0004\u0018\u00010\n2\b\u0010\u001b\u001a\u0004\u0018\u00010\n2\b\u0010\u0011\u001a\u0004\u0018\u00010\nH\u0016J\u001c\u0010\u001c\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\u0011\u001a\u0004\u0018\u00010\nH\u0016R\u001d\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/codegen/coroutines/IntLikeCoerceInterpreter;", "Lorg/jetbrains/kotlin/codegen/optimization/common/OptimizationBasicInterpreter;", "()V", "needsToBeCoerced", "", "Lorg/jetbrains/org/objectweb/asm/tree/VarInsnNode;", "Lorg/jetbrains/org/objectweb/asm/Type;", "getNeedsToBeCoerced", "()Ljava/util/Map;", "binaryOperation", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicValue;", "insn", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", RegisterSpec.PREFIX, "w", "coerce", "", "value", "Lorg/jetbrains/kotlin/codegen/coroutines/IloadedValue;", ModuleXmlParser.TYPE, "copyOperation", "merge", "naryOperation", CompoundEntry.NAME_values, "", "ternaryOperation", "arrayref", "index", "unaryOperation", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
final class IntLikeCoerceInterpreter extends OptimizationBasicInterpreter {
    private final Map<VarInsnNode, Type> needsToBeCoerced = new LinkedHashMap();

    @Override // org.jetbrains.kotlin.codegen.optimization.common.OptimizationBasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public /* bridge */ /* synthetic */ Value naryOperation(AbstractInsnNode abstractInsnNode, List list) {
        return naryOperation(abstractInsnNode, (List<? extends BasicValue>) list);
    }

    public final Map<VarInsnNode, Type> getNeedsToBeCoerced() {
        return this.needsToBeCoerced;
    }

    private final void coerce(IloadedValue value, Type type) {
        Iterator<VarInsnNode> it = value.getInsns().iterator();
        while (it.hasNext()) {
            this.needsToBeCoerced.put(it.next(), type);
        }
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.common.OptimizationBasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue copyOperation(AbstractInsnNode insn, BasicValue value) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        if (insn.getOpcode() == 21) {
            return new IloadedValue(SetsKt.setOf((VarInsnNode) insn));
        }
        if (value == null) {
            return null;
        }
        return new BasicValue(value.getType());
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.common.OptimizationBasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue binaryOperation(AbstractInsnNode insn, BasicValue v, BasicValue w) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(w, "w");
        if (insn.getOpcode() == 181) {
            Type type = Type.getType(((FieldInsnNode) insn).desc);
            if (w instanceof IloadedValue) {
                Intrinsics.checkNotNull(type);
                if (SpilledVariableFieldTypesAnalysisKt.isIntLike(type)) {
                    coerce((IloadedValue) w, type);
                }
            }
        }
        return super.binaryOperation(insn, v, w);
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.common.OptimizationBasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue unaryOperation(AbstractInsnNode insn, BasicValue value) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        if (insn.getOpcode() == 179) {
            Type type = Type.getType(((FieldInsnNode) insn).desc);
            if (value instanceof IloadedValue) {
                Intrinsics.checkNotNull(type);
                if (SpilledVariableFieldTypesAnalysisKt.isIntLike(type)) {
                    coerce((IloadedValue) value, type);
                }
            }
        }
        return super.unaryOperation(insn, value);
    }

    private static final void naryOperation$checkTypes(List<? extends BasicValue> list, IntLikeCoerceInterpreter intLikeCoerceInterpreter, Type[] typeArr, boolean z) {
        int length = typeArr.length;
        for (int i = 0; i < length; i++) {
            Type type = typeArr[i];
            BasicValue basicValue = list.get(i + (z ? 1 : 0));
            if (basicValue != null && SpilledVariableFieldTypesAnalysisKt.isIntLike(type) && (basicValue instanceof IloadedValue)) {
                intLikeCoerceInterpreter.coerce((IloadedValue) basicValue, type);
            }
        }
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.common.OptimizationBasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue naryOperation(AbstractInsnNode insn, List<? extends BasicValue> values) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        Intrinsics.checkNotNullParameter(values, "values");
        switch (insn.getOpcode()) {
            case 182:
            case 183:
            case 185:
                Type[] argumentTypes = Type.getArgumentTypes(((MethodInsnNode) insn).desc);
                Intrinsics.checkNotNullExpressionValue(argumentTypes, "getArgumentTypes(...)");
                naryOperation$checkTypes(values, this, argumentTypes, true);
                break;
            case 184:
                Type[] argumentTypes2 = Type.getArgumentTypes(((MethodInsnNode) insn).desc);
                Intrinsics.checkNotNullExpressionValue(argumentTypes2, "getArgumentTypes(...)");
                naryOperation$checkTypes(values, this, argumentTypes2, false);
                break;
            case 186:
                Type[] argumentTypes3 = Type.getArgumentTypes(((InvokeDynamicInsnNode) insn).desc);
                Intrinsics.checkNotNullExpressionValue(argumentTypes3, "getArgumentTypes(...)");
                naryOperation$checkTypes(values, this, argumentTypes3, false);
                break;
        }
        return super.naryOperation(insn, values);
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.common.OptimizationBasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue ternaryOperation(AbstractInsnNode insn, BasicValue arrayref, BasicValue index, BasicValue value) {
        Type type;
        Intrinsics.checkNotNullParameter(insn, "insn");
        switch (insn.getOpcode()) {
            case 84:
                if (value instanceof IloadedValue) {
                    Type type2 = Intrinsics.areEqual((arrayref == null || (type = arrayref.getType()) == null) ? null : type.getDescriptor(), "[Z") ? Type.BOOLEAN_TYPE : Type.BYTE_TYPE;
                    Intrinsics.checkNotNull(type2);
                    coerce((IloadedValue) value, type2);
                }
                break;
            case 85:
                if (value instanceof IloadedValue) {
                    Type CHAR_TYPE = Type.CHAR_TYPE;
                    Intrinsics.checkNotNullExpressionValue(CHAR_TYPE, "CHAR_TYPE");
                    coerce((IloadedValue) value, CHAR_TYPE);
                }
                break;
            case 86:
                if (value instanceof IloadedValue) {
                    Type SHORT_TYPE = Type.SHORT_TYPE;
                    Intrinsics.checkNotNullExpressionValue(SHORT_TYPE, "SHORT_TYPE");
                    coerce((IloadedValue) value, SHORT_TYPE);
                }
                break;
        }
        return super.ternaryOperation(insn, arrayref, index, value);
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.common.OptimizationBasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue merge(BasicValue v, BasicValue w) {
        Object next;
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(w, "w");
        if ((v instanceof IloadedValue) && (w instanceof IloadedValue)) {
            IloadedValue iloadedValue = (IloadedValue) v;
            IloadedValue iloadedValue2 = (IloadedValue) w;
            if (Intrinsics.areEqual(iloadedValue.getType(), iloadedValue2.getType())) {
                Set setPlus = SetsKt.plus((Set) iloadedValue.getInsns(), (Iterable) iloadedValue2.getInsns());
                Iterator it = setPlus.iterator();
                do {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                } while (!this.needsToBeCoerced.containsKey((VarInsnNode) next));
                VarInsnNode varInsnNode = (VarInsnNode) next;
                if (varInsnNode != null) {
                    Type type = this.needsToBeCoerced.get(varInsnNode);
                    Intrinsics.checkNotNull(type);
                    Type type2 = type;
                    coerce(iloadedValue, type2);
                    coerce(iloadedValue2, type2);
                }
                return new IloadedValue(setPlus);
            }
        }
        if (Intrinsics.areEqual(v.getType(), w.getType())) {
            return w instanceof IloadedValue ? w : v;
        }
        BasicValue basicValueMerge = super.merge(v, w);
        Intrinsics.checkNotNullExpressionValue(basicValueMerge, "merge(...)");
        return basicValueMerge;
    }
}
