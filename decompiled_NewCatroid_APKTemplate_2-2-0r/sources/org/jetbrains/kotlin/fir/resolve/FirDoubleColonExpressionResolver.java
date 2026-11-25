package org.jetbrains.kotlin.fir.resolve;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.references.FirNamedReference;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjectionIn;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjectionOut;
import org.jetbrains.kotlin.fir.types.ConeStarProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeProjectionWithVariance;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.impl.ConeClassLikeTypeImpl;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: FirDoubleColonExpressionResolver.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0017\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0000¢\u0006\u0002\b\tJ\u0012\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0012\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\f\u001a\u00020\bH\u0002J\u0010\u0010\u0012\u001a\u00020\u00112\u0006\u0010\f\u001a\u00020\bH\u0002JK\u0010\u0013\u001a\u0004\u0018\u0001H\u0014\"\b\b\u0000\u0010\u0014*\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00110\u00162\u0014\u0010\u0017\u001a\u0010\u0012\u0004\u0012\u00020\r\u0012\u0006\u0012\u0004\u0018\u0001H\u00140\u0016H\u0002¢\u0006\u0002\u0010\u0018J\f\u0010\u0019\u001a\u00020\u0011*\u00020\rH\u0002J\f\u0010\u001a\u001a\u00020\u0011*\u00020\rH\u0002J\u000e\u0010\u001b\u001a\u0004\u0018\u00010\u001c*\u00020\u001dH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/FirDoubleColonExpressionResolver;", "", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "resolveDoubleColonLHS", "Lorg/jetbrains/kotlin/fir/resolve/DoubleColonLHS;", "doubleColonExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;", "resolveDoubleColonLHS$resolve", "resolveExpressionOnLHS", "Lorg/jetbrains/kotlin/fir/resolve/DoubleColonLHS$Expression;", "expression", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "resolveTypeOnLHS", "Lorg/jetbrains/kotlin/fir/resolve/DoubleColonLHS$Type;", "shouldTryResolveLHSAsExpression", "", "shouldTryResolveLHSAsType", "tryResolveLHS", ExifInterface.GPS_DIRECTION_TRUE, "criterion", "Lkotlin/Function1;", "resolve", "(Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/fir/resolve/DoubleColonLHS;", "canBeConsideredProperExpression", "canBeConsideredProperType", "expandedRegularClassIfAny", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDoubleColonExpressionResolver {
    private final FirSession session;

    /* JADX INFO: compiled from: FirDoubleColonExpressionResolver.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Variance.values().length];
            try {
                iArr[Variance.INVARIANT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Variance.IN_VARIANCE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Variance.OUT_VARIANCE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public FirDoubleColonExpressionResolver(FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        this.session = session;
    }

    private final boolean canBeConsideredProperExpression(FirExpression firExpression) {
        if (!(firExpression instanceof FirQualifiedAccessExpression)) {
            return true;
        }
        FirExpression explicitReceiver = ((FirQualifiedAccessExpression) firExpression).getExplicitReceiver();
        return explicitReceiver != null && canBeConsideredProperExpression(explicitReceiver);
    }

    private final boolean canBeConsideredProperType(FirExpression firExpression) {
        FirQualifiedAccessExpression firQualifiedAccessExpression;
        FirExpression explicitReceiver;
        FirExpression explicitReceiver2;
        if ((firExpression instanceof FirFunctionCall) && ((explicitReceiver2 = ((FirFunctionCall) firExpression).getExplicitReceiver()) == null || canBeConsideredProperType(explicitReceiver2))) {
            return false;
        }
        return ((firExpression instanceof FirQualifiedAccessExpression) && (((explicitReceiver = (firQualifiedAccessExpression = (FirQualifiedAccessExpression) firExpression).getExplicitReceiver()) == null || canBeConsideredProperType(explicitReceiver)) && (firQualifiedAccessExpression.getCalleeReference() instanceof FirNamedReference))) || (firExpression instanceof FirResolvedQualifier);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean shouldTryResolveLHSAsExpression(FirCallableReferenceAccess expression) {
        FirExpression explicitReceiver = expression.getExplicitReceiver();
        return (explicitReceiver == null || !canBeConsideredProperExpression(explicitReceiver) || expression.getHasQuestionMarkAtLHS()) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean shouldTryResolveLHSAsType(FirCallableReferenceAccess expression) {
        FirExpression explicitReceiver = expression.getExplicitReceiver();
        return explicitReceiver != null && canBeConsideredProperType(explicitReceiver);
    }

    public final DoubleColonLHS resolveDoubleColonLHS$resolve(FirCallableReferenceAccess doubleColonExpression) {
        Intrinsics.checkNotNullParameter(doubleColonExpression, "doubleColonExpression");
        DoubleColonLHS.Expression expression = (DoubleColonLHS.Expression) tryResolveLHS(doubleColonExpression, new FirDoubleColonExpressionResolver$resolveDoubleColonLHS$resultForExpr$1(this), new FirDoubleColonExpressionResolver$resolveDoubleColonLHS$resultForExpr$2(this));
        if (expression != null && !expression.getIsObjectQualifier()) {
            return expression;
        }
        DoubleColonLHS.Type type = (DoubleColonLHS.Type) tryResolveLHS(doubleColonExpression, new FirDoubleColonExpressionResolver$resolveDoubleColonLHS$resultForType$1(this), new Function1<FirExpression, DoubleColonLHS.Type>() { // from class: org.jetbrains.kotlin.fir.resolve.FirDoubleColonExpressionResolver$resolveDoubleColonLHS$resultForType$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final DoubleColonLHS.Type invoke(FirExpression expression2) {
                Intrinsics.checkNotNullParameter(expression2, "expression");
                return this.this$0.resolveTypeOnLHS(expression2);
            }
        });
        if (type != null) {
            if (expression != null && Intrinsics.areEqual(type.getType(), expression.getType())) {
                return expression;
            }
            return type;
        }
        return expression;
    }

    private final <T extends DoubleColonLHS> T tryResolveLHS(FirCallableReferenceAccess doubleColonExpression, Function1<? super FirCallableReferenceAccess, Boolean> criterion, Function1<? super FirExpression, ? extends T> resolve) {
        FirExpression explicitReceiver = doubleColonExpression.getExplicitReceiver();
        if (explicitReceiver != null && criterion.invoke(doubleColonExpression).booleanValue()) {
            return resolve.invoke(explicitReceiver);
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final FirRegularClass expandedRegularClassIfAny(FirResolvedQualifier firResolvedQualifier) {
        FirClassLikeDeclaration firClassLikeDeclaration;
        ConeClassLikeLookupTag lookupTag;
        FirClassLikeSymbol<?> symbol;
        FirClassLikeSymbol<?> symbol2 = firResolvedQualifier.getSymbol();
        if (symbol2 == null || (firClassLikeDeclaration = (FirClassLikeDeclaration) symbol2.getFir()) == null) {
            return null;
        }
        while (firClassLikeDeclaration instanceof FirTypeAlias) {
            ConeClassLikeType expandedConeType = FirDeclarationUtilKt.getExpandedConeType((FirTypeAlias) firClassLikeDeclaration);
            if (expandedConeType == null || (lookupTag = expandedConeType.getLookupTag()) == null || (symbol = LookupTagUtilsKt.toSymbol(lookupTag, this.session)) == null || (firClassLikeDeclaration = (FirClassLikeDeclaration) symbol.getFir()) == null) {
                return null;
            }
        }
        if (firClassLikeDeclaration instanceof FirRegularClass) {
            return (FirRegularClass) firClassLikeDeclaration;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final DoubleColonLHS.Expression resolveExpressionOnLHS(FirExpression expression) {
        ConeKotlinType coneType = FirTypeUtilsKt.getConeType(expression.getTypeRef());
        if (expression instanceof FirResolvedQualifier) {
            FirRegularClass firRegularClassExpandedRegularClassIfAny = expandedRegularClassIfAny((FirResolvedQualifier) expression);
            if (firRegularClassExpandedRegularClassIfAny != null && firRegularClassExpandedRegularClassIfAny.getClassKind() == ClassKind.OBJECT) {
                return new DoubleColonLHS.Expression(coneType, true);
            }
            return null;
        }
        return new DoubleColonLHS.Expression(coneType, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final DoubleColonLHS.Type resolveTypeOnLHS(FirExpression expression) {
        FirClassLikeSymbol<?> symbol;
        FirClassLikeDeclaration firClassLikeDeclaration;
        ConeKotlinType coneKotlinTypeProjectionIn;
        FirResolvedQualifier firResolvedQualifier = expression instanceof FirResolvedQualifier ? (FirResolvedQualifier) expression : null;
        if (firResolvedQualifier == null || (symbol = firResolvedQualifier.getSymbol()) == null || (firClassLikeDeclaration = (FirClassLikeDeclaration) symbol.getFir()) == null) {
            return null;
        }
        ConeClassLikeLookupTag lookupTag = firClassLikeDeclaration.getSymbol().getLookupTag();
        int size = firClassLikeDeclaration.getTypeParameters().size();
        ConeTypeProjection[] coneTypeProjectionArr = new ConeTypeProjection[size];
        for (int i = 0; i < size; i++) {
            FirTypeProjection firTypeProjection = (FirTypeProjection) CollectionsKt.getOrNull(((FirResolvedQualifier) expression).getTypeArguments(), i);
            if (firTypeProjection == null) {
                coneKotlinTypeProjectionIn = ConeStarProjection.INSTANCE;
            } else if (firTypeProjection instanceof FirTypeProjectionWithVariance) {
                FirTypeProjectionWithVariance firTypeProjectionWithVariance = (FirTypeProjectionWithVariance) firTypeProjection;
                ConeKotlinType coneType = FirTypeUtilsKt.getConeType(firTypeProjectionWithVariance.getTypeRef());
                int i2 = WhenMappings.$EnumSwitchMapping$0[firTypeProjectionWithVariance.getVariance().ordinal()];
                if (i2 == 1) {
                    coneKotlinTypeProjectionIn = coneType;
                } else if (i2 == 2) {
                    coneKotlinTypeProjectionIn = new ConeKotlinTypeProjectionIn(coneType);
                } else {
                    if (i2 != 3) {
                        throw new NoWhenBranchMatchedException();
                    }
                    coneKotlinTypeProjectionIn = new ConeKotlinTypeProjectionOut(coneType);
                }
            } else {
                coneKotlinTypeProjectionIn = ConeStarProjection.INSTANCE;
            }
            coneTypeProjectionArr[i] = coneKotlinTypeProjectionIn;
        }
        return new DoubleColonLHS.Type(new ConeClassLikeTypeImpl(lookupTag, coneTypeProjectionArr, firResolvedQualifier.getIsNullableLHSForCallableReference(), null, 8, null));
    }
}
