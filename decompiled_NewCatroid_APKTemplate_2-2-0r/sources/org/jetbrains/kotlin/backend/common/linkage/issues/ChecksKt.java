package org.jetbrains.kotlin.backend.common.linkage.issues;

import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.KotlinIrLinker;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.IrMessageLogger;
import org.jetbrains.kotlin.ir.util.IrMessageLoggerKt;
import org.jetbrains.kotlin.ir.util.SymbolTable;

/* JADX INFO: compiled from: checks.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u001a\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u001a\u001a\u0010\u0000\u001a\u00020\u0001*\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u001a\u001a\u0010\u0000\u001a\u00020\u0001*\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\t"}, d2 = {"checkNoUnboundSymbols", "", "Lorg/jetbrains/kotlin/backend/common/serialization/KotlinIrLinker;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "whenDetected", "", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "Lorg/jetbrains/kotlin/ir/util/IrMessageLogger;", "ir.serialization.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ChecksKt {
    public static final void checkNoUnboundSymbols(KotlinIrLinker kotlinIrLinker, SymbolTable symbolTable, String whenDetected) {
        Intrinsics.checkNotNullParameter(kotlinIrLinker, "<this>");
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Intrinsics.checkNotNullParameter(whenDetected, "whenDetected");
        checkNoUnboundSymbols(kotlinIrLinker.getMessageLogger(), symbolTable, whenDetected);
    }

    public static final void checkNoUnboundSymbols(IrMessageLogger irMessageLogger, SymbolTable symbolTable, String whenDetected) {
        Intrinsics.checkNotNullParameter(irMessageLogger, "<this>");
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Intrinsics.checkNotNullParameter(whenDetected, "whenDetected");
        Set<IrSymbol> allUnboundSymbols = symbolTable.getDescriptorExtension().getAllUnboundSymbols();
        if (allUnboundSymbols.isEmpty()) {
            return;
        }
        new UnexpectedUnboundIrSymbols(allUnboundSymbols, whenDetected).raiseIssue(irMessageLogger);
        throw null;
    }

    public static final void checkNoUnboundSymbols(CompilerConfiguration compilerConfiguration, SymbolTable symbolTable, String whenDetected) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Intrinsics.checkNotNullParameter(whenDetected, "whenDetected");
        Set<IrSymbol> allUnboundSymbols = symbolTable.getDescriptorExtension().getAllUnboundSymbols();
        if (allUnboundSymbols.isEmpty()) {
            return;
        }
        new UnexpectedUnboundIrSymbols(allUnboundSymbols, whenDetected).raiseIssue(IrMessageLoggerKt.getIrMessageLogger(compilerConfiguration));
        throw null;
    }
}
