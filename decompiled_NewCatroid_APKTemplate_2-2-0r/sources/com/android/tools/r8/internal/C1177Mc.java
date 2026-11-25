package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0699l2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.ToIntFunction;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Mc, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1177Mc implements com.android.tools.r8.utils.structural.x {
    static final /* synthetic */ boolean f = true;
    private final int a;
    private final C0759u2 b;
    private final C0767v2 c;
    private final C0699l2 d;
    private final List e;

    public C1177Mc(int i, C0759u2 c0759u2, C0767v2 c0767v2, C0699l2 c0699l2, ArrayList arrayList) {
        if (!f && !arrayList.isEmpty()) {
            throw new AssertionError();
        }
        this.a = i;
        this.b = c0759u2;
        this.c = c0767v2;
        this.d = c0699l2;
        this.e = arrayList;
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.x U() {
        return this;
    }

    public final List b() {
        return this.e;
    }

    public final C0759u2 c() {
        return this.b;
    }

    public final C0767v2 d() {
        return this.c;
    }

    public final boolean equals(Object obj) {
        return com.android.tools.r8.utils.structural.k.a(this, obj);
    }

    public final int hashCode() {
        return Objects.hash(this.b, this.c, this.d, this.e);
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.y p() {
        return new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.internal.Mc$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.utils.structural.y
            public final void a(com.android.tools.r8.utils.structural.z zVar) {
                C1177Mc.a(zVar);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.a(new ToIntFunction() { // from class: com.android.tools.r8.internal.Mc$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((C1177Mc) obj).a;
            }
        });
    }

    public final C0699l2 a() {
        return this.d;
    }
}
