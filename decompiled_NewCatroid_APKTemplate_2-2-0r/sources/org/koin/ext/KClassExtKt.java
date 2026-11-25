package org.koin.ext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/koin-core.jar:org/koin/ext/KClassExtKt.class
  classes6.dex
 */
/* JADX INFO: compiled from: KClassExt.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\u001a\u000e\u0010\u0004\u001a\u00020\u0003*\u0006\u0012\u0002\b\u00030\u0002\u001a\u000e\u0010\u0005\u001a\u00020\u0003*\u0006\u0012\u0002\b\u00030\u0002\"\u001e\u0010\u0000\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0002\u0012\u0004\u0012\u00020\u00030\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"classNames", "", "Lkotlin/reflect/KClass;", "", "getFullName", "saveCache", "koin-core"}, k = 2, mv = {1, 1, 16})
public final class KClassExtKt {
    private static final Map<KClass<?>, String> classNames = new ConcurrentHashMap();

    public static final String getFullName(KClass<?> getFullName) {
        Intrinsics.checkParameterIsNotNull(getFullName, "$this$getFullName");
        String str = classNames.get(getFullName);
        return str != null ? str : saveCache(getFullName);
    }

    public static final String saveCache(KClass<?> saveCache) {
        Intrinsics.checkParameterIsNotNull(saveCache, "$this$saveCache");
        String name2 = JvmClassMappingKt.getJavaClass((KClass) saveCache).getName();
        Map<KClass<?>, String> map = classNames;
        Intrinsics.checkExpressionValueIsNotNull(name2, "name");
        map.put(saveCache, name2);
        return name2;
    }
}
