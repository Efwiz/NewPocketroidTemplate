package org.jetbrains.kotlin.ir.backend.js.ic;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.IrFileDeserializer;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.IdSignature;

/* JADX INFO: compiled from: IdSignatureSource.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b0\u0018\u00002\u00020\u0001:\u0002\u000f\u0010B\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0014\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bH&J\u000e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\t0\fH&J\u0014\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u000e0\bH&R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0002\u0011\u0012¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/FileSignatureProvider;", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "(Lorg/jetbrains/kotlin/ir/declarations/IrFile;)V", "getIrFile", "()Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "getImplementedSymbols", "", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "getReachableSignatures", "", "getSignatureToIndexMapping", "", "DeserializedFromKlib", "GeneratedFunctionTypeInterface", "Lorg/jetbrains/kotlin/ir/backend/js/ic/FileSignatureProvider$DeserializedFromKlib;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/FileSignatureProvider$GeneratedFunctionTypeInterface;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FileSignatureProvider {
    private final IrFile irFile;

    public /* synthetic */ FileSignatureProvider(IrFile irFile, DefaultConstructorMarker defaultConstructorMarker) {
        this(irFile);
    }

    public abstract Map<IdSignature, IrSymbol> getImplementedSymbols();

    public abstract Set<IdSignature> getReachableSignatures();

    public abstract Map<IdSignature, Integer> getSignatureToIndexMapping();

    private FileSignatureProvider(IrFile irFile) {
        this.irFile = irFile;
    }

    public final IrFile getIrFile() {
        return this.irFile;
    }

    /* JADX INFO: compiled from: IdSignatureSource.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0014\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006H\u0016J\u000e\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\nH\u0016J\u0014\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\f0\u0006H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/FileSignatureProvider$DeserializedFromKlib;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/FileSignatureProvider;", "fileDeserializer", "Lorg/jetbrains/kotlin/backend/common/serialization/IrFileDeserializer;", "(Lorg/jetbrains/kotlin/backend/common/serialization/IrFileDeserializer;)V", "getImplementedSymbols", "", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "getReachableSignatures", "", "getSignatureToIndexMapping", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DeserializedFromKlib extends FileSignatureProvider {
        private final IrFileDeserializer fileDeserializer;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public DeserializedFromKlib(IrFileDeserializer fileDeserializer) {
            super(fileDeserializer.getFile(), null);
            Intrinsics.checkNotNullParameter(fileDeserializer, "fileDeserializer");
            this.fileDeserializer = fileDeserializer;
        }

        @Override // org.jetbrains.kotlin.ir.backend.js.ic.FileSignatureProvider
        public Map<IdSignature, Integer> getSignatureToIndexMapping() {
            return this.fileDeserializer.getSymbolDeserializer().getSignatureDeserializer().signatureToIndexMapping();
        }

        @Override // org.jetbrains.kotlin.ir.backend.js.ic.FileSignatureProvider
        public Set<IdSignature> getReachableSignatures() {
            return getSignatureToIndexMapping().keySet();
        }

        @Override // org.jetbrains.kotlin.ir.backend.js.ic.FileSignatureProvider
        public Map<IdSignature, IrSymbol> getImplementedSymbols() {
            Map<IdSignature, IrSymbol> deserializedSymbols = this.fileDeserializer.getSymbolDeserializer().getDeserializedSymbols();
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Map.Entry<IdSignature, IrSymbol> entry : deserializedSymbols.entrySet()) {
                if (entry.getValue().isBound()) {
                    linkedHashMap.put(entry.getKey(), entry.getValue());
                }
            }
            return IdSignatureSourceKt.collectImplementedSymbol(linkedHashMap);
        }
    }

    /* JADX INFO: compiled from: IdSignatureSource.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0014\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006H\u0016J\u000e\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u000bH\u0016J\u0014\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\r0\u0006H\u0016R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/FileSignatureProvider$GeneratedFunctionTypeInterface;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/FileSignatureProvider;", "file", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "(Lorg/jetbrains/kotlin/ir/declarations/IrFile;)V", "allSignatures", "", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "getImplementedSymbols", "getReachableSignatures", "", "getSignatureToIndexMapping", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class GeneratedFunctionTypeInterface extends FileSignatureProvider {
        private final Map<IdSignature, IrSymbol> allSignatures;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public GeneratedFunctionTypeInterface(IrFile file) {
            super(file, null);
            Intrinsics.checkNotNullParameter(file, "file");
            Map mapCreateMapBuilder = MapsKt.createMapBuilder();
            for (IrDeclaration irDeclaration : getIrFile().getDeclarations()) {
                IdSignature signature = irDeclaration.getSymbol().getSignature();
                if (signature != null) {
                    mapCreateMapBuilder.put(signature, irDeclaration.getSymbol());
                }
            }
            this.allSignatures = IdSignatureSourceKt.collectImplementedSymbol(MapsKt.build(mapCreateMapBuilder));
        }

        @Override // org.jetbrains.kotlin.ir.backend.js.ic.FileSignatureProvider
        public Map<IdSignature, Integer> getSignatureToIndexMapping() {
            return MapsKt.emptyMap();
        }

        @Override // org.jetbrains.kotlin.ir.backend.js.ic.FileSignatureProvider
        public Set<IdSignature> getReachableSignatures() {
            return this.allSignatures.keySet();
        }

        @Override // org.jetbrains.kotlin.ir.backend.js.ic.FileSignatureProvider
        public Map<IdSignature, IrSymbol> getImplementedSymbols() {
            return this.allSignatures;
        }
    }
}
