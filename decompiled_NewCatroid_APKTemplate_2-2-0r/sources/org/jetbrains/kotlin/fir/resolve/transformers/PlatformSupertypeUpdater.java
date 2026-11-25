package org.jetbrains.kotlin.fir.resolve.transformers;

import kotlin.Metadata;
import org.jetbrains.kotlin.fir.FirSessionComponent;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;

/* JADX INFO: compiled from: PlatformSupertypeUpdater.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH&¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/PlatformSupertypeUpdater;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "()V", "updateSupertypesIfNeeded", "", "firClass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class PlatformSupertypeUpdater implements FirSessionComponent {
    public abstract void updateSupertypesIfNeeded(FirClass firClass, ScopeSession scopeSession);
}
