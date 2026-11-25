package org.jetbrains.kotlin.com.intellij.util;

import javax.swing.Icon;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.ui.IconManager;
import org.jetbrains.kotlin.com.intellij.ui.icons.RowIcon;

/* JADX INFO: loaded from: classes3.dex */
public final class VisibilityIcons {
    public static void setVisibilityIcon(PsiModifierList psiModifierList, RowIcon rowIcon) {
        if (psiModifierList != null) {
            if (psiModifierList.hasModifierProperty("public")) {
                setVisibilityIcon(4, rowIcon);
                return;
            }
            if (psiModifierList.hasModifierProperty("private")) {
                setVisibilityIcon(1, rowIcon);
                return;
            }
            if (psiModifierList.hasModifierProperty("protected")) {
                setVisibilityIcon(3, rowIcon);
                return;
            } else if (psiModifierList.hasModifierProperty(PsiModifier.PACKAGE_LOCAL)) {
                setVisibilityIcon(2, rowIcon);
                return;
            } else {
                rowIcon.setIcon(IconManager.getInstance().createEmptyIcon(PlatformIcons.PUBLIC_ICON), 1);
                return;
            }
        }
        if (PlatformIcons.PUBLIC_ICON != null) {
            rowIcon.setIcon(IconManager.getInstance().createEmptyIcon(PlatformIcons.PUBLIC_ICON), 1);
        }
    }

    public static void setVisibilityIcon(int i, RowIcon rowIcon) {
        Icon iconCreateEmptyIcon;
        if (i == 1) {
            iconCreateEmptyIcon = PlatformIcons.PRIVATE_ICON;
        } else if (i == 2) {
            iconCreateEmptyIcon = PlatformIcons.PACKAGE_LOCAL_ICON;
        } else if (i == 3) {
            iconCreateEmptyIcon = PlatformIcons.PROTECTED_ICON;
        } else if (i == 4) {
            iconCreateEmptyIcon = PlatformIcons.PUBLIC_ICON;
        } else if (PlatformIcons.PUBLIC_ICON == null) {
            return;
        } else {
            iconCreateEmptyIcon = IconManager.getInstance().createEmptyIcon(PlatformIcons.PUBLIC_ICON);
        }
        rowIcon.setIcon(iconCreateEmptyIcon, 1);
    }
}
