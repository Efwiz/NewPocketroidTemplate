package org.catrobat.catroid.common;

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.text.format.DateUtils;
import java.util.Date;
import org.catrobat.catroid.transfers.GetSurveyTask;
import org.catrobat.catroid.ui.WebViewActivity;
import org.catrobat.catroid.utils.Utils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/common/Survey.class
  classes12.dex
 */
/* JADX INFO: loaded from: classes12.dex */
public class Survey implements GetSurveyTask.SurveyResponseListener {
    public static final int MINIMUM_TIME_SPENT_IN_APP_IN_SECONDS = 3600;
    long sessionTimeSpentInIdeInSeconds;
    long stageStartTimeInMilliseconds;
    boolean fulfilledSurveyRequirements = false;
    boolean uploadFlag = false;
    long sessionTimeSpentInStageInMilliseconds = 0;
    long sessionStartTimeInMilliseconds = 0;

    public Survey(Context context) {
        checkSurveyRequirement(context);
    }

    public void startAppTime(Context context) {
        checkSurveyRequirement(context);
        PreferenceManager.getDefaultSharedPreferences(context).edit().putLong(SharedPreferenceKeys.LAST_USED_DATE_KEY, new Date(System.currentTimeMillis()).getTime()).apply();
        if (this.fulfilledSurveyRequirements || this.sessionStartTimeInMilliseconds != 0) {
            return;
        }
        this.sessionStartTimeInMilliseconds = System.currentTimeMillis();
    }

    public void endAppTime(Context context) {
        if (this.fulfilledSurveyRequirements) {
            return;
        }
        this.sessionTimeSpentInIdeInSeconds = ((System.currentTimeMillis() - this.sessionStartTimeInMilliseconds) - this.sessionTimeSpentInStageInMilliseconds) / 1000;
        this.sessionStartTimeInMilliseconds = 0L;
        PreferenceManager.getDefaultSharedPreferences(context).edit().putLong(SharedPreferenceKeys.TIME_SPENT_IN_APP_IN_SECONDS_KEY, PreferenceManager.getDefaultSharedPreferences(context).getLong(SharedPreferenceKeys.TIME_SPENT_IN_APP_IN_SECONDS_KEY, 0L) + this.sessionTimeSpentInIdeInSeconds).apply();
    }

    public void startStageTime() {
        if (this.fulfilledSurveyRequirements) {
            return;
        }
        this.stageStartTimeInMilliseconds = System.currentTimeMillis();
    }

    public void endStageTime() {
        if (this.fulfilledSurveyRequirements) {
            return;
        }
        this.sessionTimeSpentInStageInMilliseconds += System.currentTimeMillis() - this.stageStartTimeInMilliseconds;
    }

    private void checkSurveyRequirement(Context context) {
        if (this.fulfilledSurveyRequirements) {
            return;
        }
        this.fulfilledSurveyRequirements = PreferenceManager.getDefaultSharedPreferences(context).getLong(SharedPreferenceKeys.TIME_SPENT_IN_APP_IN_SECONDS_KEY, 0L) > 3600;
    }

    public boolean getUploadFlag() {
        return this.uploadFlag;
    }

    public void setUploadFlag(boolean value) {
        this.uploadFlag = value;
    }

    public void showSurvey(Context context) {
        if (this.fulfilledSurveyRequirements) {
            long j = PreferenceManager.getDefaultSharedPreferences(context).getLong(SharedPreferenceKeys.LAST_USED_DATE_KEY, new Date(System.currentTimeMillis()).getTime());
            boolean z = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(SharedPreferenceKeys.SHOW_SURVEY_KEY, false);
            if (!DateUtils.isToday(j) || (z && getUploadFlag())) {
                if (Utils.isNetworkAvailable(context)) {
                    getSurvey(context);
                } else {
                    PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(SharedPreferenceKeys.SHOW_SURVEY_KEY, true).apply();
                }
            }
        }
        this.uploadFlag = false;
    }

    public void getSurvey(Context context) {
        GetSurveyTask getSurveyTask = new GetSurveyTask(context);
        getSurveyTask.setOnSurveyResponseListener(this);
        getSurveyTask.execute(new String[0]);
    }

    @Override // org.catrobat.catroid.transfers.GetSurveyTask.SurveyResponseListener
    public void onSurveyReceived(Context context, String surveyUrl) {
        if (isUrlNew(context, surveyUrl)) {
            Intent intent = new Intent(context, (Class<?>) WebViewActivity.class);
            intent.putExtra("url", surveyUrl);
            intent.putExtra(WebViewActivity.INTENT_FORCE_OPEN_IN_APP, true);
            context.startActivity(intent);
            saveUrlHash(context, surveyUrl);
            PreferenceManager.getDefaultSharedPreferences(context).edit().putLong(SharedPreferenceKeys.LAST_USED_DATE_KEY, new Date(System.currentTimeMillis()).getTime()).apply();
            PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(SharedPreferenceKeys.SHOW_SURVEY_KEY, false).apply();
        }
    }

    public void saveUrlHash(Context context, String surveyUrl) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putLong(SharedPreferenceKeys.SURVEY_URL2_HASH_KEY, PreferenceManager.getDefaultSharedPreferences(context).getLong(SharedPreferenceKeys.SURVEY_URL1_HASH_KEY, 0L)).apply();
        PreferenceManager.getDefaultSharedPreferences(context).edit().putLong(SharedPreferenceKeys.SURVEY_URL1_HASH_KEY, surveyUrl.hashCode()).apply();
    }

    public boolean isUrlNew(Context context, String surveyUrl) {
        return (((long) surveyUrl.hashCode()) == PreferenceManager.getDefaultSharedPreferences(context).getLong(SharedPreferenceKeys.SURVEY_URL1_HASH_KEY, 0L) || ((long) surveyUrl.hashCode()) == PreferenceManager.getDefaultSharedPreferences(context).getLong(SharedPreferenceKeys.SURVEY_URL2_HASH_KEY, 0L)) ? false : true;
    }
}
