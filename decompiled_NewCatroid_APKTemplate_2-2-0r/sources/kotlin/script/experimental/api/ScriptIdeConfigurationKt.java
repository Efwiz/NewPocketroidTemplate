package kotlin.script.experimental.api;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.script.experimental.util.PropertiesCollection;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: scriptIdeConfiguration.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\"+\u0010\u0000\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001*\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006\"+\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00020\u0001*\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\b\u001a\u0004\b\u000b\u0010\u0006\"\u0015\u0010\r\u001a\u00020\u000e*\u00020\u000f8F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0012"}, d2 = {"acceptedLocations", "Lkotlin/script/experimental/util/PropertiesCollection$Key;", "", "Lkotlin/script/experimental/api/ScriptAcceptedLocation;", "Lkotlin/script/experimental/api/IdeScriptCompilationConfigurationKeys;", "getAcceptedLocations", "(Lkotlin/script/experimental/api/IdeScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;", "acceptedLocations$delegate", "Lkotlin/script/experimental/util/PropertiesCollection$PropertyKeyDelegate;", "dependenciesSources", "Lkotlin/script/experimental/api/ScriptDependency;", "getDependenciesSources", "dependenciesSources$delegate", "ide", "Lkotlin/script/experimental/api/IdeScriptCompilationConfigurationBuilder;", "Lkotlin/script/experimental/api/ScriptCompilationConfigurationKeys;", "getIde", "(Lkotlin/script/experimental/api/ScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/api/IdeScriptCompilationConfigurationBuilder;", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ScriptIdeConfigurationKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ScriptIdeConfigurationKt.class, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME), "dependenciesSources", "getDependenciesSources(Lkotlin/script/experimental/api/IdeScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ScriptIdeConfigurationKt.class, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME), "acceptedLocations", "getAcceptedLocations(Lkotlin/script/experimental/api/IdeScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;"))};
    private static final PropertiesCollection.PropertyKeyDelegate dependenciesSources$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, false, 3, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate acceptedLocations$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) CollectionsKt.listOf(ScriptAcceptedLocation.Everywhere), false, 2, (Object) null);

    public static final IdeScriptCompilationConfigurationBuilder getIde(ScriptCompilationConfigurationKeys scriptCompilationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptCompilationConfigurationKeys, "<this>");
        return new IdeScriptCompilationConfigurationBuilder();
    }

    public static final PropertiesCollection.Key<List<ScriptDependency>> getDependenciesSources(IdeScriptCompilationConfigurationKeys ideScriptCompilationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(ideScriptCompilationConfigurationKeys, "<this>");
        return dependenciesSources$delegate.getValue(ideScriptCompilationConfigurationKeys, $$delegatedProperties[0]);
    }

    public static final PropertiesCollection.Key<List<ScriptAcceptedLocation>> getAcceptedLocations(IdeScriptCompilationConfigurationKeys ideScriptCompilationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(ideScriptCompilationConfigurationKeys, "<this>");
        return acceptedLocations$delegate.getValue(ideScriptCompilationConfigurationKeys, $$delegatedProperties[1]);
    }
}
