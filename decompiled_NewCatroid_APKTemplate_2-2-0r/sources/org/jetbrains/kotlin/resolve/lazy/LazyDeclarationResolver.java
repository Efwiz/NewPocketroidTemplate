package org.jetbrains.kotlin.resolve.lazy;

import com.google.firebase.analytics.FirebaseAnalytics;
import io.netty.util.internal.StringUtil;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.context.GlobalContext;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptorWithResolutionScopes;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.incremental.KotlinLookupLocation;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.javax.inject.Inject;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclarationEntry;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtNamedDeclaration;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtPrimaryConstructor;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.psi.KtSecondaryConstructor;
import org.jetbrains.kotlin.psi.KtStubbedPsiUtil;
import org.jetbrains.kotlin.psi.KtTypeAlias;
import org.jetbrains.kotlin.psi.KtTypeParameter;
import org.jetbrains.kotlin.psi.KtTypeParameterListOwner;
import org.jetbrains.kotlin.psi.KtVisitor;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.storage.LockBasedLazyResolveStorageManager;

/* JADX INFO: compiled from: LazyDeclarationResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0018\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u001a\u0010\u001c\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u0018\u0010\u001d\u001a\u00020\u00172\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u001a\u0010 \u001a\u0004\u0018\u00010\u00172\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u001d\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$2\u0006\u0010\u001a\u001a\u00020\u001bH\u0000¢\u0006\u0002\b%J\u0016\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020)2\u0006\u0010\u001a\u001a\u00020\u001bJ\u000e\u0010*\u001a\u00020+2\u0006\u0010#\u001a\u00020$J\u001a\u0010*\u001a\u0004\u0018\u00010+2\u0006\u0010#\u001a\u00020$2\u0006\u0010,\u001a\u00020-H\u0002J\u0010\u0010.\u001a\u00020/2\u0006\u0010\u000f\u001a\u000200H\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\u00020\f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0084.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00061"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/LazyDeclarationResolver;", "", "globalContext", "Lorg/jetbrains/kotlin/context/GlobalContext;", "delegationTrace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "topLevelDescriptorProvider", "Lorg/jetbrains/kotlin/resolve/lazy/TopLevelDescriptorProvider;", "absentDescriptorHandler", "Lorg/jetbrains/kotlin/resolve/lazy/AbsentDescriptorHandler;", "(Lorg/jetbrains/kotlin/context/GlobalContext;Lorg/jetbrains/kotlin/resolve/BindingTrace;Lorg/jetbrains/kotlin/resolve/lazy/TopLevelDescriptorProvider;Lorg/jetbrains/kotlin/resolve/lazy/AbsentDescriptorHandler;)V", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "getBindingContext", "()Lorg/jetbrains/kotlin/resolve/BindingContext;", "scopeProvider", "Lorg/jetbrains/kotlin/resolve/lazy/DeclarationScopeProvider;", "getScopeProvider", "()Lorg/jetbrains/kotlin/resolve/lazy/DeclarationScopeProvider;", "setScopeProvider", "(Lorg/jetbrains/kotlin/resolve/lazy/DeclarationScopeProvider;)V", "trace", "findClassDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "classObjectOrScript", "Lorg/jetbrains/kotlin/psi/KtNamedDeclaration;", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "findClassDescriptorIfAny", "getClassDescriptor", "classOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "getClassDescriptorIfAny", "getMemberScopeDeclaredIn", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "getMemberScopeDeclaredIn$frontend", "getScriptDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptorWithResolutionScopes;", "script", "Lorg/jetbrains/kotlin/psi/KtScript;", "resolveToDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "track", "", "setDeclarationScopeProvider", "", "Lorg/jetbrains/kotlin/resolve/lazy/DeclarationScopeProviderImpl;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class LazyDeclarationResolver {
    private final AbsentDescriptorHandler absentDescriptorHandler;
    protected DeclarationScopeProvider scopeProvider;
    private final TopLevelDescriptorProvider topLevelDescriptorProvider;
    private final BindingTrace trace;

    public LazyDeclarationResolver(GlobalContext globalContext, BindingTrace delegationTrace, TopLevelDescriptorProvider topLevelDescriptorProvider, AbsentDescriptorHandler absentDescriptorHandler) {
        Intrinsics.checkNotNullParameter(globalContext, "globalContext");
        Intrinsics.checkNotNullParameter(delegationTrace, "delegationTrace");
        Intrinsics.checkNotNullParameter(topLevelDescriptorProvider, "topLevelDescriptorProvider");
        Intrinsics.checkNotNullParameter(absentDescriptorHandler, "absentDescriptorHandler");
        this.topLevelDescriptorProvider = topLevelDescriptorProvider;
        this.absentDescriptorHandler = absentDescriptorHandler;
        this.trace = new LockBasedLazyResolveStorageManager(globalContext.getStorageManager()).createSafeTrace(delegationTrace);
    }

    protected final DeclarationScopeProvider getScopeProvider() {
        DeclarationScopeProvider declarationScopeProvider = this.scopeProvider;
        if (declarationScopeProvider != null) {
            return declarationScopeProvider;
        }
        Intrinsics.throwUninitializedPropertyAccessException("scopeProvider");
        return null;
    }

    protected final void setScopeProvider(DeclarationScopeProvider declarationScopeProvider) {
        Intrinsics.checkNotNullParameter(declarationScopeProvider, "<set-?>");
        this.scopeProvider = declarationScopeProvider;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final BindingContext getBindingContext() {
        BindingContext context = this.trace.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        return context;
    }

    @Inject
    public final void setDeclarationScopeProvider(DeclarationScopeProviderImpl scopeProvider) {
        Intrinsics.checkNotNullParameter(scopeProvider, "scopeProvider");
        setScopeProvider(scopeProvider);
    }

    public ClassDescriptor getClassDescriptorIfAny(KtClassOrObject classOrObject, LookupLocation location) {
        Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
        Intrinsics.checkNotNullParameter(location, "location");
        return findClassDescriptorIfAny(classOrObject, location);
    }

    public ClassDescriptor getClassDescriptor(KtClassOrObject classOrObject, LookupLocation location) {
        Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
        Intrinsics.checkNotNullParameter(location, "location");
        return findClassDescriptor(classOrObject, location);
    }

    public final ClassDescriptorWithResolutionScopes getScriptDescriptor(KtScript script, LookupLocation location) {
        Intrinsics.checkNotNullParameter(script, "script");
        Intrinsics.checkNotNullParameter(location, "location");
        ClassDescriptor classDescriptorFindClassDescriptor = findClassDescriptor(script, location);
        Intrinsics.checkNotNull(classDescriptorFindClassDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptorWithResolutionScopes");
        return (ClassDescriptorWithResolutionScopes) classDescriptorFindClassDescriptor;
    }

    private final ClassDescriptor findClassDescriptorIfAny(KtNamedDeclaration classObjectOrScript, LookupLocation location) {
        MemberScope memberScopeDeclaredIn$frontend = getMemberScopeDeclaredIn$frontend(classObjectOrScript, location);
        Name nameAsSafeName = classObjectOrScript.getNameAsSafeName();
        Intrinsics.checkNotNullExpressionValue(nameAsSafeName, "getNameAsSafeName(...)");
        memberScopeDeclaredIn$frontend.mo7465getContributedClassifier(nameAsSafeName, location);
        DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) getBindingContext().get(BindingContext.DECLARATION_TO_DESCRIPTOR, classObjectOrScript);
        if (declarationDescriptor instanceof ClassDescriptor) {
            return (ClassDescriptor) declarationDescriptor;
        }
        return null;
    }

    private final ClassDescriptor findClassDescriptor(KtNamedDeclaration classObjectOrScript, LookupLocation location) {
        ClassDescriptor classDescriptorFindClassDescriptorIfAny = findClassDescriptorIfAny(classObjectOrScript, location);
        if (classDescriptorFindClassDescriptorIfAny != null) {
            return classDescriptorFindClassDescriptorIfAny;
        }
        DeclarationDescriptor declarationDescriptorMo7442diagnoseDescriptorNotFound = this.absentDescriptorHandler.mo7442diagnoseDescriptorNotFound(classObjectOrScript);
        Intrinsics.checkNotNull(declarationDescriptorMo7442diagnoseDescriptorNotFound, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        return (ClassDescriptor) declarationDescriptorMo7442diagnoseDescriptorNotFound;
    }

    public final DeclarationDescriptor resolveToDescriptor(KtDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        DeclarationDescriptor declarationDescriptorResolveToDescriptor = resolveToDescriptor(declaration, true);
        return declarationDescriptorResolveToDescriptor == null ? this.absentDescriptorHandler.mo7442diagnoseDescriptorNotFound(declaration) : declarationDescriptorResolveToDescriptor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final DeclarationDescriptor resolveToDescriptor(KtDeclaration declaration, final boolean track) {
        return (DeclarationDescriptor) declaration.accept(new KtVisitor() { // from class: org.jetbrains.kotlin.resolve.lazy.LazyDeclarationResolver.resolveToDescriptor.1
            private final LookupLocation lookupLocationFor(KtDeclaration declaration2, boolean isTopLevel) {
                return (isTopLevel && track) ? new KotlinLookupLocation(declaration2) : NoLookupLocation.WHEN_RESOLVE_DECLARATION;
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitor
            public DeclarationDescriptor visitClass(KtClass klass, Void data) {
                Intrinsics.checkNotNullParameter(klass, "klass");
                return this.getClassDescriptorIfAny(klass, lookupLocationFor(klass, klass.isTopLevel()));
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitor
            public DeclarationDescriptor visitObjectDeclaration(KtObjectDeclaration declaration2, Void data) {
                Intrinsics.checkNotNullParameter(declaration2, "declaration");
                return this.getClassDescriptorIfAny(declaration2, lookupLocationFor(declaration2, declaration2.isTopLevel()));
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitor
            public DeclarationDescriptor visitTypeParameter(KtTypeParameter parameter, Void data) {
                List<TypeParameterDescriptor> parameters;
                Intrinsics.checkNotNullParameter(parameter, "parameter");
                KtTypeParameterListOwner ktTypeParameterListOwner = (KtTypeParameterListOwner) PsiTreeUtil.getParentOfType(parameter, KtTypeParameterListOwner.class);
                if (ktTypeParameterListOwner != null) {
                    DeclarationDescriptor declarationDescriptorResolveToDescriptor = this.resolveToDescriptor(ktTypeParameterListOwner, false);
                    Object obj = null;
                    if (declarationDescriptorResolveToDescriptor == null) {
                        return null;
                    }
                    if (declarationDescriptorResolveToDescriptor instanceof CallableDescriptor) {
                        parameters = ((CallableDescriptor) declarationDescriptorResolveToDescriptor).getTypeParameters();
                        Intrinsics.checkNotNullExpressionValue(parameters, "getTypeParameters(...)");
                    } else {
                        if (!(declarationDescriptorResolveToDescriptor instanceof ClassifierDescriptorWithTypeParameters)) {
                            throw new IllegalStateException("Unknown owner kind for a type parameter: " + declarationDescriptorResolveToDescriptor);
                        }
                        parameters = ((ClassifierDescriptorWithTypeParameters) declarationDescriptorResolveToDescriptor).getTypeConstructor().getParameters();
                        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
                    }
                    Name nameAsSafeName = parameter.getNameAsSafeName();
                    Intrinsics.checkNotNullExpressionValue(nameAsSafeName, "getNameAsSafeName(...)");
                    for (Object obj2 : parameters) {
                        if (Intrinsics.areEqual(((TypeParameterDescriptor) obj2).getShortName(), nameAsSafeName)) {
                            obj = obj2;
                            break;
                        }
                    }
                    TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor) obj;
                    if (typeParameterDescriptor != null) {
                        return typeParameterDescriptor;
                    }
                    throw new IllegalStateException("Type parameter " + nameAsSafeName + " not found for " + declarationDescriptorResolveToDescriptor);
                }
                throw new IllegalStateException(("Owner not found for type parameter: " + parameter.getText()).toString());
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitor
            public DeclarationDescriptor visitNamedFunction(KtNamedFunction function, Void data) {
                Intrinsics.checkNotNullParameter(function, "function");
                KtNamedFunction ktNamedFunction = function;
                LookupLocation lookupLocationLookupLocationFor = lookupLocationFor(ktNamedFunction, function.isTopLevel());
                MemberScope memberScopeDeclaredIn$frontend = this.getMemberScopeDeclaredIn$frontend(ktNamedFunction, lookupLocationLookupLocationFor);
                Name nameAsSafeName = function.getNameAsSafeName();
                Intrinsics.checkNotNullExpressionValue(nameAsSafeName, "getNameAsSafeName(...)");
                memberScopeDeclaredIn$frontend.getContributedFunctions(nameAsSafeName, lookupLocationLookupLocationFor);
                return (DeclarationDescriptor) this.getBindingContext().get(BindingContext.DECLARATION_TO_DESCRIPTOR, function);
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitor
            public DeclarationDescriptor visitParameter(KtParameter parameter, Void data) {
                CallableDescriptor callableDescriptor;
                Intrinsics.checkNotNullParameter(parameter, "parameter");
                PsiElement lightParent = parameter.getLightParent().getLightParent();
                if (lightParent instanceof KtPrimaryConstructor) {
                    KtClassOrObject containingClassOrObject = ((KtPrimaryConstructor) lightParent).getContainingClassOrObject();
                    ClassDescriptor classDescriptorIfAny = this.getClassDescriptorIfAny(containingClassOrObject, lookupLocationFor(containingClassOrObject, false));
                    if (classDescriptorIfAny == null) {
                        return null;
                    }
                    if (parameter.hasValOrVar()) {
                        MemberScope memberScope = classDescriptorIfAny.getDefaultType().getMemberScope();
                        Name nameAsSafeName = parameter.getNameAsSafeName();
                        Intrinsics.checkNotNullExpressionValue(nameAsSafeName, "getNameAsSafeName(...)");
                        memberScope.getContributedVariables(nameAsSafeName, lookupLocationFor(parameter, false));
                        return (DeclarationDescriptor) this.getBindingContext().get(BindingContext.PRIMARY_CONSTRUCTOR_PARAMETER, parameter);
                    }
                    ClassConstructorDescriptor classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor = classDescriptorIfAny.mo6347getUnsubstitutedPrimaryConstructor();
                    if (classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor == null) {
                        throw new IllegalStateException("There are constructor parameters found, so a constructor should also exist".toString());
                    }
                    classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor.getValueParameters();
                    return (DeclarationDescriptor) this.getBindingContext().get(BindingContext.VALUE_PARAMETER, parameter);
                }
                if (lightParent instanceof KtNamedFunction) {
                    Intrinsics.checkNotNull(lightParent);
                    DeclarationDescriptor declarationDescriptorVisitNamedFunction = visitNamedFunction((KtNamedFunction) lightParent, data);
                    callableDescriptor = declarationDescriptorVisitNamedFunction instanceof FunctionDescriptor ? (FunctionDescriptor) declarationDescriptorVisitNamedFunction : null;
                    if (callableDescriptor != null) {
                        callableDescriptor.getValueParameters();
                    }
                    return (DeclarationDescriptor) this.getBindingContext().get(BindingContext.VALUE_PARAMETER, parameter);
                }
                if (lightParent instanceof KtSecondaryConstructor) {
                    Intrinsics.checkNotNull(lightParent);
                    DeclarationDescriptor declarationDescriptorVisitSecondaryConstructor = visitSecondaryConstructor((KtSecondaryConstructor) lightParent, data);
                    callableDescriptor = declarationDescriptorVisitSecondaryConstructor instanceof ConstructorDescriptor ? (ConstructorDescriptor) declarationDescriptorVisitSecondaryConstructor : null;
                    if (callableDescriptor != null) {
                        callableDescriptor.getValueParameters();
                    }
                    return (DeclarationDescriptor) this.getBindingContext().get(BindingContext.VALUE_PARAMETER, parameter);
                }
                return (DeclarationDescriptor) super.visitParameter(parameter, data);
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitor
            public DeclarationDescriptor visitSecondaryConstructor(KtSecondaryConstructor constructor, Void data) {
                Intrinsics.checkNotNullParameter(constructor, "constructor");
                LazyDeclarationResolver lazyDeclarationResolver = this;
                PsiElement lightParent = constructor.getLightParent().getLightParent();
                Intrinsics.checkNotNull(lightParent, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtClassOrObject");
                ClassDescriptor classDescriptorIfAny = lazyDeclarationResolver.getClassDescriptorIfAny((KtClassOrObject) lightParent, lookupLocationFor(constructor, false));
                if (classDescriptorIfAny != null) {
                    classDescriptorIfAny.getConstructors();
                }
                return (DeclarationDescriptor) this.getBindingContext().get(BindingContext.CONSTRUCTOR, constructor);
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitor
            public DeclarationDescriptor visitPrimaryConstructor(KtPrimaryConstructor constructor, Void data) {
                Intrinsics.checkNotNullParameter(constructor, "constructor");
                ClassDescriptor classDescriptorIfAny = this.getClassDescriptorIfAny(constructor.getContainingClassOrObject(), lookupLocationFor(constructor, false));
                if (classDescriptorIfAny != null) {
                    classDescriptorIfAny.getConstructors();
                }
                return (DeclarationDescriptor) this.getBindingContext().get(BindingContext.CONSTRUCTOR, constructor);
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitor
            public DeclarationDescriptor visitProperty(KtProperty property, Void data) {
                Intrinsics.checkNotNullParameter(property, "property");
                KtProperty ktProperty = property;
                LookupLocation lookupLocationLookupLocationFor = lookupLocationFor(ktProperty, property.isTopLevel());
                MemberScope memberScopeDeclaredIn$frontend = this.getMemberScopeDeclaredIn$frontend(ktProperty, lookupLocationLookupLocationFor);
                Name nameAsSafeName = property.getNameAsSafeName();
                Intrinsics.checkNotNullExpressionValue(nameAsSafeName, "getNameAsSafeName(...)");
                memberScopeDeclaredIn$frontend.getContributedVariables(nameAsSafeName, lookupLocationLookupLocationFor);
                return (DeclarationDescriptor) this.getBindingContext().get(BindingContext.DECLARATION_TO_DESCRIPTOR, property);
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitor
            public DeclarationDescriptor visitDestructuringDeclarationEntry(KtDestructuringDeclarationEntry destructuringDeclarationEntry, Void data) {
                Intrinsics.checkNotNullParameter(destructuringDeclarationEntry, "destructuringDeclarationEntry");
                LookupLocation lookupLocationLookupLocationFor = lookupLocationFor(destructuringDeclarationEntry, false);
                PsiElement parent = destructuringDeclarationEntry.getLightParent();
                KtDestructuringDeclaration ktDestructuringDeclaration = parent instanceof KtDestructuringDeclaration ? (KtDestructuringDeclaration) parent : null;
                if (ktDestructuringDeclaration == null) {
                    return null;
                }
                MemberScope memberScopeDeclaredIn$frontend = this.getMemberScopeDeclaredIn$frontend(ktDestructuringDeclaration, lookupLocationLookupLocationFor);
                Name nameAsSafeName = destructuringDeclarationEntry.getNameAsSafeName();
                Intrinsics.checkNotNullExpressionValue(nameAsSafeName, "getNameAsSafeName(...)");
                memberScopeDeclaredIn$frontend.getContributedVariables(nameAsSafeName, lookupLocationLookupLocationFor);
                return (DeclarationDescriptor) this.getBindingContext().get(BindingContext.DECLARATION_TO_DESCRIPTOR, destructuringDeclarationEntry);
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitor
            public DeclarationDescriptor visitTypeAlias(KtTypeAlias typeAlias, Void data) {
                Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
                KtTypeAlias ktTypeAlias = typeAlias;
                LookupLocation lookupLocationLookupLocationFor = lookupLocationFor(ktTypeAlias, typeAlias.isTopLevel());
                MemberScope memberScopeDeclaredIn$frontend = this.getMemberScopeDeclaredIn$frontend(ktTypeAlias, lookupLocationLookupLocationFor);
                Name nameAsSafeName = typeAlias.getNameAsSafeName();
                Intrinsics.checkNotNullExpressionValue(nameAsSafeName, "getNameAsSafeName(...)");
                memberScopeDeclaredIn$frontend.mo7465getContributedClassifier(nameAsSafeName, lookupLocationLookupLocationFor);
                return (DeclarationDescriptor) this.getBindingContext().get(BindingContext.DECLARATION_TO_DESCRIPTOR, typeAlias);
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitor
            public DeclarationDescriptor visitScript(KtScript script, Void data) {
                Intrinsics.checkNotNullParameter(script, "script");
                return this.getScriptDescriptor(script, lookupLocationFor(script, true));
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitor
            public DeclarationDescriptor visitKtElement(KtElement element, Void data) {
                Intrinsics.checkNotNullParameter(element, "element");
                throw new IllegalArgumentException("Unsupported declaration type: " + element + StringUtil.SPACE + PsiUtilsKt.getElementTextWithContext(element));
            }
        }, null);
    }

    public final MemberScope getMemberScopeDeclaredIn$frontend(KtDeclaration declaration, LookupLocation location) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(location, "location");
        KtDeclaration ktDeclaration = declaration;
        KtDeclaration containingDeclaration = KtStubbedPsiUtil.getContainingDeclaration(ktDeclaration);
        if (containingDeclaration == null) {
            PsiFile containingFile = declaration.getContainingFile();
            Intrinsics.checkNotNull(containingFile, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtFile");
            KtFile ktFile = (KtFile) containingFile;
            FqName packageFqName = ktFile.getPackageFqName();
            this.topLevelDescriptorProvider.assertValid();
            MemberScope memberScope = this.topLevelDescriptorProvider.getPackageFragmentOrDiagnoseFailure(packageFqName, ktFile).getMemberScope();
            Intrinsics.checkNotNullExpressionValue(memberScope, "getMemberScope(...)");
            return memberScope;
        }
        if (containingDeclaration instanceof KtClassOrObject) {
            MemberScope unsubstitutedMemberScope = getClassDescriptor((KtClassOrObject) containingDeclaration, location).getUnsubstitutedMemberScope();
            Intrinsics.checkNotNullExpressionValue(unsubstitutedMemberScope, "getUnsubstitutedMemberScope(...)");
            return unsubstitutedMemberScope;
        }
        if (!(containingDeclaration instanceof KtScript)) {
            throw new IllegalStateException("Don't call this method for local declarations: " + declaration + '\n' + PsiUtilsKt.getElementTextWithContext(ktDeclaration));
        }
        MemberScope unsubstitutedMemberScope2 = getScriptDescriptor((KtScript) containingDeclaration, location).getUnsubstitutedMemberScope();
        Intrinsics.checkNotNullExpressionValue(unsubstitutedMemberScope2, "getUnsubstitutedMemberScope(...)");
        return unsubstitutedMemberScope2;
    }
}
