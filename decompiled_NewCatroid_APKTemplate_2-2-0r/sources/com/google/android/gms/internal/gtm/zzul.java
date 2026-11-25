package com.google.android.gms.internal.gtm;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzul extends zzuk<zzuw> {
    zzul() {
    }

    @Override // com.google.android.gms.internal.gtm.zzuk
    final int zza(Map.Entry<?, ?> entry) {
        return ((zzuw) entry.getKey()).zzb;
    }

    @Override // com.google.android.gms.internal.gtm.zzuk
    final zzuo<zzuw> zzb(Object obj) {
        return ((zzuv) obj).zza;
    }

    @Override // com.google.android.gms.internal.gtm.zzuk
    final zzuo<zzuw> zzc(Object obj) {
        return ((zzuv) obj).zzU();
    }

    @Override // com.google.android.gms.internal.gtm.zzuk
    final Object zzd(zzuj zzujVar, zzwk zzwkVar, int i) {
        return zzujVar.zzc(zzwkVar, i);
    }

    @Override // com.google.android.gms.internal.gtm.zzuk
    final <UT, UB> UB zze(zzww zzwwVar, Object obj, zzuj zzujVar, zzuo<zzuw> zzuoVar, UB ub, zzxo<UT, UB> zzxoVar) throws IOException {
        Object objValueOf;
        Object objZze;
        zzux zzuxVar = (zzux) obj;
        zzuw zzuwVar = zzuxVar.zzd;
        int i = zzuwVar.zzb;
        if (zzuwVar.zzc != zzye.ENUM) {
            switch (zzuxVar.zzd.zzc) {
                case DOUBLE:
                    objValueOf = Double.valueOf(zzwwVar.zza());
                    break;
                case FLOAT:
                    objValueOf = Float.valueOf(zzwwVar.zzb());
                    break;
                case INT64:
                    objValueOf = Long.valueOf(zzwwVar.zzl());
                    break;
                case UINT64:
                    objValueOf = Long.valueOf(zzwwVar.zzo());
                    break;
                case INT32:
                    objValueOf = Integer.valueOf(zzwwVar.zzg());
                    break;
                case FIXED64:
                    objValueOf = Long.valueOf(zzwwVar.zzk());
                    break;
                case FIXED32:
                    objValueOf = Integer.valueOf(zzwwVar.zzf());
                    break;
                case BOOL:
                    objValueOf = Boolean.valueOf(zzwwVar.zzS());
                    break;
                case STRING:
                    objValueOf = zzwwVar.zzv();
                    break;
                case GROUP:
                    objValueOf = zzwwVar.zzr(zzuxVar.zzc.getClass(), zzujVar);
                    break;
                case MESSAGE:
                    objValueOf = zzwwVar.zzt(zzuxVar.zzc.getClass(), zzujVar);
                    break;
                case BYTES:
                    objValueOf = zzwwVar.zzq();
                    break;
                case UINT32:
                    objValueOf = Integer.valueOf(zzwwVar.zzj());
                    break;
                case ENUM:
                    throw new IllegalStateException("Shouldn't reach here.");
                case SFIXED32:
                    objValueOf = Integer.valueOf(zzwwVar.zzh());
                    break;
                case SFIXED64:
                    objValueOf = Long.valueOf(zzwwVar.zzm());
                    break;
                case SINT32:
                    objValueOf = Integer.valueOf(zzwwVar.zzi());
                    break;
                case SINT64:
                    objValueOf = Long.valueOf(zzwwVar.zzn());
                    break;
                default:
                    objValueOf = null;
                    break;
            }
        } else {
            int iZzg = zzwwVar.zzg();
            zzvc<?> zzvcVar = zzuxVar.zzd.zza;
            if (zzyl.zzc(iZzg) == null) {
                return (UB) zzwz.zzD(i, iZzg, ub, zzxoVar);
            }
            objValueOf = Integer.valueOf(iZzg);
        }
        zzuxVar.zza();
        int iOrdinal = zzuxVar.zzd.zzc.ordinal();
        if ((iOrdinal == 9 || iOrdinal == 10) && (objZze = zzuoVar.zze(zzuxVar.zzd)) != null) {
            objValueOf = zzvi.zzg(objZze, objValueOf);
        }
        zzuoVar.zzi(zzuxVar.zzd, objValueOf);
        return ub;
    }

    @Override // com.google.android.gms.internal.gtm.zzuk
    final void zzf(Object obj) {
        ((zzuv) obj).zza.zzg();
    }

    @Override // com.google.android.gms.internal.gtm.zzuk
    final void zzg(zzww zzwwVar, Object obj, zzuj zzujVar, zzuo<zzuw> zzuoVar) throws IOException {
        zzux zzuxVar = (zzux) obj;
        zzuoVar.zzi(zzuxVar.zzd, zzwwVar.zzt(zzuxVar.zzc.getClass(), zzujVar));
    }

    @Override // com.google.android.gms.internal.gtm.zzuk
    final void zzh(zztd zztdVar, Object obj, zzuj zzujVar, zzuo<zzuw> zzuoVar) throws IOException {
        byte[] bArr;
        zzux zzuxVar = (zzux) obj;
        zzwk zzwkVarZzD = zzuxVar.zzc.zzao().zzD();
        int iZzd = zztdVar.zzd();
        if (iZzd == 0) {
            bArr = zzvi.zzc;
        } else {
            byte[] bArr2 = new byte[iZzd];
            zztdVar.zze(bArr2, 0, 0, iZzd);
            bArr = bArr2;
        }
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArr);
        if (!byteBufferWrap.hasArray()) {
            throw new IllegalArgumentException("Direct buffers not yet supported");
        }
        zzsn zzsnVar = new zzsn(byteBufferWrap, true);
        zzwt.zza().zzb(zzwkVarZzD.getClass()).zzh(zzwkVarZzD, zzsnVar, zzujVar);
        zzuoVar.zzi(zzuxVar.zzd, zzwkVarZzD);
        if (zzsnVar.zzc() != Integer.MAX_VALUE) {
            throw zzvk.zzb();
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzuk
    final boolean zzi(zzwk zzwkVar) {
        return zzwkVar instanceof zzuv;
    }

    @Override // com.google.android.gms.internal.gtm.zzuk
    final void zzj(zztp zztpVar, Map.Entry<?, ?> entry) throws IOException {
        zzuw zzuwVar = (zzuw) entry.getKey();
        zzye zzyeVar = zzye.DOUBLE;
        switch (zzuwVar.zzc) {
            case DOUBLE:
                zztpVar.zzf(zzuwVar.zzb, ((Double) entry.getValue()).doubleValue());
                break;
            case FLOAT:
                zztpVar.zzo(zzuwVar.zzb, ((Float) entry.getValue()).floatValue());
                break;
            case INT64:
                zztpVar.zzt(zzuwVar.zzb, ((Long) entry.getValue()).longValue());
                break;
            case UINT64:
                zztpVar.zzK(zzuwVar.zzb, ((Long) entry.getValue()).longValue());
                break;
            case INT32:
                zztpVar.zzr(zzuwVar.zzb, ((Integer) entry.getValue()).intValue());
                break;
            case FIXED64:
                zztpVar.zzm(zzuwVar.zzb, ((Long) entry.getValue()).longValue());
                break;
            case FIXED32:
                zztpVar.zzk(zzuwVar.zzb, ((Integer) entry.getValue()).intValue());
                break;
            case BOOL:
                zztpVar.zzb(zzuwVar.zzb, ((Boolean) entry.getValue()).booleanValue());
                break;
            case STRING:
                zztpVar.zzG(zzuwVar.zzb, (String) entry.getValue());
                break;
            case GROUP:
                zztpVar.zzq(zzuwVar.zzb, entry.getValue(), zzwt.zza().zzb(entry.getValue().getClass()));
                break;
            case MESSAGE:
                zztpVar.zzv(zzuwVar.zzb, entry.getValue(), zzwt.zza().zzb(entry.getValue().getClass()));
                break;
            case BYTES:
                zztpVar.zzd(zzuwVar.zzb, (zztd) entry.getValue());
                break;
            case UINT32:
                zztpVar.zzI(zzuwVar.zzb, ((Integer) entry.getValue()).intValue());
                break;
            case ENUM:
                zztpVar.zzr(zzuwVar.zzb, ((Integer) entry.getValue()).intValue());
                break;
            case SFIXED32:
                zztpVar.zzx(zzuwVar.zzb, ((Integer) entry.getValue()).intValue());
                break;
            case SFIXED64:
                zztpVar.zzz(zzuwVar.zzb, ((Long) entry.getValue()).longValue());
                break;
            case SINT32:
                zztpVar.zzB(zzuwVar.zzb, ((Integer) entry.getValue()).intValue());
                break;
            case SINT64:
                zztpVar.zzD(zzuwVar.zzb, ((Long) entry.getValue()).longValue());
                break;
        }
    }
}
