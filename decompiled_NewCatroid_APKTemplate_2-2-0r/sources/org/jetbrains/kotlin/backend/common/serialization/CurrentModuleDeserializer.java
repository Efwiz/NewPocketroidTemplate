package org.jetbrains.kotlin.backend.common.serialization;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.encodings.BinarySymbolData;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.library.KotlinAbiVersion;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrModuleDeserializer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0011\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0096\u0002J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0018\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u001a\u001a\u00020\u001bH\u0016R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/CurrentModuleDeserializer;", "Lorg/jetbrains/kotlin/backend/common/serialization/IrModuleDeserializer;", "moduleFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "moduleDependencies", "", "(Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;Ljava/util/Collection;)V", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/backend/common/serialization/IrModuleDeserializerKind;", "getKind", "()Lorg/jetbrains/kotlin/backend/common/serialization/IrModuleDeserializerKind;", "getModuleDependencies", "()Ljava/util/Collection;", "getModuleFragment", "()Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "contains", "", "idSig", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "declareIrSymbol", "", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "deserializedSymbolNotFound", "", "tryDeserializeIrSymbol", "symbolKind", "Lorg/jetbrains/kotlin/backend/common/serialization/encodings/BinarySymbolData$SymbolKind;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class CurrentModuleDeserializer extends IrModuleDeserializer {
    private final Collection<IrModuleDeserializer> moduleDependencies;
    private final IrModuleFragment moduleFragment;

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public boolean contains(IdSignature idSig) {
        Intrinsics.checkNotNullParameter(idSig, "idSig");
        return false;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public void declareIrSymbol(IrSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    /* JADX INFO: renamed from: tryDeserializeIrSymbol, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ IrSymbol mo6122tryDeserializeIrSymbol(IdSignature idSignature, BinarySymbolData.SymbolKind symbolKind) {
        return (IrSymbol) tryDeserializeIrSymbol(idSignature, symbolKind);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public IrModuleFragment getModuleFragment() {
        return this.moduleFragment;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public Collection<IrModuleDeserializer> getModuleDependencies() {
        return this.moduleDependencies;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public CurrentModuleDeserializer(IrModuleFragment moduleFragment, Collection<? extends IrModuleDeserializer> moduleDependencies) {
        super(moduleFragment.getDescriptor(), KotlinAbiVersion.INSTANCE.getCURRENT());
        Intrinsics.checkNotNullParameter(moduleFragment, "moduleFragment");
        Intrinsics.checkNotNullParameter(moduleDependencies, "moduleDependencies");
        this.moduleFragment = moduleFragment;
        this.moduleDependencies = moduleDependencies;
    }

    public Void tryDeserializeIrSymbol(IdSignature idSig, BinarySymbolData.SymbolKind symbolKind) {
        Intrinsics.checkNotNullParameter(idSig, "idSig");
        Intrinsics.checkNotNullParameter(symbolKind, "symbolKind");
        throw new IllegalStateException(("Unreachable execution: there could not be back-links (sig: " + idSig + ')').toString());
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public Void deserializedSymbolNotFound(IdSignature idSig) {
        Intrinsics.checkNotNullParameter(idSig, "idSig");
        throw new IllegalStateException(("Unreachable execution: there could not be back-links (sig: " + idSig + ')').toString());
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public IrModuleDeserializerKind getKind() {
        return IrModuleDeserializerKind.CURRENT;
    }
}
