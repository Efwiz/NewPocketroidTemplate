package org.jetbrains.kotlin.com.intellij.psi.util;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.core.JavaPsiBundle;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.psi.HierarchicalMethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstant;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.psi.PsiMember;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightRecordCanonicalConstructor;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public enum AccessModifier {
    PUBLIC("public"),
    PROTECTED("protected"),
    PACKAGE_LOCAL(PsiModifier.PACKAGE_LOCAL),
    PRIVATE("private");

    public static final List<AccessModifier> ALL_MODIFIERS;
    private static final List<AccessModifier> PUBLIC_PACKAGE;
    private static final List<AccessModifier> PUBLIC_PRIVATE;
    private final String myModifier;

    /* JADX WARN: Code duplicated, block: B:11:0x0013  */
    /* JADX WARN: Code duplicated, block: B:6:0x0009  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 1) {
            switch (i) {
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 1) {
            switch (i) {
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/util/AccessModifier";
                break;
            case 2:
                objArr[0] = "owner";
                break;
            case 3:
                objArr[0] = "modifierList";
                break;
            case 4:
                objArr[0] = "other";
                break;
            default:
                objArr[0] = "modifier";
                break;
        }
        if (i != 1) {
            switch (i) {
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                    objArr[1] = "getAvailableModifiers";
                    break;
                case 20:
                case 21:
                    objArr[1] = "getMinAccess";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/util/AccessModifier";
                    break;
            }
        } else {
            objArr[1] = "toPsiModifier";
        }
        switch (i) {
            case 1:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
                break;
            case 2:
                objArr[2] = "hasModifier";
                break;
            case 3:
                objArr[2] = "fromModifierList";
                break;
            case 4:
                objArr[2] = "isWeaker";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1) {
            switch (i) {
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    static {
        AccessModifier accessModifier = PUBLIC;
        AccessModifier accessModifier2 = PACKAGE_LOCAL;
        AccessModifier accessModifier3 = PRIVATE;
        ALL_MODIFIERS = ContainerUtil.immutableList(values());
        PUBLIC_PACKAGE = ContainerUtil.immutableList(accessModifier, accessModifier2);
        PUBLIC_PRIVATE = ContainerUtil.immutableList(accessModifier, accessModifier3);
    }

    AccessModifier(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        this.myModifier = str;
    }

    public String toPsiModifier() {
        String str = this.myModifier;
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        return str;
    }

    public boolean hasModifier(PsiModifierListOwner psiModifierListOwner) {
        if (psiModifierListOwner == null) {
            $$$reportNull$$$0(2);
        }
        return psiModifierListOwner.hasModifierProperty(toPsiModifier());
    }

    public static AccessModifier fromKeyword(PsiKeyword psiKeyword) {
        if (psiKeyword == null) {
            return null;
        }
        return fromPsiModifier(psiKeyword.getText());
    }

    public static AccessModifier fromPsiModifier(String str) {
        if (str == null) {
            return null;
        }
        str.hashCode();
        switch (str) {
            case "packageLocal":
                return PACKAGE_LOCAL;
            case "public":
                return PUBLIC;
            case "protected":
                return PROTECTED;
            case "private":
                return PRIVATE;
            default:
                return null;
        }
    }

    public static AccessModifier fromModifierList(PsiModifierList psiModifierList) {
        if (psiModifierList == null) {
            $$$reportNull$$$0(3);
        }
        if (psiModifierList.hasModifierProperty("private")) {
            return PRIVATE;
        }
        if (psiModifierList.hasModifierProperty(PsiModifier.PACKAGE_LOCAL)) {
            return PACKAGE_LOCAL;
        }
        if (psiModifierList.hasModifierProperty("protected")) {
            return PROTECTED;
        }
        return PUBLIC;
    }

    public boolean isWeaker(AccessModifier accessModifier) {
        if (accessModifier == null) {
            $$$reportNull$$$0(4);
        }
        return compareTo(accessModifier) < 0;
    }

    @Override // java.lang.Enum
    public String toString() {
        return JavaPsiBundle.visibilityPresentation(toPsiModifier());
    }

    public static List<AccessModifier> getAvailableModifiers(PsiMember psiMember) {
        if (psiMember == null) {
            List<AccessModifier> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(5);
            }
            return listEmptyList;
        }
        PsiClass psiClassMo6092getContainingClass = psiMember.mo6092getContainingClass();
        if (psiMember instanceof PsiField) {
            if ((psiMember instanceof PsiEnumConstant) || psiClassMo6092getContainingClass == null || psiClassMo6092getContainingClass.isInterface()) {
                List<AccessModifier> listEmptyList2 = Collections.emptyList();
                if (listEmptyList2 == null) {
                    $$$reportNull$$$0(6);
                }
                return listEmptyList2;
            }
            List<AccessModifier> list = ALL_MODIFIERS;
            if (list == null) {
                $$$reportNull$$$0(7);
            }
            return list;
        }
        if (psiMember instanceof PsiMethod) {
            PsiMethod psiMethod = (PsiMethod) psiMember;
            if (psiClassMo6092getContainingClass == null || (psiClassMo6092getContainingClass.isEnum() && psiMethod.isConstructor())) {
                List<AccessModifier> listEmptyList3 = Collections.emptyList();
                if (listEmptyList3 == null) {
                    $$$reportNull$$$0(8);
                }
                return listEmptyList3;
            }
            if (JavaPsiRecordUtil.getRecordComponentForAccessor(psiMethod) != null) {
                List<AccessModifier> listSingletonList = Collections.singletonList(PUBLIC);
                if (listSingletonList == null) {
                    $$$reportNull$$$0(9);
                }
                return listSingletonList;
            }
            if (JavaPsiRecordUtil.isCompactConstructor(psiMethod) || JavaPsiRecordUtil.isExplicitCanonicalConstructor(psiMethod) || (psiMethod instanceof LightRecordCanonicalConstructor)) {
                PsiModifierList modifierList = psiClassMo6092getContainingClass.mo6095getModifierList();
                if (modifierList != null) {
                    final AccessModifier accessModifierFromModifierList = fromModifierList(modifierList);
                    List<AccessModifier> listFilter = ContainerUtil.filter(ALL_MODIFIERS, new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.AccessModifier$$ExternalSyntheticLambda0
                        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
                        public final boolean value(Object obj) {
                            return AccessModifier.lambda$getAvailableModifiers$0(this.f$0, (AccessModifier) obj);
                        }
                    });
                    if (listFilter == null) {
                        $$$reportNull$$$0(10);
                    }
                    return listFilter;
                }
                List<AccessModifier> listSingletonList2 = Collections.singletonList(PUBLIC);
                if (listSingletonList2 == null) {
                    $$$reportNull$$$0(11);
                }
                return listSingletonList2;
            }
            if (psiClassMo6092getContainingClass.isInterface()) {
                if (psiMethod.getBody() != null && PsiUtil.isLanguageLevel9OrHigher(psiMember)) {
                    List<AccessModifier> list2 = PUBLIC_PRIVATE;
                    if (list2 == null) {
                        $$$reportNull$$$0(12);
                    }
                    return list2;
                }
                List<AccessModifier> listSingletonList3 = Collections.singletonList(PUBLIC);
                if (listSingletonList3 == null) {
                    $$$reportNull$$$0(13);
                }
                return listSingletonList3;
            }
            final AccessModifier minAccess = getMinAccess(psiMethod);
            if (minAccess != PRIVATE) {
                List<AccessModifier> listFilter2 = ContainerUtil.filter(ALL_MODIFIERS, new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.AccessModifier$$ExternalSyntheticLambda1
                    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
                    public final boolean value(Object obj) {
                        return AccessModifier.lambda$getAvailableModifiers$1(this.f$0, (AccessModifier) obj);
                    }
                });
                if (listFilter2 == null) {
                    $$$reportNull$$$0(14);
                }
                return listFilter2;
            }
            List<AccessModifier> list3 = ALL_MODIFIERS;
            if (list3 == null) {
                $$$reportNull$$$0(15);
            }
            return list3;
        }
        if (psiMember instanceof PsiClass) {
            if (PsiUtil.isLocalOrAnonymousClass((PsiClass) psiMember)) {
                List<AccessModifier> listEmptyList4 = Collections.emptyList();
                if (listEmptyList4 == null) {
                    $$$reportNull$$$0(16);
                }
                return listEmptyList4;
            }
            if (psiClassMo6092getContainingClass == null) {
                List<AccessModifier> list4 = PUBLIC_PACKAGE;
                if (list4 == null) {
                    $$$reportNull$$$0(17);
                }
                return list4;
            }
            List<AccessModifier> list5 = ALL_MODIFIERS;
            if (list5 == null) {
                $$$reportNull$$$0(18);
            }
            return list5;
        }
        List<AccessModifier> listEmptyList5 = Collections.emptyList();
        if (listEmptyList5 == null) {
            $$$reportNull$$$0(19);
        }
        return listEmptyList5;
    }

    static /* synthetic */ boolean lambda$getAvailableModifiers$0(AccessModifier accessModifier, AccessModifier accessModifier2) {
        return !accessModifier.isWeaker(accessModifier2);
    }

    static /* synthetic */ boolean lambda$getAvailableModifiers$1(AccessModifier accessModifier, AccessModifier accessModifier2) {
        return accessModifier2.compareTo(accessModifier) <= 0;
    }

    private static AccessModifier getMinAccess(PsiMethod psiMethod) {
        if (psiMethod.isConstructor() || psiMethod.hasModifierProperty("static")) {
            AccessModifier accessModifier = PRIVATE;
            if (accessModifier == null) {
                $$$reportNull$$$0(20);
            }
            return accessModifier;
        }
        HierarchicalMethodSignature hierarchicalMethodSignature = psiMethod.getHierarchicalMethodSignature();
        AccessModifier accessModifier2 = PRIVATE;
        Iterator<HierarchicalMethodSignature> it = hierarchicalMethodSignature.getSuperSignatures().iterator();
        while (it.hasNext()) {
            PsiMethod method = it.next().getMethod();
            AccessModifier accessModifierFromModifierList = fromModifierList(method.mo6095getModifierList());
            if (accessModifierFromModifierList.isWeaker(accessModifier2) && (!psiMethod.hasModifierProperty("abstract") || MethodSignatureUtil.isSuperMethod(method, psiMethod))) {
                if (!PsiUtil.isAccessible(psiMethod.getProject(), method, psiMethod, null)) {
                    continue;
                } else {
                    if (accessModifierFromModifierList == PUBLIC) {
                        accessModifier2 = accessModifierFromModifierList;
                        break;
                    }
                    accessModifier2 = accessModifierFromModifierList;
                }
            }
        }
        if (accessModifier2 == null) {
            $$$reportNull$$$0(21);
        }
        return accessModifier2;
    }
}
