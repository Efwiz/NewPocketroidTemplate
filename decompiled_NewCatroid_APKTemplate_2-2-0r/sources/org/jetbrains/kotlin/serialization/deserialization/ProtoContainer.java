package org.jetbrains.kotlin.serialization.deserialization;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.Flags;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.metadata.deserialization.TypeTable;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: ProtoContainer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u0013\u0014B!\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\b\u0010\u000f\u001a\u00020\u0010H&J\b\u0010\u0011\u001a\u00020\u0012H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u0082\u0001\u0002\u0015\u0016¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer;", "", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "typeTable", "Lorg/jetbrains/kotlin/metadata/deserialization/TypeTable;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "(Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;Lorg/jetbrains/kotlin/metadata/deserialization/TypeTable;Lorg/jetbrains/kotlin/descriptors/SourceElement;)V", "getNameResolver", "()Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "getSource", "()Lorg/jetbrains/kotlin/descriptors/SourceElement;", "getTypeTable", "()Lorg/jetbrains/kotlin/metadata/deserialization/TypeTable;", "debugFqName", "Lorg/jetbrains/kotlin/name/FqName;", "toString", "", "Class", "Package", "Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer$Class;", "Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer$Package;", "deserialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ProtoContainer {
    private final NameResolver nameResolver;
    private final SourceElement source;
    private final TypeTable typeTable;

    public /* synthetic */ ProtoContainer(NameResolver nameResolver, TypeTable typeTable, SourceElement sourceElement, DefaultConstructorMarker defaultConstructorMarker) {
        this(nameResolver, typeTable, sourceElement);
    }

    /* JADX INFO: renamed from: debugFqName */
    public abstract FqName getFqName();

    private ProtoContainer(NameResolver nameResolver, TypeTable typeTable, SourceElement sourceElement) {
        this.nameResolver = nameResolver;
        this.typeTable = typeTable;
        this.source = sourceElement;
    }

    public final NameResolver getNameResolver() {
        return this.nameResolver;
    }

    public final TypeTable getTypeTable() {
        return this.typeTable;
    }

    public final SourceElement getSource() {
        return this.source;
    }

    /* JADX INFO: compiled from: ProtoContainer.kt */
    @Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0000¢\u0006\u0002\u0010\u000bJ\b\u0010\u001b\u001a\u00020\u001cH\u0016R\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0012\u001a\u00020\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0014R\u0011\u0010\u0015\u001a\u00020\u0016¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0000¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer$Class;", "Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer;", "classProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "typeTable", "Lorg/jetbrains/kotlin/metadata/deserialization/TypeTable;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "outerClass", "(Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;Lorg/jetbrains/kotlin/metadata/deserialization/TypeTable;Lorg/jetbrains/kotlin/descriptors/SourceElement;Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer$Class;)V", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "getClassProto", "()Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;", "isInner", "", "()Z", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class$Kind;", "getKind", "()Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class$Kind;", "getOuterClass", "()Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer$Class;", "debugFqName", "Lorg/jetbrains/kotlin/name/FqName;", "deserialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Class extends ProtoContainer {
        private final ClassId classId;
        private final ProtoBuf.Class classProto;
        private final boolean isInner;
        private final ProtoBuf.Class.Kind kind;
        private final Class outerClass;

        public final ProtoBuf.Class getClassProto() {
            return this.classProto;
        }

        public final Class getOuterClass() {
            return this.outerClass;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Class(ProtoBuf.Class classProto, NameResolver nameResolver, TypeTable typeTable, SourceElement sourceElement, Class r6) {
            super(nameResolver, typeTable, sourceElement, null);
            Intrinsics.checkNotNullParameter(classProto, "classProto");
            Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
            Intrinsics.checkNotNullParameter(typeTable, "typeTable");
            this.classProto = classProto;
            this.outerClass = r6;
            this.classId = NameResolverUtilKt.getClassId(nameResolver, classProto.getFqName());
            ProtoBuf.Class.Kind kind = Flags.CLASS_KIND.get(classProto.getFlags());
            this.kind = kind == null ? ProtoBuf.Class.Kind.CLASS : kind;
            Boolean bool = Flags.IS_INNER.get(classProto.getFlags());
            Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
            this.isInner = bool.booleanValue();
        }

        public final ClassId getClassId() {
            return this.classId;
        }

        public final ProtoBuf.Class.Kind getKind() {
            return this.kind;
        }

        /* JADX INFO: renamed from: isInner, reason: from getter */
        public final boolean getIsInner() {
            return this.isInner;
        }

        @Override // org.jetbrains.kotlin.serialization.deserialization.ProtoContainer
        /* JADX INFO: renamed from: debugFqName */
        public FqName getFqName() {
            FqName fqNameAsSingleFqName = this.classId.asSingleFqName();
            Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName, "asSingleFqName(...)");
            return fqNameAsSingleFqName;
        }
    }

    /* JADX INFO: compiled from: ProtoContainer.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nJ\b\u0010\r\u001a\u00020\u0003H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer$Package;", "Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "typeTable", "Lorg/jetbrains/kotlin/metadata/deserialization/TypeTable;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "(Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;Lorg/jetbrains/kotlin/metadata/deserialization/TypeTable;Lorg/jetbrains/kotlin/descriptors/SourceElement;)V", "getFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "debugFqName", "deserialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Package extends ProtoContainer {
        private final FqName fqName;

        public final FqName getFqName() {
            return this.fqName;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Package(FqName fqName, NameResolver nameResolver, TypeTable typeTable, SourceElement sourceElement) {
            super(nameResolver, typeTable, sourceElement, null);
            Intrinsics.checkNotNullParameter(fqName, "fqName");
            Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
            Intrinsics.checkNotNullParameter(typeTable, "typeTable");
            this.fqName = fqName;
        }

        @Override // org.jetbrains.kotlin.serialization.deserialization.ProtoContainer
        /* JADX INFO: renamed from: debugFqName, reason: from getter */
        public FqName getFqName() {
            return this.fqName;
        }
    }

    public String toString() {
        return getClass().getSimpleName() + ": " + getFqName();
    }
}
