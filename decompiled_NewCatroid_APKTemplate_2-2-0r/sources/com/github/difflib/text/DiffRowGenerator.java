package com.github.difflib.text;

import com.github.difflib.DiffUtils;
import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.ChangeDelta;
import com.github.difflib.patch.Chunk;
import com.github.difflib.patch.DeleteDelta;
import com.github.difflib.patch.DeltaType;
import com.github.difflib.patch.InsertDelta;
import com.github.difflib.patch.Patch;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.eclipse.jgit.transport.WalkEncryption;

/* JADX INFO: loaded from: classes.dex */
public final class DiffRowGenerator {
    private final int columnWidth;
    private final boolean decompressDeltas;
    private final BiPredicate<String, String> equalizer;
    private final boolean ignoreWhiteSpaces;
    private final Function<String, List<String>> inlineDiffSplitter;
    private final Function<String, String> lineNormalizer;
    private final boolean mergeOriginalRevised;
    private final BiFunction<DiffRow.Tag, Boolean, String> newTag;
    private final BiFunction<DiffRow.Tag, Boolean, String> oldTag;
    private final Function<String, String> processDiffs;
    private final boolean replaceOriginalLinefeedInChangesWithSpaces;
    private final boolean reportLinesUnchanged;
    private final boolean showInlineDiffs;
    public static final BiPredicate<String, String> DEFAULT_EQUALIZER = new BiPredicate() { // from class: com.github.difflib.text.DiffRowGenerator$$ExternalSyntheticLambda0
        @Override // java.util.function.BiPredicate
        public final boolean test(Object obj, Object obj2) {
            return ((String) obj).equals((String) obj2);
        }
    };
    public static final BiPredicate<String, String> IGNORE_WHITESPACE_EQUALIZER = new BiPredicate() { // from class: com.github.difflib.text.DiffRowGenerator$$ExternalSyntheticLambda1
        @Override // java.util.function.BiPredicate
        public final boolean test(Object obj, Object obj2) {
            return DiffRowGenerator.adjustWhitespace((String) obj).equals(DiffRowGenerator.adjustWhitespace((String) obj2));
        }
    };
    public static final Function<String, String> LINE_NORMALIZER_FOR_HTML = new Function() { // from class: com.github.difflib.text.DiffRowGenerator$$ExternalSyntheticLambda2
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return StringUtils.normalize((String) obj);
        }
    };
    public static final Function<String, List<String>> SPLITTER_BY_CHARACTER = new Function() { // from class: com.github.difflib.text.DiffRowGenerator$$ExternalSyntheticLambda3
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return DiffRowGenerator.lambda$static$1((String) obj);
        }
    };
    public static final Pattern SPLIT_BY_WORD_PATTERN = Pattern.compile("\\s+|[,.\\[\\](){}/\\\\*+\\-#]");
    public static final Function<String, List<String>> SPLITTER_BY_WORD = new Function() { // from class: com.github.difflib.text.DiffRowGenerator$$ExternalSyntheticLambda4
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return DiffRowGenerator.splitStringPreserveDelimiter((String) obj, DiffRowGenerator.SPLIT_BY_WORD_PATTERN);
        }
    };
    public static final Pattern WHITESPACE_PATTERN = Pattern.compile(WalkEncryption.Vals.REGEX_WS);

    /* synthetic */ DiffRowGenerator(Builder builder, AnonymousClass1 anonymousClass1) {
        this(builder);
    }

    static /* synthetic */ List lambda$static$1(String str) {
        ArrayList arrayList = new ArrayList(str.length());
        for (char c : str.toCharArray()) {
            arrayList.add(Character.valueOf(c).toString());
        }
        return arrayList;
    }

    public static Builder create() {
        return new Builder(null);
    }

    private static String adjustWhitespace(String str) {
        return WHITESPACE_PATTERN.matcher(str.trim()).replaceAll(" ");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static final List<String> splitStringPreserveDelimiter(String str, Pattern pattern) {
        ArrayList arrayList = new ArrayList();
        if (str != null) {
            Matcher matcher = pattern.matcher(str);
            int iEnd = 0;
            while (matcher.find()) {
                if (iEnd < matcher.start()) {
                    arrayList.add(str.substring(iEnd, matcher.start()));
                }
                arrayList.add(matcher.group());
                iEnd = matcher.end();
            }
            if (iEnd < str.length()) {
                arrayList.add(str.substring(iEnd));
            }
        }
        return arrayList;
    }

    static void wrapInTag(List<String> list, int i, int i2, DiffRow.Tag tag, BiFunction<DiffRow.Tag, Boolean, String> biFunction, Function<String, String> function, boolean z) {
        while (i2 >= i) {
            while (i2 > i) {
                int i3 = i2 - 1;
                if (!"\n".equals(list.get(i3))) {
                    break;
                }
                if (z) {
                    list.set(i3, " ");
                    break;
                }
                i2--;
            }
            if (i2 == i) {
                return;
            }
            list.add(i2, biFunction.apply(tag, false));
            if (function != null) {
                int i4 = i2 - 1;
                list.set(i4, function.apply(list.get(i4)));
            }
            while (true) {
                i2--;
                if (i2 <= i) {
                    break;
                }
                int i5 = i2 - 1;
                if ("\n".equals(list.get(i5))) {
                    if (!z) {
                        break;
                    } else {
                        list.set(i5, " ");
                    }
                }
                if (function != null) {
                    list.set(i5, function.apply(list.get(i5)));
                }
            }
            list.add(i2, biFunction.apply(tag, true));
            i2--;
        }
    }

    private DiffRowGenerator(Builder builder) {
        this.showInlineDiffs = builder.showInlineDiffs;
        boolean z = builder.ignoreWhiteSpaces;
        this.ignoreWhiteSpaces = z;
        this.oldTag = builder.oldTag;
        this.newTag = builder.newTag;
        this.columnWidth = builder.columnWidth;
        this.mergeOriginalRevised = builder.mergeOriginalRevised;
        Function<String, List<String>> function = builder.inlineDiffSplitter;
        this.inlineDiffSplitter = function;
        this.decompressDeltas = builder.decompressDeltas;
        if (builder.equalizer == null) {
            this.equalizer = z ? IGNORE_WHITESPACE_EQUALIZER : DEFAULT_EQUALIZER;
        } else {
            this.equalizer = builder.equalizer;
        }
        this.reportLinesUnchanged = builder.reportLinesUnchanged;
        Function<String, String> function2 = builder.lineNormalizer;
        this.lineNormalizer = function2;
        this.processDiffs = builder.processDiffs;
        this.replaceOriginalLinefeedInChangesWithSpaces = builder.replaceOriginalLinefeedInChangesWithSpaces;
        Objects.requireNonNull(function);
        Objects.requireNonNull(function2);
    }

    public List<DiffRow> generateDiffRows(List<String> list, List<String> list2) {
        return generateDiffRows(list, DiffUtils.diff(list, list2, this.equalizer));
    }

    public List<DiffRow> generateDiffRows(List<String> list, Patch<String> patch) {
        ArrayList arrayList = new ArrayList();
        List<AbstractDelta<String>> deltas = patch.getDeltas();
        int iTransformDeltaIntoDiffRow = 0;
        if (this.decompressDeltas) {
            Iterator<AbstractDelta<String>> it = deltas.iterator();
            while (it.hasNext()) {
                Iterator<AbstractDelta<String>> it2 = decompressDeltas(it.next()).iterator();
                while (it2.hasNext()) {
                    iTransformDeltaIntoDiffRow = transformDeltaIntoDiffRow(list, iTransformDeltaIntoDiffRow, arrayList, it2.next());
                }
            }
        } else {
            Iterator<AbstractDelta<String>> it3 = deltas.iterator();
            while (it3.hasNext()) {
                iTransformDeltaIntoDiffRow = transformDeltaIntoDiffRow(list, iTransformDeltaIntoDiffRow, arrayList, it3.next());
            }
        }
        for (String str : list.subList(iTransformDeltaIntoDiffRow, list.size())) {
            arrayList.add(buildDiffRow(DiffRow.Tag.EQUAL, str, str));
        }
        return arrayList;
    }

    private int transformDeltaIntoDiffRow(List<String> list, int i, List<DiffRow> list2, AbstractDelta<String> abstractDelta) {
        Chunk<String> source = abstractDelta.getSource();
        Chunk<String> target = abstractDelta.getTarget();
        for (String str : list.subList(i, source.getPosition())) {
            list2.add(buildDiffRow(DiffRow.Tag.EQUAL, str, str));
        }
        int i2 = AnonymousClass1.$SwitchMap$com$github$difflib$patch$DeltaType[abstractDelta.getType().ordinal()];
        if (i2 == 1) {
            Iterator<String> it = target.getLines().iterator();
            while (it.hasNext()) {
                list2.add(buildDiffRow(DiffRow.Tag.INSERT, "", it.next()));
            }
        } else if (i2 == 2) {
            Iterator<String> it2 = source.getLines().iterator();
            while (it2.hasNext()) {
                list2.add(buildDiffRow(DiffRow.Tag.DELETE, it2.next(), ""));
            }
        } else if (this.showInlineDiffs) {
            list2.addAll(generateInlineDiffs(abstractDelta));
        } else {
            int i3 = 0;
            while (i3 < Math.max(source.size(), target.size())) {
                list2.add(buildDiffRow(DiffRow.Tag.CHANGE, source.getLines().size() > i3 ? source.getLines().get(i3) : "", target.getLines().size() > i3 ? target.getLines().get(i3) : ""));
                i3++;
            }
        }
        return source.last() + 1;
    }

    /* JADX INFO: renamed from: com.github.difflib.text.DiffRowGenerator$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$github$difflib$patch$DeltaType;

        static {
            int[] iArr = new int[DeltaType.values().length];
            $SwitchMap$com$github$difflib$patch$DeltaType = iArr;
            try {
                iArr[DeltaType.INSERT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$github$difflib$patch$DeltaType[DeltaType.DELETE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    private List<AbstractDelta<String>> decompressDeltas(AbstractDelta<String> abstractDelta) {
        if (abstractDelta.getType() == DeltaType.CHANGE && abstractDelta.getSource().size() != abstractDelta.getTarget().size()) {
            ArrayList arrayList = new ArrayList();
            int iMin = Math.min(abstractDelta.getSource().size(), abstractDelta.getTarget().size());
            Chunk<String> source = abstractDelta.getSource();
            Chunk<String> target = abstractDelta.getTarget();
            arrayList.add(new ChangeDelta(new Chunk(source.getPosition(), source.getLines().subList(0, iMin)), new Chunk(target.getPosition(), target.getLines().subList(0, iMin))));
            if (source.getLines().size() < target.getLines().size()) {
                arrayList.add(new InsertDelta(new Chunk(source.getPosition() + iMin, Collections.emptyList()), new Chunk(target.getPosition() + iMin, target.getLines().subList(iMin, target.getLines().size()))));
            } else {
                arrayList.add(new DeleteDelta(new Chunk(source.getPosition() + iMin, source.getLines().subList(iMin, source.getLines().size())), new Chunk(target.getPosition() + iMin, Collections.emptyList())));
            }
            return arrayList;
        }
        return Collections.singletonList(abstractDelta);
    }

    private DiffRow buildDiffRow(DiffRow.Tag tag, String str, String str2) {
        if (this.reportLinesUnchanged) {
            return new DiffRow(tag, str, str2);
        }
        String strPreprocessLine = preprocessLine(str);
        if (DiffRow.Tag.DELETE == tag && (this.mergeOriginalRevised || this.showInlineDiffs)) {
            strPreprocessLine = this.oldTag.apply(tag, true) + strPreprocessLine + this.oldTag.apply(tag, false);
        }
        String strPreprocessLine2 = preprocessLine(str2);
        if (DiffRow.Tag.INSERT == tag) {
            if (this.mergeOriginalRevised) {
                strPreprocessLine = this.newTag.apply(tag, true) + strPreprocessLine2 + this.newTag.apply(tag, false);
            } else if (this.showInlineDiffs) {
                strPreprocessLine2 = this.newTag.apply(tag, true) + strPreprocessLine2 + this.newTag.apply(tag, false);
            }
        }
        return new DiffRow(tag, strPreprocessLine, strPreprocessLine2);
    }

    private DiffRow buildDiffRowWithoutNormalizing(DiffRow.Tag tag, String str, String str2) {
        return new DiffRow(tag, StringUtils.wrapText(str, this.columnWidth), StringUtils.wrapText(str2, this.columnWidth));
    }

    List<String> normalizeLines(List<String> list) {
        if (this.reportLinesUnchanged) {
            return list;
        }
        Stream<String> stream = list.stream();
        final Function<String, String> function = this.lineNormalizer;
        function.getClass();
        return (List) stream.map(new Function() { // from class: com.github.difflib.text.DiffRowGenerator$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (String) function.apply((String) obj);
            }
        }).collect(Collectors.toList());
    }

    private List<DiffRow> generateInlineDiffs(AbstractDelta<String> abstractDelta) {
        int i;
        List<String> listNormalizeLines = normalizeLines(abstractDelta.getSource().getLines());
        List<String> listNormalizeLines2 = normalizeLines(abstractDelta.getTarget().getLines());
        String strJoin = String.join("\n", listNormalizeLines);
        String strJoin2 = String.join("\n", listNormalizeLines2);
        List<String> listApply = this.inlineDiffSplitter.apply(strJoin);
        List<String> listApply2 = this.inlineDiffSplitter.apply(strJoin2);
        List deltas = DiffUtils.diff(listApply, listApply2, this.equalizer).getDeltas();
        Collections.reverse(deltas);
        Iterator it = deltas.iterator();
        while (true) {
            i = 0;
            if (!it.hasNext()) {
                break;
            }
            AbstractDelta abstractDelta2 = (AbstractDelta) it.next();
            Chunk source = abstractDelta2.getSource();
            Chunk target = abstractDelta2.getTarget();
            if (abstractDelta2.getType() == DeltaType.DELETE) {
                wrapInTag(listApply, source.getPosition(), source.size() + source.getPosition(), DiffRow.Tag.DELETE, this.oldTag, this.processDiffs, this.replaceOriginalLinefeedInChangesWithSpaces && this.mergeOriginalRevised);
            } else if (abstractDelta2.getType() == DeltaType.INSERT) {
                if (this.mergeOriginalRevised) {
                    listApply.addAll(source.getPosition(), listApply2.subList(target.getPosition(), target.getPosition() + target.size()));
                    wrapInTag(listApply, source.getPosition(), source.getPosition() + target.size(), DiffRow.Tag.INSERT, this.newTag, this.processDiffs, false);
                } else {
                    wrapInTag(listApply2, target.getPosition(), target.getPosition() + target.size(), DiffRow.Tag.INSERT, this.newTag, this.processDiffs, false);
                }
            } else if (abstractDelta2.getType() == DeltaType.CHANGE) {
                if (this.mergeOriginalRevised) {
                    listApply.addAll(source.getPosition() + source.size(), listApply2.subList(target.getPosition(), target.getPosition() + target.size()));
                    wrapInTag(listApply, source.size() + source.getPosition(), source.getPosition() + source.size() + target.size(), DiffRow.Tag.CHANGE, this.newTag, this.processDiffs, false);
                } else {
                    wrapInTag(listApply2, target.getPosition(), target.getPosition() + target.size(), DiffRow.Tag.CHANGE, this.newTag, this.processDiffs, false);
                }
                wrapInTag(listApply, source.getPosition(), source.size() + source.getPosition(), DiffRow.Tag.CHANGE, this.oldTag, this.processDiffs, this.replaceOriginalLinefeedInChangesWithSpaces && this.mergeOriginalRevised);
            }
        }
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        Iterator<String> it2 = listApply.iterator();
        while (it2.hasNext()) {
            sb.append(it2.next());
        }
        Iterator<String> it3 = listApply2.iterator();
        while (it3.hasNext()) {
            sb2.append(it3.next());
        }
        List listAsList = Arrays.asList(sb.toString().split("\n"));
        List listAsList2 = Arrays.asList(sb2.toString().split("\n"));
        ArrayList arrayList = new ArrayList();
        while (i < Math.max(listAsList.size(), listAsList2.size())) {
            DiffRow.Tag tag = DiffRow.Tag.CHANGE;
            String str = "";
            String str2 = listAsList.size() > i ? (String) listAsList.get(i) : "";
            if (listAsList2.size() > i) {
                str = (String) listAsList2.get(i);
            }
            arrayList.add(buildDiffRowWithoutNormalizing(tag, str2, str));
            i++;
        }
        return arrayList;
    }

    private String preprocessLine(String str) {
        if (this.columnWidth == 0) {
            return this.lineNormalizer.apply(str);
        }
        return StringUtils.wrapText(this.lineNormalizer.apply(str), this.columnWidth);
    }

    public static class Builder {
        private int columnWidth;
        private boolean decompressDeltas;
        private BiPredicate<String, String> equalizer;
        private boolean ignoreWhiteSpaces;
        private Function<String, List<String>> inlineDiffSplitter;
        private Function<String, String> lineNormalizer;
        private boolean mergeOriginalRevised;
        private BiFunction<DiffRow.Tag, Boolean, String> newTag;
        private BiFunction<DiffRow.Tag, Boolean, String> oldTag;
        private Function<String, String> processDiffs;
        private boolean replaceOriginalLinefeedInChangesWithSpaces;
        private boolean reportLinesUnchanged;
        private boolean showInlineDiffs;

        /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
            this();
        }

        static /* synthetic */ String lambda$new$0(DiffRow.Tag tag, Boolean bool) {
            return bool.booleanValue() ? "<span class=\"editOldInline\">" : "</span>";
        }

        static /* synthetic */ String lambda$new$1(DiffRow.Tag tag, Boolean bool) {
            return bool.booleanValue() ? "<span class=\"editNewInline\">" : "</span>";
        }

        private Builder() {
            this.showInlineDiffs = false;
            this.ignoreWhiteSpaces = false;
            this.decompressDeltas = true;
            this.oldTag = new BiFunction() { // from class: com.github.difflib.text.DiffRowGenerator$Builder$$ExternalSyntheticLambda0
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return DiffRowGenerator.Builder.lambda$new$0((DiffRow.Tag) obj, (Boolean) obj2);
                }
            };
            this.newTag = new BiFunction() { // from class: com.github.difflib.text.DiffRowGenerator$Builder$$ExternalSyntheticLambda1
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return DiffRowGenerator.Builder.lambda$new$1((DiffRow.Tag) obj, (Boolean) obj2);
                }
            };
            this.columnWidth = 0;
            this.mergeOriginalRevised = false;
            this.reportLinesUnchanged = false;
            this.inlineDiffSplitter = DiffRowGenerator.SPLITTER_BY_CHARACTER;
            this.lineNormalizer = DiffRowGenerator.LINE_NORMALIZER_FOR_HTML;
            this.processDiffs = null;
            this.equalizer = null;
            this.replaceOriginalLinefeedInChangesWithSpaces = false;
        }

        public Builder showInlineDiffs(boolean z) {
            this.showInlineDiffs = z;
            return this;
        }

        public Builder ignoreWhiteSpaces(boolean z) {
            this.ignoreWhiteSpaces = z;
            return this;
        }

        public Builder reportLinesUnchanged(boolean z) {
            this.reportLinesUnchanged = z;
            return this;
        }

        public Builder oldTag(BiFunction<DiffRow.Tag, Boolean, String> biFunction) {
            this.oldTag = biFunction;
            return this;
        }

        static /* synthetic */ String lambda$oldTag$2(Function function, DiffRow.Tag tag, Boolean bool) {
            return (String) function.apply(bool);
        }

        public Builder oldTag(final Function<Boolean, String> function) {
            this.oldTag = new BiFunction() { // from class: com.github.difflib.text.DiffRowGenerator$Builder$$ExternalSyntheticLambda2
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return DiffRowGenerator.Builder.lambda$oldTag$2(function, (DiffRow.Tag) obj, (Boolean) obj2);
                }
            };
            return this;
        }

        public Builder newTag(BiFunction<DiffRow.Tag, Boolean, String> biFunction) {
            this.newTag = biFunction;
            return this;
        }

        static /* synthetic */ String lambda$newTag$3(Function function, DiffRow.Tag tag, Boolean bool) {
            return (String) function.apply(bool);
        }

        public Builder newTag(final Function<Boolean, String> function) {
            this.newTag = new BiFunction() { // from class: com.github.difflib.text.DiffRowGenerator$Builder$$ExternalSyntheticLambda3
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return DiffRowGenerator.Builder.lambda$newTag$3(function, (DiffRow.Tag) obj, (Boolean) obj2);
                }
            };
            return this;
        }

        public Builder processDiffs(Function<String, String> function) {
            this.processDiffs = function;
            return this;
        }

        public Builder columnWidth(int i) {
            if (i >= 0) {
                this.columnWidth = i;
            }
            return this;
        }

        public DiffRowGenerator build() {
            return new DiffRowGenerator(this, null);
        }

        public Builder mergeOriginalRevised(boolean z) {
            this.mergeOriginalRevised = z;
            return this;
        }

        public Builder decompressDeltas(boolean z) {
            this.decompressDeltas = z;
            return this;
        }

        public Builder inlineDiffByWord(boolean z) {
            this.inlineDiffSplitter = z ? DiffRowGenerator.SPLITTER_BY_WORD : DiffRowGenerator.SPLITTER_BY_CHARACTER;
            return this;
        }

        public Builder inlineDiffBySplitter(Function<String, List<String>> function) {
            this.inlineDiffSplitter = function;
            return this;
        }

        public Builder lineNormalizer(Function<String, String> function) {
            this.lineNormalizer = function;
            return this;
        }

        public Builder equalizer(BiPredicate<String, String> biPredicate) {
            this.equalizer = biPredicate;
            return this;
        }

        public Builder replaceOriginalLinefeedInChangesWithSpaces(boolean z) {
            this.replaceOriginalLinefeedInChangesWithSpaces = z;
            return this;
        }
    }
}
