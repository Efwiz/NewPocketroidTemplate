package org.xmlunit.builder;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import org.xmlunit.builder.TransformationBuilderBase;
import org.xmlunit.transform.Transformation;

/* JADX INFO: loaded from: classes3.dex */
abstract class AbstractTransformationBuilder<B extends TransformationBuilderBase<B>> implements TransformationBuilderBase<B> {
    private final Transformation helper;

    private B asB() {
        return this;
    }

    protected AbstractTransformationBuilder(Source source) {
        this.helper = new Transformation(source);
    }

    @Override // org.xmlunit.builder.TransformationBuilderBase
    public B withStylesheet(Source source) {
        this.helper.setStylesheet(source);
        return (B) asB();
    }

    @Override // org.xmlunit.builder.TransformationBuilderBase
    public B withOutputProperty(String str, String str2) {
        this.helper.addOutputProperty(str, str2);
        return (B) asB();
    }

    @Override // org.xmlunit.builder.TransformationBuilderBase
    public B withParameter(String str, Object obj) {
        this.helper.addParameter(str, obj);
        return (B) asB();
    }

    @Override // org.xmlunit.builder.TransformationBuilderBase
    public B usingFactory(TransformerFactory transformerFactory) {
        this.helper.setFactory(transformerFactory);
        return (B) asB();
    }

    @Override // org.xmlunit.builder.TransformationBuilderBase
    public B withURIResolver(URIResolver uRIResolver) {
        this.helper.setURIResolver(uRIResolver);
        return (B) asB();
    }

    protected Transformation getHelper() {
        return this.helper;
    }
}
