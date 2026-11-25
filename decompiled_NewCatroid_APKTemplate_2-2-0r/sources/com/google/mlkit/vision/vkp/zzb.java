package com.google.mlkit.vision.vkp;

import com.gaurav.avnc.vnc.XKeySym;
import com.google.mlkit.common.model.LocalModel;
import org.eclipse.jdt.internal.compiler.parser.ParserBasicInformation;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes10.dex */
final class zzb extends VkpObjectDetectorOptions {
    private final boolean zza;
    private final boolean zzb;
    private final boolean zzc;
    private final boolean zzd;
    private final float zze;
    private final int zzf;
    private final LocalModel zzg;
    private final String zzh;
    private final String zzi;

    zzb(boolean z, boolean z2, boolean z3, boolean z4, float f, int i, LocalModel localModel, String str, String str2) {
        this.zza = z;
        this.zzb = z2;
        this.zzc = z3;
        this.zzd = z4;
        this.zze = f;
        this.zzf = i;
        this.zzg = localModel;
        this.zzh = str;
        if (str2 == null) {
            throw new NullPointerException("Null clientLibraryVersion");
        }
        this.zzi = str2;
    }

    public final boolean equals(Object obj) {
        LocalModel localModel;
        if (obj == this) {
            return true;
        }
        if (obj instanceof VkpObjectDetectorOptions) {
            VkpObjectDetectorOptions vkpObjectDetectorOptions = (VkpObjectDetectorOptions) obj;
            if (this.zza == vkpObjectDetectorOptions.zzi() && this.zzb == vkpObjectDetectorOptions.zzh() && this.zzc == vkpObjectDetectorOptions.zzg() && this.zzd == vkpObjectDetectorOptions.zzf() && Float.floatToIntBits(this.zze) == Float.floatToIntBits(vkpObjectDetectorOptions.zza()) && this.zzf == vkpObjectDetectorOptions.zzb() && ((localModel = this.zzg) != null ? localModel.equals(vkpObjectDetectorOptions.zzc()) : vkpObjectDetectorOptions.zzc() == null) && this.zzh.equals(vkpObjectDetectorOptions.zzd()) && this.zzi.equals(vkpObjectDetectorOptions.zze())) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        boolean z = this.zza;
        int i = XKeySym.XK_kana_YU;
        int i2 = ((((((true != z ? 1237 : 1231) ^ 1000003) * 1000003) ^ (true != this.zzb ? 1237 : 1231)) * 1000003) ^ (true != this.zzc ? 1237 : 1231)) * 1000003;
        if (true == this.zzd) {
            i = 1231;
        }
        int iFloatToIntBits = (((((i2 ^ i) * 1000003) ^ Float.floatToIntBits(this.zze)) * 1000003) ^ this.zzf) * 1000003;
        LocalModel localModel = this.zzg;
        return ((((iFloatToIntBits ^ (localModel == null ? 0 : localModel.hashCode())) * 1000003) ^ this.zzh.hashCode()) * 1000003) ^ this.zzi.hashCode();
    }

    public final String toString() {
        boolean z = this.zza;
        boolean z2 = this.zzb;
        boolean z3 = this.zzc;
        boolean z4 = this.zzd;
        float f = this.zze;
        int i = this.zzf;
        String strValueOf = String.valueOf(this.zzg);
        String str = this.zzh;
        String str2 = this.zzi;
        int length = String.valueOf(strValueOf).length();
        int length2 = str.length();
        StringBuilder sb = new StringBuilder(length + ParserBasicInformation.SCOPE_UBOUND + length2 + str2.length());
        sb.append("VkpObjectDetectorOptions{streamingMode=");
        sb.append(z);
        sb.append(", multipleObjectsEnabled=");
        sb.append(z2);
        sb.append(", classificationEnabled=");
        sb.append(z3);
        sb.append(", accelerationEnabled=");
        sb.append(z4);
        sb.append(", classificationConfidenceThreshold=");
        sb.append(f);
        sb.append(", maxPerObjectLabelCount=");
        sb.append(i);
        sb.append(", customClassifierLocalModel=");
        sb.append(strValueOf);
        sb.append(", clientLibraryName=");
        sb.append(str);
        sb.append(", clientLibraryVersion=");
        sb.append(str2);
        sb.append("}");
        return sb.toString();
    }

    @Override // com.google.mlkit.vision.vkp.VkpObjectDetectorOptions
    final float zza() {
        return this.zze;
    }

    @Override // com.google.mlkit.vision.vkp.VkpObjectDetectorOptions
    final int zzb() {
        return this.zzf;
    }

    @Override // com.google.mlkit.vision.vkp.VkpObjectDetectorOptions
    final LocalModel zzc() {
        return this.zzg;
    }

    @Override // com.google.mlkit.vision.vkp.VkpObjectDetectorOptions
    final String zzd() {
        return this.zzh;
    }

    @Override // com.google.mlkit.vision.vkp.VkpObjectDetectorOptions
    final String zze() {
        return this.zzi;
    }

    @Override // com.google.mlkit.vision.vkp.VkpObjectDetectorOptions
    final boolean zzf() {
        return this.zzd;
    }

    @Override // com.google.mlkit.vision.vkp.VkpObjectDetectorOptions
    final boolean zzg() {
        return this.zzc;
    }

    @Override // com.google.mlkit.vision.vkp.VkpObjectDetectorOptions
    final boolean zzh() {
        return this.zzb;
    }

    @Override // com.google.mlkit.vision.vkp.VkpObjectDetectorOptions
    final boolean zzi() {
        return this.zza;
    }
}
