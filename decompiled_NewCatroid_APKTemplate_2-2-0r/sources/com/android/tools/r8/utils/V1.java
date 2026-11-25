package com.android.tools.r8.utils;

import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.C1507Xl;
import com.android.tools.r8.naming.C3634k;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class V1 {
    public static <S, T> List<T> a(List<S> list, final Function<S, Collection<T>> function) {
        final ArrayList arrayList = new ArrayList();
        list.forEach(new Consumer() { // from class: com.android.tools.r8.utils.V1$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                V1.a(arrayList, function, obj);
            }
        });
        return arrayList;
    }

    public static List b(List list, Function function) {
        ArrayList arrayList = null;
        for (int i = 0; i < list.size(); i++) {
            Object obj = list.get(i);
            Collection collection = (Collection) function.apply(obj);
            if (collection != null) {
                if (arrayList == null) {
                    arrayList = new ArrayList((collection.size() + list.size()) - 1);
                    for (int i2 = 0; i2 < i; i2++) {
                        arrayList.add(list.get(i2));
                    }
                }
                arrayList.addAll(collection);
            } else if (arrayList != null) {
                arrayList.add(obj);
            }
        }
        if (arrayList != null) {
            return arrayList;
        }
        return null;
    }

    public static C3634k.b c(List list) {
        if (list == null) {
            return null;
        }
        return (C3634k.b) b(list);
    }

    public static <T> List<T> d(List<T> list) {
        ArrayList arrayList = new ArrayList(list.size());
        for (int size = list.size() - 1; size >= 0; size--) {
            arrayList.add(list.get(size));
        }
        return arrayList;
    }

    public static int c(List list, Predicate predicate) {
        int i = -1;
        for (int i2 = 0; i2 < list.size(); i2++) {
            if (predicate.test(list.get(i2))) {
                if (i != -1) {
                    return -1;
                }
                i = i2;
            }
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(List list, Function function, Object obj) {
        list.addAll((Collection) function.apply(obj));
    }

    public static <T> List<T> a(Collection<T> collection, final Predicate<? super T> predicate) {
        final ArrayList arrayList = new ArrayList(collection.size());
        collection.forEach(new Consumer() { // from class: com.android.tools.r8.utils.V1$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                V1.a(predicate, arrayList, obj);
            }
        });
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Predicate predicate, ArrayList arrayList, Object obj) {
        if (predicate.test(obj)) {
            arrayList.add(obj);
        }
    }

    public static <T> T a(List<T> list) {
        return list.get(0);
    }

    public static int a(List list, Predicate predicate) {
        for (int i = 0; i < list.size(); i++) {
            if (predicate.test(list.get(i))) {
                return i;
            }
        }
        return -1;
    }

    public static <S, T> List<T> a(S[] sArr, Function<S, T> function) {
        ArrayList arrayList = new ArrayList();
        for (S s : sArr) {
            arrayList.add(function.apply(s));
        }
        return arrayList;
    }

    public static <S, T> List<T> a(Collection<S> collection, Function<S, T> function) {
        ArrayList arrayList = new ArrayList(collection.size());
        Iterator<S> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(function.apply(it.next()));
        }
        return arrayList;
    }

    public static <T> T b(List<T> list) {
        return list.get(list.size() - 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object a(Function function, int i, Object obj) {
        return function.apply(obj);
    }

    public static <T> int b(List<T> list, Predicate<T> predicate) {
        for (int size = list.size() - 1; size >= 0; size--) {
            if (predicate.test(list.get(size))) {
                return size;
            }
        }
        return -1;
    }

    public static List a(List list, final Function function, List list2) {
        return a(list, new InterfaceC3947p1() { // from class: com.android.tools.r8.utils.V1$$ExternalSyntheticLambda3
            @Override // com.android.tools.r8.utils.InterfaceC3947p1
            public final Object a(int i, Object obj) {
                return V1.a(function, i, obj);
            }
        }, list2);
    }

    public static List a(List list, InterfaceC3947p1 interfaceC3947p1, List list2) {
        ArrayList arrayList = null;
        for (int i = 0; i < list.size(); i++) {
            Object obj = list.get(i);
            Object objA = interfaceC3947p1.a(i, obj);
            if (objA != obj) {
                if (arrayList == null) {
                    arrayList = new ArrayList(list.size());
                    for (int i2 = 0; i2 < i; i2++) {
                        arrayList.add(list.get(i2));
                    }
                }
                if (objA != null) {
                    arrayList.add(objA);
                }
            } else if (arrayList != null) {
                arrayList.add(obj);
            }
        }
        return arrayList != null ? arrayList : list2;
    }

    public static AbstractC1639am b(InterfaceC3877c1 interfaceC3877c1) {
        final C1507Xl c1507XlG = AbstractC1639am.g();
        interfaceC3877c1.forEach(new Consumer() { // from class: com.android.tools.r8.utils.V1$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c1507XlG.a(obj);
            }
        });
        return c1507XlG.a();
    }

    public static ArrayList b(List list, Comparator comparator) {
        ArrayList arrayList = new ArrayList(list);
        arrayList.sort(comparator);
        return arrayList;
    }

    public static <T> ArrayList<T> a(InterfaceC3877c1<T> interfaceC3877c1) {
        final ArrayList<T> arrayList = new ArrayList<>();
        interfaceC3877c1.forEach(new Consumer() { // from class: com.android.tools.r8.utils.V1$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                arrayList.add(obj);
            }
        });
        return arrayList;
    }

    public static Object a(List list, Object obj, BiFunction biFunction) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            obj = biFunction.apply(obj, it.next());
        }
        return obj;
    }

    public static void a(List list, U1 u1) {
        for (int i = 0; i < list.size(); i++) {
            u1.accept(list.get(i), i);
        }
    }

    public static void a(List list, Comparator comparator) {
        list.sort(comparator);
    }
}
