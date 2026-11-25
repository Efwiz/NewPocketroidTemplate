package org.jetbrains.kotlin.fir.resolve.calls;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.scopes.impl.FirOnlyCallablesScope;
import org.jetbrains.kotlin.fir.scopes.impl.FirOnlyClassifiersScope;
import org.jetbrains.kotlin.fir.scopes.impl.FirPackageMemberScope;

/* JADX INFO: compiled from: QualifierReceiver.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\u000eH\u0016R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/PackageQualifierReceiver;", "Lorg/jetbrains/kotlin/fir/resolve/calls/QualifierReceiver;", "explicitReceiver", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;", "useSiteSession", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;Lorg/jetbrains/kotlin/fir/FirSession;)V", "scope", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirPackageMemberScope;", "getScope", "()Lorg/jetbrains/kotlin/fir/scopes/impl/FirPackageMemberScope;", "callableScope", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirOnlyCallablesScope;", "classifierScope", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PackageQualifierReceiver extends QualifierReceiver {
    private final FirPackageMemberScope scope;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PackageQualifierReceiver(FirResolvedQualifier explicitReceiver, FirSession useSiteSession) {
        super(explicitReceiver);
        Intrinsics.checkNotNullParameter(explicitReceiver, "explicitReceiver");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        this.scope = new FirPackageMemberScope(explicitReceiver.getPackageFqName(), useSiteSession, null, null, 12, null);
    }

    public final FirPackageMemberScope getScope() {
        return this.scope;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.QualifierReceiver
    public FirScope classifierScope() {
        return new FirOnlyClassifiersScope(this.scope);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.QualifierReceiver
    public FirOnlyCallablesScope callableScope() {
        return new FirOnlyCallablesScope(this.scope);
    }
}
