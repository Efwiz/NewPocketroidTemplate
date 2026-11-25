package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.util.List;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzcu {

    /* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
    public static final class zza extends zzfb<zza, C0055zza> implements zzgk {
        private static final zza zzh;
        private static volatile zzgr<zza> zzi;
        private int zzc;
        private float zzd;
        private float zze;
        private float zzf;
        private float zzg;

        private zza() {
        }

        /* JADX INFO: renamed from: com.google.android.gms.internal.mlkit_vision_mediapipe.zzcu$zza$zza, reason: collision with other inner class name */
        /* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
        public static final class C0055zza extends zzfb.zza<zza, C0055zza> implements zzgk {
            private C0055zza() {
                super(zza.zzh);
            }

            /* synthetic */ C0055zza(zzct zzctVar) {
                this();
            }
        }

        public final float zza() {
            return this.zzd;
        }

        public final float zzb() {
            return this.zze;
        }

        public final float zzc() {
            return this.zzg;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r3v13 */
        /* JADX WARN: Type inference failed for: r3v14, types: [com.google.android.gms.internal.mlkit_vision_mediapipe.zzfb$zzc, com.google.android.gms.internal.mlkit_vision_mediapipe.zzgr<com.google.android.gms.internal.mlkit_vision_mediapipe.zzcu$zza>] */
        /* JADX WARN: Type inference failed for: r3v17 */
        /* JADX WARN: Type inference failed for: r3v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzfb
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r3;
            zzct zzctVar = null;
            switch (zzct.zza[i - 1]) {
                case 1:
                    return new zza();
                case 2:
                    return new C0055zza(zzctVar);
                case 3:
                    return zza(zzh, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ခ\u0000\u0002ခ\u0001\u0003ခ\u0002\u0004ခ\u0003", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg"});
                case 4:
                    return zzh;
                case 5:
                    zzgr<zza> zzgrVar = zzi;
                    Object obj3 = zzgrVar;
                    if (zzgrVar == null) {
                        synchronized (zza.class) {
                            zzgr<zza> zzgrVar2 = zzi;
                            r3 = zzgrVar2;
                            if (zzgrVar2 == null) {
                                ?? zzcVar = new zzfb.zzc(zzh);
                                zzi = zzcVar;
                                r3 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r3;
                    }
                    return obj3;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            zza zzaVar = new zza();
            zzh = zzaVar;
            zzfb.zza((Class<zza>) zza.class, zzaVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
    public static final class zzb extends zzfb<zzb, zza> implements zzgk {
        private static final zzb zzd;
        private static volatile zzgr<zzb> zze;
        private zzff<zza> zzc = zzi();

        private zzb() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
        public static final class zza extends zzfb.zza<zzb, zza> implements zzgk {
            private zza() {
                super(zzb.zzd);
            }

            /* synthetic */ zza(zzct zzctVar) {
                this();
            }
        }

        public final List<zza> zza() {
            return this.zzc;
        }

        public static zzb zza(byte[] bArr) throws zzfi {
            return (zzb) zzfb.zza(zzd, bArr);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v13 */
        /* JADX WARN: Type inference failed for: r1v14, types: [com.google.android.gms.internal.mlkit_vision_mediapipe.zzfb$zzc, com.google.android.gms.internal.mlkit_vision_mediapipe.zzgr<com.google.android.gms.internal.mlkit_vision_mediapipe.zzcu$zzb>] */
        /* JADX WARN: Type inference failed for: r1v17 */
        /* JADX WARN: Type inference failed for: r1v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzfb
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r1;
            zzct zzctVar = null;
            switch (zzct.zza[i - 1]) {
                case 1:
                    return new zzb();
                case 2:
                    return new zza(zzctVar);
                case 3:
                    return zza(zzd, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"zzc", zza.class});
                case 4:
                    return zzd;
                case 5:
                    zzgr<zzb> zzgrVar = zze;
                    Object obj3 = zzgrVar;
                    if (zzgrVar == null) {
                        synchronized (zzb.class) {
                            zzgr<zzb> zzgrVar2 = zze;
                            r1 = zzgrVar2;
                            if (zzgrVar2 == null) {
                                ?? zzcVar = new zzfb.zzc(zzd);
                                zze = zzcVar;
                                r1 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r1;
                    }
                    return obj3;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            zzb zzbVar = new zzb();
            zzd = zzbVar;
            zzfb.zza((Class<zzb>) zzb.class, zzbVar);
        }
    }
}
