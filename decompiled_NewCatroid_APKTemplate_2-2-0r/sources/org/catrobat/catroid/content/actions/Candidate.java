package org.catrobat.catroid.content.actions;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/Candidate.class
  classes14.dex
 */
/* JADX INFO: compiled from: AskGeminiAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\f\u001a\u00020\rHÖ\u0001J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lorg/catrobat/catroid/content/actions/Candidate;", "", FirebaseAnalytics.Param.CONTENT, "Lorg/catrobat/catroid/content/actions/Content;", "(Lorg/catrobat/catroid/content/actions/Content;)V", "getContent", "()Lorg/catrobat/catroid/content/actions/Content;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class Candidate {
    private final Content content;

    public static /* synthetic */ Candidate copy$default(Candidate candidate, Content content, int i, Object obj) {
        if ((i & 1) != 0) {
            content = candidate.content;
        }
        return candidate.copy(content);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final Content getContent() {
        return this.content;
    }

    public final Candidate copy(Content content) {
        Intrinsics.checkNotNullParameter(content, "content");
        return new Candidate(content);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof Candidate) && Intrinsics.areEqual(this.content, ((Candidate) other).content);
    }

    public int hashCode() {
        return this.content.hashCode();
    }

    public String toString() {
        return "Candidate(content=" + this.content + Constant.AFTER_QUTO;
    }

    public Candidate(Content content) {
        Intrinsics.checkNotNullParameter(content, "content");
        this.content = content;
    }

    public final Content getContent() {
        return this.content;
    }
}
