package org.catrobat.catroid.libraryeditor.data;

import com.google.gson.annotations.SerializedName;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/libraryeditor/data/EditableFormula.class
  classes14.dex
 */
/* JADX INFO: compiled from: EditableFormula.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tJ\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\b0\u0007HÆ\u0003J7\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007HÆ\u0001J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001c\u001a\u00020\u001dHÖ\u0001J\t\u0010\u001e\u001a\u00020\u0003HÖ\u0001R\u001e\u0010\u0005\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001e\u0010\u0004\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000b\"\u0004\b\u000f\u0010\rR\u001e\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u000b\"\u0004\b\u0011\u0010\rR\u001c\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u001f"}, d2 = {"Lorg/catrobat/catroid/libraryeditor/data/EditableFormula;", "", "id", "", "functionName", "displayName", "params", "", "Lorg/catrobat/catroid/libraryeditor/data/EditableParam;", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;)V", "getDisplayName", "()Ljava/lang/String;", "setDisplayName", "(Ljava/lang/String;)V", "getFunctionName", "setFunctionName", "getId", "setId", "getParams", "()Ljava/util/List;", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class EditableFormula {

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("function_name")
    private String functionName;

    @SerializedName("id")
    private String id;

    @SerializedName("params")
    private final List<EditableParam> params;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ EditableFormula copy$default(EditableFormula editableFormula, String str, String str2, String str3, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            str = editableFormula.id;
        }
        if ((i & 2) != 0) {
            str2 = editableFormula.functionName;
        }
        if ((i & 4) != 0) {
            str3 = editableFormula.displayName;
        }
        if ((i & 8) != 0) {
            list = editableFormula.params;
        }
        return editableFormula.copy(str, str2, str3, list);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getFunctionName() {
        return this.functionName;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getDisplayName() {
        return this.displayName;
    }

    public final List<EditableParam> component4() {
        return this.params;
    }

    public final EditableFormula copy(String id2, String functionName, String displayName, List<EditableParam> params) {
        Intrinsics.checkNotNullParameter(id2, "id");
        Intrinsics.checkNotNullParameter(functionName, "functionName");
        Intrinsics.checkNotNullParameter(displayName, "displayName");
        Intrinsics.checkNotNullParameter(params, "params");
        return new EditableFormula(id2, functionName, displayName, params);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof EditableFormula)) {
            return false;
        }
        EditableFormula editableFormula = (EditableFormula) other;
        return Intrinsics.areEqual(this.id, editableFormula.id) && Intrinsics.areEqual(this.functionName, editableFormula.functionName) && Intrinsics.areEqual(this.displayName, editableFormula.displayName) && Intrinsics.areEqual(this.params, editableFormula.params);
    }

    public int hashCode() {
        return (((((this.id.hashCode() * 31) + this.functionName.hashCode()) * 31) + this.displayName.hashCode()) * 31) + this.params.hashCode();
    }

    public String toString() {
        return "EditableFormula(id=" + this.id + ", functionName=" + this.functionName + ", displayName=" + this.displayName + ", params=" + this.params + Constant.AFTER_QUTO;
    }

    public EditableFormula(String id2, String functionName, String displayName, List<EditableParam> params) {
        Intrinsics.checkNotNullParameter(id2, "id");
        Intrinsics.checkNotNullParameter(functionName, "functionName");
        Intrinsics.checkNotNullParameter(displayName, "displayName");
        Intrinsics.checkNotNullParameter(params, "params");
        this.id = id2;
        this.functionName = functionName;
        this.displayName = displayName;
        this.params = params;
    }

    public final String getId() {
        return this.id;
    }

    public final void setId(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.id = str;
    }

    public final String getFunctionName() {
        return this.functionName;
    }

    public final void setFunctionName(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.functionName = str;
    }

    public final String getDisplayName() {
        return this.displayName;
    }

    public final void setDisplayName(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.displayName = str;
    }

    public /* synthetic */ EditableFormula(String str, String str2, String str3, ArrayList arrayList, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, str3, (i & 8) != 0 ? new ArrayList() : arrayList);
    }

    public final List<EditableParam> getParams() {
        return this.params;
    }
}
