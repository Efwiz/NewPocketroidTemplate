package com.reandroid.arsc.pool;

import com.reandroid.arsc.array.OffsetArray;
import com.reandroid.arsc.array.ResXmlStringArray;
import com.reandroid.arsc.array.StringArray;
import com.reandroid.arsc.array.StyleArray;
import com.reandroid.arsc.chunk.xml.ResXmlDocument;
import com.reandroid.arsc.chunk.xml.ResXmlIDMap;
import com.reandroid.arsc.item.IntegerItem;
import com.reandroid.arsc.item.ResXmlString;
import com.reandroid.utils.NumbersUtil;
import com.reandroid.xml.StyleDocument;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes10.dex */
public class ResXmlStringPool extends StringPool<ResXmlString> {
    private static boolean HAS_STYLE_NOTIFIED;

    public ResXmlStringPool(boolean z) {
        super(z, false);
    }

    @Override // com.reandroid.arsc.pool.StringPool
    StringArray<ResXmlString> newInstance(OffsetArray offsetArray, IntegerItem integerItem, IntegerItem integerItem2, boolean z) {
        return new ResXmlStringArray(offsetArray, integerItem, integerItem2, z);
    }

    @Override // com.reandroid.arsc.pool.StringPool
    public ResXmlString getOrCreate(String str) {
        return getOrCreate(0, str);
    }

    @Override // com.reandroid.arsc.pool.StringPool
    public ResXmlString getOrCreate(StyleDocument styleDocument) {
        final String xml = styleDocument.getXml();
        if (!styleDocument.hasElements()) {
            return getOrCreate(0, xml);
        }
        ResXmlString resXmlString = get(xml, new Predicate() { // from class: com.reandroid.arsc.pool.ResXmlStringPool$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ResXmlStringPool.lambda$getOrCreate$0(xml, (ResXmlString) obj);
            }
        });
        if (resXmlString != null) {
            return resXmlString;
        }
        ResXmlString resXmlStringCreateNewString = createNewString();
        resXmlStringCreateNewString.set(styleDocument);
        return resXmlStringCreateNewString;
    }

    static /* synthetic */ boolean lambda$getOrCreate$0(String str, ResXmlString resXmlString) {
        if (!str.equals(resXmlString.getXml()) || resXmlString.hasResourceId()) {
            return false;
        }
        return resXmlString.hasStyle();
    }

    public ResXmlString getOrCreate(final int i, final String str) {
        ResXmlString resXmlString = get(str, new Predicate() { // from class: com.reandroid.arsc.pool.ResXmlStringPool$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ResXmlStringPool.lambda$getOrCreate$1(str, i, (ResXmlString) obj);
            }
        });
        if (resXmlString != null) {
            return resXmlString;
        }
        ResXmlString resXmlStringCreateNewString = createNewString(str);
        resXmlStringCreateNewString.setResourceId(i);
        return resXmlStringCreateNewString;
    }

    static /* synthetic */ boolean lambda$getOrCreate$1(String str, int i, ResXmlString resXmlString) {
        if (!str.equals(resXmlString.getXml()) || resXmlString.hasStyle()) {
            return false;
        }
        return (i == 0) == (resXmlString.getResourceId() == 0);
    }

    private ResXmlIDMap getResXmlIDMap() {
        ResXmlDocument resXmlDocument = (ResXmlDocument) getParentInstance(ResXmlDocument.class);
        if (resXmlDocument != null) {
            return resXmlDocument.getResXmlIDMap();
        }
        return null;
    }

    public void linkResXmlIDMapInternal() {
        ResXmlIDMap resXmlIDMap = getResXmlIDMap();
        if (resXmlIDMap == null) {
            return;
        }
        StringArray<ResXmlString> stringsArray = getStringsArray();
        int iMin = NumbersUtil.min(resXmlIDMap.size(), stringsArray.size());
        for (int i = 0; i < iMin; i++) {
            ((ResXmlString) stringsArray.get(i)).linkResourceIdInternal(resXmlIDMap.get(i));
        }
    }

    @Override // com.reandroid.arsc.pool.StringPool, com.reandroid.arsc.chunk.Chunk
    public void onChunkLoaded() {
        super.onChunkLoaded();
        linkResXmlIDMapInternal();
        StyleArray styleArray = getStyleArray();
        if (styleArray.size() > 0) {
            notifyResXmlStringPoolHasStyles(styleArray.size());
        }
    }

    private static void notifyResXmlStringPoolHasStyles(int i) {
        if (HAS_STYLE_NOTIFIED) {
            return;
        }
        System.err.println("Not expecting ResXmlStringPool to have styles count=" + i + ",\n please create issue along with this apk/file on https://github.com/REAndroid/ARSCLib");
        HAS_STYLE_NOTIFIED = true;
    }
}
