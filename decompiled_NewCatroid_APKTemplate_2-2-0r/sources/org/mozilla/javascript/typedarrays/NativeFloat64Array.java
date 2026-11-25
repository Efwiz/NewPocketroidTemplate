package org.mozilla.javascript.typedarrays;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Undefined;

/* JADX INFO: loaded from: classes6.dex */
public class NativeFloat64Array extends NativeTypedArrayView<Double> {
    private static final int BYTES_PER_ELEMENT = 8;
    private static final String CLASS_NAME = "Float64Array";
    private static final long serialVersionUID = -1255405650050639335L;

    @Override // org.mozilla.javascript.typedarrays.NativeTypedArrayView
    public int getBytesPerElement() {
        return 8;
    }

    public NativeFloat64Array() {
    }

    public NativeFloat64Array(NativeArrayBuffer nativeArrayBuffer, int i, int i2) {
        super(nativeArrayBuffer, i, i2, i2 * 8);
    }

    public NativeFloat64Array(int i) {
        this(new NativeArrayBuffer(((double) i) * 8.0d), 0, i);
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public String getClassName() {
        return CLASS_NAME;
    }

    public static void init(Context context, Scriptable scriptable, boolean z) {
        new NativeFloat64Array().exportAsJSClass(6, scriptable, z);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.mozilla.javascript.typedarrays.NativeTypedArrayView
    /* JADX INFO: renamed from: construct */
    public NativeTypedArrayView<Double> construct2(NativeArrayBuffer nativeArrayBuffer, int i, int i2) {
        return new NativeFloat64Array(nativeArrayBuffer, i, i2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.mozilla.javascript.typedarrays.NativeTypedArrayView
    /* JADX INFO: renamed from: realThis */
    public NativeTypedArrayView<Double> realThis2(Scriptable scriptable, IdFunctionObject idFunctionObject) {
        if (!(scriptable instanceof NativeFloat64Array)) {
            throw incompatibleCallError(idFunctionObject);
        }
        return (NativeFloat64Array) scriptable;
    }

    @Override // org.mozilla.javascript.typedarrays.NativeTypedArrayView
    protected Object js_get(int i) {
        if (checkIndex(i)) {
            return Undefined.instance;
        }
        return Double.valueOf(Double.longBitsToDouble(ByteIo.readUint64Primitive(this.arrayBuffer.buffer, (i * 8) + this.offset, useLittleEndian())));
    }

    @Override // org.mozilla.javascript.typedarrays.NativeTypedArrayView
    protected Object js_set(int i, Object obj) {
        if (checkIndex(i)) {
            return Undefined.instance;
        }
        ByteIo.writeUint64(this.arrayBuffer.buffer, (i * 8) + this.offset, Double.doubleToLongBits(ScriptRuntime.toNumber(obj)), useLittleEndian());
        return null;
    }

    @Override // java.util.List
    public Double get(int i) {
        if (checkIndex(i)) {
            throw new IndexOutOfBoundsException();
        }
        return (Double) js_get(i);
    }

    @Override // java.util.List
    public Double set(int i, Double d) {
        if (checkIndex(i)) {
            throw new IndexOutOfBoundsException();
        }
        return (Double) js_set(i, d);
    }
}
