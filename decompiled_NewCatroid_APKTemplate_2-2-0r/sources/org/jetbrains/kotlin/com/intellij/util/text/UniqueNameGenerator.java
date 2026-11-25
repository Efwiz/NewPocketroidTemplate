package org.jetbrains.kotlin.com.intellij.util.text;

import com.reandroid.archive.PathTree;
import java.util.HashSet;
import java.util.Set;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;

/* JADX INFO: loaded from: classes3.dex */
public class UniqueNameGenerator implements Condition<String> {
    private final Set<String> myExistingNames = new HashSet();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 24 || i == 25 || i == 29 || i == 35 || i == 38) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 24 || i == 25 || i == 29 || i == 35 || i == 38) ? 2 : 3];
        switch (i) {
            case 1:
            case 2:
                objArr[0] = "candidate";
                break;
            case 3:
                objArr[0] = "name";
                break;
            case 4:
            case 9:
            case 15:
            case 19:
            case 27:
            case 31:
                objArr[0] = "prefix";
                break;
            case 5:
            case 10:
            case 16:
            case 20:
            case 28:
            case 32:
                objArr[0] = "suffix";
                break;
            case 6:
            case 8:
            case 12:
            case 14:
            case 18:
            case 26:
            case 30:
            case 37:
                objArr[0] = "defaultName";
                break;
            case 7:
            case 11:
                objArr[0] = "existingNames";
                break;
            case 13:
            case 17:
            case 23:
                objArr[0] = "validator";
                break;
            case 21:
            case 33:
                objArr[0] = "beforeNumber";
                break;
            case 22:
            case 34:
                objArr[0] = "afterNumber";
                break;
            case 24:
            case 25:
            case 29:
            case 35:
            case 38:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/text/UniqueNameGenerator";
                break;
            case 36:
                objArr[0] = "result";
                break;
            default:
                objArr[0] = PathTree.NAME_elements;
                break;
        }
        if (i == 24 || i == 25 || i == 29 || i == 35 || i == 38) {
            objArr[1] = "generateUniqueName";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/text/UniqueNameGenerator";
        }
        switch (i) {
            case 1:
                objArr[2] = "value";
                break;
            case 2:
            case 3:
            case 4:
            case 5:
                objArr[2] = "isUnique";
                break;
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
            case 22:
            case 23:
            case 26:
            case 27:
            case 28:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 37:
                objArr[2] = "generateUniqueName";
                break;
            case 24:
            case 25:
            case 29:
            case 35:
            case 38:
                break;
            case 36:
                objArr[2] = "addExistingName";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 24 && i != 25 && i != 29 && i != 35 && i != 38) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
    public final boolean value(String str) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        return isUnique(str);
    }

    public final boolean isUnique(String str) {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        return !this.myExistingNames.contains(str);
    }

    public static String generateUniqueName(String str, String str2, String str3, String str4, String str5, Condition<? super String> condition) {
        String strTrim;
        if (str == null) {
            $$$reportNull$$$0(18);
        }
        if (str2 == null) {
            $$$reportNull$$$0(19);
        }
        if (str3 == null) {
            $$$reportNull$$$0(20);
        }
        if (str4 == null) {
            $$$reportNull$$$0(21);
        }
        if (str5 == null) {
            $$$reportNull$$$0(22);
        }
        if (condition == null) {
            $$$reportNull$$$0(23);
        }
        String strTrim2 = (str2 + str + str3).trim();
        if (condition.value(strTrim2)) {
            if (strTrim2 == null) {
                $$$reportNull$$$0(24);
            }
            return strTrim2;
        }
        int i = 2;
        while (true) {
            strTrim = (str2 + str + str4 + i + str5 + str3).trim();
            if (condition.value(strTrim)) {
                break;
            }
            i++;
        }
        if (strTrim == null) {
            $$$reportNull$$$0(25);
        }
        return strTrim;
    }

    public String generateUniqueName(String str, String str2, String str3) {
        if (str == null) {
            $$$reportNull$$$0(26);
        }
        if (str2 == null) {
            $$$reportNull$$$0(27);
        }
        if (str3 == null) {
            $$$reportNull$$$0(28);
        }
        String strGenerateUniqueName = generateUniqueName(str, str2, str3, "", "");
        if (strGenerateUniqueName == null) {
            $$$reportNull$$$0(29);
        }
        return strGenerateUniqueName;
    }

    public String generateUniqueName(String str, String str2, String str3, String str4, String str5) {
        if (str == null) {
            $$$reportNull$$$0(30);
        }
        if (str2 == null) {
            $$$reportNull$$$0(31);
        }
        if (str3 == null) {
            $$$reportNull$$$0(32);
        }
        if (str4 == null) {
            $$$reportNull$$$0(33);
        }
        if (str5 == null) {
            $$$reportNull$$$0(34);
        }
        String strGenerateUniqueName = generateUniqueName(str, str2, str3, str4, str5, this);
        addExistingName(strGenerateUniqueName);
        if (strGenerateUniqueName == null) {
            $$$reportNull$$$0(35);
        }
        return strGenerateUniqueName;
    }

    public void addExistingName(String str) {
        if (str == null) {
            $$$reportNull$$$0(36);
        }
        this.myExistingNames.add(str);
    }

    public String generateUniqueName(String str) {
        if (str == null) {
            $$$reportNull$$$0(37);
        }
        String strGenerateUniqueName = generateUniqueName(str, "", "");
        if (strGenerateUniqueName == null) {
            $$$reportNull$$$0(38);
        }
        return strGenerateUniqueName;
    }
}
