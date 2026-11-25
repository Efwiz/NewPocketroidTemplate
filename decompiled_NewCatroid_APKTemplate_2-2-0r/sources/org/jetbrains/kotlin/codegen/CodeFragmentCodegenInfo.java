package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: CodeFragmentCodegen.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001:\u0001\u0014B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/codegen/CodeFragmentCodegenInfo;", "", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "methodDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "parameters", "", "Lorg/jetbrains/kotlin/codegen/CodeFragmentCodegenInfo$IParameter;", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;Ljava/util/List;)V", "getClassDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "classType", "Lorg/jetbrains/org/objectweb/asm/Type;", "getClassType", "()Lorg/jetbrains/org/objectweb/asm/Type;", "getMethodDescriptor", "()Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getParameters", "()Ljava/util/List;", "IParameter", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CodeFragmentCodegenInfo {
    private final ClassDescriptor classDescriptor;
    private final Type classType;
    private final FunctionDescriptor methodDescriptor;
    private final List<IParameter> parameters;

    /* JADX INFO: compiled from: CodeFragmentCodegen.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0004R\u0012\u0010\u0005\u001a\u00020\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0012\u0010\t\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\rÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/codegen/CodeFragmentCodegenInfo$IParameter;", "", "isLValue", "", "()Z", "targetDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getTargetDescriptor", "()Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "targetType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getTargetType", "()Lorg/jetbrains/kotlin/types/KotlinType;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface IParameter {
        DeclarationDescriptor getTargetDescriptor();

        KotlinType getTargetType();

        boolean isLValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public CodeFragmentCodegenInfo(ClassDescriptor classDescriptor, FunctionDescriptor methodDescriptor, List<? extends IParameter> parameters) {
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        Intrinsics.checkNotNullParameter(methodDescriptor, "methodDescriptor");
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        this.classDescriptor = classDescriptor;
        this.methodDescriptor = methodDescriptor;
        this.parameters = parameters;
        Type objectType = Type.getObjectType(classDescriptor.getName().asString());
        Intrinsics.checkNotNullExpressionValue(objectType, "getObjectType(...)");
        this.classType = objectType;
    }

    public final ClassDescriptor getClassDescriptor() {
        return this.classDescriptor;
    }

    public final FunctionDescriptor getMethodDescriptor() {
        return this.methodDescriptor;
    }

    public final List<IParameter> getParameters() {
        return this.parameters;
    }

    public final Type getClassType() {
        return this.classType;
    }
}
