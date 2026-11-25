package org.jetbrains.kotlin.fir.scopes;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationAttributes;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationDataRegistry;

/* JADX INFO: compiled from: FakeOverrideTypeCalculator.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\"3\u0010\u0002\u001a\u0004\u0018\u00010\u0001*\u00020\u00032\b\u0010\u0000\u001a\u0004\u0018\u00010\u00018F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\b\u0010\t\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007¨\u0006\n"}, d2 = {"<set-?>", "Lorg/jetbrains/kotlin/fir/scopes/FakeOverrideSubstitution;", "fakeOverrideSubstitution", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "getFakeOverrideSubstitution", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;)Lorg/jetbrains/kotlin/fir/scopes/FakeOverrideSubstitution;", "setFakeOverrideSubstitution", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;Lorg/jetbrains/kotlin/fir/scopes/FakeOverrideSubstitution;)V", "fakeOverrideSubstitution$delegate", "Lkotlin/properties/ReadWriteProperty;", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FakeOverrideTypeCalculatorKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(FakeOverrideTypeCalculatorKt.class, "providers"), "fakeOverrideSubstitution", "getFakeOverrideSubstitution(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;)Lorg/jetbrains/kotlin/fir/scopes/FakeOverrideSubstitution;"))};
    private static final ReadWriteProperty fakeOverrideSubstitution$delegate = FirDeclarationDataRegistry.INSTANCE.attributesAccessor(FakeOverrideSubstitutionKey.INSTANCE);

    public static final FakeOverrideSubstitution getFakeOverrideSubstitution(FirDeclarationAttributes firDeclarationAttributes) {
        Intrinsics.checkNotNullParameter(firDeclarationAttributes, "<this>");
        return (FakeOverrideSubstitution) fakeOverrideSubstitution$delegate.getValue(firDeclarationAttributes, $$delegatedProperties[0]);
    }

    public static final void setFakeOverrideSubstitution(FirDeclarationAttributes firDeclarationAttributes, FakeOverrideSubstitution fakeOverrideSubstitution) {
        Intrinsics.checkNotNullParameter(firDeclarationAttributes, "<this>");
        fakeOverrideSubstitution$delegate.setValue(firDeclarationAttributes, $$delegatedProperties[0], fakeOverrideSubstitution);
    }
}
