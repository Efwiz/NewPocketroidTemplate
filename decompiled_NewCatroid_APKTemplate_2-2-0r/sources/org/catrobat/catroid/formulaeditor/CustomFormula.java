package org.catrobat.catroid.formulaeditor;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import name.antonsmirnov.firmata.FormatHelper;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/CustomFormula.class
  classes14.dex
 */
/* JADX INFO: compiled from: CustomFormula.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001BI\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\b\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\b\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\u0006\u0010\f\u001a\u00020\u0003¢\u0006\u0002\u0010\rJ\t\u0010\u0018\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0019\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001a\u001a\u00020\u0006HÆ\u0003J\u000f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00030\bHÆ\u0003J\u000f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\n0\bHÆ\u0003J\t\u0010\u001d\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001e\u001a\u00020\u0003HÆ\u0003J[\u0010\u001f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\b2\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\b2\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\u0003HÆ\u0001J\u0013\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010#\u001a\u00020\u0006HÖ\u0001J\t\u0010$\u001a\u00020\u0003HÖ\u0001R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\b¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\b¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u000b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0012R\u0011\u0010\f\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0012R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0012¨\u0006%"}, d2 = {"Lorg/catrobat/catroid/formulaeditor/CustomFormula;", "", "uniqueName", "", "displayName", "paramCount", "", "defaultParamValues", "", "defaultParamTypes", "Lorg/catrobat/catroid/formulaeditor/InternTokenType;", "lunoFunctionName", "ownerLibraryId", "(Ljava/lang/String;Ljava/lang/String;ILjava/util/List;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;)V", "getDefaultParamTypes", "()Ljava/util/List;", "getDefaultParamValues", "getDisplayName", "()Ljava/lang/String;", "getLunoFunctionName", "getOwnerLibraryId", "getParamCount", "()I", "getUniqueName", "component1", "component2", "component3", "component4", "component5", "component6", "component7", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "toString", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class CustomFormula {
    private final List<InternTokenType> defaultParamTypes;
    private final List<String> defaultParamValues;
    private final String displayName;
    private final String lunoFunctionName;
    private final String ownerLibraryId;
    private final int paramCount;
    private final String uniqueName;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ CustomFormula copy$default(CustomFormula customFormula, String str, String str2, int i, List list, List list2, String str3, String str4, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = customFormula.uniqueName;
        }
        if ((i2 & 2) != 0) {
            str2 = customFormula.displayName;
        }
        String str5 = str2;
        if ((i2 & 4) != 0) {
            i = customFormula.paramCount;
        }
        int i3 = i;
        if ((i2 & 8) != 0) {
            list = customFormula.defaultParamValues;
        }
        List list3 = list;
        if ((i2 & 16) != 0) {
            list2 = customFormula.defaultParamTypes;
        }
        List list4 = list2;
        if ((i2 & 32) != 0) {
            str3 = customFormula.lunoFunctionName;
        }
        String str6 = str3;
        if ((i2 & 64) != 0) {
            str4 = customFormula.ownerLibraryId;
        }
        return customFormula.copy(str, str5, i3, list3, list4, str6, str4);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getUniqueName() {
        return this.uniqueName;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getDisplayName() {
        return this.displayName;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final int getParamCount() {
        return this.paramCount;
    }

    public final List<String> component4() {
        return this.defaultParamValues;
    }

    public final List<InternTokenType> component5() {
        return this.defaultParamTypes;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final String getLunoFunctionName() {
        return this.lunoFunctionName;
    }

    /* JADX INFO: renamed from: component7, reason: from getter */
    public final String getOwnerLibraryId() {
        return this.ownerLibraryId;
    }

    public final CustomFormula copy(String uniqueName, String displayName, int paramCount, List<String> defaultParamValues, List<? extends InternTokenType> defaultParamTypes, String lunoFunctionName, String ownerLibraryId) {
        Intrinsics.checkNotNullParameter(uniqueName, "uniqueName");
        Intrinsics.checkNotNullParameter(displayName, "displayName");
        Intrinsics.checkNotNullParameter(defaultParamValues, "defaultParamValues");
        Intrinsics.checkNotNullParameter(defaultParamTypes, "defaultParamTypes");
        Intrinsics.checkNotNullParameter(lunoFunctionName, "lunoFunctionName");
        Intrinsics.checkNotNullParameter(ownerLibraryId, "ownerLibraryId");
        return new CustomFormula(uniqueName, displayName, paramCount, defaultParamValues, defaultParamTypes, lunoFunctionName, ownerLibraryId);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CustomFormula)) {
            return false;
        }
        CustomFormula customFormula = (CustomFormula) other;
        return Intrinsics.areEqual(this.uniqueName, customFormula.uniqueName) && Intrinsics.areEqual(this.displayName, customFormula.displayName) && this.paramCount == customFormula.paramCount && Intrinsics.areEqual(this.defaultParamValues, customFormula.defaultParamValues) && Intrinsics.areEqual(this.defaultParamTypes, customFormula.defaultParamTypes) && Intrinsics.areEqual(this.lunoFunctionName, customFormula.lunoFunctionName) && Intrinsics.areEqual(this.ownerLibraryId, customFormula.ownerLibraryId);
    }

    public int hashCode() {
        return (((((((((((this.uniqueName.hashCode() * 31) + this.displayName.hashCode()) * 31) + Integer.hashCode(this.paramCount)) * 31) + this.defaultParamValues.hashCode()) * 31) + this.defaultParamTypes.hashCode()) * 31) + this.lunoFunctionName.hashCode()) * 31) + this.ownerLibraryId.hashCode();
    }

    public String toString() {
        return "CustomFormula(uniqueName=" + this.uniqueName + ", displayName=" + this.displayName + ", paramCount=" + this.paramCount + ", defaultParamValues=" + this.defaultParamValues + ", defaultParamTypes=" + this.defaultParamTypes + ", lunoFunctionName=" + this.lunoFunctionName + ", ownerLibraryId=" + this.ownerLibraryId + Constant.AFTER_QUTO;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public CustomFormula(String uniqueName, String displayName, int i, List<String> defaultParamValues, List<? extends InternTokenType> defaultParamTypes, String lunoFunctionName, String ownerLibraryId) {
        Intrinsics.checkNotNullParameter(uniqueName, "uniqueName");
        Intrinsics.checkNotNullParameter(displayName, "displayName");
        Intrinsics.checkNotNullParameter(defaultParamValues, "defaultParamValues");
        Intrinsics.checkNotNullParameter(defaultParamTypes, "defaultParamTypes");
        Intrinsics.checkNotNullParameter(lunoFunctionName, "lunoFunctionName");
        Intrinsics.checkNotNullParameter(ownerLibraryId, "ownerLibraryId");
        this.uniqueName = uniqueName;
        this.displayName = displayName;
        this.paramCount = i;
        this.defaultParamValues = defaultParamValues;
        this.defaultParamTypes = defaultParamTypes;
        this.lunoFunctionName = lunoFunctionName;
        this.ownerLibraryId = ownerLibraryId;
        if (defaultParamValues.size() != i) {
            throw new IllegalArgumentException(("Размер defaultParamValues (" + defaultParamValues.size() + ") должен соответствовать paramCount (" + i + ") для '" + uniqueName + FormatHelper.QUOTE).toString());
        }
        if (defaultParamTypes.size() == i) {
            return;
        }
        throw new IllegalArgumentException(("Размер defaultParamTypes (" + defaultParamTypes.size() + ") должен соответствовать paramCount (" + i + ") для '" + uniqueName + FormatHelper.QUOTE).toString());
    }

    public final String getUniqueName() {
        return this.uniqueName;
    }

    public final String getDisplayName() {
        return this.displayName;
    }

    public final int getParamCount() {
        return this.paramCount;
    }

    public final List<String> getDefaultParamValues() {
        return this.defaultParamValues;
    }

    public final List<InternTokenType> getDefaultParamTypes() {
        return this.defaultParamTypes;
    }

    public final String getLunoFunctionName() {
        return this.lunoFunctionName;
    }

    public final String getOwnerLibraryId() {
        return this.ownerLibraryId;
    }
}
