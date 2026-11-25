package org.eclipse.jdt.internal.compiler.lookup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;

/* JADX INFO: loaded from: classes14.dex */
class BoundSet {
    static final BoundSet TRUE = new BoundSet();
    static final BoundSet FALSE = new BoundSet();
    HashMap<InferenceVariable, ThreeSets> boundsPerVariable = new HashMap<>();
    HashMap<ParameterizedTypeBinding, ParameterizedTypeBinding> captures = new HashMap<>();
    Set<InferenceVariable> inThrows = new HashSet();
    private TypeBound[] incorporatedBounds = new TypeBound[0];
    private TypeBound[] unincorporatedBounds = new TypeBound[1024];
    private int unincorporatedBoundsCount = 0;
    private TypeBound[] mostRecentBounds = new TypeBound[4];

    private class ThreeSets {
        TypeBinding instantiation;
        Map<InferenceVariable, TypeBound> inverseBounds;
        Set<TypeBound> sameBounds;
        Set<TypeBound> subBounds;
        Set<TypeBound> superBounds;

        public ThreeSets() {
        }

        public ParameterizedTypeBinding mergeTypeParameters(ParameterizedTypeBinding parameterizedTypeBinding, ParameterizedTypeBinding parameterizedTypeBinding2) {
            Set<TypeBound> set;
            Set<TypeBound> set2;
            TypeBinding[] typeBindingArrTypeArguments = parameterizedTypeBinding.typeArguments();
            TypeBinding[] typeBindingArrTypeArguments2 = parameterizedTypeBinding2.typeArguments();
            TypeBinding[] typeBindingArr = new TypeBinding[typeBindingArrTypeArguments.length];
            System.arraycopy(typeBindingArrTypeArguments, 0, typeBindingArr, 0, typeBindingArrTypeArguments.length);
            boolean z = false;
            for (int i = 0; i < typeBindingArrTypeArguments.length; i++) {
                if (!TypeBinding.equalsEquals(typeBindingArrTypeArguments[i], typeBindingArrTypeArguments2[i])) {
                    if (!typeBindingArrTypeArguments[i].isCapture() && !typeBindingArrTypeArguments2[i].isCapture()) {
                        TypeBinding typeBinding = typeBindingArrTypeArguments[i];
                        if (typeBinding instanceof InferenceVariable) {
                            TypeBinding typeBinding2 = typeBindingArrTypeArguments2[i];
                            if (!(typeBinding2 instanceof InferenceVariable)) {
                                ThreeSets threeSets = BoundSet.this.boundsPerVariable.get(typeBindingArrTypeArguments[i]);
                                if (threeSets != null && (set2 = threeSets.sameBounds) != null && set2.contains(new TypeBound((InferenceVariable) typeBindingArrTypeArguments[i], typeBindingArrTypeArguments2[i], 4))) {
                                    typeBindingArr[i] = typeBindingArrTypeArguments2[i];
                                    z = true;
                                }
                            } else if (!typeBinding.equals(typeBinding2)) {
                                return null;
                            }
                        } else {
                            TypeBinding typeBinding3 = typeBindingArrTypeArguments2[i];
                            if (!(typeBinding3 instanceof InferenceVariable)) {
                                if (!TypeBinding.equalsEquals(typeBinding, typeBinding3)) {
                                    return null;
                                }
                            } else {
                                ThreeSets threeSets2 = BoundSet.this.boundsPerVariable.get(typeBindingArrTypeArguments2[i]);
                                if (threeSets2 == null || (set = threeSets2.sameBounds) == null || !set.contains(new TypeBound((InferenceVariable) typeBindingArrTypeArguments2[i], typeBindingArrTypeArguments[i], 4))) {
                                }
                            }
                        }
                    }
                    return null;
                }
            }
            if (!z) {
                return null;
            }
            ParameterizedTypeBinding parameterizedTypeBinding3 = (ParameterizedTypeBinding) parameterizedTypeBinding.clone(parameterizedTypeBinding.enclosingType());
            parameterizedTypeBinding3.arguments = typeBindingArr;
            return parameterizedTypeBinding3;
        }

        public boolean addBound(TypeBound typeBound) {
            ParameterizedTypeBinding parameterizedTypeBindingMergeTypeParameters;
            ParameterizedTypeBinding parameterizedTypeBindingMergeTypeParameters2;
            int i = typeBound.relation;
            if (i == 2) {
                if (this.subBounds == null) {
                    this.subBounds = new HashSet();
                }
                if (CompilerOptions.useunspecdtypeinferenceperformanceoptimization && !typeBound.right.isProperType(true)) {
                    for (TypeBound typeBound2 : this.subBounds) {
                        if (typeBound.right.isParameterizedType() && typeBound2.right.isParameterizedType() && typeBound2.right.original() == typeBound.right.original() && (parameterizedTypeBindingMergeTypeParameters = mergeTypeParameters((ParameterizedTypeBinding) typeBound2.right, (ParameterizedTypeBinding) typeBound.right)) != null) {
                            typeBound2.right = parameterizedTypeBindingMergeTypeParameters;
                            return false;
                        }
                    }
                }
                return this.subBounds.add(typeBound);
            }
            if (i != 3) {
                if (i == 4) {
                    if (this.sameBounds == null) {
                        this.sameBounds = new HashSet();
                    }
                    return this.sameBounds.add(typeBound);
                }
                throw new IllegalArgumentException("Unexpected bound relation in : " + typeBound);
            }
            if (this.superBounds == null) {
                this.superBounds = new HashSet();
            }
            if (CompilerOptions.useunspecdtypeinferenceperformanceoptimization && !typeBound.right.isProperType(true)) {
                for (TypeBound typeBound3 : this.superBounds) {
                    if (typeBound.right.isParameterizedType() && typeBound3.right.isParameterizedType() && typeBound3.right.original() == typeBound.right.original() && (parameterizedTypeBindingMergeTypeParameters2 = mergeTypeParameters((ParameterizedTypeBinding) typeBound3.right, (ParameterizedTypeBinding) typeBound.right)) != null) {
                        typeBound3.right = parameterizedTypeBindingMergeTypeParameters2;
                        return false;
                    }
                }
            }
            return this.superBounds.add(typeBound);
        }

        public TypeBinding[] lowerBounds(boolean z, InferenceVariable inferenceVariable) {
            int size = this.superBounds.size();
            TypeBinding[] typeBindingArr = new TypeBinding[size];
            long j = inferenceVariable.nullHints;
            int i = 0;
            for (TypeBound typeBound : this.superBounds) {
                TypeBinding typeBinding = typeBound.right;
                if (!z || typeBinding.isProperType(true)) {
                    typeBindingArr[i] = typeBinding;
                    j |= typeBound.nullHints;
                    i++;
                }
            }
            if (i == 0) {
                return Binding.NO_TYPES;
            }
            if (i < size) {
                TypeBinding[] typeBindingArr2 = new TypeBinding[i];
                System.arraycopy(typeBindingArr, 0, typeBindingArr2, 0, i);
                typeBindingArr = typeBindingArr2;
            }
            useNullHints(j, typeBindingArr, inferenceVariable.environment);
            InferenceContext18.sortTypes(typeBindingArr);
            return typeBindingArr;
        }

        public TypeBinding[] upperBounds(boolean z, InferenceVariable inferenceVariable) {
            int size = this.subBounds.size();
            ReferenceBinding[] referenceBindingArr = new ReferenceBinding[size];
            Iterator<TypeBound> it = this.subBounds.iterator();
            long j = inferenceVariable.nullHints;
            TypeBinding typeBinding = null;
            int i = 0;
            while (it.hasNext()) {
                TypeBinding typeBinding2 = it.next().right;
                if (!z || typeBinding2.isProperType(true)) {
                    if (typeBinding2 instanceof ReferenceBinding) {
                        referenceBindingArr[i] = (ReferenceBinding) typeBinding2;
                        j |= typeBinding2.tagBits & TagBits.AnnotationNullMASK;
                        i++;
                    } else {
                        if (typeBinding != null) {
                            return Binding.NO_TYPES;
                        }
                        typeBinding = typeBinding2;
                    }
                }
            }
            if (i == 0) {
                return typeBinding != null ? new TypeBinding[]{typeBinding} : Binding.NO_TYPES;
            }
            if (i == 1 && typeBinding != null) {
                return new TypeBinding[]{typeBinding};
            }
            if (i < size) {
                ReferenceBinding[] referenceBindingArr2 = new ReferenceBinding[i];
                System.arraycopy(referenceBindingArr, 0, referenceBindingArr2, 0, i);
                referenceBindingArr = referenceBindingArr2;
            }
            useNullHints(j, referenceBindingArr, inferenceVariable.environment);
            InferenceContext18.sortTypes(referenceBindingArr);
            return referenceBindingArr;
        }

        public boolean hasDependency(InferenceVariable inferenceVariable) {
            Set<TypeBound> set = this.superBounds;
            if (set != null && hasDependency(set, inferenceVariable)) {
                return true;
            }
            Set<TypeBound> set2 = this.sameBounds;
            if (set2 != null && hasDependency(set2, inferenceVariable)) {
                return true;
            }
            Set<TypeBound> set3 = this.subBounds;
            if (set3 != null && hasDependency(set3, inferenceVariable)) {
                return true;
            }
            Map<InferenceVariable, TypeBound> map = this.inverseBounds;
            return map != null && map.containsKey(inferenceVariable);
        }

        private boolean hasDependency(Set<TypeBound> set, InferenceVariable inferenceVariable) {
            for (TypeBound typeBound : set) {
                if (TypeBinding.equalsEquals(typeBound.right, inferenceVariable) || typeBound.right.mentionsAny(new TypeBinding[]{inferenceVariable}, -1)) {
                    return true;
                }
            }
            return false;
        }

        public int size() {
            Set<TypeBound> set = this.superBounds;
            int size = set != null ? set.size() : 0;
            Set<TypeBound> set2 = this.sameBounds;
            if (set2 != null) {
                size += set2.size();
            }
            Set<TypeBound> set3 = this.subBounds;
            return set3 != null ? size + set3.size() : size;
        }

        public int flattenInto(TypeBound[] typeBoundArr, int i) {
            Set<TypeBound> set = this.superBounds;
            if (set != null) {
                int size = set.size();
                System.arraycopy(this.superBounds.toArray(), 0, typeBoundArr, i, size);
                i += size;
            }
            Set<TypeBound> set2 = this.sameBounds;
            if (set2 != null) {
                int size2 = set2.size();
                System.arraycopy(this.sameBounds.toArray(), 0, typeBoundArr, i, size2);
                i += size2;
            }
            Set<TypeBound> set3 = this.subBounds;
            if (set3 == null) {
                return i;
            }
            int size3 = set3.size();
            System.arraycopy(this.subBounds.toArray(), 0, typeBoundArr, i, size3);
            return i + size3;
        }

        public ThreeSets copy() {
            ThreeSets threeSets = BoundSet.this.new ThreeSets();
            if (this.superBounds != null) {
                threeSets.superBounds = new HashSet(this.superBounds);
            }
            if (this.sameBounds != null) {
                threeSets.sameBounds = new HashSet(this.sameBounds);
            }
            if (this.subBounds != null) {
                threeSets.subBounds = new HashSet(this.subBounds);
            }
            threeSets.instantiation = this.instantiation;
            return threeSets;
        }

        public TypeBinding findSingleWrapperType() {
            TypeBinding typeBinding = this.instantiation;
            if (typeBinding != null && typeBinding.isProperType(true)) {
                switch (this.instantiation.f626id) {
                    case 26:
                    case 27:
                    case 28:
                    case 29:
                    case 30:
                    case 31:
                    case 32:
                    case 33:
                        return this.instantiation;
                }
            }
            Set<TypeBound> set = this.subBounds;
            if (set != null) {
                Iterator<TypeBound> it = set.iterator();
                while (it.hasNext()) {
                    TypeBinding typeBinding2 = it.next().right;
                    if (typeBinding2.isProperType(true)) {
                        switch (typeBinding2.f626id) {
                            case 26:
                            case 27:
                            case 28:
                            case 29:
                            case 30:
                            case 31:
                            case 32:
                            case 33:
                                return typeBinding2;
                        }
                    }
                }
            }
            Set<TypeBound> set2 = this.superBounds;
            if (set2 == null) {
                return null;
            }
            Iterator<TypeBound> it2 = set2.iterator();
            while (it2.hasNext()) {
                TypeBinding typeBinding3 = it2.next().right;
                if (typeBinding3.isProperType(true)) {
                    switch (typeBinding3.f626id) {
                        case 26:
                        case 27:
                        case 28:
                        case 29:
                        case 30:
                        case 31:
                        case 32:
                        case 33:
                            return typeBinding3;
                    }
                }
            }
            return null;
        }

        private void useNullHints(long j, TypeBinding[] typeBindingArr, LookupEnvironment lookupEnvironment) {
            int i = 0;
            if (j == TagBits.AnnotationNullMASK) {
                while (i < typeBindingArr.length) {
                    typeBindingArr[i] = typeBindingArr[i].withoutToplevelNullAnnotation();
                    i++;
                }
            } else {
                AnnotationBinding[] annotationBindingArrNullAnnotationsFromTagBits = lookupEnvironment.nullAnnotationsFromTagBits(j);
                if (annotationBindingArrNullAnnotationsFromTagBits != null) {
                    while (i < typeBindingArr.length) {
                        typeBindingArr[i] = lookupEnvironment.createAnnotatedType(typeBindingArr[i], annotationBindingArrNullAnnotationsFromTagBits);
                        i++;
                    }
                }
            }
        }

        TypeBinding combineAndUseNullHints(TypeBinding typeBinding, long j, LookupEnvironment lookupEnvironment) {
            Set<TypeBound> set = this.sameBounds;
            if (set != null) {
                Iterator<TypeBound> it = set.iterator();
                while (it.hasNext()) {
                    j |= it.next().nullHints;
                }
            }
            Set<TypeBound> set2 = this.superBounds;
            if (set2 != null) {
                Iterator<TypeBound> it2 = set2.iterator();
                while (it2.hasNext()) {
                    j |= it2.next().nullHints;
                }
            }
            Set<TypeBound> set3 = this.subBounds;
            if (set3 != null) {
                Iterator<TypeBound> it3 = set3.iterator();
                while (it3.hasNext()) {
                    j |= it3.next().nullHints;
                }
            }
            if (j == TagBits.AnnotationNullMASK) {
                return typeBinding.withoutToplevelNullAnnotation();
            }
            AnnotationBinding[] annotationBindingArrNullAnnotationsFromTagBits = lookupEnvironment.nullAnnotationsFromTagBits(j);
            return annotationBindingArrNullAnnotationsFromTagBits != null ? lookupEnvironment.createAnnotatedType(typeBinding, annotationBindingArrNullAnnotationsFromTagBits) : typeBinding;
        }

        public void setInstantiation(TypeBinding typeBinding, InferenceVariable inferenceVariable, LookupEnvironment lookupEnvironment) {
            if (lookupEnvironment.globalOptions.isAnnotationBasedNullAnalysisEnabled) {
                long j = inferenceVariable.tagBits & TagBits.AnnotationNullMASK;
                long j2 = typeBinding.tagBits | j;
                TypeBinding typeBinding2 = this.instantiation;
                if (typeBinding2 != null) {
                    j2 |= typeBinding2.tagBits;
                }
                long j3 = j2 & TagBits.AnnotationNullMASK;
                if (j3 != TagBits.AnnotationNullMASK) {
                    j = j3;
                }
                if (j != (TagBits.AnnotationNullMASK & typeBinding.tagBits)) {
                    AnnotationBinding[] annotationBindingArrNullAnnotationsFromTagBits = lookupEnvironment.nullAnnotationsFromTagBits(j);
                    if (annotationBindingArrNullAnnotationsFromTagBits != null) {
                        typeBinding = lookupEnvironment.createAnnotatedType(typeBinding.withoutToplevelNullAnnotation(), annotationBindingArrNullAnnotationsFromTagBits);
                    } else if (typeBinding.hasNullTypeAnnotations()) {
                        typeBinding = typeBinding.withoutToplevelNullAnnotation();
                    }
                }
            }
            this.instantiation = typeBinding;
        }
    }

    public void addBoundsFromTypeParameters(InferenceContext18 inferenceContext18, TypeVariableBinding[] typeVariableBindingArr, InferenceVariable[] inferenceVariableArr) {
        int length = typeVariableBindingArr.length;
        for (int i = 0; i < length; i++) {
            TypeVariableBinding typeVariableBinding = typeVariableBindingArr[i];
            InferenceVariable inferenceVariable = inferenceVariableArr[i];
            TypeBound[] typeBounds = typeVariableBinding.getTypeBounds(inferenceVariable, new InferenceSubstitution(inferenceContext18));
            if (!(typeBounds.length > 0 ? addBounds(typeBounds, inferenceContext18.environment) : false)) {
                addBound(new TypeBound(inferenceVariable, inferenceContext18.object, 2), inferenceContext18.environment);
            }
        }
    }

    public TypeBound[] flatten() {
        Iterator<ThreeSets> it = this.boundsPerVariable.values().iterator();
        int iFlattenInto = 0;
        int size = 0;
        while (it.hasNext()) {
            size += it.next().size();
        }
        TypeBound[] typeBoundArr = new TypeBound[size];
        if (size == 0) {
            return typeBoundArr;
        }
        Iterator<ThreeSets> it2 = this.boundsPerVariable.values().iterator();
        while (it2.hasNext()) {
            iFlattenInto = it2.next().flattenInto(typeBoundArr, iFlattenInto);
        }
        return typeBoundArr;
    }

    public BoundSet copy() {
        BoundSet boundSet = new BoundSet();
        for (Map.Entry<InferenceVariable, ThreeSets> entry : this.boundsPerVariable.entrySet()) {
            boundSet.boundsPerVariable.put(entry.getKey(), entry.getValue().copy());
        }
        boundSet.inThrows.addAll(this.inThrows);
        boundSet.captures.putAll(this.captures);
        TypeBound[] typeBoundArr = this.incorporatedBounds;
        TypeBound[] typeBoundArr2 = new TypeBound[typeBoundArr.length];
        boundSet.incorporatedBounds = typeBoundArr2;
        System.arraycopy(typeBoundArr, 0, typeBoundArr2, 0, this.incorporatedBounds.length);
        TypeBound[] typeBoundArr3 = this.unincorporatedBounds;
        TypeBound[] typeBoundArr4 = new TypeBound[typeBoundArr3.length];
        boundSet.unincorporatedBounds = typeBoundArr4;
        System.arraycopy(typeBoundArr3, 0, typeBoundArr4, 0, this.unincorporatedBounds.length);
        boundSet.unincorporatedBoundsCount = this.unincorporatedBoundsCount;
        return boundSet;
    }

    public void addBound(TypeBound typeBound, LookupEnvironment lookupEnvironment) {
        if ((typeBound.relation == 2 && typeBound.right.f626id == 1) || typeBound.left == typeBound.right) {
            return;
        }
        for (int i = 0; i < 4; i++) {
            if (typeBound.equals(this.mostRecentBounds[i])) {
                if (lookupEnvironment.globalOptions.isAnnotationBasedNullAnalysisEnabled) {
                    TypeBound typeBound2 = this.mostRecentBounds[i];
                    long j = typeBound.right.tagBits & TagBits.AnnotationNullMASK;
                    long j2 = TagBits.AnnotationNullMASK & typeBound2.right.tagBits;
                    if (j != j2) {
                        if (j2 == 0) {
                            typeBound2.right = typeBound.right;
                            return;
                        } else {
                            if (j != 0) {
                                typeBound2.right = lookupEnvironment.createAnnotatedType(typeBound2.right, lookupEnvironment.nullAnnotationsFromTagBits(j));
                                return;
                            }
                            return;
                        }
                    }
                    return;
                }
                return;
            }
        }
        TypeBound[] typeBoundArr = this.mostRecentBounds;
        typeBoundArr[3] = typeBoundArr[2];
        typeBoundArr[2] = typeBoundArr[1];
        typeBoundArr[1] = typeBoundArr[0];
        typeBoundArr[0] = typeBound;
        InferenceVariable inferenceVariablePrototype = typeBound.left.prototype();
        ThreeSets threeSets = this.boundsPerVariable.get(inferenceVariablePrototype);
        if (threeSets == null) {
            HashMap<InferenceVariable, ThreeSets> map = this.boundsPerVariable;
            ThreeSets threeSets2 = new ThreeSets();
            map.put(inferenceVariablePrototype, threeSets2);
            threeSets = threeSets2;
        }
        if (threeSets.addBound(typeBound)) {
            TypeBound[] typeBoundArr2 = this.unincorporatedBounds;
            int length = typeBoundArr2.length;
            if (this.unincorporatedBoundsCount >= length) {
                TypeBound[] typeBoundArr3 = new TypeBound[length * 2];
                this.unincorporatedBounds = typeBoundArr3;
                System.arraycopy(typeBoundArr2, 0, typeBoundArr3, 0, length);
            }
            TypeBound[] typeBoundArr4 = this.unincorporatedBounds;
            int i2 = this.unincorporatedBoundsCount;
            this.unincorporatedBoundsCount = i2 + 1;
            typeBoundArr4[i2] = typeBound;
            TypeBinding typeBinding = typeBound.right;
            if (typeBound.relation == 4 && typeBinding.isProperType(true)) {
                threeSets.setInstantiation(typeBinding, inferenceVariablePrototype, lookupEnvironment);
            }
            if (typeBound.right instanceof InferenceVariable) {
                InferenceVariable inferenceVariable = (InferenceVariable) typeBound.right.prototype();
                ThreeSets threeSets3 = this.boundsPerVariable.get(inferenceVariable);
                if (threeSets3 == null) {
                    HashMap<InferenceVariable, ThreeSets> map2 = this.boundsPerVariable;
                    ThreeSets threeSets4 = new ThreeSets();
                    map2.put(inferenceVariable, threeSets4);
                    threeSets3 = threeSets4;
                }
                if (threeSets3.inverseBounds == null) {
                    threeSets3.inverseBounds = new HashMap();
                }
                threeSets3.inverseBounds.put(inferenceVariable, typeBound);
            }
        }
    }

    private boolean addBounds(TypeBound[] typeBoundArr, LookupEnvironment lookupEnvironment) {
        boolean zIsBound = false;
        for (int i = 0; i < typeBoundArr.length; i++) {
            addBound(typeBoundArr[i], lookupEnvironment);
            zIsBound |= typeBoundArr[i].isBound();
        }
        return zIsBound;
    }

    public void addBounds(BoundSet boundSet, LookupEnvironment lookupEnvironment) {
        if (boundSet == null || lookupEnvironment == null) {
            return;
        }
        addBounds(boundSet.flatten(), lookupEnvironment);
    }

    public boolean isInstantiated(InferenceVariable inferenceVariable) {
        ThreeSets threeSets = this.boundsPerVariable.get(inferenceVariable.prototype());
        return (threeSets == null || threeSets.instantiation == null) ? false : true;
    }

    public TypeBinding getInstantiation(InferenceVariable inferenceVariable, LookupEnvironment lookupEnvironment) {
        ThreeSets threeSets = this.boundsPerVariable.get(inferenceVariable.prototype());
        if (threeSets == null) {
            return null;
        }
        TypeBinding typeBinding = threeSets.instantiation;
        return (lookupEnvironment == null || !lookupEnvironment.globalOptions.isAnnotationBasedNullAnalysisEnabled || typeBinding == null || (typeBinding.tagBits & TagBits.AnnotationNullMASK) != 0) ? typeBinding : threeSets.combineAndUseNullHints(typeBinding, inferenceVariable.nullHints, lookupEnvironment);
    }

    public int numUninstantiatedVariables(InferenceVariable[] inferenceVariableArr) {
        int i = 0;
        for (InferenceVariable inferenceVariable : inferenceVariableArr) {
            if (!isInstantiated(inferenceVariable)) {
                i++;
            }
        }
        return i;
    }

    boolean incorporate(InferenceContext18 inferenceContext18) throws InferenceFailureException {
        if (this.unincorporatedBoundsCount == 0 && this.captures.size() == 0) {
            return true;
        }
        do {
            TypeBound[] typeBoundArr = this.unincorporatedBounds;
            int i = this.unincorporatedBoundsCount;
            TypeBound[] typeBoundArr2 = new TypeBound[i];
            System.arraycopy(typeBoundArr, 0, typeBoundArr2, 0, i);
            this.unincorporatedBoundsCount = 0;
            if (!incorporate(inferenceContext18, this.incorporatedBounds, typeBoundArr2) || !incorporate(inferenceContext18, typeBoundArr2, typeBoundArr2)) {
                return false;
            }
            TypeBound[] typeBoundArr3 = this.incorporatedBounds;
            int length = typeBoundArr3.length;
            TypeBound[] typeBoundArr4 = new TypeBound[length + i];
            System.arraycopy(typeBoundArr3, 0, typeBoundArr4, 0, length);
            System.arraycopy(typeBoundArr2, 0, typeBoundArr4, length, i);
            this.incorporatedBounds = typeBoundArr4;
        } while (this.unincorporatedBoundsCount > 0);
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:144:0x02cb  */
    /* JADX WARN: Code duplicated, block: B:95:0x01fa  */
    boolean incorporate(InferenceContext18 inferenceContext18, TypeBound[] typeBoundArr, TypeBound[] typeBoundArr2) throws InferenceFailureException {
        int i;
        int i2;
        TypeVariableBinding[] typeVariableBindingArr;
        InferenceSubstitution inferenceSubstitution;
        TypeBinding typeBindingCreateIntersectionType18;
        Iterator<TypeBound> it;
        ConstraintTypeFormula constraintTypeFormulaCombineEqualSupers;
        boolean zEqualsEquals;
        int i3;
        boolean z;
        ConstraintTypeFormula[] constraintTypeFormulaArr;
        int i4;
        int i5;
        boolean z2 = inferenceContext18.environment.globalOptions.isAnnotationBasedNullAnalysisEnabled;
        ConstraintTypeFormula[] constraintTypeFormulaArr2 = new ConstraintTypeFormula[4];
        int length = typeBoundArr.length;
        int i6 = 0;
        int i7 = 0;
        while (true) {
            boolean z3 = true;
            if (i7 < length) {
                TypeBound typeBound = typeBoundArr[i7];
                int length2 = typeBoundArr2.length;
                TypeBound typeBound2 = typeBound;
                int i8 = 0;
                while (i8 < length2) {
                    TypeBound typeBound3 = typeBoundArr2[i8];
                    if (typeBound2 == typeBound3) {
                        z = z2;
                        constraintTypeFormulaArr = constraintTypeFormulaArr2;
                        i3 = length;
                        i4 = i7;
                        i8 = i8;
                    } else {
                        TypeBound typeBound4 = typeBound3;
                        int i9 = 2;
                        TypeBound typeBound5 = typeBound2;
                        int i10 = 1;
                        while (true) {
                            if (i10 == i9) {
                                TypeBound typeBound6 = typeBound4;
                                typeBound4 = typeBound5;
                                typeBound5 = typeBound6;
                            }
                            int i11 = typeBound5.relation;
                            if (i11 != i9) {
                                if (i11 == 3) {
                                    int i12 = typeBound4.relation;
                                    if (i12 == 2) {
                                        constraintTypeFormulaCombineEqualSupers = combineSuperAndSub(typeBound5, typeBound4);
                                    } else if (i12 == 3) {
                                        constraintTypeFormulaCombineEqualSupers = combineEqualSupers(typeBound5, typeBound4);
                                    } else if (i12 != 4) {
                                        constraintTypeFormulaCombineEqualSupers = null;
                                    } else {
                                        constraintTypeFormulaCombineEqualSupers = combineSameSubSuper(typeBound4, typeBound5);
                                    }
                                } else if (i11 == 4) {
                                    int i13 = typeBound4.relation;
                                    if (i13 == i9 || i13 == 3) {
                                        constraintTypeFormulaCombineEqualSupers = combineSameSubSuper(typeBound5, typeBound4);
                                    } else if (i13 != 4) {
                                        constraintTypeFormulaCombineEqualSupers = null;
                                    } else {
                                        constraintTypeFormulaCombineEqualSupers = combineSameSame(typeBound5, typeBound4);
                                    }
                                } else {
                                    constraintTypeFormulaCombineEqualSupers = null;
                                }
                                zEqualsEquals = false;
                            } else {
                                int i14 = typeBound4.relation;
                                if (i14 != 2) {
                                    if (i14 == 3) {
                                        constraintTypeFormulaCombineEqualSupers = combineSuperAndSub(typeBound4, typeBound5);
                                    } else if (i14 != 4) {
                                        constraintTypeFormulaCombineEqualSupers = null;
                                    } else {
                                        constraintTypeFormulaCombineEqualSupers = combineSameSubSuper(typeBound4, typeBound5);
                                    }
                                    zEqualsEquals = false;
                                } else {
                                    constraintTypeFormulaCombineEqualSupers = combineEqualSupers(typeBound5, typeBound4);
                                    zEqualsEquals = TypeBinding.equalsEquals(typeBound5.left, typeBound4.left);
                                }
                            }
                            if (constraintTypeFormulaCombineEqualSupers != null) {
                                i3 = length;
                                if (constraintTypeFormulaCombineEqualSupers.left == constraintTypeFormulaCombineEqualSupers.right || constraintTypeFormulaCombineEqualSupers.equalsEquals(constraintTypeFormulaArr2[0]) || constraintTypeFormulaCombineEqualSupers.equalsEquals(constraintTypeFormulaArr2[1]) || constraintTypeFormulaCombineEqualSupers.equalsEquals(constraintTypeFormulaArr2[2]) || constraintTypeFormulaCombineEqualSupers.equalsEquals(constraintTypeFormulaArr2[3])) {
                                    constraintTypeFormulaCombineEqualSupers = null;
                                }
                            } else {
                                i3 = length;
                            }
                            if (constraintTypeFormulaCombineEqualSupers != null) {
                                constraintTypeFormulaArr2[3] = constraintTypeFormulaArr2[2];
                                constraintTypeFormulaArr2[2] = constraintTypeFormulaArr2[1];
                                constraintTypeFormulaArr2[1] = constraintTypeFormulaArr2[0];
                                constraintTypeFormulaArr2[0] = constraintTypeFormulaCombineEqualSupers;
                                if (!reduceOneConstraint(inferenceContext18, constraintTypeFormulaCombineEqualSupers)) {
                                    return false;
                                }
                                if (z2) {
                                    z = z2;
                                    constraintTypeFormulaArr = constraintTypeFormulaArr2;
                                    long j = constraintTypeFormulaCombineEqualSupers.left.tagBits;
                                    TypeBinding typeBinding = constraintTypeFormulaCombineEqualSupers.right;
                                    i4 = i7;
                                    long j2 = (j | typeBinding.tagBits) & TagBits.AnnotationNullMASK;
                                    if (j2 != 0 && (TypeBinding.equalsEquals(typeBound5.left, typeBound4.left) || ((typeBound5.relation == 4 && TypeBinding.equalsEquals(typeBound5.right, typeBound4.left)) || (typeBound4.relation == 4 && TypeBinding.equalsEquals(typeBound5.left, typeBound4.right))))) {
                                        typeBound5.nullHints |= j2;
                                        typeBound4.nullHints = j2 | typeBound4.nullHints;
                                    }
                                } else {
                                    z = z2;
                                    constraintTypeFormulaArr = constraintTypeFormulaArr2;
                                    i4 = i7;
                                }
                            } else {
                                z = z2;
                                constraintTypeFormulaArr = constraintTypeFormulaArr2;
                                i4 = i7;
                            }
                            ConstraintTypeFormula[] constraintTypeFormulaArrDeriveTypeArgumentConstraints = zEqualsEquals ? deriveTypeArgumentConstraints(typeBound5, typeBound4) : null;
                            if (constraintTypeFormulaArrDeriveTypeArgumentConstraints != null) {
                                for (ConstraintTypeFormula constraintTypeFormula : constraintTypeFormulaArrDeriveTypeArgumentConstraints) {
                                    if (!reduceOneConstraint(inferenceContext18, constraintTypeFormula)) {
                                        return false;
                                    }
                                }
                                i5 = 2;
                            } else {
                                i5 = 2;
                            }
                            if (i10 == i5) {
                                TypeBound typeBound7 = typeBound4;
                                typeBound4 = typeBound5;
                                typeBound5 = typeBound7;
                            }
                            if (typeBoundArr == typeBoundArr2 || (i10 = i10 + 1) > i5) {
                                break;
                            }
                            i8 = i8;
                            i7 = i4;
                            constraintTypeFormulaArr2 = constraintTypeFormulaArr;
                            length = i3;
                            i9 = i5;
                            z2 = z;
                        }
                        typeBound2 = typeBound5;
                    }
                    i8++;
                    i7 = i4;
                    z2 = z;
                    constraintTypeFormulaArr2 = constraintTypeFormulaArr;
                    length = i3;
                }
                i7++;
                i6 = 0;
            } else {
                for (Map.Entry<ParameterizedTypeBinding, ParameterizedTypeBinding> entry : this.captures.entrySet()) {
                    ParameterizedTypeBinding key = entry.getKey();
                    ParameterizedTypeBinding value = entry.getValue();
                    final TypeVariableBinding[] typeVariableBindingArrTypeVariables = ((ReferenceBinding) value.original()).typeVariables();
                    int length3 = key.arguments.length;
                    InferenceVariable[] inferenceVariableArr = new InferenceVariable[length3];
                    System.arraycopy(key.arguments, i6, inferenceVariableArr, i6, length3);
                    InferenceSubstitution inferenceSubstitution2 = inferenceSubstitution;
                    InferenceSubstitution inferenceSubstitution3 = new InferenceSubstitution(inferenceContext18.environment, inferenceVariableArr, inferenceContext18.currentInvocation) { // from class: org.eclipse.jdt.internal.compiler.lookup.BoundSet.1
                        @Override // org.eclipse.jdt.internal.compiler.lookup.InferenceSubstitution
                        protected TypeBinding getP(int i15) {
                            return typeVariableBindingArrTypeVariables[i15];
                        }
                    };
                    int length4 = typeVariableBindingArrTypeVariables.length;
                    int i15 = i6;
                    while (i15 < length4) {
                        TypeVariableBinding typeVariableBinding = typeVariableBindingArrTypeVariables[i15];
                        InferenceVariable inferenceVariable = (InferenceVariable) key.arguments[i15];
                        addBounds(typeVariableBinding.getTypeBounds(inferenceVariable, inferenceSubstitution2), inferenceContext18.environment);
                        TypeBinding typeBinding2 = value.arguments[i15];
                        if (typeBinding2 instanceof WildcardBinding) {
                            WildcardBinding wildcardBinding = (WildcardBinding) typeBinding2;
                            TypeBinding typeBinding3 = wildcardBinding.bound;
                            ThreeSets threeSets = this.boundsPerVariable.get(inferenceVariable.prototype());
                            if (threeSets != null) {
                                if (threeSets.sameBounds != null) {
                                    for (Iterator<TypeBound> it2 = threeSets.sameBounds.iterator(); it2.hasNext(); it2 = it) {
                                        TypeBound next = it2.next();
                                        if (next.right instanceof CaptureBinding) {
                                            it = it2;
                                            if (next.right.isProperType(true)) {
                                                continue;
                                            }
                                        } else {
                                            it = it2;
                                        }
                                        if (!(next.right instanceof InferenceVariable)) {
                                            return false;
                                        }
                                    }
                                }
                                if (threeSets.subBounds != null && typeVariableBinding.firstBound != null) {
                                    Iterator<TypeBound> it3 = threeSets.subBounds.iterator();
                                    while (it3.hasNext()) {
                                        TypeBound next2 = it3.next();
                                        if (!(next2.right instanceof InferenceVariable)) {
                                            TypeBinding typeBinding4 = next2.right;
                                            TypeBinding typeBinding5 = typeVariableBinding.firstBound;
                                            TypeBinding typeBinding6 = typeBinding3;
                                            ReferenceBinding[] referenceBindingArr = typeVariableBinding.superInterfaces;
                                            TypeVariableBinding typeVariableBinding2 = typeVariableBinding;
                                            if (referenceBindingArr == Binding.NO_SUPERINTERFACES) {
                                                typeBindingCreateIntersectionType18 = typeBinding5;
                                            } else {
                                                int length5 = referenceBindingArr.length;
                                                ReferenceBinding[] referenceBindingArr2 = new ReferenceBinding[length5 + 1];
                                                referenceBindingArr2[0] = (ReferenceBinding) typeBinding5;
                                                System.arraycopy(referenceBindingArr, 0, referenceBindingArr2, 1, length5);
                                                typeBindingCreateIntersectionType18 = inferenceContext18.environment.createIntersectionType18(referenceBindingArr2);
                                            }
                                            addTypeBoundsFromWildcardBound(inferenceContext18, inferenceSubstitution2, wildcardBinding.boundKind, typeBinding6, typeBinding4, typeBindingCreateIntersectionType18);
                                            wildcardBinding = wildcardBinding;
                                            typeBinding3 = typeBinding6;
                                            typeVariableBindingArrTypeVariables = typeVariableBindingArrTypeVariables;
                                            i15 = i15;
                                            length4 = length4;
                                            typeVariableBinding = typeVariableBinding2;
                                            inferenceSubstitution2 = inferenceSubstitution2;
                                            it3 = it3;
                                        }
                                    }
                                }
                                TypeBinding typeBinding7 = typeBinding3;
                                i = i15;
                                i2 = length4;
                                typeVariableBindingArr = typeVariableBindingArrTypeVariables;
                                inferenceSubstitution = inferenceSubstitution2;
                                WildcardBinding wildcardBinding2 = wildcardBinding;
                                if (threeSets.superBounds != null) {
                                    for (TypeBound typeBound8 : threeSets.superBounds) {
                                        if (!(typeBound8.right instanceof InferenceVariable)) {
                                            if (wildcardBinding2.boundKind != 2) {
                                                return false;
                                            }
                                            reduceOneConstraint(inferenceContext18, ConstraintTypeFormula.create(typeBound8.right, typeBinding7, 2));
                                        }
                                    }
                                } else {
                                    continue;
                                }
                            } else {
                                i = i15;
                                i2 = length4;
                                typeVariableBindingArr = typeVariableBindingArrTypeVariables;
                                inferenceSubstitution = inferenceSubstitution2;
                            }
                        } else {
                            i = i15;
                            i2 = length4;
                            typeVariableBindingArr = typeVariableBindingArrTypeVariables;
                            inferenceSubstitution = inferenceSubstitution2;
                            addBound(new TypeBound(inferenceVariable, typeBinding2, 4), inferenceContext18.environment);
                        }
                        i15 = i + 1;
                        typeVariableBindingArrTypeVariables = typeVariableBindingArr;
                        length4 = i2;
                        inferenceSubstitution2 = inferenceSubstitution;
                        i6 = 0;
                        z3 = true;
                    }
                }
                this.captures.clear();
                return z3;
            }
        }
    }

    void addTypeBoundsFromWildcardBound(InferenceContext18 inferenceContext18, InferenceSubstitution inferenceSubstitution, int i, TypeBinding typeBinding, TypeBinding typeBinding2, TypeBinding typeBinding3) throws InferenceFailureException {
        ConstraintTypeFormula constraintTypeFormulaCreate;
        if (i == 1) {
            constraintTypeFormulaCreate = typeBinding3.f626id == 1 ? ConstraintTypeFormula.create(typeBinding, typeBinding2, 2) : null;
            if (typeBinding.f626id == 1) {
                constraintTypeFormulaCreate = ConstraintTypeFormula.create(inferenceSubstitution.substitute(inferenceSubstitution, typeBinding3), typeBinding2, 2);
            }
        } else {
            constraintTypeFormulaCreate = ConstraintTypeFormula.create(inferenceSubstitution.substitute(inferenceSubstitution, typeBinding3), typeBinding2, 2);
        }
        if (constraintTypeFormulaCreate != null) {
            reduceOneConstraint(inferenceContext18, constraintTypeFormulaCreate);
        }
    }

    private ConstraintTypeFormula combineSameSame(TypeBound typeBound, TypeBound typeBound2) {
        if (TypeBinding.equalsEquals(typeBound.left, typeBound2.left)) {
            return ConstraintTypeFormula.create(typeBound.right, typeBound2.right, 4, typeBound.isSoft || typeBound2.isSoft);
        }
        ConstraintTypeFormula constraintTypeFormulaCombineSameSameWithProperType = combineSameSameWithProperType(typeBound, typeBound2);
        if (constraintTypeFormulaCombineSameSameWithProperType != null) {
            return constraintTypeFormulaCombineSameSameWithProperType;
        }
        ConstraintTypeFormula constraintTypeFormulaCombineSameSameWithProperType2 = combineSameSameWithProperType(typeBound2, typeBound);
        if (constraintTypeFormulaCombineSameSameWithProperType2 != null) {
            return constraintTypeFormulaCombineSameSameWithProperType2;
        }
        return null;
    }

    private ConstraintTypeFormula combineSameSameWithProperType(TypeBound typeBound, TypeBound typeBound2) {
        TypeBinding typeBinding = typeBound.right;
        boolean z = true;
        if (!typeBinding.isProperType(true)) {
            return null;
        }
        InferenceVariable inferenceVariable = typeBound.left;
        InferenceVariable inferenceVariable2 = typeBound2.left;
        TypeBinding typeBindingSubstituteInferenceVariable = typeBound2.right.substituteInferenceVariable(inferenceVariable, typeBinding);
        if (!typeBound.isSoft && !typeBound2.isSoft) {
            z = false;
        }
        return ConstraintTypeFormula.create(inferenceVariable2, typeBindingSubstituteInferenceVariable, 4, z);
    }

    private ConstraintTypeFormula combineSameSubSuper(TypeBound typeBound, TypeBound typeBound2) {
        InferenceVariable inferenceVariable = typeBound.left;
        TypeBinding typeBinding = typeBound.right;
        if (TypeBinding.equalsEquals(inferenceVariable, typeBound2.left)) {
            return ConstraintTypeFormula.create(typeBinding, typeBound2.right, typeBound2.relation, typeBound2.isSoft || typeBound.isSoft);
        }
        if (TypeBinding.equalsEquals(inferenceVariable, typeBound2.right)) {
            return ConstraintTypeFormula.create(typeBound2.right, typeBinding, typeBound2.relation, typeBound2.isSoft || typeBound.isSoft);
        }
        if (typeBound.right instanceof InferenceVariable) {
            inferenceVariable = (InferenceVariable) typeBound.right;
            InferenceVariable inferenceVariable2 = typeBound.left;
            if (TypeBinding.equalsEquals(inferenceVariable, typeBound2.left)) {
                return ConstraintTypeFormula.create(inferenceVariable2, typeBound2.right, typeBound2.relation, typeBound2.isSoft || typeBound.isSoft);
            }
            if (TypeBinding.equalsEquals(inferenceVariable, typeBound2.right)) {
                return ConstraintTypeFormula.create(typeBound2.right, inferenceVariable2, typeBound2.relation, typeBound2.isSoft || typeBound.isSoft);
            }
        }
        TypeBinding typeBinding2 = typeBound.right;
        if (!typeBinding2.isProperType(true)) {
            return null;
        }
        boolean zEqualsEquals = TypeBinding.equalsEquals(inferenceVariable, typeBound2.left);
        TypeBinding typeBinding3 = zEqualsEquals ? typeBinding2 : typeBound2.left;
        TypeBinding typeBindingSubstituteInferenceVariable = typeBound2.right.substituteInferenceVariable(inferenceVariable, typeBinding2);
        if (TypeBinding.notEquals(typeBindingSubstituteInferenceVariable, typeBound2.right) || zEqualsEquals) {
            return ConstraintTypeFormula.create(typeBinding3, typeBindingSubstituteInferenceVariable, typeBound2.relation, typeBound2.isSoft || typeBound.isSoft);
        }
        return null;
    }

    private ConstraintTypeFormula combineSuperAndSub(TypeBound typeBound, TypeBound typeBound2) {
        if (TypeBinding.equalsEquals(typeBound.left, typeBound2.left)) {
            return ConstraintTypeFormula.create(typeBound.right, typeBound2.right, 2, typeBound2.isSoft || typeBound.isSoft);
        }
        if ((typeBound.right instanceof InferenceVariable) && TypeBinding.equalsEquals((InferenceVariable) typeBound.right, typeBound2.right)) {
            return ConstraintTypeFormula.create(typeBound.left, typeBound2.left, 3, typeBound2.isSoft || typeBound.isSoft);
        }
        return null;
    }

    private ConstraintTypeFormula combineEqualSupers(TypeBound typeBound, TypeBound typeBound2) {
        if (TypeBinding.equalsEquals(typeBound.left, typeBound2.right)) {
            return ConstraintTypeFormula.create(typeBound2.left, typeBound.right, typeBound.relation, typeBound2.isSoft || typeBound.isSoft);
        }
        if (TypeBinding.equalsEquals(typeBound.right, typeBound2.left)) {
            return ConstraintTypeFormula.create(typeBound.left, typeBound2.right, typeBound.relation, typeBound2.isSoft || typeBound.isSoft);
        }
        return null;
    }

    private ConstraintTypeFormula[] deriveTypeArgumentConstraints(TypeBound typeBound, TypeBound typeBound2) {
        TypeBinding[] typeBindingArrSuperTypesWithCommonGenericType = superTypesWithCommonGenericType(typeBound.right, typeBound2.right);
        if (typeBindingArrSuperTypesWithCommonGenericType != null) {
            return typeArgumentEqualityConstraints(typeBindingArrSuperTypesWithCommonGenericType[0], typeBindingArrSuperTypesWithCommonGenericType[1], typeBound.isSoft || typeBound2.isSoft);
        }
        return null;
    }

    private ConstraintTypeFormula[] typeArgumentEqualityConstraints(TypeBinding typeBinding, TypeBinding typeBinding2, boolean z) {
        if (typeBinding == null || typeBinding.kind() != 260 || typeBinding2 == null || typeBinding2.kind() != 260 || TypeBinding.equalsEquals(typeBinding, typeBinding2)) {
            return null;
        }
        TypeBinding[] typeBindingArrTypeArguments = typeBinding.typeArguments();
        TypeBinding[] typeBindingArrTypeArguments2 = typeBinding2.typeArguments();
        if (typeBindingArrTypeArguments != null && typeBindingArrTypeArguments2 != null && typeBindingArrTypeArguments.length == typeBindingArrTypeArguments2.length) {
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < typeBindingArrTypeArguments.length; i++) {
                TypeBinding typeBinding3 = typeBindingArrTypeArguments[i];
                TypeBinding typeBinding4 = typeBindingArrTypeArguments2[i];
                if (!typeBinding3.isWildcard() && !typeBinding4.isWildcard() && !TypeBinding.equalsEquals(typeBinding3, typeBinding4)) {
                    arrayList.add(ConstraintTypeFormula.create(typeBinding3, typeBinding4, 4, z));
                }
            }
            if (arrayList.size() > 0) {
                return (ConstraintTypeFormula[]) arrayList.toArray(new ConstraintTypeFormula[arrayList.size()]);
            }
            return null;
        }
        return null;
    }

    public boolean reduceOneConstraint(InferenceContext18 inferenceContext18, ConstraintFormula constraintFormula) throws InferenceFailureException {
        Object objReduce = constraintFormula.reduce(inferenceContext18);
        if (objReduce == ReductionResult.FALSE) {
            return false;
        }
        if (objReduce == ReductionResult.TRUE) {
            return true;
        }
        if (objReduce == constraintFormula) {
            throw new IllegalStateException("Failed to reduce constraint formula");
        }
        if (objReduce != null) {
            if (objReduce instanceof ConstraintFormula) {
                if (!reduceOneConstraint(inferenceContext18, (ConstraintFormula) objReduce)) {
                    return false;
                }
            } else if (objReduce instanceof ConstraintFormula[]) {
                ConstraintFormula[] constraintFormulaArr = (ConstraintFormula[]) objReduce;
                for (ConstraintFormula constraintFormula2 : constraintFormulaArr) {
                    if (!reduceOneConstraint(inferenceContext18, constraintFormula2)) {
                        return false;
                    }
                }
            } else {
                addBound((TypeBound) objReduce, inferenceContext18.environment);
            }
        }
        return true;
    }

    public boolean dependsOnResolutionOf(InferenceVariable inferenceVariable, InferenceVariable inferenceVariable2) {
        InferenceVariable inferenceVariablePrototype = inferenceVariable.prototype();
        InferenceVariable inferenceVariablePrototype2 = inferenceVariable2.prototype();
        if (TypeBinding.equalsEquals(inferenceVariablePrototype, inferenceVariablePrototype2)) {
            return true;
        }
        boolean z = false;
        for (Map.Entry<ParameterizedTypeBinding, ParameterizedTypeBinding> entry : this.captures.entrySet()) {
            ParameterizedTypeBinding key = entry.getKey();
            for (int i = 0; i < key.arguments.length; i++) {
                if (TypeBinding.equalsEquals(key.arguments[i], inferenceVariablePrototype)) {
                    if (entry.getValue().mentionsAny(new TypeBinding[]{inferenceVariablePrototype2}, -1) || key.mentionsAny(new TypeBinding[]{inferenceVariablePrototype2}, i)) {
                        return true;
                    }
                } else if (TypeBinding.equalsEquals(key.arguments[i], inferenceVariablePrototype2)) {
                    z = true;
                }
            }
        }
        if (z) {
            ThreeSets threeSets = this.boundsPerVariable.get(inferenceVariablePrototype2);
            if (threeSets != null && threeSets.hasDependency(inferenceVariablePrototype)) {
                return true;
            }
        } else {
            ThreeSets threeSets2 = this.boundsPerVariable.get(inferenceVariablePrototype);
            if (threeSets2 != null && threeSets2.hasDependency(inferenceVariablePrototype2)) {
                return true;
            }
        }
        return false;
    }

    List<Set<InferenceVariable>> computeConnectedComponents(InferenceVariable[] inferenceVariableArr) {
        Map<InferenceVariable, Set<InferenceVariable>> map = new HashMap<>();
        for (int i = 0; i < inferenceVariableArr.length; i++) {
            InferenceVariable inferenceVariable = inferenceVariableArr[i];
            HashSet hashSet = new HashSet();
            map.put(inferenceVariable, hashSet);
            for (int i2 = 0; i2 < i; i2++) {
                InferenceVariable inferenceVariable2 = inferenceVariableArr[i2];
                if (dependsOnResolutionOf(inferenceVariable, inferenceVariable2) || dependsOnResolutionOf(inferenceVariable2, inferenceVariable)) {
                    hashSet.add(inferenceVariable2);
                    map.get(inferenceVariable2).add(inferenceVariable);
                }
            }
        }
        Set<InferenceVariable> hashSet2 = new HashSet<>();
        ArrayList arrayList = new ArrayList();
        for (InferenceVariable inferenceVariable3 : inferenceVariableArr) {
            HashSet hashSet3 = new HashSet();
            addConnected(hashSet3, inferenceVariable3, map, hashSet2);
            if (!hashSet3.isEmpty()) {
                arrayList.add(hashSet3);
            }
        }
        return arrayList;
    }

    private void addConnected(Set<InferenceVariable> set, InferenceVariable inferenceVariable, Map<InferenceVariable, Set<InferenceVariable>> map, Set<InferenceVariable> set2) {
        if (set2.add(inferenceVariable)) {
            set.add(inferenceVariable);
            Iterator<InferenceVariable> it = map.get(inferenceVariable).iterator();
            while (it.hasNext()) {
                addConnected(set, it.next(), map, set2);
            }
        }
    }

    public boolean hasCaptureBound(Set<InferenceVariable> set) {
        Iterator<ParameterizedTypeBinding> it = this.captures.keySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                return false;
            }
            ParameterizedTypeBinding next = it.next();
            for (int i = 0; i < next.arguments.length; i++) {
                if (set.contains(next.arguments[i])) {
                    return true;
                }
            }
        }
    }

    public boolean hasOnlyTrivialExceptionBounds(InferenceVariable inferenceVariable, TypeBinding[] typeBindingArr) {
        if (typeBindingArr != null) {
            for (TypeBinding typeBinding : typeBindingArr) {
                int i = typeBinding.f626id;
                if (i != 1 && i != 21 && i != 25) {
                    return false;
                }
            }
        }
        return true;
    }

    public TypeBinding[] upperBounds(InferenceVariable inferenceVariable, boolean z) {
        ThreeSets threeSets = this.boundsPerVariable.get(inferenceVariable.prototype());
        if (threeSets == null || threeSets.subBounds == null) {
            return Binding.NO_TYPES;
        }
        return threeSets.upperBounds(z, inferenceVariable);
    }

    TypeBinding[] lowerBounds(InferenceVariable inferenceVariable, boolean z) {
        ThreeSets threeSets = this.boundsPerVariable.get(inferenceVariable.prototype());
        if (threeSets == null || threeSets.superBounds == null) {
            return Binding.NO_TYPES;
        }
        return threeSets.lowerBounds(z, inferenceVariable);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("Type Bounds:\n");
        for (TypeBound typeBound : flatten()) {
            stringBuffer.append('\t').append(typeBound.toString()).append('\n');
        }
        stringBuffer.append("Capture Bounds:\n");
        for (Map.Entry<ParameterizedTypeBinding, ParameterizedTypeBinding> entry : this.captures.entrySet()) {
            stringBuffer.append('\t').append(String.valueOf(entry.getKey().shortReadableName())).append(" = capt(").append(String.valueOf(entry.getValue().shortReadableName())).append(")\n");
        }
        return stringBuffer.toString();
    }

    public TypeBinding findWrapperTypeBound(InferenceVariable inferenceVariable) {
        ThreeSets threeSets = this.boundsPerVariable.get(inferenceVariable.prototype());
        if (threeSets == null) {
            return null;
        }
        return threeSets.findSingleWrapperType();
    }

    public boolean condition18_5_2_bullet_3_3_1(InferenceVariable inferenceVariable, TypeBinding typeBinding) {
        ThreeSets threeSets;
        if (typeBinding.isBaseType() || InferenceContext18.parameterizedWithWildcard(typeBinding) != null || (threeSets = this.boundsPerVariable.get(inferenceVariable.prototype())) == null) {
            return false;
        }
        if (threeSets.sameBounds != null) {
            Iterator<TypeBound> it = threeSets.sameBounds.iterator();
            while (it.hasNext()) {
                if (InferenceContext18.parameterizedWithWildcard(it.next().right) != null) {
                    return true;
                }
            }
        }
        if (threeSets.superBounds != null) {
            Iterator<TypeBound> it2 = threeSets.superBounds.iterator();
            while (it2.hasNext()) {
                if (InferenceContext18.parameterizedWithWildcard(it2.next().right) != null) {
                    return true;
                }
            }
        }
        if (threeSets.superBounds != null) {
            ArrayList arrayList = new ArrayList(threeSets.superBounds);
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                TypeBinding typeBinding2 = ((TypeBound) arrayList.get(i)).right;
                i++;
                for (int i2 = i; i2 < size; i2++) {
                    TypeBinding[] typeBindingArrSuperTypesWithCommonGenericType = superTypesWithCommonGenericType(typeBinding2, ((TypeBound) arrayList.get(i2)).right);
                    if (typeBindingArrSuperTypesWithCommonGenericType != null && typeBindingArrSuperTypesWithCommonGenericType[0].isProperType(true) && typeBindingArrSuperTypesWithCommonGenericType[1].isProperType(true) && !TypeBinding.equalsEquals(typeBindingArrSuperTypesWithCommonGenericType[0], typeBindingArrSuperTypesWithCommonGenericType[1])) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean condition18_5_2_bullet_3_3_2(InferenceVariable inferenceVariable, TypeBinding typeBinding, InferenceContext18 inferenceContext18) {
        if (!typeBinding.isParameterizedType()) {
            return false;
        }
        TypeBinding typeBindingOriginal = typeBinding.original();
        ThreeSets threeSets = this.boundsPerVariable.get(inferenceVariable.prototype());
        if (threeSets == null) {
            return false;
        }
        if (threeSets.sameBounds != null) {
            Iterator<TypeBound> it = threeSets.sameBounds.iterator();
            while (it.hasNext()) {
                if (superOnlyRaw(typeBindingOriginal, it.next().right, inferenceContext18.environment)) {
                    return true;
                }
            }
        }
        if (threeSets.superBounds != null) {
            Iterator<TypeBound> it2 = threeSets.superBounds.iterator();
            while (it2.hasNext()) {
                if (superOnlyRaw(typeBindingOriginal, it2.next().right, inferenceContext18.environment)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean superOnlyRaw(TypeBinding typeBinding, TypeBinding typeBinding2, LookupEnvironment lookupEnvironment) {
        TypeBinding typeBindingFindSuperTypeOriginatingFrom;
        if ((typeBinding2 instanceof InferenceVariable) || (typeBindingFindSuperTypeOriginatingFrom = typeBinding2.findSuperTypeOriginatingFrom(typeBinding)) == null || typeBindingFindSuperTypeOriginatingFrom.isParameterizedType()) {
            return false;
        }
        return typeBinding2.isCompatibleWith(lookupEnvironment.convertToRawType(typeBinding, false));
    }

    protected TypeBinding[] superTypesWithCommonGenericType(TypeBinding typeBinding, TypeBinding typeBinding2) {
        if (typeBinding != null && typeBinding.f626id != 1 && typeBinding2 != null && typeBinding2.f626id != 1) {
            if (TypeBinding.equalsEquals(typeBinding.original(), typeBinding2.original())) {
                return new TypeBinding[]{typeBinding, typeBinding2};
            }
            TypeBinding typeBindingFindSuperTypeOriginatingFrom = typeBinding2.findSuperTypeOriginatingFrom(typeBinding);
            if (typeBindingFindSuperTypeOriginatingFrom != null) {
                return new TypeBinding[]{typeBinding, typeBindingFindSuperTypeOriginatingFrom};
            }
            TypeBinding[] typeBindingArrSuperTypesWithCommonGenericType = superTypesWithCommonGenericType(typeBinding.superclass(), typeBinding2);
            if (typeBindingArrSuperTypesWithCommonGenericType != null) {
                return typeBindingArrSuperTypesWithCommonGenericType;
            }
            ReferenceBinding[] referenceBindingArrSuperInterfaces = typeBinding.superInterfaces();
            if (referenceBindingArrSuperInterfaces != null) {
                for (ReferenceBinding referenceBinding : referenceBindingArrSuperInterfaces) {
                    TypeBinding[] typeBindingArrSuperTypesWithCommonGenericType2 = superTypesWithCommonGenericType(referenceBinding, typeBinding2);
                    if (typeBindingArrSuperTypesWithCommonGenericType2 != null) {
                        return typeBindingArrSuperTypesWithCommonGenericType2;
                    }
                }
            }
        }
        return null;
    }

    public TypeBinding getEquivalentOuterVariable(InferenceVariable inferenceVariable, InferenceVariable[] inferenceVariableArr) {
        ThreeSets threeSets = this.boundsPerVariable.get(inferenceVariable);
        if (threeSets != null) {
            for (TypeBound typeBound : threeSets.sameBounds) {
                for (InferenceVariable inferenceVariable2 : inferenceVariableArr) {
                    if (TypeBinding.equalsEquals(typeBound.right, inferenceVariable2)) {
                        return inferenceVariable2;
                    }
                }
            }
        }
        for (InferenceVariable inferenceVariable3 : inferenceVariableArr) {
            ThreeSets threeSets2 = this.boundsPerVariable.get(inferenceVariableArr);
            if (threeSets2 != null) {
                Iterator<TypeBound> it = threeSets2.sameBounds.iterator();
                while (it.hasNext()) {
                    if (TypeBinding.equalsEquals(it.next().right, inferenceVariable)) {
                        return inferenceVariable3;
                    }
                }
            }
        }
        return null;
    }
}
