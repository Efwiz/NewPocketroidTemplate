package kotlin.script.experimental.jvm.impl;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: KJvmCompiledModule.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0012\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\tH\u0016R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u000b"}, d2 = {"Lkotlin/script/experimental/jvm/impl/KJvmCompiledModuleFromClassPath;", "Lkotlin/script/experimental/jvm/impl/KJvmCompiledModule;", ModuleXmlParser.CLASSPATH, "", "Ljava/io/File;", "(Ljava/util/Collection;)V", "getClasspath", "()Ljava/util/Collection;", "createClassLoader", "Ljava/lang/ClassLoader;", "baseClassLoader", PathUtil.KOTLIN_SCRIPTING_JVM_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KJvmCompiledModuleFromClassPath implements KJvmCompiledModule {
    private final Collection<File> classpath;

    /* JADX WARN: Multi-variable type inference failed */
    public KJvmCompiledModuleFromClassPath(Collection<? extends File> classpath) {
        Intrinsics.checkNotNullParameter(classpath, "classpath");
        this.classpath = classpath;
    }

    public final Collection<File> getClasspath() {
        return this.classpath;
    }

    @Override // kotlin.script.experimental.jvm.impl.KJvmCompiledModule
    public ClassLoader createClassLoader(ClassLoader baseClassLoader) {
        Collection<File> collection = this.classpath;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(((File) it.next()).toURI().toURL());
        }
        return new URLClassLoader((URL[]) arrayList.toArray(new URL[0]), baseClassLoader);
    }
}
