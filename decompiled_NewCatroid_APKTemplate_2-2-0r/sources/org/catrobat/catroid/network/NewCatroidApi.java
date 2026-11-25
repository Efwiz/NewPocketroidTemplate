package org.catrobat.catroid.network;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/network/NewCatroidApi.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public interface NewCatroidApi {
    @POST("/api/authentication")
    Call<Object> login(@Body Object loginRequest);

    @POST("/api/projects")
    @Multipart
    Call<Void> uploadProject(@Header("Authorization") String token, @Part MultipartBody.Part file, @Part("checksum") RequestBody checksum);
}
