package org.jetbrains.kotlin.fir.renderer;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;

/* JADX INFO: compiled from: ConeTypeRendererForDebugging.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0013\b\u0016\u0012\n\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004¢\u0006\u0002\u0010\u0005B\u0005¢\u0006\u0002\u0010\u0006J?\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0014\u0010\u000b\u001a\u0010\u0012\u0004\u0012\u00020\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\f2\u0017\u0010\u000e\u001a\u0013\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\b0\f¢\u0006\u0002\b\u0010H\u0016¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/renderer/ConeTypeRendererForDebugging;", "Lorg/jetbrains/kotlin/fir/renderer/ConeTypeRenderer;", "builder", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "(Ljava/lang/StringBuilder;)V", "()V", "renderAsPossibleFunctionType", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "functionClassKindExtractor", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "renderType", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "Lkotlin/ExtensionFunctionType;", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeTypeRendererForDebugging extends ConeTypeRenderer {
    public ConeTypeRendererForDebugging() {
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ConeTypeRendererForDebugging(StringBuilder builder) {
        this();
        Intrinsics.checkNotNullParameter(builder, "builder");
        setBuilder(builder);
        setIdRenderer(new ConeIdRendererForDebugging());
        getIdRenderer().setBuilder(builder);
    }

    @Override // org.jetbrains.kotlin.fir.renderer.ConeTypeRenderer
    public void renderAsPossibleFunctionType(ConeKotlinType type, Function1<? super ConeKotlinType, ? extends FunctionTypeKind> functionClassKindExtractor, Function1<? super ConeTypeProjection, Unit> renderType) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(functionClassKindExtractor, "functionClassKindExtractor");
        Intrinsics.checkNotNullParameter(renderType, "renderType");
        getBuilder().append("R|");
        super.renderAsPossibleFunctionType(type, functionClassKindExtractor, renderType);
        getBuilder().append("|");
    }
}
