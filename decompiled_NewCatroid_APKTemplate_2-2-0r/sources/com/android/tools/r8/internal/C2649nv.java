package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/* JADX INFO: renamed from: com.android.tools.r8.internal.nv, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2649nv {
    static final /* synthetic */ boolean b = true;
    private final List a;

    /* JADX INFO: renamed from: com.android.tools.r8.internal.nv$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a {
        private ArrayList a;

        private a() {
            this.a = new ArrayList();
        }

        public a a(C2814px c2814px) {
            this.a.add(c2814px);
            return this;
        }

        public C2649nv a() {
            if (this.a.isEmpty()) {
                throw new C2883qv("Invalid empty consequent set");
            }
            return new C2649nv(this.a);
        }
    }

    private C2649nv(ArrayList arrayList) {
        boolean z = b;
        if (!z && arrayList == null) {
            throw new AssertionError();
        }
        if (!z && arrayList.isEmpty()) {
            throw new AssertionError();
        }
        this.a = arrayList;
    }

    public static a a() {
        return new a();
    }

    public final boolean b() {
        return this.a.isEmpty();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || C2649nv.class != obj.getClass()) {
            return false;
        }
        return this.a.equals(((C2649nv) obj).a);
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final String toString() {
        return (String) this.a.stream().map(new Function() { // from class: com.android.tools.r8.internal.nv$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C2814px) obj).toString();
            }
        }).collect(Collectors.joining(", "));
    }

    public final void a(Consumer consumer) {
        this.a.forEach(consumer);
    }
}
