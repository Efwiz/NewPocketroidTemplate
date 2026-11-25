package com.huawei.hms.mlsdk.document;

import android.graphics.Point;
import android.graphics.Rect;
import com.huawei.hms.mlsdk.document.internal.client.DocumentUtils;
import com.huawei.hms.mlsdk.document.internal.client.bo.Char;
import com.huawei.hms.mlsdk.document.internal.client.bo.Document;
import com.huawei.hms.mlsdk.document.internal.client.bo.Language;
import com.huawei.hms.mlsdk.document.internal.client.bo.Region;
import com.huawei.hms.mlsdk.document.internal.client.bo.Term;
import com.huawei.hms.mlsdk.text.TextLanguage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class MLDocument {
    private List<Block> blocks;
    private String textString;

    public static class Base {
        private final Rect border;
        private final Interval interval;
        private final List<TextLanguage> languages;
        private final float possibility;
        private final String textString;

        Base(List<TextLanguage> list, Interval interval, Rect rect, String str, float f) {
            this.textString = str;
            this.languages = list;
            this.interval = interval;
            this.border = rect;
            this.possibility = f;
        }

        public Rect getBorder() {
            return this.border;
        }

        public Interval getInterval() {
            return this.interval;
        }

        public List<TextLanguage> getLanguageList() {
            return this.languages;
        }

        public Float getPossibility() {
            return Float.valueOf(this.possibility);
        }

        public String getStringValue() {
            return this.textString;
        }
    }

    public static class Block extends Base {
        private final List<Section> sections;

        private Block(List<TextLanguage> list, Interval interval, Rect rect, List<Section> list2, String str, float f) {
            super(list, interval, rect, str, f);
            this.sections = list2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static Block createChapter(Region region, float f) {
            ArrayList arrayList = new ArrayList();
            StringBuilder sb = new StringBuilder();
            ArrayList arrayList2 = new ArrayList();
            boolean z = false;
            if (region.getSections() != null) {
                List<com.huawei.hms.mlsdk.document.internal.client.bo.Section> sections = region.getSections();
                for (int i = 0; i < sections.size(); i++) {
                    if (sections.get(i) != null) {
                        Section sectionCreateSection = Section.createSection(sections.get(i), f);
                        if (sb.length() != 0) {
                            sb.append(System.lineSeparator());
                        }
                        sb.append(sectionCreateSection.getStringValue());
                        arrayList.add(sectionCreateSection);
                        arrayList2.removeAll(sectionCreateSection.getLanguageList());
                        arrayList2.addAll(sectionCreateSection.getLanguageList());
                    }
                }
            }
            return new Block(arrayList2, new Interval(8, z), DocumentUtils.createBorder(region.getCoords(), f), arrayList, sb.toString(), (float) region.getConfidence());
        }

        public List<Section> getSections() {
            return this.sections;
        }
    }

    public static class Character extends Base {
        private Character(List<TextLanguage> list, Interval interval, Rect rect, String str, float f) {
            super(list, interval, rect, str, f);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static Character createCharacter(Char r7, float f) {
            return new Character(MLDocument.createLanguageList(r7.getLanguages()), Interval.createInterval(r7.getBreakType()), DocumentUtils.createBorder(r7.getCoords(), f), r7.getValue(), (float) r7.getConfidence());
        }
    }

    public static class Interval {
        public static final int END_OF_LINE_HYPHEN = 9;
        public static final int NEW_LINE_CHARACTER = 8;
        public static final int OTHER = 5;
        public static final int SESSION_INTERVAL = 10;
        public static final int SPACE = 6;
        public static final int WIDE_SPACE = 7;
        private final boolean isTextFollowed;
        private final int type;

        /* JADX INFO: Access modifiers changed from: private */
        public static Interval createInterval(String str) {
            int i;
            if (str == null) {
                return null;
            }
            str.hashCode();
            if (str.equals("SPACE")) {
                i = 6;
            } else {
                i = !str.equals("BR") ? 5 : 8;
            }
            return new Interval(i, false);
        }

        public int getIntervalType() {
            return this.type;
        }

        public boolean isTextFollowed() {
            return this.isTextFollowed;
        }

        private Interval(int i, boolean z) {
            this.type = i;
            this.isTextFollowed = z;
        }
    }

    public static class Line extends Base {
        private final List<Point> points;
        private final List<Word> words;

        private Line(List<TextLanguage> list, Interval interval, Rect rect, List<Word> list2, List<Point> list3, String str, float f) {
            super(list, interval, rect, str, f);
            this.words = list2;
            this.points = list3;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static Line createLine(com.huawei.hms.mlsdk.document.internal.client.bo.Line line, float f) {
            StringBuilder sb = new StringBuilder();
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            List<Point> listCoordsToPoints = DocumentUtils.coordsToPoints(line.getCoords(), f);
            Interval interval = new Interval(8, false);
            List<Term> terms = line.getTerms();
            if (terms != null) {
                Iterator<Term> it = terms.iterator();
                while (it.hasNext()) {
                    Word wordCreateWord = Word.createWord(it.next(), f);
                    arrayList2.add(wordCreateWord);
                    sb.append(wordCreateWord.getStringValue());
                    if (wordCreateWord.getInterval() != null && wordCreateWord.getInterval().getIntervalType() == 6) {
                        sb.append(" ");
                    }
                    arrayList.removeAll(wordCreateWord.getLanguageList());
                    arrayList.addAll(wordCreateWord.getLanguageList());
                }
            }
            return new Line(arrayList, interval, DocumentUtils.createBorder(line.getCoords(), f), arrayList2, listCoordsToPoints, sb.toString(), (float) line.getConfidence());
        }

        public List<Point> getPoints() {
            return this.points;
        }

        public List<Word> getWordList() {
            return this.words;
        }
    }

    public static class Section extends Base {
        private final List<Line> lines;
        private final List<Word> words;

        private Section(List<TextLanguage> list, Interval interval, Rect rect, List<Word> list2, List<Line> list3, String str, float f) {
            super(list, interval, rect, str, f);
            this.words = list2;
            this.lines = list3;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static Section createSection(com.huawei.hms.mlsdk.document.internal.client.bo.Section section, float f) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            StringBuilder sb = new StringBuilder();
            ArrayList arrayList3 = new ArrayList();
            List<com.huawei.hms.mlsdk.document.internal.client.bo.Line> lines = section.getLines();
            boolean z = false;
            if (lines != null && lines.size() > 0) {
                for (int i = 0; i < lines.size(); i++) {
                    Line lineCreateLine = Line.createLine(lines.get(i), f);
                    arrayList2.add(lineCreateLine);
                    arrayList3.removeAll(lineCreateLine.getLanguageList());
                    arrayList3.addAll(lineCreateLine.getLanguageList());
                    sb.append(lineCreateLine.getStringValue());
                    sb.append(System.lineSeparator());
                    arrayList.addAll(lineCreateLine.getWordList());
                }
            }
            return new Section(arrayList3, new Interval(8, z), DocumentUtils.createBorder(section.getCoords(), f), arrayList, arrayList2, sb.toString(), (float) section.getConfidence());
        }

        public List<Line> getLineList() {
            return this.lines;
        }

        public List<Word> getWordList() {
            return this.words;
        }
    }

    public static class Word extends Base {
        private final List<Character> characters;

        private Word(List<TextLanguage> list, Interval interval, Rect rect, List<Character> list2, String str, float f) {
            super(list, interval, rect, str, f);
            this.characters = list2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static Word createWord(Term term, float f) {
            ArrayList arrayList = new ArrayList();
            StringBuilder sb = new StringBuilder();
            List listCreateLanguageList = MLDocument.createLanguageList(term.getLanguages());
            List<Char> chars = term.getChars();
            Interval interval = null;
            if (chars != null && chars.size() > 0) {
                int i = 0;
                while (i < chars.size()) {
                    Character characterCreateCharacter = Character.createCharacter(chars.get(i), f);
                    Interval interval2 = characterCreateCharacter.getInterval();
                    sb.append(characterCreateCharacter.getStringValue());
                    arrayList.add(characterCreateCharacter);
                    i++;
                    interval = interval2;
                }
            }
            return new Word(listCreateLanguageList, interval, DocumentUtils.createBorder(term.getCoords(), f), arrayList, sb.toString(), (float) term.getConfidence());
        }

        public List<Character> getCharacterList() {
            return this.characters;
        }
    }

    MLDocument(String str, List<Block> list) {
        this.textString = str;
        this.blocks = list;
    }

    static MLDocument createDocument(List<Document> list, float f) {
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Document document = list.get(i);
                sb.append(document.getDoc());
                sb.append(System.lineSeparator());
                List<Region> regions = document.getRegions();
                for (int i2 = 0; i2 < regions.size(); i2++) {
                    Region region = regions.get(i2);
                    if (region != null) {
                        arrayList.add(Block.createChapter(region, f));
                    }
                }
            }
        }
        return new MLDocument(sb.toString(), arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static List<TextLanguage> createLanguageList(List<Language> list) {
        if (list == null || list.size() == 0) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            arrayList.add(DocumentUtils.language(list.get(i)));
        }
        return arrayList;
    }

    public List<Block> getBlocks() {
        return this.blocks;
    }

    public String getStringValue() {
        String str = this.textString;
        return str == null ? "" : str;
    }
}
