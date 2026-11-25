package org.jetbrains.kotlin.com.intellij.util;

import java.util.Collection;
import java.util.Iterator;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public class CollectionQuery<T> implements Query<T> {
    private final Collection<T> myCollection;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/CollectionQuery";
        } else if (i != 2) {
            objArr[0] = "collection";
        } else {
            objArr[0] = "consumer";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/CollectionQuery";
        } else {
            objArr[1] = "findAll";
        }
        if (i != 1) {
            if (i != 2) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "forEach";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    public CollectionQuery(Collection<T> collection) {
        if (collection == null) {
            $$$reportNull$$$0(0);
        }
        this.myCollection = collection;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.Query
    public Collection<T> findAll() {
        Collection<T> collection = this.myCollection;
        if (collection == null) {
            $$$reportNull$$$0(1);
        }
        return collection;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.Query
    public T findFirst() {
        Iterator<T> it = iterator();
        if (it.hasNext()) {
            return it.next();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.Query
    public boolean forEach(Processor<? super T> processor) {
        if (processor == null) {
            $$$reportNull$$$0(2);
        }
        return ContainerUtil.process(this.myCollection, processor);
    }
}
