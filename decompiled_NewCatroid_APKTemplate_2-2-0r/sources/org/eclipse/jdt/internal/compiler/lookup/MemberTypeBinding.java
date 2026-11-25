package org.eclipse.jdt.internal.compiler.lookup;

import org.eclipse.jdt.core.compiler.CharOperation;

/* JADX INFO: loaded from: classes14.dex */
public final class MemberTypeBinding extends NestedTypeBinding {
    public MemberTypeBinding(char[][] cArr, ClassScope classScope, SourceTypeBinding sourceTypeBinding) {
        super(cArr, classScope, sourceTypeBinding);
        this.tagBits |= TagBits.MemberTypeMask;
    }

    public MemberTypeBinding(MemberTypeBinding memberTypeBinding) {
        super(memberTypeBinding);
    }

    void checkSyntheticArgsAndFields() {
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        if (isStatic() || isInterface()) {
            return;
        }
        if (!isPrototype()) {
            ((MemberTypeBinding) this.prototype).checkSyntheticArgsAndFields();
        } else {
            addSyntheticArgumentAndField(this.enclosingType);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] constantPoolName() {
        if (this.constantPoolName != null) {
            return this.constantPoolName;
        }
        if (!isPrototype()) {
            return this.prototype.constantPoolName();
        }
        char[] cArrConcat = CharOperation.concat(enclosingType().constantPoolName(), this.sourceName, '$');
        this.constantPoolName = cArrConcat;
        return cArrConcat;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding clone(TypeBinding typeBinding) {
        MemberTypeBinding memberTypeBinding = new MemberTypeBinding(this);
        memberTypeBinding.enclosingType = (SourceTypeBinding) typeBinding;
        return memberTypeBinding;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public void initializeDeprecatedAnnotationTagBits() {
        if (!isPrototype()) {
            this.prototype.initializeDeprecatedAnnotationTagBits();
            return;
        }
        if ((this.tagBits & 17179869184L) == 0) {
            super.initializeDeprecatedAnnotationTagBits();
            if ((this.tagBits & 70368744177664L) == 0) {
                ReferenceBinding referenceBindingEnclosingType = enclosingType();
                if ((referenceBindingEnclosingType.tagBits & 17179869184L) == 0) {
                    referenceBindingEnclosingType.initializeDeprecatedAnnotationTagBits();
                }
                if (referenceBindingEnclosingType.isViewedAsDeprecated()) {
                    this.modifiers |= 2097152;
                }
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding
    public String toString() {
        if (hasTypeAnnotations()) {
            return annotatedDebugName();
        }
        return "Member type : " + new String(sourceName()) + " " + super.toString();
    }
}
