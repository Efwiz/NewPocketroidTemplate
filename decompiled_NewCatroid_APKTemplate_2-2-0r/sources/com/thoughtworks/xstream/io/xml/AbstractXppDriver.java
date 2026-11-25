package com.thoughtworks.xstream.io.xml;

import com.thoughtworks.xstream.core.util.XmlHeaderAwareReader;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.io.naming.NameCoder;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/xml/AbstractXppDriver.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public abstract class AbstractXppDriver extends AbstractXmlDriver {
    protected abstract XmlPullParser createParser() throws XmlPullParserException;

    public AbstractXppDriver(NameCoder nameCoder) {
        super(nameCoder);
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamReader createReader(Reader reader) {
        try {
            return new XppReader(reader, createParser(), getNameCoder());
        } catch (XmlPullParserException e) {
            throw new StreamException("Cannot create XmlPullParser", e);
        }
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamReader createReader(InputStream inputStream) {
        try {
            return createReader(new XmlHeaderAwareReader(inputStream));
        } catch (UnsupportedEncodingException e) {
            throw new StreamException(e);
        } catch (IOException e2) {
            throw new StreamException(e2);
        }
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamWriter createWriter(Writer writer) {
        return new PrettyPrintWriter(writer, getNameCoder());
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamWriter createWriter(OutputStream outputStream) {
        return createWriter(new OutputStreamWriter(outputStream));
    }
}
