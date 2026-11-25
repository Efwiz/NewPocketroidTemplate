package com.reandroid.xml.source;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes11.dex */
public interface XMLParserSource {
    XmlPullParser getParser() throws XmlPullParserException;

    String getPath();
}
