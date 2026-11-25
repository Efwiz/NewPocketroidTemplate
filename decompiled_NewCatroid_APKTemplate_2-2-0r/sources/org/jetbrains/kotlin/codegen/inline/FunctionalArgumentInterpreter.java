package org.jetbrains.kotlin.codegen.inline;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.FieldInsnNode;
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicInterpreter;
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue;

/* JADX INFO: compiled from: MethodInlinerUtil.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001e\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u00062\b\u0010\b\u001a\u0004\u0018\u00010\u0006H\u0016J\u0012\u0010\t\u001a\u0004\u0018\u00010\u00062\u0006\u0010\n\u001a\u00020\u000bH\u0016J \u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u001a\u0010\u0013\u001a\u0004\u0018\u00010\u00062\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u0006H\u0016J\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u00062\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\u0016\u001a\u0004\u0018\u00010\u0006H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/FunctionalArgumentInterpreter;", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicInterpreter;", "inliner", "Lorg/jetbrains/kotlin/codegen/inline/MethodInliner;", "(Lorg/jetbrains/kotlin/codegen/inline/MethodInliner;)V", "merge", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicValue;", RegisterSpec.PREFIX, "w", "newOperation", "insn", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "newParameterValue", "isInstanceMethod", "", "local", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", "unaryOperation", "value", "wrapArgumentInValueIfNeeded", "basicValue", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FunctionalArgumentInterpreter extends BasicInterpreter {
    private final MethodInliner inliner;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FunctionalArgumentInterpreter(MethodInliner inliner) {
        super(589824);
        Intrinsics.checkNotNullParameter(inliner, "inliner");
        this.inliner = inliner;
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue newParameterValue(boolean isInstanceMethod, int local, Type type) {
        Intrinsics.checkNotNullParameter(type, "type");
        FunctionalArgument functionalArgumentIfExists$backend = this.inliner.getFunctionalArgumentIfExists$backend(local);
        if (functionalArgumentIfExists$backend != null) {
            return new FunctionalArgumentValue(functionalArgumentIfExists$backend, newValue(type));
        }
        BasicValue basicValueNewValue = newValue(type);
        Intrinsics.checkNotNullExpressionValue(basicValueNewValue, "newValue(...)");
        return basicValueNewValue;
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.BasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue unaryOperation(AbstractInsnNode insn, BasicValue value) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        Intrinsics.checkNotNullParameter(value, "value");
        return wrapArgumentInValueIfNeeded(insn, super.unaryOperation(insn, value));
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.BasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue newOperation(AbstractInsnNode insn) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        return wrapArgumentInValueIfNeeded(insn, super.newOperation(insn));
    }

    private final BasicValue wrapArgumentInValueIfNeeded(AbstractInsnNode insn, BasicValue basicValue) {
        FunctionalArgument functionalArgumentIfExists$backend;
        return (!(insn instanceof FieldInsnNode) || (functionalArgumentIfExists$backend = this.inliner.getFunctionalArgumentIfExists$backend((FieldInsnNode) insn)) == null) ? basicValue : new FunctionalArgumentValue(functionalArgumentIfExists$backend, basicValue);
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.BasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue merge(BasicValue v, BasicValue w) {
        return ((v instanceof FunctionalArgumentValue) && (w instanceof FunctionalArgumentValue) && Intrinsics.areEqual(((FunctionalArgumentValue) v).getFunctionalArgument(), ((FunctionalArgumentValue) w).getFunctionalArgument())) ? v : super.merge(v, w);
    }
}
