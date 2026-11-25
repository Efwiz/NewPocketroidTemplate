package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertyGetter;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertySetter;
import org.jetbrains.kotlin.fir.declarations.utils.DeclarationAttributesKt;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirErrorTypeRef;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;

/* JADX INFO: compiled from: FirInapplicableLateinitChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0018\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\f\u0010\u0015\u001a\u00020\u0006*\u00020\u0002H\u0002J\f\u0010\u0016\u001a\u00020\u0006*\u00020\u0002H\u0002J\f\u0010\u0017\u001a\u00020\u0006*\u00020\u0002H\u0002J&\u0010\u0018\u001a\u00020\u000b*\u00020\u00102\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\r\u001a\u00020\u000eH\u0002R\u0018\u0010\u0005\u001a\u00020\u0006*\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirInapplicableLateinitChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirPropertyChecker;", "()V", "hasNullableUpperBound", "", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "getHasNullableUpperBound", "(Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;)Z", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "hasUnderlyingTypeForbiddenForLateinit", ModuleXmlParser.TYPE, "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "hasGetter", "hasSetter", "isNullable", "reportError", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "target", "", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirInapplicableLateinitChecker extends FirDeclarationChecker<FirProperty> {
    public static final FirInapplicableLateinitChecker INSTANCE = new FirInapplicableLateinitChecker();

    private FirInapplicableLateinitChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirProperty declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (!declaration.getStatus().isLateInit() || (declaration.getReturnTypeRef() instanceof FirErrorTypeRef)) {
            return;
        }
        if (declaration.getIsVal()) {
            reportError(reporter, declaration.getSource(), "is allowed only on mutable properties", context);
        }
        if (declaration.getInitializer() != null) {
            if (declaration.getIsLocal()) {
                reportError(reporter, declaration.getSource(), "is not allowed on local variables with initializer", context);
            } else {
                reportError(reporter, declaration.getSource(), "is not allowed on properties with initializer", context);
            }
        }
        if (declaration.getDelegate() != null) {
            reportError(reporter, declaration.getSource(), "is not allowed on delegated properties", context);
        }
        if (isNullable(declaration)) {
            reportError(reporter, declaration.getSource(), "is not allowed on properties of a type with nullable upper bound", context);
        }
        if (ConeBuiltinTypeUtilsKt.isPrimitive(FirTypeUtilsKt.getConeType(declaration.getReturnTypeRef()))) {
            if (declaration.getIsLocal()) {
                reportError(reporter, declaration.getSource(), "is not allowed on local variables of primitive types", context);
            } else {
                reportError(reporter, declaration.getSource(), "is not allowed on properties of primitive types", context);
            }
        }
        if (DeclarationAttributesKt.getHasExplicitBackingField(declaration)) {
            reportError(reporter, declaration.getSource(), "must be moved to the field declaration", context);
        }
        if ((hasGetter(declaration) || hasSetter(declaration)) && declaration.getDelegate() == null) {
            reportError(reporter, declaration.getSource(), "is not allowed on properties with a custom getter or setter", context);
        }
        if (FirHelpersKt.isSingleFieldValueClass(FirTypeUtilsKt.getConeType(declaration.getReturnTypeRef()), context.getSession())) {
            ConeKotlinType coneType = FirTypeUtilsKt.getConeType(declaration.getReturnTypeRef());
            String str = declaration.getIsLocal() ? "local variables" : "properties";
            if (ConeBuiltinTypeUtilsKt.isUnsignedType(coneType)) {
                reportError(reporter, declaration.getSource(), "is not allowed on " + str + " of unsigned types", context);
            } else if (!context.get$languageVersionSettings().supportsFeature(LanguageFeature.InlineLateinit)) {
                reportError(reporter, declaration.getSource(), "is not allowed on " + str + " of inline class types", context);
            } else if (hasUnderlyingTypeForbiddenForLateinit(coneType, context.getSession())) {
                reportError(reporter, declaration.getSource(), "is not allowed on " + str + " of inline type with underlying type not suitable for lateinit declaration", context);
            }
        }
    }

    private static final boolean hasUnderlyingTypeForbiddenForLateinit$isForbiddenTypeForLateinit(FirSession firSession, ConeKotlinType coneKotlinType) {
        if (ConeBuiltinTypeUtilsKt.isPrimitiveOrNullablePrimitive(coneKotlinType) || INSTANCE.getHasNullableUpperBound(coneKotlinType)) {
            return true;
        }
        if (FirHelpersKt.isSingleFieldValueClass(coneKotlinType, firSession)) {
            return hasUnderlyingTypeForbiddenForLateinit$isForbiddenTypeForLateinit(firSession, FirHelpersKt.getInlineClassUnderlyingType(coneKotlinType, firSession));
        }
        return false;
    }

    private final boolean hasUnderlyingTypeForbiddenForLateinit(ConeKotlinType type, FirSession session) {
        if (FirHelpersKt.isRecursiveValueClassType(type, session)) {
            return false;
        }
        return hasUnderlyingTypeForbiddenForLateinit$isForbiddenTypeForLateinit(session, FirHelpersKt.getInlineClassUnderlyingType(type, session));
    }

    private final boolean getHasNullableUpperBound(ConeKotlinType coneKotlinType) {
        if (!(coneKotlinType instanceof ConeTypeParameterType)) {
            return ConeTypeUtilsKt.isNullable(coneKotlinType);
        }
        if (!ConeTypeUtilsKt.isNullable(coneKotlinType)) {
            List<FirResolvedTypeRef> resolvedBounds = ((ConeTypeParameterType) coneKotlinType).getLookupTag().getTypeParameterSymbol().getResolvedBounds();
            if (!(resolvedBounds instanceof Collection) || !resolvedBounds.isEmpty()) {
                Iterator<T> it = resolvedBounds.iterator();
                while (it.hasNext()) {
                    if (ConeTypeUtilsKt.isNullable(FirTypeUtilsKt.getConeType((FirResolvedTypeRef) it.next()))) {
                    }
                }
            }
            return false;
        }
        return true;
    }

    private final boolean isNullable(FirProperty firProperty) {
        return getHasNullableUpperBound(FirTypeUtilsKt.getConeType(firProperty.getReturnTypeRef()));
    }

    private final boolean hasGetter(FirProperty firProperty) {
        return (firProperty.getGetter() == null || (firProperty.getGetter() instanceof FirDefaultPropertyGetter)) ? false : true;
    }

    private final boolean hasSetter(FirProperty firProperty) {
        return (firProperty.getSetter() == null || (firProperty.getSetter() instanceof FirDefaultPropertySetter)) ? false : true;
    }

    private final void reportError(DiagnosticReporter diagnosticReporter, KtSourceElement ktSourceElement, String str, CheckerContext checkerContext) {
        KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, ktSourceElement, FirErrors.INSTANCE.getINAPPLICABLE_LATEINIT_MODIFIER(), str, checkerContext, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
    }
}
