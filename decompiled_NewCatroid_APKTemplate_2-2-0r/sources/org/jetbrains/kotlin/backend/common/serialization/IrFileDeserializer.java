package org.jetbrains.kotlin.backend.common.serialization;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCall;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: IrFileDeserializer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u000e\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u0016J\u0006\u0010 \u001a\u00020!J\u0010\u0010\"\u001a\u00020#2\u0006\u0010\u001f\u001a\u00020\u0016H\u0002R\u0016\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u000f\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R%\u0010\u0014\u001a\u0016\u0012\u0004\u0012\u00020\u0016\u0012\f\u0012\n \u0018*\u0004\u0018\u00010\u00170\u00170\u0015¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001c¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/IrFileDeserializer;", "", "file", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "libraryFile", "Lorg/jetbrains/kotlin/backend/common/serialization/IrLibraryFile;", "fileProto", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrFile;", "symbolDeserializer", "Lorg/jetbrains/kotlin/backend/common/serialization/IrSymbolDeserializer;", "declarationDeserializer", "Lorg/jetbrains/kotlin/backend/common/serialization/IrDeclarationDeserializer;", "(Lorg/jetbrains/kotlin/ir/declarations/IrFile;Lorg/jetbrains/kotlin/backend/common/serialization/IrLibraryFile;Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrFile;Lorg/jetbrains/kotlin/backend/common/serialization/IrSymbolDeserializer;Lorg/jetbrains/kotlin/backend/common/serialization/IrDeclarationDeserializer;)V", "annotations", "", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrConstructorCall;", "getDeclarationDeserializer", "()Lorg/jetbrains/kotlin/backend/common/serialization/IrDeclarationDeserializer;", "getFile", "()Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "reversedSignatureIndex", "", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getReversedSignatureIndex", "()Ljava/util/Map;", "getSymbolDeserializer", "()Lorg/jetbrains/kotlin/backend/common/serialization/IrSymbolDeserializer;", "deserializeDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "idSig", "deserializeFileImplicitDataIfFirstUse", "", "loadTopLevelDeclarationProto", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrDeclaration;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrFileDeserializer {
    private List<IrConstructorCall> annotations;
    private final IrDeclarationDeserializer declarationDeserializer;
    private final IrFile file;
    private final IrLibraryFile libraryFile;
    private final Map<IdSignature, Integer> reversedSignatureIndex;
    private final IrSymbolDeserializer symbolDeserializer;

    public IrFileDeserializer(IrFile file, IrLibraryFile libraryFile, org.jetbrains.kotlin.backend.common.serialization.proto.IrFile fileProto, IrSymbolDeserializer symbolDeserializer, IrDeclarationDeserializer declarationDeserializer) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(libraryFile, "libraryFile");
        Intrinsics.checkNotNullParameter(fileProto, "fileProto");
        Intrinsics.checkNotNullParameter(symbolDeserializer, "symbolDeserializer");
        Intrinsics.checkNotNullParameter(declarationDeserializer, "declarationDeserializer");
        this.file = file;
        this.libraryFile = libraryFile;
        this.symbolDeserializer = symbolDeserializer;
        this.declarationDeserializer = declarationDeserializer;
        List<Integer> declarationIdList = fileProto.getDeclarationIdList();
        Intrinsics.checkNotNullExpressionValue(declarationIdList, "getDeclarationIdList(...)");
        List<Integer> list = declarationIdList;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list, 10)), 16));
        for (Object obj : list) {
            Integer num = (Integer) obj;
            IrSymbolDeserializer irSymbolDeserializer = this.symbolDeserializer;
            Intrinsics.checkNotNull(num);
            linkedHashMap.put(irSymbolDeserializer.deserializeIdSignature(num.intValue()), obj);
        }
        this.reversedSignatureIndex = linkedHashMap;
        this.annotations = fileProto.getAnnotationList();
    }

    public final IrFile getFile() {
        return this.file;
    }

    public final IrSymbolDeserializer getSymbolDeserializer() {
        return this.symbolDeserializer;
    }

    public final IrDeclarationDeserializer getDeclarationDeserializer() {
        return this.declarationDeserializer;
    }

    public final Map<IdSignature, Integer> getReversedSignatureIndex() {
        return this.reversedSignatureIndex;
    }

    public final IrDeclaration deserializeDeclaration(IdSignature idSig) {
        Intrinsics.checkNotNullParameter(idSig, "idSig");
        IrDeclaration irDeclarationDeserializeDeclaration$default = IrDeclarationDeserializer.deserializeDeclaration$default(this.declarationDeserializer, loadTopLevelDeclarationProto(idSig), false, 2, null);
        this.file.getDeclarations().add(irDeclarationDeserializeDeclaration$default);
        return irDeclarationDeserializeDeclaration$default;
    }

    private final org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclaration loadTopLevelDeclarationProto(IdSignature idSig) {
        Integer num = this.reversedSignatureIndex.get(idSig);
        if (num == null) {
            throw new IllegalStateException(("Not found Idx for " + idSig).toString());
        }
        return this.libraryFile.declaration(num.intValue());
    }

    public final void deserializeFileImplicitDataIfFirstUse() {
        List<IrConstructorCall> list = this.annotations;
        if (list != null) {
            IrFile irFile = this.file;
            irFile.setAnnotations(CollectionsKt.plus((Collection) irFile.getAnnotations(), (Iterable) this.declarationDeserializer.deserializeAnnotations$ir_serialization_common(list)));
            this.annotations = null;
        }
    }
}
