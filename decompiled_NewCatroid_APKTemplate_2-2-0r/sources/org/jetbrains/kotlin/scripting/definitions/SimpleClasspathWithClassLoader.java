package org.jetbrains.kotlin.scripting.definitions;

import java.io.File;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ScriptiDefinitionsFromClasspathDiscoverySource.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0002\u0018\u00002\u00020\u0001B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001a\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/scripting/definitions/SimpleClasspathWithClassLoader;", "Lorg/jetbrains/kotlin/scripting/definitions/ClasspathWithClassLoader;", ModuleXmlParser.CLASSPATH, "", "Ljava/io/File;", "classLoader", "Ljava/lang/ClassLoader;", "(Ljava/util/List;Ljava/lang/ClassLoader;)V", "getClassLoader", "()Ljava/lang/ClassLoader;", "getClasspath", "()Ljava/util/List;", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
final class SimpleClasspathWithClassLoader implements ClasspathWithClassLoader {
    private final ClassLoader classLoader;
    private final List<File> classpath;

    /* JADX WARN: Multi-variable type inference failed */
    public SimpleClasspathWithClassLoader(List<? extends File> classpath, ClassLoader classLoader) {
        Intrinsics.checkNotNullParameter(classpath, "classpath");
        Intrinsics.checkNotNullParameter(classLoader, "classLoader");
        this.classpath = classpath;
        this.classLoader = classLoader;
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.ClasspathWithClassLoader
    public List<File> getClasspath() {
        return this.classpath;
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.ClasspathWithClassLoader
    public ClassLoader getClassLoader() {
        return this.classLoader;
    }
}
