package org.jetbrains.kotlin.backend.common.serialization;

import java.util.LinkedHashSet;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.backend.common.serialization.encodings.BinarySymbolData;
import org.jetbrains.kotlin.backend.common.serialization.proto.Actual;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.symbols.IrFileSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.SymbolTable;

/* JADX INFO: compiled from: IrFileDeserializer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001BM\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\r\u0012\u0006\u0010\u000f\u001a\u00020\r\u0012\u0006\u0010\u0010\u001a\u00020\u0011¢\u0006\u0002\u0010\u0012J\u000e\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020%J\u0006\u0010.\u001a\u00020,J\u0006\u0010/\u001a\u00020,R\u0011\u0010\u0013\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0019\u001a\u00020\u001a¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u001e\u0010#\u001a\u0012\u0012\u0004\u0012\u00020%0$j\b\u0012\u0004\u0012\u00020%`&X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010'\u001a\u00020(¢\u0006\b\n\u0000\u001a\u0004\b)\u0010*¨\u00060"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/FileDeserializationState;", "", "linker", "Lorg/jetbrains/kotlin/backend/common/serialization/KotlinIrLinker;", "fileIndex", "", "file", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "fileReader", "Lorg/jetbrains/kotlin/backend/common/serialization/IrLibraryFileFromBytes;", "fileProto", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrFile;", "deserializeBodies", "", "allowErrorNodes", "deserializeInlineFunctions", "moduleDeserializer", "Lorg/jetbrains/kotlin/backend/common/serialization/IrModuleDeserializer;", "(Lorg/jetbrains/kotlin/backend/common/serialization/KotlinIrLinker;ILorg/jetbrains/kotlin/ir/declarations/IrFile;Lorg/jetbrains/kotlin/backend/common/serialization/IrLibraryFileFromBytes;Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrFile;ZZZLorg/jetbrains/kotlin/backend/common/serialization/IrModuleDeserializer;)V", "declarationDeserializer", "Lorg/jetbrains/kotlin/backend/common/serialization/IrDeclarationDeserializer;", "getDeclarationDeserializer", "()Lorg/jetbrains/kotlin/backend/common/serialization/IrDeclarationDeserializer;", "getFile", "()Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "fileDeserializer", "Lorg/jetbrains/kotlin/backend/common/serialization/IrFileDeserializer;", "getFileDeserializer", "()Lorg/jetbrains/kotlin/backend/common/serialization/IrFileDeserializer;", "getFileIndex", "()I", "getFileReader", "()Lorg/jetbrains/kotlin/backend/common/serialization/IrLibraryFileFromBytes;", "getLinker", "()Lorg/jetbrains/kotlin/backend/common/serialization/KotlinIrLinker;", "reachableTopLevels", "Ljava/util/LinkedHashSet;", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "Lkotlin/collections/LinkedHashSet;", "symbolDeserializer", "Lorg/jetbrains/kotlin/backend/common/serialization/IrSymbolDeserializer;", "getSymbolDeserializer", "()Lorg/jetbrains/kotlin/backend/common/serialization/IrSymbolDeserializer;", "addIdSignature", "", ASTExpr.DEFAULT_MAP_KEY_NAME, "deserializeAllFileReachableTopLevel", "enqueueAllDeclarations", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FileDeserializationState {
    private final IrDeclarationDeserializer declarationDeserializer;
    private final IrFile file;
    private final IrFileDeserializer fileDeserializer;
    private final int fileIndex;
    private final IrLibraryFileFromBytes fileReader;
    private final KotlinIrLinker linker;
    private final LinkedHashSet<IdSignature> reachableTopLevels;
    private final IrSymbolDeserializer symbolDeserializer;

    public FileDeserializationState(KotlinIrLinker linker, int i, IrFile file, IrLibraryFileFromBytes fileReader, org.jetbrains.kotlin.backend.common.serialization.proto.IrFile fileProto, boolean z, boolean z2, boolean z3, final IrModuleDeserializer moduleDeserializer) {
        Intrinsics.checkNotNullParameter(linker, "linker");
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(fileReader, "fileReader");
        Intrinsics.checkNotNullParameter(fileProto, "fileProto");
        Intrinsics.checkNotNullParameter(moduleDeserializer, "moduleDeserializer");
        this.linker = linker;
        this.fileIndex = i;
        this.file = file;
        this.fileReader = fileReader;
        SymbolTable symbolTable = linker.getSymbolTable();
        IrFileSymbol symbol = file.getSymbol();
        List<Actual> actualList = fileProto.getActualList();
        FileDeserializationState$symbolDeserializer$1 fileDeserializationState$symbolDeserializer$1 = new FileDeserializationState$symbolDeserializer$1(this);
        FileDeserializationState$symbolDeserializer$2 fileDeserializationState$symbolDeserializer$2 = new FileDeserializationState$symbolDeserializer$2(linker);
        Function3<IrSymbolDeserializer, IrSymbol, IdSignature, IrSymbol> symbolProcessor = linker.getSymbolProcessor();
        IrInterningService internationService = linker.getInternationService();
        Intrinsics.checkNotNull(actualList);
        IrSymbolDeserializer irSymbolDeserializer = new IrSymbolDeserializer(symbolTable, fileReader, symbol, actualList, fileDeserializationState$symbolDeserializer$1, fileDeserializationState$symbolDeserializer$2, internationService, symbolProcessor, null, new Function2<IdSignature, BinarySymbolData.SymbolKind, IrSymbol>() { // from class: org.jetbrains.kotlin.backend.common.serialization.FileDeserializationState$symbolDeserializer$3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final IrSymbol invoke(IdSignature idSignature, BinarySymbolData.SymbolKind symbolKind) {
                Intrinsics.checkNotNullParameter(idSignature, "idSignature");
                Intrinsics.checkNotNullParameter(symbolKind, "symbolKind");
                return this.this$0.getLinker().deserializeOrReturnUnboundIrSymbolIfPartialLinkageEnabled(idSignature, symbolKind, moduleDeserializer);
            }
        }, 256, null);
        this.symbolDeserializer = irSymbolDeserializer;
        IrDeclarationDeserializer irDeclarationDeserializer = new IrDeclarationDeserializer(linker.getBuiltIns(), linker.getSymbolTable(), linker.getSymbolTable().getIrFactory(), fileReader, file, z2, z3, z, irSymbolDeserializer, linker.getFakeOverrideBuilder().getPlatformSpecificClassFilter(), linker.getFakeOverrideBuilder(), moduleDeserializer.getCompatibilityMode(), linker.getPartialLinkageSupport().isEnabled(), linker.getInternationService());
        this.declarationDeserializer = irDeclarationDeserializer;
        this.fileDeserializer = new IrFileDeserializer(file, fileReader, fileProto, irSymbolDeserializer, irDeclarationDeserializer);
        this.reachableTopLevels = new LinkedHashSet<>();
        List<Long> explicitlyExportedToCompilerList = fileProto.getExplicitlyExportedToCompilerList();
        Intrinsics.checkNotNullExpressionValue(explicitlyExportedToCompilerList, "getExplicitlyExportedToCompilerList(...)");
        for (Long l : explicitlyExportedToCompilerList) {
            IrSymbolDeserializer irSymbolDeserializer2 = this.symbolDeserializer;
            Intrinsics.checkNotNull(l);
            IdSignature idSignatureDeserializeIdSignature = this.symbolDeserializer.deserializeIdSignature(BinarySymbolData.m6206getSignatureIdimpl(irSymbolDeserializer2.m6140parseSymbolData9x8F8T0(l.longValue())));
            idSignatureDeserializeIdSignature.isPackageSignature();
            addIdSignature(idSignatureDeserializeIdSignature.topLevelSignature());
        }
    }

    public final KotlinIrLinker getLinker() {
        return this.linker;
    }

    public final int getFileIndex() {
        return this.fileIndex;
    }

    public final IrFile getFile() {
        return this.file;
    }

    public final IrLibraryFileFromBytes getFileReader() {
        return this.fileReader;
    }

    public final IrSymbolDeserializer getSymbolDeserializer() {
        return this.symbolDeserializer;
    }

    public final IrDeclarationDeserializer getDeclarationDeserializer() {
        return this.declarationDeserializer;
    }

    public final IrFileDeserializer getFileDeserializer() {
        return this.fileDeserializer;
    }

    public final void addIdSignature(IdSignature key) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.reachableTopLevels.add(key);
    }

    public final void enqueueAllDeclarations() {
        this.reachableTopLevels.addAll(this.fileDeserializer.getReversedSignatureIndex().keySet());
    }

    public final void deserializeAllFileReachableTopLevel() {
        while (!this.reachableTopLevels.isEmpty()) {
            IdSignature idSignature = (IdSignature) CollectionsKt.first(this.reachableTopLevels);
            IrSymbol irSymbol = this.symbolDeserializer.getDeserializedSymbols().get(idSignature);
            if (irSymbol == null || !irSymbol.isBound()) {
                this.fileDeserializer.deserializeDeclaration(idSignature);
            }
            this.reachableTopLevels.remove(idSignature);
        }
    }
}
