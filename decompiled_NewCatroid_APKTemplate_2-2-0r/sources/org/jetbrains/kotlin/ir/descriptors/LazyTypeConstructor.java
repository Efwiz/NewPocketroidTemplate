package org.jetbrains.kotlin.ir.descriptors;

import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.SupertypeLoopChecker;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.AbstractClassTypeConstructor;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: IrBasedDescriptors.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0005\u0012\u0012\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u00060\u0005\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u000e\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\t0\u0006H\u0014J\b\u0010\u001f\u001a\u00020\u0003H\u0016J\u000e\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016J\b\u0010!\u001a\u00020\"H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001d\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R!\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0012\u0010\u0013R\u001d\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0010R!\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\t0\u00068FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0019\u0010\u0015\u001a\u0004\b\u0018\u0010\u0013R\u0014\u0010\u001a\u001a\u00020\u001b8TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001d¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/ir/descriptors/LazyTypeConstructor;", "Lorg/jetbrains/kotlin/types/AbstractClassTypeConstructor;", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "parametersBuilder", "Lkotlin/Function0;", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "superTypesBuilder", "Lorg/jetbrains/kotlin/types/KotlinType;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Lorg/jetbrains/kotlin/storage/StorageManager;)V", "getClassDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getParametersBuilder", "()Lkotlin/jvm/functions/Function0;", "parameters_", "getParameters_", "()Ljava/util/List;", "parameters_$delegate", "Lkotlin/Lazy;", "getSuperTypesBuilder", "superTypes_", "getSuperTypes_", "superTypes_$delegate", "supertypeLoopChecker", "Lorg/jetbrains/kotlin/descriptors/SupertypeLoopChecker;", "getSupertypeLoopChecker", "()Lorg/jetbrains/kotlin/descriptors/SupertypeLoopChecker;", "computeSupertypes", "getDeclarationDescriptor", "getParameters", "isDenotable", "", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LazyTypeConstructor extends AbstractClassTypeConstructor {
    private final ClassDescriptor classDescriptor;
    private final Function0<List<TypeParameterDescriptor>> parametersBuilder;

    /* JADX INFO: renamed from: parameters_$delegate, reason: from kotlin metadata */
    private final Lazy parameters_;
    private final Function0<List<KotlinType>> superTypesBuilder;

    /* JADX INFO: renamed from: superTypes_$delegate, reason: from kotlin metadata */
    private final Lazy superTypes_;

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    public boolean isDenotable() {
        return true;
    }

    public final ClassDescriptor getClassDescriptor() {
        return this.classDescriptor;
    }

    public final Function0<List<TypeParameterDescriptor>> getParametersBuilder() {
        return this.parametersBuilder;
    }

    public final Function0<List<KotlinType>> getSuperTypesBuilder() {
        return this.superTypesBuilder;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public LazyTypeConstructor(ClassDescriptor classDescriptor, Function0<? extends List<? extends TypeParameterDescriptor>> parametersBuilder, Function0<? extends List<? extends KotlinType>> superTypesBuilder, StorageManager storageManager) {
        super(storageManager);
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        Intrinsics.checkNotNullParameter(parametersBuilder, "parametersBuilder");
        Intrinsics.checkNotNullParameter(superTypesBuilder, "superTypesBuilder");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        this.classDescriptor = classDescriptor;
        this.parametersBuilder = parametersBuilder;
        this.superTypesBuilder = superTypesBuilder;
        this.parameters_ = LazyKt.lazy(new Function0<List<? extends TypeParameterDescriptor>>() { // from class: org.jetbrains.kotlin.ir.descriptors.LazyTypeConstructor$parameters_$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends TypeParameterDescriptor> invoke() {
                return this.this$0.getParametersBuilder().invoke();
            }
        });
        this.superTypes_ = LazyKt.lazy(new Function0<List<? extends KotlinType>>() { // from class: org.jetbrains.kotlin.ir.descriptors.LazyTypeConstructor$superTypes_$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends KotlinType> invoke() {
                return this.this$0.getSuperTypesBuilder().invoke();
            }
        });
    }

    public final List<TypeParameterDescriptor> getParameters_() {
        return (List) this.parameters_.getValue();
    }

    public final List<KotlinType> getSuperTypes_() {
        return (List) this.superTypes_.getValue();
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    public List<TypeParameterDescriptor> getParameters() {
        return getParameters_();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.types.AbstractTypeConstructor
    public List<KotlinType> computeSupertypes() {
        return getSuperTypes_();
    }

    @Override // org.jetbrains.kotlin.types.AbstractClassTypeConstructor, org.jetbrains.kotlin.types.ClassifierBasedTypeConstructor, org.jetbrains.kotlin.types.TypeConstructor
    /* JADX INFO: renamed from: getDeclarationDescriptor */
    public ClassDescriptor getThis$0() {
        return this.classDescriptor;
    }

    @Override // org.jetbrains.kotlin.types.AbstractTypeConstructor
    protected SupertypeLoopChecker getSupertypeLoopChecker() {
        return SupertypeLoopChecker.EMPTY.INSTANCE;
    }
}
