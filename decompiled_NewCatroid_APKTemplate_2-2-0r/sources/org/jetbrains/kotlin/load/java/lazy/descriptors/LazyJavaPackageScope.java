package org.jetbrains.kotlin.load.java.lazy.descriptors;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JavaClassFinder;
import org.jetbrains.kotlin.load.java.lazy.LazyJavaResolverContext;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaPackage;
import org.jetbrains.kotlin.load.java.structure.LightClassOriginKind;
import org.jetbrains.kotlin.load.kotlin.KotlinClassFinder;
import org.jetbrains.kotlin.load.kotlin.KotlinClassFinderKt;
import org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass;
import org.jetbrains.kotlin.load.kotlin.header.KotlinClassHeader;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.storage.MemoizedFunctionToNullable;
import org.jetbrains.kotlin.storage.NullableLazyValue;
import org.jetbrains.kotlin.utils.DeserializationHelpersKt;
import org.jetbrains.kotlin.utils.FunctionsKt;

/* JADX INFO: compiled from: LazyJavaPackageScope.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u00029:B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ,\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u00132\u0006\u0010\u0019\u001a\u00020\u001a2\u0014\u0010\u001b\u001a\u0010\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u001d\u0018\u00010\u001cH\u0014J,\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00180\u00132\u0006\u0010\u0019\u001a\u00020\u001a2\u0014\u0010\u001b\u001a\u0010\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u001d\u0018\u00010\u001cH\u0014J\b\u0010\u001f\u001a\u00020 H\u0014J\u001e\u0010!\u001a\u00020\"2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020%0$2\u0006\u0010&\u001a\u00020\u0018H\u0014J,\u0010'\u001a\b\u0012\u0004\u0012\u00020\u00180\u00132\u0006\u0010\u0019\u001a\u00020\u001a2\u0014\u0010\u001b\u001a\u0010\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u001d\u0018\u00010\u001cH\u0014J\u001c\u0010(\u001a\u0004\u0018\u00010\f2\u0006\u0010&\u001a\u00020\u00182\b\u0010)\u001a\u0004\u0018\u00010*H\u0002J\u0017\u0010+\u001a\u0004\u0018\u00010\f2\u0006\u0010)\u001a\u00020*H\u0000¢\u0006\u0002\b,J\u001a\u0010-\u001a\u0004\u0018\u00010\f2\u0006\u0010&\u001a\u00020\u00182\u0006\u0010.\u001a\u00020/H\u0016J*\u00100\u001a\b\u0012\u0004\u0012\u000202012\u0006\u0010\u0019\u001a\u00020\u001a2\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u001d0\u001cH\u0016J\u001e\u00103\u001a\b\u0012\u0004\u0012\u000204012\u0006\u0010&\u001a\u00020\u00182\u0006\u0010.\u001a\u00020/H\u0016J\u0012\u00105\u001a\u0002062\b\u00107\u001a\u0004\u0018\u000108H\u0002R\u001a\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\u00020\u000e8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u00130\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u0007X\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016¨\u0006;"}, d2 = {"Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaPackageScope;", "Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaStaticScope;", "c", "Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;", "jPackage", "Lorg/jetbrains/kotlin/load/java/structure/JavaPackage;", "ownerDescriptor", "Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaPackageFragment;", "(Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;Lorg/jetbrains/kotlin/load/java/structure/JavaPackage;Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaPackageFragment;)V", "classes", "Lorg/jetbrains/kotlin/storage/MemoizedFunctionToNullable;", "Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaPackageScope$FindClassRequest;", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "jvmMetadataVersion", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "getJvmMetadataVersion", "()Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "knownClassNamesInPackage", "Lorg/jetbrains/kotlin/storage/NullableLazyValue;", "", "", "getOwnerDescriptor", "()Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaPackageFragment;", "computeClassNames", "Lorg/jetbrains/kotlin/name/Name;", "kindFilter", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", "nameFilter", "Lkotlin/Function1;", "", "computeFunctionNames", "computeMemberIndex", "Lorg/jetbrains/kotlin/load/java/lazy/descriptors/DeclaredMemberIndex;", "computeNonDeclaredFunctions", "", "result", "", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "name", "computePropertyNames", "findClassifier", "javaClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "findClassifierByJavaClass", "findClassifierByJavaClass$descriptors_jvm", "getContributedClassifier", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "getContributedDescriptors", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getContributedVariables", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "resolveKotlinBinaryClass", "Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaPackageScope$KotlinClassLookupResult;", "kotlinClass", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;", "FindClassRequest", "KotlinClassLookupResult", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LazyJavaPackageScope extends LazyJavaStaticScope {
    private final MemoizedFunctionToNullable<FindClassRequest, ClassDescriptor> classes;
    private final JavaPackage jPackage;
    private final NullableLazyValue<Set<String>> knownClassNamesInPackage;
    private final LazyJavaPackageFragment ownerDescriptor;

    @Override // org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaScope
    protected void computeNonDeclaredFunctions(Collection<SimpleFunctionDescriptor> result, Name name2) {
        Intrinsics.checkNotNullParameter(result, "result");
        Intrinsics.checkNotNullParameter(name2, "name");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaScope
    public LazyJavaPackageFragment getOwnerDescriptor() {
        return this.ownerDescriptor;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LazyJavaPackageScope(final LazyJavaResolverContext c, JavaPackage jPackage, LazyJavaPackageFragment ownerDescriptor) {
        super(c);
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(jPackage, "jPackage");
        Intrinsics.checkNotNullParameter(ownerDescriptor, "ownerDescriptor");
        this.jPackage = jPackage;
        this.ownerDescriptor = ownerDescriptor;
        this.knownClassNamesInPackage = c.getStorageManager().createNullableLazyValue(new Function0<Set<? extends String>>() { // from class: org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaPackageScope$knownClassNamesInPackage$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<? extends String> invoke() {
                return c.getComponents().getFinder().knownClassNamesInPackage(this.getOwnerDescriptor().getFqName());
            }
        });
        this.classes = c.getStorageManager().createMemoizedFunctionWithNullableValues(new Function1<FindClassRequest, ClassDescriptor>() { // from class: org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaPackageScope$classes$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final ClassDescriptor invoke(LazyJavaPackageScope.FindClassRequest request) {
                Intrinsics.checkNotNullParameter(request, "request");
                ClassId classId = new ClassId(this.this$0.getOwnerDescriptor().getFqName(), request.getName());
                KotlinClassFinder.Result resultFindKotlinClassOrContent = request.getJavaClass() != null ? c.getComponents().getKotlinClassFinder().findKotlinClassOrContent(request.getJavaClass(), this.this$0.getJvmMetadataVersion()) : c.getComponents().getKotlinClassFinder().findKotlinClassOrContent(classId, this.this$0.getJvmMetadataVersion());
                LazyJavaClassDescriptor lazyJavaClassDescriptor = null;
                KotlinJvmBinaryClass kotlinJvmBinaryClass = resultFindKotlinClassOrContent != null ? resultFindKotlinClassOrContent.toKotlinJvmBinaryClass() : null;
                ClassId classId2 = kotlinJvmBinaryClass != null ? kotlinJvmBinaryClass.getClassId() : null;
                if (classId2 != null && (classId2.isNestedClass() || classId2.isLocal())) {
                    return null;
                }
                LazyJavaPackageScope.KotlinClassLookupResult kotlinClassLookupResultResolveKotlinBinaryClass = this.this$0.resolveKotlinBinaryClass(kotlinJvmBinaryClass);
                if (kotlinClassLookupResultResolveKotlinBinaryClass instanceof LazyJavaPackageScope.KotlinClassLookupResult.Found) {
                    return ((LazyJavaPackageScope.KotlinClassLookupResult.Found) kotlinClassLookupResultResolveKotlinBinaryClass).getDescriptor();
                }
                if (kotlinClassLookupResultResolveKotlinBinaryClass instanceof LazyJavaPackageScope.KotlinClassLookupResult.SyntheticClass) {
                    return null;
                }
                if (!(kotlinClassLookupResultResolveKotlinBinaryClass instanceof LazyJavaPackageScope.KotlinClassLookupResult.NotFound)) {
                    throw new NoWhenBranchMatchedException();
                }
                JavaClass javaClass = request.getJavaClass();
                if (javaClass == null) {
                    JavaClassFinder finder = c.getComponents().getFinder();
                    KotlinClassFinder.Result.ClassFileContent classFileContent = resultFindKotlinClassOrContent instanceof KotlinClassFinder.Result.ClassFileContent ? (KotlinClassFinder.Result.ClassFileContent) resultFindKotlinClassOrContent : null;
                    javaClass = finder.findClass(new JavaClassFinder.Request(classId, classFileContent != null ? classFileContent.getContent() : null, null, 4, null));
                }
                JavaClass javaClass2 = javaClass;
                if ((javaClass2 != null ? javaClass2.getLightClassOriginKind() : null) == LightClassOriginKind.BINARY) {
                    throw new IllegalStateException("Couldn't find kotlin binary class for light class created by kotlin binary file\nJavaClass: " + javaClass2 + "\nClassId: " + classId + "\nfindKotlinClass(JavaClass) = " + KotlinClassFinderKt.findKotlinClass(c.getComponents().getKotlinClassFinder(), javaClass2, this.this$0.getJvmMetadataVersion()) + "\nfindKotlinClass(ClassId) = " + KotlinClassFinderKt.findKotlinClass(c.getComponents().getKotlinClassFinder(), classId, this.this$0.getJvmMetadataVersion()) + '\n');
                }
                FqName fqName = javaClass2 != null ? javaClass2.getFqName() : null;
                if (fqName != null && !fqName.isRoot() && Intrinsics.areEqual(fqName.parent(), this.this$0.getOwnerDescriptor().getFqName())) {
                    lazyJavaClassDescriptor = new LazyJavaClassDescriptor(c, this.this$0.getOwnerDescriptor(), javaClass2, null, 8, null);
                    c.getComponents().getJavaClassesTracker().reportClass(lazyJavaClassDescriptor);
                }
                return lazyJavaClassDescriptor;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final JvmMetadataVersion getJvmMetadataVersion() {
        return DeserializationHelpersKt.jvmMetadataVersionOrDefault(getC().getComponents().getDeserializedDescriptorResolver().getComponents().getConfiguration());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: LazyJavaPackageScope.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b2\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaPackageScope$KotlinClassLookupResult;", "", "()V", "Found", "NotFound", "SyntheticClass", "Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaPackageScope$KotlinClassLookupResult$Found;", "Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaPackageScope$KotlinClassLookupResult$NotFound;", "Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaPackageScope$KotlinClassLookupResult$SyntheticClass;", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static abstract class KotlinClassLookupResult {
        public /* synthetic */ KotlinClassLookupResult(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: compiled from: LazyJavaPackageScope.kt */
        @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaPackageScope$KotlinClassLookupResult$Found;", "Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaPackageScope$KotlinClassLookupResult;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;)V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Found extends KotlinClassLookupResult {
            private final ClassDescriptor descriptor;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public Found(ClassDescriptor descriptor) {
                super(null);
                Intrinsics.checkNotNullParameter(descriptor, "descriptor");
                this.descriptor = descriptor;
            }

            public final ClassDescriptor getDescriptor() {
                return this.descriptor;
            }
        }

        private KotlinClassLookupResult() {
        }

        /* JADX INFO: compiled from: LazyJavaPackageScope.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaPackageScope$KotlinClassLookupResult$NotFound;", "Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaPackageScope$KotlinClassLookupResult;", "()V", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class NotFound extends KotlinClassLookupResult {
            public static final NotFound INSTANCE = new NotFound();

            private NotFound() {
                super(null);
            }
        }

        /* JADX INFO: compiled from: LazyJavaPackageScope.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaPackageScope$KotlinClassLookupResult$SyntheticClass;", "Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaPackageScope$KotlinClassLookupResult;", "()V", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class SyntheticClass extends KotlinClassLookupResult {
            public static final SyntheticClass INSTANCE = new SyntheticClass();

            private SyntheticClass() {
                super(null);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final KotlinClassLookupResult resolveKotlinBinaryClass(KotlinJvmBinaryClass kotlinClass) {
        if (kotlinClass == null) {
            return KotlinClassLookupResult.NotFound.INSTANCE;
        }
        if (kotlinClass.getClassHeader().getKind() == KotlinClassHeader.Kind.CLASS) {
            ClassDescriptor classDescriptorResolveClass = getC().getComponents().getDeserializedDescriptorResolver().resolveClass(kotlinClass);
            return classDescriptorResolveClass != null ? new KotlinClassLookupResult.Found(classDescriptorResolveClass) : KotlinClassLookupResult.NotFound.INSTANCE;
        }
        return KotlinClassLookupResult.SyntheticClass.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: LazyJavaPackageScope.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaPackageScope$FindClassRequest;", "", "name", "Lorg/jetbrains/kotlin/name/Name;", "javaClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "(Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/load/java/structure/JavaClass;)V", "getJavaClass", "()Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class FindClassRequest {
        private final JavaClass javaClass;
        private final Name name;

        public FindClassRequest(Name name2, JavaClass javaClass) {
            Intrinsics.checkNotNullParameter(name2, "name");
            this.name = name2;
            this.javaClass = javaClass;
        }

        public final JavaClass getJavaClass() {
            return this.javaClass;
        }

        public final Name getName() {
            return this.name;
        }

        public boolean equals(Object other) {
            return (other instanceof FindClassRequest) && Intrinsics.areEqual(this.name, ((FindClassRequest) other).name);
        }

        public int hashCode() {
            return this.name.hashCode();
        }
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    /* JADX INFO: renamed from: getContributedClassifier */
    public ClassDescriptor mo7465getContributedClassifier(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return findClassifier(name2, null);
    }

    private final ClassDescriptor findClassifier(Name name2, JavaClass javaClass) {
        if (!SpecialNames.INSTANCE.isSafeIdentifier(name2)) {
            return null;
        }
        Set<String> setInvoke = this.knownClassNamesInPackage.invoke();
        if (javaClass != null || setInvoke == null || setInvoke.contains(name2.asString())) {
            return this.classes.invoke(new FindClassRequest(name2, javaClass));
        }
        return null;
    }

    public final ClassDescriptor findClassifierByJavaClass$descriptors_jvm(JavaClass javaClass) {
        Intrinsics.checkNotNullParameter(javaClass, "javaClass");
        return findClassifier(javaClass.getName(), javaClass);
    }

    @Override // org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaScope, org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<PropertyDescriptor> getContributedVariables(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaScope
    protected DeclaredMemberIndex computeMemberIndex() {
        return DeclaredMemberIndex.Empty.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaScope
    protected Set<Name> computeClassNames(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        if (!kindFilter.acceptsKinds(DescriptorKindFilter.INSTANCE.getNON_SINGLETON_CLASSIFIERS_MASK())) {
            return SetsKt.emptySet();
        }
        Set<String> setInvoke = this.knownClassNamesInPackage.invoke();
        if (setInvoke == null) {
            JavaPackage javaPackage = this.jPackage;
            if (nameFilter == null) {
                nameFilter = FunctionsKt.alwaysTrue();
            }
            Collection<JavaClass> classes = javaPackage.getClasses(nameFilter);
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            for (JavaClass javaClass : classes) {
                Name name2 = javaClass.getLightClassOriginKind() == LightClassOriginKind.SOURCE ? null : javaClass.getName();
                if (name2 != null) {
                    linkedHashSet.add(name2);
                }
            }
            return linkedHashSet;
        }
        HashSet hashSet = new HashSet();
        Iterator<T> it = setInvoke.iterator();
        while (it.hasNext()) {
            hashSet.add(Name.identifier((String) it.next()));
        }
        return hashSet;
    }

    @Override // org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaScope
    protected Set<Name> computeFunctionNames(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        return SetsKt.emptySet();
    }

    @Override // org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaScope
    protected Set<Name> computePropertyNames(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        return SetsKt.emptySet();
    }

    @Override // org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaScope, org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        if (!kindFilter.acceptsKinds(DescriptorKindFilter.INSTANCE.getCLASSIFIERS_MASK() | DescriptorKindFilter.INSTANCE.getNON_SINGLETON_CLASSIFIERS_MASK())) {
            return CollectionsKt.emptyList();
        }
        Collection<DeclarationDescriptor> collectionInvoke = getAllDescriptors().invoke();
        ArrayList arrayList = new ArrayList();
        for (Object obj : collectionInvoke) {
            DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) obj;
            if (declarationDescriptor instanceof ClassDescriptor) {
                Name name2 = ((ClassDescriptor) declarationDescriptor).getShortName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                if (nameFilter.invoke(name2).booleanValue()) {
                    arrayList.add(obj);
                }
            }
        }
        return arrayList;
    }
}
