package org.jetbrains.kotlin.fir.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.util.AbstractArrayMapOwner;
import org.jetbrains.kotlin.util.ArrayMapAccessor;
import org.jetbrains.kotlin.util.TypeRegistry;

/* JADX INFO: compiled from: FirDefaultParametersResolver.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\"\u001f\u0010\u0000\u001a\u00020\u0001*\u00020\u00028@X\u0080\u0084\u0002¢\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0007"}, d2 = {"defaultParameterResolver", "Lorg/jetbrains/kotlin/fir/resolve/FirDefaultParametersResolver;", "Lorg/jetbrains/kotlin/fir/FirSession;", "getDefaultParameterResolver", "(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/resolve/FirDefaultParametersResolver;", "defaultParameterResolver$delegate", "Lorg/jetbrains/kotlin/util/ArrayMapAccessor;", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirDefaultParametersResolverKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(FirDefaultParametersResolverKt.class, "resolve"), "defaultParameterResolver", "getDefaultParameterResolver(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/resolve/FirDefaultParametersResolver;"))};
    private static final ArrayMapAccessor defaultParameterResolver$delegate = TypeRegistry.generateAccessor$default(FirSession.INSTANCE, Reflection.getOrCreateKotlinClass(FirDefaultParametersResolver.class), (Object) null, 2, (Object) null);

    public static final FirDefaultParametersResolver getDefaultParameterResolver(FirSession firSession) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        return (FirDefaultParametersResolver) defaultParameterResolver$delegate.getValue((AbstractArrayMapOwner) firSession, $$delegatedProperties[0]);
    }
}
