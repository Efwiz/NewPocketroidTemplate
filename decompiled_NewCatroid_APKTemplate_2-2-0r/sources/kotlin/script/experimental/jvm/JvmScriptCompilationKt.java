package kotlin.script.experimental.jvm;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.reflect.KProperty;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.ScriptCompilationConfigurationKeys;
import kotlin.script.experimental.api.ScriptCompilationKt;
import kotlin.script.experimental.api.ScriptDependency;
import kotlin.script.experimental.host.ScriptingHostConfiguration;
import kotlin.script.experimental.jvm.util.JvmClasspathUtilKt;
import kotlin.script.experimental.util.PropertiesCollection;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: jvmScriptCompilation.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000r\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a9\u0010\u0019\u001a\u00020\u001a*\u00020\u000e2\n\u0010\u001b\u001a\u0006\u0012\u0002\b\u00030\u001c2\u0012\u0010\u001d\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00150\u001e\"\u00020\u00152\b\b\u0002\u0010\u001f\u001a\u00020 ¢\u0006\u0002\u0010!\u001aA\u0010\"\u001a\u00020\u001a*\u00020\u000e2\u0012\u0010\u001d\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00150\u001e\"\u00020\u00152\b\b\u0002\u0010#\u001a\u00020$2\b\b\u0002\u0010\u001f\u001a\u00020 2\b\b\u0002\u0010%\u001a\u00020 ¢\u0006\u0002\u0010&\u001a7\u0010'\u001a\u00020\u001a*\u00020\u000e2\u0012\u0010\u001d\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00150\u001e\"\u00020\u00152\b\b\u0002\u0010\u001f\u001a\u00020 2\b\b\u0002\u0010%\u001a\u00020 ¢\u0006\u0002\u0010(\u001a*\u0010)\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010**\b\u0012\u0004\u0012\u00020\u00020+2\u000e\u0010,\u001a\n\u0012\u0004\u0012\u00020-\u0018\u00010+H\u0002\u001a\u001a\u0010.\u001a\u00020\u001a*\u00020/2\u000e\u00100\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010+\u001a\u001a\u0010.\u001a\u00020\u001a*\u00020\u000e2\u000e\u00100\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010+\u001a\u001c\u00101\u001a\u00020\u001a*\u0002022\u000e\u00100\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010+H\u0002\u001a\u0018\u00103\u001a\u000204*\u0002042\f\u00100\u001a\b\u0012\u0004\u0012\u00020\u00020+\"+\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00038FX\u0087\u0084\u0002¢\u0006\u0012\n\u0004\b\b\u0010\t\u0012\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\"%\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\t\u001a\u0004\b\u000b\u0010\u0007\"\u001b\u0010\r\u001a\u00020\u000e*\u00020\u000f8F¢\u0006\f\u0012\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013\"%\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u0001*\u00020\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0016\u0010\u0007*\"\u00105\"\u000e\u0012\u0004\u0012\u000204\u0012\u0004\u0012\u00020$062\u000e\u0012\u0004\u0012\u000204\u0012\u0004\u0012\u00020$06¨\u00067"}, d2 = {"javaHome", "Lkotlin/script/experimental/util/PropertiesCollection$Key;", "Ljava/io/File;", "Lkotlin/script/experimental/jvm/JvmScriptCompilationConfigurationKeys;", "getJavaHome$annotations", "(Lkotlin/script/experimental/jvm/JvmScriptCompilationConfigurationKeys;)V", "getJavaHome", "(Lkotlin/script/experimental/jvm/JvmScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;", "javaHome$delegate", "Lkotlin/script/experimental/util/PropertiesCollection$PropertyKeyCopyDelegate;", "jdkHome", "getJdkHome", "jdkHome$delegate", "jvm", "Lkotlin/script/experimental/jvm/JvmScriptCompilationConfigurationBuilder;", "Lkotlin/script/experimental/api/ScriptCompilationConfigurationKeys;", "getJvm$annotations", "(Lkotlin/script/experimental/api/ScriptCompilationConfigurationKeys;)V", "getJvm", "(Lkotlin/script/experimental/api/ScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/jvm/JvmScriptCompilationConfigurationBuilder;", "jvmTarget", "", "getJvmTarget", "jvmTarget$delegate", "Lkotlin/script/experimental/util/PropertiesCollection$PropertyKeyDelegate;", "dependenciesFromClassContext", "", "contextClass", "Lkotlin/reflect/KClass;", "libraries", "", "wholeClasspath", "", "(Lkotlin/script/experimental/jvm/JvmScriptCompilationConfigurationBuilder;Lkotlin/reflect/KClass;[Ljava/lang/String;Z)V", "dependenciesFromClassloader", "classLoader", "Ljava/lang/ClassLoader;", "unpackJarCollections", "(Lkotlin/script/experimental/jvm/JvmScriptCompilationConfigurationBuilder;[Ljava/lang/String;Ljava/lang/ClassLoader;ZZ)V", "dependenciesFromCurrentContext", "(Lkotlin/script/experimental/jvm/JvmScriptCompilationConfigurationBuilder;[Ljava/lang/String;ZZ)V", "filterNewClasspath", "", "", "known", "Lkotlin/script/experimental/api/ScriptDependency;", "updateClasspath", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration$Builder;", ModuleXmlParser.CLASSPATH, "updateClasspathImpl", "Lkotlin/script/experimental/util/PropertiesCollection$Builder;", "withUpdatedClasspath", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "ClassLoaderByConfiguration", "Lkotlin/Function1;", PathUtil.KOTLIN_SCRIPTING_JVM_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmScriptCompilationKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(JvmScriptCompilationKt.class, PathUtil.KOTLIN_SCRIPTING_JVM_NAME), "javaHome", "getJavaHome(Lkotlin/script/experimental/jvm/JvmScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(JvmScriptCompilationKt.class, PathUtil.KOTLIN_SCRIPTING_JVM_NAME), "jdkHome", "getJdkHome(Lkotlin/script/experimental/jvm/JvmScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(JvmScriptCompilationKt.class, PathUtil.KOTLIN_SCRIPTING_JVM_NAME), "jvmTarget", "getJvmTarget(Lkotlin/script/experimental/jvm/JvmScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;"))};
    private static final PropertiesCollection.PropertyKeyCopyDelegate javaHome$delegate = PropertiesCollection.Companion.keyCopy$default(PropertiesCollection.INSTANCE, JvmScriptingHostConfigurationKt.getJavaHome(JvmScriptingHostConfigurationKt.getJvm(ScriptingHostConfiguration.INSTANCE)), null, 2, null);
    private static final PropertiesCollection.PropertyKeyCopyDelegate jdkHome$delegate = PropertiesCollection.INSTANCE.keyCopy(JvmScriptingHostConfigurationKt.getJdkHome(JvmScriptingHostConfigurationKt.getJvm(ScriptingHostConfiguration.INSTANCE)), new Function1<PropertiesCollection, PropertiesCollection>() { // from class: kotlin.script.experimental.jvm.JvmScriptCompilationKt$jdkHome$2
        @Override // kotlin.jvm.functions.Function1
        public final PropertiesCollection invoke(PropertiesCollection keyCopy) {
            Intrinsics.checkNotNullParameter(keyCopy, "$this$keyCopy");
            return (PropertiesCollection) keyCopy.get(ScriptCompilationKt.getHostConfiguration(ScriptCompilationConfiguration.INSTANCE));
        }
    });
    private static final PropertiesCollection.PropertyKeyDelegate jvmTarget$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, false, 3, (Object) null);

    @Deprecated(message = "Unused")
    public static /* synthetic */ void getJavaHome$annotations(JvmScriptCompilationConfigurationKeys jvmScriptCompilationConfigurationKeys) {
    }

    public static /* synthetic */ void getJvm$annotations(ScriptCompilationConfigurationKeys scriptCompilationConfigurationKeys) {
    }

    public static /* synthetic */ void dependenciesFromClassContext$default(JvmScriptCompilationConfigurationBuilder jvmScriptCompilationConfigurationBuilder, KClass kClass, String[] strArr, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        dependenciesFromClassContext(jvmScriptCompilationConfigurationBuilder, kClass, strArr, z);
    }

    public static final void dependenciesFromClassContext(JvmScriptCompilationConfigurationBuilder jvmScriptCompilationConfigurationBuilder, KClass<?> contextClass, String[] libraries, boolean z) {
        Intrinsics.checkNotNullParameter(jvmScriptCompilationConfigurationBuilder, "<this>");
        Intrinsics.checkNotNullParameter(contextClass, "contextClass");
        Intrinsics.checkNotNullParameter(libraries, "libraries");
        String[] strArr = (String[]) Arrays.copyOf(libraries, libraries.length);
        ClassLoader classLoader = JvmClassMappingKt.getJavaClass((KClass) contextClass).getClassLoader();
        Intrinsics.checkNotNullExpressionValue(classLoader, "getClassLoader(...)");
        dependenciesFromClassloader$default(jvmScriptCompilationConfigurationBuilder, strArr, classLoader, z, false, 8, null);
    }

    public static /* synthetic */ void dependenciesFromCurrentContext$default(JvmScriptCompilationConfigurationBuilder jvmScriptCompilationConfigurationBuilder, String[] strArr, boolean z, boolean z2, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        if ((i & 4) != 0) {
            z2 = false;
        }
        dependenciesFromCurrentContext(jvmScriptCompilationConfigurationBuilder, strArr, z, z2);
    }

    public static final void dependenciesFromCurrentContext(JvmScriptCompilationConfigurationBuilder jvmScriptCompilationConfigurationBuilder, String[] libraries, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(jvmScriptCompilationConfigurationBuilder, "<this>");
        Intrinsics.checkNotNullParameter(libraries, "libraries");
        dependenciesFromClassloader$default(jvmScriptCompilationConfigurationBuilder, (String[]) Arrays.copyOf(libraries, libraries.length), null, z, z2, 2, null);
    }

    public static /* synthetic */ void dependenciesFromClassloader$default(JvmScriptCompilationConfigurationBuilder jvmScriptCompilationConfigurationBuilder, String[] strArr, ClassLoader classLoader, boolean z, boolean z2, int i, Object obj) {
        if ((i & 2) != 0) {
            classLoader = Thread.currentThread().getContextClassLoader();
            Intrinsics.checkNotNullExpressionValue(classLoader, "getContextClassLoader(...)");
        }
        if ((i & 4) != 0) {
            z = false;
        }
        if ((i & 8) != 0) {
            z2 = false;
        }
        dependenciesFromClassloader(jvmScriptCompilationConfigurationBuilder, strArr, classLoader, z, z2);
    }

    public static final void dependenciesFromClassloader(JvmScriptCompilationConfigurationBuilder jvmScriptCompilationConfigurationBuilder, String[] libraries, ClassLoader classLoader, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(jvmScriptCompilationConfigurationBuilder, "<this>");
        Intrinsics.checkNotNullParameter(libraries, "libraries");
        Intrinsics.checkNotNullParameter(classLoader, "classLoader");
        updateClasspath(jvmScriptCompilationConfigurationBuilder, JvmClasspathUtilKt.scriptCompilationClasspathFromContext((String[]) Arrays.copyOf(libraries, libraries.length), classLoader, z, z2));
    }

    public static final ScriptCompilationConfiguration withUpdatedClasspath(ScriptCompilationConfiguration scriptCompilationConfiguration, Collection<? extends File> classpath) {
        Intrinsics.checkNotNullParameter(scriptCompilationConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(classpath, "classpath");
        final List<File> listFilterNewClasspath = filterNewClasspath(classpath, (Collection) scriptCompilationConfiguration.get(ScriptCompilationKt.getDependencies(ScriptCompilationConfiguration.INSTANCE)));
        return listFilterNewClasspath == null ? scriptCompilationConfiguration : new ScriptCompilationConfiguration(new ScriptCompilationConfiguration[]{scriptCompilationConfiguration}, new Function1<ScriptCompilationConfiguration.Builder, Unit>() { // from class: kotlin.script.experimental.jvm.JvmScriptCompilationKt.withUpdatedClasspath.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ScriptCompilationConfiguration.Builder builder) {
                invoke2(builder);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ScriptCompilationConfiguration.Builder $receiver) {
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                $receiver.append(ScriptCompilationKt.getDependencies($receiver), new JvmDependency(listFilterNewClasspath));
            }
        });
    }

    public static final void updateClasspath(ScriptCompilationConfiguration.Builder builder, Collection<? extends File> collection) {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        updateClasspathImpl(builder, collection);
    }

    public static final void updateClasspath(JvmScriptCompilationConfigurationBuilder jvmScriptCompilationConfigurationBuilder, Collection<? extends File> collection) {
        Intrinsics.checkNotNullParameter(jvmScriptCompilationConfigurationBuilder, "<this>");
        updateClasspathImpl(jvmScriptCompilationConfigurationBuilder, collection);
    }

    private static final void updateClasspathImpl(PropertiesCollection.Builder builder, Collection<? extends File> collection) {
        List<File> listFilterNewClasspath;
        if (collection == null || (listFilterNewClasspath = filterNewClasspath(collection, (Collection) builder.get(ScriptCompilationKt.getDependencies(ScriptCompilationConfiguration.INSTANCE)))) == null) {
            return;
        }
        builder.append(ScriptCompilationKt.getDependencies(ScriptCompilationConfiguration.INSTANCE), new JvmDependency(listFilterNewClasspath));
    }

    private static final List<File> filterNewClasspath(Collection<? extends File> collection, Collection<? extends ScriptDependency> collection2) {
        HashSet hashSet;
        List<File> listEmptyList;
        if (collection.isEmpty()) {
            return null;
        }
        if (collection2 != null) {
            HashSet hashSet2 = new HashSet();
            for (ScriptDependency scriptDependency : collection2) {
                JvmDependency jvmDependency = scriptDependency instanceof JvmDependency ? (JvmDependency) scriptDependency : null;
                if (jvmDependency == null || (listEmptyList = jvmDependency.getClasspath()) == null) {
                    listEmptyList = CollectionsKt.emptyList();
                }
                CollectionsKt.addAll(hashSet2, listEmptyList);
            }
            hashSet = hashSet2;
        } else {
            hashSet = null;
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : collection) {
            File file = (File) obj;
            if (hashSet == null || !hashSet.contains(file)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        if (arrayList2.isEmpty()) {
            return null;
        }
        return arrayList2;
    }

    public static final PropertiesCollection.Key<File> getJavaHome(JvmScriptCompilationConfigurationKeys jvmScriptCompilationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(jvmScriptCompilationConfigurationKeys, "<this>");
        return javaHome$delegate.getValue(jvmScriptCompilationConfigurationKeys, $$delegatedProperties[0]);
    }

    public static final PropertiesCollection.Key<File> getJdkHome(JvmScriptCompilationConfigurationKeys jvmScriptCompilationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(jvmScriptCompilationConfigurationKeys, "<this>");
        return jdkHome$delegate.getValue(jvmScriptCompilationConfigurationKeys, $$delegatedProperties[1]);
    }

    public static final PropertiesCollection.Key<String> getJvmTarget(JvmScriptCompilationConfigurationKeys jvmScriptCompilationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(jvmScriptCompilationConfigurationKeys, "<this>");
        return jvmTarget$delegate.getValue(jvmScriptCompilationConfigurationKeys, $$delegatedProperties[2]);
    }

    public static final JvmScriptCompilationConfigurationBuilder getJvm(ScriptCompilationConfigurationKeys scriptCompilationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptCompilationConfigurationKeys, "<this>");
        return new JvmScriptCompilationConfigurationBuilder();
    }
}
