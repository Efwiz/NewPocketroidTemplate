package org.jetbrains.kotlin.fir.builder;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.fir.extensions.FirExtensionService;
import org.jetbrains.kotlin.util.AbstractArrayMapOwner;
import org.jetbrains.kotlin.util.ArrayMapAccessor;

/* JADX INFO: compiled from: FirScriptConfiguratorExtension.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\"%\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0006\u0010\u0007\u001a\u0004\b\u0004\u0010\u0005¨\u0006\b"}, d2 = {"scriptConfigurators", "", "Lorg/jetbrains/kotlin/fir/builder/FirScriptConfiguratorExtension;", "Lorg/jetbrains/kotlin/fir/extensions/FirExtensionService;", "getScriptConfigurators", "(Lorg/jetbrains/kotlin/fir/extensions/FirExtensionService;)Ljava/util/List;", "scriptConfigurators$delegate", "Lorg/jetbrains/kotlin/util/ArrayMapAccessor;", "raw-fir.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirScriptConfiguratorExtensionKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(FirScriptConfiguratorExtensionKt.class, "raw-fir.common"), "scriptConfigurators", "getScriptConfigurators(Lorg/jetbrains/kotlin/fir/extensions/FirExtensionService;)Ljava/util/List;"))};
    private static final ArrayMapAccessor scriptConfigurators$delegate;

    static {
        FirExtensionService.Companion companion = FirExtensionService.INSTANCE;
        KClass orCreateKotlinClass = Reflection.getOrCreateKotlinClass(FirScriptConfiguratorExtension.class);
        List listEmptyList = CollectionsKt.emptyList();
        Intrinsics.checkNotNull(listEmptyList, "null cannot be cast to non-null type V of org.jetbrains.kotlin.fir.extensions.FirExtensionService.Companion.registeredExtensions");
        scriptConfigurators$delegate = companion.generateAccessor(orCreateKotlinClass, listEmptyList);
    }

    public static final List<FirScriptConfiguratorExtension> getScriptConfigurators(FirExtensionService firExtensionService) {
        Intrinsics.checkNotNullParameter(firExtensionService, "<this>");
        return (List) scriptConfigurators$delegate.getValue((AbstractArrayMapOwner) firExtensionService, $$delegatedProperties[0]);
    }
}
