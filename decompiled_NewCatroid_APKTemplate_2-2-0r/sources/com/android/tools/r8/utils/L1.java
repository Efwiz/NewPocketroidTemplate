package com.android.tools.r8.utils;

import com.android.tools.r8.internal.AbstractC2734ot;
import com.android.tools.r8.internal.B4;
import com.android.tools.r8.internal.C3332x4;
import com.android.tools.r8.internal.C3405y10;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class L1 {
    static final /* synthetic */ boolean a = true;

    public static Iterator a(LinkedList linkedList) {
        if (a || !AbstractC2734ot.c(linkedList)) {
            return new J1(linkedList);
        }
        throw new AssertionError();
    }

    public static Object b(Iterator it, Predicate predicate) {
        while (it.hasNext()) {
            Object next = it.next();
            if (predicate.test(next)) {
                return next;
            }
        }
        return null;
    }

    public static int a(Iterator it) {
        final C3932m1 c3932m1 = new C3932m1();
        it.forEachRemaining(new Consumer() { // from class: com.android.tools.r8.utils.L1$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c3932m1.c();
            }
        });
        return c3932m1.a();
    }

    public static Object b(ListIterator listIterator) {
        Object objPrevious = listIterator.previous();
        Object next = listIterator.next();
        if (a || objPrevious == next) {
            return objPrevious;
        }
        throw new AssertionError();
    }

    public static Iterator a(Iterator it, Predicate predicate) {
        return new K1(it, predicate);
    }

    public static Object b(ListIterator listIterator, Predicate predicate) {
        while (listIterator.hasPrevious()) {
            Object objPrevious = listIterator.previous();
            if (predicate.test(objPrevious)) {
                return objPrevious;
            }
        }
        throw new C3405y10();
    }

    public static Object a(ListIterator listIterator) {
        if (!listIterator.hasNext()) {
            return null;
        }
        Object next = listIterator.next();
        Object objPrevious = listIterator.previous();
        if (a || objPrevious == next) {
            return next;
        }
        throw new AssertionError();
    }

    public static Object a(B4 b4, Predicate predicate) {
        while (b4.hasPrevious()) {
            C3332x4 c3332x4Previous = b4.previous();
            if (predicate.test(c3332x4Previous)) {
                return c3332x4Previous;
            }
        }
        return null;
    }

    public static boolean a(ListIterator listIterator, Predicate predicate) {
        boolean z;
        Object objA = a(listIterator);
        while (true) {
            if (!listIterator.hasNext()) {
                z = false;
                break;
            }
            if (predicate.test(listIterator.next())) {
                z = true;
                break;
            }
        }
        while (listIterator.hasPrevious() && listIterator.previous() != objA) {
        }
        if (a || a(listIterator) == objA) {
            return z;
        }
        throw new AssertionError();
    }
}
