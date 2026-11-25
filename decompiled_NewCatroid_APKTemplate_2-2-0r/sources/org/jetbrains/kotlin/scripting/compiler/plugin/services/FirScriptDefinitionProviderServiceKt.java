package org.jetbrains.kotlin.scripting.compiler.plugin.services;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.util.AbstractArrayMapOwner;
import org.jetbrains.kotlin.util.NullableArrayMapAccessor;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: FirScriptDefinitionProviderService.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\"!\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0007"}, d2 = {"scriptDefinitionProviderService", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/services/FirScriptDefinitionProviderService;", "Lorg/jetbrains/kotlin/fir/FirSession;", "getScriptDefinitionProviderService", "(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/scripting/compiler/plugin/services/FirScriptDefinitionProviderService;", "scriptDefinitionProviderService$delegate", "Lorg/jetbrains/kotlin/util/NullableArrayMapAccessor;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirScriptDefinitionProviderServiceKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(FirScriptDefinitionProviderServiceKt.class, PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME), "scriptDefinitionProviderService", "getScriptDefinitionProviderService(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/scripting/compiler/plugin/services/FirScriptDefinitionProviderService;"))};
    private static final NullableArrayMapAccessor scriptDefinitionProviderService$delegate = FirSession.INSTANCE.generateNullableAccessor(Reflection.getOrCreateKotlinClass(FirScriptDefinitionProviderService.class));

    public static final FirScriptDefinitionProviderService getScriptDefinitionProviderService(FirSession firSession) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        return (FirScriptDefinitionProviderService) scriptDefinitionProviderService$delegate.getValue((AbstractArrayMapOwner) firSession, $$delegatedProperties[0]);
    }
}
