package com.android.tools.r8.internal;

import com.android.tools.r8.origin.Origin;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class EF {
    private final Origin a;
    private final C1533Yk b;
    private final Map c;
    private final Map d;
    private final Map e;

    public EF(Origin origin, C1533Yk c1533Yk, HashMap map, HashMap map2, HashMap map3) {
        this.a = origin;
        this.b = c1533Yk;
        this.c = map;
        this.d = map2;
        this.e = map3;
    }

    public Map<C2822q2, C1297Qk> a() {
        return this.c;
    }

    public Map<C2822q2, C1297Qk> b() {
        return this.d;
    }

    public final Origin c() {
        return this.a;
    }

    public Map<C2822q2, C1297Qk> d() {
        return this.e;
    }

    public C1533Yk e() {
        return this.b;
    }
}
