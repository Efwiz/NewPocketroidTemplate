package org.jetbrains.kotlin.com.intellij.util.containers;

import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.util.NotNullFunction;
import org.jetbrains.kotlin.com.intellij.util.containers.hash.EqualityPolicy;

/* JADX INFO: loaded from: classes3.dex */
public abstract class SLRUCache<K, V> extends SLRUMap<K, V> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2) ? 2 : 3];
        if (i == 1 || i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/containers/SLRUCache";
        } else if (i != 3) {
            objArr[0] = "hashingStrategy";
        } else {
            objArr[0] = "valueProducer";
        }
        if (i == 1 || i == 2) {
            objArr[1] = "get";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/SLRUCache";
        }
        if (i != 1 && i != 2) {
            if (i != 3) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public abstract V createValue(K k);

    protected SLRUCache(int i, int i2) {
        super(i, i2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected SLRUCache(int i, int i2, EqualityPolicy<? super K> equalityPolicy) {
        super(i, i2, equalityPolicy);
        if (equalityPolicy == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.containers.SLRUMap
    public V get(K k) {
        V v = (V) super.get(k);
        if (v != null) {
            if (v == null) {
                $$$reportNull$$$0(1);
            }
            return v;
        }
        V vCreateValue = createValue(k);
        put(k, vCreateValue);
        if (vCreateValue == null) {
            $$$reportNull$$$0(2);
        }
        return vCreateValue;
    }

    public V getIfCached(K k) {
        return (V) super.get(k);
    }

    public static <K, V> SLRUCache<K, V> create(int i, int i2, final NotNullFunction<? super K, ? extends V> notNullFunction) {
        if (notNullFunction == null) {
            $$$reportNull$$$0(3);
        }
        return new SLRUCache<K, V>(i, i2) { // from class: org.jetbrains.kotlin.com.intellij.util.containers.SLRUCache.1
            private static /* synthetic */ void $$$reportNull$$$0(int i3) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/util/containers/SLRUCache$1", "createValue"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.util.containers.SLRUCache
            public V createValue(K k) {
                V v = (V) notNullFunction.fun(k);
                if (v == null) {
                    $$$reportNull$$$0(0);
                }
                return v;
            }
        };
    }
}
