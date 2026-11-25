package org.jetbrains.kotlin.scripting.definitions;

import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.script.experimental.api.KotlinType;
import kotlin.script.experimental.api.ScriptCompilationConfigurationKeys;
import kotlin.script.experimental.host.ScriptingHostConfigurationKeys;
import kotlin.script.experimental.util.PropertiesCollection;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ScriptCompilationConfigurationFromDefinition.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\"+\u0010\u0000\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001*\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006\"A\u0010\t\u001a$\u0012 \u0012\u001e\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\r\u0018\u00010\u000bj\u0004\u0018\u0001`\u000e0\n0\u0001*\u00020\u000f8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\b\u001a\u0004\b\u0010\u0010\u0011\"%\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\f0\u0001*\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\b\u001a\u0004\b\u0014\u0010\u0006¨\u0006\u0016"}, d2 = {"annotationsForSamWithReceivers", "Lkotlin/script/experimental/util/PropertiesCollection$Key;", "", "Lkotlin/script/experimental/api/KotlinType;", "Lkotlin/script/experimental/api/ScriptCompilationConfigurationKeys;", "getAnnotationsForSamWithReceivers", "(Lkotlin/script/experimental/api/ScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;", "annotationsForSamWithReceivers$delegate", "Lkotlin/script/experimental/util/PropertiesCollection$PropertyKeyDelegate;", "getEnvironment", "Lkotlin/Function0;", "", "", "", "Lkotlin/script/dependencies/Environment;", "Lkotlin/script/experimental/host/ScriptingHostConfigurationKeys;", "getGetEnvironment", "(Lkotlin/script/experimental/host/ScriptingHostConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;", "getEnvironment$delegate", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "getPlatform", "platform$delegate", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ScriptCompilationConfigurationFromDefinitionKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ScriptCompilationConfigurationFromDefinitionKt.class, PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME), "annotationsForSamWithReceivers", "getAnnotationsForSamWithReceivers(Lkotlin/script/experimental/api/ScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ScriptCompilationConfigurationFromDefinitionKt.class, PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME), NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "getPlatform(Lkotlin/script/experimental/api/ScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ScriptCompilationConfigurationFromDefinitionKt.class, PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME), "getEnvironment", "getGetEnvironment(Lkotlin/script/experimental/host/ScriptingHostConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;"))};
    private static final PropertiesCollection.PropertyKeyDelegate annotationsForSamWithReceivers$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, false, 3, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate platform$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, false, 3, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate getEnvironment$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, false, 3, (Object) null);

    public static final PropertiesCollection.Key<List<KotlinType>> getAnnotationsForSamWithReceivers(ScriptCompilationConfigurationKeys scriptCompilationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptCompilationConfigurationKeys, "<this>");
        return annotationsForSamWithReceivers$delegate.getValue(scriptCompilationConfigurationKeys, $$delegatedProperties[0]);
    }

    public static final PropertiesCollection.Key<String> getPlatform(ScriptCompilationConfigurationKeys scriptCompilationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptCompilationConfigurationKeys, "<this>");
        return platform$delegate.getValue(scriptCompilationConfigurationKeys, $$delegatedProperties[1]);
    }

    public static final PropertiesCollection.Key<Function0<Map<String, Object>>> getGetEnvironment(ScriptingHostConfigurationKeys scriptingHostConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptingHostConfigurationKeys, "<this>");
        return getEnvironment$delegate.getValue(scriptingHostConfigurationKeys, $$delegatedProperties[2]);
    }
}
