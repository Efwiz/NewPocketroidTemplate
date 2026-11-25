package org.jetbrains.kotlin.cli.js.klib;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.messages.AnalyzerWithCompilerReport;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.fir.pipeline.ModuleCompilerAnalyzedOutput;
import org.jetbrains.kotlin.psi.KtFile;

/* JADX INFO: compiled from: compilerPipeline.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B!\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003¢\u0006\u0002\u0010\u0007J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0014R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/cli/js/klib/AnalyzedFirWithPsiOutput;", "Lorg/jetbrains/kotlin/cli/js/klib/AnalyzedFirOutput;", "output", "", "Lorg/jetbrains/kotlin/fir/pipeline/ModuleCompilerAnalyzedOutput;", "compiledFiles", "Lorg/jetbrains/kotlin/psi/KtFile;", "(Ljava/util/List;Ljava/util/List;)V", "checkSyntaxErrors", "", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "cli-js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AnalyzedFirWithPsiOutput extends AnalyzedFirOutput {
    private final List<KtFile> compiledFiles;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public AnalyzedFirWithPsiOutput(List<ModuleCompilerAnalyzedOutput> output, List<? extends KtFile> compiledFiles) {
        super(output);
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(compiledFiles, "compiledFiles");
        this.compiledFiles = compiledFiles;
    }

    @Override // org.jetbrains.kotlin.cli.js.klib.AnalyzedFirOutput
    protected boolean checkSyntaxErrors(MessageCollector messageCollector) {
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        Iterator<T> it = this.compiledFiles.iterator();
        boolean isHasErrors = false;
        while (it.hasNext()) {
            isHasErrors |= AnalyzerWithCompilerReport.INSTANCE.reportSyntaxErrors((KtFile) it.next(), messageCollector).getIsHasErrors();
        }
        return isHasErrors;
    }
}
