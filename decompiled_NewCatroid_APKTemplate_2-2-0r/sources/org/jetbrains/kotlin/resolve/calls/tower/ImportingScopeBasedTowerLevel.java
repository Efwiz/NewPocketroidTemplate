package org.jetbrains.kotlin.resolve.calls.tower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.scopes.ImportingScope;

/* JADX INFO: compiled from: TowerLevels.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/ImportingScopeBasedTowerLevel;", "Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeBasedTowerLevel;", "scopeTower", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "importingScope", "Lorg/jetbrains/kotlin/resolve/scopes/ImportingScope;", "(Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;Lorg/jetbrains/kotlin/resolve/scopes/ImportingScope;)V", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ImportingScopeBasedTowerLevel extends ScopeBasedTowerLevel {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ImportingScopeBasedTowerLevel(ImplicitScopeTower scopeTower, ImportingScope importingScope) {
        super(scopeTower, importingScope);
        Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
        Intrinsics.checkNotNullParameter(importingScope, "importingScope");
    }
}
