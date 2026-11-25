package org.jetbrains.kotlin.resolve;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.util.containers.MultiMap;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptorWithResolutionScopes;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorNonRoot;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SourceFile;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.idea.MainFunctionDetector;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;

/* JADX INFO: compiled from: OverloadResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\f\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012J\u0016\u0010\u0013\u001a\u00020\u00102\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015H\u0002J\u001e\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0017\u001a\u00020\u00182\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0015H\u0002J\u0016\u0010\u001b\u001a\u00020\u00102\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0015H\u0002J\u0010\u0010\u001d\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002JS\u0010\u001e\u001a\u00020\u00102\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\u001c0 2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020#0\u00152\u0006\u0010\u0004\u001a\u00020\u00052\u001e\u0010$\u001a\u001a\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u00020'\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c0\u00150%H\u0082\bJ\u001c\u0010(\u001a\u000e\u0012\u0004\u0012\u00020)\u0012\u0004\u0012\u00020\u001a0 2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u001c\u0010*\u001a\b\u0012\u0004\u0012\u00020\u001c0\u00152\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0015H\u0002J?\u0010+\u001a\b\u0012\u0004\u0012\u00020\u001c0\u00152\u0006\u0010,\u001a\u00020#2\u0006\u0010\u0004\u001a\u00020\u00052\u001e\u0010$\u001a\u001a\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u00020'\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c0\u00150%H\u0082\bJ\"\u0010-\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c0\u00150\u00152\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0015H\u0002J$\u0010.\u001a\u000e\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\u001c0 2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0004\u001a\u00020\u0005H\u0002J\u0018\u0010/\u001a\u0002002\u0006\u00101\u001a\u00020#2\u0006\u00102\u001a\u00020#H\u0002J\u0018\u00103\u001a\u0002002\u0006\u00101\u001a\u00020\u001c2\u0006\u00102\u001a\u00020\u001cH\u0002J\u0018\u00104\u001a\u0002002\u0006\u00105\u001a\u00020#2\u0006\u00106\u001a\u00020#H\u0002J\u0018\u00107\u001a\u0002002\u0006\u00101\u001a\u00020#2\u0006\u00102\u001a\u00020#H\u0002J\u0016\u00108\u001a\u00020\u00102\f\u00109\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0015H\u0002J\f\u0010:\u001a\u000200*\u00020#H\u0002J\f\u0010;\u001a\u000200*\u00020#H\u0002R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006<"}, d2 = {"Lorg/jetbrains/kotlin/resolve/OverloadResolver;", "", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "overloadFilter", "Lorg/jetbrains/kotlin/resolve/OverloadFilter;", "overloadChecker", "Lorg/jetbrains/kotlin/resolve/OverloadChecker;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "mainFunctionDetectorFactory", "Lorg/jetbrains/kotlin/idea/MainFunctionDetector$Factory;", "(Lorg/jetbrains/kotlin/resolve/BindingTrace;Lorg/jetbrains/kotlin/resolve/OverloadFilter;Lorg/jetbrains/kotlin/resolve/OverloadChecker;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/idea/MainFunctionDetector$Factory;)V", "mainFunctionDetector", "Lorg/jetbrains/kotlin/idea/MainFunctionDetector;", "checkOverloads", "", "c", "Lorg/jetbrains/kotlin/resolve/BodiesResolveContext;", "checkOverloadsInClass", "members", "", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptorWithResolutionScopes;", "nestedClassConstructors", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "checkOverloadsInPackage", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorNonRoot;", "checkOverloadsInPackages", "collectModulePackageMembersWithSameName", "packageMembersByName", "Lorg/jetbrains/kotlin/com/intellij/util/containers/MultiMap;", "Lorg/jetbrains/kotlin/name/FqNameUnsafe;", "interestingDescriptors", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getMembersByName", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "Lorg/jetbrains/kotlin/name/Name;", "findConstructorsInNestedClassesAndTypeAliases", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "findRedeclarations", "getModulePackageMembersWithSameName", "descriptor", "getPossibleRedeclarationGroups", "groupModulePackageMembersByFqName", "isConstructorsOfDifferentRedeclaredClasses", "", "member1", "member2", "isDefinitionsForDifferentPlatforms", "isExpectDeclarationAndDefinition", "declaration", "definition", "isTopLevelMainInDifferentFiles", "reportRedeclarations", "redeclarations", "isPrivate", "isSynthesized", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class OverloadResolver {
    private final MainFunctionDetector mainFunctionDetector;
    private final OverloadChecker overloadChecker;
    private final OverloadFilter overloadFilter;
    private final BindingTrace trace;

    public OverloadResolver(BindingTrace trace, OverloadFilter overloadFilter, OverloadChecker overloadChecker, LanguageVersionSettings languageVersionSettings, MainFunctionDetector.Factory mainFunctionDetectorFactory) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(overloadFilter, "overloadFilter");
        Intrinsics.checkNotNullParameter(overloadChecker, "overloadChecker");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(mainFunctionDetectorFactory, "mainFunctionDetectorFactory");
        this.trace = trace;
        this.overloadFilter = overloadFilter;
        this.overloadChecker = overloadChecker;
        this.mainFunctionDetector = mainFunctionDetectorFactory.createMainFunctionDetector(trace, languageVersionSettings);
    }

    public final void checkOverloads(BodiesResolveContext c) {
        Intrinsics.checkNotNullParameter(c, "c");
        MultiMap<ClassDescriptor, FunctionDescriptor> multiMapFindConstructorsInNestedClassesAndTypeAliases = findConstructorsInNestedClassesAndTypeAliases(c);
        for (ClassDescriptorWithResolutionScopes classDescriptorWithResolutionScopes : c.getDeclaredClasses().values()) {
            Intrinsics.checkNotNull(classDescriptorWithResolutionScopes);
            Collection<FunctionDescriptor> collection = multiMapFindConstructorsInNestedClassesAndTypeAliases.get(classDescriptorWithResolutionScopes);
            Intrinsics.checkNotNullExpressionValue(collection, "get(...)");
            checkOverloadsInClass(classDescriptorWithResolutionScopes, collection);
        }
        checkOverloadsInPackages(c);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final MultiMap<ClassDescriptor, FunctionDescriptor> findConstructorsInNestedClassesAndTypeAliases(BodiesResolveContext c) {
        MultiMap<ClassDescriptor, FunctionDescriptor> multiMapCreate = MultiMap.create();
        Intrinsics.checkNotNullExpressionValue(multiMapCreate, "create(...)");
        for (ClassDescriptorWithResolutionScopes classDescriptorWithResolutionScopes : c.getDeclaredClasses().values()) {
            if (!classDescriptorWithResolutionScopes.getClassKind().isSingleton() && !classDescriptorWithResolutionScopes.getShortName().isSpecial()) {
                DeclarationDescriptor containingDeclaration = classDescriptorWithResolutionScopes.getContainingModule();
                Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
                if (containingDeclaration instanceof ScriptDescriptor) {
                    continue;
                } else if (containingDeclaration instanceof ClassDescriptor) {
                    multiMapCreate.putValues(containingDeclaration, classDescriptorWithResolutionScopes.getConstructors());
                } else if (!(containingDeclaration instanceof FunctionDescriptor) && !(containingDeclaration instanceof PropertyDescriptor) && !(containingDeclaration instanceof PackageFragmentDescriptor)) {
                    throw new IllegalStateException("Illegal class container: " + containingDeclaration);
                }
            }
        }
        for (TypeAliasDescriptor typeAliasDescriptor : c.getTypeAliases().values()) {
            DeclarationDescriptor containingDeclaration2 = typeAliasDescriptor.getContainingModule();
            Intrinsics.checkNotNullExpressionValue(containingDeclaration2, "getContainingDeclaration(...)");
            if (containingDeclaration2 instanceof ClassDescriptor) {
                multiMapCreate.putValues(containingDeclaration2, typeAliasDescriptor.getConstructors());
            }
        }
        return multiMapCreate;
    }

    private final void checkOverloadsInPackages(BodiesResolveContext c) {
        Iterator<Map.Entry<FqNameUnsafe, Collection<DeclarationDescriptorNonRoot>>> it = groupModulePackageMembersByFqName(c, this.overloadFilter).entrySet().iterator();
        while (it.hasNext()) {
            Collection<DeclarationDescriptorNonRoot> value = it.next().getValue();
            Intrinsics.checkNotNullExpressionValue(value, "<get-value>(...)");
            checkOverloadsInPackage(value);
        }
    }

    private final MultiMap<FqNameUnsafe, DeclarationDescriptorNonRoot> groupModulePackageMembersByFqName(BodiesResolveContext c, OverloadFilter overloadFilter) {
        List listFilterPackageMemberOverloads;
        List listFilterPackageMemberOverloads2;
        MultiMap<FqNameUnsafe, DeclarationDescriptorNonRoot> multiMap = new MultiMap<>();
        Collection<SimpleFunctionDescriptor> collectionValues = c.getFunctions().values();
        Intrinsics.checkNotNull(collectionValues, "null cannot be cast to non-null type kotlin.collections.Collection<org.jetbrains.kotlin.descriptors.DeclarationDescriptor>");
        List<DeclarationDescriptor> listPlus = CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) collectionValues, (Iterable) c.getDeclaredClasses().values()), (Iterable) c.getTypeAliases().values());
        HashSet hashSet = new HashSet();
        for (DeclarationDescriptor declarationDescriptor : listPlus) {
            if (declarationDescriptor.getContainingModule() instanceof PackageFragmentDescriptor) {
                FqNameUnsafe fqName = DescriptorUtils.getFqName(declarationDescriptor);
                Intrinsics.checkNotNullExpressionValue(fqName, "getFqName(...)");
                if (hashSet.contains(fqName)) {
                    continue;
                } else {
                    hashSet.add(fqName);
                    DeclarationDescriptor declarationDescriptor2 = declarationDescriptor.getContainingModule();
                    if (!(declarationDescriptor2 instanceof PackageFragmentDescriptor)) {
                        throw new AssertionError(declarationDescriptor + " is not a top-level package member");
                    }
                    ModuleDescriptor containingModuleOrNull = DescriptorUtils.getContainingModuleOrNull(declarationDescriptor);
                    if (containingModuleOrNull == null) {
                        if (declarationDescriptor instanceof CallableMemberDescriptor) {
                            listFilterPackageMemberOverloads2 = CollectionsKt.listOf(declarationDescriptor);
                        } else {
                            if (!(declarationDescriptor instanceof ClassDescriptor)) {
                                throw new AssertionError("Unexpected descriptor kind: " + declarationDescriptor);
                            }
                            listFilterPackageMemberOverloads2 = ((ClassDescriptor) declarationDescriptor).getConstructors();
                            Intrinsics.checkNotNullExpressionValue(listFilterPackageMemberOverloads2, "getConstructors(...)");
                        }
                    } else {
                        Intrinsics.checkNotNull(containingModuleOrNull);
                        MemberScope memberScope = containingModuleOrNull.getPackage(((PackageFragmentDescriptor) declarationDescriptor2).getFqName()).getMemberScope();
                        Name name2 = declarationDescriptor.getShortName();
                        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                        List contributedFunctions = memberScope.getContributedFunctions(name2, NoLookupLocation.WHEN_CHECK_DECLARATION_CONFLICTS);
                        ClassifierDescriptor contributedClassifier = memberScope.mo7465getContributedClassifier(name2, NoLookupLocation.WHEN_CHECK_DECLARATION_CONFLICTS);
                        if (contributedClassifier instanceof ClassDescriptor) {
                            ClassDescriptor classDescriptor = (ClassDescriptor) contributedClassifier;
                            if (!classDescriptor.getClassKind().isSingleton()) {
                                Collection<ClassConstructorDescriptor> constructors = classDescriptor.getConstructors();
                                Intrinsics.checkNotNullExpressionValue(constructors, "getConstructors(...)");
                                contributedFunctions = CollectionsKt.plus((Collection) contributedFunctions, (Iterable) constructors);
                            }
                        } else if (contributedClassifier instanceof TypeAliasDescriptor) {
                            contributedFunctions = CollectionsKt.plus((Collection) contributedFunctions, (Iterable) ((TypeAliasDescriptor) contributedClassifier).getConstructors());
                        }
                        ArrayList arrayList = new ArrayList();
                        for (Object obj : contributedFunctions) {
                            if (Intrinsics.areEqual(DescriptorUtils.getContainingModule((DeclarationDescriptorNonRoot) obj), containingModuleOrNull)) {
                                arrayList.add(obj);
                            }
                        }
                        listFilterPackageMemberOverloads2 = overloadFilter.filterPackageMemberOverloads(arrayList);
                    }
                    multiMap.putValues(fqName, listFilterPackageMemberOverloads2);
                }
            }
        }
        Collection<PropertyDescriptor> collectionValues2 = c.getProperties().values();
        HashSet hashSet2 = new HashSet();
        for (PropertyDescriptor propertyDescriptor : collectionValues2) {
            if (propertyDescriptor.getContainingModule() instanceof PackageFragmentDescriptor) {
                FqNameUnsafe fqName2 = DescriptorUtils.getFqName(propertyDescriptor);
                Intrinsics.checkNotNullExpressionValue(fqName2, "getFqName(...)");
                if (hashSet2.contains(fqName2)) {
                    continue;
                } else {
                    hashSet2.add(fqName2);
                    DeclarationDescriptor declarationDescriptor3 = propertyDescriptor.getContainingModule();
                    if (!(declarationDescriptor3 instanceof PackageFragmentDescriptor)) {
                        throw new AssertionError(propertyDescriptor + " is not a top-level package member");
                    }
                    ModuleDescriptor containingModuleOrNull2 = DescriptorUtils.getContainingModuleOrNull(propertyDescriptor);
                    if (containingModuleOrNull2 == null) {
                        if (propertyDescriptor instanceof CallableMemberDescriptor) {
                            listFilterPackageMemberOverloads = CollectionsKt.listOf(propertyDescriptor);
                        } else {
                            if (!(propertyDescriptor instanceof ClassDescriptor)) {
                                throw new AssertionError("Unexpected descriptor kind: " + propertyDescriptor);
                            }
                            listFilterPackageMemberOverloads = ((ClassDescriptor) propertyDescriptor).getConstructors();
                            Intrinsics.checkNotNullExpressionValue(listFilterPackageMemberOverloads, "getConstructors(...)");
                        }
                    } else {
                        Intrinsics.checkNotNull(containingModuleOrNull2);
                        MemberScope memberScope2 = containingModuleOrNull2.getPackage(((PackageFragmentDescriptor) declarationDescriptor3).getFqName()).getMemberScope();
                        Name name3 = propertyDescriptor.getShortName();
                        Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
                        List listPlus2 = CollectionsKt.plus((Collection) memberScope2.getContributedVariables(name3, NoLookupLocation.WHEN_CHECK_DECLARATION_CONFLICTS), (Iterable) CollectionsKt.listOfNotNull(memberScope2.mo7465getContributedClassifier(name3, NoLookupLocation.WHEN_CHECK_DECLARATION_CONFLICTS)));
                        ArrayList arrayList2 = new ArrayList();
                        for (Object obj2 : listPlus2) {
                            if (Intrinsics.areEqual(DescriptorUtils.getContainingModule((DeclarationDescriptorNonRoot) obj2), containingModuleOrNull2)) {
                                arrayList2.add(obj2);
                            }
                        }
                        listFilterPackageMemberOverloads = overloadFilter.filterPackageMemberOverloads(arrayList2);
                    }
                    multiMap.putValues(fqName2, listFilterPackageMemberOverloads);
                }
            }
        }
        return multiMap;
    }

    private final void checkOverloadsInClass(ClassDescriptorWithResolutionScopes classDescriptor, Collection<? extends FunctionDescriptor> nestedClassConstructors) {
        MultiMap multiMapCreate = MultiMap.create();
        Intrinsics.checkNotNullExpressionValue(multiMapCreate, "create(...)");
        for (CallableMemberDescriptor callableMemberDescriptor : classDescriptor.getDeclaredCallableMembers()) {
            multiMapCreate.putValue(callableMemberDescriptor.getShortName(), callableMemberDescriptor);
        }
        for (FunctionDescriptor functionDescriptor : nestedClassConstructors) {
            Name name2 = functionDescriptor.getContainingModule().getShortName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            multiMapCreate.putValue(name2, functionDescriptor);
        }
        Iterator it = multiMapCreate.entrySet().iterator();
        while (it.hasNext()) {
            Object value = ((Map.Entry) it.next()).getValue();
            Intrinsics.checkNotNullExpressionValue(value, "<get-value>(...)");
            checkOverloadsInClass((Collection) value);
        }
    }

    private final void checkOverloadsInPackage(Collection<? extends DeclarationDescriptorNonRoot> members) {
        if (members.size() == 1) {
            return;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator<Collection<DeclarationDescriptorNonRoot>> it = getPossibleRedeclarationGroups(members).iterator();
        while (it.hasNext()) {
            Collection<DeclarationDescriptorNonRoot> collectionFindRedeclarations = findRedeclarations(it.next());
            for (DeclarationDescriptorNonRoot declarationDescriptorNonRoot : collectionFindRedeclarations) {
                LinkedHashMap linkedHashMap2 = linkedHashMap;
                Object obj = linkedHashMap2.get(declarationDescriptorNonRoot);
                if (obj == null) {
                    obj = (Set) new LinkedHashSet();
                    linkedHashMap2.put(declarationDescriptorNonRoot, obj);
                }
                ((Set) obj).addAll(collectionFindRedeclarations);
            }
        }
        HashSet hashSet = new HashSet();
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            DeclarationDescriptorNonRoot declarationDescriptorNonRoot2 = (DeclarationDescriptorNonRoot) entry.getKey();
            Set set = (Set) entry.getValue();
            if (!hashSet.contains(declarationDescriptorNonRoot2)) {
                Set set2 = set;
                hashSet.addAll(set2);
                reportRedeclarations(set2);
            }
        }
    }

    private final Collection<Collection<DeclarationDescriptorNonRoot>> getPossibleRedeclarationGroups(Collection<? extends DeclarationDescriptorNonRoot> members) {
        ArrayList arrayList = new ArrayList();
        Collection<? extends DeclarationDescriptorNonRoot> collection = members;
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : collection) {
            if (!isPrivate((DeclarationDescriptorNonRoot) obj)) {
                arrayList2.add(obj);
            }
        }
        ArrayList arrayList3 = arrayList2;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj2 : collection) {
            SourceFile containingSourceFile = DescriptorUtils.getContainingSourceFile((DeclarationDescriptorNonRoot) obj2);
            Object obj3 = linkedHashMap.get(containingSourceFile);
            if (obj3 == null) {
                obj3 = (List) new ArrayList();
                linkedHashMap.put(containingSourceFile, obj3);
            }
            ((List) obj3).add(obj2);
        }
        boolean z = false;
        for (List list : linkedHashMap.values()) {
            if (!(list instanceof Collection) || !list.isEmpty()) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    if (isPrivate((DeclarationDescriptorNonRoot) it.next())) {
                        arrayList.add(SetsKt.plus((Set) new LinkedHashSet(arrayList3), (Iterable) list));
                        z = true;
                        break;
                    }
                }
            }
        }
        if (!z && arrayList3.size() > 1) {
            arrayList.add(arrayList3);
        }
        return arrayList;
    }

    private final boolean isPrivate(DeclarationDescriptor declarationDescriptor) {
        return (declarationDescriptor instanceof DeclarationDescriptorWithVisibility) && DescriptorVisibilities.isPrivate(((DeclarationDescriptorWithVisibility) declarationDescriptor).getVisibility());
    }

    private final void checkOverloadsInClass(Collection<? extends CallableMemberDescriptor> members) {
        if (members.size() == 1) {
            return;
        }
        reportRedeclarations(findRedeclarations(members));
    }

    private final boolean isSynthesized(DeclarationDescriptor declarationDescriptor) {
        return (declarationDescriptor instanceof CallableMemberDescriptor) && ((CallableMemberDescriptor) declarationDescriptor).getKind() == CallableMemberDescriptor.Kind.SYNTHESIZED;
    }

    private final Collection<DeclarationDescriptorNonRoot> findRedeclarations(Collection<? extends DeclarationDescriptorNonRoot> members) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (DeclarationDescriptorNonRoot declarationDescriptorNonRoot : members) {
            DeclarationDescriptorNonRoot declarationDescriptorNonRoot2 = declarationDescriptorNonRoot;
            if (!isSynthesized(declarationDescriptorNonRoot2)) {
                for (DeclarationDescriptorNonRoot declarationDescriptorNonRoot3 : members) {
                    if (!Intrinsics.areEqual(declarationDescriptorNonRoot, declarationDescriptorNonRoot3)) {
                        DeclarationDescriptorNonRoot declarationDescriptorNonRoot4 = declarationDescriptorNonRoot3;
                        if (!isConstructorsOfDifferentRedeclaredClasses(declarationDescriptorNonRoot2, declarationDescriptorNonRoot4) && !isTopLevelMainInDifferentFiles(declarationDescriptorNonRoot2, declarationDescriptorNonRoot4) && !isDefinitionsForDifferentPlatforms(declarationDescriptorNonRoot, declarationDescriptorNonRoot3) && !isExpectDeclarationAndDefinition(declarationDescriptorNonRoot2, declarationDescriptorNonRoot4) && !isExpectDeclarationAndDefinition(declarationDescriptorNonRoot4, declarationDescriptorNonRoot2) && !this.overloadChecker.isOverloadable(declarationDescriptorNonRoot2, declarationDescriptorNonRoot4)) {
                            linkedHashSet.add(declarationDescriptorNonRoot);
                        }
                    }
                }
            }
        }
        return linkedHashSet;
    }

    private final boolean isConstructorsOfDifferentRedeclaredClasses(DeclarationDescriptor member1, DeclarationDescriptor member2) {
        if (!(member1 instanceof ConstructorDescriptor) || !(member2 instanceof ConstructorDescriptor)) {
            return false;
        }
        ClassifierDescriptorWithTypeParameters declarationDescriptor = ((ConstructorDescriptor) member1).getContainingModule();
        Intrinsics.checkNotNullExpressionValue(declarationDescriptor, "getContainingDeclaration(...)");
        ClassifierDescriptorWithTypeParameters declarationDescriptor2 = ((ConstructorDescriptor) member2).getContainingModule();
        Intrinsics.checkNotNullExpressionValue(declarationDescriptor2, "getContainingDeclaration(...)");
        return declarationDescriptor != declarationDescriptor2 && Intrinsics.areEqual(declarationDescriptor.getContainingModule(), declarationDescriptor2.getContainingModule());
    }

    private final boolean isTopLevelMainInDifferentFiles(DeclarationDescriptor member1, DeclarationDescriptor member2) {
        if (!MainFunctionDetector.isMain$default(this.mainFunctionDetector, member1, false, false, false, 14, null) || !MainFunctionDetector.isMain$default(this.mainFunctionDetector, member2, false, false, false, 14, null)) {
            return false;
        }
        KtFile containingFile = DescriptorToSourceUtils.getContainingFile(member1);
        KtFile containingFile2 = DescriptorToSourceUtils.getContainingFile(member2);
        return containingFile == null || containingFile2 == null || containingFile != containingFile2;
    }

    private final boolean isExpectDeclarationAndDefinition(DeclarationDescriptor declaration, DeclarationDescriptor definition) {
        return (declaration instanceof MemberDescriptor) && ((MemberDescriptor) declaration).get$isExpect() && (definition instanceof MemberDescriptor) && !((MemberDescriptor) definition).get$isExpect();
    }

    private final boolean isDefinitionsForDifferentPlatforms(DeclarationDescriptorNonRoot member1, DeclarationDescriptorNonRoot member2) {
        return (member1 instanceof MemberDescriptor) && (member2 instanceof MemberDescriptor) && ((MemberDescriptor) member1).getIsActual() && ((MemberDescriptor) member2).getIsActual() && !Intrinsics.areEqual(DescriptorUtilsKt.getPlatform(member1), DescriptorUtilsKt.getPlatform(member2));
    }

    private final void reportRedeclarations(Collection<? extends DeclarationDescriptorNonRoot> redeclarations) {
        if (redeclarations.isEmpty()) {
            return;
        }
        for (DeclarationDescriptorNonRoot declarationDescriptorNonRoot : redeclarations) {
            if (declarationDescriptorNonRoot instanceof PropertyDescriptor ? true : declarationDescriptorNonRoot instanceof ClassifierDescriptor) {
                BindingTrace bindingTrace = this.trace;
                PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(declarationDescriptorNonRoot);
                if (psiElementDescriptorToDeclaration != null) {
                    Diagnostic diagnosticOn = Errors.REDECLARATION.on(psiElementDescriptorToDeclaration, redeclarations);
                    Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
                    bindingTrace.report(diagnosticOn);
                }
            } else if (declarationDescriptorNonRoot instanceof FunctionDescriptor) {
                BindingTrace bindingTrace2 = this.trace;
                PsiElement psiElementDescriptorToDeclaration2 = DescriptorToSourceUtils.descriptorToDeclaration(declarationDescriptorNonRoot);
                if (psiElementDescriptorToDeclaration2 != null) {
                    Diagnostic diagnosticOn2 = Errors.CONFLICTING_OVERLOADS.on(psiElementDescriptorToDeclaration2, redeclarations);
                    Intrinsics.checkNotNullExpressionValue(diagnosticOn2, "on(...)");
                    bindingTrace2.report(diagnosticOn2);
                }
            }
        }
    }
}
