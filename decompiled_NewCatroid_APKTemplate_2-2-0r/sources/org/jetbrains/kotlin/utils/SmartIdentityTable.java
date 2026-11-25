package org.jetbrains.kotlin.utils;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;

/* JADX INFO: compiled from: SmartIdentityTable.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 \u001a*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u00020\u0003:\u0001\u001aB\u0005¢\u0006\u0002\u0010\u0004J\b\u0010\u000e\u001a\u00020\u000fH\u0002J\u0018\u0010\u0010\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0011\u001a\u00028\u0000H\u0086\u0002¢\u0006\u0002\u0010\u0012J!\u0010\u0013\u001a\u00028\u00012\u0006\u0010\u0011\u001a\u00028\u00002\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00010\u0015¢\u0006\u0002\u0010\u0016J \u0010\u0017\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0011\u001a\u00028\u00002\u0006\u0010\u0018\u001a\u00028\u0001H\u0086\u0002¢\u0006\u0002\u0010\u0019R\u0016\u0010\u0005\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\t\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0016\u0010\r\u001a\n\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/utils/SmartIdentityTable;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "", "()V", "keysArray", "", "largeMap", "Ljava/util/IdentityHashMap;", "size", "", "getSize", "()I", "valuesArray", "convertToHashMap", "", "get", ASTExpr.DEFAULT_MAP_KEY_NAME, "(Ljava/lang/Object;)Ljava/lang/Object;", "getOrCreate", "factory", "Lkotlin/Function0;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "value", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "Companion", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SmartIdentityTable<K, V> {
    private IdentityHashMap<K, V> largeMap;
    private List<K> keysArray = new ArrayList(10);
    private List<V> valuesArray = new ArrayList(10);

    public final int getSize() {
        List<K> list = this.keysArray;
        if (list != null) {
            return list.size();
        }
        IdentityHashMap<K, V> identityHashMap = this.largeMap;
        Intrinsics.checkNotNull(identityHashMap);
        return identityHashMap.size();
    }

    public final V get(K key) {
        List<K> list = this.keysArray;
        if (list != null) {
            Iterator<K> it = list.iterator();
            int i = 0;
            while (it.hasNext()) {
                int i2 = i + 1;
                if (it.next() == key) {
                    List<V> list2 = this.valuesArray;
                    Intrinsics.checkNotNull(list2);
                    return list2.get(i);
                }
                i = i2;
            }
            return null;
        }
        IdentityHashMap<K, V> identityHashMap = this.largeMap;
        Intrinsics.checkNotNull(identityHashMap);
        return identityHashMap.get(key);
    }

    public final V set(K key, V value) {
        List<K> list = this.keysArray;
        if (list != null) {
            List<V> list2 = this.valuesArray;
            Intrinsics.checkNotNull(list2);
            int size = list.size();
            for (int i = 0; i < size; i++) {
                if (list.get(i) == key) {
                    V v = list2.get(i);
                    list2.set(i, value);
                    return v;
                }
            }
            if (list.size() < 10) {
                list.add(key);
                list2.add(value);
                return null;
            }
            convertToHashMap();
        }
        IdentityHashMap<K, V> identityHashMap = this.largeMap;
        Intrinsics.checkNotNull(identityHashMap);
        return identityHashMap.put(key, value);
    }

    private final void convertToHashMap() {
        IdentityHashMap<K, V> identityHashMap = new IdentityHashMap<>();
        List<K> list = this.keysArray;
        Intrinsics.checkNotNull(list);
        List<V> list2 = this.valuesArray;
        Intrinsics.checkNotNull(list2);
        int size = list.size();
        for (int i = 0; i < size; i++) {
            identityHashMap.put(list.get(i), list2.get(i));
        }
        this.largeMap = identityHashMap;
        this.keysArray = null;
        this.valuesArray = null;
    }

    public final V getOrCreate(K key, Function0<? extends V> factory) {
        Intrinsics.checkNotNullParameter(factory, "factory");
        V v = get(key);
        if (v != null) {
            return v;
        }
        V vInvoke = factory.invoke();
        set(key, vInvoke);
        return vInvoke;
    }
}
