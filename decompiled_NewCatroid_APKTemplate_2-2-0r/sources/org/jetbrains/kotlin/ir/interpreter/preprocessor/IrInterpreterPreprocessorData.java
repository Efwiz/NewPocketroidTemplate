package org.jetbrains.kotlin.ir.interpreter.preprocessor;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.interpreter.checker.EvaluationMode;

/* JADX INFO: compiled from: IrInterpreterPreprocessor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/preprocessor/IrInterpreterPreprocessorData;", "", "mode", "Lorg/jetbrains/kotlin/ir/interpreter/checker/EvaluationMode;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "(Lorg/jetbrains/kotlin/ir/interpreter/checker/EvaluationMode;Lorg/jetbrains/kotlin/ir/IrBuiltIns;)V", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "getMode", "()Lorg/jetbrains/kotlin/ir/interpreter/checker/EvaluationMode;", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrInterpreterPreprocessorData {
    private final IrBuiltIns irBuiltIns;
    private final EvaluationMode mode;

    public IrInterpreterPreprocessorData(EvaluationMode mode, IrBuiltIns irBuiltIns) {
        Intrinsics.checkNotNullParameter(mode, "mode");
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        this.mode = mode;
        this.irBuiltIns = irBuiltIns;
    }

    public final EvaluationMode getMode() {
        return this.mode;
    }

    public final IrBuiltIns getIrBuiltIns() {
        return this.irBuiltIns;
    }
}
