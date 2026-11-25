package com.google.android.gms.internal.mlkit_vision_pose_common;

import com.badlogic.gdx.net.HttpStatus;
import org.catrobat.catroid.common.Constants;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbm {

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zza extends zzgd<zza, zzb> implements zzhn {
        private static final zza zzg;
        private static volatile zzhv<zza> zzh;
        private int zzc;
        private C0056zza zzd;
        private int zze;
        private zzab zzf;

        /* JADX INFO: renamed from: com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zza$zza, reason: collision with other inner class name */
        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class C0056zza extends zzgd<C0056zza, C0057zza> implements zzhn {
            private static final C0056zza zzh;
            private static volatile zzhv<C0056zza> zzi;
            private int zzc;
            private int zzd;
            private boolean zze;
            private zzae zzf;
            private zzam zzg;

            private C0056zza() {
            }

            /* JADX INFO: renamed from: com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zza$zza$zza, reason: collision with other inner class name */
            /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
            public static final class C0057zza extends zzgd.zza<C0056zza, C0057zza> implements zzhn {
                private C0057zza() {
                    super(C0056zza.zzh);
                }

                /* synthetic */ C0057zza(zzbo zzboVar) {
                    this();
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r7v12 */
            /* JADX WARN: Type inference failed for: r7v13, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zza$zza>] */
            /* JADX WARN: Type inference failed for: r7v16 */
            /* JADX WARN: Type inference failed for: r7v17 */
            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
            protected final Object zza(int i, Object obj, Object obj2) {
                ?? r7;
                zzbo zzboVar = null;
                switch (zzbo.zza[i - 1]) {
                    case 1:
                        return new C0056zza();
                    case 2:
                        return new C0057zza(zzboVar);
                    case 3:
                        return zza(zzh, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဇ\u0001\u0003ဉ\u0002\u0004ဉ\u0003", new Object[]{"zzc", "zzd", zzbx.zzb(), "zze", "zzf", "zzg"});
                    case 4:
                        return zzh;
                    case 5:
                        zzhv<C0056zza> zzhvVar = zzi;
                        Object obj3 = zzhvVar;
                        if (zzhvVar == null) {
                            synchronized (C0056zza.class) {
                                zzhv<C0056zza> zzhvVar2 = zzi;
                                r7 = zzhvVar2;
                                if (zzhvVar2 == null) {
                                    ?? zzcVar = new zzgd.zzc(zzh);
                                    zzi = zzcVar;
                                    r7 = zzcVar;
                                }
                                break;
                            }
                            obj3 = r7;
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
                C0056zza c0056zza = new C0056zza();
                zzh = c0056zza;
                zzgd.zza((Class<C0056zza>) C0056zza.class, c0056zza);
            }
        }

        private zza() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zzb extends zzgd.zza<zza, zzb> implements zzhn {
            private zzb() {
                super(zza.zzg);
            }

            /* synthetic */ zzb(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zza>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zza();
                case 2:
                    return new zzb(zzboVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဋ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzhv<zza> zzhvVar = zzh;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zza.class) {
                            zzhv<zza> zzhvVar2 = zzh;
                            r2 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzg);
                                zzh = zzcVar;
                                r2 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r2;
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
            zzg = zzaVar;
            zzgd.zza((Class<zza>) zza.class, zzaVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzaa extends zzgd<zzaa, zza> implements zzhn {
        private static final zzaa zzg;
        private static volatile zzhv<zzaa> zzh;
        private int zzc;
        private int zzd;
        private boolean zze;
        private String zzf = "";

        private zzaa() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzaa, zza> implements zzhn {
            private zza() {
                super(zzaa.zzg);
            }

            /* synthetic */ zza(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r3v13 */
        /* JADX WARN: Type inference failed for: r3v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzaa>] */
        /* JADX WARN: Type inference failed for: r3v17 */
        /* JADX WARN: Type inference failed for: r3v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r3;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzaa();
                case 2:
                    return new zza(zzboVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဇ\u0001\u0003ဈ\u0002", new Object[]{"zzc", "zzd", zzal.zza.zzb(), "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzhv<zzaa> zzhvVar = zzh;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzaa.class) {
                            zzhv<zzaa> zzhvVar2 = zzh;
                            r3 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzg);
                                zzh = zzcVar;
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
            zzaa zzaaVar = new zzaa();
            zzg = zzaaVar;
            zzgd.zza((Class<zzaa>) zzaa.class, zzaaVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzab extends zzgd<zzab, zza> implements zzhn {
        private static final zzab zzj;
        private static volatile zzhv<zzab> zzk;
        private int zzc;
        private long zzd;
        private long zze;
        private long zzf;
        private long zzg;
        private long zzh;
        private long zzi;

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzab, zza> implements zzhn {
            private zza() {
                super(zzab.zzj);
            }

            /* synthetic */ zza(zzbo zzboVar) {
                this();
            }

            public final zza zza(long j) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzab) this.zza).zza(j);
                return this;
            }

            public final zza zzb(long j) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzab) this.zza).zzb(j);
                return this;
            }

            public final zza zzc(long j) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzab) this.zza).zzc(j);
                return this;
            }

            public final zza zzd(long j) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzab) this.zza).zzd(j);
                return this;
            }

            public final zza zze(long j) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzab) this.zza).zze(j);
                return this;
            }

            public final zza zzf(long j) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzab) this.zza).zzf(j);
                return this;
            }
        }

        static {
            zzab zzabVar = new zzab();
            zzj = zzabVar;
            zzgd.zza((Class<zzab>) zzab.class, zzabVar);
        }

        private zzab() {
        }

        public static zza zza() {
            return zzj.zzh();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(long j) {
            this.zzc |= 1;
            this.zzd = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzb(long j) {
            this.zzc |= 2;
            this.zze = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzc(long j) {
            this.zzc |= 4;
            this.zzf = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzd(long j) {
            this.zzc |= 8;
            this.zzg = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zze(long j) {
            this.zzc |= 16;
            this.zzh = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzf(long j) {
            this.zzc |= 32;
            this.zzi = j;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r8v12 */
        /* JADX WARN: Type inference failed for: r8v13, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzab>] */
        /* JADX WARN: Type inference failed for: r8v16 */
        /* JADX WARN: Type inference failed for: r8v17 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r8;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzab();
                case 2:
                    return new zza(zzboVar);
                case 3:
                    return zza(zzj, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0000\u0000\u0001ဃ\u0000\u0002ဃ\u0001\u0003ဃ\u0002\u0004ဃ\u0003\u0005ဃ\u0004\u0006ဃ\u0005", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg", "zzh", "zzi"});
                case 4:
                    return zzj;
                case 5:
                    zzhv<zzab> zzhvVar = zzk;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzab.class) {
                            zzhv<zzab> zzhvVar2 = zzk;
                            r8 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzj);
                                zzk = zzcVar;
                                r8 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r8;
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
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzac extends zzgd<zzac, zzb> implements zzhn {
        private static final zzac zzj;
        private static volatile zzhv<zzac> zzk;
        private int zzc;
        private int zzd;
        private int zze;
        private int zzf;
        private int zzg;
        private boolean zzh;
        private float zzi;

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public enum zza implements zzgf {
            UNKNOWN_CLASSIFICATIONS(0),
            NO_CLASSIFICATIONS(1),
            ALL_CLASSIFICATIONS(2);

            private static final zzgi<zza> zzd = new zzce();
            private final int zze;

            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgf
            public final int zza() {
                return this.zze;
            }

            public static zzgh zzb() {
                return zzcd.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zze);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zza(int i) {
                this.zze = i;
            }
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public enum zzc implements zzgf {
            UNKNOWN_CONTOURS(0),
            NO_CONTOURS(1),
            ALL_CONTOURS(2);

            private static final zzgi<zzc> zzd = new zzcf();
            private final int zze;

            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgf
            public final int zza() {
                return this.zze;
            }

            public static zzgh zzb() {
                return zzcg.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zze);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zzc(int i) {
                this.zze = i;
            }
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public enum zzd implements zzgf {
            UNKNOWN_LANDMARKS(0),
            NO_LANDMARKS(1),
            ALL_LANDMARKS(2);

            private static final zzgi<zzd> zzd = new zzci();
            private final int zze;

            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgf
            public final int zza() {
                return this.zze;
            }

            public static zzgh zzb() {
                return zzch.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zze);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zzd(int i) {
                this.zze = i;
            }
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public enum zze implements zzgf {
            UNKNOWN_PERFORMANCE(0),
            FAST(1),
            ACCURATE(2);

            private static final zzgi<zze> zzd = new zzcj();
            private final int zze;

            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgf
            public final int zza() {
                return this.zze;
            }

            public static zzgh zzb() {
                return zzck.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zze);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zze(int i) {
                this.zze = i;
            }
        }

        private zzac() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zzb extends zzgd.zza<zzac, zzb> implements zzhn {
            private zzb() {
                super(zzac.zzj);
            }

            /* synthetic */ zzb(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r12v12 */
        /* JADX WARN: Type inference failed for: r12v13, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzac>] */
        /* JADX WARN: Type inference failed for: r12v16 */
        /* JADX WARN: Type inference failed for: r12v17 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r12;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzac();
                case 2:
                    return new zzb(zzboVar);
                case 3:
                    return zza(zzj, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဌ\u0001\u0003ဌ\u0002\u0004ဌ\u0003\u0005ဇ\u0004\u0006ခ\u0005", new Object[]{"zzc", "zzd", zzd.zzb(), "zze", zza.zzb(), "zzf", zze.zzb(), "zzg", zzc.zzb(), "zzh", "zzi"});
                case 4:
                    return zzj;
                case 5:
                    zzhv<zzac> zzhvVar = zzk;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzac.class) {
                            zzhv<zzac> zzhvVar2 = zzk;
                            r12 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzj);
                                zzk = zzcVar;
                                r12 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r12;
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
            zzac zzacVar = new zzac();
            zzj = zzacVar;
            zzgd.zza((Class<zzac>) zzac.class, zzacVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzad extends zzgd.zze<zzad, zza> implements zzhn {
        private static final zzad zzbd;
        private static volatile zzhv<zzad> zzbe;
        private zzp zzaa;
        private zzm zzab;
        private zzo zzac;
        private zzr zzad;
        private zzq zzae;
        private zzs zzaf;
        private zzt zzag;
        private zzu zzah;
        private zzv zzai;
        private zzw zzaj;
        private zzj zzak;
        private zzl zzal;
        private zzk zzam;
        private zzah zzan;
        private zzaa zzao;
        private zza zzap;
        private zzb zzaq;
        private zzd zzar;
        private zzc zzas;
        private zze zzat;
        private zzf zzau;
        private zzi zzav;
        private zzg zzaw;
        private zzh zzax;
        private zzbg zzaz;
        private zzag zzba;
        private zzaj zzbb;
        private int zzd;
        private int zze;
        private zzbh zzf;
        private int zzg;
        private boolean zzh;
        private zzak zzi;
        private zzz zzj;
        private zzy zzk;
        private zzx zzl;
        private zzap zzm;
        private zzbd zzn;
        private zzao zzo;
        private zzaq zzp;
        private zzas zzq;
        private zzar zzr;
        private zzav zzs;
        private zzay zzt;
        private zzax zzu;
        private zzaz zzv;
        private zzbb zzw;
        private zzbc zzx;
        private zzau zzy;
        private zzbe zzz;
        private byte zzbc = 2;
        private zzgm<zzjp.zzf> zzay = zzl();

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zzb<zzad, zza> implements zzhn {
            private zza() {
                super(zzad.zzbd);
            }

            /* synthetic */ zza(zzbo zzboVar) {
                this();
            }

            public final zzbh zza() {
                return ((zzad) this.zza).zza();
            }

            public final zza zza(zzbh.zza zzaVar) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzad) this.zza).zza((zzbh) ((zzgd) zzaVar.zzh()));
                return this;
            }

            public final zza zza(zzcb zzcbVar) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzad) this.zza).zza(zzcbVar);
                return this;
            }

            public final zza zza(boolean z) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzad) this.zza).zza(true);
                return this;
            }

            public final zza zza(zzaz zzazVar) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzad) this.zza).zza(zzazVar);
                return this;
            }

            public final zza zza(zzaz.zza zzaVar) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzad) this.zza).zza((zzaz) ((zzgd) zzaVar.zzh()));
                return this;
            }

            public final zza zza(zzg.zzb zzbVar) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzad) this.zza).zza((zzg) ((zzgd) zzbVar.zzh()));
                return this;
            }
        }

        static {
            zzad zzadVar = new zzad();
            zzbd = zzadVar;
            zzgd.zza((Class<zzad>) zzad.class, zzadVar);
        }

        private zzad() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static zza zza(zzad zzadVar) {
            return (zza) zzbd.zza(zzadVar);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zzaz zzazVar) {
            zzazVar.getClass();
            this.zzv = zzazVar;
            this.zzd |= 65536;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zzbh zzbhVar) {
            zzbhVar.getClass();
            this.zzf = zzbhVar;
            this.zzd |= 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zzg zzgVar) {
            zzgVar.getClass();
            this.zzaw = zzgVar;
            this.zze |= 2048;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zzcb zzcbVar) {
            this.zzg = zzcbVar.zza();
            this.zzd |= 2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(boolean z) {
            this.zzd |= 4;
            this.zzh = true;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static zza zzb() {
            return (zza) zzbd.zzh();
        }

        public final zzbh zza() {
            zzbh zzbhVar = this.zzf;
            return zzbhVar == null ? zzbh.zzc() : zzbhVar;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v11 */
        /* JADX WARN: Type inference failed for: r0v12, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzad>] */
        /* JADX WARN: Type inference failed for: r0v17 */
        /* JADX WARN: Type inference failed for: r0v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r0;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzad();
                case 2:
                    return new zza(zzboVar);
                case 3:
                    return zza(zzbd, "\u00011\u0000\u0002\u000131\u0000\u0001\u0001\u0001ဉ\u0000\u0002ဌ\u0001\u0003ဉ\u0003\u0004ဉ\u0005\u0005ဉ\u0007\u0006ဉ\b\u0007ဉ\t\bဉ\u0015\tဉ\u0016\nဉ\u0017\u000bဉ\u0018\fဉ\u0019\rဉ\u001a\u000eဉ\u001b\u000fဉ\u001c\u0010ဉ\u001d\u0011ဉ\u001e\u0012ဉ\f\u0013ဉ\u0012\u0014ဉ\u0004\u0015ဉ\u0013\u0016ဉ\u0014\u0017ဉ\u001f\u0018ဉ \u0019ဉ!\u001aဉ\r\u001bဉ\u000e\u001cဉ\u000f\u001dဉ\u0006\u001eဉ$\u001fဉ% ဉ&!ဉ'\"ဉ(#ဉ)$ဉ*%ဇ\u0002'ဉ\"(ဉ#)Л*ဉ-,ဉ\u0010-ဉ\u0011.ဉ+/ဉ,0ဉ\n1ဉ\u000b2ဉ.3ဉ/", new Object[]{"zzd", "zze", "zzf", "zzg", zzcb.zzb(), "zzi", "zzk", "zzm", "zzn", "zzo", "zzaa", "zzab", "zzac", "zzad", "zzae", "zzaf", "zzag", "zzah", "zzai", "zzaj", "zzr", "zzx", "zzj", "zzy", "zzz", "zzak", "zzal", "zzam", "zzs", "zzt", "zzu", "zzl", "zzap", "zzaq", "zzar", "zzas", "zzat", "zzau", "zzav", "zzh", "zzan", "zzao", "zzay", zzjp.zzf.class, "zzaz", "zzv", "zzw", "zzaw", "zzax", "zzp", "zzq", "zzba", "zzbb"});
                case 4:
                    return zzbd;
                case 5:
                    zzhv<zzad> zzhvVar = zzbe;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzad.class) {
                            zzhv<zzad> zzhvVar2 = zzbe;
                            r0 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzbd);
                                zzbe = zzcVar;
                                r0 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r0;
                    }
                    return obj3;
                case 6:
                    return Byte.valueOf(this.zzbc);
                case 7:
                    this.zzbc = (byte) (obj == null ? 0 : 1);
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzag extends zzgd<zzag, zza> implements zzhn {
        private static final zzag zzk;
        private static volatile zzhv<zzag> zzl;
        private int zzc;
        private long zzd;
        private int zze;
        private int zzf;
        private int zzg;
        private int zzh;
        private int zzi;
        private int zzj;

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public enum zzb implements zzgf {
            SOURCE_UNKNOWN(0),
            BITMAP(1),
            BYTEARRAY(2),
            BYTEBUFFER(3),
            FILEPATH(4),
            ANDROID_MEDIA_IMAGE(5);

            private static final zzgi<zzb> zzg = new zzcn();
            private final int zzh;

            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgf
            public final int zza() {
                return this.zzh;
            }

            public static zzgh zzb() {
                return zzco.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zzh);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zzb(int i) {
                this.zzh = i;
            }
        }

        static {
            zzag zzagVar = new zzag();
            zzk = zzagVar;
            zzgd.zza((Class<zzag>) zzag.class, zzagVar);
        }

        private zzag() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r11v12 */
        /* JADX WARN: Type inference failed for: r11v13, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzag>] */
        /* JADX WARN: Type inference failed for: r11v16 */
        /* JADX WARN: Type inference failed for: r11v17 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r11;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzag();
                case 2:
                    return new zza(zzboVar);
                case 3:
                    return zza(zzk, "\u0001\u0007\u0000\u0001\u0001\u0007\u0007\u0000\u0000\u0000\u0001ဃ\u0000\u0002ဌ\u0001\u0003ဌ\u0002\u0004ဋ\u0003\u0005ဋ\u0004\u0006ဋ\u0005\u0007ဋ\u0006", new Object[]{"zzc", "zzd", "zze", zzb.zzb(), "zzf", zzae.zza.zzb(), "zzg", "zzh", "zzi", "zzj"});
                case 4:
                    return zzk;
                case 5:
                    zzhv<zzag> zzhvVar = zzl;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzag.class) {
                            zzhv<zzag> zzhvVar2 = zzl;
                            r11 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzk);
                                zzl = zzcVar;
                                r11 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r11;
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

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzag, zza> implements zzhn {
            private zza() {
                super(zzag.zzk);
            }

            /* synthetic */ zza(zzbo zzboVar) {
                this();
            }
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzah extends zzgd<zzah, zza> implements zzhn {
        private static final zzah zzg;
        private static volatile zzhv<zzah> zzh;
        private int zzc;
        private int zzd;
        private boolean zze;
        private String zzf = "";

        private zzah() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzah, zza> implements zzhn {
            private zza() {
                super(zzah.zzg);
            }

            /* synthetic */ zza(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r3v13 */
        /* JADX WARN: Type inference failed for: r3v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzah>] */
        /* JADX WARN: Type inference failed for: r3v17 */
        /* JADX WARN: Type inference failed for: r3v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r3;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzah();
                case 2:
                    return new zza(zzboVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဇ\u0001\u0003ဈ\u0002", new Object[]{"zzc", "zzd", zzal.zza.zzb(), "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzhv<zzah> zzhvVar = zzh;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzah.class) {
                            zzhv<zzah> zzhvVar2 = zzh;
                            r3 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzg);
                                zzh = zzcVar;
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
            zzah zzahVar = new zzah();
            zzg = zzahVar;
            zzgd.zza((Class<zzah>) zzah.class, zzahVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzai extends zzgd<zzai, zza> implements zzhn {
        private static final zzai zzg;
        private static volatile zzhv<zzai> zzh;
        private int zzc;
        private float zzd;
        private float zze;
        private float zzf;

        private zzai() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzai, zza> implements zzhn {
            private zza() {
                super(zzai.zzg);
            }

            /* synthetic */ zza(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzai>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzai();
                case 2:
                    return new zza(zzboVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ခ\u0000\u0002ခ\u0001\u0003ခ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzhv<zzai> zzhvVar = zzh;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzai.class) {
                            zzhv<zzai> zzhvVar2 = zzh;
                            r2 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzg);
                                zzh = zzcVar;
                                r2 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r2;
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
            zzai zzaiVar = new zzai();
            zzg = zzaiVar;
            zzgd.zza((Class<zzai>) zzai.class, zzaiVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzaj extends zzgd<zzaj, zzb> implements zzhn {
        private static final zzaj zze;
        private static volatile zzhv<zzaj> zzf;
        private int zzc;
        private int zzd;

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public enum zza implements zzgf {
            UNKNOWN(0),
            TRANSLATE(1);

            private static final zzgi<zza> zzc = new zzcp();
            private final int zzd;

            zza(int i) {
                this.zzd = i;
            }

            public static zzgh zzb() {
                return zzcq.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zzd);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgf
            public final int zza() {
                return this.zzd;
            }
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zzb extends zzgd.zza<zzaj, zzb> implements zzhn {
            private zzb() {
                super(zzaj.zze);
            }

            /* synthetic */ zzb(zzbo zzboVar) {
                this();
            }
        }

        static {
            zzaj zzajVar = new zzaj();
            zze = zzajVar;
            zzgd.zza((Class<zzaj>) zzaj.class, zzajVar);
        }

        private zzaj() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v13 */
        /* JADX WARN: Type inference failed for: r1v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzaj>] */
        /* JADX WARN: Type inference failed for: r1v17 */
        /* JADX WARN: Type inference failed for: r1v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r1;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzaj();
                case 2:
                    return new zzb(zzboVar);
                case 3:
                    return zza(zze, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001ဌ\u0000", new Object[]{"zzc", "zzd", zza.zzb()});
                case 4:
                    return zze;
                case 5:
                    zzhv<zzaj> zzhvVar = zzf;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzaj.class) {
                            zzhv<zzaj> zzhvVar2 = zzf;
                            r1 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zze);
                                zzf = zzcVar;
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
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzak extends zzgd<zzak, zza> implements zzhn {
        private static final zzak zzk;
        private static volatile zzhv<zzak> zzl;
        private int zzc;
        private zzam zzd;
        private long zze;
        private int zzf;
        private long zzg;
        private int zzh;
        private long zzi;
        private zzgk zzj = zzk();

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public enum zzb implements zzgf {
            UNKNOWN_STATUS(0),
            EXPLICITLY_REQUESTED(1),
            IMPLICITLY_REQUESTED(2),
            MODEL_INFO_RETRIEVAL_SUCCEEDED(3),
            MODEL_INFO_RETRIEVAL_FAILED(4),
            SCHEDULED(5),
            DOWNLOADING(6),
            SUCCEEDED(7),
            FAILED(8),
            LIVE(9),
            UPDATE_AVAILABLE(10),
            DOWNLOADED(11),
            STARTED(12);

            private static final zzgi<zzb> zzn = new zzcr();
            private final int zzo;

            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgf
            public final int zza() {
                return this.zzo;
            }

            public static zzgh zzb() {
                return zzcs.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zzo);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zzb(int i) {
                this.zzo = i;
            }
        }

        private zzak() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzak, zza> implements zzhn {
            private zza() {
                super(zzak.zzk);
            }

            /* synthetic */ zza(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r11v12 */
        /* JADX WARN: Type inference failed for: r11v13, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzak>] */
        /* JADX WARN: Type inference failed for: r11v16 */
        /* JADX WARN: Type inference failed for: r11v17 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r11;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzak();
                case 2:
                    return new zza(zzboVar);
                case 3:
                    return zza(zzk, "\u0001\u0007\u0000\u0001\u0001\u0007\u0007\u0000\u0001\u0000\u0001ဉ\u0000\u0002ဃ\u0001\u0003ဌ\u0002\u0004ဃ\u0003\u0005ဌ\u0004\u0006ဂ\u0005\u0007\u0016", new Object[]{"zzc", "zzd", "zze", "zzf", zzbx.zzb(), "zzg", "zzh", zzb.zzb(), "zzi", "zzj"});
                case 4:
                    return zzk;
                case 5:
                    zzhv<zzak> zzhvVar = zzl;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzak.class) {
                            zzhv<zzak> zzhvVar2 = zzl;
                            r11 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzk);
                                zzl = zzcVar;
                                r11 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r11;
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
            zzak zzakVar = new zzak();
            zzk = zzakVar;
            zzgd.zza((Class<zzak>) zzak.class, zzakVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzal extends zzgd<zzal, zzb> implements zzhn {
        private static final zzal zzl;
        private static volatile zzhv<zzal> zzm;
        private int zzc;
        private int zzf;
        private int zzi;
        private long zzj;
        private boolean zzk;
        private String zzd = "";
        private String zze = "";
        private String zzg = "";
        private String zzh = "";

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public enum zza implements zzgf {
            TYPE_UNKNOWN(0),
            CUSTOM(1),
            AUTOML_IMAGE_LABELING(2),
            BASE_TRANSLATE(3),
            CUSTOM_OBJECT_DETECTION(4),
            CUSTOM_IMAGE_LABELING(5),
            BASE_ENTITY_EXTRACTION(6);

            private static final zzgi<zza> zzh = new zzcu();
            private final int zzi;

            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgf
            public final int zza() {
                return this.zzi;
            }

            public static zzgh zzb() {
                return zzct.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zzi);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zza(int i) {
                this.zzi = i;
            }
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public enum zzc implements zzgf {
            SOURCE_UNKNOWN(0),
            APP_ASSET(1),
            LOCAL(2),
            CLOUD(3),
            SDK_BUILT_IN(4),
            URI(5);

            private static final zzgi<zzc> zzg = new zzcv();
            private final int zzh;

            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgf
            public final int zza() {
                return this.zzh;
            }

            public static zzgh zzb() {
                return zzcw.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zzh);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zzc(int i) {
                this.zzh = i;
            }
        }

        private zzal() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zzb extends zzgd.zza<zzal, zzb> implements zzhn {
            private zzb() {
                super(zzal.zzl);
            }

            /* synthetic */ zzb(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r12v12 */
        /* JADX WARN: Type inference failed for: r12v13, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzal>] */
        /* JADX WARN: Type inference failed for: r12v16 */
        /* JADX WARN: Type inference failed for: r12v17 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r12;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzal();
                case 2:
                    return new zzb(zzboVar);
                case 3:
                    return zza(zzl, "\u0001\b\u0000\u0001\u0001\b\b\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဈ\u0001\u0003ဌ\u0002\u0004ဈ\u0003\u0005ဈ\u0004\u0006ဌ\u0005\u0007ဃ\u0006\bဇ\u0007", new Object[]{"zzc", "zzd", "zze", "zzf", zzc.zzb(), "zzg", "zzh", "zzi", zza.zzb(), "zzj", "zzk"});
                case 4:
                    return zzl;
                case 5:
                    zzhv<zzal> zzhvVar = zzm;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzal.class) {
                            zzhv<zzal> zzhvVar2 = zzm;
                            r12 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzl);
                                zzm = zzcVar;
                                r12 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r12;
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
            zzal zzalVar = new zzal();
            zzl = zzalVar;
            zzgd.zza((Class<zzal>) zzal.class, zzalVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzam extends zzgd<zzam, zzb> implements zzhn {
        private static final zzam zzh;
        private static volatile zzhv<zzam> zzi;
        private int zzc;
        private zzal zzd;
        private zza zze;
        private zza zzf;
        private boolean zzg;

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd<zza, C0058zza> implements zzhn {
            private static final zza zzh;
            private static volatile zzhv<zza> zzi;
            private int zzc;
            private boolean zzd;
            private boolean zze;
            private boolean zzf;
            private boolean zzg;

            private zza() {
            }

            /* JADX INFO: renamed from: com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzam$zza$zza, reason: collision with other inner class name */
            /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
            public static final class C0058zza extends zzgd.zza<zza, C0058zza> implements zzhn {
                private C0058zza() {
                    super(zza.zzh);
                }

                /* synthetic */ C0058zza(zzbo zzboVar) {
                    this();
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r3v13 */
            /* JADX WARN: Type inference failed for: r3v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzam$zza>] */
            /* JADX WARN: Type inference failed for: r3v17 */
            /* JADX WARN: Type inference failed for: r3v18 */
            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
            protected final Object zza(int i, Object obj, Object obj2) {
                ?? r3;
                zzbo zzboVar = null;
                switch (zzbo.zza[i - 1]) {
                    case 1:
                        return new zza();
                    case 2:
                        return new C0058zza(zzboVar);
                    case 3:
                        return zza(zzh, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဇ\u0000\u0002ဇ\u0001\u0003ဇ\u0002\u0004ဇ\u0003", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg"});
                    case 4:
                        return zzh;
                    case 5:
                        zzhv<zza> zzhvVar = zzi;
                        Object obj3 = zzhvVar;
                        if (zzhvVar == null) {
                            synchronized (zza.class) {
                                zzhv<zza> zzhvVar2 = zzi;
                                r3 = zzhvVar2;
                                if (zzhvVar2 == null) {
                                    ?? zzcVar = new zzgd.zzc(zzh);
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
                zzgd.zza((Class<zza>) zza.class, zzaVar);
            }
        }

        private zzam() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zzb extends zzgd.zza<zzam, zzb> implements zzhn {
            private zzb() {
                super(zzam.zzh);
            }

            /* synthetic */ zzb(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r3v13 */
        /* JADX WARN: Type inference failed for: r3v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzam>] */
        /* JADX WARN: Type inference failed for: r3v17 */
        /* JADX WARN: Type inference failed for: r3v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r3;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzam();
                case 2:
                    return new zzb(zzboVar);
                case 3:
                    return zza(zzh, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဉ\u0002\u0004ဇ\u0003", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg"});
                case 4:
                    return zzh;
                case 5:
                    zzhv<zzam> zzhvVar = zzi;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzam.class) {
                            zzhv<zzam> zzhvVar2 = zzi;
                            r3 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzh);
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
            zzam zzamVar = new zzam();
            zzh = zzamVar;
            zzgd.zza((Class<zzam>) zzam.class, zzamVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzan extends zzgd<zzan, zzb> implements zzhn {
        private static final zzan zzh;
        private static volatile zzhv<zzan> zzi;
        private int zzc;
        private int zzd;
        private float zze;
        private int zzf;
        private int zzg;

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public enum zza implements zzgf {
            CATEGORY_UNKNOWN(0),
            CATEGORY_HOME_GOOD(1),
            CATEGORY_FASHION_GOOD(2),
            CATEGORY_ANIMAL(3),
            CATEGORY_FOOD(4),
            CATEGORY_PLACE(5),
            CATEGORY_PLANT(6);

            private static final zzgi<zza> zzh = new zzcy();
            private final int zzi;

            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgf
            public final int zza() {
                return this.zzi;
            }

            public static zzgh zzb() {
                return zzcx.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zzi);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zza(int i) {
                this.zzi = i;
            }
        }

        private zzan() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zzb extends zzgd.zza<zzan, zzb> implements zzhn {
            private zzb() {
                super(zzan.zzh);
            }

            /* synthetic */ zzb(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r7v12 */
        /* JADX WARN: Type inference failed for: r7v13, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzan>] */
        /* JADX WARN: Type inference failed for: r7v16 */
        /* JADX WARN: Type inference failed for: r7v17 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r7;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzan();
                case 2:
                    return new zzb(zzboVar);
                case 3:
                    return zza(zzh, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဌ\u0000\u0002ခ\u0001\u0003င\u0002\u0004ဋ\u0003", new Object[]{"zzc", "zzd", zza.zzb(), "zze", "zzf", "zzg"});
                case 4:
                    return zzh;
                case 5:
                    zzhv<zzan> zzhvVar = zzi;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzan.class) {
                            zzhv<zzan> zzhvVar2 = zzi;
                            r7 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzh);
                                zzi = zzcVar;
                                r7 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r7;
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
            zzan zzanVar = new zzan();
            zzh = zzanVar;
            zzgd.zza((Class<zzan>) zzan.class, zzanVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzao extends zzgd<zzao, zzc> implements zzhn {
        private static final zzgj<Integer, zza> zzg = new zzcz();
        private static final zzgj<Integer, zzb> zzi = new zzda();
        private static final zzao zzk;
        private static volatile zzhv<zzao> zzl;
        private int zzc;
        private zzaf zzd;
        private zzdx.zza zze;
        private zzgk zzf = zzk();
        private zzgk zzh = zzk();
        private zzae zzj;

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public enum zza implements zzgf {
            FORMAT_UNKNOWN(0),
            FORMAT_CODE_128(1),
            FORMAT_CODE_39(2),
            FORMAT_CODE_93(4),
            FORMAT_CODABAR(8),
            FORMAT_DATA_MATRIX(16),
            FORMAT_EAN_13(32),
            FORMAT_EAN_8(64),
            FORMAT_ITF(128),
            FORMAT_QR_CODE(256),
            FORMAT_UPC_A(512),
            FORMAT_UPC_E(1024),
            FORMAT_PDF417(2048),
            FORMAT_AZTEC(4096);

            private static final zzgi<zza> zzo = new zzdc();
            private final int zzp;

            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgf
            public final int zza() {
                return this.zzp;
            }

            public static zzgh zzb() {
                return zzdb.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zzp);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zza(int i) {
                this.zzp = i;
            }
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public enum zzb implements zzgf {
            TYPE_UNKNOWN(0),
            TYPE_CONTACT_INFO(1),
            TYPE_EMAIL(2),
            TYPE_ISBN(3),
            TYPE_PHONE(4),
            TYPE_PRODUCT(5),
            TYPE_SMS(6),
            TYPE_TEXT(7),
            TYPE_URL(8),
            TYPE_WIFI(9),
            TYPE_GEO(10),
            TYPE_CALENDAR_EVENT(11),
            TYPE_DRIVER_LICENSE(12);

            private static final zzgi<zzb> zzn = new zzdd();
            private final int zzo;

            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgf
            public final int zza() {
                return this.zzo;
            }

            public static zzgh zzb() {
                return zzde.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zzo);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zzb(int i) {
                this.zzo = i;
            }
        }

        private zzao() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zzc extends zzgd.zza<zzao, zzc> implements zzhn {
            private zzc() {
                super(zzao.zzk);
            }

            /* synthetic */ zzc(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r9v12 */
        /* JADX WARN: Type inference failed for: r9v13, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzao>] */
        /* JADX WARN: Type inference failed for: r9v16 */
        /* JADX WARN: Type inference failed for: r9v17 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r9;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzao();
                case 2:
                    return new zzc(zzboVar);
                case 3:
                    return zza(zzk, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0002\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003\u001e\u0004\u001e\u0005ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf", zza.zzb(), "zzh", zzb.zzb(), "zzj"});
                case 4:
                    return zzk;
                case 5:
                    zzhv<zzao> zzhvVar = zzl;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzao.class) {
                            zzhv<zzao> zzhvVar2 = zzl;
                            r9 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzk);
                                zzl = zzcVar;
                                r9 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r9;
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

        /* JADX WARN: Type inference failed for: r0v0, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzcz, com.google.android.gms.internal.mlkit_vision_pose_common.zzgj<java.lang.Integer, com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzao$zza>] */
        /* JADX WARN: Type inference failed for: r0v1, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzda, com.google.android.gms.internal.mlkit_vision_pose_common.zzgj<java.lang.Integer, com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzao$zzb>] */
        static {
            zzao zzaoVar = new zzao();
            zzk = zzaoVar;
            zzgd.zza((Class<zzao>) zzao.class, zzaoVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzap extends zzgd<zzap, zza> implements zzhn {
        private static final zzap zzj;
        private static volatile zzhv<zzap> zzk;
        private int zzc;
        private zzaf zzd;
        private zzdx.zzb zze;
        private zzae zzf;
        private zzac zzg;
        private int zzh;
        private int zzi;

        private zzap() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzap, zza> implements zzhn {
            private zza() {
                super(zzap.zzj);
            }

            /* synthetic */ zza(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r8v12 */
        /* JADX WARN: Type inference failed for: r8v13, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzap>] */
        /* JADX WARN: Type inference failed for: r8v16 */
        /* JADX WARN: Type inference failed for: r8v17 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r8;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzap();
                case 2:
                    return new zza(zzboVar);
                case 3:
                    return zza(zzj, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဉ\u0002\u0004ဉ\u0003\u0005ဋ\u0004\u0006ဋ\u0005", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg", "zzh", "zzi"});
                case 4:
                    return zzj;
                case 5:
                    zzhv<zzap> zzhvVar = zzk;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzap.class) {
                            zzhv<zzap> zzhvVar2 = zzk;
                            r8 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzj);
                                zzk = zzcVar;
                                r8 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r8;
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
            zzap zzapVar = new zzap();
            zzj = zzapVar;
            zzgd.zza((Class<zzap>) zzap.class, zzapVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzaq extends zzgd<zzaq, zza> implements zzhn {
        private static final zzaq zzf;
        private static volatile zzhv<zzaq> zzg;
        private int zzc;
        private zzat zzd;
        private int zze;

        private zzaq() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzaq, zza> implements zzhn {
            private zza() {
                super(zzaq.zzf);
            }

            /* synthetic */ zza(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzaq>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzaq();
                case 2:
                    return new zza(zzboVar);
                case 3:
                    return zza(zzf, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဌ\u0001", new Object[]{"zzc", "zzd", "zze", zzbx.zzb()});
                case 4:
                    return zzf;
                case 5:
                    zzhv<zzaq> zzhvVar = zzg;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzaq.class) {
                            zzhv<zzaq> zzhvVar2 = zzg;
                            r2 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzf);
                                zzg = zzcVar;
                                r2 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r2;
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
            zzaq zzaqVar = new zzaq();
            zzf = zzaqVar;
            zzgd.zza((Class<zzaq>) zzaq.class, zzaqVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzar extends zzgd<zzar, zza> implements zzhn {
        private static final zzar zzi;
        private static volatile zzhv<zzar> zzj;
        private int zzc;
        private zzaf zzd;
        private zzat zze;
        private zzae zzf;
        private int zzg;
        private float zzh;

        private zzar() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzar, zza> implements zzhn {
            private zza() {
                super(zzar.zzi);
            }

            /* synthetic */ zza(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r7v12 */
        /* JADX WARN: Type inference failed for: r7v13, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzar>] */
        /* JADX WARN: Type inference failed for: r7v16 */
        /* JADX WARN: Type inference failed for: r7v17 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r7;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzar();
                case 2:
                    return new zza(zzboVar);
                case 3:
                    return zza(zzi, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဉ\u0002\u0004ဋ\u0003\u0005ခ\u0004", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg", "zzh"});
                case 4:
                    return zzi;
                case 5:
                    zzhv<zzar> zzhvVar = zzj;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzar.class) {
                            zzhv<zzar> zzhvVar2 = zzj;
                            r7 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzi);
                                zzj = zzcVar;
                                r7 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r7;
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
            zzar zzarVar = new zzar();
            zzi = zzarVar;
            zzgd.zza((Class<zzar>) zzar.class, zzarVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzas extends zzgd<zzas, zza> implements zzhn {
        private static final zzgj<Integer, zzbx> zzf = new zzdf();
        private static final zzas zzj;
        private static volatile zzhv<zzas> zzk;
        private int zzc;
        private zzat zzd;
        private long zzg;
        private long zzh;
        private zzgk zze = zzk();
        private zzgm<zzbf> zzi = zzl();

        private zzas() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzas, zza> implements zzhn {
            private zza() {
                super(zzas.zzj);
            }

            /* synthetic */ zza(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r9v12 */
        /* JADX WARN: Type inference failed for: r9v13, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzas>] */
        /* JADX WARN: Type inference failed for: r9v16 */
        /* JADX WARN: Type inference failed for: r9v17 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r9;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzas();
                case 2:
                    return new zza(zzboVar);
                case 3:
                    return zza(zzj, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0002\u0000\u0001ဉ\u0000\u0002\u001e\u0003ဃ\u0001\u0004ဃ\u0002\u0005\u001b", new Object[]{"zzc", "zzd", "zze", zzbx.zzb(), "zzg", "zzh", "zzi", zzbf.class});
                case 4:
                    return zzj;
                case 5:
                    zzhv<zzas> zzhvVar = zzk;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzas.class) {
                            zzhv<zzas> zzhvVar2 = zzk;
                            r9 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzj);
                                zzk = zzcVar;
                                r9 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r9;
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

        /* JADX WARN: Type inference failed for: r0v0, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzdf, com.google.android.gms.internal.mlkit_vision_pose_common.zzgj<java.lang.Integer, com.google.android.gms.internal.mlkit_vision_pose_common.zzbx>] */
        static {
            zzas zzasVar = new zzas();
            zzj = zzasVar;
            zzgd.zza((Class<zzas>) zzas.class, zzasVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzat extends zzgd<zzat, zza> implements zzhn {
        private static final zzat zzg;
        private static volatile zzhv<zzat> zzh;
        private int zzc;
        private int zzd;
        private float zze;
        private zzam zzf;

        private zzat() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzat, zza> implements zzhn {
            private zza() {
                super(zzat.zzg);
            }

            /* synthetic */ zza(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzat>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzat();
                case 2:
                    return new zza(zzboVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဋ\u0000\u0002ခ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzhv<zzat> zzhvVar = zzh;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzat.class) {
                            zzhv<zzat> zzhvVar2 = zzh;
                            r2 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzg);
                                zzh = zzcVar;
                                r2 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r2;
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
            zzat zzatVar = new zzat();
            zzg = zzatVar;
            zzgd.zza((Class<zzat>) zzat.class, zzatVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzau extends zzgd<zzau, zzb> implements zzhn {
        private static final zzau zzh;
        private static volatile zzhv<zzau> zzi;
        private int zzc;
        private zzaf zzd;
        private zzai zze;
        private zzc zzf;
        private zzd zzg;

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd<zza, C0059zza> implements zzhn {
            private static final zza zzf;
            private static volatile zzhv<zza> zzg;
            private int zzc;
            private float zzd;
            private String zze = "";

            private zza() {
            }

            /* JADX INFO: renamed from: com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzau$zza$zza, reason: collision with other inner class name */
            /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
            public static final class C0059zza extends zzgd.zza<zza, C0059zza> implements zzhn {
                private C0059zza() {
                    super(zza.zzf);
                }

                /* synthetic */ C0059zza(zzbo zzboVar) {
                    this();
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r1v13 */
            /* JADX WARN: Type inference failed for: r1v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzau$zza>] */
            /* JADX WARN: Type inference failed for: r1v17 */
            /* JADX WARN: Type inference failed for: r1v18 */
            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
            protected final Object zza(int i, Object obj, Object obj2) {
                ?? r1;
                zzbo zzboVar = null;
                switch (zzbo.zza[i - 1]) {
                    case 1:
                        return new zza();
                    case 2:
                        return new C0059zza(zzboVar);
                    case 3:
                        return zza(zzf, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ခ\u0000\u0002ဈ\u0001", new Object[]{"zzc", "zzd", "zze"});
                    case 4:
                        return zzf;
                    case 5:
                        zzhv<zza> zzhvVar = zzg;
                        Object obj3 = zzhvVar;
                        if (zzhvVar == null) {
                            synchronized (zza.class) {
                                zzhv<zza> zzhvVar2 = zzg;
                                r1 = zzhvVar2;
                                if (zzhvVar2 == null) {
                                    ?? zzcVar = new zzgd.zzc(zzf);
                                    zzg = zzcVar;
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
                zza zzaVar = new zza();
                zzf = zzaVar;
                zzgd.zza((Class<zza>) zza.class, zzaVar);
            }
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zzc extends zzgd<zzc, zza> implements zzhn {
            private static final zzc zze;
            private static volatile zzhv<zzc> zzf;
            private int zzc;
            private zza zzd;

            private zzc() {
            }

            /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
            public static final class zza extends zzgd.zza<zzc, zza> implements zzhn {
                private zza() {
                    super(zzc.zze);
                }

                /* synthetic */ zza(zzbo zzboVar) {
                    this();
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r1v13 */
            /* JADX WARN: Type inference failed for: r1v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzau$zzc>] */
            /* JADX WARN: Type inference failed for: r1v17 */
            /* JADX WARN: Type inference failed for: r1v18 */
            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
            protected final Object zza(int i, Object obj, Object obj2) {
                ?? r1;
                zzbo zzboVar = null;
                switch (zzbo.zza[i - 1]) {
                    case 1:
                        return new zzc();
                    case 2:
                        return new zza(zzboVar);
                    case 3:
                        return zza(zze, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001ဉ\u0000", new Object[]{"zzc", "zzd"});
                    case 4:
                        return zze;
                    case 5:
                        zzhv<zzc> zzhvVar = zzf;
                        Object obj3 = zzhvVar;
                        if (zzhvVar == null) {
                            synchronized (zzc.class) {
                                zzhv<zzc> zzhvVar2 = zzf;
                                r1 = zzhvVar2;
                                if (zzhvVar2 == null) {
                                    ?? zzcVar = new zzgd.zzc(zze);
                                    zzf = zzcVar;
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
                zzc zzcVar = new zzc();
                zze = zzcVar;
                zzgd.zza((Class<zzc>) zzc.class, zzcVar);
            }
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zzd extends zzgd<zzd, zza> implements zzhn {
            private static final zzd zzd;
            private static volatile zzhv<zzd> zze;
            private zzgm<zza> zzc = zzl();

            private zzd() {
            }

            /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
            public static final class zza extends zzgd.zza<zzd, zza> implements zzhn {
                private zza() {
                    super(zzd.zzd);
                }

                /* synthetic */ zza(zzbo zzboVar) {
                    this();
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r1v13 */
            /* JADX WARN: Type inference failed for: r1v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzau$zzd>] */
            /* JADX WARN: Type inference failed for: r1v17 */
            /* JADX WARN: Type inference failed for: r1v18 */
            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
            protected final Object zza(int i, Object obj, Object obj2) {
                ?? r1;
                zzbo zzboVar = null;
                switch (zzbo.zza[i - 1]) {
                    case 1:
                        return new zzd();
                    case 2:
                        return new zza(zzboVar);
                    case 3:
                        return zza(zzd, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"zzc", zza.class});
                    case 4:
                        return zzd;
                    case 5:
                        zzhv<zzd> zzhvVar = zze;
                        Object obj3 = zzhvVar;
                        if (zzhvVar == null) {
                            synchronized (zzd.class) {
                                zzhv<zzd> zzhvVar2 = zze;
                                r1 = zzhvVar2;
                                if (zzhvVar2 == null) {
                                    ?? zzcVar = new zzgd.zzc(zzd);
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
                zzd zzdVar = new zzd();
                zzd = zzdVar;
                zzgd.zza((Class<zzd>) zzd.class, zzdVar);
            }
        }

        private zzau() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zzb extends zzgd.zza<zzau, zzb> implements zzhn {
            private zzb() {
                super(zzau.zzh);
            }

            /* synthetic */ zzb(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r3v13 */
        /* JADX WARN: Type inference failed for: r3v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzau>] */
        /* JADX WARN: Type inference failed for: r3v17 */
        /* JADX WARN: Type inference failed for: r3v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r3;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzau();
                case 2:
                    return new zzb(zzboVar);
                case 3:
                    return zza(zzh, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဉ\u0002\u0004ဉ\u0003", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg"});
                case 4:
                    return zzh;
                case 5:
                    zzhv<zzau> zzhvVar = zzi;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzau.class) {
                            zzhv<zzau> zzhvVar2 = zzi;
                            r3 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzh);
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
            zzau zzauVar = new zzau();
            zzh = zzauVar;
            zzgd.zza((Class<zzau>) zzau.class, zzauVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzav extends zzgd<zzav, zza> implements zzhn {
        private static final zzav zzf;
        private static volatile zzhv<zzav> zzg;
        private int zzc;
        private zzaw zzd;
        private int zze;

        private zzav() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzav, zza> implements zzhn {
            private zza() {
                super(zzav.zzf);
            }

            /* synthetic */ zza(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzav>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzav();
                case 2:
                    return new zza(zzboVar);
                case 3:
                    return zza(zzf, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဌ\u0001", new Object[]{"zzc", "zzd", "zze", zzbx.zzb()});
                case 4:
                    return zzf;
                case 5:
                    zzhv<zzav> zzhvVar = zzg;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzav.class) {
                            zzhv<zzav> zzhvVar2 = zzg;
                            r2 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzf);
                                zzg = zzcVar;
                                r2 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r2;
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
            zzav zzavVar = new zzav();
            zzf = zzavVar;
            zzgd.zza((Class<zzav>) zzav.class, zzavVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzaw extends zzgd<zzaw, zzb> implements zzhn {
        private static final zzaw zzj;
        private static volatile zzhv<zzaw> zzk;
        private int zzc;
        private int zzd;
        private boolean zze;
        private boolean zzf;
        private int zzg;
        private float zzh;
        private zzam zzi;

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public enum zza implements zzgf {
            MODE_UNSPECIFIED(0),
            STREAM(1),
            SINGLE_IMAGE(2);

            private static final zzgi<zza> zzd = new zzdh();
            private final int zze;

            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgf
            public final int zza() {
                return this.zze;
            }

            public static zzgh zzb() {
                return zzdg.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zze);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zza(int i) {
                this.zze = i;
            }
        }

        private zzaw() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zzb extends zzgd.zza<zzaw, zzb> implements zzhn {
            private zzb() {
                super(zzaw.zzj);
            }

            /* synthetic */ zzb(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r9v12 */
        /* JADX WARN: Type inference failed for: r9v13, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzaw>] */
        /* JADX WARN: Type inference failed for: r9v16 */
        /* JADX WARN: Type inference failed for: r9v17 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r9;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzaw();
                case 2:
                    return new zzb(zzboVar);
                case 3:
                    return zza(zzj, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဇ\u0001\u0003ဇ\u0002\u0004ဋ\u0003\u0005ခ\u0004\u0006ဉ\u0005", new Object[]{"zzc", "zzd", zza.zzb(), "zze", "zzf", "zzg", "zzh", "zzi"});
                case 4:
                    return zzj;
                case 5:
                    zzhv<zzaw> zzhvVar = zzk;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzaw.class) {
                            zzhv<zzaw> zzhvVar2 = zzk;
                            r9 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzj);
                                zzk = zzcVar;
                                r9 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r9;
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
            zzaw zzawVar = new zzaw();
            zzj = zzawVar;
            zzgd.zza((Class<zzaw>) zzaw.class, zzawVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzax extends zzgd<zzax, zza> implements zzhn {
        private static final zzax zzh;
        private static volatile zzhv<zzax> zzi;
        private int zzc;
        private zzaf zzd;
        private zzae zze;
        private zzaw zzf;
        private zzgm<zzan> zzg = zzl();

        private zzax() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzax, zza> implements zzhn {
            private zza() {
                super(zzax.zzh);
            }

            /* synthetic */ zza(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r7v12 */
        /* JADX WARN: Type inference failed for: r7v13, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzax>] */
        /* JADX WARN: Type inference failed for: r7v16 */
        /* JADX WARN: Type inference failed for: r7v17 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r7;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzax();
                case 2:
                    return new zza(zzboVar);
                case 3:
                    return zza(zzh, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0001\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဉ\u0002\u0004\u001b", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg", zzan.class});
                case 4:
                    return zzh;
                case 5:
                    zzhv<zzax> zzhvVar = zzi;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzax.class) {
                            zzhv<zzax> zzhvVar2 = zzi;
                            r7 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzh);
                                zzi = zzcVar;
                                r7 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r7;
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
            zzax zzaxVar = new zzax();
            zzh = zzaxVar;
            zzgd.zza((Class<zzax>) zzax.class, zzaxVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzay extends zzgd<zzay, zza> implements zzhn {
        private static final zzay zzi;
        private static volatile zzhv<zzay> zzj;
        private int zzc;
        private zzaw zzd;
        private int zze;
        private long zzf;
        private long zzg;
        private zzgm<zzbf> zzh = zzl();

        private zzay() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzay, zza> implements zzhn {
            private zza() {
                super(zzay.zzi);
            }

            /* synthetic */ zza(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r9v12 */
        /* JADX WARN: Type inference failed for: r9v13, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzay>] */
        /* JADX WARN: Type inference failed for: r9v16 */
        /* JADX WARN: Type inference failed for: r9v17 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r9;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzay();
                case 2:
                    return new zza(zzboVar);
                case 3:
                    return zza(zzi, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0001\u0000\u0001ဉ\u0000\u0002ဌ\u0001\u0003ဃ\u0002\u0004ဃ\u0003\u0005\u001b", new Object[]{"zzc", "zzd", "zze", zzbx.zzb(), "zzf", "zzg", "zzh", zzbf.class});
                case 4:
                    return zzi;
                case 5:
                    zzhv<zzay> zzhvVar = zzj;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzay.class) {
                            zzhv<zzay> zzhvVar2 = zzj;
                            r9 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzi);
                                zzj = zzcVar;
                                r9 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r9;
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
            zzay zzayVar = new zzay();
            zzi = zzayVar;
            zzgd.zza((Class<zzay>) zzay.class, zzayVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzb extends zzgd<zzb, C0061zzb> implements zzhn {
        private static final zzb zzg;
        private static volatile zzhv<zzb> zzh;
        private int zzc;
        private zza zzd;
        private int zze;
        private zzab zzf;

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd<zza, C0060zza> implements zzhn {
            private static final zza zzi;
            private static volatile zzhv<zza> zzj;
            private int zzc;
            private int zzd;
            private boolean zze;
            private zzgm<zzy.zza> zzf = zzl();
            private zzgm<zzy.zza> zzg = zzl();
            private zzam zzh;

            private zza() {
            }

            /* JADX INFO: renamed from: com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzb$zza$zza, reason: collision with other inner class name */
            /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
            public static final class C0060zza extends zzgd.zza<zza, C0060zza> implements zzhn {
                private C0060zza() {
                    super(zza.zzi);
                }

                /* synthetic */ C0060zza(zzbo zzboVar) {
                    this();
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r10v12 */
            /* JADX WARN: Type inference failed for: r10v13, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzb$zza>] */
            /* JADX WARN: Type inference failed for: r10v16 */
            /* JADX WARN: Type inference failed for: r10v17 */
            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
            protected final Object zza(int i, Object obj, Object obj2) {
                ?? r10;
                zzbo zzboVar = null;
                switch (zzbo.zza[i - 1]) {
                    case 1:
                        return new zza();
                    case 2:
                        return new C0060zza(zzboVar);
                    case 3:
                        return zza(zzi, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0002\u0000\u0001ဌ\u0000\u0002ဇ\u0001\u0003\u001b\u0004\u001b\u0005ဉ\u0002", new Object[]{"zzc", "zzd", zzbx.zzb(), "zze", "zzf", zzy.zza.class, "zzg", zzy.zza.class, "zzh"});
                    case 4:
                        return zzi;
                    case 5:
                        zzhv<zza> zzhvVar = zzj;
                        Object obj3 = zzhvVar;
                        if (zzhvVar == null) {
                            synchronized (zza.class) {
                                zzhv<zza> zzhvVar2 = zzj;
                                r10 = zzhvVar2;
                                if (zzhvVar2 == null) {
                                    ?? zzcVar = new zzgd.zzc(zzi);
                                    zzj = zzcVar;
                                    r10 = zzcVar;
                                }
                                break;
                            }
                            obj3 = r10;
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
                zzi = zzaVar;
                zzgd.zza((Class<zza>) zza.class, zzaVar);
            }
        }

        private zzb() {
        }

        /* JADX INFO: renamed from: com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzb$zzb, reason: collision with other inner class name */
        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class C0061zzb extends zzgd.zza<zzb, C0061zzb> implements zzhn {
            private C0061zzb() {
                super(zzb.zzg);
            }

            /* synthetic */ C0061zzb(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzb>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzb();
                case 2:
                    return new C0061zzb(zzboVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဋ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzhv<zzb> zzhvVar = zzh;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzb.class) {
                            zzhv<zzb> zzhvVar2 = zzh;
                            r2 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzg);
                                zzh = zzcVar;
                                r2 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r2;
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
            zzg = zzbVar;
            zzgd.zza((Class<zzb>) zzb.class, zzbVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzbb extends zzgd<zzbb, zza> implements zzhn {
        private static final zzbb zzf;
        private static volatile zzhv<zzbb> zzg;
        private int zzc;
        private zzaf zzd;
        private zzae zze;

        private zzbb() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzbb, zza> implements zzhn {
            private zza() {
                super(zzbb.zzf);
            }

            /* synthetic */ zza(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v13 */
        /* JADX WARN: Type inference failed for: r1v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzbb>] */
        /* JADX WARN: Type inference failed for: r1v17 */
        /* JADX WARN: Type inference failed for: r1v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r1;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzbb();
                case 2:
                    return new zza(zzboVar);
                case 3:
                    return zza(zzf, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001", new Object[]{"zzc", "zzd", "zze"});
                case 4:
                    return zzf;
                case 5:
                    zzhv<zzbb> zzhvVar = zzg;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzbb.class) {
                            zzhv<zzbb> zzhvVar2 = zzg;
                            r1 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzf);
                                zzg = zzcVar;
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
            zzbb zzbbVar = new zzbb();
            zzf = zzbbVar;
            zzgd.zza((Class<zzbb>) zzbb.class, zzbbVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzbc extends zzgd<zzbc, zza> implements zzhn {
        private static final zzbc zzi;
        private static volatile zzhv<zzbc> zzj;
        private int zzc;
        private zzaf zzd;
        private zzgm<zzc> zze = zzl();
        private int zzf;
        private int zzg;
        private int zzh;

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public enum zzb implements zzgf {
            NO_ERROR(0),
            STATUS_SENSITIVE_TOPIC(1),
            STATUS_QUALITY_THRESHOLDED(2),
            STATUS_INTERNAL_ERROR(3),
            STATUS_NOT_SUPPORTED_LANGUAGE(101),
            STATUS_32_BIT_CPU(1001),
            STATUS_32_BIT_APP(1002);

            private static final zzgi<zzb> zzh = new zzdm();
            private final int zzi;

            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgf
            public final int zza() {
                return this.zzi;
            }

            public static zzgh zzb() {
                return zzdn.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zzi);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zzb(int i) {
                this.zzi = i;
            }
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zzc extends zzgd<zzc, zza> implements zzhn {
            private static final zzc zze;
            private static volatile zzhv<zzc> zzf;
            private int zzc;
            private float zzd;

            private zzc() {
            }

            /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
            public static final class zza extends zzgd.zza<zzc, zza> implements zzhn {
                private zza() {
                    super(zzc.zze);
                }

                /* synthetic */ zza(zzbo zzboVar) {
                    this();
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r1v13 */
            /* JADX WARN: Type inference failed for: r1v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzbc$zzc>] */
            /* JADX WARN: Type inference failed for: r1v17 */
            /* JADX WARN: Type inference failed for: r1v18 */
            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
            protected final Object zza(int i, Object obj, Object obj2) {
                ?? r1;
                zzbo zzboVar = null;
                switch (zzbo.zza[i - 1]) {
                    case 1:
                        return new zzc();
                    case 2:
                        return new zza(zzboVar);
                    case 3:
                        return zza(zze, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001ခ\u0000", new Object[]{"zzc", "zzd"});
                    case 4:
                        return zze;
                    case 5:
                        zzhv<zzc> zzhvVar = zzf;
                        Object obj3 = zzhvVar;
                        if (zzhvVar == null) {
                            synchronized (zzc.class) {
                                zzhv<zzc> zzhvVar2 = zzf;
                                r1 = zzhvVar2;
                                if (zzhvVar2 == null) {
                                    ?? zzcVar = new zzgd.zzc(zze);
                                    zzf = zzcVar;
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
                zzc zzcVar = new zzc();
                zze = zzcVar;
                zzgd.zza((Class<zzc>) zzc.class, zzcVar);
            }
        }

        private zzbc() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzbc, zza> implements zzhn {
            private zza() {
                super(zzbc.zzi);
            }

            /* synthetic */ zza(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r9v12 */
        /* JADX WARN: Type inference failed for: r9v13, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzbc>] */
        /* JADX WARN: Type inference failed for: r9v16 */
        /* JADX WARN: Type inference failed for: r9v17 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r9;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzbc();
                case 2:
                    return new zza(zzboVar);
                case 3:
                    return zza(zzi, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0001\u0000\u0001ဉ\u0000\u0002\u001b\u0003ဌ\u0001\u0004င\u0002\u0005င\u0003", new Object[]{"zzc", "zzd", "zze", zzc.class, "zzf", zzb.zzb(), "zzg", "zzh"});
                case 4:
                    return zzi;
                case 5:
                    zzhv<zzbc> zzhvVar = zzj;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzbc.class) {
                            zzhv<zzbc> zzhvVar2 = zzj;
                            r9 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzi);
                                zzj = zzcVar;
                                r9 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r9;
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
            zzbc zzbcVar = new zzbc();
            zzi = zzbcVar;
            zzgd.zza((Class<zzbc>) zzbc.class, zzbcVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzbd extends zzgd<zzbd, zza> implements zzhn {
        private static final zzbd zzf;
        private static volatile zzhv<zzbd> zzg;
        private int zzc;
        private zzaf zzd;
        private zzae zze;

        private zzbd() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzbd, zza> implements zzhn {
            private zza() {
                super(zzbd.zzf);
            }

            /* synthetic */ zza(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v13 */
        /* JADX WARN: Type inference failed for: r1v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzbd>] */
        /* JADX WARN: Type inference failed for: r1v17 */
        /* JADX WARN: Type inference failed for: r1v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r1;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzbd();
                case 2:
                    return new zza(zzboVar);
                case 3:
                    return zza(zzf, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001", new Object[]{"zzc", "zzd", "zze"});
                case 4:
                    return zzf;
                case 5:
                    zzhv<zzbd> zzhvVar = zzg;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzbd.class) {
                            zzhv<zzbd> zzhvVar2 = zzg;
                            r1 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzf);
                                zzg = zzcVar;
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
            zzbd zzbdVar = new zzbd();
            zzf = zzbdVar;
            zzgd.zza((Class<zzbd>) zzbd.class, zzbdVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzbe extends zzgd<zzbe, zzb> implements zzhn {
        private static final zzbe zzl;
        private static volatile zzhv<zzbe> zzm;
        private int zzc;
        private zzaf zzd;
        private zzbi zze;
        private int zzf;
        private int zzg;
        private int zzh;
        private int zzi;
        private int zzj;
        private int zzk;

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public enum zza implements zzgf {
            NO_ERROR(0),
            METADATA_FILE_UNAVAILABLE(1),
            METADATA_ENTRY_NOT_FOUND(2),
            METADATA_JSON_INVALID(3),
            METADATA_HASH_NOT_FOUND(4),
            DOWNLOAD_MANAGER_SERVICE_MISSING(5),
            DOWNLOAD_MANAGER_HTTP_UNKNOWN_STATUS(6),
            DOWNLOAD_MANAGER_HTTP_BAD_REQUEST(400),
            DOWNLOAD_MANAGER_HTTP_UNAUTHORIZED(401),
            DOWNLOAD_MANAGER_HTTP_FORBIDDEN(403),
            DOWNLOAD_MANAGER_HTTP_NOT_FOUND(404),
            DOWNLOAD_MANAGER_HTTP_REQUEST_TIMEOUT(HttpStatus.SC_REQUEST_TIMEOUT),
            DOWNLOAD_MANAGER_HTTP_ABORTED(HttpStatus.SC_CONFLICT),
            DOWNLOAD_MANAGER_HTTP_TOO_MANY_REQUESTS(Constants.ERROR_TOO_MANY_REQUESTS),
            DOWNLOAD_MANAGER_HTTP_CANCELLED(499),
            DOWNLOAD_MANAGER_HTTP_UNIMPLEMENTED(501),
            DOWNLOAD_MANAGER_HTTP_INTERNAL_SERVICE_ERROR(500),
            DOWNLOAD_MANAGER_HTTP_SERVICE_UNAVAILABLE(503),
            DOWNLOAD_MANAGER_HTTP_DEADLINE_EXCEEDED(504),
            DOWNLOAD_MANAGER_HTTP_NETWORK_AUTHENTICATION_REQUIRED(511),
            DOWNLOAD_MANAGER_FILE_ERROR(7),
            DOWNLOAD_MANAGER_UNHANDLED_HTTP_CODE(8),
            DOWNLOAD_MANAGER_HTTP_DATA_ERROR(9),
            DOWNLOAD_MANAGER_TOO_MANY_REDIRECTS(10),
            DOWNLOAD_MANAGER_INSUFFICIENT_SPACE(11),
            DOWNLOAD_MANAGER_DEVICE_NOT_FOUND(12),
            DOWNLOAD_MANAGER_CANNOT_RESUME(13),
            DOWNLOAD_MANAGER_FILE_ALREADY_EXISTS(14),
            DOWNLOAD_MANAGER_UNKNOWN_ERROR(15),
            POST_DOWNLOAD_FILE_NOT_FOUND(16),
            POST_DOWNLOAD_MOVE_FILE_FAILED(17),
            POST_DOWNLOAD_UNZIP_FAILED(18),
            RAPID_RESPONSE_COULD_NOT_BE_WRITTEN(19),
            DRIVER_OBJECT_DEALLOCATED(20);

            private static final zzgi<zza> zzai = new zzdp();
            private final int zzaj;

            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgf
            public final int zza() {
                return this.zzaj;
            }

            public static zzgh zzb() {
                return zzdo.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zzaj);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zza(int i) {
                this.zzaj = i;
            }
        }

        private zzbe() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zzb extends zzgd.zza<zzbe, zzb> implements zzhn {
            private zzb() {
                super(zzbe.zzl);
            }

            /* synthetic */ zzb(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r11v12 */
        /* JADX WARN: Type inference failed for: r11v13, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzbe>] */
        /* JADX WARN: Type inference failed for: r11v16 */
        /* JADX WARN: Type inference failed for: r11v17 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r11;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzbe();
                case 2:
                    return new zzb(zzboVar);
                case 3:
                    return zza(zzl, "\u0001\b\u0000\u0001\u0001\b\b\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003င\u0002\u0004င\u0003\u0005င\u0004\u0006င\u0005\u0007ဌ\u0006\bင\u0007", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg", "zzh", "zzi", "zzj", zza.zzb(), "zzk"});
                case 4:
                    return zzl;
                case 5:
                    zzhv<zzbe> zzhvVar = zzm;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzbe.class) {
                            zzhv<zzbe> zzhvVar2 = zzm;
                            r11 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzl);
                                zzm = zzcVar;
                                r11 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r11;
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
            zzbe zzbeVar = new zzbe();
            zzl = zzbeVar;
            zzgd.zza((Class<zzbe>) zzbe.class, zzbeVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzbf extends zzgd<zzbf, zza> implements zzhn {
        private static final zzbf zzf;
        private static volatile zzhv<zzbf> zzg;
        private int zzc;
        private int zzd;
        private int zze;

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public enum zzb implements zzgf {
            UNKNOWN(0),
            CANONICAL(1),
            TFLITE(2),
            TFLITE_SUPPORT(3);

            private static final zzgi<zzb> zze = new zzdq();
            private final int zzf;

            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgf
            public final int zza() {
                return this.zzf;
            }

            public static zzgh zzb() {
                return zzdr.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zzf);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zzb(int i) {
                this.zzf = i;
            }
        }

        private zzbf() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzbf, zza> implements zzhn {
            private zza() {
                super(zzbf.zzf);
            }

            /* synthetic */ zza(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzbf>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzbf();
                case 2:
                    return new zza(zzboVar);
                case 3:
                    return zza(zzf, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဌ\u0000\u0002င\u0001", new Object[]{"zzc", "zzd", zzb.zzb(), "zze"});
                case 4:
                    return zzf;
                case 5:
                    zzhv<zzbf> zzhvVar = zzg;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzbf.class) {
                            zzhv<zzbf> zzhvVar2 = zzg;
                            r2 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzf);
                                zzg = zzcVar;
                                r2 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r2;
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
            zzbf zzbfVar = new zzbf();
            zzf = zzbfVar;
            zzgd.zza((Class<zzbf>) zzbf.class, zzbfVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzbg extends zzgd<zzbg, zza> implements zzhn {
        private static final zzgj<Integer, zzb> zzf = new zzdt();
        private static final zzgj<Integer, zzb> zzh = new zzds();
        private static final zzgj<Integer, zzb> zzj = new zzdu();
        private static final zzbg zzl;
        private static volatile zzhv<zzbg> zzm;
        private int zzc;
        private long zzd;
        private zzgk zze = zzk();
        private zzgk zzg = zzk();
        private zzgk zzi = zzk();
        private int zzk;

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public enum zzb implements zzgf {
            UNKNOWN_ERROR(0),
            NO_CONNECTION(1),
            RPC_ERROR(2),
            RPC_RETURNED_INVALID_RESULT(3),
            RPC_RETURNED_MALFORMED_RESULT(4),
            RPC_EXPONENTIAL_BACKOFF_FAILED(5),
            DIRECTORY_CREATION_FAILED(10),
            FILE_WRITE_FAILED_DISK_FULL(11),
            FILE_WRITE_FAILED(12),
            FILE_READ_FAILED(13),
            FILE_READ_RETURNED_INVALID_DATA(14),
            FILE_READ_RETURNED_MALFORMED_DATA(15);

            private static final zzgi<zzb> zzm = new zzdv();
            private final int zzn;

            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgf
            public final int zza() {
                return this.zzn;
            }

            public static zzgh zzb() {
                return zzdw.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zzn);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zzb(int i) {
                this.zzn = i;
            }
        }

        private zzbg() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzbg, zza> implements zzhn {
            private zza() {
                super(zzbg.zzl);
            }

            /* synthetic */ zza(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r10v12 */
        /* JADX WARN: Type inference failed for: r10v13, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzbg>] */
        /* JADX WARN: Type inference failed for: r10v16 */
        /* JADX WARN: Type inference failed for: r10v17 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r10;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzbg();
                case 2:
                    return new zza(zzboVar);
                case 3:
                    return zza(zzl, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0003\u0000\u0001ဃ\u0000\u0002\u001e\u0003\u001e\u0004\u001e\u0005င\u0001", new Object[]{"zzc", "zzd", "zze", zzb.zzb(), "zzg", zzb.zzb(), "zzi", zzb.zzb(), "zzk"});
                case 4:
                    return zzl;
                case 5:
                    zzhv<zzbg> zzhvVar = zzm;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzbg.class) {
                            zzhv<zzbg> zzhvVar2 = zzm;
                            r10 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzl);
                                zzm = zzcVar;
                                r10 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r10;
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

        /* JADX WARN: Type inference failed for: r0v0, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzdt, com.google.android.gms.internal.mlkit_vision_pose_common.zzgj<java.lang.Integer, com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzbg$zzb>] */
        /* JADX WARN: Type inference failed for: r0v1, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzds, com.google.android.gms.internal.mlkit_vision_pose_common.zzgj<java.lang.Integer, com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzbg$zzb>] */
        /* JADX WARN: Type inference failed for: r0v2, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzdu, com.google.android.gms.internal.mlkit_vision_pose_common.zzgj<java.lang.Integer, com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzbg$zzb>] */
        static {
            zzbg zzbgVar = new zzbg();
            zzl = zzbgVar;
            zzgd.zza((Class<zzbg>) zzbg.class, zzbgVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzbi extends zzgd<zzbi, zza> implements zzhn {
        private static final zzbi zzf;
        private static volatile zzhv<zzbi> zzg;
        private int zzc;
        private String zzd = "";
        private String zze = "";

        private zzbi() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzbi, zza> implements zzhn {
            private zza() {
                super(zzbi.zzf);
            }

            /* synthetic */ zza(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v13 */
        /* JADX WARN: Type inference failed for: r1v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzbi>] */
        /* JADX WARN: Type inference failed for: r1v17 */
        /* JADX WARN: Type inference failed for: r1v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r1;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzbi();
                case 2:
                    return new zza(zzboVar);
                case 3:
                    return zza(zzf, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဈ\u0001", new Object[]{"zzc", "zzd", "zze"});
                case 4:
                    return zzf;
                case 5:
                    zzhv<zzbi> zzhvVar = zzg;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzbi.class) {
                            zzhv<zzbi> zzhvVar2 = zzg;
                            r1 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzf);
                                zzg = zzcVar;
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
            zzbi zzbiVar = new zzbi();
            zzf = zzbiVar;
            zzgd.zza((Class<zzbi>) zzbi.class, zzbiVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzc extends zzgd<zzc, zzb> implements zzhn {
        private static final zzc zzg;
        private static volatile zzhv<zzc> zzh;
        private int zzc;
        private zza zzd;
        private int zze;
        private zzab zzf;

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd<zza, C0062zza> implements zzhn {
            private static final zzgj<Integer, zzao.zza> zzj = new zzbq();
            private static final zzgj<Integer, zzao.zzb> zzl = new zzbp();
            private static final zza zzm;
            private static volatile zzhv<zza> zzn;
            private int zzc;
            private int zzd;
            private boolean zze;
            private boolean zzf;
            private zzae zzg;
            private zzdx.zza zzh;
            private zzgk zzi = zzk();
            private zzgk zzk = zzk();

            private zza() {
            }

            /* JADX INFO: renamed from: com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzc$zza$zza, reason: collision with other inner class name */
            /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
            public static final class C0062zza extends zzgd.zza<zza, C0062zza> implements zzhn {
                private C0062zza() {
                    super(zza.zzm);
                }

                /* synthetic */ C0062zza(zzbo zzboVar) {
                    this();
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r12v12 */
            /* JADX WARN: Type inference failed for: r12v13, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzc$zza>] */
            /* JADX WARN: Type inference failed for: r12v16 */
            /* JADX WARN: Type inference failed for: r12v17 */
            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
            protected final Object zza(int i, Object obj, Object obj2) {
                ?? r12;
                zzbo zzboVar = null;
                switch (zzbo.zza[i - 1]) {
                    case 1:
                        return new zza();
                    case 2:
                        return new C0062zza(zzboVar);
                    case 3:
                        return zza(zzm, "\u0001\u0007\u0000\u0001\u0001\u0007\u0007\u0000\u0002\u0000\u0001ဌ\u0000\u0002ဇ\u0001\u0003ဇ\u0002\u0004ဉ\u0003\u0005ဉ\u0004\u0006\u001e\u0007\u001e", new Object[]{"zzc", "zzd", zzbx.zzb(), "zze", "zzf", "zzg", "zzh", "zzi", zzao.zza.zzb(), "zzk", zzao.zzb.zzb()});
                    case 4:
                        return zzm;
                    case 5:
                        zzhv<zza> zzhvVar = zzn;
                        Object obj3 = zzhvVar;
                        if (zzhvVar == null) {
                            synchronized (zza.class) {
                                zzhv<zza> zzhvVar2 = zzn;
                                r12 = zzhvVar2;
                                if (zzhvVar2 == null) {
                                    ?? zzcVar = new zzgd.zzc(zzm);
                                    zzn = zzcVar;
                                    r12 = zzcVar;
                                }
                                break;
                            }
                            obj3 = r12;
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

            /* JADX WARN: Type inference failed for: r0v0, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzbq, com.google.android.gms.internal.mlkit_vision_pose_common.zzgj<java.lang.Integer, com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzao$zza>] */
            /* JADX WARN: Type inference failed for: r0v1, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzbp, com.google.android.gms.internal.mlkit_vision_pose_common.zzgj<java.lang.Integer, com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzao$zzb>] */
            static {
                zza zzaVar = new zza();
                zzm = zzaVar;
                zzgd.zza((Class<zza>) zza.class, zzaVar);
            }
        }

        private zzc() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zzb extends zzgd.zza<zzc, zzb> implements zzhn {
            private zzb() {
                super(zzc.zzg);
            }

            /* synthetic */ zzb(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzc>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzc();
                case 2:
                    return new zzb(zzboVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဋ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzhv<zzc> zzhvVar = zzh;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzc.class) {
                            zzhv<zzc> zzhvVar2 = zzh;
                            r2 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzg);
                                zzh = zzcVar;
                                r2 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r2;
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
            zzc zzcVar = new zzc();
            zzg = zzcVar;
            zzgd.zza((Class<zzc>) zzc.class, zzcVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzd extends zzgd<zzd, zzb> implements zzhn {
        private static final zzd zzg;
        private static volatile zzhv<zzd> zzh;
        private int zzc;
        private zza zzd;
        private int zze;
        private zzab zzf;

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd<zza, C0063zza> implements zzhn {
            private static final zza zzj;
            private static volatile zzhv<zza> zzk;
            private int zzc;
            private int zzd;
            private boolean zze;
            private zzae zzf;
            private zzac zzg;
            private int zzh;
            private int zzi;

            private zza() {
            }

            /* JADX INFO: renamed from: com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzd$zza$zza, reason: collision with other inner class name */
            /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
            public static final class C0063zza extends zzgd.zza<zza, C0063zza> implements zzhn {
                private C0063zza() {
                    super(zza.zzj);
                }

                /* synthetic */ C0063zza(zzbo zzboVar) {
                    this();
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r9v12 */
            /* JADX WARN: Type inference failed for: r9v13, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzd$zza>] */
            /* JADX WARN: Type inference failed for: r9v16 */
            /* JADX WARN: Type inference failed for: r9v17 */
            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
            protected final Object zza(int i, Object obj, Object obj2) {
                ?? r9;
                zzbo zzboVar = null;
                switch (zzbo.zza[i - 1]) {
                    case 1:
                        return new zza();
                    case 2:
                        return new C0063zza(zzboVar);
                    case 3:
                        return zza(zzj, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဇ\u0001\u0003ဉ\u0002\u0004ဉ\u0003\u0005ဋ\u0004\u0006ဋ\u0005", new Object[]{"zzc", "zzd", zzbx.zzb(), "zze", "zzf", "zzg", "zzh", "zzi"});
                    case 4:
                        return zzj;
                    case 5:
                        zzhv<zza> zzhvVar = zzk;
                        Object obj3 = zzhvVar;
                        if (zzhvVar == null) {
                            synchronized (zza.class) {
                                zzhv<zza> zzhvVar2 = zzk;
                                r9 = zzhvVar2;
                                if (zzhvVar2 == null) {
                                    ?? zzcVar = new zzgd.zzc(zzj);
                                    zzk = zzcVar;
                                    r9 = zzcVar;
                                }
                                break;
                            }
                            obj3 = r9;
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
                zzj = zzaVar;
                zzgd.zza((Class<zza>) zza.class, zzaVar);
            }
        }

        private zzd() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zzb extends zzgd.zza<zzd, zzb> implements zzhn {
            private zzb() {
                super(zzd.zzg);
            }

            /* synthetic */ zzb(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzd>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzd();
                case 2:
                    return new zzb(zzboVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဋ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzhv<zzd> zzhvVar = zzh;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzd.class) {
                            zzhv<zzd> zzhvVar2 = zzh;
                            r2 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzg);
                                zzh = zzcVar;
                                r2 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r2;
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
            zzd zzdVar = new zzd();
            zzg = zzdVar;
            zzgd.zza((Class<zzd>) zzd.class, zzdVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zze extends zzgd<zze, zzb> implements zzhn {
        private static final zze zzg;
        private static volatile zzhv<zze> zzh;
        private int zzc;
        private zza zzd;
        private int zze;
        private zzab zzf;

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd<zza, C0064zza> implements zzhn {
            private static final zza zzh;
            private static volatile zzhv<zza> zzi;
            private int zzc;
            private int zzd;
            private boolean zze;
            private zzae zzf;
            private zzat zzg;

            private zza() {
            }

            /* JADX INFO: renamed from: com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zze$zza$zza, reason: collision with other inner class name */
            /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
            public static final class C0064zza extends zzgd.zza<zza, C0064zza> implements zzhn {
                private C0064zza() {
                    super(zza.zzh);
                }

                /* synthetic */ C0064zza(zzbo zzboVar) {
                    this();
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r7v12 */
            /* JADX WARN: Type inference failed for: r7v13, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zze$zza>] */
            /* JADX WARN: Type inference failed for: r7v16 */
            /* JADX WARN: Type inference failed for: r7v17 */
            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
            protected final Object zza(int i, Object obj, Object obj2) {
                ?? r7;
                zzbo zzboVar = null;
                switch (zzbo.zza[i - 1]) {
                    case 1:
                        return new zza();
                    case 2:
                        return new C0064zza(zzboVar);
                    case 3:
                        return zza(zzh, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဇ\u0001\u0003ဉ\u0002\u0004ဉ\u0003", new Object[]{"zzc", "zzd", zzbx.zzb(), "zze", "zzf", "zzg"});
                    case 4:
                        return zzh;
                    case 5:
                        zzhv<zza> zzhvVar = zzi;
                        Object obj3 = zzhvVar;
                        if (zzhvVar == null) {
                            synchronized (zza.class) {
                                zzhv<zza> zzhvVar2 = zzi;
                                r7 = zzhvVar2;
                                if (zzhvVar2 == null) {
                                    ?? zzcVar = new zzgd.zzc(zzh);
                                    zzi = zzcVar;
                                    r7 = zzcVar;
                                }
                                break;
                            }
                            obj3 = r7;
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
                zzgd.zza((Class<zza>) zza.class, zzaVar);
            }
        }

        private zze() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zzb extends zzgd.zza<zze, zzb> implements zzhn {
            private zzb() {
                super(zze.zzg);
            }

            /* synthetic */ zzb(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zze>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zze();
                case 2:
                    return new zzb(zzboVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဋ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzhv<zze> zzhvVar = zzh;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zze.class) {
                            zzhv<zze> zzhvVar2 = zzh;
                            r2 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzg);
                                zzh = zzcVar;
                                r2 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r2;
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
            zze zzeVar = new zze();
            zzg = zzeVar;
            zzgd.zza((Class<zze>) zze.class, zzeVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzf extends zzgd<zzf, zzb> implements zzhn {
        private static final zzf zzg;
        private static volatile zzhv<zzf> zzh;
        private int zzc;
        private zza zzd;
        private int zze;
        private zzab zzf;

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd<zza, C0065zza> implements zzhn {
            private static final zza zzi;
            private static volatile zzhv<zza> zzj;
            private int zzc;
            private int zzd;
            private boolean zze;
            private boolean zzf;
            private zzae zzg;
            private zzaw zzh;

            private zza() {
            }

            /* JADX INFO: renamed from: com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzf$zza$zza, reason: collision with other inner class name */
            /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
            public static final class C0065zza extends zzgd.zza<zza, C0065zza> implements zzhn {
                private C0065zza() {
                    super(zza.zzi);
                }

                /* synthetic */ C0065zza(zzbo zzboVar) {
                    this();
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r8v12 */
            /* JADX WARN: Type inference failed for: r8v13, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzf$zza>] */
            /* JADX WARN: Type inference failed for: r8v16 */
            /* JADX WARN: Type inference failed for: r8v17 */
            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
            protected final Object zza(int i, Object obj, Object obj2) {
                ?? r8;
                zzbo zzboVar = null;
                switch (zzbo.zza[i - 1]) {
                    case 1:
                        return new zza();
                    case 2:
                        return new C0065zza(zzboVar);
                    case 3:
                        return zza(zzi, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဇ\u0001\u0003ဇ\u0002\u0004ဉ\u0003\u0005ဉ\u0004", new Object[]{"zzc", "zzd", zzbx.zzb(), "zze", "zzf", "zzg", "zzh"});
                    case 4:
                        return zzi;
                    case 5:
                        zzhv<zza> zzhvVar = zzj;
                        Object obj3 = zzhvVar;
                        if (zzhvVar == null) {
                            synchronized (zza.class) {
                                zzhv<zza> zzhvVar2 = zzj;
                                r8 = zzhvVar2;
                                if (zzhvVar2 == null) {
                                    ?? zzcVar = new zzgd.zzc(zzi);
                                    zzj = zzcVar;
                                    r8 = zzcVar;
                                }
                                break;
                            }
                            obj3 = r8;
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
                zzi = zzaVar;
                zzgd.zza((Class<zza>) zza.class, zzaVar);
            }
        }

        private zzf() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zzb extends zzgd.zza<zzf, zzb> implements zzhn {
            private zzb() {
                super(zzf.zzg);
            }

            /* synthetic */ zzb(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzf>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzf();
                case 2:
                    return new zzb(zzboVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဋ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzhv<zzf> zzhvVar = zzh;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzf.class) {
                            zzhv<zzf> zzhvVar2 = zzh;
                            r2 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzg);
                                zzh = zzcVar;
                                r2 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r2;
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
            zzf zzfVar = new zzf();
            zzg = zzfVar;
            zzgd.zza((Class<zzf>) zzf.class, zzfVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzg extends zzgd<zzg, zzb> implements zzhn {
        private static final zzg zzg;
        private static volatile zzhv<zzg> zzh;
        private int zzc;
        private zza zzd;
        private int zze;
        private zzab zzf;

        private zzg() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd<zza, C0066zza> implements zzhn {
            private static final zza zzh;
            private static volatile zzhv<zza> zzi;
            private int zzc;
            private int zzd;
            private boolean zze;
            private zzae zzf;
            private zzba zzg;

            private zza() {
            }

            /* JADX INFO: renamed from: com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzg$zza$zza, reason: collision with other inner class name */
            /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
            public static final class C0066zza extends zzgd.zza<zza, C0066zza> implements zzhn {
                private C0066zza() {
                    super(zza.zzh);
                }

                public final C0066zza zza(zzbx zzbxVar) {
                    if (this.zzb) {
                        zzc();
                        this.zzb = false;
                    }
                    ((zza) this.zza).zza(zzbxVar);
                    return this;
                }

                public final C0066zza zza(boolean z) {
                    if (this.zzb) {
                        zzc();
                        this.zzb = false;
                    }
                    ((zza) this.zza).zza(z);
                    return this;
                }

                public final C0066zza zza(zzae zzaeVar) {
                    if (this.zzb) {
                        zzc();
                        this.zzb = false;
                    }
                    ((zza) this.zza).zza(zzaeVar);
                    return this;
                }

                public final C0066zza zza(zzba zzbaVar) {
                    if (this.zzb) {
                        zzc();
                        this.zzb = false;
                    }
                    ((zza) this.zza).zza(zzbaVar);
                    return this;
                }

                /* synthetic */ C0066zza(zzbo zzboVar) {
                    this();
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public final void zza(zzbx zzbxVar) {
                this.zzd = zzbxVar.zza();
                this.zzc |= 1;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public final void zza(boolean z) {
                this.zzc |= 2;
                this.zze = z;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public final void zza(zzae zzaeVar) {
                zzaeVar.getClass();
                this.zzf = zzaeVar;
                this.zzc |= 4;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public final void zza(zzba zzbaVar) {
                zzbaVar.getClass();
                this.zzg = zzbaVar;
                this.zzc |= 8;
            }

            public static C0066zza zza() {
                return zzh.zzh();
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r7v12 */
            /* JADX WARN: Type inference failed for: r7v13, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzg$zza>] */
            /* JADX WARN: Type inference failed for: r7v16 */
            /* JADX WARN: Type inference failed for: r7v17 */
            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
            protected final Object zza(int i, Object obj, Object obj2) {
                ?? r7;
                zzbo zzboVar = null;
                switch (zzbo.zza[i - 1]) {
                    case 1:
                        return new zza();
                    case 2:
                        return new C0066zza(zzboVar);
                    case 3:
                        return zza(zzh, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဇ\u0001\u0003ဉ\u0002\u0004ဉ\u0003", new Object[]{"zzc", "zzd", zzbx.zzb(), "zze", "zzf", "zzg"});
                    case 4:
                        return zzh;
                    case 5:
                        zzhv<zza> zzhvVar = zzi;
                        Object obj3 = zzhvVar;
                        if (zzhvVar == null) {
                            synchronized (zza.class) {
                                zzhv<zza> zzhvVar2 = zzi;
                                r7 = zzhvVar2;
                                if (zzhvVar2 == null) {
                                    ?? zzcVar = new zzgd.zzc(zzh);
                                    zzi = zzcVar;
                                    r7 = zzcVar;
                                }
                                break;
                            }
                            obj3 = r7;
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
                zzgd.zza((Class<zza>) zza.class, zzaVar);
            }
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zzb extends zzgd.zza<zzg, zzb> implements zzhn {
            private zzb() {
                super(zzg.zzg);
            }

            public final zzb zza(zza zzaVar) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzg) this.zza).zza(zzaVar);
                return this;
            }

            public final zzb zza(int i) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzg) this.zza).zzb(i);
                return this;
            }

            public final zzb zza(zzab zzabVar) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzg) this.zza).zza(zzabVar);
                return this;
            }

            /* synthetic */ zzb(zzbo zzboVar) {
                this();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zza zzaVar) {
            zzaVar.getClass();
            this.zzd = zzaVar;
            this.zzc |= 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzb(int i) {
            this.zzc |= 2;
            this.zze = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zzab zzabVar) {
            zzabVar.getClass();
            this.zzf = zzabVar;
            this.zzc |= 4;
        }

        public static zzb zza() {
            return zzg.zzh();
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzg>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzg();
                case 2:
                    return new zzb(zzboVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဋ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzhv<zzg> zzhvVar = zzh;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzg.class) {
                            zzhv<zzg> zzhvVar2 = zzh;
                            r2 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzg);
                                zzh = zzcVar;
                                r2 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r2;
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
            zzg zzgVar = new zzg();
            zzg = zzgVar;
            zzgd.zza((Class<zzg>) zzg.class, zzgVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzh extends zzgd<zzh, zzb> implements zzhn {
        private static final zzh zzg;
        private static volatile zzhv<zzh> zzh;
        private int zzc;
        private zza zzd;
        private int zze;
        private zzab zzf;

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd<zza, C0067zza> implements zzhn {
            private static final zza zzg;
            private static volatile zzhv<zza> zzh;
            private int zzc;
            private int zzd;
            private boolean zze;
            private zzae zzf;

            private zza() {
            }

            /* JADX INFO: renamed from: com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzh$zza$zza, reason: collision with other inner class name */
            /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
            public static final class C0067zza extends zzgd.zza<zza, C0067zza> implements zzhn {
                private C0067zza() {
                    super(zza.zzg);
                }

                /* synthetic */ C0067zza(zzbo zzboVar) {
                    this();
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r3v13 */
            /* JADX WARN: Type inference failed for: r3v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzh$zza>] */
            /* JADX WARN: Type inference failed for: r3v17 */
            /* JADX WARN: Type inference failed for: r3v18 */
            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
            protected final Object zza(int i, Object obj, Object obj2) {
                ?? r3;
                zzbo zzboVar = null;
                switch (zzbo.zza[i - 1]) {
                    case 1:
                        return new zza();
                    case 2:
                        return new C0067zza(zzboVar);
                    case 3:
                        return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဇ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", zzbx.zzb(), "zze", "zzf"});
                    case 4:
                        return zzg;
                    case 5:
                        zzhv<zza> zzhvVar = zzh;
                        Object obj3 = zzhvVar;
                        if (zzhvVar == null) {
                            synchronized (zza.class) {
                                zzhv<zza> zzhvVar2 = zzh;
                                r3 = zzhvVar2;
                                if (zzhvVar2 == null) {
                                    ?? zzcVar = new zzgd.zzc(zzg);
                                    zzh = zzcVar;
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
                zzg = zzaVar;
                zzgd.zza((Class<zza>) zza.class, zzaVar);
            }
        }

        private zzh() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zzb extends zzgd.zza<zzh, zzb> implements zzhn {
            private zzb() {
                super(zzh.zzg);
            }

            /* synthetic */ zzb(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzh>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzh();
                case 2:
                    return new zzb(zzboVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဋ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzhv<zzh> zzhvVar = zzh;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzh.class) {
                            zzhv<zzh> zzhvVar2 = zzh;
                            r2 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzg);
                                zzh = zzcVar;
                                r2 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r2;
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
            zzh zzhVar = new zzh();
            zzg = zzhVar;
            zzgd.zza((Class<zzh>) zzh.class, zzhVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzi extends zzgd<zzi, zzb> implements zzhn {
        private static final zzi zzg;
        private static volatile zzhv<zzi> zzh;
        private int zzc;
        private zza zzd;
        private int zze;
        private zzab zzf;

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd<zza, C0068zza> implements zzhn {
            private static final zza zzh;
            private static volatile zzhv<zza> zzi;
            private int zzc;
            private int zzd;
            private boolean zze;
            private boolean zzf;
            private zzae zzg;

            private zza() {
            }

            /* JADX INFO: renamed from: com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzi$zza$zza, reason: collision with other inner class name */
            /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
            public static final class C0068zza extends zzgd.zza<zza, C0068zza> implements zzhn {
                private C0068zza() {
                    super(zza.zzh);
                }

                /* synthetic */ C0068zza(zzbo zzboVar) {
                    this();
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r7v12 */
            /* JADX WARN: Type inference failed for: r7v13, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzi$zza>] */
            /* JADX WARN: Type inference failed for: r7v16 */
            /* JADX WARN: Type inference failed for: r7v17 */
            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
            protected final Object zza(int i, Object obj, Object obj2) {
                ?? r7;
                zzbo zzboVar = null;
                switch (zzbo.zza[i - 1]) {
                    case 1:
                        return new zza();
                    case 2:
                        return new C0068zza(zzboVar);
                    case 3:
                        return zza(zzh, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဇ\u0001\u0003ဇ\u0002\u0004ဉ\u0003", new Object[]{"zzc", "zzd", zzbx.zzb(), "zze", "zzf", "zzg"});
                    case 4:
                        return zzh;
                    case 5:
                        zzhv<zza> zzhvVar = zzi;
                        Object obj3 = zzhvVar;
                        if (zzhvVar == null) {
                            synchronized (zza.class) {
                                zzhv<zza> zzhvVar2 = zzi;
                                r7 = zzhvVar2;
                                if (zzhvVar2 == null) {
                                    ?? zzcVar = new zzgd.zzc(zzh);
                                    zzi = zzcVar;
                                    r7 = zzcVar;
                                }
                                break;
                            }
                            obj3 = r7;
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
                zzgd.zza((Class<zza>) zza.class, zzaVar);
            }
        }

        private zzi() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zzb extends zzgd.zza<zzi, zzb> implements zzhn {
            private zzb() {
                super(zzi.zzg);
            }

            /* synthetic */ zzb(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzi>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzi();
                case 2:
                    return new zzb(zzboVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဋ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzhv<zzi> zzhvVar = zzh;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzi.class) {
                            zzhv<zzi> zzhvVar2 = zzh;
                            r2 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzg);
                                zzh = zzcVar;
                                r2 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r2;
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
            zzi zziVar = new zzi();
            zzg = zziVar;
            zzgd.zza((Class<zzi>) zzi.class, zziVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzj extends zzgd<zzj, zza> implements zzhn {
        private static final zzj zze;
        private static volatile zzhv<zzj> zzf;
        private int zzc;
        private int zzd;

        private zzj() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzj, zza> implements zzhn {
            private zza() {
                super(zzj.zze);
            }

            /* synthetic */ zza(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v13 */
        /* JADX WARN: Type inference failed for: r1v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzj>] */
        /* JADX WARN: Type inference failed for: r1v17 */
        /* JADX WARN: Type inference failed for: r1v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r1;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzj();
                case 2:
                    return new zza(zzboVar);
                case 3:
                    return zza(zze, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001ဌ\u0000", new Object[]{"zzc", "zzd", zzbx.zzb()});
                case 4:
                    return zze;
                case 5:
                    zzhv<zzj> zzhvVar = zzf;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzj.class) {
                            zzhv<zzj> zzhvVar2 = zzf;
                            r1 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zze);
                                zzf = zzcVar;
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
            zzj zzjVar = new zzj();
            zze = zzjVar;
            zzgd.zza((Class<zzj>) zzj.class, zzjVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzk extends zzgd<zzk, zza> implements zzhn {
        private static final zzk zzi;
        private static volatile zzhv<zzk> zzj;
        private int zzc;
        private zzaf zzd;
        private zzam zze;
        private long zzf;
        private float zzg;
        private zzae zzh;

        private zzk() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzk, zza> implements zzhn {
            private zza() {
                super(zzk.zzi);
            }

            /* synthetic */ zza(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r7v12 */
        /* JADX WARN: Type inference failed for: r7v13, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzk>] */
        /* JADX WARN: Type inference failed for: r7v16 */
        /* JADX WARN: Type inference failed for: r7v17 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r7;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzk();
                case 2:
                    return new zza(zzboVar);
                case 3:
                    return zza(zzi, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဃ\u0002\u0004ခ\u0003\u0005ဉ\u0004", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg", "zzh"});
                case 4:
                    return zzi;
                case 5:
                    zzhv<zzk> zzhvVar = zzj;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzk.class) {
                            zzhv<zzk> zzhvVar2 = zzj;
                            r7 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzi);
                                zzj = zzcVar;
                                r7 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r7;
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
            zzk zzkVar = new zzk();
            zzi = zzkVar;
            zzgd.zza((Class<zzk>) zzk.class, zzkVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzl extends zzgd<zzl, zza> implements zzhn {
        private static final zzgj<Integer, zzbx> zzg = new zzbr();
        private static final zzl zzi;
        private static volatile zzhv<zzl> zzj;
        private int zzc;
        private zzam zzd;
        private zzam zze;
        private zzgk zzf = zzk();
        private long zzh;

        private zzl() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzl, zza> implements zzhn {
            private zza() {
                super(zzl.zzi);
            }

            /* synthetic */ zza(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r7v12 */
        /* JADX WARN: Type inference failed for: r7v13, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzl>] */
        /* JADX WARN: Type inference failed for: r7v16 */
        /* JADX WARN: Type inference failed for: r7v17 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r7;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzl();
                case 2:
                    return new zza(zzboVar);
                case 3:
                    return zza(zzi, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0001\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003\u001e\u0004ဃ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf", zzbx.zzb(), "zzh"});
                case 4:
                    return zzi;
                case 5:
                    zzhv<zzl> zzhvVar = zzj;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzl.class) {
                            zzhv<zzl> zzhvVar2 = zzj;
                            r7 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzi);
                                zzj = zzcVar;
                                r7 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r7;
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

        /* JADX WARN: Type inference failed for: r0v0, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzbr, com.google.android.gms.internal.mlkit_vision_pose_common.zzgj<java.lang.Integer, com.google.android.gms.internal.mlkit_vision_pose_common.zzbx>] */
        static {
            zzl zzlVar = new zzl();
            zzi = zzlVar;
            zzgd.zza((Class<zzl>) zzl.class, zzlVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzm extends zzgd<zzm, zza> implements zzhn {
        private static final zzm zzg;
        private static volatile zzhv<zzm> zzh;
        private int zzc;
        private zzaf zzd;
        private zzn zze;
        private zzae zzf;

        private zzm() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzm, zza> implements zzhn {
            private zza() {
                super(zzm.zzg);
            }

            /* synthetic */ zza(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzm>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzm();
                case 2:
                    return new zza(zzboVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzhv<zzm> zzhvVar = zzh;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzm.class) {
                            zzhv<zzm> zzhvVar2 = zzh;
                            r2 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzg);
                                zzh = zzcVar;
                                r2 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r2;
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
            zzm zzmVar = new zzm();
            zzg = zzmVar;
            zzgd.zza((Class<zzm>) zzm.class, zzmVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzn extends zzgd<zzn, zza> implements zzhn {
        private static final zzn zzf;
        private static volatile zzhv<zzn> zzg;
        private int zzc;
        private int zzd;
        private int zze;

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public enum zzb implements zzgf {
            UNKNOWN_MODEL_TYPE(0),
            STABLE_MODEL(1),
            LATEST_MODEL(2);

            private static final zzgi<zzb> zzd = new zzbs();
            private final int zze;

            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgf
            public final int zza() {
                return this.zze;
            }

            public static zzgh zzb() {
                return zzbt.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zze);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zzb(int i) {
                this.zze = i;
            }
        }

        private zzn() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzn, zza> implements zzhn {
            private zza() {
                super(zzn.zzf);
            }

            /* synthetic */ zza(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzn>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzn();
                case 2:
                    return new zza(zzboVar);
                case 3:
                    return zza(zzf, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001င\u0000\u0002ဌ\u0001", new Object[]{"zzc", "zzd", "zze", zzb.zzb()});
                case 4:
                    return zzf;
                case 5:
                    zzhv<zzn> zzhvVar = zzg;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzn.class) {
                            zzhv<zzn> zzhvVar2 = zzg;
                            r2 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzf);
                                zzg = zzcVar;
                                r2 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r2;
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
            zzn zznVar = new zzn();
            zzf = zznVar;
            zzgd.zza((Class<zzn>) zzn.class, zznVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzo extends zzgd<zzo, zza> implements zzhn {
        private static final zzo zzg;
        private static volatile zzhv<zzo> zzh;
        private int zzc;
        private zzaf zzd;
        private zzn zze;
        private zzae zzf;

        private zzo() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzo, zza> implements zzhn {
            private zza() {
                super(zzo.zzg);
            }

            /* synthetic */ zza(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzo>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzo();
                case 2:
                    return new zza(zzboVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzhv<zzo> zzhvVar = zzh;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzo.class) {
                            zzhv<zzo> zzhvVar2 = zzh;
                            r2 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzg);
                                zzh = zzcVar;
                                r2 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r2;
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
            zzo zzoVar = new zzo();
            zzg = zzoVar;
            zzgd.zza((Class<zzo>) zzo.class, zzoVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzp extends zzgd<zzp, zza> implements zzhn {
        private static final zzp zzg;
        private static volatile zzhv<zzp> zzh;
        private int zzc;
        private zzaf zzd;
        private zzn zze;
        private zzae zzf;

        private zzp() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzp, zza> implements zzhn {
            private zza() {
                super(zzp.zzg);
            }

            /* synthetic */ zza(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzp>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzp();
                case 2:
                    return new zza(zzboVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzhv<zzp> zzhvVar = zzh;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzp.class) {
                            zzhv<zzp> zzhvVar2 = zzh;
                            r2 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzg);
                                zzh = zzcVar;
                                r2 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r2;
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
            zzp zzpVar = new zzp();
            zzg = zzpVar;
            zzgd.zza((Class<zzp>) zzp.class, zzpVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzq extends zzgd<zzq, zza> implements zzhn {
        private static final zzq zzg;
        private static volatile zzhv<zzq> zzh;
        private int zzc;
        private zzaf zzd;
        private zzn zze;
        private zzae zzf;

        private zzq() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzq, zza> implements zzhn {
            private zza() {
                super(zzq.zzg);
            }

            /* synthetic */ zza(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzq>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzq();
                case 2:
                    return new zza(zzboVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzhv<zzq> zzhvVar = zzh;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzq.class) {
                            zzhv<zzq> zzhvVar2 = zzh;
                            r2 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzg);
                                zzh = zzcVar;
                                r2 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r2;
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
            zzq zzqVar = new zzq();
            zzg = zzqVar;
            zzgd.zza((Class<zzq>) zzq.class, zzqVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzr extends zzgd<zzr, zza> implements zzhn {
        private static final zzr zzg;
        private static volatile zzhv<zzr> zzh;
        private int zzc;
        private zzaf zzd;
        private zzn zze;
        private zzae zzf;

        private zzr() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzr, zza> implements zzhn {
            private zza() {
                super(zzr.zzg);
            }

            /* synthetic */ zza(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzr>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzr();
                case 2:
                    return new zza(zzboVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzhv<zzr> zzhvVar = zzh;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzr.class) {
                            zzhv<zzr> zzhvVar2 = zzh;
                            r2 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzg);
                                zzh = zzcVar;
                                r2 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r2;
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
            zzr zzrVar = new zzr();
            zzg = zzrVar;
            zzgd.zza((Class<zzr>) zzr.class, zzrVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzs extends zzgd<zzs, zza> implements zzhn {
        private static final zzs zzg;
        private static volatile zzhv<zzs> zzh;
        private int zzc;
        private zzaf zzd;
        private zzn zze;
        private zzae zzf;

        private zzs() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzs, zza> implements zzhn {
            private zza() {
                super(zzs.zzg);
            }

            /* synthetic */ zza(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzs>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzs();
                case 2:
                    return new zza(zzboVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzhv<zzs> zzhvVar = zzh;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzs.class) {
                            zzhv<zzs> zzhvVar2 = zzh;
                            r2 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzg);
                                zzh = zzcVar;
                                r2 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r2;
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
            zzs zzsVar = new zzs();
            zzg = zzsVar;
            zzgd.zza((Class<zzs>) zzs.class, zzsVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzt extends zzgd<zzt, zza> implements zzhn {
        private static final zzt zzg;
        private static volatile zzhv<zzt> zzh;
        private int zzc;
        private zzaf zzd;
        private zzn zze;
        private zzae zzf;

        private zzt() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzt, zza> implements zzhn {
            private zza() {
                super(zzt.zzg);
            }

            /* synthetic */ zza(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzt>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzt();
                case 2:
                    return new zza(zzboVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzhv<zzt> zzhvVar = zzh;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzt.class) {
                            zzhv<zzt> zzhvVar2 = zzh;
                            r2 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzg);
                                zzh = zzcVar;
                                r2 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r2;
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
            zzt zztVar = new zzt();
            zzg = zztVar;
            zzgd.zza((Class<zzt>) zzt.class, zztVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzu extends zzgd<zzu, zza> implements zzhn {
        private static final zzu zzg;
        private static volatile zzhv<zzu> zzh;
        private int zzc;
        private zzaf zzd;
        private zzn zze;
        private zzae zzf;

        private zzu() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzu, zza> implements zzhn {
            private zza() {
                super(zzu.zzg);
            }

            /* synthetic */ zza(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzu>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzu();
                case 2:
                    return new zza(zzboVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzhv<zzu> zzhvVar = zzh;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzu.class) {
                            zzhv<zzu> zzhvVar2 = zzh;
                            r2 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzg);
                                zzh = zzcVar;
                                r2 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r2;
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
            zzu zzuVar = new zzu();
            zzg = zzuVar;
            zzgd.zza((Class<zzu>) zzu.class, zzuVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzv extends zzgd<zzv, zza> implements zzhn {
        private static final zzv zzg;
        private static volatile zzhv<zzv> zzh;
        private int zzc;
        private zzaf zzd;
        private zzn zze;
        private zzae zzf;

        private zzv() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzv, zza> implements zzhn {
            private zza() {
                super(zzv.zzg);
            }

            /* synthetic */ zza(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzv>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzv();
                case 2:
                    return new zza(zzboVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzhv<zzv> zzhvVar = zzh;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzv.class) {
                            zzhv<zzv> zzhvVar2 = zzh;
                            r2 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzg);
                                zzh = zzcVar;
                                r2 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r2;
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
            zzv zzvVar = new zzv();
            zzg = zzvVar;
            zzgd.zza((Class<zzv>) zzv.class, zzvVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzw extends zzgd<zzw, zza> implements zzhn {
        private static final zzw zzg;
        private static volatile zzhv<zzw> zzh;
        private int zzc;
        private zzaf zzd;
        private zzn zze;
        private zzae zzf;

        private zzw() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzw, zza> implements zzhn {
            private zza() {
                super(zzw.zzg);
            }

            /* synthetic */ zza(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzw>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzw();
                case 2:
                    return new zza(zzboVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzhv<zzw> zzhvVar = zzh;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzw.class) {
                            zzhv<zzw> zzhvVar2 = zzh;
                            r2 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzg);
                                zzh = zzcVar;
                                r2 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r2;
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
            zzw zzwVar = new zzw();
            zzg = zzwVar;
            zzgd.zza((Class<zzw>) zzw.class, zzwVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzx extends zzgd<zzx, zza> implements zzhn {
        private static final zzx zzf;
        private static volatile zzhv<zzx> zzg;
        private int zzc;
        private zzam zzd;
        private int zze;

        private zzx() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzx, zza> implements zzhn {
            private zza() {
                super(zzx.zzf);
            }

            /* synthetic */ zza(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzx>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzx();
                case 2:
                    return new zza(zzboVar);
                case 3:
                    return zza(zzf, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဌ\u0001", new Object[]{"zzc", "zzd", "zze", zzbx.zzb()});
                case 4:
                    return zzf;
                case 5:
                    zzhv<zzx> zzhvVar = zzg;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzx.class) {
                            zzhv<zzx> zzhvVar2 = zzg;
                            r2 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzf);
                                zzg = zzcVar;
                                r2 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r2;
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
            zzx zzxVar = new zzx();
            zzf = zzxVar;
            zzgd.zza((Class<zzx>) zzx.class, zzxVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzy extends zzgd<zzy, zzb> implements zzhn {
        private static final zzy zzi;
        private static volatile zzhv<zzy> zzj;
        private int zzc;
        private zzaf zzd;
        private zzam zze;
        private zzgm<zza> zzf = zzl();
        private zzgm<zza> zzg = zzl();
        private long zzh;

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd<zza, zzb> implements zzhn {
            private static final zza zzf;
            private static volatile zzhv<zza> zzg;
            private int zzc;
            private int zzd;
            private zzgk zze = zzk();

            /* JADX INFO: renamed from: com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzy$zza$zza, reason: collision with other inner class name */
            /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
            public enum EnumC0069zza implements zzgf {
                UNKNOWN_DATA_TYPE(0),
                TYPE_FLOAT32(1),
                TYPE_INT32(2),
                TYPE_BYTE(3),
                TYPE_LONG(4);

                private static final zzgi<EnumC0069zza> zzf = new zzbv();
                private final int zzg;

                @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgf
                public final int zza() {
                    return this.zzg;
                }

                public static zzgh zzb() {
                    return zzbu.zza;
                }

                @Override // java.lang.Enum
                public final String toString() {
                    StringBuilder sb = new StringBuilder("<");
                    sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                    sb.append(" number=").append(this.zzg);
                    return sb.append(" name=").append(name()).append('>').toString();
                }

                EnumC0069zza(int i) {
                    this.zzg = i;
                }
            }

            private zza() {
            }

            /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
            public static final class zzb extends zzgd.zza<zza, zzb> implements zzhn {
                private zzb() {
                    super(zza.zzf);
                }

                /* synthetic */ zzb(zzbo zzboVar) {
                    this();
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r2v13 */
            /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzy$zza>] */
            /* JADX WARN: Type inference failed for: r2v17 */
            /* JADX WARN: Type inference failed for: r2v18 */
            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
            protected final Object zza(int i, Object obj, Object obj2) {
                ?? r2;
                zzbo zzboVar = null;
                switch (zzbo.zza[i - 1]) {
                    case 1:
                        return new zza();
                    case 2:
                        return new zzb(zzboVar);
                    case 3:
                        return zza(zzf, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0001\u0000\u0001ဌ\u0000\u0002\u0016", new Object[]{"zzc", "zzd", EnumC0069zza.zzb(), "zze"});
                    case 4:
                        return zzf;
                    case 5:
                        zzhv<zza> zzhvVar = zzg;
                        Object obj3 = zzhvVar;
                        if (zzhvVar == null) {
                            synchronized (zza.class) {
                                zzhv<zza> zzhvVar2 = zzg;
                                r2 = zzhvVar2;
                                if (zzhvVar2 == null) {
                                    ?? zzcVar = new zzgd.zzc(zzf);
                                    zzg = zzcVar;
                                    r2 = zzcVar;
                                }
                                break;
                            }
                            obj3 = r2;
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
                zzf = zzaVar;
                zzgd.zza((Class<zza>) zza.class, zzaVar);
            }
        }

        private zzy() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zzb extends zzgd.zza<zzy, zzb> implements zzhn {
            private zzb() {
                super(zzy.zzi);
            }

            /* synthetic */ zzb(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r9v12 */
        /* JADX WARN: Type inference failed for: r9v13, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzy>] */
        /* JADX WARN: Type inference failed for: r9v16 */
        /* JADX WARN: Type inference failed for: r9v17 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r9;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzy();
                case 2:
                    return new zzb(zzboVar);
                case 3:
                    return zza(zzi, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0002\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003\u001b\u0004\u001b\u0005ဃ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf", zza.class, "zzg", zza.class, "zzh"});
                case 4:
                    return zzi;
                case 5:
                    zzhv<zzy> zzhvVar = zzj;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzy.class) {
                            zzhv<zzy> zzhvVar2 = zzj;
                            r9 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzi);
                                zzj = zzcVar;
                                r9 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r9;
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
            zzy zzyVar = new zzy();
            zzi = zzyVar;
            zzgd.zza((Class<zzy>) zzy.class, zzyVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzz extends zzgd<zzz, zza> implements zzhn {
        private static final zzgj<Integer, zzbx> zzg = new zzbw();
        private static final zzz zzj;
        private static volatile zzhv<zzz> zzk;
        private int zzc;
        private zzam zzd;
        private zzam zze;
        private zzgk zzf = zzk();
        private long zzh;
        private boolean zzi;

        private zzz() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzz, zza> implements zzhn {
            private zza() {
                super(zzz.zzj);
            }

            /* synthetic */ zza(zzbo zzboVar) {
                this();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r8v12 */
        /* JADX WARN: Type inference failed for: r8v13, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzz>] */
        /* JADX WARN: Type inference failed for: r8v16 */
        /* JADX WARN: Type inference failed for: r8v17 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r8;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzz();
                case 2:
                    return new zza(zzboVar);
                case 3:
                    return zza(zzj, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0001\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003\u001e\u0004ဃ\u0002\u0005ဇ\u0003", new Object[]{"zzc", "zzd", "zze", "zzf", zzbx.zzb(), "zzh", "zzi"});
                case 4:
                    return zzj;
                case 5:
                    zzhv<zzz> zzhvVar = zzk;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzz.class) {
                            zzhv<zzz> zzhvVar2 = zzk;
                            r8 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzj);
                                zzk = zzcVar;
                                r8 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r8;
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

        /* JADX WARN: Type inference failed for: r0v0, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzbw, com.google.android.gms.internal.mlkit_vision_pose_common.zzgj<java.lang.Integer, com.google.android.gms.internal.mlkit_vision_pose_common.zzbx>] */
        static {
            zzz zzzVar = new zzz();
            zzj = zzzVar;
            zzgd.zza((Class<zzz>) zzz.class, zzzVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzae extends zzgd<zzae, zzb> implements zzhn {
        private static final zzae zzg;
        private static volatile zzhv<zzae> zzh;
        private int zzc;
        private int zzd;
        private int zze;
        private int zzf;

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public enum zza implements zzgf {
            UNKNOWN_FORMAT(0),
            NV16(1),
            NV21(2),
            YV12(3),
            YUV_420_888(7),
            JPEG(8),
            BITMAP(4),
            CM_SAMPLE_BUFFER_REF(5),
            UI_IMAGE(6);

            private static final zzgi<zza> zzj = new zzcm();
            private final int zzk;

            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgf
            public final int zza() {
                return this.zzk;
            }

            public static zzgh zzb() {
                return zzcl.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zzk);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zza(int i) {
                this.zzk = i;
            }
        }

        private zzae() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zzb extends zzgd.zza<zzae, zzb> implements zzhn {
            private zzb() {
                super(zzae.zzg);
            }

            public final zzb zza(zza zzaVar) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzae) this.zza).zza(zzaVar);
                return this;
            }

            public final zzb zza(int i) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzae) this.zza).zzb(i);
                return this;
            }

            /* synthetic */ zzb(zzbo zzboVar) {
                this();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zza zzaVar) {
            this.zzd = zzaVar.zza();
            this.zzc |= 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzb(int i) {
            this.zzc |= 2;
            this.zze = i;
        }

        public static zzb zza() {
            return zzg.zzh();
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r3v13 */
        /* JADX WARN: Type inference failed for: r3v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzae>] */
        /* JADX WARN: Type inference failed for: r3v17 */
        /* JADX WARN: Type inference failed for: r3v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r3;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzae();
                case 2:
                    return new zzb(zzboVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဋ\u0001\u0003ဋ\u0002", new Object[]{"zzc", "zzd", zza.zzb(), "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzhv<zzae> zzhvVar = zzh;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzae.class) {
                            zzhv<zzae> zzhvVar2 = zzh;
                            r3 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzg);
                                zzh = zzcVar;
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
            zzae zzaeVar = new zzae();
            zzg = zzaeVar;
            zzgd.zza((Class<zzae>) zzae.class, zzaeVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzaf extends zzgd<zzaf, zza> implements zzhn {
        private static final zzaf zzl;
        private static volatile zzhv<zzaf> zzm;
        private int zzc;
        private long zzd;
        private int zze;
        private boolean zzf;
        private boolean zzg;
        private boolean zzh;
        private boolean zzi;
        private int zzj;
        private zzgm<zzbf> zzk = zzl();

        private zzaf() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzaf, zza> implements zzhn {
            private zza() {
                super(zzaf.zzl);
            }

            public final zza zza(long j) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzaf) this.zza).zza(j);
                return this;
            }

            public final zza zza(zzbx zzbxVar) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzaf) this.zza).zza(zzbxVar);
                return this;
            }

            public final zza zza(boolean z) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzaf) this.zza).zza(z);
                return this;
            }

            public final zza zzb(boolean z) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzaf) this.zza).zzb(true);
                return this;
            }

            public final zza zzc(boolean z) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzaf) this.zza).zzc(true);
                return this;
            }

            /* synthetic */ zza(zzbo zzboVar) {
                this();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(long j) {
            this.zzc |= 1;
            this.zzd = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zzbx zzbxVar) {
            this.zze = zzbxVar.zza();
            this.zzc |= 2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(boolean z) {
            this.zzc |= 4;
            this.zzf = z;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzb(boolean z) {
            this.zzc |= 8;
            this.zzg = true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzc(boolean z) {
            this.zzc |= 16;
            this.zzh = true;
        }

        public static zza zza() {
            return zzl.zzh();
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r12v12 */
        /* JADX WARN: Type inference failed for: r12v13, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzaf>] */
        /* JADX WARN: Type inference failed for: r12v16 */
        /* JADX WARN: Type inference failed for: r12v17 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r12;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzaf();
                case 2:
                    return new zza(zzboVar);
                case 3:
                    return zza(zzl, "\u0001\b\u0000\u0001\u0001\b\b\u0000\u0001\u0000\u0001ဃ\u0000\u0002ဌ\u0001\u0003ဇ\u0002\u0004ဇ\u0003\u0005ဇ\u0004\u0006ဇ\u0005\u0007ဋ\u0006\b\u001b", new Object[]{"zzc", "zzd", "zze", zzbx.zzb(), "zzf", "zzg", "zzh", "zzi", "zzj", "zzk", zzbf.class});
                case 4:
                    return zzl;
                case 5:
                    zzhv<zzaf> zzhvVar = zzm;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzaf.class) {
                            zzhv<zzaf> zzhvVar2 = zzm;
                            r12 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzl);
                                zzm = zzcVar;
                                r12 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r12;
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
            zzaf zzafVar = new zzaf();
            zzl = zzafVar;
            zzgd.zza((Class<zzaf>) zzaf.class, zzafVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzaz extends zzgd<zzaz, zza> implements zzhn {
        private static final zzaz zzg;
        private static volatile zzhv<zzaz> zzh;
        private int zzc;
        private zzaf zzd;
        private zzae zze;
        private zzba zzf;

        private zzaz() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzaz, zza> implements zzhn {
            private zza() {
                super(zzaz.zzg);
            }

            public final zza zza(zzaf.zza zzaVar) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzaz) this.zza).zza((zzaf) ((zzgd) zzaVar.zzh()));
                return this;
            }

            public final zza zza(zzae zzaeVar) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzaz) this.zza).zza(zzaeVar);
                return this;
            }

            public final zza zza(zzba zzbaVar) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzaz) this.zza).zza(zzbaVar);
                return this;
            }

            /* synthetic */ zza(zzbo zzboVar) {
                this();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zzaf zzafVar) {
            zzafVar.getClass();
            this.zzd = zzafVar;
            this.zzc |= 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zzae zzaeVar) {
            zzaeVar.getClass();
            this.zze = zzaeVar;
            this.zzc |= 2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zzba zzbaVar) {
            zzbaVar.getClass();
            this.zzf = zzbaVar;
            this.zzc |= 4;
        }

        public static zza zza() {
            return zzg.zzh();
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v13 */
        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzaz>] */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r2;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzaz();
                case 2:
                    return new zza(zzboVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဉ\u0002", new Object[]{"zzc", "zzd", "zze", "zzf"});
                case 4:
                    return zzg;
                case 5:
                    zzhv<zzaz> zzhvVar = zzh;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzaz.class) {
                            zzhv<zzaz> zzhvVar2 = zzh;
                            r2 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzg);
                                zzh = zzcVar;
                                r2 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r2;
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
            zzaz zzazVar = new zzaz();
            zzg = zzazVar;
            zzgd.zza((Class<zzaz>) zzaz.class, zzazVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzba extends zzgd<zzba, zza> implements zzhn {
        private static final zzba zzg;
        private static volatile zzhv<zzba> zzh;
        private int zzc;
        private int zzd;
        private int zze;
        private int zzf;

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public enum zzb implements zzgf {
            INVALID_MODE(0),
            STREAM(1),
            SINGLE_IMAGE(2);

            private static final zzgi<zzb> zzd = new zzdi();
            private final int zze;

            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgf
            public final int zza() {
                return this.zze;
            }

            public static zzgh zzb() {
                return zzdj.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zze);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zzb(int i) {
                this.zze = i;
            }
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public enum zzc implements zzgf {
            UNKNOWN_PERFORMANCE(0),
            FAST(1),
            ACCURATE(2);

            private static final zzgi<zzc> zzd = new zzdl();
            private final int zze;

            @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgf
            public final int zza() {
                return this.zze;
            }

            public static zzgh zzb() {
                return zzdk.zza;
            }

            @Override // java.lang.Enum
            public final String toString() {
                StringBuilder sb = new StringBuilder("<");
                sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
                sb.append(" number=").append(this.zze);
                return sb.append(" name=").append(name()).append('>').toString();
            }

            zzc(int i) {
                this.zze = i;
            }
        }

        private zzba() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzba, zza> implements zzhn {
            private zza() {
                super(zzba.zzg);
            }

            public final zza zza(zzb zzbVar) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzba) this.zza).zza(zzbVar);
                return this;
            }

            public final zza zza(zzc zzcVar) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzba) this.zza).zza(zzcVar);
                return this;
            }

            public final zza zzb(zzc zzcVar) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzba) this.zza).zzb(zzcVar);
                return this;
            }

            /* synthetic */ zza(zzbo zzboVar) {
                this();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zzb zzbVar) {
            this.zzd = zzbVar.zza();
            this.zzc |= 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zzc zzcVar) {
            this.zze = zzcVar.zza();
            this.zzc |= 2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzb(zzc zzcVar) {
            this.zzf = zzcVar.zza();
            this.zzc |= 4;
        }

        public static zza zza() {
            return zzg.zzh();
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r8v12 */
        /* JADX WARN: Type inference failed for: r8v13, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzba>] */
        /* JADX WARN: Type inference failed for: r8v16 */
        /* JADX WARN: Type inference failed for: r8v17 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r8;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzba();
                case 2:
                    return new zza(zzboVar);
                case 3:
                    return zza(zzg, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဌ\u0001\u0003ဌ\u0002", new Object[]{"zzc", "zzd", zzb.zzb(), "zze", zzc.zzb(), "zzf", zzc.zzb()});
                case 4:
                    return zzg;
                case 5:
                    zzhv<zzba> zzhvVar = zzh;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzba.class) {
                            zzhv<zzba> zzhvVar2 = zzh;
                            r8 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzg);
                                zzh = zzcVar;
                                r8 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r8;
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
            zzba zzbaVar = new zzba();
            zzg = zzbaVar;
            zzgd.zza((Class<zzba>) zzba.class, zzbaVar);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static final class zzbh extends zzgd<zzbh, zza> implements zzhn {
        private static final zzbh zzo;
        private static volatile zzhv<zzbh> zzp;
        private int zzc;
        private String zzd = "";
        private String zze = "";
        private String zzf = "";
        private String zzg = "";
        private String zzh = "";
        private String zzi = "";
        private String zzj = "";
        private zzgm<String> zzk = zzgd.zzl();
        private String zzl = "";
        private boolean zzm;
        private boolean zzn;

        private zzbh() {
        }

        /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
        public static final class zza extends zzgd.zza<zzbh, zza> implements zzhn {
            private zza() {
                super(zzbh.zzo);
            }

            public final zza zza(String str) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzbh) this.zza).zza(str);
                return this;
            }

            public final zza zzb(String str) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzbh) this.zza).zzb(str);
                return this;
            }

            public final zza zzc(String str) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzbh) this.zza).zzc(str);
                return this;
            }

            public final zza zzd(String str) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzbh) this.zza).zzd(str);
                return this;
            }

            public final zza zza(Iterable<String> iterable) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzbh) this.zza).zza(iterable);
                return this;
            }

            public final zza zze(String str) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzbh) this.zza).zze(str);
                return this;
            }

            public final zza zza(boolean z) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzbh) this.zza).zza(true);
                return this;
            }

            public final zza zzb(boolean z) {
                if (this.zzb) {
                    zzc();
                    this.zzb = false;
                }
                ((zzbh) this.zza).zzb(true);
                return this;
            }

            /* synthetic */ zza(zzbo zzboVar) {
                this();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(String str) {
            str.getClass();
            this.zzc |= 1;
            this.zzd = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzb(String str) {
            str.getClass();
            this.zzc |= 2;
            this.zze = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzc(String str) {
            str.getClass();
            this.zzc |= 8;
            this.zzg = str;
        }

        public final String zza() {
            return this.zzh;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzd(String str) {
            str.getClass();
            this.zzc |= 16;
            this.zzh = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(Iterable<String> iterable) {
            zzgm<String> zzgmVar = this.zzk;
            if (!zzgmVar.zza()) {
                int size = zzgmVar.size();
                this.zzk = zzgmVar.zzb(size == 0 ? 10 : size << 1);
            }
            zzeu.zza(iterable, this.zzk);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zze(String str) {
            str.getClass();
            this.zzc |= 128;
            this.zzl = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(boolean z) {
            this.zzc |= 256;
            this.zzm = true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzb(boolean z) {
            this.zzc |= 512;
            this.zzn = true;
        }

        public static zza zzb() {
            return zzo.zzh();
        }

        public static zza zza(zzbh zzbhVar) {
            return zzo.zza(zzbhVar);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r13v12 */
        /* JADX WARN: Type inference failed for: r13v13, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzgd$zzc, com.google.android.gms.internal.mlkit_vision_pose_common.zzhv<com.google.android.gms.internal.mlkit_vision_pose_common.zzbm$zzbh>] */
        /* JADX WARN: Type inference failed for: r13v16 */
        /* JADX WARN: Type inference failed for: r13v17 */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd
        protected final Object zza(int i, Object obj, Object obj2) {
            ?? r13;
            zzbo zzboVar = null;
            switch (zzbo.zza[i - 1]) {
                case 1:
                    return new zzbh();
                case 2:
                    return new zza(zzboVar);
                case 3:
                    return zza(zzo, "\u0001\u000b\u0000\u0001\u0001\u000b\u000b\u0000\u0001\u0000\u0001ဈ\u0000\u0002ဈ\u0001\u0003ဈ\u0002\u0004ဈ\u0003\u0005ဈ\u0004\u0006ဈ\u0005\u0007ဈ\u0006\b\u001a\tဈ\u0007\nဇ\b\u000bဇ\t", new Object[]{"zzc", "zzd", "zze", "zzf", "zzg", "zzh", "zzi", "zzj", "zzk", "zzl", "zzm", "zzn"});
                case 4:
                    return zzo;
                case 5:
                    zzhv<zzbh> zzhvVar = zzp;
                    Object obj3 = zzhvVar;
                    if (zzhvVar == null) {
                        synchronized (zzbh.class) {
                            zzhv<zzbh> zzhvVar2 = zzp;
                            r13 = zzhvVar2;
                            if (zzhvVar2 == null) {
                                ?? zzcVar = new zzgd.zzc(zzo);
                                zzp = zzcVar;
                                r13 = zzcVar;
                            }
                            break;
                        }
                        obj3 = r13;
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

        public static zzbh zzc() {
            return zzo;
        }

        static {
            zzbh zzbhVar = new zzbh();
            zzo = zzbhVar;
            zzgd.zza((Class<zzbh>) zzbh.class, zzbhVar);
        }
    }
}
