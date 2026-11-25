package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.Implementation;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;

/* JADX INFO: compiled from: FirPropertyAccessorsTypesChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J(\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J \u0010\u0010\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J \u0010\u0011\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0018\u0010\u0012\u001a\u00020\u00132\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\tH\u0002J\f\u0010\u0014\u001a\u00020\u0013*\u00020\u000fH\u0002¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirPropertyAccessorsTypesChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirPropertyChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkAccessorForDelegatedProperty", "property", Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX, "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "checkGetter", "checkSetter", "isLegallyAbstract", "", "isImplicitDelegateAccessor", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirPropertyAccessorsTypesChecker extends FirDeclarationChecker<FirProperty> {
    public static final FirPropertyAccessorsTypesChecker INSTANCE = new FirPropertyAccessorsTypesChecker();

    private FirPropertyAccessorsTypesChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirProperty declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        checkGetter(declaration, context, reporter);
        checkSetter(declaration, context, reporter);
    }

    private final void checkGetter(FirProperty property, CheckerContext context, DiagnosticReporter reporter) {
        FirPropertyAccessor getter = property.getGetter();
        if (getter == null) {
            return;
        }
        ConeKotlinType coneType = FirTypeUtilsKt.getConeType(property.getReturnTypeRef());
        checkAccessorForDelegatedProperty(property, getter, context, reporter);
        if (isImplicitDelegateAccessor(getter)) {
            return;
        }
        if (!Intrinsics.areEqual(getter.getStatus().getVisibility(), property.getStatus().getVisibility())) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, getter.getSource(), FirErrors.INSTANCE.getGETTER_VISIBILITY_DIFFERS_FROM_PROPERTY_VISIBILITY(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
        if (property.getSymbol().getCallableId().getClassId() != null && getter.getBody() != null && property.getDelegate() == null && isLegallyAbstract(property, context)) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, getter.getSource(), FirErrors.INSTANCE.getABSTRACT_PROPERTY_WITH_GETTER(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
        FirTypeRef returnTypeRef = getter.getReturnTypeRef();
        KtSourceElement source = returnTypeRef.getSource();
        if ((source != null ? source.getKind() : null) instanceof KtFakeSourceElementKind) {
            return;
        }
        ConeKotlinType coneType2 = FirTypeUtilsKt.getConeType(returnTypeRef);
        if ((coneType instanceof ConeErrorType) || (coneType2 instanceof ConeErrorType) || Intrinsics.areEqual(coneType2, FirTypeUtilsKt.getConeType(property.getReturnTypeRef()))) {
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, returnTypeRef.getSource(), FirErrors.INSTANCE.getWRONG_GETTER_RETURN_TYPE(), coneType, coneType2, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
    }

    private final void checkSetter(FirProperty property, CheckerContext context, DiagnosticReporter reporter) {
        FirPropertyAccessor setter = property.getSetter();
        if (setter == null) {
            return;
        }
        ConeKotlinType coneType = FirTypeUtilsKt.getConeType(property.getReturnTypeRef());
        if (property.getIsVal()) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, setter.getSource(), FirErrors.INSTANCE.getVAL_WITH_SETTER(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
        checkAccessorForDelegatedProperty(property, setter, context, reporter);
        if (isImplicitDelegateAccessor(setter)) {
            return;
        }
        FirPropertyAccessor firPropertyAccessor = setter;
        FirProperty firProperty = property;
        Integer numCompareTo = firPropertyAccessor.getStatus().getVisibility().compareTo(firProperty.getStatus().getVisibility());
        if (numCompareTo == null || numCompareTo.intValue() > 0) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, setter.getSource(), FirErrors.INSTANCE.getSETTER_VISIBILITY_INCONSISTENT_WITH_PROPERTY_VISIBILITY(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
        if (property.getSymbol().getCallableId().getClassId() != null && property.getDelegate() == null) {
            boolean zIsLegallyAbstract = isLegallyAbstract(property, context);
            if (Intrinsics.areEqual(firPropertyAccessor.getStatus().getVisibility(), Visibilities.Private.INSTANCE) && !Intrinsics.areEqual(firProperty.getStatus().getVisibility(), Visibilities.Private.INSTANCE)) {
                if (zIsLegallyAbstract) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, setter.getSource(), FirErrors.INSTANCE.getPRIVATE_SETTER_FOR_ABSTRACT_PROPERTY(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                } else if (!DeclarationUtilsKt.isEffectivelyFinal(firProperty, context)) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, setter.getSource(), FirErrors.INSTANCE.getPRIVATE_SETTER_FOR_OPEN_PROPERTY(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                }
            }
            if (zIsLegallyAbstract && setter.getBody() != null) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, setter.getSource(), FirErrors.INSTANCE.getABSTRACT_PROPERTY_WITH_SETTER(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
        FirValueParameter firValueParameter = (FirValueParameter) CollectionsKt.first((List) setter.getValueParameters());
        if (firValueParameter.getIsVararg()) {
            return;
        }
        ConeKotlinType coneType2 = FirTypeUtilsKt.getConeType(firValueParameter.getReturnTypeRef());
        KtSourceElement source = firValueParameter.getReturnTypeRef().getSource();
        if ((coneType instanceof ConeErrorType) || (coneType2 instanceof ConeErrorType)) {
            return;
        }
        if (!Intrinsics.areEqual(coneType2, coneType) && !ConeTypeUtilsKt.hasError(coneType2)) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getWRONG_SETTER_PARAMETER_TYPE(), coneType, coneType2, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
        }
        if (ConeBuiltinTypeUtilsKt.isUnit(FirTypeUtilsKt.getConeType(setter.getReturnTypeRef()))) {
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, setter.getReturnTypeRef().getSource(), FirErrors.INSTANCE.getWRONG_SETTER_RETURN_TYPE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
    }

    private final void checkAccessorForDelegatedProperty(FirProperty property, FirPropertyAccessor accessor, CheckerContext context, DiagnosticReporter reporter) {
        if (property.getDelegateFieldSymbol() == null || accessor.getBody() == null) {
            return;
        }
        KtSourceElement source = accessor.getSource();
        if (Intrinsics.areEqual(source != null ? source.getKind() : null, KtFakeSourceElementKind.DelegatedPropertyAccessor.INSTANCE)) {
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, accessor.getSource(), FirErrors.INSTANCE.getACCESSOR_FOR_DELEGATED_PROPERTY(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
    }

    private final boolean isImplicitDelegateAccessor(FirPropertyAccessor firPropertyAccessor) {
        KtSourceElement source = firPropertyAccessor.getSource();
        return Intrinsics.areEqual(source != null ? source.getKind() : null, KtFakeSourceElementKind.DelegatedPropertyAccessor.INSTANCE);
    }

    private final boolean isLegallyAbstract(FirProperty property, CheckerContext context) {
        if (property.getStatus().getModality() == Modality.ABSTRACT) {
            FirClass firClassFindClosestClassOrObject = FirHelpersKt.findClosestClassOrObject(context);
            if (firClassFindClosestClassOrObject instanceof FirRegularClass) {
                FirRegularClass firRegularClass = (FirRegularClass) firClassFindClosestClassOrObject;
                if (firRegularClass.getStatus().getModality() == Modality.ABSTRACT || firRegularClass.getStatus().getModality() == Modality.SEALED || firRegularClass.getClassKind() == ClassKind.ENUM_CLASS) {
                    return true;
                }
            }
        }
        return false;
    }
}
