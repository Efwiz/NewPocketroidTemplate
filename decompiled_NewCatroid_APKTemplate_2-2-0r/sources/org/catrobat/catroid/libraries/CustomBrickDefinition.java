package org.catrobat.catroid.libraries;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/libraries/CustomBrickDefinition.class
  classes14.dex
 */
/* JADX INFO: compiled from: CustomBrickDefinition.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003¢\u0006\u0002\u0010\nJ\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003JA\u0010\u0017\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001J\t\u0010\u001d\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u0011\u0010\t\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\fR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u001e"}, d2 = {"Lorg/catrobat/catroid/libraries/CustomBrickDefinition;", "", "id", "", "headerText", "parameters", "", "Lorg/catrobat/catroid/libraries/BrickParameter;", "lunoFunctionName", "ownerLibraryId", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;)V", "getHeaderText", "()Ljava/lang/String;", "getId", "getLunoFunctionName", "getOwnerLibraryId", "getParameters", "()Ljava/util/List;", "component1", "component2", "component3", "component4", "component5", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class CustomBrickDefinition {
    private final String headerText;
    private final String id;
    private final String lunoFunctionName;
    private final String ownerLibraryId;
    private final List<BrickParameter> parameters;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ CustomBrickDefinition copy$default(CustomBrickDefinition customBrickDefinition, String str, String str2, List list, String str3, String str4, int i, Object obj) {
        if ((i & 1) != 0) {
            str = customBrickDefinition.id;
        }
        if ((i & 2) != 0) {
            str2 = customBrickDefinition.headerText;
        }
        String str5 = str2;
        if ((i & 4) != 0) {
            list = customBrickDefinition.parameters;
        }
        List list2 = list;
        if ((i & 8) != 0) {
            str3 = customBrickDefinition.lunoFunctionName;
        }
        String str6 = str3;
        if ((i & 16) != 0) {
            str4 = customBrickDefinition.ownerLibraryId;
        }
        return customBrickDefinition.copy(str, str5, list2, str6, str4);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getHeaderText() {
        return this.headerText;
    }

    public final List<BrickParameter> component3() {
        return this.parameters;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getLunoFunctionName() {
        return this.lunoFunctionName;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final String getOwnerLibraryId() {
        return this.ownerLibraryId;
    }

    public final CustomBrickDefinition copy(String id2, String headerText, List<BrickParameter> parameters, String lunoFunctionName, String ownerLibraryId) {
        Intrinsics.checkNotNullParameter(id2, "id");
        Intrinsics.checkNotNullParameter(headerText, "headerText");
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        Intrinsics.checkNotNullParameter(lunoFunctionName, "lunoFunctionName");
        Intrinsics.checkNotNullParameter(ownerLibraryId, "ownerLibraryId");
        return new CustomBrickDefinition(id2, headerText, parameters, lunoFunctionName, ownerLibraryId);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CustomBrickDefinition)) {
            return false;
        }
        CustomBrickDefinition customBrickDefinition = (CustomBrickDefinition) other;
        return Intrinsics.areEqual(this.id, customBrickDefinition.id) && Intrinsics.areEqual(this.headerText, customBrickDefinition.headerText) && Intrinsics.areEqual(this.parameters, customBrickDefinition.parameters) && Intrinsics.areEqual(this.lunoFunctionName, customBrickDefinition.lunoFunctionName) && Intrinsics.areEqual(this.ownerLibraryId, customBrickDefinition.ownerLibraryId);
    }

    public int hashCode() {
        return (((((((this.id.hashCode() * 31) + this.headerText.hashCode()) * 31) + this.parameters.hashCode()) * 31) + this.lunoFunctionName.hashCode()) * 31) + this.ownerLibraryId.hashCode();
    }

    public String toString() {
        return "CustomBrickDefinition(id=" + this.id + ", headerText=" + this.headerText + ", parameters=" + this.parameters + ", lunoFunctionName=" + this.lunoFunctionName + ", ownerLibraryId=" + this.ownerLibraryId + Constant.AFTER_QUTO;
    }

    public CustomBrickDefinition(String id2, String headerText, List<BrickParameter> parameters, String lunoFunctionName, String ownerLibraryId) {
        Intrinsics.checkNotNullParameter(id2, "id");
        Intrinsics.checkNotNullParameter(headerText, "headerText");
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        Intrinsics.checkNotNullParameter(lunoFunctionName, "lunoFunctionName");
        Intrinsics.checkNotNullParameter(ownerLibraryId, "ownerLibraryId");
        this.id = id2;
        this.headerText = headerText;
        this.parameters = parameters;
        this.lunoFunctionName = lunoFunctionName;
        this.ownerLibraryId = ownerLibraryId;
    }

    public final String getId() {
        return this.id;
    }

    public final String getHeaderText() {
        return this.headerText;
    }

    public final List<BrickParameter> getParameters() {
        return this.parameters;
    }

    public final String getLunoFunctionName() {
        return this.lunoFunctionName;
    }

    public final String getOwnerLibraryId() {
        return this.ownerLibraryId;
    }
}
