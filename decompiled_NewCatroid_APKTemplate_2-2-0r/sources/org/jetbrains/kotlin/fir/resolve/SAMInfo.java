package org.jetbrains.kotlin.fir.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: SamResolution.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\u00020\u0003B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00028\u0000¢\u0006\u0002\u0010\u0007J\u000e\u0010\r\u001a\u00020\u0005HÀ\u0003¢\u0006\u0002\b\u000eJ\u000e\u0010\u000f\u001a\u00028\u0000HÆ\u0003¢\u0006\u0002\u0010\u000bJ(\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00028\u0000HÆ\u0001¢\u0006\u0002\u0010\u0011J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0003HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0006\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000b¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/SAMInfo;", "C", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", ModuleXmlParser.TYPE, "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;)V", "getSymbol$resolve", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "getType", "()Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "component1", "component1$resolve", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;)Lorg/jetbrains/kotlin/fir/resolve/SAMInfo;", Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class SAMInfo<C extends ConeKotlinType> {
    private final FirNamedFunctionSymbol symbol;
    private final C type;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ SAMInfo copy$default(SAMInfo sAMInfo, FirNamedFunctionSymbol firNamedFunctionSymbol, ConeKotlinType coneKotlinType, int i, Object obj) {
        if ((i & 1) != 0) {
            firNamedFunctionSymbol = sAMInfo.symbol;
        }
        if ((i & 2) != 0) {
            coneKotlinType = sAMInfo.type;
        }
        return sAMInfo.copy(firNamedFunctionSymbol, coneKotlinType);
    }

    /* JADX INFO: renamed from: component1$resolve, reason: from getter */
    public final FirNamedFunctionSymbol getSymbol() {
        return this.symbol;
    }

    public final C component2() {
        return this.type;
    }

    public final SAMInfo<C> copy(FirNamedFunctionSymbol symbol, C type) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(type, "type");
        return new SAMInfo<>(symbol, type);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SAMInfo)) {
            return false;
        }
        SAMInfo sAMInfo = (SAMInfo) other;
        return Intrinsics.areEqual(this.symbol, sAMInfo.symbol) && Intrinsics.areEqual(this.type, sAMInfo.type);
    }

    public int hashCode() {
        return (this.symbol.hashCode() * 31) + this.type.hashCode();
    }

    public String toString() {
        return "SAMInfo(symbol=" + this.symbol + ", type=" + this.type + ')';
    }

    public SAMInfo(FirNamedFunctionSymbol symbol, C type) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(type, "type");
        this.symbol = symbol;
        this.type = type;
    }

    public final FirNamedFunctionSymbol getSymbol$resolve() {
        return this.symbol;
    }

    public final C getType() {
        return this.type;
    }
}
