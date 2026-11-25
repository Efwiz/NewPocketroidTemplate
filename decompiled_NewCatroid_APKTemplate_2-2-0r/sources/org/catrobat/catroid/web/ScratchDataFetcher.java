package org.catrobat.catroid.web;

import java.io.InterruptedIOException;
import org.catrobat.catroid.common.ScratchProgramData;
import org.catrobat.catroid.common.ScratchSearchResult;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/web/ScratchDataFetcher.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public interface ScratchDataFetcher {
    ScratchSearchResult fetchDefaultScratchPrograms() throws WebConnectionException, InterruptedIOException;

    ScratchProgramData fetchScratchProgramDetails(long programID) throws WebScratchProgramException, WebConnectionException, InterruptedIOException;

    ScratchSearchResult scratchSearch(String query, int numberOfItems, int pageNumber) throws WebConnectionException, InterruptedIOException;
}
