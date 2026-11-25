package com.android.tools.r8.internal;

import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.util.BitSet;
import java.util.Currency;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

/* JADX INFO: renamed from: com.android.tools.r8.internal.s00, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2961s00 {
    public static final EZ A;
    public static final FZ B;
    public static final FZ C;
    public static final FZ a = new C1964f00(Class.class, new RZ());
    public static final FZ b = new C1964f00(BitSet.class, new C1808d00());
    public static final EZ c;
    public static final FZ d;
    public static final FZ e;
    public static final FZ f;
    public static final FZ g;
    public static final FZ h;
    public static final FZ i;
    public static final FZ j;
    public static final EZ k;
    public static final FZ l;
    public static final FZ m;
    public static final EZ n;
    public static final EZ o;
    public static final FZ p;
    public static final FZ q;
    public static final FZ r;
    public static final FZ s;
    public static final FZ t;
    public static final FZ u;
    public static final FZ v;
    public static final FZ w;
    public static final FZ x;
    public static final FZ y;
    public static final FZ z;

    static {
        C2349k00 c2349k00 = new C2349k00();
        c = new C2429l00();
        d = new C2040g00(Boolean.TYPE, Boolean.class, c2349k00);
        e = new C2040g00(Byte.TYPE, Byte.class, new C2506m00());
        f = new C2040g00(Short.TYPE, Short.class, new C2580n00());
        g = new C2040g00(Integer.TYPE, Integer.class, new C2658o00());
        h = new C1964f00(AtomicInteger.class, new C2746p00().a());
        i = new C1964f00(AtomicBoolean.class, new C2819q00().a());
        j = new C1964f00(AtomicIntegerArray.class, new HZ().a());
        k = new IZ();
        new JZ();
        new KZ();
        l = new C1964f00(Number.class, new LZ());
        m = new C2040g00(Character.TYPE, Character.class, new MZ());
        NZ nz = new NZ();
        n = new OZ();
        o = new PZ();
        p = new C1964f00(String.class, nz);
        q = new C1964f00(StringBuilder.class, new QZ());
        r = new C1964f00(StringBuffer.class, new SZ());
        s = new C1964f00(URL.class, new TZ());
        t = new C1964f00(URI.class, new UZ());
        u = new C2274j00(InetAddress.class, new VZ());
        v = new C1964f00(UUID.class, new WZ());
        w = new C1964f00(Currency.class, new XZ().a());
        x = new ZZ();
        y = new C2124h00(new C1577a00());
        z = new C1964f00(Locale.class, new C1657b00());
        C1731c00 c1731c00 = new C1731c00();
        A = c1731c00;
        B = new C2274j00(AbstractC1249Ot.class, c1731c00);
        C = new C1881e00();
    }

    public static FZ a(Class cls, EZ ez) {
        return new C1964f00(cls, ez);
    }

    public static FZ a(Class cls, Class cls2, EZ ez) {
        return new C2040g00(cls, cls2, ez);
    }
}
