package org.apache.xerces.impl.xs.traversers;

import org.apache.xerces.impl.xs.SchemaGrammar;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.apache.xerces.impl.xs.XSElementDecl;
import org.apache.xerces.impl.xs.identity.IdentityConstraint;
import org.apache.xerces.impl.xs.identity.UniqueOrKey;
import org.apache.xerces.util.DOMUtil;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes13.dex */
class XSDUniqueOrKeyTraverser extends XSDAbstractIDConstraintTraverser {
    public XSDUniqueOrKeyTraverser(XSDHandler xSDHandler, XSAttributeChecker xSAttributeChecker) {
        super(xSDHandler, xSAttributeChecker);
    }

    void traverse(Element element, XSElementDecl xSElementDecl, XSDocumentInfo xSDocumentInfo, SchemaGrammar schemaGrammar) {
        Object[] objArrCheckAttributes = this.fAttrChecker.checkAttributes(element, false, xSDocumentInfo);
        String str = (String) objArrCheckAttributes[XSAttributeChecker.ATTIDX_NAME];
        if (str == null) {
            reportSchemaError("s4s-att-must-appear", new Object[]{DOMUtil.getLocalName(element), SchemaSymbols.ATT_NAME}, element);
        } else {
            UniqueOrKey uniqueOrKey = DOMUtil.getLocalName(element).equals(SchemaSymbols.ELT_UNIQUE) ? new UniqueOrKey(xSDocumentInfo.fTargetNamespace, str, xSElementDecl.fName, (short) 3) : new UniqueOrKey(xSDocumentInfo.fTargetNamespace, str, xSElementDecl.fName, (short) 1);
            if (traverseIdentityConstraint(uniqueOrKey, element, xSDocumentInfo, objArrCheckAttributes)) {
                if (schemaGrammar.getIDConstraintDecl(uniqueOrKey.getIdentityConstraintName()) == null) {
                    schemaGrammar.addIDConstraintDecl(xSElementDecl, uniqueOrKey);
                }
                String strSchemaDocument2SystemId = this.fSchemaHandler.schemaDocument2SystemId(xSDocumentInfo);
                IdentityConstraint iDConstraintDecl = schemaGrammar.getIDConstraintDecl(uniqueOrKey.getIdentityConstraintName(), strSchemaDocument2SystemId);
                if (iDConstraintDecl == null) {
                    schemaGrammar.addIDConstraintDecl(xSElementDecl, uniqueOrKey, strSchemaDocument2SystemId);
                }
                if (this.fSchemaHandler.fTolerateDuplicates) {
                    if (iDConstraintDecl != null) {
                        boolean z = iDConstraintDecl instanceof UniqueOrKey;
                    }
                    this.fSchemaHandler.addIDConstraintDecl(uniqueOrKey);
                }
            }
        }
        this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
    }
}
