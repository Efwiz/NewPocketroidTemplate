package org.catrobat.catroid.libraryeditor.data;

import com.google.gson.annotations.SerializedName;
import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/libraryeditor/data/EditableParam.class
  classes14.dex
 */
/* JADX INFO: compiled from: EditableParam.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0006J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0003HÆ\u0003J+\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0003HÖ\u0001R \u0010\u0004\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR \u0010\u0005\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\b\"\u0004\b\f\u0010\nR\u001e\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\b\"\u0004\b\u000e\u0010\n¨\u0006\u0019"}, d2 = {"Lorg/catrobat/catroid/libraryeditor/data/EditableParam;", "", ModuleXmlParser.TYPE, "", "defaultValue", "name", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getDefaultValue", "()Ljava/lang/String;", "setDefaultValue", "(Ljava/lang/String;)V", "getName", "setName", "getType", "setType", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class EditableParam {

    @SerializedName("default_value")
    private String defaultValue;

    @SerializedName("name")
    private String name;

    @SerializedName(ModuleXmlParser.TYPE)
    private String type;

    public static /* synthetic */ EditableParam copy$default(EditableParam editableParam, String str, String str2, String str3, int i, Object obj) {
        if ((i & 1) != 0) {
            str = editableParam.type;
        }
        if ((i & 2) != 0) {
            str2 = editableParam.defaultValue;
        }
        if ((i & 4) != 0) {
            str3 = editableParam.name;
        }
        return editableParam.copy(str, str2, str3);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getType() {
        return this.type;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getDefaultValue() {
        return this.defaultValue;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getName() {
        return this.name;
    }

    public final EditableParam copy(String type, String defaultValue, String name2) {
        Intrinsics.checkNotNullParameter(type, "type");
        return new EditableParam(type, defaultValue, name2);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof EditableParam)) {
            return false;
        }
        EditableParam editableParam = (EditableParam) other;
        return Intrinsics.areEqual(this.type, editableParam.type) && Intrinsics.areEqual(this.defaultValue, editableParam.defaultValue) && Intrinsics.areEqual(this.name, editableParam.name);
    }

    public int hashCode() {
        int iHashCode = this.type.hashCode() * 31;
        String str = this.defaultValue;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.name;
        return iHashCode2 + (str2 != null ? str2.hashCode() : 0);
    }

    public String toString() {
        return "EditableParam(type=" + this.type + ", defaultValue=" + this.defaultValue + ", name=" + this.name + Constant.AFTER_QUTO;
    }

    public EditableParam(String type, String str, String str2) {
        Intrinsics.checkNotNullParameter(type, "type");
        this.type = type;
        this.defaultValue = str;
        this.name = str2;
    }

    public /* synthetic */ EditableParam(String str, String str2, String str3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? null : str3);
    }

    public final String getType() {
        return this.type;
    }

    public final void setType(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.type = str;
    }

    public final String getDefaultValue() {
        return this.defaultValue;
    }

    public final void setDefaultValue(String str) {
        this.defaultValue = str;
    }

    public final String getName() {
        return this.name;
    }

    public final void setName(String str) {
        this.name = str;
    }
}
