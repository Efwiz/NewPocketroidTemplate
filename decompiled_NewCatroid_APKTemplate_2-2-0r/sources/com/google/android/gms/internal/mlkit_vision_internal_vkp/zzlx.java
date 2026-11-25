package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzlx extends zzans<zzlx, zzlu> implements zzapg {
    private static final zzlx zzb;
    private zzaob<zzlw> zze = zzI();

    static {
        zzlx zzlxVar = new zzlx();
        zzb = zzlxVar;
        zzans.zzM(zzlx.class, zzlxVar);
    }

    private zzlx() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"zze", zzlw.class});
        }
        if (i2 == 3) {
            return new zzlx();
        }
        zzlr zzlrVar = null;
        if (i2 == 4) {
            return new zzlu(zzlrVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
