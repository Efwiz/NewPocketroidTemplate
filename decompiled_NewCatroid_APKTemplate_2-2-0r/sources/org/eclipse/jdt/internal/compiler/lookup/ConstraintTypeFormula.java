package org.eclipse.jdt.internal.compiler.lookup;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jdt.internal.compiler.ast.Invocation;

/* JADX INFO: loaded from: classes14.dex */
class ConstraintTypeFormula extends ConstraintFormula {
    boolean isSoft;
    TypeBinding left;

    public static ConstraintTypeFormula create(TypeBinding typeBinding, TypeBinding typeBinding2, int i) {
        if (typeBinding == null || typeBinding2 == null) {
            return FALSE;
        }
        return new ConstraintTypeFormula(typeBinding, typeBinding2, i, false);
    }

    public static ConstraintTypeFormula create(TypeBinding typeBinding, TypeBinding typeBinding2, int i, boolean z) {
        if (typeBinding == null || typeBinding2 == null) {
            return FALSE;
        }
        return new ConstraintTypeFormula(typeBinding, typeBinding2, i, z);
    }

    private ConstraintTypeFormula(TypeBinding typeBinding, TypeBinding typeBinding2, int i, boolean z) {
        this.left = typeBinding;
        this.right = typeBinding2;
        this.relation = i;
        this.isSoft = z;
    }

    ConstraintTypeFormula() {
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ConstraintFormula
    public Object reduce(InferenceContext18 inferenceContext18) {
        int i = this.relation;
        if (i == 1) {
            if (this.left.isProperType(true) && this.right.isProperType(true)) {
                return (this.left.isCompatibleWith(this.right, inferenceContext18.scope) || this.left.isBoxingCompatibleWith(this.right, inferenceContext18.scope)) ? TRUE : FALSE;
            }
            if (this.left.isPrimitiveType()) {
                return create(inferenceContext18.environment.computeBoxingType(this.left), this.right, 1, this.isSoft);
            }
            if (this.right.isPrimitiveType()) {
                return create(this.left, inferenceContext18.environment.computeBoxingType(this.right), 4, this.isSoft);
            }
            int iKind = this.right.kind();
            if (iKind == 68 ? this.right.leafComponentType().kind() == 260 : iKind == 260) {
                TypeBinding typeBindingFindSuperTypeOriginatingFrom = this.left.findSuperTypeOriginatingFrom(this.right);
                if (typeBindingFindSuperTypeOriginatingFrom != null && typeBindingFindSuperTypeOriginatingFrom.leafComponentType().isRawType()) {
                    inferenceContext18.recordUncheckedConversion(this);
                    return TRUE;
                }
            }
            return create(this.left, this.right, 2, this.isSoft);
        }
        if (i == 2) {
            return reduceSubType(inferenceContext18.scope, this.left, this.right);
        }
        if (i == 3) {
            return reduceSubType(inferenceContext18.scope, this.right, this.left);
        }
        if (i == 4) {
            if (inferenceContext18.environment.globalOptions.isAnnotationBasedNullAnalysisEnabled && !checkIVFreeTVmatch(this.left, this.right)) {
                checkIVFreeTVmatch(this.right, this.left);
            }
            return reduceTypeEquality(inferenceContext18.object);
        }
        if (i == 5) {
            if (this.right.kind() != 516) {
                if (this.left.kind() != 516) {
                    return create(this.left, this.right, 4, this.isSoft);
                }
                return FALSE;
            }
            WildcardBinding wildcardBinding = (WildcardBinding) this.right;
            if (wildcardBinding.boundKind == 0) {
                return TRUE;
            }
            if (wildcardBinding.boundKind == 1) {
                if (this.left.kind() != 516) {
                    return create(this.left, wildcardBinding.bound, 2, this.isSoft);
                }
                WildcardBinding wildcardBinding2 = (WildcardBinding) this.left;
                int i2 = wildcardBinding2.boundKind;
                if (i2 == 0) {
                    return create(inferenceContext18.object, wildcardBinding.bound, 2, this.isSoft);
                }
                if (i2 == 1) {
                    return create(wildcardBinding2.bound, wildcardBinding.bound, 2, this.isSoft);
                }
                if (i2 == 2) {
                    return create(inferenceContext18.object, wildcardBinding.bound, 4, this.isSoft);
                }
                throw new IllegalArgumentException("Unexpected boundKind " + wildcardBinding2.boundKind);
            }
            if (this.left.kind() != 516) {
                return create(wildcardBinding.bound, this.left, 2, this.isSoft);
            }
            WildcardBinding wildcardBinding3 = (WildcardBinding) this.left;
            if (wildcardBinding3.boundKind == 2) {
                return create(wildcardBinding.bound, wildcardBinding3.bound, 2, this.isSoft);
            }
            return FALSE;
        }
        throw new IllegalStateException("Unexpected relation kind " + this.relation);
    }

    boolean checkIVFreeTVmatch(TypeBinding typeBinding, TypeBinding typeBinding2) {
        if (!(typeBinding instanceof InferenceVariable) || !typeBinding2.isTypeVariable() || (typeBinding2.tagBits & TagBits.AnnotationNullMASK) != 0) {
            return false;
        }
        ((InferenceVariable) typeBinding).nullHints = TagBits.AnnotationNullMASK;
        return true;
    }

    private Object reduceTypeEquality(TypeBinding typeBinding) {
        if (this.left.kind() == 516) {
            if (this.right.kind() == 516) {
                WildcardBinding wildcardBinding = (WildcardBinding) this.left;
                WildcardBinding wildcardBinding2 = (WildcardBinding) this.right;
                if (wildcardBinding.boundKind == 0 && wildcardBinding2.boundKind == 0) {
                    return TRUE;
                }
                if (wildcardBinding.boundKind == 0 && wildcardBinding2.boundKind == 1) {
                    return create(typeBinding, wildcardBinding2.bound, 4, this.isSoft);
                }
                if (wildcardBinding.boundKind == 1 && wildcardBinding2.boundKind == 0) {
                    return create(wildcardBinding.bound, typeBinding, 4, this.isSoft);
                }
                if ((wildcardBinding.boundKind == 1 && wildcardBinding2.boundKind == 1) || (wildcardBinding.boundKind == 2 && wildcardBinding2.boundKind == 2)) {
                    return create(wildcardBinding.bound, wildcardBinding2.bound, 4, this.isSoft);
                }
            }
        } else if (this.right.kind() != 516) {
            if (this.left.isProperType(true) && this.right.isProperType(true)) {
                if (TypeBinding.equalsEquals(this.left, this.right)) {
                    return TRUE;
                }
                return FALSE;
            }
            if (this.left.f626id == 12 || this.right.f626id == 12) {
                return FALSE;
            }
            if ((this.left instanceof InferenceVariable) && !this.right.isPrimitiveType()) {
                return new TypeBound((InferenceVariable) this.left, this.right, 4, this.isSoft);
            }
            if ((this.right instanceof InferenceVariable) && !this.left.isPrimitiveType()) {
                return new TypeBound((InferenceVariable) this.right, this.left, 4, this.isSoft);
            }
            if ((this.left.isClass() || this.left.isInterface()) && ((this.right.isClass() || this.right.isInterface()) && TypeBinding.equalsEquals(this.left.erasure(), this.right.erasure()))) {
                TypeBinding[] typeBindingArrTypeArguments = this.left.typeArguments();
                TypeBinding[] typeBindingArrTypeArguments2 = this.right.typeArguments();
                if (typeBindingArrTypeArguments == null || typeBindingArrTypeArguments2 == null) {
                    return typeBindingArrTypeArguments == typeBindingArrTypeArguments2 ? TRUE : FALSE;
                }
                if (typeBindingArrTypeArguments.length != typeBindingArrTypeArguments2.length) {
                    return FALSE;
                }
                int length = typeBindingArrTypeArguments.length;
                ConstraintFormula[] constraintFormulaArr = new ConstraintFormula[length];
                for (int i = 0; i < length; i++) {
                    constraintFormulaArr[i] = create(typeBindingArrTypeArguments[i], typeBindingArrTypeArguments2[i], 4, this.isSoft);
                }
                return constraintFormulaArr;
            }
            if (this.left.isArrayType() && this.right.isArrayType() && this.left.dimensions() == this.right.dimensions()) {
                return create(this.left.leafComponentType(), this.right.leafComponentType(), 4, this.isSoft);
            }
        }
        return FALSE;
    }

    private Object reduceSubType(Scope scope, TypeBinding typeBinding, TypeBinding typeBinding2) {
        ArrayBinding arrayBindingFindMostSpecificSuperArray;
        ReferenceBinding[] intersectingTypes;
        ReferenceBinding[] intersectingTypes2;
        if (typeBinding.isProperType(true) && typeBinding2.isProperType(true)) {
            if (typeBinding.isCompatibleWith(typeBinding2, scope)) {
                return TRUE;
            }
            return FALSE;
        }
        if (typeBinding.f626id == 12) {
            return TRUE;
        }
        if (typeBinding2.f626id == 12) {
            return FALSE;
        }
        if (typeBinding instanceof InferenceVariable) {
            return new TypeBound((InferenceVariable) typeBinding, typeBinding2, 2, this.isSoft);
        }
        if (typeBinding2 instanceof InferenceVariable) {
            return new TypeBound((InferenceVariable) typeBinding2, typeBinding, 3, this.isSoft);
        }
        int iKind = typeBinding2.kind();
        if (iKind != 4) {
            if (iKind == 68) {
                TypeBinding typeBindingElementsType = ((ArrayBinding) typeBinding2).elementsType();
                int iKind2 = typeBinding.kind();
                if (iKind2 == 68) {
                    arrayBindingFindMostSpecificSuperArray = (ArrayBinding) typeBinding;
                } else if (iKind2 == 4100) {
                    TypeVariableBinding typeVariableBinding = (TypeVariableBinding) typeBinding;
                    arrayBindingFindMostSpecificSuperArray = findMostSpecificSuperArray(typeVariableBinding.firstBound, typeVariableBinding.otherUpperBounds(), typeVariableBinding);
                } else if (iKind2 == 8196) {
                    WildcardBinding wildcardBinding = (WildcardBinding) typeBinding;
                    arrayBindingFindMostSpecificSuperArray = findMostSpecificSuperArray(wildcardBinding.bound, wildcardBinding.otherBounds, wildcardBinding);
                } else {
                    return FALSE;
                }
                if (arrayBindingFindMostSpecificSuperArray == null) {
                    return FALSE;
                }
                TypeBinding typeBindingElementsType2 = arrayBindingFindMostSpecificSuperArray.elementsType();
                if (typeBindingElementsType.isPrimitiveType() || typeBindingElementsType2.isPrimitiveType()) {
                    return TypeBinding.equalsEquals(typeBindingElementsType, typeBindingElementsType2) ? TRUE : FALSE;
                }
                return create(typeBindingElementsType2, typeBindingElementsType, 2, this.isSoft);
            }
            int i = 0;
            if (iKind == 260) {
                List<ConstraintFormula> arrayList = new ArrayList<>();
                while (typeBinding2 != null && typeBinding2.kind() == 260 && typeBinding != null) {
                    if (!addConstraintsFromTypeParameters(typeBinding, (ParameterizedTypeBinding) typeBinding2, arrayList)) {
                        return FALSE;
                    }
                    typeBinding2 = typeBinding2.enclosingType();
                    typeBinding = typeBinding.enclosingType();
                }
                int size = arrayList.size();
                if (size == 0) {
                    return TRUE;
                }
                if (size == 1) {
                    return arrayList.get(0);
                }
                return arrayList.toArray(new ConstraintFormula[arrayList.size()]);
            }
            if (iKind == 516) {
                if (typeBinding.kind() == 8196 && (intersectingTypes = typeBinding.getIntersectingTypes()) != null) {
                    while (i < intersectingTypes.length) {
                        if (TypeBinding.equalsEquals(intersectingTypes[i], typeBinding2)) {
                            return true;
                        }
                        i++;
                    }
                }
                WildcardBinding wildcardBinding2 = (WildcardBinding) typeBinding2;
                if (wildcardBinding2.boundKind == 2) {
                    return create(typeBinding, wildcardBinding2.bound, 2, this.isSoft);
                }
                return FALSE;
            }
            if (iKind != 1028 && iKind != 2052) {
                if (iKind == 4100) {
                    if (typeBinding.kind() == 8196 && (intersectingTypes2 = typeBinding.getIntersectingTypes()) != null) {
                        while (i < intersectingTypes2.length) {
                            if (TypeBinding.equalsEquals(intersectingTypes2[i], typeBinding2)) {
                                return true;
                            }
                            i++;
                        }
                    }
                    if (typeBinding2 instanceof CaptureBinding) {
                        CaptureBinding captureBinding = (CaptureBinding) typeBinding2;
                        if (captureBinding.lowerBound != null && (captureBinding.firstBound == null || captureBinding.firstBound.f626id == 1)) {
                            return create(typeBinding, captureBinding.lowerBound, 2, this.isSoft);
                        }
                    }
                    return FALSE;
                }
                if (iKind == 8196) {
                    typeBinding2 = ((WildcardBinding) typeBinding2).allBounds();
                } else if (iKind != 32772) {
                    if (iKind == 65540) {
                        Invocation invocation = (Invocation) ((PolyTypeBinding) typeBinding2).expression;
                        MethodBinding methodBindingBinding = invocation.binding();
                        if (methodBindingBinding == null || !methodBindingBinding.isValidBinding()) {
                            return FALSE;
                        }
                        return reduceSubType(scope, typeBinding, (methodBindingBinding.isConstructor() ? methodBindingBinding.declaringClass : methodBindingBinding.returnType).capture(scope, invocation.sourceStart(), invocation.sourceEnd()));
                    }
                    throw new IllegalStateException("Unexpected RHS " + typeBinding2);
                }
                ReferenceBinding[] referenceBindingArr = ((IntersectionTypeBinding18) typeBinding2).intersectingTypes;
                ConstraintFormula[] constraintFormulaArr = new ConstraintFormula[referenceBindingArr.length];
                while (i < referenceBindingArr.length) {
                    constraintFormulaArr[i] = create(typeBinding, referenceBindingArr[i], 2, this.isSoft);
                    i++;
                }
                return constraintFormulaArr;
            }
        }
        if (typeBinding.isSubtypeOf(typeBinding2)) {
            return TRUE;
        }
        return FALSE;
    }

    private ArrayBinding findMostSpecificSuperArray(TypeBinding typeBinding, TypeBinding[] typeBindingArr, TypeBinding typeBinding2) {
        int i;
        ArrayBinding arrayBinding;
        if (typeBinding == null || !typeBinding.isArrayType()) {
            i = 0;
            arrayBinding = null;
        } else {
            arrayBinding = (ArrayBinding) typeBinding;
            i = 1;
        }
        for (int i2 = 0; i2 < typeBindingArr.length; i2++) {
            if (typeBindingArr[i2].isArrayType()) {
                arrayBinding = (ArrayBinding) typeBindingArr[i2];
                i++;
            }
        }
        if (i == 0) {
            return null;
        }
        if (i == 1) {
            return arrayBinding;
        }
        InferenceContext18.missingImplementation("Extracting array from intersection is not defined");
        return null;
    }

    boolean addConstraintsFromTypeParameters(TypeBinding typeBinding, ParameterizedTypeBinding parameterizedTypeBinding, List<ConstraintFormula> list) {
        TypeBinding[] typeBindingArr = parameterizedTypeBinding.arguments;
        if (typeBindingArr == null) {
            return true;
        }
        TypeBinding typeBindingFindSuperTypeOriginatingFrom = typeBinding.findSuperTypeOriginatingFrom(parameterizedTypeBinding);
        if (typeBindingFindSuperTypeOriginatingFrom == null) {
            return false;
        }
        if (TypeBinding.equalsEquals(parameterizedTypeBinding, typeBindingFindSuperTypeOriginatingFrom)) {
            return true;
        }
        if (!(typeBindingFindSuperTypeOriginatingFrom instanceof ParameterizedTypeBinding)) {
            return parameterizedTypeBinding.isParameterizedWithOwnVariables();
        }
        TypeBinding[] typeBindingArr2 = ((ParameterizedTypeBinding) typeBindingFindSuperTypeOriginatingFrom).arguments;
        if (typeBindingFindSuperTypeOriginatingFrom.isRawType() || typeBindingArr2 == null || typeBindingArr2.length == 0) {
            return this.isSoft;
        }
        for (int i = 0; i < typeBindingArr.length; i++) {
            list.add(create(typeBindingArr2[i], typeBindingArr[i], 5, this.isSoft));
        }
        return true;
    }

    public boolean equalsEquals(ConstraintTypeFormula constraintTypeFormula) {
        return constraintTypeFormula != null && this.relation == constraintTypeFormula.relation && this.isSoft == constraintTypeFormula.isSoft && TypeBinding.equalsEquals(this.left, constraintTypeFormula.left) && TypeBinding.equalsEquals(this.right, constraintTypeFormula.right);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ConstraintFormula
    public boolean applySubstitution(BoundSet boundSet, InferenceVariable[] inferenceVariableArr) {
        super.applySubstitution(boundSet, inferenceVariableArr);
        for (InferenceVariable inferenceVariable : inferenceVariableArr) {
            TypeBinding instantiation = boundSet.getInstantiation(inferenceVariable, null);
            if (instantiation == null) {
                return false;
            }
            this.left = this.left.substituteInferenceVariable(inferenceVariable, instantiation);
        }
        return true;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("Type Constraint:\n");
        stringBuffer.append("\t⟨");
        appendTypeName(stringBuffer, this.left);
        stringBuffer.append(relationToString(this.relation));
        appendTypeName(stringBuffer, this.right);
        stringBuffer.append((char) 10217);
        return stringBuffer.toString();
    }
}
