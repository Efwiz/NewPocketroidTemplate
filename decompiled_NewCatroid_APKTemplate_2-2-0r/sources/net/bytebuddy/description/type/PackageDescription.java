package net.bytebuddy.description.type;

import net.bytebuddy.description.NamedElement;
import net.bytebuddy.description.annotation.AnnotationList;
import net.bytebuddy.description.annotation.AnnotationSource;
import net.bytebuddy.utility.nullability.AlwaysNull;
import net.bytebuddy.utility.nullability.MaybeNull;

/* JADX INFO: loaded from: classes12.dex */
public interface PackageDescription extends NamedElement.WithRuntimeName, AnnotationSource {
    public static final String PACKAGE_CLASS_NAME = "package-info";
    public static final int PACKAGE_MODIFIERS = 5632;
    public static final PackageDescription DEFAULT = new Simple("");

    @AlwaysNull
    public static final PackageDescription UNDEFINED = null;

    boolean contains(TypeDescription typeDescription);

    boolean isDefault();

    public static abstract class AbstractBase implements PackageDescription {
        @Override // net.bytebuddy.description.NamedElement.WithRuntimeName
        public String getInternalName() {
            return getName().replace('.', '/');
        }

        @Override // net.bytebuddy.description.NamedElement
        public String getActualName() {
            return getName();
        }

        @Override // net.bytebuddy.description.type.PackageDescription
        public boolean contains(TypeDescription typeDescription) {
            return equals(typeDescription.getPackage());
        }

        @Override // net.bytebuddy.description.type.PackageDescription
        public boolean isDefault() {
            return getName().equals("");
        }

        public int hashCode() {
            return getName().hashCode();
        }

        public boolean equals(@MaybeNull Object obj) {
            return this == obj || ((obj instanceof PackageDescription) && getName().equals(((PackageDescription) obj).getName()));
        }

        public String toString() {
            return "package " + getName();
        }
    }

    public static class Simple extends AbstractBase {

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        private final String f354name;

        public Simple(String str) {
            this.f354name = str;
        }

        @Override // net.bytebuddy.description.annotation.AnnotationSource
        public AnnotationList getDeclaredAnnotations() {
            return new AnnotationList.Empty();
        }

        @Override // net.bytebuddy.description.NamedElement.WithRuntimeName
        public String getName() {
            return this.f354name;
        }
    }

    public static class ForLoadedPackage extends AbstractBase {
        private final Package aPackage;

        public ForLoadedPackage(Package r1) {
            this.aPackage = r1;
        }

        @Override // net.bytebuddy.description.annotation.AnnotationSource
        public AnnotationList getDeclaredAnnotations() {
            return new AnnotationList.ForLoadedAnnotations(this.aPackage.getDeclaredAnnotations());
        }

        @Override // net.bytebuddy.description.NamedElement.WithRuntimeName
        public String getName() {
            return this.aPackage.getName();
        }
    }
}
