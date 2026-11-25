package org.apache.xerces.impl.xs;

import org.apache.xerces.impl.xs.opti.ElementImpl;
import org.apache.xerces.util.NamespaceSupport;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.XMLSymbols;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xni.QName;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/* JADX INFO: loaded from: classes13.dex */
public class SchemaNamespaceSupport extends NamespaceSupport {
    private SchemaRootContext fSchemaRootContext;

    static final class SchemaRootContext {
        private final Element fSchemaRoot;
        private final SymbolTable fSymbolTable;
        String[] fNamespace = new String[32];
        int fNamespaceSize = 0;
        boolean fDOMContextBuilt = false;
        private final QName fAttributeQName = new QName();

        SchemaRootContext(Element element, SymbolTable symbolTable) {
            this.fSchemaRoot = element;
            this.fSymbolTable = symbolTable;
        }

        private void declarePrefix(String str, String str2) {
            int i = this.fNamespaceSize;
            String[] strArr = this.fNamespace;
            if (i == strArr.length) {
                String[] strArr2 = new String[i * 2];
                System.arraycopy(strArr, 0, strArr2, 0, i);
                this.fNamespace = strArr2;
            }
            String[] strArr3 = this.fNamespace;
            int i2 = this.fNamespaceSize;
            int i3 = i2 + 1;
            this.fNamespaceSize = i3;
            strArr3[i2] = str;
            this.fNamespaceSize = i2 + 2;
            strArr3[i3] = str2;
        }

        private void fillQName(QName qName, Node node) {
            String prefix = node.getPrefix();
            String localName = node.getLocalName();
            String nodeName = node.getNodeName();
            String namespaceURI = node.getNamespaceURI();
            qName.prefix = prefix != null ? this.fSymbolTable.addSymbol(prefix) : XMLSymbols.EMPTY_STRING;
            qName.localpart = localName != null ? this.fSymbolTable.addSymbol(localName) : XMLSymbols.EMPTY_STRING;
            qName.rawname = nodeName != null ? this.fSymbolTable.addSymbol(nodeName) : XMLSymbols.EMPTY_STRING;
            qName.uri = (namespaceURI == null || namespaceURI.length() <= 0) ? null : this.fSymbolTable.addSymbol(namespaceURI);
        }

        /* JADX WARN: Code duplicated, block: B:22:0x0053 A[PHI: r4
          0x0053: PHI (r4v7 java.lang.String) = (r4v6 java.lang.String), (r4v10 java.lang.String) binds: [B:21:0x0051, B:18:0x0048] A[DONT_GENERATE, DONT_INLINE]] */
        void fillNamespaceContext() {
            String str;
            Element element = this.fSchemaRoot;
            if (element != null) {
                for (Node parentNode = element.getParentNode(); parentNode != null; parentNode = parentNode.getParentNode()) {
                    if (1 == parentNode.getNodeType()) {
                        NamedNodeMap attributes = parentNode.getAttributes();
                        int length = attributes.getLength();
                        for (int i = 0; i < length; i++) {
                            Attr attr = (Attr) attributes.item(i);
                            String value = attr.getValue();
                            if (value == null) {
                                value = XMLSymbols.EMPTY_STRING;
                            }
                            fillQName(this.fAttributeQName, attr);
                            if (this.fAttributeQName.uri == NamespaceContext.XMLNS_URI) {
                                String strAddSymbol = null;
                                if (this.fAttributeQName.prefix == XMLSymbols.PREFIX_XMLNS) {
                                    str = this.fAttributeQName.localpart;
                                    if (value.length() != 0) {
                                        strAddSymbol = this.fSymbolTable.addSymbol(value);
                                    }
                                } else {
                                    str = XMLSymbols.EMPTY_STRING;
                                    if (value.length() != 0) {
                                        strAddSymbol = this.fSymbolTable.addSymbol(value);
                                    }
                                }
                                declarePrefix(str, strAddSymbol);
                            }
                        }
                    }
                }
            }
        }

        String getURI(String str) {
            for (int i = 0; i < this.fNamespaceSize; i += 2) {
                String[] strArr = this.fNamespace;
                if (strArr[i] == str) {
                    return strArr[i + 1];
                }
            }
            return null;
        }
    }

    public SchemaNamespaceSupport(SchemaNamespaceSupport schemaNamespaceSupport) {
        this.fSchemaRootContext = null;
        this.fSchemaRootContext = schemaNamespaceSupport.fSchemaRootContext;
        this.fNamespaceSize = schemaNamespaceSupport.fNamespaceSize;
        if (this.fNamespace.length < this.fNamespaceSize) {
            this.fNamespace = new String[this.fNamespaceSize];
        }
        System.arraycopy(schemaNamespaceSupport.fNamespace, 0, this.fNamespace, 0, this.fNamespaceSize);
        this.fCurrentContext = schemaNamespaceSupport.fCurrentContext;
        if (this.fContext.length <= this.fCurrentContext) {
            this.fContext = new int[this.fCurrentContext + 1];
        }
        System.arraycopy(schemaNamespaceSupport.fContext, 0, this.fContext, 0, this.fCurrentContext + 1);
    }

    public SchemaNamespaceSupport(Element element, SymbolTable symbolTable) {
        Document ownerDocument;
        this.fSchemaRootContext = null;
        if (element == null || (element instanceof ElementImpl) || (ownerDocument = element.getOwnerDocument()) == null || element == ownerDocument.getDocumentElement()) {
            return;
        }
        this.fSchemaRootContext = new SchemaRootContext(element, symbolTable);
    }

    public String[] getEffectiveLocalContext() {
        int i;
        int i2;
        if (this.fCurrentContext < 3 || (i2 = this.fNamespaceSize - (i = this.fContext[3])) <= 0) {
            return null;
        }
        String[] strArr = new String[i2];
        System.arraycopy(this.fNamespace, i, strArr, 0, i2);
        return strArr;
    }

    @Override // org.apache.xerces.util.NamespaceSupport, org.apache.xerces.xni.NamespaceContext
    public String getURI(String str) {
        SchemaRootContext schemaRootContext;
        String uri = super.getURI(str);
        if (uri != null || (schemaRootContext = this.fSchemaRootContext) == null) {
            return uri;
        }
        if (!schemaRootContext.fDOMContextBuilt) {
            this.fSchemaRootContext.fillNamespaceContext();
            this.fSchemaRootContext.fDOMContextBuilt = true;
        }
        return (this.fSchemaRootContext.fNamespaceSize <= 0 || containsPrefix(str)) ? uri : this.fSchemaRootContext.getURI(str);
    }

    public void makeGlobal() {
        if (this.fCurrentContext >= 3) {
            this.fCurrentContext = 3;
            this.fNamespaceSize = this.fContext[3];
        }
    }

    public void setEffectiveContext(String[] strArr) {
        if (strArr == null || strArr.length == 0) {
            return;
        }
        pushContext();
        int length = this.fNamespaceSize + strArr.length;
        if (this.fNamespace.length < length) {
            String[] strArr2 = new String[length];
            System.arraycopy(this.fNamespace, 0, strArr2, 0, this.fNamespace.length);
            this.fNamespace = strArr2;
        }
        System.arraycopy(strArr, 0, this.fNamespace, this.fNamespaceSize, strArr.length);
        this.fNamespaceSize = length;
    }
}
