package org.catrobat.catroid.common;

import java.util.List;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/common/ScratchSearchResult.class
  classes12.dex
 */
/* JADX INFO: loaded from: classes12.dex */
public class ScratchSearchResult {
    private int pageNumber;
    private List<ScratchProgramData> programDataList;
    private String query;

    public ScratchSearchResult(List<ScratchProgramData> programDataList, String query, int pageNumber) {
        this.query = query;
        this.programDataList = programDataList;
        this.pageNumber = pageNumber;
    }

    public List<ScratchProgramData> getProgramDataList() {
        return this.programDataList;
    }

    public String getQuery() {
        return this.query;
    }

    public int getPageNumber() {
        return this.pageNumber;
    }
}
