package com.android.tools.r8.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Gj, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1012Gj {
    final B0 a;
    final Object b;
    final B0 c;
    final C0985Fj d;
    final Method e;

    C1012Gj(AbstractC0958Ej abstractC0958Ej, Object obj, AbstractC1045Hj abstractC1045Hj, C0985Fj c0985Fj, Class cls) {
        if (abstractC0958Ej == null) {
            throw new IllegalArgumentException("Null containingTypeDefaultInstance");
        }
        if (c0985Fj.a() == L20.f && abstractC1045Hj == null) {
            throw new IllegalArgumentException("Null messageDefaultInstance");
        }
        this.a = abstractC0958Ej;
        this.b = obj;
        this.c = abstractC1045Hj;
        this.d = c0985Fj;
        if (InterfaceC2880qs.class.isAssignableFrom(cls)) {
            this.e = AbstractC1045Hj.a(cls, Integer.TYPE);
        } else {
            this.e = null;
        }
    }

    final Object a(Object obj) {
        if (this.d.b.a() != M20.i) {
            return obj;
        }
        try {
            return this.e.invoke(null, (Integer) obj);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", e);
        } catch (InvocationTargetException e2) {
            Throwable cause = e2.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            }
            if (cause instanceof Error) {
                throw ((Error) cause);
            }
            throw new RuntimeException("Unexpected exception thrown by generated accessor method.", cause);
        }
    }

    final Object b(Object obj) {
        return this.d.b.a() == M20.i ? Integer.valueOf(((InterfaceC2880qs) obj).a()) : obj;
    }
}
