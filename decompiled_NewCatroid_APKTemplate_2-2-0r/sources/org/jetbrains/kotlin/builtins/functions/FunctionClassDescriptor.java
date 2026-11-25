package org.jetbrains.kotlin.builtins.functions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FindClassInModuleKt;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.SupertypeLoopChecker;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueClassRepresentation;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.AbstractClassDescriptor;
import org.jetbrains.kotlin.descriptors.impl.TypeParameterDescriptorImpl;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.AbstractClassTypeConstructor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeAttributes;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjectionImpl;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.utils.IDEAPlatforms;
import org.jetbrains.kotlin.utils.IDEAPluginsCompatibilityAPI;
import org.jetbrains.kotlin.utils.addToStdlib.AddToStdlibKt;

/* JADX INFO: compiled from: FunctionClassDescriptor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000¨\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u0000 I2\u00020\u0001:\u0002IJB%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\n\u0010#\u001a\u0004\u0018\u00010$H\u0016J\u000e\u0010%\u001a\b\u0012\u0004\u0012\u00020&0\u001fH\u0016J\b\u0010'\u001a\u00020\u0005H\u0016J\u000e\u0010(\u001a\b\u0012\u0004\u0012\u00020 0\u001fH\u0016J\b\u0010)\u001a\u00020*H\u0016J\b\u0010+\u001a\u00020,H\u0016J\u000e\u0010-\u001a\b\u0012\u0004\u0012\u00020.0\u001fH\u0016J\b\u0010/\u001a\u000200H\u0016J\b\u00101\u001a\u000202H\u0016J\b\u00103\u001a\u000204H\u0016J\u0010\u00105\u001a\u00020\u001d2\u0006\u00106\u001a\u000207H\u0014J\n\u00108\u001a\u0004\u0018\u00010$H\u0016J\u0010\u00109\u001a\n\u0012\u0004\u0012\u00020;\u0018\u00010:H\u0016J\b\u0010<\u001a\u00020=H\u0016J\b\u0010>\u001a\u00020\u001aH\u0016J\b\u0010?\u001a\u00020\u001aH\u0016J\b\u0010@\u001a\u00020\u001aH\u0016J\b\u0010A\u001a\u00020\u001aH\u0016J\b\u0010B\u001a\u00020\u001aH\u0016J\b\u0010C\u001a\u00020\u001aH\u0016J\b\u0010D\u001a\u00020\u001aH\u0016J\b\u0010E\u001a\u00020\u001aH\u0016J\b\u0010F\u001a\u00020\u001aH\u0016J\b\u0010G\u001a\u00020HH\u0016R\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0011\u001a\u00020\u00128\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0019\u001a\u00020\u001a8G¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001bR\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010!\u001a\u00060\"R\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006K"}, d2 = {"Lorg/jetbrains/kotlin/builtins/functions/FunctionClassDescriptor;", "Lorg/jetbrains/kotlin/descriptors/impl/AbstractClassDescriptor;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "containingDeclaration", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "functionTypeKind", "Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "arity", "", "(Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;I)V", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "getAnnotations", "()Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "getArity", "()I", "functionKind", "Lorg/jetbrains/kotlin/builtins/functions/FunctionClassKind;", "getFunctionKind$annotations", "()V", "getFunctionKind", "()Lorg/jetbrains/kotlin/builtins/functions/FunctionClassKind;", "getFunctionTypeKind", "()Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "hasBigArity", "", "()Z", "memberScope", "Lorg/jetbrains/kotlin/builtins/functions/FunctionClassScope;", "parameters", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "typeConstructor", "Lorg/jetbrains/kotlin/builtins/functions/FunctionClassDescriptor$FunctionTypeConstructor;", "getCompanionObjectDescriptor", "", "getConstructors", "Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "getContainingDeclaration", "getDeclaredTypeParameters", "getKind", "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "getModality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "getSealedSubclasses", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getSource", "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "getStaticScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope$Empty;", "getTypeConstructor", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "getUnsubstitutedMemberScope", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "getUnsubstitutedPrimaryConstructor", "getValueClassRepresentation", "Lorg/jetbrains/kotlin/descriptors/ValueClassRepresentation;", "Lorg/jetbrains/kotlin/types/SimpleType;", "getVisibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "isActual", "isCompanionObject", "isData", "isExpect", "isExternal", "isFun", "isInline", "isInner", "isValue", "toString", "", "Companion", "FunctionTypeConstructor", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FunctionClassDescriptor extends AbstractClassDescriptor {
    private static final ClassId functionClassId = new ClassId(StandardNames.BUILT_INS_PACKAGE_FQ_NAME, Name.identifier("Function"));
    private static final ClassId kFunctionClassId = new ClassId(StandardNames.KOTLIN_REFLECT_FQ_NAME, Name.identifier(StandardNames.K_FUNCTION_PREFIX));
    private final int arity;
    private final PackageFragmentDescriptor containingDeclaration;
    private final FunctionClassKind functionKind;
    private final FunctionTypeKind functionTypeKind;
    private final FunctionClassScope memberScope;
    private final List<TypeParameterDescriptor> parameters;
    private final StorageManager storageManager;
    private final FunctionTypeConstructor typeConstructor;

    @IDEAPluginsCompatibilityAPI(message = "Please migrate to the functionTypeKind", plugins = "android", usedIn = {IDEAPlatforms._223})
    public static /* synthetic */ void getFunctionKind$annotations() {
    }

    public Void getCompanionObjectDescriptor() {
        return null;
    }

    public Void getUnsubstitutedPrimaryConstructor() {
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

    @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
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

    @Override // org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters
    /* JADX INFO: renamed from: isInner */
    public boolean getIsInner() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public boolean isValue() {
        return false;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    /* JADX INFO: renamed from: getCompanionObjectDescriptor, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ ClassDescriptor mo6346getCompanionObjectDescriptor() {
        return (ClassDescriptor) getCompanionObjectDescriptor();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    /* JADX INFO: renamed from: getUnsubstitutedPrimaryConstructor, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ ClassConstructorDescriptor mo6347getUnsubstitutedPrimaryConstructor() {
        return (ClassConstructorDescriptor) getUnsubstitutedPrimaryConstructor();
    }

    public final FunctionTypeKind getFunctionTypeKind() {
        return this.functionTypeKind;
    }

    public final int getArity() {
        return this.arity;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FunctionClassDescriptor(StorageManager storageManager, PackageFragmentDescriptor containingDeclaration, FunctionTypeKind functionTypeKind, int i) {
        super(storageManager, functionTypeKind.numberedClassName(i));
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter(functionTypeKind, "functionTypeKind");
        this.storageManager = storageManager;
        this.containingDeclaration = containingDeclaration;
        this.functionTypeKind = functionTypeKind;
        this.arity = i;
        this.typeConstructor = new FunctionTypeConstructor();
        this.memberScope = new FunctionClassScope(storageManager, this);
        ArrayList arrayList = new ArrayList();
        IntRange intRange = new IntRange(1, i);
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(intRange, 10));
        Iterator<Integer> it = intRange.iterator();
        while (it.hasNext()) {
            _init_$typeParameter(arrayList, this, Variance.IN_VARIANCE, "P" + ((IntIterator) it).nextInt());
            arrayList2.add(Unit.INSTANCE);
        }
        _init_$typeParameter(arrayList, this, Variance.OUT_VARIANCE, "R");
        this.parameters = CollectionsKt.toList(arrayList);
        this.functionKind = FunctionClassKind.INSTANCE.getFunctionClassKind(this.functionTypeKind);
    }

    private static final void _init_$typeParameter(ArrayList<TypeParameterDescriptor> arrayList, FunctionClassDescriptor functionClassDescriptor, Variance variance, String str) {
        arrayList.add(TypeParameterDescriptorImpl.createWithDefaultBound(functionClassDescriptor, Annotations.INSTANCE.getEMPTY(), false, variance, Name.identifier(str), arrayList.size(), functionClassDescriptor.storageManager));
    }

    public final boolean hasBigArity() {
        return this.arity >= 23;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorNonRoot, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public PackageFragmentDescriptor getContainingDeclaration() {
        return this.containingDeclaration;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public MemberScope.Empty getStaticScope() {
        return MemberScope.Empty.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassifierDescriptor
    public TypeConstructor getTypeConstructor() {
        return this.typeConstructor;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.descriptors.impl.ModuleAwareClassDescriptor
    public FunctionClassScope getUnsubstitutedMemberScope(KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        return this.memberScope;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public List<ClassConstructorDescriptor> getConstructors() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public ClassKind getKind() {
        return ClassKind.INTERFACE;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.MemberDescriptor
    public Modality getModality() {
        return Modality.ABSTRACT;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility, org.jetbrains.kotlin.descriptors.MemberDescriptor
    public DescriptorVisibility getVisibility() {
        DescriptorVisibility PUBLIC = DescriptorVisibilities.PUBLIC;
        Intrinsics.checkNotNullExpressionValue(PUBLIC, "PUBLIC");
        return PUBLIC;
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.Annotated
    public Annotations getAnnotations() {
        return Annotations.INSTANCE.getEMPTY();
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource
    /* JADX INFO: renamed from: getSource */
    public SourceElement getSourceElement() {
        SourceElement NO_SOURCE = SourceElement.NO_SOURCE;
        Intrinsics.checkNotNullExpressionValue(NO_SOURCE, "NO_SOURCE");
        return NO_SOURCE;
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    public List<ClassDescriptor> getSealedSubclasses() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters
    public List<TypeParameterDescriptor> getDeclaredTypeParameters() {
        return this.parameters;
    }

    /* JADX INFO: compiled from: FunctionClassDescriptor.kt */
    @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0014J\b\u0010\n\u001a\u00020\u000bH\u0016J\u000e\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rH\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016R\u0014\u0010\u0003\u001a\u00020\u00048TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/builtins/functions/FunctionClassDescriptor$FunctionTypeConstructor;", "Lorg/jetbrains/kotlin/types/AbstractClassTypeConstructor;", "(Lorg/jetbrains/kotlin/builtins/functions/FunctionClassDescriptor;)V", "supertypeLoopChecker", "Lorg/jetbrains/kotlin/descriptors/SupertypeLoopChecker;", "getSupertypeLoopChecker", "()Lorg/jetbrains/kotlin/descriptors/SupertypeLoopChecker;", "computeSupertypes", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "getDeclarationDescriptor", "Lorg/jetbrains/kotlin/builtins/functions/FunctionClassDescriptor;", "getParameters", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "isDenotable", "", "toString", "", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class FunctionTypeConstructor extends AbstractClassTypeConstructor {
        @Override // org.jetbrains.kotlin.types.TypeConstructor
        public boolean isDenotable() {
            return true;
        }

        public FunctionTypeConstructor() {
            super(FunctionClassDescriptor.this.storageManager);
        }

        @Override // org.jetbrains.kotlin.types.AbstractTypeConstructor
        protected Collection<KotlinType> computeSupertypes() {
            List listListOf;
            FunctionTypeKind functionTypeKind = FunctionClassDescriptor.this.getFunctionTypeKind();
            if (Intrinsics.areEqual(functionTypeKind, FunctionTypeKind.Function.INSTANCE)) {
                listListOf = CollectionsKt.listOf(FunctionClassDescriptor.functionClassId);
            } else if (Intrinsics.areEqual(functionTypeKind, FunctionTypeKind.KFunction.INSTANCE)) {
                listListOf = CollectionsKt.listOf((Object[]) new ClassId[]{FunctionClassDescriptor.kFunctionClassId, new ClassId(StandardNames.BUILT_INS_PACKAGE_FQ_NAME, FunctionTypeKind.Function.INSTANCE.numberedClassName(FunctionClassDescriptor.this.getArity()))});
            } else if (Intrinsics.areEqual(functionTypeKind, FunctionTypeKind.SuspendFunction.INSTANCE)) {
                listListOf = CollectionsKt.listOf(FunctionClassDescriptor.functionClassId);
            } else if (Intrinsics.areEqual(functionTypeKind, FunctionTypeKind.KSuspendFunction.INSTANCE)) {
                listListOf = CollectionsKt.listOf((Object[]) new ClassId[]{FunctionClassDescriptor.kFunctionClassId, new ClassId(StandardNames.COROUTINES_PACKAGE_FQ_NAME, FunctionTypeKind.SuspendFunction.INSTANCE.numberedClassName(FunctionClassDescriptor.this.getArity()))});
            } else {
                AddToStdlibKt.shouldNotBeCalled$default(null, 1, null);
                throw null;
            }
            ModuleDescriptor containingDeclaration = FunctionClassDescriptor.this.containingDeclaration.getContainingDeclaration();
            List<ClassId> list = listListOf;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            for (ClassId classId : list) {
                ClassDescriptor classDescriptorFindClassAcrossModuleDependencies = FindClassInModuleKt.findClassAcrossModuleDependencies(containingDeclaration, classId);
                if (classDescriptorFindClassAcrossModuleDependencies == null) {
                    throw new IllegalStateException(("Built-in class " + classId + " not found").toString());
                }
                List listTakeLast = CollectionsKt.takeLast(getParameters(), classDescriptorFindClassAcrossModuleDependencies.getTypeConstructor().getParameters().size());
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listTakeLast, 10));
                Iterator it = listTakeLast.iterator();
                while (it.hasNext()) {
                    arrayList2.add(new TypeProjectionImpl(((TypeParameterDescriptor) it.next()).getDefaultType()));
                }
                arrayList.add(KotlinTypeFactory.simpleNotNullType(TypeAttributes.INSTANCE.getEmpty(), classDescriptorFindClassAcrossModuleDependencies, arrayList2));
            }
            return CollectionsKt.toList(arrayList);
        }

        @Override // org.jetbrains.kotlin.types.TypeConstructor
        public List<TypeParameterDescriptor> getParameters() {
            return FunctionClassDescriptor.this.parameters;
        }

        @Override // org.jetbrains.kotlin.types.AbstractClassTypeConstructor, org.jetbrains.kotlin.types.ClassifierBasedTypeConstructor, org.jetbrains.kotlin.types.TypeConstructor
        /* JADX INFO: renamed from: getDeclarationDescriptor, reason: from getter */
        public FunctionClassDescriptor getThis$0() {
            return FunctionClassDescriptor.this;
        }

        public String toString() {
            return getThis$0().toString();
        }

        @Override // org.jetbrains.kotlin.types.AbstractTypeConstructor
        protected SupertypeLoopChecker getSupertypeLoopChecker() {
            return SupertypeLoopChecker.EMPTY.INSTANCE;
        }
    }

    public String toString() {
        String strAsString = getShortName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return strAsString;
    }

    public final FunctionClassKind getFunctionKind() {
        return this.functionKind;
    }
}
