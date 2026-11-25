package com.android.tools.r8.internal;

import com.android.tools.r8.dex.code.InterfaceC0419c;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.g6, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2049g6 {
    private static final C2049g6 b = new C2049g6(Collections.emptyMap());
    static final /* synthetic */ boolean c = true;
    private final Map a;

    C2049g6(Map map) {
        if (!c && !map.values().stream().noneMatch(new Predicate() { // from class: com.android.tools.r8.internal.g6$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Objects.isNull((C1890e6) obj);
            }
        })) {
            throw new AssertionError();
        }
        this.a = map;
    }

    public static C1973f6 a(C2133h6 c2133h6) {
        return new C1973f6(c2133h6);
    }

    public static C2049g6 a() {
        return b;
    }

    public final C1890e6 a(InterfaceC0419c interfaceC0419c) {
        return (C1890e6) this.a.get(interfaceC0419c);
    }
}
