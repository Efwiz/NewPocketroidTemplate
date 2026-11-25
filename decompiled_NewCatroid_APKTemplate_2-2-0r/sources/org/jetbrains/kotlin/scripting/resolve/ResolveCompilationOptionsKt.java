package org.jetbrains.kotlin.scripting.resolve;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.script.experimental.api.KotlinType;
import kotlin.script.experimental.api.ScriptCompilationConfigurationKeys;
import kotlin.script.experimental.util.PropertiesCollection;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: resolveCompilationOptions.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\"+\u0010\u0000\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001*\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006\"+\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001*\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\b\u001a\u0004\b\n\u0010\u0006¨\u0006\f"}, d2 = {"skipExtensionsResolutionForImplicits", "Lkotlin/script/experimental/util/PropertiesCollection$Key;", "", "Lkotlin/script/experimental/api/KotlinType;", "Lkotlin/script/experimental/api/ScriptCompilationConfigurationKeys;", "getSkipExtensionsResolutionForImplicits", "(Lkotlin/script/experimental/api/ScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;", "skipExtensionsResolutionForImplicits$delegate", "Lkotlin/script/experimental/util/PropertiesCollection$PropertyKeyDelegate;", "skipExtensionsResolutionForImplicitsExceptInnermost", "getSkipExtensionsResolutionForImplicitsExceptInnermost", "skipExtensionsResolutionForImplicitsExceptInnermost$delegate", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ResolveCompilationOptionsKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ResolveCompilationOptionsKt.class, PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME), "skipExtensionsResolutionForImplicits", "getSkipExtensionsResolutionForImplicits(Lkotlin/script/experimental/api/ScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ResolveCompilationOptionsKt.class, PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME), "skipExtensionsResolutionForImplicitsExceptInnermost", "getSkipExtensionsResolutionForImplicitsExceptInnermost(Lkotlin/script/experimental/api/ScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;"))};
    private static final PropertiesCollection.PropertyKeyDelegate skipExtensionsResolutionForImplicits$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) CollectionsKt.emptyList(), false, 2, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate skipExtensionsResolutionForImplicitsExceptInnermost$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) CollectionsKt.emptyList(), false, 2, (Object) null);

    public static final PropertiesCollection.Key<Collection<KotlinType>> getSkipExtensionsResolutionForImplicits(ScriptCompilationConfigurationKeys scriptCompilationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptCompilationConfigurationKeys, "<this>");
        return skipExtensionsResolutionForImplicits$delegate.getValue(scriptCompilationConfigurationKeys, $$delegatedProperties[0]);
    }

    public static final PropertiesCollection.Key<Collection<KotlinType>> getSkipExtensionsResolutionForImplicitsExceptInnermost(ScriptCompilationConfigurationKeys scriptCompilationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptCompilationConfigurationKeys, "<this>");
        return skipExtensionsResolutionForImplicitsExceptInnermost$delegate.getValue(scriptCompilationConfigurationKeys, $$delegatedProperties[1]);
    }
}
