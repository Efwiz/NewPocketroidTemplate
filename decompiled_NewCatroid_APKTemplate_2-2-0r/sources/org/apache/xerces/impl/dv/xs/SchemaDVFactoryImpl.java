package org.apache.xerces.impl.dv.xs;

import org.apache.xerces.impl.dv.XSSimpleType;
import org.apache.xerces.util.SymbolHash;

/* JADX INFO: loaded from: classes13.dex */
public class SchemaDVFactoryImpl extends BaseSchemaDVFactory {
    static final SymbolHash fBuiltInTypes = new SymbolHash();

    static {
        createBuiltInTypes();
    }

    static void createBuiltInTypes() {
        createBuiltInTypes(fBuiltInTypes, XSSimpleTypeDecl.fAnySimpleType);
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
