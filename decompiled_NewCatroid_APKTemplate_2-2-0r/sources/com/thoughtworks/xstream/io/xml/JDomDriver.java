package com.thoughtworks.xstream.io.xml;

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
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/xml/JDomDriver.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class JDomDriver extends AbstractXmlDriver {
    public JDomDriver() {
        super(new XmlFriendlyNameCoder());
    }

    public JDomDriver(NameCoder nameCoder) {
        super(nameCoder);
    }

    public JDomDriver(XmlFriendlyReplacer xmlFriendlyReplacer) {
        this((NameCoder) xmlFriendlyReplacer);
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamReader createReader(Reader reader) {
        try {
            return new JDomReader(createBuilder().build(reader), getNameCoder());
        } catch (IOException e) {
            throw new StreamException(e);
        } catch (JDOMException e2) {
            throw new StreamException((Throwable) e2);
        }
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamReader createReader(InputStream inputStream) {
        try {
            return new JDomReader(createBuilder().build(inputStream), getNameCoder());
        } catch (IOException e) {
            throw new StreamException(e);
        } catch (JDOMException e2) {
            throw new StreamException((Throwable) e2);
        }
    }

    @Override // com.thoughtworks.xstream.io.AbstractDriver, com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamReader createReader(URL url) {
        try {
            return new JDomReader(createBuilder().build(url), getNameCoder());
        } catch (IOException e) {
            throw new StreamException(e);
        } catch (JDOMException e2) {
            throw new StreamException((Throwable) e2);
        }
    }

    @Override // com.thoughtworks.xstream.io.AbstractDriver, com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamReader createReader(File file) {
        try {
            return new JDomReader(createBuilder().build(file), getNameCoder());
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
