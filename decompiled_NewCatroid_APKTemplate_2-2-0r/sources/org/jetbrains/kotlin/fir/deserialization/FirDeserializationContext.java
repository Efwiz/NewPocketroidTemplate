package org.jetbrains.kotlin.fir.deserialization;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.metadata.deserialization.TypeTable;
import org.jetbrains.kotlin.metadata.deserialization.VersionRequirementTable;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;

/* JADX INFO: compiled from: FirMemberDeserializer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u0000 G2\u00020\u0001:\u0001GBq\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u000b\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0012\u0012\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014\u0012\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016\u0012\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018¢\u0006\u0002\u0010\u001aJv\u0010@\u001a\u00020\u00002\f\u0010A\u001a\b\u0012\u0004\u0012\u00020B0\u00182\n\u0010C\u001a\u0006\u0012\u0002\b\u00030D2\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u000b2\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00142\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u00162\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u00122\b\b\u0002\u0010E\u001a\u00020FR\u0017\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u000f\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010\u0011\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0013\u0010\u0013\u001a\u0004\u0018\u00010\u0014¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0013\u0010$\u001a\u0004\u0018\u00010%¢\u0006\b\n\u0000\u001a\u0004\b&\u0010'R\u0011\u0010(\u001a\u00020)¢\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b,\u0010-R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b.\u0010/R\u0013\u0010\u0015\u001a\u0004\u0018\u00010\u0016¢\u0006\b\n\u0000\u001a\u0004\b0\u00101R\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018¢\u0006\b\n\u0000\u001a\u0004\b2\u0010\u001dR\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b3\u00104R\u0013\u0010\f\u001a\u0004\u0018\u00010\u000b¢\u0006\b\n\u0000\u001a\u0004\b5\u00104R\u0011\u00106\u001a\u000207¢\u0006\b\n\u0000\u001a\u0004\b8\u00109R\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b:\u0010;R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b<\u0010=R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b>\u0010?¨\u0006H"}, d2 = {"Lorg/jetbrains/kotlin/fir/deserialization/FirDeserializationContext;", "", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "typeTable", "Lorg/jetbrains/kotlin/metadata/deserialization/TypeTable;", "versionRequirementTable", "Lorg/jetbrains/kotlin/metadata/deserialization/VersionRequirementTable;", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "relativeClassName", "typeDeserializer", "Lorg/jetbrains/kotlin/fir/deserialization/FirTypeDeserializer;", "annotationDeserializer", "Lorg/jetbrains/kotlin/fir/deserialization/AbstractAnnotationDeserializer;", "constDeserializer", "Lorg/jetbrains/kotlin/fir/deserialization/FirConstDeserializer;", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "outerClassSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "outerTypeParameters", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "(Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;Lorg/jetbrains/kotlin/metadata/deserialization/TypeTable;Lorg/jetbrains/kotlin/metadata/deserialization/VersionRequirementTable;Lorg/jetbrains/kotlin/fir/FirModuleData;Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/fir/deserialization/FirTypeDeserializer;Lorg/jetbrains/kotlin/fir/deserialization/AbstractAnnotationDeserializer;Lorg/jetbrains/kotlin/fir/deserialization/FirConstDeserializer;Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;Ljava/util/List;)V", "allTypeParameters", "getAllTypeParameters", "()Ljava/util/List;", "getAnnotationDeserializer", "()Lorg/jetbrains/kotlin/fir/deserialization/AbstractAnnotationDeserializer;", "getConstDeserializer", "()Lorg/jetbrains/kotlin/fir/deserialization/FirConstDeserializer;", "getContainerSource", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "dispatchReceiver", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "getDispatchReceiver", "()Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "memberDeserializer", "Lorg/jetbrains/kotlin/fir/deserialization/FirMemberDeserializer;", "getMemberDeserializer", "()Lorg/jetbrains/kotlin/fir/deserialization/FirMemberDeserializer;", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "getNameResolver", "()Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "getOuterClassSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "getOuterTypeParameters", "getPackageFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "getRelativeClassName", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "getTypeDeserializer", "()Lorg/jetbrains/kotlin/fir/deserialization/FirTypeDeserializer;", "getTypeTable", "()Lorg/jetbrains/kotlin/metadata/deserialization/TypeTable;", "getVersionRequirementTable", "()Lorg/jetbrains/kotlin/metadata/deserialization/VersionRequirementTable;", "childContext", "typeParameterProtos", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeParameter;", "containingDeclarationSymbol", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "capturesTypeParameters", "", "Companion", "fir-deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDeserializationContext {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final List<FirTypeParameterSymbol> allTypeParameters;
    private final AbstractAnnotationDeserializer annotationDeserializer;
    private final FirConstDeserializer constDeserializer;
    private final DeserializedContainerSource containerSource;
    private final ConeClassLikeType dispatchReceiver;
    private final FirMemberDeserializer memberDeserializer;
    private final FirModuleData moduleData;
    private final NameResolver nameResolver;
    private final FirRegularClassSymbol outerClassSymbol;
    private final List<FirTypeParameterSymbol> outerTypeParameters;
    private final FqName packageFqName;
    private final FqName relativeClassName;
    private final FirSession session;
    private final FirTypeDeserializer typeDeserializer;
    private final TypeTable typeTable;
    private final VersionRequirementTable versionRequirementTable;

    public FirDeserializationContext(NameResolver nameResolver, TypeTable typeTable, VersionRequirementTable versionRequirementTable, FirModuleData moduleData, FqName packageFqName, FqName fqName, FirTypeDeserializer typeDeserializer, AbstractAnnotationDeserializer annotationDeserializer, FirConstDeserializer constDeserializer, DeserializedContainerSource deserializedContainerSource, FirRegularClassSymbol firRegularClassSymbol, List<FirTypeParameterSymbol> outerTypeParameters) {
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        Intrinsics.checkNotNullParameter(versionRequirementTable, "versionRequirementTable");
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(typeDeserializer, "typeDeserializer");
        Intrinsics.checkNotNullParameter(annotationDeserializer, "annotationDeserializer");
        Intrinsics.checkNotNullParameter(constDeserializer, "constDeserializer");
        Intrinsics.checkNotNullParameter(outerTypeParameters, "outerTypeParameters");
        this.nameResolver = nameResolver;
        this.typeTable = typeTable;
        this.versionRequirementTable = versionRequirementTable;
        this.moduleData = moduleData;
        this.packageFqName = packageFqName;
        this.relativeClassName = fqName;
        this.typeDeserializer = typeDeserializer;
        this.annotationDeserializer = annotationDeserializer;
        this.constDeserializer = constDeserializer;
        this.containerSource = deserializedContainerSource;
        this.outerClassSymbol = firRegularClassSymbol;
        this.outerTypeParameters = outerTypeParameters;
        this.session = moduleData.getSession();
        List<FirTypeParameterSymbol> listPlus = CollectionsKt.plus((Collection) typeDeserializer.getOwnTypeParameters(), (Iterable) outerTypeParameters);
        this.allTypeParameters = listPlus;
        this.memberDeserializer = new FirMemberDeserializer(this);
        this.dispatchReceiver = fqName != null ? ScopeUtilsKt.defaultType(new ClassId(packageFqName, fqName, false), listPlus) : null;
    }

    public final NameResolver getNameResolver() {
        return this.nameResolver;
    }

    public final TypeTable getTypeTable() {
        return this.typeTable;
    }

    public final VersionRequirementTable getVersionRequirementTable() {
        return this.versionRequirementTable;
    }

    public final FirModuleData getModuleData() {
        return this.moduleData;
    }

    public final FqName getPackageFqName() {
        return this.packageFqName;
    }

    public final FqName getRelativeClassName() {
        return this.relativeClassName;
    }

    public final FirTypeDeserializer getTypeDeserializer() {
        return this.typeDeserializer;
    }

    public final AbstractAnnotationDeserializer getAnnotationDeserializer() {
        return this.annotationDeserializer;
    }

    public final FirConstDeserializer getConstDeserializer() {
        return this.constDeserializer;
    }

    public final DeserializedContainerSource getContainerSource() {
        return this.containerSource;
    }

    public final FirRegularClassSymbol getOuterClassSymbol() {
        return this.outerClassSymbol;
    }

    public final List<FirTypeParameterSymbol> getOuterTypeParameters() {
        return this.outerTypeParameters;
    }

    public final FirSession getSession() {
        return this.session;
    }

    public final List<FirTypeParameterSymbol> getAllTypeParameters() {
        return this.allTypeParameters;
    }

    public final FirDeserializationContext childContext(List<ProtoBuf.TypeParameter> typeParameterProtos, FirBasedSymbol<?> containingDeclarationSymbol, NameResolver nameResolver, TypeTable typeTable, FqName relativeClassName, DeserializedContainerSource containerSource, FirRegularClassSymbol outerClassSymbol, AbstractAnnotationDeserializer annotationDeserializer, FirConstDeserializer constDeserializer, boolean capturesTypeParameters) {
        Intrinsics.checkNotNullParameter(typeParameterProtos, "typeParameterProtos");
        Intrinsics.checkNotNullParameter(containingDeclarationSymbol, "containingDeclarationSymbol");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        Intrinsics.checkNotNullParameter(annotationDeserializer, "annotationDeserializer");
        Intrinsics.checkNotNullParameter(constDeserializer, "constDeserializer");
        return new FirDeserializationContext(nameResolver, typeTable, this.versionRequirementTable, this.moduleData, this.packageFqName, relativeClassName, new FirTypeDeserializer(this.moduleData, nameResolver, typeTable, annotationDeserializer, typeParameterProtos, this.typeDeserializer, containingDeclarationSymbol), annotationDeserializer, constDeserializer, containerSource, outerClassSymbol, capturesTypeParameters ? this.allTypeParameters : CollectionsKt.emptyList());
    }

    public final FirMemberDeserializer getMemberDeserializer() {
        return this.memberDeserializer;
    }

    public final ConeClassLikeType getDispatchReceiver() {
        return this.dispatchReceiver;
    }

    /* JADX INFO: compiled from: FirMemberDeserializer.kt */
    @Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JH\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u0014J@\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012Jz\u0010\u001a\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u001f\u001a\u00020\u00172\b\u0010 \u001a\u0004\u0018\u00010\u00172\f\u0010!\u001a\b\u0012\u0004\u0012\u00020#0\"2\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\f\u0010$\u001a\b\u0012\u0002\b\u0003\u0018\u00010%H\u0002¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/fir/deserialization/FirDeserializationContext$Companion;", "", "()V", "createForClass", "Lorg/jetbrains/kotlin/fir/deserialization/FirDeserializationContext;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "classProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "annotationDeserializer", "Lorg/jetbrains/kotlin/fir/deserialization/AbstractAnnotationDeserializer;", "constDeserializer", "Lorg/jetbrains/kotlin/fir/deserialization/FirConstDeserializer;", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "outerClassSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "createForPackage", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "packageProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Package;", "createRootContext", "typeTable", "Lorg/jetbrains/kotlin/metadata/deserialization/TypeTable;", "versionRequirementTable", "Lorg/jetbrains/kotlin/metadata/deserialization/VersionRequirementTable;", "packageFqName", "relativeClassName", "typeParameterProtos", "", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeParameter;", "containingDeclarationSymbol", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "fir-deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final FirDeserializationContext createForPackage(FqName fqName, ProtoBuf.Package packageProto, NameResolver nameResolver, FirModuleData moduleData, AbstractAnnotationDeserializer annotationDeserializer, FirConstDeserializer constDeserializer, DeserializedContainerSource containerSource) {
            Intrinsics.checkNotNullParameter(fqName, "fqName");
            Intrinsics.checkNotNullParameter(packageProto, "packageProto");
            Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
            Intrinsics.checkNotNullParameter(moduleData, "moduleData");
            Intrinsics.checkNotNullParameter(annotationDeserializer, "annotationDeserializer");
            Intrinsics.checkNotNullParameter(constDeserializer, "constDeserializer");
            ProtoBuf.TypeTable typeTable = packageProto.getTypeTable();
            Intrinsics.checkNotNullExpressionValue(typeTable, "getTypeTable(...)");
            TypeTable typeTable2 = new TypeTable(typeTable);
            VersionRequirementTable.Companion companion = VersionRequirementTable.INSTANCE;
            ProtoBuf.VersionRequirementTable versionRequirementTable = packageProto.getVersionRequirementTable();
            Intrinsics.checkNotNullExpressionValue(versionRequirementTable, "getVersionRequirementTable(...)");
            return createRootContext(nameResolver, typeTable2, moduleData, companion.create(versionRequirementTable), annotationDeserializer, constDeserializer, fqName, null, CollectionsKt.emptyList(), containerSource, null, null);
        }

        public final FirDeserializationContext createForClass(ClassId classId, ProtoBuf.Class classProto, NameResolver nameResolver, FirModuleData moduleData, AbstractAnnotationDeserializer annotationDeserializer, FirConstDeserializer constDeserializer, DeserializedContainerSource containerSource, FirRegularClassSymbol outerClassSymbol) {
            Intrinsics.checkNotNullParameter(classId, "classId");
            Intrinsics.checkNotNullParameter(classProto, "classProto");
            Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
            Intrinsics.checkNotNullParameter(moduleData, "moduleData");
            Intrinsics.checkNotNullParameter(annotationDeserializer, "annotationDeserializer");
            Intrinsics.checkNotNullParameter(constDeserializer, "constDeserializer");
            Intrinsics.checkNotNullParameter(outerClassSymbol, "outerClassSymbol");
            ProtoBuf.TypeTable typeTable = classProto.getTypeTable();
            Intrinsics.checkNotNullExpressionValue(typeTable, "getTypeTable(...)");
            TypeTable typeTable2 = new TypeTable(typeTable);
            VersionRequirementTable.Companion companion = VersionRequirementTable.INSTANCE;
            ProtoBuf.VersionRequirementTable versionRequirementTable = classProto.getVersionRequirementTable();
            Intrinsics.checkNotNullExpressionValue(versionRequirementTable, "getVersionRequirementTable(...)");
            VersionRequirementTable versionRequirementTableCreate = companion.create(versionRequirementTable);
            FqName packageFqName = classId.getPackageFqName();
            Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
            FqName relativeClassName = classId.getRelativeClassName();
            List<ProtoBuf.TypeParameter> typeParameterList = classProto.getTypeParameterList();
            Intrinsics.checkNotNullExpressionValue(typeParameterList, "getTypeParameterList(...)");
            return createRootContext(nameResolver, typeTable2, moduleData, versionRequirementTableCreate, annotationDeserializer, constDeserializer, packageFqName, relativeClassName, typeParameterList, containerSource, outerClassSymbol, outerClassSymbol);
        }

        private final FirDeserializationContext createRootContext(NameResolver nameResolver, TypeTable typeTable, FirModuleData moduleData, VersionRequirementTable versionRequirementTable, AbstractAnnotationDeserializer annotationDeserializer, FirConstDeserializer constDeserializer, FqName packageFqName, FqName relativeClassName, List<ProtoBuf.TypeParameter> typeParameterProtos, DeserializedContainerSource containerSource, FirRegularClassSymbol outerClassSymbol, FirBasedSymbol<?> containingDeclarationSymbol) {
            return new FirDeserializationContext(nameResolver, typeTable, versionRequirementTable, moduleData, packageFqName, relativeClassName, new FirTypeDeserializer(moduleData, nameResolver, typeTable, annotationDeserializer, typeParameterProtos, null, containingDeclarationSymbol), annotationDeserializer, constDeserializer, containerSource, outerClassSymbol, CollectionsKt.emptyList());
        }
    }
}
