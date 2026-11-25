package io.github.rosemoe.sora.lang.completion;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: comparators.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\u000f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\f¨\u0006\u0017"}, d2 = {"Lio/github/rosemoe/sora/lang/completion/SortedCompletionItem;", "", "completionItem", "Lio/github/rosemoe/sora/lang/completion/CompletionItem;", FirebaseAnalytics.Param.SCORE, "Lio/github/rosemoe/sora/lang/completion/FuzzyScore;", "(Lio/github/rosemoe/sora/lang/completion/CompletionItem;Lio/github/rosemoe/sora/lang/completion/FuzzyScore;)V", "getCompletionItem", "()Lio/github/rosemoe/sora/lang/completion/CompletionItem;", "getScore", "()Lio/github/rosemoe/sora/lang/completion/FuzzyScore;", "setScore", "(Lio/github/rosemoe/sora/lang/completion/FuzzyScore;)V", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class SortedCompletionItem {
    private final CompletionItem completionItem;
    private FuzzyScore score;

    public static /* synthetic */ SortedCompletionItem copy$default(SortedCompletionItem sortedCompletionItem, CompletionItem completionItem, FuzzyScore fuzzyScore, int i, Object obj) {
        if ((i & 1) != 0) {
            completionItem = sortedCompletionItem.completionItem;
        }
        if ((i & 2) != 0) {
            fuzzyScore = sortedCompletionItem.score;
        }
        return sortedCompletionItem.copy(completionItem, fuzzyScore);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final CompletionItem getCompletionItem() {
        return this.completionItem;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final FuzzyScore getScore() {
        return this.score;
    }

    public final SortedCompletionItem copy(CompletionItem completionItem, FuzzyScore score) {
        Intrinsics.checkNotNullParameter(completionItem, "completionItem");
        Intrinsics.checkNotNullParameter(score, "score");
        return new SortedCompletionItem(completionItem, score);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SortedCompletionItem)) {
            return false;
        }
        SortedCompletionItem sortedCompletionItem = (SortedCompletionItem) other;
        return Intrinsics.areEqual(this.completionItem, sortedCompletionItem.completionItem) && Intrinsics.areEqual(this.score, sortedCompletionItem.score);
    }

    public int hashCode() {
        return (this.completionItem.hashCode() * 31) + this.score.hashCode();
    }

    public String toString() {
        return "SortedCompletionItem(completionItem=" + this.completionItem + ", score=" + this.score + Constant.AFTER_QUTO;
    }

    public SortedCompletionItem(CompletionItem completionItem, FuzzyScore score) {
        Intrinsics.checkNotNullParameter(completionItem, "completionItem");
        Intrinsics.checkNotNullParameter(score, "score");
        this.completionItem = completionItem;
        this.score = score;
    }

    public final CompletionItem getCompletionItem() {
        return this.completionItem;
    }

    public final FuzzyScore getScore() {
        return this.score;
    }

    public final void setScore(FuzzyScore fuzzyScore) {
        Intrinsics.checkNotNullParameter(fuzzyScore, "<set-?>");
        this.score = fuzzyScore;
    }
}
