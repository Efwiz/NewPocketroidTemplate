package com.android.tools.r8.internal;

import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.utils.StringDiagnostic;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Tb, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1367Tb extends RuntimeException {
    private final Origin a;
    private final Position b;

    public C1367Tb(String str) {
        this(str, Origin.unknown());
    }

    public final Origin a() {
        return this.a;
    }

    public final Position b() {
        return this.b;
    }

    public final StringDiagnostic c() {
        return new StringDiagnostic(getMessage(), this.a, this.b);
    }

    public C1367Tb(String str, Throwable th) {
        this(Origin.unknown(), str, th);
    }

    public C1367Tb(String str, Origin origin) {
        this(origin, str, null);
    }

    public C1367Tb(Origin origin, String str, Throwable th) {
        this(str, th, origin, Position.UNKNOWN);
    }

    public C1367Tb(String str, Throwable th, Origin origin, Position position) {
        super(str, th);
        this.a = origin;
        this.b = position;
    }
}
