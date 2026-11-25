package org.jetbrains.kotlin.fir.descriptors;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.ModuleCapability;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageViewDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.fir.FirModuleDataKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.platform.TargetPlatform;

/* JADX INFO: compiled from: FirModuleDescriptor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001e\u0010&\u001a\u00020'2\u0014\u0010(\u001a\u0010\u0012\u0004\u0012\u00020*\u0012\u0004\u0012\u00020*\u0018\u00010)H\u0016J\b\u0010+\u001a\u00020'H\u0016J#\u0010,\u001a\u0004\u0018\u0001H-\"\u0004\b\u0000\u0010-2\f\u0010.\u001a\b\u0012\u0004\u0012\u0002H-0/H\u0016¢\u0006\u0002\u00100J\b\u00101\u001a\u00020#H\u0016J\b\u00102\u001a\u000203H\u0016J\u0010\u00104\u001a\u0002052\u0006\u00106\u001a\u000207H\u0016J*\u00108\u001a\b\u0012\u0004\u0012\u000207092\u0006\u00106\u001a\u0002072\u0012\u0010:\u001a\u000e\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020\u001a0;H\u0016J\u0010\u0010<\u001a\u00020\u001a2\u0006\u0010=\u001a\u00020\u0001H\u0016R \u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00010\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\nR\u0014\u0010\u0019\u001a\u00020\u001a8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001bR\u0014\u0010\u001c\u001a\u00020\u001d8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0014\u0010\"\u001a\u00020#8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b$\u0010%¨\u0006>"}, d2 = {"Lorg/jetbrains/kotlin/fir/descriptors/FirModuleDescriptor;", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;)V", "allDependencyModules", "", "getAllDependencyModules", "()Ljava/util/List;", "setAllDependencyModules", "(Ljava/util/List;)V", "allExpectedByModules", "", "getAllExpectedByModules", "()Ljava/util/Set;", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "getAnnotations", "()Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "getBuiltIns", "()Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "expectedByModules", "getExpectedByModules", "isValid", "", "()Z", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "getPlatform", "()Lorg/jetbrains/kotlin/platform/TargetPlatform;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "stableName", "Lorg/jetbrains/kotlin/name/Name;", "getStableName", "()Lorg/jetbrains/kotlin/name/Name;", "acceptVoid", "", "visitor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;", "Ljava/lang/Void;", "assertValid", "getCapability", ExifInterface.GPS_DIRECTION_TRUE, "capability", "Lorg/jetbrains/kotlin/descriptors/ModuleCapability;", "(Lorg/jetbrains/kotlin/descriptors/ModuleCapability;)Ljava/lang/Object;", "getName", "getOriginal", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getPackage", "Lorg/jetbrains/kotlin/descriptors/PackageViewDescriptor;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "getSubPackagesOf", "", "nameFilter", "Lkotlin/Function1;", "shouldSeeInternalsOf", "targetModule", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirModuleDescriptor implements ModuleDescriptor {
    private List<? extends ModuleDescriptor> allDependencyModules;
    private final KotlinBuiltIns builtIns;
    private final FirSession session;

    @Override // org.jetbrains.kotlin.descriptors.ModuleDescriptor
    /* JADX INFO: renamed from: assertValid */
    public void mo7478assertValid() {
    }

    @Override // org.jetbrains.kotlin.descriptors.ModuleDescriptor
    public <T> T getCapability(ModuleCapability<T> capability) {
        Intrinsics.checkNotNullParameter(capability, "capability");
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.ModuleDescriptor
    /* JADX INFO: renamed from: isValid */
    public boolean getIsValid() {
        return true;
    }

    @Override // org.jetbrains.kotlin.descriptors.ModuleDescriptor
    public boolean shouldSeeInternalsOf(ModuleDescriptor targetModule) {
        Intrinsics.checkNotNullParameter(targetModule, "targetModule");
        return false;
    }

    public FirModuleDescriptor(FirSession session, KotlinBuiltIns builtIns) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        this.session = session;
        this.builtIns = builtIns;
        this.allDependencyModules = CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.descriptors.ModuleDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> declarationDescriptorVisitor, D d) {
        return (R) ModuleDescriptor.DefaultImpls.accept(this, declarationDescriptorVisitor, d);
    }

    @Override // org.jetbrains.kotlin.descriptors.ModuleDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public DeclarationDescriptor getContainingDeclaration() {
        return ModuleDescriptor.DefaultImpls.getContainingDeclaration(this);
    }

    public final FirSession getSession() {
        return this.session;
    }

    @Override // org.jetbrains.kotlin.descriptors.ModuleDescriptor
    public KotlinBuiltIns getBuiltIns() {
        return this.builtIns;
    }

    @Override // org.jetbrains.kotlin.descriptors.ModuleDescriptor
    public TargetPlatform getPlatform() {
        return FirModuleDataKt.getModuleData(this.session).getPlatform();
    }

    @Override // org.jetbrains.kotlin.descriptors.ModuleDescriptor
    public PackageViewDescriptor getPackage(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        if (FirSymbolProviderKt.getSymbolProvider(this.session).getPackage(fqName) != null) {
            return new FirPackageViewDescriptor(fqName, this);
        }
        throw new NotImplementedError("An operation is not implemented: Missing package reporting");
    }

    @Override // org.jetbrains.kotlin.descriptors.ModuleDescriptor
    public Collection<FqName> getSubPackagesOf(FqName fqName, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        throw new NotImplementedError("An operation is not implemented: not implemented");
    }

    @Override // org.jetbrains.kotlin.descriptors.ModuleDescriptor
    public List<ModuleDescriptor> getAllDependencyModules() {
        return this.allDependencyModules;
    }

    public void setAllDependencyModules(List<? extends ModuleDescriptor> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.allDependencyModules = list;
    }

    @Override // org.jetbrains.kotlin.descriptors.ModuleDescriptor
    public List<ModuleDescriptor> getExpectedByModules() {
        throw new NotImplementedError("An operation is not implemented: not implemented");
    }

    @Override // org.jetbrains.kotlin.descriptors.ModuleDescriptor
    public Set<ModuleDescriptor> getAllExpectedByModules() {
        throw new NotImplementedError("An operation is not implemented: not implemented");
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public DeclarationDescriptor getOriginal() {
        return this;
    }

    @Override // org.jetbrains.kotlin.descriptors.Named
    /* JADX INFO: renamed from: getName */
    public Name getShortName() {
        return FirModuleDataKt.getModuleData(this.session).getName();
    }

    @Override // org.jetbrains.kotlin.descriptors.ModuleDescriptor
    public Name getStableName() {
        return getShortName();
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    /* JADX INFO: renamed from: acceptVoid */
    public void mo7454acceptVoid(DeclarationDescriptorVisitor<Void, Void> visitor) {
        throw new NotImplementedError("An operation is not implemented: not implemented");
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.Annotated
    public Annotations getAnnotations() {
        return Annotations.INSTANCE.getEMPTY();
    }
}
