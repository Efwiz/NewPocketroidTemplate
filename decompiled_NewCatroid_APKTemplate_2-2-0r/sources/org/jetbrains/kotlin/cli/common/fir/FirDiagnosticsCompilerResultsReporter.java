package org.jetbrains.kotlin.cli.common.fir;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.messages.AnalyzerWithCompilerReport;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSourceLocation;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.common.messages.MessageRenderer;
import org.jetbrains.kotlin.cli.common.messages.MessageUtil;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.diagnostics.DiagnosticUtils;
import org.jetbrains.kotlin.diagnostics.KtDiagnostic;
import org.jetbrains.kotlin.diagnostics.KtPsiDiagnostic;
import org.jetbrains.kotlin.diagnostics.Severity;
import org.jetbrains.kotlin.diagnostics.impl.BaseDiagnosticsCollector;
import org.jetbrains.kotlin.diagnostics.rendering.RootDiagnosticRendererFactory;

/* JADX INFO: compiled from: FirDiagnosticsCompilerResultsReporter.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u001bB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J*\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0018\u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\bH\u0002J(\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0004H\u0002J\u0016\u0010\u0012\u001a\u00020\u000b2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\t0\u0014H\u0002J\u001e\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0004J \u0010\u0017\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\n2\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0018\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0018\u001a\u00020\u0019¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/fir/FirDiagnosticsCompilerResultsReporter;", "", "()V", "reportByFile", "", "diagnosticsCollector", "Lorg/jetbrains/kotlin/diagnostics/impl/BaseDiagnosticsCollector;", "report", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnostic;", "Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageSourceLocation;", "", "reportDiagnosticToMessageCollector", "diagnostic", FirebaseAnalytics.Param.LOCATION, "reporter", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "renderDiagnosticName", "reportSpecialErrors", "diagnostics", "", "reportToMessageCollector", "messageCollector", "throwErrorDiagnosticAsException", "messageRenderer", "Lorg/jetbrains/kotlin/cli/common/messages/MessageRenderer;", "throwFirstErrorAsException", "InFileDiagnosticsComparator", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDiagnosticsCompilerResultsReporter {
    public static final FirDiagnosticsCompilerResultsReporter INSTANCE = new FirDiagnosticsCompilerResultsReporter();

    private FirDiagnosticsCompilerResultsReporter() {
    }

    public final boolean reportToMessageCollector(BaseDiagnosticsCollector diagnosticsCollector, final MessageCollector messageCollector, final boolean renderDiagnosticName) {
        Intrinsics.checkNotNullParameter(diagnosticsCollector, "diagnosticsCollector");
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        return reportByFile(diagnosticsCollector, new Function2<KtDiagnostic, CompilerMessageSourceLocation, Unit>() { // from class: org.jetbrains.kotlin.cli.common.fir.FirDiagnosticsCompilerResultsReporter.reportToMessageCollector.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(KtDiagnostic ktDiagnostic, CompilerMessageSourceLocation compilerMessageSourceLocation) {
                invoke2(ktDiagnostic, compilerMessageSourceLocation);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(KtDiagnostic diagnostic, CompilerMessageSourceLocation location) {
                Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
                Intrinsics.checkNotNullParameter(location, "location");
                FirDiagnosticsCompilerResultsReporter.INSTANCE.reportDiagnosticToMessageCollector(diagnostic, location, messageCollector, renderDiagnosticName);
            }
        });
    }

    public static /* synthetic */ boolean throwFirstErrorAsException$default(FirDiagnosticsCompilerResultsReporter firDiagnosticsCompilerResultsReporter, BaseDiagnosticsCollector baseDiagnosticsCollector, MessageRenderer PLAIN_RELATIVE_PATHS, int i, Object obj) {
        if ((i & 2) != 0) {
            PLAIN_RELATIVE_PATHS = MessageRenderer.PLAIN_RELATIVE_PATHS;
            Intrinsics.checkNotNullExpressionValue(PLAIN_RELATIVE_PATHS, "PLAIN_RELATIVE_PATHS");
        }
        return firDiagnosticsCompilerResultsReporter.throwFirstErrorAsException(baseDiagnosticsCollector, PLAIN_RELATIVE_PATHS);
    }

    public final boolean throwFirstErrorAsException(BaseDiagnosticsCollector diagnosticsCollector, final MessageRenderer messageRenderer) {
        Intrinsics.checkNotNullParameter(diagnosticsCollector, "diagnosticsCollector");
        Intrinsics.checkNotNullParameter(messageRenderer, "messageRenderer");
        return reportByFile(diagnosticsCollector, new Function2<KtDiagnostic, CompilerMessageSourceLocation, Unit>() { // from class: org.jetbrains.kotlin.cli.common.fir.FirDiagnosticsCompilerResultsReporter.throwFirstErrorAsException.1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(KtDiagnostic ktDiagnostic, CompilerMessageSourceLocation compilerMessageSourceLocation) {
                invoke2(ktDiagnostic, compilerMessageSourceLocation);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(KtDiagnostic diagnostic, CompilerMessageSourceLocation location) {
                Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
                Intrinsics.checkNotNullParameter(location, "location");
                FirDiagnosticsCompilerResultsReporter.INSTANCE.throwErrorDiagnosticAsException(diagnostic, location, messageRenderer);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final boolean reportByFile(BaseDiagnosticsCollector diagnosticsCollector, Function2<? super KtDiagnostic, ? super CompilerMessageSourceLocation, Unit> report) throws IOException {
        SequentialFilePositionFinder sequentialFilePositionFinder;
        SequentialFilePositionFinder sequentialFilePositionFinder2;
        CompilerMessageSourceLocation compilerMessageSourceLocationPsiFileToMessageLocation;
        KtSourceFileDiagnosticPos ktSourceFileDiagnosticPosFindNextPosition$default;
        boolean z = false;
        for (final String str : diagnosticsCollector.getDiagnosticsByFilePath().keySet()) {
            Lazy lazy = LazyKt.lazy(new Function0<SequentialFilePositionFinder>() { // from class: org.jetbrains.kotlin.cli.common.fir.FirDiagnosticsCompilerResultsReporter$reportByFile$positionFinder$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final SequentialFilePositionFinder invoke() {
                    String str2 = str;
                    File file = str2 != null ? new File(str2) : null;
                    if (file == null || !file.isFile()) {
                        return null;
                    }
                    return new SequentialFilePositionFinder(file);
                }
            });
            try {
                List<KtDiagnostic> listEmptyList = diagnosticsCollector.getDiagnosticsByFilePath().get(str);
                if (listEmptyList == null) {
                    listEmptyList = CollectionsKt.emptyList();
                }
                for (KtDiagnostic ktDiagnostic : CollectionsKt.sortedWith(listEmptyList, InFileDiagnosticsComparator.INSTANCE)) {
                    if (ktDiagnostic instanceof KtPsiDiagnostic) {
                        PsiFile containingFile = ((KtPsiDiagnostic) ktDiagnostic).getElement().getPsi().getContainingFile();
                        compilerMessageSourceLocationPsiFileToMessageLocation = MessageUtil.psiFileToMessageLocation(containingFile, containingFile.getName(), DiagnosticUtils.getLineAndColumnRange(containingFile, ktDiagnostic.getTextRanges()));
                    } else {
                        SequentialFilePositionFinder sequentialFilePositionFinder3 = (SequentialFilePositionFinder) lazy.getValue();
                        CompilerMessageSourceLocation compilerMessageSourceLocationCreateMessageLocation = null;
                        if (sequentialFilePositionFinder3 != null && (ktSourceFileDiagnosticPosFindNextPosition$default = SequentialPositionFinder.findNextPosition$default(sequentialFilePositionFinder3, DiagnosticUtils.firstRange(ktDiagnostic.getTextRanges()).getStartOffset(), false, 2, null)) != null) {
                            compilerMessageSourceLocationCreateMessageLocation = MessageUtil.createMessageLocation(str, ktSourceFileDiagnosticPosFindNextPosition$default.getLineContent(), ktSourceFileDiagnosticPosFindNextPosition$default.getLine(), ktSourceFileDiagnosticPosFindNextPosition$default.getColumn(), -1, -1);
                        }
                        compilerMessageSourceLocationPsiFileToMessageLocation = compilerMessageSourceLocationCreateMessageLocation;
                    }
                    if (compilerMessageSourceLocationPsiFileToMessageLocation != null) {
                        report.invoke(ktDiagnostic, compilerMessageSourceLocationPsiFileToMessageLocation);
                        z = z || ktDiagnostic.getSeverity() == Severity.ERROR;
                    }
                }
                if (lazy.isInitialized() && (sequentialFilePositionFinder2 = (SequentialFilePositionFinder) lazy.getValue()) != null) {
                    sequentialFilePositionFinder2.close();
                }
            } catch (Throwable th) {
                if (lazy.isInitialized() && (sequentialFilePositionFinder = (SequentialFilePositionFinder) lazy.getValue()) != null) {
                    sequentialFilePositionFinder.close();
                }
                throw th;
            }
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void reportDiagnosticToMessageCollector(KtDiagnostic diagnostic, CompilerMessageSourceLocation location, MessageCollector reporter, boolean renderDiagnosticName) {
        CompilerMessageSeverity compilerMessageSeverityConvertSeverity = AnalyzerWithCompilerReport.INSTANCE.convertSeverity(diagnostic.getSeverity());
        String strRender = RootDiagnosticRendererFactory.INSTANCE.invoke(diagnostic).render(diagnostic);
        if (renderDiagnosticName) {
            strRender = "[" + diagnostic.getFactoryName() + "] " + strRender;
        } else if (renderDiagnosticName) {
            throw new NoWhenBranchMatchedException();
        }
        reporter.report(compilerMessageSeverityConvertSeverity, strRender, location);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void throwErrorDiagnosticAsException(KtDiagnostic diagnostic, CompilerMessageSourceLocation location, MessageRenderer messageRenderer) {
        if (diagnostic.getSeverity() != Severity.ERROR) {
            return;
        }
        throw new IllegalStateException(diagnostic.getFactory().getName() + ": " + messageRenderer.render(AnalyzerWithCompilerReport.INSTANCE.convertSeverity(diagnostic.getSeverity()), RootDiagnosticRendererFactory.INSTANCE.invoke(diagnostic).render(diagnostic), location));
    }

    /* JADX INFO: compiled from: FirDiagnosticsCompilerResultsReporter.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\bÂ\u0002\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u00020\u0001j\b\u0012\u0004\u0012\u00020\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u0002H\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/fir/FirDiagnosticsCompilerResultsReporter$InFileDiagnosticsComparator;", "Ljava/util/Comparator;", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnostic;", "Lkotlin/Comparator;", "()V", "compare", "", "o1", "o2", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class InFileDiagnosticsComparator implements Comparator<KtDiagnostic> {
        public static final InFileDiagnosticsComparator INSTANCE = new InFileDiagnosticsComparator();

        private InFileDiagnosticsComparator() {
        }

        @Override // java.util.Comparator
        public int compare(KtDiagnostic o1, KtDiagnostic o2) {
            Intrinsics.checkNotNullParameter(o1, "o1");
            Intrinsics.checkNotNullParameter(o2, "o2");
            TextRange textRangeFirstRange = DiagnosticUtils.firstRange(o1.getTextRanges());
            Intrinsics.checkNotNullExpressionValue(textRangeFirstRange, "firstRange(...)");
            TextRange textRangeFirstRange2 = DiagnosticUtils.firstRange(o2.getTextRanges());
            Intrinsics.checkNotNullExpressionValue(textRangeFirstRange2, "firstRange(...)");
            if (!Intrinsics.areEqual(textRangeFirstRange, textRangeFirstRange2)) {
                return DiagnosticUtils.TEXT_RANGE_COMPARATOR.compare(textRangeFirstRange, textRangeFirstRange2);
            }
            return o1.getFactory().getName().compareTo(o2.getFactory().getName());
        }
    }
}
