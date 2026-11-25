package org.apache.xerces.impl.xs.traversers;

import org.apache.xerces.impl.xs.SchemaGrammar;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.apache.xerces.impl.xs.XSAnnotationImpl;
import org.apache.xerces.impl.xs.XSParticleDecl;
import org.apache.xerces.impl.xs.XSWildcardDecl;
import org.apache.xerces.impl.xs.util.XInt;
import org.apache.xerces.impl.xs.util.XSObjectListImpl;
import org.apache.xerces.util.DOMUtil;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes13.dex */
class XSDWildcardTraverser extends XSDAbstractTraverser {
    XSDWildcardTraverser(XSDHandler xSDHandler, XSAttributeChecker xSAttributeChecker) {
        super(xSDHandler, xSAttributeChecker);
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0045  */
    XSParticleDecl traverseAny(Element element, XSDocumentInfo xSDocumentInfo, SchemaGrammar schemaGrammar) {
        XSParticleDecl particleDecl;
        Object[] objArrCheckAttributes = this.fAttrChecker.checkAttributes(element, false, xSDocumentInfo);
        XSWildcardDecl xSWildcardDeclTraverseWildcardDecl = traverseWildcardDecl(element, objArrCheckAttributes, xSDocumentInfo, schemaGrammar);
        if (xSWildcardDeclTraverseWildcardDecl != null) {
            int iIntValue = ((XInt) objArrCheckAttributes[XSAttributeChecker.ATTIDX_MINOCCURS]).intValue();
            int iIntValue2 = ((XInt) objArrCheckAttributes[XSAttributeChecker.ATTIDX_MAXOCCURS]).intValue();
            if (iIntValue2 != 0) {
                particleDecl = this.fSchemaHandler.fDeclPool != null ? this.fSchemaHandler.fDeclPool.getParticleDecl() : new XSParticleDecl();
                particleDecl.fType = (short) 2;
                particleDecl.fValue = xSWildcardDeclTraverseWildcardDecl;
                particleDecl.fMinOccurs = iIntValue;
                particleDecl.fMaxOccurs = iIntValue2;
                particleDecl.fAnnotations = xSWildcardDeclTraverseWildcardDecl.fAnnotations;
            } else {
                particleDecl = null;
            }
        } else {
            particleDecl = null;
        }
        this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
        return particleDecl;
    }

    XSWildcardDecl traverseAnyAttribute(Element element, XSDocumentInfo xSDocumentInfo, SchemaGrammar schemaGrammar) {
        Object[] objArrCheckAttributes = this.fAttrChecker.checkAttributes(element, false, xSDocumentInfo);
        XSWildcardDecl xSWildcardDeclTraverseWildcardDecl = traverseWildcardDecl(element, objArrCheckAttributes, xSDocumentInfo, schemaGrammar);
        this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
        return xSWildcardDeclTraverseWildcardDecl;
    }

    XSWildcardDecl traverseWildcardDecl(Element element, Object[] objArr, XSDocumentInfo xSDocumentInfo, SchemaGrammar schemaGrammar) {
        XSObjectListImpl xSObjectListImpl;
        XSWildcardDecl xSWildcardDecl = new XSWildcardDecl();
        xSWildcardDecl.fType = ((XInt) objArr[XSAttributeChecker.ATTIDX_NAMESPACE]).shortValue();
        xSWildcardDecl.fNamespaceList = (String[]) objArr[XSAttributeChecker.ATTIDX_NAMESPACE_LIST];
        xSWildcardDecl.fProcessContents = ((XInt) objArr[XSAttributeChecker.ATTIDX_PROCESSCONTENTS]).shortValue();
        Element firstChildElement = DOMUtil.getFirstChildElement(element);
        XSAnnotationImpl xSAnnotationImplTraverseSyntheticAnnotation = null;
        if (firstChildElement != null) {
            if (DOMUtil.getLocalName(firstChildElement).equals(SchemaSymbols.ELT_ANNOTATION)) {
                xSAnnotationImplTraverseSyntheticAnnotation = traverseAnnotationDecl(firstChildElement, objArr, false, xSDocumentInfo);
                firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement);
            } else {
                String syntheticAnnotation = DOMUtil.getSyntheticAnnotation(element);
                if (syntheticAnnotation != null) {
                    xSAnnotationImplTraverseSyntheticAnnotation = traverseSyntheticAnnotation(element, syntheticAnnotation, objArr, false, xSDocumentInfo);
                }
            }
            if (firstChildElement != null) {
                reportSchemaError("s4s-elt-must-match.1", new Object[]{"wildcard", "(annotation?)", DOMUtil.getLocalName(firstChildElement)}, element);
            }
        } else {
            String syntheticAnnotation2 = DOMUtil.getSyntheticAnnotation(element);
            if (syntheticAnnotation2 != null) {
                xSAnnotationImplTraverseSyntheticAnnotation = traverseSyntheticAnnotation(element, syntheticAnnotation2, objArr, false, xSDocumentInfo);
            }
        }
        if (xSAnnotationImplTraverseSyntheticAnnotation != null) {
            xSObjectListImpl = new XSObjectListImpl();
            xSObjectListImpl.addXSObject(xSAnnotationImplTraverseSyntheticAnnotation);
        } else {
            xSObjectListImpl = XSObjectListImpl.EMPTY_LIST;
        }
        xSWildcardDecl.fAnnotations = xSObjectListImpl;
        return xSWildcardDecl;
    }
}
