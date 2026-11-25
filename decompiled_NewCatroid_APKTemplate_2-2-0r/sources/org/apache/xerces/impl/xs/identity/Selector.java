package org.apache.xerces.impl.xs.identity;

import org.apache.xerces.impl.xpath.XPathException;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.XMLChar;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xs.ShortList;
import org.apache.xerces.xs.XSTypeDefinition;

/* JADX INFO: loaded from: classes13.dex */
public class Selector {
    protected IdentityConstraint fIDConstraint;
    protected final IdentityConstraint fIdentityConstraint;
    protected final XPath fXPath;

    public class Matcher extends XPathMatcher {
        protected int fElementDepth;
        protected final FieldActivator fFieldActivator;
        protected final int fInitialDepth;
        protected int fMatchedDepth;

        public Matcher(XPath xPath, FieldActivator fieldActivator, int i) {
            super(xPath);
            this.fFieldActivator = fieldActivator;
            this.fInitialDepth = i;
        }

        @Override // org.apache.xerces.impl.xs.identity.XPathMatcher
        public void endElement(QName qName, XSTypeDefinition xSTypeDefinition, boolean z, Object obj, short s, ShortList shortList) {
            super.endElement(qName, xSTypeDefinition, z, obj, s, shortList);
            int i = this.fElementDepth;
            this.fElementDepth = i - 1;
            if (i == this.fMatchedDepth) {
                this.fMatchedDepth = -1;
                this.fFieldActivator.endValueScopeFor(Selector.this.fIdentityConstraint, this.fInitialDepth);
            }
        }

        public IdentityConstraint getIdentityConstraint() {
            return Selector.this.fIdentityConstraint;
        }

        public int getInitialDepth() {
            return this.fInitialDepth;
        }

        @Override // org.apache.xerces.impl.xs.identity.XPathMatcher
        public void startDocumentFragment() {
            super.startDocumentFragment();
            this.fElementDepth = 0;
            this.fMatchedDepth = -1;
        }

        @Override // org.apache.xerces.impl.xs.identity.XPathMatcher
        public void startElement(QName qName, XMLAttributes xMLAttributes) {
            super.startElement(qName, xMLAttributes);
            this.fElementDepth++;
            if (isMatched()) {
                this.fMatchedDepth = this.fElementDepth;
                this.fFieldActivator.startValueScopeFor(Selector.this.fIdentityConstraint, this.fInitialDepth);
                int fieldCount = Selector.this.fIdentityConstraint.getFieldCount();
                for (int i = 0; i < fieldCount; i++) {
                    this.fFieldActivator.activateField(Selector.this.fIdentityConstraint.getFieldAt(i), this.fInitialDepth).startElement(qName, xMLAttributes);
                }
            }
        }
    }

    public static class XPath extends org.apache.xerces.impl.xpath.XPath {
        public XPath(String str, SymbolTable symbolTable, NamespaceContext namespaceContext) throws XPathException {
            super(normalize(str), symbolTable, namespaceContext);
            for (int i = 0; i < this.fLocationPaths.length; i++) {
                if (this.fLocationPaths[i].steps[this.fLocationPaths[i].steps.length - 1].axis.type == 2) {
                    throw new XPathException("c-selector-xpath");
                }
            }
        }

        private static String normalize(String str) {
            StringBuffer stringBuffer = new StringBuffer(str.length() + 5);
            while (true) {
                if (!XMLChar.trim(str).startsWith("/") && !XMLChar.trim(str).startsWith(".")) {
                    stringBuffer.append("./");
                }
                int iIndexOf = str.indexOf(124);
                if (iIndexOf == -1) {
                    stringBuffer.append(str);
                    return stringBuffer.toString();
                }
                int i = iIndexOf + 1;
                stringBuffer.append(str.substring(0, i));
                str = str.substring(i, str.length());
            }
        }
    }

    public Selector(XPath xPath, IdentityConstraint identityConstraint) {
        this.fXPath = xPath;
        this.fIdentityConstraint = identityConstraint;
    }

    public XPathMatcher createMatcher(FieldActivator fieldActivator, int i) {
        return new Matcher(this.fXPath, fieldActivator, i);
    }

    public IdentityConstraint getIDConstraint() {
        return this.fIdentityConstraint;
    }

    public org.apache.xerces.impl.xpath.XPath getXPath() {
        return this.fXPath;
    }

    public String toString() {
        return this.fXPath.toString();
    }
}
