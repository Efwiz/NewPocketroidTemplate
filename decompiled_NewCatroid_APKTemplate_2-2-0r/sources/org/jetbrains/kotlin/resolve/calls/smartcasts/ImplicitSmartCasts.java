package org.jetbrains.kotlin.resolve.calls.smartcasts;

import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.scopes.receivers.ImplicitReceiver;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: ImplicitSmartCasts.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\u001b\b\u0002\u0012\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00050\b¢\u0006\u0002\u0010\tJ\u0015\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00050\bHÆ\u0003J\u001f\u0010\r\u001a\u00020\u00002\u0014\b\u0002\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00050\bHÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\u0011\u0010\u0013\u001a\u00020\u00002\u0006\u0010\u0010\u001a\u00020\u0000H\u0086\u0002J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u001d\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00050\b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/smartcasts/ImplicitSmartCasts;", "", "receiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ImplicitReceiver;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "(Lorg/jetbrains/kotlin/resolve/scopes/receivers/ImplicitReceiver;Lorg/jetbrains/kotlin/types/KotlinType;)V", "receiverTypes", "", "(Ljava/util/Map;)V", "getReceiverTypes", "()Ljava/util/Map;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "plus", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ImplicitSmartCasts {
    private final Map<ImplicitReceiver, KotlinType> receiverTypes;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ImplicitSmartCasts copy$default(ImplicitSmartCasts implicitSmartCasts, Map map, int i, Object obj) {
        if ((i & 1) != 0) {
            map = implicitSmartCasts.receiverTypes;
        }
        return implicitSmartCasts.copy(map);
    }

    public final Map<ImplicitReceiver, KotlinType> component1() {
        return this.receiverTypes;
    }

    public final ImplicitSmartCasts copy(Map<ImplicitReceiver, ? extends KotlinType> receiverTypes) {
        Intrinsics.checkNotNullParameter(receiverTypes, "receiverTypes");
        return new ImplicitSmartCasts(receiverTypes);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof ImplicitSmartCasts) && Intrinsics.areEqual(this.receiverTypes, ((ImplicitSmartCasts) other).receiverTypes);
    }

    public int hashCode() {
        return this.receiverTypes.hashCode();
    }

    public String toString() {
        return "ImplicitSmartCasts(receiverTypes=" + this.receiverTypes + ')';
    }

    /* JADX WARN: Multi-variable type inference failed */
    private ImplicitSmartCasts(Map<ImplicitReceiver, ? extends KotlinType> map) {
        this.receiverTypes = map;
    }

    public final Map<ImplicitReceiver, KotlinType> getReceiverTypes() {
        return this.receiverTypes;
    }

    public final ImplicitSmartCasts plus(ImplicitSmartCasts other) {
        Intrinsics.checkNotNullParameter(other, "other");
        return new ImplicitSmartCasts(MapsKt.plus(this.receiverTypes, other.receiverTypes));
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ImplicitSmartCasts(ImplicitReceiver receiver, KotlinType type) {
        this(MapsKt.mapOf(TuplesKt.to(receiver, type)));
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        Intrinsics.checkNotNullParameter(type, "type");
    }
}
