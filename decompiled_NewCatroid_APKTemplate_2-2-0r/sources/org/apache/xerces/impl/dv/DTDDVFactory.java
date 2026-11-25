package org.apache.xerces.impl.dv;

import java.util.Hashtable;

/* JADX INFO: loaded from: classes13.dex */
public abstract class DTDDVFactory {
    private static final String DEFAULT_FACTORY_CLASS = "org.apache.xerces.impl.dv.dtd.DTDDVFactoryImpl";

    protected DTDDVFactory() {
    }

    public static final DTDDVFactory getInstance() throws DVFactoryException {
        return getInstance(DEFAULT_FACTORY_CLASS);
    }

    public static final DTDDVFactory getInstance(String str) throws DVFactoryException {
        try {
            DTDDVFactory dTDDVFactory = (DTDDVFactory) ObjectFactory.newInstance(str, ObjectFactory.findClassLoader(), true);
            return dTDDVFactory;
        } catch (ClassCastException unused) {
            throw new DVFactoryException("DTD factory class " + str + " does not extend from DTDDVFactory.");
        }
    }

    public abstract DatatypeValidator getBuiltInDV(String str);

    public abstract Hashtable getBuiltInTypes();
}
