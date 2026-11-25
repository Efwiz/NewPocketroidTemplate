package org.jetbrains.kotlin.descriptors.runtime.structure;

import com.koushikdutta.async.http.cache.ResponseCacheMiddleware;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ReflectJavaMember.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\bÂ\u0002\u0018\u00002\u00020\u0001:\u0001\u000fB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bJ\u0016\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\r2\u0006\u0010\n\u001a\u00020\u000bR\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/runtime/structure/Java8ParameterNamesLoader;", "", "()V", ResponseCacheMiddleware.CACHE, "Lorg/jetbrains/kotlin/descriptors/runtime/structure/Java8ParameterNamesLoader$Cache;", "getCache", "()Lorg/jetbrains/kotlin/descriptors/runtime/structure/Java8ParameterNamesLoader$Cache;", "setCache", "(Lorg/jetbrains/kotlin/descriptors/runtime/structure/Java8ParameterNamesLoader$Cache;)V", "buildCache", "member", "Ljava/lang/reflect/Member;", "loadParameterNames", "", "", "Cache", "descriptors.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class Java8ParameterNamesLoader {
    public static final Java8ParameterNamesLoader INSTANCE = new Java8ParameterNamesLoader();
    private static Cache cache;

    /* JADX INFO: compiled from: ReflectJavaMember.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0005R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/runtime/structure/Java8ParameterNamesLoader$Cache;", "", "getParameters", "Ljava/lang/reflect/Method;", "getName", "(Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;)V", "getGetName", "()Ljava/lang/reflect/Method;", "getGetParameters", "descriptors.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Cache {
        private final Method getName;
        private final Method getParameters;

        public Cache(Method method, Method method2) {
            this.getParameters = method;
            this.getName = method2;
        }

        public final Method getGetName() {
            return this.getName;
        }

        public final Method getGetParameters() {
            return this.getParameters;
        }
    }

    private Java8ParameterNamesLoader() {
    }

    public final Cache getCache() {
        return cache;
    }

    public final void setCache(Cache cache2) {
        cache = cache2;
    }

    public final Cache buildCache(Member member) {
        Intrinsics.checkNotNullParameter(member, "member");
        Class<?> cls = member.getClass();
        try {
            return new Cache(cls.getMethod("getParameters", new Class[0]), ReflectClassUtilKt.getSafeClassLoader(cls).loadClass("java.lang.reflect.Parameter").getMethod("getName", new Class[0]));
        } catch (NoSuchMethodException unused) {
            return new Cache(null, null);
        }
    }

    public final List<String> loadParameterNames(Member member) throws IllegalAccessException, InvocationTargetException {
        Method getName;
        Intrinsics.checkNotNullParameter(member, "member");
        Cache cacheBuildCache = cache;
        if (cacheBuildCache == null) {
            synchronized (this) {
                Java8ParameterNamesLoader java8ParameterNamesLoader = INSTANCE;
                Cache cache2 = cache;
                if (cache2 == null) {
                    cacheBuildCache = java8ParameterNamesLoader.buildCache(member);
                    cache = cacheBuildCache;
                } else {
                    cacheBuildCache = cache2;
                }
            }
        }
        Method getParameters = cacheBuildCache.getGetParameters();
        if (getParameters == null || (getName = cacheBuildCache.getGetName()) == null) {
            return null;
        }
        Object objInvoke = getParameters.invoke(member, new Object[0]);
        Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type kotlin.Array<*>");
        Object[] objArr = (Object[]) objInvoke;
        ArrayList arrayList = new ArrayList(objArr.length);
        for (Object obj : objArr) {
            Object objInvoke2 = getName.invoke(obj, new Object[0]);
            Intrinsics.checkNotNull(objInvoke2, "null cannot be cast to non-null type kotlin.String");
            arrayList.add((String) objInvoke2);
        }
        return arrayList;
    }
}
