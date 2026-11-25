package kotlin.script.experimental.jvm;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.reflect.KProperty;
import kotlin.script.experimental.api.EvaluationResult;
import kotlin.script.experimental.api.ScriptEvaluationConfiguration;
import kotlin.script.experimental.api.ScriptEvaluationConfigurationKeys;
import kotlin.script.experimental.api.ScriptEvaluationKt;
import kotlin.script.experimental.host.ScriptingHostConfiguration;
import kotlin.script.experimental.util.PropertiesCollection;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: jvmScriptEvaluation.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000B\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\"'\u0010\u0000\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001*\u00020\u00038@X\u0080\u0084\u0002¢\u0006\f\n\u0004\b\u0006\u0010\u0007\u001a\u0004\b\u0004\u0010\u0005\"'\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001*\u00020\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u0007\u001a\u0004\b\t\u0010\u0005\"\u0015\u0010\u000b\u001a\u00020\f*\u00020\r8F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f\"'\u0010\u0010\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001*\u00020\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\u0007\u001a\u0004\b\u0011\u0010\u0005\"%\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u0001*\u00020\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0016\u0010\u0007\u001a\u0004\b\u0015\u0010\u0005\"-\u0010\u0017\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00190\u00180\u0001*\u00020\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001b\u0010\u0007\u001a\u0004\b\u001a\u0010\u0005\"5\u0010\u001c\u001a\u0018\u0012\u0014\u0012\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001e\u0012\u0004\u0012\u00020\u001f0\u001d0\u0001*\u00020\u00038@X\u0080\u0084\u0002¢\u0006\f\n\u0004\b!\u0010\u0007\u001a\u0004\b \u0010\u0005¨\u0006\""}, d2 = {"actualClassLoader", "Lkotlin/script/experimental/util/PropertiesCollection$Key;", "Ljava/lang/ClassLoader;", "Lkotlin/script/experimental/jvm/JvmScriptEvaluationConfigurationKeys;", "getActualClassLoader", "(Lkotlin/script/experimental/jvm/JvmScriptEvaluationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;", "actualClassLoader$delegate", "Lkotlin/script/experimental/util/PropertiesCollection$PropertyKeyDelegate;", "baseClassLoader", "getBaseClassLoader", "baseClassLoader$delegate", "jvm", "Lkotlin/script/experimental/jvm/JvmScriptEvaluationConfigurationBuilder;", "Lkotlin/script/experimental/api/ScriptEvaluationConfigurationKeys;", "getJvm", "(Lkotlin/script/experimental/api/ScriptEvaluationConfigurationKeys;)Lkotlin/script/experimental/jvm/JvmScriptEvaluationConfigurationBuilder;", "lastSnippetClassLoader", "getLastSnippetClassLoader", "lastSnippetClassLoader$delegate", "loadDependencies", "", "getLoadDependencies", "loadDependencies$delegate", "mainArguments", "", "", "getMainArguments", "mainArguments$delegate", "scriptsInstancesSharingMap", "", "Lkotlin/reflect/KClass;", "Lkotlin/script/experimental/api/EvaluationResult;", "getScriptsInstancesSharingMap", "scriptsInstancesSharingMap$delegate", PathUtil.KOTLIN_SCRIPTING_JVM_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmScriptEvaluationKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(JvmScriptEvaluationKt.class, PathUtil.KOTLIN_SCRIPTING_JVM_NAME), "baseClassLoader", "getBaseClassLoader(Lkotlin/script/experimental/jvm/JvmScriptEvaluationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(JvmScriptEvaluationKt.class, PathUtil.KOTLIN_SCRIPTING_JVM_NAME), "lastSnippetClassLoader", "getLastSnippetClassLoader(Lkotlin/script/experimental/jvm/JvmScriptEvaluationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(JvmScriptEvaluationKt.class, PathUtil.KOTLIN_SCRIPTING_JVM_NAME), "loadDependencies", "getLoadDependencies(Lkotlin/script/experimental/jvm/JvmScriptEvaluationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(JvmScriptEvaluationKt.class, PathUtil.KOTLIN_SCRIPTING_JVM_NAME), "mainArguments", "getMainArguments(Lkotlin/script/experimental/jvm/JvmScriptEvaluationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(JvmScriptEvaluationKt.class, PathUtil.KOTLIN_SCRIPTING_JVM_NAME), "actualClassLoader", "getActualClassLoader(Lkotlin/script/experimental/jvm/JvmScriptEvaluationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(JvmScriptEvaluationKt.class, PathUtil.KOTLIN_SCRIPTING_JVM_NAME), "scriptsInstancesSharingMap", "getScriptsInstancesSharingMap(Lkotlin/script/experimental/jvm/JvmScriptEvaluationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;"))};
    private static final PropertiesCollection.PropertyKeyDelegate baseClassLoader$delegate = PropertiesCollection.INSTANCE.key((Function1) new Function1<PropertiesCollection, ClassLoader>() { // from class: kotlin.script.experimental.jvm.JvmScriptEvaluationKt$baseClassLoader$2
        @Override // kotlin.jvm.functions.Function1
        public final ClassLoader invoke(PropertiesCollection key) {
            ClassLoader classLoader;
            Intrinsics.checkNotNullParameter(key, "$this$key");
            ScriptingHostConfiguration scriptingHostConfiguration = (ScriptingHostConfiguration) key.get(ScriptEvaluationKt.getHostConfiguration(ScriptEvaluationConfiguration.INSTANCE));
            return (scriptingHostConfiguration == null || (classLoader = (ClassLoader) scriptingHostConfiguration.get(JvmScriptingHostConfigurationKt.getBaseClassLoader(JvmScriptingHostConfigurationKt.getJvm(ScriptingHostConfiguration.INSTANCE)))) == null) ? Thread.currentThread().getContextClassLoader() : classLoader;
        }
    }, true);
    private static final PropertiesCollection.PropertyKeyDelegate lastSnippetClassLoader$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, true, 1, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate loadDependencies$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) true, false, 2, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate mainArguments$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, false, 3, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate actualClassLoader$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, true, 1, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate scriptsInstancesSharingMap$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, true, 1, (Object) null);

    public static final PropertiesCollection.Key<ClassLoader> getBaseClassLoader(JvmScriptEvaluationConfigurationKeys jvmScriptEvaluationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(jvmScriptEvaluationConfigurationKeys, "<this>");
        return baseClassLoader$delegate.getValue(jvmScriptEvaluationConfigurationKeys, $$delegatedProperties[0]);
    }

    public static final PropertiesCollection.Key<ClassLoader> getLastSnippetClassLoader(JvmScriptEvaluationConfigurationKeys jvmScriptEvaluationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(jvmScriptEvaluationConfigurationKeys, "<this>");
        return lastSnippetClassLoader$delegate.getValue(jvmScriptEvaluationConfigurationKeys, $$delegatedProperties[1]);
    }

    public static final PropertiesCollection.Key<Boolean> getLoadDependencies(JvmScriptEvaluationConfigurationKeys jvmScriptEvaluationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(jvmScriptEvaluationConfigurationKeys, "<this>");
        return loadDependencies$delegate.getValue(jvmScriptEvaluationConfigurationKeys, $$delegatedProperties[2]);
    }

    public static final PropertiesCollection.Key<String[]> getMainArguments(JvmScriptEvaluationConfigurationKeys jvmScriptEvaluationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(jvmScriptEvaluationConfigurationKeys, "<this>");
        return mainArguments$delegate.getValue(jvmScriptEvaluationConfigurationKeys, $$delegatedProperties[3]);
    }

    public static final PropertiesCollection.Key<ClassLoader> getActualClassLoader(JvmScriptEvaluationConfigurationKeys jvmScriptEvaluationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(jvmScriptEvaluationConfigurationKeys, "<this>");
        return actualClassLoader$delegate.getValue(jvmScriptEvaluationConfigurationKeys, $$delegatedProperties[4]);
    }

    public static final PropertiesCollection.Key<Map<KClass<?>, EvaluationResult>> getScriptsInstancesSharingMap(JvmScriptEvaluationConfigurationKeys jvmScriptEvaluationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(jvmScriptEvaluationConfigurationKeys, "<this>");
        return scriptsInstancesSharingMap$delegate.getValue(jvmScriptEvaluationConfigurationKeys, $$delegatedProperties[5]);
    }

    public static final JvmScriptEvaluationConfigurationBuilder getJvm(ScriptEvaluationConfigurationKeys scriptEvaluationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptEvaluationConfigurationKeys, "<this>");
        return new JvmScriptEvaluationConfigurationBuilder();
    }
}
