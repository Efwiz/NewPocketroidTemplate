package org.jetbrains.kotlin.fir.resolve.transformers;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.fir.CopyUtilsKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirIntegerLiteralOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.builder.FirFunctionCallBuilder;
import org.jetbrains.kotlin.fir.references.FirNamedReference;
import org.jetbrains.kotlin.fir.references.FirResolvedErrorReference;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;
import org.jetbrains.kotlin.fir.references.builder.FirResolvedErrorReferenceBuilder;
import org.jetbrains.kotlin.fir.references.builder.FirResolvedNamedReferenceBuilder;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.scopes.FakeOverrideTypeCalculator;
import org.jetbrains.kotlin.fir.scopes.FirScopeKt;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.impl.FirIntegerConstantOperatorScopeKt;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeIntegerLiteralType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitBuiltinTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitUIntTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.ConstantValueKind;

/* JADX INFO: compiled from: IntegerLiteralAndOperatorApproximationTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 (2\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001:\u0001(B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0018\u0010\u0015\u001a\u00020\r2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J&\u0010\u001a\u001a\u00020\u001b\"\u0004\b\u0000\u0010\u001c2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u0002H\u001c0\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0002H\u0016J)\u0010 \u001a\u0002H!\"\b\b\u0000\u0010!*\u00020\"2\u0006\u0010#\u001a\u0002H!2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0002H\u0016¢\u0006\u0002\u0010$J\u001a\u0010%\u001a\u00020\u001b2\u0006\u0010&\u001a\u00020'2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0002H\u0016R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001b\u0010\f\u001a\u00020\r8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u000e\u0010\u000fR\u001b\u0010\u0012\u001a\u00020\r8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0014\u0010\u0011\u001a\u0004\b\u0013\u0010\u000f¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/IntegerLiteralAndOperatorApproximationTransformer;", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;)V", "getScopeSession", "()Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "toLongSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "getToLongSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "toLongSymbol$delegate", "Lkotlin/Lazy;", "toULongSymbol", "getToULongSymbol", "toULongSymbol$delegate", "findConversionFunction", "receiverType", "Lorg/jetbrains/kotlin/fir/types/impl/FirImplicitBuiltinTypeRef;", "name", "Lorg/jetbrains/kotlin/name/Name;", "transformConstExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", ExifInterface.GPS_DIRECTION_TRUE, "constExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirConstExpression;", "data", "transformElement", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "(Lorg/jetbrains/kotlin/fir/FirElement;Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformIntegerLiteralOperatorCall", "integerLiteralOperatorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirIntegerLiteralOperatorCall;", "Companion", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IntegerLiteralAndOperatorApproximationTransformer extends FirTransformer<ConeKotlinType> {
    private static final Name TO_LONG;
    private static final Name TO_U_LONG;
    private final ScopeSession scopeSession;
    private final FirSession session;

    /* JADX INFO: renamed from: toLongSymbol$delegate, reason: from kotlin metadata */
    private final Lazy toLongSymbol;

    /* JADX INFO: renamed from: toULongSymbol$delegate, reason: from kotlin metadata */
    private final Lazy toULongSymbol;

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public <E extends FirElement> E transformElement(E element, ConeKotlinType data) {
        Intrinsics.checkNotNullParameter(element, "element");
        return element;
    }

    public final FirSession getSession() {
        return this.session;
    }

    public final ScopeSession getScopeSession() {
        return this.scopeSession;
    }

    public IntegerLiteralAndOperatorApproximationTransformer(FirSession session, ScopeSession scopeSession) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        this.session = session;
        this.scopeSession = scopeSession;
        this.toLongSymbol = LazyKt.lazy(new Function0<FirNamedFunctionSymbol>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.IntegerLiteralAndOperatorApproximationTransformer$toLongSymbol$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final FirNamedFunctionSymbol invoke() {
                IntegerLiteralAndOperatorApproximationTransformer integerLiteralAndOperatorApproximationTransformer = this.this$0;
                return integerLiteralAndOperatorApproximationTransformer.findConversionFunction(integerLiteralAndOperatorApproximationTransformer.getSession().getBuiltinTypes().getIntType(), IntegerLiteralAndOperatorApproximationTransformer.TO_LONG);
            }
        });
        this.toULongSymbol = LazyKt.lazy(new Function0<FirNamedFunctionSymbol>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.IntegerLiteralAndOperatorApproximationTransformer$toULongSymbol$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final FirNamedFunctionSymbol invoke() {
                IntegerLiteralAndOperatorApproximationTransformer integerLiteralAndOperatorApproximationTransformer = this.this$0;
                return integerLiteralAndOperatorApproximationTransformer.findConversionFunction(integerLiteralAndOperatorApproximationTransformer.getSession().getBuiltinTypes().getUIntType(), IntegerLiteralAndOperatorApproximationTransformer.TO_U_LONG);
            }
        });
    }

    static {
        Name nameIdentifier = Name.identifier("toLong");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        TO_LONG = nameIdentifier;
        Name nameIdentifier2 = Name.identifier("toULong");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        TO_U_LONG = nameIdentifier2;
    }

    private final FirNamedFunctionSymbol getToLongSymbol() {
        return (FirNamedFunctionSymbol) this.toLongSymbol.getValue();
    }

    private final FirNamedFunctionSymbol getToULongSymbol() {
        return (FirNamedFunctionSymbol) this.toULongSymbol.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FirNamedFunctionSymbol findConversionFunction(FirImplicitBuiltinTypeRef receiverType, Name name2) {
        FirTypeScope firTypeScopeScope = ScopeUtilsKt.scope(receiverType.getType(), this.session, this.scopeSession, FakeOverrideTypeCalculator.DoNothing.INSTANCE, FirResolvePhase.STATUS);
        Intrinsics.checkNotNull(firTypeScopeScope);
        return (FirNamedFunctionSymbol) CollectionsKt.single((List) FirScopeKt.getFunctions(firTypeScopeScope, name2));
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public <T> FirStatement transformConstExpression(FirConstExpression<T> constExpression, ConeKotlinType data) {
        Intrinsics.checkNotNullParameter(constExpression, "constExpression");
        FirConstExpression<T> firConstExpression = constExpression;
        FirTypeRef typeRef = firConstExpression.getTypeRef();
        FirResolvedTypeRef firResolvedTypeRef = typeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) typeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        if (!(type instanceof ConeIntegerLiteralType)) {
            type = null;
        }
        ConeIntegerLiteralType coneIntegerLiteralType = (ConeIntegerLiteralType) type;
        if (coneIntegerLiteralType == null) {
            return constExpression;
        }
        ConeClassLikeType approximatedType = coneIntegerLiteralType.getApproximatedType(data != null ? TypeExpansionUtilsKt.fullyExpandedType(data, this.session) : null);
        firConstExpression.replaceTypeRef(CopyUtilsKt.resolvedTypeFromPrototype$default(firConstExpression.getTypeRef(), approximatedType, null, 2, null));
        ConstantValueKind<?> constKind = FirTypeUtilsKt.toConstKind(approximatedType);
        Intrinsics.checkNotNull(constKind, "null cannot be cast to non-null type org.jetbrains.kotlin.types.ConstantValueKind<T of org.jetbrains.kotlin.fir.resolve.transformers.IntegerLiteralAndOperatorApproximationTransformer.transformConstExpression>");
        constExpression.replaceKind(constKind);
        return constExpression;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformIntegerLiteralOperatorCall(FirIntegerLiteralOperatorCall integerLiteralOperatorCall, ConeKotlinType data) {
        FirImplicitUIntTypeRef intType;
        FirResolvedErrorReference firResolvedErrorReferenceBuild;
        Intrinsics.checkNotNullParameter(integerLiteralOperatorCall, "integerLiteralOperatorCall");
        FirIntegerLiteralOperatorCall firIntegerLiteralOperatorCall = integerLiteralOperatorCall;
        FirTypeRef typeRef = firIntegerLiteralOperatorCall.getTypeRef();
        FirResolvedTypeRef firResolvedTypeRef = typeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) typeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        if (!(type instanceof ConeIntegerLiteralType)) {
            type = null;
        }
        ConeIntegerLiteralType coneIntegerLiteralType = (ConeIntegerLiteralType) type;
        if (coneIntegerLiteralType == null) {
            return integerLiteralOperatorCall;
        }
        ConeClassLikeType approximatedType = coneIntegerLiteralType.getApproximatedType(data != null ? TypeExpansionUtilsKt.fullyExpandedType(data, this.session) : null);
        IntegerLiteralAndOperatorApproximationTransformer integerLiteralAndOperatorApproximationTransformer = this;
        integerLiteralOperatorCall.transformDispatchReceiver(integerLiteralAndOperatorApproximationTransformer, null);
        integerLiteralOperatorCall.transformExtensionReceiver(integerLiteralAndOperatorApproximationTransformer, null);
        integerLiteralOperatorCall.getArgumentList().transformArguments(integerLiteralAndOperatorApproximationTransformer, null);
        ConeClassLikeType coneClassLikeType = approximatedType;
        firIntegerLiteralOperatorCall.replaceTypeRef(CopyUtilsKt.resolvedTypeFromPrototype$default(firIntegerLiteralOperatorCall.getTypeRef(), coneClassLikeType, null, 2, null));
        FirNamedReference calleeReference = integerLiteralOperatorCall.getCalleeReference();
        if (calleeReference instanceof FirResolvedNamedReference) {
            FirBasedSymbol<?> resolvedSymbol = ((FirResolvedNamedReference) calleeReference).getResolvedSymbol();
            Intrinsics.checkNotNull(resolvedSymbol, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol");
            FirNamedFunctionSymbol originalForWrappedIntegerOperator = FirIntegerConstantOperatorScopeKt.getOriginalForWrappedIntegerOperator((FirSimpleFunction) ((FirNamedFunctionSymbol) resolvedSymbol).getFir());
            Intrinsics.checkNotNull(originalForWrappedIntegerOperator);
            if (calleeReference instanceof FirResolvedErrorReference) {
                FirResolvedErrorReferenceBuilder firResolvedErrorReferenceBuilder = new FirResolvedErrorReferenceBuilder();
                firResolvedErrorReferenceBuilder.setName(calleeReference.getName());
                firResolvedErrorReferenceBuilder.setSource(calleeReference.getSource());
                firResolvedErrorReferenceBuilder.setResolvedSymbol(originalForWrappedIntegerOperator);
                firResolvedErrorReferenceBuilder.setDiagnostic(((FirResolvedErrorReference) calleeReference).getDiagnostic());
                firResolvedErrorReferenceBuild = firResolvedErrorReferenceBuilder.build();
            } else {
                FirResolvedNamedReferenceBuilder firResolvedNamedReferenceBuilder = new FirResolvedNamedReferenceBuilder();
                firResolvedNamedReferenceBuilder.setName(calleeReference.getName());
                firResolvedNamedReferenceBuilder.setSource(calleeReference.getSource());
                firResolvedNamedReferenceBuilder.setResolvedSymbol(originalForWrappedIntegerOperator);
                firResolvedErrorReferenceBuild = firResolvedNamedReferenceBuilder.build();
            }
            integerLiteralOperatorCall.replaceCalleeReference((FirNamedReference) firResolvedErrorReferenceBuild);
        }
        if (ConeBuiltinTypeUtilsKt.isInt(coneClassLikeType) || ConeBuiltinTypeUtilsKt.isUInt(coneClassLikeType)) {
            return integerLiteralOperatorCall;
        }
        if (coneIntegerLiteralType.getIsUnsigned()) {
            intType = this.session.getBuiltinTypes().getUIntType();
        } else {
            intType = this.session.getBuiltinTypes().getIntType();
        }
        integerLiteralOperatorCall.replaceTypeRef(intType);
        FirFunctionCallBuilder firFunctionCallBuilder = new FirFunctionCallBuilder();
        KtSourceElement source = integerLiteralOperatorCall.getSource();
        firFunctionCallBuilder.setSource(source != null ? KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.IntToLongConversion.INSTANCE, 0, 0, 6, null) : null);
        firFunctionCallBuilder.setTypeRef(this.session.getBuiltinTypes().getLongType());
        firFunctionCallBuilder.setExplicitReceiver(firIntegerLiteralOperatorCall);
        firFunctionCallBuilder.setDispatchReceiver(firIntegerLiteralOperatorCall);
        FirResolvedNamedReferenceBuilder firResolvedNamedReferenceBuilder2 = new FirResolvedNamedReferenceBuilder();
        if (coneIntegerLiteralType.getIsUnsigned()) {
            firResolvedNamedReferenceBuilder2.setName(TO_U_LONG);
            firResolvedNamedReferenceBuilder2.setResolvedSymbol(getToULongSymbol());
        } else {
            firResolvedNamedReferenceBuilder2.setName(TO_LONG);
            firResolvedNamedReferenceBuilder2.setResolvedSymbol(getToLongSymbol());
        }
        firFunctionCallBuilder.setCalleeReference(firResolvedNamedReferenceBuilder2.build());
        return firFunctionCallBuilder.mo6809build();
    }
}
