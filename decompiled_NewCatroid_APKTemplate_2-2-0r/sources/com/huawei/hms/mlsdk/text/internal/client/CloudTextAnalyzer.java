package com.huawei.hms.mlsdk.text.internal.client;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Base64;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.huawei.hmf.tasks.Task;
import com.huawei.hmf.tasks.Tasks;
import com.huawei.hms.ml.common.utils.ImageConvertUtils;
import com.huawei.hms.ml.common.utils.SmartLog;
import com.huawei.hms.ml.grs.GrsRegionUtils;
import com.huawei.hms.ml.grs.GrsUtils;
import com.huawei.hms.mlsdk.cloud.Coord;
import com.huawei.hms.mlsdk.cloud.RemoteRequestService;
import com.huawei.hms.mlsdk.common.AppSettingHolder;
import com.huawei.hms.mlsdk.common.MLApplication;
import com.huawei.hms.mlsdk.common.MLException;
import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.document.internal.client.bo.Char;
import com.huawei.hms.mlsdk.document.internal.client.bo.Document;
import com.huawei.hms.mlsdk.document.internal.client.bo.Language;
import com.huawei.hms.mlsdk.document.internal.client.bo.Line;
import com.huawei.hms.mlsdk.document.internal.client.bo.Region;
import com.huawei.hms.mlsdk.document.internal.client.bo.RemoteOcrDocumentResponse;
import com.huawei.hms.mlsdk.document.internal.client.bo.Section;
import com.huawei.hms.mlsdk.document.internal.client.bo.Term;
import com.huawei.hms.mlsdk.internal.client.rest.RestErrorResponse;
import com.huawei.hms.mlsdk.internal.client.rest.e;
import com.huawei.hms.mlsdk.internal.client.rest.f;
import com.huawei.hms.mlsdk.mlvision.a;
import com.huawei.hms.mlsdk.text.MLRemoteTextSetting;
import com.huawei.hms.mlsdk.text.MLText;
import com.huawei.hms.mlsdk.text.TextLanguage;
import com.huawei.hms.mlsdk.text.internal.client.bo.RemoteOcrTextResponse;
import com.huawei.hms.mlsdk.text.internal.client.bo.RemoteText;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;
import okhttp3.ResponseBody;
import retrofit2.Response;

/* JADX INFO: loaded from: classes10.dex */
public class CloudTextAnalyzer implements Closeable {
    private static final String TAG = "CloudTextAnalyzer";
    private static Map<AppSettingHolder<MLRemoteTextSetting>, CloudTextAnalyzer> appOptionDetectMap = new HashMap();

    /* JADX INFO: renamed from: app, reason: collision with root package name */
    private MLApplication f198app;
    private final MLRemoteTextSetting options;
    private RemoteRequestService requestService;

    private CloudTextAnalyzer(MLApplication mLApplication, MLRemoteTextSetting mLRemoteTextSetting) {
        this.f198app = mLApplication;
        this.options = mLRemoteTextSetting;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float calculateScale(Bitmap bitmap) {
        float fMin = (Math.min(bitmap.getWidth(), bitmap.getHeight()) * 1.0f) / 1080.0f;
        if (fMin < 1.0f) {
            return 1.0f;
        }
        return fMin;
    }

    private MLText.Block cloudBlockToHmsBlock(Region region, float f) {
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        ArrayList arrayList2 = new ArrayList();
        List<Section> sections = region.getSections();
        if (sections != null) {
            Iterator<Section> it = sections.iterator();
            while (it.hasNext()) {
                List<Line> lines = it.next().getLines();
                if (lines != null) {
                    Iterator<Line> it2 = lines.iterator();
                    while (it2.hasNext()) {
                        MLText.TextLine textLineCloudLineToLine = cloudLineToLine(it2.next(), f);
                        if (sb.length() != 0) {
                            sb.append(System.lineSeparator());
                        }
                        sb.append(textLineCloudLineToLine.getStringValue());
                        arrayList.add(textLineCloudLineToLine);
                        if (textLineCloudLineToLine.getLanguageList() != null) {
                            arrayList2.removeAll(textLineCloudLineToLine.getLanguageList());
                            arrayList2.addAll(textLineCloudLineToLine.getLanguageList());
                        }
                    }
                }
            }
        }
        return new MLText.Block(sb.toString(), createBoundingBox(region.getCoords(), f), arrayList2, arrayList, boundingPloyToPoints(region.getCoords(), f), Float.parseFloat(String.valueOf(region.getConfidence())));
    }

    private MLText.TextLine cloudLineToLine(Line line, float f) {
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        ArrayList arrayList2 = new ArrayList();
        List<Term> terms = line.getTerms();
        if (terms != null) {
            for (Term term : terms) {
                MLText.Word wordCloudWordToElement = cloudWordToElement(term, f);
                sb.append(wordCloudWordToElement.getStringValue());
                if (term.getChars() != null && term.getChars().size() > 0 && "SPACE".equals(term.getChars().get(term.getChars().size() - 1).getBreakType())) {
                    sb.append(" ");
                }
                arrayList.add(wordCloudWordToElement);
                if (wordCloudWordToElement.getLanguageList() != null) {
                    arrayList2.removeAll(wordCloudWordToElement.getLanguageList());
                    arrayList2.addAll(wordCloudWordToElement.getLanguageList());
                }
            }
        }
        return new MLText.TextLine(sb.toString(), createBoundingBox(line.getCoords(), f), arrayList2, arrayList, boundingPloyToPoints(line.getCoords(), f), Float.parseFloat(String.valueOf(line.getConfidence())));
    }

    private MLText.Word cloudWordToElement(Term term, float f) {
        StringBuilder sb = new StringBuilder();
        List<TextLanguage> listPropertyToLanguages = propertyToLanguages(term.getLanguages());
        List<Char> chars = term.getChars();
        if (chars != null) {
            Iterator<Char> it = chars.iterator();
            while (it.hasNext()) {
                sb.append(it.next().getValue());
            }
        }
        return new MLText.Word(sb.toString(), createBoundingBox(term.getCoords(), f), listPropertyToLanguages, boundingPloyToPoints(term.getCoords(), f), f);
    }

    public static synchronized CloudTextAnalyzer create(MLApplication mLApplication, MLRemoteTextSetting mLRemoteTextSetting) {
        CloudTextAnalyzer cloudTextAnalyzer;
        try {
            if (mLApplication == null) {
                throw new NullPointerException();
            }
            AppSettingHolder<MLRemoteTextSetting> appSettingHolderCreate = AppSettingHolder.create(mLApplication.getUniqueKey(), mLRemoteTextSetting);
            cloudTextAnalyzer = appOptionDetectMap.get(appSettingHolderCreate);
            if (cloudTextAnalyzer == null) {
                cloudTextAnalyzer = new CloudTextAnalyzer(mLApplication, mLRemoteTextSetting);
                appOptionDetectMap.put(appSettingHolderCreate, cloudTextAnalyzer);
            }
        } catch (Throwable th) {
            throw th;
        }
        return cloudTextAnalyzer;
    }

    public static Rect createBoundingBox(List<Coord> list, float f) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        int iMax = Integer.MIN_VALUE;
        int iMin = Integer.MAX_VALUE;
        int iMax2 = Integer.MIN_VALUE;
        int iMin2 = Integer.MAX_VALUE;
        for (int i = 0; i < list.size(); i++) {
            Coord coord = list.get(i);
            iMin2 = Math.min(coord.getX(), iMin2);
            iMin = Math.min(coord.getY(), iMin);
            iMax = Math.max(coord.getX(), iMax);
            iMax2 = Math.max(coord.getY(), iMax2);
        }
        return new Rect(Math.round(iMin2 * f), Math.round(iMin * f), Math.round(iMax * f), Math.round(iMax2 * f));
    }

    public static Rect createBoundingBoxFromCoords(List<Coord> list, float f) {
        if (list == null || list.size() <= 0) {
            return null;
        }
        int iMax = Integer.MIN_VALUE;
        int iMin = Integer.MAX_VALUE;
        int iMax2 = Integer.MIN_VALUE;
        int iMin2 = Integer.MAX_VALUE;
        for (int i = 0; i < list.size(); i++) {
            Coord coord = list.get(i);
            iMin2 = Math.min(coord.getX(), iMin2);
            iMin = Math.min(coord.getY(), iMin);
            iMax = Math.max(coord.getX(), iMax);
            iMax2 = Math.max(coord.getY(), iMax2);
        }
        return new Rect(Math.round(iMin2 * f), Math.round(iMin * f), Math.round(iMax * f), Math.round(iMax2 * f));
    }

    private MLText createText(RemoteOcrDocumentResponse remoteOcrDocumentResponse, float f) {
        ArrayList arrayList = new ArrayList();
        List<Document> docs = remoteOcrDocumentResponse.getDocs();
        if (docs == null || docs.size() == 0) {
            return new MLText();
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator<Document> it = docs.iterator();
        while (it.hasNext()) {
            arrayList2.addAll(it.next().getRegions());
        }
        for (int i = 0; i < arrayList2.size(); i++) {
            arrayList.add(cloudBlockToHmsBlock((Region) arrayList2.get(i), f));
        }
        return new MLText(docs.get(0).getDoc(), arrayList);
    }

    private MLText createVisionText(List<RemoteText> list, double d) {
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < list.size(); i++) {
            RemoteText remoteText = list.get(i);
            String text = remoteText.getText();
            float confidence = (float) remoteText.getConfidence();
            TextLanguage textLanguage = new TextLanguage(remoteText.getLanguage());
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(textLanguage);
            float f = (float) d;
            Rect rectCreateBoundingBoxFromCoords = createBoundingBoxFromCoords(remoteText.getCoords(), f);
            Point[] pointArrBoundingPloyToPointsFromCoords = boundingPloyToPointsFromCoords(remoteText.getCoords(), f);
            MLText.Word word = new MLText.Word(text, rectCreateBoundingBoxFromCoords, arrayList2, pointArrBoundingPloyToPointsFromCoords, confidence);
            ArrayList arrayList3 = new ArrayList();
            arrayList3.add(word);
            MLText.TextLine textLine = new MLText.TextLine(text, rectCreateBoundingBoxFromCoords, arrayList2, arrayList3, pointArrBoundingPloyToPointsFromCoords, confidence);
            ArrayList arrayList4 = new ArrayList();
            arrayList4.add(textLine);
            arrayList.add(new MLText.Block(text, rectCreateBoundingBoxFromCoords, arrayList2, arrayList4, pointArrBoundingPloyToPointsFromCoords, confidence));
            if (sb.length() > 0) {
                sb.append(System.lineSeparator());
            }
            sb.append(text);
        }
        return new MLText(sb.toString(), arrayList);
    }

    private void getErrorMessageByResponseCode(String str) throws MLException {
        str.hashCode();
        switch (str) {
            case "2001":
                throw new MLException("Identity authentication required.", 15);
            case "2002":
                throw new MLException("Incorrect parameter. This exception is irrelevant to services.", 5);
            case "2005":
                throw new MLException("Picture is not recognized.", 2);
            case "4005":
                throw new MLException("The project has been out of credit and grace period is over.", 9);
            case "4006":
                throw new MLException("Please subscribe package first on https://developer.huawei.com.", 9);
            case "4007":
                throw new MLException("The free quota has been used up,please upgrade package on https://developer.huawei.com.", 9);
            default:
                throw new MLException("Internal error.", 2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MLText handleResult(Response<String> response, float f) throws Exception {
        List<RemoteText> responses;
        MLText mLText = new MLText();
        if (!response.isSuccessful()) {
            throw new MLException("Get cloud response failed.", 2);
        }
        Object objFromJson = new Gson().fromJson(response.body(), (Class<Object>) (this.options.getTextDensityScene() == 1 ? RemoteOcrTextResponse.class : RemoteOcrDocumentResponse.class));
        if (objFromJson == null) {
            throw new MLException("Cloud service return the empty result.", 2);
        }
        if (this.options.getTextDensityScene() != 1) {
            if (this.options.getTextDensityScene() != 2) {
                return mLText;
            }
            RemoteOcrDocumentResponse remoteOcrDocumentResponse = (RemoteOcrDocumentResponse) objFromJson;
            if ("0".equals(remoteOcrDocumentResponse.getRetCode())) {
                return createText(remoteOcrDocumentResponse, f);
            }
            getErrorMessageByResponseCode(remoteOcrDocumentResponse.getRetCode());
            return mLText;
        }
        RemoteOcrTextResponse remoteOcrTextResponse = (RemoteOcrTextResponse) objFromJson;
        if ("0".equals(remoteOcrTextResponse.getRetCode())) {
            responses = remoteOcrTextResponse.getResponses();
        } else {
            getErrorMessageByResponseCode(remoteOcrTextResponse.getRetCode());
            responses = null;
        }
        if (responses == null || responses.size() == 0) {
            throw new MLException("Cloud service return the empty result.", 2);
        }
        return createVisionText(responses, f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isHeaderInvalidate(Map<String, String> map) {
        String str = map.get("appId");
        if (str == null || str.isEmpty()) {
            SmartLog.e(TAG, "header app_id is empty");
            return true;
        }
        if (map.get("Authorization").replace("Bearer ", "").isEmpty()) {
            SmartLog.e(TAG, "header file api_key is empty");
            return true;
        }
        String str2 = map.get("X-Package-Name");
        if (str2 != null && !str2.isEmpty()) {
            return false;
        }
        SmartLog.e(TAG, "header file package_name is empty");
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isTokenInvalid(ResponseBody responseBody) {
        if (responseBody == null) {
            return false;
        }
        try {
            String strString = responseBody.string();
            if (android.text.TextUtils.isEmpty(strString)) {
                return false;
            }
            return "001001".equals(((RestErrorResponse) new Gson().fromJson(strString, RestErrorResponse.class)).getRetCode());
        } catch (JsonSyntaxException | IOException unused) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String packageParams(String str, MLRemoteTextSetting mLRemoteTextSetting) {
        return String.format(Locale.ENGLISH, "{\"imgBase64\":\"%s\",\"coordsType\":\"%s\",\"languages\":%s}", str, mLRemoteTextSetting.getBorderType(), new Gson().toJson(mLRemoteTextSetting.getLanguageList(), new TypeToken<ArrayList<String>>() { // from class: com.huawei.hms.mlsdk.text.internal.client.CloudTextAnalyzer.2
        }.getType()));
    }

    public static List<TextLanguage> propertyToLanguages(List<Language> list) {
        ArrayList arrayList = new ArrayList();
        if (list != null && !list.isEmpty()) {
            Iterator<Language> it = list.iterator();
            while (it.hasNext()) {
                String language = it.next().getLanguage();
                arrayList.add(!language.isEmpty() ? new TextLanguage(language) : null);
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bitmap resizeImage(Bitmap bitmap, float f) {
        return Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() / f), (int) (bitmap.getHeight() / f), true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Response<String> traverseCloudAnalyzerApi(List<String> list, Map<String, String> map, String str) {
        Response<String> responseExecute = null;
        for (String str2 : list) {
            try {
                SmartLog.i(TAG, "request start, url = " + str2, false);
                this.requestService = (RemoteRequestService) e.a().a(str2).a(RemoteRequestService.class);
                responseExecute = this.options.getTextDensityScene() == 1 ? this.requestService.detect("v1/image/recognition/ocr/text", map, str).execute() : this.requestService.detect("v1/image/recognition/ocr/document", map, str).execute();
                if (responseExecute.code() == 200) {
                    return responseExecute;
                }
            } catch (IOException e) {
                SmartLog.e(TAG, a.a("Error===>").append(e.getMessage()).toString());
            }
        }
        return responseExecute;
    }

    public Point[] boundingPloyToPoints(List<Coord> list, float f) {
        if (list == null || list.isEmpty()) {
            return new Point[0];
        }
        Point[] pointArr = new Point[list.size()];
        for (int i = 0; i < list.size(); i++) {
            Coord coord = list.get(i);
            pointArr[i] = new Point((int) (coord.getX() * f), (int) (coord.getY() * f));
        }
        return pointArr;
    }

    public Point[] boundingPloyToPointsFromCoords(List<Coord> list, float f) {
        if (list == null || list.size() <= 0) {
            return new Point[0];
        }
        Point[] pointArr = new Point[list.size()];
        for (int i = 0; i < list.size(); i++) {
            Coord coord = list.get(i);
            pointArr[i] = new Point((int) (coord.getX() * f), (int) (coord.getY() * f));
        }
        return pointArr;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
    }

    public Task<MLText> processImage(final MLFrame mLFrame) {
        return Tasks.callInBackground(new Callable<MLText>() { // from class: com.huawei.hms.mlsdk.text.internal.client.CloudTextAnalyzer.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public MLText call() throws Exception {
                String certFingerprint;
                if (CloudTextAnalyzer.this.options.isEnableFingerprintVerification() && ((certFingerprint = CloudTextAnalyzer.this.f198app.getAppSetting().getCertFingerprint()) == null || certFingerprint.isEmpty())) {
                    throw new MLException("Failed to analyse.", 2);
                }
                String grsCountryCode = GrsRegionUtils.getGrsCountryCode();
                SmartLog.i(CloudTextAnalyzer.TAG, "GRS countryCode is " + grsCountryCode);
                List<String> listAddHttpsHeaders = GrsUtils.addHttpsHeaders(grsCountryCode != null ? GrsUtils.getVisionSearchUrls(GrsUtils.initGrsVisionSearchClientWithCountry(MLApplication.getInstance().getAppContext(), grsCountryCode)) : GrsUtils.getUrls(MLApplication.getInstance().getAppContext(), false));
                if (listAddHttpsHeaders == null || listAddHttpsHeaders.isEmpty()) {
                    throw new MLException("UrlList is empty, failed to detect cloud text.", 2);
                }
                Map<String, String> mapA = new f.b().a().a();
                if (CloudTextAnalyzer.this.isHeaderInvalidate(mapA)) {
                    throw new MLException("Header param error, fail to detect cloud text", 2);
                }
                float fCalculateScale = CloudTextAnalyzer.this.calculateScale(mLFrame.readBitmap());
                String strEncodeToString = Base64.encodeToString(ImageConvertUtils.bitmap2Jpeg(CloudTextAnalyzer.this.resizeImage(mLFrame.readBitmap(), fCalculateScale), 90), 2);
                CloudTextAnalyzer cloudTextAnalyzer = CloudTextAnalyzer.this;
                Response responseTraverseCloudAnalyzerApi = CloudTextAnalyzer.this.traverseCloudAnalyzerApi(listAddHttpsHeaders, mapA, cloudTextAnalyzer.packageParams(strEncodeToString, cloudTextAnalyzer.options));
                if (responseTraverseCloudAnalyzerApi != null && responseTraverseCloudAnalyzerApi.code() == 200) {
                    return CloudTextAnalyzer.this.handleResult(responseTraverseCloudAnalyzerApi, fCalculateScale);
                }
                if (responseTraverseCloudAnalyzerApi == null || responseTraverseCloudAnalyzerApi.code() != 401) {
                    throw new MLException("Cloud text detect failed.", 3);
                }
                if (CloudTextAnalyzer.this.isTokenInvalid(responseTraverseCloudAnalyzerApi.errorBody())) {
                    throw new MLException("Token is invalid or expired.", 19);
                }
                throw new MLException("Cloud text detect failed.", 15);
            }
        });
    }
}
