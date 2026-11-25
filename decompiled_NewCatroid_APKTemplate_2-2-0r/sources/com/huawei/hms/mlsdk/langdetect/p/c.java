package com.huawei.hms.mlsdk.langdetect.p;

import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Url;

/* JADX INFO: compiled from: RemoteRequestService.java */
/* JADX INFO: loaded from: classes10.dex */
public interface c {
    @POST
    Call<String> a(@Url String str, @HeaderMap Map<String, String> map, @Body String str2);
}
