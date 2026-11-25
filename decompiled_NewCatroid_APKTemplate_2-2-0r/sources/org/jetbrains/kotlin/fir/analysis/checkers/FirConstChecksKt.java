package org.jetbrains.kotlin.fir.analysis.checkers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SpreadBuilder;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.expressions.ExhaustivenessStatusKt;
import org.jetbrains.kotlin.fir.expressions.FirCall;
import org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess;
import org.jetbrains.kotlin.fir.expressions.FirComparisonExpression;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirEqualityOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirGetClassCall;
import org.jetbrains.kotlin.fir.expressions.FirNamedArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirOperation;
import org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirStringConcatenationCall;
import org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression;
import org.jetbrains.kotlin.fir.expressions.FirTypeOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirWhenBranch;
import org.jetbrains.kotlin.fir.expressions.FirWhenExpression;
import org.jetbrains.kotlin.fir.expressions.impl.FirNoReceiverExpression;
import org.jetbrains.kotlin.fir.references.FirErrorNamedReference;
import org.jetbrains.kotlin.fir.references.FirNamedReference;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassifierLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirEnumEntrySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFieldSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeLookupTagBasedType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FunctionalTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.InferenceUtilsKt;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: FirConstChecks.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000R\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001a\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u0013H\u0000\u001a\n\u0010\u0014\u001a\u00020\b*\u00020\u0015\u001a\u0012\u0010\u0016\u001a\u00020\b*\b\u0012\u0002\b\u0003\u0018\u00010\u0017H\u0002\u001a \u0010\u0018\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0019*\b\u0012\u0002\b\u0003\u0018\u00010\u00172\u0006\u0010\u0012\u001a\u00020\u0013H\u0002\u001a\f\u0010\u001a\u001a\u00020\b*\u00020\u001bH\u0002\u001a\f\u0010\u001c\u001a\u00020\b*\u00020\u001dH\u0002\u001a\u0014\u0010\u001e\u001a\u00020\b*\u00020\t2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002\"\u001e\u0010\u0000\u001a\u0012\u0012\u0004\u0012\u00020\u00020\u0001j\b\u0012\u0004\u0012\u00020\u0002`\u0003X\u0082\u0004¢\u0006\u0002\n\u0000\"\u001e\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u00020\u0001j\b\u0012\u0004\u0012\u00020\u0002`\u0003X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0018\u0010\u0007\u001a\u00020\b*\u00020\t8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\n\"\u001e\u0010\u000b\u001a\u00020\b*\u00020\t8BX\u0082\u0004¢\u0006\f\u0012\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\n¨\u0006\u001f"}, d2 = {"compileTimeConversionFunctions", "Ljava/util/HashSet;", "Lorg/jetbrains/kotlin/name/Name;", "Lkotlin/collections/HashSet;", "compileTimeExtensionFunctions", "compileTimeFunctions", "", "isComplexBooleanConstant", "", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)Z", "usesVariableAsConstant", "getUsesVariableAsConstant$annotations", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", "getUsesVariableAsConstant", "checkConstantArguments", "Lorg/jetbrains/kotlin/fir/analysis/checkers/ConstantArgumentKind;", "expression", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "canBeUsedForConstVal", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "fromKotlin", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "getReferencedClassSymbol", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "isCompileTimeBuiltinCall", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "isCompileTimeBuiltinProperty", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "isForbiddenComplexConstant", "checkers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirConstChecksKt {
    private static final HashSet<Name> compileTimeConversionFunctions;
    private static final HashSet<Name> compileTimeExtensionFunctions;
    private static final Set<Name> compileTimeFunctions;

    public static final boolean canBeUsedForConstVal(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        ConeSimpleKotlinType coneSimpleKotlinTypeLowerBoundIfFlexible = ConeTypeUtilsKt.lowerBoundIfFlexible(coneKotlinType);
        return ConeBuiltinTypeUtilsKt.isPrimitive(coneSimpleKotlinTypeLowerBoundIfFlexible) || ConeBuiltinTypeUtilsKt.isString(coneSimpleKotlinTypeLowerBoundIfFlexible) || ConeBuiltinTypeUtilsKt.isUnsignedType(coneSimpleKotlinTypeLowerBoundIfFlexible);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final ConstantArgumentKind checkConstantArguments(FirExpression expression, FirSession session) {
        boolean z;
        FqName className;
        ConeKotlinType type;
        FirExpression firExpression;
        FirTypeRef typeRef;
        ConstantArgumentKind constantArgumentKindCheckConstantArguments;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(session, "session");
        FirReference reference = FirExpressionUtilKt.toReference(expression);
        Object obj = null;
        FirCallableSymbol<?> resolvedCallableSymbol = reference != null ? FirReferenceUtilsKt.toResolvedCallableSymbol(reference, true) : null;
        FirBasedSymbol<?> referencedClassSymbol = resolvedCallableSymbol != null ? getReferencedClassSymbol(resolvedCallableSymbol, session) : null;
        FirRegularClassSymbol firRegularClassSymbol = referencedClassSymbol instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) referencedClassSymbol : null;
        ClassKind classKind = firRegularClassSymbol != null ? firRegularClassSymbol.getClassKind() : null;
        boolean zSupportsFeature = FirLanguageSettingsComponentKt.getLanguageVersionSettings(session).supportsFeature(LanguageFeature.IntrinsicConstEvaluation);
        if (expression instanceof FirNamedArgumentExpression) {
            checkConstantArguments(((FirNamedArgumentExpression) expression).getExpression(), session);
        } else if (!(expression instanceof FirTypeOperatorCall)) {
            if (expression instanceof FirWhenExpression) {
                FirWhenExpression firWhenExpression = (FirWhenExpression) expression;
                if (!ExhaustivenessStatusKt.isProperlyExhaustive(firWhenExpression) || !zSupportsFeature) {
                    return ConstantArgumentKind.NOT_CONST;
                }
                FirExpression subject = firWhenExpression.getSubject();
                if (subject != null && (constantArgumentKindCheckConstantArguments = checkConstantArguments(subject, session)) != null) {
                    return constantArgumentKindCheckConstantArguments;
                }
                for (FirWhenBranch firWhenBranch : firWhenExpression.getBranches()) {
                    ConstantArgumentKind constantArgumentKindCheckConstantArguments2 = checkConstantArguments(firWhenBranch.getCondition(), session);
                    if (constantArgumentKindCheckConstantArguments2 != null) {
                        return constantArgumentKindCheckConstantArguments2;
                    }
                    for (FirStatement firStatement : firWhenBranch.getResult().getStatements()) {
                        if (!(firStatement instanceof FirExpression)) {
                            return ConstantArgumentKind.NOT_CONST;
                        }
                        ConstantArgumentKind constantArgumentKindCheckConstantArguments3 = checkConstantArguments((FirExpression) firStatement, session);
                        if (constantArgumentKindCheckConstantArguments3 != null) {
                            return constantArgumentKindCheckConstantArguments3;
                        }
                    }
                }
                return null;
            }
            if (!(expression instanceof FirConstExpression) && !(resolvedCallableSymbol instanceof FirEnumEntrySymbol) && ((resolvedCallableSymbol == null || !resolvedCallableSymbol.getRawStatus().isConst()) && (!((z = resolvedCallableSymbol instanceof FirConstructorSymbol)) || classKind != ClassKind.ANNOTATION_CLASS))) {
                if (classKind == ClassKind.ENUM_CLASS) {
                    return ConstantArgumentKind.ENUM_NOT_CONST;
                }
                if (expression instanceof FirComparisonExpression) {
                    return checkConstantArguments(((FirComparisonExpression) expression).getCompareToCall(), session);
                }
                if ((expression instanceof FirStringConcatenationCall) || (expression instanceof FirEqualityOperatorCall)) {
                    for (FirExpression firExpression2 : ((FirCall) expression).getArgumentList().getArguments()) {
                        if ((firExpression2 instanceof FirResolvedQualifier) || isForbiddenComplexConstant(expression, session)) {
                            return ConstantArgumentKind.NOT_CONST;
                        }
                        ConstantArgumentKind constantArgumentKindCheckConstantArguments4 = checkConstantArguments(firExpression2, session);
                        if (constantArgumentKindCheckConstantArguments4 != null) {
                            return constantArgumentKindCheckConstantArguments4;
                        }
                    }
                } else {
                    if (expression instanceof FirGetClassCall) {
                        FirCall firCall = expression instanceof FirCall ? (FirCall) expression : null;
                        ConeKotlinType coneType = (firCall == null || (firExpression = (FirExpression) CollectionsKt.first((List) firCall.getArgumentList().getArguments())) == null || (typeRef = firExpression.getTypeRef()) == null) ? null : FirTypeUtilsKt.getConeType(typeRef);
                        if (coneType instanceof ConeErrorType) {
                            return ConstantArgumentKind.NOT_CONST;
                        }
                        while (true) {
                            if (!Intrinsics.areEqual(coneType != null ? ConeTypeUtilsKt.getClassId(coneType) : null, StandardClassIds.INSTANCE.getArray())) {
                                break;
                            }
                            Object objFirst = ArraysKt.first(ConeTypeUtilsKt.lowerBoundIfFlexible(coneType).getTypeArguments());
                            ConeKotlinTypeProjection coneKotlinTypeProjection = objFirst instanceof ConeKotlinTypeProjection ? (ConeKotlinTypeProjection) objFirst : null;
                            if (coneKotlinTypeProjection == null || (type = coneKotlinTypeProjection.getType()) == null) {
                                break;
                            }
                            coneType = type;
                        }
                        if (coneType instanceof ConeTypeParameterType) {
                            return ConstantArgumentKind.KCLASS_LITERAL_OF_TYPE_PARAMETER_ERROR;
                        }
                        if (((FirExpression) CollectionsKt.first((List) ((FirCall) expression).getArgumentList().getArguments())) instanceof FirResolvedQualifier) {
                            return null;
                        }
                        return ConstantArgumentKind.NOT_KCLASS_LITERAL;
                    }
                    if (resolvedCallableSymbol != null) {
                        if (resolvedCallableSymbol instanceof FirFieldSymbol) {
                            if (!resolvedCallableSymbol.getRawStatus().isStatic() || resolvedCallableSymbol.getResolvedStatus().getModality() != Modality.FINAL) {
                                return ConstantArgumentKind.NOT_CONST;
                            }
                        } else if (z) {
                            if (expression instanceof FirCallableReferenceAccess) {
                                return null;
                            }
                            if (!ConeBuiltinTypeUtilsKt.isUnsignedType(FirTypeUtilsKt.getConeType(expression.getTypeRef()))) {
                                return ConstantArgumentKind.NOT_CONST;
                            }
                            Iterator<T> it = ((FirFunctionCall) expression).getArgumentList().getArguments().iterator();
                            while (it.hasNext()) {
                                ConstantArgumentKind constantArgumentKindCheckConstantArguments5 = checkConstantArguments((FirExpression) it.next(), session);
                                if (constantArgumentKindCheckConstantArguments5 != null) {
                                    return constantArgumentKindCheckConstantArguments5;
                                }
                            }
                        } else {
                            if (expression instanceof FirFunctionCall) {
                                FirFunctionCall firFunctionCall = (FirFunctionCall) expression;
                                FirNamedReference calleeReference = firFunctionCall.getCalleeReference();
                                if (calleeReference instanceof FirErrorNamedReference) {
                                    return null;
                                }
                                if (Intrinsics.areEqual(ConeTypeUtilsKt.getClassId(FirTypeUtilsKt.getConeType(expression.getTypeRef())), StandardClassIds.INSTANCE.getKClass())) {
                                    return ConstantArgumentKind.NOT_KCLASS_LITERAL;
                                }
                                if (firFunctionCall.getDispatchReceiver() instanceof FirThisReceiverExpression) {
                                    return null;
                                }
                                if (!(calleeReference instanceof FirResolvedNamedReference)) {
                                    return ConstantArgumentKind.NOT_CONST;
                                }
                                FirBasedSymbol<?> resolvedSymbol = ((FirResolvedNamedReference) calleeReference).getResolvedSymbol();
                                FirNamedFunctionSymbol firNamedFunctionSymbol = resolvedSymbol instanceof FirNamedFunctionSymbol ? (FirNamedFunctionSymbol) resolvedSymbol : null;
                                if (firNamedFunctionSymbol == null) {
                                    return ConstantArgumentKind.NOT_CONST;
                                }
                                if ((!checkConstantArguments$canBeEvaluated(firNamedFunctionSymbol, zSupportsFeature, session) && !isCompileTimeBuiltinCall(firFunctionCall)) || isForbiddenComplexConstant(expression, session)) {
                                    return ConstantArgumentKind.NOT_CONST;
                                }
                                for (FirExpression firExpression3 : CollectionsKt.plus((Collection<? extends FirExpression>) CollectionsKt.plus((Collection<? extends FirExpression>) ((FirCall) expression).getArgumentList().getArguments(), firFunctionCall.getDispatchReceiver()), firFunctionCall.getExtensionReceiver())) {
                                    if (!(firExpression3 instanceof FirNoReceiverExpression)) {
                                        if (!CollectionsKt.contains(StandardClassIds.INSTANCE.getConstantAllowedTypes(), ConeTypeUtilsKt.getClassId(ConeTypeUtilsKt.lowerBoundIfFlexible(FirTypeUtilsKt.getConeType(firExpression3.getTypeRef()))))) {
                                            return ConstantArgumentKind.NOT_CONST;
                                        }
                                        ConstantArgumentKind constantArgumentKindCheckConstantArguments6 = checkConstantArguments(firExpression3, session);
                                        if (constantArgumentKindCheckConstantArguments6 != null) {
                                            return constantArgumentKindCheckConstantArguments6;
                                        }
                                    }
                                }
                                return null;
                            }
                            if (expression instanceof FirQualifiedAccessExpression) {
                                ConeKotlinType coneType2 = FirTypeUtilsKt.getConeType(expression.getTypeRef());
                                if (FunctionalTypeUtilsKt.isReflectFunctionType(coneType2, session) || InferenceUtilsKt.isKProperty(coneType2, session) || InferenceUtilsKt.isKMutableProperty(coneType2, session)) {
                                    return checkConstantArguments(((FirQualifiedAccessExpression) expression).getDispatchReceiver(), session);
                                }
                                FirPropertySymbol firPropertySymbol = resolvedCallableSymbol instanceof FirPropertySymbol ? (FirPropertySymbol) resolvedCallableSymbol : null;
                                if (firPropertySymbol == null) {
                                    return ConstantArgumentKind.NOT_CONST;
                                }
                                FirProperty firProperty = (FirProperty) firPropertySymbol.getFir();
                                FirProperty firProperty2 = firProperty;
                                while (true) {
                                    FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firProperty2) || (firProperty2.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firProperty2) : null;
                                    if (originalForSubstitutionOverrideAttr == null) {
                                        originalForSubstitutionOverrideAttr = ClassMembersKt.isIntersectionOverride(firProperty2) ? ClassMembersKt.getOriginalForIntersectionOverrideAttr(firProperty2) : null;
                                    }
                                    if (originalForSubstitutionOverrideAttr == null) {
                                        break;
                                    }
                                    firProperty2 = originalForSubstitutionOverrideAttr;
                                }
                                if (!checkConstantArguments$canBeEvaluated(((FirProperty) firProperty2).getSymbol(), zSupportsFeature, session) && !isCompileTimeBuiltinProperty(firProperty)) {
                                    if (firPropertySymbol.isLocal() || ((className = firPropertySymbol.getCallableId().getClassName()) != null && !className.isRoot())) {
                                        return ConstantArgumentKind.NOT_CONST;
                                    }
                                    if (Intrinsics.areEqual(ConeTypeUtilsKt.getClassId(coneType2), StandardClassIds.INSTANCE.getKClass())) {
                                        return ConstantArgumentKind.NOT_KCLASS_LITERAL;
                                    }
                                    if (((FirQualifiedAccessExpression) expression).getDispatchReceiver() instanceof FirThisReceiverExpression) {
                                        return null;
                                    }
                                    FirExpression initializer = firProperty.getInitializer();
                                    if (initializer instanceof FirConstExpression) {
                                        return firProperty.getIsVal() ? ConstantArgumentKind.NOT_CONST_VAL_IN_CONST_EXPRESSION : ConstantArgumentKind.NOT_CONST;
                                    }
                                    return initializer instanceof FirGetClassCall ? ConstantArgumentKind.NOT_KCLASS_LITERAL : ConstantArgumentKind.NOT_CONST;
                                }
                                FirQualifiedAccessExpression firQualifiedAccessExpression = (FirQualifiedAccessExpression) expression;
                                boolean z2 = false;
                                for (Object obj2 : CollectionsKt.listOf((Object[]) new FirExpression[]{firQualifiedAccessExpression.getDispatchReceiver(), firQualifiedAccessExpression.getExtensionReceiver()})) {
                                    if (!Intrinsics.areEqual((FirExpression) obj2, FirNoReceiverExpression.INSTANCE)) {
                                        if (z2) {
                                            throw new IllegalArgumentException("Collection contains more than one matching element.");
                                        }
                                        obj = obj2;
                                        z2 = true;
                                    }
                                }
                                if (z2) {
                                    return checkConstantArguments((FirExpression) obj, session);
                                }
                                throw new NoSuchElementException("Collection contains no element matching the predicate.");
                            }
                            return ConstantArgumentKind.NOT_CONST;
                        }
                    }
                }
            }
        } else if (((FirTypeOperatorCall) expression).getOperation() == FirOperation.AS) {
            return ConstantArgumentKind.NOT_CONST;
        }
        return null;
    }

    private static final boolean checkConstantArguments$canBeEvaluated(FirBasedSymbol<?> firBasedSymbol, boolean z, FirSession firSession) {
        return z && FirAnnotationUtilsKt.hasAnnotation(firBasedSymbol, StandardClassIds.Annotations.INSTANCE.getIntrinsicConstEvaluation(), firSession);
    }

    private static final boolean isForbiddenComplexConstant(FirExpression firExpression, FirSession firSession) {
        return isComplexBooleanConstant(firExpression) && FirLanguageSettingsComponentKt.getLanguageVersionSettings(firSession).supportsFeature(LanguageFeature.ProhibitSimplificationOfNonTrivialConstBooleanExpressions);
    }

    private static final boolean isComplexBooleanConstant(FirExpression firExpression) {
        return (!ConeBuiltinTypeUtilsKt.isBoolean(FirTypeUtilsKt.getConeType(firExpression.getTypeRef())) || (firExpression instanceof FirConstExpression) || getUsesVariableAsConstant(firExpression)) ? false : true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final boolean getUsesVariableAsConstant(FirExpression firExpression) {
        FirExpression explicitReceiver;
        FirCallableSymbol<?> resolvedCallableSymbol;
        if ((firExpression instanceof FirPropertyAccessExpression) && (resolvedCallableSymbol = FirExpressionUtilKt.toResolvedCallableSymbol(firExpression)) != null && resolvedCallableSymbol.getRawStatus().isConst()) {
            return true;
        }
        if ((firExpression instanceof FirQualifiedAccessExpression) && ((explicitReceiver = ((FirQualifiedAccessExpression) firExpression).getExplicitReceiver()) == null || getUsesVariableAsConstant(explicitReceiver))) {
            return true;
        }
        if (firExpression instanceof FirCall) {
            List<FirExpression> arguments = ((FirCall) firExpression).getArgumentList().getArguments();
            if (!(arguments instanceof Collection) || !arguments.isEmpty()) {
                Iterator<T> it = arguments.iterator();
                while (it.hasNext()) {
                    if (getUsesVariableAsConstant((FirExpression) it.next())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    static {
        SpreadBuilder spreadBuilder = new SpreadBuilder(9);
        spreadBuilder.addSpread(OperatorNameConventions.BINARY_OPERATION_NAMES.toArray(new Name[0]));
        spreadBuilder.addSpread(OperatorNameConventions.UNARY_OPERATION_NAMES.toArray(new Name[0]));
        spreadBuilder.add(OperatorNameConventions.SHL);
        spreadBuilder.add(OperatorNameConventions.SHR);
        spreadBuilder.add(OperatorNameConventions.USHR);
        spreadBuilder.add(OperatorNameConventions.OR);
        spreadBuilder.add(OperatorNameConventions.AND);
        spreadBuilder.add(OperatorNameConventions.XOR);
        spreadBuilder.add(OperatorNameConventions.COMPARE_TO);
        compileTimeFunctions = SetsKt.setOf(spreadBuilder.toArray(new Name[spreadBuilder.size()]));
        List listListOf = CollectionsKt.listOf((Object[]) new String[]{"floorDiv", "mod", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY});
        HashSet<Name> hashSet = new HashSet<>();
        Iterator it = listListOf.iterator();
        while (it.hasNext()) {
            hashSet.add(Name.identifier((String) it.next()));
        }
        compileTimeExtensionFunctions = hashSet;
        List listListOf2 = CollectionsKt.listOf((Object[]) new String[]{"toInt", "toLong", "toShort", "toByte", "toFloat", "toDouble", "toChar", "toBoolean"});
        HashSet<Name> hashSet2 = new HashSet<>();
        Iterator it2 = listListOf2.iterator();
        while (it2.hasNext()) {
            hashSet2.add(Name.identifier((String) it2.next()));
        }
        compileTimeConversionFunctions = hashSet2;
    }

    private static final boolean isCompileTimeBuiltinCall(FirFunctionCall firFunctionCall) {
        ConeSimpleKotlinType coneSimpleKotlinTypeLowerBoundIfFlexible;
        FirNamedReference calleeReference = firFunctionCall.getCalleeReference();
        if (!(calleeReference instanceof FirResolvedNamedReference)) {
            return false;
        }
        Name name2 = calleeReference.getName();
        FirBasedSymbol<?> resolvedSymbol = ((FirResolvedNamedReference) calleeReference).getResolvedSymbol();
        ClassId classId = null;
        if (!fromKotlin(resolvedSymbol instanceof FirCallableSymbol ? (FirCallableSymbol) resolvedSymbol : null)) {
            return false;
        }
        FirTypeRef typeRef = firFunctionCall.getDispatchReceiver().getTypeRef();
        FirResolvedTypeRef firResolvedTypeRef = typeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) typeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        if (!(type instanceof ConeKotlinType)) {
            type = null;
        }
        if (type != null && (coneSimpleKotlinTypeLowerBoundIfFlexible = ConeTypeUtilsKt.lowerBoundIfFlexible(type)) != null) {
            classId = ConeTypeUtilsKt.getClassId(coneSimpleKotlinTypeLowerBoundIfFlexible);
        }
        if (CollectionsKt.contains(StandardClassIds.INSTANCE.getUnsignedTypes(), classId)) {
            return false;
        }
        if (compileTimeFunctions.contains(name2) || compileTimeExtensionFunctions.contains(name2) || Intrinsics.areEqual(name2, OperatorNameConventions.TO_STRING) || compileTimeConversionFunctions.contains(name2)) {
            return true;
        }
        return Intrinsics.areEqual(calleeReference.getName(), OperatorNameConventions.GET) && Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getString());
    }

    private static final boolean isCompileTimeBuiltinProperty(FirProperty firProperty) {
        ConeSimpleKotlinType coneSimpleKotlinType;
        FirTypeRef typeRef;
        ConeSimpleKotlinType dispatchReceiverType = firProperty.getDispatchReceiverType();
        if (dispatchReceiverType != null) {
            coneSimpleKotlinType = dispatchReceiverType;
        } else {
            FirReceiverParameter receiverParameter = firProperty.getReceiverParameter();
            ConeKotlinType coneKotlinType = null;
            if (receiverParameter != null && (typeRef = receiverParameter.getTypeRef()) != null) {
                FirResolvedTypeRef firResolvedTypeRef = typeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) typeRef : null;
                ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
                if (type instanceof ConeKotlinType) {
                    coneKotlinType = type;
                }
            }
            coneSimpleKotlinType = coneKotlinType;
            if (coneSimpleKotlinType == null) {
                return false;
            }
        }
        ClassId classId = ConeTypeUtilsKt.getClassId(ConeTypeUtilsKt.lowerBoundIfFlexible(coneSimpleKotlinType));
        if (classId == null) {
            return false;
        }
        String strAsString = firProperty.getName().asString();
        int iHashCode = strAsString.hashCode();
        if (iHashCode == -1106363674) {
            if (strAsString.equals("length")) {
                return Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getString());
            }
            return false;
        }
        if (iHashCode == 3059181 && strAsString.equals(ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY)) {
            return Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getChar());
        }
        return false;
    }

    private static final boolean fromKotlin(FirCallableSymbol<?> firCallableSymbol) {
        CallableId callableId;
        FqName packageName;
        return Intrinsics.areEqual((firCallableSymbol == null || (callableId = firCallableSymbol.getCallableId()) == null || (packageName = callableId.getPackageName()) == null) ? null : packageName.asString(), IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME);
    }

    private static final FirBasedSymbol<?> getReferencedClassSymbol(FirCallableSymbol<?> firCallableSymbol, FirSession firSession) {
        FirResolvedTypeRef resolvedReturnTypeRef;
        ConeClassifierLookupTag lookupTag;
        FirClassifierSymbol<?> symbol = null;
        if (firCallableSymbol != null && (resolvedReturnTypeRef = firCallableSymbol.getResolvedReturnTypeRef()) != null) {
            FirResolvedTypeRef firResolvedTypeRef = resolvedReturnTypeRef;
            ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
            if (!(type instanceof ConeLookupTagBasedType)) {
                type = null;
            }
            ConeLookupTagBasedType coneLookupTagBasedType = (ConeLookupTagBasedType) type;
            if (coneLookupTagBasedType != null && (lookupTag = coneLookupTagBasedType.getLookupTag()) != null) {
                symbol = LookupTagUtilsKt.toSymbol(lookupTag, firSession);
            }
        }
        return symbol;
    }
}
