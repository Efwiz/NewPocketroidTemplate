package org.catrobat.catroid.content.actions;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/Content.class
  classes14.dex
 */
/* JADX INFO: compiled from: AskGeminiAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u000f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u0019\u0010\t\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0011"}, d2 = {"Lorg/catrobat/catroid/content/actions/Content;", "", "parts", "", "Lorg/catrobat/catroid/content/actions/Part;", "(Ljava/util/List;)V", "getParts", "()Ljava/util/List;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class Content {
    private final List<Part> parts;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ Content copy$default(Content content, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            list = content.parts;
        }
        return content.copy(list);
    }

    public final List<Part> component1() {
        return this.parts;
    }

    public final Content copy(List<Part> parts) {
        Intrinsics.checkNotNullParameter(parts, "parts");
        return new Content(parts);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof Content) && Intrinsics.areEqual(this.parts, ((Content) other).parts);
    }

    public int hashCode() {
        return this.parts.hashCode();
    }

    public String toString() {
        return "Content(parts=" + this.parts + Constant.AFTER_QUTO;
    }

    public Content(List<Part> parts) {
        Intrinsics.checkNotNullParameter(parts, "parts");
        this.parts = parts;
    }

    public final List<Part> getParts() {
        return this.parts;
    }
}
