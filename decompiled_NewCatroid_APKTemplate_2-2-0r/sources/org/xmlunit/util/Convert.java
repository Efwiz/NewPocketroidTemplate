package org.xmlunit.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlunit.ConfigurationException;
import org.xmlunit.XMLUnitException;

/* JADX INFO: loaded from: classes3.dex */
public final class Convert {
    private Convert() {
    }

    public static InputSource toInputSource(Source source) {
        return toInputSource(source, null);
    }

    public static InputSource toInputSource(Source source, TransformerFactory transformerFactory) {
        try {
            InputSource inputSourceSourceToInputSource = SAXSource.sourceToInputSource(source);
            if (inputSourceSourceToInputSource != null) {
                return inputSourceSourceToInputSource;
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            StreamResult streamResult = new StreamResult(byteArrayOutputStream);
            if (transformerFactory == null) {
                transformerFactory = TransformerFactoryConfigurer.NoExternalAccess.configure(TransformerFactory.newInstance());
            }
            transformerFactory.newTransformer().transform(source, streamResult);
            return SAXSource.sourceToInputSource(new StreamSource(new ByteArrayInputStream(byteArrayOutputStream.toByteArray())));
        } catch (TransformerConfigurationException e) {
            throw new ConfigurationException(e);
        } catch (TransformerException e2) {
            throw new XMLUnitException(e2);
        }
    }

    public static Document toDocument(Source source) {
        Document documentTryExtractDocFromDOMSource = tryExtractDocFromDOMSource(source);
        return documentTryExtractDocFromDOMSource != null ? documentTryExtractDocFromDOMSource : toDocument(source, DocumentBuilderFactoryConfigurer.Default.configure(DocumentBuilderFactory.newInstance()));
    }

    public static Document toDocument(Source source, DocumentBuilderFactory documentBuilderFactory) {
        Document documentTryExtractDocFromDOMSource = tryExtractDocFromDOMSource(source);
        if (documentTryExtractDocFromDOMSource != null) {
            return documentTryExtractDocFromDOMSource;
        }
        InputSource inputSource = toInputSource(source);
        boolean zIsNamespaceAware = documentBuilderFactory.isNamespaceAware();
        try {
            if (!zIsNamespaceAware) {
                try {
                    documentBuilderFactory.setNamespaceAware(true);
                } catch (ParserConfigurationException e) {
                    throw new ConfigurationException(e);
                }
            }
            DocumentBuilder documentBuilderNewDocumentBuilder = documentBuilderFactory.newDocumentBuilder();
            if (!zIsNamespaceAware) {
                documentBuilderFactory.setNamespaceAware(false);
            }
            try {
                return documentBuilderNewDocumentBuilder.parse(inputSource);
            } catch (IOException e2) {
                throw new XMLUnitException(e2);
            } catch (SAXException e3) {
                throw new XMLUnitException(e3);
            }
        } catch (Throwable th) {
            if (!zIsNamespaceAware) {
                documentBuilderFactory.setNamespaceAware(false);
            }
            throw th;
        }
    }

    private static Document tryExtractDocFromDOMSource(Source source) {
        Node nodeTryExtractNodeFromDOMSource = tryExtractNodeFromDOMSource(source);
        if (nodeTryExtractNodeFromDOMSource == null || !(nodeTryExtractNodeFromDOMSource instanceof Document)) {
            return null;
        }
        return (Document) nodeTryExtractNodeFromDOMSource;
    }

    public static Node toNode(Source source) {
        Node nodeTryExtractNodeFromDOMSource = tryExtractNodeFromDOMSource(source);
        return nodeTryExtractNodeFromDOMSource != null ? nodeTryExtractNodeFromDOMSource : toDocument(source, DocumentBuilderFactoryConfigurer.Default.configure(DocumentBuilderFactory.newInstance()));
    }

    public static Node toNode(Source source, DocumentBuilderFactory documentBuilderFactory) {
        Node nodeTryExtractNodeFromDOMSource = tryExtractNodeFromDOMSource(source);
        return nodeTryExtractNodeFromDOMSource != null ? nodeTryExtractNodeFromDOMSource : toDocument(source, documentBuilderFactory);
    }

    private static Node tryExtractNodeFromDOMSource(Source source) {
        if (source instanceof DOMSource) {
            return ((DOMSource) source).getNode();
        }
        return null;
    }

    public static NamespaceContext toNamespaceContext(Map<String, String> map) {
        final LinkedHashMap linkedHashMap = new LinkedHashMap(map);
        return new NamespaceContext() { // from class: org.xmlunit.util.Convert.1
            @Override // javax.xml.namespace.NamespaceContext
            public String getNamespaceURI(String str) {
                if (str == null) {
                    throw new IllegalArgumentException("prefix must not be null");
                }
                if ("xml".equals(str)) {
                    return "http://www.w3.org/XML/1998/namespace";
                }
                if (XMLConstants.XMLNS_ATTRIBUTE.equals(str)) {
                    return XMLConstants.XMLNS_ATTRIBUTE_NS_URI;
                }
                String str2 = (String) linkedHashMap.get(str);
                return str2 != null ? str2 : "";
            }

            @Override // javax.xml.namespace.NamespaceContext
            public String getPrefix(String str) {
                Iterator prefixes = getPrefixes(str);
                if (prefixes.hasNext()) {
                    return (String) prefixes.next();
                }
                return null;
            }

            @Override // javax.xml.namespace.NamespaceContext
            public Iterator getPrefixes(String str) {
                boolean z;
                if (str == null) {
                    throw new IllegalArgumentException("uri must not be null");
                }
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                boolean z2 = true;
                if ("http://www.w3.org/XML/1998/namespace".equals(str)) {
                    linkedHashSet.add("xml");
                    z = true;
                } else {
                    z = false;
                }
                if (XMLConstants.XMLNS_ATTRIBUTE_NS_URI.equals(str)) {
                    linkedHashSet.add(XMLConstants.XMLNS_ATTRIBUTE);
                } else {
                    z2 = z;
                }
                if (!z2) {
                    for (Map.Entry entry : linkedHashMap.entrySet()) {
                        if (str.equals(entry.getValue())) {
                            linkedHashSet.add(entry.getKey());
                        }
                    }
                }
                return linkedHashSet.iterator();
            }
        };
    }
}
