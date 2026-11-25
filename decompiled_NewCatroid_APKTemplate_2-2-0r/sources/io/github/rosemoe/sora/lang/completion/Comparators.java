package io.github.rosemoe.sora.lang.completion;

import com.google.firebase.analytics.FirebaseAnalytics;
import io.github.rosemoe.sora.text.CharPosition;
import io.github.rosemoe.sora.text.ContentLine;
import io.github.rosemoe.sora.text.ContentReference;
import java.util.Collection;
import java.util.Comparator;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: compiled from: comparators.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u00008\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\u0010\r\n\u0000\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003\u001a4\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\u00030\u0006j\b\u0012\u0004\u0012\u00020\u0003`\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00030\r\u001a\u0016\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003\u001a\u000e\u0010\u000f\u001a\u00020\u0010*\u0004\u0018\u00010\u0011H\u0002¨\u0006\u0012"}, d2 = {"defaultComparator", "", "a", "Lio/github/rosemoe/sora/lang/completion/CompletionItem;", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "getCompletionItemComparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", FirebaseAnalytics.Param.SOURCE, "Lio/github/rosemoe/sora/text/ContentReference;", "cursorPosition", "Lio/github/rosemoe/sora/text/CharPosition;", "completionItemList", "", "snippetUpComparator", "asString", "", "", "editor_release"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class Comparators {
    private static final String asString(CharSequence charSequence) {
        if (charSequence == null) {
            return " ";
        }
        return charSequence instanceof String ? (String) charSequence : charSequence.toString();
    }

    public static final int defaultComparator(CompletionItem a, CompletionItem b) {
        FuzzyScore score;
        FuzzyScore score2;
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        Object obj = a.extra;
        SortedCompletionItem sortedCompletionItem = obj instanceof SortedCompletionItem ? (SortedCompletionItem) obj : null;
        int score3 = (sortedCompletionItem == null || (score2 = sortedCompletionItem.getScore()) == null) ? 0 : score2.getScore();
        Object obj2 = b.extra;
        SortedCompletionItem sortedCompletionItem2 = obj2 instanceof SortedCompletionItem ? (SortedCompletionItem) obj2 : null;
        int score4 = (sortedCompletionItem2 == null || (score = sortedCompletionItem2.getScore()) == null) ? 0 : score.getScore();
        if (score3 < score4) {
            return 1;
        }
        if (score3 > score4) {
            return -1;
        }
        String strAsString = asString(a.sortText);
        String strAsString2 = asString(b.sortText);
        if (strAsString.compareTo(strAsString2) < 0) {
            return -1;
        }
        if (strAsString.compareTo(strAsString2) > 0) {
            return 1;
        }
        String strAsString3 = asString(a.label);
        String strAsString4 = asString(b.label);
        if (strAsString3.compareTo(strAsString4) < 0) {
            return -1;
        }
        if (strAsString3.compareTo(strAsString4) > 0) {
            return 1;
        }
        CompletionItemKind completionItemKind = b.kind;
        int value = completionItemKind != null ? completionItemKind.getValue() : 0;
        CompletionItemKind completionItemKind2 = a.kind;
        return value - (completionItemKind2 != null ? completionItemKind2.getValue() : 0);
    }

    public static final int snippetUpComparator(CompletionItem a, CompletionItem b) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        if (a.kind != b.kind) {
            if (a.kind == CompletionItemKind.Snippet) {
                return 1;
            }
            if (b.kind == CompletionItemKind.Snippet) {
                return -1;
            }
        }
        return defaultComparator(a, b);
    }

    public static final Comparator<CompletionItem> getCompletionItemComparator(ContentReference source, CharPosition cursorPosition, Collection<? extends CompletionItem> completionItemList) {
        ContentLine contentLine;
        SortedCompletionItem sortedCompletionItem;
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(cursorPosition, "cursorPosition");
        Intrinsics.checkNotNullParameter(completionItemList, "completionItemList");
        source.validateAccess();
        final ContentLine line = source.getReference().getLine(cursorPosition.line);
        FuzzyScorer fuzzyScorer = new FuzzyScorer() { // from class: io.github.rosemoe.sora.lang.completion.Comparators$$ExternalSyntheticLambda0
            @Override // io.github.rosemoe.sora.lang.completion.FuzzyScorer
            public final FuzzyScore calculateScore(String str, String str2, int i, String str3, String str4, int i2, FuzzyScoreOptions fuzzyScoreOptions) {
                return Comparators.getCompletionItemComparator$lambda$0(line, str, str2, i, str3, str4, i2, fuzzyScoreOptions);
            }
        };
        String string = "";
        String lowerCase = string;
        for (CompletionItem completionItem : completionItemList) {
            source.validateAccess();
            int i = completionItem.prefixLength;
            if (string.length() != i) {
                if (i == 0) {
                    string = "";
                } else {
                    Intrinsics.checkNotNull(line);
                    ContentLine contentLine2 = line;
                    string = contentLine2.subSequence(line.length() - i, contentLine2.length()).toString();
                }
                lowerCase = string.toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
            }
            String str = string;
            String str2 = lowerCase;
            SortedCompletionItem sortedCompletionItem2 = new SortedCompletionItem(completionItem, FuzzyScore.INSTANCE.getDefault());
            if (i == 0) {
                sortedCompletionItem2.setScore(FuzzyScore.INSTANCE.getDefault());
                contentLine = line;
            } else {
                int i2 = 0;
                while (i2 < i) {
                    char cCharAt = str.charAt(i2);
                    if (cCharAt != '\t' && cCharAt != ' ') {
                        break;
                    }
                    i2++;
                }
                if (i2 >= i) {
                    sortedCompletionItem2.setScore(FuzzyScore.INSTANCE.getDefault());
                    contentLine = line;
                    sortedCompletionItem = sortedCompletionItem2;
                } else {
                    String str3 = completionItem.sortText;
                    if (str3 != null && str3.length() > 0) {
                        String strAsString = asString(completionItem.sortText);
                        String lowerCase2 = asString(completionItem.sortText).toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(lowerCase2, "toLowerCase(...)");
                        int i3 = i2;
                        contentLine = line;
                        sortedCompletionItem = sortedCompletionItem2;
                        FuzzyScore fuzzyScoreCalculateScore = fuzzyScorer.calculateScore(str, str2, i2, strAsString, lowerCase2, 0, FuzzyScoreOptions.INSTANCE.getDefault());
                        if (fuzzyScoreCalculateScore != null) {
                            if (completionItem.sortText == completionItem.label) {
                                sortedCompletionItem.setScore(fuzzyScoreCalculateScore);
                            } else {
                                String strAsString2 = asString(completionItem.label);
                                String lowerCase3 = asString(completionItem.label).toLowerCase(Locale.ROOT);
                                Intrinsics.checkNotNullExpressionValue(lowerCase3, "toLowerCase(...)");
                                FuzzyScore fuzzyScoreCalculateScore2 = fuzzyScorer.calculateScore(str, str2, i3, strAsString2, lowerCase3, 0, FuzzyScoreOptions.INSTANCE.getDefault());
                                if (fuzzyScoreCalculateScore2 != null) {
                                    sortedCompletionItem.setScore(fuzzyScoreCalculateScore2);
                                    fuzzyScoreCalculateScore2.getMatches().set(0, fuzzyScoreCalculateScore.getMatches().get(0));
                                }
                            }
                        }
                    } else {
                        contentLine = line;
                        int i4 = i2;
                        sortedCompletionItem = sortedCompletionItem2;
                        String strAsString3 = asString(completionItem.label);
                        String lowerCase4 = asString(completionItem.label).toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(lowerCase4, "toLowerCase(...)");
                        FuzzyScore fuzzyScoreCalculateScore3 = fuzzyScorer.calculateScore(str, str2, i4, strAsString3, lowerCase4, 0, FuzzyScoreOptions.INSTANCE.getDefault());
                        if (fuzzyScoreCalculateScore3 != null) {
                            sortedCompletionItem.setScore(fuzzyScoreCalculateScore3);
                        }
                    }
                }
                completionItem.extra = sortedCompletionItem;
            }
            line = contentLine;
            string = str;
            lowerCase = str2;
        }
        return new Comparator() { // from class: io.github.rosemoe.sora.lang.completion.Comparators$$ExternalSyntheticLambda1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return Comparators.getCompletionItemComparator$lambda$1((CompletionItem) obj, (CompletionItem) obj2);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final FuzzyScore getCompletionItemComparator$lambda$0(ContentLine contentLine, String pattern, String lowPattern, int i, String wordText, String lowWord, int i2, FuzzyScoreOptions fuzzyScoreOptions) {
        Intrinsics.checkNotNullParameter(pattern, "pattern");
        Intrinsics.checkNotNullParameter(lowPattern, "lowPattern");
        Intrinsics.checkNotNullParameter(wordText, "wordText");
        Intrinsics.checkNotNullParameter(lowWord, "lowWord");
        if (contentLine.length() > 2000) {
            return Filters.fuzzyScore(pattern, lowPattern, i, wordText, lowWord, i2, fuzzyScoreOptions);
        }
        return Filters.fuzzyScoreGracefulAggressive(pattern, lowPattern, i, wordText, lowWord, i2, fuzzyScoreOptions);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int getCompletionItemComparator$lambda$1(CompletionItem completionItem, CompletionItem completionItem2) {
        Intrinsics.checkNotNull(completionItem);
        Intrinsics.checkNotNull(completionItem2);
        return snippetUpComparator(completionItem, completionItem2);
    }
}
