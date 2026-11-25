package org.jf.dexlib2.iface.debug;

import org.jf.dexlib2.iface.reference.StringReference;
import org.jf.dexlib2.iface.reference.TypeReference;

/* JADX INFO: loaded from: classes6.dex */
public interface StartLocal extends DebugItem, LocalInfo {
    StringReference getNameReference();

    int getRegister();

    StringReference getSignatureReference();

    TypeReference getTypeReference();
}
