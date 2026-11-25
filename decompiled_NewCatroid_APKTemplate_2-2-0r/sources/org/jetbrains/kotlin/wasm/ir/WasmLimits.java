package org.jetbrains.kotlin.wasm.ir;

import kotlin.Metadata;
import kotlin.UInt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: Declarations.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0005J\u0016\u0010\u000b\u001a\u00020\u0003HÆ\u0003ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b\f\u0010\tJ\u0016\u0010\r\u001a\u0004\u0018\u00010\u0003HÆ\u0003ø\u0001\u0001ø\u0001\u0000¢\u0006\u0002\b\u000eJ)\u0010\u000f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003HÆ\u0001ø\u0001\u0000¢\u0006\u0004\b\u0010\u0010\u0011J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u0019\u0010\u0004\u001a\u0004\u0018\u00010\u0003ø\u0001\u0000ø\u0001\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0019\u0010\u0002\u001a\u00020\u0003ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\n\u001a\u0004\b\b\u0010\t\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmLimits;", "", "minSize", "Lkotlin/UInt;", "maxSize", "(ILkotlin/UInt;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "getMaxSize-0hXNFcg", "()Lkotlin/UInt;", "getMinSize-pVg5ArA", "()I", "I", "component1", "component1-pVg5ArA", "component2", "component2-0hXNFcg", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "copy-Ut0gzDY", "(ILkotlin/UInt;)Lorg/jetbrains/kotlin/wasm/ir/WasmLimits;", Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class WasmLimits {
    private final UInt maxSize;
    private final int minSize;

    public /* synthetic */ WasmLimits(int i, UInt uInt, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, uInt);
    }

    /* JADX INFO: renamed from: copy-Ut0gzDY$default, reason: not valid java name */
    public static /* synthetic */ WasmLimits m7493copyUt0gzDY$default(WasmLimits wasmLimits, int i, UInt uInt, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = wasmLimits.minSize;
        }
        if ((i2 & 2) != 0) {
            uInt = wasmLimits.maxSize;
        }
        return wasmLimits.m7496copyUt0gzDY(i, uInt);
    }

    /* JADX INFO: renamed from: component1-pVg5ArA, reason: not valid java name and from getter */
    public final int getMinSize() {
        return this.minSize;
    }

    /* JADX INFO: renamed from: component2-0hXNFcg, reason: not valid java name and from getter */
    public final UInt getMaxSize() {
        return this.maxSize;
    }

    /* JADX INFO: renamed from: copy-Ut0gzDY, reason: not valid java name */
    public final WasmLimits m7496copyUt0gzDY(int minSize, UInt maxSize) {
        return new WasmLimits(minSize, maxSize, null);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof WasmLimits)) {
            return false;
        }
        WasmLimits wasmLimits = (WasmLimits) other;
        return this.minSize == wasmLimits.minSize && Intrinsics.areEqual(this.maxSize, wasmLimits.maxSize);
    }

    public int hashCode() {
        int iM3005hashCodeimpl = UInt.m3005hashCodeimpl(this.minSize) * 31;
        UInt uInt = this.maxSize;
        return iM3005hashCodeimpl + (uInt == null ? 0 : UInt.m3005hashCodeimpl(uInt.getData()));
    }

    public String toString() {
        return "WasmLimits(minSize=" + ((Object) UInt.m3039toStringimpl(this.minSize)) + ", maxSize=" + this.maxSize + ')';
    }

    private WasmLimits(int i, UInt uInt) {
        this.minSize = i;
        this.maxSize = uInt;
    }

    /* JADX INFO: renamed from: getMinSize-pVg5ArA, reason: not valid java name */
    public final int m7498getMinSizepVg5ArA() {
        return this.minSize;
    }

    /* JADX INFO: renamed from: getMaxSize-0hXNFcg, reason: not valid java name */
    public final UInt m7497getMaxSize0hXNFcg() {
        return this.maxSize;
    }
}
