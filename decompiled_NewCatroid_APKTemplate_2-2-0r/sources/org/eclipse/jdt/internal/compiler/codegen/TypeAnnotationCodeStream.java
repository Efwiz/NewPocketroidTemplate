package org.eclipse.jdt.internal.compiler.codegen;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jdt.internal.compiler.ClassFile;
import org.eclipse.jdt.internal.compiler.ast.ArrayAllocationExpression;
import org.eclipse.jdt.internal.compiler.ast.TypeReference;
import org.eclipse.jdt.internal.compiler.lookup.ArrayBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class TypeAnnotationCodeStream extends StackMapFrameCodeStream {
    public List allTypeAnnotationContexts;

    public TypeAnnotationCodeStream(ClassFile classFile) {
        super(classFile);
        this.generateAttributes |= 32;
        this.allTypeAnnotationContexts = new ArrayList();
    }

    private void addAnnotationContext(TypeReference typeReference, int i, int i2, ArrayAllocationExpression arrayAllocationExpression) {
        arrayAllocationExpression.getAllAnnotationContexts(i2, i, this.allTypeAnnotationContexts);
    }

    private void addAnnotationContext(TypeReference typeReference, int i, int i2) {
        typeReference.getAllAnnotationContexts(i2, i, this.allTypeAnnotationContexts);
    }

    private void addAnnotationContext(TypeReference typeReference, int i, int i2, int i3) {
        typeReference.getAllAnnotationContexts(i3, i, i2, this.allTypeAnnotationContexts);
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.CodeStream
    public void instance_of(TypeReference typeReference, TypeBinding typeBinding) {
        if (typeReference != null && (typeReference.bits & 1048576) != 0) {
            addAnnotationContext(typeReference, this.position, 67);
        }
        super.instance_of(typeReference, typeBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.CodeStream
    public void multianewarray(TypeReference typeReference, TypeBinding typeBinding, int i, ArrayAllocationExpression arrayAllocationExpression) {
        if (typeReference != null && (typeReference.bits & 1048576) != 0) {
            addAnnotationContext(typeReference, this.position, 68, arrayAllocationExpression);
        }
        super.multianewarray(typeReference, typeBinding, i, arrayAllocationExpression);
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.CodeStream
    public void new_(TypeReference typeReference, TypeBinding typeBinding) {
        if (typeReference != null && (typeReference.bits & 1048576) != 0) {
            addAnnotationContext(typeReference, this.position, 68);
        }
        super.new_(typeReference, typeBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.CodeStream
    public void newArray(TypeReference typeReference, ArrayAllocationExpression arrayAllocationExpression, ArrayBinding arrayBinding) {
        if (typeReference != null && (typeReference.bits & 1048576) != 0) {
            addAnnotationContext(typeReference, this.position, 68, arrayAllocationExpression);
        }
        super.newArray(typeReference, arrayAllocationExpression, arrayBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.CodeStream
    public void checkcast(TypeReference typeReference, TypeBinding typeBinding, int i) {
        if (typeReference != null) {
            TypeReference[] typeReferences = typeReference.getTypeReferences();
            for (int length = typeReferences.length - 1; length >= 0; length--) {
                TypeReference typeReference2 = typeReferences[length];
                if (typeReference2 != null) {
                    if ((typeReference2.bits & 1048576) != 0) {
                        if (!typeReference2.resolvedType.isBaseType()) {
                            addAnnotationContext(typeReference2, this.position, length, 71);
                        } else {
                            addAnnotationContext(typeReference2, i, length, 71);
                        }
                    }
                    if (!typeReference2.resolvedType.isBaseType()) {
                        super.checkcast(typeReference2, typeReference2.resolvedType, i);
                    }
                }
            }
            return;
        }
        super.checkcast(null, typeBinding, i);
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.CodeStream
    public void invoke(byte b, MethodBinding methodBinding, TypeBinding typeBinding, TypeReference[] typeReferenceArr) {
        if (typeReferenceArr != null) {
            int i = methodBinding.isConstructor() ? 72 : 73;
            int length = typeReferenceArr.length;
            for (int i2 = 0; i2 < length; i2++) {
                TypeReference typeReference = typeReferenceArr[i2];
                if ((typeReference.bits & 1048576) != 0) {
                    addAnnotationContext(typeReference, this.position, i2, i);
                }
            }
        }
        super.invoke(b, methodBinding, typeBinding, typeReferenceArr);
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.CodeStream
    public void invokeDynamic(int i, int i2, int i3, char[] cArr, char[] cArr2, boolean z, TypeReference typeReference, TypeReference[] typeReferenceArr) {
        if (typeReference != null && (typeReference.bits & 1048576) != 0) {
            if (z) {
                addAnnotationContext(typeReference, this.position, 0, 69);
            } else {
                addAnnotationContext(typeReference, this.position, 0, 70);
            }
        }
        if (typeReferenceArr != null) {
            int i4 = z ? 74 : 75;
            int length = typeReferenceArr.length;
            for (int i5 = 0; i5 < length; i5++) {
                TypeReference typeReference2 = typeReferenceArr[i5];
                if ((typeReference2.bits & 1048576) != 0) {
                    addAnnotationContext(typeReference2, this.position, i5, i4);
                }
            }
        }
        super.invokeDynamic(i, i2, i3, cArr, cArr2, z, typeReference, typeReferenceArr);
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.StackMapFrameCodeStream, org.eclipse.jdt.internal.compiler.codegen.CodeStream
    public void reset(ClassFile classFile) {
        super.reset(classFile);
        this.allTypeAnnotationContexts = new ArrayList();
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.StackMapFrameCodeStream, org.eclipse.jdt.internal.compiler.codegen.CodeStream
    public void init(ClassFile classFile) {
        super.init(classFile);
        this.allTypeAnnotationContexts = new ArrayList();
    }
}
