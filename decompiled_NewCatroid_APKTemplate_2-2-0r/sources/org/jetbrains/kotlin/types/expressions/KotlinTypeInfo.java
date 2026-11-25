package org.jetbrains.kotlin.types.expressions;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: KotlinTypeInfo.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000f\u0018\u00002\u00020\u0001B-\b\u0007\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0005¢\u0006\u0002\u0010\tJ\u0006\u0010\u0011\u001a\u00020\u0000J\u000e\u0010\u0012\u001a\u00020\u00002\u0006\u0010\u0004\u001a\u00020\u0005J\u000e\u0010\u0013\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020\u0005J\u000e\u0010\u0014\u001a\u00020\u00002\u0006\u0010\u0006\u001a\u00020\u0007J\u0010\u0010\u0015\u001a\u00020\u00002\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/types/expressions/KotlinTypeInfo;", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "dataFlowInfo", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "jumpOutPossible", "", "jumpFlowInfo", "(Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;ZLorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;)V", "getDataFlowInfo", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "getJumpFlowInfo", "getJumpOutPossible", "()Z", "getType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "clearType", "replaceDataFlowInfo", "replaceJumpFlowInfo", "replaceJumpOutPossible", "replaceType", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinTypeInfo {
    private final DataFlowInfo dataFlowInfo;
    private final DataFlowInfo jumpFlowInfo;
    private final boolean jumpOutPossible;
    private final KotlinType type;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public KotlinTypeInfo(KotlinType kotlinType, DataFlowInfo dataFlowInfo) {
        this(kotlinType, dataFlowInfo, false, null, 12, null);
        Intrinsics.checkNotNullParameter(dataFlowInfo, "dataFlowInfo");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public KotlinTypeInfo(KotlinType kotlinType, DataFlowInfo dataFlowInfo, boolean z) {
        this(kotlinType, dataFlowInfo, z, null, 8, null);
        Intrinsics.checkNotNullParameter(dataFlowInfo, "dataFlowInfo");
    }

    public KotlinTypeInfo(KotlinType kotlinType, DataFlowInfo dataFlowInfo, boolean z, DataFlowInfo jumpFlowInfo) {
        Intrinsics.checkNotNullParameter(dataFlowInfo, "dataFlowInfo");
        Intrinsics.checkNotNullParameter(jumpFlowInfo, "jumpFlowInfo");
        this.type = kotlinType;
        this.dataFlowInfo = dataFlowInfo;
        this.jumpOutPossible = z;
        this.jumpFlowInfo = jumpFlowInfo;
    }

    public /* synthetic */ KotlinTypeInfo(KotlinType kotlinType, DataFlowInfo dataFlowInfo, boolean z, DataFlowInfo dataFlowInfo2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(kotlinType, dataFlowInfo, (i & 4) != 0 ? false : z, (i & 8) != 0 ? dataFlowInfo : dataFlowInfo2);
    }

    public final KotlinType getType() {
        return this.type;
    }

    public final DataFlowInfo getDataFlowInfo() {
        return this.dataFlowInfo;
    }

    public final boolean getJumpOutPossible() {
        return this.jumpOutPossible;
    }

    public final DataFlowInfo getJumpFlowInfo() {
        return this.jumpFlowInfo;
    }

    public final KotlinTypeInfo clearType() {
        return replaceType(null);
    }

    public final KotlinTypeInfo replaceType(KotlinType type) {
        return new KotlinTypeInfo(type, this.dataFlowInfo, this.jumpOutPossible, this.jumpFlowInfo);
    }

    public final KotlinTypeInfo replaceJumpOutPossible(boolean jumpOutPossible) {
        return jumpOutPossible == this.jumpOutPossible ? this : new KotlinTypeInfo(this.type, this.dataFlowInfo, jumpOutPossible, this.jumpFlowInfo);
    }

    public final KotlinTypeInfo replaceJumpFlowInfo(DataFlowInfo jumpFlowInfo) {
        Intrinsics.checkNotNullParameter(jumpFlowInfo, "jumpFlowInfo");
        return Intrinsics.areEqual(jumpFlowInfo, this.jumpFlowInfo) ? this : new KotlinTypeInfo(this.type, this.dataFlowInfo, this.jumpOutPossible, jumpFlowInfo);
    }

    public final KotlinTypeInfo replaceDataFlowInfo(DataFlowInfo dataFlowInfo) {
        Intrinsics.checkNotNullParameter(dataFlowInfo, "dataFlowInfo");
        DataFlowInfo dataFlowInfo2 = this.dataFlowInfo;
        if (Intrinsics.areEqual(dataFlowInfo2, dataFlowInfo)) {
            return this;
        }
        return Intrinsics.areEqual(dataFlowInfo2, this.jumpFlowInfo) ? new KotlinTypeInfo(this.type, dataFlowInfo, this.jumpOutPossible, dataFlowInfo) : new KotlinTypeInfo(this.type, dataFlowInfo, this.jumpOutPossible, this.jumpFlowInfo);
    }
}
