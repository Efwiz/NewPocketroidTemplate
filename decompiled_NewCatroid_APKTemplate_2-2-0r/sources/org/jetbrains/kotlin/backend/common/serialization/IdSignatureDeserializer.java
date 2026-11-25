package org.jetbrains.kotlin.backend.common.serialization;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.proto.AccessorIdSignature;
import org.jetbrains.kotlin.backend.common.serialization.proto.CommonIdSignature;
import org.jetbrains.kotlin.backend.common.serialization.proto.CompositeSignature;
import org.jetbrains.kotlin.backend.common.serialization.proto.FileLocalIdSignature;
import org.jetbrains.kotlin.backend.common.serialization.proto.FileSignature;
import org.jetbrains.kotlin.backend.common.serialization.proto.LocalSignature;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: IdSignatureDeserializer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0000\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\u0014H\u0002J\u0010\u0010\u0015\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\u0016H\u0002J\u0010\u0010\u0017\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0018H\u0002J\u000e\u0010\u0019\u001a\u00020\f2\u0006\u0010\u001a\u001a\u00020\u000bJ\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0010\u001a\u00020\u001dH\u0002J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0010\u001a\u00020 H\u0002J\u0010\u0010!\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u000bH\u0002J\u0010\u0010\"\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020#H\u0002J\u0010\u0010$\u001a\u00020#2\u0006\u0010\u001a\u001a\u00020\u000bH\u0002J\u0012\u0010%\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u000b0&R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\t\u001a\u001e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nj\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f`\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/IdSignatureDeserializer;", "", "libraryFile", "Lorg/jetbrains/kotlin/backend/common/serialization/IrLibraryFile;", "fileSignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature$FileSignature;", "internationService", "Lorg/jetbrains/kotlin/backend/common/serialization/IrInterningService;", "(Lorg/jetbrains/kotlin/backend/common/serialization/IrLibraryFile;Lorg/jetbrains/kotlin/ir/util/IdSignature$FileSignature;Lorg/jetbrains/kotlin/backend/common/serialization/IrInterningService;)V", "signatureCache", "Ljava/util/HashMap;", "", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "Lkotlin/collections/HashMap;", "deserializeAccessorIdSignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature$AccessorSignature;", "proto", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/AccessorIdSignature;", "deserializeCompositeIdSignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature$CompositeSignature;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/CompositeSignature;", "deserializeFileIdSignature", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/FileSignature;", "deserializeFileLocalIdSignature", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/FileLocalIdSignature;", "deserializeIdSignature", "index", "deserializeLocalIdSignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature$LocalSignature;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/LocalSignature;", "deserializePublicIdSignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature$CommonSignature;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/CommonIdSignature;", "deserializeScopeLocalIdSignature", "deserializeSignatureData", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IdSignature;", "loadSignatureProto", "signatureToIndexMapping", "", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IdSignatureDeserializer {
    private final IdSignature.FileSignature fileSignature;
    private final IrInterningService internationService;
    private final IrLibraryFile libraryFile;
    private final HashMap<Integer, IdSignature> signatureCache;

    /* JADX INFO: compiled from: IdSignatureDeserializer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[org.jetbrains.kotlin.backend.common.serialization.proto.IdSignature.IdSigCase.values().length];
            try {
                iArr[org.jetbrains.kotlin.backend.common.serialization.proto.IdSignature.IdSigCase.PUBLIC_SIG.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[org.jetbrains.kotlin.backend.common.serialization.proto.IdSignature.IdSigCase.ACCESSOR_SIG.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[org.jetbrains.kotlin.backend.common.serialization.proto.IdSignature.IdSigCase.PRIVATE_SIG.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[org.jetbrains.kotlin.backend.common.serialization.proto.IdSignature.IdSigCase.SCOPED_LOCAL_SIG.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[org.jetbrains.kotlin.backend.common.serialization.proto.IdSignature.IdSigCase.COMPOSITE_SIG.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[org.jetbrains.kotlin.backend.common.serialization.proto.IdSignature.IdSigCase.LOCAL_SIG.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[org.jetbrains.kotlin.backend.common.serialization.proto.IdSignature.IdSigCase.FILE_SIG.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private final IdSignature.FileSignature deserializeFileIdSignature(FileSignature proto) {
        IdSignature.FileSignature fileSignature = this.fileSignature;
        if (fileSignature != null) {
            return fileSignature;
        }
        throw new IllegalStateException("Provide file symbol".toString());
    }

    public IdSignatureDeserializer(IrLibraryFile libraryFile, IdSignature.FileSignature fileSignature, IrInterningService internationService) {
        Intrinsics.checkNotNullParameter(libraryFile, "libraryFile");
        Intrinsics.checkNotNullParameter(internationService, "internationService");
        this.libraryFile = libraryFile;
        this.fileSignature = fileSignature;
        this.internationService = internationService;
        this.signatureCache = new HashMap<>();
    }

    private final org.jetbrains.kotlin.backend.common.serialization.proto.IdSignature loadSignatureProto(int index) {
        return this.libraryFile.signature(index);
    }

    public final IdSignature deserializeIdSignature(int index) {
        HashMap<Integer, IdSignature> map = this.signatureCache;
        Integer numValueOf = Integer.valueOf(index);
        IdSignature idSignatureDeserializeSignatureData = map.get(numValueOf);
        if (idSignatureDeserializeSignatureData == null) {
            idSignatureDeserializeSignatureData = deserializeSignatureData(loadSignatureProto(index));
            map.put(numValueOf, idSignatureDeserializeSignatureData);
        }
        return idSignatureDeserializeSignatureData;
    }

    private final IdSignature.CommonSignature deserializePublicIdSignature(CommonIdSignature proto) {
        String strDebugInfo;
        IrInterningService irInterningService = this.internationService;
        IrLibraryFile irLibraryFile = this.libraryFile;
        List<Integer> packageFqNameList = proto.getPackageFqNameList();
        Intrinsics.checkNotNullExpressionValue(packageFqNameList, "getPackageFqNameList(...)");
        String strString = irInterningService.string(IrFileDeserializerKt.deserializeFqName(irLibraryFile, packageFqNameList));
        IrInterningService irInterningService2 = this.internationService;
        IrLibraryFile irLibraryFile2 = this.libraryFile;
        List<Integer> declarationFqNameList = proto.getDeclarationFqNameList();
        Intrinsics.checkNotNullExpressionValue(declarationFqNameList, "getDeclarationFqNameList(...)");
        return new IdSignature.CommonSignature(strString, irInterningService2.string(IrFileDeserializerKt.deserializeFqName(irLibraryFile2, declarationFqNameList)), proto.hasMemberUniqId() ? Long.valueOf(proto.getMemberUniqId()) : null, proto.getFlags(), (!proto.hasDebugInfo() || (strDebugInfo = this.libraryFile.debugInfo(proto.getDebugInfo())) == null) ? null : this.internationService.string(strDebugInfo));
    }

    private final IdSignature.AccessorSignature deserializeAccessorIdSignature(AccessorIdSignature proto) {
        String strDebugInfo;
        IdSignature idSignatureDeserializeIdSignature = deserializeIdSignature(proto.getPropertySignature());
        if (!(idSignatureDeserializeIdSignature instanceof IdSignature.CommonSignature)) {
            throw new IllegalArgumentException("For public accessor corresponding property supposed to be public as well".toString());
        }
        IdSignature.CommonSignature commonSignature = (IdSignature.CommonSignature) idSignatureDeserializeIdSignature;
        return new IdSignature.AccessorSignature(idSignatureDeserializeIdSignature, new IdSignature.CommonSignature(commonSignature.getPackageFqName(), this.internationService.string(commonSignature.getDeclarationFqName() + '.' + this.libraryFile.string(proto.getName())), Long.valueOf(proto.getAccessorHashId()), proto.getFlags(), (!proto.hasDebugInfo() || (strDebugInfo = this.libraryFile.debugInfo(proto.getDebugInfo())) == null) ? null : this.internationService.string(strDebugInfo)));
    }

    private final IdSignature deserializeFileLocalIdSignature(FileLocalIdSignature proto) {
        return new IdSignature.FileLocalSignature(deserializeIdSignature(proto.getContainer()), proto.getLocalId(), null, 4, null);
    }

    private final IdSignature deserializeScopeLocalIdSignature(int proto) {
        return new IdSignature.ScopeLocalDeclaration(proto, null, 2, null);
    }

    private final IdSignature.CompositeSignature deserializeCompositeIdSignature(CompositeSignature proto) {
        return new IdSignature.CompositeSignature(deserializeIdSignature(proto.getContainerSig()), deserializeIdSignature(proto.getInnerSig()));
    }

    private final IdSignature.LocalSignature deserializeLocalIdSignature(LocalSignature proto) {
        IrInterningService irInterningService = this.internationService;
        IrLibraryFile irLibraryFile = this.libraryFile;
        List<Integer> localFqNameList = proto.getLocalFqNameList();
        Intrinsics.checkNotNullExpressionValue(localFqNameList, "getLocalFqNameList(...)");
        return new IdSignature.LocalSignature(irInterningService.string(IrFileDeserializerKt.deserializeFqName(irLibraryFile, localFqNameList)), proto.hasLocalHash() ? Long.valueOf(proto.getLocalHash()) : null, proto.hasDebugInfo() ? this.libraryFile.debugInfo(proto.getDebugInfo()) : null);
    }

    private final IdSignature deserializeSignatureData(org.jetbrains.kotlin.backend.common.serialization.proto.IdSignature proto) {
        org.jetbrains.kotlin.backend.common.serialization.proto.IdSignature.IdSigCase idSigCase = proto.getIdSigCase();
        switch (idSigCase == null ? -1 : WhenMappings.$EnumSwitchMapping$0[idSigCase.ordinal()]) {
            case 1:
                CommonIdSignature publicSig = proto.getPublicSig();
                Intrinsics.checkNotNullExpressionValue(publicSig, "getPublicSig(...)");
                return deserializePublicIdSignature(publicSig);
            case 2:
                AccessorIdSignature accessorSig = proto.getAccessorSig();
                Intrinsics.checkNotNullExpressionValue(accessorSig, "getAccessorSig(...)");
                return deserializeAccessorIdSignature(accessorSig);
            case 3:
                FileLocalIdSignature privateSig = proto.getPrivateSig();
                Intrinsics.checkNotNullExpressionValue(privateSig, "getPrivateSig(...)");
                return deserializeFileLocalIdSignature(privateSig);
            case 4:
                return deserializeScopeLocalIdSignature(proto.getScopedLocalSig());
            case 5:
                CompositeSignature compositeSig = proto.getCompositeSig();
                Intrinsics.checkNotNullExpressionValue(compositeSig, "getCompositeSig(...)");
                return deserializeCompositeIdSignature(compositeSig);
            case 6:
                LocalSignature localSig = proto.getLocalSig();
                Intrinsics.checkNotNullExpressionValue(localSig, "getLocalSig(...)");
                return deserializeLocalIdSignature(localSig);
            case 7:
                FileSignature fileSig = proto.getFileSig();
                Intrinsics.checkNotNullExpressionValue(fileSig, "getFileSig(...)");
                return deserializeFileIdSignature(fileSig);
            default:
                throw new IllegalStateException(("Unexpected IdSignature kind: " + proto.getIdSigCase()).toString());
        }
    }

    public final Map<IdSignature, Integer> signatureToIndexMapping() {
        if (this.signatureCache.isEmpty()) {
            return MapsKt.emptyMap();
        }
        Set<Map.Entry<Integer, IdSignature>> setEntrySet = this.signatureCache.entrySet();
        Intrinsics.checkNotNullExpressionValue(setEntrySet, "<get-entries>(...)");
        HashMap mapNewHashMapWithExpectedSize = CollectionsKt.newHashMapWithExpectedSize(this.signatureCache.size());
        Iterator<T> it = setEntrySet.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Pair pair = TuplesKt.to(entry.getValue(), entry.getKey());
            mapNewHashMapWithExpectedSize.put(pair.getFirst(), pair.getSecond());
        }
        return mapNewHashMapWithExpectedSize;
    }
}
