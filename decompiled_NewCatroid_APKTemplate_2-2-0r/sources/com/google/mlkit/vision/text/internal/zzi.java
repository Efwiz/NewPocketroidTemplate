package com.google.mlkit.vision.text.internal;

import android.graphics.Point;
import android.util.SparseArray;
import com.google.android.gms.internal.mlkit_vision_text.zzab;
import com.google.android.gms.internal.mlkit_vision_text.zzbi;
import com.google.android.gms.internal.mlkit_vision_text.zzbl;
import com.google.android.gms.internal.mlkit_vision_text.zzbu;
import com.google.android.gms.internal.mlkit_vision_text.zzcn;
import com.google.android.gms.internal.mlkit_vision_text.zzv;
import com.google.mlkit.nl.languageid.LanguageIdentifier;
import com.google.mlkit.vision.text.Text;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes10.dex */
final class zzi {
    static final zzv zza = zzv.zza("\n");
    private static final Comparator<Map.Entry<String, Integer>> zzb = zzh.zza;

    /* JADX WARN: Multi-variable type inference failed */
    static Text zza(com.google.android.gms.internal.mlkit_vision_text.zzl[] zzlVarArr) {
        SparseArray sparseArray = new SparseArray();
        int i = 0;
        for (com.google.android.gms.internal.mlkit_vision_text.zzl zzlVar : zzlVarArr) {
            SparseArray sparseArray2 = (SparseArray) sparseArray.get(zzlVar.zzj);
            if (sparseArray2 == null) {
                sparseArray2 = new SparseArray();
                sparseArray.append(zzlVar.zzj, sparseArray2);
            }
            sparseArray2.append(zzlVar.zzk, zzlVar);
        }
        zzbi zzbiVarZzk = zzbl.zzk();
        int i2 = 0;
        while (i2 < sparseArray.size()) {
            SparseArray sparseArray3 = (SparseArray) sparseArray.valueAt(i2);
            zzbi zzbiVarZzk2 = zzbl.zzk();
            for (int i3 = i; i3 < sparseArray3.size(); i3++) {
                zzbiVarZzk2.zzb((com.google.android.gms.internal.mlkit_vision_text.zzl) sparseArray3.valueAt(i3));
            }
            zzbl zzblVarZzc = zzbiVarZzk2.zzc();
            List listZza = zzbu.zza(zzblVarZzc, zze.zza);
            com.google.android.gms.internal.mlkit_vision_text.zzf zzfVar = ((com.google.android.gms.internal.mlkit_vision_text.zzl) zzblVarZzc.get(i)).zzb;
            zzcn zzcnVarListIterator = zzblVarZzc.listIterator(i);
            int iMax = Integer.MIN_VALUE;
            int iMin = Integer.MAX_VALUE;
            int iMin2 = Integer.MAX_VALUE;
            int iMax2 = Integer.MIN_VALUE;
            while (zzcnVarListIterator.hasNext()) {
                com.google.android.gms.internal.mlkit_vision_text.zzf zzfVar2 = ((com.google.android.gms.internal.mlkit_vision_text.zzl) zzcnVarListIterator.next()).zzb;
                int i4 = zzfVar.zza;
                int i5 = zzfVar.zzb;
                zzcn zzcnVar = zzcnVarListIterator;
                double dSin = Math.sin(Math.toRadians(zzfVar.zze));
                double dCos = Math.cos(Math.toRadians(zzfVar.zze));
                SparseArray sparseArray4 = sparseArray;
                int i6 = i2;
                zzbi zzbiVar = zzbiVarZzk;
                List list = listZza;
                Point point = new Point(zzfVar2.zza, zzfVar2.zzb);
                point.offset(-i4, -i5);
                int i7 = (int) ((((double) pointArr[0].x) * dCos) + (((double) pointArr[0].y) * dSin));
                int i8 = (int) ((((double) (-pointArr[0].x)) * dSin) + (((double) pointArr[0].y) * dCos));
                pointArr[0].x = i7;
                pointArr[0].y = i8;
                Point[] pointArr = {point, new Point(zzfVar2.zzc + i7, i8), new Point(zzfVar2.zzc + i7, zzfVar2.zzd + i8), new Point(i7, i8 + zzfVar2.zzd)};
                for (int i9 = 0; i9 < 4; i9++) {
                    Point point2 = pointArr[i9];
                    iMin = Math.min(iMin, point2.x);
                    iMax = Math.max(iMax, point2.x);
                    iMin2 = Math.min(iMin2, point2.y);
                    iMax2 = Math.max(iMax2, point2.y);
                }
                zzcnVarListIterator = zzcnVar;
                sparseArray = sparseArray4;
                i2 = i6;
                zzbiVarZzk = zzbiVar;
                listZza = list;
            }
            zzbi zzbiVar2 = zzbiVarZzk;
            SparseArray sparseArray5 = sparseArray;
            int i10 = i2;
            List list2 = listZza;
            int i11 = zzfVar.zza;
            int i12 = zzfVar.zzb;
            double dSin2 = Math.sin(Math.toRadians(zzfVar.zze));
            double dCos2 = Math.cos(Math.toRadians(zzfVar.zze));
            Point[] pointArr2 = {new Point(iMin, iMin2), new Point(iMax, iMin2), new Point(iMax, iMax2), new Point(iMin, iMax2)};
            int i13 = 0;
            for (int i14 = 4; i13 < i14; i14 = 4) {
                int i15 = pointArr2[i13].x;
                int i16 = pointArr2[i13].y;
                int i17 = pointArr2[i13].x;
                int i18 = pointArr2[i13].y;
                pointArr2[i13].x = (int) ((((double) i15) * dCos2) - (((double) i16) * dSin2));
                pointArr2[i13].y = (int) ((((double) i17) * dSin2) + (((double) i18) * dCos2));
                pointArr2[i13].offset(i11, i12);
                i13++;
            }
            List listAsList = Arrays.asList(pointArr2);
            zzbiVar2.zzb(new Text.TextBlock(zza.zzb(zzbu.zza(list2, zzf.zza)), zza.zza(listAsList), listAsList, zzb(list2), list2));
            i2 = i10 + 1;
            zzbiVarZzk = zzbiVar2;
            sparseArray = sparseArray5;
            i = 0;
        }
        zzbl zzblVarZzc2 = zzbiVarZzk.zzc();
        return new Text(zza.zzb(zzbu.zza(zzblVarZzc2, zzd.zza)), zzblVarZzc2);
    }

    private static String zzb(List<Text.Line> list) {
        HashMap map = new HashMap();
        Iterator<Text.Line> it = list.iterator();
        while (it.hasNext()) {
            String recognizedLanguage = it.next().getRecognizedLanguage();
            map.put(recognizedLanguage, Integer.valueOf((map.containsKey(recognizedLanguage) ? ((Integer) map.get(recognizedLanguage)).intValue() : 0) + 1));
        }
        Set setEntrySet = map.entrySet();
        if (setEntrySet.isEmpty()) {
            return LanguageIdentifier.UNDETERMINED_LANGUAGE_TAG;
        }
        String str = (String) ((Map.Entry) Collections.max(setEntrySet, zzb)).getKey();
        return zzab.zza(str) ? LanguageIdentifier.UNDETERMINED_LANGUAGE_TAG : str;
    }
}
