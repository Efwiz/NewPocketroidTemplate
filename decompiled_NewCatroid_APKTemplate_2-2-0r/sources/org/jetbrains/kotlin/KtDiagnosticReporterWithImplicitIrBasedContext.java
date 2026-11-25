package org.jetbrains.kotlin;

import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.UtilsKt;
import org.jetbrains.kotlin.backend.common.psi.PsiSourceManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnostic;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReporterWithContext;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: KtDiagnosticReporterWithImplicitIrBasedContext.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u0001\u0018B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001e\u0010\t\u001a\u00060\nR\u00020\u00012\b\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J$\u0010\t\u001a\u00060\nR\u00020\u00012\b\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012J\u001a\u0010\t\u001a\u00060\nR\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u0014J\u001a\u0010\t\u001a\u00060\nR\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u0012J\u0012\u0010\t\u001a\u00060\nR\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u0014J\u0016\u0010\u0017\u001a\u0004\u0018\u00010\f*\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u0012H\u0002R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/KtDiagnosticReporterWithImplicitIrBasedContext;", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticReporterWithContext;", "diagnosticReporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "suppressCache", "Lorg/jetbrains/kotlin/IrBasedSuppressCache;", "at", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticReporterWithContext$DiagnosticContextImpl;", "sourceElement", "Lorg/jetbrains/kotlin/AbstractKtSourceElement;", "containingFilePath", "", "irElement", "Lorg/jetbrains/kotlin/ir/IrElement;", "containingFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "containingIrDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "containingIrFile", "irDeclaration", "toSourceElement", "DiagnosticContextWithSuppressionImpl", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtDiagnosticReporterWithImplicitIrBasedContext extends KtDiagnosticReporterWithContext {
    private final IrBasedSuppressCache suppressCache;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtDiagnosticReporterWithImplicitIrBasedContext(DiagnosticReporter diagnosticReporter, LanguageVersionSettings languageVersionSettings) {
        super(diagnosticReporter, languageVersionSettings);
        Intrinsics.checkNotNullParameter(diagnosticReporter, "diagnosticReporter");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        this.suppressCache = new IrBasedSuppressCache();
    }

    private final AbstractKtSourceElement toSourceElement(IrElement irElement, IrFile irFile) {
        PsiElement psiElementFindPsiElement = PsiSourceManager.INSTANCE.findPsiElement(irElement, irFile);
        return psiElementFindPsiElement != null ? new KtRealPsiSourceElement(psiElementFindPsiElement) : UtilsKt.sourceElement(irElement);
    }

    public final KtDiagnosticReporterWithContext.DiagnosticContextImpl at(IrElement irElement, IrDeclaration containingIrDeclaration) {
        Intrinsics.checkNotNullParameter(irElement, "irElement");
        Intrinsics.checkNotNullParameter(containingIrDeclaration, "containingIrDeclaration");
        return at(irElement, IrUtilsKt.getFile(containingIrDeclaration));
    }

    public final KtDiagnosticReporterWithContext.DiagnosticContextImpl at(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "irDeclaration");
        return at(irDeclaration, IrUtilsKt.getFile(irDeclaration));
    }

    public final KtDiagnosticReporterWithContext.DiagnosticContextImpl at(IrElement irElement, IrFile containingIrFile) {
        Intrinsics.checkNotNullParameter(irElement, "irElement");
        Intrinsics.checkNotNullParameter(containingIrFile, "containingIrFile");
        return at(toSourceElement(irElement, containingIrFile), irElement, containingIrFile);
    }

    public final KtDiagnosticReporterWithContext.DiagnosticContextImpl at(AbstractKtSourceElement sourceElement, IrElement irElement, IrFile containingFile) {
        Intrinsics.checkNotNullParameter(irElement, "irElement");
        Intrinsics.checkNotNullParameter(containingFile, "containingFile");
        return new DiagnosticContextWithSuppressionImpl(this, sourceElement, irElement, containingFile);
    }

    @Override // org.jetbrains.kotlin.diagnostics.KtDiagnosticReporterWithContext
    public KtDiagnosticReporterWithContext.DiagnosticContextImpl at(AbstractKtSourceElement sourceElement, String containingFilePath) {
        Intrinsics.checkNotNullParameter(containingFilePath, "containingFilePath");
        throw new IllegalStateException("Should not be called directly".toString());
    }

    /* JADX INFO: compiled from: KtDiagnosticReporterWithImplicitIrBasedContext.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0080\u0004\u0018\u00002\u00060\u0001R\u00020\u0002B\u001f\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/KtDiagnosticReporterWithImplicitIrBasedContext$DiagnosticContextWithSuppressionImpl;", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticReporterWithContext$DiagnosticContextImpl;", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticReporterWithContext;", "sourceElement", "Lorg/jetbrains/kotlin/AbstractKtSourceElement;", "irElement", "Lorg/jetbrains/kotlin/ir/IrElement;", "containingFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "(Lorg/jetbrains/kotlin/KtDiagnosticReporterWithImplicitIrBasedContext;Lorg/jetbrains/kotlin/AbstractKtSourceElement;Lorg/jetbrains/kotlin/ir/IrElement;Lorg/jetbrains/kotlin/ir/declarations/IrFile;)V", "isDiagnosticSuppressed", "", "diagnostic", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnostic;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class DiagnosticContextWithSuppressionImpl extends KtDiagnosticReporterWithContext.DiagnosticContextImpl {
        private final IrFile containingFile;
        private final IrElement irElement;
        final /* synthetic */ KtDiagnosticReporterWithImplicitIrBasedContext this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public DiagnosticContextWithSuppressionImpl(KtDiagnosticReporterWithImplicitIrBasedContext ktDiagnosticReporterWithImplicitIrBasedContext, AbstractKtSourceElement abstractKtSourceElement, IrElement irElement, IrFile containingFile) {
            super(ktDiagnosticReporterWithImplicitIrBasedContext, abstractKtSourceElement, IrDeclarationsKt.getPath(containingFile));
            Intrinsics.checkNotNullParameter(irElement, "irElement");
            Intrinsics.checkNotNullParameter(containingFile, "containingFile");
            this.this$0 = ktDiagnosticReporterWithImplicitIrBasedContext;
            this.irElement = irElement;
            this.containingFile = containingFile;
        }

        @Override // org.jetbrains.kotlin.diagnostics.KtDiagnosticReporterWithContext.DiagnosticContextImpl, org.jetbrains.kotlin.diagnostics.DiagnosticContext
        public boolean isDiagnosticSuppressed(KtDiagnostic diagnostic) {
            Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
            IrBasedSuppressCache irBasedSuppressCache = this.this$0.suppressCache;
            IrElement irElement = this.irElement;
            IrFile irFile = this.containingFile;
            String lowerCase = diagnostic.getFactory().getName().toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
            return irBasedSuppressCache.isSuppressed(irElement, irFile, lowerCase, diagnostic.getSeverity());
        }
    }
}
