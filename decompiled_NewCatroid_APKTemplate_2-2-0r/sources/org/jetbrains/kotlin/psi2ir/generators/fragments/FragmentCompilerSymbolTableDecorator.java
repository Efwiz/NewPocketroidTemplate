package org.jetbrains.kotlin.psi2ir.generators.fragments;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.impl.AbstractReceiverParameterDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension;
import org.jetbrains.kotlin.ir.util.IdSignatureComposer;
import org.jetbrains.kotlin.ir.util.NameProvider;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.resolve.scopes.receivers.ContextReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExtensionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.resolve.scopes.receivers.ThisClassReceiver;

/* JADX INFO: compiled from: FragmentCompilerSymbolTableDecorator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0011B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\b\u0010\u000f\u001a\u00020\u0010H\u0014R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/fragments/FragmentCompilerSymbolTableDecorator;", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "signatureComposer", "Lorg/jetbrains/kotlin/ir/util/IdSignatureComposer;", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "fragmentInfo", "Lorg/jetbrains/kotlin/psi2ir/generators/fragments/EvaluatorFragmentInfo;", "nameProvider", "Lorg/jetbrains/kotlin/ir/util/NameProvider;", "(Lorg/jetbrains/kotlin/ir/util/IdSignatureComposer;Lorg/jetbrains/kotlin/ir/declarations/IrFactory;Lorg/jetbrains/kotlin/psi2ir/generators/fragments/EvaluatorFragmentInfo;Lorg/jetbrains/kotlin/ir/util/NameProvider;)V", "getFragmentInfo", "()Lorg/jetbrains/kotlin/psi2ir/generators/fragments/EvaluatorFragmentInfo;", "setFragmentInfo", "(Lorg/jetbrains/kotlin/psi2ir/generators/fragments/EvaluatorFragmentInfo;)V", "createDescriptorExtension", "Lorg/jetbrains/kotlin/ir/util/DescriptorSymbolTableExtension;", "ExtensionDecorator", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FragmentCompilerSymbolTableDecorator extends SymbolTable {
    private EvaluatorFragmentInfo fragmentInfo;

    public final EvaluatorFragmentInfo getFragmentInfo() {
        return this.fragmentInfo;
    }

    public final void setFragmentInfo(EvaluatorFragmentInfo evaluatorFragmentInfo) {
        this.fragmentInfo = evaluatorFragmentInfo;
    }

    public /* synthetic */ FragmentCompilerSymbolTableDecorator(IdSignatureComposer idSignatureComposer, IrFactory irFactory, EvaluatorFragmentInfo evaluatorFragmentInfo, NameProvider.DEFAULT r4, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(idSignatureComposer, irFactory, evaluatorFragmentInfo, (i & 8) != 0 ? NameProvider.DEFAULT.INSTANCE : r4);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FragmentCompilerSymbolTableDecorator(IdSignatureComposer signatureComposer, IrFactory irFactory, EvaluatorFragmentInfo evaluatorFragmentInfo, NameProvider nameProvider) {
        super(signatureComposer, irFactory, nameProvider);
        Intrinsics.checkNotNullParameter(signatureComposer, "signatureComposer");
        Intrinsics.checkNotNullParameter(irFactory, "irFactory");
        Intrinsics.checkNotNullParameter(nameProvider, "nameProvider");
        this.fragmentInfo = evaluatorFragmentInfo;
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolTable
    protected DescriptorSymbolTableExtension createDescriptorExtension() {
        return new ExtensionDecorator();
    }

    /* JADX INFO: compiled from: FragmentCompilerSymbolTableDecorator.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/fragments/FragmentCompilerSymbolTableDecorator$ExtensionDecorator;", "Lorg/jetbrains/kotlin/ir/util/DescriptorSymbolTableExtension;", "(Lorg/jetbrains/kotlin/psi2ir/generators/fragments/FragmentCompilerSymbolTableDecorator;)V", "referenceValue", "Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "value", "Lorg/jetbrains/kotlin/descriptors/ValueDescriptor;", "referenceValueParameter", "Lorg/jetbrains/kotlin/ir/symbols/IrValueParameterSymbol;", "declaration", "Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class ExtensionDecorator extends DescriptorSymbolTableExtension {
        public ExtensionDecorator() {
            super(FragmentCompilerSymbolTableDecorator.this);
        }

        @Override // org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension, org.jetbrains.kotlin.ir.util.SymbolTableExtension, org.jetbrains.kotlin.ir.util.ReferenceSymbolTableExtension
        public IrValueParameterSymbol referenceValueParameter(ParameterDescriptor declaration) {
            Function1<EvaluatorFragmentParameterInfo, Boolean> function1;
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            EvaluatorFragmentInfo fragmentInfo = FragmentCompilerSymbolTableDecorator.this.getFragmentInfo();
            if (fragmentInfo != null && (declaration instanceof ReceiverParameterDescriptor)) {
                final ReceiverValue value = ((ReceiverParameterDescriptor) declaration).getValue();
                Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
                if (value instanceof ExtensionReceiver ? true : value instanceof ContextReceiver) {
                    function1 = new Function1<EvaluatorFragmentParameterInfo, Boolean>() { // from class: org.jetbrains.kotlin.psi2ir.generators.fragments.FragmentCompilerSymbolTableDecorator$ExtensionDecorator$referenceValueParameter$finderPredicate$1
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Boolean invoke(EvaluatorFragmentParameterInfo evaluatorFragmentParameterInfo) {
                            Intrinsics.checkNotNullParameter(evaluatorFragmentParameterInfo, "<name for destructuring parameter 0>");
                            DeclarationDescriptor descriptor = evaluatorFragmentParameterInfo.getDescriptor();
                            ReceiverValue receiverValue = value;
                            ReceiverParameterDescriptor receiverParameterDescriptor = descriptor instanceof ReceiverParameterDescriptor ? (ReceiverParameterDescriptor) descriptor : null;
                            return Boolean.valueOf(Intrinsics.areEqual(receiverValue, receiverParameterDescriptor != null ? receiverParameterDescriptor.getValue() : null));
                        }
                    };
                } else if (value instanceof ThisClassReceiver) {
                    function1 = new Function1<EvaluatorFragmentParameterInfo, Boolean>() { // from class: org.jetbrains.kotlin.psi2ir.generators.fragments.FragmentCompilerSymbolTableDecorator$ExtensionDecorator$referenceValueParameter$finderPredicate$2
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Boolean invoke(EvaluatorFragmentParameterInfo evaluatorFragmentParameterInfo) {
                            Intrinsics.checkNotNullParameter(evaluatorFragmentParameterInfo, "<name for destructuring parameter 0>");
                            return Boolean.valueOf(Intrinsics.areEqual(((ThisClassReceiver) value).getClassDescriptor(), evaluatorFragmentParameterInfo.getDescriptor().getOriginal()));
                        }
                    };
                } else {
                    throw new NotImplementedError("An operation is not implemented: Unimplemented");
                }
                Function1<EvaluatorFragmentParameterInfo, Boolean> function2 = function1;
                Iterator<EvaluatorFragmentParameterInfo> it = fragmentInfo.getParameters().iterator();
                int i = 0;
                while (true) {
                    if (!it.hasNext()) {
                        i = -1;
                        break;
                    }
                    if (function2.invoke(it.next()).booleanValue()) {
                        break;
                    }
                    i++;
                }
                if (i > -1) {
                    ValueParameterDescriptor valueParameterDescriptor = fragmentInfo.getMethodDescriptor().getValueParameters().get(i);
                    Intrinsics.checkNotNullExpressionValue(valueParameterDescriptor, "get(...)");
                    return super.referenceValueParameter((ParameterDescriptor) valueParameterDescriptor);
                }
                return super.referenceValueParameter(declaration);
            }
            return super.referenceValueParameter(declaration);
        }

        @Override // org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension
        public IrValueSymbol referenceValue(final ValueDescriptor value) {
            Intrinsics.checkNotNullParameter(value, "value");
            EvaluatorFragmentInfo fragmentInfo = FragmentCompilerSymbolTableDecorator.this.getFragmentInfo();
            if (fragmentInfo == null) {
                return super.referenceValue(value);
            }
            Function1<EvaluatorFragmentParameterInfo, Boolean> function1 = value instanceof AbstractReceiverParameterDescriptor ? new Function1<EvaluatorFragmentParameterInfo, Boolean>() { // from class: org.jetbrains.kotlin.psi2ir.generators.fragments.FragmentCompilerSymbolTableDecorator$ExtensionDecorator$referenceValue$finderPredicate$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(EvaluatorFragmentParameterInfo evaluatorFragmentParameterInfo) {
                    Intrinsics.checkNotNullParameter(evaluatorFragmentParameterInfo, "<name for destructuring parameter 0>");
                    return Boolean.valueOf(Intrinsics.areEqual(((AbstractReceiverParameterDescriptor) value).getContainingDeclaration(), evaluatorFragmentParameterInfo.getDescriptor()));
                }
            } : new Function1<EvaluatorFragmentParameterInfo, Boolean>() { // from class: org.jetbrains.kotlin.psi2ir.generators.fragments.FragmentCompilerSymbolTableDecorator$ExtensionDecorator$referenceValue$finderPredicate$2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(EvaluatorFragmentParameterInfo evaluatorFragmentParameterInfo) {
                    Intrinsics.checkNotNullParameter(evaluatorFragmentParameterInfo, "<name for destructuring parameter 0>");
                    return Boolean.valueOf(Intrinsics.areEqual(evaluatorFragmentParameterInfo.getDescriptor(), value));
                }
            };
            Iterator<EvaluatorFragmentParameterInfo> it = fragmentInfo.getParameters().iterator();
            int i = 0;
            while (true) {
                if (!it.hasNext()) {
                    i = -1;
                    break;
                }
                if (function1.invoke(it.next()).booleanValue()) {
                    break;
                }
                i++;
            }
            if (i > -1) {
                ValueParameterDescriptor valueParameterDescriptor = fragmentInfo.getMethodDescriptor().getValueParameters().get(i);
                Intrinsics.checkNotNullExpressionValue(valueParameterDescriptor, "get(...)");
                return super.referenceValueParameter((ParameterDescriptor) valueParameterDescriptor);
            }
            return super.referenceValue(value);
        }
    }
}
