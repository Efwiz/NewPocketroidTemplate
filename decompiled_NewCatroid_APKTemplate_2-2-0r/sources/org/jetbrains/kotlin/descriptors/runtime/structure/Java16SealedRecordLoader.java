package org.jetbrains.kotlin.descriptors.runtime.structure;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ReflectJavaClass.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\bÂ\u0002\u0018\u00002\u00020\u0001:\u0001\u0012B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0004H\u0002J\b\u0010\u0006\u001a\u00020\u0004H\u0002J#\u0010\u0007\u001a\u000e\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t\u0018\u00010\b2\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\t¢\u0006\u0002\u0010\u000bJ\u001f\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\b2\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\t¢\u0006\u0002\u0010\rJ\u0019\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\t¢\u0006\u0002\u0010\u0010J\u0019\u0010\u0011\u001a\u0004\u0018\u00010\u000f2\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\t¢\u0006\u0002\u0010\u0010R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/runtime/structure/Java16SealedRecordLoader;", "", "()V", "_cache", "Lorg/jetbrains/kotlin/descriptors/runtime/structure/Java16SealedRecordLoader$Cache;", "buildCache", "initCache", "loadGetPermittedSubclasses", "", "Ljava/lang/Class;", "clazz", "(Ljava/lang/Class;)[Ljava/lang/Class;", "loadGetRecordComponents", "(Ljava/lang/Class;)[Ljava/lang/Object;", "loadIsRecord", "", "(Ljava/lang/Class;)Ljava/lang/Boolean;", "loadIsSealed", "Cache", "descriptors.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class Java16SealedRecordLoader {
    public static final Java16SealedRecordLoader INSTANCE = new Java16SealedRecordLoader();
    private static Cache _cache;

    /* JADX INFO: compiled from: ReflectJavaClass.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B-\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0007R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\tR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\t¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/runtime/structure/Java16SealedRecordLoader$Cache;", "", "isSealed", "Ljava/lang/reflect/Method;", "getPermittedSubclasses", "isRecord", "getRecordComponents", "(Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;)V", "getGetPermittedSubclasses", "()Ljava/lang/reflect/Method;", "getGetRecordComponents", "descriptors.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Cache {
        private final Method getPermittedSubclasses;
        private final Method getRecordComponents;
        private final Method isRecord;
        private final Method isSealed;

        public Cache(Method method, Method method2, Method method3, Method method4) {
            this.isSealed = method;
            this.getPermittedSubclasses = method2;
            this.isRecord = method3;
            this.getRecordComponents = method4;
        }

        /* JADX INFO: renamed from: isSealed, reason: from getter */
        public final Method getIsSealed() {
            return this.isSealed;
        }

        public final Method getGetPermittedSubclasses() {
            return this.getPermittedSubclasses;
        }

        /* JADX INFO: renamed from: isRecord, reason: from getter */
        public final Method getIsRecord() {
            return this.isRecord;
        }

        public final Method getGetRecordComponents() {
            return this.getRecordComponents;
        }
    }

    private Java16SealedRecordLoader() {
    }

    private final Cache buildCache() {
        try {
            return new Cache(Class.class.getMethod("isSealed", new Class[0]), Class.class.getMethod("getPermittedSubclasses", new Class[0]), Class.class.getMethod("isRecord", new Class[0]), Class.class.getMethod("getRecordComponents", new Class[0]));
        } catch (NoSuchMethodException unused) {
            return new Cache(null, null, null, null);
        }
    }

    private final Cache initCache() {
        Cache cache = _cache;
        if (cache != null) {
            return cache;
        }
        Cache cacheBuildCache = buildCache();
        _cache = cacheBuildCache;
        return cacheBuildCache;
    }

    public final Boolean loadIsSealed(Class<?> clazz) throws IllegalAccessException, InvocationTargetException {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Method isSealed = initCache().getIsSealed();
        if (isSealed == null) {
            return null;
        }
        Object objInvoke = isSealed.invoke(clazz, new Object[0]);
        Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type kotlin.Boolean");
        return (Boolean) objInvoke;
    }

    public final Class<?>[] loadGetPermittedSubclasses(Class<?> clazz) throws IllegalAccessException, InvocationTargetException {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Method getPermittedSubclasses = initCache().getGetPermittedSubclasses();
        if (getPermittedSubclasses == null) {
            return null;
        }
        Object objInvoke = getPermittedSubclasses.invoke(clazz, new Object[0]);
        Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type kotlin.Array<java.lang.Class<*>>");
        return (Class[]) objInvoke;
    }

    public final Boolean loadIsRecord(Class<?> clazz) throws IllegalAccessException, InvocationTargetException {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Method isRecord = initCache().getIsRecord();
        if (isRecord == null) {
            return null;
        }
        Object objInvoke = isRecord.invoke(clazz, new Object[0]);
        Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type kotlin.Boolean");
        return (Boolean) objInvoke;
    }

    public final Object[] loadGetRecordComponents(Class<?> clazz) {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Method getRecordComponents = initCache().getGetRecordComponents();
        if (getRecordComponents == null) {
            return null;
        }
        return (Object[]) getRecordComponents.invoke(clazz, new Object[0]);
    }
}
