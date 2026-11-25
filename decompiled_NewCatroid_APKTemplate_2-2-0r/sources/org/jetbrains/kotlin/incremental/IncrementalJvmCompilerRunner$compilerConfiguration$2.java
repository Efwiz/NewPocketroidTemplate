package org.jetbrains.kotlin.incremental;

import java.util.function.Predicate;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.FilteringMessageCollector;
import org.jetbrains.kotlin.cli.jvm.config.JvmContentRootsKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: IncrementalJvmCompilerRunner.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {SpecialNames.ANONYMOUS_STRING, "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
final class IncrementalJvmCompilerRunner$compilerConfiguration$2 extends Lambda implements Function0<CompilerConfiguration> {
    final /* synthetic */ IncrementalJvmCompilerRunner this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    IncrementalJvmCompilerRunner$compilerConfiguration$2(IncrementalJvmCompilerRunner incrementalJvmCompilerRunner) {
        super(0);
        this.this$0 = incrementalJvmCompilerRunner;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean invoke$lambda$0(CompilerMessageSeverity compilerMessageSeverity) {
        return !compilerMessageSeverity.isError();
    }

    @Override // kotlin.jvm.functions.Function0
    public final CompilerConfiguration invoke() {
        FilteringMessageCollector filteringMessageCollector = new FilteringMessageCollector(this.this$0.messageCollector, new Predicate() { // from class: org.jetbrains.kotlin.incremental.IncrementalJvmCompilerRunner$compilerConfiguration$2$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return IncrementalJvmCompilerRunner$compilerConfiguration$2.invoke$lambda$0((CompilerMessageSeverity) obj);
            }
        });
        CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
        compilerConfiguration.put(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, filteringMessageCollector);
        JvmContentRootsKt.configureJdkClasspathRoots(compilerConfiguration);
        return compilerConfiguration;
    }
}
