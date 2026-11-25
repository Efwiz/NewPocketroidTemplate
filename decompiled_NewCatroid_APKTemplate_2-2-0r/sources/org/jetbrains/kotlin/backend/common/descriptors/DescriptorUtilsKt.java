package org.jetbrains.kotlin.backend.common.descriptors;

import java.util.Collection;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorPsiUtilsKt;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: DescriptorUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\"!\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00038F¢\u0006\f\u0012\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\"$\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00038FX\u0087\u0004¢\u0006\f\u0012\u0004\b\t\u0010\u0005\u001a\u0004\b\n\u0010\u0007\"\u0015\u0010\u000b\u001a\u00020\f*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\r\"\u0015\u0010\u000e\u001a\u00020\u000f*\u00020\u00108F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012\"\u0015\u0010\u0013\u001a\u00020\u0010*\u00020\u00108F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015¨\u0006\u0016"}, d2 = {"allParameters", "", "Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "getAllParameters$annotations", "(Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;)V", "getAllParameters", "(Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;)Ljava/util/List;", "explicitParameters", "getExplicitParameters$annotations", "getExplicitParameters", "isSuspend", "", "(Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;)Z", "synthesizedName", "Lorg/jetbrains/kotlin/name/Name;", "", "getSynthesizedName", "(Ljava/lang/String;)Lorg/jetbrains/kotlin/name/Name;", "synthesizedString", "getSynthesizedString", "(Ljava/lang/String;)Ljava/lang/String;", "ir.backend.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DescriptorUtilsKt {
    public static /* synthetic */ void getAllParameters$annotations(CallableDescriptor callableDescriptor) {
    }

    @Deprecated(message = "Please use org.jetbrains.kotlin.descriptors.explicitParameters", replaceWith = @ReplaceWith(expression = "explicitParameters", imports = {"org.jetbrains.kotlin.descriptors.explicitParameters"}))
    public static /* synthetic */ void getExplicitParameters$annotations(CallableDescriptor callableDescriptor) {
    }

    public static final Name getSynthesizedName(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Name nameIdentifier = Name.identifier(getSynthesizedString(str));
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return nameIdentifier;
    }

    public static final String getSynthesizedString(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        return "$" + str;
    }

    public static final boolean isSuspend(CallableDescriptor callableDescriptor) {
        Intrinsics.checkNotNullParameter(callableDescriptor, "<this>");
        return (callableDescriptor instanceof FunctionDescriptor) && ((FunctionDescriptor) callableDescriptor).isSuspend();
    }

    public static final List<ParameterDescriptor> getAllParameters(CallableDescriptor callableDescriptor) {
        Intrinsics.checkNotNullParameter(callableDescriptor, "<this>");
        if (callableDescriptor instanceof ConstructorDescriptor) {
            return CollectionsKt.plus((Collection) CollectionsKt.listOf(((ConstructorDescriptor) callableDescriptor).getConstructedClass().getThisAsReceiverParameter()), (Iterable) DescriptorPsiUtilsKt.getExplicitParameters(callableDescriptor));
        }
        return DescriptorPsiUtilsKt.getExplicitParameters(callableDescriptor);
    }

    public static final List<ParameterDescriptor> getExplicitParameters(CallableDescriptor callableDescriptor) {
        Intrinsics.checkNotNullParameter(callableDescriptor, "<this>");
        return DescriptorPsiUtilsKt.getExplicitParameters(callableDescriptor);
    }
}
