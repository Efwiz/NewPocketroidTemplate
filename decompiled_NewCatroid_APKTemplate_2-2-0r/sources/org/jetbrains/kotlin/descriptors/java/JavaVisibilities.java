package org.jetbrains.kotlin.descriptors.java;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.EffectiveVisibility;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: JavaVisibilities.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/java/JavaVisibilities;", "", "()V", "PackageVisibility", "ProtectedAndPackage", "ProtectedStaticVisibility", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaVisibilities {
    public static final JavaVisibilities INSTANCE = new JavaVisibilities();

    /* JADX INFO: compiled from: JavaVisibilities.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0017\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\u0001H\u0016¢\u0006\u0002\u0010\fJ\n\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0001H\u0016J\u0018\u0010\u0012\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014H\u0016R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/java/JavaVisibilities$PackageVisibility;", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "()V", "externalDisplayName", "", "getExternalDisplayName", "()Ljava/lang/String;", "internalDisplayName", "getInternalDisplayName", "compareTo", "", "visibility", "(Lorg/jetbrains/kotlin/descriptors/Visibility;)Ljava/lang/Integer;", "customEffectiveVisibility", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;", "mustCheckInImports", "", "normalize", "visibleFromPackage", "fromPackage", "Lorg/jetbrains/kotlin/name/FqName;", "myPackage", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class PackageVisibility extends Visibility {
        public static final PackageVisibility INSTANCE = new PackageVisibility();

        @Override // org.jetbrains.kotlin.descriptors.Visibility
        public boolean mustCheckInImports() {
            return true;
        }

        private PackageVisibility() {
            super("package", false);
        }

        @Override // org.jetbrains.kotlin.descriptors.Visibility
        public Integer compareTo(Visibility visibility) {
            Intrinsics.checkNotNullParameter(visibility, "visibility");
            if (this == visibility) {
                return 0;
            }
            return Visibilities.INSTANCE.isPrivate(visibility) ? 1 : -1;
        }

        @Override // org.jetbrains.kotlin.descriptors.Visibility
        public Visibility normalize() {
            return Visibilities.Protected.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.Visibility
        /* JADX INFO: renamed from: getInternalDisplayName */
        public String getName() {
            return "public/*package*/";
        }

        @Override // org.jetbrains.kotlin.descriptors.Visibility
        public String getExternalDisplayName() {
            return "package-private";
        }

        @Override // org.jetbrains.kotlin.descriptors.Visibility
        public EffectiveVisibility customEffectiveVisibility() {
            return EffectiveVisibility.PackagePrivate.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.Visibility
        public boolean visibleFromPackage(FqName fromPackage, FqName myPackage) {
            Intrinsics.checkNotNullParameter(fromPackage, "fromPackage");
            Intrinsics.checkNotNullParameter(myPackage, "myPackage");
            return Intrinsics.areEqual(fromPackage, myPackage);
        }
    }

    private JavaVisibilities() {
    }

    /* JADX INFO: compiled from: JavaVisibilities.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\nH\u0016J\b\u0010\u000b\u001a\u00020\u0001H\u0016R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/java/JavaVisibilities$ProtectedStaticVisibility;", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "()V", "externalDisplayName", "", "getExternalDisplayName", "()Ljava/lang/String;", "internalDisplayName", "getInternalDisplayName", "mustCheckInImports", "", "normalize", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ProtectedStaticVisibility extends Visibility {
        public static final ProtectedStaticVisibility INSTANCE = new ProtectedStaticVisibility();

        @Override // org.jetbrains.kotlin.descriptors.Visibility
        public boolean mustCheckInImports() {
            return false;
        }

        private ProtectedStaticVisibility() {
            super("protected_static", true);
        }

        @Override // org.jetbrains.kotlin.descriptors.Visibility
        public Visibility normalize() {
            return Visibilities.Protected.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.Visibility
        /* JADX INFO: renamed from: getInternalDisplayName */
        public String getName() {
            return "protected/*protected static*/";
        }

        @Override // org.jetbrains.kotlin.descriptors.Visibility
        public String getExternalDisplayName() {
            return "protected";
        }
    }

    /* JADX INFO: compiled from: JavaVisibilities.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0017\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\u0001H\u0016¢\u0006\u0002\u0010\fJ\b\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0001H\u0016R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/java/JavaVisibilities$ProtectedAndPackage;", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "()V", "externalDisplayName", "", "getExternalDisplayName", "()Ljava/lang/String;", "internalDisplayName", "getInternalDisplayName", "compareTo", "", "visibility", "(Lorg/jetbrains/kotlin/descriptors/Visibility;)Ljava/lang/Integer;", "mustCheckInImports", "", "normalize", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ProtectedAndPackage extends Visibility {
        public static final ProtectedAndPackage INSTANCE = new ProtectedAndPackage();

        @Override // org.jetbrains.kotlin.descriptors.Visibility
        public boolean mustCheckInImports() {
            return false;
        }

        private ProtectedAndPackage() {
            super("protected_and_package", true);
        }

        @Override // org.jetbrains.kotlin.descriptors.Visibility
        public Integer compareTo(Visibility visibility) {
            Intrinsics.checkNotNullParameter(visibility, "visibility");
            if (Intrinsics.areEqual(this, visibility)) {
                return 0;
            }
            if (visibility == Visibilities.Internal.INSTANCE) {
                return null;
            }
            return Integer.valueOf(Visibilities.INSTANCE.isPrivate(visibility) ? 1 : -1);
        }

        @Override // org.jetbrains.kotlin.descriptors.Visibility
        public Visibility normalize() {
            return Visibilities.Protected.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.Visibility
        /* JADX INFO: renamed from: getInternalDisplayName */
        public String getName() {
            return "protected/*protected and package*/";
        }

        @Override // org.jetbrains.kotlin.descriptors.Visibility
        public String getExternalDisplayName() {
            return "protected";
        }
    }
}
