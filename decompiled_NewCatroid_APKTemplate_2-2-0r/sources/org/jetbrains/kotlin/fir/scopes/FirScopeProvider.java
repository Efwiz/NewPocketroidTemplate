package org.jetbrains.kotlin.fir.scopes;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;

/* JADX INFO: compiled from: FirScopeProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\"\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH&J\"\u0010\u000b\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH&J \u0010\f\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ*\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H&¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/FirScopeProvider;", "", "()V", "getNestedClassifierScope", "Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;", "klass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "useSiteSession", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "getStaticMemberScopeForCallables", "getStaticScope", "getUseSiteMemberScope", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "memberRequiredPhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirScopeProvider {
    public abstract FirContainingNamesAwareScope getNestedClassifierScope(FirClass klass, FirSession useSiteSession, ScopeSession scopeSession);

    public abstract FirContainingNamesAwareScope getStaticMemberScopeForCallables(FirClass klass, FirSession useSiteSession, ScopeSession scopeSession);

    public abstract FirTypeScope getUseSiteMemberScope(FirClass klass, FirSession useSiteSession, ScopeSession scopeSession, FirResolvePhase memberRequiredPhase);

    public final FirContainingNamesAwareScope getStaticScope(FirClass klass, FirSession useSiteSession, ScopeSession scopeSession) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        FirContainingNamesAwareScope nestedClassifierScope = getNestedClassifierScope(klass, useSiteSession, scopeSession);
        FirContainingNamesAwareScope staticMemberScopeForCallables = getStaticMemberScopeForCallables(klass, useSiteSession, scopeSession);
        if (nestedClassifierScope == null || staticMemberScopeForCallables == null) {
            return nestedClassifierScope == null ? staticMemberScopeForCallables : nestedClassifierScope;
        }
        return new FirNameAwareCompositeScope(CollectionsKt.listOf((Object[]) new FirContainingNamesAwareScope[]{nestedClassifierScope, staticMemberScopeForCallables}));
    }
}
