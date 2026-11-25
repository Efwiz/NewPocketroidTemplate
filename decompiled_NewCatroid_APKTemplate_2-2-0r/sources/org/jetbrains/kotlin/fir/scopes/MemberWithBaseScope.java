package org.jetbrains.kotlin.fir.scopes;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: FirTypeScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\u0018\u0000*\u000e\b\u0000\u0010\u0001 \u0001*\u0006\u0012\u0002\b\u00030\u00022\u00020\u0003B\u0015\u0012\u0006\u0010\u0004\u001a\u00028\u0000\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u000e\u0010\r\u001a\u00028\u0000H\u0086\u0002¢\u0006\u0002\u0010\u000bJ\t\u0010\u000e\u001a\u00020\u0006H\u0086\u0002J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0003H\u0096\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0016R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0004\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000b¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/MemberWithBaseScope;", "D", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "", "member", "baseScope", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;)V", "getBaseScope", "()Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "getMember", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "component1", "component2", Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MemberWithBaseScope<D extends FirCallableSymbol<?>> {
    private final FirTypeScope baseScope;
    private final D member;

    public MemberWithBaseScope(D member, FirTypeScope baseScope) {
        Intrinsics.checkNotNullParameter(member, "member");
        Intrinsics.checkNotNullParameter(baseScope, "baseScope");
        this.member = member;
        this.baseScope = baseScope;
    }

    public final FirTypeScope getBaseScope() {
        return this.baseScope;
    }

    public final D getMember() {
        return this.member;
    }

    public final D component1() {
        return this.member;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final FirTypeScope getBaseScope() {
        return this.baseScope;
    }

    public boolean equals(Object other) {
        return (other instanceof MemberWithBaseScope) && Intrinsics.areEqual(this.member, ((MemberWithBaseScope) other).member);
    }

    public int hashCode() {
        return this.member.hashCode();
    }
}
