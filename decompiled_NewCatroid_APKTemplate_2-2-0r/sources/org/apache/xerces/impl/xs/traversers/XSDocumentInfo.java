package org.apache.xerces.impl.xs.traversers;

import java.util.Stack;
import java.util.Vector;
import org.apache.xerces.impl.validation.ValidationState;
import org.apache.xerces.impl.xs.SchemaNamespaceSupport;
import org.apache.xerces.impl.xs.XMLSchemaException;
import org.apache.xerces.impl.xs.opti.SchemaDOM;
import org.apache.xerces.impl.xs.util.XInt;
import org.apache.xerces.util.SymbolTable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes13.dex */
class XSDocumentInfo {
    protected boolean fAreLocalAttributesQualified;
    protected boolean fAreLocalElementsQualified;
    protected XSAttributeChecker fAttrChecker;
    protected short fBlockDefault;
    protected short fFinalDefault;
    protected boolean fIsChameleonSchema;
    protected SchemaNamespaceSupport fNamespaceSupport;
    protected SchemaNamespaceSupport fNamespaceSupportRoot;
    protected Object[] fSchemaAttrs;
    protected Element fSchemaElement;
    SymbolTable fSymbolTable;
    String fTargetNamespace;
    protected Stack SchemaNamespaceSupportStack = new Stack();
    Vector fImportedNS = new Vector();
    protected ValidationState fValidationContext = new ValidationState();
    protected XSAnnotationInfo fAnnotations = null;
    private Vector fReportedTNS = null;

    XSDocumentInfo(Element element, XSAttributeChecker xSAttributeChecker, SymbolTable symbolTable) throws XMLSchemaException {
        this.fSymbolTable = null;
        this.fSchemaElement = element;
        SchemaNamespaceSupport schemaNamespaceSupport = new SchemaNamespaceSupport(element, symbolTable);
        this.fNamespaceSupport = schemaNamespaceSupport;
        schemaNamespaceSupport.reset();
        this.fIsChameleonSchema = false;
        this.fSymbolTable = symbolTable;
        this.fAttrChecker = xSAttributeChecker;
        if (element != null) {
            Object[] objArrCheckAttributes = xSAttributeChecker.checkAttributes(element, true, this);
            this.fSchemaAttrs = objArrCheckAttributes;
            if (objArrCheckAttributes == null) {
                throw new XMLSchemaException(null, null);
            }
            this.fAreLocalAttributesQualified = ((XInt) objArrCheckAttributes[XSAttributeChecker.ATTIDX_AFORMDEFAULT]).intValue() == 1;
            this.fAreLocalElementsQualified = ((XInt) this.fSchemaAttrs[XSAttributeChecker.ATTIDX_EFORMDEFAULT]).intValue() == 1;
            this.fBlockDefault = ((XInt) this.fSchemaAttrs[XSAttributeChecker.ATTIDX_BLOCKDEFAULT]).shortValue();
            this.fFinalDefault = ((XInt) this.fSchemaAttrs[XSAttributeChecker.ATTIDX_FINALDEFAULT]).shortValue();
            String str = (String) this.fSchemaAttrs[XSAttributeChecker.ATTIDX_TARGETNAMESPACE];
            this.fTargetNamespace = str;
            if (str != null) {
                this.fTargetNamespace = symbolTable.addSymbol(str);
            }
            this.fNamespaceSupportRoot = new SchemaNamespaceSupport(this.fNamespaceSupport);
            this.fValidationContext.setNamespaceSupport(this.fNamespaceSupport);
            this.fValidationContext.setSymbolTable(symbolTable);
        }
    }

    public void addAllowedNS(String str) {
        Vector vector = this.fImportedNS;
        if (str == null) {
            str = "";
        }
        vector.addElement(str);
    }

    void addAnnotation(XSAnnotationInfo xSAnnotationInfo) {
        xSAnnotationInfo.next = this.fAnnotations;
        this.fAnnotations = xSAnnotationInfo;
    }

    void backupNSSupport(SchemaNamespaceSupport schemaNamespaceSupport) {
        this.SchemaNamespaceSupportStack.push(this.fNamespaceSupport);
        if (schemaNamespaceSupport == null) {
            schemaNamespaceSupport = this.fNamespaceSupportRoot;
        }
        SchemaNamespaceSupport schemaNamespaceSupport2 = new SchemaNamespaceSupport(schemaNamespaceSupport);
        this.fNamespaceSupport = schemaNamespaceSupport2;
        this.fValidationContext.setNamespaceSupport(schemaNamespaceSupport2);
    }

    XSAnnotationInfo getAnnotations() {
        return this.fAnnotations;
    }

    Object[] getSchemaAttrs() {
        return this.fSchemaAttrs;
    }

    public boolean isAllowedNS(String str) {
        Vector vector = this.fImportedNS;
        if (str == null) {
            str = "";
        }
        return vector.contains(str);
    }

    final boolean needReportTNSError(String str) {
        Vector vector = this.fReportedTNS;
        if (vector == null) {
            this.fReportedTNS = new Vector();
        } else if (vector.contains(str)) {
            return false;
        }
        this.fReportedTNS.addElement(str);
        return true;
    }

    void removeAnnotations() {
        this.fAnnotations = null;
    }

    void restoreNSSupport() {
        SchemaNamespaceSupport schemaNamespaceSupport = (SchemaNamespaceSupport) this.SchemaNamespaceSupportStack.pop();
        this.fNamespaceSupport = schemaNamespaceSupport;
        this.fValidationContext.setNamespaceSupport(schemaNamespaceSupport);
    }

    void returnSchemaAttrs() {
        this.fAttrChecker.returnAttrArray(this.fSchemaAttrs, null);
        this.fSchemaAttrs = null;
    }

    public String toString() {
        String str;
        String documentURI;
        StringBuffer stringBuffer = new StringBuffer();
        if (this.fTargetNamespace == null) {
            str = "no targetNamspace";
        } else {
            stringBuffer.append("targetNamespace is ");
            str = this.fTargetNamespace;
        }
        stringBuffer.append(str);
        Element element = this.fSchemaElement;
        Document ownerDocument = element != null ? element.getOwnerDocument() : null;
        if ((ownerDocument instanceof SchemaDOM) && (documentURI = ownerDocument.getDocumentURI()) != null && documentURI.length() > 0) {
            stringBuffer.append(" :: schemaLocation is ");
            stringBuffer.append(documentURI);
        }
        return stringBuffer.toString();
    }
}
