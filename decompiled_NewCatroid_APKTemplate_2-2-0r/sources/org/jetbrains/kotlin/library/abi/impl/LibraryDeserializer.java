package org.jetbrains.kotlin.library.abi.impl;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.SetsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.backend.common.serialization.BasicIrModuleDeserializerKt;
import org.jetbrains.kotlin.backend.common.serialization.IdSignatureDeserializer;
import org.jetbrains.kotlin.backend.common.serialization.IrFileDeserializerKt;
import org.jetbrains.kotlin.backend.common.serialization.IrFlags;
import org.jetbrains.kotlin.backend.common.serialization.IrInterningService;
import org.jetbrains.kotlin.backend.common.serialization.IrKlibBytesSource;
import org.jetbrains.kotlin.backend.common.serialization.IrLibraryFile;
import org.jetbrains.kotlin.backend.common.serialization.IrLibraryFileFromBytes;
import org.jetbrains.kotlin.backend.common.serialization.encodings.BinaryNameAndType;
import org.jetbrains.kotlin.backend.common.serialization.encodings.BinarySymbolData;
import org.jetbrains.kotlin.backend.common.serialization.encodings.BinaryTypeProjection;
import org.jetbrains.kotlin.backend.common.serialization.encodings.ClassFlags;
import org.jetbrains.kotlin.backend.common.serialization.encodings.FunctionFlags;
import org.jetbrains.kotlin.backend.common.serialization.encodings.PropertyFlags;
import org.jetbrains.kotlin.backend.common.serialization.encodings.TypeParameterFlags;
import org.jetbrains.kotlin.backend.common.serialization.encodings.ValueParameterFlags;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrClass;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCall;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclaration;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrDefinitelyNotNullType;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrEnumEntry;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrFile;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionBase;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrProperty;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleType;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeLegacy;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeNullability;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrType;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeParameter;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameter;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.IdSignatureRenderer;
import org.jetbrains.kotlin.ir.util.IdSignatureRendererKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.KotlinLibraryKt;
import org.jetbrains.kotlin.library.abi.AbiClass;
import org.jetbrains.kotlin.library.abi.AbiClassKind;
import org.jetbrains.kotlin.library.abi.AbiClassifierReference;
import org.jetbrains.kotlin.library.abi.AbiCompoundName;
import org.jetbrains.kotlin.library.abi.AbiDeclaration;
import org.jetbrains.kotlin.library.abi.AbiEnumEntry;
import org.jetbrains.kotlin.library.abi.AbiFunction;
import org.jetbrains.kotlin.library.abi.AbiModality;
import org.jetbrains.kotlin.library.abi.AbiProperty;
import org.jetbrains.kotlin.library.abi.AbiPropertyKind;
import org.jetbrains.kotlin.library.abi.AbiQualifiedName;
import org.jetbrains.kotlin.library.abi.AbiReadingFilter;
import org.jetbrains.kotlin.library.abi.AbiSignatureVersion;
import org.jetbrains.kotlin.library.abi.AbiSignatures;
import org.jetbrains.kotlin.library.abi.AbiTopLevelDeclarations;
import org.jetbrains.kotlin.library.abi.AbiType;
import org.jetbrains.kotlin.library.abi.AbiTypeArgument;
import org.jetbrains.kotlin.library.abi.AbiTypeNullability;
import org.jetbrains.kotlin.library.abi.AbiTypeParameter;
import org.jetbrains.kotlin.library.abi.AbiValueParameter;
import org.jetbrains.kotlin.library.abi.AbiVariance;
import org.jetbrains.kotlin.library.abi.ExperimentalLibraryAbiReader;
import org.jetbrains.kotlin.library.impl.BuiltInsPlatform;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.storage.CacheWithNotNullValues;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.utils.CollectionsKt;
import org.jetbrains.kotlin.utils.SmartList;
import org.jetbrains.kotlin.utils.SmartSet;

/* JADX INFO: compiled from: LibraryAbiReaderImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000M\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b*\u0001\u000b\b\u0003\u0018\u0000 \u001a2\u00020\u0001:\u0006\u001a\u001b\u001c\u001d\u001e\u001fB%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tJ\u0006\u0010\u0014\u001a\u00020\u0015J\u001f\u0010\u0016\u001a\u0004\u0018\u0001H\u0017\"\b\b\u0000\u0010\u0017*\u00020\u0018*\u0004\u0018\u0001H\u0017H\u0002¢\u0006\u0002\u0010\u0019R\u0010\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\fR\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/LibraryDeserializer;", "", "library", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "supportedSignatureVersions", "", "Lorg/jetbrains/kotlin/library/abi/AbiSignatureVersion;", "compositeFilter", "Lorg/jetbrains/kotlin/library/abi/AbiReadingFilter$Composite;", "(Lorg/jetbrains/kotlin/library/KotlinLibrary;Ljava/util/Set;Lorg/jetbrains/kotlin/library/abi/AbiReadingFilter$Composite;)V", "annotationsInterner", "org/jetbrains/kotlin/library/abi/impl/LibraryDeserializer$annotationsInterner$1", "Lorg/jetbrains/kotlin/library/abi/impl/LibraryDeserializer$annotationsInterner$1;", "interner", "Lorg/jetbrains/kotlin/backend/common/serialization/IrInterningService;", "needV1Signatures", "", "needV2Signatures", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "Lorg/jetbrains/kotlin/library/impl/BuiltInsPlatform;", "deserialize", "Lorg/jetbrains/kotlin/library/abi/AbiTopLevelDeclarations;", "discardIfExcluded", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/library/abi/AbiDeclaration;", "(Lorg/jetbrains/kotlin/library/abi/AbiDeclaration;)Lorg/jetbrains/kotlin/library/abi/AbiDeclaration;", "Companion", "ContainingEntity", "FileDeserializer", "TypeDeserializer", "TypeParameterResolver", "VisibilityStatus", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
@ExperimentalLibraryAbiReader
final class LibraryDeserializer {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String INIT_SUFFIX = "." + SpecialNames.INIT.asString();
    private static final AbiQualifiedName KOTLIN_ANY_QUALIFIED_NAME;
    private static final String KOTLIN_COMPOUND_NAME;
    private static final AbiQualifiedName KOTLIN_UNIT_QUALIFIED_NAME;
    private static final AbiQualifiedName PUBLISHED_API_CONSTRUCTOR_QUALIFIED_NAME;
    private final LibraryDeserializer$annotationsInterner$1 annotationsInterner;
    private final AbiReadingFilter.Composite compositeFilter;
    private final IrInterningService interner;
    private final KotlinLibrary library;
    private final boolean needV1Signatures;
    private final boolean needV2Signatures;
    private final BuiltInsPlatform platform;

    public LibraryDeserializer(KotlinLibrary library, Set<? extends AbiSignatureVersion> supportedSignatureVersions, AbiReadingFilter.Composite composite) {
        Intrinsics.checkNotNullParameter(library, "library");
        Intrinsics.checkNotNullParameter(supportedSignatureVersions, "supportedSignatureVersions");
        this.library = library;
        this.compositeFilter = composite;
        String builtInsPlatform = KotlinLibraryKt.getBuiltInsPlatform(library);
        this.platform = builtInsPlatform != null ? BuiltInsPlatform.INSTANCE.parseFromString(builtInsPlatform) : null;
        this.interner = new IrInterningService();
        this.annotationsInterner = new LibraryDeserializer$annotationsInterner$1();
        this.needV1Signatures = supportedSignatureVersions.contains(AbiSignatureVersions.Supported.V1);
        this.needV2Signatures = supportedSignatureVersions.contains(AbiSignatureVersions.Supported.V2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final <T extends AbiDeclaration> T discardIfExcluded(T t) {
        AbiReadingFilter.Composite composite;
        if (t == null || (composite = this.compositeFilter) == null || !composite.isDeclarationExcluded(t)) {
            return t;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: LibraryAbiReaderImpl.kt */
    @Metadata(d1 = {"\u0000Ú\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J4\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u00152\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u0011H\u0002J\u0016\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00160\u00152\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J$\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\u0012\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010!H\u0002J$\u0010\"\u001a\u0004\u0018\u00010#2\u0006\u0010\u0012\u001a\u00020$2\u0006\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010!H\u0002J\u0018\u0010%\u001a\u00020&2\u0006\u0010\u0012\u001a\u00020'2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J,\u0010(\u001a\u0004\u0018\u00010)2\u0006\u0010\u0012\u001a\u00020*2\u0006\u0010+\u001a\u00020,2\u0006\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010!H\u0002J\u0010\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u000200H\u0002J$\u00101\u001a\u0004\u0018\u0001022\u0006\u0010\u0012\u001a\u0002032\u0006\u0010\u001e\u001a\u00020\u001f2\b\u00104\u001a\u0004\u0018\u00010!H\u0002J\u0018\u00105\u001a\u00020\u00162\u0006\u00106\u001a\u00020\u00032\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0014\u00107\u001a\u0002082\f\u00109\u001a\b\u0012\u0004\u0012\u00020#0:J$\u0010;\u001a\b\u0012\u0004\u0012\u00020<0\r2\f\u0010=\u001a\b\u0012\u0004\u0012\u00020>0\r2\u0006\u00104\u001a\u00020!H\u0002J9\u0010?\u001a\b\u0012\u0004\u0012\u00020@0\r2\f\u0010A\u001a\b\u0012\u0004\u0012\u00020\u00030\r2\u0006\u00104\u001a\u00020!2\u0012\u0010B\u001a\u000e\u0012\u0004\u0012\u00020@\u0012\u0004\u0012\u00020,0CH\u0082\bJ\u0018\u0010D\u001a\u00020E2\u0006\u0010\u0012\u001a\u00020F2\u0006\u00104\u001a\u00020!H\u0002J\u0010\u0010G\u001a\u00020,2\u0006\u0010\u0012\u001a\u00020\u001dH\u0002J\f\u0010H\u001a\u00020I*\u00020.H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\u00020\bX\u0082\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00030\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006J"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/LibraryDeserializer$FileDeserializer;", "", "fileIndex", "", "(Lorg/jetbrains/kotlin/library/abi/impl/LibraryDeserializer;I)V", "fileReader", "Lorg/jetbrains/kotlin/backend/common/serialization/IrLibraryFileFromBytes;", "packageName", "Lorg/jetbrains/kotlin/library/abi/AbiCompoundName;", "Ljava/lang/String;", "signatureDeserializer", "Lorg/jetbrains/kotlin/backend/common/serialization/IdSignatureDeserializer;", "topLevelDeclarationIds", "", "typeDeserializer", "Lorg/jetbrains/kotlin/library/abi/impl/LibraryDeserializer$TypeDeserializer;", "computeVisibilityStatus", "Lorg/jetbrains/kotlin/library/abi/impl/LibraryDeserializer$VisibilityStatus;", "proto", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrDeclarationBase;", "annotations", "", "Lorg/jetbrains/kotlin/library/abi/AbiQualifiedName;", "containingClassModality", "Lorg/jetbrains/kotlin/library/abi/AbiModality;", "parentPropertyVisibilityStatus", "deserializeAnnotations", "deserializeClass", "Lorg/jetbrains/kotlin/library/abi/AbiClass;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrClass;", "containingEntity", "Lorg/jetbrains/kotlin/library/abi/impl/LibraryDeserializer$ContainingEntity;", "parentTypeParameterResolver", "Lorg/jetbrains/kotlin/library/abi/impl/LibraryDeserializer$TypeParameterResolver;", "deserializeDeclaration", "Lorg/jetbrains/kotlin/library/abi/AbiDeclaration;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrDeclaration;", "deserializeEnumEntry", "Lorg/jetbrains/kotlin/library/abi/AbiEnumEntry;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrEnumEntry;", "deserializeFunction", "Lorg/jetbrains/kotlin/library/abi/AbiFunction;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrFunctionBase;", "isConstructor", "", "deserializeIdSignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "symbolId", "", "deserializeProperty", "Lorg/jetbrains/kotlin/library/abi/AbiProperty;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrProperty;", "typeParameterResolver", "deserializeQualifiedName", "nameId", "deserializeTo", "", "output", "", "deserializeTypeParameters", "Lorg/jetbrains/kotlin/library/abi/AbiTypeParameter;", "protos", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrTypeParameter;", "deserializeTypes", "Lorg/jetbrains/kotlin/library/abi/AbiType;", "typeIds", "predicate", "Lkotlin/Function1;", "deserializeValueParameter", "Lorg/jetbrains/kotlin/library/abi/AbiValueParameter;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrValueParameter;", "isDirectlyInheritedFromNativeInteropClass", "toAbiSignatures", "Lorg/jetbrains/kotlin/library/abi/AbiSignatures;", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    final class FileDeserializer {
        private final IrLibraryFileFromBytes fileReader;
        private final String packageName;
        private final IdSignatureDeserializer signatureDeserializer;
        private final List<Integer> topLevelDeclarationIds;
        private final TypeDeserializer typeDeserializer;

        /* JADX INFO: compiled from: LibraryAbiReaderImpl.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;
            public static final /* synthetic */ int[] $EnumSwitchMapping$1;
            public static final /* synthetic */ int[] $EnumSwitchMapping$2;
            public static final /* synthetic */ int[] $EnumSwitchMapping$3;

            static {
                int[] iArr = new int[IrDeclaration.DeclaratorCase.values().length];
                try {
                    iArr[IrDeclaration.DeclaratorCase.IR_CLASS.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[IrDeclaration.DeclaratorCase.IR_CONSTRUCTOR.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[IrDeclaration.DeclaratorCase.IR_FUNCTION.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                try {
                    iArr[IrDeclaration.DeclaratorCase.IR_PROPERTY.ordinal()] = 4;
                } catch (NoSuchFieldError unused4) {
                }
                try {
                    iArr[IrDeclaration.DeclaratorCase.IR_ENUM_ENTRY.ordinal()] = 5;
                } catch (NoSuchFieldError unused5) {
                }
                $EnumSwitchMapping$0 = iArr;
                int[] iArr2 = new int[ClassKind.values().length];
                try {
                    iArr2[ClassKind.CLASS.ordinal()] = 1;
                } catch (NoSuchFieldError unused6) {
                }
                try {
                    iArr2[ClassKind.INTERFACE.ordinal()] = 2;
                } catch (NoSuchFieldError unused7) {
                }
                try {
                    iArr2[ClassKind.OBJECT.ordinal()] = 3;
                } catch (NoSuchFieldError unused8) {
                }
                try {
                    iArr2[ClassKind.ENUM_CLASS.ordinal()] = 4;
                } catch (NoSuchFieldError unused9) {
                }
                try {
                    iArr2[ClassKind.ANNOTATION_CLASS.ordinal()] = 5;
                } catch (NoSuchFieldError unused10) {
                }
                try {
                    iArr2[ClassKind.ENUM_ENTRY.ordinal()] = 6;
                } catch (NoSuchFieldError unused11) {
                }
                $EnumSwitchMapping$1 = iArr2;
                int[] iArr3 = new int[IrType.KindCase.values().length];
                try {
                    iArr3[IrType.KindCase.DNN.ordinal()] = 1;
                } catch (NoSuchFieldError unused12) {
                }
                try {
                    iArr3[IrType.KindCase.SIMPLE.ordinal()] = 2;
                } catch (NoSuchFieldError unused13) {
                }
                try {
                    iArr3[IrType.KindCase.LEGACYSIMPLE.ordinal()] = 3;
                } catch (NoSuchFieldError unused14) {
                }
                try {
                    iArr3[IrType.KindCase.DYNAMIC.ordinal()] = 4;
                } catch (NoSuchFieldError unused15) {
                }
                try {
                    iArr3[IrType.KindCase.ERROR.ordinal()] = 5;
                } catch (NoSuchFieldError unused16) {
                }
                try {
                    iArr3[IrType.KindCase.KIND_NOT_SET.ordinal()] = 6;
                } catch (NoSuchFieldError unused17) {
                }
                $EnumSwitchMapping$2 = iArr3;
                int[] iArr4 = new int[ProtoBuf.Visibility.values().length];
                try {
                    iArr4[ProtoBuf.Visibility.PUBLIC.ordinal()] = 1;
                } catch (NoSuchFieldError unused18) {
                }
                try {
                    iArr4[ProtoBuf.Visibility.PROTECTED.ordinal()] = 2;
                } catch (NoSuchFieldError unused19) {
                }
                try {
                    iArr4[ProtoBuf.Visibility.INTERNAL.ordinal()] = 3;
                } catch (NoSuchFieldError unused20) {
                }
                $EnumSwitchMapping$3 = iArr4;
            }
        }

        public FileDeserializer(int i) throws IOException {
            IrLibraryFileFromBytes irLibraryFileFromBytes = new IrLibraryFileFromBytes(new IrKlibBytesSource(LibraryDeserializer.this.library, i));
            this.fileReader = irLibraryFileFromBytes;
            IrFile from = IrFile.parseFrom(BasicIrModuleDeserializerKt.getCodedInputStream(LibraryDeserializer.this.library.file(i)), IrLibraryFileFromBytes.INSTANCE.getExtensionRegistryLite());
            List<Integer> declarationIdList = from.getDeclarationIdList();
            Intrinsics.checkNotNullExpressionValue(declarationIdList, "getDeclarationIdList(...)");
            this.topLevelDeclarationIds = declarationIdList;
            List<Integer> fqNameList = from.getFqNameList();
            Intrinsics.checkNotNullExpressionValue(fqNameList, "getFqNameList(...)");
            String strDeserializeFqName = IrFileDeserializerKt.deserializeFqName(irLibraryFileFromBytes, fqNameList);
            this.packageName = AbiCompoundName.m7310constructorimpl(strDeserializeFqName);
            String name2 = (from.hasFileEntry() && from.getFileEntry().hasName()) ? from.getFileEntry().getName() : "<unknown>";
            Object obj = new Object();
            FqName fqName = new FqName(strDeserializeFqName);
            Intrinsics.checkNotNull(name2);
            IdSignatureDeserializer idSignatureDeserializer = new IdSignatureDeserializer(irLibraryFileFromBytes, new IdSignature.FileSignature(obj, fqName, name2), LibraryDeserializer.this.interner);
            this.signatureDeserializer = idSignatureDeserializer;
            this.typeDeserializer = new TypeDeserializer(new LockBasedStorageManager("file '" + name2 + "', package '" + strDeserializeFqName + '\''), irLibraryFileFromBytes, idSignatureDeserializer);
        }

        public final void deserializeTo(List<AbiDeclaration> output) {
            Intrinsics.checkNotNullParameter(output, "output");
            AbiReadingFilter.Composite composite = LibraryDeserializer.this.compositeFilter;
            if (composite == null || !composite.mo7327isPackageExcluded6OaPNJE(this.packageName)) {
                Iterator<T> it = this.topLevelDeclarationIds.iterator();
                while (it.hasNext()) {
                    AbiDeclaration abiDeclarationDeserializeDeclaration = deserializeDeclaration(this.fileReader.declaration(((Number) it.next()).intValue()), new ContainingEntity.Package(this.packageName, null), null);
                    if (abiDeclarationDeserializeDeclaration != null) {
                        output.add(abiDeclarationDeserializeDeclaration);
                    }
                }
            }
        }

        private final AbiDeclaration deserializeDeclaration(IrDeclaration proto, ContainingEntity containingEntity, TypeParameterResolver parentTypeParameterResolver) {
            AbiClass abiClassDeserializeClass;
            LibraryDeserializer libraryDeserializer = LibraryDeserializer.this;
            IrDeclaration.DeclaratorCase declaratorCase = proto.getDeclaratorCase();
            int i = declaratorCase == null ? -1 : WhenMappings.$EnumSwitchMapping$0[declaratorCase.ordinal()];
            if (i == 1) {
                IrClass irClass = proto.getIrClass();
                Intrinsics.checkNotNullExpressionValue(irClass, "getIrClass(...)");
                abiClassDeserializeClass = deserializeClass(irClass, containingEntity, parentTypeParameterResolver);
            } else if (i == 2) {
                IrFunctionBase base = proto.getIrConstructor().getBase();
                Intrinsics.checkNotNullExpressionValue(base, "getBase(...)");
                abiClassDeserializeClass = deserializeFunction(base, true, containingEntity, parentTypeParameterResolver);
            } else if (i == 3) {
                IrFunctionBase base2 = proto.getIrFunction().getBase();
                Intrinsics.checkNotNullExpressionValue(base2, "getBase(...)");
                abiClassDeserializeClass = deserializeFunction(base2, false, containingEntity, parentTypeParameterResolver);
            } else if (i == 4) {
                IrProperty irProperty = proto.getIrProperty();
                Intrinsics.checkNotNullExpressionValue(irProperty, "getIrProperty(...)");
                abiClassDeserializeClass = deserializeProperty(irProperty, containingEntity, parentTypeParameterResolver);
            } else if (i != 5) {
                abiClassDeserializeClass = null;
            } else {
                IrEnumEntry irEnumEntry = proto.getIrEnumEntry();
                Intrinsics.checkNotNullExpressionValue(irEnumEntry, "getIrEnumEntry(...)");
                abiClassDeserializeClass = deserializeEnumEntry(irEnumEntry, containingEntity);
            }
            return libraryDeserializer.discardIfExcluded(abiClassDeserializeClass);
        }

        private final AbiClass deserializeClass(final IrClass proto, ContainingEntity containingEntity, TypeParameterResolver parentTypeParameterResolver) {
            AbiClassKind abiClassKind;
            IrDeclarationBase base = proto.getBase();
            Intrinsics.checkNotNullExpressionValue(base, "getBase(...)");
            Set<AbiQualifiedName> setDeserializeAnnotations = deserializeAnnotations(base);
            ContainingEntity.Class r0 = containingEntity instanceof ContainingEntity.Class ? (ContainingEntity.Class) containingEntity : null;
            AbiModality modality = r0 != null ? r0.getModality() : null;
            IrDeclarationBase base2 = proto.getBase();
            Intrinsics.checkNotNullExpressionValue(base2, "getBase(...)");
            if (!computeVisibilityStatus$default(this, base2, setDeserializeAnnotations, modality, null, 8, null).getIsPubliclyVisible()) {
                return null;
            }
            long jM6241decodeK2PyDQs = ClassFlags.INSTANCE.m6241decodeK2PyDQs(proto.getBase().getFlags());
            AbiModality abiModality = LibraryDeserializer.INSTANCE.toAbiModality(ClassFlags.m6229getModalityimpl(jM6241decodeK2PyDQs), null);
            AbiQualifiedName abiQualifiedNameDeserializeQualifiedName = deserializeQualifiedName(proto.getName(), containingEntity);
            final LibraryDeserializer libraryDeserializer = LibraryDeserializer.this;
            ContainingEntity.Class r3 = new ContainingEntity.Class(abiQualifiedNameDeserializeQualifiedName, abiModality, new Function0<Boolean>() { // from class: org.jetbrains.kotlin.library.abi.impl.LibraryDeserializer$FileDeserializer$deserializeClass$thisClassEntity$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                public final Boolean invoke() {
                    return Boolean.valueOf((libraryDeserializer.platform == BuiltInsPlatform.NATIVE && this.isDirectlyInheritedFromNativeInteropClass(proto)) ? false : true);
                }
            });
            TypeParameterResolver typeParameterResolver = new TypeParameterResolver(abiQualifiedNameDeserializeQualifiedName, ClassFlags.m6237isInnerimpl(jM6241decodeK2PyDQs) ? parentTypeParameterResolver : null, (ClassFlags.m6237isInnerimpl(jM6241decodeK2PyDQs) || parentTypeParameterResolver == null) ? 0 : parentTypeParameterResolver.getLevel() + 1);
            List<IrDeclaration> declarationList = proto.getDeclarationList();
            Intrinsics.checkNotNullExpressionValue(declarationList, "getDeclarationList(...)");
            ArrayList arrayList = new ArrayList();
            for (IrDeclaration irDeclaration : declarationList) {
                Intrinsics.checkNotNull(irDeclaration);
                AbiDeclaration abiDeclarationDeserializeDeclaration = deserializeDeclaration(irDeclaration, r3, typeParameterResolver);
                if (abiDeclarationDeserializeDeclaration != null) {
                    arrayList.add(abiDeclarationDeserializeDeclaration);
                }
            }
            List listCompactIfPossible = CollectionsKt.compactIfPossible(arrayList);
            AbiSignatures abiSignatures = toAbiSignatures(deserializeIdSignature(proto.getBase().getSymbol()));
            ClassKind classKindM6228getKindimpl = ClassFlags.m6228getKindimpl(jM6241decodeK2PyDQs);
            switch (WhenMappings.$EnumSwitchMapping$1[classKindM6228getKindimpl.ordinal()]) {
                case 1:
                    abiClassKind = AbiClassKind.CLASS;
                    break;
                case 2:
                    abiClassKind = AbiClassKind.INTERFACE;
                    break;
                case 3:
                    abiClassKind = AbiClassKind.OBJECT;
                    break;
                case 4:
                    abiClassKind = AbiClassKind.ENUM_CLASS;
                    break;
                case 5:
                    abiClassKind = AbiClassKind.ANNOTATION_CLASS;
                    break;
                case 6:
                    throw new IllegalStateException(("Unexpected class kind: " + classKindM6228getKindimpl).toString());
                default:
                    throw new NoWhenBranchMatchedException();
            }
            AbiClassKind abiClassKind2 = abiClassKind;
            boolean zM6237isInnerimpl = ClassFlags.m6237isInnerimpl(jM6241decodeK2PyDQs);
            boolean zM6238isValueimpl = ClassFlags.m6238isValueimpl(jM6241decodeK2PyDQs);
            boolean zM6236isFunimpl = ClassFlags.m6236isFunimpl(jM6241decodeK2PyDQs);
            List<Integer> superTypeList = proto.getSuperTypeList();
            Intrinsics.checkNotNullExpressionValue(superTypeList, "getSuperTypeList(...)");
            ArrayList arrayList2 = new ArrayList();
            Iterator<T> it = superTypeList.iterator();
            while (it.hasNext()) {
                AbiType abiTypeDeserializeType = this.typeDeserializer.deserializeType(((Number) it.next()).intValue(), typeParameterResolver);
                if (LibraryDeserializer.INSTANCE.isKotlinBuiltInType(abiTypeDeserializeType, LibraryDeserializer.KOTLIN_ANY_QUALIFIED_NAME, AbiTypeNullability.DEFINITELY_NOT_NULL) || !this.typeDeserializer.isPubliclyVisible(abiTypeDeserializeType)) {
                    abiTypeDeserializeType = null;
                }
                if (abiTypeDeserializeType != null) {
                    arrayList2.add(abiTypeDeserializeType);
                }
            }
            List listCompactIfPossible2 = CollectionsKt.compactIfPossible(arrayList2);
            List<IrTypeParameter> typeParameterList = proto.getTypeParameterList();
            Intrinsics.checkNotNullExpressionValue(typeParameterList, "getTypeParameterList(...)");
            return new AbiClassImpl(abiQualifiedNameDeserializeQualifiedName, abiSignatures, setDeserializeAnnotations, abiModality, abiClassKind2, zM6237isInnerimpl, zM6238isValueimpl, zM6236isFunimpl, listCompactIfPossible2, listCompactIfPossible, deserializeTypeParameters(typeParameterList, typeParameterResolver));
        }

        private static final IdSignature isDirectlyInheritedFromNativeInteropClass$extractIdSignature(FileDeserializer fileDeserializer, int i) {
            long classifier;
            IrType irTypeType = fileDeserializer.fileReader.type(i);
            IrType.KindCase kindCase = irTypeType.getKindCase();
            switch (kindCase == null ? -1 : WhenMappings.$EnumSwitchMapping$2[kindCase.ordinal()]) {
                case -1:
                case 4:
                case 5:
                case 6:
                    return null;
                case 0:
                default:
                    throw new NoWhenBranchMatchedException();
                case 1:
                    TypeDeserializer.Companion companion = TypeDeserializer.INSTANCE;
                    IrDefinitelyNotNullType dnn = irTypeType.getDnn();
                    Intrinsics.checkNotNullExpressionValue(dnn, "getDnn(...)");
                    return isDirectlyInheritedFromNativeInteropClass$extractIdSignature(fileDeserializer, companion.getUnderlyingTypeId(dnn));
                case 2:
                    classifier = irTypeType.getSimple().getClassifier();
                    break;
                case 3:
                    classifier = irTypeType.getLegacySimple().getClassifier();
                    break;
            }
            return fileDeserializer.deserializeIdSignature(classifier);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean isDirectlyInheritedFromNativeInteropClass(IrClass proto) {
            List<Integer> superTypeList = proto.getSuperTypeList();
            Intrinsics.checkNotNullExpressionValue(superTypeList, "getSuperTypeList(...)");
            List<Integer> list = superTypeList;
            if ((list instanceof Collection) && list.isEmpty()) {
                return false;
            }
            for (Integer num : list) {
                Intrinsics.checkNotNull(num);
                IdSignature idSignatureIsDirectlyInheritedFromNativeInteropClass$extractIdSignature = isDirectlyInheritedFromNativeInteropClass$extractIdSignature(this, num.intValue());
                if (idSignatureIsDirectlyInheritedFromNativeInteropClass$extractIdSignature == null ? false : idSignatureIsDirectlyInheritedFromNativeInteropClass$extractIdSignature.test(IdSignature.Flags.IS_NATIVE_INTEROP_LIBRARY)) {
                    return true;
                }
            }
            return false;
        }

        private final AbiEnumEntry deserializeEnumEntry(IrEnumEntry proto, ContainingEntity containingEntity) {
            AbiQualifiedName abiQualifiedNameDeserializeQualifiedName = deserializeQualifiedName(proto.getName(), containingEntity);
            AbiSignatures abiSignatures = toAbiSignatures(deserializeIdSignature(proto.getBase().getSymbol()));
            IrDeclarationBase base = proto.getBase();
            Intrinsics.checkNotNullExpressionValue(base, "getBase(...)");
            return new AbiEnumEntryImpl(abiQualifiedNameDeserializeQualifiedName, abiSignatures, deserializeAnnotations(base));
        }

        private final AbiFunction deserializeFunction(IrFunctionBase proto, boolean isConstructor, ContainingEntity containingEntity, TypeParameterResolver parentTypeParameterResolver) {
            ContainingEntity.Property property;
            ContainingEntity.Class containingClass;
            TypeParameterResolver typeParameterResolver;
            AbiValueParameter abiValueParameterDeserializeValueParameter;
            IrDeclarationBase base = proto.getBase();
            Intrinsics.checkNotNullExpressionValue(base, "getBase(...)");
            Set<AbiQualifiedName> setDeserializeAnnotations = deserializeAnnotations(base);
            if (containingEntity instanceof ContainingEntity.Class) {
                containingClass = (ContainingEntity.Class) containingEntity;
                property = null;
            } else if (containingEntity instanceof ContainingEntity.Property) {
                property = (ContainingEntity.Property) containingEntity;
                containingClass = property.getContainingClass();
            } else {
                property = null;
                containingClass = null;
            }
            VisibilityStatus propertyVisibilityStatus = property != null ? property.getPropertyVisibilityStatus() : null;
            IrDeclarationBase base2 = proto.getBase();
            Intrinsics.checkNotNullExpressionValue(base2, "getBase(...)");
            if (!computeVisibilityStatus(base2, setDeserializeAnnotations, containingClass != null ? containingClass.getModality() : null, propertyVisibilityStatus).getIsPubliclyVisible()) {
                return null;
            }
            long jM6273decodeUIJRpeM = FunctionFlags.INSTANCE.m6273decodeUIJRpeM(proto.getBase().getFlags());
            if (FunctionFlags.m6263isFakeOverrideimpl(jM6273decodeUIJRpeM) && ContainingEntity.Class.INSTANCE.getExcludeFakeOverrides(containingClass)) {
                return null;
            }
            long jM6200decodeWXC2TjU = BinaryNameAndType.INSTANCE.m6200decodeWXC2TjU(proto.getNameType());
            AbiQualifiedName abiQualifiedNameDeserializeQualifiedName = deserializeQualifiedName(BinaryNameAndType.m6195getNameIndeximpl(jM6200decodeWXC2TjU), containingEntity);
            if (isConstructor) {
                Intrinsics.checkNotNull(parentTypeParameterResolver);
                typeParameterResolver = parentTypeParameterResolver;
            } else if (property != null) {
                typeParameterResolver = new TypeParameterResolver(property.getPropertyName(), parentTypeParameterResolver, 1);
            } else {
                typeParameterResolver = new TypeParameterResolver(abiQualifiedNameDeserializeQualifiedName, parentTypeParameterResolver, 0, 4, null);
            }
            if (proto.hasExtensionReceiver()) {
                IrValueParameter extensionReceiver = proto.getExtensionReceiver();
                Intrinsics.checkNotNullExpressionValue(extensionReceiver, "getExtensionReceiver(...)");
                abiValueParameterDeserializeValueParameter = deserializeValueParameter(extensionReceiver, typeParameterResolver);
            } else {
                abiValueParameterDeserializeValueParameter = null;
            }
            int contextReceiverParametersCount = proto.hasContextReceiverParametersCount() ? proto.getContextReceiverParametersCount() : 0;
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = arrayList;
            CollectionsKt.addIfNotNull(arrayList2, abiValueParameterDeserializeValueParameter);
            List<IrValueParameter> valueParameterList = proto.getValueParameterList();
            Intrinsics.checkNotNullExpressionValue(valueParameterList, "getValueParameterList(...)");
            for (IrValueParameter irValueParameter : valueParameterList) {
                Intrinsics.checkNotNull(irValueParameter);
                arrayList2.add(deserializeValueParameter(irValueParameter, typeParameterResolver));
            }
            if (isConstructor) {
                if (abiValueParameterDeserializeValueParameter != null) {
                    throw new IllegalStateException(("Unexpected extension receiver found for constructor " + abiQualifiedNameDeserializeQualifiedName).toString());
                }
                return new AbiConstructorImpl(abiQualifiedNameDeserializeQualifiedName, toAbiSignatures(deserializeIdSignature(proto.getBase().getSymbol())), setDeserializeAnnotations, FunctionFlags.m6265isInlineimpl(jM6273decodeUIJRpeM), contextReceiverParametersCount, CollectionsKt.compact(arrayList));
            }
            AbiType abiTypeDeserializeType = this.typeDeserializer.deserializeType(BinaryNameAndType.m6196getTypeIndeximpl(jM6200decodeWXC2TjU), typeParameterResolver);
            AbiType abiType = !LibraryDeserializer.INSTANCE.isKotlinBuiltInType(abiTypeDeserializeType, LibraryDeserializer.KOTLIN_UNIT_QUALIFIED_NAME, AbiTypeNullability.DEFINITELY_NOT_NULL) ? abiTypeDeserializeType : null;
            AbiSignatures abiSignatures = toAbiSignatures(deserializeIdSignature(proto.getBase().getSymbol()));
            AbiModality abiModality = LibraryDeserializer.INSTANCE.toAbiModality(FunctionFlags.m6258getModalityimpl(jM6273decodeUIJRpeM), containingClass != null ? containingClass.getModality() : null);
            boolean zM6265isInlineimpl = FunctionFlags.m6265isInlineimpl(jM6273decodeUIJRpeM);
            boolean zM6268isSuspendimpl = FunctionFlags.m6268isSuspendimpl(jM6273decodeUIJRpeM);
            List<IrTypeParameter> typeParameterList = proto.getTypeParameterList();
            Intrinsics.checkNotNullExpressionValue(typeParameterList, "getTypeParameterList(...)");
            return new AbiFunctionImpl(abiQualifiedNameDeserializeQualifiedName, abiSignatures, setDeserializeAnnotations, abiModality, zM6265isInlineimpl, zM6268isSuspendimpl, deserializeTypeParameters(typeParameterList, typeParameterResolver), abiValueParameterDeserializeValueParameter != null, contextReceiverParametersCount, CollectionsKt.compact(arrayList), abiType);
        }

        private final AbiProperty deserializeProperty(IrProperty proto, ContainingEntity containingEntity, TypeParameterResolver typeParameterResolver) {
            AbiPropertyKind abiPropertyKind;
            AbiFunction abiFunction;
            IrDeclarationBase base = proto.getBase();
            Intrinsics.checkNotNullExpressionValue(base, "getBase(...)");
            Set<AbiQualifiedName> setDeserializeAnnotations = deserializeAnnotations(base);
            AbiFunction abiFunction2 = null;
            ContainingEntity.Class r10 = containingEntity instanceof ContainingEntity.Class ? (ContainingEntity.Class) containingEntity : null;
            IrDeclarationBase base2 = proto.getBase();
            Intrinsics.checkNotNullExpressionValue(base2, "getBase(...)");
            VisibilityStatus visibilityStatusComputeVisibilityStatus$default = computeVisibilityStatus$default(this, base2, setDeserializeAnnotations, r10 != null ? r10.getModality() : null, null, 8, null);
            if (!visibilityStatusComputeVisibilityStatus$default.getIsPubliclyVisible()) {
                return null;
            }
            long jM6302decode85tB66k = PropertyFlags.INSTANCE.m6302decode85tB66k(proto.getBase().getFlags());
            if (PropertyFlags.m6296isFakeOverrideimpl(jM6302decode85tB66k) && ContainingEntity.Class.INSTANCE.getExcludeFakeOverrides(r10)) {
                return null;
            }
            AbiQualifiedName abiQualifiedNameDeserializeQualifiedName = deserializeQualifiedName(proto.getName(), containingEntity);
            ContainingEntity.Property property = new ContainingEntity.Property(abiQualifiedNameDeserializeQualifiedName, r10, visibilityStatusComputeVisibilityStatus$default);
            AbiSignatures abiSignatures = toAbiSignatures(deserializeIdSignature(proto.getBase().getSymbol()));
            AbiModality abiModality = LibraryDeserializer.INSTANCE.toAbiModality(PropertyFlags.m6289getModalityimpl(jM6302decode85tB66k), r10 != null ? r10.getModality() : null);
            if (PropertyFlags.m6292isConstimpl(jM6302decode85tB66k)) {
                abiPropertyKind = AbiPropertyKind.CONST_VAL;
            } else {
                abiPropertyKind = PropertyFlags.m6298isVarimpl(jM6302decode85tB66k) ? AbiPropertyKind.VAR : AbiPropertyKind.VAL;
            }
            AbiPropertyKind abiPropertyKind2 = abiPropertyKind;
            boolean zHasGetter = proto.hasGetter();
            LibraryDeserializer libraryDeserializer = LibraryDeserializer.this;
            if (zHasGetter) {
                IrFunctionBase base3 = proto.getGetter().getBase();
                Intrinsics.checkNotNullExpressionValue(base3, "getBase(...)");
                abiFunction = (AbiFunction) libraryDeserializer.discardIfExcluded(deserializeFunction(base3, false, property, typeParameterResolver));
            } else {
                abiFunction = null;
            }
            boolean zHasSetter = proto.hasSetter();
            LibraryDeserializer libraryDeserializer2 = LibraryDeserializer.this;
            if (zHasSetter) {
                IrFunctionBase base4 = proto.getSetter().getBase();
                Intrinsics.checkNotNullExpressionValue(base4, "getBase(...)");
                abiFunction2 = (AbiFunction) libraryDeserializer2.discardIfExcluded(deserializeFunction(base4, false, property, typeParameterResolver));
            }
            return new AbiPropertyImpl(abiQualifiedNameDeserializeQualifiedName, abiSignatures, setDeserializeAnnotations, abiModality, abiPropertyKind2, abiFunction, abiFunction2);
        }

        private final AbiQualifiedName deserializeQualifiedName(int nameId, ContainingEntity containingEntity) {
            return containingEntity.computeNestedName(this.fileReader.string(nameId));
        }

        private final IdSignature deserializeIdSignature(long symbolId) {
            return this.signatureDeserializer.deserializeIdSignature(BinarySymbolData.m6206getSignatureIdimpl(BinarySymbolData.INSTANCE.m6211decode9x8F8T0(symbolId)));
        }

        private final AbiSignatures toAbiSignatures(IdSignature idSignature) {
            return new AbiSignaturesImpl(LibraryDeserializer.this.needV1Signatures ? IdSignatureRendererKt.render(idSignature, IdSignatureRenderer.INSTANCE.getLEGACY()) : null, LibraryDeserializer.this.needV2Signatures ? IdSignatureRendererKt.render(idSignature, IdSignatureRenderer.INSTANCE.getDEFAULT()) : null);
        }

        private final List<AbiTypeParameter> deserializeTypeParameters(List<IrTypeParameter> protos, TypeParameterResolver typeParameterResolver) {
            List<IrTypeParameter> list = protos;
            ArrayList arrayList = new ArrayList(list.size());
            int i = 0;
            for (Object obj : list) {
                int i2 = i + 1;
                if (i < 0) {
                    kotlin.collections.CollectionsKt.throwIndexOverflow();
                }
                IrTypeParameter irTypeParameter = (IrTypeParameter) obj;
                long jM6322decodedTrNHuw = TypeParameterFlags.INSTANCE.m6322decodedTrNHuw(irTypeParameter.getBase().getFlags());
                String strComputeTypeParameterTag = typeParameterResolver.computeTypeParameterTag(i);
                AbiVariance abiVariance = LibraryDeserializer.INSTANCE.toAbiVariance(TypeParameterFlags.m6317getVarianceimpl(jM6322decodedTrNHuw));
                boolean zM6319isReifiedimpl = TypeParameterFlags.m6319isReifiedimpl(jM6322decodedTrNHuw);
                List<Integer> superTypeList = irTypeParameter.getSuperTypeList();
                Intrinsics.checkNotNullExpressionValue(superTypeList, "getSuperTypeList(...)");
                ArrayList arrayList2 = new ArrayList();
                Iterator<T> it = superTypeList.iterator();
                while (it.hasNext()) {
                    AbiType abiTypeDeserializeType = this.typeDeserializer.deserializeType(((Number) it.next()).intValue(), typeParameterResolver);
                    if (LibraryDeserializer.INSTANCE.isKotlinBuiltInType(abiTypeDeserializeType, LibraryDeserializer.KOTLIN_ANY_QUALIFIED_NAME, AbiTypeNullability.MARKED_NULLABLE)) {
                        abiTypeDeserializeType = null;
                    }
                    if (abiTypeDeserializeType != null) {
                        arrayList2.add(abiTypeDeserializeType);
                    }
                }
                arrayList.add(new AbiTypeParameterImpl(strComputeTypeParameterTag, abiVariance, zM6319isReifiedimpl, CollectionsKt.compactIfPossible(arrayList2)));
                i = i2;
            }
            return CollectionsKt.compactIfPossible(arrayList);
        }

        private static final AbiQualifiedName deserializeAnnotations$deserialize(FileDeserializer fileDeserializer, LibraryDeserializer libraryDeserializer, IrConstructorCall irConstructorCall) {
            IdSignature.CommonSignature commonSignature;
            IdSignature idSignatureDeserializeIdSignature = fileDeserializer.deserializeIdSignature(irConstructorCall.getSymbol());
            Companion companion = LibraryDeserializer.INSTANCE;
            if (!(idSignatureDeserializeIdSignature instanceof IdSignature.CommonSignature)) {
                if (idSignatureDeserializeIdSignature instanceof IdSignature.CompositeSignature) {
                    IdSignature.CompositeSignature compositeSignature = (IdSignature.CompositeSignature) idSignatureDeserializeIdSignature;
                    if (compositeSignature.getContainer() instanceof IdSignature.FileSignature) {
                        IdSignature inner = compositeSignature.getInner();
                        Intrinsics.checkNotNull(inner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.util.IdSignature.CommonSignature");
                        commonSignature = (IdSignature.CommonSignature) inner;
                    }
                }
                throw new IllegalStateException(("Unexpected annotation signature encountered: " + idSignatureDeserializeIdSignature.getClass() + ", " + IdSignatureRendererKt.render$default(idSignatureDeserializeIdSignature, null, 1, null)).toString());
            }
            commonSignature = (IdSignature.CommonSignature) idSignatureDeserializeIdSignature;
            String strM7310constructorimpl = AbiCompoundName.m7310constructorimpl(commonSignature.getPackageFqName());
            String declarationFqName = commonSignature.getDeclarationFqName();
            if (StringsKt.endsWith$default(declarationFqName, LibraryDeserializer.INIT_SUFFIX, false, 2, (Object) null)) {
                String strSubstring = declarationFqName.substring(0, declarationFqName.length() - LibraryDeserializer.INIT_SUFFIX.length());
                Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                return libraryDeserializer.annotationsInterner.intern(new AbiQualifiedName(strM7310constructorimpl, AbiCompoundName.m7310constructorimpl(strSubstring), null));
            }
            throw new IllegalStateException(("Annotation constructor name does not have '" + LibraryDeserializer.INIT_SUFFIX + "' suffix: " + declarationFqName).toString());
        }

        private final Set<AbiQualifiedName> deserializeAnnotations(IrDeclarationBase proto) {
            int annotationCount = proto.getAnnotationCount();
            if (annotationCount == 0) {
                return SetsKt.emptySet();
            }
            if (annotationCount == 1) {
                LibraryDeserializer libraryDeserializer = LibraryDeserializer.this;
                IrConstructorCall irConstructorCall = proto.getAnnotationList().get(0);
                Intrinsics.checkNotNullExpressionValue(irConstructorCall, "get(...)");
                return SetsKt.setOf(deserializeAnnotations$deserialize(this, libraryDeserializer, irConstructorCall));
            }
            List<IrConstructorCall> annotationList = proto.getAnnotationList();
            Intrinsics.checkNotNullExpressionValue(annotationList, "getAnnotationList(...)");
            SmartSet smartSetCreate = SmartSet.INSTANCE.create();
            LibraryDeserializer libraryDeserializer2 = LibraryDeserializer.this;
            Iterator<T> it = annotationList.iterator();
            while (it.hasNext()) {
                smartSetCreate.add(deserializeAnnotations$deserialize(this, libraryDeserializer2, (IrConstructorCall) it.next()));
            }
            return smartSetCreate;
        }

        static /* synthetic */ VisibilityStatus computeVisibilityStatus$default(FileDeserializer fileDeserializer, IrDeclarationBase irDeclarationBase, Set set, AbiModality abiModality, VisibilityStatus visibilityStatus, int i, Object obj) {
            if ((i & 8) != 0) {
                visibilityStatus = null;
            }
            return fileDeserializer.computeVisibilityStatus(irDeclarationBase, set, abiModality, visibilityStatus);
        }

        private final VisibilityStatus computeVisibilityStatus(IrDeclarationBase proto, Set<AbiQualifiedName> annotations, AbiModality containingClassModality, VisibilityStatus parentPropertyVisibilityStatus) {
            ProtoBuf.Visibility visibility = IrFlags.VISIBILITY.get((int) proto.getFlags());
            int i = visibility == null ? -1 : WhenMappings.$EnumSwitchMapping$3[visibility.ordinal()];
            if (i == 1) {
                return VisibilityStatus.PUBLIC;
            }
            if (i == 2) {
                if (containingClassModality == AbiModality.FINAL) {
                    return VisibilityStatus.NON_PUBLIC;
                }
                return VisibilityStatus.PUBLIC;
            }
            if (i == 3) {
                if (parentPropertyVisibilityStatus != VisibilityStatus.INTERNAL_PUBLISHED_API && !annotations.contains(LibraryDeserializer.PUBLISHED_API_CONSTRUCTOR_QUALIFIED_NAME)) {
                    return VisibilityStatus.NON_PUBLIC;
                }
                return VisibilityStatus.INTERNAL_PUBLISHED_API;
            }
            return VisibilityStatus.NON_PUBLIC;
        }

        private final AbiValueParameter deserializeValueParameter(IrValueParameter proto, TypeParameterResolver typeParameterResolver) {
            long jM6334decodeeyNVhwA = ValueParameterFlags.INSTANCE.m6334decodeeyNVhwA(proto.getBase().getFlags());
            return new AbiValueParameterImpl(this.typeDeserializer.deserializeType(BinaryNameAndType.m6196getTypeIndeximpl(BinaryNameAndType.INSTANCE.m6200decodeWXC2TjU(proto.getNameType())), typeParameterResolver), proto.hasVarargElementType(), proto.hasDefaultValue(), ValueParameterFlags.m6331isNoInlineimpl(jM6334decodeeyNVhwA), ValueParameterFlags.m6329isCrossInlineimpl(jM6334decodeeyNVhwA));
        }
    }

    /* JADX INFO: compiled from: LibraryAbiReaderImpl.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\br\u0018\u0000 \u00072\u00020\u0001:\u0004\u0006\u0007\b\tJ\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&\u0082\u0001\u0003\n\u000b\fø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\rÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/LibraryDeserializer$ContainingEntity;", "", "computeNestedName", "Lorg/jetbrains/kotlin/library/abi/AbiQualifiedName;", Namer.METADATA_SIMPLE_NAME, "", "Class", "Companion", "Package", "Property", "Lorg/jetbrains/kotlin/library/abi/impl/LibraryDeserializer$ContainingEntity$Class;", "Lorg/jetbrains/kotlin/library/abi/impl/LibraryDeserializer$ContainingEntity$Package;", "Lorg/jetbrains/kotlin/library/abi/impl/LibraryDeserializer$ContainingEntity$Property;", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private interface ContainingEntity {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = Companion.$$INSTANCE;

        AbiQualifiedName computeNestedName(String simpleName);

        /* JADX INFO: compiled from: LibraryAbiReaderImpl.kt */
        @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u0019\u0010\u0002\u001a\u00020\u0003ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u0005\u0010\u0006\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/LibraryDeserializer$ContainingEntity$Package;", "Lorg/jetbrains/kotlin/library/abi/impl/LibraryDeserializer$ContainingEntity;", "packageName", "Lorg/jetbrains/kotlin/library/abi/AbiCompoundName;", "(Ljava/lang/String;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "getPackageName-l9-qHIQ", "()Ljava/lang/String;", "Ljava/lang/String;", "computeNestedName", "Lorg/jetbrains/kotlin/library/abi/AbiQualifiedName;", Namer.METADATA_SIMPLE_NAME, "", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Package implements ContainingEntity {
            private final String packageName;

            public /* synthetic */ Package(String str, DefaultConstructorMarker defaultConstructorMarker) {
                this(str);
            }

            private Package(String packageName) {
                Intrinsics.checkNotNullParameter(packageName, "packageName");
                this.packageName = packageName;
            }

            /* JADX INFO: renamed from: getPackageName-l9-qHIQ, reason: not valid java name and from getter */
            public final String getPackageName() {
                return this.packageName;
            }

            @Override // org.jetbrains.kotlin.library.abi.impl.LibraryDeserializer.ContainingEntity
            public AbiQualifiedName computeNestedName(String simpleName) {
                Intrinsics.checkNotNullParameter(simpleName, "simpleName");
                return ContainingEntity.INSTANCE.m7339qualifiedName7cjYDiI(this.packageName, simpleName);
            }
        }

        /* JADX INFO: compiled from: LibraryAbiReaderImpl.kt */
        @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tJ\u0010\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u0015H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001b\u0010\f\u001a\u00020\b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/LibraryDeserializer$ContainingEntity$Class;", "Lorg/jetbrains/kotlin/library/abi/impl/LibraryDeserializer$ContainingEntity;", "className", "Lorg/jetbrains/kotlin/library/abi/AbiQualifiedName;", "modality", "Lorg/jetbrains/kotlin/library/abi/AbiModality;", "lazyExcludeFakeOverrides", "Lkotlin/Function0;", "", "(Lorg/jetbrains/kotlin/library/abi/AbiQualifiedName;Lorg/jetbrains/kotlin/library/abi/AbiModality;Lkotlin/jvm/functions/Function0;)V", "getClassName", "()Lorg/jetbrains/kotlin/library/abi/AbiQualifiedName;", "excludeFakeOverrides", "getExcludeFakeOverrides", "()Z", "excludeFakeOverrides$delegate", "Lkotlin/Lazy;", "getModality", "()Lorg/jetbrains/kotlin/library/abi/AbiModality;", "computeNestedName", Namer.METADATA_SIMPLE_NAME, "", "Companion", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Class implements ContainingEntity {

            /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
            public static final Companion INSTANCE = new Companion(null);
            private final AbiQualifiedName className;

            /* JADX INFO: renamed from: excludeFakeOverrides$delegate, reason: from kotlin metadata */
            private final Lazy excludeFakeOverrides;
            private final AbiModality modality;

            public Class(AbiQualifiedName className, AbiModality modality, Function0<Boolean> lazyExcludeFakeOverrides) {
                Intrinsics.checkNotNullParameter(className, "className");
                Intrinsics.checkNotNullParameter(modality, "modality");
                Intrinsics.checkNotNullParameter(lazyExcludeFakeOverrides, "lazyExcludeFakeOverrides");
                this.className = className;
                this.modality = modality;
                this.excludeFakeOverrides = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) lazyExcludeFakeOverrides);
            }

            public final AbiQualifiedName getClassName() {
                return this.className;
            }

            public final AbiModality getModality() {
                return this.modality;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public final boolean getExcludeFakeOverrides() {
                return ((Boolean) this.excludeFakeOverrides.getValue()).booleanValue();
            }

            @Override // org.jetbrains.kotlin.library.abi.impl.LibraryDeserializer.ContainingEntity
            public AbiQualifiedName computeNestedName(String simpleName) {
                Intrinsics.checkNotNullParameter(simpleName, "simpleName");
                return ContainingEntity.INSTANCE.qualifiedName(this.className, simpleName);
            }

            /* JADX INFO: compiled from: LibraryAbiReaderImpl.kt */
            @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\u00020\u0004*\u0004\u0018\u00010\u00058F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/LibraryDeserializer$ContainingEntity$Class$Companion;", "", "()V", "excludeFakeOverrides", "", "Lorg/jetbrains/kotlin/library/abi/impl/LibraryDeserializer$ContainingEntity$Class;", "getExcludeFakeOverrides", "(Lorg/jetbrains/kotlin/library/abi/impl/LibraryDeserializer$ContainingEntity$Class;)Z", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
            public static final class Companion {
                public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                    this();
                }

                private Companion() {
                }

                public final boolean getExcludeFakeOverrides(Class r1) {
                    if (r1 != null) {
                        return r1.getExcludeFakeOverrides();
                    }
                    return false;
                }
            }
        }

        /* JADX INFO: compiled from: LibraryAbiReaderImpl.kt */
        @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/LibraryDeserializer$ContainingEntity$Property;", "Lorg/jetbrains/kotlin/library/abi/impl/LibraryDeserializer$ContainingEntity;", "propertyName", "Lorg/jetbrains/kotlin/library/abi/AbiQualifiedName;", "containingClass", "Lorg/jetbrains/kotlin/library/abi/impl/LibraryDeserializer$ContainingEntity$Class;", "propertyVisibilityStatus", "Lorg/jetbrains/kotlin/library/abi/impl/LibraryDeserializer$VisibilityStatus;", "(Lorg/jetbrains/kotlin/library/abi/AbiQualifiedName;Lorg/jetbrains/kotlin/library/abi/impl/LibraryDeserializer$ContainingEntity$Class;Lorg/jetbrains/kotlin/library/abi/impl/LibraryDeserializer$VisibilityStatus;)V", "getContainingClass", "()Lorg/jetbrains/kotlin/library/abi/impl/LibraryDeserializer$ContainingEntity$Class;", "getPropertyName", "()Lorg/jetbrains/kotlin/library/abi/AbiQualifiedName;", "getPropertyVisibilityStatus", "()Lorg/jetbrains/kotlin/library/abi/impl/LibraryDeserializer$VisibilityStatus;", "computeNestedName", Namer.METADATA_SIMPLE_NAME, "", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Property implements ContainingEntity {
            private final Class containingClass;
            private final AbiQualifiedName propertyName;
            private final VisibilityStatus propertyVisibilityStatus;

            public Property(AbiQualifiedName propertyName, Class r3, VisibilityStatus propertyVisibilityStatus) {
                Intrinsics.checkNotNullParameter(propertyName, "propertyName");
                Intrinsics.checkNotNullParameter(propertyVisibilityStatus, "propertyVisibilityStatus");
                this.propertyName = propertyName;
                this.containingClass = r3;
                this.propertyVisibilityStatus = propertyVisibilityStatus;
            }

            public final AbiQualifiedName getPropertyName() {
                return this.propertyName;
            }

            public final Class getContainingClass() {
                return this.containingClass;
            }

            public final VisibilityStatus getPropertyVisibilityStatus() {
                return this.propertyVisibilityStatus;
            }

            @Override // org.jetbrains.kotlin.library.abi.impl.LibraryDeserializer.ContainingEntity
            public AbiQualifiedName computeNestedName(String simpleName) {
                Intrinsics.checkNotNullParameter(simpleName, "simpleName");
                return ContainingEntity.INSTANCE.qualifiedName(this.propertyName, simpleName);
            }
        }

        /* JADX INFO: compiled from: LibraryAbiReaderImpl.kt */
        @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\"\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002ø\u0001\u0000¢\u0006\u0004\b\t\u0010\nJ\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\bH\u0002\u0082\u0002\u0007\n\u0005\b¡\u001e0\u0001¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/LibraryDeserializer$ContainingEntity$Companion;", "", "()V", "qualifiedName", "Lorg/jetbrains/kotlin/library/abi/AbiQualifiedName;", "packageName", "Lorg/jetbrains/kotlin/library/abi/AbiCompoundName;", "topLevelSimpleName", "", "qualifiedName-7cjYDiI", "(Ljava/lang/String;Ljava/lang/String;)Lorg/jetbrains/kotlin/library/abi/AbiQualifiedName;", "parentName", "memberSimpleName", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion {
            static final /* synthetic */ Companion $$INSTANCE = new Companion();

            private Companion() {
            }

            /* JADX INFO: Access modifiers changed from: private */
            /* JADX INFO: renamed from: qualifiedName-7cjYDiI, reason: not valid java name */
            public final AbiQualifiedName m7339qualifiedName7cjYDiI(String packageName, String topLevelSimpleName) {
                return new AbiQualifiedName(packageName, AbiCompoundName.m7310constructorimpl(topLevelSimpleName), null);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public final AbiQualifiedName qualifiedName(AbiQualifiedName parentName, String memberSimpleName) {
                return new AbiQualifiedName(parentName.m7325getPackageNamel9qHIQ(), AbiCompoundName.m7310constructorimpl(((Object) AbiCompoundName.m7318toStringimpl(parentName.m7326getRelativeNamel9qHIQ())) + '.' + memberSimpleName), null);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: LibraryAbiReaderImpl.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0002\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0000\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0006J\u0016\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0006R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\b\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0000¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/LibraryDeserializer$TypeParameterResolver;", "", "declarationName", "Lorg/jetbrains/kotlin/library/abi/AbiQualifiedName;", "parent", "levelAdjustment", "", "(Lorg/jetbrains/kotlin/library/abi/AbiQualifiedName;Lorg/jetbrains/kotlin/library/abi/impl/LibraryDeserializer$TypeParameterResolver;I)V", FirebaseAnalytics.Param.LEVEL, "getLevel", "()I", "getParent", "()Lorg/jetbrains/kotlin/library/abi/impl/LibraryDeserializer$TypeParameterResolver;", "computeTypeParameterTag", "", "index", "resolveTypeParameterTag", "Companion", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class TypeParameterResolver {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private final AbiQualifiedName declarationName;
        private final int level;
        private final TypeParameterResolver parent;

        public TypeParameterResolver(AbiQualifiedName declarationName, TypeParameterResolver typeParameterResolver, int i) {
            Intrinsics.checkNotNullParameter(declarationName, "declarationName");
            this.declarationName = declarationName;
            this.parent = typeParameterResolver;
            this.level = (typeParameterResolver != null ? typeParameterResolver.level + 1 : 0) + i;
        }

        public /* synthetic */ TypeParameterResolver(AbiQualifiedName abiQualifiedName, TypeParameterResolver typeParameterResolver, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(abiQualifiedName, typeParameterResolver, (i2 & 4) != 0 ? 0 : i);
        }

        public final TypeParameterResolver getParent() {
            return this.parent;
        }

        public final int getLevel() {
            return this.level;
        }

        public final String computeTypeParameterTag(int index) {
            String strComputeTagPrefix = INSTANCE.computeTagPrefix(index);
            return this.level > 0 ? strComputeTagPrefix + this.level : strComputeTagPrefix;
        }

        public final String resolveTypeParameterTag(AbiQualifiedName declarationName, int index) {
            String strResolveTypeParameterTag;
            Intrinsics.checkNotNullParameter(declarationName, "declarationName");
            if (Intrinsics.areEqual(declarationName, this.declarationName)) {
                return computeTypeParameterTag(index);
            }
            TypeParameterResolver typeParameterResolver = this.parent;
            if (typeParameterResolver == null || (strResolveTypeParameterTag = typeParameterResolver.resolveTypeParameterTag(declarationName, index)) == null) {
                throw new IllegalStateException(("Type parameter with local index " + index + " can not be resolved for " + declarationName).toString());
            }
            return strResolveTypeParameterTag;
        }

        /* JADX INFO: compiled from: LibraryAbiReaderImpl.kt */
        @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0004H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/LibraryDeserializer$TypeParameterResolver$Companion;", "", "()V", "ALPHABET_SIZE", "", "computeTagPrefix", "", "index", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            /* JADX INFO: Access modifiers changed from: private */
            public final String computeTagPrefix(int index) {
                SmartList smartList = new SmartList();
                int i = index % 26;
                do {
                    smartList.add(Character.valueOf((char) (i + 65)));
                    index /= 26;
                    i = (index - 1) % 26;
                } while (index != 0);
                return smartList.size() == 1 ? String.valueOf(smartList.get(0)) : kotlin.collections.CollectionsKt.joinToString$default(kotlin.collections.CollectionsKt.asReversedMutable(smartList), "", null, null, 0, null, null, 62, null);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: LibraryAbiReaderImpl.kt */
    @Metadata(d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0002\u0018\u0000 '2\u00020\u0001:\u0001'B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0018\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J.\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00190\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0018\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0012\u001a\u00020\u001e2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0018\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0012\u001a\u00020\u001f2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0016\u0010 \u001a\u00020\u00102\u0006\u0010!\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0015J$\u0010\"\u001a\b\u0012\u0004\u0012\u00020#0\u001b2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00190\u001b2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u000e\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\u0010R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\t\u001a\u0012\u0012\u0004\u0012\u00020\u000b0\nj\b\u0012\u0004\u0012\u00020\u000b`\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/LibraryDeserializer$TypeDeserializer;", "", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "libraryFile", "Lorg/jetbrains/kotlin/backend/common/serialization/IrLibraryFile;", "signatureDeserializer", "Lorg/jetbrains/kotlin/backend/common/serialization/IdSignatureDeserializer;", "(Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/backend/common/serialization/IrLibraryFile;Lorg/jetbrains/kotlin/backend/common/serialization/IdSignatureDeserializer;)V", "nonPublicTopLevelClassNames", "Ljava/util/HashSet;", "Lorg/jetbrains/kotlin/library/abi/AbiQualifiedName;", "Lkotlin/collections/HashSet;", "typeIdToTypeCache", "Lorg/jetbrains/kotlin/storage/CacheWithNotNullValues;", "", "Lorg/jetbrains/kotlin/library/abi/AbiType;", "deserializeDefinitelyNotNullType", "proto", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrDefinitelyNotNullType;", "typeParameterResolver", "Lorg/jetbrains/kotlin/library/abi/impl/LibraryDeserializer$TypeParameterResolver;", "deserializeSimpleType", "Lorg/jetbrains/kotlin/library/abi/AbiType$Simple;", "symbolId", "", "typeArgumentIds", "", "nullability", "Lorg/jetbrains/kotlin/library/abi/AbiTypeNullability;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrSimpleType;", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrSimpleTypeLegacy;", "deserializeType", "typeId", "deserializeTypeArguments", "Lorg/jetbrains/kotlin/library/abi/AbiTypeArgument;", "isPubliclyVisible", "", ModuleXmlParser.TYPE, "Companion", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class TypeDeserializer {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private final IrLibraryFile libraryFile;
        private final HashSet<AbiQualifiedName> nonPublicTopLevelClassNames;
        private final IdSignatureDeserializer signatureDeserializer;
        private final CacheWithNotNullValues<Integer, AbiType> typeIdToTypeCache;

        /* JADX INFO: compiled from: LibraryAbiReaderImpl.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[IrSimpleTypeNullability.values().length];
                try {
                    iArr[IrSimpleTypeNullability.MARKED_NULLABLE.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[IrSimpleTypeNullability.NOT_SPECIFIED.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[IrSimpleTypeNullability.DEFINITELY_NOT_NULL.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public TypeDeserializer(StorageManager storageManager, IrLibraryFile libraryFile, IdSignatureDeserializer signatureDeserializer) {
            Intrinsics.checkNotNullParameter(storageManager, "storageManager");
            Intrinsics.checkNotNullParameter(libraryFile, "libraryFile");
            Intrinsics.checkNotNullParameter(signatureDeserializer, "signatureDeserializer");
            this.libraryFile = libraryFile;
            this.signatureDeserializer = signatureDeserializer;
            this.typeIdToTypeCache = storageManager.createCacheWithNotNullValues();
            this.nonPublicTopLevelClassNames = new HashSet<>();
        }

        public final AbiType deserializeType(final int typeId, final TypeParameterResolver typeParameterResolver) {
            Intrinsics.checkNotNullParameter(typeParameterResolver, "typeParameterResolver");
            return this.typeIdToTypeCache.computeIfAbsent(Integer.valueOf(typeId), new Function0<AbiType>() { // from class: org.jetbrains.kotlin.library.abi.impl.LibraryDeserializer$TypeDeserializer$deserializeType$1

                /* JADX INFO: compiled from: LibraryAbiReaderImpl.kt */
                @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
                public /* synthetic */ class WhenMappings {
                    public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                    static {
                        int[] iArr = new int[IrType.KindCase.values().length];
                        try {
                            iArr[IrType.KindCase.DNN.ordinal()] = 1;
                        } catch (NoSuchFieldError unused) {
                        }
                        try {
                            iArr[IrType.KindCase.SIMPLE.ordinal()] = 2;
                        } catch (NoSuchFieldError unused2) {
                        }
                        try {
                            iArr[IrType.KindCase.LEGACYSIMPLE.ordinal()] = 3;
                        } catch (NoSuchFieldError unused3) {
                        }
                        try {
                            iArr[IrType.KindCase.DYNAMIC.ordinal()] = 4;
                        } catch (NoSuchFieldError unused4) {
                        }
                        try {
                            iArr[IrType.KindCase.ERROR.ordinal()] = 5;
                        } catch (NoSuchFieldError unused5) {
                        }
                        try {
                            iArr[IrType.KindCase.KIND_NOT_SET.ordinal()] = 6;
                        } catch (NoSuchFieldError unused6) {
                        }
                        $EnumSwitchMapping$0 = iArr;
                    }
                }

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final AbiType invoke() {
                    IrType irTypeType = this.this$0.libraryFile.type(typeId);
                    IrType.KindCase kindCase = irTypeType.getKindCase();
                    switch (kindCase == null ? -1 : WhenMappings.$EnumSwitchMapping$0[kindCase.ordinal()]) {
                        case -1:
                        case 6:
                            throw new IllegalStateException(("Unexpected IR type: " + kindCase).toString());
                        case 0:
                        default:
                            throw new NoWhenBranchMatchedException();
                        case 1:
                            LibraryDeserializer.TypeDeserializer typeDeserializer = this.this$0;
                            IrDefinitelyNotNullType dnn = irTypeType.getDnn();
                            Intrinsics.checkNotNullExpressionValue(dnn, "getDnn(...)");
                            return typeDeserializer.deserializeDefinitelyNotNullType(dnn, typeParameterResolver);
                        case 2:
                            LibraryDeserializer.TypeDeserializer typeDeserializer2 = this.this$0;
                            IrSimpleType simple = irTypeType.getSimple();
                            Intrinsics.checkNotNullExpressionValue(simple, "getSimple(...)");
                            return typeDeserializer2.deserializeSimpleType(simple, typeParameterResolver);
                        case 3:
                            LibraryDeserializer.TypeDeserializer typeDeserializer3 = this.this$0;
                            IrSimpleTypeLegacy legacySimple = irTypeType.getLegacySimple();
                            Intrinsics.checkNotNullExpressionValue(legacySimple, "getLegacySimple(...)");
                            return typeDeserializer3.deserializeSimpleType(legacySimple, typeParameterResolver);
                        case 4:
                            return DynamicTypeImpl.INSTANCE;
                        case 5:
                            return ErrorTypeImpl.INSTANCE;
                    }
                }
            });
        }

        public final boolean isPubliclyVisible(AbiType type) {
            Intrinsics.checkNotNullParameter(type, "type");
            HashSet<AbiQualifiedName> hashSet = this.nonPublicTopLevelClassNames;
            AbiType.Simple simple = type instanceof AbiType.Simple ? (AbiType.Simple) type : null;
            AbiClassifierReference classifierReference = simple != null ? simple.getClassifierReference() : null;
            AbiClassifierReference.ClassReference classReference = classifierReference instanceof AbiClassifierReference.ClassReference ? (AbiClassifierReference.ClassReference) classifierReference : null;
            return !kotlin.collections.CollectionsKt.contains(hashSet, classReference != null ? classReference.getClassName() : null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final AbiType deserializeDefinitelyNotNullType(IrDefinitelyNotNullType proto, TypeParameterResolver typeParameterResolver) {
            AbiType abiTypeDeserializeType = deserializeType(INSTANCE.getUnderlyingTypeId(proto), typeParameterResolver);
            if (!(abiTypeDeserializeType instanceof AbiType.Simple)) {
                return abiTypeDeserializeType;
            }
            AbiType.Simple simple = (AbiType.Simple) abiTypeDeserializeType;
            return simple.getNullability() != AbiTypeNullability.DEFINITELY_NOT_NULL ? new SimpleTypeImpl(simple.getClassifierReference(), simple.getArguments(), AbiTypeNullability.DEFINITELY_NOT_NULL) : abiTypeDeserializeType;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final AbiType.Simple deserializeSimpleType(IrSimpleType proto, TypeParameterResolver typeParameterResolver) {
            AbiTypeNullability abiTypeNullability;
            long classifier = proto.getClassifier();
            List<Long> argumentList = proto.getArgumentList();
            Intrinsics.checkNotNullExpressionValue(argumentList, "getArgumentList(...)");
            if (proto.hasNullability()) {
                IrSimpleTypeNullability nullability = proto.getNullability();
                Intrinsics.checkNotNull(nullability);
                int i = WhenMappings.$EnumSwitchMapping$0[nullability.ordinal()];
                if (i == 1) {
                    abiTypeNullability = AbiTypeNullability.MARKED_NULLABLE;
                } else if (i == 2) {
                    abiTypeNullability = AbiTypeNullability.NOT_SPECIFIED;
                } else {
                    if (i != 3) {
                        throw new NoWhenBranchMatchedException();
                    }
                    abiTypeNullability = AbiTypeNullability.DEFINITELY_NOT_NULL;
                }
            } else {
                abiTypeNullability = AbiTypeNullability.NOT_SPECIFIED;
            }
            return deserializeSimpleType(classifier, argumentList, abiTypeNullability, typeParameterResolver);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final AbiType.Simple deserializeSimpleType(IrSimpleTypeLegacy proto, TypeParameterResolver typeParameterResolver) {
            long classifier = proto.getClassifier();
            List<Long> argumentList = proto.getArgumentList();
            Intrinsics.checkNotNullExpressionValue(argumentList, "getArgumentList(...)");
            return deserializeSimpleType(classifier, argumentList, (proto.hasHasQuestionMark() && proto.getHasQuestionMark()) ? AbiTypeNullability.MARKED_NULLABLE : AbiTypeNullability.NOT_SPECIFIED, typeParameterResolver);
        }

        private final AbiType.Simple deserializeSimpleType(long symbolId, List<Long> typeArgumentIds, AbiTypeNullability nullability, TypeParameterResolver typeParameterResolver) {
            long jM6211decode9x8F8T0 = BinarySymbolData.INSTANCE.m6211decode9x8F8T0(symbolId);
            IdSignature idSignatureDeserializeIdSignature = this.signatureDeserializer.deserializeIdSignature(BinarySymbolData.m6206getSignatureIdimpl(jM6211decode9x8F8T0));
            BinarySymbolData.SymbolKind symbolKindM6205getKindimpl = BinarySymbolData.m6205getKindimpl(jM6211decode9x8F8T0);
            if (symbolKindM6205getKindimpl == BinarySymbolData.SymbolKind.CLASS_SYMBOL && (idSignatureDeserializeIdSignature instanceof IdSignature.CommonSignature)) {
                Companion companion = LibraryDeserializer.INSTANCE;
                IdSignature.CommonSignature commonSignature = (IdSignature.CommonSignature) idSignatureDeserializeIdSignature;
                return new SimpleTypeImpl(new ClassReferenceImpl(new AbiQualifiedName(AbiCompoundName.m7310constructorimpl(commonSignature.getPackageFqName()), AbiCompoundName.m7310constructorimpl(commonSignature.getDeclarationFqName()), null)), deserializeTypeArguments(typeArgumentIds, typeParameterResolver), nullability);
            }
            if (symbolKindM6205getKindimpl == BinarySymbolData.SymbolKind.CLASS_SYMBOL && (idSignatureDeserializeIdSignature instanceof IdSignature.CompositeSignature)) {
                IdSignature.CompositeSignature compositeSignature = (IdSignature.CompositeSignature) idSignatureDeserializeIdSignature;
                if (compositeSignature.getContainer() instanceof IdSignature.FileSignature) {
                    Companion companion2 = LibraryDeserializer.INSTANCE;
                    IdSignature inner = compositeSignature.getInner();
                    Intrinsics.checkNotNull(inner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.util.IdSignature.CommonSignature");
                    IdSignature.CommonSignature commonSignature2 = (IdSignature.CommonSignature) inner;
                    AbiQualifiedName abiQualifiedName = new AbiQualifiedName(AbiCompoundName.m7310constructorimpl(commonSignature2.getPackageFqName()), AbiCompoundName.m7310constructorimpl(commonSignature2.getDeclarationFqName()), null);
                    this.nonPublicTopLevelClassNames.add(abiQualifiedName);
                    return new SimpleTypeImpl(new ClassReferenceImpl(abiQualifiedName), deserializeTypeArguments(typeArgumentIds, typeParameterResolver), nullability);
                }
            }
            if (symbolKindM6205getKindimpl == BinarySymbolData.SymbolKind.TYPE_PARAMETER_SYMBOL && (idSignatureDeserializeIdSignature instanceof IdSignature.CompositeSignature)) {
                Companion companion3 = LibraryDeserializer.INSTANCE;
                IdSignature.CompositeSignature compositeSignature2 = (IdSignature.CompositeSignature) idSignatureDeserializeIdSignature;
                IdSignature container = compositeSignature2.getContainer();
                Intrinsics.checkNotNull(container, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.util.IdSignature.CommonSignature");
                IdSignature.CommonSignature commonSignature3 = (IdSignature.CommonSignature) container;
                AbiQualifiedName abiQualifiedName2 = new AbiQualifiedName(AbiCompoundName.m7310constructorimpl(commonSignature3.getPackageFqName()), AbiCompoundName.m7310constructorimpl(commonSignature3.getDeclarationFqName()), null);
                IdSignature inner2 = compositeSignature2.getInner();
                Intrinsics.checkNotNull(inner2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.util.IdSignature.LocalSignature");
                return new SimpleTypeImpl(new TypeParameterReferenceImpl(typeParameterResolver.resolveTypeParameterTag(abiQualifiedName2, ((IdSignature.LocalSignature) inner2).index())), kotlin.collections.CollectionsKt.emptyList(), nullability);
            }
            throw new IllegalStateException(("Unexpected combination of symbol kind (" + symbolKindM6205getKindimpl + ") and a signature: " + idSignatureDeserializeIdSignature.getClass() + ", " + IdSignatureRendererKt.render$default(idSignatureDeserializeIdSignature, null, 1, null)).toString());
        }

        private final List<AbiTypeArgument> deserializeTypeArguments(List<Long> typeArgumentIds, TypeParameterResolver typeParameterResolver) {
            AbiTypeArgument typeProjectionImpl;
            if (typeArgumentIds.isEmpty()) {
                return kotlin.collections.CollectionsKt.emptyList();
            }
            List<Long> list = typeArgumentIds;
            ArrayList arrayList = new ArrayList(list.size());
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                long jM6223decode2ztXSlc = BinaryTypeProjection.INSTANCE.m6223decode2ztXSlc(((Number) it.next()).longValue());
                if (BinaryTypeProjection.m6219isStarProjectionimpl(jM6223decode2ztXSlc)) {
                    typeProjectionImpl = StarProjectionImpl.INSTANCE;
                } else {
                    typeProjectionImpl = new TypeProjectionImpl(deserializeType(BinaryTypeProjection.m6216getTypeIndeximpl(jM6223decode2ztXSlc), typeParameterResolver), LibraryDeserializer.INSTANCE.toAbiVariance(BinaryTypeProjection.m6217getVarianceimpl(jM6223decode2ztXSlc)));
                }
                arrayList.add(typeProjectionImpl);
            }
            return CollectionsKt.compactIfPossible(arrayList);
        }

        /* JADX INFO: compiled from: LibraryAbiReaderImpl.kt */
        @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0015\u0010\u0003\u001a\u00020\u0004*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/LibraryDeserializer$TypeDeserializer$Companion;", "", "()V", "underlyingTypeId", "", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrDefinitelyNotNullType;", "getUnderlyingTypeId", "(Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrDefinitelyNotNullType;)I", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            public final int getUnderlyingTypeId(IrDefinitelyNotNullType irDefinitelyNotNullType) {
                Intrinsics.checkNotNullParameter(irDefinitelyNotNullType, "<this>");
                List<Integer> typesList = irDefinitelyNotNullType.getTypesList();
                Intrinsics.checkNotNullExpressionValue(typesList, "getTypesList(...)");
                Integer num = (Integer) kotlin.collections.CollectionsKt.singleOrNull((List) typesList);
                if (num != null) {
                    return num.intValue();
                }
                throw new IllegalStateException("Only DefinitelyNotNull type is now supported".toString());
            }

            private Companion() {
            }
        }
    }

    public final AbiTopLevelDeclarations deserialize() {
        ArrayList arrayList = new ArrayList();
        int iFileCount = this.library.fileCount();
        for (int i = 0; i < iFileCount; i++) {
            new FileDeserializer(i).deserializeTo(arrayList);
        }
        return new AbiTopLevelDeclarationsImpl(CollectionsKt.compact(arrayList));
    }

    /* JADX INFO: compiled from: LibraryAbiReaderImpl.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/LibraryDeserializer$VisibilityStatus;", "", "isPubliclyVisible", "", "(Ljava/lang/String;IZ)V", "()Z", "PUBLIC", "INTERNAL_PUBLISHED_API", "NON_PUBLIC", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private enum VisibilityStatus {
        PUBLIC(true),
        INTERNAL_PUBLISHED_API(true),
        NON_PUBLIC(false);

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
        private final boolean isPubliclyVisible;

        public static EnumEntries<VisibilityStatus> getEntries() {
            return $ENTRIES;
        }

        VisibilityStatus(boolean z) {
            this.isPubliclyVisible = z;
        }

        /* JADX INFO: renamed from: isPubliclyVisible, reason: from getter */
        public final boolean getIsPubliclyVisible() {
            return this.isPubliclyVisible;
        }
    }

    /* JADX INFO: compiled from: LibraryAbiReaderImpl.kt */
    @Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J#\u0010\u0013\u001a\u00020\u0006*\u00020\u00142\u0014\b\u0002\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0016H\u0082\bJ\u0016\u0010\u0017\u001a\u00020\u0018*\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u0018H\u0002J\f\u0010\u001b\u001a\u00020\u001c*\u00020\u001dH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\u00020\bX\u0082\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\tR\u000e\u0010\n\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/LibraryDeserializer$Companion;", "", "()V", "INIT_SUFFIX", "", "KOTLIN_ANY_QUALIFIED_NAME", "Lorg/jetbrains/kotlin/library/abi/AbiQualifiedName;", "KOTLIN_COMPOUND_NAME", "Lorg/jetbrains/kotlin/library/abi/AbiCompoundName;", "Ljava/lang/String;", "KOTLIN_UNIT_QUALIFIED_NAME", "PUBLISHED_API_CONSTRUCTOR_QUALIFIED_NAME", "isKotlinBuiltInType", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/library/abi/AbiType;", "className", "nullability", "Lorg/jetbrains/kotlin/library/abi/AbiTypeNullability;", "extractQualifiedName", "Lorg/jetbrains/kotlin/ir/util/IdSignature$CommonSignature;", "transformRelativeName", "Lkotlin/Function1;", "toAbiModality", "Lorg/jetbrains/kotlin/library/abi/AbiModality;", "Lorg/jetbrains/kotlin/descriptors/Modality;", "containingClassModality", "toAbiVariance", "Lorg/jetbrains/kotlin/library/abi/AbiVariance;", "Lorg/jetbrains/kotlin/types/Variance;", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {

        /* JADX INFO: compiled from: LibraryAbiReaderImpl.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;
            public static final /* synthetic */ int[] $EnumSwitchMapping$1;

            static {
                int[] iArr = new int[Modality.values().length];
                try {
                    iArr[Modality.FINAL.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[Modality.OPEN.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[Modality.ABSTRACT.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                try {
                    iArr[Modality.SEALED.ordinal()] = 4;
                } catch (NoSuchFieldError unused4) {
                }
                $EnumSwitchMapping$0 = iArr;
                int[] iArr2 = new int[Variance.values().length];
                try {
                    iArr2[Variance.INVARIANT.ordinal()] = 1;
                } catch (NoSuchFieldError unused5) {
                }
                try {
                    iArr2[Variance.IN_VARIANCE.ordinal()] = 2;
                } catch (NoSuchFieldError unused6) {
                }
                try {
                    iArr2[Variance.OUT_VARIANCE.ordinal()] = 3;
                } catch (NoSuchFieldError unused7) {
                }
                $EnumSwitchMapping$1 = iArr2;
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean isKotlinBuiltInType(AbiType type, AbiQualifiedName className, AbiTypeNullability nullability) {
            if (!(type instanceof AbiType.Simple)) {
                return false;
            }
            AbiType.Simple simple = (AbiType.Simple) type;
            if (simple.getNullability() != nullability) {
                return false;
            }
            AbiClassifierReference classifierReference = simple.getClassifierReference();
            AbiClassifierReference.ClassReference classReference = classifierReference instanceof AbiClassifierReference.ClassReference ? (AbiClassifierReference.ClassReference) classifierReference : null;
            return Intrinsics.areEqual(classReference != null ? classReference.getClassName() : null, className);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final AbiModality toAbiModality(Modality modality, AbiModality abiModality) {
            int i = WhenMappings.$EnumSwitchMapping$0[modality.ordinal()];
            if (i == 1) {
                return AbiModality.FINAL;
            }
            if (i == 2) {
                return abiModality == AbiModality.FINAL ? AbiModality.FINAL : AbiModality.OPEN;
            }
            if (i == 3) {
                return AbiModality.ABSTRACT;
            }
            if (i == 4) {
                return AbiModality.SEALED;
            }
            throw new NoWhenBranchMatchedException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final AbiVariance toAbiVariance(Variance variance) {
            int i = WhenMappings.$EnumSwitchMapping$1[variance.ordinal()];
            if (i == 1) {
                return AbiVariance.INVARIANT;
            }
            if (i == 2) {
                return AbiVariance.IN;
            }
            if (i == 3) {
                return AbiVariance.OUT;
            }
            throw new NoWhenBranchMatchedException();
        }
    }

    static {
        String strM7310constructorimpl = AbiCompoundName.m7310constructorimpl(IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME);
        KOTLIN_COMPOUND_NAME = strM7310constructorimpl;
        PUBLISHED_API_CONSTRUCTOR_QUALIFIED_NAME = new AbiQualifiedName(strM7310constructorimpl, AbiCompoundName.m7310constructorimpl("PublishedApi"), null);
        KOTLIN_ANY_QUALIFIED_NAME = new AbiQualifiedName(strM7310constructorimpl, AbiCompoundName.m7310constructorimpl("Any"), null);
        KOTLIN_UNIT_QUALIFIED_NAME = new AbiQualifiedName(strM7310constructorimpl, AbiCompoundName.m7310constructorimpl("Unit"), null);
    }
}
