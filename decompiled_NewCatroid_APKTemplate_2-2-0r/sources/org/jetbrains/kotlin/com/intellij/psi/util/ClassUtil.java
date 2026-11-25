package org.jetbrains.kotlin.com.intellij.psi.util;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Optional;
import java.util.function.Supplier;
import okhttp3.HttpUrl;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaLanguage;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.JavaDirectoryService;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaRecursiveElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock;
import org.jetbrains.kotlin.com.intellij.psi.PsiDeclarationStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiDirectory;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor;
import org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.containers.ObjectIntHashMap;
import org.jetbrains.kotlin.com.intellij.util.containers.ObjectIntMap;

/* JADX INFO: loaded from: classes3.dex */
public final class ClassUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 9 || i == 26) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 9 || i == 26) ? 2 : 3];
        switch (i) {
            case 1:
            case 9:
            case 26:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/util/ClassUtil";
                break;
            case 2:
            case 19:
            case 22:
                objArr[0] = "aClass";
                break;
            case 3:
                objArr[0] = "buf";
                break;
            case 4:
                objArr[0] = "psiClass";
                break;
            case 5:
            case 7:
                objArr[0] = "containingClass";
                break;
            case 6:
            case 8:
                objArr[0] = "indexName";
                break;
            case 10:
            case 12:
            case 14:
            case 20:
                objArr[0] = "manager";
                break;
            case 11:
            case 13:
            case 15:
            case 17:
                objArr[0] = "name";
                break;
            case 16:
                objArr[0] = "scope";
                break;
            case 18:
                objArr[0] = "parent";
                break;
            case 21:
                objArr[0] = "jvmClassName";
                break;
            case 23:
            case 24:
            case 25:
                objArr[0] = "psiType";
                break;
            default:
                objArr[0] = "fqName";
                break;
        }
        if (i == 1) {
            objArr[1] = "extractClassName";
        } else if (i == 9) {
            objArr[1] = "getDigitPrefix";
        } else if (i != 26) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/util/ClassUtil";
        } else {
            objArr[1] = "toBinary";
        }
        switch (i) {
            case 1:
            case 9:
            case 26:
                break;
            case 2:
            case 3:
                objArr[2] = "formatClassName";
                break;
            case 4:
            case 5:
                objArr[2] = "getNonQualifiedClassIdx";
                break;
            case 6:
            case 7:
                objArr[2] = "findNonQualifiedClassByIndex";
                break;
            case 8:
                objArr[2] = "getDigitPrefix";
                break;
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
                objArr[2] = "findPsiClass";
                break;
            case 17:
            case 18:
                objArr[2] = "findSubClass";
                break;
            case 19:
                objArr[2] = "getJVMClassName";
                break;
            case 20:
            case 21:
                objArr[2] = "findPsiClassByJVMName";
                break;
            case 22:
                objArr[2] = "isTopLevelClass";
                break;
            case 23:
                objArr[2] = "getClassObjectPresentation";
                break;
            case 24:
                objArr[2] = "getBinaryPresentation";
                break;
            case 25:
                objArr[2] = "toBinary";
                break;
            default:
                objArr[2] = "extractClassName";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 9 && i != 26) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private ClassUtil() {
    }

    public static String extractPackageName(String str) {
        if (str == null) {
            return null;
        }
        int iLastIndexOf = str.lastIndexOf(46);
        return iLastIndexOf == -1 ? "" : str.substring(0, iLastIndexOf);
    }

    public static String extractClassName(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        int iLastIndexOf = str.lastIndexOf(46);
        if (iLastIndexOf != -1) {
            str = str.substring(iLastIndexOf + 1);
        }
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        return str;
    }

    public static String createNewClassQualifiedName(String str, String str2) {
        if (str2 == null) {
            return null;
        }
        return (str == null || str.isEmpty()) ? str2 : str + "." + extractClassName(str2);
    }

    public static PsiDirectory sourceRoot(PsiDirectory psiDirectory) {
        while (psiDirectory != null) {
            if (JavaDirectoryService.getInstance().isSourceRoot(psiDirectory)) {
                return psiDirectory;
            }
            psiDirectory = psiDirectory.getParentDirectory();
        }
        return null;
    }

    public static void formatClassName(PsiClass psiClass, StringBuilder sb) {
        if (psiClass == null) {
            $$$reportNull$$$0(2);
        }
        if (sb == null) {
            $$$reportNull$$$0(3);
        }
        String qualifiedName = psiClass.getQualifiedName();
        if (qualifiedName != null) {
            sb.append(qualifiedName);
            return;
        }
        PsiClass psiClass2 = (PsiClass) PsiTreeUtil.getContextOfType((PsiElement) psiClass, PsiClass.class, true);
        if (psiClass2 != null) {
            formatClassName(psiClass2, sb);
            sb.append("$");
            sb.append(getNonQualifiedClassIdx(psiClass, psiClass2));
            String name2 = psiClass.getName();
            if (name2 != null) {
                sb.append(name2);
            }
        }
    }

    private static int getNonQualifiedClassIdx(PsiClass psiClass, final PsiClass psiClass2) {
        if (psiClass == null) {
            $$$reportNull$$$0(4);
        }
        if (psiClass2 == null) {
            $$$reportNull$$$0(5);
        }
        return ((ObjectIntMap) CachedValuesManager.getCachedValue((PsiElement) psiClass2, new CachedValueProvider() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.ClassUtil$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result compute() {
                return ClassUtil.lambda$getNonQualifiedClassIdx$0(psiClass2);
            }
        })).get(psiClass);
    }

    static /* synthetic */ CachedValueProvider.Result lambda$getNonQualifiedClassIdx$0(PsiClass psiClass) {
        ObjectIntHashMap objectIntHashMap = new ObjectIntHashMap();
        int i = 0;
        for (PsiClass psiClass2 : SyntaxTraverser.psiTraverser().withRoot(psiClass).postOrderDfsTraversal().filter(PsiClass.class)) {
            if (psiClass2.getQualifiedName() == null) {
                i++;
                objectIntHashMap.put(psiClass2, i);
            }
        }
        return CachedValueProvider.Result.create(objectIntHashMap, psiClass);
    }

    public static PsiClass findNonQualifiedClassByIndex(String str, final PsiClass psiClass, final boolean z) {
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        if (psiClass == null) {
            $$$reportNull$$$0(7);
        }
        String digitPrefix = getDigitPrefix(str);
        final int i = !digitPrefix.isEmpty() ? Integer.parseInt(digitPrefix) : -1;
        final String strSubstring = digitPrefix.length() < str.length() ? str.substring(digitPrefix.length()) : null;
        final PsiClass[] psiClassArr = new PsiClass[1];
        psiClass.accept(new JavaRecursiveElementVisitor() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.ClassUtil.1
            private int myCurrentIdx;

            private static /* synthetic */ void $$$reportNull$$$0(int i2) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "org/jetbrains/kotlin/com/intellij/psi/util/ClassUtil$1", "visitElement"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.JavaRecursiveElementVisitor, org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor
            public void visitElement(PsiElement psiElement) {
                if (psiElement == null) {
                    $$$reportNull$$$0(0);
                }
                if (psiClassArr[0] == null) {
                    super.visitElement(psiElement);
                }
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
            public void visitClass(PsiClass psiClass2) {
                if (!z) {
                    super.visitClass(psiClass2);
                    if (psiClass2.getQualifiedName() == null) {
                        int i2 = this.myCurrentIdx + 1;
                        this.myCurrentIdx = i2;
                        if (i2 == i && Comparing.strEqual(strSubstring, psiClass2.getName())) {
                            psiClassArr[0] = psiClass2;
                            return;
                        }
                        return;
                    }
                    return;
                }
                if (psiClass2 == psiClass) {
                    super.visitClass(psiClass2);
                    return;
                }
                if (Comparing.strEqual(strSubstring, psiClass2.getName())) {
                    int i3 = this.myCurrentIdx + 1;
                    this.myCurrentIdx = i3;
                    int i4 = i;
                    if (i3 == i4 || i4 == -1) {
                        psiClassArr[0] = psiClass2;
                    }
                }
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
            public void visitTypeParameter(PsiTypeParameter psiTypeParameter) {
                if (!z) {
                    super.visitTypeParameter(psiTypeParameter);
                } else {
                    visitElement(psiTypeParameter);
                }
            }
        });
        return psiClassArr[0];
    }

    private static String getDigitPrefix(String str) {
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        int i = 0;
        while (i < str.length() && Character.isDigit(str.charAt(i))) {
            i++;
        }
        String strSubstring = i == 0 ? "" : str.substring(0, i);
        if (strSubstring == null) {
            $$$reportNull$$$0(9);
        }
        return strSubstring;
    }

    public static PsiClass findPsiClass(PsiManager psiManager, String str) {
        if (psiManager == null) {
            $$$reportNull$$$0(10);
        }
        if (str == null) {
            $$$reportNull$$$0(11);
        }
        return findPsiClass(psiManager, str, null, false);
    }

    public static PsiClass findPsiClass(PsiManager psiManager, String str, PsiClass psiClass, boolean z) {
        if (psiManager == null) {
            $$$reportNull$$$0(12);
        }
        if (str == null) {
            $$$reportNull$$$0(13);
        }
        return findPsiClass(psiManager, str, psiClass, z, GlobalSearchScope.allScope(psiManager.getProject()));
    }

    public static PsiClass findPsiClass(PsiManager psiManager, String str, PsiClass psiClass, boolean z, GlobalSearchScope globalSearchScope) {
        PsiClass psiClassFindSubClass;
        if (psiManager == null) {
            $$$reportNull$$$0(14);
        }
        if (str == null) {
            $$$reportNull$$$0(15);
        }
        if (globalSearchScope == null) {
            $$$reportNull$$$0(16);
        }
        if (psiClass != null) {
            return findSubClass(str, psiClass, z);
        }
        PsiClass psiClassFindClass = JavaPsiFacade.getInstance(psiManager.getProject()).findClass(str, globalSearchScope);
        if (psiClassFindClass != null) {
            return psiClassFindClass;
        }
        int iIndexOf = 0;
        while (true) {
            iIndexOf = str.indexOf(36, iIndexOf + 1);
            if (iIndexOf <= 0 || iIndexOf >= str.length() - 1) {
                return null;
            }
            PsiClass psiClassFindClass2 = JavaPsiFacade.getInstance(psiManager.getProject()).findClass(str.substring(0, iIndexOf), globalSearchScope);
            if (psiClassFindClass2 != null && (psiClassFindSubClass = findSubClass(str.substring(iIndexOf + 1), psiClassFindClass2, z)) != null) {
                return psiClassFindSubClass;
            }
        }
    }

    private static PsiClass findSubClass(String str, PsiClass psiClass, boolean z) {
        PsiClass psiClassFindSubClass;
        if (str == null) {
            $$$reportNull$$$0(17);
        }
        if (psiClass == null) {
            $$$reportNull$$$0(18);
        }
        PsiClass psiClassFindNonQualifiedClassByIndex = isIndexed(str) ? findNonQualifiedClassByIndex(str, psiClass, z) : psiClass.findInnerClassByName(str, false);
        if (psiClassFindNonQualifiedClassByIndex != null) {
            return psiClassFindNonQualifiedClassByIndex;
        }
        int iIndexOf = 0;
        while (true) {
            iIndexOf = str.indexOf(36, iIndexOf + 1);
            if (iIndexOf <= 0 || iIndexOf >= str.length() - 1) {
                return null;
            }
            String strSubstring = str.substring(0, iIndexOf);
            PsiClass psiClassFindNonQualifiedClassByIndex2 = isIndexed(strSubstring) ? findNonQualifiedClassByIndex(strSubstring, psiClass, z) : psiClass.findInnerClassByName(strSubstring, false);
            if (psiClassFindNonQualifiedClassByIndex2 != null && (psiClassFindSubClass = findSubClass(str.substring(iIndexOf + 1), psiClassFindNonQualifiedClassByIndex2, z)) != null) {
                return psiClassFindSubClass;
            }
        }
    }

    private static boolean isIndexed(String str) {
        return Character.isDigit(str.charAt(0));
    }

    public static String getJVMClassName(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(19);
        }
        PsiClass psiClassMo6092getContainingClass = psiClass.getContainingClass();
        if (psiClassMo6092getContainingClass != null) {
            String jVMClassName = getJVMClassName(psiClassMo6092getContainingClass);
            if (jVMClassName == null) {
                return null;
            }
            return jVMClassName + "$" + psiClass.getName();
        }
        return psiClass.getQualifiedName();
    }

    public static PsiClass findPsiClassByJVMName(PsiManager psiManager, String str) {
        if (psiManager == null) {
            $$$reportNull$$$0(20);
        }
        if (str == null) {
            $$$reportNull$$$0(21);
        }
        return findPsiClass(psiManager, str.replace('/', '.'), null, true);
    }

    public static boolean isTopLevelClass(PsiClass psiClass) {
        PsiFile containingFile;
        if (psiClass == null) {
            $$$reportNull$$$0(22);
        }
        if (psiClass.getContainingClass() != null || (psiClass instanceof PsiAnonymousClass)) {
            return false;
        }
        PsiElement parent = psiClass.getLightParent();
        return (((parent instanceof PsiDeclarationStatement) && (parent.getLightParent() instanceof PsiCodeBlock)) || (containingFile = psiClass.getContainingFile()) == null || containingFile.getLanguage() != JavaLanguage.INSTANCE) ? false : true;
    }

    public static String getAsmMethodSignature(PsiMethod psiMethod) {
        StringBuilder sb = new StringBuilder("(");
        for (PsiParameter psiParameter : psiMethod.getParameterList().getParameters()) {
            sb.append(getBinaryPresentation(psiParameter.getType()));
        }
        sb.append(Constant.AFTER_QUTO);
        sb.append(getBinaryPresentation((PsiType) Optional.ofNullable(psiMethod.getReturnType()).orElse(PsiType.VOID)));
        return sb.toString();
    }

    public static String getVMParametersMethodSignature(PsiMethod psiMethod) {
        return StringUtil.join(psiMethod.getParameterList().getParameters(), new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.ClassUtil$$ExternalSyntheticLambda4
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return ClassUtil.lambda$getVMParametersMethodSignature$1((PsiParameter) obj);
            }
        }, ",");
    }

    static /* synthetic */ String lambda$getVMParametersMethodSignature$1(PsiParameter psiParameter) {
        PsiType psiTypeErasure = TypeConversionUtil.erasure(psiParameter.getType());
        return psiTypeErasure != null ? (String) psiTypeErasure.accept(createSignatureVisitor()) : "";
    }

    private static PsiTypeVisitor<String> createSignatureVisitor() {
        return new PsiTypeVisitor<String>() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.ClassUtil.2
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i == 1) {
                    objArr[0] = "classType";
                } else if (i != 2) {
                    objArr[0] = "primitiveType";
                } else {
                    objArr[0] = "arrayType";
                }
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/util/ClassUtil$2";
                if (i == 1) {
                    objArr[2] = "visitClassType";
                } else if (i != 2) {
                    objArr[2] = "visitPrimitiveType";
                } else {
                    objArr[2] = "visitArrayType";
                }
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public String visitPrimitiveType(PsiPrimitiveType psiPrimitiveType) {
                if (psiPrimitiveType == null) {
                    $$$reportNull$$$0(0);
                }
                return psiPrimitiveType.getCanonicalText();
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public String visitClassType(PsiClassType psiClassType) {
                if (psiClassType == null) {
                    $$$reportNull$$$0(1);
                }
                PsiClass psiClassResolve = psiClassType.resolve();
                if (psiClassResolve == null) {
                    return "";
                }
                return ClassUtil.getJVMClassName(psiClassResolve);
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public String visitArrayType(PsiArrayType psiArrayType) {
                if (psiArrayType == null) {
                    $$$reportNull$$$0(2);
                }
                PsiType componentType = psiArrayType.getComponentType();
                String str = (String) componentType.accept(this);
                if (psiArrayType.getDeepComponentType() instanceof PsiPrimitiveType) {
                    return str + HttpUrl.PATH_SEGMENT_ENCODE_SET_URI;
                }
                if (componentType instanceof PsiClassType) {
                    str = "L" + str + ";";
                }
                return "[" + str;
            }
        };
    }

    public static String getClassObjectPresentation(PsiType psiType) {
        if (psiType == null) {
            $$$reportNull$$$0(23);
        }
        return toBinary(psiType, false);
    }

    public static String getBinaryPresentation(PsiType psiType) {
        if (psiType == null) {
            $$$reportNull$$$0(24);
        }
        return toBinary(psiType, true);
    }

    private static String toBinary(final PsiType psiType, final boolean z) {
        if (psiType == null) {
            $$$reportNull$$$0(25);
        }
        String str = (String) Optional.of(psiType).map(new java.util.function.Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.ClassUtil$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return TypeConversionUtil.erasure((PsiType) obj);
            }
        }).map(new java.util.function.Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.ClassUtil$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ClassUtil.lambda$toBinary$3(z, (PsiType) obj);
            }
        }).orElseGet(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.ClassUtil$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                return psiType.getPresentableText();
            }
        });
        if (str == null) {
            $$$reportNull$$$0(26);
        }
        return str;
    }

    static /* synthetic */ String lambda$toBinary$3(boolean z, PsiType psiType) {
        return (String) psiType.accept(createBinarySignatureVisitor(z));
    }

    private static PsiTypeVisitor<String> createBinarySignatureVisitor(final boolean z) {
        return new PsiTypeVisitor<String>() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.ClassUtil.3
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i == 1) {
                    objArr[0] = "classType";
                } else if (i != 2) {
                    objArr[0] = "primitiveType";
                } else {
                    objArr[0] = "arrayType";
                }
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/util/ClassUtil$3";
                if (i == 1) {
                    objArr[2] = "visitClassType";
                } else if (i != 2) {
                    objArr[2] = "visitPrimitiveType";
                } else {
                    objArr[2] = "visitArrayType";
                }
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public String visitPrimitiveType(PsiPrimitiveType psiPrimitiveType) {
                if (psiPrimitiveType == null) {
                    $$$reportNull$$$0(0);
                }
                return psiPrimitiveType.getKind().getBinaryName();
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public String visitClassType(PsiClassType psiClassType) {
                if (psiClassType == null) {
                    $$$reportNull$$$0(1);
                }
                PsiClass psiClassResolve = psiClassType.resolve();
                if (psiClassResolve == null) {
                    return "";
                }
                String jVMClassName = ClassUtil.getJVMClassName(psiClassResolve);
                if (jVMClassName == null) {
                    return jVMClassName;
                }
                StringBuilder sb = new StringBuilder("L");
                if (z) {
                    jVMClassName = jVMClassName.replace(".", "/");
                }
                return sb.append(jVMClassName).append(";").toString();
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeVisitor
            public String visitArrayType(PsiArrayType psiArrayType) {
                if (psiArrayType == null) {
                    $$$reportNull$$$0(2);
                }
                return "[" + ((String) psiArrayType.getComponentType().accept(this));
            }
        };
    }
}
