package org.jetbrains.kotlin.load.kotlin;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: methodSignatureMapping.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\bÀ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0016\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007H\u0016J\u0012\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0012\u0010\f\u001a\u0004\u0018\u00010\u00022\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\u000bH\u0016¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/TypeMappingConfigurationImpl;", "Lorg/jetbrains/kotlin/load/kotlin/TypeMappingConfiguration;", "Lorg/jetbrains/kotlin/load/kotlin/JvmType;", "()V", "commonSupertype", "Lorg/jetbrains/kotlin/types/KotlinType;", SpecTypePair.NAME_types, "", "getPredefinedInternalNameForClass", "", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getPredefinedTypeForClass", "processErrorType", "", "kotlinType", "descriptor", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeMappingConfigurationImpl implements TypeMappingConfiguration<JvmType> {
    public static final TypeMappingConfigurationImpl INSTANCE = new TypeMappingConfigurationImpl();

    @Override // org.jetbrains.kotlin.load.kotlin.TypeMappingConfiguration
    public String getPredefinedInternalNameForClass(ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        return null;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.TypeMappingConfiguration
    public JvmType getPredefinedTypeForClass(ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        return null;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.TypeMappingConfiguration
    public void processErrorType(KotlinType kotlinType, ClassDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(kotlinType, "kotlinType");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
    }

    private TypeMappingConfigurationImpl() {
    }

    @Override // org.jetbrains.kotlin.load.kotlin.TypeMappingConfiguration
    public KotlinType commonSupertype(Collection<? extends KotlinType> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        throw new AssertionError("There should be no intersection type in existing descriptors, but found: " + CollectionsKt.joinToString$default(types, null, null, null, 0, null, null, 63, null));
    }
}
