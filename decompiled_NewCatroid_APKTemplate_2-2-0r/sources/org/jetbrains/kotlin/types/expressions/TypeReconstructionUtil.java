package org.jetbrains.kotlin.types.expressions;

import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.PossiblyBareType;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeReconstructionResult;
import org.jetbrains.kotlin.types.error.ErrorType;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;

/* JADX INFO: loaded from: classes6.dex */
public class TypeReconstructionUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 5 || i == 8) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 5 || i == 8) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "possiblyBareTarget";
                break;
            case 2:
                objArr[0] = "trace";
                break;
            case 3:
                objArr[0] = "builtIns";
                break;
            case 4:
            case 5:
            case 8:
                objArr[0] = "org/jetbrains/kotlin/types/expressions/TypeReconstructionUtil";
                break;
            case 6:
                objArr[0] = JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME;
                break;
            case 7:
                objArr[0] = "name";
                break;
            default:
                objArr[0] = "right";
                break;
        }
        if (i == 4 || i == 5) {
            objArr[1] = "reconstructBareType";
        } else if (i != 8) {
            objArr[1] = "org/jetbrains/kotlin/types/expressions/TypeReconstructionUtil";
        } else {
            objArr[1] = "getTypeNameAndStarProjectionsString";
        }
        switch (i) {
            case 4:
            case 5:
            case 8:
                break;
            case 6:
                objArr[2] = "allStarProjectionsString";
                break;
            case 7:
                objArr[2] = "getTypeNameAndStarProjectionsString";
                break;
            default:
                objArr[2] = "reconstructBareType";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 5 && i != 8) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static KotlinType reconstructBareType(KtTypeReference ktTypeReference, PossiblyBareType possiblyBareType, KotlinType kotlinType, BindingTrace bindingTrace, KotlinBuiltIns kotlinBuiltIns) {
        if (ktTypeReference == null) {
            $$$reportNull$$$0(0);
        }
        if (possiblyBareType == null) {
            $$$reportNull$$$0(1);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(2);
        }
        if (kotlinBuiltIns == null) {
            $$$reportNull$$$0(3);
        }
        if (kotlinType == null) {
            kotlinType = kotlinBuiltIns.getAnyType();
        }
        TypeReconstructionResult typeReconstructionResultReconstruct = possiblyBareType.reconstruct(kotlinType);
        if (!typeReconstructionResultReconstruct.isAllArgumentsInferred()) {
            TypeConstructor bareTypeConstructor = possiblyBareType.getBareTypeConstructor();
            bindingTrace.report(Errors.NO_TYPE_ARGUMENTS_ON_RHS.on(ktTypeReference, Integer.valueOf(bareTypeConstructor.getParameters().size()), allStarProjectionsString(bareTypeConstructor)));
        }
        KotlinType resultingType = typeReconstructionResultReconstruct.getResultingType();
        if (resultingType != null) {
            if (possiblyBareType.isBare()) {
                bindingTrace.record(BindingContext.TYPE, ktTypeReference, resultingType);
            }
            if (resultingType == null) {
                $$$reportNull$$$0(4);
            }
            return resultingType;
        }
        ErrorType errorTypeCreateErrorType = ErrorUtils.createErrorType(ErrorTypeKind.ERROR_WHILE_RECONSTRUCTING_BARE_TYPE, ktTypeReference.getText());
        if (errorTypeCreateErrorType == null) {
            $$$reportNull$$$0(5);
        }
        return errorTypeCreateErrorType;
    }

    private static String allStarProjectionsString(TypeConstructor typeConstructor) {
        if (typeConstructor == null) {
            $$$reportNull$$$0(6);
        }
        return getTypeNameAndStarProjectionsString(typeConstructor.getThis$0().getShortName().asString(), typeConstructor.getParameters().size());
    }

    public static String getTypeNameAndStarProjectionsString(String str, int i) {
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        StringBuilder sb = new StringBuilder(str);
        sb.append("<");
        for (int i2 = 0; i2 < i; i2++) {
            sb.append("*");
            if (i2 == i - 1) {
                break;
            }
            sb.append(", ");
        }
        sb.append(">");
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(8);
        }
        return string;
    }
}
