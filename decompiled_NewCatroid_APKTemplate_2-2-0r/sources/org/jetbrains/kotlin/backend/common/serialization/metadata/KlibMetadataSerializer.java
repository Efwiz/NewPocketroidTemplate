package org.jetbrains.kotlin.backend.common.serialization.metadata;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.serialization.LegacyDescriptorUtilsKt;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf;
import org.jetbrains.kotlin.library.metadata.KlibMetadataSerializationUtilsKt;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;
import org.jetbrains.kotlin.serialization.ApproximatingStringTable;
import org.jetbrains.kotlin.serialization.DescriptorSerializer;

/* JADX INFO: compiled from: KlibMetadataSerializer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0090\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b&\u0018\u00002\u00020\u0001:\u0001KBE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\t\u0012\b\b\u0002\u0010\u000b\u001a\u00020\t\u0012\u0006\u0010\f\u001a\u00020\t¢\u0006\u0002\u0010\rJ\b\u0010$\u001a\u00020\u001eH\u0004J\b\u0010%\u001a\u00020&H\u0002J\u0016\u0010'\u001a\b\u0012\u0004\u0012\u00020)0(2\u0006\u0010*\u001a\u00020+H\u0004J*\u0010,\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020/\u0012\u0004\u0012\u00020\u000f0.0-2\u0006\u00100\u001a\u00020)2\u0006\u00101\u001a\u000202H\u0002J0\u00103\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020/\u0012\u0004\u0012\u00020\u000f0.0-2\u0006\u00100\u001a\u00020)2\f\u00104\u001a\b\u0012\u0004\u0012\u00020605H\u0002J2\u00107\u001a\b\u0012\u0004\u0012\u0002080-2\u0006\u00109\u001a\u00020)2\f\u0010:\u001a\b\u0012\u0004\u0012\u0002060-2\f\u0010;\u001a\b\u0012\u0004\u0012\u0002060-H\u0004J*\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020+2\f\u0010?\u001a\b\u0012\u0004\u0012\u00020@0-2\f\u0010A\u001a\b\u0012\u0004\u0012\u00020@0-J2\u0010B\u001a\u0002HC\"\u0004\b\u0000\u0010C2\u0019\b\u0004\u0010D\u001a\u0013\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u0002HC0E¢\u0006\u0002\bFH\u0084\bø\u0001\u0000¢\u0006\u0002\u0010GJ\"\u0010H\u001a\u00020&*\u00020\u001e2\u0006\u00109\u001a\u00020)2\f\u00104\u001a\b\u0012\u0004\u0012\u0002060-H\u0002J\u0018\u0010I\u001a\b\u0012\u0004\u0012\u0002060-*\b\u0012\u0004\u0012\u0002060-H\u0004J\u0018\u0010J\u001a\b\u0012\u0004\u0012\u0002060-*\b\u0012\u0004\u0012\u0002060-H\u0002R\u0014\u0010\u000e\u001a\u0004\u0018\u00010\u000fX¤\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u0004\u0018\u00010\u000fX¤\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0011R\u000e\u0010\f\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u000b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0015R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u001a\u0010\u001d\u001a\u00020\u001eX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u0011\u0010\n\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0015\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006L"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/metadata/KlibMetadataSerializer;", "", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "metadataVersion", "Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "exportKDoc", "", "skipExpects", "includeOnlyModuleContent", "allowErrorTypes", "(Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;Lcom/intellij/openapi/project/Project;ZZZZ)V", "TOP_LEVEL_CLASS_DECLARATION_COUNT_PER_FILE", "", "getTOP_LEVEL_CLASS_DECLARATION_COUNT_PER_FILE", "()Ljava/lang/Integer;", "TOP_LEVEL_DECLARATION_COUNT_PER_FILE", "getTOP_LEVEL_DECLARATION_COUNT_PER_FILE", "getExportKDoc", "()Z", "getIncludeOnlyModuleContent", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getMetadataVersion", "()Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "getProject", "()Lcom/intellij/openapi/project/Project;", "serializerContext", "Lorg/jetbrains/kotlin/backend/common/serialization/metadata/KlibMetadataSerializer$SerializerContext;", "getSerializerContext", "()Lorg/jetbrains/kotlin/backend/common/serialization/metadata/KlibMetadataSerializer$SerializerContext;", "setSerializerContext", "(Lorg/jetbrains/kotlin/backend/common/serialization/metadata/KlibMetadataSerializer$SerializerContext;)V", "getSkipExpects", "createNewContext", "emptyPackageProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Package;", "getPackagesFqNames", "", "Lorg/jetbrains/kotlin/name/FqName;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "serializeClass", "", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;", "packageName", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "serializeClasses", "descriptors", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "serializeDescriptors", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;", "fqName", "allClassifierDescriptors", "allTopLevelDescriptors", "serializeHeader", "Lorg/jetbrains/kotlin/library/metadata/KlibMetadataProtoBuf$Header;", "moduleDescriptor", "fragmentNames", "", "emptyPackages", "withNewContext", ExifInterface.GPS_DIRECTION_TRUE, "block", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "buildPackageProto", "filterOutExpects", "filterOutExpectsWithActuals", "SerializerContext", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KlibMetadataSerializer {
    private final boolean allowErrorTypes;
    private final boolean exportKDoc;
    private final boolean includeOnlyModuleContent;
    private final LanguageVersionSettings languageVersionSettings;
    private final BinaryVersion metadataVersion;
    private final Project project;
    public SerializerContext serializerContext;
    private final boolean skipExpects;

    protected abstract Integer getTOP_LEVEL_CLASS_DECLARATION_COUNT_PER_FILE();

    protected abstract Integer getTOP_LEVEL_DECLARATION_COUNT_PER_FILE();

    public KlibMetadataSerializer(LanguageVersionSettings languageVersionSettings, BinaryVersion metadataVersion, Project project, boolean z, boolean z2, boolean z3, boolean z4) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(metadataVersion, "metadataVersion");
        this.languageVersionSettings = languageVersionSettings;
        this.metadataVersion = metadataVersion;
        this.project = project;
        this.exportKDoc = z;
        this.skipExpects = z2;
        this.includeOnlyModuleContent = z3;
        this.allowErrorTypes = z4;
    }

    public /* synthetic */ KlibMetadataSerializer(LanguageVersionSettings languageVersionSettings, BinaryVersion binaryVersion, Project project, boolean z, boolean z2, boolean z3, boolean z4, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(languageVersionSettings, binaryVersion, project, (i & 8) != 0 ? false : z, (i & 16) != 0 ? false : z2, (i & 32) != 0 ? false : z3, z4);
    }

    public final LanguageVersionSettings getLanguageVersionSettings() {
        return this.languageVersionSettings;
    }

    public final BinaryVersion getMetadataVersion() {
        return this.metadataVersion;
    }

    public final Project getProject() {
        return this.project;
    }

    public final boolean getExportKDoc() {
        return this.exportKDoc;
    }

    public final boolean getSkipExpects() {
        return this.skipExpects;
    }

    public final boolean getIncludeOnlyModuleContent() {
        return this.includeOnlyModuleContent;
    }

    public final SerializerContext getSerializerContext() {
        SerializerContext serializerContext = this.serializerContext;
        if (serializerContext != null) {
            return serializerContext;
        }
        Intrinsics.throwUninitializedPropertyAccessException("serializerContext");
        return null;
    }

    public final void setSerializerContext(SerializerContext serializerContext) {
        Intrinsics.checkNotNullParameter(serializerContext, "<set-?>");
        this.serializerContext = serializerContext;
    }

    /* JADX INFO: compiled from: KlibMetadataSerializer.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0005HÆ\u0003J'\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001R\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\t¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/metadata/KlibMetadataSerializer$SerializerContext;", "", "serializerExtension", "Lorg/jetbrains/kotlin/backend/common/serialization/metadata/KlibMetadataSerializerExtension;", "topSerializer", "Lorg/jetbrains/kotlin/serialization/DescriptorSerializer;", "classSerializer", "(Lorg/jetbrains/kotlin/backend/common/serialization/metadata/KlibMetadataSerializerExtension;Lorg/jetbrains/kotlin/serialization/DescriptorSerializer;Lorg/jetbrains/kotlin/serialization/DescriptorSerializer;)V", "getClassSerializer", "()Lorg/jetbrains/kotlin/serialization/DescriptorSerializer;", "setClassSerializer", "(Lorg/jetbrains/kotlin/serialization/DescriptorSerializer;)V", "getSerializerExtension", "()Lorg/jetbrains/kotlin/backend/common/serialization/metadata/KlibMetadataSerializerExtension;", "getTopSerializer", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class SerializerContext {
        private DescriptorSerializer classSerializer;
        private final KlibMetadataSerializerExtension serializerExtension;
        private final DescriptorSerializer topSerializer;

        public static /* synthetic */ SerializerContext copy$default(SerializerContext serializerContext, KlibMetadataSerializerExtension klibMetadataSerializerExtension, DescriptorSerializer descriptorSerializer, DescriptorSerializer descriptorSerializer2, int i, Object obj) {
            if ((i & 1) != 0) {
                klibMetadataSerializerExtension = serializerContext.serializerExtension;
            }
            if ((i & 2) != 0) {
                descriptorSerializer = serializerContext.topSerializer;
            }
            if ((i & 4) != 0) {
                descriptorSerializer2 = serializerContext.classSerializer;
            }
            return serializerContext.copy(klibMetadataSerializerExtension, descriptorSerializer, descriptorSerializer2);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final KlibMetadataSerializerExtension getSerializerExtension() {
            return this.serializerExtension;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final DescriptorSerializer getTopSerializer() {
            return this.topSerializer;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final DescriptorSerializer getClassSerializer() {
            return this.classSerializer;
        }

        public final SerializerContext copy(KlibMetadataSerializerExtension serializerExtension, DescriptorSerializer topSerializer, DescriptorSerializer classSerializer) {
            Intrinsics.checkNotNullParameter(serializerExtension, "serializerExtension");
            Intrinsics.checkNotNullParameter(topSerializer, "topSerializer");
            Intrinsics.checkNotNullParameter(classSerializer, "classSerializer");
            return new SerializerContext(serializerExtension, topSerializer, classSerializer);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof SerializerContext)) {
                return false;
            }
            SerializerContext serializerContext = (SerializerContext) other;
            return Intrinsics.areEqual(this.serializerExtension, serializerContext.serializerExtension) && Intrinsics.areEqual(this.topSerializer, serializerContext.topSerializer) && Intrinsics.areEqual(this.classSerializer, serializerContext.classSerializer);
        }

        public int hashCode() {
            return (((this.serializerExtension.hashCode() * 31) + this.topSerializer.hashCode()) * 31) + this.classSerializer.hashCode();
        }

        public String toString() {
            return "SerializerContext(serializerExtension=" + this.serializerExtension + ", topSerializer=" + this.topSerializer + ", classSerializer=" + this.classSerializer + ')';
        }

        public SerializerContext(KlibMetadataSerializerExtension serializerExtension, DescriptorSerializer topSerializer, DescriptorSerializer classSerializer) {
            Intrinsics.checkNotNullParameter(serializerExtension, "serializerExtension");
            Intrinsics.checkNotNullParameter(topSerializer, "topSerializer");
            Intrinsics.checkNotNullParameter(classSerializer, "classSerializer");
            this.serializerExtension = serializerExtension;
            this.topSerializer = topSerializer;
            this.classSerializer = classSerializer;
        }

        public /* synthetic */ SerializerContext(KlibMetadataSerializerExtension klibMetadataSerializerExtension, DescriptorSerializer descriptorSerializer, DescriptorSerializer descriptorSerializer2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(klibMetadataSerializerExtension, descriptorSerializer, (i & 4) != 0 ? descriptorSerializer : descriptorSerializer2);
        }

        public final KlibMetadataSerializerExtension getSerializerExtension() {
            return this.serializerExtension;
        }

        public final DescriptorSerializer getTopSerializer() {
            return this.topSerializer;
        }

        public final DescriptorSerializer getClassSerializer() {
            return this.classSerializer;
        }

        public final void setClassSerializer(DescriptorSerializer descriptorSerializer) {
            Intrinsics.checkNotNullParameter(descriptorSerializer, "<set-?>");
            this.classSerializer = descriptorSerializer;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final SerializerContext createNewContext() {
        KlibMetadataSerializerExtension klibMetadataSerializerExtension = new KlibMetadataSerializerExtension(this.languageVersionSettings, this.metadataVersion, new ApproximatingStringTable(), this.allowErrorTypes, this.exportKDoc);
        return new SerializerContext(klibMetadataSerializerExtension, DescriptorSerializer.INSTANCE.createTopLevel(klibMetadataSerializerExtension, this.languageVersionSettings, this.project), null, 4, null);
    }

    protected final <T> T withNewContext(Function1<? super SerializerContext, ? extends T> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        setSerializerContext(createNewContext());
        return block.invoke(getSerializerContext());
    }

    private final List<Pair<ProtoBuf.Class, Integer>> serializeClass(FqName packageName, ClassDescriptor classDescriptor) {
        SerializerContext serializerContext = getSerializerContext();
        DescriptorSerializer classSerializer = serializerContext.getClassSerializer();
        serializerContext.setClassSerializer(DescriptorSerializer.INSTANCE.create(classDescriptor, serializerContext.getSerializerExtension(), serializerContext.getClassSerializer(), this.languageVersionSettings, this.project));
        ProtoBuf.Class classBuild = serializerContext.getClassSerializer().classProto(classDescriptor).build();
        if (classBuild == null) {
            throw new IllegalStateException(("Class not serialized: " + classDescriptor).toString());
        }
        Intrinsics.checkNotNull(classBuild);
        int fqNameIndex = serializerContext.getClassSerializer().getStringTable().getFqNameIndex(classDescriptor);
        MemberScope unsubstitutedInnerClassesScope = classDescriptor.getUnsubstitutedInnerClassesScope();
        Intrinsics.checkNotNullExpressionValue(unsubstitutedInnerClassesScope, "getUnsubstitutedInnerClassesScope(...)");
        List<Pair<ProtoBuf.Class, Integer>> listSerializeClasses = serializeClasses(packageName, ResolutionScope.DefaultImpls.getContributedDescriptors$default(unsubstitutedInnerClassesScope, DescriptorKindFilter.CLASSIFIERS, null, 2, null));
        serializerContext.setClassSerializer(classSerializer);
        return CollectionsKt.plus((Collection<? extends Pair>) listSerializeClasses, new Pair(classBuild, Integer.valueOf(fqNameIndex)));
    }

    private final List<DeclarationDescriptor> filterOutExpectsWithActuals(List<? extends DeclarationDescriptor> list) {
        List<? extends DeclarationDescriptor> list2 = list;
        ArrayList arrayList = new ArrayList();
        for (Object obj : list2) {
            if (!LegacyDescriptorUtilsKt.isExpectMember((DeclarationDescriptor) obj)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add(ClassId.topLevel(DescriptorUtilsKt.getFqNameSafe((DeclarationDescriptor) it.next())));
        }
        ArrayList arrayList4 = arrayList3;
        ArrayList arrayList5 = new ArrayList();
        for (Object obj2 : list2) {
            DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) obj2;
            if (!(declarationDescriptor instanceof ClassDescriptor) || !((ClassDescriptor) declarationDescriptor).get$isExpect() || !arrayList4.contains(ClassId.topLevel(DescriptorUtilsKt.getFqNameSafe(declarationDescriptor)))) {
                arrayList5.add(obj2);
            }
        }
        return arrayList5;
    }

    protected final List<DeclarationDescriptor> filterOutExpects(List<? extends DeclarationDescriptor> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        if (!this.skipExpects) {
            return filterOutExpectsWithActuals(list);
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) obj;
            if (!LegacyDescriptorUtilsKt.isExpectMember(declarationDescriptor) || LegacyDescriptorUtilsKt.isSerializableExpectClass(declarationDescriptor)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<Pair<ProtoBuf.Class, Integer>> serializeClasses(FqName packageName, Collection<? extends DeclarationDescriptor> descriptors) {
        ArrayList arrayList = new ArrayList();
        for (Object obj : descriptors) {
            if (obj instanceof ClassDescriptor) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList2, serializeClass(packageName, (ClassDescriptor) it.next()));
        }
        return arrayList2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ProtoBuf.Package emptyPackageProto() {
        ProtoBuf.Package packageBuild = ProtoBuf.Package.newBuilder().build();
        Intrinsics.checkNotNullExpressionValue(packageBuild, "build(...)");
        return packageBuild;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ProtoBuf.Package buildPackageProto(SerializerContext serializerContext, FqName fqName, List<? extends DeclarationDescriptor> list) {
        ProtoBuf.Package packageBuild = serializerContext.getTopSerializer().packagePartProto(fqName, list).build();
        if (packageBuild != null) {
            return packageBuild;
        }
        throw new IllegalStateException(("Package fragments not serialized: for " + list).toString());
    }

    protected final List<ProtoBuf.PackageFragment> serializeDescriptors(final FqName fqName, List<? extends DeclarationDescriptor> allClassifierDescriptors, List<? extends DeclarationDescriptor> allTopLevelDescriptors) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(allClassifierDescriptors, "allClassifierDescriptors");
        Intrinsics.checkNotNullParameter(allTopLevelDescriptors, "allTopLevelDescriptors");
        List<DeclarationDescriptor> listFilterOutExpects = filterOutExpects(allClassifierDescriptors);
        List<DeclarationDescriptor> listFilterOutExpects2 = filterOutExpects(allTopLevelDescriptors);
        if (getTOP_LEVEL_CLASS_DECLARATION_COUNT_PER_FILE() != null || getTOP_LEVEL_DECLARATION_COUNT_PER_FILE() != null) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = arrayList;
            CollectionsKt.addAll(arrayList2, KlibMetadataSerializerKt.maybeChunked(listFilterOutExpects, getTOP_LEVEL_CLASS_DECLARATION_COUNT_PER_FILE(), new Function1<List<? extends DeclarationDescriptor>, ProtoBuf.PackageFragment>() { // from class: org.jetbrains.kotlin.backend.common.serialization.metadata.KlibMetadataSerializer.serializeDescriptors.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final ProtoBuf.PackageFragment invoke(List<? extends DeclarationDescriptor> descriptors) {
                    Intrinsics.checkNotNullParameter(descriptors, "descriptors");
                    KlibMetadataSerializer klibMetadataSerializer = KlibMetadataSerializer.this;
                    FqName fqName2 = fqName;
                    klibMetadataSerializer.setSerializerContext(klibMetadataSerializer.createNewContext());
                    SerializerContext serializerContext = klibMetadataSerializer.getSerializerContext();
                    List listSerializeClasses = klibMetadataSerializer.serializeClasses(fqName2, descriptors);
                    ArrayList arrayList3 = new ArrayList();
                    for (Object obj : descriptors) {
                        if (obj instanceof TypeAliasDescriptor) {
                            arrayList3.add(obj);
                        }
                    }
                    ArrayList arrayList4 = arrayList3;
                    return KlibMetadataSerializationUtilsKt.buildKlibPackageFragment(!arrayList4.isEmpty() ? klibMetadataSerializer.buildPackageProto(serializerContext, fqName2, arrayList4) : klibMetadataSerializer.emptyPackageProto(), listSerializeClasses, fqName2, descriptors.isEmpty(), serializerContext.getSerializerExtension().getStringTable());
                }
            }));
            CollectionsKt.addAll(arrayList2, KlibMetadataSerializerKt.maybeChunked(listFilterOutExpects2, getTOP_LEVEL_DECLARATION_COUNT_PER_FILE(), new Function1<List<? extends DeclarationDescriptor>, ProtoBuf.PackageFragment>() { // from class: org.jetbrains.kotlin.backend.common.serialization.metadata.KlibMetadataSerializer.serializeDescriptors.3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final ProtoBuf.PackageFragment invoke(List<? extends DeclarationDescriptor> descriptors) {
                    Intrinsics.checkNotNullParameter(descriptors, "descriptors");
                    KlibMetadataSerializer klibMetadataSerializer = KlibMetadataSerializer.this;
                    FqName fqName2 = fqName;
                    klibMetadataSerializer.setSerializerContext(klibMetadataSerializer.createNewContext());
                    SerializerContext serializerContext = klibMetadataSerializer.getSerializerContext();
                    return KlibMetadataSerializationUtilsKt.buildKlibPackageFragment(klibMetadataSerializer.buildPackageProto(serializerContext, fqName2, descriptors), CollectionsKt.emptyList(), fqName2, descriptors.isEmpty(), serializerContext.getSerializerExtension().getStringTable());
                }
            }));
            if (arrayList.isEmpty()) {
                setSerializerContext(createNewContext());
                arrayList2.add(KlibMetadataSerializationUtilsKt.buildKlibPackageFragment(emptyPackageProto(), CollectionsKt.emptyList(), fqName, true, getSerializerContext().getSerializerExtension().getStringTable()));
            }
            return arrayList;
        }
        ArrayList arrayList3 = new ArrayList();
        for (Object obj : listFilterOutExpects) {
            if (obj instanceof TypeAliasDescriptor) {
                arrayList3.add(obj);
            }
        }
        List listPlus = CollectionsKt.plus((Collection) listFilterOutExpects2, (Iterable) arrayList3);
        setSerializerContext(createNewContext());
        SerializerContext serializerContext = getSerializerContext();
        return CollectionsKt.listOf(KlibMetadataSerializationUtilsKt.buildKlibPackageFragment(listPlus.isEmpty() ? emptyPackageProto() : buildPackageProto(serializerContext, fqName, listPlus), serializeClasses(fqName, listFilterOutExpects), fqName, listFilterOutExpects2.isEmpty() && listFilterOutExpects.isEmpty(), serializerContext.getSerializerExtension().getStringTable()));
    }

    protected final Set<FqName> getPackagesFqNames(ModuleDescriptor module) {
        Intrinsics.checkNotNullParameter(module, "module");
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        FqName ROOT = FqName.ROOT;
        Intrinsics.checkNotNullExpressionValue(ROOT, "ROOT");
        getPackagesFqNames$getSubPackages(linkedHashSet, this, module, ROOT);
        return linkedHashSet;
    }

    private static final Collection<FqName> getPackagesFqNames$getSubPackagesOfModule(KlibMetadataSerializer klibMetadataSerializer, ModuleDescriptor moduleDescriptor, FqName fqName) {
        if (klibMetadataSerializer.includeOnlyModuleContent) {
            return KlibMetadataSerializerKt.getPackageFragmentProviderForModuleContentWithoutDependencies(moduleDescriptor).getSubPackagesOf(fqName, new Function1<Name, Boolean>() { // from class: org.jetbrains.kotlin.backend.common.serialization.metadata.KlibMetadataSerializer$getPackagesFqNames$getSubPackagesOfModule$1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(Name it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return true;
                }
            });
        }
        return moduleDescriptor.getSubPackagesOf(fqName, new Function1<Name, Boolean>() { // from class: org.jetbrains.kotlin.backend.common.serialization.metadata.KlibMetadataSerializer$getPackagesFqNames$getSubPackagesOfModule$2
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Name it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return true;
            }
        });
    }

    private static final void getPackagesFqNames$getSubPackages(Set<FqName> set, KlibMetadataSerializer klibMetadataSerializer, ModuleDescriptor moduleDescriptor, FqName fqName) {
        set.add(fqName);
        Iterator<T> it = getPackagesFqNames$getSubPackagesOfModule(klibMetadataSerializer, moduleDescriptor, fqName).iterator();
        while (it.hasNext()) {
            getPackagesFqNames$getSubPackages(set, klibMetadataSerializer, moduleDescriptor, (FqName) it.next());
        }
    }

    public final KlibMetadataProtoBuf.Header serializeHeader(ModuleDescriptor moduleDescriptor, List<String> fragmentNames, List<String> emptyPackages) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(fragmentNames, "fragmentNames");
        Intrinsics.checkNotNullParameter(emptyPackages, "emptyPackages");
        return KlibMetadataSerializerKt.serializeKlibHeader(this.languageVersionSettings, moduleDescriptor, fragmentNames, emptyPackages);
    }
}
