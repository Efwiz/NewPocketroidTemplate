package com.reandroid.apkeditor.refactor;

import com.reandroid.apkeditor.utils.StringHelper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes10.dex */
public class RefactorUtil {
    public static final String RES_DIR = "res";
    private static final Pattern PATTERN_GOOD_NAME = Pattern.compile("^[A-Za-z]{2,15}[_.A-Za-z0-9]*$");
    private static final Pattern PATTERN_GENERATED_NAME = Pattern.compile("^.+_(0x[0-9a-f]{7,8})$");

    public static boolean isObfuscated(Collection<String> collection) {
        return hasDuplicates(collection) || isAllEqualLength(collection) || isAllShortLength(collection) || isSequentialNames(collection) || !isAllGoodName(collection);
    }

    private static boolean hasDuplicates(Collection<String> collection) {
        return ((collection instanceof HashSet) || new HashSet(collection).size() == collection.size()) ? false : true;
    }

    private static boolean isAllEqualLength(Collection<String> collection) {
        Iterator<String> it = collection.iterator();
        int i = 0;
        while (it.hasNext()) {
            int length = it.next().length();
            if (i != 0 && length != i) {
                return false;
            }
            i = length;
        }
        return i != 0;
    }

    private static boolean isAllShortLength(Collection<String> collection) {
        Iterator<String> it = collection.iterator();
        int i = 0;
        while (it.hasNext()) {
            int length = it.next().length();
            if (i == 0) {
                i = length;
            } else if (length > 3) {
                return false;
            }
        }
        return collection.size() > 10;
    }

    public static boolean isSequentialNames(Collection<String> collection) {
        if (collection.size() == 0) {
            return false;
        }
        List<String> listSortAscending = StringHelper.sortAscending(new ArrayList(collection));
        Iterator<String> it = listSortAscending.iterator();
        int i = 0;
        int i2 = 0;
        while (it.hasNext()) {
            int iHashCode = it.next().hashCode();
            if (i2 != 0 && iHashCode != i2 + 1) {
                i++;
            }
            i2 = iHashCode;
        }
        return i == 0 || i < listSortAscending.size() / 2;
    }

    public static boolean isGeneratedName(String str, int i) {
        return str.endsWith(String.format("0x%08x", Integer.valueOf(i)));
    }

    public static boolean isGeneratedName(String str) {
        return PATTERN_GENERATED_NAME.matcher(str).matches();
    }

    public static String generateUniqueName(String str, int i) {
        return str + "_" + String.format("0x%08x", Integer.valueOf(i));
    }

    public static boolean isAllGoodName(Collection<String> collection) {
        Iterator<String> it = collection.iterator();
        while (it.hasNext()) {
            if (!PATTERN_GOOD_NAME.matcher(it.next()).matches()) {
                return false;
            }
        }
        return collection.size() > 0;
    }

    public static boolean isGoodName(String str) {
        if (str == null) {
            return false;
        }
        return PATTERN_GOOD_NAME.matcher(str).matches();
    }
}
