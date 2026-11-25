package org.jetbrains.kotlin.serialization.deserialization;

import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentProvider;
import org.jetbrains.kotlin.descriptors.PackageFragmentProviderKt;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.deserialization.ClassDescriptorFactory;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.metadata.deserialization.TypeTable;
import org.jetbrains.kotlin.metadata.deserialization.VersionRequirementTable;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedClassDescriptor;

/* JADX INFO: compiled from: ClassDeserializer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00112\u00020\u0001:\u0002\u0010\u0011B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\t\u001a\u0004\u0018\u00010\b2\u0006\u0010\n\u001a\u00020\u0007H\u0002J\u001c\u0010\u000b\u001a\u0004\u0018\u00010\b2\u0006\u0010\f\u001a\u00020\r2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000fR\u001c\u0010\u0005\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/ClassDeserializer;", "", "components", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationComponents;", "(Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationComponents;)V", "classes", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/serialization/deserialization/ClassDeserializer$ClassKey;", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "createClass", ASTExpr.DEFAULT_MAP_KEY_NAME, "deserializeClass", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "classData", "Lorg/jetbrains/kotlin/serialization/deserialization/ClassData;", "ClassKey", "Companion", "deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClassDeserializer {
    private final Function1<ClassKey, ClassDescriptor> classes;
    private final DeserializationComponents components;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Set<ClassId> BLACK_LIST = SetsKt.setOf(ClassId.topLevel(StandardNames.FqNames.cloneable.toSafe()));

    public ClassDeserializer(DeserializationComponents components) {
        Intrinsics.checkNotNullParameter(components, "components");
        this.components = components;
        this.classes = components.getStorageManager().createMemoizedFunctionWithNullableValues(new Function1<ClassKey, ClassDescriptor>() { // from class: org.jetbrains.kotlin.serialization.deserialization.ClassDeserializer$classes$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final ClassDescriptor invoke(ClassDeserializer.ClassKey key) {
                Intrinsics.checkNotNullParameter(key, "key");
                return this.this$0.createClass(key);
            }
        });
    }

    public static /* synthetic */ ClassDescriptor deserializeClass$default(ClassDeserializer classDeserializer, ClassId classId, ClassData classData, int i, Object obj) {
        if ((i & 2) != 0) {
            classData = null;
        }
        return classDeserializer.deserializeClass(classId, classData);
    }

    public final ClassDescriptor deserializeClass(ClassId classId, ClassData classData) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        return this.classes.invoke(new ClassKey(classId, classData));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ClassDescriptor createClass(ClassKey key) {
        Object next;
        DeserializationContext deserializationContextCreateContext;
        PackageFragmentDescriptor packageFragmentDescriptor;
        Name shortClassName;
        ClassId classId = key.getClassId();
        Iterator<ClassDescriptorFactory> it = this.components.getFictitiousClassDescriptorFactories().iterator();
        while (it.hasNext()) {
            ClassDescriptor classDescriptorCreateClass = it.next().createClass(classId);
            if (classDescriptorCreateClass != null) {
                return classDescriptorCreateClass;
            }
        }
        if (BLACK_LIST.contains(classId)) {
            return null;
        }
        ClassData classData = key.getClassData();
        if (classData == null && (classData = this.components.getClassDataFinder().findClassData(classId)) == null) {
            return null;
        }
        NameResolver nameResolver = classData.getNameResolver();
        ProtoBuf.Class classProto = classData.getClassProto();
        BinaryVersion metadataVersion = classData.getMetadataVersion();
        SourceElement sourceElement = classData.getSourceElement();
        ClassId outerClassId = classId.getOuterClassId();
        if (outerClassId != null) {
            ClassDescriptor classDescriptorDeserializeClass$default = deserializeClass$default(this, outerClassId, null, 2, null);
            DeserializedClassDescriptor deserializedClassDescriptor = classDescriptorDeserializeClass$default instanceof DeserializedClassDescriptor ? (DeserializedClassDescriptor) classDescriptorDeserializeClass$default : null;
            if (deserializedClassDescriptor == null) {
                return null;
            }
            Name shortClassName2 = classId.getShortClassName();
            Intrinsics.checkNotNullExpressionValue(shortClassName2, "getShortClassName(...)");
            if (!deserializedClassDescriptor.hasNestedClass$deserialization(shortClassName2)) {
                return null;
            }
            deserializationContextCreateContext = deserializedClassDescriptor.getC();
        } else {
            PackageFragmentProvider packageFragmentProvider = this.components.getPackageFragmentProvider();
            FqName packageFqName = classId.getPackageFqName();
            Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
            Iterator<T> it2 = PackageFragmentProviderKt.packageFragments(packageFragmentProvider, packageFqName).iterator();
            do {
                if (!it2.hasNext()) {
                    next = null;
                    break;
                }
                next = it2.next();
                packageFragmentDescriptor = (PackageFragmentDescriptor) next;
                if (!(packageFragmentDescriptor instanceof DeserializedPackageFragment)) {
                    break;
                }
                shortClassName = classId.getShortClassName();
                Intrinsics.checkNotNullExpressionValue(shortClassName, "getShortClassName(...)");
            } while (!((DeserializedPackageFragment) packageFragmentDescriptor).hasTopLevelClass(shortClassName));
            PackageFragmentDescriptor packageFragmentDescriptor2 = (PackageFragmentDescriptor) next;
            if (packageFragmentDescriptor2 == null) {
                return null;
            }
            DeserializationComponents deserializationComponents = this.components;
            ProtoBuf.TypeTable typeTable = classProto.getTypeTable();
            Intrinsics.checkNotNullExpressionValue(typeTable, "getTypeTable(...)");
            TypeTable typeTable2 = new TypeTable(typeTable);
            VersionRequirementTable.Companion companion = VersionRequirementTable.INSTANCE;
            ProtoBuf.VersionRequirementTable versionRequirementTable = classProto.getVersionRequirementTable();
            Intrinsics.checkNotNullExpressionValue(versionRequirementTable, "getVersionRequirementTable(...)");
            deserializationContextCreateContext = deserializationComponents.createContext(packageFragmentDescriptor2, nameResolver, typeTable2, companion.create(versionRequirementTable), metadataVersion, null);
        }
        return new DeserializedClassDescriptor(deserializationContextCreateContext, classProto, nameResolver, metadataVersion, sourceElement);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: ClassDeserializer.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/ClassDeserializer$ClassKey;", "", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "classData", "Lorg/jetbrains/kotlin/serialization/deserialization/ClassData;", "(Lorg/jetbrains/kotlin/name/ClassId;Lorg/jetbrains/kotlin/serialization/deserialization/ClassData;)V", "getClassData", "()Lorg/jetbrains/kotlin/serialization/deserialization/ClassData;", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class ClassKey {
        private final ClassData classData;
        private final ClassId classId;

        public ClassKey(ClassId classId, ClassData classData) {
            Intrinsics.checkNotNullParameter(classId, "classId");
            this.classId = classId;
            this.classData = classData;
        }

        public final ClassData getClassData() {
            return this.classData;
        }

        public final ClassId getClassId() {
            return this.classId;
        }

        public boolean equals(Object other) {
            return (other instanceof ClassKey) && Intrinsics.areEqual(this.classId, ((ClassKey) other).classId);
        }

        public int hashCode() {
            return this.classId.hashCode();
        }
    }

    /* JADX INFO: compiled from: ClassDeserializer.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/ClassDeserializer$Companion;", "", "()V", "BLACK_LIST", "", "Lorg/jetbrains/kotlin/name/ClassId;", "getBLACK_LIST", "()Ljava/util/Set;", "deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Set<ClassId> getBLACK_LIST() {
            return ClassDeserializer.BLACK_LIST;
        }
    }
}
