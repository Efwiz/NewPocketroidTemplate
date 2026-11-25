package org.jetbrains.kotlin.fir.declarations;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.util.AbstractArrayMapOwner;
import org.jetbrains.kotlin.util.NullableArrayMapAccessor;

/* JADX INFO: compiled from: FirTypeSpecificityComparatorProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\"!\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0007"}, d2 = {"typeSpecificityComparatorProvider", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeSpecificityComparatorProvider;", "Lorg/jetbrains/kotlin/fir/FirSession;", "getTypeSpecificityComparatorProvider", "(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/declarations/FirTypeSpecificityComparatorProvider;", "typeSpecificityComparatorProvider$delegate", "Lorg/jetbrains/kotlin/util/NullableArrayMapAccessor;", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirTypeSpecificityComparatorProviderKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(FirTypeSpecificityComparatorProviderKt.class, "providers"), "typeSpecificityComparatorProvider", "getTypeSpecificityComparatorProvider(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/declarations/FirTypeSpecificityComparatorProvider;"))};
    private static final NullableArrayMapAccessor typeSpecificityComparatorProvider$delegate = FirSession.INSTANCE.generateNullableAccessor(Reflection.getOrCreateKotlinClass(FirTypeSpecificityComparatorProvider.class));

    public static final FirTypeSpecificityComparatorProvider getTypeSpecificityComparatorProvider(FirSession firSession) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        return (FirTypeSpecificityComparatorProvider) typeSpecificityComparatorProvider$delegate.getValue((AbstractArrayMapOwner) firSession, $$delegatedProperties[0]);
    }
}
