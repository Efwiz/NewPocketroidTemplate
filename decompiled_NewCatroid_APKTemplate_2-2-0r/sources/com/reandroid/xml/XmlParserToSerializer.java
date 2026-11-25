package com.reandroid.xml;

import java.io.Closeable;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes11.dex */
public class XmlParserToSerializer {
    private boolean enableIndent = true;
    private final XmlPullParser parser;
    boolean processNamespace;
    boolean reportNamespaceAttrs;
    private XmlSerializer serializer;

    public XmlParserToSerializer(XmlPullParser xmlPullParser, XmlSerializer xmlSerializer) {
        this.parser = xmlPullParser;
        this.serializer = XmlIndentingSerializer.create(xmlSerializer);
        XMLUtil.setFeatureSafe(xmlPullParser, XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
        XMLUtil.setFeatureSafe(xmlPullParser, XmlPullParser.FEATURE_REPORT_NAMESPACE_ATTRIBUTES, true);
    }

    public void setEnableIndent(boolean z) {
        if (z == this.enableIndent) {
            return;
        }
        this.enableIndent = z;
        if (z) {
            this.serializer = XmlIndentingSerializer.create(this.serializer);
            return;
        }
        XmlSerializer xmlSerializer = this.serializer;
        if (xmlSerializer instanceof XmlIndentingSerializer) {
            this.serializer = ((XmlIndentingSerializer) xmlSerializer).getBaseSerializer();
        }
    }

    public void write() throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser = this.parser;
        this.processNamespace = XMLUtil.getFeatureSafe(xmlPullParser, XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        this.reportNamespaceAttrs = XMLUtil.getFeatureSafe(xmlPullParser, XmlPullParser.FEATURE_REPORT_NAMESPACE_ATTRIBUTES, false);
        for (int next = xmlPullParser.next(); nextEvent(next); next = xmlPullParser.next()) {
        }
        close();
    }

    private void close() throws IOException {
        XmlPullParser xmlPullParser = this.parser;
        if (xmlPullParser instanceof Closeable) {
            ((Closeable) xmlPullParser).close();
        }
        XmlSerializer xmlSerializer = this.serializer;
        if (xmlSerializer instanceof Closeable) {
            ((Closeable) xmlSerializer).close();
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private boolean nextEvent(int i) throws XmlPullParserException, IOException {
        if (i != 9) {
            switch (i) {
                case 0:
                    onStartDocument();
                    break;
                case 1:
                    onEndDocument();
                    return false;
                case 2:
                    onStartTag();
                    break;
                case 3:
                    onEndTag();
                    break;
                case 4:
                    onText();
                    break;
                case 5:
                    onCdsect();
                    break;
                case 6:
                    onEntityRef();
                    break;
            }
        } else {
            onComment();
        }
        return true;
    }

    private void onStartDocument() throws IOException {
        this.serializer.startDocument("utf-8", null);
    }

    private void onStartTag() throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser = this.parser;
        XmlSerializer xmlSerializer = this.serializer;
        boolean z = this.processNamespace;
        if (!z || !this.reportNamespaceAttrs) {
            int namespaceCount = xmlPullParser.getNamespaceCount(xmlPullParser.getDepth());
            for (int i = 0; i < namespaceCount; i++) {
                xmlSerializer.setPrefix(xmlPullParser.getNamespacePrefix(i), xmlPullParser.getNamespaceUri(i));
            }
        }
        xmlSerializer.startTag(xmlPullParser.getNamespace(), xmlPullParser.getName());
        int attributeCount = xmlPullParser.getAttributeCount();
        for (int i2 = 0; i2 < attributeCount; i2++) {
            xmlSerializer.attribute(z ? xmlPullParser.getAttributeNamespace(i2) : null, xmlPullParser.getAttributeName(i2), xmlPullParser.getAttributeValue(i2));
        }
    }

    private void onText() throws IOException {
        this.serializer.text(this.parser.getText());
    }

    private void onEntityRef() throws IOException {
        this.serializer.entityRef(this.parser.getText());
    }

    private void onCdsect() throws IOException {
        this.serializer.cdsect(this.parser.getText());
    }

    private void onComment() throws IOException {
        this.serializer.comment(this.parser.getText());
    }

    private void onEndTag() throws IOException {
        this.serializer.endTag(this.parser.getNamespace(), this.parser.getName());
    }

    private void onEndDocument() throws IOException {
        this.serializer.endDocument();
    }
}
