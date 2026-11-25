package org.eclipse.jdt.internal.compiler.apt.model;

import org.eclipse.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;

/* JADX INFO: loaded from: classes14.dex */
public class AnnotationMemberValue extends AnnotationValueImpl {
    private final MethodBinding _methodBinding;

    public AnnotationMemberValue(BaseProcessingEnvImpl baseProcessingEnvImpl, Object obj, MethodBinding methodBinding) {
        super(baseProcessingEnvImpl, obj, methodBinding.returnType);
        this._methodBinding = methodBinding;
    }

    public MethodBinding getMethodBinding() {
        return this._methodBinding;
    }
}
