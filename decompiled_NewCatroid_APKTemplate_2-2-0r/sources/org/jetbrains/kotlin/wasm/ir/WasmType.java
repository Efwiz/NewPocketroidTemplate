package org.jetbrains.kotlin.wasm.ir;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.catrobat.catroid.web.ServerAuthenticationConstants;

/* JADX INFO: compiled from: Types.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0005\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u0017\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u000b\u001a\u00020\u0003H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u0082\u0001\u0012\f\r\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001d¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmType;", "", "name", "", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "", "(Ljava/lang/String;B)V", "getCode", "()B", "getName", "()Ljava/lang/String;", "toString", "Lorg/jetbrains/kotlin/wasm/ir/WasmAnyRef;", "Lorg/jetbrains/kotlin/wasm/ir/WasmEqRef;", "Lorg/jetbrains/kotlin/wasm/ir/WasmExternRef;", "Lorg/jetbrains/kotlin/wasm/ir/WasmF32;", "Lorg/jetbrains/kotlin/wasm/ir/WasmF64;", "Lorg/jetbrains/kotlin/wasm/ir/WasmFuncRef;", "Lorg/jetbrains/kotlin/wasm/ir/WasmI16;", "Lorg/jetbrains/kotlin/wasm/ir/WasmI31Ref;", "Lorg/jetbrains/kotlin/wasm/ir/WasmI32;", "Lorg/jetbrains/kotlin/wasm/ir/WasmI64;", "Lorg/jetbrains/kotlin/wasm/ir/WasmI8;", "Lorg/jetbrains/kotlin/wasm/ir/WasmRefNullExternrefType;", "Lorg/jetbrains/kotlin/wasm/ir/WasmRefNullType;", "Lorg/jetbrains/kotlin/wasm/ir/WasmRefNullrefType;", "Lorg/jetbrains/kotlin/wasm/ir/WasmRefType;", "Lorg/jetbrains/kotlin/wasm/ir/WasmStructRef;", "Lorg/jetbrains/kotlin/wasm/ir/WasmUnreachableType;", "Lorg/jetbrains/kotlin/wasm/ir/WasmV128;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class WasmType {
    private final byte code;
    private final String name;

    public /* synthetic */ WasmType(String str, byte b, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, b);
    }

    private WasmType(String str, byte b) {
        this.name = str;
        this.code = b;
    }

    public final String getName() {
        return this.name;
    }

    public final byte getCode() {
        return this.code;
    }

    public String toString() {
        return this.name;
    }
}
