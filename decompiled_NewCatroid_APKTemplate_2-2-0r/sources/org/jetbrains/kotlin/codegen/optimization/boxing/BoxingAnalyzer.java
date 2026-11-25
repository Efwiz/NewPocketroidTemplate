package org.jetbrains.kotlin.codegen.optimization.boxing;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.common.FastMethodAnalyzer;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.analysis.AnalyzerException;
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue;
import org.jetbrains.org.objectweb.asm.tree.analysis.Frame;
import org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter;
import org.jetbrains.org.objectweb.asm.tree.analysis.Value;

/* JADX INFO: compiled from: BoxingAnalyzer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u000fB\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u001e\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rH\u0014R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/boxing/BoxingAnalyzer;", "Lorg/jetbrains/kotlin/codegen/optimization/common/FastMethodAnalyzer;", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicValue;", "owner", "", "method", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "boxingInterpreter", "Lorg/jetbrains/kotlin/codegen/optimization/boxing/BoxingInterpreter;", "(Ljava/lang/String;Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;Lorg/jetbrains/kotlin/codegen/optimization/boxing/BoxingInterpreter;)V", "newFrame", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/Frame;", "nLocals", "", "nStack", "BoxingFrame", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BoxingAnalyzer extends FastMethodAnalyzer<BasicValue> {
    private final BoxingInterpreter boxingInterpreter;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BoxingAnalyzer(String owner, MethodNode method, BoxingInterpreter boxingInterpreter) {
        super(owner, method, boxingInterpreter, false, 8, null);
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(boxingInterpreter, "boxingInterpreter");
        this.boxingInterpreter = boxingInterpreter;
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.common.FastMethodAnalyzer
    protected Frame<BasicValue> newFrame(int nLocals, int nStack) {
        return new BoxingFrame(nLocals, nStack);
    }

    /* JADX INFO: compiled from: BoxingAnalyzer.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0002\u0010\u0006J&\u0010\u0007\u001a\u00020\b2\u000e\u0010\t\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u00012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/boxing/BoxingAnalyzer$BoxingFrame;", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/Frame;", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicValue;", "nLocals", "", "nStack", "(Lorg/jetbrains/kotlin/codegen/optimization/boxing/BoxingAnalyzer;II)V", "merge", "", TypedValues.AttributesType.S_FRAME, "interpreter", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/Interpreter;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class BoxingFrame extends Frame<BasicValue> {
        public BoxingFrame(int i, int i2) {
            super(i, i2);
        }

        @Override // org.jetbrains.org.objectweb.asm.tree.analysis.Frame
        public boolean merge(Frame<? extends BasicValue> frame, Interpreter<BasicValue> interpreter) throws AnalyzerException {
            Intrinsics.checkNotNullParameter(frame, "frame");
            Intrinsics.checkNotNullParameter(interpreter, "interpreter");
            if (getStackSize() != frame.getStackSize()) {
                throw new AnalyzerException(null, "Incompatible stack heights");
            }
            int locals = getLocals();
            boolean z = false;
            for (int i = 0; i < locals; i++) {
                BasicValue local = getLocal(i);
                BoxingInterpreter boxingInterpreter = BoxingAnalyzer.this.boxingInterpreter;
                Intrinsics.checkNotNull(local);
                Value local2 = frame.getLocal(i);
                Intrinsics.checkNotNullExpressionValue(local2, "getLocal(...)");
                BasicValue basicValueMergeLocalVariableValues = boxingInterpreter.mergeLocalVariableValues(local, (BasicValue) local2);
                if (!Intrinsics.areEqual(local, basicValueMergeLocalVariableValues)) {
                    setLocal(i, basicValueMergeLocalVariableValues);
                    z = true;
                }
            }
            int stackSize = getStackSize();
            for (int i2 = 0; i2 < stackSize; i2++) {
                BasicValue stack = getStack(i2);
                BoxingInterpreter boxingInterpreter2 = BoxingAnalyzer.this.boxingInterpreter;
                Intrinsics.checkNotNull(stack);
                Value stack2 = frame.getStack(i2);
                Intrinsics.checkNotNullExpressionValue(stack2, "getStack(...)");
                BasicValue basicValueMergeStackValues = boxingInterpreter2.mergeStackValues(stack, (BasicValue) stack2);
                if (!Intrinsics.areEqual(stack, basicValueMergeStackValues)) {
                    setStack(i2, basicValueMergeStackValues);
                    z = true;
                }
            }
            return z;
        }
    }
}
