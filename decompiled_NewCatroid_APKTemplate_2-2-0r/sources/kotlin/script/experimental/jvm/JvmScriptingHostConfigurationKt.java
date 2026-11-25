package kotlin.script.experimental.jvm;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.script.experimental.host.HostConfigurationKt;
import kotlin.script.experimental.host.ScriptingHostConfiguration;
import kotlin.script.experimental.host.ScriptingHostConfigurationKeys;
import kotlin.script.experimental.jvm.impl.BridgeDependenciesResolverKt;
import kotlin.script.experimental.util.PropertiesCollection;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: jvmScriptingHostConfiguration.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\"\u0011\u0010\u0000\u001a\u00020\u00018F¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0003\"%\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005*\u00020\u00078FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\t\"+\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u0005*\u00020\u00078FX\u0087\u0084\u0002¢\u0006\u0012\n\u0004\b\u0011\u0010\u000b\u0012\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\t\"%\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\r0\u0005*\u00020\u00078FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0014\u0010\u000b\u001a\u0004\b\u0013\u0010\t\"\u001b\u0010\u0015\u001a\u00020\u0016*\u00020\u00178F¢\u0006\f\u0012\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001b¨\u0006\u001c"}, d2 = {"defaultJvmScriptingHostConfiguration", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "getDefaultJvmScriptingHostConfiguration", "()Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "baseClassLoader", "Lkotlin/script/experimental/util/PropertiesCollection$Key;", "Ljava/lang/ClassLoader;", "Lkotlin/script/experimental/jvm/JvmScriptingHostConfigurationKeys;", "getBaseClassLoader", "(Lkotlin/script/experimental/jvm/JvmScriptingHostConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;", "baseClassLoader$delegate", "Lkotlin/script/experimental/util/PropertiesCollection$PropertyKeyDelegate;", "javaHome", "Ljava/io/File;", "getJavaHome$annotations", "(Lkotlin/script/experimental/jvm/JvmScriptingHostConfigurationKeys;)V", "getJavaHome", "javaHome$delegate", "jdkHome", "getJdkHome", "jdkHome$delegate", "jvm", "Lkotlin/script/experimental/jvm/JvmScriptingHostConfigurationBuilder;", "Lkotlin/script/experimental/host/ScriptingHostConfigurationKeys;", "getJvm$annotations", "(Lkotlin/script/experimental/host/ScriptingHostConfigurationKeys;)V", "getJvm", "(Lkotlin/script/experimental/host/ScriptingHostConfigurationKeys;)Lkotlin/script/experimental/jvm/JvmScriptingHostConfigurationBuilder;", PathUtil.KOTLIN_SCRIPTING_JVM_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmScriptingHostConfigurationKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(JvmScriptingHostConfigurationKt.class, PathUtil.KOTLIN_SCRIPTING_JVM_NAME), "javaHome", "getJavaHome(Lkotlin/script/experimental/jvm/JvmScriptingHostConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(JvmScriptingHostConfigurationKt.class, PathUtil.KOTLIN_SCRIPTING_JVM_NAME), "jdkHome", "getJdkHome(Lkotlin/script/experimental/jvm/JvmScriptingHostConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(JvmScriptingHostConfigurationKt.class, PathUtil.KOTLIN_SCRIPTING_JVM_NAME), "baseClassLoader", "getBaseClassLoader(Lkotlin/script/experimental/jvm/JvmScriptingHostConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;"))};
    private static final PropertiesCollection.PropertyKeyDelegate javaHome$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) new File(System.getProperty("java.home")), false, 2, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate jdkHome$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, false, 3, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate baseClassLoader$delegate = PropertiesCollection.INSTANCE.key((Function1) new Function1<PropertiesCollection, ClassLoader>() { // from class: kotlin.script.experimental.jvm.JvmScriptingHostConfigurationKt$baseClassLoader$2
        @Override // kotlin.jvm.functions.Function1
        public final ClassLoader invoke(PropertiesCollection key) {
            Intrinsics.checkNotNullParameter(key, "$this$key");
            List list = (List) key.get(HostConfigurationKt.getConfigurationDependencies(ScriptingHostConfiguration.INSTANCE));
            URLClassLoader uRLClassLoader = null;
            if (list != null) {
                List<File> classPathOrEmpty = BridgeDependenciesResolverKt.toClassPathOrEmpty(list);
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(classPathOrEmpty, 10));
                Iterator<T> it = classPathOrEmpty.iterator();
                while (it.hasNext()) {
                    arrayList.add(((File) it.next()).toURI().toURL());
                }
                uRLClassLoader = new URLClassLoader((URL[]) arrayList.toArray(new URL[0]), null);
            }
            return uRLClassLoader;
        }
    }, true);

    @Deprecated(message = "Unused")
    public static /* synthetic */ void getJavaHome$annotations(JvmScriptingHostConfigurationKeys jvmScriptingHostConfigurationKeys) {
    }

    public static /* synthetic */ void getJvm$annotations(ScriptingHostConfigurationKeys scriptingHostConfigurationKeys) {
    }

    public static final PropertiesCollection.Key<File> getJavaHome(JvmScriptingHostConfigurationKeys jvmScriptingHostConfigurationKeys) {
        Intrinsics.checkNotNullParameter(jvmScriptingHostConfigurationKeys, "<this>");
        return javaHome$delegate.getValue(jvmScriptingHostConfigurationKeys, $$delegatedProperties[0]);
    }

    public static final PropertiesCollection.Key<File> getJdkHome(JvmScriptingHostConfigurationKeys jvmScriptingHostConfigurationKeys) {
        Intrinsics.checkNotNullParameter(jvmScriptingHostConfigurationKeys, "<this>");
        return jdkHome$delegate.getValue(jvmScriptingHostConfigurationKeys, $$delegatedProperties[1]);
    }

    public static final PropertiesCollection.Key<ClassLoader> getBaseClassLoader(JvmScriptingHostConfigurationKeys jvmScriptingHostConfigurationKeys) {
        Intrinsics.checkNotNullParameter(jvmScriptingHostConfigurationKeys, "<this>");
        return baseClassLoader$delegate.getValue(jvmScriptingHostConfigurationKeys, $$delegatedProperties[2]);
    }

    public static final JvmScriptingHostConfigurationBuilder getJvm(ScriptingHostConfigurationKeys scriptingHostConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptingHostConfigurationKeys, "<this>");
        return new JvmScriptingHostConfigurationBuilder();
    }

    public static final ScriptingHostConfiguration getDefaultJvmScriptingHostConfiguration() {
        return new ScriptingHostConfiguration(new Function1<ScriptingHostConfiguration.Builder, Unit>() { // from class: kotlin.script.experimental.jvm.JvmScriptingHostConfigurationKt$defaultJvmScriptingHostConfiguration$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ScriptingHostConfiguration.Builder builder) {
                invoke2(builder);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ScriptingHostConfiguration.Builder $receiver) {
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                $receiver.invoke((PropertiesCollection.Key<JvmGetScriptingClass>) HostConfigurationKt.getGetScriptingClass($receiver), new JvmGetScriptingClass());
            }
        });
    }
}
