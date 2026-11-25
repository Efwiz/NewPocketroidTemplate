package org.xmlunit.builder.javax_jaxb;

import org.xmlunit.builder.JaxbBuilder;
import org.xmlunit.builder.JaxbBuilderFactory;

/* JADX INFO: loaded from: classes3.dex */
public class DefaultJaxbBuilderFactory implements JaxbBuilderFactory {
    @Override // org.xmlunit.builder.JaxbBuilderFactory
    public JaxbBuilder create(Object obj) {
        return new JavaxJaxbBuilder(obj);
    }
}
