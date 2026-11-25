package org.jetbrains.kotlin.com.intellij.psi;

import com.reandroid.arsc.container.SpecTypePair;
import io.netty.util.internal.StringUtil;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiNameHelper {
    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("(?:\\s)|(?:/\\*.*\\*/)|(?://[^\\n]*)");

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 1:
            case 3:
            case 6:
            case 7:
            case 9:
            case 10:
            case 12:
            case 13:
            case 15:
            case 16:
            case 17:
            case 18:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 2:
            case 4:
            case 5:
            case 8:
            case 11:
            case 14:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 1:
            case 3:
            case 6:
            case 7:
            case 9:
            case 10:
            case 12:
            case 13:
            case 15:
            case 16:
            case 17:
            case 18:
                i2 = 2;
                break;
            case 2:
            case 4:
            case 5:
            case 8:
            case 11:
            case 14:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 3:
            case 6:
            case 7:
            case 9:
            case 10:
            case 12:
            case 13:
            case 15:
            case 16:
            case 17:
            case 18:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiNameHelper";
                break;
            case 2:
                objArr[0] = "ref";
                break;
            case 4:
            case 24:
            case 26:
                objArr[0] = "annotations";
                break;
            case 5:
            case 22:
                objArr[0] = SpecTypePair.NAME_types;
                break;
            case 8:
            case 11:
            case 14:
            default:
                objArr[0] = "referenceText";
                break;
            case 19:
                objArr[0] = "subpackageName";
                break;
            case 20:
                objArr[0] = "packageName";
                break;
            case 21:
            case 23:
            case 25:
                objArr[0] = "sb";
                break;
            case 27:
                objArr[0] = "name";
                break;
            case 28:
                objArr[0] = "context";
                break;
        }
        switch (i) {
            case 1:
                objArr[1] = "getShortClassName";
                break;
            case 2:
            case 4:
            case 5:
            case 8:
            case 11:
            case 14:
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiNameHelper";
                break;
            case 3:
            case 6:
                objArr[1] = "getPresentableText";
                break;
            case 7:
                objArr[1] = "getOuterClassReference";
                break;
            case 9:
            case 10:
                objArr[1] = "getQualifiedClassName";
                break;
            case 12:
            case 13:
                objArr[1] = "removeWhitespace";
                break;
            case 15:
            case 16:
            case 17:
            case 18:
                objArr[1] = "getClassParametersText";
                break;
        }
        switch (i) {
            case 1:
            case 3:
            case 6:
            case 7:
            case 9:
            case 10:
            case 12:
            case 13:
            case 15:
            case 16:
            case 17:
            case 18:
                break;
            case 2:
            case 4:
            case 5:
                objArr[2] = "getPresentableText";
                break;
            case 8:
                objArr[2] = "getQualifiedClassName";
                break;
            case 11:
                objArr[2] = "removeWhitespace";
                break;
            case 14:
                objArr[2] = "getClassParametersText";
                break;
            case 19:
            case 20:
                objArr[2] = "isSubpackageOf";
                break;
            case 21:
            case 22:
                objArr[2] = "appendTypeArgs";
                break;
            case 23:
            case 24:
            case 25:
            case 26:
                objArr[2] = "appendAnnotations";
                break;
            case 27:
            case 28:
                objArr[2] = "isValidModuleName";
                break;
            default:
                objArr[2] = "getShortClassName";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 1:
            case 3:
            case 6:
            case 7:
            case 9:
            case 10:
            case 12:
            case 13:
            case 15:
            case 16:
            case 17:
            case 18:
                throw new IllegalStateException(str2);
            case 2:
            case 4:
            case 5:
            case 8:
            case 11:
            case 14:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    public abstract boolean isIdentifier(String str);

    public abstract boolean isIdentifier(String str, LanguageLevel languageLevel);

    public abstract boolean isKeyword(String str);

    public abstract boolean isQualifiedName(String str);

    public static PsiNameHelper getInstance(Project project) {
        return (PsiNameHelper) project.getService(PsiNameHelper.class);
    }

    /* JADX WARN: Code duplicated, block: B:30:0x004b  */
    /* JADX WARN: Code duplicated, block: B:31:0x004e  */
    public static String getShortClassName(String str) {
        int i = 0;
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        int length = str.length();
        int length2 = str.length() - 1;
        loop0: while (length2 >= 0) {
            char cCharAt = str.charAt(length2);
            if (cCharAt != '(') {
                if (cCharAt != ')') {
                    if (cCharAt != '.') {
                        if (cCharAt == '<') {
                            i--;
                        } else if (cCharAt == '>') {
                            i++;
                        } else if (cCharAt != '@') {
                            if (Character.isWhitespace(cCharAt) && i <= 0) {
                                for (int i2 = length2 + 1; i2 < length; i2++) {
                                    if (!Character.isWhitespace(str.charAt(i2))) {
                                        break loop0;
                                    }
                                }
                            }
                        }
                        length = length2;
                    }
                    if (i <= 0) {
                        break;
                    }
                } else {
                    i++;
                }
            } else {
                i--;
                length = length2;
            }
            length2--;
        }
        String strTrim = str.substring(length2 + 1, length).trim();
        if (strTrim == null) {
            $$$reportNull$$$0(1);
        }
        return strTrim;
    }

    public static String getPresentableText(PsiJavaCodeReferenceElement psiJavaCodeReferenceElement) {
        if (psiJavaCodeReferenceElement == null) {
            $$$reportNull$$$0(2);
        }
        String referenceName = psiJavaCodeReferenceElement.getReferenceName();
        PsiType[] typeParameters = psiJavaCodeReferenceElement.getTypeParameters();
        if (typeParameters.length == 0) {
            return referenceName != null ? referenceName : "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(referenceName);
        appendTypeArgs(sb, typeParameters, false, false);
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(3);
        }
        return string;
    }

    public static String getPresentableText(String str, PsiAnnotation[] psiAnnotationArr, PsiType[] psiTypeArr) {
        if (psiAnnotationArr == null) {
            $$$reportNull$$$0(4);
        }
        if (psiTypeArr == null) {
            $$$reportNull$$$0(5);
        }
        if (psiTypeArr.length == 0 && psiAnnotationArr.length == 0) {
            return str != null ? str : "";
        }
        StringBuilder sb = new StringBuilder();
        appendAnnotations(sb, psiAnnotationArr, false);
        sb.append(str);
        appendTypeArgs(sb, psiTypeArr, false, true);
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(6);
        }
        return string;
    }

    public static String getOuterClassReference(String str) {
        int i = 0;
        for (int length = str.length() - 1; length >= 0; length--) {
            char cCharAt = str.charAt(length);
            if (cCharAt == '.') {
                if (i == 0) {
                    String strSubstring = str.substring(0, length);
                    if (strSubstring == null) {
                        $$$reportNull$$$0(7);
                    }
                    return strSubstring;
                }
            } else if (cCharAt == '<') {
                i--;
            } else if (cCharAt == '>') {
                i++;
            }
        }
        return "";
    }

    public static String getQualifiedClassName(String str, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        if (z) {
            str = removeWhitespace(str);
        }
        if (str.indexOf(60) < 0) {
            if (str == null) {
                $$$reportNull$$$0(9);
            }
            return str;
        }
        StringBuilder sb = new StringBuilder(str.length());
        char[] charArray = str.toCharArray();
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < charArray.length; i3++) {
            char c = charArray[i3];
            if (c == '<') {
                i++;
                if (i == 1) {
                    sb.append(new String(charArray, i2, i3 - i2));
                }
            } else if (c == '>') {
                i--;
                i2 = i3 + 1;
            }
        }
        if (i == 0) {
            sb.append(new String(charArray, i2, charArray.length - i2));
        }
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(10);
        }
        return string;
    }

    private static String removeWhitespace(String str) {
        if (str == null) {
            $$$reportNull$$$0(11);
        }
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '/' || Character.isWhitespace(cCharAt)) {
                String strReplaceAll = WHITESPACE_PATTERN.matcher(str).replaceAll("");
                if (strReplaceAll == null) {
                    $$$reportNull$$$0(13);
                }
                return strReplaceAll;
            }
        }
        if (str == null) {
            $$$reportNull$$$0(12);
        }
        return str;
    }

    /* JADX WARN: Code duplicated, block: B:41:0x0065 A[PHI: r3
      0x0065: PHI (r3v11 int) = (r3v2 int), (r3v13 int) binds: [B:40:0x0063, B:38:0x0060] A[DONT_GENERATE, DONT_INLINE]] */
    public static String[] getClassParametersText(String str) {
        int i;
        if (str == null) {
            $$$reportNull$$$0(14);
        }
        if (str.indexOf(60) < 0) {
            String[] strArr = ArrayUtilRt.EMPTY_STRING_ARRAY;
            if (strArr == null) {
                $$$reportNull$$$0(15);
            }
            return strArr;
        }
        char[] charArray = str.toCharArray();
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < charArray.length; i5++) {
            char c = charArray[i5];
            if (c != '.') {
                if (c == '<') {
                    i3++;
                } else if (c == '>') {
                    i3--;
                }
            } else if (i3 == 0) {
                i4 = i5 + 1;
            }
        }
        if (i3 != 0) {
            String[] strArr2 = ArrayUtilRt.EMPTY_STRING_ARRAY;
            if (strArr2 == null) {
                $$$reportNull$$$0(16);
            }
            return strArr2;
        }
        int i6 = 0;
        for (int i7 = i4; i7 < charArray.length; i7++) {
            char c2 = charArray[i7];
            if (c2 != ',') {
                if (c2 == '<') {
                    i3++;
                    if (i3 == 1) {
                        i6++;
                    }
                } else if (c2 == '>') {
                    i3--;
                }
            } else if (i3 == 1) {
                i6++;
            }
        }
        if (i3 != 0 || i6 == 0) {
            String[] strArr3 = ArrayUtilRt.EMPTY_STRING_ARRAY;
            if (strArr3 == null) {
                $$$reportNull$$$0(17);
            }
            return strArr3;
        }
        String[] strArr4 = new String[i6];
        int i8 = i3;
        int i9 = 0;
        for (int i10 = i4; i10 < charArray.length; i10++) {
            char c3 = charArray[i10];
            if (c3 != ',') {
                if (c3 == '<') {
                    i8++;
                    if (i8 == 1) {
                        i9 = i10;
                    }
                } else if (c3 == '>' && (i8 = i8 - 1) == 0) {
                    i = i2 + 1;
                    strArr4[i2] = new String(charArray, i9 + 1, (i10 - i9) - 1);
                    i2 = i;
                }
            } else if (i8 == 1) {
                i = i2 + 1;
                strArr4[i2] = new String(charArray, i9 + 1, (i10 - i9) - 1);
                i9 = i10;
                i2 = i;
            }
        }
        return strArr4;
    }

    public static boolean isSubpackageOf(String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(19);
        }
        if (str2 == null) {
            $$$reportNull$$$0(20);
        }
        return str.equals(str2) || (str.startsWith(str2) && str.charAt(str2.length()) == '.');
    }

    public static void appendTypeArgs(StringBuilder sb, PsiType[] psiTypeArr, boolean z, boolean z2) {
        if (sb == null) {
            $$$reportNull$$$0(21);
        }
        if (psiTypeArr == null) {
            $$$reportNull$$$0(22);
        }
        if (psiTypeArr.length == 0) {
            return;
        }
        sb.append('<');
        for (int i = 0; i < psiTypeArr.length; i++) {
            if (i > 0) {
                sb.append(z ? "," : ", ");
            }
            PsiType psiType = psiTypeArr[i];
            if (z) {
                sb.append(psiType.getCanonicalText(z2));
            } else {
                sb.append(psiType.getPresentableText(z2));
            }
        }
        sb.append('>');
    }

    public static boolean appendAnnotations(StringBuilder sb, PsiAnnotation[] psiAnnotationArr, boolean z) {
        if (sb == null) {
            $$$reportNull$$$0(23);
        }
        if (psiAnnotationArr == null) {
            $$$reportNull$$$0(24);
        }
        return appendAnnotations(sb, (List<? extends PsiAnnotation>) Arrays.asList(psiAnnotationArr), z);
    }

    public static boolean appendAnnotations(StringBuilder sb, List<? extends PsiAnnotation> list, boolean z) {
        if (sb == null) {
            $$$reportNull$$$0(25);
        }
        if (list == null) {
            $$$reportNull$$$0(26);
        }
        boolean z2 = false;
        for (PsiAnnotation psiAnnotation : list) {
            if (z) {
                String qualifiedName = psiAnnotation.getQualifiedName();
                if (qualifiedName != null) {
                    sb.append('@').append(qualifiedName).append(psiAnnotation.getParameterList().getText()).append(StringUtil.SPACE);
                    z2 = true;
                }
            } else {
                PsiJavaCodeReferenceElement nameReferenceElement = psiAnnotation.getNameReferenceElement();
                if (nameReferenceElement != null) {
                    sb.append('@').append(nameReferenceElement.getText()).append(StringUtil.SPACE);
                    z2 = true;
                }
            }
        }
        return z2;
    }

    public static boolean isValidModuleName(String str, PsiElement psiElement) {
        if (str == null) {
            $$$reportNull$$$0(27);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(28);
        }
        final PsiNameHelper psiNameHelper = getInstance(psiElement.getProject());
        final LanguageLevel languageLevel = PsiUtil.getLanguageLevel(psiElement);
        return org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil.split(str, ".", true, false).stream().allMatch(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiNameHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.isIdentifier((String) obj, languageLevel);
            }
        });
    }
}
