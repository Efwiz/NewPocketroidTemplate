package com.reandroid.archive.writer;

import com.reandroid.archive.Archive;
import com.reandroid.archive.block.LocalFileHeader;
import com.reandroid.archive.block.ZipHeader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes10.dex */
public class ZipAligner {
    private static final int ALIGNMENT_4 = 4;
    private static final int ALIGNMENT_PAGE = 4096;
    private static final int NO_ALIGNMENT = 1;
    private final Map<Pattern, Integer> alignmentMap = new HashMap();
    private int defaultAlignment;

    public void setFileAlignment(Pattern pattern, int i) {
        if (pattern == null) {
            return;
        }
        this.alignmentMap.remove(pattern);
        if (i > 1) {
            this.alignmentMap.put(pattern, Integer.valueOf(i));
        }
    }

    public void clearFileAlignment() {
        this.alignmentMap.clear();
    }

    public void setDefaultAlignment(int i) {
        if (i <= 0) {
            i = 1;
        }
        this.defaultAlignment = i;
    }

    public void align(long j, LocalFileHeader localFileHeader) {
        int alignment;
        if (ZipHeader.isZip64Length(localFileHeader.getSize() + j)) {
            return;
        }
        int iCountBytes = 0;
        localFileHeader.setZipAlign(0);
        localFileHeader.updateDataDescriptor();
        if (localFileHeader.getMethod() != Archive.DEFLATED && (alignment = getAlignment(localFileHeader.getFileName())) != 1) {
            long j2 = alignment;
            iCountBytes = (int) ((j2 - ((j + ((long) localFileHeader.countBytes())) % j2)) % j2);
        }
        localFileHeader.setZipAlign(iCountBytes);
    }

    private int getAlignment(String str) {
        if (!this.alignmentMap.isEmpty()) {
            for (Map.Entry<Pattern, Integer> entry : this.alignmentMap.entrySet()) {
                if (entry.getKey().matcher(str).matches()) {
                    return entry.getValue().intValue();
                }
            }
        }
        return this.defaultAlignment;
    }

    public static ZipAligner apkAligner() {
        ZipAligner zipAligner = new ZipAligner();
        zipAligner.setDefaultAlignment(4);
        zipAligner.setFileAlignment(Pattern.compile("^lib/.+\\.so$"), 4096);
        return zipAligner;
    }

    public static ZipAligner noAlignment() {
        ZipAligner zipAligner = new ZipAligner();
        zipAligner.setDefaultAlignment(1);
        return zipAligner;
    }
}
