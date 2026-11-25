package org.eclipse.jdt.internal.compiler.ast;

import io.netty.util.internal.StringUtil;
import java.util.List;
import okhttp3.HttpUrl;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.ArrayBinding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.TagBits;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class ArrayAllocationExpression extends Expression {
    public Annotation[][] annotationsOnDimensions;
    public Expression[] dimensions;
    public ArrayInitializer initializer;
    public TypeReference type;

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        int length = this.dimensions.length;
        for (int i = 0; i < length; i++) {
            Expression expression = this.dimensions[i];
            if (expression != null) {
                flowInfo = expression.analyseCode(blockScope, flowContext, flowInfo);
                expression.checkNPEbyUnboxing(blockScope, flowContext, flowInfo);
            }
        }
        flowContext.recordAbruptExit();
        ArrayInitializer arrayInitializer = this.initializer;
        return arrayInitializer != null ? arrayInitializer.analyseCode(blockScope, flowContext, flowInfo) : flowInfo;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public void generateCode(BlockScope blockScope, CodeStream codeStream, boolean z) {
        Expression expression;
        int i = codeStream.position;
        ArrayInitializer arrayInitializer = this.initializer;
        if (arrayInitializer != null) {
            arrayInitializer.generateCode(this.type, this, blockScope, codeStream, z);
            return;
        }
        int length = this.dimensions.length;
        int i2 = 0;
        for (int i3 = 0; i3 < length && (expression = this.dimensions[i3]) != null; i3++) {
            expression.generateCode(blockScope, codeStream, true);
            i2++;
        }
        if (i2 == 1) {
            codeStream.newArray(this.type, this, (ArrayBinding) this.resolvedType);
        } else {
            codeStream.multianewarray(this.type, this.resolvedType, i2, this);
        }
        if (z) {
            codeStream.generateImplicitConversion(this.implicitConversion);
        } else {
            codeStream.pop();
        }
        codeStream.recordPositionsFrom(i, this.sourceStart);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public StringBuffer printExpression(int i, StringBuffer stringBuffer) {
        stringBuffer.append("new ");
        this.type.print(0, stringBuffer);
        for (int i2 = 0; i2 < this.dimensions.length; i2++) {
            Annotation[][] annotationArr = this.annotationsOnDimensions;
            if (annotationArr != null && annotationArr[i2] != null) {
                stringBuffer.append(StringUtil.SPACE);
                printAnnotations(this.annotationsOnDimensions[i2], stringBuffer);
                stringBuffer.append(StringUtil.SPACE);
            }
            if (this.dimensions[i2] == null) {
                stringBuffer.append(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
            } else {
                stringBuffer.append('[');
                this.dimensions[i2].printExpression(0, stringBuffer);
                stringBuffer.append(']');
            }
        }
        ArrayInitializer arrayInitializer = this.initializer;
        if (arrayInitializer != null) {
            arrayInitializer.printExpression(0, stringBuffer);
        }
        return stringBuffer;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public TypeBinding resolveType(BlockScope blockScope) {
        TypeBinding typeBindingResolveTypeExpecting;
        TypeBinding typeBindingResolveType = this.type.resolveType(blockScope, true);
        this.constant = Constant.NotAConstant;
        if (typeBindingResolveType == TypeBinding.VOID) {
            blockScope.problemReporter().cannotAllocateVoidArray(this);
            typeBindingResolveType = null;
        }
        int length = this.dimensions.length;
        int i = -1;
        while (true) {
            length--;
            if (length < 0) {
                break;
            }
            if (this.dimensions[length] == null) {
                if (i > 0) {
                    blockScope.problemReporter().incorrectLocationForNonEmptyDimension(this, i);
                    break;
                }
            } else if (i < 0) {
                i = length;
            }
        }
        if (this.initializer == null) {
            if (i < 0) {
                blockScope.problemReporter().mustDefineDimensionsOrInitializer(this);
            }
            if (typeBindingResolveType != null && !typeBindingResolveType.isReifiable()) {
                blockScope.problemReporter().illegalGenericArray(typeBindingResolveType, this);
            }
        } else if (i >= 0) {
            blockScope.problemReporter().cannotDefineDimensionsAndInitializer(this);
        }
        for (int i2 = 0; i2 <= i; i2++) {
            Expression expression = this.dimensions[i2];
            if (expression != null && (typeBindingResolveTypeExpecting = expression.resolveTypeExpecting(blockScope, TypeBinding.INT)) != null) {
                this.dimensions[i2].computeConversion(blockScope, TypeBinding.INT, typeBindingResolveTypeExpecting);
            }
        }
        if (typeBindingResolveType != null) {
            if (this.dimensions.length > 255) {
                blockScope.problemReporter().tooManyDimensions(this);
            }
            if (this.type.annotations != null && (typeBindingResolveType.tagBits & TagBits.AnnotationNullMASK) == TagBits.AnnotationNullMASK) {
                blockScope.problemReporter().contradictoryNullAnnotations(this.type.annotations[this.type.annotations.length - 1]);
            }
            this.resolvedType = blockScope.createArrayType(typeBindingResolveType, this.dimensions.length);
            Annotation[][] annotationArr = this.annotationsOnDimensions;
            if (annotationArr != null) {
                this.resolvedType = resolveAnnotations(blockScope, annotationArr, this.resolvedType);
                long[] jArr = ((ArrayBinding) this.resolvedType).nullTagBitsPerDimension;
                if (jArr != null) {
                    for (int i3 = 0; i3 < this.annotationsOnDimensions.length; i3++) {
                        if ((jArr[i3] & TagBits.AnnotationNullMASK) == TagBits.AnnotationNullMASK) {
                            blockScope.problemReporter().contradictoryNullAnnotations(this.annotationsOnDimensions[i3]);
                            jArr[i3] = 0;
                        }
                    }
                }
            }
            ArrayInitializer arrayInitializer = this.initializer;
            if (arrayInitializer != null && arrayInitializer.resolveTypeExpecting(blockScope, this.resolvedType) != null) {
                this.initializer.binding = (ArrayBinding) this.resolvedType;
            }
            if ((typeBindingResolveType.tagBits & 128) != 0) {
                return null;
            }
        }
        return this.resolvedType;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            int length = this.dimensions.length;
            this.type.traverse(aSTVisitor, blockScope);
            for (int i = 0; i < length; i++) {
                Annotation[][] annotationArr = this.annotationsOnDimensions;
                Annotation[] annotationArr2 = annotationArr == null ? null : annotationArr[i];
                int length2 = annotationArr2 == null ? 0 : annotationArr2.length;
                for (int i2 = 0; i2 < length2; i2++) {
                    annotationArr2[i2].traverse(aSTVisitor, blockScope);
                }
                Expression expression = this.dimensions[i];
                if (expression != null) {
                    expression.traverse(aSTVisitor, blockScope);
                }
            }
            ArrayInitializer arrayInitializer = this.initializer;
            if (arrayInitializer != null) {
                arrayInitializer.traverse(aSTVisitor, blockScope);
            }
        }
        aSTVisitor.endVisit(this, blockScope);
    }

    public void getAllAnnotationContexts(int i, int i2, List list) {
        TypeReference.AnnotationCollector annotationCollector = new TypeReference.AnnotationCollector(this, i, i2, list);
        this.type.traverse(annotationCollector, (BlockScope) null);
        if (this.annotationsOnDimensions != null) {
            int length = this.dimensions.length;
            for (int i3 = 0; i3 < length; i3++) {
                Annotation[] annotationArr = this.annotationsOnDimensions[i3];
                int length2 = annotationArr == null ? 0 : annotationArr.length;
                for (int i4 = 0; i4 < length2; i4++) {
                    annotationArr[i4].traverse(annotationCollector, (BlockScope) null);
                }
            }
        }
    }

    public Annotation[][] getAnnotationsOnDimensions() {
        return this.annotationsOnDimensions;
    }
}
