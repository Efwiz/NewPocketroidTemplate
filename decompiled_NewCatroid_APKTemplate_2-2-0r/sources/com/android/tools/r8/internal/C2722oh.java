package com.android.tools.r8.internal;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.oh, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2722oh implements FZ, Cloneable {
    public static final C2722oh c = new C2722oh();
    private List a = Collections.emptyList();
    private List b = Collections.emptyList();

    @Override // com.android.tools.r8.internal.FZ
    public final EZ a(C1862dk c1862dk, I00 i00) {
        Class clsA = i00.a();
        boolean zA = a(clsA, true);
        boolean zA2 = a(clsA, false);
        if (zA || zA2) {
            return new C2633nh(this, zA2, zA, c1862dk, i00);
        }
        return null;
    }

    protected final Object clone() {
        try {
            return (C2722oh) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final boolean a(Field field, boolean z) {
        if ((field.getModifiers() & 136) != 0 || field.isSynthetic()) {
            return true;
        }
        Class<?> type = field.getType();
        if (!Enum.class.isAssignableFrom(type) && (type.isAnonymousClass() || type.isLocalClass())) {
            return true;
        }
        List list = z ? this.a : this.b;
        if (list.isEmpty()) {
            return false;
        }
        Iterator it = list.iterator();
        if (!it.hasNext()) {
            return false;
        }
        AbstractC1288Qc.a(it.next());
        throw null;
    }

    public final boolean a(Class cls, boolean z) {
        if (!Enum.class.isAssignableFrom(cls) && (cls.isAnonymousClass() || cls.isLocalClass())) {
            return true;
        }
        Iterator it = (z ? this.a : this.b).iterator();
        if (!it.hasNext()) {
            return false;
        }
        AbstractC1288Qc.a(it.next());
        throw null;
    }
}
