package com.huawei.hms.mlsdk.text;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.SparseArray;
import com.google.mlkit.nl.languageid.LanguageIdentifier;
import com.huawei.hms.ml.common.base.SmartLog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes10.dex */
public class MLText {
    private final List<Block> blocks;
    private final String text;

    public static class Base implements Text {
        private List<TextLanguage> languageList;
        private float possibility;
        private Rect rect;
        private String text;
        private Point[] vertexes;

        @Override // com.huawei.hms.mlsdk.text.MLText.Text
        public Rect getBorder() {
            return this.rect;
        }

        @Override // com.huawei.hms.mlsdk.text.MLText.Text
        public List<? extends Base> getContents() {
            return null;
        }

        @Override // com.huawei.hms.mlsdk.text.MLText.Text
        public String getLanguage() {
            int iIntValue;
            List<TextLanguage> list = this.languageList;
            if (list == null || list.isEmpty()) {
                return LanguageIdentifier.UNDETERMINED_LANGUAGE_TAG;
            }
            HashMap map = new HashMap();
            Iterator<TextLanguage> it = this.languageList.iterator();
            while (true) {
                iIntValue = 0;
                if (!it.hasNext()) {
                    break;
                }
                String language = it.next().getLanguage();
                if (map.containsKey(language)) {
                    iIntValue = ((Integer) map.get(language)).intValue();
                }
                map.put(language, Integer.valueOf(iIntValue + 1));
            }
            String str = "";
            for (Map.Entry entry : map.entrySet()) {
                if (iIntValue < ((Integer) entry.getValue()).intValue()) {
                    iIntValue = ((Integer) entry.getValue()).intValue();
                    str = (String) entry.getKey();
                }
            }
            return (str == null || str.isEmpty()) ? LanguageIdentifier.UNDETERMINED_LANGUAGE_TAG : str;
        }

        @Override // com.huawei.hms.mlsdk.text.MLText.Text
        public List<TextLanguage> getLanguageList() {
            return this.languageList;
        }

        @Override // com.huawei.hms.mlsdk.text.MLText.Text
        public Float getPossibility() {
            float f = this.possibility;
            if (f == 0.0f) {
                return null;
            }
            return Float.valueOf(f);
        }

        @Override // com.huawei.hms.mlsdk.text.MLText.Text
        public String getStringValue() {
            String str = this.text;
            return str == null ? "" : str;
        }

        @Override // com.huawei.hms.mlsdk.text.MLText.Text
        public Point[] getVertexes() {
            Point[] pointArr = this.vertexes;
            return pointArr != null ? (Point[]) Arrays.copyOf(pointArr, pointArr.length) : new Point[0];
        }

        private Base(String str, Rect rect, List<TextLanguage> list, Point[] pointArr, float f) {
            this.possibility = f;
            this.vertexes = pointArr;
            this.text = str;
            this.rect = rect;
            this.languageList = list;
        }
    }

    public static class Block extends Base {
        private final List<TextLine> textLines;

        public Block(String str, Rect rect, List<TextLanguage> list, List<TextLine> list2, Point[] pointArr, float f) {
            super(str, rect, list, pointArr, f);
            this.textLines = list2;
        }

        @Override // com.huawei.hms.mlsdk.text.MLText.Base, com.huawei.hms.mlsdk.text.MLText.Text
        public List<TextLine> getContents() {
            return this.textLines;
        }
    }

    public interface Text {
        Rect getBorder();

        List<? extends Text> getContents();

        String getLanguage();

        List<TextLanguage> getLanguageList();

        Float getPossibility();

        String getStringValue();

        Point[] getVertexes();
    }

    public static class TextLine extends Base {
        private final List<Word> words;

        public TextLine(String str, Rect rect, List<TextLanguage> list, List<Word> list2, Point[] pointArr, float f) {
            super(str, rect, list, pointArr, f);
            this.words = list2;
        }

        @Override // com.huawei.hms.mlsdk.text.MLText.Base, com.huawei.hms.mlsdk.text.MLText.Text
        public synchronized List<Word> getContents() {
            return this.words;
        }

        public float getRotatingDegree() {
            Point[] vertexes = getVertexes();
            float fAtan2 = (vertexes == null || vertexes.length < 2) ? 0.0f : (float) ((Math.atan2(vertexes[1].y - vertexes[0].y, vertexes[1].x - vertexes[0].x) * 180.0d) / 3.141592653589793d);
            SmartLog.d("MLText-Angle:", String.valueOf(fAtan2));
            return fAtan2;
        }

        public boolean isVertical() {
            return false;
        }
    }

    public static class Word extends Base {
        public Word(String str, Rect rect, List<TextLanguage> list, Point[] pointArr, float f) {
            super(str, rect, list, pointArr, f);
        }

        @Override // com.huawei.hms.mlsdk.text.MLText.Base, com.huawei.hms.mlsdk.text.MLText.Text
        public List<? extends Base> getContents() {
            return null;
        }
    }

    public MLText() {
        this.blocks = new ArrayList();
        this.text = "";
    }

    public List<Block> getBlocks() {
        return Collections.unmodifiableList(this.blocks);
    }

    public String getStringValue() {
        return this.text;
    }

    public MLText(String str, SparseArray<Block> sparseArray) {
        this.blocks = new ArrayList();
        this.text = str == null ? "" : str;
        for (int i = 0; i < sparseArray.size(); i++) {
            Block block = sparseArray.get(sparseArray.keyAt(i));
            if (block != null) {
                this.blocks.add(block);
            }
        }
    }

    public MLText(String str, List<Block> list) {
        ArrayList arrayList = new ArrayList();
        this.blocks = arrayList;
        this.text = str;
        arrayList.addAll(list);
    }
}
