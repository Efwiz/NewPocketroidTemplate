package org.jetbrains.kotlin.load.java.lazy.descriptors;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.structure.JavaField;
import org.jetbrains.kotlin.load.java.structure.JavaMethod;
import org.jetbrains.kotlin.load.java.structure.JavaRecordComponent;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: DeclaredMemberIndex.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u0001\u000fJ\u0012\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0016\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0012\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0004\u001a\u00020\u0005H&J\u000e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\fH&J\u000e\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00050\fH&J\u000e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00050\fH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0010À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/load/java/lazy/descriptors/DeclaredMemberIndex;", "", "findFieldByName", "Lorg/jetbrains/kotlin/load/java/structure/JavaField;", "name", "Lorg/jetbrains/kotlin/name/Name;", "findMethodsByName", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaMethod;", "findRecordComponentByName", "Lorg/jetbrains/kotlin/load/java/structure/JavaRecordComponent;", "getFieldNames", "", "getMethodNames", "getRecordComponentNames", "Empty", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface DeclaredMemberIndex {
    JavaField findFieldByName(Name name2);

    Collection<JavaMethod> findMethodsByName(Name name2);

    JavaRecordComponent findRecordComponentByName(Name name2);

    Set<Name> getFieldNames();

    Set<Name> getMethodNames();

    Set<Name> getRecordComponentNames();

    /* JADX INFO: compiled from: DeclaredMemberIndex.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0012\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u000e\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\rH\u0016J\u000e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00060\rH\u0016J\u000e\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00060\rH\u0016¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/load/java/lazy/descriptors/DeclaredMemberIndex$Empty;", "Lorg/jetbrains/kotlin/load/java/lazy/descriptors/DeclaredMemberIndex;", "()V", "findFieldByName", "Lorg/jetbrains/kotlin/load/java/structure/JavaField;", "name", "Lorg/jetbrains/kotlin/name/Name;", "findMethodsByName", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaMethod;", "findRecordComponentByName", "Lorg/jetbrains/kotlin/load/java/structure/JavaRecordComponent;", "getFieldNames", "", "getMethodNames", "getRecordComponentNames", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Empty implements DeclaredMemberIndex {
        public static final Empty INSTANCE = new Empty();

        @Override // org.jetbrains.kotlin.load.java.lazy.descriptors.DeclaredMemberIndex
        public JavaField findFieldByName(Name name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            return null;
        }

        @Override // org.jetbrains.kotlin.load.java.lazy.descriptors.DeclaredMemberIndex
        public JavaRecordComponent findRecordComponentByName(Name name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            return null;
        }

        private Empty() {
        }

        @Override // org.jetbrains.kotlin.load.java.lazy.descriptors.DeclaredMemberIndex
        public List<JavaMethod> findMethodsByName(Name name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.load.java.lazy.descriptors.DeclaredMemberIndex
        public Set<Name> getMethodNames() {
            return SetsKt.emptySet();
        }

        @Override // org.jetbrains.kotlin.load.java.lazy.descriptors.DeclaredMemberIndex
        public Set<Name> getFieldNames() {
            return SetsKt.emptySet();
        }

        @Override // org.jetbrains.kotlin.load.java.lazy.descriptors.DeclaredMemberIndex
        public Set<Name> getRecordComponentNames() {
            return SetsKt.emptySet();
        }
    }
}
