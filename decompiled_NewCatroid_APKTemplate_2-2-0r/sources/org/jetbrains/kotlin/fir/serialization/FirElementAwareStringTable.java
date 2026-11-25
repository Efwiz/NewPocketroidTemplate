package org.jetbrains.kotlin.fir.serialization;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.metadata.serialization.StringTable;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: FirElementAwareStringTable.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0012\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\u0007H\u0016ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/serialization/FirElementAwareStringTable;", "Lorg/jetbrains/kotlin/metadata/serialization/StringTable;", "getFqNameIndex", "", "classLikeDeclaration", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "getLocalClassIdReplacement", "Lorg/jetbrains/kotlin/name/ClassId;", "firClass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "getQualifiedClassNameIndex", "classId", "fir-serialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface FirElementAwareStringTable extends StringTable {
    default ClassId getLocalClassIdReplacement(FirClass firClass) {
        Intrinsics.checkNotNullParameter(firClass, "firClass");
        return null;
    }

    default int getQualifiedClassNameIndex(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        String strAsString = classId.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return getQualifiedClassNameIndex(strAsString, classId.isLocal());
    }

    default int getFqNameIndex(FirClassLikeDeclaration classLikeDeclaration) {
        Intrinsics.checkNotNullParameter(classLikeDeclaration, "classLikeDeclaration");
        ClassId classId = classLikeDeclaration.getSymbol().getClassId();
        if (classId.isLocal()) {
            classId = null;
        }
        if (classId == null && (classId = getLocalClassIdReplacement((FirClass) classLikeDeclaration)) == null) {
            throw new IllegalStateException("Cannot get FQ name of local class: " + UtilsKt.render(classLikeDeclaration));
        }
        return getQualifiedClassNameIndex(classId);
    }
}
