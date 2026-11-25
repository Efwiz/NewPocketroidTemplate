package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.utils.DeclarationAttributesKt;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.types.TypeCheckerState;

/* JADX INFO: compiled from: FirPropertyFieldTypeChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J \u0010\u0013\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J \u0010\u0015\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002R\u001a\u0010\u0005\u001a\u00020\u0006*\u0004\u0018\u00010\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\bR\u0018\u0010\t\u001a\u00020\u0006*\u00020\n8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u000b¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirPropertyFieldTypeChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirPropertyChecker;", "()V", "isNotExplicit", "", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "(Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;)Z", "isNullable", "Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "(Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;)Z", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkAsFieldNotSubtype", "property", "checkAsPropertyNotSubtype", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirPropertyFieldTypeChecker extends FirDeclarationChecker<FirProperty> {
    public static final FirPropertyFieldTypeChecker INSTANCE = new FirPropertyFieldTypeChecker();

    private FirPropertyFieldTypeChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirProperty declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirBackingField backingField = declaration.getBackingField();
        if (backingField != null && DeclarationAttributesKt.getHasExplicitBackingField(declaration)) {
            TypeCheckerState typeCheckerStateNewTypeCheckerState = TypeComponentsKt.getTypeContext(context.getSession()).newTypeCheckerState(false, false);
            if (declaration.getInitializer() != null) {
                FirExpression initializer = declaration.getInitializer();
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, initializer != null ? initializer.getSource() : null, FirErrors.INSTANCE.getPROPERTY_INITIALIZER_WITH_EXPLICIT_FIELD_DECLARATION(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
            FirBackingField firBackingField = backingField;
            if (firBackingField.getStatus().isLateInit() && declaration.getIsVal()) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, backingField.getSource(), FirErrors.INSTANCE.getLATEINIT_FIELD_IN_VAL_PROPERTY(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
            if (backingField.getInitializer() == null && !firBackingField.getStatus().isLateInit()) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, backingField.getSource(), FirErrors.INSTANCE.getPROPERTY_FIELD_DECLARATION_MISSING_INITIALIZER(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            } else if (backingField.getInitializer() != null && firBackingField.getStatus().isLateInit()) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, backingField.getSource(), FirErrors.INSTANCE.getLATEINIT_PROPERTY_FIELD_DECLARATION_WITH_INITIALIZER(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
            if (firBackingField.getStatus().isLateInit() && isNullable(backingField)) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, backingField.getSource(), FirErrors.INSTANCE.getLATEINIT_NULLABLE_BACKING_FIELD(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
            if (declaration.getDelegate() != null) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, backingField.getSource(), FirErrors.INSTANCE.getBACKING_FIELD_FOR_DELEGATED_PROPERTY(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
            if (Intrinsics.areEqual(FirTypeUtilsKt.getConeType(backingField.getReturnTypeRef()), FirTypeUtilsKt.getConeType(declaration.getReturnTypeRef()))) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, backingField.getSource(), FirErrors.INSTANCE.getREDUNDANT_EXPLICIT_BACKING_FIELD(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                return;
            }
            FirBackingField firBackingField2 = backingField;
            FirProperty firProperty = declaration;
            if (!TypeUtilsKt.isSubtypeOf(firBackingField2, firProperty, typeCheckerStateNewTypeCheckerState)) {
                checkAsFieldNotSubtype(declaration, context, reporter);
            }
            if (TypeUtilsKt.isSubtypeOf(firProperty, firBackingField2, typeCheckerStateNewTypeCheckerState)) {
                return;
            }
            checkAsPropertyNotSubtype(declaration, context, reporter);
        }
    }

    private final boolean isNullable(FirBackingField firBackingField) {
        ConeKotlinType coneType = FirTypeUtilsKt.getConeType(firBackingField.getReturnTypeRef());
        if (!(coneType instanceof ConeTypeParameterType)) {
            return ConeTypeUtilsKt.isNullable(coneType);
        }
        if (!ConeTypeUtilsKt.isNullable(coneType)) {
            List<FirResolvedTypeRef> resolvedBounds = ((ConeTypeParameterType) coneType).getLookupTag().getTypeParameterSymbol().getResolvedBounds();
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

    private final boolean isNotExplicit(FirPropertyAccessor firPropertyAccessor) {
        return firPropertyAccessor == null || (firPropertyAccessor instanceof FirDefaultPropertyAccessor);
    }

    private final void checkAsPropertyNotSubtype(FirProperty property, CheckerContext context, DiagnosticReporter reporter) {
        if (property.getIsVar() && isNotExplicit(property.getSetter())) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, property.getSource(), FirErrors.INSTANCE.getPROPERTY_MUST_HAVE_SETTER(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }

    private final void checkAsFieldNotSubtype(FirProperty property, CheckerContext context, DiagnosticReporter reporter) {
        if (isNotExplicit(property.getGetter())) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, property.getSource(), FirErrors.INSTANCE.getPROPERTY_MUST_HAVE_GETTER(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }
}
