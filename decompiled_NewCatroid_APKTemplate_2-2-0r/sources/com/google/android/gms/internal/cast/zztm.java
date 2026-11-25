package com.google.android.gms.internal.cast;

import com.google.android.gms.internal.cast.zztm;
import com.google.android.gms.internal.cast.zztp;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public class zztm<MessageType extends zztp<MessageType, BuilderType>, BuilderType extends zztm<MessageType, BuilderType>> extends zzsg<MessageType, BuilderType> {
    protected zztp zza;
    private final zztp zzb;

    protected zztm(MessageType messagetype) {
        this.zzb = messagetype;
        if (messagetype.zzK()) {
            throw new IllegalArgumentException("Default instance must be immutable.");
        }
        this.zza = messagetype.zzx();
    }

    private static void zza(Object obj, Object obj2) {
        zzvf.zza().zzb(obj.getClass()).zze(obj, obj2);
    }

    @Override // com.google.android.gms.internal.cast.zzsg
    /* JADX INFO: renamed from: zzo, reason: merged with bridge method [inline-methods] */
    public final zztm clone() {
        zztm zztmVar = (zztm) this.zzb.zzb(5, null, null);
        zztmVar.zza = zzs();
        return zztmVar;
    }

    public final zztm zzp(zztp zztpVar) {
        if (!this.zzb.equals(zztpVar)) {
            if (!this.zza.zzK()) {
                zzv();
            }
            zza(this.zza, zztpVar);
        }
        return this;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0036, code lost:
    
        if (r4 != false) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final MessageType zzq() {
        /*
            r6 = this;
            com.google.android.gms.internal.cast.zztp r0 = r6.zzs()
            java.lang.Boolean r1 = java.lang.Boolean.TRUE
            boolean r1 = r1.booleanValue()
            r2 = 1
            r3 = 0
            java.lang.Object r4 = r0.zzb(r2, r3, r3)
            java.lang.Byte r4 = (java.lang.Byte) r4
            byte r4 = r4.byteValue()
            if (r4 != r2) goto L19
            goto L38
        L19:
            if (r4 == 0) goto L39
            com.google.android.gms.internal.cast.zzvf r4 = com.google.android.gms.internal.cast.zzvf.zza()
            java.lang.Class r5 = r0.getClass()
            com.google.android.gms.internal.cast.zzvi r4 = r4.zzb(r5)
            boolean r4 = r4.zzh(r0)
            if (r1 == 0) goto L36
            if (r2 == r4) goto L31
            r1 = r3
            goto L32
        L31:
            r1 = r0
        L32:
            r2 = 2
            r0.zzb(r2, r1, r3)
        L36:
            if (r4 == 0) goto L39
        L38:
            return r0
        L39:
            com.google.android.gms.internal.cast.zzvy r1 = new com.google.android.gms.internal.cast.zzvy
            r1.<init>(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zztm.zzq():com.google.android.gms.internal.cast.zztp");
    }

    @Override // com.google.android.gms.internal.cast.zzuw
    /* JADX INFO: renamed from: zzr, reason: merged with bridge method [inline-methods] */
    public MessageType zzs() {
        if (!this.zza.zzK()) {
            return (MessageType) this.zza;
        }
        this.zza.zzF();
        return (MessageType) this.zza;
    }

    @Override // com.google.android.gms.internal.cast.zzuy
    public final /* bridge */ /* synthetic */ zzux zzt() {
        throw null;
    }

    protected final void zzu() {
        if (this.zza.zzK()) {
            return;
        }
        zzv();
    }

    protected void zzv() {
        zztp zztpVarZzx = this.zzb.zzx();
        zza(zztpVarZzx, this.zza);
        this.zza = zztpVarZzx;
    }
}
