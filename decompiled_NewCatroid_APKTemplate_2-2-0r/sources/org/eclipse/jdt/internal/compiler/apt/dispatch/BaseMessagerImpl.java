package org.eclipse.jdt.internal.compiler.apt.dispatch;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.tools.Diagnostic;
import org.eclipse.jdt.internal.compiler.CompilationResult;
import org.eclipse.jdt.internal.compiler.apt.model.AnnotationMemberValue;
import org.eclipse.jdt.internal.compiler.apt.model.AnnotationMirrorImpl;
import org.eclipse.jdt.internal.compiler.apt.model.ExecutableElementImpl;
import org.eclipse.jdt.internal.compiler.apt.model.TypeElementImpl;
import org.eclipse.jdt.internal.compiler.apt.model.VariableElementImpl;
import org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.Annotation;
import org.eclipse.jdt.internal.compiler.ast.ArrayInitializer;
import org.eclipse.jdt.internal.compiler.ast.Expression;
import org.eclipse.jdt.internal.compiler.ast.FieldDeclaration;
import org.eclipse.jdt.internal.compiler.ast.LocalDeclaration;
import org.eclipse.jdt.internal.compiler.ast.MemberValuePair;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;
import org.eclipse.jdt.internal.compiler.lookup.AnnotationBinding;
import org.eclipse.jdt.internal.compiler.lookup.AptSourceLocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeIds;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes14.dex */
public class BaseMessagerImpl {
    private static /* synthetic */ int[] $SWITCH_TABLE$javax$lang$model$element$ElementKind;
    private static /* synthetic */ int[] $SWITCH_TABLE$javax$tools$Diagnostic$Kind;
    static final String[] NO_ARGUMENTS = new String[0];

    static /* synthetic */ int[] $SWITCH_TABLE$javax$lang$model$element$ElementKind() {
        int[] iArr = $SWITCH_TABLE$javax$lang$model$element$ElementKind;
        if (iArr != null) {
            return iArr;
        }
        int[] iArr2 = new int[ElementKind.values().length];
        try {
            iArr2[ElementKind.ANNOTATION_TYPE.ordinal()] = 4;
        } catch (NoSuchFieldError unused) {
        }
        try {
            iArr2[ElementKind.CLASS.ordinal()] = 3;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            iArr2[ElementKind.CONSTRUCTOR.ordinal()] = 12;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            iArr2[ElementKind.ENUM.ordinal()] = 2;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            iArr2[ElementKind.ENUM_CONSTANT.ordinal()] = 6;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            iArr2[ElementKind.EXCEPTION_PARAMETER.ordinal()] = 10;
        } catch (NoSuchFieldError unused6) {
        }
        try {
            iArr2[ElementKind.FIELD.ordinal()] = 7;
        } catch (NoSuchFieldError unused7) {
        }
        try {
            iArr2[ElementKind.INSTANCE_INIT.ordinal()] = 14;
        } catch (NoSuchFieldError unused8) {
        }
        try {
            iArr2[ElementKind.INTERFACE.ordinal()] = 5;
        } catch (NoSuchFieldError unused9) {
        }
        try {
            iArr2[ElementKind.LOCAL_VARIABLE.ordinal()] = 9;
        } catch (NoSuchFieldError unused10) {
        }
        try {
            iArr2[ElementKind.METHOD.ordinal()] = 11;
        } catch (NoSuchFieldError unused11) {
        }
        try {
            iArr2[ElementKind.OTHER.ordinal()] = 16;
        } catch (NoSuchFieldError unused12) {
        }
        try {
            iArr2[ElementKind.PACKAGE.ordinal()] = 1;
        } catch (NoSuchFieldError unused13) {
        }
        try {
            iArr2[ElementKind.PARAMETER.ordinal()] = 8;
        } catch (NoSuchFieldError unused14) {
        }
        try {
            iArr2[ElementKind.RESOURCE_VARIABLE.ordinal()] = 17;
        } catch (NoSuchFieldError unused15) {
        }
        try {
            iArr2[ElementKind.STATIC_INIT.ordinal()] = 13;
        } catch (NoSuchFieldError unused16) {
        }
        try {
            iArr2[ElementKind.TYPE_PARAMETER.ordinal()] = 15;
        } catch (NoSuchFieldError unused17) {
        }
        $SWITCH_TABLE$javax$lang$model$element$ElementKind = iArr2;
        return iArr2;
    }

    static /* synthetic */ int[] $SWITCH_TABLE$javax$tools$Diagnostic$Kind() {
        int[] iArr = $SWITCH_TABLE$javax$tools$Diagnostic$Kind;
        if (iArr != null) {
            return iArr;
        }
        int[] iArr2 = new int[Diagnostic.Kind.values().length];
        try {
            iArr2[Diagnostic.Kind.ERROR.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            iArr2[Diagnostic.Kind.MANDATORY_WARNING.ordinal()] = 3;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            iArr2[Diagnostic.Kind.NOTE.ordinal()] = 4;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            iArr2[Diagnostic.Kind.OTHER.ordinal()] = 5;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            iArr2[Diagnostic.Kind.WARNING.ordinal()] = 2;
        } catch (NoSuchFieldError unused5) {
        }
        $SWITCH_TABLE$javax$tools$Diagnostic$Kind = iArr2;
        return iArr2;
    }

    /* JADX WARN: Code duplicated, block: B:49:0x00b6  */
    /* JADX WARN: Code duplicated, block: B:72:0x0105  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r9v0 */
    /* JADX WARN: Type inference failed for: r9v1, types: [org.eclipse.jdt.internal.compiler.impl.ReferenceContext] */
    /* JADX WARN: Type inference failed for: r9v2 */
    /* JADX WARN: Type inference failed for: r9v3 */
    public static AptProblem createProblem(Diagnostic.Kind kind, CharSequence charSequence, Element element, AnnotationMirror annotationMirror, AnnotationValue annotationValue) {
        Annotation[] annotationArr;
        ?? r9;
        int i;
        int i2;
        int i3;
        int i4;
        int iSearchColumnNumber;
        char[] cArr;
        int i5;
        int lineNumber;
        Annotation annotationFindAnnotation;
        Object obj;
        AptSourceLocalVariableBinding aptSourceLocalVariableBinding;
        LocalDeclaration localDeclaration;
        TypeIds typeIdsSourceMethod;
        Annotation[] annotationArr2;
        int i6;
        int i7;
        AbstractMethodDeclaration abstractMethodDeclarationSourceMethod;
        if (element == null) {
            annotationArr = null;
            r9 = 0;
            i = 0;
            i2 = 0;
        } else {
            int i8 = $SWITCH_TABLE$javax$lang$model$element$ElementKind()[element.getKind().ordinal()];
            if (i8 == 2 || i8 == 3 || i8 == 4 || i8 == 5) {
                Binding binding = ((TypeElementImpl) element)._binding;
                if (binding instanceof SourceTypeBinding) {
                    TypeDeclaration typeDeclaration = (TypeDeclaration) ((SourceTypeBinding) binding).scope.referenceContext();
                    annotationArr = typeDeclaration.annotations;
                    i = typeDeclaration.sourceStart;
                    i2 = typeDeclaration.sourceEnd;
                    obj = typeDeclaration;
                    r9 = obj;
                } else {
                    annotationArr = null;
                    r9 = 0;
                    i = 0;
                    i2 = 0;
                }
            } else {
                if (i8 == 7 || i8 == 8) {
                    Binding binding2 = ((VariableElementImpl) element)._binding;
                    if (binding2 instanceof FieldBinding) {
                        FieldBinding fieldBinding = (FieldBinding) binding2;
                        FieldDeclaration fieldDeclarationSourceField = fieldBinding.sourceField();
                        if (fieldDeclarationSourceField != null) {
                            ReferenceBinding referenceBinding = fieldBinding.declaringClass;
                            typeIdsSourceMethod = referenceBinding instanceof SourceTypeBinding ? (TypeDeclaration) ((SourceTypeBinding) referenceBinding).scope.referenceContext() : null;
                            annotationArr2 = fieldDeclarationSourceField.annotations;
                            i6 = fieldDeclarationSourceField.sourceStart;
                            i7 = fieldDeclarationSourceField.sourceEnd;
                            r9 = typeIdsSourceMethod;
                            int i9 = i6;
                            i2 = i7;
                            annotationArr = annotationArr2;
                            i = i9;
                        }
                    } else if ((binding2 instanceof AptSourceLocalVariableBinding) && (localDeclaration = (aptSourceLocalVariableBinding = (AptSourceLocalVariableBinding) binding2).declaration) != null) {
                        MethodBinding methodBinding = aptSourceLocalVariableBinding.methodBinding;
                        typeIdsSourceMethod = methodBinding != null ? methodBinding.sourceMethod() : null;
                        annotationArr2 = localDeclaration.annotations;
                        i6 = localDeclaration.sourceStart;
                        i7 = localDeclaration.sourceEnd;
                        r9 = typeIdsSourceMethod;
                        int i10 = i6;
                        i2 = i7;
                        annotationArr = annotationArr2;
                        i = i10;
                    }
                } else if (i8 == 11 || i8 == 12) {
                    Binding binding3 = ((ExecutableElementImpl) element)._binding;
                    if ((binding3 instanceof MethodBinding) && (abstractMethodDeclarationSourceMethod = ((MethodBinding) binding3).sourceMethod()) != null) {
                        annotationArr = abstractMethodDeclarationSourceMethod.annotations;
                        i = abstractMethodDeclarationSourceMethod.sourceStart;
                        i2 = abstractMethodDeclarationSourceMethod.sourceEnd;
                        obj = abstractMethodDeclarationSourceMethod;
                        r9 = obj;
                    }
                }
                annotationArr = null;
                r9 = 0;
                i = 0;
                i2 = 0;
            }
        }
        StringBuilder sb = new StringBuilder();
        if (charSequence != null) {
            sb.append(charSequence);
        }
        if (annotationMirror == null || annotationArr == null || (annotationFindAnnotation = findAnnotation(annotationArr, ((AnnotationMirrorImpl) annotationMirror)._binding)) == null) {
            i3 = i;
            i4 = i2;
        } else {
            int i11 = annotationFindAnnotation.sourceStart;
            int i12 = annotationFindAnnotation.sourceEnd;
            if (annotationValue == null || !(annotationValue instanceof AnnotationMemberValue)) {
                i3 = i11;
                i4 = i12;
            } else {
                MethodBinding methodBinding2 = ((AnnotationMemberValue) annotationValue).getMethodBinding();
                MemberValuePair[] memberValuePairArrMemberValuePairs = annotationFindAnnotation.memberValuePairs();
                MemberValuePair memberValuePair = null;
                for (int i13 = 0; memberValuePair == null && i13 < memberValuePairArrMemberValuePairs.length; i13++) {
                    if (methodBinding2 == memberValuePairArrMemberValuePairs[i13].binding) {
                        memberValuePair = memberValuePairArrMemberValuePairs[i13];
                    }
                }
                if (memberValuePair != null) {
                    i3 = memberValuePair.sourceStart;
                    i4 = memberValuePair.sourceEnd;
                } else {
                    i3 = i11;
                    i4 = i12;
                }
            }
        }
        if (r9 != 0) {
            CompilationResult compilationResult = r9.compilationResult();
            char[] cArr2 = compilationResult.fileName;
            if (i3 >= 0) {
                int[] lineSeparatorPositions = compilationResult.getLineSeparatorPositions();
                lineNumber = Util.getLineNumber(i3, lineSeparatorPositions, 0, lineSeparatorPositions.length - 1);
            } else {
                lineNumber = 0;
            }
            iSearchColumnNumber = i3 >= 0 ? Util.searchColumnNumber(compilationResult.getLineSeparatorPositions(), lineNumber, i3) : 0;
            cArr = cArr2;
            i5 = lineNumber;
        } else {
            iSearchColumnNumber = 1;
            cArr = null;
            i5 = 0;
        }
        return new AptProblem(r9, cArr, String.valueOf(sb), 0, NO_ARGUMENTS, $SWITCH_TABLE$javax$tools$Diagnostic$Kind()[kind.ordinal()] != 1 ? 0 : 1, i3, i4, i5, iSearchColumnNumber);
    }

    private static Annotation findAnnotation(Annotation[] annotationArr, AnnotationBinding annotationBinding) {
        for (Annotation annotation : annotationArr) {
            Annotation annotationFindAnnotation = findAnnotation(annotation, annotationBinding);
            if (annotationFindAnnotation != null) {
                return annotationFindAnnotation;
            }
        }
        return null;
    }

    private static Annotation findAnnotation(Annotation annotation, AnnotationBinding annotationBinding) {
        Annotation annotationFindAnnotation;
        if (annotationBinding == annotation.getCompilerAnnotation()) {
            return annotation;
        }
        for (MemberValuePair memberValuePair : annotation.memberValuePairs()) {
            Expression expression = memberValuePair.value;
            if (expression instanceof Annotation) {
                Annotation annotationFindAnnotation2 = findAnnotation((Annotation) expression, annotationBinding);
                if (annotationFindAnnotation2 != null) {
                    return annotationFindAnnotation2;
                }
            } else if (expression instanceof ArrayInitializer) {
                for (Expression expression2 : ((ArrayInitializer) expression).expressions) {
                    if ((expression2 instanceof Annotation) && (annotationFindAnnotation = findAnnotation((Annotation) expression2, annotationBinding)) != null) {
                        return annotationFindAnnotation;
                    }
                }
            } else {
                continue;
            }
        }
        return null;
    }
}
