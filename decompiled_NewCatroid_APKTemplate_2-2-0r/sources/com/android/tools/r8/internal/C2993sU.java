package com.android.tools.r8.internal;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Set;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.sU, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2993sU {
    static final /* synthetic */ boolean h = true;
    private int a = 0;
    private final MR b = new MR();
    private final Set c = OU.c();
    private final ArrayDeque d = new ArrayDeque();
    private final ArrayDeque e = new ArrayDeque();
    private final ArrayList f = new ArrayList();
    private final Function g;

    public C2993sU(Function function) {
        this.g = function;
    }

    private void b(Object obj) {
        Object objPop;
        MR mr = this.b;
        int i = this.a;
        this.a = i + 1;
        mr.a(i, obj);
        this.c.add(obj);
        this.d.push(obj);
        this.e.push(obj);
        for (Object obj2 : (Iterable) this.g.apply(obj)) {
            if (!this.b.containsKey(obj2)) {
                b(obj2);
            } else if (this.c.contains(obj2)) {
                int iA = this.b.a(obj2);
                while (iA < this.b.a(this.e.peek())) {
                    this.e.pop();
                }
            }
        }
        if (this.e.peek() == obj) {
            Set setB = com.android.tools.r8.utils.T2.b(this.d.size());
            do {
                objPop = this.d.pop();
                this.c.remove(objPop);
                setB.add(objPop);
            } while (objPop != obj);
            this.f.add(setB);
            this.e.pop();
        }
    }

    public final ArrayList a(Object obj) {
        if (!h && this.a != 0) {
            throw new AssertionError();
        }
        b(obj);
        return this.f;
    }
}
