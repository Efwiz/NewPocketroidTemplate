package com.android.tools.r8.utils;

import com.android.tools.r8.internal.AbstractC1048Hm;
import com.android.tools.r8.internal.C0862Bm;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.internal.RE;
import java.util.Collections;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class T2 {
    public static Set a(int i) {
        return Collections.newSetFromMap(new ConcurrentHashMap(i));
    }

    public static <T> Set<T> b(T... tArr) {
        Set<T> setB = b(tArr.length);
        Collections.addAll(setB, tArr);
        return setB;
    }

    public static AbstractC1048Hm c(Object... objArr) {
        C0862Bm c0862BmG = AbstractC1048Hm.g();
        for (Object obj : objArr) {
            if (obj != null) {
                c0862BmG.a(obj);
            }
        }
        return c0862BmG.a();
    }

    public static HashSet a(RE re) {
        HashSet hashSet = new HashSet(1);
        hashSet.add(re);
        return hashSet;
    }

    public static Set b(int i) {
        return Collections.newSetFromMap(new IdentityHashMap(i));
    }

    public static <T> HashSet<T> a(T... tArr) {
        HashSet<T> hashSet = new HashSet<>(tArr.length);
        Collections.addAll(hashSet, tArr);
        return hashSet;
    }

    public static AbstractC1048Hm b(InterfaceC3877c1 interfaceC3877c1) {
        final C0862Bm c0862BmG = AbstractC1048Hm.g();
        interfaceC3877c1.forEach(new Consumer() { // from class: com.android.tools.r8.utils.T2$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c0862BmG.a(obj);
            }
        });
        return c0862BmG.a();
    }

    public static Set a(Object obj) {
        Set setC = OU.c();
        setC.add(obj);
        return setC;
    }

    public static Set a(InterfaceC3877c1 interfaceC3877c1) {
        Set setC = OU.c();
        Objects.requireNonNull(setC);
        interfaceC3877c1.forEach(new T2$$ExternalSyntheticLambda0(setC));
        return setC;
    }

    public static <T> Set<T> a(Iterable<? extends T> iterable) {
        Set<T> setC = OU.c();
        Objects.requireNonNull(setC);
        iterable.forEach(new T2$$ExternalSyntheticLambda0(setC));
        return setC;
    }

    public static <T> Set<T> a(Iterable<T> iterable, Iterable<T> iterable2) {
        Set<T> setC = OU.c();
        Objects.requireNonNull(setC);
        iterable.forEach(new T2$$ExternalSyntheticLambda0(setC));
        iterable2.forEach(new T2$$ExternalSyntheticLambda0(setC));
        return setC;
    }

    public static Set a(Set set, Set set2, Set set3) {
        Set setC = OU.c();
        Objects.requireNonNull(setC);
        set.forEach(new T2$$ExternalSyntheticLambda0(setC));
        set2.forEach(new T2$$ExternalSyntheticLambda0(setC));
        set3.forEach(new T2$$ExternalSyntheticLambda0(setC));
        return setC;
    }

    public static Set a(Set set, Function function) {
        Set setB = b(set.size());
        Iterator it = set.iterator();
        while (it.hasNext()) {
            setB.add(function.apply(it.next()));
        }
        return setB;
    }

    public static Set a(Set set) {
        return A1.b() ? Collections.unmodifiableSet(set) : set;
    }
}
