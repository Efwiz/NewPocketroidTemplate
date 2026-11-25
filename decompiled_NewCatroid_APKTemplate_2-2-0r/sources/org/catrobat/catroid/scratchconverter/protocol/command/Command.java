package org.catrobat.catroid.scratchconverter.protocol.command;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/scratchconverter/protocol/command/Command.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public abstract class Command {
    private final Map<ArgumentType, Object> arguments = new EnumMap(ArgumentType.class);
    private final Type type;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/scratchconverter/protocol/command/Command$Type.class
      classes14.dex
     */
    public enum Type {
        AUTHENTICATE(0),
        RETRIEVE_INFO(1),
        SCHEDULE_JOB(2),
        CANCEL_DOWNLOAD(3);

        private final int typeID;

        Type(final int typeID) {
            this.typeID = typeID;
        }

        public int getTypeID() {
            return this.typeID;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/scratchconverter/protocol/command/Command$ArgumentType.class
      classes14.dex
     */
    public enum ArgumentType {
        CLIENT_ID("clientID"),
        JOB_ID("jobID"),
        FORCE("force"),
        VERBOSE("verbose");

        private final String rawValue;

        ArgumentType(final String rawValue) {
            this.rawValue = rawValue;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.rawValue;
        }
    }

    public Command(Type type) {
        this.type = type;
    }

    public void addArgument(ArgumentType type, Object value) {
        this.arguments.put(type, value);
    }

    public JSONObject toJson() {
        HashMap map = new HashMap();
        for (Map.Entry<ArgumentType, Object> entry : this.arguments.entrySet()) {
            map.put(entry.getKey().toString(), entry.getValue());
        }
        HashMap map2 = new HashMap();
        map2.put("cmd", Integer.valueOf(this.type.getTypeID()));
        map2.put("args", map);
        return new JSONObject(map2);
    }

    public Type getType() {
        return this.type;
    }
}
