package org.jetbrains.kotlin.com.intellij.psi.impl;

import javax.swing.Icon;
import org.jetbrains.kotlin.com.intellij.codeInsight.TestFrameworks;
import org.jetbrains.kotlin.com.intellij.core.JavaPsiBundle;
import org.jetbrains.kotlin.com.intellij.icons.AllIcons;
import org.jetbrains.kotlin.com.intellij.ide.IconLayerProvider;
import org.jetbrains.kotlin.com.intellij.openapi.project.DumbService;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.Strings;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValue;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.com.intellij.psi.util.InheritanceUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiClassUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiMethodUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.ui.IconManager;
import org.jetbrains.kotlin.com.intellij.ui.icons.RowIcon;
import org.jetbrains.kotlin.com.intellij.util.BitUtil;
import org.jetbrains.kotlin.com.intellij.util.PlatformIcons;
import org.jetbrains.kotlin.com.intellij.util.VisibilityIcons;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

/* JADX INFO: loaded from: classes3.dex */
public final class ElementPresentationUtil implements PlatformIcons {
    private static final Int2ObjectMap<Icon> BASE_ICON;
    public static final int CLASS_KIND_CLASS = 30;
    public static final int CLASS_KIND_EXCEPTION = 80;
    public static final int CLASS_KIND_JSP = 70;
    private static final Key<CachedValue<Integer>> CLASS_KIND_KEY = new Key<>("CLASS_KIND_KEY");
    public static final int FLAGS_RUNNABLE = 16384;

    private ElementPresentationUtil() {
    }

    public static int getFlags(PsiModifierListOwner psiModifierListOwner, boolean z) {
        boolean z2 = psiModifierListOwner instanceof PsiClass;
        boolean z3 = z2 && ((PsiClass) psiModifierListOwner).isEnum();
        int i = ((!psiModifierListOwner.hasModifierProperty("static") || z3) ? 0 : 512) | ((!psiModifierListOwner.hasModifierProperty("final") || z3) ? 0 : 1024) | (z ? 2048 : 0);
        if (!z2) {
            return i;
        }
        PsiClass psiClass = (PsiClass) psiModifierListOwner;
        if (psiModifierListOwner.hasModifierProperty("abstract") && !psiClass.isInterface()) {
            i |= 256;
        }
        int classKind = getClassKind(psiClass);
        if (classKind == 90) {
            return i | 8192;
        }
        return classKind == 100 ? i | 16384 : i;
    }

    static {
        Int2ObjectOpenHashMap int2ObjectOpenHashMap = new Int2ObjectOpenHashMap(20);
        BASE_ICON = int2ObjectOpenHashMap;
        int2ObjectOpenHashMap.put(30, IconManager.getInstance().tooltipOnlyIfComposite(CLASS_ICON));
        int2ObjectOpenHashMap.put(286, ABSTRACT_CLASS_ICON);
        int2ObjectOpenHashMap.put(20, ANNOTATION_TYPE_ICON);
        int2ObjectOpenHashMap.put(276, ANNOTATION_TYPE_ICON);
        int2ObjectOpenHashMap.put(40, ANONYMOUS_CLASS_ICON);
        int2ObjectOpenHashMap.put(296, ANONYMOUS_CLASS_ICON);
        int2ObjectOpenHashMap.put(60, ASPECT_ICON);
        int2ObjectOpenHashMap.put(316, ASPECT_ICON);
        int2ObjectOpenHashMap.put(50, ENUM_ICON);
        int2ObjectOpenHashMap.put(306, ENUM_ICON);
        int2ObjectOpenHashMap.put(80, EXCEPTION_CLASS_ICON);
        int2ObjectOpenHashMap.put(336, AllIcons.Nodes.AbstractException);
        int2ObjectOpenHashMap.put(10, IconManager.getInstance().tooltipOnlyIfComposite(INTERFACE_ICON));
        int2ObjectOpenHashMap.put(266, IconManager.getInstance().tooltipOnlyIfComposite(INTERFACE_ICON));
        int2ObjectOpenHashMap.put(90, IconManager.getInstance().tooltipOnlyIfComposite(CLASS_ICON));
        int2ObjectOpenHashMap.put(346, ABSTRACT_CLASS_ICON);
        int2ObjectOpenHashMap.put(110, RECORD_ICON);
        int2ObjectOpenHashMap.put(100, CLASS_ICON);
        IconManager iconManager = IconManager.getInstance();
        iconManager.registerIconLayer(512, AllIcons.Nodes.StaticMark);
        iconManager.registerIconLayer(1024, AllIcons.Nodes.FinalMark);
        iconManager.registerIconLayer(8192, AllIcons.Nodes.JunitTestMark);
        iconManager.registerIconLayer(16384, AllIcons.Nodes.RunnableMark);
    }

    public static int getBasicClassKind(PsiClass psiClass) {
        if (!psiClass.isValid()) {
            return 30;
        }
        if (psiClass.isAnnotationType()) {
            return 20;
        }
        if (psiClass.isEnum()) {
            return 50;
        }
        if (psiClass.isRecord()) {
            return 110;
        }
        if (psiClass.isInterface()) {
            return 10;
        }
        return psiClass instanceof PsiAnonymousClass ? 40 : 30;
    }

    public static int getClassKind(final PsiClass psiClass) {
        if (!psiClass.isValid()) {
            psiClass.putUserData(CLASS_KIND_KEY, null);
            return 30;
        }
        Key<CachedValue<Integer>> key = CLASS_KIND_KEY;
        CachedValue cachedValueCreateCachedValue = (CachedValue) psiClass.getUserData(key);
        if (cachedValueCreateCachedValue == null) {
            cachedValueCreateCachedValue = CachedValuesManager.getManager(psiClass.getProject()).createCachedValue(new CachedValueProvider() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.ElementPresentationUtil$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
                public final CachedValueProvider.Result compute() {
                    PsiClass psiClass2 = psiClass;
                    return CachedValueProvider.Result.createSingleDependency(Integer.valueOf(ElementPresentationUtil.getClassKindImpl(psiClass2)), psiClass2);
                }
            }, false);
            psiClass.putUserData(key, cachedValueCreateCachedValue);
        }
        return ((Integer) cachedValueCreateCachedValue.getValue()).intValue();
    }

    private static int getClassKindImpl(PsiClass psiClass) {
        if (!psiClass.isValid()) {
            return 30;
        }
        if (psiClass.isAnnotationType()) {
            return 20;
        }
        if (psiClass.isEnum()) {
            return 50;
        }
        if (psiClass.isRecord()) {
            return 110;
        }
        if (psiClass.isInterface()) {
            return 10;
        }
        if (psiClass instanceof PsiAnonymousClass) {
            return 40;
        }
        if (!DumbService.getInstance(psiClass.getProject()).isDumb()) {
            PsiClass psiClassFindClass = JavaPsiFacade.getInstance(psiClass.getManager().getProject()).findClass(CommonClassNames.JAVA_LANG_THROWABLE, psiClass.getResolveScope());
            if (psiClassFindClass != null && InheritanceUtil.isInheritorOrSelf(psiClass, psiClassFindClass, true)) {
                return 80;
            }
            if (TestFrameworks.getInstance().isTestClass(psiClass)) {
                return 90;
            }
            if (PsiClassUtil.isRunnableClass(psiClass, false) && PsiMethodUtil.findMainMethod(psiClass) != null) {
                return 100;
            }
        }
        return 30;
    }

    public static Icon getClassIconOfKind(PsiClass psiClass, int i) {
        return BASE_ICON.get((psiClass.hasModifierProperty("abstract") ? 256 : 0) | i);
    }

    public static String getDescription(PsiModifierListOwner psiModifierListOwner) {
        String strMessage;
        if (psiModifierListOwner instanceof PsiClass) {
            strMessage = getClassNoun((PsiClass) psiModifierListOwner);
        } else if (psiModifierListOwner instanceof PsiMethod) {
            strMessage = JavaPsiBundle.message("node.method.tooltip", new Object[0]);
        } else {
            if (!(psiModifierListOwner instanceof PsiField)) {
                return null;
            }
            strMessage = JavaPsiBundle.message("node.field.tooltip", new Object[0]);
        }
        return (getFlagsDescription(psiModifierListOwner) + " " + strMessage).trim();
    }

    private static String getClassNoun(PsiClass psiClass) {
        int classKind = getClassKind(psiClass);
        if (classKind == 10) {
            return JavaPsiBundle.message("node.interface.tooltip", new Object[0]);
        }
        if (classKind == 20) {
            return JavaPsiBundle.message("node.annotation.tooltip", new Object[0]);
        }
        if (classKind == 40) {
            return JavaPsiBundle.message("node.anonymous.class.tooltip", new Object[0]);
        }
        if (classKind == 50) {
            return JavaPsiBundle.message("node.enum.tooltip", new Object[0]);
        }
        if (classKind == 80) {
            return JavaPsiBundle.message("node.exception.tooltip", new Object[0]);
        }
        if (classKind == 90) {
            return JavaPsiBundle.message("node.junit.test.tooltip", new Object[0]);
        }
        if (classKind == 100) {
            return JavaPsiBundle.message("node.runnable.class.tooltip", new Object[0]);
        }
        if (classKind == 110) {
            return JavaPsiBundle.message("node.record.tooltip", new Object[0]);
        }
        return JavaPsiBundle.message("node.class.tooltip", new Object[0]);
    }

    private static String getFlagsDescription(PsiModifierListOwner psiModifierListOwner) {
        int accessLevel;
        int flags = getFlags(psiModifierListOwner, false);
        StringBuilder sb = new StringBuilder();
        for (IconLayerProvider iconLayerProvider : IconLayerProvider.EP_NAME.getExtensionList()) {
            if (iconLayerProvider.getLayerIcon(psiModifierListOwner, false) != null) {
                sb.append(" ").append(iconLayerProvider.getLayerDescription());
            }
        }
        if (BitUtil.isSet(flags, 256)) {
            sb.append(" ").append(JavaPsiBundle.message("node.abstract.flag.tooltip", new Object[0]));
        }
        if (BitUtil.isSet(flags, 1024)) {
            sb.append(" ").append(JavaPsiBundle.message("node.final.flag.tooltip", new Object[0]));
        }
        if (BitUtil.isSet(flags, 512)) {
            sb.append(" ").append(JavaPsiBundle.message("node.static.flag.tooltip", new Object[0]));
        }
        PsiModifierList psiModifierListMo6095getModifierList = psiModifierListOwner.mo6095getModifierList();
        if (psiModifierListMo6095getModifierList != null && (accessLevel = PsiUtil.getAccessLevel(psiModifierListMo6095getModifierList)) != 4) {
            sb.append(" ").append(Strings.capitalize(JavaPsiBundle.visibilityPresentation(PsiUtil.getAccessModifier(accessLevel))));
        }
        return sb.toString();
    }

    public static Icon addVisibilityIcon(PsiModifierListOwner psiModifierListOwner, int i, RowIcon rowIcon) {
        if (BitUtil.isSet(i, 1)) {
            VisibilityIcons.setVisibilityIcon(psiModifierListOwner.mo6095getModifierList(), rowIcon);
        }
        return rowIcon;
    }
}
