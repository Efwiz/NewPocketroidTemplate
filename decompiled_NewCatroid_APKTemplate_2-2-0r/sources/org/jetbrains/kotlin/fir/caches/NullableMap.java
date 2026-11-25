package org.jetbrains.kotlin.fir.caches;

import androidx.exifinterface.media.ExifInterface;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.JvmInline;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.util.PrivateForInline;

/* JADX INFO: compiled from: NullableMap.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010%\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0087@\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u00020\u0003:\u0001$B\u001d\u0012\u0014\b\u0002\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u001a\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0003HÖ\u0003¢\u0006\u0004\b\u000f\u0010\u0010J)\u0010\u0011\u001a\u00028\u00012\u0006\u0010\u0012\u001a\u00028\u00002\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00028\u00010\u0014H\u0086\bø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016J\u0010\u0010\u0017\u001a\u00020\u0018HÖ\u0001¢\u0006\u0004\b\u0019\u0010\u001aJ \u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0012\u001a\u00028\u00002\u0006\u0010\u001d\u001a\u00028\u0001H\u0086\n¢\u0006\u0004\b\u001e\u0010\u001fJ\u0010\u0010 \u001a\u00020!HÖ\u0001¢\u0006\u0004\b\"\u0010#R(\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00030\u00058\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u0088\u0001\u0004\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/fir/caches/NullableMap;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "", "map", "", "constructor-impl", "(Ljava/util/Map;)Ljava/util/Map;", "getMap$annotations", "()V", "getMap", "()Ljava/util/Map;", Namer.EQUALS_METHOD_NAME, "", "other", "equals-impl", "(Ljava/util/Map;Ljava/lang/Object;)Z", "getOrElse", ASTExpr.DEFAULT_MAP_KEY_NAME, "orElse", "Lkotlin/Function0;", "getOrElse-impl", "(Ljava/util/Map;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "hashCode", "", "hashCode-impl", "(Ljava/util/Map;)I", FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "", "value", "set-impl", "(Ljava/util/Map;Ljava/lang/Object;Ljava/lang/Object;)V", "toString", "", "toString-impl", "(Ljava/util/Map;)Ljava/lang/String;", "NullValue", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
@JvmInline
public final class NullableMap<K, V> {
    private final Map<K, Object> map;

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ NullableMap m6788boximpl(Map map) {
        return new NullableMap(map);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static <K, V> Map<K, Object> m6789constructorimpl(Map<K, Object> map) {
        Intrinsics.checkNotNullParameter(map, "map");
        return map;
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m6791equalsimpl(Map<K, Object> map, Object obj) {
        return (obj instanceof NullableMap) && Intrinsics.areEqual(map, ((NullableMap) obj).getMap());
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m6792equalsimpl0(Map<K, Object> map, Map<K, Object> map2) {
        return Intrinsics.areEqual(map, map2);
    }

    @PrivateForInline
    public static /* synthetic */ void getMap$annotations() {
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m6794hashCodeimpl(Map<K, Object> map) {
        return map.hashCode();
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m6796toStringimpl(Map<K, Object> map) {
        return "NullableMap(map=" + map + ')';
    }

    public boolean equals(Object obj) {
        return m6791equalsimpl(this.map, obj);
    }

    public int hashCode() {
        return m6794hashCodeimpl(this.map);
    }

    public String toString() {
        return m6796toStringimpl(this.map);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name and from getter */
    public final /* synthetic */ Map getMap() {
        return this.map;
    }

    private /* synthetic */ NullableMap(Map map) {
        this.map = map;
    }

    /* JADX INFO: renamed from: constructor-impl$default, reason: not valid java name */
    public static /* synthetic */ Map m6790constructorimpl$default(Map map, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 1) != 0) {
            map = new HashMap();
        }
        return m6789constructorimpl(map);
    }

    public final Map<K, Object> getMap() {
        return this.map;
    }

    /* JADX INFO: renamed from: getOrElse-impl, reason: not valid java name */
    public static final V m6793getOrElseimpl(Map<K, Object> map, K k, Function0<? extends V> orElse) {
        Intrinsics.checkNotNullParameter(orElse, "orElse");
        V v = (V) map.get(k);
        if (v == null) {
            return orElse.invoke();
        }
        if (Intrinsics.areEqual(v, NullValue.INSTANCE)) {
            return null;
        }
        return v;
    }

    /* JADX INFO: renamed from: set-impl, reason: not valid java name */
    public static final void m6795setimpl(Map<K, Object> map, K k, V v) {
        if (v == null) {
            v = (V) NullValue.INSTANCE;
        }
        map.put(k, v);
    }

    /* JADX INFO: compiled from: NullableMap.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/caches/NullableMap$NullValue;", "", "()V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    @PrivateForInline
    public static final class NullValue {
        public static final NullValue INSTANCE = new NullValue();

        private NullValue() {
        }
    }
}
