package org.jetbrains.kotlin.fir.analysis.jvm.checkers.declaration;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirKeywordUtilsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirModifier;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.analysis.diagnostics.jvm.FirJvmErrors;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.lexer.KtTokens;

/* JADX INFO: compiled from: FirJvmExternalDeclarationChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J4\u0010\f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/jvm/checkers/declaration/FirJvmExternalDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBasicDeclarationChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkInternal", "reportSource", "Lorg/jetbrains/kotlin/KtSourceElement;", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "checkers.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJvmExternalDeclarationChecker extends FirDeclarationChecker<FirDeclaration> {
    public static final FirJvmExternalDeclarationChecker INSTANCE = new FirJvmExternalDeclarationChecker();

    private FirJvmExternalDeclarationChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (declaration instanceof FirPropertyAccessor) {
            return;
        }
        checkInternal(declaration, null, null, context, reporter);
    }

    private final void checkInternal(FirDeclaration declaration, KtSourceElement reportSource, Modality modality, CheckerContext context, DiagnosticReporter reporter) {
        KtSourceElement source;
        String str;
        FirPropertyAccessor getter;
        if (declaration instanceof FirMemberDeclaration) {
            if ((declaration instanceof FirProperty) && (getter = ((FirProperty) declaration).getGetter()) != null) {
                INSTANCE.checkInternal(getter, declaration.getSource(), ((FirMemberDeclaration) declaration).getStatus().getModality(), context, reporter);
            }
            FirMemberDeclaration firMemberDeclaration = (FirMemberDeclaration) declaration;
            if (!firMemberDeclaration.getStatus().isExternal() || (source = declaration.getSource()) == null || (source.getKind() instanceof KtFakeSourceElementKind)) {
                return;
            }
            if (!(declaration instanceof FirFunction)) {
                if (firMemberDeclaration instanceof FirProperty) {
                    str = "property";
                } else {
                    str = firMemberDeclaration instanceof FirRegularClass ? "class" : "non-function declaration";
                }
                String str2 = str;
                KtModifierKeywordToken EXTERNAL_KEYWORD = KtTokens.EXTERNAL_KEYWORD;
                Intrinsics.checkNotNullExpressionValue(EXTERNAL_KEYWORD, "EXTERNAL_KEYWORD");
                FirModifier<?> modifier = FirKeywordUtilsKt.getModifier(declaration, EXTERNAL_KEYWORD);
                if (modifier != null) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, modifier.getSource(), FirErrors.INSTANCE.getWRONG_MODIFIER_TARGET(), modifier.getToken(), str2, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
                    return;
                }
                return;
            }
            FirFunction firFunction = (FirFunction) declaration;
            ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag(firFunction.getSymbol());
            FirRegularClassSymbol firRegularClassSymbol = coneClassLikeLookupTagContainingClassLookupTag != null ? LookupTagUtilsKt.toFirRegularClassSymbol(coneClassLikeLookupTagContainingClassLookupTag, context.getSession()) : null;
            if (firRegularClassSymbol != null) {
                if (firRegularClassSymbol.getClassKind() == ClassKind.INTERFACE) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirJvmErrors.INSTANCE.getEXTERNAL_DECLARATION_IN_INTERFACE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                } else {
                    if (modality == null) {
                        modality = firMemberDeclaration.getStatus().getModality();
                    }
                    if (modality == Modality.ABSTRACT) {
                        if (reportSource == null) {
                            reportSource = declaration.getSource();
                        }
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, reportSource, FirJvmErrors.INSTANCE.getEXTERNAL_DECLARATION_CANNOT_BE_ABSTRACT(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                    }
                }
            }
            if (!(declaration instanceof FirConstructor) && firFunction.getBody() != null) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirJvmErrors.INSTANCE.getEXTERNAL_DECLARATION_CANNOT_HAVE_BODY(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
            if (firMemberDeclaration.getStatus().isInline()) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirJvmErrors.INSTANCE.getEXTERNAL_DECLARATION_CANNOT_BE_INLINED(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
    }
}
