package com.google.mlkit.vision.face.internal;

import android.graphics.Rect;
import android.os.SystemClock;
import android.util.Pair;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.mlkit_vision_face.zzdk;
import com.google.android.gms.internal.mlkit_vision_face.zzdl;
import com.google.android.gms.internal.mlkit_vision_face.zzdm;
import com.google.android.gms.internal.mlkit_vision_face.zzhw;
import com.google.android.gms.internal.mlkit_vision_face.zzij;
import com.google.android.gms.internal.mlkit_vision_face.zzip;
import com.google.android.gms.internal.mlkit_vision_face.zziq;
import com.google.android.gms.internal.mlkit_vision_face.zzis;
import com.google.android.gms.internal.mlkit_vision_face.zzjb;
import com.google.android.gms.internal.mlkit_vision_face.zzjc;
import com.google.android.gms.internal.mlkit_vision_face.zzkn;
import com.google.android.gms.internal.mlkit_vision_face.zzko;
import com.google.android.gms.internal.mlkit_vision_face.zzkq;
import com.google.android.gms.internal.mlkit_vision_face.zzkr;
import com.google.android.gms.internal.mlkit_vision_face.zzks;
import com.google.android.gms.internal.mlkit_vision_face.zzld;
import com.google.mlkit.common.MlKitException;
import com.google.mlkit.common.sdkinternal.MLTask;
import com.google.mlkit.common.sdkinternal.MlKitContext;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.common.internal.BitmapInStreamingChecker;
import com.google.mlkit.vision.common.internal.ImageUtils;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetectorOptions;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes10.dex */
public final class zzh extends MLTask<List<Face>, InputImage> {
    static final AtomicBoolean zza = new AtomicBoolean(true);
    private static final ImageUtils zzf = ImageUtils.getInstance();
    private final FaceDetectorOptions zzb;
    private final zzkq zzc;
    private final zzkr zzd;
    private final zzb zze;
    private boolean zzg;
    private final BitmapInStreamingChecker zzh = new BitmapInStreamingChecker();

    public zzh(zzkq zzkqVar, FaceDetectorOptions faceDetectorOptions, zzb zzbVar) {
        Preconditions.checkNotNull(faceDetectorOptions, "FaceDetectorOptions can not be null");
        this.zzb = faceDetectorOptions;
        this.zzc = zzkqVar;
        this.zze = zzbVar;
        this.zzd = zzkr.zza(MlKitContext.getInstance().getApplicationContext());
    }

    static void zzd(List<Face> list) {
        Iterator<Face> it = list.iterator();
        while (it.hasNext()) {
            it.next().zzc(-1);
        }
    }

    private final synchronized void zzg(final zzip zzipVar, long j, final InputImage inputImage, final int i, final int i2) {
        final long jElapsedRealtime = SystemClock.elapsedRealtime() - j;
        this.zzc.zza(new zzko(this, jElapsedRealtime, zzipVar, i, i2, inputImage) { // from class: com.google.mlkit.vision.face.internal.zzf
            private final zzh zza;
            private final long zzb;
            private final zzip zzc;
            private final int zzd;
            private final int zze;
            private final InputImage zzf;

            {
                this.zza = this;
                this.zzb = jElapsedRealtime;
                this.zzc = zzipVar;
                this.zzd = i;
                this.zze = i2;
                this.zzf = inputImage;
            }

            @Override // com.google.android.gms.internal.mlkit_vision_face.zzko
            public final zzks zza() {
                return this.zza.zzf(this.zzb, this.zzc, this.zzd, this.zze, this.zzf);
            }
        }, zziq.ON_DEVICE_FACE_DETECT);
        zzdl zzdlVar = new zzdl();
        zzdlVar.zza(zzipVar);
        zzdlVar.zzb(Boolean.valueOf(zza.get()));
        ImageUtils imageUtils = zzf;
        zzdlVar.zzc(zzld.zza(imageUtils.getMobileVisionImageFormat(inputImage), imageUtils.getMobileVisionImageSize(inputImage)));
        zzdlVar.zze(Integer.valueOf(i));
        zzdlVar.zzf(Integer.valueOf(i2));
        zzdlVar.zzd(zzi.zza(this.zzb));
        this.zzc.zzb(zzdlVar.zzg(), jElapsedRealtime, zziq.AGGREGATED_ON_DEVICE_FACE_DETECTION, new zzkn(this) { // from class: com.google.mlkit.vision.face.internal.zzg
            private final zzh zza;

            {
                this.zza = this;
            }

            @Override // com.google.android.gms.internal.mlkit_vision_face.zzkn
            public final zzks zza(Object obj, int i3, zzhw zzhwVar) {
                return this.zza.zze((zzdm) obj, i3, zzhwVar);
            }
        });
        boolean z = this.zzg;
        long j2 = j + jElapsedRealtime;
        this.zzd.zzb(true != z ? 24303 : 24304, zzipVar.zza(), j, j2);
    }

    @Override // com.google.mlkit.common.sdkinternal.ModelResource
    public final synchronized void load() throws MlKitException {
        this.zzg = this.zze.zza();
    }

    @Override // com.google.mlkit.common.sdkinternal.ModelResource
    public final synchronized void release() {
        this.zze.zzc();
        zza.set(true);
    }

    /* JADX WARN: Code duplicated, block: B:33:0x00d8  */
    /* JADX WARN: Code duplicated, block: B:42:0x0108  */
    /* JADX WARN: Code duplicated, block: B:43:0x010a A[Catch: MlKitException -> 0x0129, all -> 0x0148, TryCatch #1 {, blocks: (B:4:0x0005, B:5:0x000e, B:9:0x0021, B:10:0x002a, B:12:0x002d, B:40:0x0104, B:47:0x0118, B:46:0x0113, B:43:0x010a, B:15:0x003a, B:16:0x0041, B:17:0x004a, B:19:0x0050, B:20:0x005b, B:22:0x0061, B:24:0x006d, B:26:0x0073, B:28:0x0081, B:30:0x00ab, B:32:0x00cf, B:34:0x00de, B:54:0x012e, B:56:0x0136, B:58:0x013b, B:59:0x0147, B:57:0x0139, B:37:0x00f1, B:39:0x00fc), top: B:64:0x0005 }] */
    /* JADX WARN: Code duplicated, block: B:45:0x0111  */
    /* JADX WARN: Code duplicated, block: B:46:0x0113 A[Catch: MlKitException -> 0x0129, all -> 0x0148, TryCatch #1 {, blocks: (B:4:0x0005, B:5:0x000e, B:9:0x0021, B:10:0x002a, B:12:0x002d, B:40:0x0104, B:47:0x0118, B:46:0x0113, B:43:0x010a, B:15:0x003a, B:16:0x0041, B:17:0x004a, B:19:0x0050, B:20:0x005b, B:22:0x0061, B:24:0x006d, B:26:0x0073, B:28:0x0081, B:30:0x00ab, B:32:0x00cf, B:34:0x00de, B:54:0x012e, B:56:0x0136, B:58:0x013b, B:59:0x0147, B:57:0x0139, B:37:0x00f1, B:39:0x00fc), top: B:64:0x0005 }] */
    @Override // com.google.mlkit.common.sdkinternal.MLTask
    /* JADX INFO: renamed from: zzc, reason: merged with bridge method [inline-methods] */
    public final synchronized List<Face> run(InputImage inputImage) throws MlKitException {
        long j;
        List<Face> arrayList;
        Iterator it;
        Iterator it2;
        List<Face> list;
        int size;
        int size2;
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        this.zzh.check(inputImage);
        try {
            Pair<List<Face>, List<Face>> pairZzb = this.zze.zzb(inputImage);
            List list2 = (List) pairZzb.first;
            List list3 = (List) pairZzb.second;
            if (list2 == null && list3 == null) {
                throw new MlKitException("No detector is enabled", 13);
            }
            if (list2 == null) {
                list = (List) Preconditions.checkNotNull(list3);
            } else if (list3 == null) {
                list = (List) Preconditions.checkNotNull(list2);
            } else {
                HashSet hashSet = new HashSet();
                Iterator it3 = list3.iterator();
                while (it3.hasNext()) {
                    Face face = (Face) it3.next();
                    Iterator it4 = list2.iterator();
                    boolean z = false;
                    while (it4.hasNext()) {
                        Face face2 = (Face) it4.next();
                        if (face.getBoundingBox() == null || face2.getBoundingBox() == null) {
                            it = it3;
                            it2 = it4;
                            j = jElapsedRealtime;
                        } else {
                            Rect boundingBox = face.getBoundingBox();
                            Rect boundingBox2 = face2.getBoundingBox();
                            if (boundingBox.intersect(boundingBox2)) {
                                it = it3;
                                it2 = it4;
                                j = jElapsedRealtime;
                                double dMin = (Math.min(boundingBox.right, boundingBox2.right) - Math.max(boundingBox.left, boundingBox2.left)) * (Math.min(boundingBox.bottom, boundingBox2.bottom) - Math.max(boundingBox.top, boundingBox2.top));
                                try {
                                    if (dMin / ((((double) ((boundingBox.right - boundingBox.left) * (boundingBox.bottom - boundingBox.top))) + ((double) ((boundingBox2.right - boundingBox2.left) * (boundingBox2.bottom - boundingBox2.top)))) - dMin) > 0.6d) {
                                        face2.zzb(face.zza());
                                        z = true;
                                    }
                                } catch (MlKitException e) {
                                    e = e;
                                    zzg(e.getErrorCode() == 14 ? zzip.MODEL_NOT_DOWNLOADED : zzip.UNKNOWN_ERROR, j, inputImage, 0, 0);
                                    throw e;
                                }
                            } else {
                                it = it3;
                                it2 = it4;
                                j = jElapsedRealtime;
                            }
                        }
                        hashSet.add(face2);
                        it3 = it;
                        it4 = it2;
                        jElapsedRealtime = j;
                    }
                    Iterator it5 = it3;
                    long j2 = jElapsedRealtime;
                    if (!z) {
                        hashSet.add(face);
                    }
                    it3 = it5;
                    jElapsedRealtime = j2;
                }
                j = jElapsedRealtime;
                arrayList = new ArrayList(hashSet);
                zzip zzipVar = zzip.NO_ERROR;
                if (list3 == null) {
                    size = 0;
                } else {
                    size = list3.size();
                }
                if (list2 == null) {
                    size2 = 0;
                } else {
                    size2 = list2.size();
                }
                zzg(zzipVar, j, inputImage, size, size2);
                zza.set(false);
            }
            arrayList = list;
            j = jElapsedRealtime;
            zzip zzipVar2 = zzip.NO_ERROR;
            if (list3 == null) {
                size = 0;
            } else {
                size = list3.size();
            }
            if (list2 == null) {
                size2 = 0;
            } else {
                size2 = list2.size();
            }
            zzg(zzipVar2, j, inputImage, size, size2);
            zza.set(false);
        } catch (MlKitException e2) {
            e = e2;
            j = jElapsedRealtime;
        }
        return arrayList;
    }

    final /* synthetic */ zzks zze(zzdm zzdmVar, int i, zzhw zzhwVar) {
        zzis zzisVar = new zzis();
        zzisVar.zzc(Boolean.valueOf(this.zzg));
        zzdk zzdkVar = new zzdk();
        zzdkVar.zzb(Integer.valueOf(i));
        zzdkVar.zza(zzdmVar);
        zzdkVar.zzc(zzhwVar);
        zzisVar.zze(zzdkVar.zzd());
        return zzks.zzc(zzisVar);
    }

    final /* synthetic */ zzks zzf(long j, zzip zzipVar, int i, int i2, InputImage inputImage) {
        zzjb zzjbVar = new zzjb();
        zzij zzijVar = new zzij();
        zzijVar.zza(Long.valueOf(j));
        zzijVar.zzb(zzipVar);
        zzijVar.zzc(Boolean.valueOf(zza.get()));
        zzijVar.zzd(true);
        zzijVar.zze(true);
        zzjbVar.zza(zzijVar.zzf());
        zzjbVar.zzc(zzi.zza(this.zzb));
        zzjbVar.zzd(Integer.valueOf(i));
        zzjbVar.zze(Integer.valueOf(i2));
        ImageUtils imageUtils = zzf;
        zzjbVar.zzb(zzld.zza(imageUtils.getMobileVisionImageFormat(inputImage), imageUtils.getMobileVisionImageSize(inputImage)));
        zzjc zzjcVarZzf = zzjbVar.zzf();
        zzis zzisVar = new zzis();
        zzisVar.zzc(Boolean.valueOf(this.zzg));
        zzisVar.zzd(zzjcVarZzf);
        return zzks.zzc(zzisVar);
    }
}
