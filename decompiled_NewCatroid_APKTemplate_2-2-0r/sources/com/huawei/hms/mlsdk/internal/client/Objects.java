package com.huawei.hms.mlsdk.internal.client;

import com.huawei.hms.framework.common.ContainerUtils;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes10.dex */
public class Objects {
    public static boolean equal(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    public static int hashCode(Object... objArr) {
        return Arrays.hashCode(objArr);
    }

    public static ToStringHelper toStringHelper(Object obj) {
        return new ToStringHelper(obj);
    }

    public static final class ToStringHelper {
        private Map<String, Object> attributes;
        private Object obj;

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer(71);
            stringBuffer.append(this.obj.getClass().getName()).append("{");
            for (Map.Entry<String, Object> entry : this.attributes.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value == null) {
                    stringBuffer.append(key + "=null");
                } else if (this.obj.getClass().isPrimitive()) {
                    stringBuffer.append(key + ContainerUtils.KEY_VALUE_DELIMITER + value.toString());
                } else {
                    stringBuffer.append(key + "={" + value.toString() + "}");
                }
                stringBuffer.append(",");
            }
            if (stringBuffer.charAt(stringBuffer.length() - 1) == ',') {
                stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            }
            stringBuffer.append("}");
            return stringBuffer.toString();
        }

        public ToStringHelper attribute(String str, Object obj) {
            this.attributes.put(str, obj);
            return this;
        }

        private ToStringHelper(Object obj) {
            this.attributes = new LinkedHashMap();
            this.obj = obj;
        }
    }
}
