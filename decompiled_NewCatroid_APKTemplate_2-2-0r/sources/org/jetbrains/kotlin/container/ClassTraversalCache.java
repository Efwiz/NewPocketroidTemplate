package org.jetbrains.kotlin.container;

import com.koushikdutta.async.http.cache.ResponseCacheMiddleware;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: Cache.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\b\u001a\u00020\u00072\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\u0005R2\u0010\u0003\u001a&\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003 \u0006*\b\u0012\u0002\b\u0003\u0018\u00010\u00050\u0005\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00070\u00070\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/container/ClassTraversalCache;", "", "()V", ResponseCacheMiddleware.CACHE, "Ljava/util/concurrent/ConcurrentMap;", "Ljava/lang/Class;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "Lorg/jetbrains/kotlin/container/ClassInfo;", "getClassInfo", "c", "container"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class ClassTraversalCache {
    public static final ClassTraversalCache INSTANCE = new ClassTraversalCache();
    private static final ConcurrentMap<Class<?>, ClassInfo> cache;

    private ClassTraversalCache() {
    }

    static {
        ConcurrentHashMap concurrentHashMapCreateConcurrentWeakKeySoftValueMap;
        if (System.getProperty("idea.system.path") != null) {
            concurrentHashMapCreateConcurrentWeakKeySoftValueMap = new ConcurrentHashMap();
        } else {
            concurrentHashMapCreateConcurrentWeakKeySoftValueMap = ContainerUtil.createConcurrentWeakKeySoftValueMap();
            Intrinsics.checkNotNullExpressionValue(concurrentHashMapCreateConcurrentWeakKeySoftValueMap, "createConcurrentWeakKeySoftValueMap(...)");
        }
        cache = concurrentHashMapCreateConcurrentWeakKeySoftValueMap;
    }

    public final ClassInfo getClassInfo(Class<?> c) {
        Intrinsics.checkNotNullParameter(c, "c");
        ConcurrentMap<Class<?>, ClassInfo> concurrentMap = cache;
        ClassInfo classInfo = concurrentMap.get(c);
        if (classInfo != null) {
            return classInfo;
        }
        ClassInfo classInfoTraverseClass = CacheKt.traverseClass(c);
        concurrentMap.put(c, classInfoTraverseClass);
        return classInfoTraverseClass;
    }
}
