package org.jetbrains.kotlin.com.intellij.psi.impl.light;

import io.netty.util.internal.StringUtil;
import java.util.Set;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaLanguage;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public class LightModifierList extends LightElement implements PsiModifierList {
    private final Set<String> myModifiers;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 5 || i == 9) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 5 || i == 9) ? 2 : 3];
        switch (i) {
            case 4:
            case 5:
            case 9:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightModifierList";
                break;
            case 6:
            case 7:
                objArr[0] = "qualifiedName";
                break;
            case 8:
                objArr[0] = "visitor";
                break;
            default:
                objArr[0] = "name";
                break;
        }
        if (i == 4) {
            objArr[1] = "getAnnotations";
        } else if (i == 5) {
            objArr[1] = "getApplicableAnnotations";
        } else if (i != 9) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightModifierList";
        } else {
            objArr[1] = "getModifiers";
        }
        switch (i) {
            case 1:
                objArr[2] = "hasExplicitModifier";
                break;
            case 2:
                objArr[2] = "setModifierProperty";
                break;
            case 3:
                objArr[2] = "checkSetModifierProperty";
                break;
            case 4:
            case 5:
            case 9:
                break;
            case 6:
                objArr[2] = "findAnnotation";
                break;
            case 7:
                objArr[2] = "addAnnotation";
                break;
            case 8:
                objArr[2] = "accept";
                break;
            default:
                objArr[2] = "hasModifierProperty";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 5 && i != 9) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public PsiAnnotation findAnnotation(String str) {
        if (str != null) {
            return null;
        }
        $$$reportNull$$$0(6);
        return null;
    }

    public LightModifierList(PsiModifierListOwner psiModifierListOwner) {
        this(psiModifierListOwner.getManager());
        copyModifiers(psiModifierListOwner.mo6095getModifierList());
    }

    public LightModifierList(PsiManager psiManager) {
        this(psiManager, JavaLanguage.INSTANCE, new String[0]);
    }

    public LightModifierList(PsiManager psiManager, Language language, String... strArr) {
        super(psiManager, language);
        this.myModifiers = ContainerUtil.set(strArr);
    }

    public void addModifier(String str) {
        this.myModifiers.add(str);
    }

    public void copyModifiers(PsiModifierList psiModifierList) {
        if (psiModifierList == null) {
            return;
        }
        for (String str : PsiModifier.MODIFIERS) {
            if (psiModifierList.hasExplicitModifier(str)) {
                addModifier(str);
            }
        }
    }

    public void clearModifiers() {
        this.myModifiers.clear();
    }

    public boolean hasModifierProperty(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        return this.myModifiers.contains(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierList
    public boolean hasExplicitModifier(String str) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        return this.myModifiers.contains(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierList
    public void setModifierProperty(String str, boolean z) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierList
    public void checkSetModifierProperty(String str, boolean z) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        throw new IncorrectOperationException();
    }

    public PsiAnnotation[] getAnnotations() {
        PsiAnnotation[] psiAnnotationArr = PsiAnnotation.EMPTY_ARRAY;
        if (psiAnnotationArr == null) {
            $$$reportNull$$$0(4);
        }
        return psiAnnotationArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public PsiAnnotation[] getApplicableAnnotations() {
        PsiAnnotation[] annotations = getAnnotations();
        if (annotations == null) {
            $$$reportNull$$$0(5);
        }
        return annotations;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner
    public PsiAnnotation addAnnotation(String str) {
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(8);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitModifierList(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiModifierList";
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        StringBuilder sb = new StringBuilder();
        for (String str : PsiModifier.MODIFIERS) {
            if (hasExplicitModifier(str)) {
                sb.append(str);
                sb.append(StringUtil.SPACE);
            }
        }
        if (sb.length() > 0) {
            sb.delete(sb.length() - 1, sb.length());
        }
        return sb.toString();
    }

    public String[] getModifiers() {
        String[] stringArray = ArrayUtilRt.toStringArray(this.myModifiers);
        if (stringArray == null) {
            $$$reportNull$$$0(9);
        }
        return stringArray;
    }
}
