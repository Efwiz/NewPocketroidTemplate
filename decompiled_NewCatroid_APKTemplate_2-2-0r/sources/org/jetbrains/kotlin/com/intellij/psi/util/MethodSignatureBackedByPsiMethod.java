package org.jetbrains.kotlin.com.intellij.psi.util;

import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.diagnostic.PluginException;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;

/* JADX INFO: loaded from: classes3.dex */
public class MethodSignatureBackedByPsiMethod extends MethodSignatureBase {
    private final boolean myIsRaw;
    private final PsiMethod myMethod;
    private final String myName;

    /* JADX WARN: Code duplicated, block: B:26:0x003d  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 5) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "substitutor";
        } else if (i == 2) {
            objArr[0] = "parameterTypes";
        } else if (i == 3) {
            objArr[0] = "methodTypeParameters";
        } else if (i == 4 || i == 5) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/util/MethodSignatureBackedByPsiMethod";
        } else if (i == 7 || i == 9) {
            objArr[0] = "substitutor";
        } else {
            objArr[0] = "method";
        }
        if (i == 4) {
            objArr[1] = "getName";
        } else if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/util/MethodSignatureBackedByPsiMethod";
        } else {
            objArr[1] = "getMethod";
        }
        switch (i) {
            case 4:
            case 5:
                break;
            case 6:
            case 7:
            case 8:
            case 9:
                objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected MethodSignatureBackedByPsiMethod(PsiMethod psiMethod, PsiSubstitutor psiSubstitutor, boolean z, PsiType[] psiTypeArr, PsiTypeParameter[] psiTypeParameterArr) {
        super(psiSubstitutor, psiTypeArr, psiTypeParameterArr);
        if (psiMethod == null) {
            $$$reportNull$$$0(0);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(1);
        }
        if (psiTypeArr == null) {
            $$$reportNull$$$0(2);
        }
        if (psiTypeParameterArr == null) {
            $$$reportNull$$$0(3);
        }
        this.myIsRaw = z;
        this.myMethod = psiMethod;
        this.myName = psiMethod.getName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.util.MethodSignature
    public String getName() {
        String str = this.myName;
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        return str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.util.MethodSignature
    public boolean isRaw() {
        return this.myIsRaw;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.util.MethodSignature
    public boolean isConstructor() {
        return this.myMethod.isConstructor();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.util.MethodSignatureBase
    public boolean equals(Object obj) {
        if ((obj instanceof MethodSignatureBackedByPsiMethod) && ((MethodSignatureBackedByPsiMethod) obj).myMethod == this.myMethod) {
            return true;
        }
        return super.equals(obj);
    }

    public PsiMethod getMethod() {
        PsiMethod psiMethod = this.myMethod;
        if (psiMethod == null) {
            $$$reportNull$$$0(5);
        }
        return psiMethod;
    }

    public static MethodSignatureBackedByPsiMethod create(PsiMethod psiMethod, PsiSubstitutor psiSubstitutor) {
        if (psiMethod == null) {
            $$$reportNull$$$0(6);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(7);
        }
        return create(psiMethod, psiSubstitutor, PsiUtil.isRawSubstitutor(psiMethod, psiSubstitutor));
    }

    public static MethodSignatureBackedByPsiMethod create(PsiMethod psiMethod, PsiSubstitutor psiSubstitutor, boolean z) {
        if (psiMethod == null) {
            $$$reportNull$$$0(8);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(9);
        }
        PsiTypeParameter[] typeParameters = psiMethod.getTypeParameters();
        if (z) {
            psiSubstitutor = JavaPsiFacade.getElementFactory(psiMethod.getProject()).createRawSubstitutor(psiSubstitutor, typeParameters);
            typeParameters = PsiTypeParameter.EMPTY_ARRAY;
        }
        PsiSubstitutor psiSubstitutor2 = psiSubstitutor;
        PsiTypeParameter[] psiTypeParameterArr = typeParameters;
        try {
            psiSubstitutor2.ensureValid();
            PsiParameter[] parameters = psiMethod.getParameterList().getParameters();
            PsiType[] psiTypeArrCreateArray = PsiType.createArray(parameters.length);
            for (int i = 0; i < psiTypeArrCreateArray.length; i++) {
                PsiType type = parameters[i].getType();
                if (z) {
                    type = TypeConversionUtil.erasure(psiSubstitutor2.substitute(type));
                }
                psiTypeArrCreateArray[i] = type;
            }
            return new MethodSignatureBackedByPsiMethod(psiMethod, psiSubstitutor2, z, psiTypeArrCreateArray, psiTypeParameterArr);
        } catch (ProcessCanceledException e) {
            throw e;
        } catch (Throwable th) {
            throw PluginException.createByClass(th, psiMethod.getClass());
        }
    }
}
