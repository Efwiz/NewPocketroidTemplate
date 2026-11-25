package org.jetbrains.kotlin.codegen.context;

import org.jetbrains.kotlin.codegen.OwnerKind;
import org.jetbrains.kotlin.codegen.binding.CodegenBinding;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;

/* JADX INFO: loaded from: classes3.dex */
public class ClosureContext extends ClassContext {
    private final FunctionDescriptor functionDescriptor;
    private final FunctionDescriptor originalSuspendLambdaDescriptor;

    /* JADX WARN: Code duplicated, block: B:23:0x0032  */
    /* JADX WARN: Code duplicated, block: B:24:0x0035  */
    /* JADX WARN: Code duplicated, block: B:25:0x003a  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 5 || i == 9) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 5 || i == 9) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "functionDescriptor";
        } else if (i == 2) {
            objArr[0] = "localLookup";
        } else if (i == 4) {
            objArr[0] = "functionDescriptor";
        } else if (i == 5) {
            objArr[0] = "org/jetbrains/kotlin/codegen/context/ClosureContext";
        } else if (i == 7) {
            objArr[0] = "functionDescriptor";
        } else if (i == 8) {
            objArr[0] = "localLookup";
        } else if (i != 9) {
            objArr[0] = "typeMapper";
        } else {
            objArr[0] = "org/jetbrains/kotlin/codegen/context/ClosureContext";
        }
        if (i == 5) {
            objArr[1] = "getClassForCallable";
        } else if (i != 9) {
            objArr[1] = "org/jetbrains/kotlin/codegen/context/ClosureContext";
        } else {
            objArr[1] = "getFunctionDescriptor";
        }
        if (i == 3 || i == 4) {
            objArr[2] = "getClassForCallable";
        } else if (i != 5 && i != 9) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 5 && i != 9) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClosureContext(KotlinTypeMapper kotlinTypeMapper, FunctionDescriptor functionDescriptor, CodegenContext codegenContext, LocalLookup localLookup, FunctionDescriptor functionDescriptor2) {
        super(kotlinTypeMapper, getClassForCallable(kotlinTypeMapper, functionDescriptor, functionDescriptor2), OwnerKind.IMPLEMENTATION, codegenContext, localLookup);
        if (kotlinTypeMapper == null) {
            $$$reportNull$$$0(0);
        }
        if (functionDescriptor == null) {
            $$$reportNull$$$0(1);
        }
        if (localLookup == null) {
            $$$reportNull$$$0(2);
        }
        this.functionDescriptor = functionDescriptor;
        this.originalSuspendLambdaDescriptor = functionDescriptor2;
    }

    private static ClassDescriptor getClassForCallable(KotlinTypeMapper kotlinTypeMapper, FunctionDescriptor functionDescriptor, FunctionDescriptor functionDescriptor2) {
        if (kotlinTypeMapper == null) {
            $$$reportNull$$$0(3);
        }
        if (functionDescriptor == null) {
            $$$reportNull$$$0(4);
        }
        if (functionDescriptor2 == null) {
            functionDescriptor2 = functionDescriptor;
        }
        ClassDescriptor classDescriptor = (ClassDescriptor) kotlinTypeMapper.getBindingContext().get(CodegenBinding.CLASS_FOR_CALLABLE, functionDescriptor2);
        if (classDescriptor == null) {
            throw new IllegalStateException("Class for callable is not found: " + functionDescriptor);
        }
        if (classDescriptor == null) {
            $$$reportNull$$$0(5);
        }
        return classDescriptor;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ClosureContext(KotlinTypeMapper kotlinTypeMapper, FunctionDescriptor functionDescriptor, CodegenContext codegenContext, LocalLookup localLookup) {
        this(kotlinTypeMapper, functionDescriptor, codegenContext, localLookup, null);
        if (kotlinTypeMapper == null) {
            $$$reportNull$$$0(6);
        }
        if (functionDescriptor == null) {
            $$$reportNull$$$0(7);
        }
        if (localLookup == null) {
            $$$reportNull$$$0(8);
        }
    }

    public FunctionDescriptor getFunctionDescriptor() {
        FunctionDescriptor functionDescriptor = this.functionDescriptor;
        if (functionDescriptor == null) {
            $$$reportNull$$$0(9);
        }
        return functionDescriptor;
    }

    @Override // org.jetbrains.kotlin.codegen.context.ClassContext
    public String toString() {
        return "Closure: " + getContextDescriptor();
    }

    public FunctionDescriptor getOriginalSuspendLambdaDescriptor() {
        return this.originalSuspendLambdaDescriptor;
    }
}
