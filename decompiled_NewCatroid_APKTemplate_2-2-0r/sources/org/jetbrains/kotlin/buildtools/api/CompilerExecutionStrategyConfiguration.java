package org.jetbrains.kotlin.buildtools.api;

import java.util.List;
import kotlin.Metadata;

/* JADX INFO: compiled from: CompilerExecutionStrategyConfiguration.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00002\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H&J\b\u0010\u0006\u001a\u00020\u0000H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0007À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/buildtools/api/CompilerExecutionStrategyConfiguration;", "", "useDaemonStrategy", "jvmArguments", "", "", "useInProcessStrategy", "kotlin-build-tools-api"}, k = 1, mv = {1, 5, 1}, xi = 48)
@ExperimentalBuildToolsApi
public interface CompilerExecutionStrategyConfiguration {
    CompilerExecutionStrategyConfiguration useDaemonStrategy(List<String> jvmArguments);

    CompilerExecutionStrategyConfiguration useInProcessStrategy();
}
