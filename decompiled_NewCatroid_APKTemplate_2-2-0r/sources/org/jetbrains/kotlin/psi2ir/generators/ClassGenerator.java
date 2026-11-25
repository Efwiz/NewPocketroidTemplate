package org.jetbrains.kotlin.psi2ir.generators;

import com.gaurav.avnc.vnc.XKeySym;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.backend.common.CodegenUtil;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorNonRoot;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilityUtils;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueClassRepresentation;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.declarations.DescriptorMetadataSource;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.descriptors.IrImplementingDelegateDescriptorImpl;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrExpressionsKt;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrFieldSymbolImpl;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension;
import org.jetbrains.kotlin.ir.util.DescriptorToIrUtilKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.OverridesKt;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtClassBody;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDelegatedSuperTypeEntry;
import org.jetbrains.kotlin.psi.KtEnumEntry;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtPrimaryConstructor;
import org.jetbrains.kotlin.psi.KtPureClassOrObject;
import org.jetbrains.kotlin.psi.KtSuperTypeList;
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.psi.synthetics.SyntheticClassOrObjectDescriptor;
import org.jetbrains.kotlin.psi.synthetics.SyntheticClassOrObjectDescriptorKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DelegationResolver;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjectionImpl;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.error.ErrorType;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: ClassGenerator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000ª\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0001\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0006H\u0002J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0010\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0018\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0018\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u001a\u0010\u0013\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00142\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0016J \u0010\u0017\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u000e\u001a\u00020\u0014H\u0002J(\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010 \u001a\u00020!H\u0002J(\u0010\"\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020\u0006H\u0002J \u0010\"\u001a\u00020!2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020\u0006H\u0002J \u0010#\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'H\u0002J(\u0010(\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020*H\u0002J(\u0010,\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020-2\u0006\u0010\u001f\u001a\u00020-H\u0002J \u0010,\u001a\u00020.2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020-2\u0006\u0010\u001f\u001a\u00020-H\u0002J\u000e\u0010/\u001a\u0002002\u0006\u00101\u001a\u000202J\u0018\u00103\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0014H\u0002J\u0018\u00104\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0018\u00105\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0014H\u0002J\u0018\u00106\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u001a\u00107\u001a\u0004\u0018\u00010\u00192\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0014H\u0002J\u0018\u00108\u001a\u0002092\u0006\u0010\u000e\u001a\u00020\u00142\u0006\u0010\u0007\u001a\u00020\bH\u0002J&\u0010:\u001a\u0010\u0012\u0004\u0012\u00020<\u0012\u0004\u0012\u00020=\u0018\u00010;2\u0006\u0010\u001e\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020\u0006H\u0002J'\u0010>\u001a\u0002H?\"\b\b\u0000\u0010?*\u00020*2\u0006\u0010@\u001a\u0002H?2\u0006\u0010A\u001a\u0002H?H\u0002¢\u0006\u0002\u0010BJ\u0014\u0010C\u001a\u00020\u000b*\u00020.2\u0006\u0010D\u001a\u00020-H\u0002J\f\u0010E\u001a\u00020F*\u00020\u0014H\u0002J\f\u0010G\u001a\u00020F*\u000202H\u0002¨\u0006H"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/ClassGenerator;", "Lorg/jetbrains/kotlin/psi2ir/generators/DeclarationGeneratorExtension;", "declarationGenerator", "Lorg/jetbrains/kotlin/psi2ir/generators/DeclarationGenerator;", "(Lorg/jetbrains/kotlin/psi2ir/generators/DeclarationGenerator;)V", "createFakeOverrideDescriptorForParentStaticMember", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "parentStaticMember", "generateAdditionalMembersForDataClass", "", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "ktClassOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "generateAdditionalMembersForEnumClass", "generateAdditionalMembersForMultiFieldValueClasses", "generateAdditionalMembersForSingleFieldValueClasses", "generateClass", "Lorg/jetbrains/kotlin/psi/KtPureClassOrObject;", "visibility_", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "generateDeclarationsForPrimaryConstructorParameters", "irPrimaryConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "generateDelegateFunctionBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", "irDelegate", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "delegatedDescriptor", "delegateToDescriptor", "irDelegatedFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "generateDelegatedFunction", "generateDelegatedImplementationMembers", "ktEntry", "Lorg/jetbrains/kotlin/psi/KtDelegatedSuperTypeEntry;", "delegateNumber", "", "generateDelegatedMember", "delegatedMember", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "delegateToMember", "generateDelegatedProperty", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "generateEnumEntry", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "ktEnumEntry", "Lorg/jetbrains/kotlin/psi/KtEnumEntry;", "generateFakeOverrideMemberDeclarations", "generateFieldsForContextReceivers", "generateMembersDeclaredInClassBody", "generateMembersDeclaredInSupertypeList", "generatePrimaryConstructor", "getEffectiveModality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "getTypeArgumentsForOverriddenDescriptorDelegatingCall", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "Lorg/jetbrains/kotlin/types/KotlinType;", "substituteDelegateToDescriptor", "D", "delegated", "overridden", "(Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;)Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "generateOverrides", "propertyDescriptor", "hasEnumEntriesWithClassMembers", "", "hasMemberDeclarations", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
@ObsoleteDescriptorBasedAPI
public final class ClassGenerator extends DeclarationGeneratorExtension {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClassGenerator(DeclarationGenerator declarationGenerator) {
        super(declarationGenerator);
        Intrinsics.checkNotNullParameter(declarationGenerator, "declarationGenerator");
    }

    public static /* synthetic */ IrClass generateClass$default(ClassGenerator classGenerator, KtPureClassOrObject ktPureClassOrObject, DescriptorVisibility descriptorVisibility, int i, Object obj) {
        if ((i & 2) != 0) {
            descriptorVisibility = null;
        }
        return classGenerator.generateClass(ktPureClassOrObject, descriptorVisibility);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final IrClass generateClass(KtPureClassOrObject ktClassOrObject, DescriptorVisibility visibility_) {
        final DescriptorVisibility descriptorVisibility;
        Intrinsics.checkNotNullParameter(ktClassOrObject, "ktClassOrObject");
        KtPureClassOrObject ktPureClassOrObject = ktClassOrObject;
        final ClassDescriptor classDescriptorFindClassDescriptor = SyntheticClassOrObjectDescriptorKt.findClassDescriptor(ktPureClassOrObject, getContext().getBindingContext());
        Integer startOffsetOfClassDeclarationOrNull = DeclarationStartOffsetKt.getStartOffsetOfClassDeclarationOrNull(ktClassOrObject);
        final int iIntValue = startOffsetOfClassDeclarationOrNull != null ? startOffsetOfClassDeclarationOrNull.intValue() : PsiUtilsKt.getPureStartOffset(ktPureClassOrObject);
        final int pureEndOffset = PsiUtilsKt.getPureEndOffset(ktPureClassOrObject);
        if (visibility_ == null) {
            DescriptorVisibility visibility = classDescriptorFindClassDescriptor.getVisibility();
            Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
            descriptorVisibility = visibility;
        } else {
            descriptorVisibility = visibility_;
        }
        final Modality effectiveModality = getEffectiveModality(ktClassOrObject, classDescriptorFindClassDescriptor);
        IrClass irClassDeclareClass = getContext().getSymbolTable().getDescriptorExtension().declareClass(classDescriptorFindClassDescriptor, new Function1<IrClassSymbol, IrClass>() { // from class: org.jetbrains.kotlin.psi2ir.generators.ClassGenerator.generateClass.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrClass invoke(IrClassSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                IrClass irClassCreateIrClassFromDescriptor = DescriptorToIrUtilKt.createIrClassFromDescriptor(ClassGenerator.this.getContext().getIrFactory(), iIntValue, pureEndOffset, IrDeclarationOrigin.DEFINED.INSTANCE, it, classDescriptorFindClassDescriptor, ClassGenerator.this.getContext().getSymbolTable().getNameProvider().nameForDeclaration(classDescriptorFindClassDescriptor), descriptorVisibility, effectiveModality);
                irClassCreateIrClassFromDescriptor.setMetadata(new DescriptorMetadataSource.Class(it.getDescriptor()));
                return irClassCreateIrClassFromDescriptor;
            }
        });
        SymbolTable symbolTable = getContext().getSymbolTable();
        symbolTable.enterScope(irClassDeclareClass);
        IrClass irClass = irClassDeclareClass;
        List<TypeParameterDescriptor> declaredTypeParameters = classDescriptorFindClassDescriptor.getDeclaredTypeParameters();
        Intrinsics.checkNotNullExpressionValue(declaredTypeParameters, "getDeclaredTypeParameters(...)");
        getDeclarationGenerator().generateGlobalTypeParametersDeclarations(irClass, declaredTypeParameters);
        Collection<KotlinType> collectionMo7438getSupertypes = classDescriptorFindClassDescriptor.getTypeConstructor().mo7438getSupertypes();
        Intrinsics.checkNotNullExpressionValue(collectionMo7438getSupertypes, "getSupertypes(...)");
        Collection<KotlinType> collection = collectionMo7438getSupertypes;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        for (KotlinType kotlinType : collection) {
            Intrinsics.checkNotNull(kotlinType);
            arrayList.add(toIrType(kotlinType));
        }
        irClass.setSuperTypes(arrayList);
        DescriptorSymbolTableExtension descriptorExtension = getContext().getSymbolTable().getDescriptorExtension();
        IrDeclarationOrigin.INSTANCE_RECEIVER instance_receiver = IrDeclarationOrigin.INSTANCE_RECEIVER.INSTANCE;
        ReceiverParameterDescriptor thisAsReceiverParameter = classDescriptorFindClassDescriptor.getThisAsReceiverParameter();
        Intrinsics.checkNotNullExpressionValue(thisAsReceiverParameter, "getThisAsReceiverParameter(...)");
        ReceiverParameterDescriptor receiverParameterDescriptor = thisAsReceiverParameter;
        KotlinType type = classDescriptorFindClassDescriptor.getThisAsReceiverParameter().getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        irClass.setThisReceiver(DescriptorSymbolTableExtension.declareValueParameter$default(descriptorExtension, iIntValue, pureEndOffset, instance_receiver, receiverParameterDescriptor, toIrType(type), null, null, null, false, null, XKeySym.XK_amacron, null));
        generateFieldsForContextReceivers(irClass, classDescriptorFindClassDescriptor);
        IrConstructor irConstructorGeneratePrimaryConstructor = generatePrimaryConstructor(irClass, ktClassOrObject);
        if (irConstructorGeneratePrimaryConstructor != null) {
            generateDeclarationsForPrimaryConstructorParameters(irClass, irConstructorGeneratePrimaryConstructor, ktClassOrObject);
        }
        boolean z = ktClassOrObject instanceof KtClassOrObject;
        if (z) {
            generateMembersDeclaredInSupertypeList(irClass, (KtClassOrObject) ktClassOrObject);
        }
        generateMembersDeclaredInClassBody(irClass, ktClassOrObject);
        generateFakeOverrideMemberDeclarations(irClass, ktClassOrObject);
        ValueClassRepresentation<SimpleType> valueClassRepresentation = classDescriptorFindClassDescriptor.getValueClassRepresentation();
        irClass.setValueClassRepresentation(valueClassRepresentation != null ? valueClassRepresentation.mapUnderlyingType(new Function1<SimpleType, IrSimpleType>() { // from class: org.jetbrains.kotlin.psi2ir.generators.ClassGenerator$generateClass$2$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrSimpleType invoke(SimpleType simpleType) {
                ClassGenerator classGenerator = this.this$0;
                Intrinsics.checkNotNull(simpleType);
                IrType irType = classGenerator.toIrType(simpleType);
                IrSimpleType irSimpleType = irType instanceof IrSimpleType ? (IrSimpleType) irType : null;
                if (irSimpleType != null) {
                    return irSimpleType;
                }
                throw new IllegalStateException(("Value class underlying type is not a simple type: " + classDescriptorFindClassDescriptor).toString());
            }
        }) : null);
        if (IrDeclarationsKt.isSingleFieldValueClass(irClass) && z) {
            generateAdditionalMembersForSingleFieldValueClasses(irClass, (KtClassOrObject) ktClassOrObject);
        }
        if (irClass.getIsData() && z) {
            generateAdditionalMembersForDataClass(irClass, (KtClassOrObject) ktClassOrObject);
        }
        if (IrDeclarationsKt.isMultiFieldValueClass(irClass) && z) {
            generateAdditionalMembersForMultiFieldValueClasses(irClass, (KtClassOrObject) ktClassOrObject);
        }
        if (DescriptorUtils.isEnumClass(classDescriptorFindClassDescriptor)) {
            generateAdditionalMembersForEnumClass(irClass);
        }
        Collection<ClassDescriptor> sealedSubclasses = classDescriptorFindClassDescriptor.getSealedSubclasses();
        Intrinsics.checkNotNullExpressionValue(sealedSubclasses, "getSealedSubclasses(...)");
        Collection<ClassDescriptor> collection2 = sealedSubclasses;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection2, 10));
        for (ClassDescriptor classDescriptor : collection2) {
            DescriptorSymbolTableExtension descriptorExtension2 = getContext().getSymbolTable().getDescriptorExtension();
            Intrinsics.checkNotNull(classDescriptor);
            arrayList2.add(descriptorExtension2.referenceClass(classDescriptor));
        }
        irClass.setSealedSubclasses(arrayList2);
        Unit unit = Unit.INSTANCE;
        symbolTable.leaveScope(irClassDeclareClass);
        return irClass;
    }

    private final Modality getEffectiveModality(KtPureClassOrObject ktClassOrObject, ClassDescriptor classDescriptor) {
        ClassDescriptor classDescriptor2 = classDescriptor;
        if (DescriptorUtils.isAnnotationClass(classDescriptor2)) {
            return Modality.OPEN;
        }
        if (!DescriptorUtils.isEnumClass(classDescriptor2)) {
            Modality modality = classDescriptor.getModality();
            Intrinsics.checkNotNullExpressionValue(modality, "getModality(...)");
            return modality;
        }
        if (DescriptorUtils.hasAbstractMembers(classDescriptor)) {
            return Modality.ABSTRACT;
        }
        if (hasEnumEntriesWithClassMembers(ktClassOrObject)) {
            return Modality.OPEN;
        }
        return Modality.FINAL;
    }

    private final boolean hasEnumEntriesWithClassMembers(KtPureClassOrObject ktPureClassOrObject) {
        KtClassBody body = ktPureClassOrObject.getBody();
        if (body == null) {
            return false;
        }
        List<KtEnumEntry> enumEntries = body.getEnumEntries();
        if ((enumEntries instanceof Collection) && enumEntries.isEmpty()) {
            return false;
        }
        Iterator<T> it = enumEntries.iterator();
        while (it.hasNext()) {
            if (hasMemberDeclarations((KtEnumEntry) it.next())) {
                return true;
            }
        }
        return false;
    }

    private final boolean hasMemberDeclarations(KtEnumEntry ktEnumEntry) {
        return !ktEnumEntry.getDeclarations().isEmpty();
    }

    private final void generateFakeOverrideMemberDeclarations(IrClass irClass, KtPureClassOrObject ktClassOrObject) {
        IrDeclaration irDeclarationGenerateFakeOverrideDeclaration;
        ClassDescriptor descriptor = irClass.getDescriptor();
        MemberScope unsubstitutedMemberScope = descriptor.getUnsubstitutedMemberScope();
        Intrinsics.checkNotNullExpressionValue(unsubstitutedMemberScope, "getUnsubstitutedMemberScope(...)");
        for (DeclarationDescriptor declarationDescriptor : ResolutionScope.DefaultImpls.getContributedDescriptors$default(unsubstitutedMemberScope, null, null, 3, null)) {
            if (declarationDescriptor instanceof CallableMemberDescriptor) {
                CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) declarationDescriptor;
                if (callableMemberDescriptor.getKind() == CallableMemberDescriptor.Kind.FAKE_OVERRIDE && (irDeclarationGenerateFakeOverrideDeclaration = getDeclarationGenerator().generateFakeOverrideDeclaration(callableMemberDescriptor, ktClassOrObject)) != null) {
                    irClass.getDeclarations().add(irDeclarationGenerateFakeOverrideDeclaration);
                }
            }
        }
        MemberScope parentClassStaticScope = getContext().getExtensions().getParentClassStaticScope(descriptor);
        if (parentClassStaticScope != null) {
            for (DeclarationDescriptor declarationDescriptor2 : ResolutionScope.DefaultImpls.getContributedDescriptors$default(parentClassStaticScope, null, null, 3, null)) {
                if ((declarationDescriptor2 instanceof FunctionDescriptor) && DescriptorVisibilityUtils.isVisibleIgnoringReceiver((DeclarationDescriptorWithVisibility) declarationDescriptor2, descriptor, getContext().getLanguageVersionSettings())) {
                    IrDeclaration irDeclarationGenerateFakeOverrideDeclaration2 = getDeclarationGenerator().generateFakeOverrideDeclaration(createFakeOverrideDescriptorForParentStaticMember(descriptor, (FunctionDescriptor) declarationDescriptor2), ktClassOrObject);
                    if (irDeclarationGenerateFakeOverrideDeclaration2 != null) {
                        irClass.getDeclarations().add(irDeclarationGenerateFakeOverrideDeclaration2);
                    }
                }
            }
        }
    }

    private final FunctionDescriptor createFakeOverrideDescriptorForParentStaticMember(ClassDescriptor classDescriptor, FunctionDescriptor parentStaticMember) {
        FunctionDescriptor functionDescriptorCopy = parentStaticMember.copy((DeclarationDescriptor) classDescriptor, parentStaticMember.getModality(), parentStaticMember.getVisibility(), CallableMemberDescriptor.Kind.FAKE_OVERRIDE, false);
        Intrinsics.checkNotNullExpressionValue(functionDescriptorCopy, "copy(...)");
        DescriptorUtilsKt.setSingleOverridden(functionDescriptorCopy, parentStaticMember);
        return functionDescriptorCopy;
    }

    private final void generateMembersDeclaredInSupertypeList(IrClass irClass, KtClassOrObject ktClassOrObject) {
        KtSuperTypeList superTypeList = ktClassOrObject.getSuperTypeList();
        if (superTypeList == null) {
            return;
        }
        int i = 0;
        for (KtSuperTypeListEntry ktSuperTypeListEntry : superTypeList.getEntries()) {
            if (ktSuperTypeListEntry instanceof KtDelegatedSuperTypeEntry) {
                generateDelegatedImplementationMembers(irClass, (KtDelegatedSuperTypeEntry) ktSuperTypeListEntry, i);
                i++;
            }
        }
    }

    private final void generateDelegatedImplementationMembers(IrClass irClass, KtDelegatedSuperTypeEntry ktEntry, int delegateNumber) {
        ErrorType typeInferredByFrontend;
        TypeConstructor constructor;
        ClassifierDescriptor this$0;
        IrField irFieldDeclareField;
        Object next;
        IrProperty next2;
        KtExpression delegateExpression = ktEntry.getDelegateExpression();
        Intrinsics.checkNotNull(delegateExpression);
        if (getContext().getConfiguration().getGenerateBodies()) {
            typeInferredByFrontend = GeneratorKt.getTypeInferredByFrontendOrFail(this, delegateExpression);
        } else {
            typeInferredByFrontend = GeneratorKt.getTypeInferredByFrontend(this, delegateExpression);
            if (typeInferredByFrontend != null && (constructor = typeInferredByFrontend.getConstructor()) != null && (this$0 = constructor.getThis$0()) != null && DescriptorUtils.isLocal(this$0)) {
                typeInferredByFrontend = null;
            }
            if (typeInferredByFrontend == null) {
                ErrorTypeKind errorTypeKind = ErrorTypeKind.UNRESOLVED_TYPE;
                String text = delegateExpression.getText();
                Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
                typeInferredByFrontend = ErrorUtils.createErrorType(errorTypeKind, text);
            }
        }
        ClassGenerator classGenerator = this;
        WritableSlice<KtTypeReference, KotlinType> TYPE = BindingContext.TYPE;
        Intrinsics.checkNotNullExpressionValue(TYPE, "TYPE");
        KtTypeReference typeReference = ktEntry.getTypeReference();
        Intrinsics.checkNotNull(typeReference);
        KotlinType kotlinType = (KotlinType) GeneratorKt.getOrFail(classGenerator, TYPE, typeReference);
        ClassifierDescriptor this$1 = kotlinType.getConstructor().getThis$0();
        ClassDescriptor classDescriptor = this$1 instanceof ClassDescriptor ? (ClassDescriptor) this$1 : null;
        if (classDescriptor == null) {
            throw new AssertionError("Unexpected supertype constructor for delegation: " + this$1);
        }
        PropertyDescriptor delegatePropertyIfAny = CodegenUtil.getDelegatePropertyIfAny(delegateExpression, irClass.getDescriptor(), getContext().getBindingContext());
        if (!CodegenUtil.isFinalPropertyWithBackingField(delegatePropertyIfAny, getContext().getBindingContext())) {
            ClassDescriptor descriptor = irClass.getDescriptor();
            Intrinsics.checkNotNull(kotlinType);
            IrImplementingDelegateDescriptorImpl irImplementingDelegateDescriptorImpl = new IrImplementingDelegateDescriptorImpl(descriptor, typeInferredByFrontend, kotlinType, delegateNumber);
            IrExpressionBody irExpressionBodyGenerateExpressionBody = getContext().getConfiguration().getGenerateBodies() ? DeclarationGeneratorKt.createBodyGenerator$default(classGenerator, irClass.getSymbol(), null, 2, null).generateExpressionBody(delegateExpression) : null;
            DescriptorSymbolTableExtension descriptorExtension = getContext().getSymbolTable().getDescriptorExtension();
            KtExpression ktExpression = delegateExpression;
            int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktExpression);
            int endOffset = PsiUtilsKt.getEndOffset(ktExpression);
            IrDeclarationOrigin.DELEGATE delegate = IrDeclarationOrigin.DELEGATE.INSTANCE;
            KotlinType type = irImplementingDelegateDescriptorImpl.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            irFieldDeclareField = descriptorExtension.declareField(startOffsetSkippingComments, endOffset, delegate, irImplementingDelegateDescriptorImpl, toIrType(type), irExpressionBodyGenerateExpressionBody);
            IrDeclarationsKt.addMember(irClass, irFieldDeclareField);
        } else {
            Iterator<IrProperty> it = IrUtilsKt.getProperties(irClass).iterator();
            do {
                if (!it.hasNext()) {
                    throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                }
                next2 = it.next();
            } while (!Intrinsics.areEqual(next2.getDescriptor(), delegatePropertyIfAny));
            irFieldDeclareField = next2.getBackingField();
            Intrinsics.checkNotNull(irFieldDeclareField);
        }
        Map<CallableMemberDescriptor, CallableMemberDescriptor> delegates = DelegationResolver.INSTANCE.getDelegates(irClass.getDescriptor(), classDescriptor, typeInferredByFrontend);
        for (CallableMemberDescriptor callableMemberDescriptor : delegates.keySet()) {
            Collection<? extends CallableMemberDescriptor> overriddenDescriptors = callableMemberDescriptor.getOverriddenDescriptors();
            Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
            Iterator<T> it2 = overriddenDescriptors.iterator();
            do {
                if (!it2.hasNext()) {
                    next = null;
                    break;
                }
                next = it2.next();
            } while (!Intrinsics.areEqual(((CallableMemberDescriptor) next).getContainingModule().getOriginal(), classDescriptor.getOriginal()));
            CallableMemberDescriptor callableMemberDescriptor2 = (CallableMemberDescriptor) next;
            if (callableMemberDescriptor2 != null) {
                CallableMemberDescriptor callableMemberDescriptor3 = delegates.get(callableMemberDescriptor);
                if (callableMemberDescriptor3 == null) {
                    throw new AssertionError("No corresponding member in delegate type " + typeInferredByFrontend + " for " + callableMemberDescriptor + " overriding " + callableMemberDescriptor2);
                }
                generateDelegatedMember(irClass, irFieldDeclareField, callableMemberDescriptor, callableMemberDescriptor3);
            }
        }
    }

    private final void generateDelegatedMember(IrClass irClass, IrField irDelegate, CallableMemberDescriptor delegatedMember, CallableMemberDescriptor delegateToMember) {
        if (delegatedMember instanceof FunctionDescriptor) {
            Intrinsics.checkNotNull(delegateToMember, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.FunctionDescriptor");
            generateDelegatedFunction(irClass, irDelegate, (FunctionDescriptor) delegatedMember, (FunctionDescriptor) delegateToMember);
        } else if (delegatedMember instanceof PropertyDescriptor) {
            Intrinsics.checkNotNull(delegateToMember, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.PropertyDescriptor");
            generateDelegatedProperty(irClass, irDelegate, (PropertyDescriptor) delegatedMember, (PropertyDescriptor) delegateToMember);
        }
    }

    private final void generateDelegatedProperty(IrClass irClass, IrField irDelegate, PropertyDescriptor delegatedDescriptor, PropertyDescriptor delegateToDescriptor) {
        IrDeclarationsKt.addMember(irClass, generateDelegatedProperty(irDelegate, delegatedDescriptor, delegateToDescriptor));
    }

    private final IrProperty generateDelegatedProperty(IrField irDelegate, PropertyDescriptor delegatedDescriptor, PropertyDescriptor delegateToDescriptor) {
        IrProperty irPropertyDeclareProperty = getContext().getSymbolTable().getDescriptorExtension().declareProperty(irDelegate.getStartOffset(), irDelegate.getEndOffset(), IrDeclarationOrigin.DELEGATED_MEMBER.INSTANCE, delegatedDescriptor, delegatedDescriptor.getIsDelegated());
        PropertyGetterDescriptor getter = delegatedDescriptor.getGetter();
        Intrinsics.checkNotNull(getter);
        PropertyGetterDescriptor getter2 = delegateToDescriptor.getGetter();
        Intrinsics.checkNotNull(getter2);
        irPropertyDeclareProperty.setGetter(generateDelegatedFunction(irDelegate, getter, getter2));
        if (delegatedDescriptor.isVar()) {
            PropertySetterDescriptor setter = delegatedDescriptor.getSetter();
            Intrinsics.checkNotNull(setter);
            PropertySetterDescriptor setter2 = delegateToDescriptor.getSetter();
            Intrinsics.checkNotNull(setter2);
            irPropertyDeclareProperty.setSetter(generateDelegatedFunction(irDelegate, setter, setter2));
        }
        generateOverrides(irPropertyDeclareProperty, delegatedDescriptor);
        PropertyGeneratorKt.linkCorrespondingPropertySymbol(irPropertyDeclareProperty);
        return irPropertyDeclareProperty;
    }

    private final void generateOverrides(IrProperty irProperty, PropertyDescriptor propertyDescriptor) {
        Collection<? extends PropertyDescriptor> overriddenDescriptors = propertyDescriptor.getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
        Collection<? extends PropertyDescriptor> collection = overriddenDescriptors;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        for (PropertyDescriptor propertyDescriptor2 : collection) {
            DescriptorSymbolTableExtension descriptorExtension = getContext().getSymbolTable().getDescriptorExtension();
            PropertyDescriptor original = propertyDescriptor2.getOriginal();
            Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
            arrayList.add(descriptorExtension.referenceProperty(original));
        }
        irProperty.setOverriddenSymbols(arrayList);
    }

    private final void generateDelegatedFunction(IrClass irClass, IrField irDelegate, FunctionDescriptor delegatedDescriptor, FunctionDescriptor delegateToDescriptor) {
        IrDeclarationsKt.addMember(irClass, generateDelegatedFunction(irDelegate, delegatedDescriptor, delegateToDescriptor));
    }

    private final IrSimpleFunction generateDelegatedFunction(IrField irDelegate, FunctionDescriptor delegatedDescriptor, FunctionDescriptor delegateToDescriptor) {
        IrSimpleFunction irSimpleFunctionDeclareSimpleFunctionWithOverrides = OverridesKt.declareSimpleFunctionWithOverrides(getContext().getSymbolTable(), -2, -2, IrDeclarationOrigin.DELEGATED_MEMBER.INSTANCE, delegatedDescriptor);
        SymbolTable symbolTable = getContext().getSymbolTable();
        symbolTable.enterScope(irSimpleFunctionDeclareSimpleFunctionWithOverrides);
        IrSimpleFunction irSimpleFunction = irSimpleFunctionDeclareSimpleFunctionWithOverrides;
        new FunctionGenerator(getDeclarationGenerator()).generateSyntheticFunctionParameterDeclarations(irSimpleFunction);
        KotlinType returnType = delegatedDescriptor.getReturnType();
        Intrinsics.checkNotNull(returnType);
        irSimpleFunction.setReturnType(toIrType(returnType));
        if (getContext().getConfiguration().getGenerateBodies()) {
            irSimpleFunction.setBody(generateDelegateFunctionBody(irDelegate, delegatedDescriptor, delegateToDescriptor, irSimpleFunction));
        }
        Unit unit = Unit.INSTANCE;
        symbolTable.leaveScope(irSimpleFunctionDeclareSimpleFunctionWithOverrides);
        return irSimpleFunction;
    }

    private final IrBlockBody generateDelegateFunctionBody(IrField irDelegate, FunctionDescriptor delegatedDescriptor, FunctionDescriptor delegateToDescriptor, IrSimpleFunction irDelegatedFunction) {
        IrBlockBody irBlockBodyCreateBlockBody = getContext().getIrFactory().createBlockBody(-2, -2);
        FunctionDescriptor functionDescriptor = (FunctionDescriptor) substituteDelegateToDescriptor(delegatedDescriptor, delegateToDescriptor);
        KotlinType returnType = functionDescriptor.getReturnType();
        Intrinsics.checkNotNull(returnType);
        DescriptorSymbolTableExtension descriptorExtension = getContext().getSymbolTable().getDescriptorExtension();
        FunctionDescriptor original = delegateToDescriptor.getOriginal();
        Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
        IrSimpleFunctionSymbol irSimpleFunctionSymbolReferenceSimpleFunction = descriptorExtension.referenceSimpleFunction(original);
        IrCallImpl irCallImplFromSymbolDescriptor = IrCallImpl.INSTANCE.fromSymbolDescriptor(-2, -2, toIrType(returnType), irSimpleFunctionSymbolReferenceSimpleFunction, (176 & 16) != 0 ? IrExpressionsKt.getTypeParametersCount(irSimpleFunctionSymbolReferenceSimpleFunction.getDescriptor()) : IrExpressionsKt.getTypeParametersCount(functionDescriptor), (176 & 32) != 0 ? irSimpleFunctionSymbolReferenceSimpleFunction.getDescriptor().getValueParameters().size() + irSimpleFunctionSymbolReferenceSimpleFunction.getDescriptor().getContextReceiverParameters().size() : 0, (176 & 64) != 0 ? null : null, (176 & 128) != 0 ? null : null);
        getContext().getCallToSubstitutedDescriptorMap$ir_psi2ir().put(irCallImplFromSymbolDescriptor, functionDescriptor);
        Map<TypeParameterDescriptor, KotlinType> typeArgumentsForOverriddenDescriptorDelegatingCall = getTypeArgumentsForOverriddenDescriptorDelegatingCall(delegatedDescriptor, delegateToDescriptor);
        IrCallImpl irCallImpl = irCallImplFromSymbolDescriptor;
        if (typeArgumentsForOverriddenDescriptorDelegatingCall != null) {
            for (Map.Entry<TypeParameterDescriptor, KotlinType> entry : typeArgumentsForOverriddenDescriptorDelegatingCall.entrySet()) {
                irCallImpl.putTypeArgument(entry.getKey().getIndex(), toIrType(entry.getValue()));
            }
        }
        IrValueParameter dispatchReceiverParameter = irDelegatedFunction.getDispatchReceiverParameter();
        Intrinsics.checkNotNull(dispatchReceiverParameter);
        irCallImplFromSymbolDescriptor.setDispatchReceiver(new IrGetFieldImpl(-2, -2, irDelegate.getSymbol(), irDelegate.getType(), new IrGetValueImpl(-2, -2, dispatchReceiverParameter.getType(), dispatchReceiverParameter.getSymbol(), null, 16, null), null, null, 96, null));
        IrValueParameter extensionReceiverParameter = irDelegatedFunction.getExtensionReceiverParameter();
        irCallImplFromSymbolDescriptor.setExtensionReceiver(extensionReceiverParameter != null ? new IrGetValueImpl(-2, -2, extensionReceiverParameter.getType(), extensionReceiverParameter.getSymbol(), null, 16, null) : null);
        List<ReceiverParameterDescriptor> contextReceiverParameters = delegatedDescriptor.getContextReceiverParameters();
        Intrinsics.checkNotNullExpressionValue(contextReceiverParameters, "getContextReceiverParameters(...)");
        List<ValueParameterDescriptor> valueParameters = delegatedDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        int i = 0;
        for (Object obj : CollectionsKt.plus((Collection) contextReceiverParameters, (Iterable) valueParameters)) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            ParameterDescriptor parameterDescriptor = (ParameterDescriptor) obj;
            Intrinsics.checkNotNull(parameterDescriptor);
            IrValueParameter irValueParameter = IrDeclarationsKt.getIrValueParameter(irDelegatedFunction, parameterDescriptor, i);
            irCallImplFromSymbolDescriptor.putValueArgument(i, new IrGetValueImpl(-2, -2, irValueParameter.getType(), irValueParameter.getSymbol(), null, 16, null));
            i = i2;
        }
        if (KotlinBuiltIns.isUnit(returnType) || KotlinBuiltIns.isNothing(returnType)) {
            irBlockBodyCreateBlockBody.getStatements().add(irCallImplFromSymbolDescriptor);
        } else {
            irBlockBodyCreateBlockBody.getStatements().add(new IrReturnImpl(-2, -2, getContext().getIrBuiltIns().getNothingType(), irDelegatedFunction.getSymbol(), irCallImplFromSymbolDescriptor));
        }
        return irBlockBodyCreateBlockBody;
    }

    private final <D extends CallableMemberDescriptor> D substituteDelegateToDescriptor(D delegated, D overridden) {
        if (overridden instanceof PropertyGetterDescriptor) {
            Intrinsics.checkNotNull(delegated, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor");
            PropertyGetterDescriptor getter = ((PropertyDescriptor) substituteDelegateToDescriptor(((PropertyGetterDescriptor) delegated).getCorrespondingProperty(), ((PropertyGetterDescriptor) overridden).getCorrespondingProperty())).getGetter();
            Intrinsics.checkNotNull(getter, "null cannot be cast to non-null type D of org.jetbrains.kotlin.psi2ir.generators.ClassGenerator.substituteDelegateToDescriptor");
            return getter;
        }
        if (overridden instanceof PropertySetterDescriptor) {
            Intrinsics.checkNotNull(delegated, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.PropertySetterDescriptor");
            PropertySetterDescriptor setter = ((PropertyDescriptor) substituteDelegateToDescriptor(((PropertySetterDescriptor) delegated).getCorrespondingProperty(), ((PropertySetterDescriptor) overridden).getCorrespondingProperty())).getSetter();
            Intrinsics.checkNotNull(setter, "null cannot be cast to non-null type D of org.jetbrains.kotlin.psi2ir.generators.ClassGenerator.substituteDelegateToDescriptor");
            return setter;
        }
        List<TypeParameterDescriptor> typeParameters = delegated.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        List<TypeParameterDescriptor> typeParameters2 = overridden.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters2, "getTypeParameters(...)");
        List<TypeParameterDescriptor> list = typeParameters2;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list, 10)), 16));
        for (TypeParameterDescriptor typeParameterDescriptor : list) {
            SimpleType defaultType = typeParameters.get(typeParameterDescriptor.getIndex()).getDefaultType();
            Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
            Pair pair = TuplesKt.to(typeParameterDescriptor.getTypeConstructor(), new TypeProjectionImpl(defaultType));
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        TypeSubstitutor typeSubstitutorCreate = TypeSubstitutor.create(linkedHashMap);
        Intrinsics.checkNotNullExpressionValue(typeSubstitutorCreate, "create(...)");
        DeclarationDescriptorNonRoot declarationDescriptorNonRootSubstitute = overridden.substitute(typeSubstitutorCreate);
        Intrinsics.checkNotNull(declarationDescriptorNonRootSubstitute);
        return (D) declarationDescriptorNonRootSubstitute;
    }

    private final Map<TypeParameterDescriptor, KotlinType> getTypeArgumentsForOverriddenDescriptorDelegatingCall(FunctionDescriptor delegatedDescriptor, FunctionDescriptor delegateToDescriptor) {
        List<TypeParameterDescriptor> typeParameters = DescriptorUtilsKt.getPropertyIfAccessor(delegateToDescriptor).getOriginal().getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        if (typeParameters.isEmpty()) {
            return null;
        }
        List<TypeParameterDescriptor> typeParameters2 = DescriptorUtilsKt.getPropertyIfAccessor(delegatedDescriptor).getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters2, "getTypeParameters(...)");
        HashMap mapNewHashMapWithExpectedSize = org.jetbrains.kotlin.utils.CollectionsKt.newHashMapWithExpectedSize(typeParameters.size());
        int i = 0;
        for (TypeParameterDescriptor typeParameterDescriptor : typeParameters) {
            int i2 = i + 1;
            Intrinsics.checkNotNull(typeParameterDescriptor);
            SimpleType defaultType = typeParameters2.get(i).getDefaultType();
            Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
            mapNewHashMapWithExpectedSize.put(typeParameterDescriptor, defaultType);
            i = i2;
        }
        return mapNewHashMapWithExpectedSize;
    }

    private final void generateAdditionalMembersForSingleFieldValueClasses(IrClass irClass, KtClassOrObject ktClassOrObject) {
        new DataClassMembersGenerator(getDeclarationGenerator(), getContext().getConfiguration().getGenerateBodies()).generateSingleFieldValueClassMembers(ktClassOrObject, irClass);
    }

    private final void generateAdditionalMembersForMultiFieldValueClasses(IrClass irClass, KtClassOrObject ktClassOrObject) {
        new DataClassMembersGenerator(getDeclarationGenerator(), getContext().getConfiguration().getGenerateBodies()).generateMultiFieldValueClassMembers(ktClassOrObject, irClass);
    }

    private final void generateAdditionalMembersForDataClass(IrClass irClass, KtClassOrObject ktClassOrObject) {
        new DataClassMembersGenerator(getDeclarationGenerator(), getContext().getConfiguration().getGenerateBodies()).generateDataClassMembers(ktClassOrObject, irClass);
    }

    private final void generateAdditionalMembersForEnumClass(IrClass irClass) {
        new EnumClassMembersGenerator(getDeclarationGenerator()).generateSpecialMembers(irClass);
    }

    private final void generateFieldsForContextReceivers(IrClass irClass, ClassDescriptor classDescriptor) {
        int i = 0;
        for (ReceiverParameterDescriptor receiverParameterDescriptor : classDescriptor.getContextReceivers()) {
            int i2 = i + 1;
            IrFactory irFactory = getContext().getIrFactory();
            IrDeclarationOrigin.FIELD_FOR_CLASS_CONTEXT_RECEIVER field_for_class_context_receiver = IrDeclarationOrigin.FIELD_FOR_CLASS_CONTEXT_RECEIVER.INSTANCE;
            Name nameIdentifier = Name.identifier("contextReceiverField" + i);
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            DescriptorVisibility PRIVATE = DescriptorVisibilities.PRIVATE;
            Intrinsics.checkNotNullExpressionValue(PRIVATE, "PRIVATE");
            IrFieldSymbolImpl irFieldSymbolImpl = new IrFieldSymbolImpl(null, 1, null);
            KotlinType type = receiverParameterDescriptor.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            IrField irFieldCreateField = irFactory.createField(-1, -1, (IrDeclarationOrigin) field_for_class_context_receiver, nameIdentifier, PRIVATE, (IrFieldSymbol) irFieldSymbolImpl, toIrType(type), true, false, false);
            DescriptorStorageForContextReceivers additionalDescriptorStorage = getContext().getAdditionalDescriptorStorage();
            ReceiverValue value = receiverParameterDescriptor.getValue();
            Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
            additionalDescriptorStorage.put(value, irFieldCreateField);
            IrDeclarationsKt.addMember(irClass, irFieldCreateField);
            i = i2;
        }
    }

    private final IrConstructor generatePrimaryConstructor(IrClass irClass, KtPureClassOrObject ktClassOrObject) {
        ClassConstructorDescriptor classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor = irClass.getDescriptor().mo6347getUnsubstitutedPrimaryConstructor();
        if (classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor == null) {
            return null;
        }
        IrConstructor irConstructorGeneratePrimaryConstructor = new FunctionGenerator(getDeclarationGenerator()).generatePrimaryConstructor(classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor, ktClassOrObject);
        IrDeclarationsKt.addMember(irClass, irConstructorGeneratePrimaryConstructor);
        return irConstructorGeneratePrimaryConstructor;
    }

    private final void generateDeclarationsForPrimaryConstructorParameters(IrClass irClass, IrConstructor irPrimaryConstructor, KtPureClassOrObject ktClassOrObject) {
        KtPrimaryConstructor primaryConstructor = ktClassOrObject.getPrimaryConstructor();
        if (primaryConstructor != null) {
            Iterator<T> it = irPrimaryConstructor.getValueParameters().iterator();
            while (it.hasNext()) {
                getContext().getSymbolTable().getDescriptorExtension().introduceValueParameter((IrValueParameter) it.next());
            }
            int i = 0;
            for (Object obj : primaryConstructor.getValueParameters()) {
                int i2 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                KtParameter ktParameter = (KtParameter) obj;
                IrValueParameter irValueParameter = irPrimaryConstructor.getValueParameters().get(i + ktClassOrObject.getContextReceivers().size());
                if (ktParameter.hasValOrVar()) {
                    PropertyGenerator propertyGenerator = new PropertyGenerator(getDeclarationGenerator());
                    Intrinsics.checkNotNull(ktParameter);
                    IrDeclarationsKt.addMember(irClass, propertyGenerator.generatePropertyForPrimaryConstructorParameter(ktParameter, irValueParameter));
                }
                i = i2;
            }
        }
    }

    private final void generateMembersDeclaredInClassBody(IrClass irClass, KtPureClassOrObject ktClassOrObject) {
        KtClassBody body = ktClassOrObject.getBody();
        if (body != null) {
            List<KtDeclaration> declarations = body.getDeclarations();
            List<IrDeclaration> declarations2 = irClass.getDeclarations();
            for (KtDeclaration ktDeclaration : declarations) {
                DeclarationGenerator declarationGenerator = getDeclarationGenerator();
                Intrinsics.checkNotNull(ktDeclaration);
                IrDeclaration irDeclarationGenerateClassMemberDeclaration = declarationGenerator.generateClassMemberDeclaration(ktDeclaration, irClass, ktClassOrObject);
                if (irDeclarationGenerateClassMemberDeclaration != null) {
                    declarations2.add(irDeclarationGenerateClassMemberDeclaration);
                }
            }
        }
        Sequence sequenceFilter = SequencesKt.filter(CollectionsKt.asSequence(irClass.getDescriptor().getUnsubstitutedMemberScope().getContributedDescriptors(DescriptorKindFilter.CLASSIFIERS, MemberScope.INSTANCE.getALL_NAME_FILTER())), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.psi2ir.generators.ClassGenerator$generateMembersDeclaredInClassBody$$inlined$filterIsInstance$1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                return Boolean.valueOf(obj instanceof SyntheticClassOrObjectDescriptor);
            }
        });
        Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
        List<IrDeclaration> declarations3 = irClass.getDeclarations();
        Iterator it = sequenceFilter.iterator();
        while (it.hasNext()) {
            declarations3.add(getDeclarationGenerator().generateSyntheticClassOrObject(((SyntheticClassOrObjectDescriptor) it.next()).getSyntheticDeclaration()));
        }
    }

    public final IrEnumEntry generateEnumEntry(KtEnumEntry ktEnumEntry) {
        Intrinsics.checkNotNullParameter(ktEnumEntry, "ktEnumEntry");
        ClassGenerator classGenerator = this;
        WritableSlice<PsiElement, ClassDescriptor> CLASS = BindingContext.CLASS;
        Intrinsics.checkNotNullExpressionValue(CLASS, "CLASS");
        ClassDescriptor classDescriptor = (ClassDescriptor) GeneratorKt.getOrFail(classGenerator, CLASS, ktEnumEntry);
        DeclarationDescriptor containingDeclaration = classDescriptor.getContainingModule();
        Intrinsics.checkNotNull(containingDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        IrClass owner = getContext().getSymbolTable().getDescriptorExtension().referenceClass((ClassDescriptor) containingDeclaration).getOwner();
        DescriptorSymbolTableExtension descriptorExtension = getContext().getSymbolTable().getDescriptorExtension();
        KtEnumEntry ktEnumEntry2 = ktEnumEntry;
        int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktEnumEntry2);
        int endOffset = PsiUtilsKt.getEndOffset(ktEnumEntry2);
        IrDeclarationOrigin.DEFINED defined = IrDeclarationOrigin.DEFINED.INSTANCE;
        Intrinsics.checkNotNull(classDescriptor);
        IrEnumEntry irEnumEntryDeclareEnumEntry = descriptorExtension.declareEnumEntry(startOffsetSkippingComments, endOffset, defined, classDescriptor);
        SymbolTable symbolTable = getContext().getSymbolTable();
        symbolTable.enterScope(irEnumEntryDeclareEnumEntry);
        IrEnumEntry irEnumEntry = irEnumEntryDeclareEnumEntry;
        irEnumEntry.setParent(owner);
        if (!classDescriptor.get$isExpect() && getContext().getConfiguration().getGenerateBodies()) {
            irEnumEntry.setInitializerExpression(IrFactoryHelpersKt.createExpressionBody(getContext().getIrFactory(), DeclarationGeneratorKt.createBodyGenerator$default(classGenerator, irEnumEntry.getSymbol(), null, 2, null).generateEnumEntryInitializer(ktEnumEntry, classDescriptor)));
        }
        if (hasMemberDeclarations(ktEnumEntry)) {
            irEnumEntry.setCorrespondingClass(generateClass(ktEnumEntry, DescriptorVisibilities.PRIVATE));
        }
        Unit unit = Unit.INSTANCE;
        symbolTable.leaveScope(irEnumEntryDeclareEnumEntry);
        return irEnumEntry;
    }
}
