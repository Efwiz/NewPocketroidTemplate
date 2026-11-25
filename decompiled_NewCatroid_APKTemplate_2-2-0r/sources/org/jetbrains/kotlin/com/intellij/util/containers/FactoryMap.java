package org.jetbrains.kotlin.com.intellij.util.containers;

import com.reandroid.arsc.value.CompoundEntry;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.RecursionGuard;
import org.jetbrains.kotlin.com.intellij.openapi.util.RecursionManager;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;

/* JADX INFO: loaded from: classes3.dex */
public abstract class FactoryMap<K, V> implements Map<K, V> {
    private Map<K, V> myMap;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 5 || i == 6 || i == 7) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 2 || i == 5 || i == 6 || i == 7) ? 3 : 2];
        if (i == 2) {
            objArr[0] = "m";
        } else if (i == 5 || i == 6) {
            objArr[0] = "computeValue";
        } else if (i != 7) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/containers/FactoryMap";
        } else {
            objArr[0] = "mapCreator";
        }
        switch (i) {
            case 2:
            case 5:
            case 6:
            case 7:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/FactoryMap";
                break;
            case 3:
                objArr[1] = CompoundEntry.NAME_values;
                break;
            case 4:
                objArr[1] = "entrySet";
                break;
            default:
                objArr[1] = "keySet";
                break;
        }
        if (i == 2) {
            objArr[2] = "putAll";
        } else if (i == 5) {
            objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
        } else if (i == 6 || i == 7) {
            objArr[2] = "createMap";
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 5 && i != 6 && i != 7) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    protected abstract V create(K k);

    private FactoryMap() {
    }

    protected Map<K, V> createMap() {
        return new HashMap();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    public V get(Object obj) {
        Map<K, V> map = getMap();
        Object objNotNull = notNull(obj);
        Object obj2 = map.get(objNotNull);
        if (obj2 == null) {
            RecursionGuard.StackStamp stackStampMarkStack = RecursionManager.markStack();
            V vCreate = create(obj);
            if (stackStampMarkStack.mayCacheNow()) {
                map.put(objNotNull, notNull(vCreate));
            }
            obj2 = vCreate;
        }
        return (V) nullize(obj2);
    }

    private Map<K, V> getMap() {
        Map<K, V> map = this.myMap;
        if (map != null) {
            return map;
        }
        Map<K, V> mapCreateMap = createMap();
        this.myMap = mapCreateMap;
        return mapCreateMap;
    }

    private static <T> T FAKE_NULL() {
        return (T) ObjectUtils.NULL;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static <T> T notNull(Object obj) {
        return obj == 0 ? (T) FAKE_NULL() : obj;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T> T nullize(T t) {
        if (t == FAKE_NULL()) {
            return null;
        }
        return t;
    }

    @Override // java.util.Map
    public final boolean containsKey(Object obj) {
        return getMap().containsKey(notNull(obj));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    public V put(K k, V v) {
        return (V) nullize(getMap().put(notNull(k), notNull(v)));
    }

    @Override // java.util.Map
    public V remove(Object obj) {
        return (V) nullize(getMap().remove(obj));
    }

    @Override // java.util.Map
    public Set<K> keySet() {
        Set<K> setKeySet = getMap().keySet();
        Object objFAKE_NULL = FAKE_NULL();
        if (!setKeySet.contains(objFAKE_NULL)) {
            if (setKeySet == null) {
                $$$reportNull$$$0(1);
            }
            return setKeySet;
        }
        HashSet hashSet = new HashSet(setKeySet);
        hashSet.remove(objFAKE_NULL);
        hashSet.add(null);
        return hashSet;
    }

    @Override // java.util.Map
    public void clear() {
        getMap().clear();
    }

    @Override // java.util.Map
    public int size() {
        return getMap().size();
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return getMap().isEmpty();
    }

    @Override // java.util.Map
    public boolean containsValue(Object obj) {
        return getMap().containsValue(obj);
    }

    @Override // java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
        if (map == null) {
            $$$reportNull$$$0(2);
        }
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override // java.util.Map
    public Collection<V> values() {
        List map = ContainerUtil.map((Collection) getMap().values(), new Function() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.FactoryMap$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return FactoryMap.nullize(obj);
            }
        });
        if (map == null) {
            $$$reportNull$$$0(3);
        }
        return map;
    }

    @Override // java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> setMap2Set = ContainerUtil.map2Set(getMap().entrySet(), new Function() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.FactoryMap$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return FactoryMap.lambda$entrySet$0((Map.Entry) obj);
            }
        });
        if (setMap2Set == null) {
            $$$reportNull$$$0(4);
        }
        return setMap2Set;
    }

    static /* synthetic */ Map.Entry lambda$entrySet$0(Map.Entry entry) {
        return new AbstractMap.SimpleEntry(nullize(entry.getKey()), nullize(entry.getValue()));
    }

    public String toString() {
        return String.valueOf(this.myMap);
    }

    public static <K, V> Map<K, V> create(final Function<? super K, ? extends V> function) {
        if (function == null) {
            $$$reportNull$$$0(5);
        }
        return new FactoryMap<K, V>() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.FactoryMap.1
            {
                super();
            }

            @Override // org.jetbrains.kotlin.com.intellij.util.containers.FactoryMap
            protected V create(K k) {
                return (V) function.fun(k);
            }
        };
    }
}
