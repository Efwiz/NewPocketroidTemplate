package org.jetbrains.kotlin.com.intellij.psi;

import io.netty.util.internal.StringUtil;
import okhttp3.HttpUrl;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.com.intellij.lang.jvm.types.JvmArrayType;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;

/* JADX INFO: loaded from: classes3.dex */
public class PsiArrayType extends PsiType.Stub implements JvmArrayType {
    private final PsiType myComponentType;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 5 || i == 6 || i == 7 || i == 12 || i == 13) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 5 || i == 6 || i == 7 || i == 12 || i == 13) ? 2 : 3];
        switch (i) {
            case 2:
                objArr[0] = "annotations";
                break;
            case 3:
            default:
                objArr[0] = "componentType";
                break;
            case 4:
                objArr[0] = Constants.EXTRA_PROVIDER;
                break;
            case 5:
            case 6:
            case 7:
            case 12:
            case 13:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiArrayType";
                break;
            case 8:
                objArr[0] = "prefix";
                break;
            case 9:
                objArr[0] = "suffix";
                break;
            case 10:
                objArr[0] = "text";
                break;
            case 11:
                objArr[0] = "visitor";
                break;
        }
        if (i == 5) {
            objArr[1] = "getPresentableText";
        } else if (i == 6) {
            objArr[1] = "getCanonicalText";
        } else if (i == 7) {
            objArr[1] = "getInternalCanonicalText";
        } else if (i == 12) {
            objArr[1] = "getSuperTypes";
        } else if (i != 13) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiArrayType";
        } else {
            objArr[1] = "getComponentType";
        }
        switch (i) {
            case 5:
            case 6:
            case 7:
            case 12:
            case 13:
                break;
            case 8:
            case 9:
                objArr[2] = "getText";
                break;
            case 10:
                objArr[2] = "equalsToText";
                break;
            case 11:
                objArr[2] = "accept";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 5 && i != 6 && i != 7 && i != 12 && i != 13) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public PsiArrayType(PsiType psiType) {
        this(psiType, TypeAnnotationProvider.EMPTY);
        if (psiType == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiArrayType(PsiType psiType, PsiAnnotation[] psiAnnotationArr) {
        super(psiAnnotationArr);
        if (psiType == null) {
            $$$reportNull$$$0(1);
        }
        if (psiAnnotationArr == null) {
            $$$reportNull$$$0(2);
        }
        this.myComponentType = psiType;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiArrayType(PsiType psiType, TypeAnnotationProvider typeAnnotationProvider) {
        super(typeAnnotationProvider);
        if (psiType == null) {
            $$$reportNull$$$0(3);
        }
        if (typeAnnotationProvider == null) {
            $$$reportNull$$$0(4);
        }
        this.myComponentType = psiType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType.Stub, org.jetbrains.kotlin.com.intellij.psi.PsiType
    public String getPresentableText(boolean z) {
        String text = getText(getDeepComponentType().getPresentableText(z), HttpUrl.PATH_SEGMENT_ENCODE_SET_URI, false, z);
        if (text == null) {
            $$$reportNull$$$0(5);
        }
        return text;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType.Stub, org.jetbrains.kotlin.com.intellij.psi.PsiType
    public String getCanonicalText(boolean z) {
        String text = getText(getDeepComponentType().getCanonicalText(z), HttpUrl.PATH_SEGMENT_ENCODE_SET_URI, true, z);
        if (text == null) {
            $$$reportNull$$$0(6);
        }
        return text;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public String getInternalCanonicalText() {
        String text = getText(getDeepComponentType().getInternalCanonicalText(), HttpUrl.PATH_SEGMENT_ENCODE_SET_URI, true, true);
        if (text == null) {
            $$$reportNull$$$0(7);
        }
        return text;
    }

    protected String getText(String str, String str2, boolean z, boolean z2) {
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        if (str2 == null) {
            $$$reportNull$$$0(9);
        }
        int arrayDimensions = getArrayDimensions();
        int i = arrayDimensions - 1;
        StringBuilder sb = new StringBuilder(str.length() + (i * 2) + str2.length());
        sb.append(str);
        PsiType componentType = this;
        for (int i2 = 0; i2 < arrayDimensions; i2++) {
            if (z2) {
                PsiAnnotation[] annotations = componentType.getAnnotations();
                if (annotations.length != 0) {
                    sb.append(StringUtil.SPACE);
                    PsiNameHelper.appendAnnotations(sb, annotations, z);
                }
            }
            if (i2 == i) {
                sb.append(str2);
            } else {
                sb.append(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
                componentType = ((PsiArrayType) componentType).getComponentType();
            }
        }
        return sb.toString();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public boolean isValid() {
        for (PsiAnnotation psiAnnotation : getAnnotations()) {
            if (!psiAnnotation.isValid()) {
                return false;
            }
        }
        return this.myComponentType.isValid();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public boolean equalsToText(String str) {
        if (str == null) {
            $$$reportNull$$$0(10);
        }
        return str.endsWith(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI) && this.myComponentType.equalsToText(str.substring(0, str.length() + (-2)));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public <A> A accept(PsiTypeVisitor<A> psiTypeVisitor) {
        if (psiTypeVisitor == null) {
            $$$reportNull$$$0(11);
        }
        return psiTypeVisitor.visitArrayType(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public GlobalSearchScope getResolveScope() {
        return this.myComponentType.getResolveScope();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiType
    public PsiType[] getSuperTypes() {
        PsiType[] superTypes = this.myComponentType.getSuperTypes();
        PsiType[] psiTypeArrCreateArray = createArray(superTypes.length);
        for (int i = 0; i < superTypes.length; i++) {
            psiTypeArrCreateArray[i] = superTypes[i].createArrayType();
        }
        if (psiTypeArrCreateArray == null) {
            $$$reportNull$$$0(12);
        }
        return psiTypeArrCreateArray;
    }

    public PsiType getComponentType() {
        PsiType psiType = this.myComponentType;
        if (psiType == null) {
            $$$reportNull$$$0(13);
        }
        return psiType;
    }

    public boolean equals(Object obj) {
        return (obj instanceof PsiArrayType) && (this instanceof PsiEllipsisType) == (obj instanceof PsiEllipsisType) && this.myComponentType.equals(((PsiArrayType) obj).getComponentType());
    }

    public int hashCode() {
        return this.myComponentType.hashCode() * 3;
    }
}
