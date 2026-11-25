package org.jetbrains.kotlin.serialization;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.serialization.Interner;
import org.jetbrains.kotlin.metadata.serialization.StringTable;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: StringTableImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0016\u0018\u00002\u00020\u0001:\u0001\u0019B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\tJ\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u0012J\u0018\u0010\u0013\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\u00072\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\r2\u0006\u0010\u0018\u001a\u00020\u0007H\u0016R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/serialization/SerializableStringTable;", "Lorg/jetbrains/kotlin/metadata/serialization/StringTable;", "()V", "qualifiedNames", "Lorg/jetbrains/kotlin/metadata/serialization/Interner;", "Lorg/jetbrains/kotlin/serialization/SerializableStringTable$FqNameProto;", "strings", "", "buildProto", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$StringTable;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$QualifiedNameTable;", "getClassIdIndex", "", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getPackageFqNameIndex", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "getQualifiedClassNameIndex", "className", "isLocal", "", "getStringIndex", "string", "FqNameProto", "serialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class SerializableStringTable implements StringTable {
    private final Interner<FqNameProto> qualifiedNames;
    private final Interner<String> strings;

    /* JADX WARN: Multi-variable type inference failed */
    public SerializableStringTable() {
        int i = 1;
        this.strings = new Interner<>(null, i, 0 == true ? 1 : 0);
        this.qualifiedNames = new Interner<>(0 == true ? 1 : 0, i, 0 == true ? 1 : 0);
    }

    /* JADX INFO: compiled from: StringTableImpl.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\n\u001a\u00020\u000bH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/serialization/SerializableStringTable$FqNameProto;", "", "fqName", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$QualifiedNameTable$QualifiedName$Builder;", "(Lorg/jetbrains/kotlin/metadata/ProtoBuf$QualifiedNameTable$QualifiedName$Builder;)V", "getFqName", "()Lorg/jetbrains/kotlin/metadata/ProtoBuf$QualifiedNameTable$QualifiedName$Builder;", Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "serialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class FqNameProto {
        private final ProtoBuf.QualifiedNameTable.QualifiedName.Builder fqName;

        public FqNameProto(ProtoBuf.QualifiedNameTable.QualifiedName.Builder fqName) {
            Intrinsics.checkNotNullParameter(fqName, "fqName");
            this.fqName = fqName;
        }

        public final ProtoBuf.QualifiedNameTable.QualifiedName.Builder getFqName() {
            return this.fqName;
        }

        public int hashCode() {
            return ((((403 + this.fqName.getParentQualifiedName()) * 31) + this.fqName.getShortName()) * 31) + this.fqName.getKind().hashCode();
        }

        public boolean equals(Object other) {
            if (other == null || !(other instanceof FqNameProto)) {
                return false;
            }
            ProtoBuf.QualifiedNameTable.QualifiedName.Builder builder = ((FqNameProto) other).fqName;
            return this.fqName.getParentQualifiedName() == builder.getParentQualifiedName() && this.fqName.getShortName() == builder.getShortName() && this.fqName.getKind() == builder.getKind();
        }
    }

    @Override // org.jetbrains.kotlin.metadata.serialization.StringTable
    public int getStringIndex(String string) {
        Intrinsics.checkNotNullParameter(string, "string");
        return this.strings.intern(string);
    }

    @Override // org.jetbrains.kotlin.metadata.serialization.StringTable
    public int getQualifiedClassNameIndex(String className, boolean isLocal) {
        Intrinsics.checkNotNullParameter(className, "className");
        ClassId classIdFromString = ClassId.fromString(className, isLocal);
        Intrinsics.checkNotNullExpressionValue(classIdFromString, "fromString(...)");
        return getClassIdIndex(classIdFromString);
    }

    public final int getClassIdIndex(ClassId classId) {
        int packageFqNameIndex;
        Intrinsics.checkNotNullParameter(classId, "classId");
        ProtoBuf.QualifiedNameTable.QualifiedName.Builder builderNewBuilder = ProtoBuf.QualifiedNameTable.QualifiedName.newBuilder();
        builderNewBuilder.setKind(ProtoBuf.QualifiedNameTable.QualifiedName.Kind.CLASS);
        ClassId outerClassId = classId.getOuterClassId();
        if (outerClassId != null) {
            packageFqNameIndex = getClassIdIndex(outerClassId);
        } else {
            FqName packageFqName = classId.getPackageFqName();
            Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
            packageFqNameIndex = getPackageFqNameIndex(packageFqName);
        }
        builderNewBuilder.setParentQualifiedName(packageFqNameIndex);
        String strAsString = classId.getShortClassName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        builderNewBuilder.setShortName(getStringIndex(strAsString));
        Interner<FqNameProto> interner = this.qualifiedNames;
        Intrinsics.checkNotNull(builderNewBuilder);
        return interner.intern(new FqNameProto(builderNewBuilder));
    }

    public final int getPackageFqNameIndex(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        int iIntern = -1;
        for (Name name2 : fqName.pathSegments()) {
            ProtoBuf.QualifiedNameTable.QualifiedName.Builder builderNewBuilder = ProtoBuf.QualifiedNameTable.QualifiedName.newBuilder();
            String strAsString = name2.asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            builderNewBuilder.setShortName(getStringIndex(strAsString));
            if (iIntern != -1) {
                builderNewBuilder.setParentQualifiedName(iIntern);
            }
            Interner<FqNameProto> interner = this.qualifiedNames;
            Intrinsics.checkNotNull(builderNewBuilder);
            iIntern = interner.intern(new FqNameProto(builderNewBuilder));
        }
        return iIntern;
    }

    public final Pair<ProtoBuf.StringTable, ProtoBuf.QualifiedNameTable> buildProto() {
        ProtoBuf.StringTable.Builder builderNewBuilder = ProtoBuf.StringTable.newBuilder();
        Iterator<String> it = this.strings.getAllInternedObjects().iterator();
        while (it.hasNext()) {
            builderNewBuilder.addString(it.next());
        }
        ProtoBuf.QualifiedNameTable.Builder builderNewBuilder2 = ProtoBuf.QualifiedNameTable.newBuilder();
        Iterator<FqNameProto> it2 = this.qualifiedNames.getAllInternedObjects().iterator();
        while (it2.hasNext()) {
            builderNewBuilder2.addQualifiedName(it2.next().getFqName());
        }
        return new Pair<>(builderNewBuilder.build(), builderNewBuilder2.build());
    }
}
