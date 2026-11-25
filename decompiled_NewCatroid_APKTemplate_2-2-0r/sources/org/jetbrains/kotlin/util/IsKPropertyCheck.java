package org.jetbrains.kotlin.util;

import com.google.android.gms.cast.MediaTrack;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.ReflectionTypes;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: modifierChecks.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/util/IsKPropertyCheck;", "Lorg/jetbrains/kotlin/util/Check;", "()V", MediaTrack.ROLE_DESCRIPTION, "", "getDescription", "()Ljava/lang/String;", "check", "", "functionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class IsKPropertyCheck implements Check {
    public static final IsKPropertyCheck INSTANCE = new IsKPropertyCheck();
    private static final String description = "second parameter must be of type KProperty<*> or its supertype";

    private IsKPropertyCheck() {
    }

    @Override // org.jetbrains.kotlin.util.Check
    public String invoke(FunctionDescriptor functionDescriptor) {
        return Check.DefaultImpls.invoke(this, functionDescriptor);
    }

    @Override // org.jetbrains.kotlin.util.Check
    public String getDescription() {
        return description;
    }

    @Override // org.jetbrains.kotlin.util.Check
    public boolean check(FunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        ValueParameterDescriptor valueParameterDescriptor = functionDescriptor.getValueParameters().get(1);
        ReflectionTypes.Companion companion = ReflectionTypes.INSTANCE;
        Intrinsics.checkNotNull(valueParameterDescriptor);
        KotlinType kotlinTypeCreateKPropertyStarType = companion.createKPropertyStarType(DescriptorUtilsKt.getModule(valueParameterDescriptor));
        if (kotlinTypeCreateKPropertyStarType == null) {
            return false;
        }
        KotlinType type = valueParameterDescriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        return TypeUtilsKt.isSubtypeOf(kotlinTypeCreateKPropertyStarType, TypeUtilsKt.makeNotNullable(type));
    }
}
