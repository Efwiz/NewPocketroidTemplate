package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0767v2;
import java.util.Iterator;
import java.util.LinkedList;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class EP {
    private final com.android.tools.r8.graph.o5 a;
    private final int b;
    private final LinkedList c;
    private final LinkedList d;
    private final LinkedList e;

    private EP(com.android.tools.r8.graph.o5 o5Var, int i, LinkedList linkedList, LinkedList linkedList2, LinkedList linkedList3) {
        this.a = o5Var;
        this.b = i;
        this.c = linkedList;
        this.d = linkedList2;
        this.e = linkedList3;
    }

    public static CP a(com.android.tools.r8.graph.o5 o5Var) {
        return new CP(o5Var);
    }

    public final LinkedList b() {
        return this.c;
    }

    public final int c() {
        return this.b;
    }

    public final LinkedList d() {
        return this.d;
    }

    public final LinkedList e() {
        return this.e;
    }

    public final C0767v2 f() {
        return this.a.t();
    }

    public final boolean g() {
        LinkedList linkedList = this.c;
        return (linkedList == null || linkedList.isEmpty()) ? false : true;
    }

    public final boolean h() {
        return ZP.a(this.b);
    }

    public final int i() {
        LinkedList linkedList = this.c;
        if (linkedList != null) {
            return linkedList.size();
        }
        return 0;
    }

    public final int j() {
        LinkedList linkedList = this.d;
        if (linkedList != null) {
            return linkedList.size();
        }
        return 0;
    }

    public final int k() {
        LinkedList linkedList = this.e;
        if (linkedList != null) {
            return linkedList.size();
        }
        return 0;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ProtoMessageInfo(fields=[");
        if (g()) {
            Iterator it = this.c.iterator();
            sb.append(it.next());
            while (it.hasNext()) {
                sb.append(", ").append(it.next());
            }
        }
        return sb.append("])").toString();
    }

    public final com.android.tools.r8.graph.o5 a() {
        return this.a;
    }
}
