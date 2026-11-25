package com.huawei.hms.mlsdk.document.internal.client;

import android.graphics.Point;
import android.graphics.Rect;
import com.huawei.hms.mlsdk.cloud.Coord;
import com.huawei.hms.mlsdk.document.internal.client.bo.Language;
import com.huawei.hms.mlsdk.text.TextLanguage;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class DocumentUtils {
    public static List<Point> coordsToPoints(List<Coord> list, float f) {
        ArrayList arrayList = new ArrayList();
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                Coord coord = list.get(i);
                arrayList.add(new Point((int) (coord.getX() * f), (int) (coord.getY() * f)));
            }
        }
        return arrayList;
    }

    public static Rect createBorder(List<Coord> list, float f) {
        if (list == null || list.size() <= 0) {
            return null;
        }
        int iMax = Integer.MIN_VALUE;
        int iMin = Integer.MAX_VALUE;
        int iMax2 = Integer.MIN_VALUE;
        int iMin2 = Integer.MAX_VALUE;
        for (int i = 0; i < list.size(); i++) {
            Coord coord = list.get(i);
            iMin2 = Math.min(coord.getX(), iMin2);
            iMin = Math.min(coord.getY(), iMin);
            iMax = Math.max(coord.getX(), iMax);
            iMax2 = Math.max(coord.getY(), iMax2);
        }
        return new Rect(Math.round(iMin2 * f), Math.round(iMin * f), Math.round(iMax * f), Math.round(iMax2 * f));
    }

    public static TextLanguage language(Language language) {
        if (language == null || language.getLanguage() == null || language.getLanguage().isEmpty()) {
            return null;
        }
        return new TextLanguage(language.getLanguage());
    }
}
