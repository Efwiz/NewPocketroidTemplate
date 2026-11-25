package org.jetbrains.kotlin.com.intellij.psi.impl.light;

import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaLanguage;
import org.jetbrains.kotlin.com.intellij.lang.jvm.types.JvmType;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class LightFieldBuilder extends LightVariableBuilder<LightFieldBuilder> implements PsiField {
    private PsiClass myContainingClass;
    private PsiDocComment myDocComment;
    private PsiExpression myInitializer;
    private boolean myIsDeprecated;

    /* JADX WARN: Code duplicated, block: B:12:0x0018  */
    /* JADX WARN: Code duplicated, block: B:13:0x001d  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = ModuleXmlParser.TYPE;
        } else if (i == 2) {
            objArr[0] = "navigationElement";
        } else if (i == 4) {
            objArr[0] = ModuleXmlParser.TYPE;
        } else if (i == 5) {
            objArr[0] = "navigationElement";
        } else if (i != 7) {
            objArr[0] = "name";
        } else {
            objArr[0] = ModuleXmlParser.TYPE;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightFieldBuilder";
        objArr[2] = "<init>";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public /* bridge */ /* synthetic */ JvmType getType() {
        return super.getType();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LightFieldBuilder(String str, String str2, PsiElement psiElement) {
        super(str, JavaPsiFacade.getElementFactory(psiElement.getProject()).createTypeFromText(str2, psiElement), psiElement);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (str2 == null) {
            $$$reportNull$$$0(1);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LightFieldBuilder(String str, PsiType psiType, PsiElement psiElement) {
        super(str, psiType, psiElement);
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        if (psiType == null) {
            $$$reportNull$$$0(4);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(5);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LightFieldBuilder(PsiManager psiManager, String str, PsiType psiType) {
        super(psiManager, str, psiType, JavaLanguage.INSTANCE);
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        if (psiType == null) {
            $$$reportNull$$$0(7);
        }
    }

    public LightFieldBuilder setContainingClass(PsiClass psiClass) {
        this.myContainingClass = psiClass;
        return this;
    }

    /* JADX INFO: renamed from: setInitializer */
    public void mo6096setInitializer(PsiExpression psiExpression) throws IncorrectOperationException {
        this.myInitializer = psiExpression;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightVariableBuilder, org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public PsiExpression getInitializer() {
        return this.myInitializer;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightVariableBuilder, org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public boolean hasInitializer() {
        return this.myInitializer != null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaDocumentedElement
    /* JADX INFO: renamed from: getDocComment */
    public PsiDocComment mo6091getDocComment() {
        return this.myDocComment;
    }

    public LightFieldBuilder setDocComment(PsiDocComment psiDocComment) {
        this.myDocComment = psiDocComment;
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocCommentOwner
    public boolean isDeprecated() {
        return this.myIsDeprecated;
    }

    public LightFieldBuilder setIsDeprecated(boolean z) {
        this.myIsDeprecated = z;
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMember, org.jetbrains.kotlin.com.intellij.psi.PsiJvmMember
    /* JADX INFO: renamed from: getContainingClass */
    public PsiClass mo6092getContainingClass() {
        return this.myContainingClass;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getLightParent() {
        return mo6092getContainingClass();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isEquivalentTo(PsiElement psiElement) {
        if (!(psiElement instanceof PsiField)) {
            return false;
        }
        PsiField psiField = (PsiField) psiElement;
        if (!psiField.getName().equals(getName()) || hasModifierProperty("static") != psiField.hasModifierProperty("static")) {
            return false;
        }
        PsiClass containingClass = mo6092getContainingClass();
        PsiClass containingClass2 = psiField.mo6092getContainingClass();
        return (containingClass == null && containingClass2 == null) || getManager().areElementsEquivalent(containingClass, containingClass2);
    }
}
