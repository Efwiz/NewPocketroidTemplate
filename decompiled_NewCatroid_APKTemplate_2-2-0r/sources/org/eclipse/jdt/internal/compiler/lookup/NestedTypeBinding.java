package org.eclipse.jdt.internal.compiler.lookup;

import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.ast.Annotation;

/* JADX INFO: loaded from: classes14.dex */
public abstract class NestedTypeBinding extends SourceTypeBinding {
    public SyntheticArgumentBinding[] enclosingInstances;
    public SourceTypeBinding enclosingType;
    private ReferenceBinding[] enclosingTypes;
    public SyntheticArgumentBinding[] outerLocalVariables;
    private int outerLocalVariablesSlotSize;

    public void updateInnerEmulationDependents() {
    }

    public NestedTypeBinding(char[][] cArr, ClassScope classScope, SourceTypeBinding sourceTypeBinding) {
        super(cArr, sourceTypeBinding.fPackage, classScope);
        this.enclosingTypes = Binding.UNINITIALIZED_REFERENCE_TYPES;
        this.outerLocalVariablesSlotSize = -1;
        this.tagBits |= 2052;
        this.enclosingType = sourceTypeBinding;
    }

    public NestedTypeBinding(NestedTypeBinding nestedTypeBinding) {
        super(nestedTypeBinding);
        this.enclosingTypes = Binding.UNINITIALIZED_REFERENCE_TYPES;
        this.outerLocalVariablesSlotSize = -1;
        this.enclosingType = nestedTypeBinding.enclosingType;
        this.enclosingInstances = nestedTypeBinding.enclosingInstances;
        this.enclosingTypes = nestedTypeBinding.enclosingTypes;
        this.outerLocalVariables = nestedTypeBinding.outerLocalVariables;
        this.outerLocalVariablesSlotSize = nestedTypeBinding.outerLocalVariablesSlotSize;
    }

    public SyntheticArgumentBinding addSyntheticArgument(LocalVariableBinding localVariableBinding) {
        SyntheticArgumentBinding syntheticArgumentBinding;
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        SyntheticArgumentBinding[] syntheticArgumentBindingArr = this.outerLocalVariables;
        if (syntheticArgumentBindingArr == null) {
            syntheticArgumentBinding = new SyntheticArgumentBinding(localVariableBinding);
            this.outerLocalVariables = new SyntheticArgumentBinding[]{syntheticArgumentBinding};
        } else {
            int length = syntheticArgumentBindingArr.length;
            int i = length;
            loop0: while (true) {
                int i2 = i;
                do {
                    i--;
                    if (i >= 0) {
                        if (this.outerLocalVariables[i].actualOuterLocalVariable == localVariableBinding) {
                            return this.outerLocalVariables[i];
                        }
                    } else {
                        SyntheticArgumentBinding[] syntheticArgumentBindingArr2 = new SyntheticArgumentBinding[length + 1];
                        System.arraycopy(this.outerLocalVariables, 0, syntheticArgumentBindingArr2, 0, i2);
                        SyntheticArgumentBinding syntheticArgumentBinding2 = new SyntheticArgumentBinding(localVariableBinding);
                        syntheticArgumentBindingArr2[i2] = syntheticArgumentBinding2;
                        System.arraycopy(this.outerLocalVariables, i2, syntheticArgumentBindingArr2, i2 + 1, length - i2);
                        this.outerLocalVariables = syntheticArgumentBindingArr2;
                        syntheticArgumentBinding = syntheticArgumentBinding2;
                        break loop0;
                    }
                } while (this.outerLocalVariables[i].f627id <= localVariableBinding.f627id);
            }
        }
        if (this.scope.referenceCompilationUnit().isPropagatingInnerClassEmulation) {
            updateInnerEmulationDependents();
        }
        return syntheticArgumentBinding;
    }

    public SyntheticArgumentBinding addSyntheticArgument(ReferenceBinding referenceBinding) {
        SyntheticArgumentBinding syntheticArgumentBinding;
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        SyntheticArgumentBinding[] syntheticArgumentBindingArr = this.enclosingInstances;
        if (syntheticArgumentBindingArr == null) {
            syntheticArgumentBinding = new SyntheticArgumentBinding(referenceBinding);
            this.enclosingInstances = new SyntheticArgumentBinding[]{syntheticArgumentBinding};
        } else {
            int length = syntheticArgumentBindingArr.length;
            int i = TypeBinding.equalsEquals(enclosingType(), referenceBinding) ? 0 : length;
            SyntheticArgumentBinding[] syntheticArgumentBindingArr2 = new SyntheticArgumentBinding[length + 1];
            System.arraycopy(this.enclosingInstances, 0, syntheticArgumentBindingArr2, i != 0 ? 0 : 1, length);
            syntheticArgumentBinding = new SyntheticArgumentBinding(referenceBinding);
            syntheticArgumentBindingArr2[i] = syntheticArgumentBinding;
            this.enclosingInstances = syntheticArgumentBindingArr2;
        }
        if (this.scope.referenceCompilationUnit().isPropagatingInnerClassEmulation) {
            updateInnerEmulationDependents();
        }
        return syntheticArgumentBinding;
    }

    public SyntheticArgumentBinding addSyntheticArgumentAndField(LocalVariableBinding localVariableBinding) {
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        SyntheticArgumentBinding syntheticArgumentBindingAddSyntheticArgument = addSyntheticArgument(localVariableBinding);
        if (syntheticArgumentBindingAddSyntheticArgument == null) {
            return null;
        }
        if (syntheticArgumentBindingAddSyntheticArgument.matchingField == null) {
            syntheticArgumentBindingAddSyntheticArgument.matchingField = addSyntheticFieldForInnerclass(localVariableBinding);
        }
        return syntheticArgumentBindingAddSyntheticArgument;
    }

    public SyntheticArgumentBinding addSyntheticArgumentAndField(ReferenceBinding referenceBinding) {
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        SyntheticArgumentBinding syntheticArgumentBindingAddSyntheticArgument = addSyntheticArgument(referenceBinding);
        if (syntheticArgumentBindingAddSyntheticArgument == null) {
            return null;
        }
        if (syntheticArgumentBindingAddSyntheticArgument.matchingField == null) {
            syntheticArgumentBindingAddSyntheticArgument.matchingField = addSyntheticFieldForInnerclass(referenceBinding);
        }
        return syntheticArgumentBindingAddSyntheticArgument;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding
    protected void checkRedundantNullnessDefaultRecurse(ASTNode aSTNode, Annotation[] annotationArr, long j, boolean z) {
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        TypeBinding typeBindingEnclosingType = this.enclosingType;
        while (((SourceTypeBinding) typeBindingEnclosingType).checkRedundantNullnessDefaultOne(aSTNode, annotationArr, j, z)) {
            typeBindingEnclosingType = typeBindingEnclosingType.enclosingType();
            if (!(typeBindingEnclosingType instanceof SourceTypeBinding)) {
                super.checkRedundantNullnessDefaultRecurse(aSTNode, annotationArr, j, z);
                return;
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public ReferenceBinding enclosingType() {
        return this.enclosingType;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public int getEnclosingInstancesSlotSize() {
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        SyntheticArgumentBinding[] syntheticArgumentBindingArr = this.enclosingInstances;
        if (syntheticArgumentBindingArr == null) {
            return 0;
        }
        return syntheticArgumentBindingArr.length;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public int getOuterLocalVariablesSlotSize() {
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        if (this.outerLocalVariablesSlotSize < 0) {
            this.outerLocalVariablesSlotSize = 0;
            SyntheticArgumentBinding[] syntheticArgumentBindingArr = this.outerLocalVariables;
            int length = syntheticArgumentBindingArr == null ? 0 : syntheticArgumentBindingArr.length;
            for (int i = 0; i < length; i++) {
                int i2 = this.outerLocalVariables[i].type.f626id;
                if (i2 == 7 || i2 == 8) {
                    this.outerLocalVariablesSlotSize += 2;
                } else {
                    this.outerLocalVariablesSlotSize++;
                }
            }
        }
        return this.outerLocalVariablesSlotSize;
    }

    public SyntheticArgumentBinding getSyntheticArgument(LocalVariableBinding localVariableBinding) {
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        SyntheticArgumentBinding[] syntheticArgumentBindingArr = this.outerLocalVariables;
        if (syntheticArgumentBindingArr == null) {
            return null;
        }
        int length = syntheticArgumentBindingArr.length;
        do {
            length--;
            if (length < 0) {
                return null;
            }
        } while (this.outerLocalVariables[length].actualOuterLocalVariable != localVariableBinding);
        return this.outerLocalVariables[length];
    }

    public SyntheticArgumentBinding getSyntheticArgument(ReferenceBinding referenceBinding, boolean z, boolean z2) {
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        SyntheticArgumentBinding[] syntheticArgumentBindingArr = this.enclosingInstances;
        if (syntheticArgumentBindingArr == null) {
            return null;
        }
        if (z2 && syntheticArgumentBindingArr.length > 0 && TypeBinding.equalsEquals(syntheticArgumentBindingArr[0].type, referenceBinding) && this.enclosingInstances[0].actualOuterLocalVariable == null) {
            return this.enclosingInstances[0];
        }
        int length = this.enclosingInstances.length;
        while (true) {
            length--;
            if (length < 0) {
                if (!z) {
                    int length2 = this.enclosingInstances.length;
                    while (true) {
                        length2--;
                        if (length2 < 0) {
                            break;
                        }
                        if (this.enclosingInstances[length2].actualOuterLocalVariable == null && this.enclosingInstances[length2].type.findSuperTypeOriginatingFrom(referenceBinding) != null) {
                            return this.enclosingInstances[length2];
                        }
                    }
                }
                return null;
            }
            if (TypeBinding.equalsEquals(this.enclosingInstances[length].type, referenceBinding) && this.enclosingInstances[length].actualOuterLocalVariable == null) {
                return this.enclosingInstances[length];
            }
        }
    }

    public SyntheticArgumentBinding[] syntheticEnclosingInstances() {
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        return this.enclosingInstances;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public ReferenceBinding[] syntheticEnclosingInstanceTypes() {
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        if (this.enclosingTypes == UNINITIALIZED_REFERENCE_TYPES) {
            SyntheticArgumentBinding[] syntheticArgumentBindingArr = this.enclosingInstances;
            if (syntheticArgumentBindingArr == null) {
                this.enclosingTypes = null;
            } else {
                int length = syntheticArgumentBindingArr.length;
                this.enclosingTypes = new ReferenceBinding[length];
                for (int i = 0; i < length; i++) {
                    this.enclosingTypes[i] = (ReferenceBinding) this.enclosingInstances[i].type;
                }
            }
        }
        return this.enclosingTypes;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public SyntheticArgumentBinding[] syntheticOuterLocalVariables() {
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        return this.outerLocalVariables;
    }
}
