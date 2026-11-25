package org.jf.dexlib2.builder.debug;

import org.jf.dexlib2.builder.BuilderDebugItem;
import org.jf.dexlib2.iface.debug.StartLocal;
import org.jf.dexlib2.iface.reference.StringReference;
import org.jf.dexlib2.iface.reference.TypeReference;

/* JADX INFO: loaded from: classes6.dex */
public class BuilderStartLocal extends BuilderDebugItem implements StartLocal {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final StringReference f1022name;
    private final int register;
    private final StringReference signature;
    private final TypeReference type;

    @Override // org.jf.dexlib2.iface.debug.DebugItem
    public int getDebugItemType() {
        return 3;
    }

    public BuilderStartLocal(int i, StringReference stringReference, TypeReference typeReference, StringReference stringReference2) {
        this.register = i;
        this.f1022name = stringReference;
        this.type = typeReference;
        this.signature = stringReference2;
    }

    @Override // org.jf.dexlib2.iface.debug.StartLocal
    public int getRegister() {
        return this.register;
    }

    @Override // org.jf.dexlib2.iface.debug.StartLocal
    public StringReference getNameReference() {
        return this.f1022name;
    }

    @Override // org.jf.dexlib2.iface.debug.StartLocal
    public TypeReference getTypeReference() {
        return this.type;
    }

    @Override // org.jf.dexlib2.iface.debug.StartLocal
    public StringReference getSignatureReference() {
        return this.signature;
    }

    @Override // org.jf.dexlib2.iface.debug.LocalInfo
    public String getName() {
        StringReference stringReference = this.f1022name;
        if (stringReference == null) {
            return null;
        }
        return stringReference.getString();
    }

    @Override // org.jf.dexlib2.iface.debug.LocalInfo
    public String getType() {
        TypeReference typeReference = this.type;
        if (typeReference == null) {
            return null;
        }
        return typeReference.getType();
    }

    @Override // org.jf.dexlib2.iface.debug.LocalInfo
    public String getSignature() {
        StringReference stringReference = this.signature;
        if (stringReference == null) {
            return null;
        }
        return stringReference.getString();
    }
}
