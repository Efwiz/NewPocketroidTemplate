package org.jetbrains.kotlin.com.intellij.psi.impl.light;

import javax.swing.Icon;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaLanguage;
import org.jetbrains.kotlin.com.intellij.navigation.NavigationItem;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.OriginInfoAwareElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.impl.ElementPresentationUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightVariableBuilder;
import org.jetbrains.kotlin.com.intellij.ui.IconManager;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.PlatformIcons;

/* JADX INFO: loaded from: classes3.dex */
public class LightVariableBuilder<T extends LightVariableBuilder> extends LightElement implements NavigationItem, OriginInfoAwareElement, PsiVariable {
    private volatile Icon myBaseIcon;
    private volatile LightModifierList myModifierList;
    private final String myName;
    private String myOriginInfo;
    private final PsiType myType;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 13 || i == 14 || i == 16) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 13 || i == 14 || i == 16) ? 2 : 3];
        switch (i) {
            case 1:
            case 4:
            case 7:
            case 10:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 2:
            case 5:
                objArr[0] = "navigationElement";
                break;
            case 3:
            case 6:
            case 9:
            case 15:
            default:
                objArr[0] = "name";
                break;
            case 8:
            case 11:
                objArr[0] = "language";
                break;
            case 12:
                objArr[0] = "modifierList";
                break;
            case 13:
            case 14:
            case 16:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightVariableBuilder";
                break;
        }
        if (i == 13) {
            objArr[1] = "getType";
        } else if (i == 14) {
            objArr[1] = "getModifierList";
        } else if (i != 16) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightVariableBuilder";
        } else {
            objArr[1] = "getName";
        }
        switch (i) {
            case 13:
            case 14:
            case 16:
                break;
            case 15:
                objArr[2] = "hasModifierProperty";
                break;
            case 17:
                objArr[2] = "setName";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 13 && i != 14 && i != 16) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public Object computeConstantValue() {
        return null;
    }

    public PsiExpression getInitializer() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable, org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public PsiIdentifier mo7392getNameIdentifier() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public PsiTypeElement getTypeElement() {
        return null;
    }

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

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LightVariableBuilder(String str, String str2, PsiElement psiElement) {
        this(str, JavaPsiFacade.getElementFactory(psiElement.getProject()).createTypeFromText(str2, psiElement), psiElement);
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

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LightVariableBuilder(String str, PsiType psiType, PsiElement psiElement) {
        this(psiElement.getManager(), str, psiType, JavaLanguage.INSTANCE);
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        if (psiType == null) {
            $$$reportNull$$$0(4);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(5);
        }
        setNavigationElement(psiElement);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LightVariableBuilder(PsiManager psiManager, String str, PsiType psiType, Language language) {
        this(psiManager, str, psiType, language, new LightModifierList(psiManager));
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        if (psiType == null) {
            $$$reportNull$$$0(7);
        }
        if (language == null) {
            $$$reportNull$$$0(8);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LightVariableBuilder(PsiManager psiManager, String str, PsiType psiType, Language language, LightModifierList lightModifierList) {
        super(psiManager, language);
        if (str == null) {
            $$$reportNull$$$0(9);
        }
        if (psiType == null) {
            $$$reportNull$$$0(10);
        }
        if (language == null) {
            $$$reportNull$$$0(11);
        }
        if (lightModifierList == null) {
            $$$reportNull$$$0(12);
        }
        this.myBaseIcon = PlatformIcons.VARIABLE_ICON;
        this.myName = str;
        this.myType = psiType;
        this.myModifierList = lightModifierList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "LightVariableBuilder:" + getName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public PsiType getType() {
        PsiType psiType = this.myType;
        if (psiType == null) {
            $$$reportNull$$$0(13);
        }
        return psiType;
    }

    /* JADX INFO: renamed from: getModifierList */
    public PsiModifierList mo6095getModifierList() {
        LightModifierList lightModifierList = this.myModifierList;
        if (lightModifierList == null) {
            $$$reportNull$$$0(14);
        }
        return lightModifierList;
    }

    public T setModifiers(String... strArr) {
        this.myModifierList = new LightModifierList(getManager(), getLanguage(), strArr);
        return this;
    }

    public T setModifierList(LightModifierList lightModifierList) {
        this.myModifierList = lightModifierList;
        return this;
    }

    public boolean hasModifierProperty(String str) {
        if (str == null) {
            $$$reportNull$$$0(15);
        }
        return this.myModifierList.hasModifierProperty(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getName() {
        String str = this.myName;
        if (str == null) {
            $$$reportNull$$$0(16);
        }
        return str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(17);
        }
        throw new UnsupportedOperationException("setName is not implemented yet in com.intellij.psi.impl.light.LightVariableBuilder");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    public Icon getElementIcon(int i) {
        return ElementPresentationUtil.addVisibilityIcon(this, i, IconManager.getInstance().createLayeredIcon(this, this.myBaseIcon, ElementPresentationUtil.getFlags(this, false)));
    }

    public T setBaseIcon(Icon icon) {
        this.myBaseIcon = icon;
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.OriginInfoAwareElement
    public String getOriginInfo() {
        return this.myOriginInfo;
    }

    public void setOriginInfo(String str) {
        this.myOriginInfo = str;
    }
}
