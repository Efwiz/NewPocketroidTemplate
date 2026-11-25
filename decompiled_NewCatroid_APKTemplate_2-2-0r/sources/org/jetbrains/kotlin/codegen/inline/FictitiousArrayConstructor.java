package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.SimpleFunctionDescriptorImpl;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: FictitiousArrayConstructor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0000\u0018\u0000 \u00052\u00020\u0001:\u0001\u0005B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/FictitiousArrayConstructor;", "Lorg/jetbrains/kotlin/descriptors/impl/SimpleFunctionDescriptorImpl;", "arrayClass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;)V", "Factory", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FictitiousArrayConstructor extends SimpleFunctionDescriptorImpl {

    /* JADX INFO: renamed from: Factory, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    @JvmStatic
    public static final FictitiousArrayConstructor create(ConstructorDescriptor constructorDescriptor) {
        return INSTANCE.create(constructorDescriptor);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FictitiousArrayConstructor(ClassDescriptor arrayClass) {
        super(arrayClass.getContainingDeclaration(), null, Annotations.INSTANCE.getEMPTY(), arrayClass.getShortName(), CallableMemberDescriptor.Kind.SYNTHESIZED, SourceElement.NO_SOURCE);
        Intrinsics.checkNotNullParameter(arrayClass, "arrayClass");
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.codegen.inline.FictitiousArrayConstructor$Factory, reason: from kotlin metadata */
    /* JADX INFO: compiled from: FictitiousArrayConstructor.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/FictitiousArrayConstructor$Factory;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/codegen/inline/FictitiousArrayConstructor;", "arrayConstructor", "Lorg/jetbrains/kotlin/descriptors/ConstructorDescriptor;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final FictitiousArrayConstructor create(ConstructorDescriptor arrayConstructor) {
            Intrinsics.checkNotNullParameter(arrayConstructor, "arrayConstructor");
            ClassDescriptor constructedClass = arrayConstructor.getConstructedClass();
            Intrinsics.checkNotNullExpressionValue(constructedClass, "getConstructedClass(...)");
            FictitiousArrayConstructor fictitiousArrayConstructor = new FictitiousArrayConstructor(constructedClass);
            fictitiousArrayConstructor.initialize((ReceiverParameterDescriptor) null, (ReceiverParameterDescriptor) null, CollectionsKt.emptyList(), (List) arrayConstructor.getTypeParameters(), (List) arrayConstructor.getValueParameters(), (KotlinType) constructedClass.getDefaultType(), Modality.FINAL, DescriptorVisibilities.PUBLIC);
            fictitiousArrayConstructor.setInline(true);
            return fictitiousArrayConstructor;
        }
    }
}
