package org.jetbrains.kotlin.ir.linkage.partial;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.CompilerConfiguration;

/* JADX INFO: compiled from: PartialLinkageConfig.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001aN\u0010\u0005\u001a\u00020\u0006*\u00020\u00022\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\t\u001a\u0004\u0018\u00010\b2\u0006\u0010\n\u001a\u00020\u000b2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00060\r2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00060\r\u001a\u0012\u0010\u0005\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u0001\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0010"}, d2 = {"partialLinkageConfig", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageConfig;", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "getPartialLinkageConfig", "(Lorg/jetbrains/kotlin/config/CompilerConfiguration;)Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageConfig;", "setupPartialLinkageConfig", "", "mode", "", "logLevel", "compilerModeAllowsUsingPartialLinkage", "", "onWarning", "Lkotlin/Function1;", "onError", "config", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PartialLinkageConfigKt {
    public static final PartialLinkageConfig getPartialLinkageConfig(CompilerConfiguration compilerConfiguration) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        PartialLinkageConfig partialLinkageConfig = (PartialLinkageConfig) compilerConfiguration.get(PartialLinkageConfig.INSTANCE.getKEY());
        return partialLinkageConfig == null ? PartialLinkageConfig.INSTANCE.getDEFAULT() : partialLinkageConfig;
    }

    public static final void setupPartialLinkageConfig(CompilerConfiguration compilerConfiguration, String str, String str2, boolean z, Function1<? super String, Unit> onWarning, Function1<? super String, Unit> onError) {
        PartialLinkageMode partialLinkageModeResolveMode;
        PartialLinkageLogLevel partialLinkageLogLevelResolveLogLevel;
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(onWarning, "onWarning");
        Intrinsics.checkNotNullParameter(onError, "onError");
        if (str != null) {
            partialLinkageModeResolveMode = PartialLinkageMode.INSTANCE.resolveMode(str);
            if (partialLinkageModeResolveMode == null) {
                onError.invoke("Unknown partial linkage mode '" + str + '\'');
                return;
            } else if (!z && partialLinkageModeResolveMode.getIsEnabled()) {
                onWarning.invoke("Current compiler configuration does not allow using partial linkage mode '" + str + "'. The partial linkage will be disabled.");
                partialLinkageModeResolveMode = PartialLinkageMode.DISABLE;
            }
        } else {
            partialLinkageModeResolveMode = !z ? PartialLinkageMode.DISABLE : PartialLinkageMode.INSTANCE.getDEFAULT();
        }
        if (str2 != null) {
            partialLinkageLogLevelResolveLogLevel = PartialLinkageLogLevel.INSTANCE.resolveLogLevel(str2);
            if (partialLinkageLogLevelResolveLogLevel == null) {
                onError.invoke("Unknown partial linkage compile-time log level '" + str2 + '\'');
                return;
            }
        } else {
            partialLinkageLogLevelResolveLogLevel = PartialLinkageLogLevel.INSTANCE.getDEFAULT();
        }
        setupPartialLinkageConfig(compilerConfiguration, new PartialLinkageConfig(partialLinkageModeResolveMode, partialLinkageLogLevelResolveLogLevel));
    }

    public static final void setupPartialLinkageConfig(CompilerConfiguration compilerConfiguration, PartialLinkageConfig config) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(config, "config");
        compilerConfiguration.put(PartialLinkageConfig.INSTANCE.getKEY(), config);
    }
}
