package com.google.mlkit.vision.text.internal;

import android.graphics.Point;
import com.google.android.gms.internal.mlkit_vision_text.zzab;
import com.google.android.gms.internal.mlkit_vision_text.zzbu;
import com.google.android.gms.internal.mlkit_vision_text.zzu;
import com.google.mlkit.nl.languageid.LanguageIdentifier;
import com.google.mlkit.vision.text.Text;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes10.dex */
final /* synthetic */ class zze implements zzu {
    static final zzu zza = new zze();

    private zze() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzu
    public final Object zza(Object obj) {
        com.google.android.gms.internal.mlkit_vision_text.zzl zzlVar = (com.google.android.gms.internal.mlkit_vision_text.zzl) obj;
        List<Point> listZzb = zza.zzb(zzlVar.zzb);
        return new Text.Line(zzab.zza(zzlVar.zze) ? "" : zzlVar.zze, zza.zza(listZzb), listZzb, zzab.zza(zzlVar.zzg) ? LanguageIdentifier.UNDETERMINED_LANGUAGE_TAG : zzlVar.zzg, zzbu.zza(Arrays.asList(zzlVar.zza), zzg.zza));
    }
}
