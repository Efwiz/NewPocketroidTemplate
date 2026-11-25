package org.jetbrains.kotlin.descriptors;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.platform.TargetPlatform;

/* JADX INFO: compiled from: ModuleDescriptor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u00014J7\u0010\u001b\u001a\u0004\u0018\u0001H\u001c\"\u0004\b\u0000\u0010\u001c\"\u0004\b\u0001\u0010\u001d2\u0012\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u0002H\u001c\u0012\u0004\u0012\u0002H\u001d0\u001f2\u0006\u0010 \u001a\u0002H\u001dH\u0016¢\u0006\u0002\u0010!J\b\u0010\"\u001a\u00020#H&J#\u0010$\u001a\u0004\u0018\u0001H%\"\u0004\b\u0000\u0010%2\f\u0010&\u001a\b\u0012\u0004\u0012\u0002H%0'H&¢\u0006\u0002\u0010(J\n\u0010)\u001a\u0004\u0018\u00010\u0001H\u0016J\u0010\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-H&J*\u0010.\u001a\b\u0012\u0004\u0012\u00020-0/2\u0006\u0010,\u001a\u00020-2\u0012\u00100\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u001101H&J\u0010\u00102\u001a\u00020\u00112\u0006\u00103\u001a\u00020\u0000H&R\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00000\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0018\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00000\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0018\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00000\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0005R\u0012\u0010\u0010\u001a\u00020\u0011X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0012R\u0014\u0010\u0013\u001a\u0004\u0018\u00010\u0014X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0017\u001a\u0004\u0018\u00010\u0018X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001a¨\u00065"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "allDependencyModules", "", "getAllDependencyModules", "()Ljava/util/List;", "allExpectedByModules", "", "getAllExpectedByModules", "()Ljava/util/Set;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "getBuiltIns", "()Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "expectedByModules", "getExpectedByModules", "isValid", "", "()Z", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "getPlatform", "()Lorg/jetbrains/kotlin/platform/TargetPlatform;", "stableName", "Lorg/jetbrains/kotlin/name/Name;", "getStableName", "()Lorg/jetbrains/kotlin/name/Name;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;", "data", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "assertValid", "", "getCapability", ExifInterface.GPS_DIRECTION_TRUE, "capability", "Lorg/jetbrains/kotlin/descriptors/ModuleCapability;", "(Lorg/jetbrains/kotlin/descriptors/ModuleCapability;)Ljava/lang/Object;", "getContainingDeclaration", "getPackage", "Lorg/jetbrains/kotlin/descriptors/PackageViewDescriptor;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "getSubPackagesOf", "", "nameFilter", "Lkotlin/Function1;", "shouldSeeInternalsOf", "targetModule", "Capability", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ModuleDescriptor extends DeclarationDescriptor {
    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    <R, D> R accept(DeclarationDescriptorVisitor<R, D> visitor, D data);

    /* JADX INFO: renamed from: assertValid */
    void mo7478assertValid();

    List<ModuleDescriptor> getAllDependencyModules();

    Set<ModuleDescriptor> getAllExpectedByModules();

    KotlinBuiltIns getBuiltIns();

    <T> T getCapability(ModuleCapability<T> capability);

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    DeclarationDescriptor getContainingDeclaration();

    List<ModuleDescriptor> getExpectedByModules();

    PackageViewDescriptor getPackage(FqName fqName);

    TargetPlatform getPlatform();

    Name getStableName();

    Collection<FqName> getSubPackagesOf(FqName fqName, Function1<? super Name, Boolean> nameFilter);

    boolean isValid();

    boolean shouldSeeInternalsOf(ModuleDescriptor targetModule);

    /* JADX INFO: compiled from: ModuleDescriptor.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static DeclarationDescriptor getContainingDeclaration(ModuleDescriptor moduleDescriptor) {
            return null;
        }

        public static <R, D> R accept(ModuleDescriptor moduleDescriptor, DeclarationDescriptorVisitor<R, D> visitor, D d) {
            Intrinsics.checkNotNullParameter(visitor, "visitor");
            return visitor.visitModuleDeclaration(moduleDescriptor, d);
        }
    }

    /* JADX INFO: compiled from: ModuleDescriptor.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\b\u001a\u00020\u0004H\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor$Capability;", ExifInterface.GPS_DIRECTION_TRUE, "", "name", "", "(Ljava/lang/String;)V", "getName", "()Ljava/lang/String;", "toString", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Capability<T> {
        private final String name;

        public Capability(String name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            this.name = name2;
        }

        public final String getName() {
            return this.name;
        }

        public String toString() {
            return this.name;
        }
    }
}
