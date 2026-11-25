package org.jetbrains.kotlin.com.intellij.psi.impl.light;

import javax.swing.Icon;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaLanguage;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.OriginInfoAwareElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.impl.ElementPresentationUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.ui.IconManager;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.PlatformIcons;

/* JADX INFO: loaded from: classes3.dex */
public abstract class LightVariableBase extends LightElement implements OriginInfoAwareElement, PsiVariable {
    protected final PsiModifierList myModifierList;
    protected PsiIdentifier myNameIdentifier;
    private String myOriginInfo;
    protected PsiElement myScope;
    protected PsiType myType;
    protected boolean myWritable;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 6) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 2 || i == 6) ? 3 : 2];
        if (i == 2 || i == 6) {
            objArr[0] = "name";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightVariableBase";
        }
        switch (i) {
            case 1:
                objArr[1] = "getName";
                break;
            case 2:
            case 6:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightVariableBase";
                break;
            case 3:
                objArr[1] = "getType";
                break;
            case 4:
                objArr[1] = "computeType";
                break;
            case 5:
                objArr[1] = "getTypeElement";
                break;
            default:
                objArr[1] = "getDeclarationScope";
                break;
        }
        if (i == 2) {
            objArr[2] = "setName";
        } else if (i == 6) {
            objArr[2] = "hasModifierProperty";
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 6) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public Object computeConstantValue() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public PsiExpression getInitializer() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public boolean hasInitializer() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    protected boolean isVisibilitySupported() {
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public void normalizeDeclaration() throws IncorrectOperationException {
    }

    public LightVariableBase(PsiManager psiManager, PsiIdentifier psiIdentifier, PsiType psiType, boolean z, PsiElement psiElement) {
        this(psiManager, psiIdentifier, JavaLanguage.INSTANCE, psiType, z, psiElement);
    }

    public LightVariableBase(PsiManager psiManager, PsiIdentifier psiIdentifier, Language language, PsiType psiType, boolean z, PsiElement psiElement) {
        super(psiManager, language);
        this.myNameIdentifier = psiIdentifier;
        this.myWritable = z;
        this.myType = psiType;
        this.myScope = psiElement;
        this.myModifierList = createModifierList();
    }

    protected PsiModifierList createModifierList() {
        return new LightModifierList(getManager());
    }

    public PsiElement getDeclarationScope() {
        PsiElement psiElement = this.myScope;
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        return psiElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable, org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public PsiIdentifier mo7392getNameIdentifier() {
        return this.myNameIdentifier;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement, org.jetbrains.kotlin.com.intellij.pom.PomTarget
    public boolean isValid() {
        PsiIdentifier psiIdentifier = this.myNameIdentifier;
        return psiIdentifier == null || psiIdentifier.isValid();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getName() {
        String strNotNullize = StringUtil.notNullize(mo7392getNameIdentifier().getText());
        if (strNotNullize == null) {
            $$$reportNull$$$0(1);
        }
        return strNotNullize;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        PsiImplUtil.setName(mo7392getNameIdentifier(), str);
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public PsiType getType() {
        PsiType psiTypeComputeType = this.myType;
        if (psiTypeComputeType == null) {
            psiTypeComputeType = computeType();
            this.myType = psiTypeComputeType;
        }
        if (psiTypeComputeType == null) {
            $$$reportNull$$$0(3);
        }
        return psiTypeComputeType;
    }

    protected PsiType computeType() {
        PsiPrimitiveType psiPrimitiveType = PsiType.VOID;
        if (psiPrimitiveType == null) {
            $$$reportNull$$$0(4);
        }
        return psiPrimitiveType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public PsiTypeElement getTypeElement() {
        PsiTypeElement psiTypeElementCreateTypeElement = JavaPsiFacade.getElementFactory(getProject()).createTypeElement(this.myType);
        if (psiTypeElementCreateTypeElement == null) {
            $$$reportNull$$$0(5);
        }
        return psiTypeElementCreateTypeElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    /* JADX INFO: renamed from: getModifierList */
    public PsiModifierList mo6095getModifierList() {
        return this.myModifierList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasModifierProperty(String str) {
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        return mo6095getModifierList().hasModifierProperty(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        return this.myNameIdentifier.getText();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isWritable() {
        return this.myWritable;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    public Icon getElementIcon(int i) {
        return ElementPresentationUtil.addVisibilityIcon(this, i, IconManager.getInstance().createLayeredIcon(this, PlatformIcons.VARIABLE_ICON, ElementPresentationUtil.getFlags(this, false)));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.OriginInfoAwareElement
    public String getOriginInfo() {
        return this.myOriginInfo;
    }

    public void setOriginInfo(String str) {
        this.myOriginInfo = str;
    }
}
