package org.jetbrains.kotlin.backend.common;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: SamTypeFactory.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0006H\u0016¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/SamTypeFactory;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/backend/common/SamType;", "originalType", "Lorg/jetbrains/kotlin/types/KotlinType;", "isSamType", "", ModuleXmlParser.TYPE, "backend-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class SamTypeFactory {
    public boolean isSamType(KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = type.getConstructor().mo7434getDeclarationDescriptor();
        return (classifierDescriptorMo7434getDeclarationDescriptor instanceof ClassDescriptor) && ((ClassDescriptor) classifierDescriptorMo7434getDeclarationDescriptor).isFun();
    }

    public final SamType create(KotlinType originalType) {
        Intrinsics.checkNotNullParameter(originalType, "originalType");
        if (isSamType(originalType)) {
            return new SamType(originalType);
        }
        return null;
    }
}
