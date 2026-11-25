package org.jetbrains.kotlin.codegen.intrinsics;

import org.jetbrains.kotlin.codegen.Callable;
import org.jetbrains.kotlin.codegen.CallableMethod;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: loaded from: classes3.dex */
public abstract class IntrinsicMethod {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 7 || i == 9) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 7 || i == 9) ? 2 : 3];
        switch (i) {
            case 1:
            case 6:
                objArr[0] = "resolvedCall";
                break;
            case 2:
                objArr[0] = "codegen";
                break;
            case 3:
            case 7:
            case 9:
                objArr[0] = "org/jetbrains/kotlin/codegen/intrinsics/IntrinsicMethod";
                break;
            case 4:
                objArr[0] = "descriptor";
                break;
            case 5:
            case 8:
            case 10:
                objArr[0] = "method";
                break;
            default:
                objArr[0] = "fd";
                break;
        }
        if (i == 3 || i == 7 || i == 9) {
            objArr[1] = "toCallable";
        } else {
            objArr[1] = "org/jetbrains/kotlin/codegen/intrinsics/IntrinsicMethod";
        }
        if (i != 3) {
            if (i == 4) {
                objArr[2] = "isApplicableToOverload";
            } else if (i != 7 && i != 9) {
                objArr[2] = "toCallable";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 7 && i != 9) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public boolean isApplicableToOverload(CallableMemberDescriptor callableMemberDescriptor) {
        if (callableMemberDescriptor != null) {
            return true;
        }
        $$$reportNull$$$0(4);
        return true;
    }

    public Type nullOr(Type type, Type type2) {
        if (type == null) {
            return null;
        }
        return type2;
    }

    public Callable toCallable(FunctionDescriptor functionDescriptor, boolean z, ResolvedCall resolvedCall, ExpressionCodegen expressionCodegen) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        if (resolvedCall == null) {
            $$$reportNull$$$0(1);
        }
        if (expressionCodegen == null) {
            $$$reportNull$$$0(2);
        }
        Callable callable = toCallable(expressionCodegen.getState().getTypeMapper().mapToCallableMethod(functionDescriptor, false), z, resolvedCall);
        if (callable == null) {
            $$$reportNull$$$0(3);
        }
        return callable;
    }

    protected Callable toCallable(CallableMethod callableMethod, boolean z, ResolvedCall resolvedCall) {
        if (callableMethod == null) {
            $$$reportNull$$$0(5);
        }
        if (resolvedCall == null) {
            $$$reportNull$$$0(6);
        }
        Callable callable = toCallable(callableMethod, z);
        if (callable == null) {
            $$$reportNull$$$0(7);
        }
        return callable;
    }

    protected Callable toCallable(CallableMethod callableMethod, boolean z) {
        if (callableMethod == null) {
            $$$reportNull$$$0(8);
        }
        Callable callable = toCallable(callableMethod);
        if (callable == null) {
            $$$reportNull$$$0(9);
        }
        return callable;
    }

    protected Callable toCallable(CallableMethod callableMethod) {
        if (callableMethod == null) {
            $$$reportNull$$$0(10);
        }
        throw new UnsupportedOperationException("Not implemented");
    }

    public Type nullOrObject(Type type) {
        return nullOr(type, AsmTypes.OBJECT_TYPE);
    }
}
