package org.jetbrains.kotlin.ir.backend.js.ic;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.IdSignatureDeserializer;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.protobuf.CodedInputStream;
import org.jetbrains.kotlin.protobuf.CodedOutputStream;

/* JADX INFO: compiled from: IdSignatureSerialization.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0007\b\u0000\u0018\u00002\u00020\u0001:\u0004\u0017\u0018\u0019\u001aB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0007ø\u0001\u0000¢\u0006\u0004\b\r\u0010\u000eJ,\u0010\u000f\u001a\u00020\u00102\u0006\u0010\f\u001a\u00020\u00072\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00140\u0012ø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016R2\u0010\u0005\u001a&\u0012\u0004\u0012\u00020\u0007\u0012\b\u0012\u00060\bR\u00020\u00000\u0006j\u0012\u0012\u0004\u0012\u00020\u0007\u0012\b\u0012\u00060\bR\u00020\u0000`\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b¡\u001e0\u0001¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/IdSignatureSerialization;", "", "library", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinLibraryHeader;", "(Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinLibraryHeader;)V", "fileSerializers", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFile;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/IdSignatureSerialization$FileIdSignatureSerialization;", "Lkotlin/collections/HashMap;", "getIdSignatureDeserializer", "Lorg/jetbrains/kotlin/ir/backend/js/ic/IdSignatureSerialization$IdSignatureICDeserializer;", "srcFile", "getIdSignatureDeserializer-wxjXL9s", "(Ljava/lang/String;)Lorg/jetbrains/kotlin/ir/backend/js/ic/IdSignatureSerialization$IdSignatureICDeserializer;", "getIdSignatureSerializer", "Lorg/jetbrains/kotlin/ir/backend/js/ic/IdSignatureSerialization$IdSignatureICSerializer;", "signatureToIndexMapping", "", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "", "getIdSignatureSerializer-9XGJH6U", "(Ljava/lang/String;Ljava/util/Map;)Lorg/jetbrains/kotlin/ir/backend/js/ic/IdSignatureSerialization$IdSignatureICSerializer;", "FileIdSignatureSerialization", "IdSignatureICDeserializer", "IdSignatureICSerializer", "IdSignatureProtoType", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IdSignatureSerialization {
    private final HashMap<KotlinSourceFile, FileIdSignatureSerialization> fileSerializers;
    private final KotlinLibraryHeader library;

    /* JADX INFO: compiled from: IdSignatureSerialization.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/IdSignatureSerialization$IdSignatureICDeserializer;", "", "deserializeIdSignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "input", "Lorg/jetbrains/kotlin/protobuf/CodedInputStream;", "skipIdSignature", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface IdSignatureICDeserializer {
        IdSignature deserializeIdSignature(CodedInputStream input);

        void skipIdSignature(CodedInputStream input);
    }

    /* JADX INFO: compiled from: IdSignatureSerialization.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/IdSignatureSerialization$IdSignatureICSerializer;", "", "serializeIdSignature", "", "out", "Lorg/jetbrains/kotlin/protobuf/CodedOutputStream;", "signature", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface IdSignatureICSerializer {
        void serializeIdSignature(CodedOutputStream out, IdSignature signature);
    }

    /* JADX INFO: compiled from: IdSignatureSerialization.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\b\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/IdSignatureSerialization$IdSignatureProtoType;", "", "id", "", "(Ljava/lang/String;II)V", "getId", "()I", "DECLARED_SIGNATURE", "COMMON_SIGNATURE", "COMPOSITE_SIGNATURE", "ACCESSOR_SIGNATURE", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private enum IdSignatureProtoType {
        DECLARED_SIGNATURE(0),
        COMMON_SIGNATURE(1),
        COMPOSITE_SIGNATURE(2),
        ACCESSOR_SIGNATURE(3);

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
        private final int id;

        public static EnumEntries<IdSignatureProtoType> getEntries() {
            return $ENTRIES;
        }

        IdSignatureProtoType(int i) {
            this.id = i;
        }

        public final int getId() {
            return this.id;
        }
    }

    public IdSignatureSerialization(KotlinLibraryHeader library) {
        Intrinsics.checkNotNullParameter(library, "library");
        this.library = library;
        this.fileSerializers = new HashMap<>();
    }

    /* JADX INFO: compiled from: IdSignatureSerialization.kt */
    @Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0018\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u000eH\u0016J\u0010\u0010\u001b\u001a\u00020\u00172\u0006\u0010\u0014\u001a\u00020\u0015H\u0016R\u001b\u0010\u0006\u001a\u00020\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR0\u0010\f\u001a\u001e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\rj\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f`\u0010X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/IdSignatureSerialization$FileIdSignatureSerialization;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/IdSignatureSerialization$IdSignatureICSerializer;", "Lorg/jetbrains/kotlin/ir/backend/js/ic/IdSignatureSerialization$IdSignatureICDeserializer;", "srcFile", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFile;", "(Lorg/jetbrains/kotlin/ir/backend/js/ic/IdSignatureSerialization;Ljava/lang/String;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "deserializer", "Lorg/jetbrains/kotlin/backend/common/serialization/IdSignatureDeserializer;", "getDeserializer", "()Lorg/jetbrains/kotlin/backend/common/serialization/IdSignatureDeserializer;", "deserializer$delegate", "Lkotlin/Lazy;", "signatureToIndexMapping", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "", "Lkotlin/collections/HashMap;", "getSignatureToIndexMapping$backend_js", "()Ljava/util/HashMap;", "deserializeIdSignature", "input", "Lorg/jetbrains/kotlin/protobuf/CodedInputStream;", "serializeIdSignature", "", "out", "Lorg/jetbrains/kotlin/protobuf/CodedOutputStream;", "signature", "skipIdSignature", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class FileIdSignatureSerialization implements IdSignatureICDeserializer, IdSignatureICSerializer {

        /* JADX INFO: renamed from: deserializer$delegate, reason: from kotlin metadata */
        private final Lazy deserializer;
        private final HashMap<IdSignature, Integer> signatureToIndexMapping;
        final /* synthetic */ IdSignatureSerialization this$0;

        public /* synthetic */ FileIdSignatureSerialization(IdSignatureSerialization idSignatureSerialization, String str, DefaultConstructorMarker defaultConstructorMarker) {
            this(idSignatureSerialization, str);
        }

        private FileIdSignatureSerialization(final IdSignatureSerialization idSignatureSerialization, final String srcFile) {
            Intrinsics.checkNotNullParameter(srcFile, "srcFile");
            this.this$0 = idSignatureSerialization;
            this.deserializer = LazyKt.lazy(new Function0<IdSignatureDeserializer>() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.IdSignatureSerialization$FileIdSignatureSerialization$deserializer$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IdSignatureDeserializer invoke() {
                    IdSignatureDeserializer idSignatureDeserializer = idSignatureSerialization.library.getSourceFileDeserializers().get(KotlinSourceFile.m7163boximpl(srcFile));
                    if (idSignatureDeserializer != null) {
                        return idSignatureDeserializer;
                    }
                    ICUtilsKt.m7123notFoundIcError5T69zGA("signature deserializer", idSignatureSerialization.library.mo7159getLibraryFiledAnkW_k(), srcFile);
                    throw null;
                }
            });
            this.signatureToIndexMapping = new HashMap<>();
        }

        private final IdSignatureDeserializer getDeserializer() {
            return (IdSignatureDeserializer) this.deserializer.getValue();
        }

        public final HashMap<IdSignature, Integer> getSignatureToIndexMapping$backend_js() {
            return this.signatureToIndexMapping;
        }

        @Override // org.jetbrains.kotlin.ir.backend.js.ic.IdSignatureSerialization.IdSignatureICSerializer
        public void serializeIdSignature(CodedOutputStream out, IdSignature signature) throws IOException {
            Intrinsics.checkNotNullParameter(out, "out");
            Intrinsics.checkNotNullParameter(signature, "signature");
            Integer num = this.signatureToIndexMapping.get(signature);
            if (num != null) {
                out.writeInt32NoTag(IdSignatureProtoType.DECLARED_SIGNATURE.getId());
                out.writeInt32NoTag(num.intValue());
                return;
            }
            if (signature instanceof IdSignature.CommonSignature) {
                out.writeInt32NoTag(IdSignatureProtoType.COMMON_SIGNATURE.getId());
                IdSignature.CommonSignature commonSignature = (IdSignature.CommonSignature) signature;
                out.writeStringNoTag(commonSignature.getPackageFqName());
                out.writeStringNoTag(commonSignature.getDeclarationFqName());
                Long id2 = commonSignature.getId();
                out.writeBoolNoTag(id2 != null);
                if (id2 != null) {
                    out.writeFixed64NoTag(id2.longValue());
                }
                out.writeInt64NoTag(commonSignature.getMask());
                String description = commonSignature.getDescription();
                out.writeBoolNoTag(description != null);
                if (description != null) {
                    out.writeStringNoTag(description);
                    return;
                }
                return;
            }
            if (signature instanceof IdSignature.CompositeSignature) {
                out.writeInt32NoTag(IdSignatureProtoType.COMPOSITE_SIGNATURE.getId());
                IdSignature.CompositeSignature compositeSignature = (IdSignature.CompositeSignature) signature;
                serializeIdSignature(out, compositeSignature.getContainer());
                serializeIdSignature(out, compositeSignature.getInner());
                return;
            }
            if (signature instanceof IdSignature.AccessorSignature) {
                out.writeInt32NoTag(IdSignatureProtoType.ACCESSOR_SIGNATURE.getId());
                IdSignature.AccessorSignature accessorSignature = (IdSignature.AccessorSignature) signature;
                serializeIdSignature(out, accessorSignature.getPropertySignature());
                serializeIdSignature(out, accessorSignature.getAccessorSignature());
                return;
            }
            ICUtilsKt.m7122icError5T69zGA$default("can not write " + signature + " signature", null, null, 6, null);
            throw null;
        }

        @Override // org.jetbrains.kotlin.ir.backend.js.ic.IdSignatureSerialization.IdSignatureICDeserializer
        public IdSignature deserializeIdSignature(CodedInputStream input) throws IOException {
            Intrinsics.checkNotNullParameter(input, "input");
            int int32 = input.readInt32();
            if (int32 == IdSignatureProtoType.DECLARED_SIGNATURE.getId()) {
                int int33 = input.readInt32();
                IdSignature idSignatureDeserializeIdSignature = getDeserializer().deserializeIdSignature(int33);
                this.signatureToIndexMapping.put(idSignatureDeserializeIdSignature, Integer.valueOf(int33));
                return idSignatureDeserializeIdSignature;
            }
            if (int32 == IdSignatureProtoType.COMMON_SIGNATURE.getId()) {
                String string = input.readString();
                String string2 = input.readString();
                Long lValueOf = input.readBool() ? Long.valueOf(input.readFixed64()) : null;
                long int64 = input.readInt64();
                String string3 = input.readBool() ? input.readString() : null;
                Intrinsics.checkNotNull(string);
                Intrinsics.checkNotNull(string2);
                return new IdSignature.CommonSignature(string, string2, lValueOf, int64, string3);
            }
            if (int32 == IdSignatureProtoType.COMPOSITE_SIGNATURE.getId()) {
                return new IdSignature.CompositeSignature(deserializeIdSignature(input), deserializeIdSignature(input));
            }
            if (int32 == IdSignatureProtoType.ACCESSOR_SIGNATURE.getId()) {
                IdSignature idSignatureDeserializeIdSignature2 = deserializeIdSignature(input);
                IdSignature idSignatureDeserializeIdSignature3 = deserializeIdSignature(input);
                if (!(idSignatureDeserializeIdSignature3 instanceof IdSignature.CommonSignature)) {
                    ICUtilsKt.m7122icError5T69zGA$default("can not read accessor signature", null, null, 6, null);
                    throw null;
                }
                return new IdSignature.AccessorSignature(idSignatureDeserializeIdSignature2, (IdSignature.CommonSignature) idSignatureDeserializeIdSignature3);
            }
            ICUtilsKt.m7122icError5T69zGA$default("can not read signature type " + int32, null, null, 6, null);
            throw null;
        }

        @Override // org.jetbrains.kotlin.ir.backend.js.ic.IdSignatureSerialization.IdSignatureICDeserializer
        public void skipIdSignature(CodedInputStream input) throws IOException {
            Intrinsics.checkNotNullParameter(input, "input");
            int int32 = input.readInt32();
            if (int32 == IdSignatureProtoType.DECLARED_SIGNATURE.getId()) {
                input.readInt32();
                return;
            }
            if (int32 == IdSignatureProtoType.COMMON_SIGNATURE.getId()) {
                input.readString();
                input.readString();
                if (input.readBool()) {
                    input.readFixed64();
                }
                input.readInt64();
                if (input.readBool()) {
                    input.readString();
                    return;
                }
                return;
            }
            if (int32 == IdSignatureProtoType.COMPOSITE_SIGNATURE.getId()) {
                skipIdSignature(input);
                skipIdSignature(input);
            } else if (int32 == IdSignatureProtoType.ACCESSOR_SIGNATURE.getId()) {
                skipIdSignature(input);
                skipIdSignature(input);
            } else {
                ICUtilsKt.m7122icError5T69zGA$default("can not skip signature type " + int32, null, null, 6, null);
                throw null;
            }
        }
    }

    /* JADX INFO: renamed from: getIdSignatureDeserializer-wxjXL9s, reason: not valid java name */
    public final IdSignatureICDeserializer m7130getIdSignatureDeserializerwxjXL9s(String srcFile) {
        Intrinsics.checkNotNullParameter(srcFile, "srcFile");
        HashMap<KotlinSourceFile, FileIdSignatureSerialization> map = this.fileSerializers;
        KotlinSourceFile kotlinSourceFileM7163boximpl = KotlinSourceFile.m7163boximpl(srcFile);
        FileIdSignatureSerialization fileIdSignatureSerialization = map.get(kotlinSourceFileM7163boximpl);
        if (fileIdSignatureSerialization == null) {
            fileIdSignatureSerialization = new FileIdSignatureSerialization(this, srcFile, null);
            map.put(kotlinSourceFileM7163boximpl, fileIdSignatureSerialization);
        }
        return fileIdSignatureSerialization;
    }

    /* JADX INFO: renamed from: getIdSignatureSerializer-9XGJH6U, reason: not valid java name */
    public final IdSignatureICSerializer m7131getIdSignatureSerializer9XGJH6U(String srcFile, Map<IdSignature, Integer> signatureToIndexMapping) {
        Intrinsics.checkNotNullParameter(srcFile, "srcFile");
        Intrinsics.checkNotNullParameter(signatureToIndexMapping, "signatureToIndexMapping");
        HashMap<KotlinSourceFile, FileIdSignatureSerialization> map = this.fileSerializers;
        KotlinSourceFile kotlinSourceFileM7163boximpl = KotlinSourceFile.m7163boximpl(srcFile);
        FileIdSignatureSerialization fileIdSignatureSerialization = map.get(kotlinSourceFileM7163boximpl);
        if (fileIdSignatureSerialization == null) {
            fileIdSignatureSerialization = new FileIdSignatureSerialization(this, srcFile, null);
            map.put(kotlinSourceFileM7163boximpl, fileIdSignatureSerialization);
        }
        fileIdSignatureSerialization.getSignatureToIndexMapping$backend_js().putAll(signatureToIndexMapping);
        return fileIdSignatureSerialization;
    }
}
