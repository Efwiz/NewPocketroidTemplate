package org.jf.dexlib2.rewriter;

import org.jf.dexlib2.base.BaseAnnotationElement;
import org.jf.dexlib2.iface.AnnotationElement;
import org.jf.dexlib2.iface.value.EncodedValue;

/* JADX INFO: loaded from: classes6.dex */
public class AnnotationElementRewriter implements Rewriter<AnnotationElement> {
    protected final Rewriters rewriters;

    public AnnotationElementRewriter(Rewriters rewriters) {
        this.rewriters = rewriters;
    }

    @Override // org.jf.dexlib2.rewriter.Rewriter
    public AnnotationElement rewrite(AnnotationElement annotationElement) {
        return new RewrittenAnnotationElement(annotationElement);
    }

    protected class RewrittenAnnotationElement extends BaseAnnotationElement {
        protected AnnotationElement annotationElement;

        public RewrittenAnnotationElement(AnnotationElement annotationElement) {
            this.annotationElement = annotationElement;
        }

        @Override // org.jf.dexlib2.iface.AnnotationElement
        public String getName() {
            return this.annotationElement.getName();
        }

        @Override // org.jf.dexlib2.iface.AnnotationElement
        public EncodedValue getValue() {
            return AnnotationElementRewriter.this.rewriters.getEncodedValueRewriter().rewrite(this.annotationElement.getValue());
        }
    }
}
