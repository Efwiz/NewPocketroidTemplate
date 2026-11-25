package org.xmlpull.v1;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xmlpull-1.1.3.1.jar:org/xmlpull/v1/XmlPullParserException.class
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xpp3_min-1.1.4c.jar:org/xmlpull/v1/XmlPullParserException.class
  classes3.dex
 */
/* JADX INFO: loaded from: classes3.dex */
public class XmlPullParserException extends Exception {
    protected int column;
    protected Throwable detail;
    protected int row;

    public XmlPullParserException(String str) {
        super(str);
        this.row = -1;
        this.column = -1;
    }

    public XmlPullParserException(String str, XmlPullParser xmlPullParser, Throwable th) {
        super((str == null ? "" : str + " ") + (xmlPullParser == null ? "" : "(position:" + xmlPullParser.getPositionDescription() + ") ") + (th != null ? "caused by: " + th : ""));
        this.row = -1;
        this.column = -1;
        if (xmlPullParser != null) {
            this.row = xmlPullParser.getLineNumber();
            this.column = xmlPullParser.getColumnNumber();
        }
        this.detail = th;
    }

    public Throwable getDetail() {
        return this.detail;
    }

    public int getLineNumber() {
        return this.row;
    }

    public int getColumnNumber() {
        return this.column;
    }

    @Override // java.lang.Throwable
    public void printStackTrace() {
        if (this.detail == null) {
            super.printStackTrace();
            return;
        }
        synchronized (System.err) {
            System.err.println(super.getMessage() + "; nested exception is:");
            this.detail.printStackTrace();
        }
    }
}
