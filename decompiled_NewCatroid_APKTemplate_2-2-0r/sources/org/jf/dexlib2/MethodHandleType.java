package org.jf.dexlib2;

import java.util.HashMap;
import java.util.Map;
import org.jf.util.ExceptionWithContext;

/* JADX INFO: loaded from: classes6.dex */
public class MethodHandleType {
    public static final int INSTANCE_GET = 3;
    public static final int INSTANCE_PUT = 2;
    public static final int INVOKE_CONSTRUCTOR = 6;
    public static final int INVOKE_DIRECT = 7;
    public static final int INVOKE_INSTANCE = 5;
    public static final int INVOKE_INTERFACE = 8;
    public static final int INVOKE_STATIC = 4;
    public static final int STATIC_GET = 1;
    public static final int STATIC_PUT = 0;
    private static final Map<Integer, String> methodHandleTypeNames;
    private static final Map<String, Integer> typeNamesMethodHandle;

    static {
        HashMap map = new HashMap();
        methodHandleTypeNames = map;
        map.put(0, "static-put");
        map.put(1, "static-get");
        map.put(2, "instance-put");
        map.put(3, "instance-get");
        map.put(4, "invoke-static");
        map.put(5, "invoke-instance");
        map.put(6, "invoke-constructor");
        map.put(7, "invoke-direct");
        map.put(8, "invoke-interface");
        HashMap map2 = new HashMap();
        typeNamesMethodHandle = map2;
        map2.put("static-put", 0);
        map2.put("static-get", 1);
        map2.put("instance-put", 2);
        map2.put("instance-get", 3);
        map2.put("invoke-static", 4);
        map2.put("invoke-instance", 5);
        map2.put("invoke-constructor", 6);
        map2.put("invoke-direct", 7);
        map2.put("invoke-interface", 8);
    }

    public static String toString(int i) {
        String str = methodHandleTypeNames.get(Integer.valueOf(i));
        if (str != null) {
            return str;
        }
        throw new InvalidMethodHandleTypeException(i);
    }

    public static int getMethodHandleType(String str) {
        Integer num = typeNamesMethodHandle.get(str);
        if (num == null) {
            throw new ExceptionWithContext("Invalid method handle type: %s", str);
        }
        return num.intValue();
    }

    public static class InvalidMethodHandleTypeException extends ExceptionWithContext {
        private final int methodHandleType;

        public InvalidMethodHandleTypeException(int i) {
            super("Invalid method handle type: %d", Integer.valueOf(i));
            this.methodHandleType = i;
        }

        public InvalidMethodHandleTypeException(int i, String str, Object... objArr) {
            super(str, objArr);
            this.methodHandleType = i;
        }

        public int getMethodHandleType() {
            return this.methodHandleType;
        }
    }
}
