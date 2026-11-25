package com.android.tools.r8.internal;

import java.math.BigInteger;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ut, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1434Ut extends AbstractC1249Ot {
    private static final Class[] b = {Integer.TYPE, Long.TYPE, Short.TYPE, Float.TYPE, Double.TYPE, Byte.TYPE, Boolean.TYPE, Character.TYPE, Integer.class, Long.class, Short.class, Float.class, Double.class, Byte.class, Boolean.class, Character.class};
    private Object a;

    public C1434Ut(Boolean bool) {
        c(bool);
    }

    @Override // com.android.tools.r8.internal.AbstractC1249Ot
    public final boolean a() {
        Object obj = this.a;
        return obj instanceof Boolean ? ((Boolean) obj).booleanValue() : Boolean.parseBoolean(f());
    }

    @Override // com.android.tools.r8.internal.AbstractC1249Ot
    public final int b() {
        return this.a instanceof Number ? g().intValue() : Integer.parseInt(f());
    }

    final void c(Object obj) {
        boolean z;
        if (obj instanceof Character) {
            this.a = String.valueOf(((Character) obj).charValue());
            return;
        }
        if ((obj instanceof Number) || (obj instanceof String)) {
            z = true;
            break;
        }
        Class<?> cls = obj.getClass();
        Class[] clsArr = b;
        z = false;
        for (int i = 0; i < 16; i++) {
            if (clsArr[i].isAssignableFrom(cls)) {
                z = true;
                break;
            }
        }
        AbstractC1575a.a(z);
        this.a = obj;
    }

    @Override // com.android.tools.r8.internal.AbstractC1249Ot
    public final long e() {
        return this.a instanceof Number ? g().longValue() : Long.parseLong(f());
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || C1434Ut.class != obj.getClass()) {
            return false;
        }
        C1434Ut c1434Ut = (C1434Ut) obj;
        if (this.a == null) {
            return c1434Ut.a == null;
        }
        if (a(this) && a(c1434Ut)) {
            return g().longValue() == c1434Ut.g().longValue();
        }
        Object obj2 = this.a;
        if (!(obj2 instanceof Number) || !(c1434Ut.a instanceof Number)) {
            return obj2.equals(c1434Ut.a);
        }
        double dDoubleValue = g().doubleValue();
        double dDoubleValue2 = c1434Ut.g().doubleValue();
        if (dDoubleValue != dDoubleValue2) {
            return Double.isNaN(dDoubleValue) && Double.isNaN(dDoubleValue2);
        }
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC1249Ot
    public final String f() {
        Object obj = this.a;
        if (obj instanceof Number) {
            return g().toString();
        }
        return obj instanceof Boolean ? ((Boolean) obj).toString() : (String) obj;
    }

    public final Number g() {
        Object obj = this.a;
        return obj instanceof String ? new TA((String) obj) : (Number) obj;
    }

    public final int hashCode() {
        long jDoubleToLongBits;
        if (this.a == null) {
            return 31;
        }
        if (a(this)) {
            jDoubleToLongBits = g().longValue();
        } else {
            Object obj = this.a;
            if (!(obj instanceof Number)) {
                return obj.hashCode();
            }
            jDoubleToLongBits = Double.doubleToLongBits(g().doubleValue());
        }
        return (int) ((jDoubleToLongBits >>> 32) ^ jDoubleToLongBits);
    }

    public final boolean i() {
        return this.a instanceof Boolean;
    }

    public final boolean j() {
        return this.a instanceof Number;
    }

    public final boolean k() {
        return this.a instanceof String;
    }

    public C1434Ut(Number number) {
        c(number);
    }

    public C1434Ut(String str) {
        c(str);
    }

    C1434Ut(Object obj) {
        c(obj);
    }

    private static boolean a(C1434Ut c1434Ut) {
        Object obj = c1434Ut.a;
        if (!(obj instanceof Number)) {
            return false;
        }
        Number number = (Number) obj;
        return (number instanceof BigInteger) || (number instanceof Long) || (number instanceof Integer) || (number instanceof Short) || (number instanceof Byte);
    }
}
