package org.jetbrains.kotlin.cli.js.klib;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.cli.common.fir.FirDiagnosticsCompilerResultsReporter;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.diagnostics.impl.BaseDiagnosticsCollector;
import org.jetbrains.kotlin.fir.pipeline.ModuleCompilerAnalyzedOutput;
import org.jetbrains.kotlin.ir.backend.js.ModulesStructure;

/* JADX INFO: compiled from: compilerPipeline.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0014J\u001e\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u000bR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/cli/js/klib/AnalyzedFirOutput;", "", "output", "", "Lorg/jetbrains/kotlin/fir/pipeline/ModuleCompilerAnalyzedOutput;", "(Ljava/util/List;)V", "getOutput", "()Ljava/util/List;", "checkSyntaxErrors", "", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "reportCompilationErrors", "moduleStructure", "Lorg/jetbrains/kotlin/ir/backend/js/ModulesStructure;", "diagnosticsReporter", "Lorg/jetbrains/kotlin/diagnostics/impl/BaseDiagnosticsCollector;", "cli-js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class AnalyzedFirOutput {
    private final List<ModuleCompilerAnalyzedOutput> output;

    protected boolean checkSyntaxErrors(MessageCollector messageCollector) {
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        return false;
    }

    public AnalyzedFirOutput(List<ModuleCompilerAnalyzedOutput> output) {
        Intrinsics.checkNotNullParameter(output, "output");
        this.output = output;
    }

    public final List<ModuleCompilerAnalyzedOutput> getOutput() {
        return this.output;
    }

    public final boolean reportCompilationErrors(ModulesStructure moduleStructure, BaseDiagnosticsCollector diagnosticsReporter, MessageCollector messageCollector) {
        Intrinsics.checkNotNullParameter(moduleStructure, "moduleStructure");
        Intrinsics.checkNotNullParameter(diagnosticsReporter, "diagnosticsReporter");
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        if (!checkSyntaxErrors(messageCollector) && !diagnosticsReporter.getHasErrors()) {
            return false;
        }
        FirDiagnosticsCompilerResultsReporter.INSTANCE.reportToMessageCollector(diagnosticsReporter, messageCollector, moduleStructure.getCompilerConfiguration().getBoolean(CLIConfigurationKeys.RENDER_DIAGNOSTIC_INTERNAL_NAME));
        return true;
    }
}
