package org.jetbrains.kotlin.fir.scopes;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.util.AbstractArrayMapOwner;
import org.jetbrains.kotlin.util.ArrayMapAccessor;
import org.jetbrains.kotlin.util.TypeRegistry;

/* JADX INFO: compiled from: FirOverrideService.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\"\u001f\u0010\u0000\u001a\u00020\u0001*\u00020\u00028FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0007"}, d2 = {"overrideService", "Lorg/jetbrains/kotlin/fir/scopes/FirOverrideService;", "Lorg/jetbrains/kotlin/fir/FirSession;", "getOverrideService", "(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/scopes/FirOverrideService;", "overrideService$delegate", "Lorg/jetbrains/kotlin/util/ArrayMapAccessor;", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirOverrideServiceKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(FirOverrideServiceKt.class, "providers"), "overrideService", "getOverrideService(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/scopes/FirOverrideService;"))};
    private static final ArrayMapAccessor overrideService$delegate = TypeRegistry.generateAccessor$default(FirSession.INSTANCE, Reflection.getOrCreateKotlinClass(FirOverrideService.class), (Object) null, 2, (Object) null);

    public static final FirOverrideService getOverrideService(FirSession firSession) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        return (FirOverrideService) overrideService$delegate.getValue((AbstractArrayMapOwner) firSession, $$delegatedProperties[0]);
    }
}
