package org.jetbrains.kotlin.asJava.elements;

import javax.swing.Icon;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.navigation.NavigationItem;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightModifierList;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.idea.KotlinIconProviderService;

/* JADX INFO: loaded from: classes2.dex */
public class LightVariableBuilder extends LightElement implements NavigationItem, PsiVariable {
    private final LightModifierList myModifierList;
    private final String myName;
    private final PsiType myType;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 5) ? 2 : 3];
        if (i == 1) {
            objArr[0] = ModuleXmlParser.TYPE;
        } else if (i == 2 || i == 3 || i == 5) {
            objArr[0] = "org/jetbrains/kotlin/asJava/elements/LightVariableBuilder";
        } else {
            objArr[0] = "name";
        }
        if (i == 2) {
            objArr[1] = "getType";
        } else if (i == 3) {
            objArr[1] = "getModifierList";
        } else if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/asJava/elements/LightVariableBuilder";
        } else {
            objArr[1] = "getName";
        }
        if (i != 2 && i != 3) {
            if (i == 4) {
                objArr[2] = "hasModifierProperty";
            } else if (i != 5) {
                if (i != 6) {
                    objArr[2] = "<init>";
                } else {
                    objArr[2] = "setName";
                }
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public Object computeConstantValue() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LightVariableBuilder(PsiManager psiManager, String str, PsiType psiType, Language language) {
        super(psiManager, language);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (psiType == null) {
            $$$reportNull$$$0(1);
        }
        this.myName = str;
        this.myType = psiType;
        this.myModifierList = new LightModifierList(psiManager);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "LightVariableBuilder:" + getName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public PsiType getType() {
        PsiType psiType = this.myType;
        if (psiType == null) {
            $$$reportNull$$$0(2);
        }
        return psiType;
    }

    /* JADX INFO: renamed from: getModifierList */
    public PsiModifierList mo6095getModifierList() {
        LightModifierList lightModifierList = this.myModifierList;
        if (lightModifierList == null) {
            $$$reportNull$$$0(3);
        }
        return lightModifierList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasModifierProperty(String str) {
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        return this.myModifierList.hasModifierProperty(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getName() {
        String str = this.myName;
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        return str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        throw new UnsupportedOperationException("setName is not implemented yet in org.jetbrains.kotlin.asJava.light.LightVariableBuilder");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    public Icon getElementIcon(int i) {
        return KotlinIconProviderService.getInstance().getLightVariableIcon(this, i);
    }
}
