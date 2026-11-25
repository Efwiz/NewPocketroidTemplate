package org.catrobat.catroid.web;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.preference.PreferenceManager;
import android.util.Log;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.android.gms.cast.MediaTrack;
import com.google.android.gms.common.images.WebImage;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.text.Typography;
import okhttp3.ConnectionSpec;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.ScratchProgramData;
import org.catrobat.catroid.common.ScratchSearchResult;
import org.catrobat.catroid.common.ScratchVisibilityState;
import org.catrobat.catroid.transfers.project.ProjectUploadData;
import org.catrobat.catroid.web.requests.HttpRequestsKt;
import org.eclipse.jgit.lib.ReflogEntry;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/web/ServerCalls.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ServerCalls implements ScratchDataFetcher {
    public static final String BASE_URL_TEST_HTTPS = "https://catroid-test.catrob.at/pocketcode/";
    public static final String TAG = "ServerCalls";
    public static boolean useTestUrl = false;
    private final OkHttpClient okHttpClient;
    private String projectId;
    private String resultString;

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/web/ServerCalls$UploadCallSuccessCallback.class
      classes14.dex
     */
    interface UploadCallSuccessCallback {
        void onSuccess(UploadResponse response);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/web/ServerCalls$UploadErrorCallback.class
      classes14.dex
     */
    public interface UploadErrorCallback {
        void onError(int statusCode, String errorMessage);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/web/ServerCalls$UploadSuccessCallback.class
      classes14.dex
     */
    public interface UploadSuccessCallback {
        void onSuccess(String projectId, String username, String token);
    }

    public ServerCalls(OkHttpClient httpClient) {
        this.okHttpClient = httpClient;
    }

    @Override // org.catrobat.catroid.web.ScratchDataFetcher
    public ScratchProgramData fetchScratchProgramDetails(final long programID) throws WebScratchProgramException, WebConnectionException {
        Date date;
        Date date2;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        try {
            this.resultString = getRequestInterruptable(Constants.SCRATCH_CONVERTER_API_DEFAULT_PROJECTS_URL + programID);
            JSONObject jSONObject = new JSONObject(this.resultString);
            if (jSONObject.length() == 0) {
                return null;
            }
            if (!jSONObject.getBoolean("accessible")) {
                throw new WebScratchProgramException(1001, "Program not accessible!");
            }
            JSONObject jSONObject2 = jSONObject.getJSONObject("projectData");
            if (jSONObject2.length() == 0) {
                throw new WebConnectionException(1003, "Field projectData is empty.");
            }
            String string = jSONObject2.getString("title");
            String string2 = jSONObject2.getString("owner");
            String string3 = jSONObject2.isNull("image_url") ? null : jSONObject2.getString("image_url");
            String string4 = jSONObject2.isNull("instructions") ? null : jSONObject2.getString("instructions");
            String string5 = jSONObject2.isNull("notes_and_credits") ? null : jSONObject2.getString("notes_and_credits");
            String string6 = jSONObject2.getString("shared_date");
            String string7 = jSONObject2.getString("modified_date");
            int i = jSONObject2.getInt("views");
            int i2 = jSONObject2.getInt("favorites");
            int i3 = jSONObject2.getInt("loves");
            ScratchVisibilityState scratchVisibilityStateValueOf = ScratchVisibilityState.valueOf(jSONObject.getInt("visibility"));
            try {
                date = simpleDateFormat.parse(string6);
            } catch (ParseException unused) {
                date = null;
            }
            try {
                date2 = simpleDateFormat.parse(string7);
            } catch (ParseException unused2) {
                date2 = null;
            }
            WebImage webImage = string3 != null ? new WebImage(Uri.parse(string3), 480, 360) : null;
            String str = Constants.TMP_IMAGE_FILE_NAME;
            String str2 = "title";
            String str3 = "owner";
            ScratchProgramData scratchProgramData = new ScratchProgramData(programID, string, string2, webImage);
            scratchProgramData.setInstructions(string4);
            scratchProgramData.setNotesAndCredits(string5);
            scratchProgramData.setModifiedDate(date2);
            scratchProgramData.setSharedDate(date);
            scratchProgramData.setViews(i);
            scratchProgramData.setLoves(i3);
            scratchProgramData.setFavorites(i2);
            scratchProgramData.setVisibilityState(scratchVisibilityStateValueOf);
            JSONArray jSONArray = jSONObject2.getJSONArray("remixes");
            int i4 = 0;
            while (i4 < jSONArray.length()) {
                JSONObject jSONObject3 = jSONArray.getJSONObject(i4);
                long j = jSONObject3.getLong("id");
                String str4 = str2;
                String string8 = jSONObject3.getString(str4);
                String str5 = str3;
                String string9 = jSONObject3.getString(str5);
                String str6 = str;
                String string10 = jSONObject3.isNull(str6) ? null : jSONObject3.getString(str6);
                scratchProgramData.addRemixProgram(new ScratchProgramData(j, string8, string9, string10 != null ? new WebImage(Uri.parse(string10), 480, 360) : null));
                i4++;
                str2 = str4;
                str3 = str5;
                str = str6;
            }
            return scratchProgramData;
        } catch (JSONException e) {
            throw new WebConnectionException(1001, Log.getStackTraceString(e));
        }
    }

    @Override // org.catrobat.catroid.web.ScratchDataFetcher
    public ScratchSearchResult fetchDefaultScratchPrograms() throws WebConnectionException, InterruptedIOException {
        try {
            this.resultString = getRequestInterruptable(Constants.SCRATCH_CONVERTER_API_DEFAULT_PROJECTS_URL);
            return new ScratchSearchResult(extractScratchProgramDataListFromJson(new JSONObject(this.resultString).getJSONArray("results")), null, 0);
        } catch (ParseException | JSONException e) {
            throw new WebConnectionException(1001, Log.getStackTraceString(e));
        }
    }

    @Override // org.catrobat.catroid.web.ScratchDataFetcher
    public ScratchSearchResult scratchSearch(final String query, final int numberOfItems, final int pageNumber) throws WebConnectionException {
        if (query == null) {
            throw new WebConnectionException(1001, "Query is null.");
        }
        if (numberOfItems < 1) {
            throw new WebConnectionException(1001, "numberOfItems has to be positive and non-zero.");
        }
        if (pageNumber < 0) {
            throw new WebConnectionException(1001, "pageNumber has to be positive.");
        }
        try {
            HashMap<String, String> map = new HashMap<String, String>(numberOfItems, pageNumber, query) { // from class: org.catrobat.catroid.web.ServerCalls.1
                final /* synthetic */ int val$numberOfItems;
                final /* synthetic */ int val$pageNumber;
                final /* synthetic */ String val$query;

                {
                    this.val$numberOfItems = numberOfItems;
                    this.val$pageNumber = pageNumber;
                    this.val$query = query;
                    put("limit", Integer.toString(numberOfItems));
                    put(TypedValues.CycleType.S_WAVE_OFFSET, Integer.toString(pageNumber * numberOfItems));
                    put("language", Locale.getDefault().getLanguage());
                    put("q", URLEncoder.encode(query, "UTF-8"));
                }
            };
            StringBuilder sb = new StringBuilder(Constants.SCRATCH_SEARCH_URL);
            sb.append(MangleConstant.Q_MARK);
            for (Map.Entry<String, String> entry : map.entrySet()) {
                sb.append(entry.getKey());
                sb.append('=');
                sb.append(entry.getValue());
                sb.append(Typography.amp);
            }
            sb.setLength(sb.length() - 1);
            this.resultString = getRequestInterruptable(sb.toString());
            return new ScratchSearchResult(extractScratchProgramDataListFromJson(new JSONArray(this.resultString)), query, pageNumber);
        } catch (UnsupportedEncodingException | ParseException | JSONException e) {
            throw new WebConnectionException(1001, Log.getStackTraceString(e));
        }
    }

    private List<ScratchProgramData> extractScratchProgramDataListFromJson(final JSONArray jsonArray) throws JSONException, ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_ISO_8601, Locale.US);
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (i < jsonArray.length()) {
            JSONObject jSONObject = jsonArray.getJSONObject(i);
            long j = jSONObject.getLong("id");
            String string = jSONObject.getString("title");
            String string2 = jSONObject.getString(MediaTrack.ROLE_DESCRIPTION);
            String string3 = jSONObject.getString("instructions");
            String string4 = jSONObject.isNull(Constants.TMP_IMAGE_FILE_NAME) ? null : jSONObject.getString(Constants.TMP_IMAGE_FILE_NAME);
            String string5 = jSONObject.getJSONObject("author").getString("username");
            JSONObject jSONObject2 = jSONObject.getJSONObject("history");
            String string6 = jSONObject2.getString(ReflogEntry.PREFIX_CREATED);
            String string7 = jSONObject2.getString("modified");
            String string8 = jSONObject2.getString("shared");
            Date date = simpleDateFormat.parse(string6);
            Date date2 = simpleDateFormat.parse(string7);
            Date date3 = simpleDateFormat.parse(string8);
            JSONObject jSONObject3 = jSONObject.getJSONObject("stats");
            int i2 = jSONObject3.getInt("views");
            SimpleDateFormat simpleDateFormat2 = simpleDateFormat;
            int i3 = jSONObject3.getInt("loves");
            int i4 = jSONObject3.getInt("favorites");
            ScratchProgramData scratchProgramData = new ScratchProgramData(j, string, string5, string4 != null ? new WebImage(Uri.parse(string4), 480, 360) : null);
            scratchProgramData.setInstructions(string3);
            scratchProgramData.setNotesAndCredits(string2);
            scratchProgramData.setCreatedDate(date);
            scratchProgramData.setModifiedDate(date2);
            scratchProgramData.setSharedDate(date3);
            scratchProgramData.setViews(i2);
            scratchProgramData.setLoves(i3);
            scratchProgramData.setFavorites(i4);
            arrayList.add(scratchProgramData);
            i++;
            simpleDateFormat = simpleDateFormat2;
        }
        return arrayList;
    }

    public void uploadProject(final ProjectUploadData uploadData, final UploadSuccessCallback successCallback, final UploadErrorCallback errorCallback) {
        executeUploadCall(HttpRequestsKt.createUploadRequest(uploadData), new UploadCallSuccessCallback() { // from class: org.catrobat.catroid.web.ServerCalls$$ExternalSyntheticLambda1
            @Override // org.catrobat.catroid.web.ServerCalls.UploadCallSuccessCallback
            public final void onSuccess(ServerCalls.UploadResponse uploadResponse) {
                this.f$0.m5867lambda$uploadProject$0$orgcatrobatcatroidwebServerCalls(errorCallback, successCallback, uploadData, uploadResponse);
            }
        }, errorCallback);
    }

    /* JADX INFO: renamed from: lambda$uploadProject$0$org-catrobat-catroid-web-ServerCalls, reason: not valid java name */
    /* synthetic */ void m5867lambda$uploadProject$0$orgcatrobatcatroidwebServerCalls(UploadErrorCallback uploadErrorCallback, UploadSuccessCallback uploadSuccessCallback, ProjectUploadData projectUploadData, UploadResponse uploadResponse) {
        String str = uploadResponse.token;
        this.projectId = uploadResponse.projectId;
        if (uploadResponse.statusCode != ServerAuthenticationConstants.SERVER_RESPONSE_TOKEN_OK.intValue()) {
            uploadErrorCallback.onError(uploadResponse.statusCode, "Upload failed! JSON Response was " + uploadResponse.statusCode);
        } else if (str.equals(ServerAuthenticationConstants.TOKEN_CODE_INVALID) || str.length() != 32) {
            uploadErrorCallback.onError(uploadResponse.statusCode, uploadResponse.answer);
        } else {
            uploadSuccessCallback.onSuccess(this.projectId, projectUploadData.getUsername(), str);
        }
    }

    private void executeUploadCall(Request request, UploadCallSuccessCallback successCallback, UploadErrorCallback errorCallback) {
        try {
            Response responseExecute = this.okHttpClient.newCall(request).execute();
            if (responseExecute.isSuccessful()) {
                successCallback.onSuccess((UploadResponse) new Gson().fromJson(responseExecute.body().string(), UploadResponse.class));
            } else {
                Log.v(TAG, "Upload not successful");
                errorCallback.onError(responseExecute.code(), "Upload failed! HTTP Status code was " + responseExecute.code());
            }
        } catch (JsonSyntaxException e) {
            Log.e(TAG, Log.getStackTraceString(e));
            errorCallback.onError(1001, "JsonSyntaxException");
        } catch (IOException e2) {
            Log.e(TAG, Log.getStackTraceString(e2));
            errorCallback.onError(1002, "I/O Exception");
        }
    }

    public void downloadMedia(final String url, final String filePath, final ResultReceiver receiver) throws IOException, WebConnectionException {
        File file = new File(filePath);
        if (!file.getParentFile().mkdirs() && !file.getParentFile().isDirectory()) {
            throw new IOException("Directory not created");
        }
        Request requestBuild = new Request.Builder().url(url).build();
        OkHttpClient.Builder builderNewBuilder = this.okHttpClient.newBuilder();
        builderNewBuilder.networkInterceptors().add(new Interceptor() { // from class: org.catrobat.catroid.web.ServerCalls$$ExternalSyntheticLambda2
            @Override // okhttp3.Interceptor
            public final Response intercept(Interceptor.Chain chain) {
                return ServerCalls.lambda$downloadMedia$2(receiver, chain);
            }
        });
        Response responseExecute = builderNewBuilder.build().newCall(requestBuild).execute();
        try {
            BufferedSink bufferedSinkBuffer = Okio.buffer(Okio.sink(file));
            try {
                if (responseExecute.body() != null) {
                    bufferedSinkBuffer.writeAll(responseExecute.body().getSource());
                    if (bufferedSinkBuffer != null) {
                        bufferedSinkBuffer.close();
                        return;
                    }
                    return;
                }
                throw new WebConnectionException(1002, "FAIL");
            } catch (Throwable th) {
                if (bufferedSinkBuffer != null) {
                    try {
                        bufferedSinkBuffer.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (IOException e) {
            throw new WebConnectionException(1002, Log.getStackTraceString(e));
        }
    }

    static /* synthetic */ Response lambda$downloadMedia$2(final ResultReceiver resultReceiver, Interceptor.Chain chain) throws IOException {
        Response responseProceed = chain.proceed(chain.request());
        return responseProceed.newBuilder().body(new ProgressResponseBody(responseProceed.body(), new CatrobatServerCalls.DownloadProgressCallback() { // from class: org.catrobat.catroid.web.ServerCalls$$ExternalSyntheticLambda0
            @Override // org.catrobat.catroid.web.CatrobatServerCalls.DownloadProgressCallback
            public final void onProgress(long j) {
                ServerCalls.lambda$downloadMedia$1(resultReceiver, j);
            }
        })).build();
    }

    static /* synthetic */ void lambda$downloadMedia$1(ResultReceiver resultReceiver, long j) {
        Bundle bundle = new Bundle();
        bundle.putLong(ProgressResponseBody.TAG_PROGRESS, j);
        resultReceiver.send(101, bundle);
    }

    private String getRequestInterruptable(String url) throws WebConnectionException {
        Request requestBuild = new Request.Builder().url(url).build();
        try {
            OkHttpClient okHttpClientBuild = this.okHttpClient;
            if (url.startsWith("http://")) {
                okHttpClientBuild = new OkHttpClient.Builder().connectionSpecs(Arrays.asList(ConnectionSpec.CLEARTEXT)).build();
            }
            return okHttpClientBuild.newCall(requestBuild).execute().body().string();
        } catch (IOException e) {
            throw new WebConnectionException(1002, Log.getStackTraceString(e));
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/web/ServerCalls$UploadResponse.class
      classes14.dex
     */
    static class UploadResponse {
        String answer;
        String projectId;
        int statusCode;
        String token;

        UploadResponse() {
        }
    }

    public boolean googleLogin(String mail, String username, String id2, String locale, Context context) throws WebConnectionException {
        if (context == null) {
            throw new WebConnectionException(1001, "Context is null.");
        }
        try {
            HashMap map = new HashMap();
            map.put("email", mail);
            map.put("username", username);
            map.put("id", id2);
            map.put("locale", locale);
            this.resultString = CatrobatWebClientKt.performCallWith(this.okHttpClient, CatrobatWebClientKt.createFormEncodedRequest(map, "https://t.me/NewCatroidDevsapi/loginWithGoogle/loginWithGoogle.json"));
            JSONObject jSONObject = new JSONObject(this.resultString);
            checkStatusCode200(jSONObject.getInt("statusCode"));
            refreshUploadTokenAndUsername(jSONObject.getString("token"), username, context);
            return true;
        } catch (JSONException e) {
            throw new WebConnectionException(1001, Log.getStackTraceString(e));
        }
    }

    public boolean googleExchangeCode(String code, String id2, String username, String mail, String locale, String idToken) throws WebConnectionException {
        try {
            HashMap map = new HashMap();
            map.put(ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, code);
            map.put("id", id2);
            map.put("username", username);
            map.put("email", mail);
            map.put("locale", locale);
            map.put(ServerAuthenticationConstants.SIGNIN_ID_TOKEN, idToken);
            map.put(Constants.REQUEST_MOBILE, "Android");
            this.resultString = CatrobatWebClientKt.performCallWith(this.okHttpClient, CatrobatWebClientKt.createFormEncodedRequest(map, ServerAuthenticationConstants.EXCHANGE_GOOGLE_CODE_URL));
            int i = new JSONObject(this.resultString).getInt("statusCode");
            if (i != ServerAuthenticationConstants.SERVER_RESPONSE_TOKEN_OK.intValue() && i != ServerAuthenticationConstants.SERVER_RESPONSE_REGISTER_OK.intValue()) {
                throw new WebConnectionException(i, this.resultString);
            }
            return true;
        } catch (JSONException e) {
            throw new WebConnectionException(1001, Log.getStackTraceString(e));
        }
    }

    private void checkStatusCode200(int statusCode) throws WebConnectionException {
        if (statusCode != ServerAuthenticationConstants.SERVER_RESPONSE_TOKEN_OK.intValue()) {
            throw new WebConnectionException(statusCode, this.resultString);
        }
    }

    private void refreshUploadTokenAndUsername(String newToken, String username, Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString("token", newToken).putString("username", username).apply();
    }
}
