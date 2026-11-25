package org.apache.xerces.impl.xs.traversers;

import org.apache.xerces.impl.xs.SchemaGrammar;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.apache.xerces.impl.xs.XSElementDecl;
import org.apache.xerces.impl.xs.identity.IdentityConstraint;
import org.apache.xerces.impl.xs.identity.KeyRef;
import org.apache.xerces.impl.xs.identity.UniqueOrKey;
import org.apache.xerces.xni.QName;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes13.dex */
class XSDKeyrefTraverser extends XSDAbstractIDConstraintTraverser {
    public XSDKeyrefTraverser(XSDHandler xSDHandler, XSAttributeChecker xSAttributeChecker) {
        super(xSDHandler, xSAttributeChecker);
    }

    void traverse(Element element, XSElementDecl xSElementDecl, XSDocumentInfo xSDocumentInfo, SchemaGrammar schemaGrammar) {
        UniqueOrKey uniqueOrKey;
        Object[] objArrCheckAttributes = this.fAttrChecker.checkAttributes(element, false, xSDocumentInfo);
        String str = (String) objArrCheckAttributes[XSAttributeChecker.ATTIDX_NAME];
        if (str == null) {
            reportSchemaError("s4s-att-must-appear", new Object[]{SchemaSymbols.ELT_KEYREF, SchemaSymbols.ATT_NAME}, element);
        } else {
            QName qName = (QName) objArrCheckAttributes[XSAttributeChecker.ATTIDX_REFER];
            if (qName == null) {
                reportSchemaError("s4s-att-must-appear", new Object[]{SchemaSymbols.ELT_KEYREF, SchemaSymbols.ATT_REFER}, element);
            } else {
                IdentityConstraint identityConstraint = (IdentityConstraint) this.fSchemaHandler.getGlobalDecl(xSDocumentInfo, 5, qName, element);
                if (identityConstraint == null) {
                    uniqueOrKey = null;
                } else if (identityConstraint.getCategory() == 1 || identityConstraint.getCategory() == 3) {
                    uniqueOrKey = (UniqueOrKey) identityConstraint;
                } else {
                    reportSchemaError("src-resolve", new Object[]{qName.rawname, "identity constraint key/unique"}, element);
                    uniqueOrKey = null;
                }
                if (uniqueOrKey != null) {
                    KeyRef keyRef = new KeyRef(xSDocumentInfo.fTargetNamespace, str, xSElementDecl.fName, uniqueOrKey);
                    if (traverseIdentityConstraint(keyRef, element, xSDocumentInfo, objArrCheckAttributes)) {
                        if (uniqueOrKey.getFieldCount() != keyRef.getFieldCount()) {
                            reportSchemaError("c-props-correct.2", new Object[]{str, uniqueOrKey.getIdentityConstraintName()}, element);
                        } else {
                            if (schemaGrammar.getIDConstraintDecl(keyRef.getIdentityConstraintName()) == null) {
                                schemaGrammar.addIDConstraintDecl(xSElementDecl, keyRef);
                            }
                            String strSchemaDocument2SystemId = this.fSchemaHandler.schemaDocument2SystemId(xSDocumentInfo);
                            IdentityConstraint iDConstraintDecl = schemaGrammar.getIDConstraintDecl(keyRef.getIdentityConstraintName(), strSchemaDocument2SystemId);
                            if (iDConstraintDecl == null) {
                                schemaGrammar.addIDConstraintDecl(xSElementDecl, keyRef, strSchemaDocument2SystemId);
                            }
                            if (this.fSchemaHandler.fTolerateDuplicates) {
                                if (iDConstraintDecl != null && (iDConstraintDecl instanceof KeyRef)) {
                                    keyRef = (KeyRef) iDConstraintDecl;
                                }
                                this.fSchemaHandler.addIDConstraintDecl(keyRef);
                            }
                        }
                    }
                }
            }
        }
        this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
    }
}
