package com.android.tools.r8.internal;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class OD extends AbstractMap {
    OD() {
    }

    final void a(Consumer consumer) {
        e().forEachRemaining(consumer);
    }

    abstract Iterator e();

    @Override // java.util.AbstractMap, java.util.Map
    public Set entrySet() {
        return new ND(this);
    }

    final Spliterator k() {
        return Spliterators.spliterator(e(), size(), 65);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public abstract int size();
}
