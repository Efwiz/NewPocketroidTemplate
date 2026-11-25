package org.jetbrains.kotlin.load.java.lazy.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.PackageFragmentDescriptorImpl;
import org.jetbrains.kotlin.load.java.lazy.ContextKt;
import org.jetbrains.kotlin.load.java.lazy.LazyJavaAnnotationsKt;
import org.jetbrains.kotlin.load.java.lazy.LazyJavaResolverContext;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaPackage;
import org.jetbrains.kotlin.load.kotlin.KotlinClassFinderKt;
import org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass;
import org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryPackageSourceElement;
import org.jetbrains.kotlin.load.kotlin.PackagePartProvider;
import org.jetbrains.kotlin.load.kotlin.header.KotlinClassHeader;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.storage.StorageKt;
import org.jetbrains.kotlin.utils.DeserializationHelpersKt;

/* JADX INFO: compiled from: LazyJavaPackageFragment.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0017\u0010#\u001a\u0004\u0018\u00010$2\u0006\u0010%\u001a\u00020&H\u0000¢\u0006\u0002\b'J\u0010\u0010(\u001a\u0004\u0018\u00010\u00182\u0006\u0010)\u001a\u00020\u0018J\b\u0010*\u001a\u00020\u001eH\u0016J\b\u0010+\u001a\u00020,H\u0016J\u0013\u0010-\u001a\b\u0012\u0004\u0012\u00020\"0!H\u0000¢\u0006\u0002\b.J\b\u0010/\u001a\u00020\rH\u0016R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR'\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\f8@X\u0080\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0013\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R7\u0010\u0016\u001a\u001e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00180\u0017j\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u0018`\u00198BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001c\u0010\u0012\u001a\u0004\b\u001a\u0010\u001bR\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\"0!0 X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00060"}, d2 = {"Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaPackageFragment;", "Lorg/jetbrains/kotlin/descriptors/impl/PackageFragmentDescriptorImpl;", "outerContext", "Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;", "jPackage", "Lorg/jetbrains/kotlin/load/java/structure/JavaPackage;", "(Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;Lorg/jetbrains/kotlin/load/java/structure/JavaPackage;)V", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "getAnnotations", "()Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "binaryClasses", "", "", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;", "getBinaryClasses$descriptors_jvm", "()Ljava/util/Map;", "binaryClasses$delegate", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "c", "jvmMetadataVersion", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "partToFacade", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/resolve/jvm/JvmClassName;", "Lkotlin/collections/HashMap;", "getPartToFacade", "()Ljava/util/HashMap;", "partToFacade$delegate", "scope", "Lorg/jetbrains/kotlin/load/java/lazy/descriptors/JvmPackageScope;", "subPackages", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "", "Lorg/jetbrains/kotlin/name/FqName;", "findClassifierByJavaClass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "jClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "findClassifierByJavaClass$descriptors_jvm", "getFacadeNameForPartName", "partName", "getMemberScope", "getSource", "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "getSubPackageFqNames", "getSubPackageFqNames$descriptors_jvm", "toString", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LazyJavaPackageFragment extends PackageFragmentDescriptorImpl {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(LazyJavaPackageFragment.class), "binaryClasses", "getBinaryClasses$descriptors_jvm()Ljava/util/Map;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(LazyJavaPackageFragment.class), "partToFacade", "getPartToFacade()Ljava/util/HashMap;"))};
    private final Annotations annotations;

    /* JADX INFO: renamed from: binaryClasses$delegate, reason: from kotlin metadata */
    private final NotNullLazyValue binaryClasses;
    private final LazyJavaResolverContext c;
    private final JavaPackage jPackage;
    private final JvmMetadataVersion jvmMetadataVersion;

    /* JADX INFO: renamed from: partToFacade$delegate, reason: from kotlin metadata */
    private final NotNullLazyValue partToFacade;
    private final JvmPackageScope scope;
    private final NotNullLazyValue<List<FqName>> subPackages;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LazyJavaPackageFragment(LazyJavaResolverContext outerContext, JavaPackage jPackage) {
        super(outerContext.getModule(), jPackage.getFqName());
        Intrinsics.checkNotNullParameter(outerContext, "outerContext");
        Intrinsics.checkNotNullParameter(jPackage, "jPackage");
        this.jPackage = jPackage;
        LazyJavaResolverContext lazyJavaResolverContextChildForClassOrPackage$default = ContextKt.childForClassOrPackage$default(outerContext, this, null, 0, 6, null);
        this.c = lazyJavaResolverContextChildForClassOrPackage$default;
        this.jvmMetadataVersion = DeserializationHelpersKt.jvmMetadataVersionOrDefault(outerContext.getComponents().getDeserializedDescriptorResolver().getComponents().getConfiguration());
        this.binaryClasses = lazyJavaResolverContextChildForClassOrPackage$default.getStorageManager().createLazyValue(new Function0<Map<String, ? extends KotlinJvmBinaryClass>>() { // from class: org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaPackageFragment$binaryClasses$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<String, ? extends KotlinJvmBinaryClass> invoke() {
                PackagePartProvider packagePartProvider = this.this$0.c.getComponents().getPackagePartProvider();
                String strAsString = this.this$0.getFqName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                List<String> listFindPackageParts = packagePartProvider.findPackageParts(strAsString);
                LazyJavaPackageFragment lazyJavaPackageFragment = this.this$0;
                ArrayList arrayList = new ArrayList();
                for (String str : listFindPackageParts) {
                    ClassId classId = ClassId.topLevel(JvmClassName.byInternalName(str).getFqNameForTopLevelClassMaybeWithDollars());
                    Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
                    KotlinJvmBinaryClass kotlinJvmBinaryClassFindKotlinClass = KotlinClassFinderKt.findKotlinClass(lazyJavaPackageFragment.c.getComponents().getKotlinClassFinder(), classId, lazyJavaPackageFragment.jvmMetadataVersion);
                    Pair pair = kotlinJvmBinaryClassFindKotlinClass != null ? TuplesKt.to(str, kotlinJvmBinaryClassFindKotlinClass) : null;
                    if (pair != null) {
                        arrayList.add(pair);
                    }
                }
                return MapsKt.toMap(arrayList);
            }
        });
        this.scope = new JvmPackageScope(lazyJavaResolverContextChildForClassOrPackage$default, jPackage, this);
        this.subPackages = lazyJavaResolverContextChildForClassOrPackage$default.getStorageManager().createRecursionTolerantLazyValue(new Function0<List<? extends FqName>>() { // from class: org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaPackageFragment$subPackages$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends FqName> invoke() {
                Collection<JavaPackage> subPackages = this.this$0.jPackage.getSubPackages();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(subPackages, 10));
                Iterator<T> it = subPackages.iterator();
                while (it.hasNext()) {
                    arrayList.add(((JavaPackage) it.next()).getFqName());
                }
                return arrayList;
            }
        }, CollectionsKt.emptyList());
        this.annotations = lazyJavaResolverContextChildForClassOrPackage$default.getComponents().getJavaTypeEnhancementState().getDisabledDefaultAnnotations() ? Annotations.INSTANCE.getEMPTY() : LazyJavaAnnotationsKt.resolveAnnotations(lazyJavaResolverContextChildForClassOrPackage$default, jPackage);
        this.partToFacade = lazyJavaResolverContextChildForClassOrPackage$default.getStorageManager().createLazyValue(new Function0<HashMap<JvmClassName, JvmClassName>>() { // from class: org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaPackageFragment$partToFacade$2

            /* JADX INFO: compiled from: LazyJavaPackageFragment.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            public /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[KotlinClassHeader.Kind.values().length];
                    try {
                        iArr[KotlinClassHeader.Kind.MULTIFILE_CLASS_PART.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[KotlinClassHeader.Kind.FILE_FACADE.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final HashMap<JvmClassName, JvmClassName> invoke() {
                HashMap<JvmClassName, JvmClassName> map = new HashMap<>();
                for (Map.Entry<String, KotlinJvmBinaryClass> entry : this.this$0.getBinaryClasses$descriptors_jvm().entrySet()) {
                    String key = entry.getKey();
                    KotlinJvmBinaryClass value = entry.getValue();
                    JvmClassName jvmClassNameByInternalName = JvmClassName.byInternalName(key);
                    Intrinsics.checkNotNullExpressionValue(jvmClassNameByInternalName, "byInternalName(...)");
                    KotlinClassHeader classHeader = value.getClassHeader();
                    int i = WhenMappings.$EnumSwitchMapping$0[classHeader.getKind().ordinal()];
                    if (i == 1) {
                        HashMap<JvmClassName, JvmClassName> map2 = map;
                        String multifileClassName = classHeader.getMultifileClassName();
                        if (multifileClassName != null) {
                            JvmClassName jvmClassNameByInternalName2 = JvmClassName.byInternalName(multifileClassName);
                            Intrinsics.checkNotNullExpressionValue(jvmClassNameByInternalName2, "byInternalName(...)");
                            map2.put(jvmClassNameByInternalName, jvmClassNameByInternalName2);
                        }
                    } else if (i == 2) {
                        map.put(jvmClassNameByInternalName, jvmClassNameByInternalName);
                    }
                }
                return map;
            }
        });
    }

    public final Map<String, KotlinJvmBinaryClass> getBinaryClasses$descriptors_jvm() {
        return (Map) StorageKt.getValue(this.binaryClasses, this, (KProperty<?>) $$delegatedProperties[0]);
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotatedImpl, org.jetbrains.kotlin.descriptors.annotations.Annotated
    public Annotations getAnnotations() {
        return this.annotations;
    }

    public final List<FqName> getSubPackageFqNames$descriptors_jvm() {
        return this.subPackages.invoke();
    }

    public final ClassDescriptor findClassifierByJavaClass$descriptors_jvm(JavaClass jClass) {
        Intrinsics.checkNotNullParameter(jClass, "jClass");
        return this.scope.getJavaScope().findClassifierByJavaClass$descriptors_jvm(jClass);
    }

    private final HashMap<JvmClassName, JvmClassName> getPartToFacade() {
        return (HashMap) StorageKt.getValue(this.partToFacade, this, (KProperty<?>) $$delegatedProperties[1]);
    }

    public final JvmClassName getFacadeNameForPartName(JvmClassName partName) {
        Intrinsics.checkNotNullParameter(partName, "partName");
        return getPartToFacade().get(partName);
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor
    public JvmPackageScope getMemberScope() {
        return this.scope;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.PackageFragmentDescriptorImpl, org.jetbrains.kotlin.descriptors.impl.DeclarationDescriptorImpl
    /* JADX INFO: renamed from: toString */
    public String getDebugString() {
        return "Lazy Java package fragment: " + getFqName() + " of module " + this.c.getComponents().getModule();
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.PackageFragmentDescriptorImpl, org.jetbrains.kotlin.descriptors.impl.DeclarationDescriptorNonRootImpl, org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource
    /* JADX INFO: renamed from: getSource */
    public SourceElement getSourceElement() {
        return new KotlinJvmBinaryPackageSourceElement(this);
    }
}
