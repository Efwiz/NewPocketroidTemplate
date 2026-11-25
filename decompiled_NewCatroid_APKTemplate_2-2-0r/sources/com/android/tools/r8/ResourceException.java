package com.android.tools.r8;

import com.android.tools.r8.origin.Origin;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class ResourceException extends Exception {
    private final Origin a;

    public ResourceException(Origin origin, String str) {
        super(str);
        this.a = origin;
    }

    public Origin getOrigin() {
        return this.a;
    }

    public ResourceException(Origin origin, Throwable th) {
        super(th);
        this.a = origin;
    }

    public ResourceException(Origin origin, String str, Throwable th) {
        super(str, th);
        this.a = origin;
    }
}
