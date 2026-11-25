package org.jetbrains.kotlin.resolve.calls.inference;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.descriptorUtil.AnnotationsForResolveUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;

/* JADX INFO: compiled from: TypeVariable.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0006\u0010\u0018\u001a\u00020\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\u0004\u001a\u00020\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\u00108F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\rR\u0011\u0010\u0014\u001a\u00020\u00158F¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/TypeVariable;", "", "call", "Lorg/jetbrains/kotlin/resolve/calls/inference/CallHandle;", "freshTypeParameter", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "originalTypeParameter", "isExternal", "", "(Lorg/jetbrains/kotlin/resolve/calls/inference/CallHandle;Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;Z)V", "getCall", "()Lorg/jetbrains/kotlin/resolve/calls/inference/CallHandle;", "getFreshTypeParameter$frontend", "()Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "()Z", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "getOriginalTypeParameter", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "getType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "hasOnlyInputTypesAnnotation", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeVariable {
    private final CallHandle call;
    private final TypeParameterDescriptor freshTypeParameter;
    private final boolean isExternal;
    private final TypeParameterDescriptor originalTypeParameter;

    public TypeVariable(CallHandle call, TypeParameterDescriptor freshTypeParameter, TypeParameterDescriptor originalTypeParameter, boolean z) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(freshTypeParameter, "freshTypeParameter");
        Intrinsics.checkNotNullParameter(originalTypeParameter, "originalTypeParameter");
        this.call = call;
        this.freshTypeParameter = freshTypeParameter;
        this.originalTypeParameter = originalTypeParameter;
        this.isExternal = z;
    }

    public final CallHandle getCall() {
        return this.call;
    }

    /* JADX INFO: renamed from: getFreshTypeParameter$frontend, reason: from getter */
    public final TypeParameterDescriptor getFreshTypeParameter() {
        return this.freshTypeParameter;
    }

    public final TypeParameterDescriptor getOriginalTypeParameter() {
        return this.originalTypeParameter;
    }

    /* JADX INFO: renamed from: isExternal, reason: from getter */
    public final boolean getIsExternal() {
        return this.isExternal;
    }

    public final Name getName() {
        Name name2 = this.originalTypeParameter.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        return name2;
    }

    public final KotlinType getType() {
        SimpleType defaultType = this.freshTypeParameter.getDefaultType();
        Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
        return defaultType;
    }

    public final boolean hasOnlyInputTypesAnnotation() {
        return AnnotationsForResolveUtilsKt.hasOnlyInputTypesAnnotation(this.originalTypeParameter);
    }
}
