package org.eclipse.jdt.internal.compiler.codegen;

import org.eclipse.jdt.internal.compiler.ast.Annotation;
import org.eclipse.jdt.internal.compiler.ast.Expression;
import org.eclipse.jdt.internal.compiler.ast.Wildcard;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;

/* JADX INFO: loaded from: classes14.dex */
public class AnnotationContext {
    public static final int INVISIBLE = 2;
    public static final int VISIBLE = 1;
    public Annotation annotation;
    public int info;
    public int info2;
    public int targetType;
    public Expression typeReference;
    public LocalVariableBinding variableBinding;
    public int visibility;
    public Wildcard wildcard;

    public AnnotationContext(Annotation annotation, Expression expression, int i, int i2) {
        this.annotation = annotation;
        this.typeReference = expression;
        this.targetType = i;
        this.visibility = i2;
    }

    public String toString() {
        return "AnnotationContext [annotation=" + this.annotation + ", typeReference=" + this.typeReference + ", targetType=" + this.targetType + ", info =" + this.info + ", boundIndex=" + this.info2 + "]";
    }
}
