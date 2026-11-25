package org.eclipse.jdt.internal.compiler.lookup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.eclipse.jdt.internal.compiler.ast.FunctionalExpression;
import org.eclipse.jdt.internal.compiler.ast.LambdaExpression;
import org.eclipse.jdt.internal.compiler.ast.ReferenceExpression;

/* JADX INFO: loaded from: classes14.dex */
public class ConstraintExceptionFormula extends ConstraintFormula {
    FunctionalExpression left;

    @Override // org.eclipse.jdt.internal.compiler.lookup.ConstraintFormula
    public /* bridge */ /* synthetic */ boolean applySubstitution(BoundSet boundSet, InferenceVariable[] inferenceVariableArr) {
        return super.applySubstitution(boundSet, inferenceVariableArr);
    }

    public ConstraintExceptionFormula(FunctionalExpression functionalExpression, TypeBinding typeBinding) {
        this.left = functionalExpression;
        this.right = typeBinding;
        this.relation = 7;
    }

    /* JADX WARN: Code duplicated, block: B:100:0x0127 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:102:0x0102 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:65:0x00cc  */
    /* JADX WARN: Code duplicated, block: B:67:0x00cf  */
    /* JADX WARN: Code duplicated, block: B:72:0x00e5 A[LOOP:5: B:69:0x00d8->B:72:0x00e5, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:73:0x00f5  */
    /* JADX WARN: Code duplicated, block: B:76:0x00fe  */
    /* JADX WARN: Code duplicated, block: B:82:0x0106 A[LOOP:4: B:80:0x0103->B:82:0x0106, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:83:0x0115  */
    @Override // org.eclipse.jdt.internal.compiler.lookup.ConstraintFormula
    public Object reduce(InferenceContext18 inferenceContext18) {
        TypeBinding[] typeBindingArr;
        TypeBinding[] typeBindingArr2;
        int length;
        ArrayList arrayList;
        int i;
        int i2;
        int i3;
        Scope scope = inferenceContext18.scope;
        if (!this.right.isFunctionalInterface(scope)) {
            return FALSE;
        }
        MethodBinding singleAbstractMethod = this.right.getSingleAbstractMethod(scope, true);
        if (singleAbstractMethod == null) {
            return FALSE;
        }
        FunctionalExpression functionalExpression = this.left;
        if (functionalExpression instanceof LambdaExpression) {
            if (((LambdaExpression) functionalExpression).argumentsTypeElided()) {
                int length2 = singleAbstractMethod.parameters.length;
                for (int i4 = 0; i4 < length2; i4++) {
                    if (!singleAbstractMethod.parameters[i4].isProperType(true)) {
                        return FALSE;
                    }
                }
            }
            if (singleAbstractMethod.returnType != TypeBinding.VOID && !singleAbstractMethod.returnType.isProperType(true)) {
                return FALSE;
            }
        } else if (!((ReferenceExpression) functionalExpression).isExactMethodReference()) {
            int length3 = singleAbstractMethod.parameters.length;
            for (int i5 = 0; i5 < length3; i5++) {
                if (!singleAbstractMethod.parameters[i5].isProperType(true)) {
                    return FALSE;
                }
            }
            if (singleAbstractMethod.returnType != TypeBinding.VOID && !singleAbstractMethod.returnType.isProperType(true)) {
                return FALSE;
            }
        }
        ReferenceBinding[] referenceBindingArr = singleAbstractMethod.thrownExceptions;
        InferenceVariable[] inferenceVariableArr = new InferenceVariable[referenceBindingArr.length];
        int i6 = 0;
        for (int i7 = 0; i7 < referenceBindingArr.length; i7++) {
            if (!referenceBindingArr[i7].isProperType(true)) {
                inferenceVariableArr[i6] = (InferenceVariable) referenceBindingArr[i7];
                i6++;
            }
        }
        if (i6 == 0) {
            return TRUE;
        }
        FunctionalExpression functionalExpression2 = this.left;
        if (functionalExpression2 instanceof LambdaExpression) {
            LambdaExpression lambdaExpressionResolveExpressionExpecting = ((LambdaExpression) functionalExpression2).resolveExpressionExpecting(this.right, inferenceContext18.scope, inferenceContext18);
            if (lambdaExpressionResolveExpressionExpecting == null) {
                return TRUE;
            }
            Set<TypeBinding> thrownExceptions = lambdaExpressionResolveExpressionExpecting.getThrownExceptions();
            typeBindingArr2 = (TypeBinding[]) thrownExceptions.toArray(new TypeBinding[thrownExceptions.size()]);
        } else {
            ReferenceExpression referenceExpressionResolveExpressionExpecting = ((ReferenceExpression) functionalExpression2).resolveExpressionExpecting(this.right, scope, inferenceContext18);
            MethodBinding methodBinding = referenceExpressionResolveExpressionExpecting != null ? referenceExpressionResolveExpressionExpecting.binding : null;
            if (methodBinding != null) {
                typeBindingArr2 = methodBinding.thrownExceptions;
            } else {
                typeBindingArr = null;
            }
            if (typeBindingArr == null) {
                return TRUE;
            }
            length = typeBindingArr.length;
            arrayList = new ArrayList();
            for (i = 0; i < length; i++) {
                if (typeBindingArr[i].isUncheckedException(false)) {
                    i2 = 0;
                    while (true) {
                        if (i2 >= referenceBindingArr.length) {
                            for (i3 = 0; i3 < i6; i3++) {
                                arrayList.add(ConstraintTypeFormula.create(typeBindingArr[i], inferenceVariableArr[i3], 2));
                            }
                            break;
                        }
                        if (!referenceBindingArr[i2].isProperType(true) && typeBindingArr[i].isCompatibleWith(referenceBindingArr[i2])) {
                            break;
                        }
                        i2++;
                    }
                }
            }
            for (int i8 = 0; i8 < i6; i8++) {
                inferenceContext18.currentBounds.inThrows.add(inferenceVariableArr[i8].prototype());
            }
            return arrayList.toArray(new ConstraintFormula[arrayList.size()]);
        }
        typeBindingArr = typeBindingArr2;
        if (typeBindingArr == null) {
            return TRUE;
        }
        length = typeBindingArr.length;
        arrayList = new ArrayList();
        while (i < length) {
            if (typeBindingArr[i].isUncheckedException(false)) {
                i2 = 0;
                while (true) {
                    if (i2 >= referenceBindingArr.length) {
                        while (i3 < i6) {
                            arrayList.add(ConstraintTypeFormula.create(typeBindingArr[i], inferenceVariableArr[i3], 2));
                        }
                        break;
                        break;
                    }
                    if (!referenceBindingArr[i2].isProperType(true)) {
                    }
                    i2++;
                }
            }
        }
        while (i8 < i6) {
            inferenceContext18.currentBounds.inThrows.add(inferenceVariableArr[i8].prototype());
        }
        return arrayList.toArray(new ConstraintFormula[arrayList.size()]);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ConstraintFormula
    Collection<InferenceVariable> inputVariables(InferenceContext18 inferenceContext18) {
        FunctionalExpression functionalExpression = this.left;
        int i = 0;
        if (functionalExpression instanceof LambdaExpression) {
            if (this.right instanceof InferenceVariable) {
                return Collections.singletonList((InferenceVariable) this.right);
            }
            if (this.right.isFunctionalInterface(inferenceContext18.scope)) {
                LambdaExpression lambdaExpression = (LambdaExpression) this.left;
                MethodBinding singleAbstractMethod = this.right.getSingleAbstractMethod(inferenceContext18.scope, true);
                HashSet hashSet = new HashSet();
                if (lambdaExpression.argumentsTypeElided()) {
                    int length = singleAbstractMethod.parameters.length;
                    while (i < length) {
                        singleAbstractMethod.parameters[i].collectInferenceVariables(hashSet);
                        i++;
                    }
                }
                if (singleAbstractMethod.returnType != TypeBinding.VOID) {
                    singleAbstractMethod.returnType.collectInferenceVariables(hashSet);
                }
                return hashSet;
            }
        } else if (functionalExpression instanceof ReferenceExpression) {
            if (this.right instanceof InferenceVariable) {
                return Collections.singletonList((InferenceVariable) this.right);
            }
            if (this.right.isFunctionalInterface(inferenceContext18.scope)) {
                MethodBinding singleAbstractMethod2 = this.right.getSingleAbstractMethod(inferenceContext18.scope, true);
                HashSet hashSet2 = new HashSet();
                int length2 = singleAbstractMethod2.parameters.length;
                while (i < length2) {
                    singleAbstractMethod2.parameters[i].collectInferenceVariables(hashSet2);
                    i++;
                }
                singleAbstractMethod2.returnType.collectInferenceVariables(hashSet2);
                return hashSet2;
            }
        }
        return EMPTY_VARIABLE_LIST;
    }

    public String toString() {
        StringBuffer stringBufferAppend = new StringBuffer().append((char) 10216);
        this.left.printExpression(4, stringBufferAppend);
        stringBufferAppend.append(" ⊆throws ");
        appendTypeName(stringBufferAppend, this.right);
        stringBufferAppend.append((char) 10217);
        return stringBufferAppend.toString();
    }
}
