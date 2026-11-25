package org.jetbrains.kotlin.resolve.lazy;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.com.google.common.collect.Lists;
import org.jetbrains.kotlin.com.intellij.openapi.application.ReadAction;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.context.GlobalContext;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptorWithResolutionScopes;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentProvider;
import org.jetbrains.kotlin.descriptors.PackageFragmentProviderOptimized;
import org.jetbrains.kotlin.descriptors.SupertypeLoopChecker;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.javax.inject.Inject;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtAnonymousInitializer;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.psi.KtTypeAlias;
import org.jetbrains.kotlin.resolve.AdditionalClassPartsProvider;
import org.jetbrains.kotlin.resolve.AnnotationResolver;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorResolver;
import org.jetbrains.kotlin.resolve.FunctionDescriptorResolver;
import org.jetbrains.kotlin.resolve.SealedClassInheritorsProvider;
import org.jetbrains.kotlin.resolve.TypeResolver;
import org.jetbrains.kotlin.resolve.calls.components.InferenceSession;
import org.jetbrains.kotlin.resolve.checkers.PlatformDiagnosticSuppressor;
import org.jetbrains.kotlin.resolve.extensions.SyntheticResolveExtension;
import org.jetbrains.kotlin.resolve.lazy.data.KtClassOrObjectInfo;
import org.jetbrains.kotlin.resolve.lazy.data.KtScriptInfo;
import org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProviderFactory;
import org.jetbrains.kotlin.resolve.lazy.declarations.PackageMemberDeclarationProvider;
import org.jetbrains.kotlin.resolve.lazy.descriptors.LazyAnnotations;
import org.jetbrains.kotlin.resolve.lazy.descriptors.LazyAnnotationsContextImpl;
import org.jetbrains.kotlin.resolve.lazy.descriptors.LazyPackageDescriptor;
import org.jetbrains.kotlin.resolve.sam.SamConversionResolver;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.storage.CacheWithNotNullValues;
import org.jetbrains.kotlin.storage.ExceptionTracker;
import org.jetbrains.kotlin.storage.LazyResolveStorageManager;
import org.jetbrains.kotlin.storage.LockBasedLazyResolveStorageManager;
import org.jetbrains.kotlin.storage.MemoizedFunctionToNotNull;
import org.jetbrains.kotlin.types.WrappedTypeFactory;
import org.jetbrains.kotlin.types.checker.NewKotlinTypeChecker;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: loaded from: classes6.dex */
public class ResolveSession implements KotlinCodeAnalyzer, LazyClassContext {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private AdditionalClassPartsProvider additionalClassPartsProvider;
    private AnnotationResolver annotationResolver;
    private final MemoizedFunctionToNotNull<KtFile, LazyAnnotations> danglingAnnotations;
    private final DeclarationProviderFactory declarationProviderFactory;
    private DeclarationScopeProvider declarationScopeProvider;
    private DelegationFilter delegationFilter;
    private DescriptorResolver descriptorResolver;
    private final ExceptionTracker exceptionTracker;
    private final MemoizedFunctionToNotNull<KtFile, LazyAnnotations> fileAnnotations;
    private FileScopeProvider fileScopeProvider;
    private FunctionDescriptorResolver functionDescriptorResolver;
    private final NewKotlinTypeChecker kotlinTypeChecker;
    private LanguageVersionSettings languageVersionSettings;
    private LazyDeclarationResolver lazyDeclarationResolver;
    private LocalDescriptorResolver localDescriptorResolver;
    private LookupTracker lookupTracker;
    private final ModuleDescriptor module;
    private final PackageFragmentProvider packageFragmentProvider;
    private final CacheWithNotNullValues<FqName, LazyPackageDescriptor> packages;
    private PlatformDiagnosticSuppressor platformDiagnosticSuppressor;
    private Project project;
    private SamConversionResolver samConversionResolver;
    private SealedClassInheritorsProvider sealedClassInheritorsProvider;
    private final LazyResolveStorageManager storageManager;
    private SupertypeLoopChecker supertypeLoopsResolver;
    private final SyntheticResolveExtension syntheticResolveExtension;
    private final BindingTrace trace;
    private TypeResolver typeResolver;
    private WrappedTypeFactory wrappedTypeFactory;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 16:
            case 19:
            case 20:
            case 21:
            case 22:
            case 25:
            case 26:
            case 27:
            case 30:
            case 32:
            case 33:
            case 34:
            case 35:
            case 37:
            case 38:
            case 41:
            case 43:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 17:
            case 18:
            case 23:
            case 24:
            case 28:
            case 29:
            case 31:
            case 36:
            case 39:
            case 40:
            case 42:
            case 44:
            case 45:
            case 55:
            case 56:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 16:
            case 19:
            case 20:
            case 21:
            case 22:
            case 25:
            case 26:
            case 27:
            case 30:
            case 32:
            case 33:
            case 34:
            case 35:
            case 37:
            case 38:
            case 41:
            case 43:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
                i2 = 2;
                break;
            case 17:
            case 18:
            case 23:
            case 24:
            case 28:
            case 29:
            case 31:
            case 36:
            case 39:
            case 40:
            case 42:
            case 44:
            case 45:
            case 55:
            case 56:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "declarationScopeProvider";
                break;
            case 2:
                objArr[0] = "lookupTracker";
                break;
            case 3:
                objArr[0] = "languageVersionSettings";
                break;
            case 4:
                objArr[0] = "delegationFilter";
                break;
            case 5:
                objArr[0] = "wrappedTypeFactory";
                break;
            case 6:
                objArr[0] = "platformDiagnosticSuppressor";
                break;
            case 7:
                objArr[0] = "samConversionResolver";
                break;
            case 8:
                objArr[0] = "additionalClassPartsProvider";
                break;
            case 9:
                objArr[0] = "sealedClassInheritorsProvider";
                break;
            case 10:
                objArr[0] = "project";
                break;
            case 11:
                objArr[0] = "globalContext";
                break;
            case 12:
                objArr[0] = "rootDescriptor";
                break;
            case 13:
                objArr[0] = "declarationProviderFactory";
                break;
            case 14:
                objArr[0] = "delegationTrace";
                break;
            case 15:
                objArr[0] = "kotlinTypeChecker";
                break;
            case 16:
            case 19:
            case 20:
            case 21:
            case 22:
            case 25:
            case 26:
            case 27:
            case 30:
            case 32:
            case 33:
            case 34:
            case 35:
            case 37:
            case 38:
            case 41:
            case 43:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
                objArr[0] = "org/jetbrains/kotlin/resolve/lazy/ResolveSession";
                break;
            case 17:
            case 18:
            case 23:
                objArr[0] = "fqName";
                break;
            case 24:
            case 29:
                objArr[0] = FirebaseAnalytics.Param.LOCATION;
                break;
            case 28:
                objArr[0] = "classOrObject";
                break;
            case 31:
                objArr[0] = "script";
                break;
            case 36:
            case 39:
                objArr[0] = "declaration";
                break;
            case 40:
            case 42:
                objArr[0] = "file";
                break;
            case 44:
                objArr[0] = "result";
                break;
            case 45:
                objArr[0] = "current";
                break;
            case 55:
                objArr[0] = "supertypeLoopsResolver";
                break;
            case 56:
                objArr[0] = "localDescriptorResolver";
                break;
            default:
                objArr[0] = "fileScopeProvider";
                break;
        }
        switch (i) {
            case 16:
                objArr[1] = "getPackageFragmentProvider";
                break;
            case 17:
            case 18:
            case 23:
            case 24:
            case 28:
            case 29:
            case 31:
            case 36:
            case 39:
            case 40:
            case 42:
            case 44:
            case 45:
            case 55:
            case 56:
            default:
                objArr[1] = "org/jetbrains/kotlin/resolve/lazy/ResolveSession";
                break;
            case 19:
                objArr[1] = "getPackageFragmentOrDiagnoseFailure";
                break;
            case 20:
                objArr[1] = "getModuleDescriptor";
                break;
            case 21:
                objArr[1] = "getStorageManager";
                break;
            case 22:
                objArr[1] = "getExceptionTracker";
                break;
            case 25:
            case 26:
            case 27:
                objArr[1] = "getTopLevelClassifierDescriptors";
                break;
            case 30:
                objArr[1] = "getClassDescriptor";
                break;
            case 32:
                objArr[1] = "getScriptDescriptor";
                break;
            case 33:
                objArr[1] = "getBindingContext";
                break;
            case 34:
                objArr[1] = "getTrace";
                break;
            case 35:
                objArr[1] = "getDeclarationProviderFactory";
                break;
            case 37:
            case 38:
                objArr[1] = "resolveToDescriptor";
                break;
            case 41:
                objArr[1] = "getFileAnnotations";
                break;
            case 43:
                objArr[1] = "getDanglingAnnotations";
                break;
            case 46:
                objArr[1] = "collectAllPackages";
                break;
            case 47:
                objArr[1] = "getAnnotationResolver";
                break;
            case 48:
                objArr[1] = "getDescriptorResolver";
                break;
            case 49:
                objArr[1] = "getTypeResolver";
                break;
            case 50:
                objArr[1] = "getFunctionDescriptorResolver";
                break;
            case 51:
                objArr[1] = "getDeclarationScopeProvider";
                break;
            case 52:
                objArr[1] = "getFileScopeProvider";
                break;
            case 53:
                objArr[1] = "getLookupTracker";
                break;
            case 54:
                objArr[1] = "getSupertypeLoopChecker";
                break;
            case 57:
                objArr[1] = "getLanguageVersionSettings";
                break;
            case 58:
                objArr[1] = "getDelegationFilter";
                break;
            case 59:
                objArr[1] = "getSyntheticResolveExtension";
                break;
            case 60:
                objArr[1] = "getWrappedTypeFactory";
                break;
            case 61:
                objArr[1] = "getPlatformDiagnosticSuppressor";
                break;
            case 62:
                objArr[1] = "getProject";
                break;
            case 63:
                objArr[1] = "getKotlinTypeCheckerOfOwnerModule";
                break;
            case 64:
                objArr[1] = "getSamConversionResolver";
                break;
            case 65:
                objArr[1] = "getAdditionalClassPartsProvider";
                break;
            case 66:
                objArr[1] = "getSealedClassInheritorsProvider";
                break;
        }
        switch (i) {
            case 1:
                objArr[2] = "setDeclarationScopeProvider";
                break;
            case 2:
                objArr[2] = "setLookupTracker";
                break;
            case 3:
                objArr[2] = "setLanguageVersionSettings";
                break;
            case 4:
                objArr[2] = "setDelegationFilter";
                break;
            case 5:
                objArr[2] = "setWrappedTypeFactory";
                break;
            case 6:
                objArr[2] = "setPlatformDiagnosticSuppressor";
                break;
            case 7:
                objArr[2] = "setSamConversionResolver";
                break;
            case 8:
                objArr[2] = "setAdditionalClassPartsProvider";
                break;
            case 9:
                objArr[2] = "setSealedClassInheritorsProvider";
                break;
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                objArr[2] = "<init>";
                break;
            case 16:
            case 19:
            case 20:
            case 21:
            case 22:
            case 25:
            case 26:
            case 27:
            case 30:
            case 32:
            case 33:
            case 34:
            case 35:
            case 37:
            case 38:
            case 41:
            case 43:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
                break;
            case 17:
                objArr[2] = "getPackageFragment";
                break;
            case 18:
                objArr[2] = "getPackageFragmentOrDiagnoseFailure";
                break;
            case 23:
            case 24:
                objArr[2] = "getTopLevelClassifierDescriptors";
                break;
            case 28:
            case 29:
                objArr[2] = "getClassDescriptor";
                break;
            case 31:
                objArr[2] = "getScriptDescriptor";
                break;
            case 36:
                objArr[2] = "resolveToDescriptor";
                break;
            case 39:
                objArr[2] = "areDescriptorsCreatedForDeclaration";
                break;
            case 40:
                objArr[2] = "getFileAnnotations";
                break;
            case 42:
                objArr[2] = "getDanglingAnnotations";
                break;
            case 44:
            case 45:
                objArr[2] = "collectAllPackages";
                break;
            case 55:
                objArr[2] = "setSupertypeLoopsResolver";
                break;
            case 56:
                objArr[2] = "setLocalDescriptorResolver";
                break;
            default:
                objArr[2] = "setFileScopeProvider";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 16:
            case 19:
            case 20:
            case 21:
            case 22:
            case 25:
            case 26:
            case 27:
            case 30:
            case 32:
            case 33:
            case 34:
            case 35:
            case 37:
            case 38:
            case 41:
            case 43:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
                throw new IllegalStateException(str2);
            case 17:
            case 18:
            case 23:
            case 24:
            case 28:
            case 29:
            case 31:
            case 36:
            case 39:
            case 40:
            case 42:
            case 44:
            case 45:
            case 55:
            case 56:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.LazyClassContext
    public InferenceSession getInferenceSession() {
        return null;
    }

    @Inject
    public void setAnnotationResolve(AnnotationResolver annotationResolver) {
        this.annotationResolver = annotationResolver;
    }

    @Inject
    public void setDescriptorResolver(DescriptorResolver descriptorResolver) {
        this.descriptorResolver = descriptorResolver;
    }

    @Inject
    public void setFunctionDescriptorResolver(FunctionDescriptorResolver functionDescriptorResolver) {
        this.functionDescriptorResolver = functionDescriptorResolver;
    }

    @Inject
    public void setTypeResolver(TypeResolver typeResolver) {
        this.typeResolver = typeResolver;
    }

    @Inject
    public void setLazyDeclarationResolver(LazyDeclarationResolver lazyDeclarationResolver) {
        this.lazyDeclarationResolver = lazyDeclarationResolver;
    }

    @Inject
    public void setFileScopeProvider(FileScopeProvider fileScopeProvider) {
        if (fileScopeProvider == null) {
            $$$reportNull$$$0(0);
        }
        this.fileScopeProvider = fileScopeProvider;
    }

    @Inject
    public void setDeclarationScopeProvider(DeclarationScopeProviderImpl declarationScopeProviderImpl) {
        if (declarationScopeProviderImpl == null) {
            $$$reportNull$$$0(1);
        }
        this.declarationScopeProvider = declarationScopeProviderImpl;
    }

    @Inject
    public void setLookupTracker(LookupTracker lookupTracker) {
        if (lookupTracker == null) {
            $$$reportNull$$$0(2);
        }
        this.lookupTracker = lookupTracker;
    }

    @Inject
    public void setLanguageVersionSettings(LanguageVersionSettings languageVersionSettings) {
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(3);
        }
        this.languageVersionSettings = languageVersionSettings;
    }

    @Inject
    public void setDelegationFilter(DelegationFilter delegationFilter) {
        if (delegationFilter == null) {
            $$$reportNull$$$0(4);
        }
        this.delegationFilter = delegationFilter;
    }

    @Inject
    public void setWrappedTypeFactory(WrappedTypeFactory wrappedTypeFactory) {
        if (wrappedTypeFactory == null) {
            $$$reportNull$$$0(5);
        }
        this.wrappedTypeFactory = wrappedTypeFactory;
    }

    @Inject
    public void setPlatformDiagnosticSuppressor(PlatformDiagnosticSuppressor platformDiagnosticSuppressor) {
        if (platformDiagnosticSuppressor == null) {
            $$$reportNull$$$0(6);
        }
        this.platformDiagnosticSuppressor = platformDiagnosticSuppressor;
    }

    @Inject
    public void setSamConversionResolver(SamConversionResolver samConversionResolver) {
        if (samConversionResolver == null) {
            $$$reportNull$$$0(7);
        }
        this.samConversionResolver = samConversionResolver;
    }

    @Inject
    public void setAdditionalClassPartsProvider(AdditionalClassPartsProvider additionalClassPartsProvider) {
        if (additionalClassPartsProvider == null) {
            $$$reportNull$$$0(8);
        }
        this.additionalClassPartsProvider = additionalClassPartsProvider;
    }

    @Inject
    public void setSealedClassInheritorsProvider(SealedClassInheritorsProvider sealedClassInheritorsProvider) {
        if (sealedClassInheritorsProvider == null) {
            $$$reportNull$$$0(9);
        }
        this.sealedClassInheritorsProvider = sealedClassInheritorsProvider;
    }

    @Deprecated
    public ResolveSession(Project project, GlobalContext globalContext, ModuleDescriptor moduleDescriptor, final DeclarationProviderFactory declarationProviderFactory, BindingTrace bindingTrace, NewKotlinTypeChecker newKotlinTypeChecker) {
        if (project == null) {
            $$$reportNull$$$0(10);
        }
        if (globalContext == null) {
            $$$reportNull$$$0(11);
        }
        if (moduleDescriptor == null) {
            $$$reportNull$$$0(12);
        }
        if (declarationProviderFactory == null) {
            $$$reportNull$$$0(13);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(14);
        }
        if (newKotlinTypeChecker == null) {
            $$$reportNull$$$0(15);
        }
        LockBasedLazyResolveStorageManager lockBasedLazyResolveStorageManager = new LockBasedLazyResolveStorageManager(globalContext.getStorageManager());
        this.storageManager = lockBasedLazyResolveStorageManager;
        this.exceptionTracker = globalContext.getExceptionTracker();
        this.trace = lockBasedLazyResolveStorageManager.createSafeTrace(bindingTrace);
        this.module = moduleDescriptor;
        this.packages = lockBasedLazyResolveStorageManager.createCacheWithNotNullValues();
        this.declarationProviderFactory = declarationProviderFactory;
        this.packageFragmentProvider = new PackageFragmentProviderOptimized() { // from class: org.jetbrains.kotlin.resolve.lazy.ResolveSession.1
            /* JADX WARN: Code duplicated, block: B:23:0x0034  */
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                String str = (i == 4 || i == 7 || i == 8) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                Object[] objArr = new Object[(i == 4 || i == 7 || i == 8) ? 2 : 3];
                if (i == 1) {
                    objArr[0] = "packageFragments";
                } else if (i == 4) {
                    objArr[0] = "org/jetbrains/kotlin/resolve/lazy/ResolveSession$1";
                } else if (i == 6) {
                    objArr[0] = "nameFilter";
                } else if (i == 7 || i == 8) {
                    objArr[0] = "org/jetbrains/kotlin/resolve/lazy/ResolveSession$1";
                } else {
                    objArr[0] = "fqName";
                }
                if (i == 4) {
                    objArr[1] = "getPackageFragments";
                } else if (i == 7 || i == 8) {
                    objArr[1] = "getSubPackagesOf";
                } else {
                    objArr[1] = "org/jetbrains/kotlin/resolve/lazy/ResolveSession$1";
                }
                switch (i) {
                    case 2:
                        objArr[2] = "isEmpty";
                        break;
                    case 3:
                        objArr[2] = "getPackageFragments";
                        break;
                    case 4:
                    case 7:
                    case 8:
                        break;
                    case 5:
                    case 6:
                        objArr[2] = "getSubPackagesOf";
                        break;
                    default:
                        objArr[2] = "collectPackageFragments";
                        break;
                }
                String str2 = String.format(str, objArr);
                if (i != 4 && i != 7 && i != 8) {
                    throw new IllegalArgumentException(str2);
                }
                throw new IllegalStateException(str2);
            }

            @Override // org.jetbrains.kotlin.descriptors.PackageFragmentProviderOptimized
            public void collectPackageFragments(FqName fqName, Collection<PackageFragmentDescriptor> collection) {
                if (fqName == null) {
                    $$$reportNull$$$0(0);
                }
                if (collection == null) {
                    $$$reportNull$$$0(1);
                }
                LazyPackageDescriptor packageFragment = ResolveSession.this.getPackageFragment(fqName);
                if (packageFragment != null) {
                    collection.add(packageFragment);
                }
            }

            @Override // org.jetbrains.kotlin.descriptors.PackageFragmentProviderOptimized
            public boolean isEmpty(FqName fqName) {
                if (fqName == null) {
                    $$$reportNull$$$0(2);
                }
                return declarationProviderFactory.getPackageMemberDeclarationProvider(fqName) == null;
            }

            @Override // org.jetbrains.kotlin.descriptors.PackageFragmentProvider
            public List<PackageFragmentDescriptor> getPackageFragments(FqName fqName) {
                if (fqName == null) {
                    $$$reportNull$$$0(3);
                }
                List<PackageFragmentDescriptor> listCreateMaybeSingletonList = ContainerUtil.createMaybeSingletonList(ResolveSession.this.getPackageFragment(fqName));
                if (listCreateMaybeSingletonList == null) {
                    $$$reportNull$$$0(4);
                }
                return listCreateMaybeSingletonList;
            }

            @Override // org.jetbrains.kotlin.descriptors.PackageFragmentProvider
            public Collection<FqName> getSubPackagesOf(FqName fqName, Function1<? super Name, Boolean> function1) {
                if (fqName == null) {
                    $$$reportNull$$$0(5);
                }
                if (function1 == null) {
                    $$$reportNull$$$0(6);
                }
                LazyPackageDescriptor packageFragment = ResolveSession.this.getPackageFragment(fqName);
                if (packageFragment == null) {
                    List listEmptyList = Collections.emptyList();
                    if (listEmptyList == null) {
                        $$$reportNull$$$0(7);
                    }
                    return listEmptyList;
                }
                Collection<FqName> allDeclaredSubPackages = packageFragment.getDeclarationProvider().getAllDeclaredSubPackages(function1);
                if (allDeclaredSubPackages == null) {
                    $$$reportNull$$$0(8);
                }
                return allDeclaredSubPackages;
            }
        };
        this.fileAnnotations = lockBasedLazyResolveStorageManager.createMemoizedFunction(new Function1() { // from class: org.jetbrains.kotlin.resolve.lazy.ResolveSession$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m7449lambda$new$0$orgjetbrainskotlinresolvelazyResolveSession((KtFile) obj);
            }
        });
        this.danglingAnnotations = lockBasedLazyResolveStorageManager.createMemoizedFunction(new Function1() { // from class: org.jetbrains.kotlin.resolve.lazy.ResolveSession$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m7450lambda$new$1$orgjetbrainskotlinresolvelazyResolveSession((KtFile) obj);
            }
        });
        this.syntheticResolveExtension = SyntheticResolveExtension.INSTANCE.getInstance(project);
        this.project = project;
        this.kotlinTypeChecker = newKotlinTypeChecker;
    }

    /* JADX INFO: renamed from: lambda$new$0$org-jetbrains-kotlin-resolve-lazy-ResolveSession, reason: not valid java name */
    /* synthetic */ LazyAnnotations m7449lambda$new$0$orgjetbrainskotlinresolvelazyResolveSession(KtFile ktFile) {
        return createAnnotations(ktFile, ktFile.getAnnotationEntries());
    }

    /* JADX INFO: renamed from: lambda$new$1$org-jetbrains-kotlin-resolve-lazy-ResolveSession, reason: not valid java name */
    /* synthetic */ LazyAnnotations m7450lambda$new$1$orgjetbrainskotlinresolvelazyResolveSession(KtFile ktFile) {
        return createAnnotations(ktFile, ktFile.getDanglingAnnotations());
    }

    private LazyAnnotations createAnnotations(KtFile ktFile, List<KtAnnotationEntry> list) {
        return new LazyAnnotations(new LazyAnnotationsContextImpl(this.annotationResolver, this.storageManager, this.trace, this.fileScopeProvider.getFileResolutionScope(ktFile)), list);
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.KotlinCodeAnalyzer
    public PackageFragmentProvider getPackageFragmentProvider() {
        PackageFragmentProvider packageFragmentProvider = this.packageFragmentProvider;
        if (packageFragmentProvider == null) {
            $$$reportNull$$$0(16);
        }
        return packageFragmentProvider;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.TopLevelDescriptorProvider
    public LazyPackageDescriptor getPackageFragment(final FqName fqName) {
        if (fqName == null) {
            $$$reportNull$$$0(17);
        }
        final PackageMemberDeclarationProvider packageMemberDeclarationProvider = this.declarationProviderFactory.getPackageMemberDeclarationProvider(fqName);
        if (packageMemberDeclarationProvider == null) {
            return null;
        }
        return this.packages.computeIfAbsent(fqName, new Function0() { // from class: org.jetbrains.kotlin.resolve.lazy.ResolveSession$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return this.f$0.m7445x7aa1f8f(fqName, packageMemberDeclarationProvider);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$getPackageFragment$2$org-jetbrains-kotlin-resolve-lazy-ResolveSession, reason: not valid java name */
    /* synthetic */ LazyPackageDescriptor m7445x7aa1f8f(FqName fqName, PackageMemberDeclarationProvider packageMemberDeclarationProvider) {
        return new LazyPackageDescriptor(this.module, fqName, this, packageMemberDeclarationProvider);
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.TopLevelDescriptorProvider
    public LazyPackageDescriptor getPackageFragmentOrDiagnoseFailure(FqName fqName, KtFile ktFile) {
        if (fqName == null) {
            $$$reportNull$$$0(18);
        }
        LazyPackageDescriptor packageFragment = getPackageFragment(fqName);
        if (packageFragment == null) {
            this.declarationProviderFactory.diagnoseMissingPackageFragment(fqName, ktFile);
        }
        if (packageFragment == null) {
            $$$reportNull$$$0(19);
        }
        return packageFragment;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.KotlinCodeAnalyzer, org.jetbrains.kotlin.resolve.lazy.LazyClassContext
    public ModuleDescriptor getModuleDescriptor() {
        ModuleDescriptor moduleDescriptor = this.module;
        if (moduleDescriptor == null) {
            $$$reportNull$$$0(20);
        }
        return moduleDescriptor;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.LazyClassContext
    public LazyResolveStorageManager getStorageManager() {
        LazyResolveStorageManager lazyResolveStorageManager = this.storageManager;
        if (lazyResolveStorageManager == null) {
            $$$reportNull$$$0(21);
        }
        return lazyResolveStorageManager;
    }

    public ExceptionTracker getExceptionTracker() {
        ExceptionTracker exceptionTracker = this.exceptionTracker;
        if (exceptionTracker == null) {
            $$$reportNull$$$0(22);
        }
        return exceptionTracker;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.TopLevelDescriptorProvider
    public Collection<ClassifierDescriptor> getTopLevelClassifierDescriptors(FqName fqName, final LookupLocation lookupLocation) {
        if (fqName == null) {
            $$$reportNull$$$0(23);
        }
        if (lookupLocation == null) {
            $$$reportNull$$$0(24);
        }
        if (fqName.isRoot()) {
            List listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(25);
            }
            return listEmptyList;
        }
        PackageMemberDeclarationProvider packageMemberDeclarationProvider = this.declarationProviderFactory.getPackageMemberDeclarationProvider(fqName.parent());
        if (packageMemberDeclarationProvider == null) {
            List listEmptyList2 = Collections.emptyList();
            if (listEmptyList2 == null) {
                $$$reportNull$$$0(26);
            }
            return listEmptyList2;
        }
        SmartList smartList = new SmartList();
        smartList.addAll(ContainerUtil.mapNotNull(packageMemberDeclarationProvider.getClassOrObjectDeclarations(fqName.shortName()), new Function() { // from class: org.jetbrains.kotlin.resolve.lazy.ResolveSession$$ExternalSyntheticLambda3
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return this.f$0.m7446x998283d0(lookupLocation, (KtClassOrObjectInfo) obj);
            }
        }));
        smartList.addAll(ContainerUtil.mapNotNull(packageMemberDeclarationProvider.getScriptDeclarations(fqName.shortName()), new Function() { // from class: org.jetbrains.kotlin.resolve.lazy.ResolveSession$$ExternalSyntheticLambda4
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return this.f$0.m7447x8b2c29ef((KtScriptInfo) obj);
            }
        }));
        smartList.addAll(ContainerUtil.map((Collection) packageMemberDeclarationProvider.getTypeAliasDeclarations(fqName.shortName()), new Function() { // from class: org.jetbrains.kotlin.resolve.lazy.ResolveSession$$ExternalSyntheticLambda5
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return this.f$0.m7448x7cd5d00e((KtTypeAlias) obj);
            }
        }));
        return smartList;
    }

    /* JADX INFO: renamed from: lambda$getTopLevelClassifierDescriptors$3$org-jetbrains-kotlin-resolve-lazy-ResolveSession, reason: not valid java name */
    /* synthetic */ ClassDescriptor m7446x998283d0(LookupLocation lookupLocation, KtClassOrObjectInfo ktClassOrObjectInfo) {
        return getClassDescriptor(ktClassOrObjectInfo.mo7451getCorrespondingClassOrObject(), lookupLocation);
    }

    /* JADX INFO: renamed from: lambda$getTopLevelClassifierDescriptors$4$org-jetbrains-kotlin-resolve-lazy-ResolveSession, reason: not valid java name */
    /* synthetic */ ClassDescriptorWithResolutionScopes m7447x8b2c29ef(KtScriptInfo ktScriptInfo) {
        return getScriptDescriptor(ktScriptInfo.getScript());
    }

    /* JADX INFO: renamed from: lambda$getTopLevelClassifierDescriptors$5$org-jetbrains-kotlin-resolve-lazy-ResolveSession, reason: not valid java name */
    /* synthetic */ ClassifierDescriptor m7448x7cd5d00e(KtTypeAlias ktTypeAlias) {
        return (ClassifierDescriptor) this.lazyDeclarationResolver.resolveToDescriptor(ktTypeAlias);
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.KotlinCodeAnalyzer
    public ClassDescriptor getClassDescriptor(KtClassOrObject ktClassOrObject, LookupLocation lookupLocation) {
        if (ktClassOrObject == null) {
            $$$reportNull$$$0(28);
        }
        if (lookupLocation == null) {
            $$$reportNull$$$0(29);
        }
        ClassDescriptor classDescriptor = this.lazyDeclarationResolver.getClassDescriptor(ktClassOrObject, lookupLocation);
        if (classDescriptor == null) {
            $$$reportNull$$$0(30);
        }
        return classDescriptor;
    }

    public ClassDescriptorWithResolutionScopes getScriptDescriptor(KtScript ktScript) {
        if (ktScript == null) {
            $$$reportNull$$$0(31);
        }
        ClassDescriptorWithResolutionScopes scriptDescriptor = this.lazyDeclarationResolver.getScriptDescriptor(ktScript, NoLookupLocation.FOR_SCRIPT);
        if (scriptDescriptor == null) {
            $$$reportNull$$$0(32);
        }
        return scriptDescriptor;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.KotlinCodeAnalyzer
    public BindingContext getBindingContext() {
        BindingContext context = this.trace.getContext();
        if (context == null) {
            $$$reportNull$$$0(33);
        }
        return context;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.LazyClassContext
    public BindingTrace getTrace() {
        BindingTrace bindingTrace = this.trace;
        if (bindingTrace == null) {
            $$$reportNull$$$0(34);
        }
        return bindingTrace;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.LazyClassContext
    public DeclarationProviderFactory getDeclarationProviderFactory() {
        DeclarationProviderFactory declarationProviderFactory = this.declarationProviderFactory;
        if (declarationProviderFactory == null) {
            $$$reportNull$$$0(35);
        }
        return declarationProviderFactory;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.KotlinCodeAnalyzer
    public DeclarationDescriptor resolveToDescriptor(final KtDeclaration ktDeclaration) {
        if (ktDeclaration == null) {
            $$$reportNull$$$0(36);
        }
        assertValid();
        if (!areDescriptorsCreatedForDeclaration(ktDeclaration)) {
            throw new IllegalStateException("No descriptors are created for declarations of type " + ktDeclaration.getClass().getSimpleName() + "\n. Change the caller accordingly");
        }
        if (!((Boolean) ReadAction.compute(new ThrowableComputable() { // from class: org.jetbrains.kotlin.resolve.lazy.ResolveSession$$ExternalSyntheticLambda6
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
            public final Object compute() {
                return Boolean.valueOf(KtPsiUtil.isLocal(ktDeclaration));
            }
        })).booleanValue()) {
            DeclarationDescriptor declarationDescriptorResolveToDescriptor = this.lazyDeclarationResolver.resolveToDescriptor(ktDeclaration);
            if (declarationDescriptorResolveToDescriptor == null) {
                $$$reportNull$$$0(37);
            }
            return declarationDescriptorResolveToDescriptor;
        }
        DeclarationDescriptor declarationDescriptorResolveLocalDeclaration = this.localDescriptorResolver.resolveLocalDeclaration(ktDeclaration);
        if (declarationDescriptorResolveLocalDeclaration == null) {
            $$$reportNull$$$0(38);
        }
        return declarationDescriptorResolveLocalDeclaration;
    }

    public static boolean areDescriptorsCreatedForDeclaration(KtDeclaration ktDeclaration) {
        if (ktDeclaration == null) {
            $$$reportNull$$$0(39);
        }
        return ((ktDeclaration instanceof KtAnonymousInitializer) || (ktDeclaration instanceof KtDestructuringDeclaration)) ? false : true;
    }

    public Annotations getFileAnnotations(KtFile ktFile) {
        if (ktFile == null) {
            $$$reportNull$$$0(40);
        }
        LazyAnnotations lazyAnnotationsInvoke = this.fileAnnotations.invoke(ktFile);
        if (lazyAnnotationsInvoke == null) {
            $$$reportNull$$$0(41);
        }
        return lazyAnnotationsInvoke;
    }

    public Annotations getDanglingAnnotations(KtFile ktFile) {
        if (ktFile == null) {
            $$$reportNull$$$0(42);
        }
        LazyAnnotations lazyAnnotationsInvoke = this.danglingAnnotations.invoke(ktFile);
        if (lazyAnnotationsInvoke == null) {
            $$$reportNull$$$0(43);
        }
        return lazyAnnotationsInvoke;
    }

    private List<LazyPackageDescriptor> getAllPackages() {
        return collectAllPackages(Lists.newArrayList(), getPackageFragment(FqName.ROOT));
    }

    private List<LazyPackageDescriptor> collectAllPackages(List<LazyPackageDescriptor> list, LazyPackageDescriptor lazyPackageDescriptor) {
        if (list == null) {
            $$$reportNull$$$0(44);
        }
        if (lazyPackageDescriptor == null) {
            $$$reportNull$$$0(45);
        }
        list.add(lazyPackageDescriptor);
        Iterator<FqName> it = this.packageFragmentProvider.getSubPackagesOf(lazyPackageDescriptor.getFqName(), MemberScope.INSTANCE.getALL_NAME_FILTER()).iterator();
        while (it.hasNext()) {
            collectAllPackages(list, getPackageFragmentOrDiagnoseFailure(it.next(), null));
        }
        if (list == null) {
            $$$reportNull$$$0(46);
        }
        return list;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.KotlinCodeAnalyzer
    public void forceResolveAll() {
        Iterator<LazyPackageDescriptor> it = getAllPackages().iterator();
        while (it.hasNext()) {
            ForceResolveUtil.forceResolveAllContents(it.next());
        }
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.LazyClassContext
    public AnnotationResolver getAnnotationResolver() {
        AnnotationResolver annotationResolver = this.annotationResolver;
        if (annotationResolver == null) {
            $$$reportNull$$$0(47);
        }
        return annotationResolver;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.LazyClassContext
    public DescriptorResolver getDescriptorResolver() {
        DescriptorResolver descriptorResolver = this.descriptorResolver;
        if (descriptorResolver == null) {
            $$$reportNull$$$0(48);
        }
        return descriptorResolver;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.LazyClassContext
    public TypeResolver getTypeResolver() {
        TypeResolver typeResolver = this.typeResolver;
        if (typeResolver == null) {
            $$$reportNull$$$0(49);
        }
        return typeResolver;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.LazyClassContext
    public FunctionDescriptorResolver getFunctionDescriptorResolver() {
        FunctionDescriptorResolver functionDescriptorResolver = this.functionDescriptorResolver;
        if (functionDescriptorResolver == null) {
            $$$reportNull$$$0(50);
        }
        return functionDescriptorResolver;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.KotlinCodeAnalyzer, org.jetbrains.kotlin.resolve.lazy.LazyClassContext
    public DeclarationScopeProvider getDeclarationScopeProvider() {
        DeclarationScopeProvider declarationScopeProvider = this.declarationScopeProvider;
        if (declarationScopeProvider == null) {
            $$$reportNull$$$0(51);
        }
        return declarationScopeProvider;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.KotlinCodeAnalyzer
    public FileScopeProvider getFileScopeProvider() {
        FileScopeProvider fileScopeProvider = this.fileScopeProvider;
        if (fileScopeProvider == null) {
            $$$reportNull$$$0(52);
        }
        return fileScopeProvider;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.LazyClassContext
    public LookupTracker getLookupTracker() {
        LookupTracker lookupTracker = this.lookupTracker;
        if (lookupTracker == null) {
            $$$reportNull$$$0(53);
        }
        return lookupTracker;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.LazyClassContext
    public SupertypeLoopChecker getSupertypeLoopChecker() {
        SupertypeLoopChecker supertypeLoopChecker = this.supertypeLoopsResolver;
        if (supertypeLoopChecker == null) {
            $$$reportNull$$$0(54);
        }
        return supertypeLoopChecker;
    }

    @Inject
    public void setSupertypeLoopsResolver(SupertypeLoopChecker supertypeLoopChecker) {
        if (supertypeLoopChecker == null) {
            $$$reportNull$$$0(55);
        }
        this.supertypeLoopsResolver = supertypeLoopChecker;
    }

    @Inject
    public void setLocalDescriptorResolver(LocalDescriptorResolver localDescriptorResolver) {
        if (localDescriptorResolver == null) {
            $$$reportNull$$$0(56);
        }
        this.localDescriptorResolver = localDescriptorResolver;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.LazyClassContext
    public LanguageVersionSettings getLanguageVersionSettings() {
        LanguageVersionSettings languageVersionSettings = this.languageVersionSettings;
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(57);
        }
        return languageVersionSettings;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.LazyClassContext
    public DelegationFilter getDelegationFilter() {
        DelegationFilter delegationFilter = this.delegationFilter;
        if (delegationFilter == null) {
            $$$reportNull$$$0(58);
        }
        return delegationFilter;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.LazyClassContext
    public SyntheticResolveExtension getSyntheticResolveExtension() {
        SyntheticResolveExtension syntheticResolveExtension = this.syntheticResolveExtension;
        if (syntheticResolveExtension == null) {
            $$$reportNull$$$0(59);
        }
        return syntheticResolveExtension;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.LazyClassContext
    public WrappedTypeFactory getWrappedTypeFactory() {
        WrappedTypeFactory wrappedTypeFactory = this.wrappedTypeFactory;
        if (wrappedTypeFactory == null) {
            $$$reportNull$$$0(60);
        }
        return wrappedTypeFactory;
    }

    public PlatformDiagnosticSuppressor getPlatformDiagnosticSuppressor() {
        PlatformDiagnosticSuppressor platformDiagnosticSuppressor = this.platformDiagnosticSuppressor;
        if (platformDiagnosticSuppressor == null) {
            $$$reportNull$$$0(61);
        }
        return platformDiagnosticSuppressor;
    }

    public Project getProject() {
        Project project = this.project;
        if (project == null) {
            $$$reportNull$$$0(62);
        }
        return project;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.TopLevelDescriptorProvider
    public void assertValid() {
        this.module.mo7478assertValid();
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.LazyClassContext
    public NewKotlinTypeChecker getKotlinTypeCheckerOfOwnerModule() {
        NewKotlinTypeChecker newKotlinTypeChecker = this.kotlinTypeChecker;
        if (newKotlinTypeChecker == null) {
            $$$reportNull$$$0(63);
        }
        return newKotlinTypeChecker;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.LazyClassContext
    public SamConversionResolver getSamConversionResolver() {
        SamConversionResolver samConversionResolver = this.samConversionResolver;
        if (samConversionResolver == null) {
            $$$reportNull$$$0(64);
        }
        return samConversionResolver;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.LazyClassContext
    public AdditionalClassPartsProvider getAdditionalClassPartsProvider() {
        AdditionalClassPartsProvider additionalClassPartsProvider = this.additionalClassPartsProvider;
        if (additionalClassPartsProvider == null) {
            $$$reportNull$$$0(65);
        }
        return additionalClassPartsProvider;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.LazyClassContext
    public SealedClassInheritorsProvider getSealedClassInheritorsProvider() {
        SealedClassInheritorsProvider sealedClassInheritorsProvider = this.sealedClassInheritorsProvider;
        if (sealedClassInheritorsProvider == null) {
            $$$reportNull$$$0(66);
        }
        return sealedClassInheritorsProvider;
    }
}
