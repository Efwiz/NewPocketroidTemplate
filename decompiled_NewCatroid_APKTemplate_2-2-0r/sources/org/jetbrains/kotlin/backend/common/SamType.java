package org.jetbrains.kotlin.backend.common;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.sam.SamConversionResolverImplKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;

/* JADX INFO: compiled from: SamType.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016R\u0011\u0010\u0005\u001a\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\r8F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000b¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/SamType;", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "(Lorg/jetbrains/kotlin/types/KotlinType;)V", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getClassDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "kotlinFunctionType", "getKotlinFunctionType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "originalAbstractMethod", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "getOriginalAbstractMethod", "()Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "getType", Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "backend-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SamType {
    private final KotlinType type;

    public final ClassDescriptor getClassDescriptor() {
        ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = this.type.getConstructor().mo7434getDeclarationDescriptor();
        ClassDescriptor classDescriptor = classifierDescriptorMo7434getDeclarationDescriptor instanceof ClassDescriptor ? (ClassDescriptor) classifierDescriptorMo7434getDeclarationDescriptor : null;
        if (classDescriptor != null) {
            return classDescriptor;
        }
        throw new IllegalStateException(("Sam/Fun interface not a class descriptor: " + this.type).toString());
    }

    public SamType(KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        this.type = type;
    }

    public final KotlinType getType() {
        return this.type;
    }

    public final KotlinType getKotlinFunctionType() {
        SimpleType defaultFunctionTypeForSamInterface = getClassDescriptor().getDefaultFunctionTypeForSamInterface();
        Intrinsics.checkNotNull(defaultFunctionTypeForSamInterface);
        return defaultFunctionTypeForSamInterface;
    }

    public final SimpleFunctionDescriptor getOriginalAbstractMethod() {
        CallableMemberDescriptor callableMemberDescriptor = SamConversionResolverImplKt.getAbstractMembers(getClassDescriptor()).get(0);
        Intrinsics.checkNotNull(callableMemberDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor");
        return (SimpleFunctionDescriptor) callableMemberDescriptor;
    }

    public boolean equals(Object other) {
        return (other instanceof SamType) && Intrinsics.areEqual(this.type, ((SamType) other).type);
    }

    public int hashCode() {
        return this.type.hashCode();
    }

    public String toString() {
        return "SamType(" + this.type + ')';
    }
}
