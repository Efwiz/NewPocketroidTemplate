package org.catrobat.catroid.content.bricks.brickspinner;

import com.huawei.hms.ml.language.common.utils.Constant;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.io.Serializable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.common.Nameable;
import org.catrobat.catroid.io.XStreamFieldKeyOrder;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/brickspinner/LanguageObject.class
  classes14.dex
 */
/* JADX INFO: compiled from: LanguageObject.kt */
/* JADX INFO: loaded from: classes14.dex */
@XStreamAlias("languageObject")
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0087\b\u0018\u0000 \u001b2\u00020\u00012\u00020\u0002:\u0001\u001bB\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0002\u0010\u0006J\t\u0010\r\u001a\u00020\u0004HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0004HÆ\u0003J\u001d\u0010\u000f\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u0004HÆ\u0001J\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013HÖ\u0003J\b\u0010\u0014\u001a\u00020\u0004H\u0016J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0004H\u0016J\t\u0010\u001a\u001a\u00020\u0004HÖ\u0001R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u0005\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\b\"\u0004\b\f\u0010\n¨\u0006\u001c"}, d2 = {"Lorg/catrobat/catroid/content/bricks/brickspinner/LanguageObject;", "Lorg/catrobat/catroid/common/Nameable;", "Ljava/io/Serializable;", "languageName", "", "languageTag", "(Ljava/lang/String;Ljava/lang/String;)V", "getLanguageName", "()Ljava/lang/String;", "setLanguageName", "(Ljava/lang/String;)V", "getLanguageTag", "setLanguageTag", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "getName", "hashCode", "", "setName", "", "name", "toString", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
@XStreamFieldKeyOrder({"languageName", "languageTag"})
public final /* data */ class LanguageObject implements Nameable, Serializable {
    private static final long serialVersionUID = 1;
    private String languageName;
    private String languageTag;

    public static /* synthetic */ LanguageObject copy$default(LanguageObject languageObject, String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = languageObject.languageName;
        }
        if ((i & 2) != 0) {
            str2 = languageObject.languageTag;
        }
        return languageObject.copy(str, str2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getLanguageName() {
        return this.languageName;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getLanguageTag() {
        return this.languageTag;
    }

    public final LanguageObject copy(String languageName, String languageTag) {
        Intrinsics.checkNotNullParameter(languageName, "languageName");
        Intrinsics.checkNotNullParameter(languageTag, "languageTag");
        return new LanguageObject(languageName, languageTag);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LanguageObject)) {
            return false;
        }
        LanguageObject languageObject = (LanguageObject) other;
        return Intrinsics.areEqual(this.languageName, languageObject.languageName) && Intrinsics.areEqual(this.languageTag, languageObject.languageTag);
    }

    public int hashCode() {
        return (this.languageName.hashCode() * 31) + this.languageTag.hashCode();
    }

    public String toString() {
        return "LanguageObject(languageName=" + this.languageName + ", languageTag=" + this.languageTag + Constant.AFTER_QUTO;
    }

    public LanguageObject(String languageName, String languageTag) {
        Intrinsics.checkNotNullParameter(languageName, "languageName");
        Intrinsics.checkNotNullParameter(languageTag, "languageTag");
        this.languageName = languageName;
        this.languageTag = languageTag;
    }

    public final String getLanguageName() {
        return this.languageName;
    }

    public final String getLanguageTag() {
        return this.languageTag;
    }

    public final void setLanguageName(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.languageName = str;
    }

    public final void setLanguageTag(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.languageTag = str;
    }

    @Override // org.catrobat.catroid.common.Nameable
    public String getName() {
        return this.languageName;
    }

    @Override // org.catrobat.catroid.common.Nameable
    public void setName(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        this.languageName = name2;
    }
}
