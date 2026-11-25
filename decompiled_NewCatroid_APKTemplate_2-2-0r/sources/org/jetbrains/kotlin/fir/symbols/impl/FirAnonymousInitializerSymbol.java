package org.jetbrains.kotlin.fir.symbols.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Reflection;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousInitializer;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;

/* JADX INFO: compiled from: FirAnonymousInitializerSymbol.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\b\u001a\u00020\tH\u0016R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u00058F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/fir/symbols/impl/FirAnonymousInitializerSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousInitializer;", "()V", "dispatchReceiverType", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "getDispatchReceiverType", "()Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "toString", "", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirAnonymousInitializerSymbol extends FirBasedSymbol<FirAnonymousInitializer> {
    public String toString() {
        return Reflection.getOrCreateKotlinClass(getClass()).getSimpleName() + " <init>";
    }

    public final ConeSimpleKotlinType getDispatchReceiverType() {
        return getFir().getDispatchReceiverType();
    }
}
