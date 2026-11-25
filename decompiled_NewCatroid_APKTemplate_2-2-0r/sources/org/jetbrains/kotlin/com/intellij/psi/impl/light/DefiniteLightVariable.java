package org.jetbrains.kotlin.com.intellij.psi.impl.light;

import java.util.Arrays;
import java.util.Objects;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;

/* JADX INFO: loaded from: classes3.dex */
public class DefiniteLightVariable extends LightVariableBuilder {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = ModuleXmlParser.TYPE;
        } else if (i == 2) {
            objArr[0] = "navigationElement";
        } else if (i != 3) {
            objArr[0] = "name";
        } else {
            objArr[0] = "modifierList";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/DefiniteLightVariable";
        if (i != 3) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "areModifiersEqual";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DefiniteLightVariable(String str, PsiType psiType, PsiElement psiElement) {
        super(str, psiType, psiElement);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (psiType == null) {
            $$$reportNull$$$0(1);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(2);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DefiniteLightVariable definiteLightVariable = (DefiniteLightVariable) obj;
        return Objects.equals(getName(), definiteLightVariable.getName()) && Objects.equals(getType(), definiteLightVariable.getType()) && areModifiersEqual(definiteLightVariable.mo6095getModifierList()) && Objects.equals(getBaseIcon(), definiteLightVariable.getBaseIcon()) && Objects.equals(getOriginInfo(), definiteLightVariable.getOriginInfo()) && areNavigationElementsEqual(definiteLightVariable);
    }

    private boolean areNavigationElementsEqual(DefiniteLightVariable definiteLightVariable) {
        PsiElement navigationElement = getNavigationElement();
        if (navigationElement == this) {
            return definiteLightVariable.getNavigationElement() == definiteLightVariable;
        }
        return Objects.equals(navigationElement, definiteLightVariable.getNavigationElement());
    }

    private boolean areModifiersEqual(PsiModifierList psiModifierList) {
        if (psiModifierList == null) {
            $$$reportNull$$$0(3);
        }
        PsiModifierList modifierList = mo6095getModifierList();
        if ((modifierList instanceof LightModifierList) && (psiModifierList instanceof LightModifierList)) {
            return Arrays.equals(((LightModifierList) modifierList).getModifiers(), ((LightModifierList) psiModifierList).getModifiers());
        }
        return Objects.equals(modifierList, psiModifierList);
    }

    public int hashCode() {
        return Objects.hash(getName(), getType(), getBaseIcon(), getOriginInfo());
    }
}
