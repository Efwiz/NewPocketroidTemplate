package org.jetbrains.kotlin.contracts.description;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.contracts.interpretation.ContractInterpretationDispatcher;
import org.jetbrains.kotlin.contracts.model.Functor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.storage.NullableLazyValue;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: ContractDescription.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B#\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0010\u0010\u0011\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0012\u001a\u00020\u0013R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/contracts/description/ContractDescription;", "", "effects", "", "Lorg/jetbrains/kotlin/contracts/description/EffectDeclaration;", "ownerFunction", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "(Ljava/util/List;Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;Lorg/jetbrains/kotlin/storage/StorageManager;)V", "computeFunctor", "Lorg/jetbrains/kotlin/storage/NullableLazyValue;", "Lorg/jetbrains/kotlin/contracts/model/Functor;", "getEffects", "()Ljava/util/List;", "getOwnerFunction", "()Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getFunctor", "usageModule", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class ContractDescription {
    private final NullableLazyValue<Functor> computeFunctor;
    private final List<EffectDeclaration> effects;
    private final FunctionDescriptor ownerFunction;

    /* JADX WARN: Multi-variable type inference failed */
    public ContractDescription(List<? extends EffectDeclaration> effects, FunctionDescriptor ownerFunction, StorageManager storageManager) {
        Intrinsics.checkNotNullParameter(effects, "effects");
        Intrinsics.checkNotNullParameter(ownerFunction, "ownerFunction");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        this.effects = effects;
        this.ownerFunction = ownerFunction;
        this.computeFunctor = storageManager.createNullableLazyValue(new Function0<Functor>() { // from class: org.jetbrains.kotlin.contracts.description.ContractDescription$computeFunctor$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Functor invoke() {
                return new ContractInterpretationDispatcher().convertContractDescriptorToFunctor(this.this$0);
            }
        });
    }

    public final List<EffectDeclaration> getEffects() {
        return this.effects;
    }

    public final FunctionDescriptor getOwnerFunction() {
        return this.ownerFunction;
    }

    public final Functor getFunctor(ModuleDescriptor usageModule) {
        Intrinsics.checkNotNullParameter(usageModule, "usageModule");
        return this.computeFunctor.invoke();
    }
}
