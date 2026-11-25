package org.jetbrains.kotlin.wasm.ir;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: Symbol.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0011\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00018\u0000¢\u0006\u0002\u0010\u0005J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0002J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0002H\u0096\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0002X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0004\u001a\u00028\u00008VX\u0096\u0004¢\u0006\f\u0012\u0004\b\u0007\u0010\b\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmSymbol;", ExifInterface.GPS_DIRECTION_TRUE, "", "Lorg/jetbrains/kotlin/wasm/ir/WasmSymbolReadOnly;", "owner", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, "_owner", "getOwner$annotations", "()V", "getOwner", "()Ljava/lang/Object;", "bind", "", "value", Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmSymbol<T> implements WasmSymbolReadOnly<T> {
    private Object _owner;

    /* JADX WARN: Illegal instructions before constructor call */
    public WasmSymbol() {
        DefaultConstructorMarker defaultConstructorMarker = null;
        this(defaultConstructorMarker, 1, defaultConstructorMarker);
    }

    public static /* synthetic */ void getOwner$annotations() {
    }

    public WasmSymbol(T t) {
        this._owner = t;
    }

    public /* synthetic */ WasmSymbol(Object obj, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : obj);
    }

    @Override // org.jetbrains.kotlin.wasm.ir.WasmSymbolReadOnly
    public T getOwner() {
        T t = (T) this._owner;
        if (t == null) {
            t = null;
        }
        if (t != null) {
            return t;
        }
        throw new IllegalStateException(("Unbound wasm symbol " + this).toString());
    }

    public final void bind(Object value) {
        Intrinsics.checkNotNullParameter(value, "value");
        this._owner = value;
    }

    public boolean equals(Object other) {
        return (other instanceof WasmSymbol) && Intrinsics.areEqual(this._owner, ((WasmSymbol) other)._owner);
    }

    public int hashCode() {
        Object obj = this._owner;
        if (obj != null) {
            return obj.hashCode();
        }
        return 0;
    }

    public String toString() {
        String string;
        Object obj = this._owner;
        return (obj == null || (string = obj.toString()) == null) ? "UNBOUND-WASM-SYMBOL" : string;
    }
}
