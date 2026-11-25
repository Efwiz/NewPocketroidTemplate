package org.jf.dexlib2.rewriter;

import org.jf.dexlib2.base.BaseExceptionHandler;
import org.jf.dexlib2.iface.ExceptionHandler;

/* JADX INFO: loaded from: classes6.dex */
public class ExceptionHandlerRewriter implements Rewriter<ExceptionHandler> {
    protected final Rewriters rewriters;

    public ExceptionHandlerRewriter(Rewriters rewriters) {
        this.rewriters = rewriters;
    }

    @Override // org.jf.dexlib2.rewriter.Rewriter
    public ExceptionHandler rewrite(ExceptionHandler exceptionHandler) {
        return new RewrittenExceptionHandler(exceptionHandler);
    }

    protected class RewrittenExceptionHandler extends BaseExceptionHandler {
        protected ExceptionHandler exceptionHandler;

        public RewrittenExceptionHandler(ExceptionHandler exceptionHandler) {
            this.exceptionHandler = exceptionHandler;
        }

        @Override // org.jf.dexlib2.iface.ExceptionHandler
        public String getExceptionType() {
            return (String) RewriterUtils.rewriteNullable(ExceptionHandlerRewriter.this.rewriters.getTypeRewriter(), this.exceptionHandler.getExceptionType());
        }

        @Override // org.jf.dexlib2.iface.ExceptionHandler
        public int getHandlerCodeAddress() {
            return this.exceptionHandler.getHandlerCodeAddress();
        }
    }
}
