package org.jetbrains.kotlin.resolve;

import com.android.dx.rop.code.RegisterSpec;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.InlineClassRepresentation;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.SimpleFunctionDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.ValueParameterDescriptorImpl;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;

/* JADX INFO: compiled from: InlineClassDescriptorResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007J\u0018\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u000e\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\u0010\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007J(\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0018\u0010 \u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010!\u001a\u00020\u001cH\u0002J\u001e\u0010\"\u001a\b\u0012\u0004\u0012\u00020$0#2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010!\u001a\u00020\u001cH\u0002J\u0010\u0010%\u001a\u00020\u00142\u0006\u0010&\u001a\u00020'H\u0007J\u0010\u0010(\u001a\u00020\u00142\u0006\u0010&\u001a\u00020'H\u0007J\u0010\u0010)\u001a\u00020\u00142\u0006\u0010&\u001a\u00020'H\u0007J\u0010\u0010*\u001a\u00020\u00142\u0006\u0010&\u001a\u00020'H\u0002J\u0018\u0010+\u001a\u00020\u00142\u0006\u0010&\u001a\u00020'2\u0006\u0010\u001d\u001a\u00020\u0004H\u0002J\u0010\u0010,\u001a\u00020\u00142\u0006\u0010&\u001a\u00020'H\u0007R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0010\u0010\u0007\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0006R\u0010\u0010\n\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0010\u0010\r\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006-"}, d2 = {"Lorg/jetbrains/kotlin/resolve/InlineClassDescriptorResolver;", "", "()V", "BOXING_VALUE_PARAMETER_NAME", "Lorg/jetbrains/kotlin/name/Name;", "getBOXING_VALUE_PARAMETER_NAME", "()Lorg/jetbrains/kotlin/name/Name;", "BOX_METHOD_NAME", "SPECIALIZED_EQUALS_FIRST_PARAMETER_NAME", "getSPECIALIZED_EQUALS_FIRST_PARAMETER_NAME", "SPECIALIZED_EQUALS_NAME", "SPECIALIZED_EQUALS_SECOND_PARAMETER_NAME", "getSPECIALIZED_EQUALS_SECOND_PARAMETER_NAME", "UNBOX_METHOD_NAME", "createBoxFunctionDescriptor", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "owner", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "createConversionFunctionDescriptor", "isBoxMethod", "", "createSpecializedEqualsDescriptor", "createUnboxFunctionDescriptor", "createValueParameter", "Lorg/jetbrains/kotlin/descriptors/impl/ValueParameterDescriptorImpl;", "functionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "name", "index", "", "createValueParameterForBoxing", "underlyingType", "createValueParametersForSpecializedEquals", "", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "isSpecializedEqualsMethod", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "isSynthesizedBoxMethod", "isSynthesizedBoxOrUnboxMethod", "isSynthesizedInlineClassMember", "isSynthesizedInlineClassMemberWithName", "isSynthesizedUnboxMethod", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InlineClassDescriptorResolver {
    private static final Name BOXING_VALUE_PARAMETER_NAME;
    public static final Name BOX_METHOD_NAME;
    public static final InlineClassDescriptorResolver INSTANCE = new InlineClassDescriptorResolver();
    private static final Name SPECIALIZED_EQUALS_FIRST_PARAMETER_NAME;
    public static final Name SPECIALIZED_EQUALS_NAME;
    private static final Name SPECIALIZED_EQUALS_SECOND_PARAMETER_NAME;
    public static final Name UNBOX_METHOD_NAME;

    private InlineClassDescriptorResolver() {
    }

    static {
        Name nameIdentifier = Name.identifier("box");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        BOX_METHOD_NAME = nameIdentifier;
        Name nameIdentifier2 = Name.identifier("unbox");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        UNBOX_METHOD_NAME = nameIdentifier2;
        Name nameIdentifier3 = Name.identifier("equals-impl0");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier3, "identifier(...)");
        SPECIALIZED_EQUALS_NAME = nameIdentifier3;
        Name nameIdentifier4 = Name.identifier(RegisterSpec.PREFIX);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier4, "identifier(...)");
        BOXING_VALUE_PARAMETER_NAME = nameIdentifier4;
        Name nameIdentifier5 = Name.identifier("p1");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier5, "identifier(...)");
        SPECIALIZED_EQUALS_FIRST_PARAMETER_NAME = nameIdentifier5;
        Name nameIdentifier6 = Name.identifier("p2");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier6, "identifier(...)");
        SPECIALIZED_EQUALS_SECOND_PARAMETER_NAME = nameIdentifier6;
    }

    public final Name getBOXING_VALUE_PARAMETER_NAME() {
        return BOXING_VALUE_PARAMETER_NAME;
    }

    public final Name getSPECIALIZED_EQUALS_FIRST_PARAMETER_NAME() {
        return SPECIALIZED_EQUALS_FIRST_PARAMETER_NAME;
    }

    public final Name getSPECIALIZED_EQUALS_SECOND_PARAMETER_NAME() {
        return SPECIALIZED_EQUALS_SECOND_PARAMETER_NAME;
    }

    @JvmStatic
    public static final SimpleFunctionDescriptor createBoxFunctionDescriptor(ClassDescriptor owner) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        return INSTANCE.createConversionFunctionDescriptor(true, owner);
    }

    @JvmStatic
    public static final SimpleFunctionDescriptor createUnboxFunctionDescriptor(ClassDescriptor owner) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        return INSTANCE.createConversionFunctionDescriptor(false, owner);
    }

    @JvmStatic
    public static final boolean isSynthesizedBoxMethod(CallableMemberDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return INSTANCE.isSynthesizedInlineClassMemberWithName(descriptor, BOX_METHOD_NAME);
    }

    @JvmStatic
    public static final boolean isSynthesizedUnboxMethod(CallableMemberDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return INSTANCE.isSynthesizedInlineClassMemberWithName(descriptor, UNBOX_METHOD_NAME);
    }

    @JvmStatic
    public static final boolean isSynthesizedBoxOrUnboxMethod(CallableMemberDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return INSTANCE.isSynthesizedInlineClassMember(descriptor) && (Intrinsics.areEqual(descriptor.getShortName(), BOX_METHOD_NAME) || Intrinsics.areEqual(descriptor.getShortName(), UNBOX_METHOD_NAME));
    }

    @JvmStatic
    public static final boolean isSpecializedEqualsMethod(CallableMemberDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return INSTANCE.isSynthesizedInlineClassMemberWithName(descriptor, SPECIALIZED_EQUALS_NAME);
    }

    private final boolean isSynthesizedInlineClassMemberWithName(CallableMemberDescriptor descriptor, Name name2) {
        return isSynthesizedInlineClassMember(descriptor) && Intrinsics.areEqual(descriptor.getShortName(), name2);
    }

    private final boolean isSynthesizedInlineClassMember(CallableMemberDescriptor descriptor) {
        if (descriptor.getKind() == CallableMemberDescriptor.Kind.SYNTHESIZED) {
            DeclarationDescriptor containingDeclaration = descriptor.getDeclarationDescriptor();
            Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
            if (InlineClassesUtilsKt.isInlineClass(containingDeclaration)) {
                return true;
            }
        }
        return false;
    }

    public final SimpleFunctionDescriptor createSpecializedEqualsDescriptor(ClassDescriptor owner) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        ClassDescriptor classDescriptor = owner;
        SimpleFunctionDescriptorImpl simpleFunctionDescriptorImplCreate = SimpleFunctionDescriptorImpl.create(classDescriptor, Annotations.INSTANCE.getEMPTY(), SPECIALIZED_EQUALS_NAME, CallableMemberDescriptor.Kind.SYNTHESIZED, SourceElement.NO_SOURCE);
        Intrinsics.checkNotNullExpressionValue(simpleFunctionDescriptorImplCreate, "create(...)");
        List<ReceiverParameterDescriptor> listEmptyList = CollectionsKt.emptyList();
        List<? extends TypeParameterDescriptor> listEmptyList2 = CollectionsKt.emptyList();
        InlineClassRepresentation<SimpleType> inlineClassRepresentation = DescriptorUtilsKt.getInlineClassRepresentation(owner);
        Intrinsics.checkNotNull(inlineClassRepresentation);
        simpleFunctionDescriptorImplCreate.initialize((ReceiverParameterDescriptor) null, (ReceiverParameterDescriptor) null, listEmptyList, listEmptyList2, createValueParametersForSpecializedEquals(simpleFunctionDescriptorImplCreate, (KotlinType) inlineClassRepresentation.getUnderlyingType()), (KotlinType) DescriptorUtilsKt.getBuiltIns(classDescriptor).getBooleanType(), Modality.FINAL, DescriptorVisibilities.PUBLIC);
        return simpleFunctionDescriptorImplCreate;
    }

    private final SimpleFunctionDescriptor createConversionFunctionDescriptor(boolean isBoxMethod, ClassDescriptor owner) {
        SimpleFunctionDescriptorImpl simpleFunctionDescriptorImplCreate = SimpleFunctionDescriptorImpl.create(owner, Annotations.INSTANCE.getEMPTY(), isBoxMethod ? BOX_METHOD_NAME : UNBOX_METHOD_NAME, CallableMemberDescriptor.Kind.SYNTHESIZED, SourceElement.NO_SOURCE);
        Intrinsics.checkNotNullExpressionValue(simpleFunctionDescriptorImplCreate, "create(...)");
        InlineClassRepresentation<SimpleType> inlineClassRepresentation = DescriptorUtilsKt.getInlineClassRepresentation(owner);
        Intrinsics.checkNotNull(inlineClassRepresentation);
        SimpleType defaultType = (SimpleType) inlineClassRepresentation.getUnderlyingType();
        ReceiverParameterDescriptor thisAsReceiverParameter = isBoxMethod ? null : owner.getThisAsReceiverParameter();
        List<ReceiverParameterDescriptor> listEmptyList = CollectionsKt.emptyList();
        List<? extends TypeParameterDescriptor> listEmptyList2 = CollectionsKt.emptyList();
        List<ValueParameterDescriptor> listListOf = isBoxMethod ? CollectionsKt.listOf(createValueParameterForBoxing(simpleFunctionDescriptorImplCreate, defaultType)) : CollectionsKt.emptyList();
        if (isBoxMethod) {
            defaultType = owner.getDefaultType();
        }
        simpleFunctionDescriptorImplCreate.initialize((ReceiverParameterDescriptor) null, thisAsReceiverParameter, listEmptyList, listEmptyList2, listListOf, (KotlinType) defaultType, Modality.FINAL, DescriptorVisibilities.PUBLIC);
        return simpleFunctionDescriptorImplCreate;
    }

    private final ValueParameterDescriptorImpl createValueParameterForBoxing(FunctionDescriptor functionDescriptor, KotlinType underlyingType) {
        return createValueParameter(functionDescriptor, underlyingType, BOXING_VALUE_PARAMETER_NAME, 0);
    }

    private final List<ValueParameterDescriptor> createValueParametersForSpecializedEquals(FunctionDescriptor functionDescriptor, KotlinType underlyingType) {
        return CollectionsKt.listOf((Object[]) new ValueParameterDescriptorImpl[]{createValueParameter(functionDescriptor, underlyingType, SPECIALIZED_EQUALS_FIRST_PARAMETER_NAME, 0), createValueParameter(functionDescriptor, underlyingType, SPECIALIZED_EQUALS_SECOND_PARAMETER_NAME, 1)});
    }

    private final ValueParameterDescriptorImpl createValueParameter(FunctionDescriptor functionDescriptor, KotlinType type, Name name2, int index) {
        Annotations empty = Annotations.INSTANCE.getEMPTY();
        SourceElement NO_SOURCE = SourceElement.NO_SOURCE;
        Intrinsics.checkNotNullExpressionValue(NO_SOURCE, "NO_SOURCE");
        return new ValueParameterDescriptorImpl(functionDescriptor, null, index, empty, name2, type, false, false, false, null, NO_SOURCE);
    }
}
