package org.xmlunit.builder;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import org.xmlunit.builder.TransformationBuilderBase;

/* JADX INFO: loaded from: classes3.dex */
public interface TransformationBuilderBase<B extends TransformationBuilderBase<B>> {
    B usingFactory(TransformerFactory transformerFactory);

    B withOutputProperty(String str, String str2);

    B withParameter(String str, Object obj);

    B withStylesheet(Source source);

    B withURIResolver(URIResolver uRIResolver);
}
