package io.github.rosemoe.sora.lang.completion;

import io.github.rosemoe.sora.text.CharPosition;
import io.github.rosemoe.sora.text.ContentReference;
import io.github.rosemoe.sora.text.TextUtils;
import io.github.rosemoe.sora.util.MutableInt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

/* JADX INFO: loaded from: classes11.dex */
public class IdentifierAutoComplete {

    @Deprecated
    private static final Comparator<CompletionItem> COMPARATOR = new Comparator() { // from class: io.github.rosemoe.sora.lang.completion.IdentifierAutoComplete$$ExternalSyntheticLambda0
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return IdentifierAutoComplete.lambda$static$0((CompletionItem) obj, (CompletionItem) obj2);
        }
    };
    private Map<String, Object> keywordMap;
    private String[] keywords;
    private boolean keywordsAreLowCase;

    public interface Identifiers {
        void filterIdentifiers(String str, List<String> list);
    }

    static /* synthetic */ int lambda$static$0(CompletionItem completionItem, CompletionItem completionItem2) {
        int iCompareTo = asString(completionItem.desc).compareTo(asString(completionItem2.desc));
        if (iCompareTo < 0) {
            return 1;
        }
        if (iCompareTo > 0) {
            return -1;
        }
        return asString(completionItem.label).compareTo(asString(completionItem2.label));
    }

    public IdentifierAutoComplete() {
    }

    public IdentifierAutoComplete(String[] strArr) {
        this();
        setKeywords(strArr, true);
    }

    private static String asString(CharSequence charSequence) {
        return charSequence instanceof String ? (String) charSequence : charSequence.toString();
    }

    public void setKeywords(String[] strArr, boolean z) {
        this.keywords = strArr;
        this.keywordsAreLowCase = z;
        HashMap map = new HashMap();
        if (strArr != null) {
            for (String str : strArr) {
                map.put(str, true);
            }
        }
        this.keywordMap = map;
    }

    public String[] getKeywords() {
        return this.keywords;
    }

    public void requireAutoComplete(ContentReference contentReference, CharPosition charPosition, String str, CompletionPublisher completionPublisher, Identifiers identifiers) {
        List<CompletionItem> listCreateCompletionItemList = createCompletionItemList(str, identifiers);
        Comparator<CompletionItem> completionItemComparator = Comparators.getCompletionItemComparator(contentReference, charPosition, listCreateCompletionItemList);
        completionPublisher.addItems(listCreateCompletionItemList);
        completionPublisher.setComparator(completionItemComparator);
    }

    public List<CompletionItem> createCompletionItemList(String str, Identifiers identifiers) {
        String str2;
        int length = str.length();
        if (length == 0) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        String[] strArr = this.keywords;
        boolean z = this.keywordsAreLowCase;
        Map<String, Object> map = this.keywordMap;
        String lowerCase = str.toLowerCase(Locale.ROOT);
        if (strArr != null) {
            String str3 = "Keyword";
            if (z) {
                int length2 = strArr.length;
                int i = 0;
                while (i < length2) {
                    String str4 = strArr[i];
                    int i2 = i;
                    int i3 = length2;
                    String str5 = str3;
                    FuzzyScore fuzzyScoreFuzzyScoreGracefulAggressive = Filters.fuzzyScoreGracefulAggressive(str, str.toLowerCase(Locale.ROOT), 0, str4, str4.toLowerCase(Locale.ROOT), 0, FuzzyScoreOptions.getDefault());
                    int score = fuzzyScoreFuzzyScoreGracefulAggressive == null ? -100 : fuzzyScoreFuzzyScoreGracefulAggressive.getScore();
                    if (str4.startsWith(lowerCase) || score >= -20) {
                        str3 = str5;
                        arrayList.add(new SimpleCompletionItem(str4, str3, length, str4).kind(CompletionItemKind.Keyword));
                    } else {
                        str3 = str5;
                    }
                    i = i2 + 1;
                    length2 = i3;
                }
            } else {
                int length3 = strArr.length;
                int i4 = 0;
                while (i4 < length3) {
                    String str6 = strArr[i4];
                    int i5 = i4;
                    int i6 = length3;
                    String str7 = str3;
                    FuzzyScore fuzzyScoreFuzzyScoreGracefulAggressive2 = Filters.fuzzyScoreGracefulAggressive(str, str.toLowerCase(Locale.ROOT), 0, str6, str6.toLowerCase(Locale.ROOT), 0, FuzzyScoreOptions.getDefault());
                    int score2 = fuzzyScoreFuzzyScoreGracefulAggressive2 == null ? -100 : fuzzyScoreFuzzyScoreGracefulAggressive2.getScore();
                    if (str6.toLowerCase(Locale.ROOT).startsWith(lowerCase) || score2 >= -20) {
                        str2 = str7;
                        arrayList.add(new SimpleCompletionItem(str6, str2, length, str6).kind(CompletionItemKind.Keyword));
                    } else {
                        str2 = str7;
                    }
                    i4 = i5 + 1;
                    str3 = str2;
                    length3 = i6;
                }
            }
        }
        if (identifiers != null) {
            ArrayList arrayList2 = new ArrayList();
            identifiers.filterIdentifiers(str, arrayList2);
            for (String str8 : arrayList2) {
                if (map == null || !map.containsKey(str8)) {
                    arrayList.add(new SimpleCompletionItem(str8, "Identifier", length, str8).kind(CompletionItemKind.Identifier));
                }
            }
        }
        return arrayList;
    }

    @Deprecated
    public void requireAutoComplete(String str, CompletionPublisher completionPublisher, Identifiers identifiers) {
        completionPublisher.setComparator(COMPARATOR);
        completionPublisher.setUpdateThreshold(0);
        completionPublisher.addItems(createCompletionItemList(str, identifiers));
    }

    public static class DisposableIdentifiers implements Identifiers {
        private static final Object SIGN = new Object();
        private HashMap<String, Object> cache;
        private final List<String> identifiers = new ArrayList(128);

        public void addIdentifier(String str) {
            HashMap<String, Object> map = this.cache;
            if (map == null) {
                throw new IllegalStateException("begin() has not been called");
            }
            Object obj = SIGN;
            if (map.put(str, obj) == obj) {
                return;
            }
            this.identifiers.add(str);
        }

        public void beginBuilding() {
            this.cache = new HashMap<>();
        }

        public void finishBuilding() {
            this.cache.clear();
            this.cache = null;
        }

        @Override // io.github.rosemoe.sora.lang.completion.IdentifierAutoComplete.Identifiers
        public void filterIdentifiers(String str, List<String> list) {
            for (String str2 : this.identifiers) {
                FuzzyScore fuzzyScoreFuzzyScoreGracefulAggressive = Filters.fuzzyScoreGracefulAggressive(str, str.toLowerCase(Locale.ROOT), 0, str2, str2.toLowerCase(Locale.ROOT), 0, FuzzyScoreOptions.getDefault());
                int score = fuzzyScoreFuzzyScoreGracefulAggressive == null ? -100 : fuzzyScoreFuzzyScoreGracefulAggressive.getScore();
                if (TextUtils.startsWith(str2, str, true) || score >= -20) {
                    if (str.length() != str2.length() || !TextUtils.startsWith(str, str2, false)) {
                        list.add(str2);
                    }
                }
            }
        }
    }

    public static class SyncIdentifiers implements Identifiers {
        private final Lock lock = new ReentrantLock(true);
        private final Map<String, MutableInt> identifierMap = new HashMap();

        public void clear() {
            this.lock.lock();
            try {
                this.identifierMap.clear();
            } finally {
                this.lock.unlock();
            }
        }

        public void identifierIncrease(String str) {
            this.lock.lock();
            try {
                this.identifierMap.computeIfAbsent(str, new Function() { // from class: io.github.rosemoe.sora.lang.completion.IdentifierAutoComplete$SyncIdentifiers$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return IdentifierAutoComplete.SyncIdentifiers.lambda$identifierIncrease$0((String) obj);
                    }
                }).increase();
            } finally {
                this.lock.unlock();
            }
        }

        static /* synthetic */ MutableInt lambda$identifierIncrease$0(String str) {
            return new MutableInt(0);
        }

        public void identifierDecrease(String str) {
            this.lock.lock();
            try {
                MutableInt mutableInt = this.identifierMap.get(str);
                if (mutableInt != null && mutableInt.decreaseAndGet() <= 0) {
                    this.identifierMap.remove(str);
                }
            } finally {
                this.lock.unlock();
            }
        }

        @Override // io.github.rosemoe.sora.lang.completion.IdentifierAutoComplete.Identifiers
        public void filterIdentifiers(String str, List<String> list) {
            filterIdentifiers(str, list, false);
        }

        public void filterIdentifiers(String str, List<String> list, boolean z) {
            boolean zTryLock;
            if (z) {
                this.lock.lock();
                zTryLock = true;
            } else {
                try {
                    zTryLock = this.lock.tryLock(3L, TimeUnit.MILLISECONDS);
                } catch (InterruptedException unused) {
                    zTryLock = false;
                }
            }
            if (zTryLock) {
                try {
                    for (String str2 : this.identifierMap.keySet()) {
                        FuzzyScore fuzzyScoreFuzzyScoreGracefulAggressive = Filters.fuzzyScoreGracefulAggressive(str, str.toLowerCase(Locale.ROOT), 0, str2, str2.toLowerCase(Locale.ROOT), 0, FuzzyScoreOptions.getDefault());
                        int score = fuzzyScoreFuzzyScoreGracefulAggressive == null ? -100 : fuzzyScoreFuzzyScoreGracefulAggressive.getScore();
                        if (TextUtils.startsWith(str2, str, true) || score >= -20) {
                            if (str.length() != str2.length() || !TextUtils.startsWith(str, str2, false)) {
                                list.add(str2);
                            }
                        }
                    }
                } finally {
                    this.lock.unlock();
                }
            }
        }
    }
}
