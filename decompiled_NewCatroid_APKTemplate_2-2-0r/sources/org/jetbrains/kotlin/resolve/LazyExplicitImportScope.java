package org.jetbrains.kotlin.resolve;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PackageViewDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueClassRepresentation;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.TypeAliasConstructorDescriptor;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.BaseImportingScope;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeSubstitution;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.utils.CallOnceFunction;
import org.jetbrains.kotlin.utils.Printer;

/* JADX INFO: compiled from: LazyExplicitImportScope.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001BI\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t\u0012\u0018\u0010\u000b\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r\u0012\u0004\u0012\u00020\u000e0\f¢\u0006\u0002\u0010\u000fJK\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00110\r\"\b\b\u0000\u0010\u0011*\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142)\u0010\u0015\u001a%\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\r0\u0016¢\u0006\u0002\b\u0018H\u0002J\u000e\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\t0\u001aH\u0016J\u001a\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\u001d\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J2\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00050\r2\u0006\u0010\u001f\u001a\u00020 2\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020#0\"2\u0006\u0010$\u001a\u00020#H\u0016J\u001e\u0010%\u001a\b\u0012\u0004\u0012\u00020&0\r2\u0006\u0010\u001d\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u001e\u0010'\u001a\b\u0012\u0004\u0012\u00020(0\r2\u0006\u0010\u001d\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010)\u001a\u00020\u000e2\u0006\u0010*\u001a\u00020+H\u0016J\u0013\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00050\rH\u0000¢\u0006\u0002\b-J\u001d\u0010.\u001a\u0004\u0018\u0001H\u0011\"\b\b\u0000\u0010\u0011*\u00020\u0012*\u0002H\u0011H\u0002¢\u0006\u0002\u0010/J\"\u00100\u001a\b\u0012\u0004\u0012\u0002H\u00110\r\"\b\b\u0000\u0010\u0011*\u00020\u0012*\b\u0012\u0004\u0012\u0002H\u00110\rH\u0002R\u000e\u0010\n\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u000b\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r\u0012\u0004\u0012\u00020\u000e0\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u00061"}, d2 = {"Lorg/jetbrains/kotlin/resolve/LazyExplicitImportScope;", "Lorg/jetbrains/kotlin/resolve/scopes/BaseImportingScope;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "packageOrClassDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "packageFragmentForVisibilityCheck", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "declaredName", "Lorg/jetbrains/kotlin/name/Name;", "aliasName", "storeReferences", "Lorg/jetbrains/kotlin/utils/CallOnceFunction;", "", "", "(Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/utils/CallOnceFunction;)V", "collectCallableMemberDescriptors", "D", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "getDescriptors", "Lkotlin/Function3;", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "Lkotlin/ExtensionFunctionType;", "computeImportedNames", "", "getContributedClassifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "name", "getContributedDescriptors", "kindFilter", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", "nameFilter", "Lkotlin/Function1;", "", "changeNamesForAliased", "getContributedFunctions", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getContributedVariables", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "printStructure", "p", "Lorg/jetbrains/kotlin/utils/Printer;", "storeReferencesToDescriptors", "storeReferencesToDescriptors$frontend", "asImportedFromObjectIfPossible", "(Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;)Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "choseOnlyVisibleOrAll", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LazyExplicitImportScope extends BaseImportingScope {
    private final Name aliasName;
    private final Name declaredName;
    private final LanguageVersionSettings languageVersionSettings;
    private final PackageFragmentDescriptor packageFragmentForVisibilityCheck;
    private final DeclarationDescriptor packageOrClassDescriptor;
    private final CallOnceFunction<Collection<DeclarationDescriptor>, Unit> storeReferences;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LazyExplicitImportScope(LanguageVersionSettings languageVersionSettings, DeclarationDescriptor packageOrClassDescriptor, PackageFragmentDescriptor packageFragmentDescriptor, Name declaredName, Name aliasName, CallOnceFunction<Collection<DeclarationDescriptor>, Unit> storeReferences) {
        super(null);
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(packageOrClassDescriptor, "packageOrClassDescriptor");
        Intrinsics.checkNotNullParameter(declaredName, "declaredName");
        Intrinsics.checkNotNullParameter(aliasName, "aliasName");
        Intrinsics.checkNotNullParameter(storeReferences, "storeReferences");
        this.languageVersionSettings = languageVersionSettings;
        this.packageOrClassDescriptor = packageOrClassDescriptor;
        this.packageFragmentForVisibilityCheck = packageFragmentDescriptor;
        this.declaredName = declaredName;
        this.aliasName = aliasName;
        this.storeReferences = storeReferences;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.BaseHierarchicalScope, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    /* JADX INFO: renamed from: getContributedClassifier */
    public ClassifierDescriptor mo7465getContributedClassifier(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        if (!Intrinsics.areEqual(name2, this.aliasName)) {
            return null;
        }
        DeclarationDescriptor declarationDescriptor = this.packageOrClassDescriptor;
        if (declarationDescriptor instanceof PackageViewDescriptor) {
            return ((PackageViewDescriptor) declarationDescriptor).getMemberScope().mo7465getContributedClassifier(this.declaredName, location);
        }
        if (declarationDescriptor instanceof ClassDescriptor) {
            return ((ClassDescriptor) declarationDescriptor).getUnsubstitutedInnerClassesScope().mo7465getContributedClassifier(this.declaredName, location);
        }
        throw new IllegalStateException("Should be class or package: " + this.packageOrClassDescriptor);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.BaseHierarchicalScope, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<FunctionDescriptor> getContributedFunctions(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return !Intrinsics.areEqual(name2, this.aliasName) ? CollectionsKt.emptyList() : collectCallableMemberDescriptors(location, AnonymousClass1.INSTANCE);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.resolve.LazyExplicitImportScope$getContributedFunctions$1, reason: invalid class name */
    /* JADX INFO: compiled from: LazyExplicitImportScope.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function3<MemberScope, Name, LookupLocation, Collection<? extends SimpleFunctionDescriptor>> {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        AnonymousClass1() {
            super(3);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "getContributedFunctions(Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/incremental/components/LookupLocation;)Ljava/util/Collection;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "getContributedFunctions";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(MemberScope.class);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Collection<? extends SimpleFunctionDescriptor> invoke(MemberScope p0, Name p1, LookupLocation p2) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            Intrinsics.checkNotNullParameter(p1, "p1");
            Intrinsics.checkNotNullParameter(p2, "p2");
            return p0.getContributedFunctions(p1, p2);
        }
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.BaseHierarchicalScope, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<VariableDescriptor> getContributedVariables(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return !Intrinsics.areEqual(name2, this.aliasName) ? CollectionsKt.emptyList() : collectCallableMemberDescriptors(location, C49551.INSTANCE);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.resolve.LazyExplicitImportScope$getContributedVariables$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: LazyExplicitImportScope.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class C49551 extends FunctionReference implements Function3<MemberScope, Name, LookupLocation, Collection<? extends PropertyDescriptor>> {
        public static final C49551 INSTANCE = new C49551();

        C49551() {
            super(3);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "getContributedVariables(Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/incremental/components/LookupLocation;)Ljava/util/Collection;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "getContributedVariables";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(MemberScope.class);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Collection<? extends PropertyDescriptor> invoke(MemberScope p0, Name p1, LookupLocation p2) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            Intrinsics.checkNotNullParameter(p1, "p1");
            Intrinsics.checkNotNullParameter(p2, "p2");
            return p0.getContributedVariables(p1, p2);
        }
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.BaseImportingScope, org.jetbrains.kotlin.resolve.scopes.ImportingScope
    public Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter, boolean changeNamesForAliased) {
        DeclarationDescriptor declarationDescriptor;
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        SmartList smartList = new SmartList();
        if (kindFilter.acceptsKinds(DescriptorKindFilter.INSTANCE.getCLASSIFIERS_MASK())) {
            org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(smartList, mo7465getContributedClassifier(this.aliasName, NoLookupLocation.WHEN_GET_ALL_DESCRIPTORS));
        }
        if (kindFilter.acceptsKinds(DescriptorKindFilter.INSTANCE.getFUNCTIONS_MASK())) {
            smartList.addAll(getContributedFunctions(this.aliasName, NoLookupLocation.WHEN_GET_ALL_DESCRIPTORS));
        }
        if (kindFilter.acceptsKinds(DescriptorKindFilter.INSTANCE.getVARIABLES_MASK())) {
            smartList.addAll(getContributedVariables(this.aliasName, NoLookupLocation.WHEN_GET_ALL_DESCRIPTORS));
        }
        if (changeNamesForAliased && !Intrinsics.areEqual(this.aliasName, this.declaredName)) {
            int size = smartList.size();
            for (int i = 0; i < size; i++) {
                final DeclarationDescriptor declarationDescriptor2 = (DeclarationDescriptor) smartList.get(i);
                if (declarationDescriptor2 instanceof ClassDescriptor) {
                    declarationDescriptor = new ClassDescriptor(declarationDescriptor2, this) { // from class: org.jetbrains.kotlin.resolve.LazyExplicitImportScope$getContributedDescriptors$newDescriptor$1
                        private final /* synthetic */ ClassDescriptor $$delegate_0;
                        final /* synthetic */ LazyExplicitImportScope this$0;

                        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
                        public <R, D> R accept(DeclarationDescriptorVisitor<R, D> p0, D p1) {
                            return (R) this.$$delegate_0.accept(p0, p1);
                        }

                        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
                        /* JADX INFO: renamed from: acceptVoid */
                        public void mo7454acceptVoid(DeclarationDescriptorVisitor<Void, Void> p0) {
                            this.$$delegate_0.mo7454acceptVoid(p0);
                        }

                        @Override // org.jetbrains.kotlin.descriptors.annotations.Annotated
                        public Annotations getAnnotations() {
                            return this.$$delegate_0.getAnnotations();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
                        /* JADX INFO: renamed from: getCompanionObjectDescriptor */
                        public ClassDescriptor mo6346getCompanionObjectDescriptor() {
                            return this.$$delegate_0.mo6346getCompanionObjectDescriptor();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
                        public Collection<ClassConstructorDescriptor> getConstructors() {
                            return this.$$delegate_0.getConstructors();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorNonRoot, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
                        public DeclarationDescriptor getContainingDeclaration() {
                            return this.$$delegate_0.getContainingDeclaration();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
                        public List<ReceiverParameterDescriptor> getContextReceivers() {
                            return this.$$delegate_0.getContextReceivers();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters
                        public List<TypeParameterDescriptor> getDeclaredTypeParameters() {
                            return this.$$delegate_0.getDeclaredTypeParameters();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
                        public SimpleType getDefaultFunctionTypeForSamInterface() {
                            return this.$$delegate_0.getDefaultFunctionTypeForSamInterface();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.ClassifierDescriptor
                        public SimpleType getDefaultType() {
                            return this.$$delegate_0.getDefaultType();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
                        public ClassKind getKind() {
                            return this.$$delegate_0.getKind();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
                        public MemberScope getMemberScope(List<? extends TypeProjection> p0) {
                            Intrinsics.checkNotNullParameter(p0, "p0");
                            return this.$$delegate_0.getMemberScope(p0);
                        }

                        @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
                        public MemberScope getMemberScope(TypeSubstitution p0) {
                            Intrinsics.checkNotNullParameter(p0, "p0");
                            return this.$$delegate_0.getMemberScope(p0);
                        }

                        @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.MemberDescriptor
                        public Modality getModality() {
                            return this.$$delegate_0.getModality();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
                        public ClassDescriptor getOriginal() {
                            return this.$$delegate_0.getOriginal();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
                        public Collection<ClassDescriptor> getSealedSubclasses() {
                            return this.$$delegate_0.getSealedSubclasses();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource
                        /* JADX INFO: renamed from: getSource */
                        public SourceElement getSourceElement() {
                            return this.$$delegate_0.getSourceElement();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
                        public MemberScope getStaticScope() {
                            return this.$$delegate_0.getStaticScope();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
                        public ReceiverParameterDescriptor getThisAsReceiverParameter() {
                            return this.$$delegate_0.getThisAsReceiverParameter();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.ClassifierDescriptor
                        public TypeConstructor getTypeConstructor() {
                            return this.$$delegate_0.getTypeConstructor();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
                        public MemberScope getUnsubstitutedInnerClassesScope() {
                            return this.$$delegate_0.getUnsubstitutedInnerClassesScope();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
                        public MemberScope getUnsubstitutedMemberScope() {
                            return this.$$delegate_0.getUnsubstitutedMemberScope();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
                        /* JADX INFO: renamed from: getUnsubstitutedPrimaryConstructor */
                        public ClassConstructorDescriptor mo6347getUnsubstitutedPrimaryConstructor() {
                            return this.$$delegate_0.mo6347getUnsubstitutedPrimaryConstructor();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
                        public ValueClassRepresentation<SimpleType> getValueClassRepresentation() {
                            return this.$$delegate_0.getValueClassRepresentation();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility, org.jetbrains.kotlin.descriptors.MemberDescriptor
                        /* JADX INFO: renamed from: getVisibility */
                        public DescriptorVisibility getVisibilityImpl() {
                            return this.$$delegate_0.getVisibilityImpl();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
                        public boolean isActual() {
                            return this.$$delegate_0.isActual();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
                        /* JADX INFO: renamed from: isCompanionObject */
                        public boolean getIsCompanionObject() {
                            return this.$$delegate_0.getIsCompanionObject();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
                        public boolean isData() {
                            return this.$$delegate_0.isData();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
                        public boolean isDefinitelyNotSamInterface() {
                            return this.$$delegate_0.isDefinitelyNotSamInterface();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
                        /* JADX INFO: renamed from: isExpect */
                        public boolean get$isExpect() {
                            return this.$$delegate_0.get$isExpect();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
                        public boolean isExternal() {
                            return this.$$delegate_0.isExternal();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
                        public boolean isFun() {
                            return this.$$delegate_0.isFun();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
                        public boolean isInline() {
                            return this.$$delegate_0.isInline();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters
                        /* JADX INFO: renamed from: isInner */
                        public boolean getIsInner() {
                            return this.$$delegate_0.getIsInner();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
                        public boolean isValue() {
                            return this.$$delegate_0.isValue();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.Substitutable
                        public ClassifierDescriptorWithTypeParameters substitute(TypeSubstitutor substitutor) {
                            Intrinsics.checkNotNullParameter(substitutor, "substitutor");
                            return this.$$delegate_0.substitute(substitutor);
                        }

                        {
                            this.this$0 = this;
                            Intrinsics.checkNotNull(declarationDescriptor2);
                            this.$$delegate_0 = (ClassDescriptor) declarationDescriptor2;
                        }

                        @Override // org.jetbrains.kotlin.descriptors.Named
                        /* JADX INFO: renamed from: getName */
                        public Name getShortName() {
                            return this.this$0.aliasName;
                        }
                    };
                } else if (declarationDescriptor2 instanceof TypeAliasDescriptor) {
                    declarationDescriptor = new TypeAliasDescriptor(declarationDescriptor2, this) { // from class: org.jetbrains.kotlin.resolve.LazyExplicitImportScope$getContributedDescriptors$newDescriptor$2
                        private final /* synthetic */ TypeAliasDescriptor $$delegate_0;
                        final /* synthetic */ LazyExplicitImportScope this$0;

                        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
                        public <R, D> R accept(DeclarationDescriptorVisitor<R, D> p0, D p1) {
                            return (R) this.$$delegate_0.accept(p0, p1);
                        }

                        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
                        /* JADX INFO: renamed from: acceptVoid */
                        public void mo7454acceptVoid(DeclarationDescriptorVisitor<Void, Void> p0) {
                            this.$$delegate_0.mo7454acceptVoid(p0);
                        }

                        @Override // org.jetbrains.kotlin.descriptors.annotations.Annotated
                        public Annotations getAnnotations() {
                            return this.$$delegate_0.getAnnotations();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.TypeAliasDescriptor
                        public ClassDescriptor getClassDescriptor() {
                            return this.$$delegate_0.getClassDescriptor();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.TypeAliasDescriptor
                        public Collection<TypeAliasConstructorDescriptor> getConstructors() {
                            return this.$$delegate_0.getConstructors();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorNonRoot, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
                        public DeclarationDescriptor getContainingDeclaration() {
                            return this.$$delegate_0.getContainingDeclaration();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters
                        public List<TypeParameterDescriptor> getDeclaredTypeParameters() {
                            return this.$$delegate_0.getDeclaredTypeParameters();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.ClassifierDescriptor
                        public SimpleType getDefaultType() {
                            return this.$$delegate_0.getDefaultType();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.TypeAliasDescriptor
                        public SimpleType getExpandedType() {
                            return this.$$delegate_0.getExpandedType();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
                        public Modality getModality() {
                            return this.$$delegate_0.getModality();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.ClassifierDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
                        public TypeAliasDescriptor getOriginal() {
                            return this.$$delegate_0.getOriginal();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource
                        /* JADX INFO: renamed from: getSource */
                        public SourceElement getSourceElement() {
                            return this.$$delegate_0.getSourceElement();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.ClassifierDescriptor
                        public TypeConstructor getTypeConstructor() {
                            return this.$$delegate_0.getTypeConstructor();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.TypeAliasDescriptor
                        public SimpleType getUnderlyingType() {
                            return this.$$delegate_0.getUnderlyingType();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility, org.jetbrains.kotlin.descriptors.MemberDescriptor
                        /* JADX INFO: renamed from: getVisibility */
                        public DescriptorVisibility getVisibilityImpl() {
                            return this.$$delegate_0.getVisibilityImpl();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
                        public boolean isActual() {
                            return this.$$delegate_0.isActual();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
                        /* JADX INFO: renamed from: isExpect */
                        public boolean get$isExpect() {
                            return this.$$delegate_0.get$isExpect();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.MemberDescriptor
                        public boolean isExternal() {
                            return this.$$delegate_0.isExternal();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters
                        /* JADX INFO: renamed from: isInner */
                        public boolean getIsInner() {
                            return this.$$delegate_0.getIsInner();
                        }

                        @Override // org.jetbrains.kotlin.descriptors.Substitutable
                        public ClassifierDescriptorWithTypeParameters substitute(TypeSubstitutor substitutor) {
                            Intrinsics.checkNotNullParameter(substitutor, "substitutor");
                            return this.$$delegate_0.substitute(substitutor);
                        }

                        {
                            this.this$0 = this;
                            Intrinsics.checkNotNull(declarationDescriptor2);
                            this.$$delegate_0 = (TypeAliasDescriptor) declarationDescriptor2;
                        }

                        @Override // org.jetbrains.kotlin.descriptors.Named
                        /* JADX INFO: renamed from: getName */
                        public Name getShortName() {
                            return this.this$0.aliasName;
                        }
                    };
                } else if (declarationDescriptor2 instanceof CallableMemberDescriptor) {
                    CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) declarationDescriptor2;
                    CallableMemberDescriptor callableMemberDescriptorBuild = callableMemberDescriptor.newCopyBuilder().setName(this.aliasName).setOriginal(callableMemberDescriptor).build();
                    Intrinsics.checkNotNull(callableMemberDescriptorBuild);
                    declarationDescriptor = callableMemberDescriptorBuild;
                } else {
                    throw new IllegalStateException(("Unknown kind of descriptor in import alias: " + declarationDescriptor2).toString());
                }
                smartList.set(i, declarationDescriptor);
            }
        }
        return smartList;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ImportingScope
    public Set<Name> computeImportedNames() {
        return SetsKt.setOf(this.aliasName);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.HierarchicalScope
    public void printStructure(Printer p) {
        Intrinsics.checkNotNullParameter(p, "p");
        p.println(getClass().getSimpleName(), ": ", this.aliasName);
    }

    public final Collection<DeclarationDescriptor> storeReferencesToDescriptors$frontend() {
        Collection<DeclarationDescriptor> contributedDescriptors$default = ResolutionScope.DefaultImpls.getContributedDescriptors$default(this, null, null, 3, null);
        this.storeReferences.invoke(contributedDescriptors$default);
        return contributedDescriptors$default;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final <D extends CallableMemberDescriptor> Collection<D> collectCallableMemberDescriptors(LookupLocation location, Function3<? super MemberScope, ? super Name, ? super LookupLocation, ? extends Collection<? extends D>> getDescriptors) {
        SmartList smartList = new SmartList();
        DeclarationDescriptor declarationDescriptor = this.packageOrClassDescriptor;
        if (declarationDescriptor instanceof PackageViewDescriptor) {
            smartList.addAll(getDescriptors.invoke(((PackageViewDescriptor) declarationDescriptor).getMemberScope(), this.declaredName, location));
        } else if (declarationDescriptor instanceof ClassDescriptor) {
            MemberScope staticScope = ((ClassDescriptor) declarationDescriptor).getStaticScope();
            Intrinsics.checkNotNullExpressionValue(staticScope, "getStaticScope(...)");
            smartList.addAll(getDescriptors.invoke(staticScope, this.declaredName, location));
            if (((ClassDescriptor) this.packageOrClassDescriptor).getKind() == ClassKind.OBJECT) {
                MemberScope unsubstitutedMemberScope = ((ClassDescriptor) this.packageOrClassDescriptor).getUnsubstitutedMemberScope();
                Intrinsics.checkNotNullExpressionValue(unsubstitutedMemberScope, "getUnsubstitutedMemberScope(...)");
                Collection<? extends D> collectionInvoke = getDescriptors.invoke(unsubstitutedMemberScope, this.declaredName, location);
                ArrayList arrayList = new ArrayList();
                Iterator<T> it = collectionInvoke.iterator();
                while (it.hasNext()) {
                    CallableMemberDescriptor callableMemberDescriptorAsImportedFromObjectIfPossible = asImportedFromObjectIfPossible((CallableMemberDescriptor) it.next());
                    if (callableMemberDescriptorAsImportedFromObjectIfPossible != null) {
                        arrayList.add(callableMemberDescriptorAsImportedFromObjectIfPossible);
                    }
                }
                smartList.addAll(arrayList);
            }
        } else {
            throw new IllegalStateException("Should be class or package: " + this.packageOrClassDescriptor);
        }
        return choseOnlyVisibleOrAll(smartList);
    }

    private final <D extends CallableMemberDescriptor> D asImportedFromObjectIfPossible(D d) {
        if (d instanceof PropertyDescriptor) {
            PropertyImportedFromObject propertyImportedFromObjectAsImportedFromObject$default = ImportedFromObjectKt.asImportedFromObject$default((PropertyDescriptor) d, (PropertyImportedFromObject) null, 1, (Object) null);
            Intrinsics.checkNotNull(propertyImportedFromObjectAsImportedFromObject$default, "null cannot be cast to non-null type D of org.jetbrains.kotlin.resolve.LazyExplicitImportScope.asImportedFromObjectIfPossible");
            return propertyImportedFromObjectAsImportedFromObject$default;
        }
        if (!(d instanceof FunctionDescriptor)) {
            return null;
        }
        FunctionImportedFromObject functionImportedFromObjectAsImportedFromObject$default = ImportedFromObjectKt.asImportedFromObject$default((FunctionDescriptor) d, (FunctionImportedFromObject) null, 1, (Object) null);
        Intrinsics.checkNotNull(functionImportedFromObjectAsImportedFromObject$default, "null cannot be cast to non-null type D of org.jetbrains.kotlin.resolve.LazyExplicitImportScope.asImportedFromObjectIfPossible");
        return functionImportedFromObjectAsImportedFromObject$default;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final <D extends CallableMemberDescriptor> Collection<D> choseOnlyVisibleOrAll(Collection<? extends D> collection) {
        ArrayList arrayList = new ArrayList();
        for (Object obj : collection) {
            if (QualifiedExpressionResolverKt.isVisible((CallableMemberDescriptor) obj, this.packageFragmentForVisibilityCheck, QualifierPosition.IMPORT, this.languageVersionSettings)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        if (arrayList2.isEmpty()) {
            arrayList2 = null;
        }
        return arrayList2 != null ? arrayList2 : collection;
    }
}
