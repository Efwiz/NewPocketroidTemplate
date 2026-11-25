package org.jetbrains.kotlin.fir.scopes.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.util.AbstractArrayMapOwner;
import org.jetbrains.kotlin.util.ArrayMapAccessor;
import org.jetbrains.kotlin.util.TypeRegistry;

/* JADX INFO: compiled from: FirClassSubstitutionScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\"\u001f\u0010\u0000\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0007"}, d2 = {"substitutionOverrideStorage", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirSubstitutionOverrideStorage;", "Lorg/jetbrains/kotlin/fir/FirSession;", "getSubstitutionOverrideStorage", "(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/scopes/impl/FirSubstitutionOverrideStorage;", "substitutionOverrideStorage$delegate", "Lorg/jetbrains/kotlin/util/ArrayMapAccessor;", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirClassSubstitutionScopeKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(FirClassSubstitutionScopeKt.class, "providers"), "substitutionOverrideStorage", "getSubstitutionOverrideStorage(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/scopes/impl/FirSubstitutionOverrideStorage;"))};
    private static final ArrayMapAccessor substitutionOverrideStorage$delegate = TypeRegistry.generateAccessor$default(FirSession.INSTANCE, Reflection.getOrCreateKotlinClass(FirSubstitutionOverrideStorage.class), (Object) null, 2, (Object) null);

    /* JADX INFO: Access modifiers changed from: private */
    public static final FirSubstitutionOverrideStorage getSubstitutionOverrideStorage(FirSession firSession) {
        return (FirSubstitutionOverrideStorage) substitutionOverrideStorage$delegate.getValue((AbstractArrayMapOwner) firSession, $$delegatedProperties[0]);
    }
}
