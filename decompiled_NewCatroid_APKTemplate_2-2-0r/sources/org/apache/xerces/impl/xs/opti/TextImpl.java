package org.apache.xerces.impl.xs.opti;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

/* JADX INFO: loaded from: classes13.dex */
public class TextImpl extends DefaultText {
    int fCol;
    String fData;
    int fRow;
    SchemaDOM fSchemaDOM;

    public TextImpl(StringBuffer stringBuffer, SchemaDOM schemaDOM, int i, int i2) {
        this.fData = null;
        this.fSchemaDOM = null;
        this.fData = stringBuffer.toString();
        this.fSchemaDOM = schemaDOM;
        this.fRow = i;
        this.fCol = i2;
        this.uri = null;
        this.localpart = null;
        this.prefix = null;
        this.rawname = null;
        this.nodeType = (short) 3;
    }

    @Override // org.apache.xerces.impl.xs.opti.DefaultText, org.w3c.dom.CharacterData
    public String getData() throws DOMException {
        return this.fData;
    }

    @Override // org.apache.xerces.impl.xs.opti.DefaultText, org.w3c.dom.CharacterData
    public int getLength() {
        String str = this.fData;
        if (str == null) {
            return 0;
        }
        return str.length();
    }

    @Override // org.apache.xerces.impl.xs.opti.DefaultNode, org.w3c.dom.Node
    public Node getNextSibling() {
        if (this.fCol == this.fSchemaDOM.relations[this.fRow].length - 1) {
            return null;
        }
        return this.fSchemaDOM.relations[this.fRow][this.fCol + 1];
    }

    @Override // org.apache.xerces.impl.xs.opti.NodeImpl, org.apache.xerces.impl.xs.opti.DefaultNode, org.w3c.dom.Node
    public String getNodeName() {
        return "#text";
    }

    @Override // org.apache.xerces.impl.xs.opti.DefaultNode, org.w3c.dom.Node
    public Node getParentNode() {
        return this.fSchemaDOM.relations[this.fRow][0];
    }

    @Override // org.apache.xerces.impl.xs.opti.DefaultNode, org.w3c.dom.Node
    public Node getPreviousSibling() {
        if (this.fCol == 1) {
            return null;
        }
        return this.fSchemaDOM.relations[this.fRow][this.fCol - 1];
    }

    @Override // org.apache.xerces.impl.xs.opti.DefaultText, org.w3c.dom.CharacterData
    public String substringData(int i, int i2) throws DOMException {
        String str = this.fData;
        if (str == null) {
            return null;
        }
        if (i2 < 0 || i < 0 || i > str.length()) {
            throw new DOMException((short) 1, "parameter error");
        }
        int i3 = i2 + i;
        return i3 >= this.fData.length() ? this.fData.substring(i) : this.fData.substring(i, i3);
    }
}
