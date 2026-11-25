package com.android.tools.r8.utils;

import com.android.tools.r8.graph.C0800x2;
import com.android.tools.r8.internal.AbstractC1271Pl;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC2734ot;
import com.android.tools.r8.internal.AbstractC3533zt;
import com.android.tools.r8.internal.C1746cA$$ExternalSyntheticLambda0;
import com.android.tools.r8.internal.InterfaceC3010sj;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class I1 {
    public static boolean a(AbstractCollection abstractCollection, Function function) {
        Iterator it = abstractCollection.iterator();
        if (!it.hasNext()) {
            return true;
        }
        Object objApply = function.apply(it.next());
        while (it.hasNext()) {
            if (function.apply(it.next()) != objApply) {
                return false;
            }
        }
        return true;
    }

    public static Object b(Iterable iterable, Predicate predicate) {
        for (Object obj : iterable) {
            if (predicate.test(obj)) {
                return obj;
            }
        }
        return null;
    }

    public static <T> int c(Iterable<T> iterable, Predicate<T> predicate) {
        Iterator<T> it = iterable.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (predicate.test(it.next())) {
                return i;
            }
            i++;
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Iterator d(Iterable iterable, Predicate predicate) {
        return new K1(iterable.iterator(), predicate);
    }

    public static Iterable b(Iterable iterable, final Function function) {
        Objects.requireNonNull(function);
        return AbstractC2734ot.a(iterable, new InterfaceC3010sj() { // from class: com.android.tools.r8.utils.I1$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return function.apply(obj);
            }
        });
    }

    public static ArrayList c(Iterable iterable) {
        ArrayList arrayList = new ArrayList();
        iterable.forEach(new C1746cA$$ExternalSyntheticLambda0(arrayList));
        return arrayList;
    }

    public static Iterable b(final Object obj) {
        return new Iterable() { // from class: com.android.tools.r8.utils.I1$$ExternalSyntheticLambda2
            @Override // java.lang.Iterable
            public final Iterator iterator() {
                return AbstractC3533zt.a(obj);
            }
        };
    }

    public static int b(Iterable iterable) {
        Object objApply = 0;
        BiFunction biFunction = new BiFunction() { // from class: com.android.tools.r8.utils.I1$$ExternalSyntheticLambda3
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return Integer.valueOf(Integer.sum(((Integer) obj).intValue(), ((Integer) obj2).intValue()));
            }
        };
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            objApply = biFunction.apply(objApply, it.next());
        }
        return ((Integer) objApply).intValue();
    }

    public static boolean a(AbstractC1639am abstractC1639am, Function function, Predicate predicate) {
        Iterator<E> it = abstractC1639am.iterator();
        while (it.hasNext()) {
            if (predicate.test(function.apply(it.next()))) {
                return true;
            }
        }
        return false;
    }

    public static Iterable a(final Iterable iterable, final Predicate predicate) {
        return new Iterable() { // from class: com.android.tools.r8.utils.I1$$ExternalSyntheticLambda1
            @Override // java.lang.Iterable
            public final Iterator iterator() {
                return I1.d(iterable, predicate);
            }
        };
    }

    public static <T> int a(Iterable<T> iterable) {
        Iterator<T> it = iterable.iterator();
        int i = 0;
        while (it.hasNext()) {
            it.next();
            i++;
        }
        return i;
    }

    public static int a(C0800x2 c0800x2, final Function function) {
        Objects.requireNonNull(function);
        return b(AbstractC2734ot.a((Iterable) c0800x2, new InterfaceC3010sj() { // from class: com.android.tools.r8.utils.I1$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (Integer) function.apply(obj);
            }
        }));
    }

    public static Iterable a(AbstractC1271Pl abstractC1271Pl) {
        return a((Iterable) abstractC1271Pl, Function.identity());
    }

    public static Iterable a(Iterable iterable, final Function function) {
        Objects.requireNonNull(function);
        return AbstractC2734ot.a(AbstractC2734ot.a(iterable, new InterfaceC3010sj() { // from class: com.android.tools.r8.utils.I1$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (Iterable) function.apply(obj);
            }
        }));
    }

    public static boolean a(BiPredicate biPredicate, Iterable iterable, Iterable iterable2) {
        Iterator it = iterable2.iterator();
        for (Object obj : iterable) {
            if (!it.hasNext() || !biPredicate.test(obj, it.next())) {
                return false;
            }
        }
        return !it.hasNext();
    }
}
