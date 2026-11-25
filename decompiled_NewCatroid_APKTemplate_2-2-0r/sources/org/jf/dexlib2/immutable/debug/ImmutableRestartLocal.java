package org.jf.dexlib2.immutable.debug;

import org.jf.dexlib2.iface.debug.RestartLocal;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableRestartLocal extends ImmutableDebugItem implements RestartLocal {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    protected final String f1028name;
    protected final int register;
    protected final String signature;
    protected final String type;

    @Override // org.jf.dexlib2.iface.debug.DebugItem
    public int getDebugItemType() {
        return 6;
    }

    public ImmutableRestartLocal(int i, int i2) {
        super(i);
        this.register = i2;
        this.f1028name = null;
        this.type = null;
        this.signature = null;
    }

    public ImmutableRestartLocal(int i, int i2, String str, String str2, String str3) {
        super(i);
        this.register = i2;
        this.f1028name = str;
        this.type = str2;
        this.signature = str3;
    }

    public static ImmutableRestartLocal of(RestartLocal restartLocal) {
        if (restartLocal instanceof ImmutableRestartLocal) {
            return (ImmutableRestartLocal) restartLocal;
        }
        return new ImmutableRestartLocal(restartLocal.getCodeAddress(), restartLocal.getRegister(), restartLocal.getType(), restartLocal.getName(), restartLocal.getSignature());
    }

    @Override // org.jf.dexlib2.iface.debug.RestartLocal
    public int getRegister() {
        return this.register;
    }

    @Override // org.jf.dexlib2.iface.debug.LocalInfo
    public String getName() {
        return this.f1028name;
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
