package org.jetbrains.kotlin.fir.types;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.util.AbstractArrayMapOwner;
import org.jetbrains.kotlin.util.ArrayMapAccessor;
import org.jetbrains.kotlin.util.TypeRegistry;

/* JADX INFO: compiled from: TypeComponents.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u001f\u0010\u0005\u001a\u00020\u0006*\u00020\u00028BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\b\"\u0015\u0010\u000b\u001a\u00020\f*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"typeApproximator", "Lorg/jetbrains/kotlin/fir/types/ConeTypeApproximator;", "Lorg/jetbrains/kotlin/fir/FirSession;", "getTypeApproximator", "(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/types/ConeTypeApproximator;", "typeComponents", "Lorg/jetbrains/kotlin/fir/types/TypeComponents;", "getTypeComponents", "(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/types/TypeComponents;", "typeComponents$delegate", "Lorg/jetbrains/kotlin/util/ArrayMapAccessor;", "typeContext", "Lorg/jetbrains/kotlin/fir/types/ConeInferenceContext;", "getTypeContext", "(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/types/ConeInferenceContext;", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TypeComponentsKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(TypeComponentsKt.class, "providers"), "typeComponents", "getTypeComponents(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/types/TypeComponents;"))};
    private static final ArrayMapAccessor typeComponents$delegate = TypeRegistry.generateAccessor$default(FirSession.INSTANCE, Reflection.getOrCreateKotlinClass(TypeComponents.class), (Object) null, 2, (Object) null);

    private static final TypeComponents getTypeComponents(FirSession firSession) {
        return (TypeComponents) typeComponents$delegate.getValue((AbstractArrayMapOwner) firSession, $$delegatedProperties[0]);
    }

    public static final ConeInferenceContext getTypeContext(FirSession firSession) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        return getTypeComponents(firSession).getTypeContext();
    }

    public static final ConeTypeApproximator getTypeApproximator(FirSession firSession) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        return getTypeComponents(firSession).getTypeApproximator();
    }
}
