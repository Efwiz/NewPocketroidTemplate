package org.apache.xerces.impl.dv;

import org.apache.xerces.util.SymbolHash;
import org.apache.xerces.xs.XSObjectList;

/* JADX INFO: loaded from: classes13.dex */
public abstract class SchemaDVFactory {
    private static final String DEFAULT_FACTORY_CLASS = "org.apache.xerces.impl.dv.xs.SchemaDVFactoryImpl";

    protected SchemaDVFactory() {
    }

    public static final SchemaDVFactory getInstance() throws DVFactoryException {
        return getInstance(DEFAULT_FACTORY_CLASS);
    }

    public static final SchemaDVFactory getInstance(String str) throws DVFactoryException {
        try {
            SchemaDVFactory schemaDVFactory = (SchemaDVFactory) ObjectFactory.newInstance(str, ObjectFactory.findClassLoader(), true);
            return schemaDVFactory;
        } catch (ClassCastException unused) {
            throw new DVFactoryException("Schema factory class " + str + " does not extend from SchemaDVFactory.");
        }
    }

    public abstract XSSimpleType createTypeList(String str, String str2, short s, XSSimpleType xSSimpleType, XSObjectList xSObjectList);

    public abstract XSSimpleType createTypeRestriction(String str, String str2, short s, XSSimpleType xSSimpleType, XSObjectList xSObjectList);

    public abstract XSSimpleType createTypeUnion(String str, String str2, short s, XSSimpleType[] xSSimpleTypeArr, XSObjectList xSObjectList);

    public abstract XSSimpleType getBuiltInType(String str);

    public abstract SymbolHash getBuiltInTypes();
}
