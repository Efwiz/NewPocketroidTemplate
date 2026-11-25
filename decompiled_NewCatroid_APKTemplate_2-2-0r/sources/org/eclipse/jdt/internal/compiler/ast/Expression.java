package org.eclipse.jdt.internal.compiler.ast;

import java.util.ArrayList;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.codegen.BranchLabel;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.impl.ReferenceContext;
import org.eclipse.jdt.internal.compiler.lookup.ArrayBinding;
import org.eclipse.jdt.internal.compiler.lookup.BaseTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.InferenceContext18;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.VariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.WildcardBinding;
import org.eclipse.jdt.internal.compiler.problem.ShouldNotImplement;
import org.eclipse.jdt.internal.compiler.util.Messages;

/* JADX INFO: loaded from: classes14.dex */
public abstract class Expression extends Statement {
    public static Expression[] NO_EXPRESSIONS = new Expression[0];
    public Constant constant;
    public int implicitConversion;
    public TypeBinding resolvedType;
    public int statementEnd = -1;

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        return flowInfo;
    }

    public boolean isCompactableOperation() {
        return false;
    }

    public boolean isExactMethodReference() {
        return false;
    }

    public boolean isFunctionalType() {
        return false;
    }

    public boolean isPertinentToApplicability(TypeBinding typeBinding, MethodBinding methodBinding) {
        return true;
    }

    public boolean isPolyExpression() throws UnsupportedOperationException {
        return false;
    }

    public boolean isPolyExpression(MethodBinding methodBinding) {
        return false;
    }

    public boolean isTypeReference() {
        return false;
    }

    public LocalVariableBinding localVariableBinding() {
        return null;
    }

    public VariableBinding nullAnnotatedVariableBinding(boolean z) {
        return null;
    }

    public int nullStatus(FlowInfo flowInfo, FlowContext flowContext) {
        return 4;
    }

    public abstract StringBuffer printExpression(int i, StringBuffer stringBuffer);

    public Expression resolveExpressionExpecting(TypeBinding typeBinding, Scope scope, InferenceContext18 inferenceContext18) {
        return this;
    }

    public TypeBinding resolveType(BlockScope blockScope) {
        return null;
    }

    public TypeBinding resolveType(ClassScope classScope) {
        return null;
    }

    public void setExpectedType(TypeBinding typeBinding) {
    }

    public void setExpressionContext(ExpressionContext expressionContext) {
    }

    public boolean statementExpression() {
        return false;
    }

    public void tagAsNeedCheckCast() {
    }

    public void tagAsUnnecessaryCast(Scope scope, TypeBinding typeBinding) {
    }

    public Expression toTypeReference() {
        return this;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
    }

    public void traverse(ASTVisitor aSTVisitor, ClassScope classScope) {
    }

    public static final boolean isConstantValueRepresentable(Constant constant, int i, int i2) {
        if (i2 == i) {
            return true;
        }
        switch (i2) {
            case 2:
                switch (i) {
                    case 2:
                        return true;
                    case 3:
                        return constant.byteValue() == constant.charValue();
                    case 4:
                        return constant.shortValue() == constant.charValue();
                    case 5:
                    case 6:
                    default:
                        return false;
                    case 7:
                        return constant.longValue() == ((long) constant.charValue());
                    case 8:
                        return constant.doubleValue() == ((double) constant.charValue());
                    case 9:
                        return constant.floatValue() == ((float) constant.charValue());
                    case 10:
                        return constant.intValue() == constant.charValue();
                }
            case 3:
                switch (i) {
                    case 2:
                        return constant.charValue() == constant.byteValue();
                    case 3:
                        return true;
                    case 4:
                        return constant.shortValue() == constant.byteValue();
                    case 5:
                    case 6:
                    default:
                        return false;
                    case 7:
                        return constant.longValue() == ((long) constant.byteValue());
                    case 8:
                        return constant.doubleValue() == ((double) constant.byteValue());
                    case 9:
                        return constant.floatValue() == ((float) constant.byteValue());
                    case 10:
                        return constant.intValue() == constant.byteValue();
                }
            case 4:
                switch (i) {
                    case 2:
                        return constant.charValue() == constant.shortValue();
                    case 3:
                        return constant.byteValue() == constant.shortValue();
                    case 4:
                        return true;
                    case 5:
                    case 6:
                    default:
                        return false;
                    case 7:
                        return constant.longValue() == ((long) constant.shortValue());
                    case 8:
                        return constant.doubleValue() == ((double) constant.shortValue());
                    case 9:
                        return constant.floatValue() == ((float) constant.shortValue());
                    case 10:
                        return constant.intValue() == constant.shortValue();
                }
            case 5:
            case 6:
            default:
                return false;
            case 7:
                switch (i) {
                    case 2:
                        return ((long) constant.charValue()) == constant.longValue();
                    case 3:
                        return ((long) constant.byteValue()) == constant.longValue();
                    case 4:
                        return ((long) constant.shortValue()) == constant.longValue();
                    case 5:
                    case 6:
                    default:
                        return false;
                    case 7:
                        return true;
                    case 8:
                        return constant.doubleValue() == ((double) constant.longValue());
                    case 9:
                        return constant.floatValue() == ((float) constant.longValue());
                    case 10:
                        return ((long) constant.intValue()) == constant.longValue();
                }
            case 8:
                switch (i) {
                    case 2:
                        return ((double) constant.charValue()) == constant.doubleValue();
                    case 3:
                        return ((double) constant.byteValue()) == constant.doubleValue();
                    case 4:
                        return ((double) constant.shortValue()) == constant.doubleValue();
                    case 5:
                    case 6:
                    default:
                        return false;
                    case 7:
                        return ((double) constant.longValue()) == constant.doubleValue();
                    case 8:
                        return true;
                    case 9:
                        return ((double) constant.floatValue()) == constant.doubleValue();
                    case 10:
                        return ((double) constant.intValue()) == constant.doubleValue();
                }
            case 9:
                switch (i) {
                    case 2:
                        return ((float) constant.charValue()) == constant.floatValue();
                    case 3:
                        return ((float) constant.byteValue()) == constant.floatValue();
                    case 4:
                        return ((float) constant.shortValue()) == constant.floatValue();
                    case 5:
                    case 6:
                    default:
                        return false;
                    case 7:
                        return ((float) constant.longValue()) == constant.floatValue();
                    case 8:
                        return constant.doubleValue() == ((double) constant.floatValue());
                    case 9:
                        return true;
                    case 10:
                        return ((float) constant.intValue()) == constant.floatValue();
                }
            case 10:
                switch (i) {
                    case 2:
                        return constant.charValue() == constant.intValue();
                    case 3:
                        return constant.byteValue() == constant.intValue();
                    case 4:
                        return constant.shortValue() == constant.intValue();
                    case 5:
                    case 6:
                    default:
                        return false;
                    case 7:
                        return constant.longValue() == ((long) constant.intValue());
                    case 8:
                        return constant.doubleValue() == ((double) constant.intValue());
                    case 9:
                        return constant.floatValue() == ((float) constant.intValue());
                    case 10:
                        return true;
                }
        }
    }

    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo, boolean z) {
        return analyseCode(blockScope, flowContext, flowInfo);
    }

    public final boolean checkCastTypesCompatibility(Scope scope, TypeBinding typeBinding, TypeBinding typeBinding2, Expression expression) {
        boolean z;
        if (typeBinding == null || typeBinding2 == null) {
            return true;
        }
        boolean z2 = scope.compilerOptions().sourceLevel >= ClassFileConstants.JDK1_5;
        boolean z3 = scope.compilerOptions().sourceLevel >= ClassFileConstants.JDK1_7;
        if (typeBinding.isBaseType()) {
            if (typeBinding2.isBaseType()) {
                if (TypeBinding.equalsEquals(typeBinding2, typeBinding)) {
                    if (expression != null) {
                        this.constant = expression.constant;
                    }
                    tagAsUnnecessaryCast(scope, typeBinding);
                    return true;
                }
                if (!typeBinding2.isCompatibleWith(typeBinding)) {
                    boolean zIsNarrowing = BaseTypeBinding.isNarrowing(typeBinding.f626id, typeBinding2.f626id);
                    z = zIsNarrowing ? zIsNarrowing : false;
                }
                if (expression != null) {
                    expression.implicitConversion = (typeBinding.f626id << 4) + typeBinding2.f626id;
                    if (expression.constant != Constant.NotAConstant) {
                        this.constant = expression.constant.castTo(expression.implicitConversion);
                    }
                }
                if (!z) {
                    tagAsUnnecessaryCast(scope, typeBinding);
                }
                return true;
            }
            if (z3 && typeBinding.isPrimitiveType() && (typeBinding2 instanceof ReferenceBinding) && !typeBinding2.isBoxedPrimitiveType() && checkCastTypesCompatibility(scope, scope.boxing(typeBinding), typeBinding2, expression)) {
                return true;
            }
            if (z2 && scope.environment().computeBoxingType(typeBinding2).isCompatibleWith(typeBinding)) {
                tagAsUnnecessaryCast(scope, typeBinding);
                return true;
            }
            return false;
        }
        if (z2 && typeBinding2.isBaseType() && scope.environment().computeBoxingType(typeBinding2).isCompatibleWith(typeBinding)) {
            tagAsUnnecessaryCast(scope, typeBinding);
            return true;
        }
        if (typeBinding.isIntersectionType18()) {
            for (ReferenceBinding referenceBinding : typeBinding.getIntersectingTypes()) {
                if (!checkCastTypesCompatibility(scope, referenceBinding, typeBinding2, expression)) {
                    return false;
                }
            }
            return true;
        }
        int iKind = typeBinding2.kind();
        if (iKind == 68) {
            if (TypeBinding.equalsEquals(typeBinding, typeBinding2)) {
                tagAsUnnecessaryCast(scope, typeBinding);
                return true;
            }
            int iKind2 = typeBinding.kind();
            if (iKind2 == 68) {
                TypeBinding typeBindingElementsType = ((ArrayBinding) typeBinding).elementsType();
                TypeBinding typeBindingElementsType2 = ((ArrayBinding) typeBinding2).elementsType();
                if (typeBindingElementsType2.isBaseType() || typeBindingElementsType.isBaseType()) {
                    if (!TypeBinding.equalsEquals(typeBindingElementsType, typeBindingElementsType2)) {
                        return false;
                    }
                    tagAsNeedCheckCast();
                    return true;
                }
                return checkCastTypesCompatibility(scope, typeBindingElementsType, typeBindingElementsType2, expression);
            }
            if (iKind2 == 4100) {
                if (typeBinding2.findSuperTypeOriginatingFrom(typeBinding) == null) {
                    checkUnsafeCast(scope, typeBinding, typeBinding2, null, true);
                }
                return checkCastTypesCompatibility(scope, ((TypeVariableBinding) typeBinding).upperBound(), typeBinding2, expression);
            }
            int i = typeBinding.f626id;
            if (i == 1) {
                tagAsUnnecessaryCast(scope, typeBinding);
                return true;
            }
            if (i != 36 && i != 37) {
                return false;
            }
            tagAsNeedCheckCast();
            return true;
        }
        if (iKind == 132) {
            if (typeBinding2 != TypeBinding.NULL) {
                return false;
            }
            tagAsUnnecessaryCast(scope, typeBinding);
            return true;
        }
        if (iKind != 516) {
            if (iKind == 4100) {
                TypeBinding typeBindingFindSuperTypeOriginatingFrom = typeBinding2.findSuperTypeOriginatingFrom(typeBinding);
                if (typeBindingFindSuperTypeOriginatingFrom != null) {
                    return checkUnsafeCast(scope, typeBinding, typeBinding2, typeBindingFindSuperTypeOriginatingFrom, false);
                }
                return checkCastTypesCompatibility(scope, typeBinding, ((TypeVariableBinding) typeBinding2).upperBound(), expression);
            }
            if (iKind != 8196) {
                if (iKind == 32772) {
                    for (ReferenceBinding referenceBinding2 : typeBinding2.getIntersectingTypes()) {
                        if (checkCastTypesCompatibility(scope, typeBinding, referenceBinding2, expression)) {
                            return true;
                        }
                    }
                    return false;
                }
                if (typeBinding2.isInterface()) {
                    int iKind3 = typeBinding.kind();
                    if (iKind3 == 68) {
                        int i2 = typeBinding2.f626id;
                        if (i2 != 36 && i2 != 37) {
                            return false;
                        }
                        tagAsNeedCheckCast();
                        return true;
                    }
                    if (iKind3 == 4100) {
                        if (typeBinding2.findSuperTypeOriginatingFrom(typeBinding) == null) {
                            checkUnsafeCast(scope, typeBinding, typeBinding2, null, true);
                        }
                        return checkCastTypesCompatibility(scope, ((TypeVariableBinding) typeBinding).upperBound(), typeBinding2, expression);
                    }
                    if (typeBinding.isInterface()) {
                        ReferenceBinding referenceBinding3 = (ReferenceBinding) typeBinding2;
                        TypeBinding typeBindingFindSuperTypeOriginatingFrom2 = referenceBinding3.findSuperTypeOriginatingFrom(typeBinding);
                        if (typeBindingFindSuperTypeOriginatingFrom2 != null) {
                            return checkUnsafeCast(scope, typeBinding, referenceBinding3, typeBindingFindSuperTypeOriginatingFrom2, false);
                        }
                        tagAsNeedCheckCast();
                        TypeBinding typeBindingFindSuperTypeOriginatingFrom3 = typeBinding.findSuperTypeOriginatingFrom(referenceBinding3);
                        if (typeBindingFindSuperTypeOriginatingFrom3 != null) {
                            return checkUnsafeCast(scope, typeBinding, referenceBinding3, typeBindingFindSuperTypeOriginatingFrom3, true);
                        }
                        if (z2) {
                            checkUnsafeCast(scope, typeBinding, typeBinding2, null, true);
                            if (scope.compilerOptions().complianceLevel < ClassFileConstants.JDK1_7) {
                                if (referenceBinding3.hasIncompatibleSuperType((ReferenceBinding) typeBinding)) {
                                    return false;
                                }
                            } else if (!typeBinding.isRawType() && referenceBinding3.hasIncompatibleSuperType((ReferenceBinding) typeBinding)) {
                                return false;
                            }
                        } else {
                            MethodBinding[] allOriginalInheritedMethods = getAllOriginalInheritedMethods((ReferenceBinding) typeBinding);
                            MethodBinding[] allOriginalInheritedMethods2 = getAllOriginalInheritedMethods(referenceBinding3);
                            int length = allOriginalInheritedMethods2.length;
                            int length2 = allOriginalInheritedMethods.length;
                            for (int i3 = 0; i3 < length2; i3++) {
                                for (int i4 = 0; i4 < length; i4++) {
                                    if (TypeBinding.notEquals(allOriginalInheritedMethods[i3].returnType, allOriginalInheritedMethods2[i4].returnType) && CharOperation.equals(allOriginalInheritedMethods[i3].selector, allOriginalInheritedMethods2[i4].selector) && allOriginalInheritedMethods[i3].areParametersEqual(allOriginalInheritedMethods2[i4])) {
                                        return false;
                                    }
                                }
                            }
                        }
                        return true;
                    }
                    if (typeBinding.f626id == 1) {
                        tagAsUnnecessaryCast(scope, typeBinding);
                        return true;
                    }
                    tagAsNeedCheckCast();
                    TypeBinding typeBindingFindSuperTypeOriginatingFrom4 = typeBinding.findSuperTypeOriginatingFrom(typeBinding2);
                    if (typeBindingFindSuperTypeOriginatingFrom4 != null) {
                        return checkUnsafeCast(scope, typeBinding, typeBinding2, typeBindingFindSuperTypeOriginatingFrom4, true);
                    }
                    ReferenceBinding referenceBinding4 = (ReferenceBinding) typeBinding;
                    if (referenceBinding4.isFinal()) {
                        return false;
                    }
                    if (z2) {
                        checkUnsafeCast(scope, typeBinding, typeBinding2, null, true);
                        if (scope.compilerOptions().complianceLevel < ClassFileConstants.JDK1_7) {
                            if (referenceBinding4.hasIncompatibleSuperType((ReferenceBinding) typeBinding2)) {
                                return false;
                            }
                        } else if (!typeBinding.isRawType() && referenceBinding4.hasIncompatibleSuperType((ReferenceBinding) typeBinding2)) {
                            return false;
                        }
                    }
                    return true;
                }
                int iKind4 = typeBinding.kind();
                if (iKind4 == 68) {
                    if (typeBinding2.f626id != 1) {
                        return false;
                    }
                    if (z2) {
                        checkUnsafeCast(scope, typeBinding, typeBinding2, typeBinding2, true);
                    }
                    tagAsNeedCheckCast();
                    return true;
                }
                if (iKind4 == 4100) {
                    if (typeBinding2.findSuperTypeOriginatingFrom(typeBinding) == null) {
                        checkUnsafeCast(scope, typeBinding, typeBinding2, null, true);
                    }
                    return checkCastTypesCompatibility(scope, ((TypeVariableBinding) typeBinding).upperBound(), typeBinding2, expression);
                }
                if (typeBinding.isInterface()) {
                    ReferenceBinding referenceBinding5 = (ReferenceBinding) typeBinding2;
                    TypeBinding typeBindingFindSuperTypeOriginatingFrom5 = referenceBinding5.findSuperTypeOriginatingFrom(typeBinding);
                    if (typeBindingFindSuperTypeOriginatingFrom5 != null) {
                        return checkUnsafeCast(scope, typeBinding, typeBinding2, typeBindingFindSuperTypeOriginatingFrom5, false);
                    }
                    if (referenceBinding5.isFinal()) {
                        return false;
                    }
                    tagAsNeedCheckCast();
                    TypeBinding typeBindingFindSuperTypeOriginatingFrom6 = typeBinding.findSuperTypeOriginatingFrom(typeBinding2);
                    if (typeBindingFindSuperTypeOriginatingFrom6 != null) {
                        return checkUnsafeCast(scope, typeBinding, typeBinding2, typeBindingFindSuperTypeOriginatingFrom6, true);
                    }
                    if (z2) {
                        checkUnsafeCast(scope, typeBinding, typeBinding2, null, true);
                        if (scope.compilerOptions().complianceLevel < ClassFileConstants.JDK1_7) {
                            if (referenceBinding5.hasIncompatibleSuperType((ReferenceBinding) typeBinding)) {
                                return false;
                            }
                        } else if (!typeBinding.isRawType() && referenceBinding5.hasIncompatibleSuperType((ReferenceBinding) typeBinding)) {
                            return false;
                        }
                    }
                    return true;
                }
                TypeBinding typeBindingFindSuperTypeOriginatingFrom7 = typeBinding2.findSuperTypeOriginatingFrom(typeBinding);
                if (typeBindingFindSuperTypeOriginatingFrom7 != null) {
                    if (expression != null && typeBinding.f626id == 11) {
                        this.constant = expression.constant;
                    }
                    return checkUnsafeCast(scope, typeBinding, typeBinding2, typeBindingFindSuperTypeOriginatingFrom7, false);
                }
                TypeBinding typeBindingFindSuperTypeOriginatingFrom8 = typeBinding.findSuperTypeOriginatingFrom(typeBinding2);
                if (typeBindingFindSuperTypeOriginatingFrom8 == null) {
                    return false;
                }
                tagAsNeedCheckCast();
                return checkUnsafeCast(scope, typeBinding, typeBinding2, typeBindingFindSuperTypeOriginatingFrom8, true);
            }
        }
        TypeBinding typeBindingFindSuperTypeOriginatingFrom9 = typeBinding2.findSuperTypeOriginatingFrom(typeBinding);
        if (typeBindingFindSuperTypeOriginatingFrom9 != null) {
            return checkUnsafeCast(scope, typeBinding, typeBinding2, typeBindingFindSuperTypeOriginatingFrom9, false);
        }
        TypeBinding javaLangObject = ((WildcardBinding) typeBinding2).bound;
        if (javaLangObject == null) {
            javaLangObject = scope.getJavaLangObject();
        }
        return checkCastTypesCompatibility(scope, typeBinding, javaLangObject, expression);
    }

    /* JADX WARN: Code duplicated, block: B:10:0x001f  */
    public boolean checkNPE(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo, int i) {
        boolean z;
        TypeBinding typeBinding = this.resolvedType;
        if (typeBinding == null) {
            z = false;
        } else {
            if ((typeBinding.tagBits & 72057594037927936L) != 0) {
                return true;
            }
            if ((this.resolvedType.tagBits & 36028797018963968L) != 0) {
                z = true;
            } else {
                z = false;
            }
        }
        LocalVariableBinding localVariableBinding = localVariableBinding();
        if (localVariableBinding == null || (localVariableBinding.type.tagBits & 2) != 0) {
            if (!z) {
                return false;
            }
            blockScope.problemReporter().dereferencingNullableExpression(this);
            return true;
        }
        if ((this.bits & 131072) == 0) {
            flowContext.recordUsingNullReference(blockScope, localVariableBinding, this, 3, flowInfo);
            if (!flowInfo.isDefinitelyNonNull(localVariableBinding)) {
                flowContext.recordAbruptExit();
            }
        }
        flowInfo.markAsComparedEqualToNonNull(localVariableBinding);
        flowContext.markFinallyNullStatus(localVariableBinding, 4);
        return true;
    }

    public boolean checkNPE(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        return checkNPE(blockScope, flowContext, flowInfo, 0);
    }

    protected void checkNPEbyUnboxing(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        int iNullStatus;
        if ((this.implicitConversion & 1024) == 0 || (this.bits & 131072) != 0 || (iNullStatus = nullStatus(flowInfo, flowContext)) == 4) {
            return;
        }
        flowContext.recordUnboxing(blockScope, this, iNullStatus, flowInfo);
    }

    public boolean checkUnsafeCast(Scope scope, TypeBinding typeBinding, TypeBinding typeBinding2, TypeBinding typeBinding3, boolean z) {
        if (TypeBinding.equalsEquals(typeBinding3, typeBinding)) {
            if (!z) {
                tagAsUnnecessaryCast(scope, typeBinding);
            }
            return true;
        }
        if (typeBinding3 != null && (!typeBinding.isReifiable() || !typeBinding2.isReifiable())) {
            if (z) {
                if (typeBinding3.isProvablyDistinct(typeBinding2)) {
                    return false;
                }
            } else if (typeBinding.isProvablyDistinct(typeBinding3)) {
                return false;
            }
        }
        if (!z) {
            tagAsUnnecessaryCast(scope, typeBinding);
        }
        return true;
    }

    public void computeConversion(Scope scope, TypeBinding typeBinding, TypeBinding typeBinding2) {
        if (typeBinding == null || typeBinding2 == null || this.implicitConversion != 0) {
        }
        if (typeBinding != TypeBinding.NULL && typeBinding.isBaseType()) {
            if (!typeBinding2.isBaseType()) {
                TypeBinding typeBindingComputeBoxingType = scope.environment().computeBoxingType(typeBinding2);
                this.implicitConversion = 1024;
                scope.problemReporter().autoboxing(this, typeBinding2, typeBinding);
                typeBinding2 = typeBindingComputeBoxingType;
            }
        } else {
            if (typeBinding2 != TypeBinding.NULL && typeBinding2.isBaseType()) {
                TypeBinding typeBindingComputeBoxingType2 = scope.environment().computeBoxingType(typeBinding);
                if (TypeBinding.equalsEquals(typeBindingComputeBoxingType2, typeBinding)) {
                    typeBindingComputeBoxingType2 = typeBinding2;
                }
                if (typeBindingComputeBoxingType2.f626id >= 128) {
                    typeBindingComputeBoxingType2 = typeBinding2;
                }
                this.implicitConversion = ((typeBindingComputeBoxingType2.f626id << 4) + typeBinding2.f626id) | 512;
                scope.problemReporter().autoboxing(this, typeBinding2, scope.environment().computeBoxingType(typeBindingComputeBoxingType2));
                return;
            }
            if (this.constant != Constant.NotAConstant && this.constant.typeID() != 11) {
                this.implicitConversion = 512;
                return;
            }
        }
        int i = typeBinding2.f626id;
        if (i >= 128) {
            i = typeBinding2.erasure().f626id != 11 ? 1 : 11;
        } else if (typeBinding.isPrimitiveType() && (typeBinding2 instanceof ReferenceBinding) && !typeBinding2.isBoxedPrimitiveType()) {
            i = 1;
        }
        int i2 = typeBinding.f626id;
        switch (i2) {
            case 2:
            case 3:
            case 4:
                if (i == 1) {
                    this.implicitConversion |= (i2 << 4) + i;
                } else {
                    this.implicitConversion = (i + 160) | this.implicitConversion;
                }
                break;
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                this.implicitConversion |= (i2 << 4) + i;
                break;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public void generateCode(BlockScope blockScope, CodeStream codeStream) {
        if ((this.bits & Integer.MIN_VALUE) == 0) {
            return;
        }
        generateCode(blockScope, codeStream, false);
    }

    public void generateCode(BlockScope blockScope, CodeStream codeStream, boolean z) {
        if (this.constant != Constant.NotAConstant) {
            int i = codeStream.position;
            codeStream.generateConstant(this.constant, this.implicitConversion);
            codeStream.recordPositionsFrom(i, this.sourceStart);
            return;
        }
        throw new ShouldNotImplement(Messages.ast_missingCode);
    }

    public void generateOptimizedBoolean(BlockScope blockScope, CodeStream codeStream, BranchLabel branchLabel, BranchLabel branchLabel2, boolean z) {
        Constant constantOptimizedBooleanConstant = optimizedBooleanConstant();
        generateCode(blockScope, codeStream, z && constantOptimizedBooleanConstant == Constant.NotAConstant);
        if (constantOptimizedBooleanConstant != Constant.NotAConstant && constantOptimizedBooleanConstant.typeID() == 5) {
            int i = codeStream.position;
            if (constantOptimizedBooleanConstant.booleanValue()) {
                if (z && branchLabel2 == null && branchLabel != null) {
                    codeStream.goto_(branchLabel);
                }
            } else if (z && branchLabel2 != null && branchLabel == null) {
                codeStream.goto_(branchLabel2);
            }
            codeStream.recordPositionsFrom(i, this.sourceStart);
            return;
        }
        int i2 = codeStream.position;
        if (z) {
            if (branchLabel2 == null) {
                if (branchLabel != null) {
                    codeStream.ifne(branchLabel);
                }
            } else if (branchLabel == null) {
                codeStream.ifeq(branchLabel2);
            }
        }
        codeStream.recordPositionsFrom(i2, this.sourceEnd);
    }

    public void generateOptimizedStringConcatenation(BlockScope blockScope, CodeStream codeStream, int i) {
        if (i == 11 && this.constant != Constant.NotAConstant && this.constant.stringValue().length() == 0) {
            return;
        }
        generateCode(blockScope, codeStream, true);
        codeStream.invokeStringConcatenationAppendForType(i);
    }

    public void generateOptimizedStringConcatenationCreation(BlockScope blockScope, CodeStream codeStream, int i) {
        codeStream.newStringContatenation();
        codeStream.dup();
        if (i == 0 || i == 1) {
            codeStream.invokeStringConcatenationDefaultConstructor();
            generateCode(blockScope, codeStream, true);
            codeStream.invokeStringConcatenationAppendForType(1);
            return;
        }
        if (i == 11 || i == 12) {
            if (this.constant != Constant.NotAConstant) {
                String strStringValue = this.constant.stringValue();
                if (strStringValue.length() == 0) {
                    codeStream.invokeStringConcatenationDefaultConstructor();
                    return;
                }
                codeStream.ldc(strStringValue);
            } else {
                generateCode(blockScope, codeStream, true);
                codeStream.invokeStringValueOf(1);
            }
        } else {
            generateCode(blockScope, codeStream, true);
            codeStream.invokeStringValueOf(i);
        }
        codeStream.invokeStringConcatenationStringConstructor();
    }

    private MethodBinding[] getAllOriginalInheritedMethods(ReferenceBinding referenceBinding) {
        ArrayList<MethodBinding> arrayList = new ArrayList<>();
        getAllInheritedMethods0(referenceBinding, arrayList);
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            arrayList.set(i, arrayList.get(i).original());
        }
        return (MethodBinding[]) arrayList.toArray(new MethodBinding[arrayList.size()]);
    }

    private void getAllInheritedMethods0(ReferenceBinding referenceBinding, ArrayList<MethodBinding> arrayList) {
        if (referenceBinding.isInterface()) {
            for (MethodBinding methodBinding : referenceBinding.methods()) {
                arrayList.add(methodBinding);
            }
            for (ReferenceBinding referenceBinding2 : referenceBinding.superInterfaces()) {
                getAllInheritedMethods0(referenceBinding2, arrayList);
            }
        }
    }

    public static Binding getDirectBinding(Expression expression) {
        if ((expression.bits & 536870912) != 0) {
            return null;
        }
        if (expression instanceof SingleNameReference) {
            return ((SingleNameReference) expression).binding;
        }
        if (expression instanceof FieldReference) {
            FieldReference fieldReference = (FieldReference) expression;
            if (fieldReference.receiver.isThis() && !(fieldReference.receiver instanceof QualifiedThisReference)) {
                return fieldReference.binding;
            }
        } else if (expression instanceof Assignment) {
            Assignment assignment = (Assignment) expression;
            if ((assignment.lhs.bits & 8192) != 0) {
                return getDirectBinding(assignment.lhs);
            }
            if (expression instanceof PrefixExpression) {
                return getDirectBinding(assignment.lhs);
            }
        } else if (expression instanceof QualifiedNameReference) {
            QualifiedNameReference qualifiedNameReference = (QualifiedNameReference) expression;
            if (qualifiedNameReference.indexOfFirstFieldBinding != 1 && qualifiedNameReference.otherBindings == null) {
                return qualifiedNameReference.binding;
            }
        } else if (expression.isThis()) {
            return expression.resolvedType;
        }
        return null;
    }

    public boolean isConstantValueOfTypeAssignableToType(TypeBinding typeBinding, TypeBinding typeBinding2) {
        if (this.constant == Constant.NotAConstant) {
            return false;
        }
        if (TypeBinding.equalsEquals(typeBinding, typeBinding2)) {
            return true;
        }
        if (BaseTypeBinding.isWidening(10, typeBinding.f626id) && BaseTypeBinding.isNarrowing(typeBinding2.f626id, 10)) {
            return isConstantValueRepresentable(this.constant, typeBinding.f626id, typeBinding2.f626id);
        }
        return false;
    }

    public void markAsNonNull() {
        this.bits |= 131072;
    }

    public Constant optimizedBooleanConstant() {
        return this.constant;
    }

    public TypeBinding postConversionType(Scope scope) {
        TypeBinding typeBinding = this.resolvedType;
        switch ((this.implicitConversion & 255) >> 4) {
            case 2:
                typeBinding = TypeBinding.CHAR;
                break;
            case 3:
                typeBinding = TypeBinding.BYTE;
                break;
            case 4:
                typeBinding = TypeBinding.SHORT;
                break;
            case 5:
                typeBinding = TypeBinding.BOOLEAN;
                break;
            case 7:
                typeBinding = TypeBinding.LONG;
                break;
            case 8:
                typeBinding = TypeBinding.DOUBLE;
                break;
            case 9:
                typeBinding = TypeBinding.FLOAT;
                break;
            case 10:
                typeBinding = TypeBinding.INT;
                break;
        }
        return (this.implicitConversion & 512) != 0 ? scope.environment().computeBoxingType(typeBinding) : typeBinding;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public StringBuffer print(int i, StringBuffer stringBuffer) {
        printIndent(i, stringBuffer);
        return printExpression(i, stringBuffer);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public StringBuffer printStatement(int i, StringBuffer stringBuffer) {
        return print(i, stringBuffer).append(";");
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public void resolve(BlockScope blockScope) {
        resolveType(blockScope);
    }

    public TypeBinding resolveTypeExpecting(BlockScope blockScope, TypeBinding typeBinding) {
        setExpectedType(typeBinding);
        TypeBinding typeBindingResolveType = resolveType(blockScope);
        if (typeBindingResolveType == null) {
            return null;
        }
        if (!TypeBinding.equalsEquals(typeBindingResolveType, typeBinding) && !typeBindingResolveType.isCompatibleWith(typeBinding)) {
            if (blockScope.isBoxingCompatibleWith(typeBindingResolveType, typeBinding)) {
                computeConversion(blockScope, typeBinding, typeBindingResolveType);
            } else {
                blockScope.problemReporter().typeMismatchError(typeBindingResolveType, typeBinding, this, (ASTNode) null);
                return null;
            }
        }
        return typeBindingResolveType;
    }

    public boolean forcedToBeRaw(ReferenceContext referenceContext) {
        if (this instanceof NameReference) {
            Binding binding = ((NameReference) this).binding;
            if (binding.isParameter() && (((LocalVariableBinding) binding).tagBits & 512) != 0) {
                return true;
            }
            if (!(binding instanceof FieldBinding)) {
                return false;
            }
            FieldBinding fieldBinding = (FieldBinding) binding;
            if (!fieldBinding.type.isRawType()) {
                return false;
            }
            if (referenceContext instanceof AbstractMethodDeclaration) {
                return TypeBinding.notEquals(fieldBinding.declaringClass, ((AbstractMethodDeclaration) referenceContext).binding.declaringClass);
            }
            return (referenceContext instanceof TypeDeclaration) && TypeBinding.notEquals(fieldBinding.declaringClass, ((TypeDeclaration) referenceContext).binding);
        }
        if (this instanceof MessageSend) {
            return !CharOperation.equals(((MessageSend) this).binding.declaringClass.getFileName(), referenceContext.compilationResult().getFileName());
        }
        if (this instanceof FieldReference) {
            FieldBinding fieldBinding2 = ((FieldReference) this).binding;
            if (!CharOperation.equals(fieldBinding2.declaringClass.getFileName(), referenceContext.compilationResult().getFileName())) {
                return true;
            }
            if (!fieldBinding2.type.isRawType()) {
                return false;
            }
            if (referenceContext instanceof AbstractMethodDeclaration) {
                return TypeBinding.notEquals(fieldBinding2.declaringClass, ((AbstractMethodDeclaration) referenceContext).binding.declaringClass);
            }
            return (referenceContext instanceof TypeDeclaration) && TypeBinding.notEquals(fieldBinding2.declaringClass, ((TypeDeclaration) referenceContext).binding);
        }
        if (!(this instanceof ConditionalExpression)) {
            return false;
        }
        ConditionalExpression conditionalExpression = (ConditionalExpression) this;
        return conditionalExpression.valueIfTrue.forcedToBeRaw(referenceContext) || conditionalExpression.valueIfFalse.forcedToBeRaw(referenceContext);
    }

    public Object reusableJSRTarget() {
        if (this.constant == Constant.NotAConstant || (this.implicitConversion & 512) != 0) {
            return null;
        }
        return this.constant;
    }

    public boolean isCompatibleWith(TypeBinding typeBinding, Scope scope) {
        TypeBinding typeBinding2 = this.resolvedType;
        return typeBinding2 != null && typeBinding2.isCompatibleWith(typeBinding, scope);
    }

    public boolean isBoxingCompatibleWith(TypeBinding typeBinding, Scope scope) {
        TypeBinding typeBinding2 = this.resolvedType;
        return typeBinding2 != null && isBoxingCompatible(typeBinding2, typeBinding, this, scope);
    }

    public boolean sIsMoreSpecific(TypeBinding typeBinding, TypeBinding typeBinding2, Scope scope) {
        return typeBinding.isCompatibleWith(typeBinding2, scope);
    }

    public Expression[] getPolyExpressions() {
        return isPolyExpression() ? new Expression[]{this} : NO_EXPRESSIONS;
    }

    public boolean isPotentiallyCompatibleWith(TypeBinding typeBinding, Scope scope) {
        return isCompatibleWith(typeBinding, scope);
    }
}
