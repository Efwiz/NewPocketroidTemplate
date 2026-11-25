package com.android.tools.r8.internal;

import java.io.Serializable;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class BY {
    public static InterfaceC3442yY a(InterfaceC3442yY interfaceC3442yY) {
        if ((interfaceC3442yY instanceof AY) || (interfaceC3442yY instanceof C3512zY)) {
            return interfaceC3442yY;
        }
        return interfaceC3442yY instanceof Serializable ? new C3512zY(interfaceC3442yY) : new AY(interfaceC3442yY);
    }
}
