package org.jetbrains.kotlin.fir.java.symbols;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.fir.symbols.impl.FirSyntheticPropertySymbol;
import org.jetbrains.kotlin.name.CallableId;

/* JADX INFO: compiled from: FirJavaOverriddenSyntheticPropertySymbol.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\b\u0010\u0006\u001a\u00020\u0001H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/symbols/FirJavaOverriddenSyntheticPropertySymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirSyntheticPropertySymbol;", "propertyId", "Lorg/jetbrains/kotlin/name/CallableId;", "getterId", "(Lorg/jetbrains/kotlin/name/CallableId;Lorg/jetbrains/kotlin/name/CallableId;)V", ConfigConstants.CONFIG_RENAMELIMIT_COPY, SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJavaOverriddenSyntheticPropertySymbol extends FirSyntheticPropertySymbol {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirJavaOverriddenSyntheticPropertySymbol(CallableId propertyId, CallableId getterId) {
        super(propertyId, getterId);
        Intrinsics.checkNotNullParameter(propertyId, "propertyId");
        Intrinsics.checkNotNullParameter(getterId, "getterId");
    }

    @Override // org.jetbrains.kotlin.fir.symbols.impl.FirSyntheticPropertySymbol
    public FirSyntheticPropertySymbol copy() {
        return new FirJavaOverriddenSyntheticPropertySymbol(getCallableId(), getGetterId());
    }
}
