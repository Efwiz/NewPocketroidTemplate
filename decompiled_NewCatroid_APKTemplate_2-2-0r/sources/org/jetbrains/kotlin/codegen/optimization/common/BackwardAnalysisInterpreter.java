package org.jetbrains.kotlin.codegen.optimization.common;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import org.jetbrains.kotlin.codegen.optimization.common.VarFrame;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;

/* JADX INFO: compiled from: backwardAnalysis.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\bf\u0018\u0000*\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00020\u0003J\u001d\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00028\u00002\u0006\u0010\u0007\u001a\u00020\bH&¢\u0006\u0002\u0010\tJ\u0015\u0010\n\u001a\u00028\u00002\u0006\u0010\u000b\u001a\u00020\fH&¢\u0006\u0002\u0010\rJ\u001d\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00028\u00002\u0006\u0010\u0007\u001a\u00020\bH&¢\u0006\u0002\u0010\tø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/common/BackwardAnalysisInterpreter;", "F", "Lorg/jetbrains/kotlin/codegen/optimization/common/VarFrame;", "", "def", "", TypedValues.AttributesType.S_FRAME, "insn", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "(Lorg/jetbrains/kotlin/codegen/optimization/common/VarFrame;Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;)V", "newFrame", "maxLocals", "", "(I)Lorg/jetbrains/kotlin/codegen/optimization/common/VarFrame;", "use", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface BackwardAnalysisInterpreter<F extends VarFrame<F>> {
    void def(F frame, AbstractInsnNode insn);

    F newFrame(int maxLocals);

    void use(F frame, AbstractInsnNode insn);
}
