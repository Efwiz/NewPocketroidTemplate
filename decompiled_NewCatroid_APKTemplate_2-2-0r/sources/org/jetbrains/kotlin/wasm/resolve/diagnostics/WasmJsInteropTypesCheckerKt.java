package org.jetbrains.kotlin.wasm.resolve.diagnostics;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: WasmJsInteropTypesChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0001H\u0002¨\u0006\u0005"}, d2 = {"isTypeSupportedInJsInterop", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "isInFunctionReturnPosition", "wasm.frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class WasmJsInteropTypesCheckerKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isTypeSupportedInJsInterop(KotlinType kotlinType, boolean z) {
        if (TypeUtilsKt.isUnit(kotlinType) || TypeUtilsKt.isNothing(kotlinType)) {
            return z;
        }
        KotlinType kotlinTypeMakeNotNullable = TypeUtilsKt.makeNotNullable(kotlinType);
        if (KotlinBuiltIns.isPrimitiveType(kotlinTypeMakeNotNullable) || KotlinBuiltIns.isString(kotlinTypeMakeNotNullable) || TypeUtilsKt.isTypeParameter(kotlinTypeMakeNotNullable)) {
            return true;
        }
        ClassifierDescriptor this$0 = kotlinTypeMakeNotNullable.getConstructor().getThis$0();
        if ((this$0 instanceof MemberDescriptor) && DescriptorUtilsKt.isEffectivelyExternal((MemberDescriptor) this$0)) {
            return true;
        }
        if (!FunctionTypesKt.isFunctionType(kotlinType)) {
            return false;
        }
        List<TypeProjection> arguments = kotlinType.getArguments();
        int lastIndex = CollectionsKt.getLastIndex(arguments);
        for (int i = 0; i < lastIndex; i++) {
            KotlinType nullableAnyType = arguments.get(i).getNullableAnyType();
            Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getType(...)");
            if (!isTypeSupportedInJsInterop(nullableAnyType, false)) {
                return false;
            }
        }
        KotlinType nullableAnyType2 = ((TypeProjection) CollectionsKt.last((List) arguments)).getNullableAnyType();
        Intrinsics.checkNotNullExpressionValue(nullableAnyType2, "getType(...)");
        return isTypeSupportedInJsInterop(nullableAnyType2, true);
    }
}
