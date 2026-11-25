package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.impl.ValueParameterDescriptorImpl;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: VariableAsmNameManglingUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0003\u001a\u00020\u0004\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"DESTRUCTURED_LAMBDA_ARGUMENT_VARIABLE_PREFIX", "", "getNameForDestructuredParameterOrNull", "valueParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class VariableAsmNameManglingUtils {
    public static final String DESTRUCTURED_LAMBDA_ARGUMENT_VARIABLE_PREFIX = "$dstr$";

    public static final String getNameForDestructuredParameterOrNull(ValueParameterDescriptor valueParameterDescriptor) {
        Intrinsics.checkNotNullParameter(valueParameterDescriptor, "valueParameterDescriptor");
        List<VariableDescriptor> destructuringVariablesOrNull = ValueParameterDescriptorImpl.INSTANCE.getDestructuringVariablesOrNull(valueParameterDescriptor);
        if (destructuringVariablesOrNull == null) {
            return null;
        }
        return DESTRUCTURED_LAMBDA_ARGUMENT_VARIABLE_PREFIX + CollectionsKt.joinToString$default(destructuringVariablesOrNull, "$", null, null, 0, null, new Function1<VariableDescriptor, CharSequence>() { // from class: org.jetbrains.kotlin.codegen.VariableAsmNameManglingUtils.getNameForDestructuredParameterOrNull.1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(VariableDescriptor descriptor) {
                String strAsString;
                Intrinsics.checkNotNullParameter(descriptor, "descriptor");
                Name name2 = descriptor.getShortName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                if (name2.isSpecial()) {
                    strAsString = "$_$";
                } else {
                    strAsString = descriptor.getShortName().asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                }
                return CommonVariableAsmNameManglingUtils.mangleNameIfNeeded(strAsString);
            }
        }, 30, null);
    }
}
