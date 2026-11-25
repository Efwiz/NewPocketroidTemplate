package com.android.tools.r8.utils;

import com.android.tools.r8.internal.AbstractC1639am;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.utils.l1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public final class C3926l1 implements com.android.tools.r8.utils.structural.x {
    static final /* synthetic */ boolean c = true;
    private final int[] a;
    private final List b;

    private C3926l1(int[] iArr, AbstractC1639am abstractC1639am) {
        this.a = iArr;
        this.b = abstractC1639am;
        if (!c && iArr.length != abstractC1639am.size()) {
            throw new AssertionError();
        }
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.x U() {
        return this;
    }

    public final boolean equals(Object obj) {
        return (obj instanceof C3926l1) && compareTo((C3926l1) obj) == 0;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(Arrays.hashCode(this.a)), this.b);
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.y p() {
        return new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.utils.l1$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.structural.y
            public final void a(com.android.tools.r8.utils.structural.z zVar) {
                C3926l1.a(zVar);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.b(new Function() { // from class: com.android.tools.r8.utils.l1$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C3926l1) obj).a;
            }
        }).f(new Function() { // from class: com.android.tools.r8.utils.l1$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C3926l1) obj).b;
            }
        });
    }

    public final boolean b() {
        return this.a.length == 0;
    }

    public final void a(BiConsumer biConsumer) {
        int i = 0;
        while (true) {
            int[] iArr = this.a;
            if (i >= iArr.length) {
                return;
            }
            biConsumer.accept(Integer.valueOf(iArr[i]), (com.android.tools.r8.utils.structural.x) this.b.get(i));
            i++;
        }
    }

    public static C3921k1 a() {
        return new C3921k1();
    }
}
