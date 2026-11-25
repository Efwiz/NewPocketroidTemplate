package org.jetbrains.kotlin.fir.resolve.calls;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeAttributes;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;

/* JADX INFO: compiled from: FirReceivers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\u0018\u00002\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u0001B#\b\u0016\u0012\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bB3\u0012\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0002\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\u0012\u0010\u0010\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u0001H\u0016R\u0014\u0010\u000e\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/ImplicitDispatchReceiverValue;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ImplicitReceiverValue;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "boundSymbol", "useSiteSession", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;)V", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "mutable", "", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;Z)V", "isContextReceiver", "()Z", "createSnapshot", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ImplicitDispatchReceiverValue extends ImplicitReceiverValue<FirClassSymbol<?>> {
    @Override // org.jetbrains.kotlin.fir.resolve.calls.ImplicitReceiverValue
    public boolean isContextReceiver() {
        return false;
    }

    public /* synthetic */ ImplicitDispatchReceiverValue(FirClassSymbol firClassSymbol, ConeKotlinType coneKotlinType, FirSession firSession, ScopeSession scopeSession, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firClassSymbol, coneKotlinType, firSession, scopeSession, (i & 16) != 0 ? true : z);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ImplicitDispatchReceiverValue(FirClassSymbol<?> boundSymbol, ConeKotlinType type, FirSession useSiteSession, ScopeSession scopeSession, boolean z) {
        super(boundSymbol, type, useSiteSession, scopeSession, z, 0, false, 96, null);
        Intrinsics.checkNotNullParameter(boundSymbol, "boundSymbol");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ImplicitDispatchReceiverValue(FirClassSymbol<?> boundSymbol, FirSession useSiteSession, ScopeSession scopeSession) {
        this(boundSymbol, TypeConstructionUtilsKt.constructType$default((FirClassLikeSymbol) boundSymbol, new ConeTypeProjection[0], false, (ConeAttributes) null, 4, (Object) null), useSiteSession, scopeSession, false, 16, null);
        Intrinsics.checkNotNullParameter(boundSymbol, "boundSymbol");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.ImplicitReceiverValue
    public ImplicitReceiverValue<FirClassSymbol<?>> createSnapshot() {
        return new ImplicitDispatchReceiverValue(getBoundSymbol(), getType(), getUseSiteSession(), getScopeSession(), false);
    }
}
