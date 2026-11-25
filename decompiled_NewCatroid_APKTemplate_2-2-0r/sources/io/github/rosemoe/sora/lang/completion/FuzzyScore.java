package io.github.rosemoe.sora.lang.completion;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: filters.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\n\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006¢\u0006\u0002\u0010\u0007R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000b¨\u0006\u0010"}, d2 = {"Lio/github/rosemoe/sora/lang/completion/FuzzyScore;", "", FirebaseAnalytics.Param.SCORE, "", "wordStart", "matches", "", "(IILjava/util/List;)V", "getMatches", "()Ljava/util/List;", "getScore", "()I", "setScore", "(I)V", "getWordStart", "Companion", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FuzzyScore {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    /* JADX INFO: renamed from: default, reason: not valid java name */
    private static final FuzzyScore f0default = new FuzzyScore(-100, 0, null, 4, null);
    private final List<Integer> matches;
    private int score;
    private final int wordStart;

    public static final FuzzyScore getDefault() {
        return INSTANCE.getDefault();
    }

    @JvmStatic
    public static final boolean isDefault(FuzzyScore fuzzyScore) {
        return INSTANCE.isDefault(fuzzyScore);
    }

    public FuzzyScore(int i, int i2, List<Integer> matches) {
        Intrinsics.checkNotNullParameter(matches, "matches");
        this.score = i;
        this.wordStart = i2;
        this.matches = matches;
    }

    public final int getScore() {
        return this.score;
    }

    public final void setScore(int i) {
        this.score = i;
    }

    public final int getWordStart() {
        return this.wordStart;
    }

    public /* synthetic */ FuzzyScore(int i, int i2, ArrayList arrayList, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, (i3 & 4) != 0 ? new ArrayList() : arrayList);
    }

    public final List<Integer> getMatches() {
        return this.matches;
    }

    /* JADX INFO: compiled from: filters.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u0004H\u0007R\u001c\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0005\u0010\u0002\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u000b"}, d2 = {"Lio/github/rosemoe/sora/lang/completion/FuzzyScore$Companion;", "", "()V", "default", "Lio/github/rosemoe/sora/lang/completion/FuzzyScore;", "getDefault$annotations", "getDefault", "()Lio/github/rosemoe/sora/lang/completion/FuzzyScore;", "isDefault", "", FirebaseAnalytics.Param.SCORE, "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public static /* synthetic */ void getDefault$annotations() {
        }

        private Companion() {
        }

        public final FuzzyScore getDefault() {
            return FuzzyScore.f0default;
        }

        @JvmStatic
        public final boolean isDefault(FuzzyScore score) {
            return score != null && score.getScore() == -100 && score.getWordStart() == 0;
        }
    }
}
