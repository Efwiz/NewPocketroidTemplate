package org.jetbrains.kotlin.fir;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.util.AbstractArrayMapOwner;
import org.jetbrains.kotlin.util.ArrayMapAccessor;
import org.jetbrains.kotlin.util.TypeRegistry;

/* JADX INFO: compiled from: FirLanguageSettingsComponent.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u001f\u0010\u0000\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0007\u001a\u00020\b*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"languageSettingsComponent", "Lorg/jetbrains/kotlin/fir/FirLanguageSettingsComponent;", "Lorg/jetbrains/kotlin/fir/FirSession;", "getLanguageSettingsComponent", "(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/FirLanguageSettingsComponent;", "languageSettingsComponent$delegate", "Lorg/jetbrains/kotlin/util/ArrayMapAccessor;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getLanguageVersionSettings", "(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirLanguageSettingsComponentKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(FirLanguageSettingsComponentKt.class, Constants.TYPE_TREE), "languageSettingsComponent", "getLanguageSettingsComponent(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/FirLanguageSettingsComponent;"))};
    private static final ArrayMapAccessor languageSettingsComponent$delegate = TypeRegistry.generateAccessor$default(FirSession.INSTANCE, Reflection.getOrCreateKotlinClass(FirLanguageSettingsComponent.class), (Object) null, 2, (Object) null);

    private static final FirLanguageSettingsComponent getLanguageSettingsComponent(FirSession firSession) {
        return (FirLanguageSettingsComponent) languageSettingsComponent$delegate.getValue((AbstractArrayMapOwner) firSession, $$delegatedProperties[0]);
    }

    public static final LanguageVersionSettings getLanguageVersionSettings(FirSession firSession) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        return getLanguageSettingsComponent(firSession).getLanguageVersionSettings();
    }
}
