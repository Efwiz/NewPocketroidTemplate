package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.xx, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3398xx extends AbstractC3467yx {
    private final C3321wx a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C3398xx(C3321wx c3321wx) {
        super(0);
        AbstractC0936Ds.c(c3321wx, "annotation");
        this.a = c3321wx;
    }

    public final C3321wx a() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof C3398xx) && AbstractC0936Ds.a(this.a, ((C3398xx) obj).a);
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final String toString() {
        return "AnnotationValue(annotation=" + this.a + ')';
    }
}
