package org.jetbrains.kotlin.codegen.inline;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicInterpreter;
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue;

/* JADX INFO: compiled from: MethodInlinerUtil.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\u0006H\u0016J\u001c\u0010\n\u001a\u00020\u00062\b\u0010\u000b\u001a\u0004\u0018\u00010\u00062\b\u0010\f\u001a\u0004\u0018\u00010\u0006H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/Aload0Interpreter;", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicInterpreter;", "node", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "(Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;)V", "copyOperation", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicValue;", "insn", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "value", "merge", RegisterSpec.PREFIX, "w", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Aload0Interpreter extends BasicInterpreter {
    private final MethodNode node;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Aload0Interpreter(MethodNode node) {
        super(589824);
        Intrinsics.checkNotNullParameter(node, "node");
        this.node = node;
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.BasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue copyOperation(AbstractInsnNode insn, BasicValue value) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        if (MethodInlinerUtilKt.isAload0(insn)) {
            return new Aload0BasicValue(this.node.instructions.indexOf(insn));
        }
        if (insn.getOpcode() != 25) {
            return super.copyOperation(insn, value);
        }
        if (value == null) {
            return null;
        }
        return new BasicValue(value.getType());
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.BasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue merge(BasicValue v, BasicValue w) {
        if ((v instanceof Aload0BasicValue) && (w instanceof Aload0BasicValue)) {
            return ((Aload0BasicValue) v).plus((Aload0BasicValue) w);
        }
        BasicValue basicValueMerge = super.merge(v, w);
        Intrinsics.checkNotNullExpressionValue(basicValueMerge, "merge(...)");
        return basicValueMerge;
    }
}
