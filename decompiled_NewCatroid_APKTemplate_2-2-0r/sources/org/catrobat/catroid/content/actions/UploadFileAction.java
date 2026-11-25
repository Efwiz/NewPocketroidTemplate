package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/UploadFileAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class UploadFileAction extends TemporalAction {
    private static final String TAG = "UploadFileAction";
    public Formula filePath;
    public int fileTypeSelection;
    public Formula mimeType;
    public Scope scope;
    public int storageTypeSelection;
    public Formula url;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        File file;
        try {
            Scope scope = this.scope;
            if (scope == null) {
                return;
            }
            final String strInterpretString = this.url.interpretString(scope);
            String strInterpretString2 = this.filePath.interpretString(this.scope);
            final String strInterpretString3 = this.mimeType.interpretString(this.scope);
            if (this.scope.getProject() == null || (file = this.scope.getProject().getFile(strInterpretString2)) == null) {
                return;
            }
            final String absolutePath = file.getAbsolutePath();
            if (strInterpretString != null && !strInterpretString.isEmpty() && !absolutePath.isEmpty()) {
                new Thread(new Runnable() { // from class: org.catrobat.catroid.content.actions.UploadFileAction$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() throws Throwable {
                        this.f$0.m4889x9e1adac6(absolutePath, strInterpretString, strInterpretString3);
                    }
                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: renamed from: lambda$update$0$org-catrobat-catroid-content-actions-UploadFileAction, reason: not valid java name */
    /* synthetic */ void m4889x9e1adac6(String str, String str2, String str3) throws Throwable {
        HttpURLConnection httpURLConnection = null;
        try {
            try {
                FileHandle fileHandleAbsolute = Gdx.files.absolute(str);
                if (!fileHandleAbsolute.exists()) {
                    Log.e(TAG, "File not found: " + str);
                    return;
                }
                HttpURLConnection httpURLConnection2 = (HttpURLConnection) new URL(str2).openConnection();
                try {
                    httpURLConnection2.setRequestMethod("POST");
                    httpURLConnection2.setDoOutput(true);
                    httpURLConnection2.setUseCaches(false);
                    httpURLConnection2.setRequestProperty("Content-Type", str3);
                    httpURLConnection2.setRequestProperty("X-Storage-Type", this.storageTypeSelection == 0 ? "permanent" : "temporary");
                    DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection2.getOutputStream());
                    InputStream inputStream = fileHandleAbsolute.read();
                    byte[] bArr = new byte[4096];
                    while (true) {
                        int i = inputStream.read(bArr);
                        if (i == -1) {
                            break;
                        } else {
                            dataOutputStream.write(bArr, 0, i);
                        }
                    }
                    inputStream.close();
                    dataOutputStream.flush();
                    dataOutputStream.close();
                    Log.i(TAG, "Server responded with code: " + httpURLConnection2.getResponseCode());
                    if (httpURLConnection2 != null) {
                        httpURLConnection2.disconnect();
                    }
                } catch (Exception e) {
                    httpURLConnection = httpURLConnection2;
                    e = e;
                    Log.e(TAG, "Error uploading file", e);
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                } catch (Throwable th) {
                    httpURLConnection = httpURLConnection2;
                    th = th;
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
