package org.jetbrains.kotlin.types.expressions.typeInfoFactory;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.expressions.KotlinTypeInfo;

/* JADX INFO: compiled from: TypeInfoFactory.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u001a\u0010\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u001a\u001c\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005\u001a\u0018\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0006\u001a\u00020\u0007\u001a(\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0007\u001a\u0012\u0010\u000b\u001a\u00020\u00012\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005\u001a\u000e\u0010\u000b\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0007¨\u0006\f"}, d2 = {"createTypeInfo", "Lorg/jetbrains/kotlin/types/expressions/KotlinTypeInfo;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "context", "Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionContext;", "dataFlowInfo", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "jumpPossible", "", "jumpFlowInfo", "noTypeInfo", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TypeInfoFactoryKt {
    public static final KotlinTypeInfo createTypeInfo(KotlinType kotlinType, DataFlowInfo dataFlowInfo) {
        Intrinsics.checkNotNullParameter(dataFlowInfo, "dataFlowInfo");
        return new KotlinTypeInfo(kotlinType, dataFlowInfo, false, null, 12, null);
    }

    public static final KotlinTypeInfo createTypeInfo(KotlinType kotlinType, DataFlowInfo dataFlowInfo, boolean z, DataFlowInfo jumpFlowInfo) {
        Intrinsics.checkNotNullParameter(dataFlowInfo, "dataFlowInfo");
        Intrinsics.checkNotNullParameter(jumpFlowInfo, "jumpFlowInfo");
        return new KotlinTypeInfo(kotlinType, dataFlowInfo, z, jumpFlowInfo);
    }

    public static final KotlinTypeInfo createTypeInfo(KotlinType kotlinType) {
        return createTypeInfo(kotlinType, DataFlowInfo.INSTANCE.getEMPTY());
    }

    public static final KotlinTypeInfo createTypeInfo(KotlinType kotlinType, ResolutionContext<?> context) {
        Intrinsics.checkNotNullParameter(context, "context");
        DataFlowInfo dataFlowInfo = context.dataFlowInfo;
        Intrinsics.checkNotNullExpressionValue(dataFlowInfo, "dataFlowInfo");
        return createTypeInfo(kotlinType, dataFlowInfo);
    }

    public static final KotlinTypeInfo noTypeInfo(DataFlowInfo dataFlowInfo) {
        Intrinsics.checkNotNullParameter(dataFlowInfo, "dataFlowInfo");
        return createTypeInfo((KotlinType) null, dataFlowInfo);
    }

    public static final KotlinTypeInfo noTypeInfo(ResolutionContext<?> context) {
        Intrinsics.checkNotNullParameter(context, "context");
        DataFlowInfo dataFlowInfo = context.dataFlowInfo;
        Intrinsics.checkNotNullExpressionValue(dataFlowInfo, "dataFlowInfo");
        return noTypeInfo(dataFlowInfo);
    }
}
