package com.android.tools.r8.utils;

import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class T0 extends RuntimeException {
    public static final /* synthetic */ int c = 0;
    final Origin a;
    final Position b;

    private T0(RuntimeException runtimeException, Origin origin, Position position) {
        super(runtimeException);
        this.a = origin;
        this.b = position;
    }
}
