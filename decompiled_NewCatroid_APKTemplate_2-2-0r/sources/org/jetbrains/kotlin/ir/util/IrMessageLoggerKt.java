package org.jetbrains.kotlin.ir.util;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.CompilerConfiguration;

/* JADX INFO: compiled from: IrMessageLogger.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0005"}, d2 = {"irMessageLogger", "Lorg/jetbrains/kotlin/ir/util/IrMessageLogger;", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "getIrMessageLogger", "(Lorg/jetbrains/kotlin/config/CompilerConfiguration;)Lorg/jetbrains/kotlin/ir/util/IrMessageLogger;", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrMessageLoggerKt {
    public static final IrMessageLogger getIrMessageLogger(CompilerConfiguration compilerConfiguration) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        IrMessageLogger irMessageLogger = (IrMessageLogger) compilerConfiguration.get(IrMessageLogger.INSTANCE.getIR_MESSAGE_LOGGER());
        return irMessageLogger == null ? IrMessageLogger.None.INSTANCE : irMessageLogger;
    }
}
