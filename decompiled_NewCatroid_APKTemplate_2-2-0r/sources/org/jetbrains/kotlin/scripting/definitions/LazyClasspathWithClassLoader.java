package org.jetbrains.kotlin.scripting.definitions;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ScriptiDefinitionsFromClasspathDiscoverySource.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b\u0002\u0018\u00002\u00020\u0001B#\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0005¢\u0006\u0002\u0010\bR\u001b\u0010\t\u001a\u00020\n8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR!\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u000e\u001a\u0004\b\u0004\u0010\u0010¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/scripting/definitions/LazyClasspathWithClassLoader;", "Lorg/jetbrains/kotlin/scripting/definitions/ClasspathWithClassLoader;", "baseClassLoader", "Ljava/lang/ClassLoader;", "getClasspath", "Lkotlin/Function0;", "", "Ljava/io/File;", "(Ljava/lang/ClassLoader;Lkotlin/jvm/functions/Function0;)V", "classLoader", "Ljava/net/URLClassLoader;", "getClassLoader", "()Ljava/net/URLClassLoader;", "classLoader$delegate", "Lkotlin/Lazy;", ModuleXmlParser.CLASSPATH, "()Ljava/util/List;", "classpath$delegate", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
final class LazyClasspathWithClassLoader implements ClasspathWithClassLoader {

    /* JADX INFO: renamed from: classLoader$delegate, reason: from kotlin metadata */
    private final Lazy classLoader;

    /* JADX INFO: renamed from: classpath$delegate, reason: from kotlin metadata */
    private final Lazy classpath;

    public LazyClasspathWithClassLoader(final ClassLoader classLoader, final Function0<? extends List<? extends File>> getClasspath) {
        Intrinsics.checkNotNullParameter(getClasspath, "getClasspath");
        this.classpath = LazyKt.lazy(new Function0<List<? extends File>>() { // from class: org.jetbrains.kotlin.scripting.definitions.LazyClasspathWithClassLoader$classpath$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends File> invoke() {
                return getClasspath.invoke();
            }
        });
        this.classLoader = LazyKt.lazy(new Function0<URLClassLoader>() { // from class: org.jetbrains.kotlin.scripting.definitions.LazyClasspathWithClassLoader$classLoader$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final URLClassLoader invoke() {
                List<File> classpath = this.this$0.getClasspath();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(classpath, 10));
                Iterator<T> it = classpath.iterator();
                while (it.hasNext()) {
                    arrayList.add(((File) it.next()).toURI().toURL());
                }
                return new URLClassLoader((URL[]) arrayList.toArray(new URL[0]), classLoader);
            }
        });
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.ClasspathWithClassLoader
    public List<File> getClasspath() {
        return (List) this.classpath.getValue();
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.ClasspathWithClassLoader
    public URLClassLoader getClassLoader() {
        return (URLClassLoader) this.classLoader.getValue();
    }
}
