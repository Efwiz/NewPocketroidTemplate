package org.mozilla.javascript.typedarrays;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Undefined;

/* JADX INFO: loaded from: classes6.dex */
public class NativeUint16Array extends NativeTypedArrayView<Integer> {
    private static final int BYTES_PER_ELEMENT = 2;
    private static final String CLASS_NAME = "Uint16Array";
    private static final long serialVersionUID = 7700018949434240321L;

    @Override // org.mozilla.javascript.typedarrays.NativeTypedArrayView
    public int getBytesPerElement() {
        return 2;
    }

    public NativeUint16Array() {
    }

    public NativeUint16Array(NativeArrayBuffer nativeArrayBuffer, int i, int i2) {
        super(nativeArrayBuffer, i, i2, i2 * 2);
    }

    public NativeUint16Array(int i) {
        this(new NativeArrayBuffer(((double) i) * 2.0d), 0, i);
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public String getClassName() {
        return CLASS_NAME;
    }

    public static void init(Context context, Scriptable scriptable, boolean z) {
        new NativeUint16Array().exportAsJSClass(6, scriptable, z);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.mozilla.javascript.typedarrays.NativeTypedArrayView
    /* JADX INFO: renamed from: construct, reason: avoid collision after fix types in other method */
    public NativeTypedArrayView<Integer> construct2(NativeArrayBuffer nativeArrayBuffer, int i, int i2) {
        return new NativeUint16Array(nativeArrayBuffer, i, i2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.mozilla.javascript.typedarrays.NativeTypedArrayView
    /* JADX INFO: renamed from: realThis, reason: avoid collision after fix types in other method */
    public NativeTypedArrayView<Integer> realThis2(Scriptable scriptable, IdFunctionObject idFunctionObject) {
        if (!(scriptable instanceof NativeUint16Array)) {
            throw incompatibleCallError(idFunctionObject);
        }
        return (NativeUint16Array) scriptable;
    }

    @Override // org.mozilla.javascript.typedarrays.NativeTypedArrayView
    protected Object js_get(int i) {
        if (checkIndex(i)) {
            return Undefined.instance;
        }
        return ByteIo.readUint16(this.arrayBuffer.buffer, (i * 2) + this.offset, useLittleEndian());
    }

    @Override // org.mozilla.javascript.typedarrays.NativeTypedArrayView
    protected Object js_set(int i, Object obj) {
        if (checkIndex(i)) {
            return Undefined.instance;
        }
        ByteIo.writeUint16(this.arrayBuffer.buffer, (i * 2) + this.offset, Conversions.toUint16(obj), useLittleEndian());
        return null;
    }

    @Override // java.util.List
    public Integer get(int i) {
        if (checkIndex(i)) {
            throw new IndexOutOfBoundsException();
        }
        return (Integer) js_get(i);
    }

    @Override // java.util.List
    public Integer set(int i, Integer num) {
        if (checkIndex(i)) {
            throw new IndexOutOfBoundsException();
        }
        return (Integer) js_set(i, num);
    }
}
