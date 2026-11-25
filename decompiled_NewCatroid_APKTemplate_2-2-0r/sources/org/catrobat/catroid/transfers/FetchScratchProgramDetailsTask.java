package org.catrobat.catroid.transfers;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import com.google.common.base.Preconditions;
import java.io.InterruptedIOException;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.common.ScratchProgramData;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.web.ScratchDataFetcher;
import org.catrobat.catroid.web.WebConnectionException;
import org.catrobat.catroid.web.WebScratchProgramException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/transfers/FetchScratchProgramDetailsTask.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class FetchScratchProgramDetailsTask extends AsyncTask<Long, Void, ScratchProgramData> {
    private static final String TAG = "FetchScratchProgramDetailsTask";
    private Context context;
    private ScratchProgramListTaskDelegate delegate = null;
    private ScratchDataFetcher fetcher = null;
    private Handler handler;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/transfers/FetchScratchProgramDetailsTask$ScratchProgramListTaskDelegate.class
      classes14.dex
     */
    public interface ScratchProgramListTaskDelegate {
        void onPostExecute(ScratchProgramData programData);

        void onPreExecute();
    }

    public FetchScratchProgramDetailsTask setContext(final Context context) {
        this.context = context;
        this.handler = new Handler(context.getMainLooper());
        return this;
    }

    public FetchScratchProgramDetailsTask setDelegate(ScratchProgramListTaskDelegate delegate) {
        this.delegate = delegate;
        return this;
    }

    public FetchScratchProgramDetailsTask setFetcher(ScratchDataFetcher fetcher) {
        this.fetcher = fetcher;
        return this;
    }

    @Override // android.os.AsyncTask
    protected void onPreExecute() {
        super.onPreExecute();
        ScratchProgramListTaskDelegate scratchProgramListTaskDelegate = this.delegate;
        if (scratchProgramListTaskDelegate != null) {
            scratchProgramListTaskDelegate.onPreExecute();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public ScratchProgramData doInBackground(Long... params) {
        Preconditions.checkArgument(params.length == 1, "No project ID given!");
        long jLongValue = params[0].longValue();
        Preconditions.checkArgument(jLongValue > 0, "Invalid project ID given!");
        try {
            return fetchProjectData(jLongValue);
        } catch (InterruptedIOException unused) {
            Log.i(TAG, "Task has been cancelled in the meanwhile!");
            return null;
        }
    }

    public ScratchProgramData fetchProjectData(final long projectID) throws InterruptedIOException {
        int i = 0;
        while (i <= 2) {
            if (isCancelled()) {
                Log.i(TAG, "Task has been cancelled in the meanwhile!");
                return null;
            }
            try {
                return this.fetcher.fetchScratchProgramDetails(projectID);
            } catch (WebConnectionException e) {
                String str = TAG;
                Log.e(str, e.getMessage() + "\n" + e.getStackTrace());
                i++;
                int iRandom = ((int) (Math.random() * 1000.0d * ((double) i))) + 1000;
                Log.i(str, "Retry #" + i + " to fetch scratch project list scheduled in " + iRandom + " ms due to " + e.getLocalizedMessage());
                try {
                    Thread.sleep(iRandom);
                } catch (InterruptedException e2) {
                    Log.e(TAG, e2.getMessage());
                }
            } catch (WebScratchProgramException e3) {
                final String string = this.context.getString(R.string.error_scratch_program_not_accessible_any_more);
                if (e3.getStatusCode() == 1001) {
                    string = this.context.getString(R.string.error_scratch_program_not_accessible_any_more);
                }
                runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.transfers.FetchScratchProgramDetailsTask.1
                    @Override // java.lang.Runnable
                    public void run() {
                        ToastUtil.showError(FetchScratchProgramDetailsTask.this.context, string);
                    }
                });
                return null;
            }
        }
        Log.w(TAG, "Maximum number of 3 attempts exceeded! Server not reachable?!");
        runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.transfers.FetchScratchProgramDetailsTask.2
            @Override // java.lang.Runnable
            public void run() {
                ToastUtil.showError(FetchScratchProgramDetailsTask.this.context, FetchScratchProgramDetailsTask.this.context.getString(R.string.error_request_timeout));
            }
        });
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(ScratchProgramData programData) {
        super.onPostExecute(programData);
        if (this.delegate == null || isCancelled()) {
            return;
        }
        this.delegate.onPostExecute(programData);
    }

    private void runOnUiThread(Runnable r) {
        this.handler.post(r);
    }
}
