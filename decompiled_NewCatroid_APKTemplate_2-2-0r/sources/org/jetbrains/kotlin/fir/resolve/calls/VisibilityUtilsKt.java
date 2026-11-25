package org.jetbrains.kotlin.fir.resolve.calls;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirVisibilityChecker;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.utils.DeclarationAttributesKt;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression;
import org.jetbrains.kotlin.fir.expressions.FirVariableAssignment;
import org.jetbrains.kotlin.fir.expressions.builder.FirSmartCastExpressionBuilder;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirThisReference;
import org.jetbrains.kotlin.fir.symbols.impl.FirAnonymousObjectSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeInferenceContext;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;

/* JADX INFO: compiled from: VisibilityUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\u0002\u001a\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u00032\b\u0010\u0005\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u0002\u001a\u000e\u0010\b\u001a\u0004\u0018\u00010\t*\u00020\nH\u0002\u001a.\u0010\u000b\u001a\u00020\u0001*\u00020\f2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0005\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0010\u001a\u00020\u0001\u001a$\u0010\u000b\u001a\u00020\u0001*\u00020\f2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\u00122\b\b\u0002\u0010\u0010\u001a\u00020\u0001¨\u0006\u0013"}, d2 = {"isExplicitReceiverExpression", "", "receiverExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "removeSmartCastTypeForAttemptToFitVisibility", "dispatchReceiver", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "getBackingFieldIfApplicable", "Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;", "isVisible", "Lorg/jetbrains/kotlin/fir/FirVisibilityChecker;", "declaration", "callInfo", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "skipCheckForContainingClassVisibility", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class VisibilityUtilsKt {
    public static /* synthetic */ boolean isVisible$default(FirVisibilityChecker firVisibilityChecker, FirMemberDeclaration firMemberDeclaration, CallInfo callInfo, FirExpression firExpression, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = false;
        }
        return isVisible(firVisibilityChecker, firMemberDeclaration, callInfo, firExpression, z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final boolean isVisible(FirVisibilityChecker firVisibilityChecker, FirMemberDeclaration declaration, CallInfo callInfo, FirExpression firExpression, boolean z) {
        Intrinsics.checkNotNullParameter(firVisibilityChecker, "<this>");
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(callInfo, "callInfo");
        FirRegularClass firRegularClass = null;
        if ((declaration instanceof FirCallableDeclaration) && declaration.getStatus().isStatic() && isExplicitReceiverExpression(firExpression)) {
            FirResolvedQualifier firResolvedQualifier = firExpression instanceof FirResolvedQualifier ? (FirResolvedQualifier) firExpression : null;
            FirClassLikeSymbol<?> symbol = firResolvedQualifier != null ? firResolvedQualifier.getSymbol() : null;
            if (symbol instanceof FirRegularClassSymbol) {
                firRegularClass = (FirRegularClass) ((FirRegularClassSymbol) symbol).getFir();
            } else if (symbol instanceof FirTypeAliasSymbol) {
                FirRegularClassSymbol firRegularClassSymbolFullyExpandedClass = DeclarationUtilsKt.fullyExpandedClass(symbol, callInfo.getSession());
                if (firRegularClassSymbolFullyExpandedClass != null) {
                    firRegularClass = (FirRegularClass) firRegularClassSymbolFullyExpandedClass.getFir();
                }
            } else if (!(symbol instanceof FirAnonymousObjectSymbol) && symbol != null) {
                throw new NoWhenBranchMatchedException();
            }
        }
        return FirVisibilityChecker.isVisible$default(firVisibilityChecker, declaration, callInfo.getSession(), callInfo.getContainingFile(), callInfo.getContainingDeclarations(), firExpression, callInfo.getCallSite() instanceof FirVariableAssignment, firRegularClass, z, null, 256, null);
    }

    public static /* synthetic */ boolean isVisible$default(FirVisibilityChecker firVisibilityChecker, FirMemberDeclaration firMemberDeclaration, Candidate candidate, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return isVisible(firVisibilityChecker, firMemberDeclaration, candidate, z);
    }

    public static final boolean isVisible(FirVisibilityChecker firVisibilityChecker, FirMemberDeclaration declaration, Candidate candidate, boolean z) {
        Intrinsics.checkNotNullParameter(firVisibilityChecker, "<this>");
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        CallInfo callInfo = candidate.getCallInfo();
        if (!isVisible(firVisibilityChecker, declaration, callInfo, candidate.getDispatchReceiver(), z)) {
            FirExpression firExpressionRemoveSmartCastTypeForAttemptToFitVisibility = removeSmartCastTypeForAttemptToFitVisibility(candidate.getDispatchReceiver(), candidate.getCallInfo().getSession());
            if (firExpressionRemoveSmartCastTypeForAttemptToFitVisibility == null || !isVisible(firVisibilityChecker, declaration, callInfo, firExpressionRemoveSmartCastTypeForAttemptToFitVisibility, z)) {
                return false;
            }
            candidate.setDispatchReceiver(firExpressionRemoveSmartCastTypeForAttemptToFitVisibility);
        }
        FirBackingField backingFieldIfApplicable = getBackingFieldIfApplicable(declaration);
        if (backingFieldIfApplicable == null) {
            return true;
        }
        candidate.setHasVisibleBackingField(isVisible(firVisibilityChecker, backingFieldIfApplicable, callInfo, candidate.getDispatchReceiver(), z));
        return true;
    }

    private static final FirExpression removeSmartCastTypeForAttemptToFitVisibility(FirExpression firExpression, FirSession firSession) {
        FirSmartCastExpression firSmartCastExpression = firExpression instanceof FirSmartCastExpression ? (FirSmartCastExpression) firExpression : null;
        if (firSmartCastExpression != null) {
            if (!firSmartCastExpression.isStable()) {
                firSmartCastExpression = null;
            }
            if (firSmartCastExpression != null) {
                ConeKotlinType coneType = FirTypeUtilsKt.getConeType(((FirSmartCastExpression) firExpression).getTypeRef());
                if (ConeBuiltinTypeUtilsKt.isNullableNothing(coneType)) {
                    return null;
                }
                FirExpression originalExpression = firSmartCastExpression.getOriginalExpression();
                ConeKotlinType coneType2 = FirTypeUtilsKt.getConeType(originalExpression.getTypeRef());
                ConeKotlinType coneKotlinTypeMakeConeTypeDefinitelyNotNullOrNotNull$default = TypeUtilsKt.makeConeTypeDefinitelyNotNullOrNotNull$default(coneType2, TypeComponentsKt.getTypeContext(firSession), false, 2, null);
                if (Intrinsics.areEqual(coneKotlinTypeMakeConeTypeDefinitelyNotNullOrNotNull$default, coneType)) {
                    return null;
                }
                ConeInferenceContext typeContext = TypeComponentsKt.getTypeContext(firSession);
                if (!typeContext.isNullableType(coneType2) || typeContext.isNullableType(coneType)) {
                    return originalExpression;
                }
                FirSmartCastExpressionBuilder firSmartCastExpressionBuilder = new FirSmartCastExpressionBuilder();
                firSmartCastExpressionBuilder.setOriginalExpression(originalExpression);
                FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
                KtSourceElement source = originalExpression.getTypeRef().getSource();
                firResolvedTypeRefBuilder.setSource(source != null ? KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.SmartCastedTypeRef.INSTANCE, 0, 0, 6, null) : null);
                firResolvedTypeRefBuilder.setType(coneKotlinTypeMakeConeTypeDefinitelyNotNullOrNotNull$default);
                firSmartCastExpressionBuilder.setSmartcastType(firResolvedTypeRefBuilder.mo6809build());
                firSmartCastExpressionBuilder.setTypesFromSmartCast(CollectionsKt.listOf(coneKotlinTypeMakeConeTypeDefinitelyNotNullOrNotNull$default));
                firSmartCastExpressionBuilder.setSmartcastStability(firSmartCastExpression.getSmartcastStability());
                firSmartCastExpressionBuilder.setTypeRef(UtilsKt.copyWithNewSourceKind(firSmartCastExpressionBuilder.getSmartcastType(), KtFakeSourceElementKind.ImplicitTypeRef.INSTANCE));
                return firSmartCastExpressionBuilder.mo6809build();
            }
        }
        return null;
    }

    private static final FirBackingField getBackingFieldIfApplicable(FirMemberDeclaration firMemberDeclaration) {
        FirBackingField explicitBackingField;
        FirProperty firProperty = firMemberDeclaration instanceof FirProperty ? (FirProperty) firMemberDeclaration : null;
        if (firProperty == null || (explicitBackingField = DeclarationAttributesKt.getExplicitBackingField(firProperty)) == null) {
            return null;
        }
        Visibility visibility = explicitBackingField.getStatus().getVisibility();
        if (Intrinsics.areEqual(visibility, Visibilities.PrivateToThis.INSTANCE) ? true : Intrinsics.areEqual(visibility, Visibilities.Private.INSTANCE) ? true : Intrinsics.areEqual(visibility, Visibilities.Internal.INSTANCE)) {
            return explicitBackingField;
        }
        return null;
    }

    private static final boolean isExplicitReceiverExpression(FirExpression firExpression) {
        if (firExpression == null) {
            return false;
        }
        FirReference reference = FirExpressionUtilKt.toReference(firExpression);
        FirThisReference firThisReference = reference instanceof FirThisReference ? (FirThisReference) reference : null;
        if (firThisReference == null) {
            return true;
        }
        return !firThisReference.getIsImplicit();
    }
}
