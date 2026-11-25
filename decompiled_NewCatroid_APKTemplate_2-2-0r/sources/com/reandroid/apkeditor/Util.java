package com.reandroid.apkeditor;

import com.reandroid.apk.ApkModule;
import com.reandroid.arsc.chunk.TableBlock;
import com.reandroid.arsc.item.IntegerItem;
import com.reandroid.arsc.item.TableString;
import com.reandroid.arsc.pool.TableStringPool;
import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Properties;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes10.dex */
public class Util {
    public static final String EDIT_TYPE_PROTECTED = "PROTECTED";

    public static boolean isHelp(String[] strArr) {
        if (isEmpty(strArr)) {
            return true;
        }
        return isHelp(strArr[0]);
    }

    public static boolean isVersion(String[] strArr) {
        if (isEmpty(strArr)) {
            return false;
        }
        return isVersion(strArr[0]);
    }

    public static boolean isVersion(String str) {
        if (isEmpty(str)) {
            return false;
        }
        String strTrim = str.toLowerCase().trim();
        return strTrim.equals("-version") || strTrim.equals("--version");
    }

    public static boolean containsHelp(String[] strArr) {
        if (isEmpty(strArr)) {
            return false;
        }
        for (String str : strArr) {
            if (isHelp(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isHelp(String str) {
        if (isEmpty(str)) {
            return false;
        }
        String strTrim = str.toLowerCase().trim();
        return strTrim.equals("-h") || strTrim.equals("-help") || strTrim.equals("--h") || strTrim.equals("--help");
    }

    public static String[] trimNull(String[] strArr) {
        if (isEmpty(strArr)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (String str : strArr) {
            if (!isEmpty(str)) {
                arrayList.add(str);
            }
        }
        return (String[]) arrayList.toArray(new String[0]);
    }

    public static boolean isEmpty(String[] strArr) {
        if (strArr != null && strArr.length != 0) {
            for (String str : strArr) {
                if (!isEmpty(str)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static File ensureUniqueFile(File file) {
        String strSubstring;
        File file2;
        int iLastIndexOf;
        if (!file.exists()) {
            return file;
        }
        File parentFile = file.getParentFile();
        String name2 = file.getName();
        if (file.isFile() && (iLastIndexOf = name2.lastIndexOf(46)) > 0) {
            strSubstring = name2.substring(iLastIndexOf);
            name2 = name2.substring(0, iLastIndexOf);
        } else {
            strSubstring = "";
        }
        for (int i = 1; i < 1000 && file.exists(); i++) {
            String str = name2 + "_" + i + strSubstring;
            if (parentFile == null) {
                file2 = new File(str);
            } else {
                file2 = new File(parentFile, str);
            }
            file = file2;
        }
        return file;
    }

    public static void deleteDir(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
                return;
            }
            if (file.isDirectory()) {
                File[] fileArrListFiles = file.listFiles();
                if (fileArrListFiles == null) {
                    deleteEmptyDirectories(file);
                    return;
                }
                for (File file2 : fileArrListFiles) {
                    deleteDir(file2);
                }
                deleteEmptyDirectories(file);
            }
        }
    }

    public static void deleteEmptyDirectories(File file) {
        if (file == null || !file.isDirectory()) {
            return;
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles == null || fileArrListFiles.length == 0) {
            file.delete();
            return;
        }
        for (File file2 : fileArrListFiles) {
            if (file2.isFile() && file2.length() != 0) {
                return;
            }
        }
        for (File file3 : fileArrListFiles) {
            if (file3.isDirectory()) {
                deleteEmptyDirectories(file3);
            }
        }
        File[] fileArrListFiles2 = file.listFiles();
        if (fileArrListFiles2 == null || fileArrListFiles2.length == 0) {
            file.delete();
        }
    }

    public static String isProtected(ApkModule apkModule) {
        if (apkModule.getZipEntryMap().iteratorWithPath(new Predicate() { // from class: com.reandroid.apkeditor.Util$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Util.lambda$isProtected$0((String) obj);
            }
        }).hasNext()) {
            return EDIT_TYPE_PROTECTED;
        }
        return null;
    }

    static /* synthetic */ boolean lambda$isProtected$0(String str) {
        return str.startsWith("classes.dex/") || str.startsWith("AndroidManifest.xml/");
    }

    private static String loadApkEditorProperties(TableBlock tableBlock) {
        if (tableBlock == null) {
            return null;
        }
        TableStringPool tableStringPool = tableBlock.getTableStringPool();
        return loadApkEditorProperties(tableStringPool.get(tableStringPool.size() - 1));
    }

    private static String loadApkEditorProperties(TableString tableString) {
        String str;
        if (tableString != null && (str = tableString.get()) != null && str.contains("REPO=") && str.contains(APKEditor.getRepo())) {
            return str;
        }
        return null;
    }

    private static Properties loadApkEditorProperties(String str) {
        if (str != null && str.contains("REPO=") && str.contains(APKEditor.getRepo())) {
            try {
                Properties properties = new Properties();
                properties.load(new StringReader(str));
                return properties;
            } catch (Exception unused) {
            }
        }
        return null;
    }

    public static void addApkEditorInfo(ApkModule apkModule, String str) {
        try {
            addApkEditorInfo(apkModule.getTableBlock(), str);
        } catch (Exception unused) {
        }
    }

    private static void addApkEditorInfo(TableBlock tableBlock, String str) {
        if (tableBlock == null) {
            return;
        }
        TableStringPool tableStringPool = tableBlock.getTableStringPool();
        if (tableStringPool.size() == 0) {
            return;
        }
        TableString last = tableStringPool.getLast();
        if (!isApkEditorInfo(last)) {
            last = tableStringPool.getStringsArray().createNext();
        }
        last.set(buildApkEditorInfo(str));
        IntegerItem integerItem = new IntegerItem();
        integerItem.set(last.getIndex());
        last.addReference(integerItem);
    }

    private static String buildApkEditorInfo(String str) {
        StringBuilder sb = new StringBuilder("NAME=");
        sb.append(APKEditor.getName());
        sb.append("\nVERSION=").append(APKEditor.getVersion());
        sb.append("\nREPO=").append(APKEditor.getRepo());
        sb.append("\nEDIT_TYPE=").append(str);
        sb.append("\nTIME=").append(System.currentTimeMillis());
        return sb.toString();
    }

    private static boolean isApkEditorInfo(TableString tableString) {
        String html;
        return tableString != null && (html = tableString.getHtml()) != null && html.contains("REPO=") && html.contains(APKEditor.getRepo());
    }
}
