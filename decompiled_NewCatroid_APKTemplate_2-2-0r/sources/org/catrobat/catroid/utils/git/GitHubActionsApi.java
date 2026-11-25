package org.catrobat.catroid.utils.git;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.eclipse.jgit.lib.ConfigConstants;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/git/GitHubActionsApi.class
  classes14.dex
 */
/* JADX INFO: compiled from: GitHubActionsApi.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J<\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0003\u0010\u0007\u001a\u00020\u00062\b\b\u0001\u0010\b\u001a\u00020\u00062\b\b\u0001\u0010\t\u001a\u00020\u0006H§@¢\u0006\u0002\u0010\nJ<\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\b\u001a\u00020\u00062\b\b\u0001\u0010\t\u001a\u00020\u00062\b\b\u0001\u0010\f\u001a\u00020\rH§@¢\u0006\u0002\u0010\u000eJ\u001e\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H§@¢\u0006\u0002\u0010\u0010JF\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\b\u001a\u00020\u00062\b\b\u0001\u0010\t\u001a\u00020\u00062\b\b\u0001\u0010\u0012\u001a\u00020\u00062\b\b\u0001\u0010\u0013\u001a\u00020\u0006H§@¢\u0006\u0002\u0010\u0014J<\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\b\u001a\u00020\u00062\b\b\u0001\u0010\t\u001a\u00020\u00062\b\b\u0001\u0010\u0016\u001a\u00020\u0006H§@¢\u0006\u0002\u0010\nJ<\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\b\u001a\u00020\u00062\b\b\u0001\u0010\t\u001a\u00020\u00062\b\b\u0001\u0010\f\u001a\u00020\rH§@¢\u0006\u0002\u0010\u000eJF\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\b\u001a\u00020\u00062\b\b\u0001\u0010\t\u001a\u00020\u00062\b\b\u0001\u0010\u0012\u001a\u00020\u00062\b\b\u0001\u0010\f\u001a\u00020\rH§@¢\u0006\u0002\u0010\u001a¨\u0006\u001b"}, d2 = {"Lorg/catrobat/catroid/utils/git/GitHubActionsApi;", "", "createFork", "Lretrofit2/Response;", "Lokhttp3/ResponseBody;", "token", "", "accept", "owner", "repo", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createRef", "body", "Lokhttp3/RequestBody;", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lokhttp3/RequestBody;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCurrentUser", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getFile", "path", "ref", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getRef", ConfigConstants.CONFIG_BRANCH_SECTION, "triggerWorkflow", "", "updateFile", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lokhttp3/RequestBody;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface GitHubActionsApi {
    @POST("repos/{owner}/{repo}/forks")
    Object createFork(@Header("Authorization") String str, @Header("Accept") String str2, @Path("owner") String str3, @Path("repo") String str4, Continuation<? super Response<ResponseBody>> continuation);

    @POST("repos/{owner}/{repo}/git/refs")
    Object createRef(@Header("Authorization") String str, @Path("owner") String str2, @Path("repo") String str3, @Body RequestBody requestBody, Continuation<? super Response<ResponseBody>> continuation);

    @GET(ConfigConstants.CONFIG_USER_SECTION)
    Object getCurrentUser(@Header("Authorization") String str, Continuation<? super Response<ResponseBody>> continuation);

    @GET("repos/{owner}/{repo}/contents/{path}")
    Object getFile(@Header("Authorization") String str, @Path("owner") String str2, @Path("repo") String str3, @Path("path") String str4, @Query("ref") String str5, Continuation<? super Response<ResponseBody>> continuation);

    @GET("repos/{owner}/{repo}/git/ref/heads/{branch}")
    Object getRef(@Header("Authorization") String str, @Path("owner") String str2, @Path("repo") String str3, @Path(ConfigConstants.CONFIG_BRANCH_SECTION) String str4, Continuation<? super Response<ResponseBody>> continuation);

    @POST("repos/{owner}/{repo}/actions/workflows/ai_feature_build.yml/dispatches")
    Object triggerWorkflow(@Header("Authorization") String str, @Path("owner") String str2, @Path("repo") String str3, @Body RequestBody requestBody, Continuation<? super Response<Unit>> continuation);

    @PUT("repos/{owner}/{repo}/contents/{path}")
    Object updateFile(@Header("Authorization") String str, @Path("owner") String str2, @Path("repo") String str3, @Path("path") String str4, @Body RequestBody requestBody, Continuation<? super Response<ResponseBody>> continuation);

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/git/GitHubActionsApi$DefaultImpls.class
      classes14.dex
     */
    /* JADX INFO: compiled from: GitHubActionsApi.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static /* synthetic */ Object createFork$default(GitHubActionsApi gitHubActionsApi, String str, String str2, String str3, String str4, Continuation continuation, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: createFork");
            }
            if ((i & 2) != 0) {
                str2 = "application/vnd.github+json";
            }
            return gitHubActionsApi.createFork(str, str2, str3, str4, continuation);
        }
    }
}
