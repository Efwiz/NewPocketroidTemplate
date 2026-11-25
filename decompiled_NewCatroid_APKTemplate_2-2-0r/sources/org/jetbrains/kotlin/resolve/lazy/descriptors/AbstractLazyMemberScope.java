package org.jetbrains.kotlin.resolve.lazy.descriptors;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclarationEntry;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.psi.KtTypeAlias;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorResolver;
import org.jetbrains.kotlin.resolve.FunctionDescriptorResolver;
import org.jetbrains.kotlin.resolve.calls.components.InferenceSession;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.lazy.LazyClassContext;
import org.jetbrains.kotlin.resolve.lazy.data.KtClassOrObjectInfo;
import org.jetbrains.kotlin.resolve.lazy.declarations.AbstractPsiBasedDeclarationProvider;
import org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProvider;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl;
import org.jetbrains.kotlin.resolve.source.KotlinSourceElement;
import org.jetbrains.kotlin.storage.MemoizedFunctionToNotNull;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.utils.Printer;

/* JADX INFO: compiled from: AbstractLazyMemberScope.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000¨\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010#\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b&\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u0002*\n\b\u0001\u0010\u0003 \u0001*\u00020\u00042\u00020\u0005B?\b\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00028\u0001\u0012\u0006\u0010\t\u001a\u00028\u0000\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0016\b\u0002\u0010\f\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u0000¢\u0006\u0002\u0010\rJ:\u0010,\u001a\u00020-2\f\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00020/2\u0006\u00100\u001a\u0002012\u0012\u00102\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u000204032\u0006\u00105\u001a\u000206H\u0014J2\u00107\u001a\b\u0012\u0004\u0012\u00020\u00020/2\u0006\u00108\u001a\u0002092\u0012\u00102\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u000204032\u0006\u00105\u001a\u000206H\u0004J\u0016\u0010:\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0006\u0010;\u001a\u00020\u0012H\u0002J\u0016\u0010<\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00192\u0006\u0010;\u001a\u00020\u0012H\u0002J\u0016\u0010=\u001a\b\u0012\u0004\u0012\u00020\u001c0\u00192\u0006\u0010;\u001a\u00020\u0012H\u0002J\u0016\u0010>\u001a\b\u0012\u0004\u0012\u00020+0\u00192\u0006\u0010;\u001a\u00020\u0012H\u0002J\u001a\u0010?\u001a\u0004\u0018\u00010@2\u0006\u0010;\u001a\u00020\u00122\u0006\u00105\u001a\u000206H\u0016J\u001e\u0010A\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00192\u0006\u0010;\u001a\u00020\u00122\u0006\u00105\u001a\u000206H\u0016J\u001e\u0010B\u001a\b\u0012\u0004\u0012\u00020+0\u00192\u0006\u0010;\u001a\u00020\u00122\u0006\u00105\u001a\u000206H\u0004J\u001e\u0010C\u001a\b\u0012\u0004\u0012\u00020\u001c0\u00192\u0006\u0010;\u001a\u00020\u00122\u0006\u00105\u001a\u000206H\u0016J\u0016\u0010D\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00192\u0006\u0010;\u001a\u00020\u0012H\u0002J\u0016\u0010E\u001a\b\u0012\u0004\u0012\u00020\u001c0\u00192\u0006\u0010;\u001a\u00020\u0012H\u0002J\u001e\u0010F\u001a\u00020-2\u0006\u0010;\u001a\u00020\u00122\f\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00140/H$J\u001e\u0010G\u001a\u00020-2\u0006\u0010;\u001a\u00020\u00122\f\u0010.\u001a\b\u0012\u0004\u0012\u00020\u001a0/H$J\u001e\u0010H\u001a\u00020-2\u0006\u0010;\u001a\u00020\u00122\f\u0010.\u001a\b\u0012\u0004\u0012\u00020\u001c0/H$J\u0010\u0010I\u001a\u00020J2\u0006\u00100\u001a\u00020KH$J\u0010\u0010L\u001a\u00020J2\u0006\u00100\u001a\u00020KH$J\u0010\u0010M\u001a\u00020-2\u0006\u0010N\u001a\u00020OH\u0016J\u0006\u0010P\u001a\u00020QJ\b\u0010R\u001a\u00020QH&R\u0014\u0010\u0006\u001a\u00020\u0007X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR \u0010\u0010\u001a\u0014\u0012\u0004\u0012\u00020\u0012\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u00130\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\u00028\u0001X\u0084\u0004¢\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u0015\u0010\u0016R \u0010\u0018\u001a\u0014\u0012\u0004\u0012\u00020\u0012\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u00190\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u001b\u001a\u0014\u0012\u0004\u0012\u00020\u0012\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c0\u00190\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u001d\u001a\u0014\u0012\u0004\u0012\u00020\u0012\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u00190\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\"\u0010\f\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u0000X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR \u0010 \u001a\u0014\u0012\u0004\u0012\u00020\u0012\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c0\u00190\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010!\u001a\u00020\"X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0016\u0010\t\u001a\u00028\u0000X\u0084\u0004¢\u0006\n\n\u0002\u0010'\u001a\u0004\b%\u0010&R\u0014\u0010\n\u001a\u00020\u000bX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b(\u0010)R \u0010*\u001a\u0014\u0012\u0004\u0012\u00020\u0012\u0012\n\u0012\b\u0012\u0004\u0012\u00020+0\u00190\u0011X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006S"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/descriptors/AbstractLazyMemberScope;", "D", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "DP", "Lorg/jetbrains/kotlin/resolve/lazy/declarations/DeclarationProvider;", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScopeImpl;", "c", "Lorg/jetbrains/kotlin/resolve/lazy/LazyClassContext;", "declarationProvider", "thisDescriptor", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "mainScope", "(Lorg/jetbrains/kotlin/resolve/lazy/LazyClassContext;Lorg/jetbrains/kotlin/resolve/lazy/declarations/DeclarationProvider;Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;Lorg/jetbrains/kotlin/resolve/BindingTrace;Lorg/jetbrains/kotlin/resolve/lazy/descriptors/AbstractLazyMemberScope;)V", "getC", "()Lorg/jetbrains/kotlin/resolve/lazy/LazyClassContext;", "classDescriptors", "Lorg/jetbrains/kotlin/storage/MemoizedFunctionToNotNull;", "Lorg/jetbrains/kotlin/name/Name;", "", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getDeclarationProvider", "()Lorg/jetbrains/kotlin/resolve/lazy/declarations/DeclarationProvider;", "Lorg/jetbrains/kotlin/resolve/lazy/declarations/DeclarationProvider;", "declaredFunctionDescriptors", "", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "declaredPropertyDescriptors", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "functionDescriptors", "getMainScope", "()Lorg/jetbrains/kotlin/resolve/lazy/descriptors/AbstractLazyMemberScope;", "propertyDescriptors", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "getStorageManager", "()Lorg/jetbrains/kotlin/storage/StorageManager;", "getThisDescriptor", "()Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getTrace", "()Lorg/jetbrains/kotlin/resolve/BindingTrace;", "typeAliasDescriptors", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "collectDescriptorsFromDestructingDeclaration", "", "result", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDestructuringDeclaration;", "nameFilter", "Lkotlin/Function1;", "", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "computeDescriptorsFromDeclaredElements", "kindFilter", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", "doGetClasses", "name", "doGetFunctions", "doGetProperties", "doGetTypeAliases", "getContributedClassifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "getContributedFunctions", "getContributedTypeAliasDescriptors", "getContributedVariables", "getDeclaredFunctions", "getDeclaredProperties", "getNonDeclaredClasses", "getNonDeclaredFunctions", "getNonDeclaredProperties", "getScopeForInitializerResolution", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "getScopeForMemberDeclarationResolution", "printScopeStructure", "p", "Lorg/jetbrains/kotlin/utils/Printer;", "toProviderString", "", "toString", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractLazyMemberScope<D extends DeclarationDescriptor, DP extends DeclarationProvider> extends MemberScopeImpl {
    private final LazyClassContext c;
    private final MemoizedFunctionToNotNull<Name, List<ClassDescriptor>> classDescriptors;
    private final DP declarationProvider;
    private final MemoizedFunctionToNotNull<Name, Collection<SimpleFunctionDescriptor>> declaredFunctionDescriptors;
    private final MemoizedFunctionToNotNull<Name, Collection<PropertyDescriptor>> declaredPropertyDescriptors;
    private final MemoizedFunctionToNotNull<Name, Collection<SimpleFunctionDescriptor>> functionDescriptors;
    private final AbstractLazyMemberScope<D, DP> mainScope;
    private final MemoizedFunctionToNotNull<Name, Collection<PropertyDescriptor>> propertyDescriptors;
    private final StorageManager storageManager;
    private final D thisDescriptor;
    private final BindingTrace trace;
    private final MemoizedFunctionToNotNull<Name, Collection<TypeAliasDescriptor>> typeAliasDescriptors;

    protected void collectDescriptorsFromDestructingDeclaration(Set<DeclarationDescriptor> result, KtDestructuringDeclaration declaration, Function1<? super Name, Boolean> nameFilter, LookupLocation location) {
        Intrinsics.checkNotNullParameter(result, "result");
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        Intrinsics.checkNotNullParameter(location, "location");
    }

    protected abstract void getNonDeclaredClasses(Name name2, Set<ClassDescriptor> result);

    protected abstract void getNonDeclaredFunctions(Name name2, Set<SimpleFunctionDescriptor> result);

    protected abstract void getNonDeclaredProperties(Name name2, Set<PropertyDescriptor> result);

    protected abstract LexicalScope getScopeForInitializerResolution(KtDeclaration declaration);

    protected abstract LexicalScope getScopeForMemberDeclarationResolution(KtDeclaration declaration);

    public abstract String toString();

    public /* synthetic */ AbstractLazyMemberScope(LazyClassContext lazyClassContext, DeclarationProvider declarationProvider, DeclarationDescriptor declarationDescriptor, BindingTrace bindingTrace, AbstractLazyMemberScope abstractLazyMemberScope, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(lazyClassContext, declarationProvider, declarationDescriptor, bindingTrace, (i & 16) != 0 ? null : abstractLazyMemberScope);
    }

    protected final LazyClassContext getC() {
        return this.c;
    }

    protected final DP getDeclarationProvider() {
        return this.declarationProvider;
    }

    protected final D getThisDescriptor() {
        return this.thisDescriptor;
    }

    protected final BindingTrace getTrace() {
        return this.trace;
    }

    protected final AbstractLazyMemberScope<D, DP> getMainScope() {
        return this.mainScope;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected AbstractLazyMemberScope(LazyClassContext c, DP declarationProvider, D thisDescriptor, BindingTrace trace, AbstractLazyMemberScope<? extends D, ? extends DP> abstractLazyMemberScope) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(declarationProvider, "declarationProvider");
        Intrinsics.checkNotNullParameter(thisDescriptor, "thisDescriptor");
        Intrinsics.checkNotNullParameter(trace, "trace");
        this.c = c;
        this.declarationProvider = declarationProvider;
        this.thisDescriptor = thisDescriptor;
        this.trace = trace;
        this.mainScope = abstractLazyMemberScope;
        StorageManager storageManager = c.getStorageManager();
        this.storageManager = storageManager;
        this.classDescriptors = storageManager.createMemoizedFunction(new Function1<Name, List<? extends ClassDescriptor>>(this) { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.AbstractLazyMemberScope$classDescriptors$1
            final /* synthetic */ AbstractLazyMemberScope<D, DP> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public final List<ClassDescriptor> invoke(Name it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return this.this$0.doGetClasses(it);
            }
        });
        this.functionDescriptors = storageManager.createMemoizedFunction(new Function1<Name, Collection<? extends SimpleFunctionDescriptor>>(this) { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.AbstractLazyMemberScope$functionDescriptors$1
            final /* synthetic */ AbstractLazyMemberScope<D, DP> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public final Collection<SimpleFunctionDescriptor> invoke(Name it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return this.this$0.doGetFunctions(it);
            }
        });
        this.propertyDescriptors = storageManager.createMemoizedFunction(new Function1<Name, Collection<? extends PropertyDescriptor>>(this) { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.AbstractLazyMemberScope$propertyDescriptors$1
            final /* synthetic */ AbstractLazyMemberScope<D, DP> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public final Collection<PropertyDescriptor> invoke(Name it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return this.this$0.doGetProperties(it);
            }
        });
        this.typeAliasDescriptors = storageManager.createMemoizedFunction(new Function1<Name, Collection<? extends TypeAliasDescriptor>>(this) { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.AbstractLazyMemberScope$typeAliasDescriptors$1
            final /* synthetic */ AbstractLazyMemberScope<D, DP> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public final Collection<TypeAliasDescriptor> invoke(Name it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return this.this$0.doGetTypeAliases(it);
            }
        }, new Function2<Name, Boolean, Collection<? extends TypeAliasDescriptor>>() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.AbstractLazyMemberScope$typeAliasDescriptors$2
            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Collection<? extends TypeAliasDescriptor> invoke(Name name2, Boolean bool) {
                return invoke(name2, bool.booleanValue());
            }

            public final Collection<TypeAliasDescriptor> invoke(Name name2, boolean z) {
                Intrinsics.checkNotNullParameter(name2, "<anonymous parameter 0>");
                return CollectionsKt.emptyList();
            }
        });
        this.declaredFunctionDescriptors = storageManager.createMemoizedFunction(new Function1<Name, Collection<? extends SimpleFunctionDescriptor>>(this) { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.AbstractLazyMemberScope$declaredFunctionDescriptors$1
            final /* synthetic */ AbstractLazyMemberScope<D, DP> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public final Collection<SimpleFunctionDescriptor> invoke(Name it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return this.this$0.getDeclaredFunctions(it);
            }
        });
        this.declaredPropertyDescriptors = storageManager.createMemoizedFunction(new Function1<Name, Collection<? extends PropertyDescriptor>>(this) { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.AbstractLazyMemberScope$declaredPropertyDescriptors$1
            final /* synthetic */ AbstractLazyMemberScope<D, DP> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public final Collection<PropertyDescriptor> invoke(Name it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return this.this$0.getDeclaredProperties(it);
            }
        });
    }

    protected final StorageManager getStorageManager() {
        return this.storageManager;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<ClassDescriptor> doGetClasses(Name name2) {
        MemoizedFunctionToNotNull<Name, List<ClassDescriptor>> memoizedFunctionToNotNull;
        List<ClassDescriptor> listInvoke;
        AbstractLazyMemberScope<D, DP> abstractLazyMemberScope = this.mainScope;
        if (abstractLazyMemberScope != null && (memoizedFunctionToNotNull = abstractLazyMemberScope.classDescriptors) != null && (listInvoke = memoizedFunctionToNotNull.invoke(name2)) != null) {
            return listInvoke;
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<T> it = this.declarationProvider.getClassOrObjectDeclarations(name2).iterator();
        while (it.hasNext()) {
            LinkedHashSet linkedHashSet2 = linkedHashSet;
            KtClassOrObjectInfo ktClassOrObjectInfo = (KtClassOrObjectInfo) it.next();
            KtModifierList ktModifierListMo7452getModifierList = ktClassOrObjectInfo.mo7452getModifierList();
            linkedHashSet2.add(new LazyClassDescriptor(this.c, this.thisDescriptor, name2, ktClassOrObjectInfo, ktModifierListMo7452getModifierList != null ? ktModifierListMo7452getModifierList.hasModifier(KtTokens.EXTERNAL_KEYWORD) : false));
        }
        getNonDeclaredClasses(name2, linkedHashSet);
        return CollectionsKt.toList(linkedHashSet);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    /* JADX INFO: renamed from: getContributedClassifier */
    public ClassifierDescriptor mo7465getContributedClassifier(Name name2, LookupLocation location) {
        KtElement psi;
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        mo7479recordLookup(name2, location);
        List<ClassDescriptor> listInvoke = this.classDescriptors.invoke(name2);
        Collection<TypeAliasDescriptor> collectionInvoke = this.typeAliasDescriptors.invoke(name2);
        TypeAliasDescriptor typeAliasDescriptor = null;
        for (ClassDescriptor classDescriptor : listInvoke) {
            if (!classDescriptor.get$isExpect()) {
                return classDescriptor;
            }
            if (typeAliasDescriptor == null) {
                typeAliasDescriptor = classDescriptor;
            }
        }
        for (TypeAliasDescriptor typeAliasDescriptor2 : collectionInvoke) {
            if (!typeAliasDescriptor2.get$isExpect()) {
                return typeAliasDescriptor2;
            }
            if (typeAliasDescriptor == null) {
                typeAliasDescriptor = typeAliasDescriptor2;
            }
        }
        SourceElement source = typeAliasDescriptor != null ? typeAliasDescriptor.getSourceElement() : null;
        KotlinSourceElement kotlinSourceElement = source instanceof KotlinSourceElement ? (KotlinSourceElement) source : null;
        if (kotlinSourceElement == null || (psi = kotlinSourceElement.getPsi()) == null || psi.isValid()) {
            return typeAliasDescriptor;
        }
        throw new AssertionError("PSI is invalidated for contributed classifier " + DescriptorUtilsKt.getFqNameSafe(typeAliasDescriptor));
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<SimpleFunctionDescriptor> getContributedFunctions(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        mo7479recordLookup(name2, location);
        return this.functionDescriptors.invoke(name2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Collection<SimpleFunctionDescriptor> doGetFunctions(Name name2) {
        LinkedHashSet linkedHashSet = new LinkedHashSet(this.declaredFunctionDescriptors.invoke(name2));
        getNonDeclaredFunctions(name2, linkedHashSet);
        return CollectionsKt.toList(linkedHashSet);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Collection<SimpleFunctionDescriptor> getDeclaredFunctions(Name name2) {
        AbstractLazyMemberScope<D, DP> abstractLazyMemberScope = this.mainScope;
        if (abstractLazyMemberScope == null) {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            for (KtNamedFunction ktNamedFunction : this.declarationProvider.getFunctionDeclarations(name2)) {
                FunctionDescriptorResolver functionDescriptorResolver = this.c.getFunctionDescriptorResolver();
                D d = this.thisDescriptor;
                LexicalScope scopeForMemberDeclarationResolution = getScopeForMemberDeclarationResolution(ktNamedFunction);
                BindingTrace bindingTrace = this.trace;
                DataFlowInfo outerDataFlowInfoForDeclaration = this.c.getDeclarationScopeProvider().getOuterDataFlowInfoForDeclaration(ktNamedFunction);
                Intrinsics.checkNotNullExpressionValue(outerDataFlowInfoForDeclaration, "getOuterDataFlowInfoForDeclaration(...)");
                linkedHashSet.add(functionDescriptorResolver.resolveFunctionDescriptor(d, scopeForMemberDeclarationResolution, ktNamedFunction, bindingTrace, outerDataFlowInfoForDeclaration, this.c.getInferenceSession()));
            }
            return linkedHashSet;
        }
        Collection<SimpleFunctionDescriptor> collectionInvoke = abstractLazyMemberScope.declaredFunctionDescriptors.invoke(name2);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionInvoke, 10));
        Iterator<T> it = collectionInvoke.iterator();
        while (it.hasNext()) {
            FunctionDescriptor functionDescriptorBuild = ((SimpleFunctionDescriptor) it.next()).newCopyBuilder().setPreserveSourceElement().build();
            Intrinsics.checkNotNull(functionDescriptorBuild);
            arrayList.add((SimpleFunctionDescriptor) functionDescriptorBuild);
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<PropertyDescriptor> getContributedVariables(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        mo7479recordLookup(name2, location);
        return this.propertyDescriptors.invoke(name2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Collection<PropertyDescriptor> doGetProperties(Name name2) {
        LinkedHashSet linkedHashSet = new LinkedHashSet(this.declaredPropertyDescriptors.invoke(name2));
        getNonDeclaredProperties(name2, linkedHashSet);
        return CollectionsKt.toList(linkedHashSet);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Collection<PropertyDescriptor> getDeclaredProperties(Name name2) {
        AbstractLazyMemberScope<D, DP> abstractLazyMemberScope = this.mainScope;
        if (abstractLazyMemberScope == null) {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            for (KtProperty ktProperty : this.declarationProvider.getPropertyDeclarations(name2)) {
                DescriptorResolver descriptorResolver = this.c.getDescriptorResolver();
                D d = this.thisDescriptor;
                KtProperty ktProperty2 = ktProperty;
                LexicalScope scopeForMemberDeclarationResolution = getScopeForMemberDeclarationResolution(ktProperty2);
                LexicalScope scopeForInitializerResolution = getScopeForInitializerResolution(ktProperty2);
                BindingTrace bindingTrace = this.trace;
                DataFlowInfo outerDataFlowInfoForDeclaration = this.c.getDeclarationScopeProvider().getOuterDataFlowInfoForDeclaration(ktProperty);
                InferenceSession inferenceSession = this.c.getInferenceSession();
                if (inferenceSession == null) {
                    inferenceSession = InferenceSession.INSTANCE.getDefault();
                }
                PropertyDescriptor propertyDescriptorResolvePropertyDescriptor = descriptorResolver.resolvePropertyDescriptor(d, scopeForMemberDeclarationResolution, scopeForInitializerResolution, ktProperty, bindingTrace, outerDataFlowInfoForDeclaration, inferenceSession);
                Intrinsics.checkNotNullExpressionValue(propertyDescriptorResolvePropertyDescriptor, "resolvePropertyDescriptor(...)");
                linkedHashSet.add(propertyDescriptorResolvePropertyDescriptor);
            }
            for (KtDestructuringDeclarationEntry ktDestructuringDeclarationEntry : this.declarationProvider.getDestructuringDeclarationsEntries(name2)) {
                DescriptorResolver descriptorResolver2 = this.c.getDescriptorResolver();
                D d2 = this.thisDescriptor;
                KtDestructuringDeclarationEntry ktDestructuringDeclarationEntry2 = ktDestructuringDeclarationEntry;
                LexicalScope scopeForMemberDeclarationResolution2 = getScopeForMemberDeclarationResolution(ktDestructuringDeclarationEntry2);
                LexicalScope scopeForInitializerResolution2 = getScopeForInitializerResolution(ktDestructuringDeclarationEntry2);
                BindingTrace bindingTrace2 = this.trace;
                DataFlowInfo outerDataFlowInfoForDeclaration2 = this.c.getDeclarationScopeProvider().getOuterDataFlowInfoForDeclaration(ktDestructuringDeclarationEntry);
                InferenceSession inferenceSession2 = this.c.getInferenceSession();
                if (inferenceSession2 == null) {
                    inferenceSession2 = InferenceSession.INSTANCE.getDefault();
                }
                PropertyDescriptor propertyDescriptorResolveDestructuringDeclarationEntryAsProperty = descriptorResolver2.resolveDestructuringDeclarationEntryAsProperty(d2, scopeForMemberDeclarationResolution2, scopeForInitializerResolution2, ktDestructuringDeclarationEntry, bindingTrace2, outerDataFlowInfoForDeclaration2, inferenceSession2);
                Intrinsics.checkNotNullExpressionValue(propertyDescriptorResolveDestructuringDeclarationEntryAsProperty, "resolveDestructuringDecl…ationEntryAsProperty(...)");
                linkedHashSet.add(propertyDescriptorResolveDestructuringDeclarationEntryAsProperty);
            }
            return linkedHashSet;
        }
        Collection<PropertyDescriptor> collectionInvoke = abstractLazyMemberScope.declaredPropertyDescriptors.invoke(name2);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionInvoke, 10));
        Iterator<T> it = collectionInvoke.iterator();
        while (it.hasNext()) {
            CallableMemberDescriptor callableMemberDescriptorBuild = ((PropertyDescriptor) it.next()).newCopyBuilder().setPreserveSourceElement().build();
            Intrinsics.checkNotNull(callableMemberDescriptorBuild);
            arrayList.add((PropertyDescriptor) callableMemberDescriptorBuild);
        }
        return arrayList;
    }

    protected final Collection<TypeAliasDescriptor> getContributedTypeAliasDescriptors(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        mo7479recordLookup(name2, location);
        return this.typeAliasDescriptors.invoke(name2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Collection<TypeAliasDescriptor> doGetTypeAliases(Name name2) {
        MemoizedFunctionToNotNull<Name, Collection<TypeAliasDescriptor>> memoizedFunctionToNotNull;
        Collection<TypeAliasDescriptor> collectionInvoke;
        AbstractLazyMemberScope<D, DP> abstractLazyMemberScope = this.mainScope;
        if (abstractLazyMemberScope != null && (memoizedFunctionToNotNull = abstractLazyMemberScope.typeAliasDescriptors) != null && (collectionInvoke = memoizedFunctionToNotNull.invoke(name2)) != null) {
            return collectionInvoke;
        }
        Collection<KtTypeAlias> typeAliasDeclarations = this.declarationProvider.getTypeAliasDeclarations(name2);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeAliasDeclarations, 10));
        for (KtTypeAlias ktTypeAlias : typeAliasDeclarations) {
            arrayList.add(this.c.getDescriptorResolver().resolveTypeAliasDescriptor(this.thisDescriptor, getScopeForMemberDeclarationResolution(ktTypeAlias), ktTypeAlias, this.trace));
        }
        return CollectionsKt.toList(arrayList);
    }

    protected final Set<DeclarationDescriptor> computeDescriptorsFromDeclaredElements(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter, LookupLocation location) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        Intrinsics.checkNotNullParameter(location, "location");
        List<KtDeclaration> declarations = this.declarationProvider.getDeclarations(kindFilter, nameFilter);
        LinkedHashSet linkedHashSet = new LinkedHashSet(declarations.size());
        for (KtDeclaration ktDeclaration : declarations) {
            if (ktDeclaration instanceof KtClassOrObject) {
                Name nameAsSafeName = ((KtClassOrObject) ktDeclaration).getNameAsSafeName();
                Intrinsics.checkNotNullExpressionValue(nameAsSafeName, "getNameAsSafeName(...)");
                if (nameFilter.invoke(nameAsSafeName).booleanValue()) {
                    linkedHashSet.addAll(this.classDescriptors.invoke(nameAsSafeName));
                }
            } else if (ktDeclaration instanceof KtFunction) {
                Name nameAsSafeName2 = ((KtFunction) ktDeclaration).getNameAsSafeName();
                Intrinsics.checkNotNullExpressionValue(nameAsSafeName2, "getNameAsSafeName(...)");
                if (nameFilter.invoke(nameAsSafeName2).booleanValue()) {
                    linkedHashSet.addAll(getContributedFunctions(nameAsSafeName2, location));
                }
            } else if (ktDeclaration instanceof KtProperty) {
                Name nameAsSafeName3 = ((KtProperty) ktDeclaration).getNameAsSafeName();
                Intrinsics.checkNotNullExpressionValue(nameAsSafeName3, "getNameAsSafeName(...)");
                if (nameFilter.invoke(nameAsSafeName3).booleanValue()) {
                    linkedHashSet.addAll(getContributedVariables(nameAsSafeName3, location));
                }
            } else if (ktDeclaration instanceof KtParameter) {
                Name nameAsSafeName4 = ((KtParameter) ktDeclaration).getNameAsSafeName();
                Intrinsics.checkNotNullExpressionValue(nameAsSafeName4, "getNameAsSafeName(...)");
                if (nameFilter.invoke(nameAsSafeName4).booleanValue()) {
                    linkedHashSet.addAll(getContributedVariables(nameAsSafeName4, location));
                }
            } else if (ktDeclaration instanceof KtTypeAlias) {
                Name nameAsSafeName5 = ((KtTypeAlias) ktDeclaration).getNameAsSafeName();
                Intrinsics.checkNotNullExpressionValue(nameAsSafeName5, "getNameAsSafeName(...)");
                if (nameFilter.invoke(nameAsSafeName5).booleanValue()) {
                    linkedHashSet.addAll(getContributedTypeAliasDescriptors(nameAsSafeName5, location));
                }
            } else if (ktDeclaration instanceof KtScript) {
                Name nameAsSafeName6 = ((KtScript) ktDeclaration).getNameAsSafeName();
                Intrinsics.checkNotNullExpressionValue(nameAsSafeName6, "getNameAsSafeName(...)");
                if (nameFilter.invoke(nameAsSafeName6).booleanValue()) {
                    linkedHashSet.addAll(this.classDescriptors.invoke(nameAsSafeName6));
                }
            } else if (ktDeclaration instanceof KtDestructuringDeclaration) {
                collectDescriptorsFromDestructingDeclaration(linkedHashSet, (KtDestructuringDeclaration) ktDeclaration, nameFilter, location);
            } else {
                throw new IllegalArgumentException("Unsupported declaration kind: " + ktDeclaration);
            }
        }
        return linkedHashSet;
    }

    public final String toProviderString() {
        String infoString$frontend;
        DP dp = this.declarationProvider;
        AbstractPsiBasedDeclarationProvider abstractPsiBasedDeclarationProvider = dp instanceof AbstractPsiBasedDeclarationProvider ? (AbstractPsiBasedDeclarationProvider) dp : null;
        return (abstractPsiBasedDeclarationProvider == null || (infoString$frontend = abstractPsiBasedDeclarationProvider.toInfoString$frontend()) == null) ? this.declarationProvider.toString() : infoString$frontend;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope
    /* JADX INFO: renamed from: printScopeStructure */
    public void mo6105printScopeStructure(Printer p) {
        Intrinsics.checkNotNullParameter(p, "p");
        p.println(getClass().getSimpleName(), " {");
        p.pushIndent();
        p.println("thisDescriptor = ", this.thisDescriptor);
        p.popIndent();
        p.println("}");
    }
}
