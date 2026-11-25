package com.android.tools.r8.internal;

import com.reandroid.archive.PathTree;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ab, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC0823Ab extends AbstractC1003Gb {
    public static void a(ArrayList arrayList, Object[] objArr) {
        AbstractC0936Ds.c(objArr, PathTree.NAME_elements);
        arrayList.addAll(AbstractC2129h3.a(objArr));
    }

    public static List b(Iterable iterable) {
        ArrayList arrayList;
        AbstractC0936Ds.c(iterable, "<this>");
        boolean z = iterable instanceof Collection;
        if (z) {
            Collection collection = (Collection) iterable;
            int size = collection.size();
            if (size == 0) {
                return C1940eg.a;
            }
            if (size != 1) {
                return new ArrayList(collection);
            }
            List listSingletonList = Collections.singletonList(iterable instanceof List ? ((List) iterable).get(0) : iterable.iterator().next());
            AbstractC0936Ds.b(listSingletonList, "singletonList(element)");
            return listSingletonList;
        }
        if (z) {
            arrayList = new ArrayList((Collection) iterable);
        } else {
            arrayList = new ArrayList();
            Iterator it = iterable.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next());
            }
        }
        ArrayList arrayList2 = arrayList;
        int size2 = arrayList2.size();
        if (size2 == 0) {
            return C1940eg.a;
        }
        if (size2 != 1) {
            return arrayList2;
        }
        List listSingletonList2 = Collections.singletonList(arrayList2.get(0));
        AbstractC0936Ds.b(listSingletonList2, "singletonList(element)");
        return listSingletonList2;
    }

    public static int a(Iterable iterable) {
        AbstractC0936Ds.c(iterable, "<this>");
        if (iterable instanceof Collection) {
            return ((Collection) iterable).size();
        }
        return 10;
    }

    public static Object a(int i, List list) {
        AbstractC0936Ds.c(list, "<this>");
        if (i < 0 || i > list.size() - 1) {
            return null;
        }
        return list.get(i);
    }

    public static List a(Collection collection) {
        AbstractC0936Ds.c(collection, "<this>");
        if (collection.size() <= 1) {
            return b(collection);
        }
        Object[] array = collection.toArray(new Comparable[0]);
        Comparable[] comparableArr = (Comparable[]) array;
        AbstractC0936Ds.c(comparableArr, "<this>");
        if (comparableArr.length > 1) {
            Arrays.sort(comparableArr);
        }
        return AbstractC2129h3.a(array);
    }

    public static C1947en b(List list) {
        return new C1947en(new C0977Fb(list));
    }

    public static List a(WO wo) {
        if (wo != null) {
            List listSingletonList = Collections.singletonList(wo);
            AbstractC0936Ds.b(listSingletonList, "singletonList(element)");
            return listSingletonList;
        }
        return C1940eg.a;
    }

    public static List a(Object... objArr) {
        return objArr.length > 0 ? AbstractC2129h3.a(objArr) : C1940eg.a;
    }

    public static void a() {
        throw new ArithmeticException("Index overflow has happened.");
    }

    public static List a(Collection collection, Comparator comparator) {
        if (collection.size() <= 1) {
            return b(collection);
        }
        Object[] array = collection.toArray(new Object[0]);
        AbstractC0936Ds.c(array, "<this>");
        if (array.length > 1) {
            Arrays.sort(array, comparator);
        }
        return AbstractC2129h3.a(array);
    }

    public static Set a(List list) {
        int size = list.size();
        if (size == 0) {
            return C2172hg.a;
        }
        if (size != 1) {
            LinkedHashSet linkedHashSet = new LinkedHashSet(SD.a(list.size()));
            Iterator it = list.iterator();
            while (it.hasNext()) {
                linkedHashSet.add(it.next());
            }
            return linkedHashSet;
        }
        Set setSingleton = Collections.singleton(list.get(0));
        AbstractC0936Ds.b(setSingleton, "singleton(element)");
        return setSingleton;
    }

    public static ArrayList a(ArrayList arrayList, List list) {
        ArrayList arrayList2 = new ArrayList(arrayList.size() + list.size());
        arrayList2.addAll(list);
        arrayList2.addAll(arrayList);
        return arrayList2;
    }

    public static String a(Iterable iterable, String str, String str2, String str3, InterfaceC1475Wi interfaceC1475Wi, int i) {
        if ((i & 1) != 0) {
            str = ", ";
        }
        if ((i & 2) != 0) {
            str2 = "";
        }
        if ((i & 4) != 0) {
            str3 = "";
        }
        int i2 = 0;
        int i3 = (i & 8) != 0 ? -1 : 0;
        String str4 = (i & 16) != 0 ? "..." : null;
        if ((i & 32) != 0) {
            interfaceC1475Wi = null;
        }
        AbstractC0936Ds.c(iterable, "<this>");
        AbstractC0936Ds.c(str2, "prefix");
        AbstractC0936Ds.c(str3, "postfix");
        AbstractC0936Ds.c(str4, "truncated");
        StringBuilder sb = new StringBuilder();
        sb.append((CharSequence) str2);
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            Object next = it.next();
            i2++;
            if (i2 > 1) {
                sb.append((CharSequence) str);
            }
            if (i3 >= 0 && i2 > i3) {
                break;
            }
            if (interfaceC1475Wi != null) {
                sb.append((CharSequence) ((C2504m) interfaceC1475Wi).a(next));
            } else {
                if (next != null ? next instanceof CharSequence : true) {
                    sb.append((CharSequence) next);
                } else if (next instanceof Character) {
                    sb.append(((Character) next).charValue());
                } else {
                    sb.append((CharSequence) String.valueOf(next));
                }
            }
        }
        if (i3 >= 0 && i2 > i3) {
            sb.append((CharSequence) str4);
        }
        sb.append((CharSequence) str3);
        String string = sb.toString();
        AbstractC0936Ds.b(string, "joinTo(StringBuilder(), …ed, transform).toString()");
        return string;
    }
}
