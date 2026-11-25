package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.annotations.KotlinTarget;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategiesKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.FirRetentionAnnotationHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirAnnotationHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirConstChecksKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeLookupTagBasedType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeProjectionKt;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: FirAnnotationClassDeclarationChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003:\u0001\u001bB\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J \u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J \u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0018\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\u0018\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0016\u001a\u00020\u0017H\u0002¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirAnnotationClassDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirRegularClassChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkAnnotationClassMember", "member", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "checkCyclesInParameters", "annotation", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "isAllowedArray", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "isAllowedClassKind", "cone", "Lorg/jetbrains/kotlin/fir/types/ConeLookupTagBasedType;", "CycleChecker", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirAnnotationClassDeclarationChecker extends FirDeclarationChecker<FirRegularClass> {
    public static final FirAnnotationClassDeclarationChecker INSTANCE = new FirAnnotationClassDeclarationChecker();

    private FirAnnotationClassDeclarationChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirRegularClass declaration, CheckerContext context, DiagnosticReporter reporter) {
        FirRegularClass firRegularClass;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (declaration.getClassKind() != ClassKind.ANNOTATION_CLASS) {
            return;
        }
        if (declaration.getSymbol().getClassId().isLocal()) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getLOCAL_ANNOTATION_CLASS_ERROR(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
        if (declaration.getSuperTypeRefs().size() != 1) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getSUPERTYPES_FOR_ANNOTATION_CLASS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
        Iterator<FirDeclaration> it = declaration.getDeclarations().iterator();
        while (it.hasNext()) {
            checkAnnotationClassMember(it.next(), context, reporter);
        }
        FirSession session = context.getSession();
        if (FirRetentionAnnotationHelpersKt.getRetention(declaration, session) != AnnotationRetention.SOURCE && FirAnnotationHelpersKt.getAllowedAnnotationTargets(declaration, session).contains(KotlinTarget.EXPRESSION)) {
            FirRegularClass firRegularClass2 = declaration;
            FirAnnotation retentionAnnotation = FirRetentionAnnotationHelpersKt.getRetentionAnnotation(firRegularClass2, session);
            if (retentionAnnotation != null) {
                firRegularClass = retentionAnnotation;
            } else {
                FirAnnotation targetAnnotation = FirAnnotationHelpersKt.getTargetAnnotation(firRegularClass2, session);
                firRegularClass = targetAnnotation != null ? targetAnnotation : declaration;
            }
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, firRegularClass.getSource(), FirErrors.INSTANCE.getRESTRICTED_RETENTION_FOR_EXPRESSION_ANNOTATION(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
        checkCyclesInParameters(declaration.getSymbol(), context, reporter);
    }

    private final void checkAnnotationClassMember(FirDeclaration member, CheckerContext context, DiagnosticReporter reporter) {
        if (member instanceof FirConstructor) {
            FirConstructor firConstructor = (FirConstructor) member;
            if (firConstructor.getIsPrimary()) {
                for (FirValueParameter firValueParameter : firConstructor.getValueParameters()) {
                    KtSourceElement source = firValueParameter.getSource();
                    if (source != null) {
                        if (!LightTreePositioningStrategiesKt.hasValOrVar(source)) {
                            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getMISSING_VAL_ON_ANNOTATION_PARAMETER(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                        } else if (LightTreePositioningStrategiesKt.hasVar(source)) {
                            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getVAR_ANNOTATION_PARAMETER(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                        }
                        FirExpression defaultValue = firValueParameter.getDefaultValue();
                        if (defaultValue != null && FirConstChecksKt.checkConstantArguments(defaultValue, context.getSession()) != null) {
                            KtDiagnosticReportHelpersKt.reportOn$default(reporter, defaultValue.getSource(), FirErrors.INSTANCE.getANNOTATION_PARAMETER_DEFAULT_VALUE_MUST_BE_CONSTANT(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                        }
                        FirTypeRef returnTypeRef = firValueParameter.getReturnTypeRef();
                        FirResolvedTypeRef firResolvedTypeRef = returnTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) returnTypeRef : null;
                        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
                        if (!(type instanceof ConeLookupTagBasedType)) {
                            type = null;
                        }
                        ConeLookupTagBasedType coneLookupTagBasedType = (ConeLookupTagBasedType) type;
                        ConeSimpleKotlinType coneSimpleKotlinTypeFullyExpandedType = coneLookupTagBasedType != null ? TypeExpansionUtilsKt.fullyExpandedType((ConeSimpleKotlinType) coneLookupTagBasedType, context.getSession()) : null;
                        ConeLookupTagBasedType coneLookupTagBasedType2 = coneSimpleKotlinTypeFullyExpandedType instanceof ConeLookupTagBasedType ? (ConeLookupTagBasedType) coneSimpleKotlinTypeFullyExpandedType : null;
                        ClassId classId = coneLookupTagBasedType2 != null ? ConeTypeUtilsKt.getClassId(coneLookupTagBasedType2) : null;
                        if (coneLookupTagBasedType2 != null && !(coneLookupTagBasedType2 instanceof ConeErrorType)) {
                            ConeLookupTagBasedType coneLookupTagBasedType3 = coneLookupTagBasedType2;
                            if (ConeTypeUtilsKt.isNullable(coneLookupTagBasedType3)) {
                                KtDiagnosticReportHelpersKt.reportOn$default(reporter, returnTypeRef.getSource(), FirErrors.INSTANCE.getNULLABLE_TYPE_OF_ANNOTATION_MEMBER(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                            } else if (!ConeBuiltinTypeUtilsKt.isPrimitiveOrNullablePrimitive(coneLookupTagBasedType3) && !ConeBuiltinTypeUtilsKt.isUnsignedTypeOrNullableUnsignedType(coneLookupTagBasedType3) && !Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getKClass()) && !Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getString()) && !CollectionsKt.contains(StandardClassIds.INSTANCE.getPrimitiveArrayTypeByElementType().values(), classId) && !CollectionsKt.contains(StandardClassIds.INSTANCE.getUnsignedArrayTypeByElementType().values(), classId)) {
                                if (Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getArray())) {
                                    if (!isAllowedArray(coneLookupTagBasedType3, context.getSession())) {
                                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, returnTypeRef.getSource(), FirErrors.INSTANCE.getINVALID_TYPE_OF_ANNOTATION_MEMBER(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                                    }
                                } else if (!isAllowedClassKind(coneLookupTagBasedType2, context.getSession())) {
                                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, returnTypeRef.getSource(), FirErrors.INSTANCE.getINVALID_TYPE_OF_ANNOTATION_MEMBER(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                                }
                            }
                        }
                    }
                }
                return;
            }
        }
        if (member instanceof FirRegularClass) {
            return;
        }
        if (member instanceof FirProperty) {
            KtSourceElement source2 = member.getSource();
            if (Intrinsics.areEqual(source2 != null ? source2.getElementType() : null, KtNodeTypes.VALUE_PARAMETER)) {
                return;
            }
        }
        if ((member instanceof FirSimpleFunction) && (member.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) {
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, member.getSource(), FirErrors.INSTANCE.getANNOTATION_CLASS_MEMBER(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
    }

    private final boolean isAllowedClassKind(ConeLookupTagBasedType cone, FirSession session) {
        ClassKind classKind;
        FirClassifierSymbol<?> symbol = LookupTagUtilsKt.toSymbol(cone.getLookupTag(), session);
        FirRegularClassSymbol firRegularClassSymbol = symbol instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) symbol : null;
        if (firRegularClassSymbol == null || (classKind = firRegularClassSymbol.getClassKind()) == null) {
            return false;
        }
        return classKind == ClassKind.ANNOTATION_CLASS || classKind == ClassKind.ENUM_CLASS;
    }

    private final boolean isAllowedArray(ConeKotlinType type, FirSession session) {
        ConeKotlinType type2;
        ConeKotlinType coneKotlinTypeFullyExpandedType;
        ConeTypeProjection[] typeArguments = type.getTypeArguments();
        if (typeArguments.length != 1) {
            return false;
        }
        ConeTypeProjection coneTypeProjection = typeArguments[0];
        ConeKotlinTypeProjection coneKotlinTypeProjection = coneTypeProjection instanceof ConeKotlinTypeProjection ? (ConeKotlinTypeProjection) coneTypeProjection : null;
        if (coneKotlinTypeProjection == null || (type2 = coneKotlinTypeProjection.getType()) == null || (coneKotlinTypeFullyExpandedType = TypeExpansionUtilsKt.fullyExpandedType(type2, session)) == null || ConeTypeUtilsKt.isNullable(coneKotlinTypeFullyExpandedType)) {
            return false;
        }
        ClassId classId = ConeTypeUtilsKt.getClassId(coneKotlinTypeFullyExpandedType);
        return Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getKClass()) || Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getString()) || isAllowedClassKind((ConeLookupTagBasedType) coneKotlinTypeFullyExpandedType, session);
    }

    private final void checkCyclesInParameters(FirRegularClassSymbol annotation, CheckerContext context, DiagnosticReporter reporter) {
        FirConstructorSymbol firConstructorSymbolPrimaryConstructorSymbol = DeclarationUtilsKt.primaryConstructorSymbol(annotation);
        if (firConstructorSymbolPrimaryConstructorSymbol == null) {
            return;
        }
        CycleChecker cycleChecker = new CycleChecker(annotation, context.getSession());
        for (FirValueParameterSymbol firValueParameterSymbol : firConstructorSymbolPrimaryConstructorSymbol.getValueParameterSymbols()) {
            if (cycleChecker.parameterHasCycle(annotation, firValueParameterSymbol)) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, firValueParameterSymbol.getSource(), FirErrors.INSTANCE.getCYCLE_IN_ANNOTATION_PARAMETER(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
    }

    /* JADX INFO: compiled from: FirAnnotationClassDeclarationChecker.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0003J\u0016\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u0014J\u0016\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u0017R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00030\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirAnnotationClassDeclarationChecker$CycleChecker;", "", "targetAnnotation", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;Lorg/jetbrains/kotlin/fir/FirSession;)V", "annotationsWithCycle", "", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "getTargetAnnotation", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "visitedAnnotations", "annotationHasCycle", "", "annotation", "parameterHasCycle", "ownedAnnotation", "parameter", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirValueParameterSymbol;", "typeHasCycle", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class CycleChecker {
        private final Set<FirRegularClassSymbol> annotationsWithCycle;
        private final FirSession session;
        private final FirRegularClassSymbol targetAnnotation;
        private final Set<FirRegularClassSymbol> visitedAnnotations;

        public CycleChecker(FirRegularClassSymbol targetAnnotation, FirSession session) {
            Intrinsics.checkNotNullParameter(targetAnnotation, "targetAnnotation");
            Intrinsics.checkNotNullParameter(session, "session");
            this.targetAnnotation = targetAnnotation;
            this.session = session;
            this.visitedAnnotations = SetsKt.mutableSetOf(targetAnnotation);
            this.annotationsWithCycle = SetsKt.mutableSetOf(targetAnnotation);
        }

        public final FirSession getSession() {
            return this.session;
        }

        public final FirRegularClassSymbol getTargetAnnotation() {
            return this.targetAnnotation;
        }

        public final boolean annotationHasCycle(FirRegularClassSymbol annotation) {
            Intrinsics.checkNotNullParameter(annotation, "annotation");
            FirConstructorSymbol firConstructorSymbolPrimaryConstructorSymbol = DeclarationUtilsKt.primaryConstructorSymbol(annotation);
            if (firConstructorSymbolPrimaryConstructorSymbol == null) {
                return false;
            }
            Iterator<FirValueParameterSymbol> it = firConstructorSymbolPrimaryConstructorSymbol.getValueParameterSymbols().iterator();
            while (it.hasNext()) {
                if (parameterHasCycle(annotation, it.next())) {
                    return true;
                }
            }
            return false;
        }

        public final boolean parameterHasCycle(FirRegularClassSymbol ownedAnnotation, FirValueParameterSymbol parameter) {
            Intrinsics.checkNotNullParameter(ownedAnnotation, "ownedAnnotation");
            Intrinsics.checkNotNullParameter(parameter, "parameter");
            ConeKotlinType coneType = FirTypeUtilsKt.getConeType(parameter.getResolvedReturnTypeRef());
            if (parameter.isVararg() || ConeBuiltinTypeUtilsKt.isNonPrimitiveArray(coneType)) {
                return false;
            }
            if (!(coneType.getTypeArguments().length == 0)) {
                if (Intrinsics.areEqual(ConeTypeUtilsKt.getClassId(coneType), StandardClassIds.INSTANCE.getKClass())) {
                    return false;
                }
                for (ConeTypeProjection coneTypeProjection : coneType.getTypeArguments()) {
                    ConeKotlinType type = ConeTypeProjectionKt.getType(coneTypeProjection);
                    if (type != null && typeHasCycle(ownedAnnotation, type)) {
                        return true;
                    }
                }
                return false;
            }
            return typeHasCycle(ownedAnnotation, coneType);
        }

        public final boolean typeHasCycle(FirRegularClassSymbol ownedAnnotation, ConeKotlinType type) {
            Intrinsics.checkNotNullParameter(ownedAnnotation, "ownedAnnotation");
            Intrinsics.checkNotNullParameter(type, "type");
            FirRegularClassSymbol regularClassSymbol = TypeUtilsKt.toRegularClassSymbol(TypeExpansionUtilsKt.fullyExpandedType(type, this.session), this.session);
            if (regularClassSymbol == null) {
                return false;
            }
            if (regularClassSymbol.getClassKind() != ClassKind.ANNOTATION_CLASS) {
                regularClassSymbol = null;
            }
            if (regularClassSymbol == null) {
                return false;
            }
            if (!this.visitedAnnotations.add(regularClassSymbol)) {
                boolean zContains = this.annotationsWithCycle.contains(regularClassSymbol);
                if (zContains) {
                    this.annotationsWithCycle.add(ownedAnnotation);
                }
                return zContains;
            }
            if (Intrinsics.areEqual(regularClassSymbol, this.targetAnnotation)) {
                this.annotationsWithCycle.add(ownedAnnotation);
                return true;
            }
            return annotationHasCycle(regularClassSymbol);
        }
    }
}
