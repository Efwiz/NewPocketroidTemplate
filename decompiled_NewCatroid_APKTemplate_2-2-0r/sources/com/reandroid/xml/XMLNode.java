package com.reandroid.xml;

import com.reandroid.utils.collection.EmptyIterator;
import com.reandroid.xml.base.Node;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes11.dex */
public abstract class XMLNode implements Node {
    private static final int DEBUG_STRING_LENGTH = 250;
    private int mColumnNumber;
    private int mLineNumber;
    private XMLNode mParent;

    int appendDebugText(Appendable appendable, int i, int i2) throws IOException {
        return 0;
    }

    int getLength() {
        return 0;
    }

    int getTextLength() {
        return 0;
    }

    abstract XMLNode newCopy(XMLNode xMLNode);

    public void parse(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
    }

    abstract void write(Appendable appendable, boolean z, boolean z2) throws IOException;

    @Override // com.reandroid.xml.base.Node, com.reandroid.xml.base.Attribute
    public XMLNode getParentNode() {
        return this.mParent;
    }

    public XMLNode getRootParentNode() {
        XMLNode parentNode = getParentNode();
        return parentNode != null ? parentNode.getRootParentNode() : this;
    }

    public Iterator<XMLNode> iterator() {
        return EmptyIterator.of();
    }

    public void removeSelf() {
        XMLNode parentNode = getParentNode();
        if (parentNode instanceof XMLNodeTree) {
            ((XMLNodeTree) parentNode).remove(this);
        }
    }

    void setParentNode(XMLNode xMLNode) {
        if (xMLNode != this) {
            this.mParent = xMLNode;
        }
    }

    @Override // com.reandroid.xml.base.Node
    public int getColumnNumber() {
        return this.mColumnNumber;
    }

    @Override // com.reandroid.xml.base.Node
    public void setColumnNumber(int i) {
        this.mColumnNumber = i;
    }

    @Override // com.reandroid.xml.base.Node
    public int getLineNumber() {
        return this.mLineNumber;
    }

    @Override // com.reandroid.xml.base.Node
    public void setLineNumber(int i) {
        this.mLineNumber = i;
    }

    public String toText(boolean z, boolean z2) {
        StringWriter stringWriter = new StringWriter();
        try {
            write(stringWriter, z, z2);
            stringWriter.flush();
            stringWriter.close();
        } catch (IOException unused) {
        }
        return stringWriter.toString();
    }

    public String getDebugText() {
        StringWriter stringWriter = new StringWriter();
        try {
            appendDebugText(stringWriter, 250, 0);
            stringWriter.flush();
            stringWriter.close();
        } catch (IOException unused) {
        }
        return stringWriter.toString();
    }

    public String toString() {
        return getDebugText();
    }
}
