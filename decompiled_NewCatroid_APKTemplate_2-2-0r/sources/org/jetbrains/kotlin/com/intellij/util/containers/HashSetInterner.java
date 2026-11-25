package org.jetbrains.kotlin.com.intellij.util.containers;

import java.util.Set;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

/* JADX INFO: loaded from: classes3.dex */
public class HashSetInterner<T> extends Interner<T> {
    private final ObjectOpenHashSet<T> mySet = new ObjectOpenHashSet<>();

    /* JADX WARN: Code duplicated, block: B:18:0x0026  */
    /* JADX WARN: Code duplicated, block: B:19:0x0029  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 4) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "name";
        } else if (i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/containers/HashSetInterner";
        } else if (i == 3) {
            objArr[0] = "name";
        } else if (i != 4) {
            objArr[0] = "initialItems";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/containers/HashSetInterner";
        }
        if (i == 2) {
            objArr[1] = "intern";
        } else if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/HashSetInterner";
        } else {
            objArr[1] = "getValues";
        }
        if (i == 1) {
            objArr[2] = "intern";
        } else if (i != 2) {
            if (i == 3) {
                objArr[2] = "get";
            } else if (i != 4) {
                objArr[2] = "<init>";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.containers.Interner
    public T intern(T t) {
        if (t == null) {
            $$$reportNull$$$0(1);
        }
        T tAddOrGet = this.mySet.addOrGet(t);
        if (tAddOrGet == null) {
            $$$reportNull$$$0(2);
        }
        return tAddOrGet;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.containers.Interner
    public void clear() {
        this.mySet.clear();
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.containers.Interner
    public Set<T> getValues() {
        ObjectOpenHashSet<T> objectOpenHashSet = this.mySet;
        if (objectOpenHashSet == null) {
            $$$reportNull$$$0(4);
        }
        return objectOpenHashSet;
    }
}
