package org.jetbrains.kotlin.com.intellij.util;

import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.xml.stream.XMLStreamException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.fasterxml.aalto.WFCException;
import org.jetbrains.kotlin.com.fasterxml.aalto.impl.ErrorConsts;
import org.jetbrains.kotlin.com.intellij.openapi.util.StaxFactory;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.jetbrains.kotlin.org.codehaus.stax2.XMLStreamReader2;

/* JADX INFO: compiled from: xmlDom.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0002\b\u0002\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u001a$\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0002\u001a\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007\u001a\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\rH\u0007\u001a\"\u0010\t\u001a\u00020\n2\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u000e\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007¨\u0006\u000f"}, d2 = {"nextTag", "", "reader", "Lorg/jetbrains/kotlin/org/codehaus/stax2/XMLStreamReader2;", "readAttributes", "", "", "interner", "Lorg/jetbrains/kotlin/com/intellij/util/XmlInterner;", "readXmlAsModel", "Lorg/jetbrains/kotlin/com/intellij/util/XmlElement;", "inputStream", "Ljava/io/InputStream;", "", "rootName", "intellij.platform.util.xmlDom"}, k = 2, mv = {1, 5, 1})
public final class XmlDomReader {
    public static final XmlElement readXmlAsModel(InputStream inputStream) throws XMLStreamException {
        Intrinsics.checkNotNullParameter(inputStream, "inputStream");
        XMLStreamReader2 xMLStreamReader2CreateXmlStreamReader = StaxFactory.createXmlStreamReader(inputStream);
        try {
            return readXmlAsModel(xMLStreamReader2CreateXmlStreamReader, nextTag(xMLStreamReader2CreateXmlStreamReader) == 1 ? xMLStreamReader2CreateXmlStreamReader.getLocalName() : null, NoOpXmlInterner.INSTANCE);
        } finally {
            xMLStreamReader2CreateXmlStreamReader.closeCompletely();
        }
    }

    public static final XmlElement readXmlAsModel(byte[] inputStream) throws XMLStreamException {
        Intrinsics.checkNotNullParameter(inputStream, "inputStream");
        XMLStreamReader2 xMLStreamReader2CreateXmlStreamReader = StaxFactory.createXmlStreamReader(inputStream);
        try {
            return readXmlAsModel(xMLStreamReader2CreateXmlStreamReader, nextTag(xMLStreamReader2CreateXmlStreamReader) == 1 ? xMLStreamReader2CreateXmlStreamReader.getLocalName() : null, NoOpXmlInterner.INSTANCE);
        } finally {
            xMLStreamReader2CreateXmlStreamReader.close();
        }
    }

    public static final XmlElement readXmlAsModel(XMLStreamReader2 reader, String str, XmlInterner interner) throws XMLStreamException {
        List listAsList;
        Intrinsics.checkNotNullParameter(reader, "reader");
        Intrinsics.checkNotNullParameter(interner, "interner");
        XmlElementBuilder xmlElementBuilder = new XmlElementBuilder(str == null ? "" : interner.name(str), readAttributes(reader, interner));
        ArrayDeque arrayDeque = new ArrayDeque();
        ArrayDeque arrayDeque2 = new ArrayDeque();
        int i = 1;
        while (reader.hasNext()) {
            int next = reader.next();
            if (next != 9) {
                if (next != 12) {
                    switch (next) {
                        case 1:
                            String localName = reader.getLocalName();
                            Intrinsics.checkNotNullExpressionValue(localName, "reader.localName");
                            String strName = interner.name(localName);
                            Map<String, String> attributes = readAttributes(reader, interner);
                            if (reader.isEmptyElement()) {
                                ArrayList<XmlElement> arrayList = xmlElementBuilder.children;
                                List listEmptyList = Collections.emptyList();
                                Intrinsics.checkNotNullExpressionValue(listEmptyList, "Collections.emptyList()");
                                arrayList.add(new XmlElement(strName, attributes, listEmptyList, null));
                                reader.skipElement();
                            } else {
                                XmlElementBuilder xmlElementBuilder2 = (XmlElementBuilder) arrayDeque2.pollLast();
                                if (xmlElementBuilder2 == null) {
                                    xmlElementBuilder2 = new XmlElementBuilder(strName, attributes);
                                } else {
                                    xmlElementBuilder2.name = strName;
                                    xmlElementBuilder2.attributes = attributes;
                                }
                                arrayDeque.addLast(xmlElementBuilder);
                                i++;
                                xmlElementBuilder = xmlElementBuilder2;
                            }
                            break;
                        case 2:
                            if (xmlElementBuilder.children.isEmpty()) {
                                listAsList = Collections.emptyList();
                                Intrinsics.checkNotNullExpressionValue(listAsList, "Collections.emptyList()");
                            } else {
                                XmlElement[] xmlElementArr = (XmlElement[]) xmlElementBuilder.children.toArray(new XmlElement[xmlElementBuilder.children.size()]);
                                listAsList = Arrays.asList((XmlElement[]) Arrays.copyOf(xmlElementArr, xmlElementArr.length));
                                Intrinsics.checkNotNullExpressionValue(listAsList, "Arrays.asList(*current.c…(current.children.size)))");
                                xmlElementBuilder.children.clear();
                            }
                            XmlElement xmlElement = new XmlElement(xmlElementBuilder.name, xmlElementBuilder.attributes, listAsList, xmlElementBuilder.content);
                            xmlElementBuilder.content = null;
                            arrayDeque2.addLast(xmlElementBuilder);
                            i--;
                            if (i == 0) {
                                return xmlElement;
                            }
                            Object objRemoveLast = arrayDeque.removeLast();
                            Intrinsics.checkNotNullExpressionValue(objRemoveLast, "stack.removeLast()");
                            xmlElementBuilder = (XmlElementBuilder) objRemoveLast;
                            xmlElementBuilder.children.add(xmlElement);
                            break;
                        case 3:
                        case 5:
                        case 6:
                            break;
                        case 4:
                            if (!reader.isWhiteSpace()) {
                                if (xmlElementBuilder.content == null) {
                                    xmlElementBuilder.content = reader.getText();
                                } else {
                                    xmlElementBuilder.content = Intrinsics.stringPlus(xmlElementBuilder.content, reader.getText());
                                }
                            }
                            break;
                        default:
                            throw new XMLStreamException("Unexpected XMLStream event " + reader.getEventType(), reader.getLocation());
                    }
                } else if (xmlElementBuilder.content == null) {
                    String str2 = xmlElementBuilder.name;
                    String text = reader.getText();
                    Intrinsics.checkNotNullExpressionValue(text, "reader.text");
                    xmlElementBuilder.content = interner.value(str2, text);
                } else {
                    xmlElementBuilder.content = Intrinsics.stringPlus(xmlElementBuilder.content, reader.getText());
                }
            }
        }
        throw new XMLStreamException("Unexpected end of input: " + reader.getEventType(), reader.getLocation());
    }

    private static final Map<String, String> readAttributes(XMLStreamReader2 xMLStreamReader2, XmlInterner xmlInterner) {
        int attributeCount = xMLStreamReader2.getAttributeCount();
        if (attributeCount == 0) {
            Map<String, String> mapEmptyMap = Collections.emptyMap();
            Intrinsics.checkNotNullExpressionValue(mapEmptyMap, "Collections.emptyMap()");
            return mapEmptyMap;
        }
        if (attributeCount == 1) {
            String attributeLocalName = xMLStreamReader2.getAttributeLocalName(0);
            Intrinsics.checkNotNullExpressionValue(attributeLocalName, "reader.getAttributeLocalName(0)");
            String strName = xmlInterner.name(attributeLocalName);
            String attributeValue = xMLStreamReader2.getAttributeValue(0);
            Intrinsics.checkNotNullExpressionValue(attributeValue, "reader.getAttributeValue(0)");
            Map<String, String> mapSingletonMap = Collections.singletonMap(strName, xmlInterner.value(strName, attributeValue));
            Intrinsics.checkNotNullExpressionValue(mapSingletonMap, "Collections.singletonMap…er.getAttributeValue(0)))");
            return mapSingletonMap;
        }
        Object2ObjectOpenHashMap object2ObjectOpenHashMap = new Object2ObjectOpenHashMap(attributeCount);
        for (int i = 0; i < attributeCount; i++) {
            String attributeLocalName2 = xMLStreamReader2.getAttributeLocalName(i);
            Intrinsics.checkNotNullExpressionValue(attributeLocalName2, "reader.getAttributeLocalName(i)");
            String strName2 = xmlInterner.name(attributeLocalName2);
            String attributeValue2 = xMLStreamReader2.getAttributeValue(i);
            Intrinsics.checkNotNullExpressionValue(attributeValue2, "reader.getAttributeValue(i)");
            object2ObjectOpenHashMap.put(strName2, xmlInterner.value(strName2, attributeValue2));
        }
        return object2ObjectOpenHashMap;
    }

    private static final int nextTag(XMLStreamReader2 xMLStreamReader2) throws WFCException {
        while (true) {
            int next = xMLStreamReader2.next();
            if (next != 11) {
                if (next != 12) {
                    switch (next) {
                        case 1:
                        case 2:
                            return next;
                        case 3:
                        case 5:
                        case 6:
                            continue;
                        case 4:
                            break;
                        default:
                            throw new WFCException("Received event " + ErrorConsts.tokenTypeDesc(next) + ", instead of START_ELEMENT or END_ELEMENT.", xMLStreamReader2.getLocation());
                    }
                }
                if (!xMLStreamReader2.isWhiteSpace()) {
                    throw new WFCException("Received non-all-whitespace CHARACTERS or CDATA event in nextTag().", xMLStreamReader2.getLocation());
                }
            }
        }
    }
}
