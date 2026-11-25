package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.inline.ReificationArgument;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;

/* JADX INFO: compiled from: BaseExpressionCodegen.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001a\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"putReifiedOperationMarkerIfTypeIsReifiedParameter", "", "Lorg/jetbrains/kotlin/codegen/BaseExpressionCodegen;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "operationKind", "Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeInliner$OperationKind;", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class BaseExpressionCodegenKt {
    public static final boolean putReifiedOperationMarkerIfTypeIsReifiedParameter(BaseExpressionCodegen baseExpressionCodegen, KotlinTypeMarker type, ReifiedTypeInliner.OperationKind operationKind) {
        Intrinsics.checkNotNullParameter(baseExpressionCodegen, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(operationKind, "operationKind");
        Pair<TypeParameterMarker, ReificationArgument> pairExtractReificationArgument = CodegenUtilKt.extractReificationArgument(baseExpressionCodegen.getTypeSystem(), type);
        if (pairExtractReificationArgument == null) {
            return false;
        }
        TypeParameterMarker typeParameterMarkerComponent1 = pairExtractReificationArgument.component1();
        ReificationArgument reificationArgumentComponent2 = pairExtractReificationArgument.component2();
        baseExpressionCodegen.consumeReifiedOperationMarker(typeParameterMarkerComponent1);
        ReifiedTypeInliner.INSTANCE.putReifiedOperationMarker(operationKind, reificationArgumentComponent2, baseExpressionCodegen.getVisitor());
        return true;
    }
}
