package org.apache.xerces.impl.xs.traversers;

import org.apache.xerces.impl.xs.SchemaGrammar;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.apache.xerces.impl.xs.XSAnnotationImpl;
import org.apache.xerces.impl.xs.XSNotationDecl;
import org.apache.xerces.impl.xs.util.XSObjectListImpl;
import org.apache.xerces.util.DOMUtil;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes13.dex */
class XSDNotationTraverser extends XSDAbstractTraverser {
    XSDNotationTraverser(XSDHandler xSDHandler, XSAttributeChecker xSAttributeChecker) {
        super(xSDHandler, xSAttributeChecker);
    }

    XSNotationDecl traverse(Element element, XSDocumentInfo xSDocumentInfo, SchemaGrammar schemaGrammar) {
        XSObjectListImpl xSObjectListImpl;
        Object[] objArrCheckAttributes = this.fAttrChecker.checkAttributes(element, true, xSDocumentInfo);
        String str = (String) objArrCheckAttributes[XSAttributeChecker.ATTIDX_NAME];
        String str2 = (String) objArrCheckAttributes[XSAttributeChecker.ATTIDX_PUBLIC];
        String str3 = (String) objArrCheckAttributes[XSAttributeChecker.ATTIDX_SYSTEM];
        XSAnnotationImpl xSAnnotationImplTraverseSyntheticAnnotation = null;
        if (str == null) {
            reportSchemaError("s4s-att-must-appear", new Object[]{SchemaSymbols.ELT_NOTATION, SchemaSymbols.ATT_NAME}, element);
            this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
            return null;
        }
        if (str3 == null && str2 == null) {
            reportSchemaError("PublicSystemOnNotation", null, element);
            str2 = "missing";
        }
        XSNotationDecl xSNotationDecl = new XSNotationDecl();
        xSNotationDecl.fName = str;
        xSNotationDecl.fTargetNamespace = xSDocumentInfo.fTargetNamespace;
        xSNotationDecl.fPublicId = str2;
        xSNotationDecl.fSystemId = str3;
        Element firstChildElement = DOMUtil.getFirstChildElement(element);
        if (firstChildElement == null || !DOMUtil.getLocalName(firstChildElement).equals(SchemaSymbols.ELT_ANNOTATION)) {
            String syntheticAnnotation = DOMUtil.getSyntheticAnnotation(element);
            if (syntheticAnnotation != null) {
                xSAnnotationImplTraverseSyntheticAnnotation = traverseSyntheticAnnotation(element, syntheticAnnotation, objArrCheckAttributes, false, xSDocumentInfo);
            }
        } else {
            xSAnnotationImplTraverseSyntheticAnnotation = traverseAnnotationDecl(firstChildElement, objArrCheckAttributes, false, xSDocumentInfo);
            firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement);
        }
        if (xSAnnotationImplTraverseSyntheticAnnotation != null) {
            xSObjectListImpl = new XSObjectListImpl();
            xSObjectListImpl.addXSObject(xSAnnotationImplTraverseSyntheticAnnotation);
        } else {
            xSObjectListImpl = XSObjectListImpl.EMPTY_LIST;
        }
        xSNotationDecl.fAnnotations = xSObjectListImpl;
        if (firstChildElement != null) {
            reportSchemaError("s4s-elt-must-match.1", new Object[]{SchemaSymbols.ELT_NOTATION, "(annotation?)", DOMUtil.getLocalName(firstChildElement)}, firstChildElement);
        }
        if (schemaGrammar.getGlobalNotationDecl(xSNotationDecl.fName) == null) {
            schemaGrammar.addGlobalNotationDecl(xSNotationDecl);
        }
        String strSchemaDocument2SystemId = this.fSchemaHandler.schemaDocument2SystemId(xSDocumentInfo);
        XSNotationDecl globalNotationDecl = schemaGrammar.getGlobalNotationDecl(xSNotationDecl.fName, strSchemaDocument2SystemId);
        if (globalNotationDecl == null) {
            schemaGrammar.addGlobalNotationDecl(xSNotationDecl, strSchemaDocument2SystemId);
        }
        if (this.fSchemaHandler.fTolerateDuplicates) {
            if (globalNotationDecl != null) {
                xSNotationDecl = globalNotationDecl;
            }
            this.fSchemaHandler.addGlobalNotationDecl(xSNotationDecl);
        }
        this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
        return xSNotationDecl;
    }
}
