package org.jetbrains.kotlin.fir;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.util.AbstractArrayMapOwner;
import org.jetbrains.kotlin.util.NullableArrayMapAccessor;

/* JADX INFO: compiled from: FirModuleData.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"!\u0010\u0005\u001a\u0004\u0018\u00010\u0001*\u00020\u00028FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0006\u0010\u0004¨\u0006\t"}, d2 = {"moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "Lorg/jetbrains/kotlin/fir/FirSession;", "getModuleData", "(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/FirModuleData;", "nullableModuleData", "getNullableModuleData", "nullableModuleData$delegate", "Lorg/jetbrains/kotlin/util/NullableArrayMapAccessor;", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirModuleDataKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(FirModuleDataKt.class, Constants.TYPE_TREE), "nullableModuleData", "getNullableModuleData(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/FirModuleData;"))};
    private static final NullableArrayMapAccessor nullableModuleData$delegate = FirSession.INSTANCE.generateNullableAccessor(Reflection.getOrCreateKotlinClass(FirModuleData.class));

    public static final FirModuleData getModuleData(FirSession firSession) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        FirModuleData nullableModuleData = getNullableModuleData(firSession);
        if (nullableModuleData != null) {
            return nullableModuleData;
        }
        throw new IllegalStateException(("Module data is not registered in " + firSession).toString());
    }

    public static final FirModuleData getNullableModuleData(FirSession firSession) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        return (FirModuleData) nullableModuleData$delegate.getValue((AbstractArrayMapOwner) firSession, $$delegatedProperties[0]);
    }
}
