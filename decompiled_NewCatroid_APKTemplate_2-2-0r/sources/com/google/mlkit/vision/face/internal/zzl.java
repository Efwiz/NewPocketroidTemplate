package com.google.mlkit.vision.face.internal;

import android.content.Context;
import android.media.Image;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.internal.mlkit_vision_face.zzm;
import com.google.android.gms.internal.mlkit_vision_face.zzp;
import com.google.mlkit.common.MlKitException;
import com.google.mlkit.common.sdkinternal.OptionalModuleUtils;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.common.internal.CommonConvertUtils;
import com.google.mlkit.vision.common.internal.ImageConvertUtils;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetectorOptions;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes10.dex */
final class zzl implements zzb {
    private boolean zza;
    private final Context zzb;
    private final FaceDetectorOptions zzc;
    private final int zzd;
    private com.google.android.gms.internal.mlkit_vision_face.zzj zze;
    private com.google.android.gms.internal.mlkit_vision_face.zzj zzf;

    zzl(Context context, FaceDetectorOptions faceDetectorOptions) {
        this.zzb = context;
        this.zzc = faceDetectorOptions;
        this.zzd = GoogleApiAvailabilityLight.getInstance().getApkVersion(context);
    }

    static int zzd(int i) {
        if (i == 1) {
            return 0;
        }
        if (i == 2) {
            return 1;
        }
        StringBuilder sb = new StringBuilder(34);
        sb.append("Invalid landmark type: ");
        sb.append(i);
        throw new IllegalArgumentException(sb.toString());
    }

    static int zze(int i) {
        if (i == 1) {
            return 0;
        }
        if (i == 2) {
            return 1;
        }
        StringBuilder sb = new StringBuilder(40);
        sb.append("Invalid classification type: ");
        sb.append(i);
        throw new IllegalArgumentException(sb.toString());
    }

    private static int zzf(int i) {
        if (i == 1) {
            return 0;
        }
        if (i == 2) {
            return 1;
        }
        StringBuilder sb = new StringBuilder(30);
        sb.append("Invalid mode type: ");
        sb.append(i);
        throw new IllegalArgumentException(sb.toString());
    }

    private final List<Face> zzg(com.google.android.gms.internal.mlkit_vision_face.zzj zzjVar, InputImage inputImage) throws MlKitException {
        com.google.android.gms.internal.mlkit_vision_face.zzf[] zzfVarArrZzd;
        try {
            zzp zzpVar = new zzp(inputImage.getWidth(), inputImage.getHeight(), 0, SystemClock.elapsedRealtime(), CommonConvertUtils.convertToMVRotation(inputImage.getRotationDegrees()));
            if (inputImage.getFormat() != 35 || this.zzd < 201500000) {
                zzfVarArrZzd = zzjVar.zzd(ObjectWrapper.wrap(ImageConvertUtils.getInstance().convertToNv21Buffer(inputImage, false)), zzpVar);
            } else {
                Image.Plane[] planeArr = (Image.Plane[]) Preconditions.checkNotNull(inputImage.getPlanes());
                zzfVarArrZzd = zzjVar.zze(ObjectWrapper.wrap(planeArr[0].getBuffer()), ObjectWrapper.wrap(planeArr[1].getBuffer()), ObjectWrapper.wrap(planeArr[2].getBuffer()), planeArr[0].getPixelStride(), planeArr[1].getPixelStride(), planeArr[2].getPixelStride(), planeArr[0].getRowStride(), planeArr[1].getRowStride(), planeArr[2].getRowStride(), zzpVar);
            }
            ArrayList arrayList = new ArrayList();
            for (com.google.android.gms.internal.mlkit_vision_face.zzf zzfVar : zzfVarArrZzd) {
                arrayList.add(new Face(zzfVar));
            }
            return arrayList;
        } catch (RemoteException e) {
            throw new MlKitException("Failed to detect with legacy face detector", 13, e);
        }
    }

    @Override // com.google.mlkit.vision.face.internal.zzb
    public final boolean zza() throws MlKitException {
        if (this.zze != null || this.zzf != null) {
            return false;
        }
        try {
            zzm zzmVarZza = com.google.android.gms.internal.mlkit_vision_face.zzl.zza(DynamiteModule.load(this.zzb, DynamiteModule.PREFER_REMOTE, OptionalModuleUtils.DEPRECATED_DYNAMITE_MODULE_ID).instantiate("com.google.android.gms.vision.face.ChimeraNativeFaceDetectorCreator"));
            IObjectWrapper iObjectWrapperWrap = ObjectWrapper.wrap(this.zzb);
            if (this.zzc.zzb() == 2) {
                if (this.zzf == null) {
                    this.zzf = zzmVarZza.zzd(iObjectWrapperWrap, new com.google.android.gms.internal.mlkit_vision_face.zzh(2, 2, 0, true, false, this.zzc.zzf()));
                }
                if ((this.zzc.zza() == 2 || this.zzc.zzc() == 2 || this.zzc.zzd() == 2) && this.zze == null) {
                    this.zze = zzmVarZza.zzd(iObjectWrapperWrap, new com.google.android.gms.internal.mlkit_vision_face.zzh(zzf(this.zzc.zzd()), zzd(this.zzc.zza()), zze(this.zzc.zzc()), false, this.zzc.zze(), this.zzc.zzf()));
                }
            } else if (this.zze == null) {
                this.zze = zzmVarZza.zzd(iObjectWrapperWrap, new com.google.android.gms.internal.mlkit_vision_face.zzh(zzf(this.zzc.zzd()), zzd(this.zzc.zza()), zze(this.zzc.zzc()), false, this.zzc.zze(), this.zzc.zzf()));
            }
            if (this.zze == null && this.zzf == null && !this.zza) {
                Log.d("LegacyFaceDelegate", "Request face optional module download.");
                OptionalModuleUtils.requestDownload(this.zzb, OptionalModuleUtils.BARCODE);
                this.zza = true;
            }
            return false;
        } catch (RemoteException e) {
            throw new MlKitException("Failed to create legacy face detector.", 13, e);
        } catch (DynamiteModule.LoadingException e2) {
            throw new MlKitException("Failed to load deprecated vision dynamite module.", 13, e2);
        }
    }

    @Override // com.google.mlkit.vision.face.internal.zzb
    public final Pair<List<Face>, List<Face>> zzb(InputImage inputImage) throws MlKitException {
        List<Face> listZzg;
        if (this.zze == null && this.zzf == null) {
            zza();
        }
        com.google.android.gms.internal.mlkit_vision_face.zzj zzjVar = this.zze;
        if (zzjVar == null && this.zzf == null) {
            throw new MlKitException("Waiting for the face detection module to be downloaded. Please wait.", 14);
        }
        List<Face> listZzg2 = null;
        if (zzjVar != null) {
            listZzg = zzg(zzjVar, inputImage);
            if (!this.zzc.zze()) {
                zzh.zzd(listZzg);
            }
        } else {
            listZzg = null;
        }
        com.google.android.gms.internal.mlkit_vision_face.zzj zzjVar2 = this.zzf;
        if (zzjVar2 != null) {
            listZzg2 = zzg(zzjVar2, inputImage);
            zzh.zzd(listZzg2);
        }
        return new Pair<>(listZzg, listZzg2);
    }

    @Override // com.google.mlkit.vision.face.internal.zzb
    public final void zzc() {
        com.google.android.gms.internal.mlkit_vision_face.zzj zzjVar = this.zze;
        if (zzjVar != null) {
            try {
                zzjVar.zzf();
            } catch (RemoteException e) {
                Log.e("LegacyFaceDelegate", "Failed to release legacy face detector.", e);
            }
            this.zze = null;
        }
        com.google.android.gms.internal.mlkit_vision_face.zzj zzjVar2 = this.zzf;
        if (zzjVar2 != null) {
            try {
                zzjVar2.zzf();
            } catch (RemoteException e2) {
                Log.e("LegacyFaceDelegate", "Failed to release legacy face detector.", e2);
            }
            this.zzf = null;
        }
    }
}
