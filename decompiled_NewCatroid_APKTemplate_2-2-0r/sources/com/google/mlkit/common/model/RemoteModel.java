package com.google.mlkit.common.model;

import android.text.TextUtils;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.mlkit_common.zzx;
import com.google.android.gms.internal.mlkit_common.zzy;
import com.google.mlkit.common.sdkinternal.ModelType;
import com.google.mlkit.common.sdkinternal.model.BaseModel;
import java.util.EnumMap;
import java.util.Map;

/* JADX INFO: compiled from: com.google.mlkit:common@@17.4.0 */
/* JADX INFO: loaded from: classes10.dex */
public abstract class RemoteModel {
    private final String zzc;
    private final BaseModel zzd;
    private final ModelType zze;
    private String zzf;
    private static final Map<BaseModel, String> zzb = new EnumMap(BaseModel.class);
    public static final Map<BaseModel, String> zza = new EnumMap(BaseModel.class);

    protected RemoteModel(String str, BaseModel baseModel, ModelType modelType) {
        Preconditions.checkArgument(TextUtils.isEmpty(str) == (baseModel != null), "One of cloud model name and base model cannot be empty");
        this.zzc = str;
        this.zzd = baseModel;
        this.zze = modelType;
    }

    public boolean baseModelHashMatches(String str) {
        BaseModel baseModel = this.zzd;
        if (baseModel == null) {
            return false;
        }
        return str.equals(zzb.get(baseModel));
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof RemoteModel)) {
            return false;
        }
        RemoteModel remoteModel = (RemoteModel) obj;
        return Objects.equal(this.zzc, remoteModel.zzc) && Objects.equal(this.zzd, remoteModel.zzd) && Objects.equal(this.zze, remoteModel.zze);
    }

    public String getModelHash() {
        return this.zzf;
    }

    public String getModelName() {
        return this.zzc;
    }

    public String getModelNameForBackend() {
        String str = this.zzc;
        return str != null ? str : zza.get(this.zzd);
    }

    public ModelType getModelType() {
        return this.zze;
    }

    public String getUniqueModelNameForPersist() {
        String str = this.zzc;
        if (str != null) {
            return str;
        }
        String strValueOf = String.valueOf(zza.get(this.zzd));
        return strValueOf.length() != 0 ? "COM.GOOGLE.BASE_".concat(strValueOf) : new String("COM.GOOGLE.BASE_");
    }

    public int hashCode() {
        return Objects.hashCode(this.zzc, this.zzd, this.zze);
    }

    public boolean isBaseModel() {
        return this.zzd != null;
    }

    public void setModelHash(String str) {
        this.zzf = str;
    }

    public String toString() {
        zzx zzxVarZzb = zzy.zzb("RemoteModel");
        zzxVarZzb.zza("modelName", this.zzc);
        zzxVarZzb.zza("baseModel", this.zzd);
        zzxVarZzb.zza("modelType", this.zze);
        return zzxVarZzb.toString();
    }
}
