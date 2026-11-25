package org.jetbrains.kotlin.types.error;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.DefaultBuiltIns;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.InvalidModuleException;
import org.jetbrains.kotlin.descriptors.ModuleCapability;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageViewDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.platform.TargetPlatform;

/* JADX INFO: compiled from: ErrorModuleDescriptor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J7\u0010\"\u001a\u0004\u0018\u0001H#\"\u0004\b\u0000\u0010#\"\u0004\b\u0001\u0010$2\u0012\u0010%\u001a\u000e\u0012\u0004\u0012\u0002H#\u0012\u0004\u0012\u0002H$0&2\u0006\u0010'\u001a\u0002H$H\u0016¢\u0006\u0002\u0010(J\u001c\u0010)\u001a\u00020*2\u0012\u0010%\u001a\u000e\u0012\u0004\u0012\u00020+\u0012\u0004\u0012\u00020+0&H\u0016J\b\u0010,\u001a\u00020-H\u0016J#\u0010.\u001a\u0004\u0018\u0001H/\"\u0004\b\u0000\u0010/2\f\u00100\u001a\b\u0012\u0004\u0012\u0002H/01H\u0016¢\u0006\u0002\u00102J\n\u00103\u001a\u0004\u0018\u000104H\u0016J\b\u00105\u001a\u00020\u001fH\u0016J\b\u00106\u001a\u000204H\u0016J\u0010\u00107\u001a\u0002082\u0006\u00109\u001a\u00020:H\u0016J*\u0010;\u001a\b\u0012\u0004\u0012\u00020:0<2\u0006\u00109\u001a\u00020:2\u0012\u0010=\u001a\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020\u00180>H\u0016J\u0010\u0010?\u001a\u00020\u00182\u0006\u0010@\u001a\u00020\u0001H\u0016R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00010\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u001b\u0010\u000f\u001a\u00020\u00108VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00010\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0006R\u0014\u0010\u0017\u001a\u00020\u0018X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0019R\u0016\u0010\u001a\u001a\u0004\u0018\u00010\u001bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0014\u0010\u001e\u001a\u00020\u001fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010!¨\u0006A"}, d2 = {"Lorg/jetbrains/kotlin/types/error/ErrorModuleDescriptor;", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "()V", "allDependencyModules", "", "getAllDependencyModules", "()Ljava/util/List;", "allExpectedByModules", "", "getAllExpectedByModules", "()Ljava/util/Set;", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "getAnnotations", "()Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "getBuiltIns", "()Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "builtIns$delegate", "Lkotlin/Lazy;", "expectedByModules", "getExpectedByModules", "isValid", "", "()Z", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "getPlatform", "()Lorg/jetbrains/kotlin/platform/TargetPlatform;", "stableName", "Lorg/jetbrains/kotlin/name/Name;", "getStableName", "()Lorg/jetbrains/kotlin/name/Name;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;", "data", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptVoid", "", "Ljava/lang/Void;", "assertValid", "", "getCapability", ExifInterface.GPS_DIRECTION_TRUE, "capability", "Lorg/jetbrains/kotlin/descriptors/ModuleCapability;", "(Lorg/jetbrains/kotlin/descriptors/ModuleCapability;)Ljava/lang/Object;", "getContainingDeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getName", "getOriginal", "getPackage", "Lorg/jetbrains/kotlin/descriptors/PackageViewDescriptor;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "getSubPackagesOf", "", "nameFilter", "Lkotlin/Function1;", "shouldSeeInternalsOf", "targetModule", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ErrorModuleDescriptor implements ModuleDescriptor {
    public static final ErrorModuleDescriptor INSTANCE = new ErrorModuleDescriptor();
    private static final List<ModuleDescriptor> allDependencyModules;
    private static final Set<ModuleDescriptor> allExpectedByModules;

    /* JADX INFO: renamed from: builtIns$delegate, reason: from kotlin metadata */
    private static final Lazy builtIns;
    private static final List<ModuleDescriptor> expectedByModules;
    private static final boolean isValid = false;
    private static final TargetPlatform platform = null;
    private static final Name stableName;

    @Override // org.jetbrains.kotlin.descriptors.ModuleDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    /* JADX INFO: renamed from: acceptVoid */
    public void mo7454acceptVoid(DeclarationDescriptorVisitor<Void, Void> visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
    }

    @Override // org.jetbrains.kotlin.descriptors.ModuleDescriptor
    public <T> T getCapability(ModuleCapability<T> capability) {
        Intrinsics.checkNotNullParameter(capability, "capability");
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.ModuleDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public DeclarationDescriptor getContainingDeclaration() {
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.ModuleDescriptor
    public boolean shouldSeeInternalsOf(ModuleDescriptor targetModule) {
        Intrinsics.checkNotNullParameter(targetModule, "targetModule");
        return false;
    }

    private ErrorModuleDescriptor() {
    }

    static {
        Name nameSpecial = Name.special(ErrorEntity.ERROR_MODULE.getDebugText());
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        stableName = nameSpecial;
        allDependencyModules = CollectionsKt.emptyList();
        expectedByModules = CollectionsKt.emptyList();
        allExpectedByModules = SetsKt.emptySet();
        builtIns = LazyKt.lazy(new Function0<DefaultBuiltIns>() { // from class: org.jetbrains.kotlin.types.error.ErrorModuleDescriptor$builtIns$2
            @Override // kotlin.jvm.functions.Function0
            public final DefaultBuiltIns invoke() {
                return DefaultBuiltIns.INSTANCE.getInstance();
            }
        });
    }

    @Override // org.jetbrains.kotlin.descriptors.ModuleDescriptor
    public Name getStableName() {
        return stableName;
    }

    @Override // org.jetbrains.kotlin.descriptors.ModuleDescriptor
    public TargetPlatform getPlatform() {
        return platform;
    }

    @Override // org.jetbrains.kotlin.descriptors.ModuleDescriptor
    public List<ModuleDescriptor> getAllDependencyModules() {
        return allDependencyModules;
    }

    @Override // org.jetbrains.kotlin.descriptors.ModuleDescriptor
    public List<ModuleDescriptor> getExpectedByModules() {
        return expectedByModules;
    }

    @Override // org.jetbrains.kotlin.descriptors.ModuleDescriptor
    public Set<ModuleDescriptor> getAllExpectedByModules() {
        return allExpectedByModules;
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.Annotated
    public Annotations getAnnotations() {
        return Annotations.INSTANCE.getEMPTY();
    }

    @Override // org.jetbrains.kotlin.descriptors.ModuleDescriptor
    public KotlinBuiltIns getBuiltIns() {
        return (KotlinBuiltIns) builtIns.getValue();
    }

    @Override // org.jetbrains.kotlin.descriptors.ModuleDescriptor
    /* JADX INFO: renamed from: isValid */
    public boolean getIsValid() {
        return isValid;
    }

    @Override // org.jetbrains.kotlin.descriptors.ModuleDescriptor
    public Collection<FqName> getSubPackagesOf(FqName fqName, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.descriptors.Named
    /* JADX INFO: renamed from: getName */
    public Name getShortName() {
        return getStableName();
    }

    @Override // org.jetbrains.kotlin.descriptors.ModuleDescriptor
    public PackageViewDescriptor getPackage(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        throw new IllegalStateException("Should not be called!");
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public DeclarationDescriptor getOriginal() {
        return this;
    }

    @Override // org.jetbrains.kotlin.descriptors.ModuleDescriptor
    /* JADX INFO: renamed from: assertValid, reason: merged with bridge method [inline-methods] */
    public Void mo7478assertValid() {
        throw new InvalidModuleException("ERROR_MODULE is not a valid module");
    }
}
