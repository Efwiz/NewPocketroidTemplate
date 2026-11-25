package com.huawei.hms.mlsdk.productvisionsearch.cloud;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.Base64;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.huawei.hmf.tasks.Task;
import com.huawei.hmf.tasks.Tasks;
import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import com.huawei.hms.framework.network.grs.GrsClient;
import com.huawei.hms.ml.grs.GrsUtils;
import com.huawei.hms.mlkit.common.internal.client.ImageConvertUtils;
import com.huawei.hms.mlkit.common.internal.client.SmartLog;
import com.huawei.hms.mlsdk.cloud.RemoteRequestService;
import com.huawei.hms.mlsdk.common.AppSettingHolder;
import com.huawei.hms.mlsdk.common.MLApplication;
import com.huawei.hms.mlsdk.common.MLException;
import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.internal.client.rest.RestErrorResponse;
import com.huawei.hms.mlsdk.internal.client.rest.e;
import com.huawei.hms.mlsdk.internal.client.rest.f;
import com.huawei.hms.mlsdk.productvisionsearch.MLProductVisionSearch;
import com.huawei.hms.mlsdk.productvisionsearch.MLVisionSearchProduct;
import com.huawei.hms.mlsdk.productvisionsearch.MLVisionSearchProductImage;
import com.huawei.hms.mlsdk.productvisionsearch.cloud.bo.BoxResult;
import com.huawei.hms.mlsdk.productvisionsearch.cloud.bo.ImageResult;
import com.huawei.hms.mlsdk.productvisionsearch.cloud.bo.ProductResult;
import com.huawei.hms.mlsdk.productvisionsearch.cloud.bo.SnapShopResponse;
import com.huawei.hms.mlsdk.productvisionsearch.cloud.bo.SnapShopResult;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/* JADX INFO: loaded from: classes10.dex */
public class MLRemoteProductVisionSearchAnalyzer {
    private static Map<AppSettingHolder<MLRemoteProductVisionSearchAnalyzerSetting>, MLRemoteProductVisionSearchAnalyzer> e = new HashMap();
    private Call<String> a;
    private MLApplication b;
    private MLRemoteProductVisionSearchAnalyzerSetting c;
    private GrsClient d;

    class a implements Callable<List<MLProductVisionSearch>> {
        final /* synthetic */ MLFrame a;

        a(MLFrame mLFrame) {
            this.a = mLFrame;
        }

        @Override // java.util.concurrent.Callable
        public List<MLProductVisionSearch> call() throws Exception {
            return MLRemoteProductVisionSearchAnalyzer.this.a(this.a);
        }
    }

    private MLRemoteProductVisionSearchAnalyzer(MLApplication mLApplication, MLRemoteProductVisionSearchAnalyzerSetting mLRemoteProductVisionSearchAnalyzerSetting) {
        this.b = mLApplication;
        this.c = mLRemoteProductVisionSearchAnalyzerSetting;
        this.d = GrsUtils.initGrsVisionSearchClientWithCountry(mLApplication.getAppContext(), mLRemoteProductVisionSearchAnalyzerSetting.a());
    }

    public static synchronized MLRemoteProductVisionSearchAnalyzer create(MLApplication mLApplication, MLRemoteProductVisionSearchAnalyzerSetting mLRemoteProductVisionSearchAnalyzerSetting) {
        MLRemoteProductVisionSearchAnalyzer mLRemoteProductVisionSearchAnalyzer;
        AppSettingHolder<MLRemoteProductVisionSearchAnalyzerSetting> appSettingHolderCreate = AppSettingHolder.create(mLApplication.getUniqueKey(), mLRemoteProductVisionSearchAnalyzerSetting);
        mLRemoteProductVisionSearchAnalyzer = e.get(appSettingHolderCreate);
        if (mLRemoteProductVisionSearchAnalyzer == null) {
            mLRemoteProductVisionSearchAnalyzer = new MLRemoteProductVisionSearchAnalyzer(mLApplication, mLRemoteProductVisionSearchAnalyzerSetting);
            e.put(appSettingHolderCreate, mLRemoteProductVisionSearchAnalyzer);
        }
        return mLRemoteProductVisionSearchAnalyzer;
    }

    public List<MLProductVisionSearch> analyseFrame(MLFrame mLFrame) throws Exception {
        SmartLog.i("RProductVisionSearch", "analyseFrame");
        return a(mLFrame);
    }

    public Task<List<MLProductVisionSearch>> asyncAnalyseFrame(MLFrame mLFrame) {
        SmartLog.i("RProductVisionSearch", "asyncAnalyseFrame");
        return Tasks.callInBackground(new a(mLFrame));
    }

    public void stop() {
        Call<String> call = this.a;
        if (call == null || call.isCanceled() || !this.a.isExecuted()) {
            return;
        }
        this.a.cancel();
        this.a = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<MLProductVisionSearch> a(MLFrame mLFrame) throws Exception {
        if (this.c.isEnableFingerprintVerification() && TextUtils.isEmpty(this.b.getAppSetting().getCertFingerprint())) {
            throw new MLException("Failed to detect cloud product vision search.", 2);
        }
        GrsClient grsClient = this.d;
        if (grsClient == null) {
            throw new MLException("region is required.", 5);
        }
        List<String> listAddHttpsHeaders = GrsUtils.addHttpsHeaders(GrsUtils.getVisionSearchUrls(grsClient));
        if (com.huawei.hms.mlsdk.dynamic.a.a(listAddHttpsHeaders)) {
            throw new MLException("UrlList is empty, fail to detect cloud product vision search.", 2);
        }
        Map<String, String> mapA = new f.b().a().a();
        String str = mapA.get("appId");
        if (str == null || str.isEmpty()) {
            SmartLog.e("RProductVisionSearch", "header app_id is empty");
        } else if (mapA.get("Authorization").replace("Bearer ", "").isEmpty()) {
            SmartLog.e("RProductVisionSearch", "header file api_key is empty");
        } else {
            String str2 = mapA.get("X-Package-Name");
            if (str2 != null && !str2.isEmpty()) {
                if (TextUtils.isEmpty(this.c.getProductSetId())) {
                    SmartLog.d("RProductVisionSearch", "productSetId is required.5");
                }
                Bitmap bitmap = mLFrame.readBitmap();
                float fMin = (Math.min(bitmap.getWidth(), bitmap.getHeight()) * 1.0f) / 640.0f;
                float f = fMin >= 1.0f ? fMin : 1.0f;
                SmartLog.i("RProductVisionSearch", "calcByLongSize = false, scaleFactor = " + f);
                double d = f;
                Bitmap bitmap2 = mLFrame.readBitmap();
                Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmap2, (int) (((double) bitmap2.getWidth()) / d), (int) (((double) bitmap2.getHeight()) / d), true);
                boolean zEquals = false;
                SmartLog.i("RProductVisionSearch", com.huawei.hms.mlsdk.mlvision.a.a("width = ").append(bitmapCreateScaledBitmap.getWidth()).append(", height = ").append(bitmapCreateScaledBitmap.getHeight()).append(", expected kb = ").append(100).append(", expected quality = ").append(90).append(", conditionalCompression = ").append(false).append(", original kb = ").append(ImageConvertUtils.bitmap2Jpeg(bitmapCreateScaledBitmap, 100).length / 1024).toString());
                byte[] bArrBitmap2Jpeg = ImageConvertUtils.bitmap2Jpeg(bitmapCreateScaledBitmap, 90);
                SmartLog.i("RProductVisionSearch", com.huawei.hms.mlsdk.mlvision.a.a("width = ").append(bitmapCreateScaledBitmap.getWidth()).append(", height = ").append(bitmapCreateScaledBitmap.getHeight()).append(", resized kb = ").append(bArrBitmap2Jpeg.length / 1024).toString());
                String strEncodeToString = Base64.encodeToString(bArrBitmap2Jpeg, 2);
                MLRemoteProductVisionSearchAnalyzerSetting mLRemoteProductVisionSearchAnalyzerSetting = this.c;
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("imgBase64", strEncodeToString);
                jsonObject.addProperty("topNum", Integer.valueOf(mLRemoteProductVisionSearchAnalyzerSetting.getLargestNumOfReturns()));
                jsonObject.addProperty("productSetId", mLRemoteProductVisionSearchAnalyzerSetting.getProductSetId());
                jsonObject.addProperty("globalRegion", Boolean.valueOf(mLRemoteProductVisionSearchAnalyzerSetting.isEnableBorderExtract()));
                jsonObject.addProperty("classType", mLRemoteProductVisionSearchAnalyzerSetting.getClassType());
                String string = jsonObject.toString();
                List<MLProductVisionSearch> arrayList = new ArrayList<>();
                Response<String> responseExecute = null;
                boolean z = false;
                for (String str3 : listAddHttpsHeaders) {
                    try {
                        SmartLog.i("RProductVisionSearch", "request start, url = " + str3, true);
                        Call<String> callDetect = ((RemoteRequestService) e.a().a(str3).a(RemoteRequestService.class)).detect("v1/image/recognition/snapshop", mapA, string);
                        this.a = callDetect;
                        responseExecute = callDetect.execute();
                        z = responseExecute != null && responseExecute.code() == 200;
                        SmartLog.i("RProductVisionSearch", "request end, success = " + z);
                        if (z) {
                            arrayList = a(responseExecute, d);
                            SmartLog.i("RProductVisionSearch", "request handleResult OK, size = " + arrayList.size());
                            break;
                        }
                        continue;
                    } catch (IOException e2) {
                        SmartLog.e("RProductVisionSearch", com.huawei.hms.mlsdk.mlvision.a.a("IOException e: ").append(e2.getMessage()).toString());
                    }
                }
                if (z) {
                    return arrayList;
                }
                if (responseExecute == null || responseExecute.code() != 401) {
                    throw new MLException("Cloud product vision search failed.", 3);
                }
                SmartLog.i("RProductVisionSearch", com.huawei.hms.mlsdk.mlvision.a.a("failed message = ").append(responseExecute.message()).append(", code = ").append(responseExecute.code()).toString());
                ResponseBody responseBodyErrorBody = responseExecute.errorBody();
                if (responseBodyErrorBody != null) {
                    try {
                        String strString = responseBodyErrorBody.string();
                        if (!TextUtils.isEmpty(strString)) {
                            RestErrorResponse restErrorResponse = (RestErrorResponse) new Gson().fromJson(strString, RestErrorResponse.class);
                            if (restErrorResponse.getRetCode() != null) {
                                zEquals = "001001".equals(restErrorResponse.getRetCode());
                            }
                        }
                    } catch (JsonSyntaxException | IOException unused) {
                    }
                }
                if (zEquals) {
                    throw new MLException("Token is invalid or expired.", 19);
                }
                throw new MLException("Cloud product vision search failed.", 15);
            }
            SmartLog.e("RProductVisionSearch", "header file package_name is empty");
        }
        throw new MLException("Header param error, fail to detect cloud product vision search.", 2);
    }

    private List<MLProductVisionSearch> a(Response<String> response, double d) throws Exception {
        ArrayList arrayList = new ArrayList();
        SnapShopResponse snapShopResponse = (SnapShopResponse) new Gson().fromJson(response.body(), SnapShopResponse.class);
        if (snapShopResponse != null) {
            SmartLog.i("RProductVisionSearch", com.huawei.hms.mlsdk.mlvision.a.a("handleResult message = ").append(snapShopResponse.b()).append(", code = ").append(snapShopResponse.a()).toString());
            if ("0".equals(snapShopResponse.a())) {
                List<SnapShopResult> listC = snapShopResponse.c();
                if (com.huawei.hms.mlsdk.dynamic.a.a(listC)) {
                    return arrayList;
                }
                for (SnapShopResult snapShopResult : listC) {
                    MLProductVisionSearch mLProductVisionSearch = new MLProductVisionSearch();
                    mLProductVisionSearch.setType(TextUtils.isEmpty(snapShopResult.b()) ? GrsBaseInfo.CountryCodeSource.UNKNOWN : snapShopResult.b());
                    BoxResult boxResultA = snapShopResult.a();
                    if (boxResultA != null) {
                        mLProductVisionSearch.setBorder(new Rect((int) (((double) boxResultA.c()) * d), (int) (((double) boxResultA.d()) * d), (int) (((double) boxResultA.a()) * d), (int) (((double) boxResultA.b()) * d)));
                    } else {
                        mLProductVisionSearch.setBorder(new Rect());
                    }
                    ArrayList arrayList2 = new ArrayList();
                    if (!com.huawei.hms.mlsdk.dynamic.a.a(snapShopResult.c())) {
                        for (ProductResult productResult : snapShopResult.c()) {
                            MLVisionSearchProduct mLVisionSearchProduct = new MLVisionSearchProduct();
                            mLVisionSearchProduct.setProductId(productResult.c());
                            ArrayList arrayList3 = new ArrayList();
                            if (!com.huawei.hms.mlsdk.dynamic.a.a(productResult.b())) {
                                for (ImageResult imageResult : productResult.b()) {
                                    MLVisionSearchProductImage mLVisionSearchProductImage = new MLVisionSearchProductImage();
                                    mLVisionSearchProductImage.setProductId(productResult.c());
                                    mLVisionSearchProductImage.setImageId(imageResult.a());
                                    mLVisionSearchProductImage.setPossibility(imageResult.c());
                                    mLVisionSearchProductImage.setInnerUrl(imageResult.b());
                                    arrayList3.add(mLVisionSearchProductImage);
                                }
                            }
                            mLVisionSearchProduct.setImageList(arrayList3);
                            mLVisionSearchProduct.setProductUrl(productResult.d());
                            mLVisionSearchProduct.setCustomContent(productResult.a());
                            arrayList2.add(mLVisionSearchProduct);
                        }
                    }
                    mLProductVisionSearch.setProductList(arrayList2);
                    arrayList.add(mLProductVisionSearch);
                }
                return arrayList;
            }
            if (!"2001".equals(snapShopResponse.a())) {
                if (!"2002".equals(snapShopResponse.a())) {
                    if (!"2005".equals(snapShopResponse.a())) {
                        if (!"2034".equals(snapShopResponse.a())) {
                            if (!"4005".equals(snapShopResponse.a())) {
                                if (!"4006".equals(snapShopResponse.a())) {
                                    if (!"4007".equals(snapShopResponse.a())) {
                                        if ("001001".equals(snapShopResponse.a())) {
                                            throw new MLException("Token is invalid or expired.", 19);
                                        }
                                        throw new MLException(com.huawei.hms.mlsdk.mlvision.a.a("Internal error, code = ").append(snapShopResponse.a()).toString(), 2);
                                    }
                                    throw new MLException("The free quota has been used up,please upgrade package on https://developer.huawei.com.", 9);
                                }
                                throw new MLException("Please subscribe package first on https://developer.huawei.com.", 9);
                            }
                            throw new MLException("The project has been out of credit and grace period is over.", 9);
                        }
                        throw new MLException("productSetId does not exist in the project", 5);
                    }
                    throw new MLException("Picture is not recognized.", 2);
                }
                throw new MLException("Incorrect parameter. This exception is irrelevant to services.", 5);
            }
            throw new MLException("Identity authentication required.", 15);
        }
        throw new MLException("Cloud service return the empty result.", 2);
    }
}
