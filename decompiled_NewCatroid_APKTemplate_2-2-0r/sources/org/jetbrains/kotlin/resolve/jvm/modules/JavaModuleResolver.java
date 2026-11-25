package org.jetbrains.kotlin.resolve.jvm.modules;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JavaModuleAnnotationsProvider;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: JavaModuleResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u0000 \n2\u00020\u0001:\u0002\t\nJ&\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u00052\b\u0010\u0007\u001a\u0004\u0018\u00010\bH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModuleResolver;", "Lorg/jetbrains/kotlin/load/java/JavaModuleAnnotationsProvider;", "checkAccessibility", "Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModuleResolver$AccessError;", "fileFromOurModule", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "referencedFile", "referencedPackage", "Lorg/jetbrains/kotlin/name/FqName;", "AccessError", "SERVICE", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface JavaModuleResolver extends JavaModuleAnnotationsProvider {

    /* JADX INFO: renamed from: SERVICE, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    AccessError checkAccessibility(VirtualFile fileFromOurModule, VirtualFile referencedFile, FqName referencedPackage);

    /* JADX INFO: compiled from: JavaModuleResolver.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModuleResolver$AccessError;", "", "()V", "ModuleDoesNotExportPackage", "ModuleDoesNotReadModule", "ModuleDoesNotReadUnnamedModule", "Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModuleResolver$AccessError$ModuleDoesNotExportPackage;", "Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModuleResolver$AccessError$ModuleDoesNotReadModule;", "Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModuleResolver$AccessError$ModuleDoesNotReadUnnamedModule;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class AccessError {
        public /* synthetic */ AccessError(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: compiled from: JavaModuleResolver.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModuleResolver$AccessError$ModuleDoesNotReadUnnamedModule;", "Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModuleResolver$AccessError;", "()V", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class ModuleDoesNotReadUnnamedModule extends AccessError {
            public static final ModuleDoesNotReadUnnamedModule INSTANCE = new ModuleDoesNotReadUnnamedModule();

            private ModuleDoesNotReadUnnamedModule() {
                super(null);
            }
        }

        private AccessError() {
        }

        /* JADX INFO: compiled from: JavaModuleResolver.kt */
        @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModuleResolver$AccessError$ModuleDoesNotReadModule;", "Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModuleResolver$AccessError;", "dependencyModuleName", "", "(Ljava/lang/String;)V", "getDependencyModuleName", "()Ljava/lang/String;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final /* data */ class ModuleDoesNotReadModule extends AccessError {
            private final String dependencyModuleName;

            public static /* synthetic */ ModuleDoesNotReadModule copy$default(ModuleDoesNotReadModule moduleDoesNotReadModule, String str, int i, Object obj) {
                if ((i & 1) != 0) {
                    str = moduleDoesNotReadModule.dependencyModuleName;
                }
                return moduleDoesNotReadModule.copy(str);
            }

            /* JADX INFO: renamed from: component1, reason: from getter */
            public final String getDependencyModuleName() {
                return this.dependencyModuleName;
            }

            public final ModuleDoesNotReadModule copy(String dependencyModuleName) {
                Intrinsics.checkNotNullParameter(dependencyModuleName, "dependencyModuleName");
                return new ModuleDoesNotReadModule(dependencyModuleName);
            }

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                return (other instanceof ModuleDoesNotReadModule) && Intrinsics.areEqual(this.dependencyModuleName, ((ModuleDoesNotReadModule) other).dependencyModuleName);
            }

            public int hashCode() {
                return this.dependencyModuleName.hashCode();
            }

            public String toString() {
                return "ModuleDoesNotReadModule(dependencyModuleName=" + this.dependencyModuleName + ')';
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public ModuleDoesNotReadModule(String dependencyModuleName) {
                super(null);
                Intrinsics.checkNotNullParameter(dependencyModuleName, "dependencyModuleName");
                this.dependencyModuleName = dependencyModuleName;
            }

            public final String getDependencyModuleName() {
                return this.dependencyModuleName;
            }
        }

        /* JADX INFO: compiled from: JavaModuleResolver.kt */
        @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModuleResolver$AccessError$ModuleDoesNotExportPackage;", "Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModuleResolver$AccessError;", "dependencyModuleName", "", "(Ljava/lang/String;)V", "getDependencyModuleName", "()Ljava/lang/String;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final /* data */ class ModuleDoesNotExportPackage extends AccessError {
            private final String dependencyModuleName;

            public static /* synthetic */ ModuleDoesNotExportPackage copy$default(ModuleDoesNotExportPackage moduleDoesNotExportPackage, String str, int i, Object obj) {
                if ((i & 1) != 0) {
                    str = moduleDoesNotExportPackage.dependencyModuleName;
                }
                return moduleDoesNotExportPackage.copy(str);
            }

            /* JADX INFO: renamed from: component1, reason: from getter */
            public final String getDependencyModuleName() {
                return this.dependencyModuleName;
            }

            public final ModuleDoesNotExportPackage copy(String dependencyModuleName) {
                Intrinsics.checkNotNullParameter(dependencyModuleName, "dependencyModuleName");
                return new ModuleDoesNotExportPackage(dependencyModuleName);
            }

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                return (other instanceof ModuleDoesNotExportPackage) && Intrinsics.areEqual(this.dependencyModuleName, ((ModuleDoesNotExportPackage) other).dependencyModuleName);
            }

            public int hashCode() {
                return this.dependencyModuleName.hashCode();
            }

            public String toString() {
                return "ModuleDoesNotExportPackage(dependencyModuleName=" + this.dependencyModuleName + ')';
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public ModuleDoesNotExportPackage(String dependencyModuleName) {
                super(null);
                Intrinsics.checkNotNullParameter(dependencyModuleName, "dependencyModuleName");
                this.dependencyModuleName = dependencyModuleName;
            }

            public final String getDependencyModuleName() {
                return this.dependencyModuleName;
            }
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.resolve.jvm.modules.JavaModuleResolver$SERVICE, reason: from kotlin metadata */
    /* JADX INFO: compiled from: JavaModuleResolver.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModuleResolver$SERVICE;", "", "()V", "getInstance", "Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModuleResolver;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public final JavaModuleResolver getInstance(Project project) {
            Intrinsics.checkNotNullParameter(project, "project");
            Object service = project.getService(JavaModuleResolver.class);
            Intrinsics.checkNotNullExpressionValue(service, "getService(...)");
            return (JavaModuleResolver) service;
        }
    }
}
