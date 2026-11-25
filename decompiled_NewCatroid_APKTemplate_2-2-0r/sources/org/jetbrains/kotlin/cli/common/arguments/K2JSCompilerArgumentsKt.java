package org.jetbrains.kotlin.cli.common.arguments;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: K2JSCompilerArguments.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0003\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0004"}, d2 = {"isIrBackendEnabled", "", "Lorg/jetbrains/kotlin/cli/common/arguments/K2JSCompilerArguments;", "isPreIrBackendDisabled", "cli-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class K2JSCompilerArgumentsKt {
    public static final boolean isPreIrBackendDisabled(K2JSCompilerArguments k2JSCompilerArguments) {
        Intrinsics.checkNotNullParameter(k2JSCompilerArguments, "<this>");
        return k2JSCompilerArguments.getIrOnly() || k2JSCompilerArguments.getIrProduceJs() || k2JSCompilerArguments.getIrProduceKlibFile() || k2JSCompilerArguments.getIrBuildCache() || k2JSCompilerArguments.getUseK2();
    }

    public static final boolean isIrBackendEnabled(K2JSCompilerArguments k2JSCompilerArguments) {
        Intrinsics.checkNotNullParameter(k2JSCompilerArguments, "<this>");
        return k2JSCompilerArguments.getIrProduceKlibDir() || k2JSCompilerArguments.getIrProduceJs() || k2JSCompilerArguments.getIrProduceKlibFile() || k2JSCompilerArguments.getWasm() || k2JSCompilerArguments.getIrBuildCache() || k2JSCompilerArguments.getUseK2();
    }
}
