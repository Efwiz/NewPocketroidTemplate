package org.jetbrains.kotlin.fir.declarations;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.descriptors.InlineClassRepresentation;
import org.jetbrains.kotlin.descriptors.MultiFieldValueClassRepresentation;
import org.jetbrains.kotlin.descriptors.ValueClassRepresentation;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;

/* JADX INFO: compiled from: FirValueClassRepresentation.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\"\u001d\u0010\u0000\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u0001*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\"\u001d\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u0007*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\b\u0010\t\"?\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u000b*\u00020\u00032\u000e\u0010\n\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u000b8F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010¨\u0006\u0013"}, d2 = {"inlineClassRepresentation", "Lorg/jetbrains/kotlin/descriptors/InlineClassRepresentation;", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "getInlineClassRepresentation", "(Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;)Lorg/jetbrains/kotlin/descriptors/InlineClassRepresentation;", "multiFieldValueClassRepresentation", "Lorg/jetbrains/kotlin/descriptors/MultiFieldValueClassRepresentation;", "getMultiFieldValueClassRepresentation", "(Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;)Lorg/jetbrains/kotlin/descriptors/MultiFieldValueClassRepresentation;", "<set-?>", "Lorg/jetbrains/kotlin/descriptors/ValueClassRepresentation;", "valueClassRepresentation", "getValueClassRepresentation", "(Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;)Lorg/jetbrains/kotlin/descriptors/ValueClassRepresentation;", "setValueClassRepresentation", "(Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;Lorg/jetbrains/kotlin/descriptors/ValueClassRepresentation;)V", "valueClassRepresentation$delegate", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationDataRegistry$DeclarationDataAccessor;", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirValueClassRepresentationKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(FirValueClassRepresentationKt.class, Constants.TYPE_TREE), "valueClassRepresentation", "getValueClassRepresentation(Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;)Lorg/jetbrains/kotlin/descriptors/ValueClassRepresentation;"))};
    private static final FirDeclarationDataRegistry.DeclarationDataAccessor valueClassRepresentation$delegate = FirDeclarationDataRegistry.INSTANCE.data(FirValueClassRepresentationKey.INSTANCE);

    public static final ValueClassRepresentation<ConeSimpleKotlinType> getValueClassRepresentation(FirRegularClass firRegularClass) {
        Intrinsics.checkNotNullParameter(firRegularClass, "<this>");
        return (ValueClassRepresentation) valueClassRepresentation$delegate.getValue(firRegularClass, $$delegatedProperties[0]);
    }

    public static final void setValueClassRepresentation(FirRegularClass firRegularClass, ValueClassRepresentation<ConeSimpleKotlinType> valueClassRepresentation) {
        Intrinsics.checkNotNullParameter(firRegularClass, "<this>");
        valueClassRepresentation$delegate.setValue(firRegularClass, $$delegatedProperties[0], valueClassRepresentation);
    }

    public static final InlineClassRepresentation<ConeSimpleKotlinType> getInlineClassRepresentation(FirRegularClass firRegularClass) {
        Intrinsics.checkNotNullParameter(firRegularClass, "<this>");
        ValueClassRepresentation<ConeSimpleKotlinType> valueClassRepresentation = getValueClassRepresentation(firRegularClass);
        if (valueClassRepresentation instanceof InlineClassRepresentation) {
            return (InlineClassRepresentation) valueClassRepresentation;
        }
        return null;
    }

    public static final MultiFieldValueClassRepresentation<ConeSimpleKotlinType> getMultiFieldValueClassRepresentation(FirRegularClass firRegularClass) {
        Intrinsics.checkNotNullParameter(firRegularClass, "<this>");
        ValueClassRepresentation<ConeSimpleKotlinType> valueClassRepresentation = getValueClassRepresentation(firRegularClass);
        if (valueClassRepresentation instanceof MultiFieldValueClassRepresentation) {
            return (MultiFieldValueClassRepresentation) valueClassRepresentation;
        }
        return null;
    }
}
