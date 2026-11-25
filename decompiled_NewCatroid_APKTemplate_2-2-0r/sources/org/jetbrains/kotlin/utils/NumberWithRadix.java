package org.jetbrains.kotlin.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: numbers.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0005HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/utils/NumberWithRadix;", "", "number", "", "radix", "", "(Ljava/lang/String;I)V", "getNumber", "()Ljava/lang/String;", "getRadix", "()I", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "toString", "util.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class NumberWithRadix {
    private final String number;
    private final int radix;

    public static /* synthetic */ NumberWithRadix copy$default(NumberWithRadix numberWithRadix, String str, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = numberWithRadix.number;
        }
        if ((i2 & 2) != 0) {
            i = numberWithRadix.radix;
        }
        return numberWithRadix.copy(str, i);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getNumber() {
        return this.number;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final int getRadix() {
        return this.radix;
    }

    public final NumberWithRadix copy(String number, int radix) {
        Intrinsics.checkNotNullParameter(number, "number");
        return new NumberWithRadix(number, radix);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof NumberWithRadix)) {
            return false;
        }
        NumberWithRadix numberWithRadix = (NumberWithRadix) other;
        return Intrinsics.areEqual(this.number, numberWithRadix.number) && this.radix == numberWithRadix.radix;
    }

    public int hashCode() {
        return (this.number.hashCode() * 31) + Integer.hashCode(this.radix);
    }

    public String toString() {
        return "NumberWithRadix(number=" + this.number + ", radix=" + this.radix + ')';
    }

    public NumberWithRadix(String number, int i) {
        Intrinsics.checkNotNullParameter(number, "number");
        this.number = number;
        this.radix = i;
    }

    public final String getNumber() {
        return this.number;
    }

    public final int getRadix() {
        return this.radix;
    }
}
