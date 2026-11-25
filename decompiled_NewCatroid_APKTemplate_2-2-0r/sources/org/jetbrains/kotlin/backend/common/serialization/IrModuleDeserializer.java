package org.jetbrains.kotlin.backend.common.serialization;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.serialization.encodings.BinarySymbolData;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.library.IrLibrary;
import org.jetbrains.kotlin.library.KotlinAbiVersion;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrModuleDeserializer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u009c\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u001e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0017\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020)H\u0016J\u0011\u0010*\u001a\u00020+2\u0006\u0010(\u001a\u00020)H¦\u0002J\u0010\u0010,\u001a\u00020'2\u0006\u0010-\u001a\u00020.H\u0016J\b\u0010/\u001a\u00020'H\u0016J\u0010\u00100\u001a\u0002012\u0006\u0010(\u001a\u00020)H&J\u000e\u00102\u001a\b\u0012\u0004\u0012\u0002030\u0016H\u0016J\b\u00104\u001a\u00020'H\u0016J\u0010\u00104\u001a\u00020'2\u0006\u00105\u001a\u00020\u0000H\u0016J\b\u00106\u001a\u00020'H\u0016J\u0018\u00107\u001a\u0002082\u0006\u00109\u001a\u00020:2\u0006\u0010;\u001a\u00020)H\u0016J\u0018\u0010<\u001a\u00020=2\u0006\u00109\u001a\u00020:2\u0006\u0010;\u001a\u00020)H\u0016J\u0010\u0010>\u001a\u00020?2\u0006\u0010@\u001a\u00020\"H\u0016J\u001a\u0010A\u001a\u0004\u0018\u00010.2\u0006\u0010(\u001a\u00020)2\u0006\u0010B\u001a\u00020CH&R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0012\u0010\u000b\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0018\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00000\u0016X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0019\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u0012\u0010\u001c\u001a\u00020\u001dX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001fR \u0010 \u001a\u000e\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020#0!X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010%¨\u0006D"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/IrModuleDeserializer;", "", "_moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "libraryAbiVersion", "Lorg/jetbrains/kotlin/library/KotlinAbiVersion;", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/library/KotlinAbiVersion;)V", "compatibilityMode", "Lorg/jetbrains/kotlin/backend/common/serialization/CompatibilityMode;", "getCompatibilityMode", "()Lorg/jetbrains/kotlin/backend/common/serialization/CompatibilityMode;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/backend/common/serialization/IrModuleDeserializerKind;", "getKind", "()Lorg/jetbrains/kotlin/backend/common/serialization/IrModuleDeserializerKind;", "klib", "Lorg/jetbrains/kotlin/library/IrLibrary;", "getKlib", "()Lorg/jetbrains/kotlin/library/IrLibrary;", "getLibraryAbiVersion", "()Lorg/jetbrains/kotlin/library/KotlinAbiVersion;", "moduleDependencies", "", "getModuleDependencies", "()Ljava/util/Collection;", "moduleDescriptor", "getModuleDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "moduleFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "getModuleFragment", "()Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "strategyResolver", "Lkotlin/Function1;", "", "Lorg/jetbrains/kotlin/backend/common/serialization/DeserializationStrategy;", "getStrategyResolver", "()Lkotlin/jvm/functions/Function1;", "addModuleReachableTopLevel", "", "idSig", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "contains", "", "declareIrSymbol", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "deserializeReachableDeclarations", "deserializedSymbolNotFound", "", "fileDeserializers", "Lorg/jetbrains/kotlin/backend/common/serialization/IrFileDeserializer;", ConfigConstants.CONFIG_INIT_SECTION, MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "postProcess", "referencePropertyByLocalSignature", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "file", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "idSignature", "referenceSimpleFunctionByLocalSignature", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "signatureDeserializerForFile", "Lorg/jetbrains/kotlin/backend/common/serialization/IdSignatureDeserializer;", PsiTreeChangeEvent.PROP_FILE_NAME, "tryDeserializeIrSymbol", "symbolKind", "Lorg/jetbrains/kotlin/backend/common/serialization/encodings/BinarySymbolData$SymbolKind;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrModuleDeserializer {
    private final ModuleDescriptor _moduleDescriptor;
    private final KotlinAbiVersion libraryAbiVersion;
    private final Function1<String, DeserializationStrategy> strategyResolver;

    public abstract boolean contains(IdSignature idSig);

    public abstract Void deserializedSymbolNotFound(IdSignature idSig);

    public abstract IrModuleDeserializerKind getKind();

    public abstract Collection<IrModuleDeserializer> getModuleDependencies();

    public final ModuleDescriptor getModuleDescriptor() {
        ModuleDescriptor moduleDescriptor = this._moduleDescriptor;
        if (moduleDescriptor != null) {
            return moduleDescriptor;
        }
        throw new IllegalStateException("No ModuleDescriptor provided".toString());
    }

    public abstract IrModuleFragment getModuleFragment();

    public void init(IrModuleDeserializer delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
    }

    public void postProcess() {
    }

    /* JADX INFO: renamed from: tryDeserializeIrSymbol */
    public abstract IrSymbol mo6122tryDeserializeIrSymbol(IdSignature idSig, BinarySymbolData.SymbolKind symbolKind);

    public IrModuleDeserializer(ModuleDescriptor moduleDescriptor, KotlinAbiVersion libraryAbiVersion) {
        Intrinsics.checkNotNullParameter(libraryAbiVersion, "libraryAbiVersion");
        this._moduleDescriptor = moduleDescriptor;
        this.libraryAbiVersion = libraryAbiVersion;
        this.strategyResolver = new Function1<String, DeserializationStrategy>() { // from class: org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer$strategyResolver$1
            @Override // kotlin.jvm.functions.Function1
            public final DeserializationStrategy invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return DeserializationStrategy.ONLY_DECLARATION_HEADERS;
            }
        };
    }

    public final KotlinAbiVersion getLibraryAbiVersion() {
        return this.libraryAbiVersion;
    }

    public IrSimpleFunctionSymbol referenceSimpleFunctionByLocalSignature(IrFile file, IdSignature idSignature) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(idSignature, "idSignature");
        throw new IllegalStateException("Unsupported operation".toString());
    }

    public IrPropertySymbol referencePropertyByLocalSignature(IrFile file, IdSignature idSignature) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(idSignature, "idSignature");
        throw new IllegalStateException("Unsupported operation".toString());
    }

    public void declareIrSymbol(IrSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        IdSignature signature = symbol.getSignature();
        if (signature == null) {
            throw new IllegalArgumentException(("Symbol is not public API: " + symbol.getDescriptor()).toString());
        }
        symbol.getHasDescriptor();
        IrModuleDeserializerKt.deserializeIrSymbolOrFail(this, signature, IrModuleDeserializerKt.kind(symbol));
    }

    public IrLibrary getKlib() {
        throw new IllegalStateException("Unsupported operation".toString());
    }

    public void init() {
        init(this);
    }

    public void addModuleReachableTopLevel(IdSignature idSig) {
        Intrinsics.checkNotNullParameter(idSig, "idSig");
        throw new IllegalStateException(("Unsupported Operation (sig: " + idSig).toString());
    }

    public void deserializeReachableDeclarations() {
        throw new IllegalStateException("Unsupported Operation".toString());
    }

    public Function1<String, DeserializationStrategy> getStrategyResolver() {
        return this.strategyResolver;
    }

    public Collection<IrFileDeserializer> fileDeserializers() {
        throw new IllegalStateException("Unsupported".toString());
    }

    public final CompatibilityMode getCompatibilityMode() {
        return new CompatibilityMode(this.libraryAbiVersion);
    }

    public IdSignatureDeserializer signatureDeserializerForFile(String fileName) {
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        throw new IllegalStateException("Unsupported".toString());
    }
}
