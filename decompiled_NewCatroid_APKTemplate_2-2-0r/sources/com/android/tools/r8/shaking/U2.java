package com.android.tools.r8.shaking;

import com.android.tools.r8.internal.V5;
import com.android.tools.r8.origin.Origin;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class U2 implements T2 {
    private final byte[] a;
    private final Origin b;

    public U2(Origin origin, byte[] bArr) {
        this.a = bArr;
        this.b = origin;
    }

    @Override // com.android.tools.r8.shaking.T2
    public final Path a() {
        return null;
    }

    @Override // com.android.tools.r8.shaking.T2
    public final String get() {
        return new String(this.a, StandardCharsets.UTF_8);
    }

    @Override // com.android.tools.r8.shaking.T2
    public final String getName() {
        return this.b.toString();
    }

    @Override // com.android.tools.r8.shaking.T2
    public final Origin getOrigin() {
        return this.b;
    }

    public U2(Origin origin, InputStream inputStream) {
        this(origin, V5.a(inputStream));
    }
}
