package org.jetbrains.kotlin.psi2ir.generators;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.BackendException;
import org.jetbrains.kotlin.backend.common.CodegenUtil;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.PsiDiagnosticUtils;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeAliasSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension;
import org.jetbrains.kotlin.ir.util.SymbolTableExtension;
import org.jetbrains.kotlin.ir.util.TypeTranslator;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtAnonymousInitializer;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtEnumEntry;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPureClassOrObject;
import org.jetbrains.kotlin.psi.KtPureElement;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.psi.KtSecondaryConstructor;
import org.jetbrains.kotlin.psi.KtTypeAlias;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.psi2ir.KotlinUtilsKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.util.slicedMap.ReadOnlySlice;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: DeclarationGenerator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000´\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J \u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0018\u0010\u0016\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aJ\u001a\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\u001a\u0010\u001f\u001a\u0004\u0018\u00010 2\u0006\u0010!\u001a\u00020\"2\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\u001c\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\f\u0010'\u001a\b\u0012\u0004\u0012\u00020)0(J\u0016\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/J\u0010\u00100\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\fJ\u001c\u00101\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\f\u0010'\u001a\b\u0012\u0004\u0012\u00020)0(J\u000e\u00102\u001a\u00020\u000e2\u0006\u00103\u001a\u00020\u0010J\u0010\u00104\u001a\u0002052\u0006\u00106\u001a\u000207H\u0002J>\u00108\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\f\u0010'\u001a\b\u0012\u0004\u0012\u00020)0(2\u001e\u00109\u001a\u001a\u0012\u0004\u0012\u00020;\u0012\u0004\u0012\u00020;\u0012\u0004\u0012\u00020)\u0012\u0004\u0012\u00020<0:H\u0002J\n\u0010=\u001a\u00020>*\u00020?R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006@"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/DeclarationGenerator;", "Lorg/jetbrains/kotlin/psi2ir/generators/Generator;", "context", "Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;", "(Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;)V", "getContext", "()Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;", "typeTranslator", "Lorg/jetbrains/kotlin/ir/util/TypeTranslator;", "generateClassMemberDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "ktDeclaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "ktClassOrObject", "Lorg/jetbrains/kotlin/psi/KtPureClassOrObject;", "generateClassOrObjectDeclaration", "generateEnumEntryDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "ktEnumEntry", "Lorg/jetbrains/kotlin/psi/KtEnumEntry;", "generateFakeOverrideDeclaration", "memberDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "ktElement", "Lorg/jetbrains/kotlin/psi/KtPureElement;", "generateFakeOverrideFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "functionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "generateFakeOverrideProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "propertyDescriptor", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "generateGlobalTypeParametersDeclarations", "", "irTypeParametersOwner", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParametersContainer;", TypedValues.TransitionType.S_FROM, "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "generateInitializerBody", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "scopeOwnerSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "ktBody", "Lorg/jetbrains/kotlin/psi/KtExpression;", "generateMemberDeclaration", "generateScopedTypeParameterDeclarations", "generateSyntheticClassOrObject", "syntheticDeclaration", "generateTypeAliasDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;", "ktTypeAlias", "Lorg/jetbrains/kotlin/psi/KtTypeAlias;", "generateTypeParameterDeclarations", "declareTypeParameter", "Lkotlin/Function3;", "", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "toIrType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lorg/jetbrains/kotlin/types/KotlinType;", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeclarationGenerator implements Generator {
    private final GeneratorContext context;
    private final TypeTranslator typeTranslator;

    public DeclarationGenerator(GeneratorContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.typeTranslator = getContext().getTypeTranslator();
    }

    @Override // org.jetbrains.kotlin.ir.builders.IrGenerator
    public GeneratorContext getContext() {
        return this.context;
    }

    public final IrType toIrType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return this.typeTranslator.translateType(kotlinType);
    }

    public final IrDeclaration generateMemberDeclaration(KtDeclaration ktDeclaration) {
        String path;
        IrErrorDeclaration irErrorDeclarationCreateErrorDeclaration;
        Intrinsics.checkNotNullParameter(ktDeclaration, "ktDeclaration");
        try {
            if (ktDeclaration instanceof KtNamedFunction) {
                irErrorDeclarationCreateErrorDeclaration = FunctionGenerator.generateFunctionDeclaration$default(new FunctionGenerator(this), (KtNamedFunction) ktDeclaration, null, null, 4, null);
            } else if (ktDeclaration instanceof KtProperty) {
                irErrorDeclarationCreateErrorDeclaration = new PropertyGenerator(this).generatePropertyDeclaration((KtProperty) ktDeclaration);
            } else if (ktDeclaration instanceof KtClassOrObject) {
                irErrorDeclarationCreateErrorDeclaration = generateClassOrObjectDeclaration((KtPureClassOrObject) ktDeclaration);
            } else if (ktDeclaration instanceof KtTypeAlias) {
                irErrorDeclarationCreateErrorDeclaration = generateTypeAliasDeclaration((KtTypeAlias) ktDeclaration);
            } else if (ktDeclaration instanceof KtScript) {
                irErrorDeclarationCreateErrorDeclaration = new ScriptGenerator(this).generateScriptDeclaration((KtScript) ktDeclaration);
            } else {
                IrFactory irFactory = getContext().getIrFactory();
                int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktDeclaration);
                int endOffset = PsiUtilsKt.getEndOffset(ktDeclaration);
                ReadOnlySlice<PsiElement, DeclarationDescriptor> DECLARATION_TO_DESCRIPTOR = BindingContext.DECLARATION_TO_DESCRIPTOR;
                Intrinsics.checkNotNullExpressionValue(DECLARATION_TO_DESCRIPTOR, "DECLARATION_TO_DESCRIPTOR");
                irErrorDeclarationCreateErrorDeclaration = irFactory.createErrorDeclaration(startOffsetSkippingComments, endOffset, (DeclarationDescriptor) GeneratorKt.getOrFail(this, DECLARATION_TO_DESCRIPTOR, ktDeclaration));
            }
            return irErrorDeclarationCreateErrorDeclaration;
        } catch (BackendException e) {
            throw e;
        } catch (Throwable th) {
            if (getContext().getConfiguration().getIgnoreErrors()) {
                IrFactory irFactory2 = getContext().getIrFactory();
                KtDeclaration ktDeclaration2 = ktDeclaration;
                int startOffsetSkippingComments2 = PsiUtilsKt.getStartOffsetSkippingComments(ktDeclaration2);
                int endOffset2 = PsiUtilsKt.getEndOffset(ktDeclaration2);
                ReadOnlySlice<PsiElement, DeclarationDescriptor> DECLARATION_TO_DESCRIPTOR2 = BindingContext.DECLARATION_TO_DESCRIPTOR;
                Intrinsics.checkNotNullExpressionValue(DECLARATION_TO_DESCRIPTOR2, "DECLARATION_TO_DESCRIPTOR");
                return irFactory2.createErrorDeclaration(startOffsetSkippingComments2, endOffset2, (DeclarationDescriptor) GeneratorKt.getOrFail(this, DECLARATION_TO_DESCRIPTOR2, ktDeclaration));
            }
            if (th instanceof ErrorExpressionException) {
                Throwable cause = th.getCause();
                CodegenUtil.reportBackendException$default(cause == null ? th : cause, "psi2ir", PsiDiagnosticUtils.atLocation(th.getKtElement()), th.getMessage(), null, 16, null);
                throw null;
            }
            KtFile containingKtFile = ktDeclaration.getContainingKtFile();
            Intrinsics.checkNotNullExpressionValue(containingKtFile, "getContainingKtFile(...)");
            VirtualFile virtualFile = containingKtFile.getVirtualFile();
            CodegenUtil.reportBackendException$default(th, "psi2ir", (virtualFile == null || (path = virtualFile.getPath()) == null) ? containingKtFile.getName() : path, null, null, 24, null);
            throw null;
        }
    }

    public final IrClass generateSyntheticClassOrObject(KtPureClassOrObject syntheticDeclaration) {
        Intrinsics.checkNotNullParameter(syntheticDeclaration, "syntheticDeclaration");
        return generateClassOrObjectDeclaration(syntheticDeclaration);
    }

    public final IrDeclaration generateClassMemberDeclaration(KtDeclaration ktDeclaration, IrClass irClass, KtPureClassOrObject ktClassOrObject) {
        Intrinsics.checkNotNullParameter(ktDeclaration, "ktDeclaration");
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Intrinsics.checkNotNullParameter(ktClassOrObject, "ktClassOrObject");
        if (ktDeclaration instanceof KtAnonymousInitializer) {
            return new AnonymousInitializerGenerator(this).generateAnonymousInitializerDeclaration((KtAnonymousInitializer) ktDeclaration, irClass);
        }
        if (ktDeclaration instanceof KtSecondaryConstructor) {
            return new FunctionGenerator(this).generateSecondaryConstructor((KtSecondaryConstructor) ktDeclaration, ktClassOrObject);
        }
        if (ktDeclaration instanceof KtEnumEntry) {
            return generateEnumEntryDeclaration((KtEnumEntry) ktDeclaration);
        }
        return generateMemberDeclaration(ktDeclaration);
    }

    private final IrEnumEntry generateEnumEntryDeclaration(KtEnumEntry ktEnumEntry) {
        return new ClassGenerator(this).generateEnumEntry(ktEnumEntry);
    }

    public final IrClass generateClassOrObjectDeclaration(KtPureClassOrObject ktClassOrObject) {
        Intrinsics.checkNotNullParameter(ktClassOrObject, "ktClassOrObject");
        return ClassGenerator.generateClass$default(new ClassGenerator(this), ktClassOrObject, null, 2, null);
    }

    private final IrTypeAlias generateTypeAliasDeclaration(final KtTypeAlias ktTypeAlias) {
        WritableSlice<PsiElement, TypeAliasDescriptor> TYPE_ALIAS = BindingContext.TYPE_ALIAS;
        Intrinsics.checkNotNullExpressionValue(TYPE_ALIAS, "TYPE_ALIAS");
        final TypeAliasDescriptor typeAliasDescriptor = (TypeAliasDescriptor) GeneratorKt.getOrFail(this, TYPE_ALIAS, ktTypeAlias);
        DescriptorSymbolTableExtension descriptorExtension = getContext().getSymbolTable().getDescriptorExtension();
        Intrinsics.checkNotNull(typeAliasDescriptor);
        IrTypeAlias irTypeAliasDeclareTypeAlias = descriptorExtension.declareTypeAlias(typeAliasDescriptor, new Function1<IrTypeAliasSymbol, IrTypeAlias>() { // from class: org.jetbrains.kotlin.psi2ir.generators.DeclarationGenerator$generateTypeAliasDeclaration$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrTypeAlias invoke(IrTypeAliasSymbol symbol) {
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                IrFactory irFactory = this.this$0.getContext().getIrFactory();
                int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktTypeAlias);
                int endOffset = PsiUtilsKt.getEndOffset(ktTypeAlias);
                IrDeclarationOrigin.DEFINED defined = IrDeclarationOrigin.DEFINED.INSTANCE;
                Name name2 = typeAliasDescriptor.getShortName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                DescriptorVisibility visibility = typeAliasDescriptor.getVisibility();
                Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
                return irFactory.createTypeAlias(startOffsetSkippingComments, endOffset, defined, name2, visibility, symbol, typeAliasDescriptor.isActual(), this.this$0.toIrType(typeAliasDescriptor.getExpandedType()));
            }
        });
        List<TypeParameterDescriptor> declaredTypeParameters = typeAliasDescriptor.getDeclaredTypeParameters();
        Intrinsics.checkNotNullExpressionValue(declaredTypeParameters, "getDeclaredTypeParameters(...)");
        generateGlobalTypeParametersDeclarations(irTypeAliasDeclareTypeAlias, declaredTypeParameters);
        return irTypeAliasDeclareTypeAlias;
    }

    public final void generateGlobalTypeParametersDeclarations(IrTypeParametersContainer irTypeParametersOwner, List<? extends TypeParameterDescriptor> from) {
        Intrinsics.checkNotNullParameter(irTypeParametersOwner, "irTypeParametersOwner");
        Intrinsics.checkNotNullParameter(from, "from");
        generateTypeParameterDeclarations(irTypeParametersOwner, from, new Function3<Integer, Integer, TypeParameterDescriptor, IrTypeParameter>() { // from class: org.jetbrains.kotlin.psi2ir.generators.DeclarationGenerator.generateGlobalTypeParametersDeclarations.1
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ IrTypeParameter invoke(Integer num, Integer num2, TypeParameterDescriptor typeParameterDescriptor) {
                return invoke(num.intValue(), num2.intValue(), typeParameterDescriptor);
            }

            public final IrTypeParameter invoke(int i, int i2, TypeParameterDescriptor typeParameterDescriptor) {
                Intrinsics.checkNotNullParameter(typeParameterDescriptor, "typeParameterDescriptor");
                return DeclarationGenerator.this.getContext().getSymbolTable().getDescriptorExtension().declareGlobalTypeParameter(i, i2, IrDeclarationOrigin.DEFINED.INSTANCE, typeParameterDescriptor);
            }
        });
    }

    public final void generateScopedTypeParameterDeclarations(IrTypeParametersContainer irTypeParametersOwner, List<? extends TypeParameterDescriptor> from) {
        Intrinsics.checkNotNullParameter(irTypeParametersOwner, "irTypeParametersOwner");
        Intrinsics.checkNotNullParameter(from, "from");
        generateTypeParameterDeclarations(irTypeParametersOwner, from, new Function3<Integer, Integer, TypeParameterDescriptor, IrTypeParameter>() { // from class: org.jetbrains.kotlin.psi2ir.generators.DeclarationGenerator.generateScopedTypeParameterDeclarations.1
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ IrTypeParameter invoke(Integer num, Integer num2, TypeParameterDescriptor typeParameterDescriptor) {
                return invoke(num.intValue(), num2.intValue(), typeParameterDescriptor);
            }

            public final IrTypeParameter invoke(int i, int i2, TypeParameterDescriptor typeParameterDescriptor) {
                Intrinsics.checkNotNullParameter(typeParameterDescriptor, "typeParameterDescriptor");
                return SymbolTableExtension.declareScopedTypeParameter$default(DeclarationGenerator.this.getContext().getSymbolTable().getDescriptorExtension(), i, i2, IrDeclarationOrigin.DEFINED.INSTANCE, typeParameterDescriptor, null, 16, null);
            }
        });
    }

    private final void generateTypeParameterDeclarations(IrTypeParametersContainer irTypeParametersOwner, List<? extends TypeParameterDescriptor> from, Function3<? super Integer, ? super Integer, ? super TypeParameterDescriptor, ? extends IrTypeParameter> declareTypeParameter) {
        List<IrTypeParameter> typeParameters = irTypeParametersOwner.getTypeParameters();
        List<? extends TypeParameterDescriptor> list = from;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (TypeParameterDescriptor typeParameterDescriptor : list) {
            PsiElement sourceFromDescriptor = DescriptorToSourceUtils.getSourceFromDescriptor(typeParameterDescriptor);
            arrayList.add(declareTypeParameter.invoke(Integer.valueOf(KotlinUtilsKt.getStartOffsetOrUndefined(sourceFromDescriptor)), Integer.valueOf(KotlinUtilsKt.getEndOffsetOrUndefined(sourceFromDescriptor)), typeParameterDescriptor));
        }
        irTypeParametersOwner.setTypeParameters(CollectionsKt.plus((Collection) typeParameters, (Iterable) arrayList));
        for (IrTypeParameter irTypeParameter : irTypeParametersOwner.getTypeParameters()) {
            List<KotlinType> upperBounds = irTypeParameter.getDescriptor().getUpperBounds();
            Intrinsics.checkNotNullExpressionValue(upperBounds, "getUpperBounds(...)");
            List<KotlinType> list2 = upperBounds;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            for (KotlinType kotlinType : list2) {
                Intrinsics.checkNotNull(kotlinType);
                arrayList2.add(toIrType(kotlinType));
            }
            irTypeParameter.setSuperTypes(arrayList2);
        }
    }

    public final IrExpressionBody generateInitializerBody(IrSymbol scopeOwnerSymbol, KtExpression ktBody) {
        Intrinsics.checkNotNullParameter(scopeOwnerSymbol, "scopeOwnerSymbol");
        Intrinsics.checkNotNullParameter(ktBody, "ktBody");
        return DeclarationGeneratorKt.createBodyGenerator$default(this, scopeOwnerSymbol, null, 2, null).generateExpressionBody(ktBody);
    }

    public final IrDeclaration generateFakeOverrideDeclaration(CallableMemberDescriptor memberDescriptor, KtPureElement ktElement) {
        Intrinsics.checkNotNullParameter(memberDescriptor, "memberDescriptor");
        Intrinsics.checkNotNullParameter(ktElement, "ktElement");
        memberDescriptor.getKind();
        CallableMemberDescriptor.Kind kind = CallableMemberDescriptor.Kind.FAKE_OVERRIDE;
        if (memberDescriptor instanceof FunctionDescriptor) {
            return generateFakeOverrideFunction((FunctionDescriptor) memberDescriptor, ktElement);
        }
        if (memberDescriptor instanceof PropertyDescriptor) {
            return generateFakeOverrideProperty((PropertyDescriptor) memberDescriptor, ktElement);
        }
        throw new AssertionError("Unexpected member descriptor: " + memberDescriptor);
    }

    private final IrProperty generateFakeOverrideProperty(PropertyDescriptor propertyDescriptor, KtPureElement ktElement) {
        return new PropertyGenerator(this).generateFakeOverrideProperty(propertyDescriptor, ktElement);
    }

    private final IrSimpleFunction generateFakeOverrideFunction(FunctionDescriptor functionDescriptor, KtPureElement ktElement) {
        return new FunctionGenerator(this).generateFakeOverrideFunction(functionDescriptor, ktElement);
    }
}
