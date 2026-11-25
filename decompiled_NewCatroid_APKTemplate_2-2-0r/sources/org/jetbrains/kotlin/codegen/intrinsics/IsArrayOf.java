package org.jetbrains.kotlin.codegen.intrinsics;

import com.reandroid.dex.sections.Marker;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.Callable;
import org.jetbrains.kotlin.codegen.CallableMethod;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: IsArrayOf.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J,\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/codegen/intrinsics/IsArrayOf;", "Lorg/jetbrains/kotlin/codegen/intrinsics/IntrinsicMethod;", "()V", "toCallable", "Lorg/jetbrains/kotlin/codegen/Callable;", "fd", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "isSuper", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IsArrayOf extends IntrinsicMethod {
    @Override // org.jetbrains.kotlin.codegen.intrinsics.IntrinsicMethod
    public Callable toCallable(FunctionDescriptor fd, boolean isSuper, ResolvedCall<?> resolvedCall, ExpressionCodegen codegen) {
        Intrinsics.checkNotNullParameter(fd, "fd");
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Map<TypeParameterDescriptor, KotlinType> typeArguments = resolvedCall.getTypeArguments();
        Intrinsics.checkNotNullExpressionValue(typeArguments, "getTypeArguments(...)");
        typeArguments.size();
        KotlinTypeMapper typeMapper = codegen.getState().getTypeMapper();
        CallableMethod callableMethodMapToCallableMethod$default = KotlinTypeMapper.mapToCallableMethod$default(typeMapper, fd, false, null, null, 12, null);
        SimpleType arrayType = DescriptorUtilsKt.getModule(fd).getBuiltIns().getArrayType(Variance.INVARIANT, (KotlinType) CollectionsKt.first(typeArguments.values()));
        Intrinsics.checkNotNullExpressionValue(arrayType, "getArrayType(...)");
        final Type typeMapType$default = KotlinTypeMapper.mapType$default(typeMapper, arrayType, null, null, 6, null);
        return IntrinsicCallableKt.createIntrinsicCallable(callableMethodMapToCallableMethod$default, new Function2<IntrinsicCallable, InstructionAdapter, Unit>() { // from class: org.jetbrains.kotlin.codegen.intrinsics.IsArrayOf.toCallable.2
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(IntrinsicCallable intrinsicCallable, InstructionAdapter instructionAdapter) {
                invoke2(intrinsicCallable, instructionAdapter);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IntrinsicCallable createIntrinsicCallable, InstructionAdapter it) {
                Intrinsics.checkNotNullParameter(createIntrinsicCallable, "$this$createIntrinsicCallable");
                Intrinsics.checkNotNullParameter(it, "it");
                it.instanceOf(typeMapType$default);
            }
        });
    }
}
