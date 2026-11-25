package android.content.res;

import android.util.AttributeSet;
import org.xmlpull.v1.XmlPullParser;

/* JADX INFO: loaded from: classes.dex */
public interface XmlResourceParser extends XmlPullParser, AttributeSet, AutoCloseable {
    @Override // java.lang.AutoCloseable
    void close();

    @Override // org.xmlpull.v1.XmlPullParser, android.util.AttributeSet
    String getAttributeNamespace(int i);
}
