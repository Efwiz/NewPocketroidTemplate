package com.thoughtworks.xstream.io.xml.xppdom;

import java.io.Reader;
import org.xmlpull.mxp1.MXParser;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/xml/xppdom/Xpp3DomBuilder.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class Xpp3DomBuilder {
    public static Xpp3Dom build(Reader reader) throws Exception {
        MXParser mXParser = new MXParser();
        mXParser.setInput(reader);
        try {
            return (Xpp3Dom) XppDom.build(mXParser);
        } finally {
            reader.close();
        }
    }
}
