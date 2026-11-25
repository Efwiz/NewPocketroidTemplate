package org.eclipse.jdt.internal.compiler.apt.dispatch;

import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;
import org.eclipse.jdt.internal.compiler.batch.Main;

/* JADX INFO: loaded from: classes14.dex */
public class BatchMessagerImpl extends BaseMessagerImpl implements Messager {
    private final Main _compiler;
    private final BaseProcessingEnvImpl _processingEnv;

    public BatchMessagerImpl(BaseProcessingEnvImpl baseProcessingEnvImpl, Main main) {
        this._compiler = main;
        this._processingEnv = baseProcessingEnvImpl;
    }

    public void printMessage(Diagnostic.Kind kind, CharSequence charSequence) {
        printMessage(kind, charSequence, null, null, null);
    }

    public void printMessage(Diagnostic.Kind kind, CharSequence charSequence, Element element) {
        printMessage(kind, charSequence, element, null, null);
    }

    public void printMessage(Diagnostic.Kind kind, CharSequence charSequence, Element element, AnnotationMirror annotationMirror) {
        printMessage(kind, charSequence, element, annotationMirror, null);
    }

    public void printMessage(Diagnostic.Kind kind, CharSequence charSequence, Element element, AnnotationMirror annotationMirror, AnnotationValue annotationValue) {
        if (kind == Diagnostic.Kind.ERROR) {
            this._processingEnv.setErrorRaised(true);
        }
        AptProblem aptProblemCreateProblem = createProblem(kind, charSequence, element, annotationMirror, annotationValue);
        if (aptProblemCreateProblem != null) {
            this._compiler.addExtraProblems(aptProblemCreateProblem);
        }
    }
}
