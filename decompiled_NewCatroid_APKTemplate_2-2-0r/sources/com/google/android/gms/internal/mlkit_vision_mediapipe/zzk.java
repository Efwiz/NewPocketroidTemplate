package com.google.android.gms.internal.mlkit_vision_mediapipe;

import com.google.android.gms.internal.mlkit_vision_mediapipe.zzd;
import com.google.android.gms.internal.mlkit_vision_mediapipe.zzv;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Arrays;
import java.util.logging.Level;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzk<LOGGER extends zzd<API>, API extends zzv<API>> implements zzai, zzv<API> {
    private static final String zza = new String();
    private final Level zzb;
    private final long zzc;
    private zzc zzd;
    private zzo zze;
    private zzaz zzf;
    private Object[] zzg;

    /* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
    public static final class zza {
        public static final zzx<Throwable> zza = zzx.zza("cause", Throwable.class);
        public static final zzx<Integer> zzb = zzx.zza("ratelimit_count", Integer.class);
        public static final zzx<zzr.zza> zzc = zzx.zza("ratelimit_period", zzr.zza.class);
        public static final zzx<String> zzd = zzx.zza("unique_key", String.class);
        public static final zzx<Boolean> zze = zzx.zza("forced", Boolean.class);
        public static final zzx<zzbp> zzf = zzx.zza("tags", zzbp.class);
        public static final zzx<zzw> zzg = zzx.zza("stack_size", zzw.class);
    }

    protected zzk(Level level, boolean z) {
        this(level, z, zzap.zze());
    }

    protected abstract zzcf zza();

    protected abstract LOGGER zzb();

    protected abstract API zzc();

    /* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
    static final class zzc extends zzan {
        private Object[] zza = new Object[8];
        private int zzb = 0;

        zzc() {
        }

        @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzan
        public final int zza() {
            return this.zzb;
        }

        @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzan
        public final zzx<?> zza(int i) {
            if (i >= this.zzb) {
                throw new IndexOutOfBoundsException();
            }
            return (zzx) this.zza[i * 2];
        }

        @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzan
        public final Object zzb(int i) {
            if (i >= this.zzb) {
                throw new IndexOutOfBoundsException();
            }
            return this.zza[(i * 2) + 1];
        }

        private final int zzc(zzx<?> zzxVar) {
            for (int i = 0; i < this.zzb; i++) {
                if (this.zza[i * 2].equals(zzxVar)) {
                    return i;
                }
            }
            return -1;
        }

        @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzan
        @NullableDecl
        public final <T> T zza(zzx<T> zzxVar) {
            int iZzc = zzc(zzxVar);
            if (iZzc != -1) {
                return zzxVar.zza(this.zza[(iZzc * 2) + 1]);
            }
            return null;
        }

        final <T> void zza(zzx<T> zzxVar, T t) {
            int iZzc = zzc(zzxVar);
            if (iZzc != -1) {
                this.zza[(iZzc * 2) + 1] = zzcj.zza(t, "metadata value");
                return;
            }
            int i = (this.zzb + 1) * 2;
            Object[] objArr = this.zza;
            if (i > objArr.length) {
                this.zza = Arrays.copyOf(objArr, objArr.length * 2);
            }
            this.zza[this.zzb * 2] = zzcj.zza(zzxVar, "metadata key");
            this.zza[(this.zzb * 2) + 1] = zzcj.zza(t, "metadata value");
            this.zzb++;
        }

        final void zzb(zzx<?> zzxVar) {
            int i;
            int iZzc = zzc(zzxVar);
            if (iZzc >= 0) {
                int i2 = iZzc * 2;
                int i3 = i2 + 2;
                while (true) {
                    i = this.zzb;
                    if (i3 >= i * 2) {
                        break;
                    }
                    Object obj = this.zza[i3];
                    if (!obj.equals(zzxVar)) {
                        Object[] objArr = this.zza;
                        objArr[i2] = obj;
                        objArr[i2 + 1] = objArr[i3 + 1];
                        i2 += 2;
                    }
                    i3 += 2;
                }
                this.zzb = i - ((i3 - i2) >> 1);
                while (i2 < i3) {
                    this.zza[i2] = null;
                    i2++;
                }
            }
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("Metadata{");
            for (int i = 0; i < zza(); i++) {
                sb.append(" '").append(zza(i)).append("': ").append(zzb(i));
            }
            return sb.append(" }").toString();
        }
    }

    private zzk(Level level, boolean z, long j) {
        this.zzd = null;
        this.zze = null;
        this.zzf = null;
        this.zzg = null;
        this.zzb = (Level) zzcj.zza(level, FirebaseAnalytics.Param.LEVEL);
        this.zzc = j;
        if (z) {
            zza((zzx<Boolean>) zza.zze, Boolean.TRUE);
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
    private static final class zzb implements zzp {
        private final zzo zza;
        private final String zzb;

        private zzb(zzo zzoVar, String str) {
            this.zza = (zzo) zzcj.zza(zzoVar, "log site");
            this.zzb = (String) zzcj.zza(str, "log site key");
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof zzb)) {
                return false;
            }
            zzb zzbVar = (zzb) obj;
            return this.zza.equals(zzbVar.zza) && this.zzb.equals(zzbVar.zzb);
        }

        public final int hashCode() {
            return this.zza.hashCode() ^ this.zzb.hashCode();
        }

        public final String toString() {
            String strValueOf = String.valueOf(this.zza);
            String str = this.zzb;
            return new StringBuilder(String.valueOf(strValueOf).length() + 46 + String.valueOf(str).length()).append("SpecializedLogSiteKey{ logSite=").append(strValueOf).append(", extraKey='").append(str).append("' }").toString();
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzai
    public final Level zzd() {
        return this.zzb;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzai
    public final long zze() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzai
    public final zzo zzf() {
        zzo zzoVar = this.zze;
        if (zzoVar != null) {
            return zzoVar;
        }
        throw new IllegalStateException("cannot request log site information prior to postProcess()");
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzai
    public final zzaz zzg() {
        return this.zzf;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzai
    public final Object[] zzh() {
        if (this.zzf == null) {
            throw new IllegalStateException("cannot get arguments unless a template context exists");
        }
        return this.zzg;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzai
    public final Object zzi() {
        if (this.zzf != null) {
            throw new IllegalStateException("cannot get literal argument if a template context exists");
        }
        return this.zzg[0];
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzai
    public final boolean zzj() {
        return this.zzd != null && Boolean.TRUE.equals(this.zzd.zza(zza.zze));
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzai
    public final zzan zzk() {
        zzc zzcVar = this.zzd;
        return zzcVar != null ? zzcVar : zzan.zzb();
    }

    protected final <T> void zza(zzx<T> zzxVar, T t) {
        if (this.zzd == null) {
            this.zzd = new zzc();
        }
        this.zzd.zza(zzxVar, t);
    }

    protected boolean zza(@NullableDecl zzp zzpVar) {
        zzc zzcVar = this.zzd;
        if (zzcVar != null && zzpVar != null) {
            Integer num = (Integer) zzcVar.zza(zza.zzb);
            zzr.zza zzaVar = (zzr.zza) this.zzd.zza(zza.zzc);
            zzr zzrVarZza = zzr.zza(zzpVar);
            if (num != null && !zzrVarZza.zza(num.intValue())) {
                return false;
            }
            if (zzaVar != null && !zzrVarZza.zza(this.zzc, zzaVar)) {
                return false;
            }
        }
        zzw zzwVar = (zzw) zzk().zza(zza.zzg);
        if (zzwVar != null) {
            zzx<zzw> zzxVar = zza.zzg;
            zzc zzcVar2 = this.zzd;
            if (zzcVar2 != null) {
                zzcVar2.zzb(zzxVar);
            }
            zza((zzx<zzs>) zza.zza, new zzs((Throwable) zzk().zza(zza.zza), zzwVar, zzcg.zza(zzk.class, new Throwable(), zzwVar.zza(), 1)));
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final boolean zzl() {
        if (this.zze == null) {
            this.zze = (zzo) zzcj.zza(zzap.zza().zza(zzk.class, 1), "logger backend must not return a null LogSite");
        }
        zzp zzpVar = null;
        Object[] objArr = 0;
        if (this.zze != zzo.zza) {
            zzp zzbVar = this.zze;
            String str = (String) zzk().zza(zza.zzd);
            if (str != null) {
                zzbVar = new zzb(this.zze, str);
            }
            zzpVar = zzbVar;
        }
        if (!zza(zzpVar)) {
            return false;
        }
        zzbp zzbpVarZzc = zzap.zzc();
        if (!zzbpVarZzc.zzc()) {
            zza((zzx<zzbp>) zza.zzf, zzbpVarZzc);
        }
        return true;
    }

    private final void zza(String str, Object... objArr) {
        this.zzg = objArr;
        for (int i = 0; i < objArr.length; i++) {
            Object obj = objArr[i];
            if (obj instanceof zzl) {
                objArr[i] = ((zzl) obj).zza();
            }
        }
        if (str != zza) {
            this.zzf = new zzaz(zza(), str);
        }
        zzb().zza(this);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzv
    public final API zza(String str, String str2, int i, @NullableDecl String str3) {
        zzo zzoVarZza = zzo.zza(str, str2, i, str3);
        if (this.zze == null) {
            this.zze = zzoVarZza;
        }
        return (API) zzc();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzv
    public final void zza(String str) {
        if (zzl()) {
            zza(zza, str);
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzv
    public final void zza(String str, @NullableDecl Object obj) {
        if (zzl()) {
            zza(str, obj);
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzv
    public final void zza(String str, @NullableDecl Object obj, @NullableDecl Object obj2) {
        if (zzl()) {
            zza(str, obj, obj2);
        }
    }
}
