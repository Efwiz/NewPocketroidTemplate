package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.Implementation;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext;
import org.jetbrains.kotlin.backend.common.ScopeWithIr;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.jvm.InlineClassAbiKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrTypeUtilsKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.backend.jvm.mapping.MethodSignatureMapper;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFieldAccessExpression;
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockBodyImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.util.TransformKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: JvmPropertiesLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 '2\u00020\u00012\u00020\u0002:\u0001'B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0002J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u0007H\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u001e\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u001a\u0010\u0017\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\t2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0007H\u0002J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\b\u001a\u00020\u001fH\u0016J\u001e\u0010 \u001a\u0004\u0018\u00010\u001a*\u00020!2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\"\u001a\u00020\tH\u0002J\u0014\u0010#\u001a\u00020\u001a*\u00020!2\u0006\u0010\u001b\u001a\u00020$H\u0002J\u001c\u0010%\u001a\u00020\u001a*\u00020!2\u0006\u0010\"\u001a\u00020\t2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u001c\u0010&\u001a\u00020\u001a*\u00020!2\u0006\u0010\"\u001a\u00020\t2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/JvmPropertiesLowering;", "Lorg/jetbrains/kotlin/backend/common/IrElementTransformerVoidWithContext;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "backendContext", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "createSyntheticMethodForAnnotations", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "isDefaultAccessorForCompanionPropertyBackingFieldOnCurrentClass", "", "property", "function", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "lowerProperty", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "shouldSubstituteAccessorWithField", Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX, "visitCall", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "visitClassNew", "Lorg/jetbrains/kotlin/ir/IrStatement;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "patchFieldAccessReceiver", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "irProperty", "patchReceiver", "Lorg/jetbrains/kotlin/ir/expressions/IrFieldAccessExpression;", "substituteGetter", "substituteSetter", "Companion", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmPropertiesLowering extends IrElementTransformerVoidWithContext implements FileLoweringPass {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final JvmBackendContext backendContext;

    public JvmPropertiesLowering(JvmBackendContext backendContext) {
        Intrinsics.checkNotNullParameter(backendContext, "backendContext");
        this.backendContext = backendContext;
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        irFile.accept(this, null);
    }

    @Override // org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext
    public IrStatement visitClassNew(final IrClass declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrElementTransformerVoidKt.transformChildrenVoid(declaration, this);
        TransformKt.transformDeclarationsFlat(declaration, new Function1<IrDeclaration, List<? extends IrDeclaration>>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmPropertiesLowering.visitClassNew.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final List<IrDeclaration> invoke(IrDeclaration it) {
                Intrinsics.checkNotNullParameter(it, "it");
                if (it instanceof IrProperty) {
                    return JvmPropertiesLowering.this.lowerProperty((IrProperty) it, declaration.getKind());
                }
                return null;
            }
        });
        return declaration;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitCall(IrCall expression) {
        IrProperty owner;
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrSimpleFunction owner2 = expression.getSymbol().getOwner();
        IrSimpleFunction irSimpleFunction = owner2 instanceof IrSimpleFunction ? owner2 : null;
        if (irSimpleFunction == null) {
            return super.visitCall(expression);
        }
        IrPropertySymbol correspondingPropertySymbol = irSimpleFunction.getCorrespondingPropertySymbol();
        if (correspondingPropertySymbol == null || (owner = correspondingPropertySymbol.getOwner()) == null) {
            return super.visitCall(expression);
        }
        transformChildrenVoid(expression);
        if (shouldSubstituteAccessorWithField(owner, irSimpleFunction) || isDefaultAccessorForCompanionPropertyBackingFieldOnCurrentClass(owner, irSimpleFunction)) {
            JvmBackendContext jvmBackendContext = this.backendContext;
            ScopeWithIr currentScope = getCurrentScope();
            Intrinsics.checkNotNull(currentScope);
            DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(jvmBackendContext, currentScope.getScope().getScopeOwnerSymbol(), expression.getStartOffset(), expression.getEndOffset());
            if (Intrinsics.areEqual(irSimpleFunction, owner.getGetter())) {
                return substituteGetter(declarationIrBuilderCreateIrBuilder, owner, expression);
            }
            if (Intrinsics.areEqual(irSimpleFunction, owner.getSetter())) {
                return substituteSetter(declarationIrBuilderCreateIrBuilder, owner, expression);
            }
            throw new IllegalStateException(("Orphaned property getter/setter: " + RenderIrElementKt.render$default(irSimpleFunction, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
        }
        return expression;
    }

    private final boolean isDefaultAccessorForCompanionPropertyBackingFieldOnCurrentClass(IrProperty property, IrSimpleFunction function) {
        if (!Intrinsics.areEqual(function.getOrigin(), IrDeclarationOrigin.DEFAULT_PROPERTY_ACCESSOR.INSTANCE) || property.getIsLateinit()) {
            return false;
        }
        List<ScopeWithIr> allScopes = getAllScopes();
        if (!(allScopes instanceof Collection) || !allScopes.isEmpty()) {
            Iterator<T> it = allScopes.iterator();
            while (it.hasNext()) {
                IrElement irElement = ((ScopeWithIr) it.next()).getIrElement();
                IrFunction irFunction = irElement instanceof IrFunction ? (IrFunction) irElement : null;
                if (irFunction != null ? irFunction.getIsInline() : false) {
                    return false;
                }
            }
        }
        IrOverridableDeclaration irOverridableDeclarationResolveFakeOverride$default = IrFakeOverrideUtilsKt.resolveFakeOverride$default(property, false, null, 3, null);
        Intrinsics.checkNotNull(irOverridableDeclarationResolveFakeOverride$default);
        IrField backingField = ((IrProperty) irOverridableDeclarationResolveFakeOverride$default).getBackingField();
        IrDeclarationParent parent = backingField != null ? backingField.getParent() : null;
        ScopeWithIr currentClass = getCurrentClass();
        if (Intrinsics.areEqual(parent, currentClass != null ? currentClass.getIrElement() : null)) {
            return Intrinsics.areEqual(backingField != null ? backingField.getOrigin() : null, JvmLoweredDeclarationOrigin.COMPANION_PROPERTY_BACKING_FIELD.INSTANCE);
        }
        return false;
    }

    private final IrExpression substituteSetter(IrBuilderWithScope irBuilderWithScope, IrProperty irProperty, IrCall irCall) {
        IrOverridableDeclaration irOverridableDeclarationResolveFakeOverride$default = IrFakeOverrideUtilsKt.resolveFakeOverride$default(irProperty, false, null, 3, null);
        Intrinsics.checkNotNull(irOverridableDeclarationResolveFakeOverride$default);
        IrField backingField = ((IrProperty) irOverridableDeclarationResolveFakeOverride$default).getBackingField();
        Intrinsics.checkNotNull(backingField);
        IrExpression irExpressionPatchFieldAccessReceiver = patchFieldAccessReceiver(irBuilderWithScope, irCall, irProperty);
        IrExpression valueArgument = irCall.getValueArgument(0);
        Intrinsics.checkNotNull(valueArgument);
        return patchReceiver(irBuilderWithScope, ExpressionHelpersKt.irSetField$default(irBuilderWithScope, irExpressionPatchFieldAccessReceiver, backingField, valueArgument, null, 8, null));
    }

    private final IrExpression substituteGetter(IrBuilderWithScope irBuilderWithScope, IrProperty irProperty, IrCall irCall) {
        IrOverridableDeclaration irOverridableDeclarationResolveFakeOverride$default = IrFakeOverrideUtilsKt.resolveFakeOverride$default(irProperty, false, null, 3, null);
        Intrinsics.checkNotNull(irOverridableDeclarationResolveFakeOverride$default);
        IrField backingField = ((IrProperty) irOverridableDeclarationResolveFakeOverride$default).getBackingField();
        Intrinsics.checkNotNull(backingField);
        IrExpression irExpressionPatchFieldAccessReceiver = patchFieldAccessReceiver(irBuilderWithScope, irCall, irProperty);
        if (!irProperty.getIsLateinit()) {
            return new IrGetFieldImpl(irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), backingField.getSymbol(), irCall.getType(), irExpressionPatchFieldAccessReceiver, null, null, 96, null);
        }
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(irBuilderWithScope.getContext(), irBuilderWithScope.getScope(), irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), null, null, false, 64, null);
        IrVariable irVariableIrTemporary$default = ExpressionHelpersKt.irTemporary$default(irBlockBuilder, new IrGetFieldImpl(irBlockBuilder.getStartOffset(), irBlockBuilder.getEndOffset(), backingField.getSymbol(), IrTypesKt.makeNullable(irCall.getType()), irExpressionPatchFieldAccessReceiver, null, null, 96, null), null, null, false, null, 30, null);
        IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
        IrType type = irCall.getType();
        IrVariable irVariable = irVariableIrTemporary$default;
        IrGetValueImpl irGetValueImplIrGet = ExpressionHelpersKt.irGet(irBlockBuilder2, irVariable);
        JvmBackendContext jvmBackendContext = this.backendContext;
        String strAsString = backingField.getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        irBlockBuilder.unaryPlus(ExpressionHelpersKt.irIfNull(irBlockBuilder2, type, irGetValueImplIrGet, jvmBackendContext.throwUninitializedPropertyAccessException(irBlockBuilder2, strAsString), ExpressionHelpersKt.irGet(irBlockBuilder2, irVariable)));
        return irBlockBuilder.getIrBlockBody();
    }

    private final IrExpression patchFieldAccessReceiver(IrBuilderWithScope irBuilderWithScope, IrCall irCall, IrProperty irProperty) {
        IrClassifierSymbol classifierOrNull;
        IrExpression dispatchReceiver = irCall.getDispatchReceiver();
        if (dispatchReceiver != null) {
            IrDeclarationParent parent = irProperty.getParent();
            if ((parent instanceof IrClass) && (((classifierOrNull = IrTypesKt.getClassifierOrNull(dispatchReceiver.getType())) == null || !IrTypeUtilsKt.isSubtypeOfClass(classifierOrNull, ((IrClass) parent).getSymbol())) && irCall.getSuperQualifierSymbol() == null)) {
                return ExpressionHelpersKt.irImplicitCast(irBuilderWithScope, dispatchReceiver, IrUtilsKt.getDefaultType((IrClass) parent));
            }
        }
        return dispatchReceiver;
    }

    private final IrExpression patchReceiver(IrBuilderWithScope irBuilderWithScope, IrFieldAccessExpression irFieldAccessExpression) {
        if (!irFieldAccessExpression.getSymbol().getOwner().getIsStatic() || irFieldAccessExpression.getReceiver() == null) {
            return irFieldAccessExpression;
        }
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(irBuilderWithScope.getContext(), irBuilderWithScope.getScope(), irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), null, null, false, 64, null);
        IrExpression receiver = irFieldAccessExpression.getReceiver();
        Intrinsics.checkNotNull(receiver);
        irBlockBuilder.unaryPlus(IrUtilsKt.coerceToUnit(receiver, irBlockBuilder.getContext().getIrBuiltIns(), this.backendContext.getTypeSystem()));
        irFieldAccessExpression.setReceiver(null);
        irBlockBuilder.unaryPlus(irFieldAccessExpression);
        return irBlockBuilder.getIrBlockBody();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<IrDeclaration> lowerProperty(IrProperty declaration, ClassKind kind) {
        ArrayList arrayList = new ArrayList(4);
        IrField backingField = declaration.getBackingField();
        if (kind != ClassKind.ANNOTATION_CLASS || (backingField != null && backingField.getIsStatic())) {
            if (Intrinsics.areEqual(backingField != null ? backingField.getParent() : null, declaration.getParent())) {
                CollectionsKt.addIfNotNull(arrayList, backingField);
            }
        }
        if (!declaration.getIsConst()) {
            IrSimpleFunction getter = declaration.getGetter();
            if (getter != null) {
                if (shouldSubstituteAccessorWithField(declaration, getter)) {
                    getter = null;
                }
                if (getter != null) {
                    arrayList.add(getter);
                }
            }
            IrSimpleFunction setter = declaration.getSetter();
            if (setter != null) {
                IrSimpleFunction irSimpleFunction = shouldSubstituteAccessorWithField(declaration, setter) ? null : setter;
                if (irSimpleFunction != null) {
                    arrayList.add(irSimpleFunction);
                }
            }
        }
        if (!declaration.getIsFakeOverride() && !declaration.getAnnotations().isEmpty()) {
            arrayList.add(createSyntheticMethodForAnnotations(declaration));
        }
        return arrayList;
    }

    private final boolean shouldSubstituteAccessorWithField(IrProperty property, IrSimpleFunction accessor) {
        return (accessor == null || JvmIrUtilsKt.needsAccessor(property, accessor)) ? false : true;
    }

    private final IrSimpleFunction createSyntheticMethodForAnnotations(IrProperty declaration) {
        IrSimpleFunction irSimpleFunctionCreateSyntheticMethodForProperty = INSTANCE.createSyntheticMethodForProperty(this.backendContext, declaration, JvmAbi.ANNOTATED_PROPERTY_METHOD_NAME_SUFFIX, JvmLoweredDeclarationOrigin.SYNTHETIC_METHOD_FOR_PROPERTY_OR_TYPEALIAS_ANNOTATIONS.INSTANCE, true, this.backendContext.getIrBuiltIns().getUnitType(), declaration.getVisibility());
        irSimpleFunctionCreateSyntheticMethodForProperty.setBody(new IrBlockBodyImpl(-1, -1));
        irSimpleFunctionCreateSyntheticMethodForProperty.setAnnotations(declaration.getAnnotations());
        return irSimpleFunctionCreateSyntheticMethodForProperty;
    }

    /* JADX INFO: compiled from: JvmPropertiesLowering.kt */
    @Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u00020\u0004*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0004H\u0002J<\u0010\t\u001a\u00020\n*\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0012\u0010\u0014\u001a\u00020\n*\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u0007¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/JvmPropertiesLowering$Companion;", "", "()V", "computeSyntheticMethodName", "", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "property", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "suffix", "createSyntheticMethodForProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "declaration", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "isStatic", "", "returnType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "createSyntheticMethodForPropertyDelegate", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final IrSimpleFunction createSyntheticMethodForProperty(JvmBackendContext jvmBackendContext, IrProperty irProperty, String str, IrDeclarationOrigin irDeclarationOrigin, boolean z, IrType irType, DescriptorVisibility descriptorVisibility) {
            IrValueParameter extensionReceiverParameter;
            IrValueParameter dispatchReceiverParameter;
            IrFactory irFactory = jvmBackendContext.getIrFactory();
            IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
            Name nameIdentifier = Name.identifier(JvmPropertiesLowering.INSTANCE.computeSyntheticMethodName(jvmBackendContext, irProperty, str));
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            irFunctionBuilder.setName(nameIdentifier);
            irFunctionBuilder.setModality(Modality.OPEN);
            irFunctionBuilder.setOrigin(irDeclarationOrigin);
            irFunctionBuilder.setReturnType(irType);
            irFunctionBuilder.setVisibility(descriptorVisibility);
            IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
            IrValueParameter irValueParameterCopyTo$default = null;
            if (!z) {
                IrSimpleFunction getter = irProperty.getGetter();
                irSimpleFunctionBuildFunction.setDispatchReceiverParameter((getter == null || (dispatchReceiverParameter = getter.getDispatchReceiverParameter()) == null) ? null : IrUtilsKt.copyTo$default(dispatchReceiverParameter, irSimpleFunctionBuildFunction, null, 0, 0, 0, null, null, JvmIrTypeUtilsKt.eraseTypeParameters(dispatchReceiverParameter.getType()), null, null, false, false, false, 8062, null));
            }
            IrSimpleFunction getter2 = irProperty.getGetter();
            if (getter2 != null && (extensionReceiverParameter = getter2.getExtensionReceiverParameter()) != null) {
                irValueParameterCopyTo$default = IrUtilsKt.copyTo$default(extensionReceiverParameter, irSimpleFunctionBuildFunction, null, 0, 0, 0, null, null, JvmIrTypeUtilsKt.eraseTypeParameters(extensionReceiverParameter.getType()), null, null, false, false, false, 8058, null);
            }
            irSimpleFunctionBuildFunction.setValueParameters(kotlin.collections.CollectionsKt.listOfNotNull(irValueParameterCopyTo$default));
            irSimpleFunctionBuildFunction.setParent(irProperty.getParent());
            irSimpleFunctionBuildFunction.setMetadata(irProperty.getMetadata());
            return irSimpleFunctionBuildFunction;
        }

        public final IrSimpleFunction createSyntheticMethodForPropertyDelegate(JvmBackendContext jvmBackendContext, IrProperty declaration) {
            Intrinsics.checkNotNullParameter(jvmBackendContext, "<this>");
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            IrDeclarationOrigin.PROPERTY_DELEGATE property_delegate = IrDeclarationOrigin.PROPERTY_DELEGATE.INSTANCE;
            IrType anyNType = jvmBackendContext.getIrBuiltIns().getAnyNType();
            DescriptorVisibility PRIVATE = DescriptorVisibilities.PRIVATE;
            Intrinsics.checkNotNullExpressionValue(PRIVATE, "PRIVATE");
            return createSyntheticMethodForProperty(jvmBackendContext, declaration, JvmAbi.DELEGATED_PROPERTY_NAME_SUFFIX, property_delegate, false, anyNType, PRIVATE);
        }

        private final String computeSyntheticMethodName(JvmBackendContext jvmBackendContext, IrProperty irProperty, String str) {
            String strAsString;
            IrType type;
            if (jvmBackendContext.getState().getLanguageVersionSettings().supportsFeature(LanguageFeature.UseGetterNameForPropertyAnnotationsMethodOnJvm)) {
                IrSimpleFunction getter = irProperty.getGetter();
                if (getter != null) {
                    IrValueParameter extensionReceiverParameter = getter.getExtensionReceiverParameter();
                    IrSimpleFunction replacementFunction = ((extensionReceiverParameter == null || (type = extensionReceiverParameter.getType()) == null || !InlineClassAbiKt.getRequiresMangling(type, true, false)) && !(jvmBackendContext.getConfig().getFunctionsWithInlineClassReturnTypesMangled() && InlineClassAbiKt.getHasMangledReturnType(getter))) ? null : jvmBackendContext.getInlineClassReplacements().getReplacementFunction(getter);
                    MethodSignatureMapper defaultMethodSignatureMapper = jvmBackendContext.getDefaultMethodSignatureMapper();
                    if (replacementFunction != null) {
                        getter = replacementFunction;
                    }
                    strAsString = MethodSignatureMapper.mapFunctionName$default(defaultMethodSignatureMapper, getter, false, 2, null);
                } else {
                    String strAsString2 = irProperty.getName().asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
                    strAsString = JvmAbi.getterName(strAsString2);
                }
            } else {
                strAsString = irProperty.getName().asString();
                Intrinsics.checkNotNull(strAsString);
            }
            return strAsString + str;
        }
    }
}
