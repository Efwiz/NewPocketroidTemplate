package com.google.mlkit.vision.text.internal;

import android.content.Context;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.internal.mlkit_vision_text.zzle;
import com.google.android.gms.internal.mlkit_vision_text.zzlg;
import com.google.android.gms.internal.mlkit_vision_text.zzli;
import com.google.mlkit.common.MlKitException;
import com.google.mlkit.common.sdkinternal.OptionalModuleUtils;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.common.internal.CommonConvertUtils;
import com.google.mlkit.vision.common.internal.ImageUtils;
import com.google.mlkit.vision.text.Text;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes10.dex */
final class zzb implements zzj {
    private final Context zza;
    private boolean zzb;
    private zzlg zzc;

    zzb(Context context) {
        this.zza = context;
    }

    @Override // com.google.mlkit.vision.text.internal.zzj
    public final void zza() throws MlKitException {
        if (this.zzc == null) {
            try {
                zzlg zzlgVarZzd = zzli.zza(DynamiteModule.load(this.zza, DynamiteModule.PREFER_REMOTE, "com.google.android.gms.vision.ocr").instantiate("com.google.android.gms.vision.text.mlkit.TextRecognizerCreator")).zzd(ObjectWrapper.wrap(this.zza));
                this.zzc = zzlgVarZzd;
                zzlgVarZzd.zzd();
            } catch (RemoteException e) {
                throw new MlKitException("Failed to init thin text recognizer.", 13, e);
            } catch (DynamiteModule.LoadingException unused) {
                if (this.zzb) {
                    return;
                }
                OptionalModuleUtils.requestDownload(this.zza, OptionalModuleUtils.OCR);
                this.zzb = true;
            }
        }
    }

    @Override // com.google.mlkit.vision.text.internal.zzj
    public final Text zzb(InputImage inputImage) throws MlKitException {
        if (this.zzc == null) {
            zza();
        }
        if (this.zzc == null) {
            throw new MlKitException("Waiting for the OCR optional module to be downloaded. Please wait.", 14);
        }
        zzle zzleVar = new zzle(inputImage.getFormat(), inputImage.getWidth(), inputImage.getHeight(), CommonConvertUtils.convertToMVRotation(inputImage.getRotationDegrees()), SystemClock.elapsedRealtime());
        try {
            return new Text(((zzlg) Preconditions.checkNotNull(this.zzc)).zzf(ImageUtils.getInstance().getImageDataWrapper(inputImage), zzleVar));
        } catch (RemoteException e) {
            throw new MlKitException("Failed to run thin text recognizer.", 13, e);
        }
    }

    @Override // com.google.mlkit.vision.text.internal.zzj
    public final void zzc() {
        zzlg zzlgVar = this.zzc;
        if (zzlgVar != null) {
            try {
                zzlgVar.zze();
            } catch (RemoteException e) {
                Log.e("DecoupledTextDelegate", "Failed to release thin text recognizer.", e);
            }
            this.zzc = null;
        }
    }
}
