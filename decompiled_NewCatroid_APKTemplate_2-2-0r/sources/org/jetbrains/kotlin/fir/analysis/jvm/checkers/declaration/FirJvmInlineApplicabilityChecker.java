package org.jetbrains.kotlin.fir.analysis.jvm.checkers.declaration;

import kotlin.Metadata;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;

/* JADX INFO: compiled from: FirJvmInlineApplicabilityChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/jvm/checkers/declaration/FirJvmInlineApplicabilityChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirRegularClassChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJvmInlineApplicabilityChecker extends FirDeclarationChecker<FirRegularClass> {
    public static final FirJvmInlineApplicabilityChecker INSTANCE = new FirJvmInlineApplicabilityChecker();

    private FirJvmInlineApplicabilityChecker() {
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x003b, code lost:
    
        if (org.jetbrains.kotlin.fir.analysis.checkers.FirKeywordUtilsKt.getModifier(r8, r3) == null) goto L8;
     */
    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void check(org.jetbrains.kotlin.fir.declarations.FirRegularClass r8, org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext r9, org.jetbrains.kotlin.diagnostics.DiagnosticReporter r10) {
        /*
            r7 = this;
            java.lang.String r0 = "declaration"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r8, r0)
            java.lang.String r0 = "context"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r9, r0)
            java.lang.String r0 = "reporter"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r10, r0)
            r0 = r8
            org.jetbrains.kotlin.fir.FirAnnotationContainer r0 = (org.jetbrains.kotlin.fir.FirAnnotationContainer) r0
            org.jetbrains.kotlin.name.ClassId r1 = org.jetbrains.kotlin.resolve.InlineClassesUtilsKt.getJVM_INLINE_ANNOTATION_CLASS_ID()
            org.jetbrains.kotlin.fir.FirSession r2 = r9.getSession()
            org.jetbrains.kotlin.fir.expressions.FirAnnotation r0 = org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt.getAnnotationByClassId(r0, r1, r2)
            java.lang.String r1 = "VALUE_KEYWORD"
            if (r0 == 0) goto L56
            r2 = r8
            org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration r2 = (org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration) r2
            org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus r2 = r2.getStatus()
            boolean r2 = r2.isInline()
            if (r2 == 0) goto L3d
            r2 = r8
            org.jetbrains.kotlin.fir.FirElement r2 = (org.jetbrains.kotlin.fir.FirElement) r2
            org.jetbrains.kotlin.lexer.KtModifierKeywordToken r3 = org.jetbrains.kotlin.lexer.KtTokens.VALUE_KEYWORD
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r1)
            org.jetbrains.kotlin.fir.analysis.checkers.FirModifier r2 = org.jetbrains.kotlin.fir.analysis.checkers.FirKeywordUtilsKt.getModifier(r2, r3)
            if (r2 != 0) goto L56
        L3d:
            org.jetbrains.kotlin.KtSourceElement r8 = r0.getSource()
            r1 = r8
            org.jetbrains.kotlin.AbstractKtSourceElement r1 = (org.jetbrains.kotlin.AbstractKtSourceElement) r1
            org.jetbrains.kotlin.fir.analysis.diagnostics.jvm.FirJvmErrors r8 = org.jetbrains.kotlin.fir.analysis.diagnostics.jvm.FirJvmErrors.INSTANCE
            org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory0 r2 = r8.getJVM_INLINE_WITHOUT_VALUE_CLASS()
            r3 = r9
            org.jetbrains.kotlin.diagnostics.DiagnosticContext r3 = (org.jetbrains.kotlin.diagnostics.DiagnosticContext) r3
            r5 = 8
            r6 = 0
            r4 = 0
            r0 = r10
            org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt.reportOn$default(r0, r1, r2, r3, r4, r5, r6)
            goto L98
        L56:
            if (r0 != 0) goto L98
            r0 = r8
            org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration r0 = (org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration) r0
            org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus r2 = r0.getStatus()
            boolean r2 = r2.isInline()
            if (r2 == 0) goto L98
            org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus r0 = r0.getStatus()
            boolean r0 = r0.isExpect()
            if (r0 != 0) goto L98
            org.jetbrains.kotlin.fir.FirElement r8 = (org.jetbrains.kotlin.fir.FirElement) r8
            org.jetbrains.kotlin.lexer.KtModifierKeywordToken r0 = org.jetbrains.kotlin.lexer.KtTokens.VALUE_KEYWORD
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r1)
            org.jetbrains.kotlin.fir.analysis.checkers.FirModifier r8 = org.jetbrains.kotlin.fir.analysis.checkers.FirKeywordUtilsKt.getModifier(r8, r0)
            if (r8 == 0) goto L98
            org.jetbrains.kotlin.KtSourceElement r8 = r8.getSource()
            if (r8 != 0) goto L83
            goto L98
        L83:
            r1 = r8
            org.jetbrains.kotlin.AbstractKtSourceElement r1 = (org.jetbrains.kotlin.AbstractKtSourceElement) r1
            org.jetbrains.kotlin.fir.analysis.diagnostics.jvm.FirJvmErrors r8 = org.jetbrains.kotlin.fir.analysis.diagnostics.jvm.FirJvmErrors.INSTANCE
            org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory0 r2 = r8.getVALUE_CLASS_WITHOUT_JVM_INLINE_ANNOTATION()
            r3 = r9
            org.jetbrains.kotlin.diagnostics.DiagnosticContext r3 = (org.jetbrains.kotlin.diagnostics.DiagnosticContext) r3
            r5 = 8
            r6 = 0
            r4 = 0
            r0 = r10
            org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt.reportOn$default(r0, r1, r2, r3, r4, r5, r6)
        L98:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.fir.analysis.jvm.checkers.declaration.FirJvmInlineApplicabilityChecker.check(org.jetbrains.kotlin.fir.declarations.FirRegularClass, org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext, org.jetbrains.kotlin.diagnostics.DiagnosticReporter):void");
    }
}
