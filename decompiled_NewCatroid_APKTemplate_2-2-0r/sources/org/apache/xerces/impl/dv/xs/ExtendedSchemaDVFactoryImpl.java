package org.apache.xerces.impl.dv.xs;

import org.apache.xerces.impl.dv.XSSimpleType;
import org.apache.xerces.util.SymbolHash;

/* JADX INFO: loaded from: classes13.dex */
public class ExtendedSchemaDVFactoryImpl extends BaseSchemaDVFactory {
    static SymbolHash fBuiltInTypes = new SymbolHash();

    static {
        createBuiltInTypes();
    }

    static void createBuiltInTypes() {
        createBuiltInTypes(fBuiltInTypes, XSSimpleTypeDecl.fAnyAtomicType);
        fBuiltInTypes.put("anyAtomicType", XSSimpleTypeDecl.fAnyAtomicType);
        XSSimpleTypeDecl xSSimpleTypeDecl = (XSSimpleTypeDecl) fBuiltInTypes.get("duration");
        fBuiltInTypes.put("yearMonthDuration", new XSSimpleTypeDecl(xSSimpleTypeDecl, "yearMonthDuration", (short) 27, (short) 1, false, false, false, true, (short) 46));
        fBuiltInTypes.put("dayTimeDuration", new XSSimpleTypeDecl(xSSimpleTypeDecl, "dayTimeDuration", (short) 28, (short) 1, false, false, false, true, (short) 47));
    }

    @Override // org.apache.xerces.impl.dv.SchemaDVFactory
    public XSSimpleType getBuiltInType(String str) {
        return (XSSimpleType) fBuiltInTypes.get(str);
    }

    @Override // org.apache.xerces.impl.dv.SchemaDVFactory
    public SymbolHash getBuiltInTypes() {
        return fBuiltInTypes.makeClone();
    }
}
