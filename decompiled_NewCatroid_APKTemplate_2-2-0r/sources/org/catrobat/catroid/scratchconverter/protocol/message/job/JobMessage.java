package org.catrobat.catroid.scratchconverter.protocol.message.job;

import android.util.Log;
import android.util.SparseArray;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.scratchconverter.protocol.JsonKeys;
import org.catrobat.catroid.scratchconverter.protocol.message.Message;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/scratchconverter/protocol/message/job/JobMessage.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public abstract class JobMessage extends Message {
    private static final String TAG = "JobMessage";
    private final long jobID;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/scratchconverter/protocol/message/job/JobMessage$Type.class
      classes14.dex
     */
    public enum Type {
        JOB_FAILED(0),
        JOB_RUNNING(1),
        JOB_ALREADY_RUNNING(2),
        JOB_READY(3),
        JOB_OUTPUT(4),
        JOB_PROGRESS(5),
        JOB_FINISHED(6);

        private static SparseArray<Type> types = new SparseArray<>();
        private int typeID;

        static {
            for (Type type : values()) {
                types.put(type.typeID, type);
            }
        }

        Type(final int typeID) {
            this.typeID = typeID;
        }

        public static Type valueOf(int typeID) {
            return types.get(typeID);
        }

        public int getTypeID() {
            return this.typeID;
        }
    }

    public JobMessage(final long jobID) {
        this.jobID = jobID;
    }

    public long getJobID() {
        return this.jobID;
    }

    public static <T extends JobMessage> T fromJson(JSONObject jSONObject) throws JSONException, ParseException {
        JSONObject jSONObject2 = jSONObject.getJSONObject(JsonKeys.DATA.toString());
        long j = jSONObject2.getLong(JsonKeys.JsonDataKeys.JOB_ID.toString());
        date = null;
        Date date = null;
        switch (AnonymousClass1.$SwitchMap$org$catrobat$catroid$scratchconverter$protocol$message$job$JobMessage$Type[Type.valueOf(jSONObject.getInt(JsonKeys.TYPE.toString())).ordinal()]) {
            case 1:
                return new JobFailedMessage(j, jSONObject2.getString(JsonKeys.JsonDataKeys.MSG.toString()));
            case 2:
                String string = jSONObject2.getString(JsonKeys.JsonDataKeys.JOB_TITLE.toString());
                String string2 = JsonKeys.JsonDataKeys.JOB_IMAGE_URL.toString();
                return new JobRunningMessage(j, string, jSONObject2.isNull(string2) ? null : jSONObject2.getString(string2));
            case 3:
                String string3 = jSONObject2.getString(JsonKeys.JsonDataKeys.JOB_TITLE.toString());
                String string4 = JsonKeys.JsonDataKeys.JOB_IMAGE_URL.toString();
                return new JobAlreadyRunningMessage(j, string3, jSONObject2.isNull(string4) ? null : jSONObject2.getString(string4));
            case 4:
                return new JobReadyMessage(j);
            case 5:
                JSONArray jSONArray = jSONObject2.getJSONArray(JsonKeys.JsonDataKeys.LINES.toString());
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < jSONArray.length(); i++) {
                    arrayList.add(jSONArray.getString(i));
                }
                return new JobOutputMessage(j, (String[]) arrayList.toArray(new String[arrayList.size()]));
            case 6:
                return new JobProgressMessage(j, (short) jSONObject2.getInt(JsonKeys.JsonDataKeys.PROGRESS.toString()));
            case 7:
                String string5 = jSONObject2.getString(JsonKeys.JsonDataKeys.URL.toString());
                String string6 = JsonKeys.JsonDataKeys.CACHED_UTC_DATE.toString();
                String string7 = jSONObject2.isNull(string6) ? null : jSONObject2.getString(string6);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_DEFAULT, Locale.US);
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                if (string7 != null) {
                    try {
                        date = simpleDateFormat.parse(string7);
                    } catch (ParseException e) {
                        Log.e(TAG, e.getLocalizedMessage());
                    }
                }
                return new JobFinishedMessage(j, string5, date);
            default:
                return null;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/scratchconverter/protocol/message/job/JobMessage$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.scratchconverter.protocol.message.job.JobMessage$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$scratchconverter$protocol$message$job$JobMessage$Type;

        static {
            int[] iArr = new int[Type.values().length];
            $SwitchMap$org$catrobat$catroid$scratchconverter$protocol$message$job$JobMessage$Type = iArr;
            try {
                iArr[Type.JOB_FAILED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$scratchconverter$protocol$message$job$JobMessage$Type[Type.JOB_RUNNING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$scratchconverter$protocol$message$job$JobMessage$Type[Type.JOB_ALREADY_RUNNING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$scratchconverter$protocol$message$job$JobMessage$Type[Type.JOB_READY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$scratchconverter$protocol$message$job$JobMessage$Type[Type.JOB_OUTPUT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$scratchconverter$protocol$message$job$JobMessage$Type[Type.JOB_PROGRESS.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$scratchconverter$protocol$message$job$JobMessage$Type[Type.JOB_FINISHED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }
}
