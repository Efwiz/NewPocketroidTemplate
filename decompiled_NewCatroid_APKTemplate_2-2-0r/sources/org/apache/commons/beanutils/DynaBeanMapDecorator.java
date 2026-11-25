package org.apache.commons.beanutils;

/* JADX INFO: loaded from: classes12.dex */
@Deprecated
public class DynaBeanMapDecorator extends BaseDynaBeanMapDecorator<Object> {
    @Override // org.apache.commons.beanutils.BaseDynaBeanMapDecorator
    protected Object convertKey(String str) {
        return str;
    }

    public DynaBeanMapDecorator(DynaBean dynaBean, boolean z) {
        super(dynaBean, z);
    }

    public DynaBeanMapDecorator(DynaBean dynaBean) {
        super(dynaBean);
    }
}
