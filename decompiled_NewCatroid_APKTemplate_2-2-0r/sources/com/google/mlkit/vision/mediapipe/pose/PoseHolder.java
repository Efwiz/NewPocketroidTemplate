package com.google.mlkit.vision.mediapipe.pose;

import com.google.android.gms.internal.mlkit_vision_mediapipe.zzcu;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes10.dex */
public class PoseHolder {
    private final List<PoseLandmarkHolder> zza = new ArrayList();

    public PoseHolder(zzcu.zzb zzbVar) {
        int i = 0;
        for (zzcu.zza zzaVar : zzbVar.zza()) {
            this.zza.add(new PoseLandmarkHolder(i, zzaVar.zza(), zzaVar.zzb(), zzaVar.zzc()));
            i++;
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
    public static class PoseLandmarkHolder {
        private final int zza;
        private final float zzb;
        private final float zzc;
        private final float zzd;

        PoseLandmarkHolder(int i, float f, float f2, float f3) {
            this.zza = i;
            this.zzb = f;
            this.zzc = f2;
            this.zzd = f3;
        }

        public int getIndex() {
            return this.zza;
        }

        public float getX() {
            return this.zzb;
        }

        public float getY() {
            return this.zzc;
        }

        public float getInFrameLikelihood() {
            return this.zzd;
        }
    }

    public List<PoseLandmarkHolder> getPoseLandmarkHolders() {
        return this.zza;
    }
}
