package com.google.mlkit.vision.mediapipe;

import com.google.mlkit.common.sdkinternal.MlKitContext;
import java.util.Map;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes10.dex */
final class zza extends MediaPipeGraphRunnerConfig {
    private final MlKitContext zza;
    private final String zzb;
    private final String zzc;
    private final String zzd;
    private final Map<String, String> zze;
    private final MediaPipeGraphRunnerConfig.ResultType zzf;

    zza(MlKitContext mlKitContext, String str, String str2, String str3, Map<String, String> map, MediaPipeGraphRunnerConfig.ResultType resultType) {
        if (mlKitContext == null) {
            throw new NullPointerException("Null mlKitContext");
        }
        this.zza = mlKitContext;
        if (str == null) {
            throw new NullPointerException("Null graphConfigPath");
        }
        this.zzb = str;
        if (str2 == null) {
            throw new NullPointerException("Null inputFrameStreamName");
        }
        this.zzc = str2;
        if (str3 == null) {
            throw new NullPointerException("Null outputStreamName");
        }
        this.zzd = str3;
        this.zze = map;
        if (resultType == null) {
            throw new NullPointerException("Null resultType");
        }
        this.zzf = resultType;
    }

    @Override // com.google.mlkit.vision.mediapipe.MediaPipeGraphRunnerConfig
    final MlKitContext zza() {
        return this.zza;
    }

    @Override // com.google.mlkit.vision.mediapipe.MediaPipeGraphRunnerConfig
    final String zzb() {
        return this.zzb;
    }

    @Override // com.google.mlkit.vision.mediapipe.MediaPipeGraphRunnerConfig
    final String zzc() {
        return this.zzc;
    }

    @Override // com.google.mlkit.vision.mediapipe.MediaPipeGraphRunnerConfig
    final String zzd() {
        return this.zzd;
    }

    @Override // com.google.mlkit.vision.mediapipe.MediaPipeGraphRunnerConfig
    final Map<String, String> zze() {
        return this.zze;
    }

    @Override // com.google.mlkit.vision.mediapipe.MediaPipeGraphRunnerConfig
    final MediaPipeGraphRunnerConfig.ResultType zzf() {
        return this.zzf;
    }

    public final String toString() {
        String strValueOf = String.valueOf(this.zza);
        String str = this.zzb;
        String str2 = this.zzc;
        String str3 = this.zzd;
        String strValueOf2 = String.valueOf(this.zze);
        String strValueOf3 = String.valueOf(this.zzf);
        return new StringBuilder(String.valueOf(strValueOf).length() + 130 + String.valueOf(str).length() + String.valueOf(str2).length() + String.valueOf(str3).length() + String.valueOf(strValueOf2).length() + String.valueOf(strValueOf3).length()).append("MediaPipeGraphRunnerConfig{mlKitContext=").append(strValueOf).append(", graphConfigPath=").append(str).append(", inputFrameStreamName=").append(str2).append(", outputStreamName=").append(str3).append(", assetRegistry=").append(strValueOf2).append(", resultType=").append(strValueOf3).append("}").toString();
    }

    public final boolean equals(Object obj) {
        Map<String, String> map;
        if (obj == this) {
            return true;
        }
        if (obj instanceof MediaPipeGraphRunnerConfig) {
            MediaPipeGraphRunnerConfig mediaPipeGraphRunnerConfig = (MediaPipeGraphRunnerConfig) obj;
            if (this.zza.equals(mediaPipeGraphRunnerConfig.zza()) && this.zzb.equals(mediaPipeGraphRunnerConfig.zzb()) && this.zzc.equals(mediaPipeGraphRunnerConfig.zzc()) && this.zzd.equals(mediaPipeGraphRunnerConfig.zzd()) && ((map = this.zze) != null ? map.equals(mediaPipeGraphRunnerConfig.zze()) : mediaPipeGraphRunnerConfig.zze() == null) && this.zzf.equals(mediaPipeGraphRunnerConfig.zzf())) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int iHashCode = (((((((this.zza.hashCode() ^ 1000003) * 1000003) ^ this.zzb.hashCode()) * 1000003) ^ this.zzc.hashCode()) * 1000003) ^ this.zzd.hashCode()) * 1000003;
        Map<String, String> map = this.zze;
        return ((iHashCode ^ (map == null ? 0 : map.hashCode())) * 1000003) ^ this.zzf.hashCode();
    }
}
