package org.jetbrains.kotlin.load.java.structure.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.codeInsight.ExternalAnnotationsManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.descriptors.java.JavaVisibilities;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementSourceFactory;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: loaded from: classes5.dex */
class JavaElementUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 5 || i == 6 || i == 7 || i == 8 || i == 10 || i == 11 || i == 14) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 5 || i == 6 || i == 7 || i == 8 || i == 10 || i == 11 || i == 14) ? 2 : 3];
        switch (i) {
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
            case 11:
            case 14:
                objArr[0] = "org/jetbrains/kotlin/load/java/structure/impl/JavaElementUtil";
                break;
            case 9:
            case 13:
            case 15:
            default:
                objArr[0] = "owner";
                break;
            case 12:
                objArr[0] = "modifierListOwner";
                break;
            case 16:
                objArr[0] = "fqName";
                break;
        }
        if (i == 5 || i == 6 || i == 7 || i == 8) {
            objArr[1] = "getVisibility";
        } else if (i == 10 || i == 11) {
            objArr[1] = "getAnnotations";
        } else if (i != 14) {
            objArr[1] = "org/jetbrains/kotlin/load/java/structure/impl/JavaElementUtil";
        } else {
            objArr[1] = "getRegularAndExternalAnnotations";
        }
        switch (i) {
            case 1:
                objArr[2] = "isStatic";
                break;
            case 2:
                objArr[2] = "isFinal";
                break;
            case 3:
                objArr[2] = "isSealed";
                break;
            case 4:
                objArr[2] = "getVisibility";
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
            case 11:
            case 14:
                break;
            case 9:
                objArr[2] = "getAnnotations";
                break;
            case 12:
                objArr[2] = "getExternalAnnotations";
                break;
            case 13:
                objArr[2] = "getRegularAndExternalAnnotations";
                break;
            case 15:
            case 16:
                objArr[2] = "findAnnotation";
                break;
            default:
                objArr[2] = "isAbstract";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 5 && i != 6 && i != 7 && i != 8 && i != 10 && i != 11 && i != 14) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private JavaElementUtil() {
    }

    public static boolean isAbstract(JavaModifierListOwnerImpl javaModifierListOwnerImpl) {
        if (javaModifierListOwnerImpl == null) {
            $$$reportNull$$$0(0);
        }
        return javaModifierListOwnerImpl.getPsi().hasModifierProperty("abstract");
    }

    public static boolean isStatic(JavaModifierListOwnerImpl javaModifierListOwnerImpl) {
        if (javaModifierListOwnerImpl == null) {
            $$$reportNull$$$0(1);
        }
        return javaModifierListOwnerImpl.getPsi().hasModifierProperty("static");
    }

    public static boolean isFinal(JavaModifierListOwnerImpl javaModifierListOwnerImpl) {
        if (javaModifierListOwnerImpl == null) {
            $$$reportNull$$$0(2);
        }
        return javaModifierListOwnerImpl.getPsi().hasModifierProperty("final");
    }

    public static boolean isSealed(JavaModifierListOwnerImpl javaModifierListOwnerImpl) {
        if (javaModifierListOwnerImpl == null) {
            $$$reportNull$$$0(3);
        }
        return javaModifierListOwnerImpl.getPsi().hasModifierProperty("sealed");
    }

    public static Visibility getVisibility(JavaModifierListOwnerImpl javaModifierListOwnerImpl) {
        if (javaModifierListOwnerImpl == null) {
            $$$reportNull$$$0(4);
        }
        PsiModifierListOwner psi = javaModifierListOwnerImpl.getPsi();
        if (psi.hasModifierProperty("public")) {
            Visibilities.Public r2 = Visibilities.Public.INSTANCE;
            if (r2 == null) {
                $$$reportNull$$$0(5);
            }
            return r2;
        }
        if (psi.hasModifierProperty("private")) {
            Visibilities.Private r3 = Visibilities.Private.INSTANCE;
            if (r3 == null) {
                $$$reportNull$$$0(6);
            }
            return r3;
        }
        if (psi.hasModifierProperty("protected")) {
            Visibility visibility = javaModifierListOwnerImpl.mo7267isStatic() ? JavaVisibilities.ProtectedStaticVisibility.INSTANCE : JavaVisibilities.ProtectedAndPackage.INSTANCE;
            if (visibility == null) {
                $$$reportNull$$$0(7);
            }
            return visibility;
        }
        JavaVisibilities.PackageVisibility packageVisibility = JavaVisibilities.PackageVisibility.INSTANCE;
        if (packageVisibility == null) {
            $$$reportNull$$$0(8);
        }
        return packageVisibility;
    }

    public static Collection<JavaAnnotation> getAnnotations(JavaAnnotationOwnerImpl javaAnnotationOwnerImpl, JavaElementSourceFactory javaElementSourceFactory) {
        if (javaAnnotationOwnerImpl == null) {
            $$$reportNull$$$0(9);
        }
        PsiAnnotationOwner annotationOwnerPsi = javaAnnotationOwnerImpl.getAnnotationOwnerPsi();
        if (annotationOwnerPsi != null) {
            Collection<JavaAnnotation> collectionAnnotations = JavaElementCollectionFromPsiArrayUtil.annotations(annotationOwnerPsi.getAnnotations(), javaElementSourceFactory);
            if (collectionAnnotations == null) {
                $$$reportNull$$$0(10);
            }
            return collectionAnnotations;
        }
        List listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(11);
        }
        return listEmptyList;
    }

    private static PsiAnnotation[] getExternalAnnotations(JavaModifierListOwnerImpl javaModifierListOwnerImpl) {
        if (javaModifierListOwnerImpl == null) {
            $$$reportNull$$$0(12);
        }
        PsiModifierListOwner psi = javaModifierListOwnerImpl.getPsi();
        return ExternalAnnotationsManager.getInstance(psi.getProject()).findExternalAnnotations(psi);
    }

    static <T extends JavaAnnotationOwnerImpl & JavaModifierListOwnerImpl> Collection<JavaAnnotation> getRegularAndExternalAnnotations(T t, JavaElementSourceFactory javaElementSourceFactory) {
        if (t == null) {
            $$$reportNull$$$0(13);
        }
        PsiAnnotation[] externalAnnotations = getExternalAnnotations(t);
        if (externalAnnotations == null) {
            return getAnnotations(t, javaElementSourceFactory);
        }
        ArrayList arrayList = new ArrayList(getAnnotations(t, javaElementSourceFactory));
        arrayList.addAll(JavaElementCollectionFromPsiArrayUtil.nullabilityAnnotations(externalAnnotations, javaElementSourceFactory));
        return arrayList;
    }

    public static JavaAnnotation findAnnotation(JavaAnnotationOwnerImpl javaAnnotationOwnerImpl, FqName fqName, JavaElementSourceFactory javaElementSourceFactory) {
        PsiAnnotation psiAnnotationFindAnnotation;
        if (javaAnnotationOwnerImpl == null) {
            $$$reportNull$$$0(15);
        }
        if (fqName == null) {
            $$$reportNull$$$0(16);
        }
        PsiAnnotationOwner annotationOwnerPsi = javaAnnotationOwnerImpl.getAnnotationOwnerPsi();
        if (annotationOwnerPsi == null || (psiAnnotationFindAnnotation = annotationOwnerPsi.findAnnotation(fqName.asString())) == null) {
            return null;
        }
        return new JavaAnnotationImpl(javaElementSourceFactory.createPsiSource(psiAnnotationFindAnnotation));
    }
}
