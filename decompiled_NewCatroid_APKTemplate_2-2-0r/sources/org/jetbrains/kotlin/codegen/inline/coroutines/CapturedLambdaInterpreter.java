package org.jetbrains.kotlin.codegen.inline.coroutines;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicInterpreter;
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue;

/* JADX INFO: compiled from: CoroutineTransformer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0004H\u0016J\u001e\u0010\b\u001a\u0004\u0018\u00010\u00042\b\u0010\t\u001a\u0004\u0018\u00010\u00042\b\u0010\n\u001a\u0004\u0018\u00010\u0004H\u0016J\u0012\u0010\u000b\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u001c\u0010\f\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0004H\u0016J\u000e\u0010\r\u001a\u0004\u0018\u00010\u000e*\u00020\u0006H\u0002¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/coroutines/CapturedLambdaInterpreter;", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicInterpreter;", "()V", "copyOperation", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicValue;", "insn", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "value", "merge", RegisterSpec.PREFIX, "w", "newOperation", "unaryOperation", "fieldLoad", "Lorg/jetbrains/kotlin/codegen/inline/coroutines/PossibleLambdaLoad;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
final class CapturedLambdaInterpreter extends BasicInterpreter {
    public CapturedLambdaInterpreter() {
        super(589824);
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.BasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue newOperation(AbstractInsnNode insn) {
        PossibleLambdaLoad possibleLambdaLoadFieldLoad;
        Intrinsics.checkNotNullParameter(insn, "insn");
        return (insn.getOpcode() != 178 || (possibleLambdaLoadFieldLoad = fieldLoad(insn)) == null) ? super.newOperation(insn) : possibleLambdaLoadFieldLoad;
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0059, code lost:
    
        if (org.jetbrains.kotlin.codegen.inline.InlineCodegenUtilsKt.isCapturedFieldName(r0) != false) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final org.jetbrains.kotlin.codegen.inline.coroutines.PossibleLambdaLoad fieldLoad(org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode r8) {
        /*
            r7 = this;
            boolean r0 = r8 instanceof org.jetbrains.org.objectweb.asm.tree.FieldInsnNode
            r1 = 0
            if (r0 != 0) goto L6
            return r1
        L6:
            r0 = r8
            org.jetbrains.org.objectweb.asm.tree.FieldInsnNode r0 = (org.jetbrains.org.objectweb.asm.tree.FieldInsnNode) r0
            java.lang.String r2 = r0.desc
            java.lang.String r3 = "desc"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r2, r3)
            java.lang.CharSequence r2 = (java.lang.CharSequence) r2
            r3 = 76
            r4 = 0
            r5 = 2
            boolean r2 = kotlin.text.StringsKt.startsWith$default(r2, r3, r4, r5, r1)
            if (r2 == 0) goto L61
            java.lang.String r2 = r0.desc
            org.jetbrains.org.objectweb.asm.Type r2 = org.jetbrains.org.objectweb.asm.Type.getType(r2)
            java.lang.String r2 = r2.getInternalName()
            java.lang.String r3 = "getInternalName(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r2, r3)
            boolean r2 = org.jetbrains.kotlin.codegen.inline.InlineCodegenUtilsKt.isNumberedFunctionInternalName(r2)
            if (r2 == 0) goto L61
            int r2 = r0.getOpcode()
            r3 = 178(0xb2, float:2.5E-43)
            java.lang.String r6 = "name"
            if (r2 != r3) goto L48
            java.lang.String r2 = r0.f1010name
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r2, r6)
            java.lang.String r3 = "$$$$"
            boolean r2 = kotlin.text.StringsKt.startsWith$default(r2, r3, r4, r5, r1)
            if (r2 != 0) goto L5b
        L48:
            int r2 = r0.getOpcode()
            r3 = 180(0xb4, float:2.52E-43)
            if (r2 != r3) goto L61
            java.lang.String r0 = r0.f1010name
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r6)
            boolean r0 = org.jetbrains.kotlin.codegen.inline.InlineCodegenUtilsKt.isCapturedFieldName(r0)
            if (r0 == 0) goto L61
        L5b:
            org.jetbrains.kotlin.codegen.inline.coroutines.PossibleLambdaLoad r0 = new org.jetbrains.kotlin.codegen.inline.coroutines.PossibleLambdaLoad
            r0.<init>(r8)
            return r0
        L61:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.codegen.inline.coroutines.CapturedLambdaInterpreter.fieldLoad(org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode):org.jetbrains.kotlin.codegen.inline.coroutines.PossibleLambdaLoad");
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.BasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue copyOperation(AbstractInsnNode insn, BasicValue value) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        return insn.getOpcode() == 25 ? new PossibleLambdaLoad(insn) : super.copyOperation(insn, value);
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.BasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue unaryOperation(AbstractInsnNode insn, BasicValue value) {
        PossibleLambdaLoad possibleLambdaLoadFieldLoad;
        Intrinsics.checkNotNullParameter(insn, "insn");
        return (insn.getOpcode() != 180 || (possibleLambdaLoadFieldLoad = fieldLoad(insn)) == null) ? super.unaryOperation(insn, value) : possibleLambdaLoadFieldLoad;
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.BasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue merge(BasicValue v, BasicValue w) {
        return ((v instanceof PossibleLambdaLoad) && (w instanceof PossibleLambdaLoad) && Intrinsics.areEqual(((PossibleLambdaLoad) v).getInsn(), ((PossibleLambdaLoad) w).getInsn())) ? v : super.merge(v, w);
    }
}
