package org.catrobat.catroid.scratchconverter.protocol.message.base;

import android.util.SparseArray;
import java.util.ArrayList;
import org.catrobat.catroid.scratchconverter.protocol.Job;
import org.catrobat.catroid.scratchconverter.protocol.JsonKeys;
import org.catrobat.catroid.scratchconverter.protocol.message.Message;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/scratchconverter/protocol/message/base/BaseMessage.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public abstract class BaseMessage extends Message {

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/scratchconverter/protocol/message/base/BaseMessage$Type.class
      classes14.dex
     */
    public enum Type {
        ERROR(0),
        INFO(1),
        CLIENT_ID(2);

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

    public static <T extends BaseMessage> T fromJson(final JSONObject jsonMessage) throws JSONException {
        JSONObject jSONObject = jsonMessage.getJSONObject(JsonKeys.DATA.toString());
        int i = AnonymousClass1.$SwitchMap$org$catrobat$catroid$scratchconverter$protocol$message$base$BaseMessage$Type[Type.valueOf(jsonMessage.getInt(JsonKeys.TYPE.toString())).ordinal()];
        if (i == 1) {
            return new ErrorMessage(jSONObject.getString(JsonKeys.JsonDataKeys.MSG.toString()));
        }
        if (i == 2) {
            return new ClientIDMessage(jSONObject.getLong(JsonKeys.JsonDataKeys.CLIENT_ID.toString()));
        }
        if (i != 3) {
            return null;
        }
        double d = jSONObject.getDouble(JsonKeys.JsonDataKeys.CATROBAT_LANGUAGE_VERSION.toString());
        JSONArray jSONArray = jSONObject.getJSONArray(JsonKeys.JsonDataKeys.JOBS_INFO.toString());
        ArrayList arrayList = new ArrayList();
        if (jSONArray != null) {
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                arrayList.add(Job.fromJson(jSONArray.getJSONObject(i2)));
            }
        }
        return new InfoMessage(d, (Job[]) arrayList.toArray(new Job[arrayList.size()]));
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/scratchconverter/protocol/message/base/BaseMessage$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.scratchconverter.protocol.message.base.BaseMessage$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$scratchconverter$protocol$message$base$BaseMessage$Type;

        static {
            int[] iArr = new int[Type.values().length];
            $SwitchMap$org$catrobat$catroid$scratchconverter$protocol$message$base$BaseMessage$Type = iArr;
            try {
                iArr[Type.ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$scratchconverter$protocol$message$base$BaseMessage$Type[Type.CLIENT_ID.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$scratchconverter$protocol$message$base$BaseMessage$Type[Type.INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }
}
