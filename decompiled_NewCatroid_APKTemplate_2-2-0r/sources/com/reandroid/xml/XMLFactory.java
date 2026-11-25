package com.reandroid.xml;

import com.reandroid.common.FileChannelInputStream;
import com.reandroid.utils.io.FileUtil;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes11.dex */
public class XMLFactory {
    public static XmlPullParser newPullParser(String str) throws XmlPullParserException {
        XmlPullParser xmlPullParserNewPullParser = newPullParser();
        xmlPullParserNewPullParser.setInput(new StringReader(str));
        return xmlPullParserNewPullParser;
    }

    public static XmlPullParser newPullParser(File file) throws XmlPullParserException {
        XmlPullParser xmlPullParserNewPullParser = newPullParser();
        try {
            xmlPullParserNewPullParser.setInput(new FileChannelInputStream(file), StandardCharsets.UTF_8.name());
            return xmlPullParserNewPullParser;
        } catch (IOException e) {
            throw new XmlPullParserException(e.getMessage());
        }
    }

    public static XmlPullParser newPullParser(Reader reader) throws XmlPullParserException {
        XmlPullParser xmlPullParserNewPullParser = newPullParser();
        xmlPullParserNewPullParser.setInput(reader);
        return xmlPullParserNewPullParser;
    }

    public static XmlPullParser newPullParser(InputStream inputStream) throws XmlPullParserException {
        XmlPullParser xmlPullParserNewPullParser = newPullParser();
        xmlPullParserNewPullParser.setInput(inputStream, StandardCharsets.UTF_8.name());
        return xmlPullParserNewPullParser;
    }

    public static XmlPullParser newPullParser() {
        CloseableParser closeableParser = new CloseableParser();
        try {
            closeableParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
        } catch (Throwable unused) {
        }
        return closeableParser;
    }

    public static XmlSerializer newSerializer(Writer writer) throws IOException {
        XmlSerializer xmlSerializerNewSerializer = newSerializer();
        xmlSerializerNewSerializer.setOutput(writer);
        return xmlSerializerNewSerializer;
    }

    public static XmlSerializer newSerializer(File file) throws IOException {
        return newSerializer(FileUtil.outputStream(file));
    }

    public static XmlSerializer newSerializer(OutputStream outputStream) throws IOException {
        XmlSerializer xmlSerializerNewSerializer = newSerializer();
        xmlSerializerNewSerializer.setOutput(outputStream, StandardCharsets.UTF_8.name());
        return xmlSerializerNewSerializer;
    }

    public static XmlSerializer newSerializer() {
        return new CloseableSerializer();
    }
}
