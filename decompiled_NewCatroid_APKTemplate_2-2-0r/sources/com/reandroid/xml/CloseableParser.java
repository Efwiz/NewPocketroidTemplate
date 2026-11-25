package com.reandroid.xml;

import com.reandroid.xml.kxml2.KXmlParser;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes11.dex */
public class CloseableParser extends KXmlParser implements Closeable {
    private InputStream inputStream;
    private Reader reader;

    @Override // com.reandroid.xml.kxml2.KXmlParser, org.xmlpull.v1.XmlPullParser
    public int next() throws XmlPullParserException, IOException {
        int next = super.next();
        if (next == 1) {
            close();
        }
        return next;
    }

    @Override // com.reandroid.xml.kxml2.KXmlParser, org.xmlpull.v1.XmlPullParser
    public int nextToken() throws XmlPullParserException, IOException {
        int iNextToken = super.nextToken();
        if (iNextToken == 1) {
            close();
        }
        return iNextToken;
    }

    @Override // com.reandroid.xml.kxml2.KXmlParser, org.xmlpull.v1.XmlPullParser
    public void setInput(Reader reader) throws XmlPullParserException {
        super.setInput(reader);
        this.reader = reader;
    }

    @Override // com.reandroid.xml.kxml2.KXmlParser, org.xmlpull.v1.XmlPullParser
    public void setInput(InputStream inputStream, String str) throws XmlPullParserException {
        super.setInput(inputStream, str);
        this.inputStream = inputStream;
    }

    @Override // com.reandroid.xml.kxml2.KXmlParser, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        Reader reader = this.reader;
        if (reader != null) {
            reader.close();
            this.reader = null;
        }
        InputStream inputStream = this.inputStream;
        if (inputStream != null) {
            inputStream.close();
            this.inputStream = null;
        }
    }
}
