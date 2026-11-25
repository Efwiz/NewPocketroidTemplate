package org.jetbrains.kotlin.resolve.calls.smartcasts;

import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: ExplicitSmartCasts.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000b\u0010\r\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0005HÆ\u0003J\u001f\u0010\u000f\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\u0011\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0000H\u0096\u0002J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001J\u0014\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\u0016R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/smartcasts/SingleSmartCast;", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/ExplicitSmartCasts;", "call", "Lorg/jetbrains/kotlin/psi/Call;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "(Lorg/jetbrains/kotlin/psi/Call;Lorg/jetbrains/kotlin/types/KotlinType;)V", "getCall", "()Lorg/jetbrains/kotlin/psi/Call;", "defaultType", "getDefaultType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "getType", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "plus", "smartCast", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class SingleSmartCast implements ExplicitSmartCasts {
    private final Call call;
    private final KotlinType type;

    public static /* synthetic */ SingleSmartCast copy$default(SingleSmartCast singleSmartCast, Call call, KotlinType kotlinType, int i, Object obj) {
        if ((i & 1) != 0) {
            call = singleSmartCast.call;
        }
        if ((i & 2) != 0) {
            kotlinType = singleSmartCast.type;
        }
        return singleSmartCast.copy(call, kotlinType);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final Call getCall() {
        return this.call;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final KotlinType getType() {
        return this.type;
    }

    public final SingleSmartCast copy(Call call, KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return new SingleSmartCast(call, type);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SingleSmartCast)) {
            return false;
        }
        SingleSmartCast singleSmartCast = (SingleSmartCast) other;
        return Intrinsics.areEqual(this.call, singleSmartCast.call) && Intrinsics.areEqual(this.type, singleSmartCast.type);
    }

    public int hashCode() {
        Call call = this.call;
        return ((call == null ? 0 : call.hashCode()) * 31) + this.type.hashCode();
    }

    public String toString() {
        return "SingleSmartCast(call=" + this.call + ", type=" + this.type + ')';
    }

    public SingleSmartCast(Call call, KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        this.call = call;
        this.type = type;
    }

    public final Call getCall() {
        return this.call;
    }

    public final KotlinType getType() {
        return this.type;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.smartcasts.ExplicitSmartCasts
    public KotlinType type(Call call) {
        if (Intrinsics.areEqual(call, this.call)) {
            return this.type;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.smartcasts.ExplicitSmartCasts
    public KotlinType getDefaultType() {
        return this.type;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.smartcasts.ExplicitSmartCasts
    public ExplicitSmartCasts plus(SingleSmartCast smartCast) {
        Intrinsics.checkNotNullParameter(smartCast, "smartCast");
        return Intrinsics.areEqual(this, smartCast) ? this : new MultipleSmartCasts(MapsKt.mapOf(TuplesKt.to(this.call, this.type), TuplesKt.to(smartCast.call, smartCast.type)));
    }
}
