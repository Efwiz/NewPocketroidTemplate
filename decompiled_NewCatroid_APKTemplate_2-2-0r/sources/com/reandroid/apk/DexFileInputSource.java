package com.reandroid.apk;

import com.android.dex.DexFormat;
import com.reandroid.archive.InputSource;
import com.reandroid.archive.RenamedInputSource;
import com.reandroid.utils.CompareUtil;
import com.reandroid.utils.ObjectsUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class DexFileInputSource extends RenamedInputSource<InputSource> implements Comparable<DexFileInputSource> {
    public static final String DEX_DIRECTORY_NAME = ObjectsUtil.of("dex");

    public DexFileInputSource(String str, InputSource inputSource) {
        super(str, inputSource);
    }

    public int getDexNumber() {
        return InputSource.getDexNumber(getAlias());
    }

    @Override // java.lang.Comparable
    public int compareTo(DexFileInputSource dexFileInputSource) {
        return Integer.compare(getDexNumber(), dexFileInputSource.getDexNumber());
    }

    public static void sort(List<DexFileInputSource> list) {
        list.sort(CompareUtil.getComparableComparator());
    }

    public static void sortDexFiles(List<File> list) {
        list.sort(new Comparator<File>() { // from class: com.reandroid.apk.DexFileInputSource.1
            @Override // java.util.Comparator
            public int compare(File file, File file2) {
                return InputSource.compareDex(file.getName(), file2.getName());
            }
        });
    }

    public static List<File> listDexFiles(File file) {
        File[] fileArrListFiles;
        ArrayList arrayList = new ArrayList();
        if (!file.isDirectory() || (fileArrListFiles = file.listFiles()) == null) {
            return arrayList;
        }
        for (File file2 : fileArrListFiles) {
            if (file2.isFile() && isDexName(file2.getName())) {
                arrayList.add(file2);
            }
        }
        sortDexFiles(arrayList);
        return arrayList;
    }

    public static boolean isDexName(String str) {
        return InputSource.getDexNumber(str) >= 0;
    }

    static String getDexName(int i) {
        if (i == 0) {
            return DexFormat.DEX_IN_JAR_NAME;
        }
        return "classes" + i + ".dex";
    }
}
