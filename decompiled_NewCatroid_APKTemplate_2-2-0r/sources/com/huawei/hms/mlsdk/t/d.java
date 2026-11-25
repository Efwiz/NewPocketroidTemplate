package com.huawei.hms.mlsdk.t;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/* JADX INFO: compiled from: EventManager.java */
/* JADX INFO: loaded from: classes10.dex */
public class d {
    private volatile Queue<h> a = new LinkedBlockingQueue();

    public int a(h hVar) {
        this.a.add(hVar);
        return this.a.size();
    }

    public boolean b() {
        return !this.a.isEmpty();
    }

    public h c() {
        if (this.a.isEmpty()) {
            return null;
        }
        return this.a.peek();
    }

    public h d() {
        if (this.a.isEmpty()) {
            return null;
        }
        return this.a.poll();
    }

    public int e() {
        return this.a.size();
    }

    public int a() {
        int size = this.a.size();
        this.a.clear();
        return size;
    }
}
