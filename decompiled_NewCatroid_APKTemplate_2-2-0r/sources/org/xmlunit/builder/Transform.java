package org.xmlunit.builder;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import org.w3c.dom.Document;

/* JADX INFO: loaded from: classes3.dex */
public final class Transform {

    public interface Builder extends TransformationBuilderBase<Builder> {
        TransformationResult build();
    }

    public interface TransformationResult {
        void to(Result result);

        Document toDocument();

        String toString();
    }

    private Transform() {
    }

    private static class TransformationBuilder extends AbstractTransformationBuilder<Builder> implements Builder, TransformationResult {
        @Override // org.xmlunit.builder.Transform.Builder
        public TransformationResult build() {
            return this;
        }

        private TransformationBuilder(Source source) {
            super(source);
        }

        @Override // org.xmlunit.builder.Transform.TransformationResult
        public String toString() {
            return getHelper().transformToString();
        }

        @Override // org.xmlunit.builder.Transform.TransformationResult
        public Document toDocument() {
            return getHelper().transformToDocument();
        }

        @Override // org.xmlunit.builder.Transform.TransformationResult
        public void to(Result result) {
            getHelper().transformTo(result);
        }
    }

    public static Builder source(Source source) {
        return new TransformationBuilder(source);
    }
}
