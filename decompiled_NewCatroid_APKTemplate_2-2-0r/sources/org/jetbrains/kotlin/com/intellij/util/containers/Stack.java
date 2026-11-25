package org.jetbrains.kotlin.com.intellij.util.containers;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EmptyStackException;
import java.util.List;
import java.util.RandomAccess;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX INFO: loaded from: classes3.dex */
public class Stack<T> extends ArrayList<T> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = ConfigConstants.CONFIG_INIT_SECTION;
        } else {
            objArr[0] = FirebaseAnalytics.Param.ITEMS;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/util/containers/Stack";
        objArr[2] = "<init>";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public Stack() {
    }

    public Stack(int i) {
        super(i);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Stack(Collection<? extends T> collection) {
        super(collection);
        if (collection == null) {
            $$$reportNull$$$0(0);
        }
    }

    public Stack(T... tArr) {
        if (tArr == null) {
            $$$reportNull$$$0(1);
        }
        for (T t : tArr) {
            push(t);
        }
    }

    public void push(T t) {
        add(t);
    }

    public T peek() {
        int size = size();
        if (size == 0) {
            throw new EmptyStackException();
        }
        return get(size - 1);
    }

    public T pop() {
        int size = size();
        if (size == 0) {
            throw new EmptyStackException();
        }
        return remove(size - 1);
    }

    public boolean empty() {
        return isEmpty();
    }

    @Override // java.util.ArrayList, java.util.AbstractList, java.util.Collection, java.util.List
    public boolean equals(Object obj) {
        if ((obj instanceof RandomAccess) && (obj instanceof List)) {
            List list = (List) obj;
            if (size() != list.size()) {
                return false;
            }
            for (int i = 0; i < list.size(); i++) {
                Object obj2 = list.get(i);
                T t = get(i);
                if (obj2 == null) {
                    if (t != null) {
                        return false;
                    }
                } else {
                    if (!obj2.equals(t)) {
                        return false;
                    }
                }
            }
            return true;
        }
        return super.equals(obj);
    }
}
