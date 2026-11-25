package org.jetbrains.kotlin.fir.analysis;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.util.AbstractArrayMapOwner;
import org.jetbrains.kotlin.util.ArrayMapAccessor;
import org.jetbrains.kotlin.util.TypeRegistry;

/* JADX INFO: compiled from: FirOverridesBackwardCompatibilityHelper.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\"\u001f\u0010\u0000\u001a\u00020\u0001*\u00020\u00028FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0007"}, d2 = {"overridesBackwardCompatibilityHelper", "Lorg/jetbrains/kotlin/fir/analysis/FirOverridesBackwardCompatibilityHelper;", "Lorg/jetbrains/kotlin/fir/FirSession;", "getOverridesBackwardCompatibilityHelper", "(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/analysis/FirOverridesBackwardCompatibilityHelper;", "overridesBackwardCompatibilityHelper$delegate", "Lorg/jetbrains/kotlin/util/ArrayMapAccessor;", "checkers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirOverridesBackwardCompatibilityHelperKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(FirOverridesBackwardCompatibilityHelperKt.class, "checkers"), "overridesBackwardCompatibilityHelper", "getOverridesBackwardCompatibilityHelper(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/analysis/FirOverridesBackwardCompatibilityHelper;"))};
    private static final ArrayMapAccessor overridesBackwardCompatibilityHelper$delegate = TypeRegistry.generateAccessor$default(FirSession.INSTANCE, Reflection.getOrCreateKotlinClass(FirOverridesBackwardCompatibilityHelper.class), (Object) null, 2, (Object) null);

    public static final FirOverridesBackwardCompatibilityHelper getOverridesBackwardCompatibilityHelper(FirSession firSession) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        return (FirOverridesBackwardCompatibilityHelper) overridesBackwardCompatibilityHelper$delegate.getValue((AbstractArrayMapOwner) firSession, $$delegatedProperties[0]);
    }
}
