package org.jetbrains.kotlin.backend.common.lower;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.BackendContext;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.ir.ExpectSymbolTransformer;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrTypeSystemContextKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols;
import org.jetbrains.kotlin.ir.util.DeepCopySymbolRemapper;
import org.jetbrains.kotlin.ir.util.DeepCopyTypeRemapper;
import org.jetbrains.kotlin.ir.util.IrTypeParameterRemapper;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.util.ReferenceSymbolTable;
import org.jetbrains.kotlin.ir.util.ReferenceSymbolTableExtension;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.multiplatform.ExpectedActualResolverKt;
import org.jetbrains.kotlin.resolve.multiplatform.OptionalAnnotationUtil;

/* JADX INFO: compiled from: ExpectDeclarationRemover.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u009e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B\u0015\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0012\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0014J\u0012\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0015\u001a\u00020\u0019H\u0014J\u0012\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u0015\u001a\u00020\u001cH\u0014J\u0012\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u0015\u001a\u00020\u001fH\u0014J\u0010\u0010 \u001a\u00020\t2\u0006\u0010!\u001a\u00020\"H\u0002J\u0010\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&H\u0016J\u0010\u0010'\u001a\u00020\t2\u0006\u0010(\u001a\u00020)H\u0002J\u0016\u0010*\u001a\n\u0012\u0004\u0012\u00020)\u0018\u00010+2\u0006\u0010(\u001a\u00020)J\u0010\u0010,\u001a\u00020$2\u0006\u0010(\u001a\u00020-H\u0002J\u0010\u0010.\u001a\u00020$2\u0006\u0010(\u001a\u00020&H\u0016J\u0010\u0010/\u001a\u00020$2\u0006\u0010(\u001a\u00020-H\u0016J(\u00100\u001a\u000201*\u0002012\u0006\u00102\u001a\u00020\u00102\u0012\u00103\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00120\u0011H\u0002J\u000e\u00104\u001a\u0004\u0018\u000105*\u000205H\u0002J\u000e\u00106\u001a\u0004\u0018\u000105*\u000205H\u0002R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR2\u0010\r\u001a&\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00100\u000f\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00120\u00110\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u00067"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/ExpectDeclarationRemover;", "Lorg/jetbrains/kotlin/backend/common/ir/ExpectSymbolTransformer;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/common/BackendContext;", "(Lorg/jetbrains/kotlin/backend/common/BackendContext;)V", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;", "doRemove", "", "(Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;Z)V", "getSymbolTable", "()Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;", "typeParameterSubstitutionMap", "", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "getActualClass", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getActualConstructor", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "getActualFunction", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getActualProperty", "Lorg/jetbrains/kotlin/backend/common/ir/ExpectSymbolTransformer$ActualPropertyResult;", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "isOptionalAnnotationClass", "klass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "shouldRemoveTopLevelDeclaration", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "transformFlat", "", "tryCopyDefaultArguments", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "visitFile", "visitValueParameter", "copyAndActualizeDefaultValue", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "actualFunction", "expectActualTypeParametersMap", "findActualForExpect", "Lorg/jetbrains/kotlin/descriptors/MemberDescriptor;", "findExpectForActual", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class ExpectDeclarationRemover extends ExpectSymbolTransformer implements FileLoweringPass {
    private final boolean doRemove;
    private final ReferenceSymbolTable symbolTable;
    private final Map<Pair<IrFunction, IrFunction>, Map<IrTypeParameter, IrTypeParameter>> typeParameterSubstitutionMap;

    public ExpectDeclarationRemover(ReferenceSymbolTable symbolTable, boolean z) {
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        this.symbolTable = symbolTable;
        this.doRemove = z;
        this.typeParameterSubstitutionMap = new LinkedHashMap();
    }

    public final ReferenceSymbolTable getSymbolTable() {
        return this.symbolTable;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ExpectDeclarationRemover(BackendContext context) {
        this(context.getIr().getSymbols().getSymbolTable(), true);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        visitFile(irFile);
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.ExpectSymbolTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitFile(IrFile declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (this.doRemove) {
            CollectionsKt.removeAll((List) declaration.getDeclarations(), (Function1) new Function1<IrDeclaration, Boolean>() { // from class: org.jetbrains.kotlin.backend.common.lower.ExpectDeclarationRemover.visitFile.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(IrDeclaration it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(ExpectDeclarationRemover.this.shouldRemoveTopLevelDeclaration(it));
                }
            });
        }
        super.visitFile(declaration);
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.ExpectSymbolTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitValueParameter(IrValueParameter declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        tryCopyDefaultArguments(declaration);
        super.visitValueParameter(declaration);
    }

    public final List<IrDeclaration> transformFlat(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (AdditionalIrUtilsKt.isTopLevelDeclaration(declaration) && shouldRemoveTopLevelDeclaration(declaration)) {
            return CollectionsKt.emptyList();
        }
        if (!(declaration instanceof IrValueParameter)) {
            return null;
        }
        tryCopyDefaultArguments((IrValueParameter) declaration);
        return null;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.ExpectSymbolTransformer
    protected IrClassSymbol getActualClass(ClassDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        ReferenceSymbolTableExtension<ClassDescriptor, TypeAliasDescriptor, ScriptDescriptor, FunctionDescriptor, ClassConstructorDescriptor, PropertyDescriptor, ParameterDescriptor, TypeParameterDescriptor> descriptorExtension = this.symbolTable.getDescriptorExtension();
        MemberDescriptor memberDescriptorFindActualForExpect = findActualForExpect(descriptor);
        ClassDescriptor classDescriptor = memberDescriptorFindActualForExpect instanceof ClassDescriptor ? (ClassDescriptor) memberDescriptorFindActualForExpect : null;
        if (classDescriptor == null) {
            return null;
        }
        return descriptorExtension.referenceClass(classDescriptor);
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.ExpectSymbolTransformer
    protected ExpectSymbolTransformer.ActualPropertyResult getActualProperty(PropertyDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        ReferenceSymbolTableExtension<ClassDescriptor, TypeAliasDescriptor, ScriptDescriptor, FunctionDescriptor, ClassConstructorDescriptor, PropertyDescriptor, ParameterDescriptor, TypeParameterDescriptor> descriptorExtension = this.symbolTable.getDescriptorExtension();
        MemberDescriptor memberDescriptorFindActualForExpect = findActualForExpect(descriptor);
        PropertyDescriptor propertyDescriptor = memberDescriptorFindActualForExpect instanceof PropertyDescriptor ? (PropertyDescriptor) memberDescriptorFindActualForExpect : null;
        if (propertyDescriptor == null) {
            return null;
        }
        IrPropertySymbol irPropertySymbolReferenceProperty = descriptorExtension.referenceProperty(propertyDescriptor);
        PropertyGetterDescriptor getter = irPropertySymbolReferenceProperty.getDescriptor().getGetter();
        IrSimpleFunctionSymbol irSimpleFunctionSymbolReferenceSimpleFunction = getter != null ? this.symbolTable.getDescriptorExtension().referenceSimpleFunction(getter) : null;
        PropertySetterDescriptor setter = irPropertySymbolReferenceProperty.getDescriptor().getSetter();
        return new ExpectSymbolTransformer.ActualPropertyResult(irPropertySymbolReferenceProperty, irSimpleFunctionSymbolReferenceSimpleFunction, setter != null ? this.symbolTable.getDescriptorExtension().referenceSimpleFunction(setter) : null);
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.ExpectSymbolTransformer
    protected IrConstructorSymbol getActualConstructor(ClassConstructorDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        ReferenceSymbolTableExtension<ClassDescriptor, TypeAliasDescriptor, ScriptDescriptor, FunctionDescriptor, ClassConstructorDescriptor, PropertyDescriptor, ParameterDescriptor, TypeParameterDescriptor> descriptorExtension = this.symbolTable.getDescriptorExtension();
        MemberDescriptor memberDescriptorFindActualForExpect = findActualForExpect(descriptor);
        ClassConstructorDescriptor classConstructorDescriptor = memberDescriptorFindActualForExpect instanceof ClassConstructorDescriptor ? (ClassConstructorDescriptor) memberDescriptorFindActualForExpect : null;
        if (classConstructorDescriptor == null) {
            return null;
        }
        return descriptorExtension.referenceConstructor(classConstructorDescriptor);
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.ExpectSymbolTransformer
    protected IrSimpleFunctionSymbol getActualFunction(FunctionDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        ReferenceSymbolTableExtension<ClassDescriptor, TypeAliasDescriptor, ScriptDescriptor, FunctionDescriptor, ClassConstructorDescriptor, PropertyDescriptor, ParameterDescriptor, TypeParameterDescriptor> descriptorExtension = this.symbolTable.getDescriptorExtension();
        MemberDescriptor memberDescriptorFindActualForExpect = findActualForExpect(descriptor);
        FunctionDescriptor functionDescriptor = memberDescriptorFindActualForExpect instanceof FunctionDescriptor ? (FunctionDescriptor) memberDescriptorFindActualForExpect : null;
        if (functionDescriptor == null) {
            return null;
        }
        return descriptorExtension.referenceSimpleFunction(functionDescriptor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final MemberDescriptor findActualForExpect(MemberDescriptor memberDescriptor) {
        if (!memberDescriptor.get$isExpect()) {
            throw new IllegalStateException(memberDescriptor.toString());
        }
        return (MemberDescriptor) CollectionsKt.singleOrNull(ExpectedActualResolverKt.findCompatibleActualsForExpected$default(memberDescriptor, DescriptorUtilsKt.getModule(memberDescriptor), null, 2, null));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean shouldRemoveTopLevelDeclaration(IrDeclaration declaration) {
        boolean isExpect;
        if (!this.doRemove) {
            return false;
        }
        if (declaration instanceof IrClass) {
            isExpect = ((IrClass) declaration).getIsExpect();
        } else if (declaration instanceof IrProperty) {
            isExpect = ((IrProperty) declaration).getIsExpect();
        } else {
            isExpect = declaration instanceof IrFunction ? ((IrFunction) declaration).getIsExpect() : false;
        }
        return isExpect;
    }

    private final boolean isOptionalAnnotationClass(IrClass klass) {
        return klass.getKind() == ClassKind.ANNOTATION_CLASS && klass.getIsExpect() && AdditionalIrUtilsKt.hasAnnotation(klass.getAnnotations(), OptionalAnnotationUtil.INSTANCE.getOPTIONAL_EXPECTATION_FQ_NAME());
    }

    private final void tryCopyDefaultArguments(IrValueParameter declaration) {
        int index;
        IrFunction owner;
        IrExpressionBody defaultValue;
        if (declaration.getDefaultValue() != null) {
            return;
        }
        IrDeclarationParent parent = declaration.getParent();
        IrFunction irFunction = parent instanceof IrFunction ? (IrFunction) parent : null;
        if (irFunction == null) {
            return;
        }
        if (!((irFunction instanceof IrConstructor) && isOptionalAnnotationClass(AdditionalIrUtilsKt.getConstructedClass((IrConstructor) irFunction))) && irFunction.getDescriptor().getIsActual() && (index = declaration.getIndex()) >= 0) {
            Intrinsics.areEqual(irFunction.getValueParameters().get(index), declaration);
            MemberDescriptor memberDescriptorFindExpectForActual = findExpectForActual(irFunction.getDescriptor());
            FunctionDescriptor functionDescriptor = memberDescriptorFindExpectForActual instanceof FunctionDescriptor ? (FunctionDescriptor) memberDescriptorFindExpectForActual : null;
            if (functionDescriptor == null || (owner = IrUtilsKt.referenceFunction(this.symbolTable, functionDescriptor).getOwner()) == null || (defaultValue = owner.getValueParameters().get(index).getDefaultValue()) == null) {
                return;
            }
            Pair<IrFunction, IrFunction> pair = TuplesKt.to(owner, irFunction);
            if (!this.typeParameterSubstitutionMap.containsKey(pair)) {
                this.typeParameterSubstitutionMap.put(pair, MapsKt.toMap(CollectionsKt.zip(IrTypeSystemContextKt.extractTypeParameters(owner), IrTypeSystemContextKt.extractTypeParameters(irFunction))));
            }
            declaration.setDefaultValue(copyAndActualizeDefaultValue(defaultValue, irFunction, (Map) MapsKt.getValue(this.typeParameterSubstitutionMap, pair)));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final IrExpressionBody copyAndActualizeDefaultValue(IrExpressionBody irExpressionBody, IrFunction irFunction, Map<IrTypeParameter, ? extends IrTypeParameter> map) {
        IrExpressionBody irExpressionBody2 = irExpressionBody;
        DeepCopySymbolRemapper deepCopySymbolRemapper = new DeepCopySymbolRemapper(null, 1, 0 == true ? 1 : 0);
        IrVisitorsKt.acceptVoid(irExpressionBody2, deepCopySymbolRemapper);
        DeepCopySymbolRemapper deepCopySymbolRemapper2 = deepCopySymbolRemapper;
        new DeepCopyTypeRemapper(deepCopySymbolRemapper2);
        IrElement irElementPatchDeclarationParents = PatchDeclarationParentsKt.patchDeclarationParents(irExpressionBody2.transform((IrElementTransformer<? super Object>) new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper2, new IrTypeParameterRemapper(map)), (Object) null), irFunction);
        if (irElementPatchDeclarationParents != null) {
            return ((IrExpressionBody) irElementPatchDeclarationParents).transform((IrElementTransformer<? super Object>) new IrElementTransformerVoid() { // from class: org.jetbrains.kotlin.backend.common.lower.ExpectDeclarationRemover.copyAndActualizeDefaultValue.2
                @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
                public IrExpression visitGetValue(IrGetValue expression) {
                    Intrinsics.checkNotNullParameter(expression, "expression");
                    transformChildrenVoid(expression);
                    final ExpectDeclarationRemover expectDeclarationRemover = ExpectDeclarationRemover.this;
                    Function1<IrClass, IrClass> function1 = new Function1<IrClass, IrClass>() { // from class: org.jetbrains.kotlin.backend.common.lower.ExpectDeclarationRemover$copyAndActualizeDefaultValue$2$visitGetValue$1
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final IrClass invoke(IrClass it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            ReferenceSymbolTableExtension<ClassDescriptor, TypeAliasDescriptor, ScriptDescriptor, FunctionDescriptor, ClassConstructorDescriptor, PropertyDescriptor, ParameterDescriptor, TypeParameterDescriptor> descriptorExtension = expectDeclarationRemover.getSymbolTable().getDescriptorExtension();
                            MemberDescriptor memberDescriptorFindActualForExpect = expectDeclarationRemover.findActualForExpect(it.getDescriptor());
                            Intrinsics.checkNotNull(memberDescriptorFindActualForExpect, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                            return descriptorExtension.referenceClass((ClassDescriptor) memberDescriptorFindActualForExpect).getOwner();
                        }
                    };
                    final ExpectDeclarationRemover expectDeclarationRemover2 = ExpectDeclarationRemover.this;
                    return LowerUtilsKt.actualize(expression, function1, new Function1<IrFunction, IrFunction>() { // from class: org.jetbrains.kotlin.backend.common.lower.ExpectDeclarationRemover$copyAndActualizeDefaultValue$2$visitGetValue$2
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final IrFunction invoke(IrFunction it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            ReferenceSymbolTable symbolTable = expectDeclarationRemover2.getSymbolTable();
                            MemberDescriptor memberDescriptorFindActualForExpect = expectDeclarationRemover2.findActualForExpect(it.getDescriptor());
                            Intrinsics.checkNotNull(memberDescriptorFindActualForExpect, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.FunctionDescriptor");
                            return IrUtilsKt.referenceFunction(symbolTable, (FunctionDescriptor) memberDescriptorFindActualForExpect).getOwner();
                        }
                    });
                }
            }, (Object) null);
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpressionBody");
    }

    private final MemberDescriptor findExpectForActual(MemberDescriptor memberDescriptor) {
        if (!memberDescriptor.getIsActual()) {
            throw new IllegalStateException(memberDescriptor.toString());
        }
        return (MemberDescriptor) CollectionsKt.singleOrNull(ExpectedActualResolverKt.findCompatibleExpectsForActual$default(memberDescriptor, null, 1, null));
    }
}
