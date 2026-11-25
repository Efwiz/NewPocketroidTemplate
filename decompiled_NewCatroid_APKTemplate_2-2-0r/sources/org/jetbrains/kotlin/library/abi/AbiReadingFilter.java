package org.jetbrains.kotlin.library.abi;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: LibraryAbiReader.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\bg\u0018\u00002\u00020\u0001:\u0004\u000b\f\r\u000eJ\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u001a\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0016ø\u0001\u0000¢\u0006\u0004\b\t\u0010\nø\u0001\u0001\u0082\u0002\r\n\u0005\b¡\u001e0\u0001\n\u0004\b!0\u0001¨\u0006\u000fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/AbiReadingFilter;", "", "isDeclarationExcluded", "", "declaration", "Lorg/jetbrains/kotlin/library/abi/AbiDeclaration;", "isPackageExcluded", "packageName", "Lorg/jetbrains/kotlin/library/abi/AbiCompoundName;", "isPackageExcluded-6OaPNJE", "(Ljava/lang/String;)Z", "Composite", "ExcludedClasses", "ExcludedPackages", "NonPublicMarkerAnnotations", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
@ExperimentalLibraryAbiReader
public interface AbiReadingFilter {
    default boolean isDeclarationExcluded(AbiDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return false;
    }

    /* JADX INFO: renamed from: isPackageExcluded-6OaPNJE, reason: not valid java name */
    default boolean mo7327isPackageExcluded6OaPNJE(String packageName) {
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        return false;
    }

    /* JADX INFO: compiled from: LibraryAbiReader.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u001a\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004H\u0016ø\u0001\u0000¢\u0006\u0004\b\n\u0010\u000bR\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b¡\u001e0\u0001¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/AbiReadingFilter$ExcludedPackages;", "Lorg/jetbrains/kotlin/library/abi/AbiReadingFilter;", "excludedPackageNames", "", "Lorg/jetbrains/kotlin/library/abi/AbiCompoundName;", "(Ljava/util/Collection;)V", "", "isPackageExcluded", "", "packageName", "isPackageExcluded-6OaPNJE", "(Ljava/lang/String;)Z", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ExcludedPackages implements AbiReadingFilter {
        private final Set<AbiCompoundName> excludedPackageNames;

        public ExcludedPackages(Collection<AbiCompoundName> excludedPackageNames) {
            Intrinsics.checkNotNullParameter(excludedPackageNames, "excludedPackageNames");
            this.excludedPackageNames = CollectionsKt.toSet(excludedPackageNames);
        }

        @Override // org.jetbrains.kotlin.library.abi.AbiReadingFilter
        /* JADX INFO: renamed from: isPackageExcluded-6OaPNJE */
        public boolean mo7327isPackageExcluded6OaPNJE(String packageName) {
            Intrinsics.checkNotNullParameter(packageName, "packageName");
            if (this.excludedPackageNames.isEmpty()) {
                return false;
            }
            if (!this.excludedPackageNames.contains(AbiCompoundName.m7308boximpl(packageName))) {
                Set<AbiCompoundName> set = this.excludedPackageNames;
                if ((set instanceof Collection) && set.isEmpty()) {
                    return false;
                }
                Iterator<T> it = set.iterator();
                while (it.hasNext()) {
                    if (AbiCompoundName.m7317isContainerOf6OaPNJE(((AbiCompoundName) it.next()).m7320unboximpl(), packageName)) {
                    }
                }
                return false;
            }
            return true;
        }
    }

    /* JADX INFO: compiled from: LibraryAbiReader.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/AbiReadingFilter$ExcludedClasses;", "Lorg/jetbrains/kotlin/library/abi/AbiReadingFilter;", "excludedClassNames", "", "Lorg/jetbrains/kotlin/library/abi/AbiQualifiedName;", "(Ljava/util/Collection;)V", "", "isDeclarationExcluded", "", "declaration", "Lorg/jetbrains/kotlin/library/abi/AbiDeclaration;", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ExcludedClasses implements AbiReadingFilter {
        private final Set<AbiQualifiedName> excludedClassNames;

        public ExcludedClasses(Collection<AbiQualifiedName> excludedClassNames) {
            Intrinsics.checkNotNullParameter(excludedClassNames, "excludedClassNames");
            this.excludedClassNames = CollectionsKt.toSet(excludedClassNames);
        }

        @Override // org.jetbrains.kotlin.library.abi.AbiReadingFilter
        public boolean isDeclarationExcluded(AbiDeclaration declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return (declaration instanceof AbiClass) && this.excludedClassNames.contains(declaration.getQualifiedName());
        }
    }

    /* JADX INFO: compiled from: LibraryAbiReader.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u0016\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0007¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/AbiReadingFilter$NonPublicMarkerAnnotations;", "Lorg/jetbrains/kotlin/library/abi/AbiReadingFilter;", "nonPublicMarkerNames", "", "Lorg/jetbrains/kotlin/library/abi/AbiQualifiedName;", "(Ljava/util/Collection;)V", "", "[Lorg/jetbrains/kotlin/library/abi/AbiQualifiedName;", "isDeclarationExcluded", "", "declaration", "Lorg/jetbrains/kotlin/library/abi/AbiDeclaration;", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class NonPublicMarkerAnnotations implements AbiReadingFilter {
        private final AbiQualifiedName[] nonPublicMarkerNames;

        public NonPublicMarkerAnnotations(Collection<AbiQualifiedName> nonPublicMarkerNames) {
            Intrinsics.checkNotNullParameter(nonPublicMarkerNames, "nonPublicMarkerNames");
            this.nonPublicMarkerNames = (AbiQualifiedName[]) CollectionsKt.toSet(nonPublicMarkerNames).toArray(new AbiQualifiedName[0]);
        }

        @Override // org.jetbrains.kotlin.library.abi.AbiReadingFilter
        public boolean isDeclarationExcluded(AbiDeclaration declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            for (AbiQualifiedName abiQualifiedName : this.nonPublicMarkerNames) {
                if (declaration.hasAnnotation(abiQualifiedName)) {
                    return true;
                }
            }
            return false;
        }
    }

    /* JADX INFO: compiled from: LibraryAbiReader.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u001a\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\rH\u0016ø\u0001\u0000¢\u0006\u0004\b\u000e\u0010\u000fR\u0016\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0006\u0082\u0002\u0007\n\u0005\b¡\u001e0\u0001¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/AbiReadingFilter$Composite;", "Lorg/jetbrains/kotlin/library/abi/AbiReadingFilter;", "filters", "", "(Ljava/util/List;)V", "", "[Lorg/jetbrains/kotlin/library/abi/AbiReadingFilter;", "isDeclarationExcluded", "", "declaration", "Lorg/jetbrains/kotlin/library/abi/AbiDeclaration;", "isPackageExcluded", "packageName", "Lorg/jetbrains/kotlin/library/abi/AbiCompoundName;", "isPackageExcluded-6OaPNJE", "(Ljava/lang/String;)Z", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Composite implements AbiReadingFilter {
        private final AbiReadingFilter[] filters;

        public Composite(List<? extends AbiReadingFilter> filters) {
            Intrinsics.checkNotNullParameter(filters, "filters");
            this.filters = (AbiReadingFilter[]) filters.toArray(new AbiReadingFilter[0]);
        }

        @Override // org.jetbrains.kotlin.library.abi.AbiReadingFilter
        /* JADX INFO: renamed from: isPackageExcluded-6OaPNJE */
        public boolean mo7327isPackageExcluded6OaPNJE(String packageName) {
            Intrinsics.checkNotNullParameter(packageName, "packageName");
            for (AbiReadingFilter abiReadingFilter : this.filters) {
                if (abiReadingFilter.mo7327isPackageExcluded6OaPNJE(packageName)) {
                    return true;
                }
            }
            return false;
        }

        @Override // org.jetbrains.kotlin.library.abi.AbiReadingFilter
        public boolean isDeclarationExcluded(AbiDeclaration declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            for (AbiReadingFilter abiReadingFilter : this.filters) {
                if (abiReadingFilter.isDeclarationExcluded(declaration)) {
                    return true;
                }
            }
            return false;
        }
    }
}
