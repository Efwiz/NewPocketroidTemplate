package org.xmlunit.builder;

import java.util.Iterator;
import java.util.ServiceLoader;
import org.xmlunit.builder.javax_jaxb.DefaultJaxbBuilderFactory;

/* JADX INFO: loaded from: classes3.dex */
class JaxbBuilderFactoryLocator {
    JaxbBuilderFactoryLocator() {
    }

    static JaxbBuilderFactory getFactory() {
        Iterator it = ServiceLoader.load(JaxbBuilderFactory.class).iterator();
        return it.hasNext() ? (JaxbBuilderFactory) it.next() : new DefaultJaxbBuilderFactory();
    }
}
