package org.xmlunit.builder;

/* JADX INFO: loaded from: classes3.dex */
public abstract class JaxbBuilder implements Input.Builder {
    private Object marshaller;
    private final Object object;
    private boolean useObjectFactory;

    protected JaxbBuilder(Object obj) {
        this.object = obj;
    }

    public JaxbBuilder withMarshaller(Object obj) {
        this.marshaller = obj;
        return this;
    }

    public JaxbBuilder useObjectFactory() {
        this.useObjectFactory = true;
        return this;
    }

    protected final Object getObject() {
        return this.object;
    }

    protected final Object getMarshaller() {
        return this.marshaller;
    }

    protected final boolean getUseObjectFactory() {
        return this.useObjectFactory;
    }
}
