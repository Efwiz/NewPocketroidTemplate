package org.jetbrains.kotlin.codegen.state;

import com.reandroid.dex.sections.Marker;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.signature.AsmTypeFactory;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.load.kotlin.DescriptorBasedTypeSignatureMappingKt;
import org.jetbrains.kotlin.load.kotlin.TypeMappingConfiguration;
import org.jetbrains.kotlin.load.kotlin.TypeMappingMode;
import org.jetbrains.kotlin.types.CommonSupertypes;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeSystemCommonBackendContext;
import org.jetbrains.kotlin.types.checker.SimpleClassicTypeSystemContext;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: StaticTypeMapperForOldBackend.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0018\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R\u0010\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/codegen/state/StaticTypeMapperForOldBackend;", "Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapperBase;", "()V", "staticTypeMappingConfiguration", "org/jetbrains/kotlin/codegen/state/StaticTypeMapperForOldBackend$staticTypeMappingConfiguration$1", "Lorg/jetbrains/kotlin/codegen/state/StaticTypeMapperForOldBackend$staticTypeMappingConfiguration$1;", "typeSystem", "Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;", "getTypeSystem", "()Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;", "mapClass", "Lorg/jetbrains/org/objectweb/asm/Type;", "classifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "mapTypeCommon", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "mode", "Lorg/jetbrains/kotlin/load/kotlin/TypeMappingMode;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StaticTypeMapperForOldBackend extends KotlinTypeMapperBase {
    public static final StaticTypeMapperForOldBackend INSTANCE = new StaticTypeMapperForOldBackend();
    private static final StaticTypeMapperForOldBackend$staticTypeMappingConfiguration$1 staticTypeMappingConfiguration = new TypeMappingConfiguration<Type>() { // from class: org.jetbrains.kotlin.codegen.state.StaticTypeMapperForOldBackend$staticTypeMappingConfiguration$1
        @Override // org.jetbrains.kotlin.load.kotlin.TypeMappingConfiguration
        public String getPredefinedInternalNameForClass(ClassDescriptor classDescriptor) {
            Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
            return null;
        }

        @Override // org.jetbrains.kotlin.load.kotlin.TypeMappingConfiguration
        public Type getPredefinedTypeForClass(ClassDescriptor classDescriptor) {
            Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
            return null;
        }

        @Override // org.jetbrains.kotlin.load.kotlin.TypeMappingConfiguration
        public KotlinType preprocessType(KotlinType kotlinType) {
            Intrinsics.checkNotNullParameter(kotlinType, "kotlinType");
            return null;
        }

        @Override // org.jetbrains.kotlin.load.kotlin.TypeMappingConfiguration
        public KotlinType commonSupertype(Collection<? extends KotlinType> types) {
            Intrinsics.checkNotNullParameter(types, "types");
            KotlinType kotlinTypeCommonSupertype = CommonSupertypes.commonSupertype(types);
            Intrinsics.checkNotNullExpressionValue(kotlinTypeCommonSupertype, "commonSupertype(...)");
            return kotlinTypeCommonSupertype;
        }

        @Override // org.jetbrains.kotlin.load.kotlin.TypeMappingConfiguration
        public void processErrorType(KotlinType kotlinType, ClassDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(kotlinType, "kotlinType");
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            throw new IllegalStateException(KotlinTypeMapper.INSTANCE.generateErrorMessageForErrorType$backend(kotlinType, descriptor));
        }
    };

    private StaticTypeMapperForOldBackend() {
    }

    @Override // org.jetbrains.kotlin.codegen.state.KotlinTypeMapperBase
    public TypeSystemCommonBackendContext getTypeSystem() {
        return SimpleClassicTypeSystemContext.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.codegen.state.KotlinTypeMapperBase
    public Type mapClass(ClassifierDescriptor classifier) {
        Intrinsics.checkNotNullParameter(classifier, "classifier");
        throw new NotImplementedError("An operation is not implemented: Should not be called");
    }

    @Override // org.jetbrains.kotlin.codegen.state.KotlinTypeMapperBase
    public Type mapTypeCommon(KotlinTypeMarker type, TypeMappingMode mode) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(mode, "mode");
        return (Type) DescriptorBasedTypeSignatureMappingKt.mapType$default((KotlinType) type, AsmTypeFactory.INSTANCE, mode, staticTypeMappingConfiguration, null, null, 32, null);
    }
}
