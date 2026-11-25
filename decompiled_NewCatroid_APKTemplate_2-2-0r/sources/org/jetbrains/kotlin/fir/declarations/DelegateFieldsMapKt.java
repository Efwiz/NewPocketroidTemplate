package org.jetbrains.kotlin.fir.declarations;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.symbols.impl.FirFieldSymbol;

/* JADX INFO: compiled from: DelegateFieldsMap.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\"K\u0010\u0004\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0001*\u00020\u00052\u0014\u0010\u0000\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u00018F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\n\u0010\u000b\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006\f"}, d2 = {"<set-?>", "", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFieldSymbol;", "delegateFieldsMap", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "getDelegateFieldsMap", "(Lorg/jetbrains/kotlin/fir/declarations/FirClass;)Ljava/util/Map;", "setDelegateFieldsMap", "(Lorg/jetbrains/kotlin/fir/declarations/FirClass;Ljava/util/Map;)V", "delegateFieldsMap$delegate", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationDataRegistry$DeclarationDataAccessor;", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DelegateFieldsMapKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(DelegateFieldsMapKt.class, Constants.TYPE_TREE), "delegateFieldsMap", "getDelegateFieldsMap(Lorg/jetbrains/kotlin/fir/declarations/FirClass;)Ljava/util/Map;"))};
    private static final FirDeclarationDataRegistry.DeclarationDataAccessor delegateFieldsMap$delegate = FirDeclarationDataRegistry.INSTANCE.data(DelegateFieldsMapKey.INSTANCE);

    public static final Map<Integer, FirFieldSymbol> getDelegateFieldsMap(FirClass firClass) {
        Intrinsics.checkNotNullParameter(firClass, "<this>");
        return (Map) delegateFieldsMap$delegate.getValue(firClass, $$delegatedProperties[0]);
    }

    public static final void setDelegateFieldsMap(FirClass firClass, Map<Integer, ? extends FirFieldSymbol> map) {
        Intrinsics.checkNotNullParameter(firClass, "<this>");
        delegateFieldsMap$delegate.setValue(firClass, $$delegatedProperties[0], map);
    }
}
