package org.jetbrains.kotlin.ir.util;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: DescriptorToIrUtil.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000N\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001aP\u0010\r\u001a\u00020\u000e*\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u001b2\b\b\u0002\u0010\u001c\u001a\u00020\u001d\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0006*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0007\"\u0015\u0010\b\u001a\u00020\u0006*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\b\u0010\u0007\"\u0017\u0010\t\u001a\u0004\u0018\u00010\n*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\u001e"}, d2 = {"indexOrMinusOne", "", "Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;", "getIndexOrMinusOne", "(Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;)I", "isCrossinline", "", "(Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;)Z", "isNoinline", "varargElementType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getVarargElementType", "(Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;)Lorg/jetbrains/kotlin/types/KotlinType;", "createIrClassFromDescriptor", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "startOffset", "endOffset", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "name", "Lorg/jetbrains/kotlin/name/Name;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DescriptorToIrUtilKt {
    public static final int getIndexOrMinusOne(ParameterDescriptor parameterDescriptor) {
        Intrinsics.checkNotNullParameter(parameterDescriptor, "<this>");
        if (parameterDescriptor instanceof ValueParameterDescriptor) {
            return ((ValueParameterDescriptor) parameterDescriptor).getIndex();
        }
        return -1;
    }

    public static final KotlinType getVarargElementType(ParameterDescriptor parameterDescriptor) {
        Intrinsics.checkNotNullParameter(parameterDescriptor, "<this>");
        ValueParameterDescriptor valueParameterDescriptor = parameterDescriptor instanceof ValueParameterDescriptor ? (ValueParameterDescriptor) parameterDescriptor : null;
        if (valueParameterDescriptor != null) {
            return valueParameterDescriptor.getVarargElementType();
        }
        return null;
    }

    public static final boolean isCrossinline(ParameterDescriptor parameterDescriptor) {
        Intrinsics.checkNotNullParameter(parameterDescriptor, "<this>");
        return (parameterDescriptor instanceof ValueParameterDescriptor) && ((ValueParameterDescriptor) parameterDescriptor).getIsCrossinline();
    }

    public static final boolean isNoinline(ParameterDescriptor parameterDescriptor) {
        Intrinsics.checkNotNullParameter(parameterDescriptor, "<this>");
        return (parameterDescriptor instanceof ValueParameterDescriptor) && ((ValueParameterDescriptor) parameterDescriptor).getIsNoinline();
    }

    public static /* synthetic */ IrClass createIrClassFromDescriptor$default(IrFactory irFactory, int i, int i2, IrDeclarationOrigin irDeclarationOrigin, IrClassSymbol irClassSymbol, ClassDescriptor classDescriptor, Name name2, DescriptorVisibility descriptorVisibility, Modality modality, int i3, Object obj) {
        Name name3;
        DescriptorVisibility descriptorVisibility2;
        Modality modality2;
        if ((i3 & 32) != 0) {
            Name name4 = classDescriptor.getShortName();
            Intrinsics.checkNotNullExpressionValue(name4, "getName(...)");
            name3 = name4;
        } else {
            name3 = name2;
        }
        if ((i3 & 64) != 0) {
            DescriptorVisibility visibility = classDescriptor.getVisibilityImpl();
            Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
            descriptorVisibility2 = visibility;
        } else {
            descriptorVisibility2 = descriptorVisibility;
        }
        if ((i3 & 128) != 0) {
            Modality modality3 = classDescriptor.getModality();
            Intrinsics.checkNotNullExpressionValue(modality3, "getModality(...)");
            modality2 = modality3;
        } else {
            modality2 = modality;
        }
        return createIrClassFromDescriptor(irFactory, i, i2, irDeclarationOrigin, irClassSymbol, classDescriptor, name3, descriptorVisibility2, modality2);
    }

    public static final IrClass createIrClassFromDescriptor(IrFactory irFactory, int i, int i2, IrDeclarationOrigin origin, IrClassSymbol symbol, ClassDescriptor descriptor, Name name2, DescriptorVisibility visibility, Modality modality) {
        Intrinsics.checkNotNullParameter(irFactory, "<this>");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(modality, "modality");
        ClassKind kind = descriptor.getKind();
        Intrinsics.checkNotNullExpressionValue(kind, "getKind(...)");
        boolean zIsEffectivelyExternal = DescriptorUtilsKt.isEffectivelyExternal(descriptor);
        boolean isCompanionObject = descriptor.getIsCompanionObject();
        boolean zIsInner = descriptor.getIsInner();
        boolean zIsData = descriptor.isData();
        boolean zIsValueClass = InlineClassesUtilsKt.isValueClass(descriptor);
        boolean zIsExpect = descriptor.get$isExpect();
        boolean zIsFun = descriptor.isFun();
        SourceElement source = descriptor.getSourceElement();
        Intrinsics.checkNotNullExpressionValue(source, "getSource(...)");
        return irFactory.createClass(i, i2, origin, name2, visibility, symbol, kind, modality, zIsEffectivelyExternal, isCompanionObject, zIsInner, zIsData, zIsValueClass, zIsExpect, zIsFun, source);
    }
}
