package org.jetbrains.kotlin.codegen.context;

import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.OwnerKind;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.codegen.binding.CodegenBinding;
import org.jetbrains.kotlin.codegen.binding.MutableClosure;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.types.SimpleType;

/* JADX INFO: loaded from: classes3.dex */
public class ClassContext extends FieldOwnerContext<ClassDescriptor> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final KotlinTypeMapper typeMapper;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "contextDescriptor";
        } else if (i != 2) {
            objArr[0] = "typeMapper";
        } else {
            objArr[0] = "contextKind";
        }
        objArr[1] = "org/jetbrains/kotlin/codegen/context/ClassContext";
        objArr[2] = "<init>";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClassContext(KotlinTypeMapper kotlinTypeMapper, ClassDescriptor classDescriptor, OwnerKind ownerKind, CodegenContext codegenContext, LocalLookup localLookup) {
        super(classDescriptor, ownerKind, codegenContext, (MutableClosure) kotlinTypeMapper.getBindingContext().get(CodegenBinding.CLOSURE, classDescriptor), classDescriptor, localLookup);
        if (kotlinTypeMapper == null) {
            $$$reportNull$$$0(0);
        }
        if (classDescriptor == null) {
            $$$reportNull$$$0(1);
        }
        if (ownerKind == null) {
            $$$reportNull$$$0(2);
        }
        OwnerKind ownerKind2 = OwnerKind.ERASED_INLINE_CLASS;
        this.typeMapper = kotlinTypeMapper;
    }

    @Override // org.jetbrains.kotlin.codegen.context.CodegenContext
    protected StackValue.Field computeOuterExpression() {
        ClassDescriptor enclosingClass = getEnclosingClass();
        if (enclosingClass == null || !CodegenBinding.canHaveOuter(this.typeMapper.getBindingContext(), (ClassDescriptor) getContextDescriptor())) {
            return null;
        }
        SimpleType defaultType = enclosingClass.getDefaultType();
        return StackValue.field(this.typeMapper.mapType(defaultType), defaultType, this.typeMapper.mapType((ClassifierDescriptor) getContextDescriptor()), AsmUtil.CAPTURED_THIS_FIELD, false, StackValue.LOCAL_0, enclosingClass);
    }

    public CodegenContext getCompanionObjectContext() {
        if (((ClassDescriptor) getContextDescriptor()).mo6346getCompanionObjectDescriptor() != null) {
            return findChildContext(((ClassDescriptor) getContextDescriptor()).mo6346getCompanionObjectDescriptor());
        }
        return null;
    }

    public String toString() {
        return "Class: " + getContextDescriptor();
    }
}
