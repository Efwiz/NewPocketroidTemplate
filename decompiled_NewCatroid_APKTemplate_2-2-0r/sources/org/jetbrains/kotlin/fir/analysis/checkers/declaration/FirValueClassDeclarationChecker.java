package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtRealSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirKeywordUtilsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirValueClassRepresentationKt;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.ValueClassesUtilsKt;
import org.jetbrains.kotlin.fir.declarations.utils.DeclarationAttributesKt;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt;
import org.jetbrains.kotlin.fir.resolve.SupertypeSupplier;
import org.jetbrains.kotlin.fir.resolve.SupertypeUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ArrayUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeStarProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeProjectionKt;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitAnyTypeRef;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: FirValueClassDeclarationChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\f\u0010\u0013\u001a\u00020\u0014*\u00020\u0015H\u0002J\f\u0010\u0016\u001a\u00020\u0014*\u00020\u0017H\u0002J\u0014\u0010\u0018\u001a\u00020\u0014*\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u0016\u0010\u001c\u001a\u00020\u0014*\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0002J\u0016\u0010 \u001a\u00020\u0014*\u00020\u001f2\b\u0010!\u001a\u0004\u0018\u00010\u001dH\u0002J\u0014\u0010\"\u001a\u00020\u0014*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u001bH\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirValueClassDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirRegularClassChecker;", "()V", "boxAndUnboxNames", "", "", "cloneableFqName", "Lorg/jetbrains/kotlin/name/FqName;", "equalsAndHashCodeNames", "javaLangFqName", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "isCloneableId", "", "Lorg/jetbrains/kotlin/name/ClassId;", "isGenericArrayOfTypeParameter", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "isInapplicableParameterType", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "isNotFinalReadOnly", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "primaryConstructorProperty", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "isRelatedToParameter", "parameter", "isSubtypeOfCloneable", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirValueClassDeclarationChecker extends FirDeclarationChecker<FirRegularClass> {
    public static final FirValueClassDeclarationChecker INSTANCE = new FirValueClassDeclarationChecker();
    private static final Set<String> boxAndUnboxNames = SetsKt.setOf((Object[]) new String[]{"box", "unbox"});
    private static final Set<String> equalsAndHashCodeNames = SetsKt.setOf((Object[]) new String[]{Namer.EQUALS_METHOD_NAME, "hashCode"});
    private static final FqName javaLangFqName = new FqName(CommonClassNames.DEFAULT_PACKAGE);
    private static final FqName cloneableFqName = new FqName("Cloneable");

    private FirValueClassDeclarationChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirRegularClass declaration, CheckerContext context, DiagnosticReporter reporter) {
        KtSourceElement source;
        FirRegularClassSymbol regularClassSymbol;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt.isInlineOrValueClass(declaration.getSymbol())) {
            FirRegularClass firRegularClass = declaration;
            if (firRegularClass.getStatus().isInner() || declaration.getSymbol().getClassId().isLocal()) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getVALUE_CLASS_NOT_TOP_LEVEL(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
            if (firRegularClass.getStatus().getModality() != Modality.FINAL) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getVALUE_CLASS_NOT_FINAL(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
            if (!declaration.getContextReceivers().isEmpty()) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getVALUE_CLASS_CANNOT_HAVE_CONTEXT_RECEIVERS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
            for (FirTypeRef firTypeRef : declaration.getSuperTypeRefs()) {
                if (!(firTypeRef instanceof FirImplicitAnyTypeRef) && ((regularClassSymbol = FirHelpersKt.toRegularClassSymbol(firTypeRef, context.getSession())) == null || regularClassSymbol.getClassKind() != ClassKind.INTERFACE)) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, firTypeRef.getSource(), FirErrors.INSTANCE.getVALUE_CLASS_CANNOT_EXTEND_CLASSES(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                }
            }
            if (isSubtypeOfCloneable(declaration, context.getSession())) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getVALUE_CLASS_CANNOT_BE_CLONEABLE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
            Map mapEmptyMap = MapsKt.emptyMap();
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            FirDeclaration firDeclaration = null;
            Set setEmptySet = SetsKt.emptySet();
            FirConstructor firConstructor = null;
            for (FirDeclaration firDeclaration2 : declaration.getDeclarations()) {
                if (firDeclaration2 instanceof FirConstructor) {
                    FirConstructor firConstructor2 = (FirConstructor) firDeclaration2;
                    if (firConstructor2.getIsPrimary()) {
                        List<FirValueParameter> valueParameters = firConstructor2.getValueParameters();
                        LinkedHashMap linkedHashMap2 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(valueParameters, 10)), 16));
                        for (Object obj : valueParameters) {
                            linkedHashMap2.put(((FirValueParameter) obj).getName(), obj);
                        }
                        ArrayList arrayList = new ArrayList(linkedHashMap2.size());
                        Iterator it = linkedHashMap2.entrySet().iterator();
                        while (it.hasNext()) {
                            arrayList.add(((FirValueParameter) ((Map.Entry) it.next()).getValue()).getSymbol());
                        }
                        setEmptySet = CollectionsKt.toSet(arrayList);
                        firConstructor = firConstructor2;
                        mapEmptyMap = linkedHashMap2;
                    } else if (firConstructor2.getBody() != null && !context.get$languageVersionSettings().supportsFeature(LanguageFeature.ValueClassesSecondaryConstructorWithBody)) {
                        FirBlock body = firConstructor2.getBody();
                        Intrinsics.checkNotNull(body);
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, body.getSource(), FirErrors.INSTANCE.getSECONDARY_CONSTRUCTOR_WITH_BODY_INSIDE_VALUE_CLASS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                    }
                } else if (!(firDeclaration2 instanceof FirRegularClass)) {
                    if (firDeclaration2 instanceof FirSimpleFunction) {
                        String strAsString = ((FirSimpleFunction) firDeclaration2).getName().asString();
                        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                        if (boxAndUnboxNames.contains(strAsString) || (equalsAndHashCodeNames.contains(strAsString) && !context.get$languageVersionSettings().supportsFeature(LanguageFeature.CustomEqualsInValueClasses))) {
                            KtDiagnosticReportHelpersKt.reportOn$default(reporter, firDeclaration2.getSource(), FirErrors.INSTANCE.getRESERVED_MEMBER_INSIDE_VALUE_CLASS(), strAsString, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                        }
                    } else if (!(firDeclaration2 instanceof FirField)) {
                        if (firDeclaration2 instanceof FirProperty) {
                            FirProperty firProperty = (FirProperty) firDeclaration2;
                            if (isRelatedToParameter(firProperty, (FirValueParameter) mapEmptyMap.get(firProperty.getName()))) {
                                linkedHashMap.put(firProperty.getName(), firDeclaration2);
                            } else if (firProperty.getDelegate() != null) {
                                FirExpression delegate = firProperty.getDelegate();
                                Intrinsics.checkNotNull(delegate);
                                KtDiagnosticReportHelpersKt.reportOn$default(reporter, delegate.getSource(), FirErrors.INSTANCE.getDELEGATED_PROPERTY_INSIDE_VALUE_CLASS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                            } else if (DeclarationAttributesKt.getHasBackingField(firProperty)) {
                                KtSourceElement source2 = firDeclaration2.getSource();
                                if (!((source2 != null ? source2.getKind() : null) instanceof KtFakeSourceElementKind)) {
                                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, firDeclaration2.getSource(), FirErrors.INSTANCE.getPROPERTY_WITH_BACKING_FIELD_INSIDE_VALUE_CLASS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                                }
                            }
                        }
                    } else if (firDeclaration2.getOrigin() instanceof FirDeclarationOrigin.Synthetic) {
                        FirField firField = (FirField) firDeclaration2;
                        FirExpression initializer = firField.getInitializer();
                        FirCallableSymbol<?> resolvedCallableSymbol = initializer != null ? FirExpressionUtilKt.toResolvedCallableSymbol(initializer) : null;
                        if (!context.get$languageVersionSettings().supportsFeature(LanguageFeature.InlineClassImplementationByDelegation) || resolvedCallableSymbol == null || !CollectionsKt.contains(setEmptySet, resolvedCallableSymbol)) {
                            FirTypeRef returnTypeRef = firField.getReturnTypeRef();
                            Intrinsics.checkNotNull(returnTypeRef, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.FirResolvedTypeRef");
                            FirTypeRef delegatedTypeRef = ((FirResolvedTypeRef) returnTypeRef).getDelegatedTypeRef();
                            KtDiagnosticReportHelpersKt.reportOn$default(reporter, delegatedTypeRef != null ? delegatedTypeRef.getSource() : null, FirErrors.INSTANCE.getVALUE_CLASS_CANNOT_IMPLEMENT_INTERFACE_BY_DELEGATION(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                        }
                    }
                } else if (((FirMemberDeclaration) firDeclaration2).getStatus().isInner()) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, firDeclaration2.getSource(), FirErrors.INSTANCE.getINNER_CLASS_INSIDE_VALUE_CLASS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                }
            }
            if (!(((firConstructor == null || (source = firConstructor.getSource()) == null) ? null : source.getKind()) instanceof KtRealSourceElementKind)) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getABSENCE_OF_PRIMARY_CONSTRUCTOR_FOR_VALUE_CLASS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                return;
            }
            if (context.get$languageVersionSettings().supportsFeature(LanguageFeature.ValueClasses)) {
                if (mapEmptyMap.isEmpty()) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, firConstructor.getSource(), FirErrors.INSTANCE.getVALUE_CLASS_EMPTY_CONSTRUCTOR(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                    return;
                }
            } else if (mapEmptyMap.size() != 1) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, firConstructor.getSource(), FirErrors.INSTANCE.getINLINE_CLASS_CONSTRUCTOR_WRONG_PARAMETERS_SIZE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                return;
            }
            for (Map.Entry entry : mapEmptyMap.entrySet()) {
                Name name2 = (Name) entry.getKey();
                FirValueParameter firValueParameter = (FirValueParameter) entry.getValue();
                if (isNotFinalReadOnly(firValueParameter, (FirProperty) linkedHashMap.get(name2))) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, firValueParameter.getSource(), FirErrors.INSTANCE.getVALUE_CLASS_CONSTRUCTOR_NOT_FINAL_READ_ONLY_PARAMETER(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                } else {
                    if (!context.get$languageVersionSettings().supportsFeature(LanguageFeature.GenericInlineClassParameter)) {
                        ConeKotlinType coneType = FirTypeUtilsKt.getConeType(firValueParameter.getReturnTypeRef());
                        if ((coneType instanceof ConeTypeParameterType) || INSTANCE.isGenericArrayOfTypeParameter(coneType)) {
                            KtDiagnosticReportHelpersKt.reportOn$default(reporter, firValueParameter.getReturnTypeRef().getSource(), FirErrors.INSTANCE.getUNSUPPORTED_FEATURE(), TuplesKt.to(LanguageFeature.GenericInlineClassParameter, context.get$languageVersionSettings()), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                        }
                    }
                    if (isInapplicableParameterType(firValueParameter.getReturnTypeRef(), context.getSession())) {
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, firValueParameter.getReturnTypeRef().getSource(), FirErrors.INSTANCE.getVALUE_CLASS_HAS_INAPPLICABLE_PARAMETER_TYPE(), FirTypeUtilsKt.getConeType(firValueParameter.getReturnTypeRef()), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                    } else if (FirHelpersKt.isRecursiveValueClassType(FirTypeUtilsKt.getConeType(firValueParameter.getReturnTypeRef()), context.getSession())) {
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, firValueParameter.getReturnTypeRef().getSource(), FirErrors.INSTANCE.getVALUE_CLASS_CANNOT_BE_RECURSIVE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                    } else if (FirValueClassRepresentationKt.getMultiFieldValueClassRepresentation(declaration) != null && firValueParameter.getDefaultValue() != null) {
                        FirExpression defaultValue = firValueParameter.getDefaultValue();
                        Intrinsics.checkNotNull(defaultValue);
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, defaultValue.getSource(), FirErrors.INSTANCE.getMULTI_FIELD_VALUE_CLASS_PRIMARY_CONSTRUCTOR_DEFAULT_PARAMETER(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                    }
                }
            }
            if (context.get$languageVersionSettings().supportsFeature(LanguageFeature.CustomEqualsInValueClasses)) {
                FirDeclaration firDeclaration3 = null;
                for (FirDeclaration firDeclaration4 : declaration.getDeclarations()) {
                    if (firDeclaration4 instanceof FirSimpleFunction) {
                        FirSimpleFunction firSimpleFunction = (FirSimpleFunction) firDeclaration4;
                        if (org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt.isEquals(firSimpleFunction, context.getSession())) {
                            firDeclaration = firDeclaration4;
                        }
                        if (ValueClassesUtilsKt.isTypedEqualsInValueClass(firSimpleFunction, context.getSession())) {
                            firDeclaration3 = firDeclaration4;
                        }
                    }
                }
                Pair pair = TuplesKt.to(firDeclaration, firDeclaration3);
                FirSimpleFunction firSimpleFunction2 = (FirSimpleFunction) pair.component1();
                FirSimpleFunction firSimpleFunction3 = (FirSimpleFunction) pair.component2();
                if (firSimpleFunction3 != null) {
                    if (!firSimpleFunction3.getTypeParameters().isEmpty()) {
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, firSimpleFunction3.getSource(), FirErrors.INSTANCE.getTYPE_PARAMETERS_NOT_ALLOWED(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                    }
                    FirTypeRef returnTypeRef2 = ((FirValueParameter) CollectionsKt.single((List) firSimpleFunction3.getValueParameters())).getReturnTypeRef();
                    for (ConeTypeProjection coneTypeProjection : FirTypeUtilsKt.getConeType(returnTypeRef2).getTypeArguments()) {
                        if (!ConeTypeProjectionKt.isStarProjection(coneTypeProjection)) {
                            KtDiagnosticReportHelpersKt.reportOn$default(reporter, returnTypeRef2.getSource(), FirErrors.INSTANCE.getTYPE_ARGUMENT_ON_TYPED_VALUE_CLASS_EQUALS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                            break;
                        }
                    }
                }
                if (firSimpleFunction2 == null || firSimpleFunction3 != null) {
                    return;
                }
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, firSimpleFunction2.getSource(), FirErrors.INSTANCE.getINEFFICIENT_EQUALS_OVERRIDING_IN_VALUE_CLASS(), ConeTypeUtilsKt.replaceArgumentsWithStarProjections(ScopeUtilsKt.defaultType(declaration)), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
            }
        }
    }

    private final boolean isRelatedToParameter(FirProperty firProperty, FirValueParameter firValueParameter) {
        if (Intrinsics.areEqual(firProperty.getName(), firValueParameter != null ? firValueParameter.getName() : null)) {
            KtSourceElement source = firProperty.getSource();
            if ((source != null ? source.getKind() : null) instanceof KtFakeSourceElementKind) {
                return true;
            }
        }
        return false;
    }

    private final boolean isNotFinalReadOnly(FirValueParameter firValueParameter, FirProperty firProperty) {
        if (firProperty == null) {
            return true;
        }
        KtModifierKeywordToken OPEN_KEYWORD = KtTokens.OPEN_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(OPEN_KEYWORD, "OPEN_KEYWORD");
        return firValueParameter.getIsVararg() || !firProperty.getIsVal() || FirKeywordUtilsKt.hasModifier(firValueParameter, OPEN_KEYWORD);
    }

    private final boolean isInapplicableParameterType(FirTypeRef firTypeRef, FirSession firSession) {
        ConeKotlinType coneKotlinTypeFullyExpandedType = TypeExpansionUtilsKt.fullyExpandedType(FirTypeUtilsKt.getConeType(firTypeRef), firSession);
        return ConeBuiltinTypeUtilsKt.isUnit(coneKotlinTypeFullyExpandedType) || ConeBuiltinTypeUtilsKt.isNothing(coneKotlinTypeFullyExpandedType);
    }

    private final boolean isGenericArrayOfTypeParameter(ConeKotlinType coneKotlinType) {
        ConeKotlinType coneKotlinTypeArrayElementType;
        ConeKotlinType type;
        if ((ArraysKt.firstOrNull(coneKotlinType.getTypeArguments()) instanceof ConeStarProjection) || !ArrayUtilsKt.isPotentiallyArray(coneKotlinType) || (coneKotlinTypeArrayElementType = ArrayUtilsKt.arrayElementType(coneKotlinType)) == null || (type = coneKotlinTypeArrayElementType.getType()) == null) {
            return false;
        }
        return (type instanceof ConeTypeParameterType) || isGenericArrayOfTypeParameter(type);
    }

    private final boolean isSubtypeOfCloneable(FirRegularClass firRegularClass, FirSession firSession) {
        ConeClassLikeType coneClassLikeTypeFullyExpandedType$default;
        ConeClassLikeLookupTag lookupTag;
        ClassId classId;
        if (isCloneableId(FirDeclarationUtilKt.getClassId(firRegularClass))) {
            return true;
        }
        List<ConeClassLikeType> listLookupSuperTypes$default = SupertypeUtilsKt.lookupSuperTypes$default((FirClass) firRegularClass, true, true, firSession, false, (SupertypeSupplier) null, 32, (Object) null);
        if (!(listLookupSuperTypes$default instanceof Collection) || !listLookupSuperTypes$default.isEmpty()) {
            for (ConeClassLikeType coneClassLikeType : listLookupSuperTypes$default) {
                if (!(coneClassLikeType instanceof ConeClassLikeType)) {
                    coneClassLikeType = null;
                }
                if (coneClassLikeType != null && (coneClassLikeTypeFullyExpandedType$default = TypeExpansionUtilsKt.fullyExpandedType$default(coneClassLikeType, firSession, null, 2, null)) != null && (lookupTag = coneClassLikeTypeFullyExpandedType$default.getLookupTag()) != null && (classId = lookupTag.getClassId()) != null && INSTANCE.isCloneableId(classId)) {
                    return true;
                }
            }
        }
        return false;
    }

    private final boolean isCloneableId(ClassId classId) {
        return Intrinsics.areEqual(classId.getRelativeClassName(), cloneableFqName) && (Intrinsics.areEqual(classId.getPackageFqName(), StandardClassIds.INSTANCE.getBASE_KOTLIN_PACKAGE()) || Intrinsics.areEqual(classId.getPackageFqName(), javaLangFqName));
    }
}
