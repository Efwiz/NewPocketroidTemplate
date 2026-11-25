package org.jetbrains.kotlin.backend.jvm.lower;

import com.android.apksig.internal.apk.AndroidBinXmlParser;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LocalDeclarationsLoweringKt;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilder;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilderKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFieldBuilder;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrFieldAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrPropertyReference;
import org.jetbrains.kotlin.ir.expressions.impl.IrCompositeImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrPropertyReferenceImpl;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrAnonymousInitializerSymbolImpl;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.util.TransformKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: PropertyReferenceDelegationLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0010H\u0016J\u001e\u0010\u0011\u001a\u00020\u0012*\u00020\t2\u0006\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\fH\u0002J\u001a\u0010\u0016\u001a\u00020\b*\u00020\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0019H\u0002J\u0014\u0010\u001b\u001a\n\u0012\u0004\u0012\u00020\u001d\u0018\u00010\u001c*\u00020\u001eH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0018\u0010\u0007\u001a\u00020\b*\u00020\t8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\n¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/PropertyReferenceDelegationTransformer;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "isFinalDefaultValGetter", "", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)Z", "visitClass", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "visitLocalDelegatedProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrLocalDelegatedProperty;", "accessorBody", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/kotlin/ir/expressions/IrPropertyReference;", "receiverFieldOrExpression", "canInline", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visibleScopes", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "transform", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class PropertyReferenceDelegationTransformer extends IrElementTransformerVoid {
    private final JvmBackendContext context;

    public PropertyReferenceDelegationTransformer(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final JvmBackendContext getContext() {
        return this.context;
    }

    private final IrExpressionBody accessorBody(IrSimpleFunction irSimpleFunction, IrPropertyReference irPropertyReference, IrStatement irStatement) {
        IrGetValueImpl irGetValueImplIrGet;
        IrGetValueImpl irGetValueImplIrGet2;
        IrExpression irExpression;
        IrFieldAccessExpression irFieldAccessExpressionIrGetField$default;
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(this.context, irSimpleFunction.getSymbol(), irSimpleFunction.getStartOffset(), irSimpleFunction.getEndOffset());
        IrValueParameter irValueParameter = (IrValueParameter) CollectionsKt.singleOrNull((List) irSimpleFunction.getValueParameters());
        IrGetValueImpl irGetValueImplIrGet3 = null;
        if (irValueParameter != null) {
            irGetValueImplIrGet = ExpressionHelpersKt.irGet(declarationIrBuilderCreateIrBuilder, irValueParameter);
        } else {
            irGetValueImplIrGet = null;
        }
        if (irStatement == null) {
            irGetValueImplIrGet2 = null;
        } else if (irStatement instanceof IrField) {
            DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder;
            IrValueParameter dispatchReceiverParameter = irSimpleFunction.getDispatchReceiverParameter();
            irGetValueImplIrGet2 = ExpressionHelpersKt.irGetField$default(declarationIrBuilder, dispatchReceiverParameter != null ? ExpressionHelpersKt.irGet(declarationIrBuilder, dispatchReceiverParameter) : null, (IrField) irStatement, null, 4, null);
        } else if (irStatement instanceof IrValueDeclaration) {
            irGetValueImplIrGet2 = ExpressionHelpersKt.irGet(declarationIrBuilderCreateIrBuilder, (IrValueDeclaration) irStatement);
        } else {
            if (!(irStatement instanceof IrExpression)) {
                throw new AssertionError("not a field/variable/expression: " + RenderIrElementKt.render$default(irStatement, (DumpIrTreeOptions) null, 1, (Object) null));
            }
            irGetValueImplIrGet2 = (IrExpression) irStatement;
        }
        IrValueParameter extensionReceiverParameter = irSimpleFunction.getExtensionReceiverParameter();
        if (extensionReceiverParameter == null) {
            extensionReceiverParameter = irSimpleFunction.getDispatchReceiverParameter();
        }
        IrFieldSymbol field = irPropertyReference.getField();
        IrField owner = field != null ? field.getOwner() : null;
        if (owner == null) {
            IrSimpleFunctionSymbol getter = irGetValueImplIrGet == null ? irPropertyReference.getGetter() : irPropertyReference.getSetter();
            Intrinsics.checkNotNull(getter);
            DeclarationIrBuilder declarationIrBuilder2 = declarationIrBuilderCreateIrBuilder;
            IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilder2, getter);
            if (getter.getOwner().getDispatchReceiverParameter() != null) {
                if (irGetValueImplIrGet2 == null) {
                    Intrinsics.checkNotNull(extensionReceiverParameter);
                    irGetValueImplIrGet2 = ExpressionHelpersKt.irGet(declarationIrBuilder2, extensionReceiverParameter);
                }
                irCall.setDispatchReceiver(irGetValueImplIrGet2);
            } else {
                irGetValueImplIrGet3 = irGetValueImplIrGet2;
            }
            if (getter.getOwner().getExtensionReceiverParameter() != null) {
                if (irGetValueImplIrGet3 == null) {
                    Intrinsics.checkNotNull(extensionReceiverParameter);
                    irGetValueImplIrGet3 = ExpressionHelpersKt.irGet(declarationIrBuilder2, extensionReceiverParameter);
                }
                irCall.setExtensionReceiver(irGetValueImplIrGet3);
            }
            if (irGetValueImplIrGet != null) {
                irCall.putValueArgument(0, irGetValueImplIrGet);
            }
            irFieldAccessExpressionIrGetField$default = irCall;
        } else {
            if (owner.getIsStatic()) {
                irExpression = irGetValueImplIrGet3;
            } else if (irGetValueImplIrGet2 == null) {
                Intrinsics.checkNotNull(extensionReceiverParameter);
                irGetValueImplIrGet3 = ExpressionHelpersKt.irGet(declarationIrBuilderCreateIrBuilder, extensionReceiverParameter);
                irExpression = irGetValueImplIrGet3;
            } else {
                irExpression = irGetValueImplIrGet2;
            }
            DeclarationIrBuilder declarationIrBuilder3 = declarationIrBuilderCreateIrBuilder;
            irFieldAccessExpressionIrGetField$default = irGetValueImplIrGet == null ? ExpressionHelpersKt.irGetField$default(declarationIrBuilder3, irExpression, owner, null, 4, null) : ExpressionHelpersKt.irSetField$default(declarationIrBuilder3, irExpression, owner, irGetValueImplIrGet, null, 8, null);
        }
        return ExpressionHelpersKt.irExprBody(declarationIrBuilderCreateIrBuilder, irFieldAccessExpressionIrGetField$default);
    }

    private final boolean canInline(IrExpression irExpression, Set<? extends IrDeclarationParent> set) {
        IrFile fileParentOrNull;
        IrExpression dispatchReceiver;
        IrExpression extensionReceiver;
        IrFile fileParentOrNull2;
        IrExpression receiver;
        if (irExpression instanceof IrGetValue) {
            IrValueDeclaration owner = ((IrGetValue) irExpression).getSymbol().getOwner();
            if ((!(owner instanceof IrVariable) || !((IrVariable) owner).getIsVar()) && set.contains(owner.getParent())) {
                return true;
            }
        } else if (irExpression instanceof IrGetField) {
            IrGetField irGetField = (IrGetField) irExpression;
            IrField owner2 = irGetField.getSymbol().getOwner();
            if (owner2.getIsFinal() && (fileParentOrNull2 = JvmIrUtilsKt.getFileParentOrNull(owner2)) != null && set.contains(fileParentOrNull2) && ((receiver = irGetField.getReceiver()) == null || canInline(receiver, set))) {
                return true;
            }
        } else if (irExpression instanceof IrCall) {
            IrCall irCall = (IrCall) irExpression;
            IrSimpleFunction owner3 = irCall.getSymbol().getOwner();
            if (isFinalDefaultValGetter(owner3) && (fileParentOrNull = JvmIrUtilsKt.getFileParentOrNull(owner3)) != null && set.contains(fileParentOrNull) && (((dispatchReceiver = irCall.getDispatchReceiver()) == null || canInline(dispatchReceiver, set)) && ((extensionReceiver = irCall.getExtensionReceiver()) == null || canInline(extensionReceiver, set)))) {
                return true;
            }
        } else {
            return IrUtilsKt.isTrivial(irExpression);
        }
        return false;
    }

    private final boolean isFinalDefaultValGetter(IrSimpleFunction irSimpleFunction) {
        IrPropertySymbol correspondingPropertySymbol;
        return Intrinsics.areEqual(irSimpleFunction.getOrigin(), IrDeclarationOrigin.DEFAULT_PROPERTY_ACCESSOR.INSTANCE) && (correspondingPropertySymbol = irSimpleFunction.getCorrespondingPropertySymbol()) != null && Intrinsics.areEqual(correspondingPropertySymbol.getOwner().getGetter(), irSimpleFunction) && correspondingPropertySymbol.getOwner().getSetter() == null && irSimpleFunction.getModality() == Modality.FINAL;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrStatement visitClass(IrClass declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        declaration.transformChildren(this, null);
        TransformKt.transformDeclarationsFlat(declaration, new Function1<IrDeclaration, List<? extends IrDeclaration>>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.PropertyReferenceDelegationTransformer.visitClass.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final List<IrDeclaration> invoke(IrDeclaration it) {
                Intrinsics.checkNotNullParameter(it, "it");
                IrProperty irProperty = it instanceof IrProperty ? (IrProperty) it : null;
                if (irProperty != null) {
                    return PropertyReferenceDelegationTransformer.this.transform(irProperty);
                }
                return null;
            }
        });
        return declaration;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:21:0x0098  */
    /* JADX WARN: Code duplicated, block: B:49:0x0132 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:50:0x0134  */
    /* JADX WARN: Code duplicated, block: B:51:0x013d  */
    public final List<IrDeclaration> transform(IrProperty irProperty) {
        IrField backingField;
        IrField irFieldBuildField;
        IrGetFieldImpl irGetFieldImplRemapReceiver;
        IrAnonymousInitializer irAnonymousInitializer;
        IrPropertyReference propertyReferenceForOptimizableDelegatedProperty = JvmIrLowerUtilsKt.getPropertyReferenceForOptimizableDelegatedProperty(irProperty);
        if (propertyReferenceForOptimizableDelegatedProperty == null || (backingField = irProperty.getBackingField()) == null) {
            return null;
        }
        IrExpression dispatchReceiver = propertyReferenceForOptimizableDelegatedProperty.getDispatchReceiver();
        if (dispatchReceiver == null) {
            dispatchReceiver = propertyReferenceForOptimizableDelegatedProperty.getExtensionReceiver();
        }
        IrExpression irExpressionTransform = dispatchReceiver != null ? dispatchReceiver.transform((IrElementTransformer<? super Object>) this, (Object) null) : null;
        if (irExpressionTransform == null) {
            irFieldBuildField = null;
        } else {
            IrExpression irExpression = !canInline(irExpressionTransform, SequencesKt.toSet(LocalDeclarationsLoweringKt.getParents(irProperty))) ? irExpressionTransform : null;
            if (irExpression != null) {
                IrFactory irFactory = this.context.getIrFactory();
                IrFieldBuilder irFieldBuilder = new IrFieldBuilder();
                irFieldBuilder.updateFrom(backingField);
                Name nameIdentifier = Name.identifier(irProperty.getName() + AsmUtil.RECEIVER_PARAMETER_NAME);
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                irFieldBuilder.setName(nameIdentifier);
                irFieldBuilder.setType(irExpressionTransform.getType());
                irFieldBuildField = DeclarationBuildersKt.buildField(irFactory, irFieldBuilder);
                irFieldBuildField.setParent(backingField.getParent());
                irFieldBuildField.setInitializer(IrFactoryHelpersKt.createExpressionBody(this.context.getIrFactory(), irExpression));
                irFieldBuildField.setCorrespondingPropertySymbol(backingField.getCorrespondingPropertySymbol());
            } else {
                irFieldBuildField = null;
            }
        }
        irProperty.setBackingField(irFieldBuildField);
        IrProperty irProperty2 = irProperty;
        IrValueParameter thisReceiver = IrUtilsKt.getParentAsClass(irProperty2).getThisReceiver();
        IrSimpleFunction getter = irProperty.getGetter();
        if (getter != null) {
            IrElement backingField2 = irProperty.getBackingField();
            if (backingField2 == null) {
                backingField2 = irExpressionTransform != null ? IrUtilsKt.remapReceiver(irExpressionTransform, thisReceiver, getter.getDispatchReceiverParameter()) : null;
            }
            getter.setBody(accessorBody(getter, propertyReferenceForOptimizableDelegatedProperty, (IrStatement) backingField2));
        }
        IrSimpleFunction setter = irProperty.getSetter();
        if (setter != null) {
            IrElement backingField3 = irProperty.getBackingField();
            if (backingField3 == null) {
                backingField3 = irExpressionTransform != null ? IrUtilsKt.remapReceiver(irExpressionTransform, thisReceiver, setter.getDispatchReceiverParameter()) : null;
            }
            setter.setBody(accessorBody(setter, propertyReferenceForOptimizableDelegatedProperty, (IrStatement) backingField3));
        }
        IrSimpleFunction irSimpleFunctionCreateSyntheticMethodForPropertyDelegate = JvmPropertiesLowering.INSTANCE.createSyntheticMethodForPropertyDelegate(this.context, irProperty);
        JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder$default = JvmIrBuilderKt.createJvmIrBuilder$default(this.context, irSimpleFunctionCreateSyntheticMethodForPropertyDelegate.getSymbol(), 0, 0, 6, null);
        IrField backingField4 = irProperty.getBackingField();
        if (backingField4 != null) {
            JvmIrBuilder jvmIrBuilder = jvmIrBuilderCreateJvmIrBuilder$default;
            IrValueParameter dispatchReceiverParameter = irSimpleFunctionCreateSyntheticMethodForPropertyDelegate.getDispatchReceiverParameter();
            IrGetFieldImpl irGetFieldImplIrGetField$default = ExpressionHelpersKt.irGetField$default(jvmIrBuilder, dispatchReceiverParameter != null ? ExpressionHelpersKt.irGet(jvmIrBuilder, dispatchReceiverParameter) : null, backingField4, null, 4, null);
            if (irGetFieldImplIrGetField$default != null) {
                irGetFieldImplRemapReceiver = irGetFieldImplIrGetField$default;
            } else if (irExpressionTransform != null) {
                irGetFieldImplRemapReceiver = IrUtilsKt.remapReceiver(irExpressionTransform, thisReceiver, irSimpleFunctionCreateSyntheticMethodForPropertyDelegate.getDispatchReceiverParameter());
            } else {
                irGetFieldImplRemapReceiver = null;
            }
        } else if (irExpressionTransform != null) {
            irGetFieldImplRemapReceiver = IrUtilsKt.remapReceiver(irExpressionTransform, thisReceiver, irSimpleFunctionCreateSyntheticMethodForPropertyDelegate.getDispatchReceiverParameter());
        } else {
            irGetFieldImplRemapReceiver = null;
        }
        JvmIrBuilder jvmIrBuilder2 = jvmIrBuilderCreateJvmIrBuilder$default;
        IrPropertyReferenceImpl irPropertyReferenceImpl = new IrPropertyReferenceImpl(propertyReferenceForOptimizableDelegatedProperty.getStartOffset(), propertyReferenceForOptimizableDelegatedProperty.getEndOffset(), propertyReferenceForOptimizableDelegatedProperty.getType(), propertyReferenceForOptimizableDelegatedProperty.getSymbol(), propertyReferenceForOptimizableDelegatedProperty.getTypeArgumentsCount(), propertyReferenceForOptimizableDelegatedProperty.getField(), propertyReferenceForOptimizableDelegatedProperty.getGetter(), propertyReferenceForOptimizableDelegatedProperty.getSetter(), PropertyReferenceLowering.REFLECTED_PROPERTY_REFERENCE.INSTANCE);
        if (propertyReferenceForOptimizableDelegatedProperty.getDispatchReceiver() != null) {
            irPropertyReferenceImpl.setDispatchReceiver(irGetFieldImplRemapReceiver);
        } else if (propertyReferenceForOptimizableDelegatedProperty.getExtensionReceiver() != null) {
            irPropertyReferenceImpl.setExtensionReceiver(irGetFieldImplRemapReceiver);
        }
        irSimpleFunctionCreateSyntheticMethodForPropertyDelegate.setBody(ExpressionHelpersKt.irExprBody(jvmIrBuilder2, irPropertyReferenceImpl));
        if (irProperty.getBackingField() != null) {
            irExpressionTransform = null;
        }
        if (irExpressionTransform != null) {
            IrAnonymousInitializer irAnonymousInitializerCreateAnonymousInitializer = this.context.getIrFactory().createAnonymousInitializer(irExpressionTransform.getStartOffset(), irExpressionTransform.getEndOffset(), IrDeclarationOrigin.DEFINED.INSTANCE, new IrAnonymousInitializerSymbolImpl(IrUtilsKt.getParentAsClass(irProperty2).getSymbol()), IrUtilsKt.isFacadeClass(IrUtilsKt.getParentAsClass(irProperty2)));
            irAnonymousInitializerCreateAnonymousInitializer.setBody(IrFactoryHelpersKt.createBlockBody(this.context.getIrFactory(), irAnonymousInitializerCreateAnonymousInitializer.getStartOffset(), irAnonymousInitializerCreateAnonymousInitializer.getEndOffset(), (List<? extends IrStatement>) CollectionsKt.listOf(IrUtilsKt.remapReceiver(irExpressionTransform, null, null))));
            irAnonymousInitializer = irAnonymousInitializerCreateAnonymousInitializer;
        } else {
            irAnonymousInitializer = null;
        }
        return CollectionsKt.listOfNotNull((Object[]) new IrDeclarationBase[]{irProperty, irSimpleFunctionCreateSyntheticMethodForPropertyDelegate, irAnonymousInitializer});
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrStatement visitLocalDelegatedProperty(IrLocalDelegatedProperty declaration) {
        IrSimpleFunction setter;
        IrVariable irVariableBuildVariable;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrExpression initializer = declaration.getDelegate().getInitializer();
        if (!(initializer instanceof IrPropertyReference) || !JvmIrLowerUtilsKt.getReturnsResultOfStdlibCall(declaration.getGetter()) || ((setter = declaration.getSetter()) != null && !JvmIrLowerUtilsKt.getReturnsResultOfStdlibCall(setter))) {
            return super.visitLocalDelegatedProperty(declaration);
        }
        IrPropertyReference irPropertyReference = (IrPropertyReference) initializer;
        IrExpression dispatchReceiver = irPropertyReference.getDispatchReceiver();
        if (dispatchReceiver == null) {
            dispatchReceiver = irPropertyReference.getExtensionReceiver();
        }
        IrSimpleFunction irSimpleFunction = null;
        if (dispatchReceiver != null) {
            IrVariable delegate = declaration.getDelegate();
            irVariableBuildVariable = DeclarationBuildersKt.buildVariable(delegate.getParent(), delegate.getStartOffset(), delegate.getEndOffset(), delegate.getOrigin(), delegate.getName(), dispatchReceiver.getType(), (AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP & 64) != 0 ? false : false, (AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP & 128) != 0 ? false : false, (AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP & 256) != 0 ? false : false);
            irVariableBuildVariable.setInitializer(dispatchReceiver.transform((IrElementTransformer<? super Object>) this, (Object) null));
        } else {
            irVariableBuildVariable = null;
        }
        IrSimpleFunction getter = declaration.getGetter();
        IrVariable irVariable = irVariableBuildVariable;
        getter.setBody(accessorBody(getter, irPropertyReference, irVariable));
        IrSimpleFunction setter2 = declaration.getSetter();
        if (setter2 != null) {
            setter2.setBody(accessorBody(setter2, irPropertyReference, irVariable));
            irSimpleFunction = setter2;
        }
        List listListOfNotNull = CollectionsKt.listOfNotNull((Object[]) new IrDeclaration[]{irVariableBuildVariable, getter, irSimpleFunction});
        IrDeclaration irDeclaration = (IrDeclaration) CollectionsKt.singleOrNull(listListOfNotNull);
        return irDeclaration != null ? irDeclaration : new IrCompositeImpl(declaration.getStartOffset(), declaration.getEndOffset(), this.context.getIrBuiltIns().getUnitType(), null, listListOfNotNull);
    }
}
