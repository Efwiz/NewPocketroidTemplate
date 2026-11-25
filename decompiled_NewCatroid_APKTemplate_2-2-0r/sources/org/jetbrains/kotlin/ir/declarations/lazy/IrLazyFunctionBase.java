package org.jetbrains.kotlin.ir.declarations.lazy;

import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.properties.ReadWriteProperty;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrValueParameterSymbolImpl;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.DeclarationStubGenerator;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: IrLazyFunctionBase.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u00012\u00020\u0002J\u0010\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\u000eH\u0016J\u001e\u0010\u000f\u001a\u0004\u0018\u00010\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\b\b\u0002\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\u000e\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00100\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u001aH\u0016R\u0018\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\f\u0012\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u0004\u0018\u00010\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/lazy/IrLazyFunctionBase;", "Lorg/jetbrains/kotlin/ir/declarations/lazy/IrLazyDeclarationBase;", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParametersContainer;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getDescriptor$annotations", "()V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "initialSignatureFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "getInitialSignatureFunction", "()Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "createInitialSignatureFunction", "Lkotlin/Lazy;", "createReceiverParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "parameter", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "functionDispatchReceiver", "", "createReturnType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "createValueParameters", "", "getTopLevelDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrLazyFunctionBase extends IrTypeParametersContainer, IrLazyDeclarationBase {
    Lazy<IrFunction> createInitialSignatureFunction();

    IrValueParameter createReceiverParameter(ReceiverParameterDescriptor parameter, boolean functionDispatchReceiver);

    IrType createReturnType();

    List<IrValueParameter> createValueParameters();

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    FunctionDescriptor getDescriptor();

    IrFunction getInitialSignatureFunction();

    IrDeclaration getTopLevelDeclaration();

    /* JADX INFO: compiled from: IrLazyFunctionBase.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static /* synthetic */ void getDescriptor$annotations() {
        }

        public static ReadWriteProperty<Object, List<IrConstructorCall>> createLazyAnnotations(IrLazyFunctionBase irLazyFunctionBase) {
            return IrLazyDeclarationBase.DefaultImpls.createLazyAnnotations(irLazyFunctionBase);
        }

        public static ReadWriteProperty<Object, IrDeclarationParent> createLazyParent(IrLazyFunctionBase irLazyFunctionBase) {
            return IrLazyDeclarationBase.DefaultImpls.createLazyParent(irLazyFunctionBase);
        }

        public static IrValueParameter generateReceiverParameterStub(IrLazyFunctionBase irLazyFunctionBase, ReceiverParameterDescriptor receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return IrLazyDeclarationBase.DefaultImpls.generateReceiverParameterStub(irLazyFunctionBase, receiver);
        }

        public static IrFactory getFactory(IrLazyFunctionBase irLazyFunctionBase) {
            return IrLazyDeclarationBase.DefaultImpls.getFactory(irLazyFunctionBase);
        }

        public static IrDeclarationParent lazyParent(IrLazyFunctionBase irLazyFunctionBase) {
            return IrLazyDeclarationBase.DefaultImpls.lazyParent(irLazyFunctionBase);
        }

        public static IrType toIrType(IrLazyFunctionBase irLazyFunctionBase, KotlinType receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return IrLazyDeclarationBase.DefaultImpls.toIrType(irLazyFunctionBase, receiver);
        }

        public static IrDeclaration getTopLevelDeclaration(IrLazyFunctionBase irLazyFunctionBase) {
            IrDeclarationParent irDeclarationParent = irLazyFunctionBase;
            while (true) {
                IrDeclaration irDeclaration = (IrDeclaration) irDeclarationParent;
                if (irDeclaration.getParent() instanceof IrPackageFragment) {
                    return irDeclaration;
                }
                IrDeclarationParent parent = irDeclaration.getParent();
                Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
                irDeclarationParent = parent;
            }
        }

        public static Lazy<IrFunction> createInitialSignatureFunction(final IrLazyFunctionBase irLazyFunctionBase) {
            return LazyKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, (Function0) new Function0<IrSimpleFunction>() { // from class: org.jetbrains.kotlin.ir.declarations.lazy.IrLazyFunctionBase.createInitialSignatureFunction.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrSimpleFunction invoke() {
                    FunctionDescriptor initialSignatureDescriptor = IrLazyFunctionBase.this.getDescriptor().getInitialSignatureDescriptor();
                    if (initialSignatureDescriptor == null || Intrinsics.areEqual(initialSignatureDescriptor, IrLazyFunctionBase.this.getDescriptor())) {
                        return null;
                    }
                    DeclarationStubGenerator stubGenerator = IrLazyFunctionBase.this.getStubGenerator();
                    FunctionDescriptor original = initialSignatureDescriptor.getOriginal();
                    Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
                    return DeclarationStubGenerator.generateFunctionStub$default(stubGenerator, original, false, 2, null);
                }
            });
        }

        public static List<IrValueParameter> createValueParameters(final IrLazyFunctionBase irLazyFunctionBase) {
            return (List) irLazyFunctionBase.getTypeTranslator().buildWithScope(irLazyFunctionBase, new Function0<ArrayList<IrValueParameter>>() { // from class: org.jetbrains.kotlin.ir.declarations.lazy.IrLazyFunctionBase.createValueParameters.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final ArrayList<IrValueParameter> invoke() {
                    ArrayList<IrValueParameter> arrayList = new ArrayList<>();
                    List<ReceiverParameterDescriptor> contextReceiverParameters = IrLazyFunctionBase.this.getDescriptor().getContextReceiverParameters();
                    Intrinsics.checkNotNullExpressionValue(contextReceiverParameters, "getContextReceiverParameters(...)");
                    IrLazyFunctionBase irLazyFunctionBase2 = IrLazyFunctionBase.this;
                    int i = 0;
                    for (Object obj : contextReceiverParameters) {
                        ArrayList<IrValueParameter> arrayList2 = arrayList;
                        int i2 = i + 1;
                        if (i < 0) {
                            CollectionsKt.throwIndexOverflow();
                        }
                        ReceiverParameterDescriptor receiverParameterDescriptor = (ReceiverParameterDescriptor) obj;
                        IrFactory factory = irLazyFunctionBase2.getFactory();
                        IrDeclarationOrigin origin = irLazyFunctionBase2.getOrigin();
                        Name nameIdentifier = Name.identifier("contextReceiverParameter" + i);
                        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                        KotlinType type = receiverParameterDescriptor.getType();
                        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                        IrValueParameter irValueParameterCreateValueParameter = factory.createValueParameter(-1, -1, origin, nameIdentifier, irLazyFunctionBase2.toIrType(type), false, (IrValueParameterSymbol) new IrValueParameterSymbolImpl(receiverParameterDescriptor), i, (IrType) null, false, false, false);
                        irValueParameterCreateValueParameter.setParent(irLazyFunctionBase2);
                        arrayList2.add(irValueParameterCreateValueParameter);
                        i = i2;
                    }
                    ArrayList<IrValueParameter> arrayList3 = arrayList;
                    List<ValueParameterDescriptor> valueParameters = IrLazyFunctionBase.this.getDescriptor().getValueParameters();
                    Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
                    IrLazyFunctionBase irLazyFunctionBase3 = IrLazyFunctionBase.this;
                    for (ValueParameterDescriptor valueParameterDescriptor : valueParameters) {
                        DeclarationStubGenerator stubGenerator = irLazyFunctionBase3.getStubGenerator();
                        Intrinsics.checkNotNull(valueParameterDescriptor);
                        IrValueParameter irValueParameterGenerateValueParameterStub$ir_tree = stubGenerator.generateValueParameterStub$ir_tree(valueParameterDescriptor, valueParameterDescriptor.getIndex() + irLazyFunctionBase3.getDescriptor().getContextReceiverParameters().size());
                        irValueParameterGenerateValueParameterStub$ir_tree.setParent(irLazyFunctionBase3);
                        arrayList3.add(irValueParameterGenerateValueParameterStub$ir_tree);
                    }
                    return arrayList3;
                }
            });
        }

        public static /* synthetic */ IrValueParameter createReceiverParameter$default(IrLazyFunctionBase irLazyFunctionBase, ReceiverParameterDescriptor receiverParameterDescriptor, boolean z, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: createReceiverParameter");
            }
            if ((i & 2) != 0) {
                z = false;
            }
            return irLazyFunctionBase.createReceiverParameter(receiverParameterDescriptor, z);
        }

        public static IrValueParameter createReceiverParameter(final IrLazyFunctionBase irLazyFunctionBase, final ReceiverParameterDescriptor receiverParameterDescriptor, boolean z) {
            if (z && irLazyFunctionBase.getStubGenerator().getExtensions().isStaticFunction(irLazyFunctionBase.getDescriptor())) {
                return null;
            }
            return (IrValueParameter) irLazyFunctionBase.getTypeTranslator().buildWithScope(irLazyFunctionBase, new Function0<IrValueParameter>() { // from class: org.jetbrains.kotlin.ir.declarations.lazy.IrLazyFunctionBase.createReceiverParameter.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrValueParameter invoke() {
                    IrValueParameter irValueParameterGenerateReceiverParameterStub;
                    ReceiverParameterDescriptor receiverParameterDescriptor2 = receiverParameterDescriptor;
                    if (receiverParameterDescriptor2 == null || (irValueParameterGenerateReceiverParameterStub = irLazyFunctionBase.generateReceiverParameterStub(receiverParameterDescriptor2)) == null) {
                        return null;
                    }
                    irValueParameterGenerateReceiverParameterStub.setParent(irLazyFunctionBase);
                    return irValueParameterGenerateReceiverParameterStub;
                }
            });
        }

        public static IrType createReturnType(final IrLazyFunctionBase irLazyFunctionBase) {
            return (IrType) irLazyFunctionBase.getTypeTranslator().buildWithScope(irLazyFunctionBase, new Function0<IrType>() { // from class: org.jetbrains.kotlin.ir.declarations.lazy.IrLazyFunctionBase.createReturnType.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrType invoke() {
                    IrLazyFunctionBase irLazyFunctionBase2 = IrLazyFunctionBase.this;
                    KotlinType returnType = irLazyFunctionBase2.getDescriptor().getReturnType();
                    Intrinsics.checkNotNull(returnType);
                    return irLazyFunctionBase2.toIrType(returnType);
                }
            });
        }
    }
}
