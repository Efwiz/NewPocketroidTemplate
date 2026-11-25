package org.catrobat.catroid.transfers;

import android.os.AsyncTask;
import android.util.Log;
import com.google.common.base.Preconditions;
import java.io.InterruptedIOException;
import org.catrobat.catroid.common.ScratchSearchResult;
import org.catrobat.catroid.web.ScratchDataFetcher;
import org.catrobat.catroid.web.WebConnectionException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/transfers/SearchScratchProgramsTask.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SearchScratchProgramsTask extends AsyncTask<String, Void, ScratchSearchResult> {
    private static final String TAG = "SearchScratchProgramsTask";
    private SearchScratchProgramsTaskDelegate delegate = null;
    private ScratchDataFetcher fetcher = null;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/transfers/SearchScratchProgramsTask$SearchScratchProgramsTaskDelegate.class
      classes14.dex
     */
    public interface SearchScratchProgramsTaskDelegate {
        void onPostExecute(ScratchSearchResult result);

        void onPreExecute();
    }

    public SearchScratchProgramsTask setDelegate(SearchScratchProgramsTaskDelegate delegate) {
        this.delegate = delegate;
        return this;
    }

    public SearchScratchProgramsTask setFetcher(ScratchDataFetcher fetcher) {
        this.fetcher = fetcher;
        return this;
    }

    @Override // android.os.AsyncTask
    protected void onPreExecute() {
        super.onPreExecute();
        SearchScratchProgramsTaskDelegate searchScratchProgramsTaskDelegate = this.delegate;
        if (searchScratchProgramsTaskDelegate != null) {
            searchScratchProgramsTaskDelegate.onPreExecute();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public ScratchSearchResult doInBackground(String... params) {
        Preconditions.checkArgument(params.length <= 2, "Invalid number of parameters!");
        try {
            return fetchProgramList(params.length > 0 ? params[0] : null);
        } catch (InterruptedIOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
            return null;
        }
    }

    public ScratchSearchResult fetchProgramList(String query) throws InterruptedIOException {
        int i = 0;
        while (i <= 2 && !isCancelled()) {
            try {
                if (query != null) {
                    return this.fetcher.scratchSearch(query, 20, 0);
                }
                return this.fetcher.fetchDefaultScratchPrograms();
            } catch (WebConnectionException e) {
                Log.e(TAG, Log.getStackTraceString(e));
                i++;
                try {
                    Thread.sleep(((int) (Math.random() * 1000.0d * ((double) i))) + 1000);
                } catch (InterruptedException e2) {
                    Log.e(TAG, Log.getStackTraceString(e2));
                }
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(ScratchSearchResult result) {
        super.onPostExecute(result);
        if (this.delegate == null || isCancelled()) {
            return;
        }
        this.delegate.onPostExecute(result);
    }
}
