package com.google.android.gms.internal.gtm;

import com.google.firebase.crashlytics.internal.metadata.UserMetadata;
import com.huawei.hms.ml.language.common.utils.Constant;
import com.reandroid.arsc.value.CompoundEntry;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzsa {
    /* JADX WARN: Multi-variable type inference failed */
    public static zzak zza(zzak zzakVar) {
        zzal zzalVarZzg = zzak.zzg();
        zzalVarZzg.zzt(1);
        zzalVarZzg.zzt(zzakVar.zzO());
        zzalVarZzg.zzi();
        zzalVarZzg.zza(zzakVar.zzq());
        zzalVarZzg.zzo(zzakVar.zzN());
        return (zzak) zzalVarZzg.zzC();
    }

    public static zzrs zzb(zzaa zzaaVar) throws zzrz {
        zzak[] zzakVarArr = new zzak[zzaaVar.zzf()];
        for (int i = 0; i < zzaaVar.zzf(); i++) {
            zze(i, zzaaVar, zzakVarArr, new HashSet(0));
        }
        zzru zzruVar = new zzru(null);
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < zzaaVar.zze(); i2++) {
            arrayList.add(zzf(zzaaVar.zzi(i2), zzaaVar, zzakVarArr, i2));
        }
        ArrayList arrayList2 = new ArrayList();
        for (int i3 = 0; i3 < zzaaVar.zzc(); i3++) {
            arrayList2.add(zzf(zzaaVar.zzh(i3), zzaaVar, zzakVarArr, i3));
        }
        ArrayList arrayList3 = new ArrayList();
        for (int i4 = 0; i4 < zzaaVar.zza(); i4++) {
            zzro zzroVarZzf = zzf(zzaaVar.zzg(i4), zzaaVar, zzakVarArr, i4);
            zzruVar.zzb(zzroVarZzf);
            arrayList3.add(zzroVarZzf);
        }
        for (zzac zzacVar : zzaaVar.zzr()) {
            zzry zzryVar = new zzry(null);
            Iterator<Integer> it = zzacVar.zzh().iterator();
            while (it.hasNext()) {
                zzryVar.zzg((zzro) arrayList2.get(it.next().intValue()));
            }
            Iterator<Integer> it2 = zzacVar.zzg().iterator();
            while (it2.hasNext()) {
                zzryVar.zzf((zzro) arrayList2.get(it2.next().intValue()));
            }
            Iterator<Integer> it3 = zzacVar.zze().iterator();
            while (it3.hasNext()) {
                zzryVar.zzd((zzro) arrayList.get(it3.next().intValue()));
            }
            Iterator<Integer> it4 = zzacVar.zzf().iterator();
            while (it4.hasNext()) {
                zzryVar.zze(zzaaVar.zzn(it4.next().intValue()).zzp());
            }
            Iterator<Integer> it5 = zzacVar.zzk().iterator();
            while (it5.hasNext()) {
                zzryVar.zzj((zzro) arrayList.get(it5.next().intValue()));
            }
            Iterator<Integer> it6 = zzacVar.zzl().iterator();
            while (it6.hasNext()) {
                zzryVar.zzk(zzaaVar.zzn(it6.next().intValue()).zzp());
            }
            Iterator<Integer> it7 = zzacVar.zzc().iterator();
            while (it7.hasNext()) {
                zzryVar.zzb((zzro) arrayList3.get(it7.next().intValue()));
            }
            Iterator<Integer> it8 = zzacVar.zzd().iterator();
            while (it8.hasNext()) {
                zzryVar.zzc(zzaaVar.zzn(it8.next().intValue()).zzp());
            }
            Iterator<Integer> it9 = zzacVar.zzi().iterator();
            while (it9.hasNext()) {
                zzryVar.zzh((zzro) arrayList3.get(it9.next().intValue()));
            }
            Iterator<Integer> it10 = zzacVar.zzj().iterator();
            while (it10.hasNext()) {
                zzryVar.zzi(zzaaVar.zzn(it10.next().intValue()).zzp());
            }
            zzruVar.zzc(zzryVar.zza());
        }
        zzruVar.zze(zzaaVar.zzo());
        zzruVar.zzd(zzaaVar.zzd());
        return zzruVar.zza();
    }

    public static void zzc(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[1024];
        while (true) {
            int i = inputStream.read(bArr);
            if (i == -1) {
                return;
            } else {
                outputStream.write(bArr, 0, i);
            }
        }
    }

    private static zzae zzd(zzak zzakVar) throws zzrz {
        if (!zzakVar.zzW(zzae.zza)) {
            String strValueOf = String.valueOf(zzakVar);
            StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 54);
            sb.append("Expected a ServingValue and didn't get one. Value is: ");
            sb.append(strValueOf);
            zzh(sb.toString());
        }
        return (zzae) zzakVar.zzV(zzae.zza);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static zzak zze(int i, zzaa zzaaVar, zzak[] zzakVarArr, Set<Integer> set) throws zzrz {
        zzal zzalVar;
        Integer numValueOf = Integer.valueOf(i);
        if (set.contains(numValueOf)) {
            String strValueOf = String.valueOf(set);
            StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 90);
            sb.append("Value cycle detected.  Current value reference: ");
            sb.append(i);
            sb.append(".  Previous value references: ");
            sb.append(strValueOf);
            sb.append(".");
            zzh(sb.toString());
        }
        zzal zzalVar2 = (zzal) ((zzak) zzg(zzaaVar.zzs(), i, CompoundEntry.NAME_values)).zzZ();
        zzak zzakVar = zzakVarArr[i];
        if (zzakVar != null) {
            return zzakVar;
        }
        set.add(numValueOf);
        int iZzu = zzalVar2.zzu();
        if (iZzu == 2) {
            zzae zzaeVarZzd = zzd((zzak) zzalVar2.zzC());
            zzal zzalVar3 = (zzal) zza((zzak) zzalVar2.zzC()).zzZ();
            zzalVar3.zzj();
            Iterator<Integer> it = zzaeVarZzd.zzf().iterator();
            while (it.hasNext()) {
                zzalVar3.zze(zze(it.next().intValue(), zzaaVar, zzakVarArr, set));
            }
            zzalVar = zzalVar3;
        } else if (iZzu == 3) {
            zzalVar = (zzal) zza((zzak) zzalVar2.zzC()).zzZ();
            zzae zzaeVarZzd2 = zzd((zzak) zzalVar2.zzC());
            if (zzaeVarZzd2.zzc() != zzaeVarZzd2.zzd()) {
                int iZzc = zzaeVarZzd2.zzc();
                int iZzd = zzaeVarZzd2.zzd();
                StringBuilder sb2 = new StringBuilder(58);
                sb2.append("Uneven map keys (");
                sb2.append(iZzc);
                sb2.append(") and map values (");
                sb2.append(iZzd);
                sb2.append(Constant.AFTER_QUTO);
                zzh(sb2.toString());
            }
            zzalVar.zzk();
            zzalVar.zzl();
            Iterator<Integer> it2 = zzaeVarZzd2.zzg().iterator();
            while (it2.hasNext()) {
                zzalVar.zzf(zze(it2.next().intValue(), zzaaVar, zzakVarArr, set));
            }
            Iterator<Integer> it3 = zzaeVarZzd2.zzh().iterator();
            while (it3.hasNext()) {
                zzalVar.zzg(zze(it3.next().intValue(), zzaaVar, zzakVarArr, set));
            }
        } else if (iZzu == 4) {
            zzalVar = (zzal) zza((zzak) zzalVar2.zzC()).zzZ();
            zzalVar.zzr(com.google.android.gms.tagmanager.zzfv.zzn(com.google.android.gms.tagmanager.zzfv.zzl(zze(zzd((zzak) zzalVar2.zzC()).zza(), zzaaVar, zzakVarArr, set))));
        } else if (iZzu != 7) {
            zzalVar = zzalVar2;
        } else {
            zzalVar = (zzal) zza((zzak) zzalVar2.zzC()).zzZ();
            zzae zzaeVarZzd3 = zzd((zzak) zzalVar2.zzC());
            zzalVar.zzm();
            Iterator<Integer> it4 = zzaeVarZzd3.zzi().iterator();
            while (it4.hasNext()) {
                zzalVar.zzh(zze(it4.next().intValue(), zzaaVar, zzakVarArr, set));
            }
        }
        if (zzalVar == null) {
            String strValueOf2 = String.valueOf(zzalVar2);
            StringBuilder sb3 = new StringBuilder(String.valueOf(strValueOf2).length() + 15);
            sb3.append("Invalid value: ");
            sb3.append(strValueOf2);
            zzh(sb3.toString());
        }
        zzakVarArr[i] = (zzak) zzalVar.zzC();
        set.remove(Integer.valueOf(i));
        return (zzak) zzalVar.zzC();
    }

    private static zzro zzf(zzs zzsVar, zzaa zzaaVar, zzak[] zzakVarArr, int i) throws zzrz {
        zzrq zzrqVar = new zzrq(null);
        Iterator<Integer> it = zzsVar.zzc().iterator();
        while (it.hasNext()) {
            zzy zzyVar = (zzy) zzg(zzaaVar.zzq(), it.next().intValue(), "properties");
            String str = (String) zzg(zzaaVar.zzp(), zzyVar.zza(), UserMetadata.KEYDATA_FILENAME);
            int iZzc = zzyVar.zzc();
            if (iZzc < 0 || iZzc >= zzakVarArr.length) {
                StringBuilder sb = new StringBuilder(51);
                sb.append("Index out of bounds detected: ");
                sb.append(iZzc);
                sb.append(" in values");
                zzh(sb.toString());
            }
            zzak zzakVar = zzakVarArr[iZzc];
            if (zzb.PUSH_AFTER_EVALUATE.toString().equals(str)) {
                zzrqVar.zzc(zzakVar);
            } else {
                zzrqVar.zzb(str, zzakVar);
            }
        }
        return zzrqVar.zza();
    }

    private static <T> T zzg(List<T> list, int i, String str) throws zzrz {
        if (i < 0 || i >= list.size()) {
            StringBuilder sb = new StringBuilder(str.length() + 45);
            sb.append("Index out of bounds detected: ");
            sb.append(i);
            sb.append(" in ");
            sb.append(str);
            zzh(sb.toString());
        }
        return list.get(i);
    }

    private static void zzh(String str) throws zzrz {
        com.google.android.gms.tagmanager.zzdh.zza(str);
        throw new zzrz(str);
    }
}
