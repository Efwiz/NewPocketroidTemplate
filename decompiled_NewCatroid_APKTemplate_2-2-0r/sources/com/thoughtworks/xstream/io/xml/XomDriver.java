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
import nu.xom.Builder;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/xml/XomDriver.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class XomDriver extends AbstractXmlDriver {
    private final Builder builder;

    public XomDriver() {
        this(new XmlFriendlyNameCoder());
    }

    public XomDriver(Builder builder) {
        this(builder, new XmlFriendlyNameCoder());
    }

    public XomDriver(NameCoder nameCoder) {
        super(nameCoder);
        this.builder = null;
    }

    public XomDriver(Builder builder, NameCoder nameCoder) {
        super(nameCoder);
        this.builder = builder;
    }

    public XomDriver(XmlFriendlyReplacer xmlFriendlyReplacer) {
        this((NameCoder) xmlFriendlyReplacer);
    }

    public XomDriver(Builder builder, XmlFriendlyReplacer xmlFriendlyReplacer) {
        this(builder, (NameCoder) xmlFriendlyReplacer);
    }

    protected Builder getBuilder() {
        return this.builder;
    }

    protected Builder createBuilder() {
        Builder builder = getBuilder();
        return builder != null ? builder : new Builder();
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamReader createReader(Reader reader) {
        try {
            return new XomReader(createBuilder().build(reader), getNameCoder());
        } catch (ParsingException e) {
            throw new StreamException((Throwable) e);
        } catch (ValidityException e2) {
            throw new StreamException((Throwable) e2);
        } catch (IOException e3) {
            throw new StreamException(e3);
        }
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamReader createReader(InputStream inputStream) {
        try {
            return new XomReader(createBuilder().build(inputStream), getNameCoder());
        } catch (ParsingException e) {
            throw new StreamException((Throwable) e);
        } catch (ValidityException e2) {
            throw new StreamException((Throwable) e2);
        } catch (IOException e3) {
            throw new StreamException(e3);
        }
    }

    @Override // com.thoughtworks.xstream.io.AbstractDriver, com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamReader createReader(URL url) {
        try {
            return new XomReader(createBuilder().build(url.toExternalForm()), getNameCoder());
        } catch (ValidityException e) {
            throw new StreamException((Throwable) e);
        } catch (ParsingException e2) {
            throw new StreamException((Throwable) e2);
        } catch (IOException e3) {
            throw new StreamException(e3);
        }
    }

    @Override // com.thoughtworks.xstream.io.AbstractDriver, com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamReader createReader(File file) {
        try {
            return new XomReader(createBuilder().build(file), getNameCoder());
        } catch (ParsingException e) {
            throw new StreamException((Throwable) e);
        } catch (ValidityException e2) {
            throw new StreamException((Throwable) e2);
        } catch (IOException e3) {
            throw new StreamException(e3);
        }
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamWriter createWriter(Writer writer) {
        return new PrettyPrintWriter(writer, getNameCoder());
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamWriter createWriter(OutputStream outputStream) {
        return new PrettyPrintWriter(new OutputStreamWriter(outputStream), getNameCoder());
    }
}
