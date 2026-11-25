package org.jetbrains.kotlin.codegen.state;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.load.kotlin.TypeMappingMode;
import org.jetbrains.kotlin.types.TypeSystemCommonBackendContext;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: KotlinTypeMapperBase.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH&J\u000e\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\rJ\u0018\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H&R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapperBase;", "", "()V", "typeSystem", "Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;", "getTypeSystem", "()Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;", "mapClass", "Lorg/jetbrains/org/objectweb/asm/Type;", "classifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "mapDefaultImpls", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "mapTypeCommon", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "mode", "Lorg/jetbrains/kotlin/load/kotlin/TypeMappingMode;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KotlinTypeMapperBase {
    public abstract TypeSystemCommonBackendContext getTypeSystem();

    public abstract Type mapClass(ClassifierDescriptor classifier);

    public abstract Type mapTypeCommon(KotlinTypeMarker type, TypeMappingMode mode);

    public final Type mapDefaultImpls(ClassDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Type objectType = Type.getObjectType(mapClass(descriptor).getInternalName() + JvmAbi.DEFAULT_IMPLS_SUFFIX);
        Intrinsics.checkNotNullExpressionValue(objectType, "getObjectType(...)");
        return objectType;
    }
}
