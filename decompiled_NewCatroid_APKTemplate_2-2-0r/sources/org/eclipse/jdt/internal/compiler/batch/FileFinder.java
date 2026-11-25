package org.eclipse.jdt.internal.compiler.batch;

import java.io.File;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes14.dex */
public class FileFinder {
    public static String[] find(File file, String str) {
        ArrayList arrayList = new ArrayList();
        find0(file, str, arrayList);
        String[] strArr = new String[arrayList.size()];
        arrayList.toArray(strArr);
        return strArr;
    }

    private static void find0(File file, String str, ArrayList arrayList) {
        String[] list;
        if (!file.isDirectory() || (list = file.list()) == null) {
            return;
        }
        for (String str2 : list) {
            File file2 = new File(file, str2);
            if (file2.isDirectory()) {
                find0(file2, str, arrayList);
            } else if (file2.getName().toUpperCase().endsWith(str)) {
                arrayList.add(file2.getAbsolutePath());
            }
        }
    }
}
