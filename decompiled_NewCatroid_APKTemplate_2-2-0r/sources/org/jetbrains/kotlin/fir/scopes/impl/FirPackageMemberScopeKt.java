package org.jetbrains.kotlin.fir.scopes.impl;

import kotlin.Metadata;
import org.jetbrains.kotlin.fir.resolve.ScopeSessionKey;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: FirPackageMemberScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u001d\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u0006"}, d2 = {"PACKAGE_MEMBER", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSessionKey;", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirPackageMemberScope;", "getPACKAGE_MEMBER", "()Lorg/jetbrains/kotlin/fir/resolve/ScopeSessionKey;", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirPackageMemberScopeKt {
    private static final ScopeSessionKey<FqName, FirPackageMemberScope> PACKAGE_MEMBER = new ScopeSessionKey<FqName, FirPackageMemberScope>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirPackageMemberScopeKt$special$$inlined$scopeSessionKey$1
    };

    public static final ScopeSessionKey<FqName, FirPackageMemberScope> getPACKAGE_MEMBER() {
        return PACKAGE_MEMBER;
    }
}
