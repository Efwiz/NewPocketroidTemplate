package org.jetbrains.kotlin.fir.types;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.ReadOnlyProperty;
import kotlin.reflect.KProperty;

/* JADX INFO: compiled from: CompilerConeAttributes.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\"!\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0007\u001a\u00020\b*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\t\u0010\n\"\u0015\u0010\u0007\u001a\u00020\b*\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b\t\u0010\f\"!\u0010\r\u001a\u0004\u0018\u00010\u000e*\u00020\u00028FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0006\u001a\u0004\b\u000f\u0010\u0010\"!\u0010\u0012\u001a\u0004\u0018\u00010\u0013*\u00020\u00028FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0016\u0010\u0006\u001a\u0004\b\u0014\u0010\u0015\"!\u0010\u0017\u001a\u0004\u0018\u00010\u0018*\u00020\u00028FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001b\u0010\u0006\u001a\u0004\b\u0019\u0010\u001a\"\u0015\u0010\u001c\u001a\u00020\u001d*\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001f\"\u0015\u0010 \u001a\u00020\u001d*\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b!\u0010\u001f\"\u0015\u0010\"\u001a\u00020\u001d*\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b\"\u0010\u001f\"!\u0010#\u001a\u0004\u0018\u00010$*\u00020\u00028FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b'\u0010\u0006\u001a\u0004\b%\u0010&¨\u0006("}, d2 = {"contextFunctionTypeParams", "Lorg/jetbrains/kotlin/fir/types/CompilerConeAttributes$ContextFunctionTypeParams;", "Lorg/jetbrains/kotlin/fir/types/ConeAttributes;", "getContextFunctionTypeParams", "(Lorg/jetbrains/kotlin/fir/types/ConeAttributes;)Lorg/jetbrains/kotlin/fir/types/CompilerConeAttributes$ContextFunctionTypeParams;", "contextFunctionTypeParams$delegate", "Lkotlin/properties/ReadOnlyProperty;", "contextReceiversNumberForFunctionType", "", "getContextReceiversNumberForFunctionType", "(Lorg/jetbrains/kotlin/fir/types/ConeAttributes;)I", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "(Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;)I", "enhancedNullability", "Lorg/jetbrains/kotlin/fir/types/CompilerConeAttributes$EnhancedNullability;", "getEnhancedNullability", "(Lorg/jetbrains/kotlin/fir/types/ConeAttributes;)Lorg/jetbrains/kotlin/fir/types/CompilerConeAttributes$EnhancedNullability;", "enhancedNullability$delegate", "exact", "Lorg/jetbrains/kotlin/fir/types/CompilerConeAttributes$Exact;", "getExact", "(Lorg/jetbrains/kotlin/fir/types/ConeAttributes;)Lorg/jetbrains/kotlin/fir/types/CompilerConeAttributes$Exact;", "exact$delegate", "extensionFunctionType", "Lorg/jetbrains/kotlin/fir/types/CompilerConeAttributes$ExtensionFunctionType;", "getExtensionFunctionType", "(Lorg/jetbrains/kotlin/fir/types/ConeAttributes;)Lorg/jetbrains/kotlin/fir/types/CompilerConeAttributes$ExtensionFunctionType;", "extensionFunctionType$delegate", "hasContextReceivers", "", "getHasContextReceivers", "(Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;)Z", "hasEnhancedNullability", "getHasEnhancedNullability", "isExtensionFunctionType", "noInfer", "Lorg/jetbrains/kotlin/fir/types/CompilerConeAttributes$NoInfer;", "getNoInfer", "(Lorg/jetbrains/kotlin/fir/types/ConeAttributes;)Lorg/jetbrains/kotlin/fir/types/CompilerConeAttributes$NoInfer;", "noInfer$delegate", "cones"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CompilerConeAttributesKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(CompilerConeAttributesKt.class, "cones"), "exact", "getExact(Lorg/jetbrains/kotlin/fir/types/ConeAttributes;)Lorg/jetbrains/kotlin/fir/types/CompilerConeAttributes$Exact;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(CompilerConeAttributesKt.class, "cones"), "noInfer", "getNoInfer(Lorg/jetbrains/kotlin/fir/types/ConeAttributes;)Lorg/jetbrains/kotlin/fir/types/CompilerConeAttributes$NoInfer;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(CompilerConeAttributesKt.class, "cones"), "enhancedNullability", "getEnhancedNullability(Lorg/jetbrains/kotlin/fir/types/ConeAttributes;)Lorg/jetbrains/kotlin/fir/types/CompilerConeAttributes$EnhancedNullability;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(CompilerConeAttributesKt.class, "cones"), "extensionFunctionType", "getExtensionFunctionType(Lorg/jetbrains/kotlin/fir/types/ConeAttributes;)Lorg/jetbrains/kotlin/fir/types/CompilerConeAttributes$ExtensionFunctionType;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(CompilerConeAttributesKt.class, "cones"), "contextFunctionTypeParams", "getContextFunctionTypeParams(Lorg/jetbrains/kotlin/fir/types/ConeAttributes;)Lorg/jetbrains/kotlin/fir/types/CompilerConeAttributes$ContextFunctionTypeParams;"))};
    private static final ReadOnlyProperty contextFunctionTypeParams$delegate;
    private static final ReadOnlyProperty enhancedNullability$delegate;
    private static final ReadOnlyProperty exact$delegate;
    private static final ReadOnlyProperty extensionFunctionType$delegate;
    private static final ReadOnlyProperty noInfer$delegate;

    static {
        ReadOnlyProperty readOnlyPropertyGenerateNullableAccessor = ConeAttributes.INSTANCE.generateNullableAccessor(Reflection.getOrCreateKotlinClass(CompilerConeAttributes.Exact.class));
        Intrinsics.checkNotNull(readOnlyPropertyGenerateNullableAccessor, "null cannot be cast to non-null type kotlin.properties.ReadOnlyProperty<org.jetbrains.kotlin.fir.types.ConeAttributes, T of org.jetbrains.kotlin.fir.types.ConeAttributes.Companion.attributeAccessor?>");
        exact$delegate = readOnlyPropertyGenerateNullableAccessor;
        ReadOnlyProperty readOnlyPropertyGenerateNullableAccessor2 = ConeAttributes.INSTANCE.generateNullableAccessor(Reflection.getOrCreateKotlinClass(CompilerConeAttributes.NoInfer.class));
        Intrinsics.checkNotNull(readOnlyPropertyGenerateNullableAccessor2, "null cannot be cast to non-null type kotlin.properties.ReadOnlyProperty<org.jetbrains.kotlin.fir.types.ConeAttributes, T of org.jetbrains.kotlin.fir.types.ConeAttributes.Companion.attributeAccessor?>");
        noInfer$delegate = readOnlyPropertyGenerateNullableAccessor2;
        ReadOnlyProperty readOnlyPropertyGenerateNullableAccessor3 = ConeAttributes.INSTANCE.generateNullableAccessor(Reflection.getOrCreateKotlinClass(CompilerConeAttributes.EnhancedNullability.class));
        Intrinsics.checkNotNull(readOnlyPropertyGenerateNullableAccessor3, "null cannot be cast to non-null type kotlin.properties.ReadOnlyProperty<org.jetbrains.kotlin.fir.types.ConeAttributes, T of org.jetbrains.kotlin.fir.types.ConeAttributes.Companion.attributeAccessor?>");
        enhancedNullability$delegate = readOnlyPropertyGenerateNullableAccessor3;
        ReadOnlyProperty readOnlyPropertyGenerateNullableAccessor4 = ConeAttributes.INSTANCE.generateNullableAccessor(Reflection.getOrCreateKotlinClass(CompilerConeAttributes.ExtensionFunctionType.class));
        Intrinsics.checkNotNull(readOnlyPropertyGenerateNullableAccessor4, "null cannot be cast to non-null type kotlin.properties.ReadOnlyProperty<org.jetbrains.kotlin.fir.types.ConeAttributes, T of org.jetbrains.kotlin.fir.types.ConeAttributes.Companion.attributeAccessor?>");
        extensionFunctionType$delegate = readOnlyPropertyGenerateNullableAccessor4;
        ReadOnlyProperty readOnlyPropertyGenerateNullableAccessor5 = ConeAttributes.INSTANCE.generateNullableAccessor(Reflection.getOrCreateKotlinClass(CompilerConeAttributes.ContextFunctionTypeParams.class));
        Intrinsics.checkNotNull(readOnlyPropertyGenerateNullableAccessor5, "null cannot be cast to non-null type kotlin.properties.ReadOnlyProperty<org.jetbrains.kotlin.fir.types.ConeAttributes, T of org.jetbrains.kotlin.fir.types.ConeAttributes.Companion.attributeAccessor?>");
        contextFunctionTypeParams$delegate = readOnlyPropertyGenerateNullableAccessor5;
    }

    public static final CompilerConeAttributes.Exact getExact(ConeAttributes coneAttributes) {
        Intrinsics.checkNotNullParameter(coneAttributes, "<this>");
        return (CompilerConeAttributes.Exact) exact$delegate.getValue(coneAttributes, $$delegatedProperties[0]);
    }

    public static final CompilerConeAttributes.NoInfer getNoInfer(ConeAttributes coneAttributes) {
        Intrinsics.checkNotNullParameter(coneAttributes, "<this>");
        return (CompilerConeAttributes.NoInfer) noInfer$delegate.getValue(coneAttributes, $$delegatedProperties[1]);
    }

    public static final CompilerConeAttributes.EnhancedNullability getEnhancedNullability(ConeAttributes coneAttributes) {
        Intrinsics.checkNotNullParameter(coneAttributes, "<this>");
        return (CompilerConeAttributes.EnhancedNullability) enhancedNullability$delegate.getValue(coneAttributes, $$delegatedProperties[2]);
    }

    public static final CompilerConeAttributes.ExtensionFunctionType getExtensionFunctionType(ConeAttributes coneAttributes) {
        Intrinsics.checkNotNullParameter(coneAttributes, "<this>");
        return (CompilerConeAttributes.ExtensionFunctionType) extensionFunctionType$delegate.getValue(coneAttributes, $$delegatedProperties[3]);
    }

    private static final CompilerConeAttributes.ContextFunctionTypeParams getContextFunctionTypeParams(ConeAttributes coneAttributes) {
        return (CompilerConeAttributes.ContextFunctionTypeParams) contextFunctionTypeParams$delegate.getValue(coneAttributes, $$delegatedProperties[4]);
    }

    public static final boolean getHasEnhancedNullability(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return getEnhancedNullability(coneKotlinType.getAttributes()) != null;
    }

    public static final boolean isExtensionFunctionType(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return getExtensionFunctionType(coneKotlinType.getAttributes()) != null;
    }

    public static final boolean getHasContextReceivers(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return getContextReceiversNumberForFunctionType(coneKotlinType.getAttributes()) > 0;
    }

    public static final int getContextReceiversNumberForFunctionType(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return getContextReceiversNumberForFunctionType(coneKotlinType.getAttributes());
    }

    public static final int getContextReceiversNumberForFunctionType(ConeAttributes coneAttributes) {
        Intrinsics.checkNotNullParameter(coneAttributes, "<this>");
        CompilerConeAttributes.ContextFunctionTypeParams contextFunctionTypeParams = getContextFunctionTypeParams(coneAttributes);
        if (contextFunctionTypeParams != null) {
            return contextFunctionTypeParams.getContextReceiverNumber();
        }
        return 0;
    }
}
