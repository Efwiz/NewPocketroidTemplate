package com.android.tools.r8.internal;

import com.android.tools.r8.profile.art.ArtProfileConsumer;
import com.android.tools.r8.profile.art.ArtProfileProvider;

/* JADX INFO: renamed from: com.android.tools.r8.internal.z3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3478z3 {
    private final ArtProfileProvider a;
    private final ArtProfileConsumer b;

    public C3478z3(ArtProfileProvider artProfileProvider, ArtProfileConsumer artProfileConsumer) {
        this.a = artProfileProvider;
        this.b = artProfileConsumer;
    }

    public ArtProfileProvider a() {
        return this.a;
    }

    public ArtProfileConsumer b() {
        return this.b;
    }
}
