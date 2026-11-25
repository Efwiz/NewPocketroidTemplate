package org.catrobat.catroid.utils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.exifinterface.media.ExifInterface;
import com.google.common.base.Splitter;
import com.huawei.hms.framework.common.ContainerUtils;
import com.huawei.hms.mlsdk.asr.MLAsrConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.text.Typography;
import okhttp3.Response;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.DefaultProjectHandler;
import org.catrobat.catroid.common.FlavoredConstants;
import org.catrobat.catroid.common.ScratchProgramData;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.XmlHeader;
import org.catrobat.catroid.formulaeditor.SensorHandler;
import org.catrobat.catroid.io.StorageOperations;
import org.catrobat.catroid.io.XstreamSerializer;
import org.catrobat.catroid.io.asynctask.ProjectSaverKt;
import org.catrobat.catroid.transfers.GoogleLoginHandler;
import org.catrobat.catroid.ui.WebViewActivity;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.catrobat.catroid.web.WebConnectionException;
import org.json.JSONException;
import org.json.JSONObject;
import org.koin.java.KoinJavaComponent;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/Utils.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class Utils {
    public static final ArrayList<String> SPEECH_RECOGNITION_SUPPORTED_LANGUAGES = new ArrayList<>();
    private static final String TAG = "Utils";
    public static final int TRANSLATION_PLURAL_OTHER_INTEGER = 767676;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/Utils$RemixUrlParsingState.class
      classes14.dex
     */
    private enum RemixUrlParsingState {
        STARTING,
        TOKEN,
        BETWEEN
    }

    public static int getBit(int number, int index) {
        if (index < 0 || index >= 32) {
            return 0;
        }
        return (number >> index) & 1;
    }

    public static int setBit(int number, int index, int value) {
        if (index < 0 || index >= 32) {
            return number;
        }
        return value == 0 ? number & (~(1 << index)) : number | (1 << index);
    }

    private Utils() {
        throw new AssertionError();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean checkIsNetworkAvailableAndShowErrorMessage(Context context) {
        boolean zIsNetworkAvailable = isNetworkAvailable(context);
        if (!zIsNetworkAvailable) {
            ToastUtil.showError(context, R.string.error_internet_connection);
        }
        return zIsNetworkAvailable;
    }

    public static boolean checkForNetworkError(WebConnectionException exception) {
        return exception != null && exception.getStatusCode() == 1002;
    }

    public static String generateRemixUrlsStringForMergedProgram(XmlHeader headerOfFirstProgram, XmlHeader headerOfSecondProgram) {
        String strReplace = headerOfFirstProgram.getProjectName().replace('[', '(').replace(']', ')').replace(',', ';');
        String strReplace2 = headerOfSecondProgram.getProjectName().replace('[', '(').replace(']', ')').replace(',', ';');
        StringBuilder sb = new StringBuilder(strReplace);
        if (!headerOfFirstProgram.getRemixParentsUrlString().equals("")) {
            sb.append(" [").append(headerOfFirstProgram.getRemixParentsUrlString()).append(']');
        }
        sb.append(", ").append(strReplace2);
        if (!headerOfSecondProgram.getRemixParentsUrlString().equals("")) {
            sb.append(" [").append(headerOfSecondProgram.getRemixParentsUrlString()).append(']');
        }
        return sb.toString();
    }

    public static List<String> extractRemixUrlsFromString(String text) {
        RemixUrlParsingState remixUrlParsingState = RemixUrlParsingState.STARTING;
        ArrayList arrayList = new ArrayList();
        StringBuffer stringBuffer = new StringBuffer("");
        for (int i = 0; i < text.length(); i++) {
            char cCharAt = text.charAt(i);
            if (cCharAt != '[') {
                if (cCharAt == ']') {
                    if (remixUrlParsingState == RemixUrlParsingState.TOKEN) {
                        String strTrim = stringBuffer.toString().trim();
                        if (!strTrim.contains(String.valueOf(',')) && strTrim.length() > 0) {
                            arrayList.add(strTrim);
                        }
                        stringBuffer.delete(0, stringBuffer.length());
                        remixUrlParsingState = RemixUrlParsingState.BETWEEN;
                    }
                } else {
                    remixUrlParsingState = RemixUrlParsingState.TOKEN;
                    stringBuffer.append(cCharAt);
                }
            } else if (remixUrlParsingState == RemixUrlParsingState.STARTING) {
                remixUrlParsingState = RemixUrlParsingState.BETWEEN;
            } else if (remixUrlParsingState == RemixUrlParsingState.TOKEN) {
                stringBuffer.delete(0, stringBuffer.length());
                remixUrlParsingState = RemixUrlParsingState.BETWEEN;
            }
        }
        if (arrayList.size() == 0 && !text.contains(String.valueOf(','))) {
            arrayList.add(text);
        }
        return arrayList;
    }

    public static String getFileNameFromURL(String url) {
        String str = url.substring(Math.max(url.substring(0, url.lastIndexOf(46)).lastIndexOf(47), 0) + 1).split("[?&#]")[0];
        return str.contains(":") ? str.split(":")[1] : str;
    }

    public static String getFileNameFromHttpResponse(Response httpResponse) {
        String strHeader = httpResponse.header("Content-Disposition");
        if (strHeader == null) {
            return null;
        }
        Matcher matcher = Pattern.compile("filename\\*?=['\"]?(?:UTF-\\d['\"]*)?([^;\r\n\"']*)['\"]?;?").matcher(strHeader);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public static Date getScratchSecondReleasePublishedDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1, Constants.SCRATCH_SECOND_RELEASE_PUBLISHED_DATE_YEAR);
        calendar.set(2, 4);
        calendar.set(5, 9);
        return calendar.getTime();
    }

    public static boolean isDeprecatedScratchProgram(final ScratchProgramData programData) {
        Date scratchSecondReleasePublishedDate = getScratchSecondReleasePublishedDate();
        if (programData.getModifiedDate() == null || !programData.getModifiedDate().before(scratchSecondReleasePublishedDate)) {
            return programData.getCreatedDate() != null && programData.getCreatedDate().before(scratchSecondReleasePublishedDate);
        }
        return true;
    }

    public static long extractScratchJobIDFromURL(final String url) {
        if (!url.startsWith(Constants.SCRATCH_CONVERTER_BASE_URL)) {
            return 0L;
        }
        String str = Splitter.on(Typography.amp).trimResults().withKeyValueSeparator(ContainerUtils.KEY_VALUE_DELIMITER).split(url.split("\\?")[1]).get("job_id");
        if (str == null) {
            return 0L;
        }
        long j = Long.parseLong(str);
        if (j > 0) {
            return j;
        }
        return 0L;
    }

    public static String changeSizeOfScratchImageURL(final String url, int newHeight) {
        return url.replace("480x", Integer.toString(Math.round(newHeight * 1.3333334f)) + "x").replace("x360", "x" + Integer.toString(newHeight));
    }

    public static String sanitizeFileName(String fileName) {
        String strReplaceAll = fileName.replaceAll("[\\\\ /:*?\"^<>|]", "");
        return strReplaceAll.substring(0, Math.min(strReplaceAll.length(), 127));
    }

    public static String md5Checksum(File file) throws Throwable {
        FileInputStream fileInputStream = null;
        if (!file.isFile()) {
            Log.e(TAG, String.format("md5Checksum() Error with file %s isFile: %s isDirectory: %s exists: %s", file.getName(), Boolean.valueOf(file.isFile()), Boolean.valueOf(file.isDirectory()), Boolean.valueOf(file.exists())));
            return null;
        }
        MessageDigest mD5MessageDigest = getMD5MessageDigest();
        try {
            try {
                try {
                    FileInputStream fileInputStream2 = new FileInputStream(file);
                    try {
                        byte[] bArr = new byte[8192];
                        while (true) {
                            int i = fileInputStream2.read(bArr);
                            if (i == -1) {
                                break;
                            }
                            mD5MessageDigest.update(bArr, 0, i);
                        }
                        fileInputStream2.close();
                    } catch (IOException unused) {
                        fileInputStream = fileInputStream2;
                        Log.w(TAG, "IOException thrown in md5Checksum()");
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        return toHex(mD5MessageDigest.digest()).toLowerCase(Locale.US);
                    } catch (Throwable th) {
                        th = th;
                        fileInputStream = fileInputStream2;
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException unused2) {
                                Log.w(TAG, "IOException thrown in finally block of md5Checksum()");
                            }
                        }
                        throw th;
                    }
                } catch (IOException unused3) {
                }
                return toHex(mD5MessageDigest.digest()).toLowerCase(Locale.US);
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException unused4) {
            Log.w(TAG, "IOException thrown in finally block of md5Checksum()");
        }
    }

    public static String md5Checksum(String string) {
        MessageDigest mD5MessageDigest = getMD5MessageDigest();
        mD5MessageDigest.update(string.getBytes());
        return toHex(mD5MessageDigest.digest()).toLowerCase(Locale.US);
    }

    public static double round(double value, int precision) {
        double dPow = (int) Math.pow(10.0d, precision);
        return Math.round(value * dPow) / dPow;
    }

    private static String toHex(byte[] messageDigest) {
        char[] charArray = "0123456789ABCDEF".toCharArray();
        char[] cArr = new char[messageDigest.length * 2];
        int i = 0;
        for (byte b : messageDigest) {
            int i2 = i + 1;
            cArr[i] = charArray[(b & 240) >> 4];
            i += 2;
            cArr[i2] = charArray[b & 15];
        }
        return String.valueOf(cArr);
    }

    private static MessageDigest getMD5MessageDigest() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException unused) {
            Log.w(TAG, "NoSuchAlgorithmException thrown in getMD5MessageDigest()");
            return null;
        }
    }

    public static InputStream getInputStreamFromAsset(Context context, String filename) throws IOException, NullPointerException {
        return context.getAssets().open(filename, 3);
    }

    public static JSONObject getJsonObjectFromInputStream(InputStream stream) throws JSONException {
        return new JSONObject(new Scanner(stream).useDelimiter("\\A").next());
    }

    public static String getVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 128).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Name not found", e);
            return "unknown";
        }
    }

    public static String getCurrentProjectName(Context context) {
        if (ProjectManager.getInstance().getCurrentProject() == null) {
            if (FileMetaDataExtractor.getProjectNames(FlavoredConstants.DEFAULT_ROOT_DIRECTORY).size() == 0) {
                ProjectManager.getInstance().initializeDefaultProject(context);
            }
            String string = PreferenceManager.getDefaultSharedPreferences(context).getString("projectName", null);
            return (string == null || !FileMetaDataExtractor.getProjectNames(FlavoredConstants.DEFAULT_ROOT_DIRECTORY).contains(string)) ? FileMetaDataExtractor.getProjectNames(FlavoredConstants.DEFAULT_ROOT_DIRECTORY).get(0) : string;
        }
        return ProjectManager.getInstance().getCurrentProject().getName();
    }

    public static void setLastUsedProjectName(Context context, String projectName) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString("projectName", projectName).apply();
    }

    public static boolean isDefaultProject(Project projectToCheck, Context context) {
        try {
            String str = "project_" + System.currentTimeMillis();
            while (FileMetaDataExtractor.getProjectNames(FlavoredConstants.DEFAULT_ROOT_DIRECTORY).contains(str)) {
                str = "project_" + System.currentTimeMillis();
            }
            Project projectCreateAndSaveDefaultProject = DefaultProjectHandler.createAndSaveDefaultProject(str, context, false);
            String xmlAsStringFromProject = XstreamSerializer.getInstance().getXmlAsStringFromProject(projectCreateAndSaveDefaultProject);
            StorageOperations.deleteDir(projectCreateAndSaveDefaultProject.getDirectory());
            StringFinder stringFinder = new StringFinder();
            String strFindBetween = stringFinder.findBetween(xmlAsStringFromProject, "<scenes>", "</scenes>");
            if (strFindBetween == null) {
                return false;
            }
            ProjectSaverKt.saveProjectSerial(projectToCheck, context);
            String strFindBetween2 = stringFinder.findBetween(XstreamSerializer.getInstance().getXmlAsStringFromProject(projectToCheck), "<scenes>", "</scenes");
            if (strFindBetween2 == null) {
                return false;
            }
            return strFindBetween.replaceAll("((?s)<scriptId>.*?</scriptId>)", "<scriptId></scriptId>").replaceAll("(?s)<brickId>.*?</brickId>", "<bricktId></brickId>").contentEquals(strFindBetween2.replaceAll("((?s)<scriptId>.*?</scriptId>)", "<scriptId></scriptId>").replaceAll("(?s)<brickId>.*?</brickId>", "<bricktId></brickId>"));
        } catch (IOException | IllegalArgumentException e) {
            Log.e(TAG, Log.getStackTraceString(e));
            return true;
        }
    }

    public static int convertDoubleToPluralInteger(double value) {
        double dAbs = Math.abs(value);
        if (dAbs > 2.5d) {
            return (int) Math.round(dAbs);
        }
        return (dAbs == 0.0d || dAbs == 1.0d || dAbs == 2.0d) ? (int) dAbs : TRANSLATION_PLURAL_OTHER_INTEGER;
    }

    public static void invalidateLoginTokenIfUserRestricted(Context context) {
        if (PreferenceManager.getDefaultSharedPreferences(context).getBoolean(Constants.RESTRICTED_USER, false)) {
            logoutUser(context);
            ToastUtil.showSuccess(context, R.string.logout_successful);
        }
    }

    public static void logoutUser(Context context) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        new GoogleLoginHandler((AppCompatActivity) context).getGoogleSignInClient().signOut();
        defaultSharedPreferences.edit().putString("token", Constants.NO_TOKEN).putString("username", Constants.NO_USERNAME).putString(Constants.GOOGLE_EXCHANGE_CODE, Constants.NO_GOOGLE_EXCHANGE_CODE).putString(Constants.GOOGLE_EMAIL, Constants.NO_GOOGLE_EMAIL).putString(Constants.GOOGLE_USERNAME, Constants.NO_GOOGLE_USERNAME).putString(Constants.GOOGLE_ID, Constants.NO_GOOGLE_ID).putString(Constants.GOOGLE_LOCALE, Constants.NO_GOOGLE_LOCALE).putString(Constants.GOOGLE_ID_TOKEN, Constants.NO_GOOGLE_ID_TOKEN).apply();
        WebViewActivity.clearCookies();
    }

    public static boolean isUserLoggedIn(Context context) {
        String string = PreferenceManager.getDefaultSharedPreferences(context).getString("token", Constants.NO_TOKEN);
        return (string.equals(Constants.NO_TOKEN) || string.length() != 32 || string.equals(ServerAuthenticationConstants.TOKEN_CODE_INVALID)) ? false : true;
    }

    public static void fetchSpeechRecognitionSupportedLanguages(Context context) {
        ArrayList<String> arrayList = SPEECH_RECOGNITION_SUPPORTED_LANGUAGES;
        if (arrayList.isEmpty()) {
            MobileServiceAvailability mobileServiceAvailability = (MobileServiceAvailability) KoinJavaComponent.get(MobileServiceAvailability.class);
            if (mobileServiceAvailability.isGmsAvailable(context)) {
                Intent intent = new Intent("android.speech.action.GET_LANGUAGE_DETAILS");
                intent.setPackage("com.google.android.googlequicksearchbox");
                context.sendOrderedBroadcast(intent, null, new BroadcastReceiver() { // from class: org.catrobat.catroid.utils.Utils.1
                    @Override // android.content.BroadcastReceiver
                    public void onReceive(Context context2, Intent intent2) {
                        Bundle resultExtras = getResultExtras(true);
                        if (resultExtras != null) {
                            String string = resultExtras.getString("android.speech.extra.LANGUAGE_PREFERENCE");
                            SensorHandler.setListeningLanguageSensor(string);
                            ArrayList<String> stringArrayList = resultExtras.getStringArrayList("android.speech.extra.SUPPORTED_LANGUAGES");
                            if (stringArrayList != null) {
                                Utils.SPEECH_RECOGNITION_SUPPORTED_LANGUAGES.clear();
                                Utils.SPEECH_RECOGNITION_SUPPORTED_LANGUAGES.addAll(stringArrayList);
                                Utils.SPEECH_RECOGNITION_SUPPORTED_LANGUAGES.remove(string);
                                Utils.SPEECH_RECOGNITION_SUPPORTED_LANGUAGES.add(0, string);
                                return;
                            }
                            Log.w(Utils.TAG, "onReceive: EXTRA_SUPPORTED_LANGUAGES is null");
                            return;
                        }
                        Log.w(Utils.TAG, "onReceive: Bundle is null");
                    }
                }, null, -1, null, null);
            } else if (mobileServiceAvailability.isHmsAvailable(context)) {
                SensorHandler.setListeningLanguageSensor("en-US");
                arrayList.clear();
                arrayList.add("en-US");
                arrayList.add("de-DE");
                arrayList.add(MLAsrConstants.LAN_EN_IN);
                arrayList.add("es-ES");
                arrayList.add("fr-FR");
                arrayList.add(MLAsrConstants.LAN_ZH_CN);
            }
        }
    }

    public static void removeExifData(File directory, String fileName) throws Throwable {
        if (fileName.toLowerCase(Locale.US).endsWith(".jpeg") || fileName.toLowerCase(Locale.US).endsWith(Constants.JPEG_IMAGE_EXTENSION)) {
            try {
                ExifInterface exifInterface = new ExifInterface(new File(directory, fileName).getAbsolutePath());
                Iterator<String> it = Constants.EXIFTAGS_FOR_EXIFREMOVER.iterator();
                while (it.hasNext()) {
                    exifInterface.setAttribute(it.next(), "");
                }
                exifInterface.saveAttributes();
            } catch (IOException unused) {
                Log.e(TAG, "removeExifData: Failed to remove exif data");
            }
        }
    }

    public static boolean checkForDuplicates(List<Object> anyList) {
        Object obj = null;
        for (Object obj2 : anyList) {
            if (obj2 == obj) {
                return true;
            }
            obj = obj2;
        }
        return false;
    }

    public static void hideStandardSystemKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService("input_method");
        View currentFocus = activity.getCurrentFocus();
        if (currentFocus == null) {
            currentFocus = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
    }

    public static void showStandardSystemKeyboard(Activity activity) {
        activity.getWindow().setSoftInputMode(16);
    }
}
