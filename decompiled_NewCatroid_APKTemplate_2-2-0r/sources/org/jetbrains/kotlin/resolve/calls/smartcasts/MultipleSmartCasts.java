package org.jetbrains.kotlin.resolve.calls.smartcasts;

import java.util.Map;
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
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\b\u0000\u0012\u0014\u0010\u0002\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\u0010\u0006J\u0017\u0010\f\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0012\u0004\u0012\u00020\u00050\u0003HÆ\u0003J!\u0010\r\u001a\u00020\u00002\u0016\b\u0002\u0010\u0002\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0012\u0004\u0012\u00020\u00050\u0003HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\u0011\u0010\u0014\u001a\u00020\u00002\u0006\u0010\u0015\u001a\u00020\u0016H\u0096\u0002J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J\u0014\u0010\u0019\u001a\u0004\u0018\u00010\u00052\b\u0010\u001a\u001a\u0004\u0018\u00010\u0004H\u0016R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u001f\u0010\u0002\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/smartcasts/MultipleSmartCasts;", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/ExplicitSmartCasts;", "map", "", "Lorg/jetbrains/kotlin/psi/Call;", "Lorg/jetbrains/kotlin/types/KotlinType;", "(Ljava/util/Map;)V", "defaultType", "getDefaultType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "getMap", "()Ljava/util/Map;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "plus", "smartCast", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/SingleSmartCast;", "toString", "", ModuleXmlParser.TYPE, "call", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class MultipleSmartCasts implements ExplicitSmartCasts {
    private final Map<Call, KotlinType> map;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ MultipleSmartCasts copy$default(MultipleSmartCasts multipleSmartCasts, Map map, int i, Object obj) {
        if ((i & 1) != 0) {
            map = multipleSmartCasts.map;
        }
        return multipleSmartCasts.copy(map);
    }

    public final Map<Call, KotlinType> component1() {
        return this.map;
    }

    public final MultipleSmartCasts copy(Map<Call, ? extends KotlinType> map) {
        Intrinsics.checkNotNullParameter(map, "map");
        return new MultipleSmartCasts(map);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof MultipleSmartCasts) && Intrinsics.areEqual(this.map, ((MultipleSmartCasts) other).map);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.smartcasts.ExplicitSmartCasts
    public KotlinType getDefaultType() {
        return null;
    }

    public int hashCode() {
        return this.map.hashCode();
    }

    public String toString() {
        return "MultipleSmartCasts(map=" + this.map + ')';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public MultipleSmartCasts(Map<Call, ? extends KotlinType> map) {
        Intrinsics.checkNotNullParameter(map, "map");
        this.map = map;
    }

    public final Map<Call, KotlinType> getMap() {
        return this.map;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.smartcasts.ExplicitSmartCasts
    public KotlinType type(Call call) {
        return this.map.get(call);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.smartcasts.ExplicitSmartCasts
    public MultipleSmartCasts plus(SingleSmartCast smartCast) {
        Intrinsics.checkNotNullParameter(smartCast, "smartCast");
        return new MultipleSmartCasts(MapsKt.plus(this.map, MapsKt.mapOf(TuplesKt.to(smartCast.getCall(), smartCast.getType()))));
    }
}
