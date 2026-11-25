package com.thoughtworks.xstream.io.json;

import com.thoughtworks.xstream.io.naming.NameCoder;
import com.thoughtworks.xstream.io.xml.QNameMap;
import com.thoughtworks.xstream.io.xml.StaxWriter;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.codehaus.jettison.AbstractXMLStreamWriter;
import org.codehaus.jettison.mapped.MappedNamespaceConvention;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/json/JettisonStaxWriter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class JettisonStaxWriter extends StaxWriter {
    static /* synthetic */ Class class$java$util$Collection;
    static /* synthetic */ Class class$java$util$Map;
    private final MappedNamespaceConvention convention;

    public JettisonStaxWriter(QNameMap qNameMap, XMLStreamWriter xMLStreamWriter, boolean z, boolean z2, NameCoder nameCoder, MappedNamespaceConvention mappedNamespaceConvention) throws XMLStreamException {
        super(qNameMap, xMLStreamWriter, z, z2, nameCoder);
        this.convention = mappedNamespaceConvention;
    }

    public JettisonStaxWriter(QNameMap qNameMap, XMLStreamWriter xMLStreamWriter, boolean z, boolean z2, XmlFriendlyReplacer xmlFriendlyReplacer, MappedNamespaceConvention mappedNamespaceConvention) throws XMLStreamException {
        this(qNameMap, xMLStreamWriter, z, z2, (NameCoder) xmlFriendlyReplacer, mappedNamespaceConvention);
    }

    public JettisonStaxWriter(QNameMap qNameMap, XMLStreamWriter xMLStreamWriter, boolean z, boolean z2, MappedNamespaceConvention mappedNamespaceConvention) throws XMLStreamException {
        super(qNameMap, xMLStreamWriter, z, z2);
        this.convention = mappedNamespaceConvention;
    }

    public JettisonStaxWriter(QNameMap qNameMap, XMLStreamWriter xMLStreamWriter, MappedNamespaceConvention mappedNamespaceConvention) throws XMLStreamException {
        super(qNameMap, xMLStreamWriter);
        this.convention = mappedNamespaceConvention;
    }

    public JettisonStaxWriter(QNameMap qNameMap, XMLStreamWriter xMLStreamWriter, NameCoder nameCoder, MappedNamespaceConvention mappedNamespaceConvention) throws XMLStreamException {
        super(qNameMap, xMLStreamWriter, nameCoder);
        this.convention = mappedNamespaceConvention;
    }

    /* JADX WARN: Code duplicated, block: B:18:0x0034  */
    /* JADX WARN: Code duplicated, block: B:20:0x005e  */
    @Override // com.thoughtworks.xstream.io.AbstractWriter, com.thoughtworks.xstream.io.ExtendedHierarchicalStreamWriter
    public void startNode(String str, Class cls) throws Throwable {
        String strCreateKey;
        AbstractXMLStreamWriter abstractXMLStreamWriter;
        AbstractXMLStreamWriter xMLStreamWriter = getXMLStreamWriter();
        if (cls != null && (xMLStreamWriter instanceof AbstractXMLStreamWriter)) {
            Class clsClass$ = class$java$util$Collection;
            if (clsClass$ == null) {
                clsClass$ = class$(CommonClassNames.JAVA_UTIL_COLLECTION);
                class$java$util$Collection = clsClass$;
            }
            if (!clsClass$.isAssignableFrom(cls)) {
                Class clsClass$2 = class$java$util$Map;
                if (clsClass$2 == null) {
                    clsClass$2 = class$(CommonClassNames.JAVA_UTIL_MAP);
                    class$java$util$Map = clsClass$2;
                }
                if (clsClass$2.isAssignableFrom(cls) || cls.isArray()) {
                    QName qName = getQNameMap().getQName(encodeNode(str));
                    strCreateKey = this.convention.createKey(qName.getPrefix(), qName.getNamespaceURI(), qName.getLocalPart());
                    abstractXMLStreamWriter = xMLStreamWriter;
                    if (!abstractXMLStreamWriter.getSerializedAsArrays().contains(strCreateKey)) {
                        abstractXMLStreamWriter.seriliazeAsArray(strCreateKey);
                    }
                }
            } else {
                QName qName2 = getQNameMap().getQName(encodeNode(str));
                strCreateKey = this.convention.createKey(qName2.getPrefix(), qName2.getNamespaceURI(), qName2.getLocalPart());
                abstractXMLStreamWriter = xMLStreamWriter;
                if (!abstractXMLStreamWriter.getSerializedAsArrays().contains(strCreateKey)) {
                    abstractXMLStreamWriter.seriliazeAsArray(strCreateKey);
                }
            }
        }
        startNode(str);
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }
}
