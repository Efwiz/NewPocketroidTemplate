package org.apache.xerces.impl.xs.traversers;

import org.apache.xerces.impl.xs.SchemaGrammar;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.apache.xerces.impl.xs.XSAnnotationImpl;
import org.apache.xerces.impl.xs.XSAttributeGroupDecl;
import org.apache.xerces.impl.xs.util.XSObjectListImpl;
import org.apache.xerces.util.DOMUtil;
import org.apache.xerces.util.XMLSymbols;
import org.apache.xerces.xni.QName;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes13.dex */
class XSDAttributeGroupTraverser extends XSDAbstractTraverser {
    XSDAttributeGroupTraverser(XSDHandler xSDHandler, XSAttributeChecker xSAttributeChecker) {
        super(xSDHandler, xSAttributeChecker);
    }

    /* JADX WARN: Code duplicated, block: B:18:0x007e  */
    /* JADX WARN: Code duplicated, block: B:21:0x0093  */
    /* JADX WARN: Code duplicated, block: B:23:0x0099  */
    /* JADX WARN: Code duplicated, block: B:29:0x00d0  */
    /* JADX WARN: Code duplicated, block: B:30:0x00dc  */
    /* JADX WARN: Code duplicated, block: B:33:0x00e8  */
    /* JADX WARN: Code duplicated, block: B:36:0x00f9  */
    /* JADX WARN: Code duplicated, block: B:39:0x0102 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:40:0x0104  */
    XSAttributeGroupDecl traverseGlobal(Element element, XSDocumentInfo xSDocumentInfo, SchemaGrammar schemaGrammar) {
        String str;
        Element element2;
        XSAnnotationImpl xSAnnotationImpl;
        XSAnnotationImpl xSAnnotationImplTraverseSyntheticAnnotation;
        Element element3;
        Element elementTraverseAttrsAndAttrGrps;
        XSAttributeGroupDecl xSAttributeGroupDecl;
        XSObjectListImpl xSObjectListImpl;
        String strSchemaDocument2SystemId;
        XSAttributeGroupDecl globalAttributeGroupDecl;
        Object[] objArrValidRestrictionOf;
        XSAttributeGroupDecl xSAttributeGroupDecl2 = new XSAttributeGroupDecl();
        Object[] objArrCheckAttributes = this.fAttrChecker.checkAttributes(element, true, xSDocumentInfo);
        String str2 = (String) objArrCheckAttributes[XSAttributeChecker.ATTIDX_NAME];
        if (str2 == null) {
            reportSchemaError("s4s-att-must-appear", new Object[]{"attributeGroup (global)", "name"}, element);
            str = "(no name)";
        } else {
            str = str2;
        }
        xSAttributeGroupDecl2.fName = str;
        xSAttributeGroupDecl2.fTargetNamespace = xSDocumentInfo.fTargetNamespace;
        Element firstChildElement = DOMUtil.getFirstChildElement(element);
        if (firstChildElement == null || !DOMUtil.getLocalName(firstChildElement).equals(SchemaSymbols.ELT_ANNOTATION)) {
            String syntheticAnnotation = DOMUtil.getSyntheticAnnotation(element);
            if (syntheticAnnotation != null) {
                xSAnnotationImplTraverseSyntheticAnnotation = traverseSyntheticAnnotation(element, syntheticAnnotation, objArrCheckAttributes, false, xSDocumentInfo);
            } else {
                element2 = firstChildElement;
                xSAnnotationImpl = null;
            }
            element3 = element2;
            elementTraverseAttrsAndAttrGrps = traverseAttrsAndAttrGrps(element2, xSAttributeGroupDecl2, xSDocumentInfo, schemaGrammar, null);
            if (elementTraverseAttrsAndAttrGrps != null) {
                reportSchemaError("s4s-elt-must-match.1", new Object[]{str, "(annotation?, ((attribute | attributeGroup)*, anyAttribute?))", DOMUtil.getLocalName(elementTraverseAttrsAndAttrGrps)}, elementTraverseAttrsAndAttrGrps);
            }
            if (str.equals("(no name)")) {
                this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                return null;
            }
            xSAttributeGroupDecl2.removeProhibitedAttrs();
            xSAttributeGroupDecl = (XSAttributeGroupDecl) this.fSchemaHandler.getGrpOrAttrGrpRedefinedByRestriction(2, new QName(XMLSymbols.EMPTY_STRING, str, str, xSDocumentInfo.fTargetNamespace), xSDocumentInfo, element);
            if (xSAttributeGroupDecl != null && (objArrValidRestrictionOf = xSAttributeGroupDecl2.validRestrictionOf(str, xSAttributeGroupDecl)) != null) {
                reportSchemaError((String) objArrValidRestrictionOf[objArrValidRestrictionOf.length - 1], objArrValidRestrictionOf, element3);
                reportSchemaError("src-redefine.7.2.2", new Object[]{str, objArrValidRestrictionOf[objArrValidRestrictionOf.length - 1]}, element3);
            }
            if (xSAnnotationImpl != null) {
                xSObjectListImpl = new XSObjectListImpl();
                xSObjectListImpl.addXSObject(xSAnnotationImpl);
            } else {
                xSObjectListImpl = XSObjectListImpl.EMPTY_LIST;
            }
            xSAttributeGroupDecl2.fAnnotations = xSObjectListImpl;
            if (schemaGrammar.getGlobalAttributeGroupDecl(xSAttributeGroupDecl2.fName) == null) {
                schemaGrammar.addGlobalAttributeGroupDecl(xSAttributeGroupDecl2);
            }
            strSchemaDocument2SystemId = this.fSchemaHandler.schemaDocument2SystemId(xSDocumentInfo);
            globalAttributeGroupDecl = schemaGrammar.getGlobalAttributeGroupDecl(xSAttributeGroupDecl2.fName, strSchemaDocument2SystemId);
            if (globalAttributeGroupDecl == null) {
                schemaGrammar.addGlobalAttributeGroupDecl(xSAttributeGroupDecl2, strSchemaDocument2SystemId);
            }
            if (this.fSchemaHandler.fTolerateDuplicates) {
                if (globalAttributeGroupDecl != null) {
                    xSAttributeGroupDecl2 = globalAttributeGroupDecl;
                }
                this.fSchemaHandler.addGlobalAttributeGroupDecl(xSAttributeGroupDecl2);
            }
            this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
            return xSAttributeGroupDecl2;
        }
        xSAnnotationImplTraverseSyntheticAnnotation = traverseAnnotationDecl(firstChildElement, objArrCheckAttributes, false, xSDocumentInfo);
        firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement);
        element2 = firstChildElement;
        xSAnnotationImpl = xSAnnotationImplTraverseSyntheticAnnotation;
        element3 = element2;
        elementTraverseAttrsAndAttrGrps = traverseAttrsAndAttrGrps(element2, xSAttributeGroupDecl2, xSDocumentInfo, schemaGrammar, null);
        if (elementTraverseAttrsAndAttrGrps != null) {
            reportSchemaError("s4s-elt-must-match.1", new Object[]{str, "(annotation?, ((attribute | attributeGroup)*, anyAttribute?))", DOMUtil.getLocalName(elementTraverseAttrsAndAttrGrps)}, elementTraverseAttrsAndAttrGrps);
        }
        if (str.equals("(no name)")) {
            this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
            return null;
        }
        xSAttributeGroupDecl2.removeProhibitedAttrs();
        xSAttributeGroupDecl = (XSAttributeGroupDecl) this.fSchemaHandler.getGrpOrAttrGrpRedefinedByRestriction(2, new QName(XMLSymbols.EMPTY_STRING, str, str, xSDocumentInfo.fTargetNamespace), xSDocumentInfo, element);
        if (xSAttributeGroupDecl != null) {
            reportSchemaError((String) objArrValidRestrictionOf[objArrValidRestrictionOf.length - 1], objArrValidRestrictionOf, element3);
            reportSchemaError("src-redefine.7.2.2", new Object[]{str, objArrValidRestrictionOf[objArrValidRestrictionOf.length - 1]}, element3);
        }
        if (xSAnnotationImpl != null) {
            xSObjectListImpl = new XSObjectListImpl();
            xSObjectListImpl.addXSObject(xSAnnotationImpl);
        } else {
            xSObjectListImpl = XSObjectListImpl.EMPTY_LIST;
        }
        xSAttributeGroupDecl2.fAnnotations = xSObjectListImpl;
        if (schemaGrammar.getGlobalAttributeGroupDecl(xSAttributeGroupDecl2.fName) == null) {
            schemaGrammar.addGlobalAttributeGroupDecl(xSAttributeGroupDecl2);
        }
        strSchemaDocument2SystemId = this.fSchemaHandler.schemaDocument2SystemId(xSDocumentInfo);
        globalAttributeGroupDecl = schemaGrammar.getGlobalAttributeGroupDecl(xSAttributeGroupDecl2.fName, strSchemaDocument2SystemId);
        if (globalAttributeGroupDecl == null) {
            schemaGrammar.addGlobalAttributeGroupDecl(xSAttributeGroupDecl2, strSchemaDocument2SystemId);
        }
        if (this.fSchemaHandler.fTolerateDuplicates) {
            if (globalAttributeGroupDecl != null) {
                xSAttributeGroupDecl2 = globalAttributeGroupDecl;
            }
            this.fSchemaHandler.addGlobalAttributeGroupDecl(xSAttributeGroupDecl2);
        }
        this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
        return xSAttributeGroupDecl2;
    }

    XSAttributeGroupDecl traverseLocal(Element element, XSDocumentInfo xSDocumentInfo, SchemaGrammar schemaGrammar) {
        Object[] objArrCheckAttributes = this.fAttrChecker.checkAttributes(element, false, xSDocumentInfo);
        QName qName = (QName) objArrCheckAttributes[XSAttributeChecker.ATTIDX_REF];
        if (qName == null) {
            reportSchemaError("s4s-att-must-appear", new Object[]{"attributeGroup (local)", "ref"}, element);
            this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
            return null;
        }
        XSAttributeGroupDecl xSAttributeGroupDecl = (XSAttributeGroupDecl) this.fSchemaHandler.getGlobalDecl(xSDocumentInfo, 2, qName, element);
        Element firstChildElement = DOMUtil.getFirstChildElement(element);
        if (firstChildElement != null) {
            if (DOMUtil.getLocalName(firstChildElement).equals(SchemaSymbols.ELT_ANNOTATION)) {
                traverseAnnotationDecl(firstChildElement, objArrCheckAttributes, false, xSDocumentInfo);
                firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement);
            } else {
                String syntheticAnnotation = DOMUtil.getSyntheticAnnotation(firstChildElement);
                if (syntheticAnnotation != null) {
                    traverseSyntheticAnnotation(firstChildElement, syntheticAnnotation, objArrCheckAttributes, false, xSDocumentInfo);
                }
            }
            if (firstChildElement != null) {
                reportSchemaError("s4s-elt-must-match.1", new Object[]{qName.rawname, "(annotation?)", DOMUtil.getLocalName(firstChildElement)}, firstChildElement);
            }
        }
        this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
        return xSAttributeGroupDecl;
    }
}
