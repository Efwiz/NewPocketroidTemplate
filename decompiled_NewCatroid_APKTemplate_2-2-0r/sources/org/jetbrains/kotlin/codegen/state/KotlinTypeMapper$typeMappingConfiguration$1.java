package org.jetbrains.kotlin.codegen.state;

import com.reandroid.arsc.container.SpecTypePair;
import com.reandroid.dex.sections.Marker;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.binding.CodegenBinding;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.load.kotlin.TypeMappingConfiguration;
import org.jetbrains.kotlin.types.CommonSupertypes;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: KotlinTypeMapper.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00001\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u0016\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006H\u0016J\u0012\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0012\u0010\u000b\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0012\u0010\f\u001a\u0004\u0018\u00010\u00022\u0006\u0010\t\u001a\u00020\nH\u0016J\u0012\u0010\r\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u000e\u001a\u00020\u0004H\u0016J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\nH\u0016¨\u0006\u0012"}, d2 = {"org/jetbrains/kotlin/codegen/state/KotlinTypeMapper$typeMappingConfiguration$1", "Lorg/jetbrains/kotlin/load/kotlin/TypeMappingConfiguration;", "Lorg/jetbrains/org/objectweb/asm/Type;", "commonSupertype", "Lorg/jetbrains/kotlin/types/KotlinType;", SpecTypePair.NAME_types, "", "getPredefinedFullInternalNameForClass", "", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getPredefinedInternalNameForClass", "getPredefinedTypeForClass", "preprocessType", "kotlinType", "processErrorType", "", "descriptor", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinTypeMapper$typeMappingConfiguration$1 implements TypeMappingConfiguration<Type> {
    final /* synthetic */ KotlinTypeMapper this$0;

    KotlinTypeMapper$typeMappingConfiguration$1(KotlinTypeMapper kotlinTypeMapper) {
        this.this$0 = kotlinTypeMapper;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.TypeMappingConfiguration
    public KotlinType commonSupertype(Collection<? extends KotlinType> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        KotlinType kotlinTypeCommonSupertype = CommonSupertypes.commonSupertype(types);
        Intrinsics.checkNotNullExpressionValue(kotlinTypeCommonSupertype, "commonSupertype(...)");
        return kotlinTypeCommonSupertype;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.TypeMappingConfiguration
    public Type getPredefinedTypeForClass(ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        return (Type) this.this$0.getBindingContext().get(CodegenBinding.ASM_TYPE, classDescriptor);
    }

    @Override // org.jetbrains.kotlin.load.kotlin.TypeMappingConfiguration
    public String getPredefinedInternalNameForClass(ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        Type predefinedTypeForClass = getPredefinedTypeForClass(classDescriptor);
        if (predefinedTypeForClass != null) {
            return predefinedTypeForClass.getInternalName();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.TypeMappingConfiguration
    public String getPredefinedFullInternalNameForClass(ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        Function1 function1 = this.this$0.namePreprocessor;
        if (function1 != null) {
            return (String) function1.invoke(classDescriptor);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.TypeMappingConfiguration
    public void processErrorType(KotlinType kotlinType, ClassDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(kotlinType, "kotlinType");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (this.this$0.getClassBuilderMode().generateBodies) {
            throw new IllegalStateException(KotlinTypeMapper.INSTANCE.generateErrorMessageForErrorType$backend(kotlinType, descriptor));
        }
    }

    @Override // org.jetbrains.kotlin.load.kotlin.TypeMappingConfiguration
    public KotlinType preprocessType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "kotlinType");
        Function1 function1 = this.this$0.typePreprocessor;
        if (function1 != null) {
            return (KotlinType) function1.invoke(kotlinType);
        }
        return null;
    }
}
