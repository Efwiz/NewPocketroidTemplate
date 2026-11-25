package com.google.mlkit.vision.face;

import android.graphics.PointF;
import android.graphics.Rect;
import android.util.SparseArray;
import com.google.android.gms.internal.mlkit_vision_face.zzd;
import com.google.android.gms.internal.mlkit_vision_face.zzf;
import com.google.android.gms.internal.mlkit_vision_face.zzli;
import com.google.android.gms.internal.mlkit_vision_face.zzlm;
import com.google.android.gms.internal.mlkit_vision_face.zzls;
import com.google.android.gms.internal.mlkit_vision_face.zzn;
import com.google.android.gms.internal.mlkit_vision_face.zzu;
import com.google.android.gms.internal.mlkit_vision_face.zzv;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes10.dex */
public class Face {
    private final Rect zza;
    private int zzb;
    private final float zzc;
    private final float zzd;
    private final float zze;
    private final float zzf;
    private final float zzg;
    private final float zzh;
    private final SparseArray<FaceLandmark> zzi = new SparseArray<>();
    private final SparseArray<FaceContour> zzj = new SparseArray<>();

    public Face(zzf zzfVar) {
        float f = zzfVar.zzc;
        float f2 = zzfVar.zze / 2.0f;
        float f3 = zzfVar.zzd;
        float f4 = zzfVar.zzf / 2.0f;
        this.zza = new Rect((int) (f - f2), (int) (f3 - f4), (int) (f + f2), (int) (f3 + f4));
        this.zzb = zzfVar.zzb;
        for (zzn zznVar : zzfVar.zzj) {
            if (zzd(zznVar.zzd)) {
                SparseArray<FaceLandmark> sparseArray = this.zzi;
                int i = zznVar.zzd;
                sparseArray.put(i, new FaceLandmark(i, new PointF(zznVar.zzb, zznVar.zzc)));
            }
        }
        for (zzd zzdVar : zzfVar.zzn) {
            int i2 = zzdVar.zzb;
            if (zze(i2)) {
                SparseArray<FaceContour> sparseArray2 = this.zzj;
                PointF[] pointFArr = zzdVar.zza;
                pointFArr.getClass();
                int length = pointFArr.length;
                long j = ((long) length) + 5 + ((long) (length / 10));
                ArrayList arrayList = new ArrayList(j > 2147483647L ? Integer.MAX_VALUE : (int) j);
                Collections.addAll(arrayList, pointFArr);
                sparseArray2.put(i2, new FaceContour(i2, arrayList));
            }
        }
        this.zzf = zzfVar.zzi;
        this.zzg = zzfVar.zzg;
        this.zzh = zzfVar.zzh;
        this.zze = zzfVar.zzm;
        this.zzd = zzfVar.zzk;
        this.zzc = zzfVar.zzl;
    }

    private static boolean zzd(int i) {
        return i == 0 || i == 1 || i == 7 || i == 3 || i == 9 || i == 4 || i == 10 || i == 5 || i == 11 || i == 6;
    }

    private static boolean zze(int i) {
        return i <= 15 && i > 0;
    }

    public List<FaceContour> getAllContours() {
        ArrayList arrayList = new ArrayList();
        int size = this.zzj.size();
        for (int i = 0; i < size; i++) {
            arrayList.add(this.zzj.valueAt(i));
        }
        return arrayList;
    }

    public List<FaceLandmark> getAllLandmarks() {
        ArrayList arrayList = new ArrayList();
        int size = this.zzi.size();
        for (int i = 0; i < size; i++) {
            arrayList.add(this.zzi.valueAt(i));
        }
        return arrayList;
    }

    public Rect getBoundingBox() {
        return this.zza;
    }

    public FaceContour getContour(int i) {
        return this.zzj.get(i);
    }

    public float getHeadEulerAngleX() {
        return this.zzf;
    }

    public float getHeadEulerAngleY() {
        return this.zzg;
    }

    public float getHeadEulerAngleZ() {
        return this.zzh;
    }

    public FaceLandmark getLandmark(int i) {
        return this.zzi.get(i);
    }

    public Float getLeftEyeOpenProbability() {
        float f = this.zze;
        if (f < 0.0f || f > 1.0f) {
            return null;
        }
        return Float.valueOf(this.zzd);
    }

    public Float getRightEyeOpenProbability() {
        float f = this.zzc;
        if (f < 0.0f || f > 1.0f) {
            return null;
        }
        return Float.valueOf(f);
    }

    public Float getSmilingProbability() {
        float f = this.zze;
        if (f < 0.0f || f > 1.0f) {
            return null;
        }
        return Float.valueOf(f);
    }

    public Integer getTrackingId() {
        int i = this.zzb;
        if (i == -1) {
            return null;
        }
        return Integer.valueOf(i);
    }

    public String toString() {
        zzu zzuVarZza = zzv.zza("Face");
        zzuVarZza.zza("boundingBox", this.zza);
        zzuVarZza.zzd("trackingId", this.zzb);
        zzuVarZza.zzc("rightEyeOpenProbability", this.zzc);
        zzuVarZza.zzc("leftEyeOpenProbability", this.zzd);
        zzuVarZza.zzc("smileProbability", this.zze);
        zzuVarZza.zzc("eulerX", this.zzf);
        zzuVarZza.zzc("eulerY", this.zzg);
        zzuVarZza.zzc("eulerZ", this.zzh);
        zzu zzuVarZza2 = zzv.zza("Landmarks");
        for (int i = 0; i <= 11; i++) {
            if (zzd(i)) {
                StringBuilder sb = new StringBuilder(20);
                sb.append("landmark_");
                sb.append(i);
                zzuVarZza2.zza(sb.toString(), getLandmark(i));
            }
        }
        zzuVarZza.zza("landmarks", zzuVarZza2.toString());
        zzu zzuVarZza3 = zzv.zza("Contours");
        for (int i2 = 1; i2 <= 15; i2++) {
            StringBuilder sb2 = new StringBuilder(19);
            sb2.append("Contour_");
            sb2.append(i2);
            zzuVarZza3.zza(sb2.toString(), getContour(i2));
        }
        zzuVarZza.zza("contours", zzuVarZza3.toString());
        return zzuVarZza.toString();
    }

    public final SparseArray<FaceContour> zza() {
        return this.zzj;
    }

    public final void zzb(SparseArray<FaceContour> sparseArray) {
        this.zzj.clear();
        for (int i = 0; i < sparseArray.size(); i++) {
            this.zzj.put(sparseArray.keyAt(i), sparseArray.valueAt(i));
        }
    }

    public final void zzc(int i) {
        this.zzb = -1;
    }

    public Face(zzlm zzlmVar) {
        this.zza = zzlmVar.zzb();
        this.zzb = zzlmVar.zza();
        for (zzls zzlsVar : zzlmVar.zzi()) {
            if (zzd(zzlsVar.zza())) {
                this.zzi.put(zzlsVar.zza(), new FaceLandmark(zzlsVar.zza(), zzlsVar.zzb()));
            }
        }
        for (zzli zzliVar : zzlmVar.zzj()) {
            int iZza = zzliVar.zza();
            if (zze(iZza)) {
                this.zzj.put(iZza, new FaceContour(iZza, zzliVar.zzb()));
            }
        }
        this.zzf = zzlmVar.zze();
        this.zzg = zzlmVar.zzd();
        this.zzh = -zzlmVar.zzc();
        this.zze = zzlmVar.zzh();
        this.zzd = zzlmVar.zzf();
        this.zzc = zzlmVar.zzg();
    }
}
