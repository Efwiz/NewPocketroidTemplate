package org.apache.xerces.impl.xs.traversers;

import org.apache.xerces.impl.xpath.XPathException;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.apache.xerces.impl.xs.identity.Field;
import org.apache.xerces.impl.xs.identity.IdentityConstraint;
import org.apache.xerces.impl.xs.identity.Selector;
import org.apache.xerces.util.DOMUtil;
import org.apache.xerces.util.XMLChar;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes13.dex */
class XSDAbstractIDConstraintTraverser extends XSDAbstractTraverser {
    public XSDAbstractIDConstraintTraverser(XSDHandler xSDHandler, XSAttributeChecker xSAttributeChecker) {
        super(xSDHandler, xSAttributeChecker);
    }

    /* JADX WARN: Code duplicated, block: B:33:0x00da  */
    /* JADX WARN: Code duplicated, block: B:36:0x00e7  */
    /* JADX WARN: Code duplicated, block: B:40:0x0107  */
    /* JADX WARN: Code duplicated, block: B:41:0x010f  */
    /* JADX WARN: Code duplicated, block: B:43:0x0112  */
    /* JADX WARN: Code duplicated, block: B:52:0x0152  */
    /* JADX WARN: Code duplicated, block: B:53:0x0162  */
    /* JADX WARN: Code duplicated, block: B:55:0x016a  */
    /* JADX WARN: Code duplicated, block: B:61:0x0196  */
    /* JADX WARN: Code duplicated, block: B:68:0x01ce A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:69:0x01d0 A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Code duplicated, block: B:78:0x012c A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:79:0x011e A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:80:0x0184 A[SYNTHETIC] */
    boolean traverseIdentityConstraint(IdentityConstraint identityConstraint, Element element, XSDocumentInfo xSDocumentInfo, Object[] objArr) {
        String str;
        String str2;
        String strTrim;
        Element nextSiblingElement;
        Element nextSiblingElement2;
        Object[] objArrCheckAttributes;
        Element firstChildElement;
        String str3;
        String syntheticAnnotation;
        String str4;
        String strTrim2;
        Element firstChildElement2 = DOMUtil.getFirstChildElement(element);
        if (firstChildElement2 == null) {
            reportSchemaError("s4s-elt-must-match.2", new Object[]{"identity constraint", "(annotation?, selector, field+)"}, element);
            return false;
        }
        if (DOMUtil.getLocalName(firstChildElement2).equals(SchemaSymbols.ELT_ANNOTATION)) {
            identityConstraint.addAnnotation(traverseAnnotationDecl(firstChildElement2, objArr, false, xSDocumentInfo));
            firstChildElement2 = DOMUtil.getNextSiblingElement(firstChildElement2);
            if (firstChildElement2 == null) {
                reportSchemaError("s4s-elt-must-match.2", new Object[]{"identity constraint", "(annotation?, selector, field+)"}, element);
                return false;
            }
        } else {
            String syntheticAnnotation2 = DOMUtil.getSyntheticAnnotation(element);
            if (syntheticAnnotation2 != null) {
                identityConstraint.addAnnotation(traverseSyntheticAnnotation(element, syntheticAnnotation2, objArr, false, xSDocumentInfo));
            }
        }
        if (!DOMUtil.getLocalName(firstChildElement2).equals(SchemaSymbols.ELT_SELECTOR)) {
            reportSchemaError("s4s-elt-must-match.1", new Object[]{"identity constraint", "(annotation?, selector, field+)", SchemaSymbols.ELT_SELECTOR}, firstChildElement2);
            return false;
        }
        Object[] objArrCheckAttributes2 = this.fAttrChecker.checkAttributes(firstChildElement2, false, xSDocumentInfo);
        Element firstChildElement3 = DOMUtil.getFirstChildElement(firstChildElement2);
        String str5 = "(annotation?)";
        if (firstChildElement3 == null) {
            String syntheticAnnotation3 = DOMUtil.getSyntheticAnnotation(firstChildElement2);
            if (syntheticAnnotation3 != null) {
                identityConstraint.addAnnotation(traverseSyntheticAnnotation(element, syntheticAnnotation3, objArrCheckAttributes2, false, xSDocumentInfo));
            }
            str = (String) objArrCheckAttributes2[XSAttributeChecker.ATTIDX_XPATH];
            str2 = "s4s-att-must-appear";
            if (str == null) {
                reportSchemaError("s4s-att-must-appear", new Object[]{SchemaSymbols.ELT_SELECTOR, SchemaSymbols.ATT_XPATH}, firstChildElement2);
                return false;
            }
            strTrim = XMLChar.trim(str);
            try {
                identityConstraint.setSelector(new Selector(new Selector.XPath(strTrim, this.fSymbolTable, xSDocumentInfo.fNamespaceSupport), identityConstraint));
                this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
                nextSiblingElement = DOMUtil.getNextSiblingElement(firstChildElement2);
                if (nextSiblingElement == null) {
                    reportSchemaError("s4s-elt-must-match.2", new Object[]{"identity constraint", "(annotation?, selector, field+)"}, firstChildElement2);
                    return false;
                }
                nextSiblingElement2 = nextSiblingElement;
                while (nextSiblingElement2 != null) {
                    if (!DOMUtil.getLocalName(nextSiblingElement2).equals(SchemaSymbols.ELT_FIELD)) {
                        objArrCheckAttributes = this.fAttrChecker.checkAttributes(nextSiblingElement2, false, xSDocumentInfo);
                        firstChildElement = DOMUtil.getFirstChildElement(nextSiblingElement2);
                        if (firstChildElement != null && DOMUtil.getLocalName(firstChildElement).equals(SchemaSymbols.ELT_ANNOTATION)) {
                            identityConstraint.addAnnotation(traverseAnnotationDecl(firstChildElement, objArrCheckAttributes, false, xSDocumentInfo));
                            firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement);
                        }
                        if (firstChildElement != null) {
                            str3 = str5;
                            syntheticAnnotation = DOMUtil.getSyntheticAnnotation(nextSiblingElement2);
                            if (syntheticAnnotation != null) {
                                identityConstraint.addAnnotation(traverseSyntheticAnnotation(element, syntheticAnnotation, objArrCheckAttributes, false, xSDocumentInfo));
                            }
                            str4 = (String) objArrCheckAttributes[XSAttributeChecker.ATTIDX_XPATH];
                            if (str4 == null) {
                                reportSchemaError(str2, new Object[]{SchemaSymbols.ELT_FIELD, SchemaSymbols.ATT_XPATH}, nextSiblingElement2);
                            } else {
                                strTrim2 = XMLChar.trim(str4);
                                try {
                                    identityConstraint.addField(new Field(new Field.XPath(strTrim2, this.fSymbolTable, xSDocumentInfo.fNamespaceSupport), identityConstraint));
                                    nextSiblingElement2 = DOMUtil.getNextSiblingElement(nextSiblingElement2);
                                    this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                                    str2 = str2;
                                    str5 = str3;
                                } catch (XPathException e) {
                                    reportSchemaError(e.getKey(), new Object[]{strTrim2}, nextSiblingElement2);
                                }
                            }
                            this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                            return false;
                        }
                        str3 = str5;
                        reportSchemaError("s4s-elt-must-match.1", new Object[]{SchemaSymbols.ELT_FIELD, str3, DOMUtil.getLocalName(firstChildElement)}, firstChildElement);
                        str4 = (String) objArrCheckAttributes[XSAttributeChecker.ATTIDX_XPATH];
                        if (str4 == null) {
                            reportSchemaError(str2, new Object[]{SchemaSymbols.ELT_FIELD, SchemaSymbols.ATT_XPATH}, nextSiblingElement2);
                        } else {
                            strTrim2 = XMLChar.trim(str4);
                            identityConstraint.addField(new Field(new Field.XPath(strTrim2, this.fSymbolTable, xSDocumentInfo.fNamespaceSupport), identityConstraint));
                            nextSiblingElement2 = DOMUtil.getNextSiblingElement(nextSiblingElement2);
                            this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                            str2 = str2;
                            str5 = str3;
                        }
                        this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                        return false;
                    }
                    reportSchemaError("s4s-elt-must-match.1", new Object[]{"identity constraint", "(annotation?, selector, field+)", SchemaSymbols.ELT_FIELD}, nextSiblingElement2);
                    nextSiblingElement2 = DOMUtil.getNextSiblingElement(nextSiblingElement2);
                }
                if (identityConstraint.getFieldCount() > 0) {
                    return true;
                }
                return false;
            } catch (XPathException e2) {
                reportSchemaError(e2.getKey(), new Object[]{strTrim}, firstChildElement2);
                this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
                return false;
            }
        }
        if (DOMUtil.getLocalName(firstChildElement3).equals(SchemaSymbols.ELT_ANNOTATION)) {
            identityConstraint.addAnnotation(traverseAnnotationDecl(firstChildElement3, objArrCheckAttributes2, false, xSDocumentInfo));
            firstChildElement3 = DOMUtil.getNextSiblingElement(firstChildElement3);
        } else {
            reportSchemaError("s4s-elt-must-match.1", new Object[]{SchemaSymbols.ELT_SELECTOR, "(annotation?)", DOMUtil.getLocalName(firstChildElement3)}, firstChildElement3);
        }
        if (firstChildElement3 != null) {
            reportSchemaError("s4s-elt-must-match.1", new Object[]{SchemaSymbols.ELT_SELECTOR, "(annotation?)", DOMUtil.getLocalName(firstChildElement3)}, firstChildElement3);
        }
        str = (String) objArrCheckAttributes2[XSAttributeChecker.ATTIDX_XPATH];
        str2 = "s4s-att-must-appear";
        if (str == null) {
            reportSchemaError("s4s-att-must-appear", new Object[]{SchemaSymbols.ELT_SELECTOR, SchemaSymbols.ATT_XPATH}, firstChildElement2);
            return false;
        }
        strTrim = XMLChar.trim(str);
        identityConstraint.setSelector(new Selector(new Selector.XPath(strTrim, this.fSymbolTable, xSDocumentInfo.fNamespaceSupport), identityConstraint));
        this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
        nextSiblingElement = DOMUtil.getNextSiblingElement(firstChildElement2);
        if (nextSiblingElement == null) {
            reportSchemaError("s4s-elt-must-match.2", new Object[]{"identity constraint", "(annotation?, selector, field+)"}, firstChildElement2);
            return false;
        }
        nextSiblingElement2 = nextSiblingElement;
        while (nextSiblingElement2 != null) {
            if (!DOMUtil.getLocalName(nextSiblingElement2).equals(SchemaSymbols.ELT_FIELD)) {
                objArrCheckAttributes = this.fAttrChecker.checkAttributes(nextSiblingElement2, false, xSDocumentInfo);
                firstChildElement = DOMUtil.getFirstChildElement(nextSiblingElement2);
                if (firstChildElement != null) {
                    identityConstraint.addAnnotation(traverseAnnotationDecl(firstChildElement, objArrCheckAttributes, false, xSDocumentInfo));
                    firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement);
                }
                if (firstChildElement != null) {
                    str3 = str5;
                    syntheticAnnotation = DOMUtil.getSyntheticAnnotation(nextSiblingElement2);
                    if (syntheticAnnotation != null) {
                        identityConstraint.addAnnotation(traverseSyntheticAnnotation(element, syntheticAnnotation, objArrCheckAttributes, false, xSDocumentInfo));
                    }
                    str4 = (String) objArrCheckAttributes[XSAttributeChecker.ATTIDX_XPATH];
                    if (str4 == null) {
                        reportSchemaError(str2, new Object[]{SchemaSymbols.ELT_FIELD, SchemaSymbols.ATT_XPATH}, nextSiblingElement2);
                    } else {
                        strTrim2 = XMLChar.trim(str4);
                        identityConstraint.addField(new Field(new Field.XPath(strTrim2, this.fSymbolTable, xSDocumentInfo.fNamespaceSupport), identityConstraint));
                        nextSiblingElement2 = DOMUtil.getNextSiblingElement(nextSiblingElement2);
                        this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                        str2 = str2;
                        str5 = str3;
                    }
                    this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                    return false;
                }
                str3 = str5;
                reportSchemaError("s4s-elt-must-match.1", new Object[]{SchemaSymbols.ELT_FIELD, str3, DOMUtil.getLocalName(firstChildElement)}, firstChildElement);
                str4 = (String) objArrCheckAttributes[XSAttributeChecker.ATTIDX_XPATH];
                if (str4 == null) {
                    reportSchemaError(str2, new Object[]{SchemaSymbols.ELT_FIELD, SchemaSymbols.ATT_XPATH}, nextSiblingElement2);
                } else {
                    strTrim2 = XMLChar.trim(str4);
                    identityConstraint.addField(new Field(new Field.XPath(strTrim2, this.fSymbolTable, xSDocumentInfo.fNamespaceSupport), identityConstraint));
                    nextSiblingElement2 = DOMUtil.getNextSiblingElement(nextSiblingElement2);
                    this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                    str2 = str2;
                    str5 = str3;
                }
                this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                return false;
            }
            reportSchemaError("s4s-elt-must-match.1", new Object[]{"identity constraint", "(annotation?, selector, field+)", SchemaSymbols.ELT_FIELD}, nextSiblingElement2);
            nextSiblingElement2 = DOMUtil.getNextSiblingElement(nextSiblingElement2);
        }
        if (identityConstraint.getFieldCount() > 0) {
            return true;
        }
        return false;
    }
}
