package org.jetbrains.kotlin.com.intellij.util.containers;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.reandroid.apk.UncompressedFiles;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.com.intellij.openapi.util.SystemInfoRt;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Hash;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectOpenCustomHashSet;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

/* JADX INFO: loaded from: classes3.dex */
public final class CollectionFactory {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 3:
            case 4:
            case 14:
                objArr[0] = "hashingStrategy";
                break;
            case 2:
            case 13:
            default:
                objArr[0] = "strategy";
                break;
            case 5:
            case 6:
                objArr[0] = FirebaseAnalytics.Param.ITEMS;
                break;
            case 7:
                objArr[0] = FirebaseAnalytics.Param.SOURCE;
                break;
            case 8:
            case 9:
            case 10:
                objArr[0] = UncompressedFiles.NAME_paths;
                break;
            case 11:
            case 15:
                objArr[0] = "map";
                break;
            case 12:
                objArr[0] = "collection";
                break;
            case 16:
                objArr[0] = FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX;
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/CollectionFactory";
        switch (i) {
            case 1:
                objArr[2] = "createWeakMap";
                break;
            case 2:
                objArr[2] = "createConcurrentWeakKeyWeakValueMap";
                break;
            case 3:
            default:
                objArr[2] = "createConcurrentWeakMap";
                break;
            case 4:
                objArr[2] = "createConcurrentWeakKeySoftValueMap";
                break;
            case 5:
                objArr[2] = "createCharSequenceSet";
                break;
            case 6:
                objArr[2] = "createCaseInsensitiveStringSet";
                break;
            case 7:
                objArr[2] = "createCaseInsensitiveStringMap";
                break;
            case 8:
            case 9:
            case 10:
                objArr[2] = "createFilePathSet";
                break;
            case 11:
                objArr[2] = "createSmallMemoryFootprintMap";
                break;
            case 12:
                objArr[2] = "createSmallMemoryFootprintSet";
                break;
            case 13:
                objArr[2] = "createSoftMap";
                break;
            case 14:
                objArr[2] = "createConcurrentSoftMap";
                break;
            case 15:
                objArr[2] = "trimMap";
                break;
            case 16:
                objArr[2] = "trimSet";
                break;
            case 17:
            case 19:
                objArr[2] = "createCustomHashingStrategyMap";
                break;
            case 18:
                objArr[2] = "adaptStrategy";
                break;
            case 20:
                objArr[2] = "createCustomHashingStrategySet";
                break;
            case 21:
                objArr[2] = "createLinkedCustomHashingStrategySet";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public static <K, V> ConcurrentMap<K, V> createConcurrentWeakMap() {
        return new ConcurrentWeakHashMap(0.75f);
    }

    public static <K, V> ConcurrentMap<K, V> createConcurrentWeakValueMap() {
        return new ConcurrentWeakValueHashMap();
    }

    public static <K, V> ConcurrentMap<K, V> createConcurrentSoftValueMap() {
        return new ConcurrentSoftValueHashMap();
    }

    public static <K, V> ConcurrentMap<K, V> createConcurrentWeakIdentityMap() {
        return new ConcurrentWeakHashMap(HashingStrategy.identity());
    }

    public static <K, V> Map<K, V> createWeakMap(int i, float f, HashingStrategy<? super K> hashingStrategy) {
        if (hashingStrategy == null) {
            $$$reportNull$$$0(1);
        }
        return new WeakHashMap(i, f, hashingStrategy);
    }

    public static <K, V> Map<K, V> createSoftKeySoftValueMap() {
        return new SoftKeySoftValueHashMap();
    }

    public static <K, V> Map<K, V> createWeakIdentityMap(int i, float f) {
        return createWeakMap(i, f, HashingStrategy.identity());
    }

    public static <K, V> ConcurrentMap<K, V> createConcurrentWeakKeyWeakValueMap() {
        return new ConcurrentWeakKeyWeakValueHashMap(100, 0.75f, Runtime.getRuntime().availableProcessors(), HashingStrategy.canonical());
    }

    public static <K, V> ConcurrentMap<K, V> createConcurrentWeakKeyWeakValueMap(HashingStrategy<? super K> hashingStrategy) {
        if (hashingStrategy == null) {
            $$$reportNull$$$0(2);
        }
        return new ConcurrentWeakKeyWeakValueHashMap(100, 0.75f, Runtime.getRuntime().availableProcessors(), hashingStrategy);
    }

    public static <K, V> ConcurrentMap<K, V> createConcurrentWeakKeySoftValueMap() {
        return createConcurrentWeakKeySoftValueMap(100, 0.75f, Runtime.getRuntime().availableProcessors(), HashingStrategy.canonical());
    }

    public static <K, V> ConcurrentMap<K, V> createConcurrentWeakKeySoftValueMap(int i, float f, int i2, HashingStrategy<? super K> hashingStrategy) {
        if (hashingStrategy == null) {
            $$$reportNull$$$0(4);
        }
        return new ConcurrentWeakKeySoftValueHashMap(i, f, i2, hashingStrategy);
    }

    public static Set<CharSequence> createCharSequenceSet(List<? extends CharSequence> list) {
        if (list == null) {
            $$$reportNull$$$0(5);
        }
        return new ObjectOpenCustomHashSet(list, FastUtilHashingStrategies.getCharSequenceStrategy(true));
    }

    public static Set<String> createCaseInsensitiveStringSet() {
        return new ObjectOpenCustomHashSet(FastUtilHashingStrategies.getCaseInsensitiveStringStrategy());
    }

    public static <V> Map<String, V> createCaseInsensitiveStringMap() {
        return new Object2ObjectOpenCustomHashMap(FastUtilHashingStrategies.getCaseInsensitiveStringStrategy());
    }

    public static <V> Map<String, V> createCaseInsensitiveStringMap(Map<String, V> map) {
        if (map == null) {
            $$$reportNull$$$0(7);
        }
        return new Object2ObjectOpenCustomHashMap(map, FastUtilHashingStrategies.getCaseInsensitiveStringStrategy());
    }

    public static <K, V> ConcurrentMap<K, V> createConcurrentSoftKeySoftValueMap(int i, float f, int i2) {
        return new ConcurrentSoftKeySoftValueHashMap(i, f, i2, HashingStrategy.canonical());
    }

    public static Set<String> createFilePathSet() {
        return SystemInfoRt.isFileSystemCaseSensitive ? new HashSet() : createCaseInsensitiveStringSet();
    }

    public static <V> Map<String, V> createFilePathMap() {
        return SystemInfoRt.isFileSystemCaseSensitive ? new HashMap() : createCaseInsensitiveStringMap();
    }

    public static <K, V> Map<K, V> createSmallMemoryFootprintMap() {
        return new Object2ObjectOpenHashMap();
    }

    public static <K> Set<K> createSmallMemoryFootprintSet(int i) {
        return new ObjectOpenHashSet(i);
    }

    public static <K, V> ConcurrentMap<K, V> createConcurrentSoftMap() {
        return new ConcurrentSoftHashMap();
    }

    public static <K, V> Map<K, V> createCustomHashingStrategyMap(HashingStrategy<? super K> hashingStrategy) {
        if (hashingStrategy == null) {
            $$$reportNull$$$0(17);
        }
        return new Object2ObjectOpenCustomHashMap(adaptStrategy(hashingStrategy));
    }

    private static <K> Hash.Strategy<K> adaptStrategy(final HashingStrategy<? super K> hashingStrategy) {
        if (hashingStrategy == null) {
            $$$reportNull$$$0(18);
        }
        return new FastUtilHashingStrategies.SerializableHashStrategy<K>() { // from class: org.jetbrains.kotlin.com.intellij.util.containers.CollectionFactory.1
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Hash.Strategy
            public int hashCode(K k) {
                return hashingStrategy.hashCode(k);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Hash.Strategy
            public boolean equals(K k, K k2) {
                return hashingStrategy.equals(k, k2);
            }
        };
    }

    public static <K> Set<K> createCustomHashingStrategySet(HashingStrategy<? super K> hashingStrategy) {
        if (hashingStrategy == null) {
            $$$reportNull$$$0(20);
        }
        return new ObjectOpenCustomHashSet(adaptStrategy(hashingStrategy));
    }
}
