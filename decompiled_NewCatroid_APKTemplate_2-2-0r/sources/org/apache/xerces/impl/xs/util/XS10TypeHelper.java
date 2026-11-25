package org.apache.xerces.impl.xs.util;

import org.apache.xerces.impl.dv.xs.XSSimpleTypeDecl;
import org.apache.xerces.impl.xs.XSComplexTypeDecl;
import org.apache.xerces.xs.XSSimpleTypeDefinition;
import org.apache.xerces.xs.XSTypeDefinition;

/* JADX INFO: loaded from: classes13.dex */
public class XS10TypeHelper {
    private XS10TypeHelper() {
    }

    public static String getSchemaTypeName(XSTypeDefinition xSTypeDefinition) {
        return xSTypeDefinition instanceof XSSimpleTypeDefinition ? ((XSSimpleTypeDecl) xSTypeDefinition).getTypeName() : ((XSComplexTypeDecl) xSTypeDefinition).getTypeName();
    }
}
