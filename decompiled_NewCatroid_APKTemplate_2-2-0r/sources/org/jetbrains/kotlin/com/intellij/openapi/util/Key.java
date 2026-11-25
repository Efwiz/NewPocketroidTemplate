package org.jetbrains.kotlin.com.intellij.openapi.util;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.IntObjectMap;

/* JADX INFO: loaded from: classes3.dex */
public class Key<T> {
    private final int myIndex;
    private final String myName;
    private static final AtomicInteger ourKeysCounter = new AtomicInteger();
    private static final IntObjectMap<Key<?>> allKeys = ContainerUtil.createIntKeyWeakValueMap();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 3 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 3 ? 3 : 2];
        if (i == 2) {
            objArr[0] = "holder";
        } else if (i != 3) {
            objArr[0] = "name";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/Key";
        }
        if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/Key";
        } else {
            objArr[1] = "getRequired";
        }
        if (i == 1) {
            objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
        } else if (i == 2) {
            objArr[2] = "getRequired";
        } else if (i != 3) {
            if (i != 4) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "findKeyByName";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 3) {
            throw new IllegalStateException(str2);
        }
    }

    public final boolean equals(Object obj) {
        return obj == this;
    }

    public Key(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        int andIncrement = ourKeysCounter.getAndIncrement();
        this.myIndex = andIncrement;
        this.myName = str;
        IntObjectMap<Key<?>> intObjectMap = allKeys;
        synchronized (intObjectMap) {
            intObjectMap.put(andIncrement, this);
        }
    }

    public final int hashCode() {
        return this.myIndex;
    }

    public String toString() {
        return this.myName;
    }

    public static <T> Key<T> create(String str) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        return new Key<>(str);
    }

    public T get(UserDataHolder userDataHolder) {
        if (userDataHolder == null) {
            return null;
        }
        return (T) userDataHolder.getUserData(this);
    }

    public T get(UserDataHolder userDataHolder, T t) {
        T t2 = get(userDataHolder);
        return t2 == null ? t : t2;
    }

    public T getRequired(UserDataHolder userDataHolder) {
        if (userDataHolder == null) {
            $$$reportNull$$$0(2);
        }
        T t = (T) Objects.requireNonNull(userDataHolder.getUserData(this));
        if (t == null) {
            $$$reportNull$$$0(3);
        }
        return t;
    }

    public boolean isIn(UserDataHolder userDataHolder) {
        return get(userDataHolder) != null;
    }

    public void set(UserDataHolder userDataHolder, T t) {
        if (userDataHolder != null) {
            userDataHolder.putUserData(this, t);
        }
    }

    public static <T> Key<T> getKeyByIndex(int i) {
        Key<T> key;
        IntObjectMap<Key<?>> intObjectMap = allKeys;
        synchronized (intObjectMap) {
            key = (Key) intObjectMap.get(i);
        }
        return key;
    }

    @Deprecated
    public static Key<?> findKeyByName(String str) {
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        IntObjectMap<Key<?>> intObjectMap = allKeys;
        synchronized (intObjectMap) {
            for (Key<?> key : intObjectMap.values()) {
                if (str.equals(((Key) key).myName)) {
                    return key;
                }
            }
            return null;
        }
    }
}
