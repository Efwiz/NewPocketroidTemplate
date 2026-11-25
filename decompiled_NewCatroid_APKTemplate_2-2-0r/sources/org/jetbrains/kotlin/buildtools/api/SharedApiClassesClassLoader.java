package org.jetbrains.kotlin.buildtools.api;

import androidx.exifinterface.media.ExifInterface;
import java.util.ServiceLoader;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import net.bytebuddy.implementation.auxiliary.TypeProxy;

/* JADX INFO: compiled from: SharedApiClassesClassLoader.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\r\u0010\u0000\u001a\u00020\u0001H\u0007¢\u0006\u0002\b\u0002\u001a-\u0010\u0003\u001a\u0002H\u0004\"\b\b\u0000\u0010\u0004*\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00040\u00072\u0006\u0010\b\u001a\u00020\u0001H\u0000¢\u0006\u0002\u0010\t¨\u0006\n"}, d2 = {"SharedApiClassesClassLoader", "Ljava/lang/ClassLoader;", TypeProxy.SilentConstruction.Appender.NEW_INSTANCE_METHOD_NAME, "loadImplementation", ExifInterface.GPS_DIRECTION_TRUE, "", "cls", "Lkotlin/reflect/KClass;", "classLoader", "(Lkotlin/reflect/KClass;Ljava/lang/ClassLoader;)Ljava/lang/Object;", "kotlin-build-tools-api"}, k = 2, mv = {1, 5, 1}, xi = 48)
public final class SharedApiClassesClassLoader {
    @ExperimentalBuildToolsApi
    public static final ClassLoader newInstance() {
        ClassLoader classLoader = SharedApiClassesClassLoaderImpl.class.getClassLoader();
        Intrinsics.checkNotNullExpressionValue(classLoader, "SharedApiClassesClassLoa…l::class.java.classLoader");
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        Intrinsics.checkNotNullExpressionValue(systemClassLoader, "getSystemClassLoader()");
        String name2 = SharedApiClassesClassLoaderImpl.class.getPackage().getName();
        Intrinsics.checkNotNullExpressionValue(name2, "SharedApiClassesClassLoa…class.java.`package`.name");
        return new SharedApiClassesClassLoaderImpl(classLoader, systemClassLoader, name2);
    }

    public static final <T> T loadImplementation(KClass<T> cls, ClassLoader classLoader) {
        Intrinsics.checkNotNullParameter(cls, "cls");
        Intrinsics.checkNotNullParameter(classLoader, "classLoader");
        ServiceLoader implementations = ServiceLoader.load(JvmClassMappingKt.getJavaClass((KClass) cls), classLoader);
        Intrinsics.checkNotNullExpressionValue(implementations, "implementations");
        ServiceLoader serviceLoader = implementations;
        if (CollectionsKt.firstOrNull(serviceLoader) == null) {
            throw new IllegalStateException(("The classpath contains no implementation for " + cls.getQualifiedName()).toString());
        }
        T t = (T) CollectionsKt.singleOrNull(serviceLoader);
        if (t != null) {
            return t;
        }
        throw new IllegalStateException(("The classpath contains more than one implementation for " + cls.getQualifiedName()).toString());
    }
}
