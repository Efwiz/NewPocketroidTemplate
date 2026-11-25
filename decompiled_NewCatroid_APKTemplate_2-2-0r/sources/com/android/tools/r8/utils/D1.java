package com.android.tools.r8.utils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public final class D1 {
    private final List a;
    private final List b;

    D1(List list, List list2) {
        this.a = list;
        this.b = list2;
    }

    public final List a() {
        return this.b;
    }

    public final List b() {
        return this.a;
    }

    public final D1 a(Function function) {
        return new D1((List) this.a.stream().map(function).collect(Collectors.toList()), (List) this.b.stream().map(function).collect(Collectors.toList()));
    }
}
