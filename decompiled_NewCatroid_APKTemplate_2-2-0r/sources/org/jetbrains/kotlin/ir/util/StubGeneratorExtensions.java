package org.jetbrains.kotlin.ir.util;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.Implementation;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: StubGeneratorExtensions.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u0000 12\u00020\u0001:\u000212B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J \u0010\u0019\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\"\u0010 \u001a\u0004\u0018\u00010\u001b2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u0012\u0010%\u001a\u0004\u0018\u00010$2\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u0010\u0010&\u001a\u00020\u00102\u0006\u0010'\u001a\u00020(H\u0016J\u0010\u0010)\u001a\u00020\u00102\u0006\u0010\u0017\u001a\u00020*H\u0016J\u0010\u0010+\u001a\u00020\u00102\u0006\u0010\u0017\u001a\u00020,H\u0016J\u0010\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u000200H\u0016R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0016\u0010\u000b\u001a\u0004\u0018\u00010\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\nR\u0016\u0010\r\u001a\u0004\u0018\u00010\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\nR\u0014\u0010\u000f\u001a\u00020\u0010X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0016\u0010\u0013\u001a\u0004\u0018\u00010\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\n¨\u00063"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/StubGeneratorExtensions;", "", "()V", "enhancedNullability", "Lorg/jetbrains/kotlin/ir/util/StubGeneratorExtensions$EnhancedNullability;", "getEnhancedNullability", "()Lorg/jetbrains/kotlin/ir/util/StubGeneratorExtensions$EnhancedNullability;", "enhancedNullabilityAnnotationConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "getEnhancedNullabilityAnnotationConstructor", "()Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "flexibleMutabilityAnnotationConstructor", "getFlexibleMutabilityAnnotationConstructor", "flexibleNullabilityAnnotationConstructor", "getFlexibleNullabilityAnnotationConstructor", "irDeserializationEnabled", "", "getIrDeserializationEnabled", "()Z", "rawTypeAnnotationConstructor", "getRawTypeAnnotationConstructor", "computeExternalDeclarationOrigin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "deserializeClass", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "stubGenerator", "Lorg/jetbrains/kotlin/ir/util/DeclarationStubGenerator;", "parent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "generateFacadeClass", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "deserializedSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "getContainerSource", "isAccessorWithExplicitImplementation", Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX, "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "isPropertyWithPlatformField", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "isStaticFunction", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "registerDeclarations", "", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "Companion", "EnhancedNullability", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class StubGeneratorExtensions {
    public static final StubGeneratorExtensions EMPTY = new StubGeneratorExtensions();
    private final boolean irDeserializationEnabled;

    public IrDeclarationOrigin computeExternalDeclarationOrigin(DeclarationDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return null;
    }

    public boolean deserializeClass(IrClass irClass, DeclarationStubGenerator stubGenerator, IrDeclarationParent parent) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Intrinsics.checkNotNullParameter(stubGenerator, "stubGenerator");
        Intrinsics.checkNotNullParameter(parent, "parent");
        return false;
    }

    public IrClass generateFacadeClass(IrFactory irFactory, DeserializedContainerSource deserializedSource, DeclarationStubGenerator stubGenerator) {
        Intrinsics.checkNotNullParameter(irFactory, "irFactory");
        Intrinsics.checkNotNullParameter(deserializedSource, "deserializedSource");
        Intrinsics.checkNotNullParameter(stubGenerator, "stubGenerator");
        return null;
    }

    public DeserializedContainerSource getContainerSource(DeclarationDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return null;
    }

    public IrConstructor getEnhancedNullabilityAnnotationConstructor() {
        return null;
    }

    public IrConstructor getFlexibleMutabilityAnnotationConstructor() {
        return null;
    }

    public IrConstructor getFlexibleNullabilityAnnotationConstructor() {
        return null;
    }

    public IrConstructor getRawTypeAnnotationConstructor() {
        return null;
    }

    public boolean isAccessorWithExplicitImplementation(IrSimpleFunction accessor) {
        Intrinsics.checkNotNullParameter(accessor, "accessor");
        return false;
    }

    public boolean isPropertyWithPlatformField(PropertyDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return false;
    }

    public boolean isStaticFunction(FunctionDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return false;
    }

    public void registerDeclarations(SymbolTable symbolTable) {
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
    }

    public EnhancedNullability getEnhancedNullability() {
        return EnhancedNullability.INSTANCE;
    }

    /* JADX INFO: compiled from: StubGeneratorExtensions.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u0000 \b2\u00020\u0001:\u0001\bB\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/StubGeneratorExtensions$EnhancedNullability;", "", "()V", "hasEnhancedNullability", "", "kotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "stripEnhancedNullability", "Instance", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static class EnhancedNullability {

        /* JADX INFO: renamed from: Instance, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);

        public boolean hasEnhancedNullability(KotlinType kotlinType) {
            Intrinsics.checkNotNullParameter(kotlinType, "kotlinType");
            return false;
        }

        public KotlinType stripEnhancedNullability(KotlinType kotlinType) {
            Intrinsics.checkNotNullParameter(kotlinType, "kotlinType");
            return kotlinType;
        }

        /* JADX INFO: renamed from: org.jetbrains.kotlin.ir.util.StubGeneratorExtensions$EnhancedNullability$Instance, reason: from kotlin metadata */
        /* JADX INFO: compiled from: StubGeneratorExtensions.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/StubGeneratorExtensions$EnhancedNullability$Instance;", "Lorg/jetbrains/kotlin/ir/util/StubGeneratorExtensions$EnhancedNullability;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion extends EnhancedNullability {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }
    }

    public boolean getIrDeserializationEnabled() {
        return this.irDeserializationEnabled;
    }
}
