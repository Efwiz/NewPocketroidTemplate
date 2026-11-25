package kotlin.script.experimental.jvm;

import java.io.File;
import java.io.Serializable;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import kotlin.script.experimental.api.KotlinType;
import kotlin.script.experimental.api.ScriptDependency;
import kotlin.script.experimental.host.HostConfigurationKt;
import kotlin.script.experimental.host.ScriptingHostConfiguration;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: jvmScriptingHostConfiguration.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u001a2\u00020\u00012\u00020\u0002:\u0001\u001aB\u0005¢\u0006\u0002\u0010\u0003J\u0013\u0010\r\u001a\u00020\u00072\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0096\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0016J'\u0010\u0012\u001a\u0006\u0012\u0002\b\u00030\u00132\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0017\u001a\u00020\u0018H\u0096\u0002J)\u0010\u0012\u001a\u0006\u0012\u0002\b\u00030\u00132\u0006\u0010\u0014\u001a\u00020\u00152\n\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\u00132\u0006\u0010\u0017\u001a\u00020\u0018H\u0096\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\bR\u0010\u0010\t\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lkotlin/script/experimental/jvm/JvmGetScriptingClass;", "Lkotlin/script/experimental/jvm/GetScriptingClassByClassLoader;", "Ljava/io/Serializable;", "()V", "baseClassLoader", "Ljava/lang/ClassLoader;", "baseClassLoaderIsInitialized", "", "Ljava/lang/Boolean;", "classLoader", "dependencies", "", "Lkotlin/script/experimental/api/ScriptDependency;", Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "invoke", "Lkotlin/reflect/KClass;", "classType", "Lkotlin/script/experimental/api/KotlinType;", "contextClassLoader", "hostConfiguration", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "contextClass", "Companion", PathUtil.KOTLIN_SCRIPTING_JVM_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmGetScriptingClass implements GetScriptingClassByClassLoader, Serializable {
    private static final long serialVersionUID = 1;
    private transient ClassLoader baseClassLoader;
    private transient Boolean baseClassLoaderIsInitialized;
    private transient ClassLoader classLoader;
    private transient List<? extends ScriptDependency> dependencies;

    @Override // kotlin.script.experimental.host.GetScriptingClass
    public KClass<?> invoke(KotlinType classType, KClass<?> contextClass, ScriptingHostConfiguration hostConfiguration) {
        Intrinsics.checkNotNullParameter(classType, "classType");
        Intrinsics.checkNotNullParameter(contextClass, "contextClass");
        Intrinsics.checkNotNullParameter(hostConfiguration, "hostConfiguration");
        return invoke(classType, JvmClassMappingKt.getJavaClass((KClass) contextClass).getClassLoader(), hostConfiguration);
    }

    @Override // kotlin.script.experimental.jvm.GetScriptingClassByClassLoader
    public synchronized KClass<?> invoke(KotlinType classType, ClassLoader contextClassLoader, ScriptingHostConfiguration hostConfiguration) {
        URLClassLoader uRLClassLoader;
        Intrinsics.checkNotNullParameter(classType, "classType");
        Intrinsics.checkNotNullParameter(hostConfiguration, "hostConfiguration");
        KClass<?> fromClass = classType.getFromClass();
        if (fromClass != null) {
            if (JvmClassMappingKt.getJavaClass((KClass) fromClass).getClassLoader() == null) {
                return fromClass;
            }
            Iterator it = SequencesKt.generateSequence(contextClassLoader, new Function1<ClassLoader, ClassLoader>() { // from class: kotlin.script.experimental.jvm.JvmGetScriptingClass$invoke$actualClassLoadersChain$1
                @Override // kotlin.jvm.functions.Function1
                public final ClassLoader invoke(ClassLoader it2) {
                    Intrinsics.checkNotNullParameter(it2, "it");
                    return it2.getParent();
                }
            }).iterator();
            while (it.hasNext()) {
                if (Intrinsics.areEqual((ClassLoader) it.next(), JvmClassMappingKt.getJavaClass((KClass) fromClass).getClassLoader())) {
                    return fromClass;
                }
            }
        }
        List<? extends ScriptDependency> list = (List) hostConfiguration.get(HostConfigurationKt.getConfigurationDependencies(ScriptingHostConfiguration.INSTANCE));
        List<? extends ScriptDependency> list2 = this.dependencies;
        ArrayList arrayList = null;
        if (list2 == null) {
            this.dependencies = list;
        } else if (!Intrinsics.areEqual(list, list2)) {
            StringBuilder sbAppend = new StringBuilder().append("scripting configuration dependencies changed:\nold: ");
            List<? extends ScriptDependency> list3 = this.dependencies;
            throw new IllegalArgumentException(sbAppend.append(list3 != null ? CollectionsKt.joinToString$default(list3, null, null, null, 0, null, new Function1<ScriptDependency, CharSequence>() { // from class: kotlin.script.experimental.jvm.JvmGetScriptingClass.invoke.2
                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(ScriptDependency it2) {
                    Intrinsics.checkNotNullParameter(it2, "it");
                    JvmDependency jvmDependency = it2 instanceof JvmDependency ? (JvmDependency) it2 : null;
                    return String.valueOf(jvmDependency != null ? jvmDependency.getClasspath() : null);
                }
            }, 31, null) : null).append("\nnew: ").append(list != null ? CollectionsKt.joinToString$default(list, null, null, null, 0, null, new Function1<ScriptDependency, CharSequence>() { // from class: kotlin.script.experimental.jvm.JvmGetScriptingClass.invoke.3
                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(ScriptDependency it2) {
                    Intrinsics.checkNotNullParameter(it2, "it");
                    JvmDependency jvmDependency = it2 instanceof JvmDependency ? (JvmDependency) it2 : null;
                    return String.valueOf(jvmDependency != null ? jvmDependency.getClasspath() : null);
                }
            }, 31, null) : null).toString());
        }
        if (!Intrinsics.areEqual((Object) this.baseClassLoaderIsInitialized, (Object) true)) {
            this.baseClassLoader = contextClassLoader;
            this.baseClassLoaderIsInitialized = true;
        }
        if (this.classLoader == null) {
            List<? extends ScriptDependency> list4 = this.dependencies;
            if (list4 != null) {
                ArrayList arrayList2 = new ArrayList();
                for (ScriptDependency scriptDependency : list4) {
                    if (!(scriptDependency instanceof JvmDependency)) {
                        throw new IllegalArgumentException("unknown dependency type " + scriptDependency);
                    }
                    List<File> classpath = ((JvmDependency) scriptDependency).getClasspath();
                    ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(classpath, 10));
                    Iterator<T> it2 = classpath.iterator();
                    while (it2.hasNext()) {
                        arrayList3.add(((File) it2.next()).toURI().toURL());
                    }
                    CollectionsKt.addAll(arrayList2, arrayList3);
                }
                arrayList = arrayList2;
            }
            if (arrayList == null || arrayList.isEmpty()) {
                uRLClassLoader = this.baseClassLoader;
            } else {
                uRLClassLoader = new URLClassLoader((URL[]) arrayList.toArray(new URL[0]), this.baseClassLoader);
            }
            this.classLoader = uRLClassLoader;
        }
        try {
            ClassLoader systemClassLoader = this.classLoader;
            if (systemClassLoader == null) {
                systemClassLoader = ClassLoader.getSystemClassLoader();
            }
            Class<?> clsLoadClass = systemClassLoader.loadClass(classType.getTypeName());
            Intrinsics.checkNotNullExpressionValue(clsLoadClass, "loadClass(...)");
            return JvmClassMappingKt.getKotlinClass(clsLoadClass);
        } catch (Throwable th) {
            throw new IllegalArgumentException("unable to load class " + classType.getTypeName(), th);
        }
    }

    public boolean equals(Object other) {
        ClassLoader classLoader;
        ClassLoader classLoader2;
        ClassLoader classLoader3;
        ClassLoader classLoader4;
        if (other == this) {
            return true;
        }
        if (other instanceof JvmGetScriptingClass) {
            JvmGetScriptingClass jvmGetScriptingClass = (JvmGetScriptingClass) other;
            if (Intrinsics.areEqual(jvmGetScriptingClass.dependencies, this.dependencies) && (((classLoader = jvmGetScriptingClass.classLoader) == null || (classLoader4 = this.classLoader) == null || Intrinsics.areEqual(classLoader, classLoader4)) && ((classLoader2 = jvmGetScriptingClass.baseClassLoader) == null || (classLoader3 = this.baseClassLoader) == null || Intrinsics.areEqual(classLoader2, classLoader3)))) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        List<? extends ScriptDependency> list = this.dependencies;
        int iHashCode = list != null ? list.hashCode() : 0;
        ClassLoader classLoader = this.classLoader;
        int iHashCode2 = iHashCode + ((classLoader != null ? classLoader.hashCode() : 0) * 23);
        ClassLoader classLoader2 = this.baseClassLoader;
        return iHashCode2 + ((classLoader2 != null ? classLoader2.hashCode() : 0) * 37);
    }
}
