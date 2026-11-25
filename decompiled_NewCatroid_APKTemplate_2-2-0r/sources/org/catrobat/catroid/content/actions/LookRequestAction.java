package org.catrobat.catroid.content.actions;

import android.content.Context;
import android.util.Log;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.io.StorageOperations;
import org.catrobat.catroid.sensing.CollisionInformation;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.utils.Utils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/LookRequestAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: LookRequestAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0004H\u0016J\b\u0010\u001a\u001a\u00020\u0018H\u0002J\b\u0010\u001b\u001a\u00020\u0018H\u0016J\b\u0010\u001c\u001a\u00020\u0018H\u0016J\u0010\u0010\u001d\u001a\u00020\u00182\u0006\u0010\u001e\u001a\u00020\u0004H\u0016J\u0010\u0010\u001f\u001a\u00020\u00182\u0006\u0010 \u001a\u00020!H\u0016J\b\u0010\"\u001a\u00020\u0018H\u0016J\u0010\u0010#\u001a\u00020\u00182\u0006\u0010$\u001a\u00020\u0004H\u0002J\b\u0010%\u001a\u00020\u0018H\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0010\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010¨\u0006&"}, d2 = {"Lorg/catrobat/catroid/content/actions/LookRequestAction;", "Lorg/catrobat/catroid/content/actions/WebAction;", "()V", "errorCode", "", "getErrorCode", "()Ljava/lang/String;", "setErrorCode", "(Ljava/lang/String;)V", "fileExtension", "lookName", "response", "Ljava/io/InputStream;", "getResponse", "()Ljava/io/InputStream;", "setResponse", "(Ljava/io/InputStream;)V", "act", "", "delta", "", "getLookFromResponse", "Lorg/catrobat/catroid/common/LookData;", "handleError", "", "error", "handleInvalidFormat", "handleResponse", "onCancelledCall", "onRequestError", "httpError", "onRequestSuccess", "httpResponse", "Lokhttp3/Response;", "restart", "showToastMessage", CrashHianalyticsData.MESSAGE, "updateLookListIndex", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class LookRequestAction extends WebAction {
    private String errorCode;
    private String fileExtension;
    private String lookName;
    private InputStream response;

    public final InputStream getResponse() {
        return this.response;
    }

    public final void setResponse(InputStream inputStream) {
        this.response = inputStream;
    }

    public final String getErrorCode() {
        return this.errorCode;
    }

    public final void setErrorCode(String str) {
        this.errorCode = str;
    }

    @Override // org.catrobat.catroid.content.actions.WebAction, com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        Scope scope = getScope();
        if ((scope != null ? scope.getSprite() : null) != null && getFormula() != null) {
            Scope scope2 = getScope();
            if ((scope2 != null ? scope2.getSequence() : null) != null) {
                return super.act(delta);
            }
        }
        return true;
    }

    @Override // org.catrobat.catroid.content.actions.WebAction
    public void handleResponse() throws InterruptedException {
        Thread thread;
        Sprite sprite;
        LookData lookFromResponse = getLookFromResponse();
        if (lookFromResponse != null) {
            updateLookListIndex();
            Scope scope = getScope();
            Look look = (scope == null || (sprite = scope.getSprite()) == null) ? null : sprite.look;
            if (look != null) {
                look.setLookData(lookFromResponse);
            }
            CollisionInformation collisionInformation = lookFromResponse.getCollisionInformation();
            if (collisionInformation != null && (thread = collisionInformation.collisionPolygonCalculationThread) != null) {
                thread.join();
            }
            File file = lookFromResponse.getFile();
            if (file != null) {
                file.delete();
            }
            lookFromResponse.setIsWebRequest(false);
        }
    }

    private final void updateLookListIndex() {
        Sprite sprite;
        List<LookData> lookList;
        Sprite sprite2;
        Look look;
        Sprite sprite3;
        Sprite sprite4;
        Scope scope = getScope();
        LookData lookData = null;
        Look look2 = (scope == null || (sprite4 = scope.getSprite()) == null) ? null : sprite4.look;
        int iIndexOf = -1;
        if (look2 == null || look2.getLookListIndexBeforeLookRequest() <= -1) {
            Scope scope2 = getScope();
            Look look3 = (scope2 == null || (sprite3 = scope2.getSprite()) == null) ? null : sprite3.look;
            if (look3 == null) {
                return;
            }
            Scope scope3 = getScope();
            if (scope3 != null && (sprite = scope3.getSprite()) != null && (lookList = sprite.getLookList()) != null) {
                Scope scope4 = getScope();
                if (scope4 != null && (sprite2 = scope4.getSprite()) != null && (look = sprite2.look) != null) {
                    lookData = look.getLookData();
                }
                iIndexOf = lookList.indexOf(lookData);
            }
            look3.setLookListIndexBeforeLookRequest(iIndexOf);
        }
    }

    public final LookData getLookFromResponse() {
        String str = this.errorCode;
        if (str != null) {
            Intrinsics.checkNotNull(str);
            handleError(str);
            return null;
        }
        if (this.response == null) {
            handleInvalidFormat();
            return null;
        }
        try {
            File fileCreateTempFile = File.createTempFile(this.lookName, this.fileExtension);
            StorageOperations.copyStreamToFile(this.response, fileCreateTempFile);
            LookData lookData = new LookData(this.lookName, fileCreateTempFile);
            lookData.getCollisionInformation().calculate();
            return lookData;
        } catch (IOException e) {
            Log.e(getClass().getSimpleName(), "Couldn't interpret InputStream as image", e);
            handleInvalidFormat();
            return null;
        }
    }

    private final void handleInvalidFormat() {
        Context appContext = CatroidApplication.getAppContext();
        if (appContext != null) {
            String string = appContext.getString(R.string.look_request_type_error_message, getUrl());
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            showToastMessage(string);
        }
    }

    private final void showToastMessage(String message) {
        StageActivity.messageHandler.obtainMessage(3, new ArrayList(CollectionsKt.listOf(message))).sendToTarget();
    }

    @Override // org.catrobat.catroid.content.actions.WebAction
    public void handleError(String error) {
        Intrinsics.checkNotNullParameter(error, "error");
        this.errorCode = error;
        Context appContext = CatroidApplication.getAppContext();
        if (appContext != null) {
            String string = appContext.getString(R.string.look_request_http_error_message, getUrl(), this.errorCode);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            showToastMessage(string);
        }
    }

    @Override // org.catrobat.catroid.content.actions.WebAction, com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        this.response = null;
        this.errorCode = null;
        this.lookName = null;
        super.restart();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:39:0x00c1  */
    @Override // org.catrobat.catroid.content.actions.WebAction, org.catrobat.catroid.web.WebConnection.WebRequestListener
    public void onRequestSuccess(Response httpResponse) {
        String str;
        String str2;
        Intrinsics.checkNotNullParameter(httpResponse, "httpResponse");
        ResponseBody responseBodyBody = httpResponse.body();
        String lowerCase = null;
        this.response = responseBodyBody != null ? responseBodyBody.byteStream() : null;
        MediaType mediaType = responseBodyBody != null ? responseBodyBody.get$contentType() : null;
        if (mediaType != null) {
            String lowerCase2 = mediaType.type().toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase2, "toLowerCase(...)");
            String lowerCase3 = mediaType.subtype().toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase3, "toLowerCase(...)");
            Log.d("LookRequestAction", "Received Content-Type: " + lowerCase2 + "/" + lowerCase3);
            switch (lowerCase2 + "/" + lowerCase3) {
                case "image/jpeg":
                    str = Constants.JPEG_IMAGE_EXTENSION;
                    break;
                case "image/webp":
                    str = ".webp";
                    break;
                case "image/bmp":
                    str = ".bmp";
                    break;
                case "image/gif":
                    str = ".gif";
                    break;
                case "image/png":
                    str = Constants.DEFAULT_IMAGE_EXTENSION;
                    break;
                case "application/octet-stream":
                    Log.d("LookRequestAction", "Content-Type is application/octet-stream. Relying on filename extension.");
                default:
                    str = null;
                    break;
            }
        } else {
            str = null;
        }
        String fileNameFromHttpResponse = Utils.getFileNameFromHttpResponse(httpResponse);
        if (fileNameFromHttpResponse == null) {
            fileNameFromHttpResponse = Utils.getFileNameFromURL(getUrl());
        }
        Intrinsics.checkNotNull(fileNameFromHttpResponse);
        List listSplit$default = StringsKt.split$default((CharSequence) fileNameFromHttpResponse, new char[]{'.'}, false, 2, 2, (Object) null);
        this.lookName = (String) listSplit$default.get(0);
        String str3 = (String) CollectionsKt.getOrNull(listSplit$default, 1);
        if (str3 != null && (str2 = "." + str3) != null) {
            lowerCase = str2.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        }
        if (str != null) {
            this.fileExtension = str;
            if (lowerCase != null && !Intrinsics.areEqual(lowerCase, str)) {
                Log.i("LookRequestAction", "Overriding original extension '" + lowerCase + "' with '" + str + "' from Content-Type.");
            }
        } else {
            this.fileExtension = lowerCase;
        }
        String str4 = this.fileExtension;
        if (str4 == null) {
            Log.w("LookRequestAction", "Could not determine file extension. Using default for temp file.");
        } else {
            Log.i("LookRequestAction", "Using file extension: " + str4 + " for look: " + this.lookName);
        }
        super.onRequestSuccess(httpResponse);
    }

    @Override // org.catrobat.catroid.content.actions.WebAction, org.catrobat.catroid.web.WebConnection.WebRequestListener
    public void onRequestError(String httpError) {
        Intrinsics.checkNotNullParameter(httpError, "httpError");
        this.errorCode = httpError;
        super.onRequestError(httpError);
    }

    @Override // org.catrobat.catroid.content.actions.WebAction, org.catrobat.catroid.web.WebConnection.WebRequestListener
    public void onCancelledCall() {
        this.response = null;
        this.errorCode = null;
        this.lookName = null;
        super.onCancelledCall();
    }
}
