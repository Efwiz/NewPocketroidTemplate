package com.thoughtworks.xstream.io.json;

import com.thoughtworks.xstream.io.AbstractDriver;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.io.xml.QNameMap;
import com.thoughtworks.xstream.io.xml.StaxReader;
import com.thoughtworks.xstream.io.xml.StaxWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import javax.xml.stream.XMLStreamException;
import org.codehaus.jettison.mapped.Configuration;
import org.codehaus.jettison.mapped.MappedNamespaceConvention;
import org.codehaus.jettison.mapped.MappedXMLInputFactory;
import org.codehaus.jettison.mapped.MappedXMLOutputFactory;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/json/JettisonMappedXmlDriver.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class JettisonMappedXmlDriver extends AbstractDriver {
    protected final MappedNamespaceConvention convention;
    protected final MappedXMLInputFactory mif;
    protected final MappedXMLOutputFactory mof;
    protected final boolean useSerializeAsArray;

    public JettisonMappedXmlDriver() {
        this(new Configuration());
    }

    public JettisonMappedXmlDriver(Configuration configuration) {
        this(configuration, true);
    }

    public JettisonMappedXmlDriver(Configuration configuration, boolean z) {
        this.mof = new MappedXMLOutputFactory(configuration);
        this.mif = new MappedXMLInputFactory(configuration);
        this.convention = new MappedNamespaceConvention(configuration);
        this.useSerializeAsArray = z;
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamReader createReader(Reader reader) {
        try {
            return new StaxReader(new QNameMap(), this.mif.createXMLStreamReader(reader), getNameCoder());
        } catch (XMLStreamException e) {
            throw new StreamException(e);
        }
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamReader createReader(InputStream inputStream) {
        try {
            return new StaxReader(new QNameMap(), this.mif.createXMLStreamReader(inputStream), getNameCoder());
        } catch (XMLStreamException e) {
            throw new StreamException(e);
        }
    }

    @Override // com.thoughtworks.xstream.io.AbstractDriver, com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamReader createReader(URL url) {
        InputStream inputStreamOpenStream = null;
        try {
            try {
                inputStreamOpenStream = url.openStream();
                StaxReader staxReader = new StaxReader(new QNameMap(), this.mif.createXMLStreamReader(url.toExternalForm(), inputStreamOpenStream), getNameCoder());
                if (inputStreamOpenStream != null) {
                    try {
                        inputStreamOpenStream.close();
                    } catch (IOException unused) {
                    }
                }
                return staxReader;
            } catch (Throwable th) {
                if (inputStreamOpenStream != null) {
                    try {
                        inputStreamOpenStream.close();
                    } catch (IOException unused2) {
                    }
                }
                throw th;
            }
        } catch (IOException e) {
            throw new StreamException(e);
        } catch (XMLStreamException e2) {
            throw new StreamException(e2);
        }
    }

    @Override // com.thoughtworks.xstream.io.AbstractDriver, com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamReader createReader(File file) throws Throwable {
        FileInputStream fileInputStream = null;
        try {
            try {
                FileInputStream fileInputStream2 = new FileInputStream(file);
                try {
                    StaxReader staxReader = new StaxReader(new QNameMap(), this.mif.createXMLStreamReader(file.toURI().toASCIIString(), fileInputStream2), getNameCoder());
                    try {
                        fileInputStream2.close();
                    } catch (IOException unused) {
                    }
                    return staxReader;
                } catch (IOException e) {
                    e = e;
                    throw new StreamException(e);
                } catch (XMLStreamException e2) {
                    e = e2;
                    throw new StreamException(e);
                } catch (Throwable th) {
                    th = th;
                    fileInputStream = fileInputStream2;
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException unused2) {
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e3) {
            e = e3;
        } catch (XMLStreamException e4) {
            e = e4;
        }
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamWriter createWriter(Writer writer) {
        try {
            if (this.useSerializeAsArray) {
                return new JettisonStaxWriter(new QNameMap(), this.mof.createXMLStreamWriter(writer), getNameCoder(), this.convention);
            }
            return new StaxWriter(new QNameMap(), this.mof.createXMLStreamWriter(writer), getNameCoder());
        } catch (XMLStreamException e) {
            throw new StreamException(e);
        }
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamWriter createWriter(OutputStream outputStream) {
        try {
            if (this.useSerializeAsArray) {
                return new JettisonStaxWriter(new QNameMap(), this.mof.createXMLStreamWriter(outputStream), getNameCoder(), this.convention);
            }
            return new StaxWriter(new QNameMap(), this.mof.createXMLStreamWriter(outputStream), getNameCoder());
        } catch (XMLStreamException e) {
            throw new StreamException(e);
        }
    }
}
