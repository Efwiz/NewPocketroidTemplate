package org.jetbrains.kotlin.ir.descriptors;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;

/* JADX INFO: compiled from: IrBasedDescriptors.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00028\u0000¢\u0006\u0002\u0010\u0005J\u000e\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0016J\n\u0010\t\u001a\u0004\u0018\u00010\nH\u0016J\u000e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0007H\u0016¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/ir/descriptors/IrBasedFunctionDescriptor;", "Function", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "Lorg/jetbrains/kotlin/ir/descriptors/IrBasedCallableDescriptor;", "owner", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)V", "getContextReceiverParameters", "", "Lorg/jetbrains/kotlin/ir/descriptors/IrBasedReceiverParameterDescriptor;", "getExtensionReceiverParameter", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "getValueParameters", "Lorg/jetbrains/kotlin/ir/descriptors/IrBasedValueParameterDescriptor;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrBasedFunctionDescriptor<Function extends IrFunction> extends IrBasedCallableDescriptor<Function> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrBasedFunctionDescriptor(Function owner) {
        super(owner);
        Intrinsics.checkNotNullParameter(owner, "owner");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedCallableDescriptor, org.jetbrains.kotlin.descriptors.CallableDescriptor
    public ReceiverParameterDescriptor getExtensionReceiverParameter() {
        IrValueParameter extensionReceiverParameter = ((IrFunction) getOwner()).getExtensionReceiverParameter();
        CallableDescriptor irBasedDescriptor = extensionReceiverParameter != null ? IrBasedDescriptorsKt.toIrBasedDescriptor(extensionReceiverParameter) : null;
        if (irBasedDescriptor instanceof ReceiverParameterDescriptor) {
            return (ReceiverParameterDescriptor) irBasedDescriptor;
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedCallableDescriptor, org.jetbrains.kotlin.descriptors.CallableDescriptor
    public List<IrBasedReceiverParameterDescriptor> getContextReceiverParameters() {
        return SequencesKt.toMutableList(SequencesKt.map(SequencesKt.take(CollectionsKt.asSequence(((IrFunction) getOwner()).getValueParameters()), ((IrFunction) getOwner()).getContextReceiverParametersCount()), AnonymousClass1.INSTANCE));
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.ir.descriptors.IrBasedFunctionDescriptor$getContextReceiverParameters$1, reason: invalid class name */
    /* JADX INFO: compiled from: IrBasedDescriptors.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function1<IrValueParameter, IrBasedReceiverParameterDescriptor> {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        AnonymousClass1() {
            super(1);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "<init>(Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;)V";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "<init>";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(IrBasedReceiverParameterDescriptor.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public final IrBasedReceiverParameterDescriptor invoke(IrValueParameter p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return new IrBasedReceiverParameterDescriptor(p0);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.descriptors.IrBasedCallableDescriptor, org.jetbrains.kotlin.descriptors.CallableDescriptor
    public List<IrBasedValueParameterDescriptor> getValueParameters() {
        return SequencesKt.toMutableList(SequencesKt.map(SequencesKt.drop(CollectionsKt.asSequence(((IrFunction) getOwner()).getValueParameters()), ((IrFunction) getOwner()).getContextReceiverParametersCount()), C47141.INSTANCE));
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.ir.descriptors.IrBasedFunctionDescriptor$getValueParameters$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: IrBasedDescriptors.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class C47141 extends FunctionReference implements Function1<IrValueParameter, IrBasedValueParameterDescriptor> {
        public static final C47141 INSTANCE = new C47141();

        C47141() {
            super(1);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "<init>(Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;)V";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "<init>";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(IrBasedValueParameterDescriptor.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public final IrBasedValueParameterDescriptor invoke(IrValueParameter p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return new IrBasedValueParameterDescriptor(p0);
        }
    }
}
