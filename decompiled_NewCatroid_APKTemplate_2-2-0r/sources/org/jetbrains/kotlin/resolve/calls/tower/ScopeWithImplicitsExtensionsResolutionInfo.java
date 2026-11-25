package org.jetbrains.kotlin.resolve.calls.tower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.scopes.HierarchicalScope;

/* JADX INFO: compiled from: ImplicitsExtensionsResolutionFilter.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeWithImplicitsExtensionsResolutionInfo;", "", "scope", "Lorg/jetbrains/kotlin/resolve/scopes/HierarchicalScope;", "resolveExtensionsForImplicitReceiver", "", "(Lorg/jetbrains/kotlin/resolve/scopes/HierarchicalScope;Z)V", "getResolveExtensionsForImplicitReceiver", "()Z", "getScope", "()Lorg/jetbrains/kotlin/resolve/scopes/HierarchicalScope;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ScopeWithImplicitsExtensionsResolutionInfo {
    private final boolean resolveExtensionsForImplicitReceiver;
    private final HierarchicalScope scope;

    public ScopeWithImplicitsExtensionsResolutionInfo(HierarchicalScope scope, boolean z) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        this.scope = scope;
        this.resolveExtensionsForImplicitReceiver = z;
    }

    public final HierarchicalScope getScope() {
        return this.scope;
    }

    public final boolean getResolveExtensionsForImplicitReceiver() {
        return this.resolveExtensionsForImplicitReceiver;
    }
}
