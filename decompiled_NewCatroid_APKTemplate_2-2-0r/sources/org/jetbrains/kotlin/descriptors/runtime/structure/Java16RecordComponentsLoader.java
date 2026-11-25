package org.jetbrains.kotlin.descriptors.runtime.structure;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ReflectJavaRecordComponent.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÂ\u0002\u0018\u00002\u00020\u0001:\u0001\fB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0001H\u0002J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0001H\u0002J\u0010\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0006\u001a\u00020\u0001J\u0014\u0010\n\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u000b2\u0006\u0010\u0006\u001a\u00020\u0001R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/runtime/structure/Java16RecordComponentsLoader;", "", "()V", "_cache", "Lorg/jetbrains/kotlin/descriptors/runtime/structure/Java16RecordComponentsLoader$Cache;", "buildCache", "recordComponent", "initCache", "loadGetAccessor", "Ljava/lang/reflect/Method;", "loadGetType", "Ljava/lang/Class;", "Cache", "descriptors.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class Java16RecordComponentsLoader {
    public static final Java16RecordComponentsLoader INSTANCE = new Java16RecordComponentsLoader();
    private static Cache _cache;

    /* JADX INFO: compiled from: ReflectJavaRecordComponent.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0005R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/runtime/structure/Java16RecordComponentsLoader$Cache;", "", "getType", "Ljava/lang/reflect/Method;", "getAccessor", "(Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;)V", "getGetAccessor", "()Ljava/lang/reflect/Method;", "getGetType", "descriptors.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Cache {
        private final Method getAccessor;
        private final Method getType;

        public Cache(Method method, Method method2) {
            this.getType = method;
            this.getAccessor = method2;
        }

        public final Method getGetType() {
            return this.getType;
        }

        public final Method getGetAccessor() {
            return this.getAccessor;
        }
    }

    private Java16RecordComponentsLoader() {
    }

    private final Cache buildCache(Object recordComponent) {
        Class<?> cls = recordComponent.getClass();
        try {
            return new Cache(cls.getMethod("getType", new Class[0]), cls.getMethod("getAccessor", new Class[0]));
        } catch (NoSuchMethodException unused) {
            return new Cache(null, null);
        }
    }

    private final Cache initCache(Object recordComponent) {
        Cache cache = _cache;
        if (cache != null) {
            return cache;
        }
        Cache cacheBuildCache = buildCache(recordComponent);
        _cache = cacheBuildCache;
        return cacheBuildCache;
    }

    public final Class<?> loadGetType(Object recordComponent) throws IllegalAccessException, InvocationTargetException {
        Intrinsics.checkNotNullParameter(recordComponent, "recordComponent");
        Method getType = initCache(recordComponent).getGetType();
        if (getType == null) {
            return null;
        }
        Object objInvoke = getType.invoke(recordComponent, new Object[0]);
        Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type java.lang.Class<*>");
        return (Class) objInvoke;
    }

    public final Method loadGetAccessor(Object recordComponent) throws IllegalAccessException, InvocationTargetException {
        Intrinsics.checkNotNullParameter(recordComponent, "recordComponent");
        Method getAccessor = initCache(recordComponent).getGetAccessor();
        if (getAccessor == null) {
            return null;
        }
        Object objInvoke = getAccessor.invoke(recordComponent, new Object[0]);
        Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type java.lang.reflect.Method");
        return (Method) objInvoke;
    }
}
