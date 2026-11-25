package org.jf.dexlib2.immutable.debug;

import org.jf.dexlib2.iface.debug.EndLocal;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableEndLocal extends ImmutableDebugItem implements EndLocal {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    protected final String f1027name;
    protected final int register;
    protected final String signature;
    protected final String type;

    @Override // org.jf.dexlib2.iface.debug.DebugItem
    public int getDebugItemType() {
        return 5;
    }

    public ImmutableEndLocal(int i, int i2) {
        super(i);
        this.register = i2;
        this.f1027name = null;
        this.type = null;
        this.signature = null;
    }

    public ImmutableEndLocal(int i, int i2, String str, String str2, String str3) {
        super(i);
        this.register = i2;
        this.f1027name = str;
        this.type = str2;
        this.signature = str3;
    }

    public static ImmutableEndLocal of(EndLocal endLocal) {
        if (endLocal instanceof ImmutableEndLocal) {
            return (ImmutableEndLocal) endLocal;
        }
        return new ImmutableEndLocal(endLocal.getCodeAddress(), endLocal.getRegister(), endLocal.getType(), endLocal.getName(), endLocal.getSignature());
    }

    @Override // org.jf.dexlib2.iface.debug.EndLocal
    public int getRegister() {
        return this.register;
    }

    @Override // org.jf.dexlib2.iface.debug.LocalInfo
    public String getName() {
        return this.f1027name;
    }

    @Override // org.jf.dexlib2.iface.debug.LocalInfo
    public String getType() {
        return this.type;
    }

    @Override // org.jf.dexlib2.iface.debug.LocalInfo
    public String getSignature() {
        return this.signature;
    }
}
