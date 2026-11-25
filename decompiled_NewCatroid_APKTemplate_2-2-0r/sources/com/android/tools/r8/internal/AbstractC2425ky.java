package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ky, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2425ky {
    private final AbstractC2425ky a;

    public AbstractC2425ky(AbstractC2425ky abstractC2425ky) {
        this.a = abstractC2425ky;
    }

    public abstract AbstractC2191hy a();

    public abstract AbstractC2191hy b();

    public void c() {
        AbstractC2425ky abstractC2425ky = this.a;
        if (abstractC2425ky != null) {
            abstractC2425ky.c();
        }
    }

    public /* synthetic */ AbstractC2425ky() {
        this(null);
    }
}
