package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.AnnotationBinding;
import org.eclipse.jdt.internal.compiler.lookup.ArrayBinding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.LookupEnvironment;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding;

/* JADX INFO: loaded from: classes14.dex */
public class ClassLiteralAccess extends Expression {
    FieldBinding syntheticField;
    public TypeBinding targetType;
    public TypeReference type;

    public ClassLiteralAccess(int i, TypeReference typeReference) {
        this.type = typeReference;
        typeReference.bits |= 1073741824;
        this.sourceStart = typeReference.sourceStart;
        this.sourceEnd = i;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        SourceTypeBinding sourceTypeBindingEnclosingSourceType = blockScope.outerMostClassScope().enclosingSourceType();
        if (!sourceTypeBindingEnclosingSourceType.isInterface() && !this.targetType.isBaseType() && blockScope.compilerOptions().targetJDK < ClassFileConstants.JDK1_5) {
            this.syntheticField = sourceTypeBindingEnclosingSourceType.addSyntheticFieldForClassLiteral(this.targetType, blockScope);
        }
        return flowInfo;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public void generateCode(BlockScope blockScope, CodeStream codeStream, boolean z) {
        int i = codeStream.position;
        if (z) {
            codeStream.generateClassLiteralAccessForType(this.type.resolvedType, this.syntheticField);
            codeStream.generateImplicitConversion(this.implicitConversion);
        }
        codeStream.recordPositionsFrom(i, this.sourceStart);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public StringBuffer printExpression(int i, StringBuffer stringBuffer) {
        return this.type.print(0, stringBuffer).append(".class");
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public TypeBinding resolveType(BlockScope blockScope) {
        TypeBinding typeBindingBoxing;
        this.constant = Constant.NotAConstant;
        TypeBinding typeBindingResolveType = this.type.resolveType(blockScope, true);
        this.targetType = typeBindingResolveType;
        if (typeBindingResolveType == null) {
            return null;
        }
        LookupEnvironment lookupEnvironmentEnvironment = blockScope.environment();
        TypeBinding typeBindingConvertToRawType = lookupEnvironmentEnvironment.convertToRawType(this.targetType, true);
        this.targetType = typeBindingConvertToRawType;
        if (typeBindingConvertToRawType.isArrayType()) {
            TypeBinding typeBinding = ((ArrayBinding) this.targetType).leafComponentType;
            if (typeBinding == TypeBinding.VOID) {
                blockScope.problemReporter().cannotAllocateVoidArray(this);
                return null;
            }
            if (typeBinding.isTypeVariable()) {
                blockScope.problemReporter().illegalClassLiteralForTypeVariable((TypeVariableBinding) typeBinding, this);
            }
        } else if (this.targetType.isTypeVariable()) {
            blockScope.problemReporter().illegalClassLiteralForTypeVariable((TypeVariableBinding) this.targetType, this);
        }
        ReferenceBinding javaLangClass = blockScope.getJavaLangClass();
        if (blockScope.compilerOptions().sourceLevel >= ClassFileConstants.JDK1_5) {
            if (this.targetType.f626id == 6) {
                typeBindingBoxing = lookupEnvironmentEnvironment.getResolvedType(JAVA_LANG_VOID, blockScope);
            } else {
                typeBindingBoxing = blockScope.boxing(this.targetType);
            }
            if (lookupEnvironmentEnvironment.usesNullTypeAnnotations()) {
                typeBindingBoxing = lookupEnvironmentEnvironment.createAnnotatedType(typeBindingBoxing, new AnnotationBinding[]{lookupEnvironmentEnvironment.getNonNullAnnotation()});
            }
            this.resolvedType = lookupEnvironmentEnvironment.createParameterizedType(javaLangClass, new TypeBinding[]{typeBindingBoxing}, null);
        } else {
            this.resolvedType = javaLangClass;
        }
        return this.resolvedType;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            this.type.traverse(aSTVisitor, blockScope);
        }
        aSTVisitor.endVisit(this, blockScope);
    }
}
