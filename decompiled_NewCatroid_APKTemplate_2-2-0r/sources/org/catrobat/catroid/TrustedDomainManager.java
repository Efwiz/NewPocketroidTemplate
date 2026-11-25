package org.catrobat.catroid;

import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.utils.Utils;
import org.eclipse.jgit.transport.AmazonS3;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/TrustedDomainManager.class
  classes.dex
 */
/* JADX INFO: compiled from: TrustedDomainManager.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0004J\u0010\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u0004H\u0002J\u0010\u0010\u0014\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u0006\u0010\u0017\u001a\u00020\u0004J\n\u0010\u0018\u001a\u0004\u0018\u00010\tH\u0002J\n\u0010\u0019\u001a\u0004\u0018\u00010\tH\u0002J\u000e\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u0004J\b\u0010\u001c\u001a\u00020\u001dH\u0007J\b\u0010\u001e\u001a\u00020\u0010H\u0002J\u000e\u0010\u001f\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\n \u0007*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\n\u001a\u0004\u0018\u00010\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006 "}, d2 = {"Lorg/catrobat/catroid/TrustedDomainManager;", "", "()V", "PARSE_ERROR_LOG", "", "READ_ERROR_LOG", "TAG", JvmProtoBufUtil.PLATFORM_TYPE_ID, "trustListPattern", "Ljava/util/regex/Pattern;", "userTrustListPattern", "getUserTrustListPattern", "()Ljava/util/regex/Pattern;", "setUserTrustListPattern", "(Ljava/util/regex/Pattern;)V", "addToUserTrustList", "", AmazonS3.Keys.DOMAIN, "cleanUpUserInput", "string", "getTrustListPatternFromDomains", Constants.TRUST_LIST_JSON_ARRAY_NAME, "Lorg/json/JSONArray;", "getUserTrustList", "initializeTrustListPattern", "initializeUserTrustListPattern", "isURLTrusted", "url", "reset", "", "resetUserTrustList", "setUserTrustList", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TrustedDomainManager {
    private static final String PARSE_ERROR_LOG = "Cannot parse trusted domains";
    private static final String READ_ERROR_LOG = "Cannot read trusted domains";
    private static Pattern trustListPattern;
    private static Pattern userTrustListPattern;
    public static final TrustedDomainManager INSTANCE = new TrustedDomainManager();
    private static final String TAG = "TrustedDomainManager";

    private TrustedDomainManager() {
    }

    public final Pattern getUserTrustListPattern() {
        return userTrustListPattern;
    }

    public final void setUserTrustListPattern(Pattern pattern) {
        userTrustListPattern = pattern;
    }

    /* JADX WARN: Code duplicated, block: B:24:0x0045  */
    public final synchronized boolean isURLTrusted(String url) {
        boolean z;
        Matcher matcher;
        Matcher matcher2;
        Intrinsics.checkNotNullParameter(url, "url");
        if (userTrustListPattern == null) {
            userTrustListPattern = initializeUserTrustListPattern();
        }
        if (trustListPattern == null) {
            trustListPattern = initializeTrustListPattern();
        }
        Pattern pattern = trustListPattern;
        if (!((pattern == null || (matcher2 = pattern.matcher(url)) == null) ? false : matcher2.matches())) {
            Pattern pattern2 = userTrustListPattern;
            z = (pattern2 == null || (matcher = pattern2.matcher(url)) == null) ? false : matcher.matches();
        }
        return z;
    }

    public final boolean addToUserTrustList(String domain) {
        JSONArray jSONArray;
        Intrinsics.checkNotNullParameter(domain, "domain");
        try {
            if (Constants.TRUSTED_USER_DOMAINS_FILE.exists()) {
                File TRUSTED_USER_DOMAINS_FILE = Constants.TRUSTED_USER_DOMAINS_FILE;
                Intrinsics.checkNotNullExpressionValue(TRUSTED_USER_DOMAINS_FILE, "TRUSTED_USER_DOMAINS_FILE");
                FileInputStream fileInputStream = new FileInputStream(TRUSTED_USER_DOMAINS_FILE);
                try {
                    jSONArray = Utils.getJsonObjectFromInputStream(fileInputStream).getJSONArray(Constants.TRUST_LIST_JSON_ARRAY_NAME).put(INSTANCE.cleanUpUserInput(domain));
                    CloseableKt.closeFinally(fileInputStream, null);
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        CloseableKt.closeFinally(fileInputStream, th);
                        throw th2;
                    }
                }
            } else {
                if (!Constants.TRUSTED_USER_DOMAINS_FILE.createNewFile()) {
                    return false;
                }
                jSONArray = new JSONArray((Collection) CollectionsKt.listOf(cleanUpUserInput(domain)));
            }
            Intrinsics.checkNotNull(jSONArray);
            userTrustListPattern = getTrustListPatternFromDomains(jSONArray);
            JSONObject jSONObject = new JSONObject(MapsKt.mapOf(TuplesKt.to(Constants.TRUST_LIST_JSON_ARRAY_NAME, jSONArray)));
            File TRUSTED_USER_DOMAINS_FILE2 = Constants.TRUSTED_USER_DOMAINS_FILE;
            Intrinsics.checkNotNullExpressionValue(TRUSTED_USER_DOMAINS_FILE2, "TRUSTED_USER_DOMAINS_FILE");
            String string = jSONObject.toString(4);
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            FilesKt.writeText$default(TRUSTED_USER_DOMAINS_FILE2, string, null, 2, null);
            return true;
        } catch (IOException e) {
            Log.e(TAG, READ_ERROR_LOG, e);
            return false;
        } catch (JSONException e2) {
            Log.e(TAG, PARSE_ERROR_LOG, e2);
            return false;
        }
    }

    public final String getUserTrustList() {
        try {
            if (!Constants.TRUSTED_USER_DOMAINS_FILE.exists()) {
                return "";
            }
            File TRUSTED_USER_DOMAINS_FILE = Constants.TRUSTED_USER_DOMAINS_FILE;
            Intrinsics.checkNotNullExpressionValue(TRUSTED_USER_DOMAINS_FILE, "TRUSTED_USER_DOMAINS_FILE");
            FileInputStream fileInputStream = new FileInputStream(TRUSTED_USER_DOMAINS_FILE);
            try {
                JSONArray jSONArray = Utils.getJsonObjectFromInputStream(fileInputStream).getJSONArray(Constants.TRUST_LIST_JSON_ARRAY_NAME);
                TrustedDomainManager trustedDomainManager = INSTANCE;
                String strJoin = jSONArray.join("\n");
                Intrinsics.checkNotNullExpressionValue(strJoin, "join(...)");
                String strCleanUpUserInput = trustedDomainManager.cleanUpUserInput(strJoin);
                CloseableKt.closeFinally(fileInputStream, null);
                return strCleanUpUserInput;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(fileInputStream, th);
                    throw th2;
                }
            }
        } catch (IOException e) {
            Log.e(TAG, READ_ERROR_LOG, e);
            return "";
        } catch (JSONException e2) {
            Log.e(TAG, PARSE_ERROR_LOG, e2);
            return "";
        }
    }

    public final boolean setUserTrustList(String domains) {
        Intrinsics.checkNotNullParameter(domains, "domains");
        try {
            if (StringsKt.isBlank(domains)) {
                return resetUserTrustList();
            }
            if (!Constants.TRUSTED_USER_DOMAINS_FILE.exists() && !Constants.TRUSTED_USER_DOMAINS_FILE.createNewFile()) {
                return false;
            }
            JSONArray jSONArray = new JSONArray((Collection) StringsKt.split$default((CharSequence) cleanUpUserInput(domains), new String[]{"\n"}, false, 0, 6, (Object) null));
            userTrustListPattern = getTrustListPatternFromDomains(jSONArray);
            JSONObject jSONObject = new JSONObject(MapsKt.mapOf(TuplesKt.to(Constants.TRUST_LIST_JSON_ARRAY_NAME, jSONArray)));
            File TRUSTED_USER_DOMAINS_FILE = Constants.TRUSTED_USER_DOMAINS_FILE;
            Intrinsics.checkNotNullExpressionValue(TRUSTED_USER_DOMAINS_FILE, "TRUSTED_USER_DOMAINS_FILE");
            String string = jSONObject.toString(4);
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            FilesKt.writeText$default(TRUSTED_USER_DOMAINS_FILE, string, null, 2, null);
            return true;
        } catch (IOException e) {
            Log.e(TAG, READ_ERROR_LOG, e);
            return false;
        } catch (JSONException e2) {
            Log.e(TAG, PARSE_ERROR_LOG, e2);
            return false;
        }
    }

    private final Pattern initializeTrustListPattern() {
        try {
            InputStream inputStreamFromAsset = Utils.getInputStreamFromAsset(CatroidApplication.getAppContext(), Constants.TRUSTED_DOMAINS_FILE_NAME);
            try {
                JSONArray jSONArray = Utils.getJsonObjectFromInputStream(inputStreamFromAsset).getJSONArray(Constants.TRUST_LIST_JSON_ARRAY_NAME);
                TrustedDomainManager trustedDomainManager = INSTANCE;
                Intrinsics.checkNotNull(jSONArray);
                Pattern trustListPatternFromDomains = trustedDomainManager.getTrustListPatternFromDomains(jSONArray);
                CloseableKt.closeFinally(inputStreamFromAsset, null);
                return trustListPatternFromDomains;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(inputStreamFromAsset, th);
                    throw th2;
                }
            }
        } catch (IOException e) {
            Log.e(TAG, READ_ERROR_LOG, e);
            return null;
        } catch (JSONException e2) {
            Log.e(TAG, PARSE_ERROR_LOG, e2);
            return null;
        }
    }

    private final Pattern initializeUserTrustListPattern() {
        try {
            if (!Constants.TRUSTED_USER_DOMAINS_FILE.exists()) {
                return null;
            }
            File TRUSTED_USER_DOMAINS_FILE = Constants.TRUSTED_USER_DOMAINS_FILE;
            Intrinsics.checkNotNullExpressionValue(TRUSTED_USER_DOMAINS_FILE, "TRUSTED_USER_DOMAINS_FILE");
            FileInputStream fileInputStream = new FileInputStream(TRUSTED_USER_DOMAINS_FILE);
            try {
                JSONArray jSONArray = Utils.getJsonObjectFromInputStream(fileInputStream).getJSONArray(Constants.TRUST_LIST_JSON_ARRAY_NAME);
                TrustedDomainManager trustedDomainManager = INSTANCE;
                Intrinsics.checkNotNull(jSONArray);
                Pattern trustListPatternFromDomains = trustedDomainManager.getTrustListPatternFromDomains(jSONArray);
                CloseableKt.closeFinally(fileInputStream, null);
                return trustListPatternFromDomains;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(fileInputStream, th);
                    throw th2;
                }
            }
        } catch (IOException e) {
            Log.e(TAG, READ_ERROR_LOG, e);
            return null;
        } catch (JSONException e2) {
            Log.e(TAG, PARSE_ERROR_LOG, e2);
            return null;
        }
    }

    private final Pattern getTrustListPatternFromDomains(JSONArray domains) {
        StringBuilder sb = new StringBuilder("(");
        int length = domains.length();
        for (int i = 0; i < length; i++) {
            sb.append(domains.getString(i));
            if (i < domains.length() - 1) {
                sb.append('|');
            }
        }
        sb.append(')');
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        Pattern patternCompile = Pattern.compile("https?://([a-zA-Z0-9-]+\\.)*" + new Regex("\\.").replace(string, "\\\\.") + "(:[0-9]{1,5})?(/.*)?");
        Intrinsics.checkNotNullExpressionValue(patternCompile, "compile(...)");
        return patternCompile;
    }

    private final String cleanUpUserInput(String string) {
        return StringsKt.removeSuffix(new Regex("[ \"{}\\[\\]]|(\\n){2,}").replace(string, ""), (CharSequence) "\n");
    }

    private final boolean resetUserTrustList() {
        userTrustListPattern = null;
        return Constants.TRUSTED_USER_DOMAINS_FILE.delete();
    }

    public final void reset() {
        resetUserTrustList();
        trustListPattern = null;
    }
}
