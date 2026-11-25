package org.jetbrains.kotlin.fir.resolve.calls;

import androidx.exifinterface.media.ExifInterface;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression;
import org.jetbrains.kotlin.fir.expressions.builder.FirSmartCastExpressionBuilder;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt;
import org.jetbrains.kotlin.fir.scopes.FakeOverrideTypeCalculator;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.types.SmartcastStability;

/* JADX INFO: compiled from: FirReceivers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\f\b\u0000\u0010\u0001*\u0006\u0012\u0002\b\u00030\u00022\u00020\u0003:\u00012BC\b\u0004\u0012\u0006\u0010\u0004\u001a\u00028\u0000\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u000f\u001a\u00020\f¢\u0006\u0002\u0010\u0010J\b\u0010,\u001a\u00020\u0012H\u0002J\u000e\u0010-\u001a\b\u0012\u0004\u0012\u00028\u00000\u0000H&J\u001a\u0010.\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010/\u001a\u0002002\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u00101\u001a\u0002002\u0006\u0010\u0005\u001a\u00020\u0006H\u0007R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u0013\u0010\u0004\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\u0015\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\"\u0010\u001a\u001a\u0004\u0018\u00010\u00192\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u000e\u0010\u000f\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u001d\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010 \u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0011\u0010#\u001a\u00020\u00128F¢\u0006\u0006\u001a\u0004\b$\u0010%R\u000e\u0010&\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\nX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b'\u0010(R\u001e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u0006@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b)\u0010\"R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b*\u0010+\u0082\u0001\u00043456¨\u00067"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/ImplicitReceiverValue;", ExifInterface.LATITUDE_SOUTH, "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ReceiverValue;", "boundSymbol", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "useSiteSession", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "mutable", "", "contextReceiverNumber", "", "inaccessibleReceiver", "(Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;ZIZ)V", "_receiverExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getBoundSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "getContextReceiverNumber", "()I", "<set-?>", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "implicitScope", "getImplicitScope", "()Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "isContextReceiver", "()Z", "originalReceiverExpression", "originalType", "getOriginalType", "()Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "receiverExpression", "getReceiverExpression", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "receiverIsSmartcasted", "getScopeSession", "()Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "getType", "getUseSiteSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "computeReceiverExpression", "createSnapshot", "scope", "updateTypeFromSmartcast", "", "updateTypeInBuilderInference", "ImplicitReceiverInternals", "Lorg/jetbrains/kotlin/fir/resolve/calls/ContextReceiverValue;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ImplicitDispatchReceiverValue;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ImplicitExtensionReceiverValue;", "Lorg/jetbrains/kotlin/fir/resolve/calls/InaccessibleImplicitReceiverValue;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ImplicitReceiverValue<S extends FirBasedSymbol<?>> extends ReceiverValue {
    private FirExpression _receiverExpression;
    private final S boundSymbol;
    private final int contextReceiverNumber;
    private FirTypeScope implicitScope;
    private final boolean inaccessibleReceiver;
    private final boolean mutable;
    private FirExpression originalReceiverExpression;
    private final ConeKotlinType originalType;
    private boolean receiverIsSmartcasted;
    private final ScopeSession scopeSession;
    private ConeKotlinType type;
    private final FirSession useSiteSession;

    /* JADX INFO: compiled from: FirReceivers.kt */
    @Retention(RetentionPolicy.RUNTIME)
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0087\u0002\u0018\u00002\u00020\u0001B\u0000¨\u0006\u0002"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/ImplicitReceiverValue$ImplicitReceiverInternals;", "", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public @interface ImplicitReceiverInternals {
    }

    public /* synthetic */ ImplicitReceiverValue(FirBasedSymbol firBasedSymbol, ConeKotlinType coneKotlinType, FirSession firSession, ScopeSession scopeSession, boolean z, int i, boolean z2, DefaultConstructorMarker defaultConstructorMarker) {
        this(firBasedSymbol, coneKotlinType, firSession, scopeSession, z, i, z2);
    }

    public abstract ImplicitReceiverValue<S> createSnapshot();

    public abstract boolean isContextReceiver();

    public /* synthetic */ ImplicitReceiverValue(FirBasedSymbol firBasedSymbol, ConeKotlinType coneKotlinType, FirSession firSession, ScopeSession scopeSession, boolean z, int i, boolean z2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(firBasedSymbol, coneKotlinType, firSession, scopeSession, z, (i2 & 32) != 0 ? -1 : i, (i2 & 64) != 0 ? false : z2, null);
    }

    public final S getBoundSymbol() {
        return this.boundSymbol;
    }

    public final FirSession getUseSiteSession() {
        return this.useSiteSession;
    }

    protected final ScopeSession getScopeSession() {
        return this.scopeSession;
    }

    public final int getContextReceiverNumber() {
        return this.contextReceiverNumber;
    }

    private ImplicitReceiverValue(S s, ConeKotlinType coneKotlinType, FirSession firSession, ScopeSession scopeSession, boolean z, int i, boolean z2) {
        this.boundSymbol = s;
        this.useSiteSession = firSession;
        this.scopeSession = scopeSession;
        this.mutable = z;
        this.contextReceiverNumber = i;
        this.inaccessibleReceiver = z2;
        this.type = coneKotlinType;
        this.originalType = coneKotlinType;
        this.implicitScope = ScopeUtilsKt.scope(coneKotlinType, firSession, scopeSession, FakeOverrideTypeCalculator.DoNothing.INSTANCE, FirResolvePhase.STATUS);
        this.originalReceiverExpression = FirReceiversKt.receiverExpression(s, coneKotlinType, i, z2);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.ReceiverValue
    public final ConeKotlinType getType() {
        return this.type;
    }

    public final ConeKotlinType getOriginalType() {
        return this.originalType;
    }

    public final FirTypeScope getImplicitScope() {
        return this.implicitScope;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.ReceiverValue
    public FirTypeScope scope(FirSession useSiteSession, ScopeSession scopeSession) {
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        return this.implicitScope;
    }

    private final FirExpression computeReceiverExpression() {
        FirSmartCastExpression firSmartCastExpressionBuild;
        FirExpression firExpression = this._receiverExpression;
        if (firExpression != null) {
            return firExpression;
        }
        if (!this.receiverIsSmartcasted) {
            firSmartCastExpressionBuild = this.originalReceiverExpression;
        } else {
            FirSmartCastExpressionBuilder firSmartCastExpressionBuilder = new FirSmartCastExpressionBuilder();
            firSmartCastExpressionBuilder.setOriginalExpression(this.originalReceiverExpression);
            FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
            KtSourceElement source = this.originalReceiverExpression.getTypeRef().getSource();
            firResolvedTypeRefBuilder.setSource(source != null ? KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.SmartCastedTypeRef.INSTANCE, 0, 0, 6, null) : null);
            firResolvedTypeRefBuilder.setType(this.type);
            firSmartCastExpressionBuilder.setSmartcastType(firResolvedTypeRefBuilder.build());
            firSmartCastExpressionBuilder.setTypesFromSmartCast(CollectionsKt.listOf(this.type));
            firSmartCastExpressionBuilder.setSmartcastStability(SmartcastStability.STABLE_VALUE);
            firSmartCastExpressionBuilder.setTypeRef(UtilsKt.copyWithNewSourceKind(firSmartCastExpressionBuilder.getSmartcastType(), KtFakeSourceElementKind.ImplicitTypeRef.INSTANCE));
            firSmartCastExpressionBuild = firSmartCastExpressionBuilder.build();
        }
        this._receiverExpression = firSmartCastExpressionBuild;
        return firSmartCastExpressionBuild;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.ReceiverValue
    public final FirExpression getReceiverExpression() {
        return computeReceiverExpression();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Builder inference should not modify implicit receivers. KT-54708")
    public final void updateTypeInBuilderInference(ConeKotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        this.type = type;
        this.originalReceiverExpression = FirReceiversKt.receiverExpression(this.boundSymbol, type, this.contextReceiverNumber, this.inaccessibleReceiver);
        this._receiverExpression = null;
        this.implicitScope = ScopeUtilsKt.scope(type, this.useSiteSession, this.scopeSession, FakeOverrideTypeCalculator.DoNothing.INSTANCE, FirResolvePhase.STATUS);
    }

    @ImplicitReceiverInternals
    public final void updateTypeFromSmartcast(ConeKotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        if (Intrinsics.areEqual(type, this.type)) {
            return;
        }
        if (!this.mutable) {
            throw new IllegalStateException("Cannot mutate an immutable ImplicitReceiverValue".toString());
        }
        this.type = type;
        this.receiverIsSmartcasted = !Intrinsics.areEqual(type, this.originalType);
        this._receiverExpression = null;
        this.implicitScope = ScopeUtilsKt.scope(type, this.useSiteSession, this.scopeSession, FakeOverrideTypeCalculator.DoNothing.INSTANCE, FirResolvePhase.STATUS);
    }
}
