package com.google.mlkit.vision.face.internal;

import android.content.Context;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.internal.mlkit_vision_face.zzlg;
import com.google.android.gms.internal.mlkit_vision_face.zzlk;
import com.google.android.gms.internal.mlkit_vision_face.zzlm;
import com.google.android.gms.internal.mlkit_vision_face.zzlo;
import com.google.android.gms.internal.mlkit_vision_face.zzlq;
import com.google.mlkit.common.MlKitException;
import com.google.mlkit.common.sdkinternal.OptionalModuleUtils;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.common.internal.CommonConvertUtils;
import com.google.mlkit.vision.common.internal.ImageConvertUtils;
import com.google.mlkit.vision.common.internal.ImageUtils;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetectorOptions;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes10.dex */
final class zza implements zzb {
    private final Context zza;
    private final FaceDetectorOptions zzb;
    private boolean zzc;
    private boolean zzd;
    private zzlo zze;
    private zzlo zzf;

    zza(Context context, FaceDetectorOptions faceDetectorOptions) {
        this.zza = context;
        this.zzb = faceDetectorOptions;
    }

    static boolean zzd(Context context) {
        return DynamiteModule.getLocalVersion(context, "com.google.android.gms.vision.dynamite.face") > 0;
    }

    private final void zzf() throws RemoteException, DynamiteModule.LoadingException {
        if (this.zzb.zzb() != 2) {
            if (this.zzf == null) {
                zzlo zzloVarZzg = zzg(new zzlk(this.zzb.zzd(), this.zzb.zza(), this.zzb.zzc(), 1, this.zzb.zze(), this.zzb.zzf()));
                this.zzf = zzloVarZzg;
                zzloVarZzg.zzd();
                return;
            }
            return;
        }
        if (this.zze == null) {
            zzlo zzloVarZzg2 = zzg(new zzlk(this.zzb.zzd(), 1, 1, 2, false, this.zzb.zzf()));
            this.zze = zzloVarZzg2;
            zzloVarZzg2.zzd();
        }
        if ((this.zzb.zza() == 2 || this.zzb.zzc() == 2 || this.zzb.zzd() == 2) && this.zzf == null) {
            zzlo zzloVarZzg3 = zzg(new zzlk(this.zzb.zzd(), this.zzb.zza(), this.zzb.zzc(), 1, this.zzb.zze(), this.zzb.zzf()));
            this.zzf = zzloVarZzg3;
            zzloVarZzg3.zzd();
        }
    }

    private final zzlo zzg(zzlk zzlkVar) throws RemoteException, DynamiteModule.LoadingException {
        return this.zzc ? zze(DynamiteModule.PREFER_LOCAL, "com.google.android.gms.vision.dynamite.face", "com.google.android.gms.vision.face.mlkit.FaceDetectorCreator", zzlkVar) : zze(DynamiteModule.PREFER_REMOTE, "com.google.android.gms.vision.face", "com.google.android.gms.vision.face.mlkit.FaceDetectorCreator", zzlkVar);
    }

    private static List<Face> zzh(zzlo zzloVar, InputImage inputImage) throws MlKitException {
        if (inputImage.getFormat() == -1) {
            inputImage = InputImage.fromByteBuffer(ImageConvertUtils.getInstance().convertToNv21Buffer(inputImage, false), inputImage.getWidth(), inputImage.getHeight(), CommonConvertUtils.convertToMVRotation(inputImage.getRotationDegrees()), 17);
        }
        try {
            List<zzlm> listZzf = zzloVar.zzf(ImageUtils.getInstance().getImageDataWrapper(inputImage), new zzlg(inputImage.getFormat(), inputImage.getWidth(), inputImage.getHeight(), CommonConvertUtils.convertToMVRotation(inputImage.getRotationDegrees()), SystemClock.elapsedRealtime()));
            ArrayList arrayList = new ArrayList();
            Iterator<zzlm> it = listZzf.iterator();
            while (it.hasNext()) {
                arrayList.add(new Face(it.next()));
            }
            return arrayList;
        } catch (RemoteException e) {
            throw new MlKitException("Failed to run face detector.", 13, e);
        }
    }

    @Override // com.google.mlkit.vision.face.internal.zzb
    public final boolean zza() throws MlKitException {
        if (this.zzf != null || this.zze != null) {
            return this.zzc;
        }
        if (DynamiteModule.getLocalVersion(this.zza, "com.google.android.gms.vision.dynamite.face") > 0) {
            this.zzc = true;
            try {
                zzf();
            } catch (RemoteException e) {
                throw new MlKitException("Failed to init thick face detector.", 13, e);
            } catch (DynamiteModule.LoadingException e2) {
                throw new MlKitException("Failed to load the bundled face module.", 14, e2);
            }
        } else {
            this.zzc = false;
            try {
                zzf();
            } catch (RemoteException e3) {
                throw new MlKitException("Failed to init thin face detector.", 13, e3);
            } catch (DynamiteModule.LoadingException unused) {
                if (!this.zzd) {
                    OptionalModuleUtils.requestDownload(this.zza, OptionalModuleUtils.FACE);
                    this.zzd = true;
                }
            }
        }
        return this.zzc;
    }

    @Override // com.google.mlkit.vision.face.internal.zzb
    public final Pair<List<Face>, List<Face>> zzb(InputImage inputImage) throws MlKitException {
        List<Face> listZzh;
        zza();
        zzlo zzloVar = this.zzf;
        if (zzloVar == null && this.zze == null) {
            throw new MlKitException("Waiting for the face detection module to be downloaded. Please wait.", 14);
        }
        List<Face> listZzh2 = null;
        if (zzloVar != null) {
            listZzh = zzh(zzloVar, inputImage);
            if (!this.zzb.zze()) {
                zzh.zzd(listZzh);
            }
        } else {
            listZzh = null;
        }
        zzlo zzloVar2 = this.zze;
        if (zzloVar2 != null) {
            listZzh2 = zzh(zzloVar2, inputImage);
            zzh.zzd(listZzh2);
        }
        return new Pair<>(listZzh, listZzh2);
    }

    @Override // com.google.mlkit.vision.face.internal.zzb
    public final void zzc() {
        try {
            zzlo zzloVar = this.zzf;
            if (zzloVar != null) {
                zzloVar.zze();
                this.zzf = null;
            }
            zzlo zzloVar2 = this.zze;
            if (zzloVar2 != null) {
                zzloVar2.zze();
                this.zze = null;
            }
        } catch (RemoteException e) {
            Log.e("DecoupledFaceDelegate", "Failed to release face detector.", e);
        }
    }

    final zzlo zze(DynamiteModule.VersionPolicy versionPolicy, String str, String str2, zzlk zzlkVar) throws RemoteException, DynamiteModule.LoadingException {
        return zzlq.zza(DynamiteModule.load(this.zza, versionPolicy, str).instantiate("com.google.android.gms.vision.face.mlkit.FaceDetectorCreator")).zzd(ObjectWrapper.wrap(this.zza), zzlkVar);
    }
}
