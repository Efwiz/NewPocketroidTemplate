package org.jetbrains.kotlin.com.intellij.psi;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Iterator;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;

/* JADX INFO: loaded from: classes3.dex */
public final class ElementDescriptionUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4) ? 2 : 3];
        if (i == 1) {
            objArr[0] = FirebaseAnalytics.Param.LOCATION;
        } else if (i == 2 || i == 3 || i == 4) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/ElementDescriptionUtil";
        } else {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        }
        if (i == 2 || i == 3 || i == 4) {
            objArr[1] = "getElementDescription";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/ElementDescriptionUtil";
        }
        if (i != 2 && i != 3 && i != 4) {
            objArr[2] = "getElementDescription";
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private ElementDescriptionUtil() {
    }

    public static String getElementDescription(PsiElement psiElement, ElementDescriptionLocation elementDescriptionLocation) {
        String elementDescription;
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        if (elementDescriptionLocation == null) {
            $$$reportNull$$$0(1);
        }
        Iterator<ElementDescriptionProvider> it = ElementDescriptionProvider.EP_NAME.getExtensionList().iterator();
        while (it.hasNext()) {
            String elementDescription2 = it.next().getElementDescription(psiElement, elementDescriptionLocation);
            if (elementDescription2 != null) {
                if (elementDescription2 == null) {
                    $$$reportNull$$$0(2);
                }
                return elementDescription2;
            }
        }
        ElementDescriptionProvider defaultProvider = elementDescriptionLocation.getDefaultProvider();
        if (defaultProvider != null && (elementDescription = defaultProvider.getElementDescription(psiElement, elementDescriptionLocation)) != null) {
            if (elementDescription == null) {
                $$$reportNull$$$0(3);
            }
            return elementDescription;
        }
        String string = psiElement.toString();
        if (string == null) {
            $$$reportNull$$$0(4);
        }
        return string;
    }
}
