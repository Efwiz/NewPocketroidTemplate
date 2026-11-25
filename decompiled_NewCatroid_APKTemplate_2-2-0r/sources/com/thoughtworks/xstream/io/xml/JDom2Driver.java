package com.thoughtworks.xstream.io.xml;

import com.thoughtworks.xstream.io.AbstractDriver;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.io.naming.NameCoder;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/xml/JDom2Driver.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class JDom2Driver extends AbstractDriver {
    public JDom2Driver() {
        super(new XmlFriendlyNameCoder());
    }

    public JDom2Driver(NameCoder nameCoder) {
        super(nameCoder);
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamReader createReader(Reader reader) {
        try {
            return new JDom2Reader(createBuilder().build(reader), getNameCoder());
        } catch (IOException e) {
            throw new StreamException(e);
        } catch (JDOMException e2) {
            throw new StreamException((Throwable) e2);
        }
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamReader createReader(InputStream inputStream) {
        try {
            return new JDom2Reader(createBuilder().build(inputStream), getNameCoder());
        } catch (IOException e) {
            throw new StreamException(e);
        } catch (JDOMException e2) {
            throw new StreamException((Throwable) e2);
        }
    }

    @Override // com.thoughtworks.xstream.io.AbstractDriver, com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamReader createReader(URL url) {
        try {
            return new JDom2Reader(createBuilder().build(url), getNameCoder());
        } catch (IOException e) {
            throw new StreamException(e);
        } catch (JDOMException e2) {
            throw new StreamException((Throwable) e2);
        }
    }

    @Override // com.thoughtworks.xstream.io.AbstractDriver, com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamReader createReader(File file) {
        try {
            return new JDom2Reader(createBuilder().build(file), getNameCoder());
        } catch (IOException e) {
            throw new StreamException(e);
        } catch (JDOMException e2) {
            throw new StreamException((Throwable) e2);
        }
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamWriter createWriter(Writer writer) {
        return new PrettyPrintWriter(writer, getNameCoder());
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamWriter createWriter(OutputStream outputStream) {
        return new PrettyPrintWriter(new OutputStreamWriter(outputStream));
    }

    protected SAXBuilder createBuilder() {
        SAXBuilder sAXBuilder = new SAXBuilder();
        sAXBuilder.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        return sAXBuilder;
    }
}
