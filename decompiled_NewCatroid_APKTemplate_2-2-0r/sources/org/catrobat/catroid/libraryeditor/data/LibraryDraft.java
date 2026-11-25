package org.catrobat.catroid.libraryeditor.data;

import com.google.gson.annotations.SerializedName;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/libraryeditor/data/LibraryDraft.class
  classes14.dex
 */
/* JADX INFO: compiled from: LibraryDraft.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BK\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u0012\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\b¢\u0006\u0002\u0010\fJ\t\u0010\u0019\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001a\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001b\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001c\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\t0\bHÆ\u0003J\u000f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u000b0\bHÆ\u0003JQ\u0010\u001f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\bHÆ\u0001J\u0013\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010#\u001a\u00020$HÖ\u0001J\t\u0010%\u001a\u00020\u0003HÖ\u0001R\u001e\u0010\u0005\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001c\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\b8\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001e\u0010\u0006\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u000e\"\u0004\b\u0014\u0010\u0010R\u001c\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b8\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0012R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u000eR\u001e\u0010\u0004\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u000e\"\u0004\b\u0018\u0010\u0010¨\u0006&"}, d2 = {"Lorg/catrobat/catroid/libraryeditor/data/LibraryDraft;", "", "id", "", "name", "author", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "formulas", "", "Lorg/catrobat/catroid/libraryeditor/data/EditableFormula;", "bricks", "Lorg/catrobat/catroid/libraryeditor/data/EditableBrick;", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/util/List;)V", "getAuthor", "()Ljava/lang/String;", "setAuthor", "(Ljava/lang/String;)V", "getBricks", "()Ljava/util/List;", "getCode", "setCode", "getFormulas", "getId", "getName", "setName", "component1", "component2", "component3", "component4", "component5", "component6", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class LibraryDraft {

    @SerializedName("author")
    private String author;

    @SerializedName("bricks")
    private final List<EditableBrick> bricks;

    @SerializedName(ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY)
    private String code;

    @SerializedName("formulas")
    private final List<EditableFormula> formulas;

    @SerializedName("id")
    private final String id;

    @SerializedName("name")
    private String name;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ LibraryDraft copy$default(LibraryDraft libraryDraft, String str, String str2, String str3, String str4, List list, List list2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = libraryDraft.id;
        }
        if ((i & 2) != 0) {
            str2 = libraryDraft.name;
        }
        String str5 = str2;
        if ((i & 4) != 0) {
            str3 = libraryDraft.author;
        }
        String str6 = str3;
        if ((i & 8) != 0) {
            str4 = libraryDraft.code;
        }
        String str7 = str4;
        if ((i & 16) != 0) {
            list = libraryDraft.formulas;
        }
        List list3 = list;
        if ((i & 32) != 0) {
            list2 = libraryDraft.bricks;
        }
        return libraryDraft.copy(str, str5, str6, str7, list3, list2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getAuthor() {
        return this.author;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getCode() {
        return this.code;
    }

    public final List<EditableFormula> component5() {
        return this.formulas;
    }

    public final List<EditableBrick> component6() {
        return this.bricks;
    }

    public final LibraryDraft copy(String id2, String name2, String author, String code, List<EditableFormula> formulas, List<EditableBrick> bricks) {
        Intrinsics.checkNotNullParameter(id2, "id");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(author, "author");
        Intrinsics.checkNotNullParameter(code, "code");
        Intrinsics.checkNotNullParameter(formulas, "formulas");
        Intrinsics.checkNotNullParameter(bricks, "bricks");
        return new LibraryDraft(id2, name2, author, code, formulas, bricks);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LibraryDraft)) {
            return false;
        }
        LibraryDraft libraryDraft = (LibraryDraft) other;
        return Intrinsics.areEqual(this.id, libraryDraft.id) && Intrinsics.areEqual(this.name, libraryDraft.name) && Intrinsics.areEqual(this.author, libraryDraft.author) && Intrinsics.areEqual(this.code, libraryDraft.code) && Intrinsics.areEqual(this.formulas, libraryDraft.formulas) && Intrinsics.areEqual(this.bricks, libraryDraft.bricks);
    }

    public int hashCode() {
        return (((((((((this.id.hashCode() * 31) + this.name.hashCode()) * 31) + this.author.hashCode()) * 31) + this.code.hashCode()) * 31) + this.formulas.hashCode()) * 31) + this.bricks.hashCode();
    }

    public String toString() {
        return "LibraryDraft(id=" + this.id + ", name=" + this.name + ", author=" + this.author + ", code=" + this.code + ", formulas=" + this.formulas + ", bricks=" + this.bricks + Constant.AFTER_QUTO;
    }

    public LibraryDraft(String id2, String name2, String author, String code, List<EditableFormula> formulas, List<EditableBrick> bricks) {
        Intrinsics.checkNotNullParameter(id2, "id");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(author, "author");
        Intrinsics.checkNotNullParameter(code, "code");
        Intrinsics.checkNotNullParameter(formulas, "formulas");
        Intrinsics.checkNotNullParameter(bricks, "bricks");
        this.id = id2;
        this.name = name2;
        this.author = author;
        this.code = code;
        this.formulas = formulas;
        this.bricks = bricks;
    }

    public final String getId() {
        return this.id;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ LibraryDraft(String str, String str2, String str3, String str4, ArrayList arrayList, ArrayList arrayList2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 2) != 0) {
            str2 = CatroidApplication.getAppContext().getString(R.string.libs_newlib);
            Intrinsics.checkNotNullExpressionValue(str2, "getString(...)");
        }
        this(str, str2, (i & 4) != 0 ? "Автор" : str3, (i & 8) != 0 ? "fun sum(a, b) {\n    return Number(a) + Number(b);\n}\n\nfun toast(sprite, text) {\n    MakeToast(\"Text is: \" + String(text));\n}" : str4, (i & 16) != 0 ? new ArrayList() : arrayList, (i & 32) != 0 ? new ArrayList() : arrayList2);
    }

    public final String getName() {
        return this.name;
    }

    public final void setName(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.name = str;
    }

    public final String getAuthor() {
        return this.author;
    }

    public final void setAuthor(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.author = str;
    }

    public final String getCode() {
        return this.code;
    }

    public final void setCode(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.code = str;
    }

    public final List<EditableFormula> getFormulas() {
        return this.formulas;
    }

    public final List<EditableBrick> getBricks() {
        return this.bricks;
    }
}
