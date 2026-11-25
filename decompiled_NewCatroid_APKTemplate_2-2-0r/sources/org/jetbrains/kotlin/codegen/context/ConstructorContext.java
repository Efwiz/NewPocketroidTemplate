package org.jetbrains.kotlin.codegen.context;

import org.jetbrains.kotlin.codegen.OwnerKind;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.codegen.binding.MutableClosure;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;

/* JADX INFO: loaded from: classes3.dex */
public class ConstructorContext extends MethodContext {
    private final KotlinTypeMapper kotlinTypeMapper;
    private boolean thisInitialized;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = Namer.METADATA_CLASS_KIND;
        } else if (i == 2) {
            objArr[0] = "parent";
        } else if (i != 3) {
            objArr[0] = "contextDescriptor";
        } else {
            objArr[0] = "kotlinTypeMapper";
        }
        objArr[1] = "org/jetbrains/kotlin/codegen/context/ConstructorContext";
        objArr[2] = "<init>";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConstructorContext(ConstructorDescriptor constructorDescriptor, OwnerKind ownerKind, CodegenContext codegenContext, MutableClosure mutableClosure, KotlinTypeMapper kotlinTypeMapper) {
        super(constructorDescriptor, ownerKind, codegenContext, mutableClosure, false);
        if (constructorDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        if (ownerKind == null) {
            $$$reportNull$$$0(1);
        }
        if (codegenContext == null) {
            $$$reportNull$$$0(2);
        }
        if (kotlinTypeMapper == null) {
            $$$reportNull$$$0(3);
        }
        this.thisInitialized = false;
        this.kotlinTypeMapper = kotlinTypeMapper;
    }

    @Override // org.jetbrains.kotlin.codegen.context.MethodContext, org.jetbrains.kotlin.codegen.context.CodegenContext
    public StackValue getOuterExpression(StackValue stackValue, boolean z) {
        StackValue.Local local = null;
        ClassDescriptor capturedOuterClassDescriptor = this.closure != null ? this.closure.getCapturedOuterClassDescriptor() : null;
        if (capturedOuterClassDescriptor != null) {
            if (InlineClassesUtilsKt.isInlineClass(capturedOuterClassDescriptor)) {
                local = StackValue.local(1, this.kotlinTypeMapper.mapType(capturedOuterClassDescriptor), capturedOuterClassDescriptor.getDefaultType());
            } else {
                local = StackValue.local(1, AsmTypes.OBJECT_TYPE);
            }
        }
        if (z || local != null) {
            return local;
        }
        throw new UnsupportedOperationException("Don't know how to generate outer expression for " + getContextDescriptor());
    }

    public ConstructorDescriptor getConstructorDescriptor() {
        return (ConstructorDescriptor) getContextDescriptor();
    }

    public boolean isThisInitialized() {
        return this.thisInitialized;
    }

    public void setThisInitialized(boolean z) {
        this.thisInitialized = z;
    }

    @Override // org.jetbrains.kotlin.codegen.context.CodegenContext
    public boolean isContextWithUninitializedThis() {
        return !isThisInitialized();
    }

    @Override // org.jetbrains.kotlin.codegen.context.MethodContext
    public String toString() {
        return "Constructor: " + (isThisInitialized() ? "" : "UNINITIALIZED ") + getContextDescriptor();
    }
}
