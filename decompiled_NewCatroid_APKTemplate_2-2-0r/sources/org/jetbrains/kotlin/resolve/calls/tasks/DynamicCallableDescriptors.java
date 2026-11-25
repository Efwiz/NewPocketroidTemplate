package org.jetbrains.kotlin.resolve.calls.tasks;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.ranges.IntRange;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.com.google.common.collect.ImmutableBiMap;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.PropertyDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.PropertyGetterDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.PropertySetterDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.ReceiverParameterDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.SimpleFunctionDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.TypeParameterDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.ValueParameterDescriptorImpl;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.lexer.KtSingleValueToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtContextReceiver;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFunctionLiteral;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.KtOperationReferenceExpression;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtTypeProjection;
import org.jetbrains.kotlin.psi.LambdaArgument;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.psi.ValueArgumentName;
import org.jetbrains.kotlin.resolve.DescriptorFactory;
import org.jetbrains.kotlin.resolve.calls.util.CallResolverUtilKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl;
import org.jetbrains.kotlin.resolve.scopes.receivers.TransientReceiver;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.storage.StorageKt;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.DynamicType;
import org.jetbrains.kotlin.types.DynamicTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.expressions.OperatorConventions;
import org.jetbrains.kotlin.utils.Printer;

/* JADX INFO: compiled from: dynamicCalls.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0016\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0011\u001a\u00020\u0015H\u0002J \u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J \u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u001e\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001d2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u001e\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020 0\u001d2\u0006\u0010\u0011\u001a\u00020!2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002R\u001b\u0010\u0007\u001a\u00020\b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tasks/DynamicCallableDescriptors;", "", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "(Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;)V", "dynamicType", "Lorg/jetbrains/kotlin/types/DynamicType;", "getDynamicType", "()Lorg/jetbrains/kotlin/types/DynamicType;", "dynamicType$delegate", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "createDynamicDescriptorScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScopeImpl;", "call", "Lorg/jetbrains/kotlin/psi/Call;", "owner", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "createDynamicDispatchReceiverParameter", "Lorg/jetbrains/kotlin/descriptors/impl/ReceiverParameterDescriptorImpl;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "createDynamicFunction", "Lorg/jetbrains/kotlin/descriptors/impl/SimpleFunctionDescriptorImpl;", "name", "Lorg/jetbrains/kotlin/name/Name;", "createDynamicProperty", "Lorg/jetbrains/kotlin/descriptors/impl/PropertyDescriptorImpl;", "createTypeParameters", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "createValueParameters", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DynamicCallableDescriptors {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(DynamicCallableDescriptors.class), "dynamicType", "getDynamicType()Lorg/jetbrains/kotlin/types/DynamicType;"))};

    /* JADX INFO: renamed from: dynamicType$delegate, reason: from kotlin metadata */
    private final NotNullLazyValue dynamicType;
    private final StorageManager storageManager;

    public DynamicCallableDescriptors(StorageManager storageManager, final KotlinBuiltIns builtIns) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        this.storageManager = storageManager;
        this.dynamicType = storageManager.createLazyValue(new Function0<DynamicType>() { // from class: org.jetbrains.kotlin.resolve.calls.tasks.DynamicCallableDescriptors$dynamicType$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final DynamicType invoke() {
                return DynamicTypesKt.createDynamicType(builtIns);
            }
        });
    }

    public final DynamicType getDynamicType() {
        return (DynamicType) StorageKt.getValue(this.dynamicType, this, (KProperty<?>) $$delegatedProperties[0]);
    }

    public final MemberScopeImpl createDynamicDescriptorScope(final Call call, final DeclarationDescriptor owner) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(owner, "owner");
        return new MemberScopeImpl() { // from class: org.jetbrains.kotlin.resolve.calls.tasks.DynamicCallableDescriptors.createDynamicDescriptorScope.1
            @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope
            /* JADX INFO: renamed from: printScopeStructure */
            public void mo6105printScopeStructure(Printer p) {
                Intrinsics.checkNotNullParameter(p, "p");
                p.println(getClass().getSimpleName(), ": dynamic candidates for " + call);
            }

            @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
            public Collection<SimpleFunctionDescriptor> getContributedFunctions(Name name2, LookupLocation location) {
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(location, "location");
                if (isAugmentedAssignmentConvention(name2)) {
                    return CollectionsKt.emptyList();
                }
                if (call.getCallType() != Call.CallType.INVOKE || call.getValueArgumentList() != null || !call.getFunctionLiteralArguments().isEmpty()) {
                    return CollectionsKt.listOf(this.createDynamicFunction(owner, name2, call));
                }
                return CollectionsKt.emptyList();
            }

            private final boolean isAugmentedAssignmentConvention(Name name2) {
                KtExpression calleeExpression = call.getCalleeExpression();
                if (!(calleeExpression instanceof KtOperationReferenceExpression)) {
                    return false;
                }
                IElementType referencedNameElementType = ((KtOperationReferenceExpression) calleeExpression).getReferencedNameElementType();
                if (!KtTokens.AUGMENTED_ASSIGNMENTS.contains(referencedNameElementType)) {
                    return false;
                }
                ImmutableBiMap<KtSingleValueToken, Name> ASSIGNMENT_OPERATIONS = OperatorConventions.ASSIGNMENT_OPERATIONS;
                Intrinsics.checkNotNullExpressionValue(ASSIGNMENT_OPERATIONS, "ASSIGNMENT_OPERATIONS");
                return !Intrinsics.areEqual(ASSIGNMENT_OPERATIONS.get(referencedNameElementType), name2);
            }

            @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
            public Collection<PropertyDescriptor> getContributedVariables(Name name2, LookupLocation location) {
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(location, "location");
                if (call.getValueArgumentList() == null && call.getValueArguments().isEmpty()) {
                    return CollectionsKt.listOf(this.createDynamicProperty(owner, name2, call));
                }
                return CollectionsKt.emptyList();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final PropertyDescriptorImpl createDynamicProperty(DeclarationDescriptor owner, Name name2, Call call) {
        PropertyDescriptorImpl propertyDescriptorImplCreate = PropertyDescriptorImpl.create(owner, Annotations.INSTANCE.getEMPTY(), Modality.FINAL, DescriptorVisibilities.PUBLIC, true, name2, CallableMemberDescriptor.Kind.DECLARATION, SourceElement.NO_SOURCE, false, false, false, false, false, false);
        Intrinsics.checkNotNullExpressionValue(propertyDescriptorImplCreate, "create(...)");
        propertyDescriptorImplCreate.setType(getDynamicType(), createTypeParameters(propertyDescriptorImplCreate, call), createDynamicDispatchReceiverParameter(propertyDescriptorImplCreate), null, CollectionsKt.emptyList());
        PropertyDescriptorImpl propertyDescriptorImpl = propertyDescriptorImplCreate;
        PropertyGetterDescriptorImpl propertyGetterDescriptorImplCreateDefaultGetter = DescriptorFactory.createDefaultGetter(propertyDescriptorImpl, Annotations.INSTANCE.getEMPTY());
        Intrinsics.checkNotNullExpressionValue(propertyGetterDescriptorImplCreateDefaultGetter, "createDefaultGetter(...)");
        propertyGetterDescriptorImplCreateDefaultGetter.initialize(propertyDescriptorImplCreate.getType());
        PropertySetterDescriptorImpl propertySetterDescriptorImplCreateDefaultSetter = DescriptorFactory.createDefaultSetter(propertyDescriptorImpl, Annotations.INSTANCE.getEMPTY(), Annotations.INSTANCE.getEMPTY());
        Intrinsics.checkNotNullExpressionValue(propertySetterDescriptorImplCreateDefaultSetter, "createDefaultSetter(...)");
        propertyDescriptorImplCreate.initialize(propertyGetterDescriptorImplCreateDefaultGetter, propertySetterDescriptorImplCreateDefaultSetter);
        return propertyDescriptorImplCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final SimpleFunctionDescriptorImpl createDynamicFunction(DeclarationDescriptor owner, Name name2, Call call) {
        SimpleFunctionDescriptorImpl simpleFunctionDescriptorImplCreate = SimpleFunctionDescriptorImpl.create(owner, Annotations.INSTANCE.getEMPTY(), name2, CallableMemberDescriptor.Kind.DECLARATION, SourceElement.NO_SOURCE);
        Intrinsics.checkNotNullExpressionValue(simpleFunctionDescriptorImplCreate, "create(...)");
        simpleFunctionDescriptorImplCreate.initialize((ReceiverParameterDescriptor) null, (ReceiverParameterDescriptor) createDynamicDispatchReceiverParameter(simpleFunctionDescriptorImplCreate), CollectionsKt.emptyList(), (List<? extends TypeParameterDescriptor>) createTypeParameters(simpleFunctionDescriptorImplCreate, call), createValueParameters(simpleFunctionDescriptorImplCreate, call), (KotlinType) getDynamicType(), Modality.FINAL, DescriptorVisibilities.PUBLIC);
        simpleFunctionDescriptorImplCreate.setHasSynthesizedParameterNames(true);
        simpleFunctionDescriptorImplCreate.setOperator(CallResolverUtilKt.isConventionCall(call));
        return simpleFunctionDescriptorImplCreate;
    }

    private final ReceiverParameterDescriptorImpl createDynamicDispatchReceiverParameter(CallableDescriptor owner) {
        return new ReceiverParameterDescriptorImpl(owner, new TransientReceiver(getDynamicType()), Annotations.INSTANCE.getEMPTY());
    }

    private final List<TypeParameterDescriptor> createTypeParameters(DeclarationDescriptor owner, Call call) {
        List<KtTypeProjection> typeArguments = call.getTypeArguments();
        Intrinsics.checkNotNullExpressionValue(typeArguments, "getTypeArguments(...)");
        IntRange indices = CollectionsKt.getIndices(typeArguments);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(indices, 10));
        Iterator<Integer> it = indices.iterator();
        while (it.hasNext()) {
            int iNextInt = ((IntIterator) it).nextInt();
            arrayList.add(TypeParameterDescriptorImpl.createWithDefaultBound(owner, Annotations.INSTANCE.getEMPTY(), false, Variance.INVARIANT, Name.identifier(ExifInterface.GPS_DIRECTION_TRUE + iNextInt), iNextInt, this.storageManager));
        }
        return arrayList;
    }

    /* JADX WARN: Code duplicated, block: B:18:0x0074  */
    /* JADX WARN: Code duplicated, block: B:22:0x008c  */
    /* JADX WARN: Code duplicated, block: B:27:0x0066 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:28:? A[LOOP:0: B:3:0x000d->B:28:?, LOOP_END, SYNTHETIC] */
    private final List<ValueParameterDescriptor> createValueParameters(FunctionDescriptor owner, Call call) {
        DynamicType dynamicType;
        DynamicType dynamicType2;
        KtLambdaExpression lambdaExpression;
        SimpleType simpleTypeCreateValueParameters$getFunctionType;
        ArrayList arrayList = new ArrayList();
        for (ValueArgument valueArgument : call.getValueArguments()) {
            KtExpression ktExpressionDeparenthesize = KtPsiUtil.deparenthesize(valueArgument.getArgumentExpression());
            boolean z = false;
            if (ktExpressionDeparenthesize instanceof KtLambdaExpression) {
                dynamicType = createValueParameters$getFunctionType(owner, this, (KtLambdaExpression) ktExpressionDeparenthesize);
            } else {
                if (valueArgument.getSpreadElement() != null) {
                    SimpleType arrayType = DescriptorUtilsKt.getBuiltIns(owner).getArrayType(Variance.OUT_VARIANCE, getDynamicType());
                    Intrinsics.checkNotNullExpressionValue(arrayType, "getArrayType(...)");
                    dynamicType = arrayType;
                    dynamicType2 = getDynamicType();
                    z = true;
                } else {
                    dynamicType = getDynamicType();
                }
                Intrinsics.checkNotNull(valueArgument);
                createValueParameters$addParameter(arrayList, owner, valueArgument, dynamicType, dynamicType2);
                if (z) {
                    for (LambdaArgument lambdaArgument : call.getFunctionLiteralArguments()) {
                        Intrinsics.checkNotNull(lambdaArgument);
                        LambdaArgument lambdaArgument2 = lambdaArgument;
                        lambdaExpression = lambdaArgument.getLambdaExpression();
                        if (lambdaExpression != null || (simpleTypeCreateValueParameters$getFunctionType = createValueParameters$getFunctionType(owner, this, lambdaExpression)) == null) {
                            simpleTypeCreateValueParameters$getFunctionType = TypeUtils.CANNOT_INFER_FUNCTION_PARAM_TYPE;
                        }
                        Intrinsics.checkNotNull(simpleTypeCreateValueParameters$getFunctionType);
                        createValueParameters$addParameter(arrayList, owner, lambdaArgument2, simpleTypeCreateValueParameters$getFunctionType, null);
                    }
                    break;
                }
            }
            dynamicType2 = null;
            Intrinsics.checkNotNull(valueArgument);
            createValueParameters$addParameter(arrayList, owner, valueArgument, dynamicType, dynamicType2);
            if (z) {
                while (r9.hasNext()) {
                    Intrinsics.checkNotNull(lambdaArgument);
                    LambdaArgument lambdaArgument3 = lambdaArgument;
                    lambdaExpression = lambdaArgument.getLambdaExpression();
                    if (lambdaExpression != null) {
                        simpleTypeCreateValueParameters$getFunctionType = TypeUtils.CANNOT_INFER_FUNCTION_PARAM_TYPE;
                    } else {
                        simpleTypeCreateValueParameters$getFunctionType = TypeUtils.CANNOT_INFER_FUNCTION_PARAM_TYPE;
                    }
                    Intrinsics.checkNotNull(simpleTypeCreateValueParameters$getFunctionType);
                    createValueParameters$addParameter(arrayList, owner, lambdaArgument3, simpleTypeCreateValueParameters$getFunctionType, null);
                }
                break;
                break;
            }
        }
        return arrayList;
    }

    private static final void createValueParameters$addParameter(ArrayList<ValueParameterDescriptor> arrayList, FunctionDescriptor functionDescriptor, ValueArgument valueArgument, KotlinType kotlinType, KotlinType kotlinType2) {
        Name nameIdentifier;
        int size = arrayList.size();
        FunctionDescriptor functionDescriptor2 = functionDescriptor;
        Annotations empty = Annotations.INSTANCE.getEMPTY();
        ValueArgumentName argumentName = valueArgument.getArgumentName();
        if (argumentName == null || (nameIdentifier = argumentName.getAsName()) == null) {
            nameIdentifier = Name.identifier("p" + size);
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        }
        SourceElement NO_SOURCE = SourceElement.NO_SOURCE;
        Intrinsics.checkNotNullExpressionValue(NO_SOURCE, "NO_SOURCE");
        arrayList.add(new ValueParameterDescriptorImpl(functionDescriptor2, null, size, empty, nameIdentifier, kotlinType, false, false, false, kotlinType2, NO_SOURCE));
    }

    private static final KotlinType createValueParameters$getFunctionType(FunctionDescriptor functionDescriptor, DynamicCallableDescriptors dynamicCallableDescriptors, KtLambdaExpression ktLambdaExpression) {
        KtFunctionLiteral functionLiteral = ktLambdaExpression.getFunctionLiteral();
        Intrinsics.checkNotNullExpressionValue(functionLiteral, "getFunctionLiteral(...)");
        DynamicType dynamicType = functionLiteral.mo7393getReceiverTypeReference() != null ? dynamicCallableDescriptors.getDynamicType() : null;
        List<KtContextReceiver> contextReceivers = functionLiteral.getContextReceivers();
        Intrinsics.checkNotNullExpressionValue(contextReceivers, "getContextReceivers(...)");
        List<KtContextReceiver> list = contextReceivers;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (KtContextReceiver ktContextReceiver : list) {
            arrayList.add(dynamicCallableDescriptors.getDynamicType());
        }
        ArrayList arrayList2 = arrayList;
        List<KtParameter> valueParameters = functionLiteral.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        List<KtParameter> list2 = valueParameters;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        for (KtParameter ktParameter : list2) {
            arrayList3.add(dynamicCallableDescriptors.getDynamicType());
        }
        return FunctionTypesKt.createFunctionType$default(DescriptorUtilsKt.getBuiltIns(functionDescriptor), Annotations.INSTANCE.getEMPTY(), dynamicType, arrayList2, arrayList3, null, dynamicCallableDescriptors.getDynamicType(), false, 128, null);
    }
}
