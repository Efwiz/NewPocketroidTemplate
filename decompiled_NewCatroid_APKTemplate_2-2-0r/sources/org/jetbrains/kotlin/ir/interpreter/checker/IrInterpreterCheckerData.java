package org.jetbrains.kotlin.ir.interpreter.checker;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.interpreter.IrInterpreterConfiguration;

/* JADX INFO: compiled from: IrInterpreterChecker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterCheckerData;", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "mode", "Lorg/jetbrains/kotlin/ir/interpreter/checker/EvaluationMode;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "interpreterConfiguration", "Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterConfiguration;", "(Lorg/jetbrains/kotlin/ir/declarations/IrFile;Lorg/jetbrains/kotlin/ir/interpreter/checker/EvaluationMode;Lorg/jetbrains/kotlin/ir/IrBuiltIns;Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterConfiguration;)V", "getInterpreterConfiguration", "()Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterConfiguration;", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "getIrFile", "()Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "getMode", "()Lorg/jetbrains/kotlin/ir/interpreter/checker/EvaluationMode;", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrInterpreterCheckerData {
    private final IrInterpreterConfiguration interpreterConfiguration;
    private final IrBuiltIns irBuiltIns;
    private final IrFile irFile;
    private final EvaluationMode mode;

    public IrInterpreterCheckerData(IrFile irFile, EvaluationMode mode, IrBuiltIns irBuiltIns, IrInterpreterConfiguration interpreterConfiguration) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        Intrinsics.checkNotNullParameter(mode, "mode");
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        Intrinsics.checkNotNullParameter(interpreterConfiguration, "interpreterConfiguration");
        this.irFile = irFile;
        this.mode = mode;
        this.irBuiltIns = irBuiltIns;
        this.interpreterConfiguration = interpreterConfiguration;
    }

    public final IrFile getIrFile() {
        return this.irFile;
    }

    public final EvaluationMode getMode() {
        return this.mode;
    }

    public final IrBuiltIns getIrBuiltIns() {
        return this.irBuiltIns;
    }

    public final IrInterpreterConfiguration getInterpreterConfiguration() {
        return this.interpreterConfiguration;
    }
}
