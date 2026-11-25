package com.google.mlkit.vision.pose;

import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.mlkit_vision_pose_common.zza;
import com.google.android.gms.internal.mlkit_vision_pose_common.zzbm;
import com.google.android.gms.internal.mlkit_vision_pose_common.zzgd;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes10.dex */
public class PoseDetectorOptionsBase {
    public static final int SINGLE_IMAGE_MODE = 2;
    public static final int STREAM_MODE = 1;
    private final int zza;

    @Nullable
    private final Executor zzb;
    private final boolean zzc;
    private final String zzd;
    private final String zze;

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    @Retention(RetentionPolicy.CLASS)
    public @interface DetectorMode {
    }

    public final int zza() {
        return this.zza;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static abstract class Builder<B extends Builder<B>> {
        private int zza = 1;

        @Nullable
        private Executor zzb;
        private boolean zzc;
        private String zzd;
        private String zze;

        protected Builder() {
        }

        protected abstract PoseDetectorOptionsBase build();

        public B setDetectorMode(int i) {
            this.zza = i;
            return this;
        }

        public B setExecutor(Executor executor) {
            this.zzb = executor;
            return this;
        }

        protected B setModelInfo(boolean z, String str, String str2) {
            this.zzc = z;
            this.zzd = str;
            this.zze = str2;
            return this;
        }
    }

    public Executor getExecutor() {
        return this.zzb;
    }

    public final String zzb() {
        return this.zzd;
    }

    public final String zzc() {
        return this.zze;
    }

    protected PoseDetectorOptionsBase(Builder<?> builder) {
        this.zza = ((Builder) builder).zza;
        this.zzb = ((Builder) builder).zzb;
        this.zzc = ((Builder) builder).zzc;
        this.zzd = (String) Preconditions.checkNotNull(((Builder) builder).zzd, "personModelPath cannot be null");
        this.zze = (String) Preconditions.checkNotNull(((Builder) builder).zze, "landmarkModelPath cannot be null");
    }

    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof PoseDetectorOptionsBase)) {
            return false;
        }
        PoseDetectorOptionsBase poseDetectorOptionsBase = (PoseDetectorOptionsBase) obj;
        return getClass().equals(poseDetectorOptionsBase.getClass()) && this.zza == poseDetectorOptionsBase.zza && this.zzc == poseDetectorOptionsBase.zzc && Objects.equal(this.zzd, poseDetectorOptionsBase.zzd) && Objects.equal(this.zze, poseDetectorOptionsBase.zze);
    }

    public int hashCode() {
        return Objects.hashCode(getClass(), Integer.valueOf(this.zza), Boolean.valueOf(this.zzc), this.zzd, this.zze);
    }

    public final zzbm.zzba zzd() {
        zzbm.zzba.zza zzaVarZza = zzbm.zzba.zza();
        if (this.zza == 1) {
            zzaVarZza.zza(zzbm.zzba.zzb.STREAM);
        } else {
            zzaVarZza.zza(zzbm.zzba.zzb.SINGLE_IMAGE);
        }
        if (this.zzc) {
            zzaVarZza.zza(zzbm.zzba.zzc.FAST);
            zzaVarZza.zzb(zzbm.zzba.zzc.FAST);
        } else {
            zzaVarZza.zza(zzbm.zzba.zzc.ACCURATE);
            zzaVarZza.zzb(zzbm.zzba.zzc.ACCURATE);
        }
        return (zzbm.zzba) ((zzgd) zzaVarZza.zzh());
    }

    public String toString() {
        return zza.zza("PoseDetectorOptionsBase").zza("DetectorMode", this.zza).zza("areFastModels", this.zzc).zza("personModelPath", this.zzd).zza("landmarkModelPath", this.zze).toString();
    }
}
