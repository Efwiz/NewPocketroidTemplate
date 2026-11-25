package org.jf.dexlib2.rewriter;

import java.util.List;
import java.util.function.Function;
import org.jf.dexlib2.base.reference.BaseMethodReference;
import org.jf.dexlib2.iface.reference.MethodReference;
import org.jf.util.collection.ListUtil;

/* JADX INFO: loaded from: classes6.dex */
public class MethodReferenceRewriter implements Rewriter<MethodReference> {
    protected final Rewriters rewriters;

    public MethodReferenceRewriter(Rewriters rewriters) {
        this.rewriters = rewriters;
    }

    @Override // org.jf.dexlib2.rewriter.Rewriter
    public MethodReference rewrite(MethodReference methodReference) {
        return new RewrittenMethodReference(methodReference);
    }

    protected class RewrittenMethodReference extends BaseMethodReference {
        protected MethodReference methodReference;

        public RewrittenMethodReference(MethodReference methodReference) {
            this.methodReference = methodReference;
        }

        @Override // org.jf.dexlib2.iface.reference.MethodReference, org.jf.dexlib2.iface.Method, org.jf.dexlib2.iface.Member
        public String getDefiningClass() {
            return MethodReferenceRewriter.this.rewriters.getTypeRewriter().rewrite(this.methodReference.getDefiningClass());
        }

        @Override // org.jf.dexlib2.iface.reference.MethodReference, org.jf.dexlib2.iface.Method, org.jf.dexlib2.iface.Member
        public String getName() {
            return this.methodReference.getName();
        }

        @Override // org.jf.dexlib2.iface.reference.MethodReference
        public List<? extends CharSequence> getParameterTypes() {
            return RewriterUtils.rewriteList(MethodReferenceRewriter.this.rewriters.getTypeRewriter(), ListUtil.transform(this.methodReference.getParameterTypes(), new Function<CharSequence, String>() { // from class: org.jf.dexlib2.rewriter.MethodReferenceRewriter.RewrittenMethodReference.1
                @Override // java.util.function.Function
                public String apply(CharSequence charSequence) {
                    return charSequence.toString();
                }
            }));
        }

        @Override // org.jf.dexlib2.iface.reference.MethodReference, org.jf.dexlib2.iface.Method
        public String getReturnType() {
            return MethodReferenceRewriter.this.rewriters.getTypeRewriter().rewrite(this.methodReference.getReturnType());
        }
    }
}
