package org.mozilla.javascript.typedarrays;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Undefined;

/* JADX INFO: loaded from: classes6.dex */
public abstract class NativeArrayBufferView extends IdScriptableObject {
    private static final int Id_buffer = 1;
    private static final int Id_byteLength = 3;
    private static final int Id_byteOffset = 2;
    protected static final int MAX_INSTANCE_ID = 3;
    private static final long serialVersionUID = 6884475582973958419L;
    private static Boolean useLittleEndian;
    protected final NativeArrayBuffer arrayBuffer;
    protected final int byteLength;
    protected final int offset;

    @Override // org.mozilla.javascript.IdScriptableObject
    protected int getMaxInstanceId() {
        return 3;
    }

    public NativeArrayBufferView() {
        this.arrayBuffer = new NativeArrayBuffer();
        this.offset = 0;
        this.byteLength = 0;
    }

    protected NativeArrayBufferView(NativeArrayBuffer nativeArrayBuffer, int i, int i2) {
        this.offset = i;
        this.byteLength = i2;
        this.arrayBuffer = nativeArrayBuffer;
    }

    public NativeArrayBuffer getBuffer() {
        return this.arrayBuffer;
    }

    public int getByteOffset() {
        return this.offset;
    }

    public int getByteLength() {
        return this.byteLength;
    }

    protected static boolean useLittleEndian() {
        if (useLittleEndian == null) {
            Context currentContext = Context.getCurrentContext();
            if (currentContext == null) {
                return false;
            }
            useLittleEndian = Boolean.valueOf(currentContext.hasFeature(19));
        }
        return useLittleEndian.booleanValue();
    }

    protected static boolean isArg(Object[] objArr, int i) {
        return objArr.length > i && !Undefined.instance.equals(objArr[i]);
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected String getInstanceIdName(int i) {
        if (i == 1) {
            return "buffer";
        }
        if (i == 2) {
            return "byteOffset";
        }
        if (i == 3) {
            return "byteLength";
        }
        return super.getInstanceIdName(i);
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected Object getInstanceIdValue(int i) {
        if (i == 1) {
            return this.arrayBuffer;
        }
        if (i == 2) {
            return ScriptRuntime.wrapInt(this.offset);
        }
        if (i == 3) {
            return ScriptRuntime.wrapInt(this.byteLength);
        }
        return super.getInstanceIdValue(i);
    }

    /* JADX WARN: Code duplicated, block: B:13:0x0025  */
    @Override // org.mozilla.javascript.IdScriptableObject
    protected int findInstanceIdInfo(String str) {
        String str2;
        int i;
        int length = str.length();
        if (length == 6) {
            str2 = "buffer";
            i = 1;
        } else if (length != 10) {
            str2 = null;
            i = 0;
        } else {
            char cCharAt = str.charAt(4);
            if (cCharAt == 'L') {
                str2 = "byteLength";
                i = 3;
            } else if (cCharAt == 'O') {
                str2 = "byteOffset";
                i = 2;
            } else {
                str2 = null;
                i = 0;
            }
        }
        int i2 = (str2 == null || str2 == str || str2.equals(str)) ? i : 0;
        if (i2 == 0) {
            return super.findInstanceIdInfo(str);
        }
        return instanceIdInfo(5, i2);
    }
}
