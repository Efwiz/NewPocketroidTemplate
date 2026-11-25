package org.jetbrains.kotlin.descriptors;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.ClassDescriptorBase;
import org.jetbrains.kotlin.descriptors.impl.EmptyPackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.impl.TypeParameterDescriptorImpl;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.storage.MemoizedFunctionToNotNull;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.ClassTypeConstructorImpl;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;

/* JADX INFO: compiled from: NotFoundClasses.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0002\u0014\u0015B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001c\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/NotFoundClasses;", "", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "(Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;)V", "classes", "Lorg/jetbrains/kotlin/storage/MemoizedFunctionToNotNull;", "Lorg/jetbrains/kotlin/descriptors/NotFoundClasses$ClassRequest;", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "packageFragments", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "getClass", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "typeParametersCount", "", "", "ClassRequest", "MockClassDescriptor", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NotFoundClasses {
    private final MemoizedFunctionToNotNull<ClassRequest, ClassDescriptor> classes;
    private final ModuleDescriptor module;
    private final MemoizedFunctionToNotNull<FqName, PackageFragmentDescriptor> packageFragments;
    private final StorageManager storageManager;

    public NotFoundClasses(StorageManager storageManager, ModuleDescriptor module) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(module, "module");
        this.storageManager = storageManager;
        this.module = module;
        this.packageFragments = storageManager.createMemoizedFunction(new Function1<FqName, PackageFragmentDescriptor>() { // from class: org.jetbrains.kotlin.descriptors.NotFoundClasses$packageFragments$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final PackageFragmentDescriptor invoke(FqName fqName) {
                Intrinsics.checkNotNullParameter(fqName, "fqName");
                return new EmptyPackageFragmentDescriptor(this.this$0.module, fqName);
            }
        });
        this.classes = storageManager.createMemoizedFunction(new Function1<ClassRequest, ClassDescriptor>() { // from class: org.jetbrains.kotlin.descriptors.NotFoundClasses$classes$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final ClassDescriptor invoke(NotFoundClasses.ClassRequest classRequest) {
                ClassDescriptor classDescriptor;
                ClassDescriptor classDescriptor2;
                Intrinsics.checkNotNullParameter(classRequest, "<name for destructuring parameter 0>");
                ClassId classId = classRequest.getClassId();
                List<Integer> listComponent2 = classRequest.component2();
                if (classId.isLocal()) {
                    throw new UnsupportedOperationException("Unresolved local class: " + classId);
                }
                ClassId outerClassId = classId.getOuterClassId();
                if (outerClassId == null || (classDescriptor2 = this.this$0.getClass(outerClassId, CollectionsKt.drop(listComponent2, 1))) == null) {
                    MemoizedFunctionToNotNull memoizedFunctionToNotNull = this.this$0.packageFragments;
                    FqName packageFqName = classId.getPackageFqName();
                    Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
                    classDescriptor = (ClassOrPackageFragmentDescriptor) memoizedFunctionToNotNull.invoke(packageFqName);
                } else {
                    classDescriptor = classDescriptor2;
                }
                boolean zIsNestedClass = classId.isNestedClass();
                StorageManager storageManager2 = this.this$0.storageManager;
                ClassOrPackageFragmentDescriptor classOrPackageFragmentDescriptor = classDescriptor;
                Name shortClassName = classId.getShortClassName();
                Intrinsics.checkNotNullExpressionValue(shortClassName, "getShortClassName(...)");
                Integer num = (Integer) CollectionsKt.firstOrNull((List) listComponent2);
                return new NotFoundClasses.MockClassDescriptor(storageManager2, classOrPackageFragmentDescriptor, shortClassName, zIsNestedClass, num != null ? num.intValue() : 0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: NotFoundClasses.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J#\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0006HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/NotFoundClasses$ClassRequest;", "", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "typeParametersCount", "", "", "(Lorg/jetbrains/kotlin/name/ClassId;Ljava/util/List;)V", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "getTypeParametersCount", "()Ljava/util/List;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "toString", "", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final /* data */ class ClassRequest {
        private final ClassId classId;
        private final List<Integer> typeParametersCount;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ ClassRequest copy$default(ClassRequest classRequest, ClassId classId, List list, int i, Object obj) {
            if ((i & 1) != 0) {
                classId = classRequest.classId;
            }
            if ((i & 2) != 0) {
                list = classRequest.typeParametersCount;
            }
            return classRequest.copy(classId, list);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final ClassId getClassId() {
            return this.classId;
        }

        public final List<Integer> component2() {
            return this.typeParametersCount;
        }

        public final ClassRequest copy(ClassId classId, List<Integer> typeParametersCount) {
            Intrinsics.checkNotNullParameter(classId, "classId");
            Intrinsics.checkNotNullParameter(typeParametersCount, "typeParametersCount");
            return new ClassRequest(classId, typeParametersCount);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ClassRequest)) {
                return false;
            }
            ClassRequest classRequest = (ClassRequest) other;
            return Intrinsics.areEqual(this.classId, classRequest.classId) && Intrinsics.areEqual(this.typeParametersCount, classRequest.typeParametersCount);
        }

        public int hashCode() {
            return (this.classId.hashCode() * 31) + this.typeParametersCount.hashCode();
        }

        public String toString() {
            return "ClassRequest(classId=" + this.classId + ", typeParametersCount=" + this.typeParametersCount + ')';
        }

        public ClassRequest(ClassId classId, List<Integer> typeParametersCount) {
            Intrinsics.checkNotNullParameter(classId, "classId");
            Intrinsics.checkNotNullParameter(typeParametersCount, "typeParametersCount");
            this.classId = classId;
            this.typeParametersCount = typeParametersCount;
        }

        public final ClassId getClassId() {
            return this.classId;
        }

        public final List<Integer> getTypeParametersCount() {
            return this.typeParametersCount;
        }
    }

    /* JADX INFO: compiled from: NotFoundClasses.kt */
    @Metadata(d1 = {"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B/\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\n\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J\u000e\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0019H\u0016J\u000e\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012H\u0016J\b\u0010\u001c\u001a\u00020\u001dH\u0016J\b\u0010\u001e\u001a\u00020\u001fH\u0016J\u000e\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00170\u0019H\u0016J\b\u0010!\u001a\u00020\"H\u0016J\b\u0010#\u001a\u00020\u0015H\u0016J\u0010\u0010$\u001a\u00020\"2\u0006\u0010%\u001a\u00020&H\u0014J\n\u0010'\u001a\u0004\u0018\u00010\u001aH\u0016J\u0010\u0010(\u001a\n\u0012\u0004\u0012\u00020*\u0018\u00010)H\u0016J\b\u0010+\u001a\u00020,H\u0016J\b\u0010-\u001a\u00020\tH\u0016J\b\u0010.\u001a\u00020\tH\u0016J\b\u0010/\u001a\u00020\tH\u0016J\b\u00100\u001a\u00020\tH\u0016J\b\u00101\u001a\u00020\tH\u0016J\b\u00102\u001a\u00020\tH\u0016J\b\u00103\u001a\u00020\tH\u0016J\b\u0010\b\u001a\u00020\tH\u0016J\b\u00104\u001a\u00020\tH\u0016J\b\u00105\u001a\u000206H\u0016R\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00067"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/NotFoundClasses$MockClassDescriptor;", "Lorg/jetbrains/kotlin/descriptors/impl/ClassDescriptorBase;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "container", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "name", "Lorg/jetbrains/kotlin/name/Name;", "isInner", "", "numberOfDeclaredTypeParameters", "", "(Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;Lorg/jetbrains/kotlin/name/Name;ZI)V", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "getAnnotations", "()Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "declaredTypeParameters", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "typeConstructor", "Lorg/jetbrains/kotlin/types/ClassTypeConstructorImpl;", "getCompanionObjectDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getConstructors", "", "Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "getDeclaredTypeParameters", "getKind", "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "getModality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "getSealedSubclasses", "getStaticScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope$Empty;", "getTypeConstructor", "getUnsubstitutedMemberScope", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "getUnsubstitutedPrimaryConstructor", "getValueClassRepresentation", "Lorg/jetbrains/kotlin/descriptors/ValueClassRepresentation;", "Lorg/jetbrains/kotlin/types/SimpleType;", "getVisibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "isActual", "isCompanionObject", "isData", "isExpect", "isExternal", "isFun", "isInline", "isValue", "toString", "", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class MockClassDescriptor extends ClassDescriptorBase {
        private final List<TypeParameterDescriptor> declaredTypeParameters;
        private final boolean isInner;
        private final ClassTypeConstructorImpl typeConstructor;

        @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
        /* JADX INFO: renamed from: getCompanionObjectDescriptor */
        public ClassDescriptor mo6346getCompanionObjectDescriptor() {
            return null;
        }

        @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
        /* JADX INFO: renamed from: getUnsubstitutedPrimaryConstructor */
        public ClassConstructorDescriptor mo6347getUnsubstitutedPrimaryConstructor() {
            return null;
        }

        @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
        public ValueClassRepresentation<SimpleType> getValueClassRepresentation() {
            return null;
        }

        @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
        public boolean isActual() {
            return false;
        }

        @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
        public boolean isCompanionObject() {
            return false;
        }

        @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
        public boolean isData() {
            return false;
        }

        @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
        /* JADX INFO: renamed from: isExpect */
        public boolean get$isExpect() {
            return false;
        }

        @Override // org.jetbrains.kotlin.descriptors.impl.ClassDescriptorBase, org.jetbrains.kotlin.descriptors.MemberDescriptor
        public boolean isExternal() {
            return false;
        }

        @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
        public boolean isFun() {
            return false;
        }

        @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
        public boolean isInline() {
            return false;
        }

        @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
        public boolean isValue() {
            return false;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public MockClassDescriptor(StorageManager storageManager, DeclarationDescriptor container, Name name2, boolean z, int i) {
            super(storageManager, container, name2, SourceElement.NO_SOURCE, false);
            Intrinsics.checkNotNullParameter(storageManager, "storageManager");
            Intrinsics.checkNotNullParameter(container, "container");
            Intrinsics.checkNotNullParameter(name2, "name");
            this.isInner = z;
            IntRange intRangeUntil = RangesKt.until(0, i);
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(intRangeUntil, 10));
            Iterator<Integer> it = intRangeUntil.iterator();
            while (it.hasNext()) {
                int iNextInt = ((IntIterator) it).nextInt();
                arrayList.add(TypeParameterDescriptorImpl.createWithDefaultBound(this, Annotations.INSTANCE.getEMPTY(), false, Variance.INVARIANT, Name.identifier(ExifInterface.GPS_DIRECTION_TRUE + iNextInt), iNextInt, storageManager));
            }
            this.declaredTypeParameters = arrayList;
            this.typeConstructor = new ClassTypeConstructorImpl(this, TypeParameterUtilsKt.computeConstructorTypeParameters(this), SetsKt.setOf(DescriptorUtilsKt.getModule(this).getBuiltIns().getAnyType()), storageManager);
        }

        @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
        public ClassKind getKind() {
            return ClassKind.CLASS;
        }

        @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.MemberDescriptor
        public Modality getModality() {
            return Modality.FINAL;
        }

        @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility, org.jetbrains.kotlin.descriptors.MemberDescriptor
        public DescriptorVisibility getVisibility() {
            DescriptorVisibility PUBLIC = DescriptorVisibilities.PUBLIC;
            Intrinsics.checkNotNullExpressionValue(PUBLIC, "PUBLIC");
            return PUBLIC;
        }

        @Override // org.jetbrains.kotlin.descriptors.ClassifierDescriptor
        public ClassTypeConstructorImpl getTypeConstructor() {
            return this.typeConstructor;
        }

        @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters
        public List<TypeParameterDescriptor> getDeclaredTypeParameters() {
            return this.declaredTypeParameters;
        }

        @Override // org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters
        /* JADX INFO: renamed from: isInner, reason: from getter */
        public boolean getIsInner() {
            return this.isInner;
        }

        @Override // org.jetbrains.kotlin.descriptors.annotations.Annotated
        public Annotations getAnnotations() {
            return Annotations.INSTANCE.getEMPTY();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.descriptors.impl.ModuleAwareClassDescriptor
        public MemberScope.Empty getUnsubstitutedMemberScope(KotlinTypeRefiner kotlinTypeRefiner) {
            Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
            return MemberScope.Empty.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
        public MemberScope.Empty getStaticScope() {
            return MemberScope.Empty.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
        public Collection<ClassConstructorDescriptor> getConstructors() {
            return SetsKt.emptySet();
        }

        @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
        public Collection<ClassDescriptor> getSealedSubclasses() {
            return CollectionsKt.emptyList();
        }

        public String toString() {
            return ExternalAnnotationProvider.CLASS_PREFIX + getShortName() + " (not found)";
        }
    }

    public final ClassDescriptor getClass(ClassId classId, List<Integer> typeParametersCount) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(typeParametersCount, "typeParametersCount");
        return this.classes.invoke(new ClassRequest(classId, typeParametersCount));
    }
}
