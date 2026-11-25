package org.jetbrains.kotlin.ir.declarations;

import java.io.File;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.InlineClassRepresentation;
import org.jetbrains.kotlin.descriptors.MultiFieldValueClassRepresentation;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueClassRepresentation;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: IrDeclarations.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u001b\u001a\u00020\u001c*\u00020\u00032\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e\u001a\u0012\u0010 \u001a\u00020\u001c*\u00020\u00032\u0006\u0010!\u001a\u00020\u001f\u001a#\u0010\"\u001a\u0002H#\"\b\b\u0000\u0010#*\u00020$*\u0002H#2\b\u0010%\u001a\u0004\u0018\u00010$¢\u0006\u0002\u0010&\u001a\u001c\u0010'\u001a\u00020(*\u00020\n2\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,H\u0007\u001a\u0014\u0010'\u001a\u00020(*\u00020\n2\u0006\u0010)\u001a\u00020-H\u0007\u001a\u001c\u0010.\u001a\u00020\u001c*\u00020\n2\u0006\u0010)\u001a\u00020-2\u0006\u0010/\u001a\u000200H\u0007\"\u001d\u0010\u0000\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u0001*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\"\u0015\u0010\u0006\u001a\u00020\u0007*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\b\"\u0015\u0010\t\u001a\u00020\u0007*\u00020\n8F¢\u0006\u0006\u001a\u0004\b\t\u0010\u000b\"\u0015\u0010\f\u001a\u00020\u0007*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\f\u0010\b\"\u0015\u0010\r\u001a\u00020\u0007*\u00020\n8F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000b\"\u001d\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u000f*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011\"\u0015\u0010\u0012\u001a\u00020\u0013*\u00020\u00148F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016\"\u0015\u0010\u0017\u001a\u00020\u0013*\u00020\u00148F¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0016\"\u0015\u0010\u0019\u001a\u00020\u0013*\u00020\u00148F¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0016¨\u00061"}, d2 = {"inlineClassRepresentation", "Lorg/jetbrains/kotlin/descriptors/InlineClassRepresentation;", "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getInlineClassRepresentation", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/descriptors/InlineClassRepresentation;", "isMultiFieldValueClass", "", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Z", "isPropertyAccessor", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)Z", "isSingleFieldValueClass", "isStaticMethodOfClass", "multiFieldValueClassRepresentation", "Lorg/jetbrains/kotlin/descriptors/MultiFieldValueClassRepresentation;", "getMultiFieldValueClassRepresentation", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/descriptors/MultiFieldValueClassRepresentation;", "name", "", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "getName", "(Lorg/jetbrains/kotlin/ir/declarations/IrFile;)Ljava/lang/String;", "nameWithPackage", "getNameWithPackage", "path", "getPath", "addAll", "", "members", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "addMember", "member", "copyAttributes", "D", "Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;", "other", "(Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;)Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;", "getIrValueParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "parameter", "Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;", "index", "", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "putDefault", "expressionBody", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrDeclarationsKt {
    public static final <D extends IrAttributeContainer> D copyAttributes(D d, IrAttributeContainer irAttributeContainer) {
        Intrinsics.checkNotNullParameter(d, "<this>");
        if (irAttributeContainer != null) {
            d.setAttributeOwnerId(irAttributeContainer.getAttributeOwnerId());
            d.setOriginalBeforeInline(irAttributeContainer.getOriginalBeforeInline());
        }
        return d;
    }

    public static final boolean isSingleFieldValueClass(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        return irClass.getValueClassRepresentation() instanceof InlineClassRepresentation;
    }

    public static final boolean isMultiFieldValueClass(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        return irClass.getValueClassRepresentation() instanceof MultiFieldValueClassRepresentation;
    }

    public static final void addMember(IrClass irClass, IrDeclaration member) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Intrinsics.checkNotNullParameter(member, "member");
        irClass.getDeclarations().add(member);
    }

    public static final void addAll(IrClass irClass, List<? extends IrDeclaration> members) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Intrinsics.checkNotNullParameter(members, "members");
        irClass.getDeclarations().addAll(members);
    }

    public static final String getPath(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "<this>");
        return irFile.getFileEntry().getName();
    }

    public static final String getName(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "<this>");
        String name2 = new File(getPath(irFile)).getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        return name2;
    }

    public static final String getNameWithPackage(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "<this>");
        String strAsString = irFile.getPackageFqName().child(Name.identifier(getName(irFile))).asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return strAsString;
    }

    @ObsoleteDescriptorBasedAPI
    public static final IrValueParameter getIrValueParameter(IrFunction irFunction, ValueParameterDescriptor parameter) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        return getIrValueParameter(irFunction, parameter, parameter.getIndex());
    }

    @ObsoleteDescriptorBasedAPI
    public static final IrValueParameter getIrValueParameter(IrFunction irFunction, ParameterDescriptor parameter, int i) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        List<IrValueParameter> valueParameters = irFunction.getValueParameters();
        if (i < 0 || i > CollectionsKt.getLastIndex(valueParameters)) {
            throw new AssertionError("No IrValueParameter for " + parameter);
        }
        IrValueParameter irValueParameter = valueParameters.get(i);
        Intrinsics.areEqual(irValueParameter.getDescriptor(), parameter);
        return irValueParameter;
    }

    @ObsoleteDescriptorBasedAPI
    public static final void putDefault(IrFunction irFunction, ValueParameterDescriptor parameter, IrExpressionBody expressionBody) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        Intrinsics.checkNotNullParameter(expressionBody, "expressionBody");
        getIrValueParameter(irFunction, parameter).setDefaultValue(expressionBody);
    }

    public static final boolean isStaticMethodOfClass(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        return (irFunction instanceof IrSimpleFunction) && (irFunction.getParent() instanceof IrClass) && irFunction.getDispatchReceiverParameter() == null;
    }

    public static final boolean isPropertyAccessor(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        return (irFunction instanceof IrSimpleFunction) && ((IrSimpleFunction) irFunction).getCorrespondingPropertySymbol() != null;
    }

    public static final MultiFieldValueClassRepresentation<IrSimpleType> getMultiFieldValueClassRepresentation(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        ValueClassRepresentation<IrSimpleType> valueClassRepresentation = irClass.getValueClassRepresentation();
        if (valueClassRepresentation instanceof MultiFieldValueClassRepresentation) {
            return (MultiFieldValueClassRepresentation) valueClassRepresentation;
        }
        return null;
    }

    public static final InlineClassRepresentation<IrSimpleType> getInlineClassRepresentation(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        ValueClassRepresentation<IrSimpleType> valueClassRepresentation = irClass.getValueClassRepresentation();
        if (valueClassRepresentation instanceof InlineClassRepresentation) {
            return (InlineClassRepresentation) valueClassRepresentation;
        }
        return null;
    }
}
