package com.google.mlkit.vision.text.internal;

import android.graphics.Point;
import com.google.android.gms.internal.mlkit_vision_text.zzab;
import com.google.android.gms.internal.mlkit_vision_text.zzr;
import com.google.android.gms.internal.mlkit_vision_text.zzu;
import com.google.mlkit.nl.languageid.LanguageIdentifier;
import com.google.mlkit.vision.text.Text;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes10.dex */
final /* synthetic */ class zzg implements zzu {
    static final zzu zza = new zzg();

    private zzg() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzu
    public final Object zza(Object obj) {
        zzr zzrVar = (zzr) obj;
        List<Point> listZzb = zza.zzb(zzrVar.zzb);
        return new Text.Element(zzab.zza(zzrVar.zzd) ? "" : zzrVar.zzd, zza.zza(listZzb), listZzb, zzab.zza(zzrVar.zzf) ? LanguageIdentifier.UNDETERMINED_LANGUAGE_TAG : zzrVar.zzf);
    }
}
