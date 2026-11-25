package org.jetbrains.kotlin.resolve.scopes;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorWithDeprecation;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.utils.ScopeUtilsKt;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: LexicalScopeStorage.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b&\u0018\u00002\u00020\u0001:\u00014B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\tH\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0004J\u0010\u0010\u001c\u001a\u00020\u00192\u0006\u0010\u0017\u001a\u00020\tH\u0004J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u000eH\u0016J \u0010\f\u001a\b\u0012\u0004\u0012\u00020\u001b0 2\u0006\u0010\u001f\u001a\u00020\u000e2\b\b\u0002\u0010!\u001a\u00020\u0016H\u0004J\u001a\u0010\"\u001a\u0004\u0018\u00010#2\u0006\u0010\u001f\u001a\u00020\u000e2\u0006\u0010$\u001a\u00020%H\u0016J*\u0010&\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010'\u001a\u00020(2\u0012\u0010)\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u001e0*H\u0016J\u001e\u0010+\u001a\b\u0012\u0004\u0012\u00020\u001b0 2\u0006\u0010\u001f\u001a\u00020\u000e2\u0006\u0010$\u001a\u00020%H\u0016J\u001e\u0010,\u001a\b\u0012\u0004\u0012\u00020-0 2\u0006\u0010\u001f\u001a\u00020\u000e2\u0006\u0010$\u001a\u00020%H\u0016J\u001c\u0010.\u001a\u0004\u0018\u00010\t2\u0006\u0010\u001f\u001a\u00020\u000e2\b\b\u0002\u0010!\u001a\u00020\u0016H\u0004J\f\u0010/\u001a\u00020\t*\u00020\u0016H\u0002J\u0017\u00100\u001a\u00020\u000f*\u0004\u0018\u00010\u000f2\u0006\u00101\u001a\u00020\u0016H\u0082\u0002J\u001c\u00102\u001a\b\u0012\u0004\u0012\u0002H30 \"\b\b\u0000\u00103*\u00020\t*\u00020\u000fH\u0002R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001c\u0010\f\u001a\u0010\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001c\u0010\u0014\u001a\u0010\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000¨\u00065"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/LexicalScopeStorage;", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "parent", "Lorg/jetbrains/kotlin/resolve/scopes/HierarchicalScope;", "redeclarationChecker", "Lorg/jetbrains/kotlin/resolve/scopes/LocalRedeclarationChecker;", "(Lorg/jetbrains/kotlin/resolve/scopes/HierarchicalScope;Lorg/jetbrains/kotlin/resolve/scopes/LocalRedeclarationChecker;)V", "addedDescriptors", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getAddedDescriptors", "()Ljava/util/List;", "functionsByName", "", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScopeStorage$IntList;", "getParent", "()Lorg/jetbrains/kotlin/resolve/scopes/HierarchicalScope;", "getRedeclarationChecker", "()Lorg/jetbrains/kotlin/resolve/scopes/LocalRedeclarationChecker;", "variablesAndClassifiersByName", "addDescriptor", "", "descriptor", "addFunctionDescriptorInternal", "", "functionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "addVariableOrClassDescriptor", "definitelyDoesNotContainName", "", "name", "", "descriptorLimit", "getContributedClassifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "getContributedDescriptors", "kindFilter", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", "nameFilter", "Lkotlin/Function1;", "getContributedFunctions", "getContributedVariables", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "variableOrClassDescriptorByName", "descriptorByIndex", "plus", "value", "toDescriptors", "TDescriptor", "IntList", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class LexicalScopeStorage implements LexicalScope {
    private final List<DeclarationDescriptor> addedDescriptors;
    private Map<Name, IntList> functionsByName;
    private final HierarchicalScope parent;
    private final LocalRedeclarationChecker redeclarationChecker;
    private Map<Name, IntList> variablesAndClassifiersByName;

    public LexicalScopeStorage(HierarchicalScope parent, LocalRedeclarationChecker redeclarationChecker) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(redeclarationChecker, "redeclarationChecker");
        this.redeclarationChecker = redeclarationChecker;
        this.parent = ScopeUtilsKt.takeSnapshot(parent);
        this.addedDescriptors = new SmartList();
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public DescriptorWithDeprecation<ClassifierDescriptor> getContributedClassifierIncludeDeprecated(Name name2, LookupLocation lookupLocation) {
        return LexicalScope.DefaultImpls.getContributedClassifierIncludeDeprecated(this, name2, lookupLocation);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public /* bridge */ /* synthetic */ Collection getContributedDescriptors(DescriptorKindFilter descriptorKindFilter, Function1 function1) {
        return getContributedDescriptors(descriptorKindFilter, (Function1<? super Name, Boolean>) function1);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    /* JADX INFO: renamed from: recordLookup */
    public void mo7479recordLookup(Name name2, LookupLocation lookupLocation) {
        LexicalScope.DefaultImpls.recordLookup(this, name2, lookupLocation);
    }

    public final LocalRedeclarationChecker getRedeclarationChecker() {
        return this.redeclarationChecker;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.LexicalScope, org.jetbrains.kotlin.resolve.scopes.HierarchicalScope
    /* JADX INFO: renamed from: getParent, reason: from getter */
    public HierarchicalScope get$newParent() {
        return this.parent;
    }

    protected final List<DeclarationDescriptor> getAddedDescriptors() {
        return this.addedDescriptors;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    /* JADX INFO: renamed from: getContributedClassifier */
    public ClassifierDescriptor mo7465getContributedClassifier(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        DeclarationDescriptor declarationDescriptorVariableOrClassDescriptorByName$default = variableOrClassDescriptorByName$default(this, name2, 0, 2, null);
        if (declarationDescriptorVariableOrClassDescriptorByName$default instanceof ClassifierDescriptor) {
            return (ClassifierDescriptor) declarationDescriptorVariableOrClassDescriptorByName$default;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public List<VariableDescriptor> getContributedVariables(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        DeclarationDescriptor declarationDescriptorVariableOrClassDescriptorByName$default = variableOrClassDescriptorByName$default(this, name2, 0, 2, null);
        return CollectionsKt.listOfNotNull(declarationDescriptorVariableOrClassDescriptorByName$default instanceof VariableDescriptor ? (VariableDescriptor) declarationDescriptorVariableOrClassDescriptorByName$default : null);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public List<FunctionDescriptor> getContributedFunctions(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return functionsByName$default(this, name2, 0, 2, null);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public List<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        return this.addedDescriptors;
    }

    protected final void addVariableOrClassDescriptor(DeclarationDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Name name2 = descriptor.getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        if (name2.isSpecial()) {
            return;
        }
        int iAddDescriptor = addDescriptor(descriptor);
        if (this.variablesAndClassifiersByName == null) {
            this.variablesAndClassifiersByName = new HashMap();
        }
        Map<Name, IntList> map = this.variablesAndClassifiersByName;
        Intrinsics.checkNotNull(map);
        Map<Name, IntList> map2 = this.variablesAndClassifiersByName;
        Intrinsics.checkNotNull(map2);
        map.put(name2, plus(map2.get(name2), iAddDescriptor));
    }

    protected final void addFunctionDescriptorInternal(FunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        Name name2 = functionDescriptor.getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        int iAddDescriptor = addDescriptor(functionDescriptor);
        if (this.functionsByName == null) {
            this.functionsByName = new HashMap(1);
        }
        Map<Name, IntList> map = this.functionsByName;
        Intrinsics.checkNotNull(map);
        Map<Name, IntList> map2 = this.functionsByName;
        Intrinsics.checkNotNull(map2);
        map.put(name2, plus(map2.get(name2), iAddDescriptor));
    }

    public static /* synthetic */ DeclarationDescriptor variableOrClassDescriptorByName$default(LexicalScopeStorage lexicalScopeStorage, Name name2, int i, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: variableOrClassDescriptorByName");
        }
        if ((i2 & 2) != 0) {
            i = lexicalScopeStorage.addedDescriptors.size();
        }
        return lexicalScopeStorage.variableOrClassDescriptorByName(name2, i);
    }

    protected final DeclarationDescriptor variableOrClassDescriptorByName(Name name2, int descriptorLimit) {
        Intrinsics.checkNotNullParameter(name2, "name");
        if (descriptorLimit == 0) {
            return null;
        }
        Map<Name, IntList> map = this.variablesAndClassifiersByName;
        for (IntList prev = map != null ? map.get(name2) : null; prev != null; prev = prev.getPrev()) {
            int last = prev.getLast();
            if (last < descriptorLimit) {
                return descriptorByIndex(last);
            }
        }
        return null;
    }

    public static /* synthetic */ List functionsByName$default(LexicalScopeStorage lexicalScopeStorage, Name name2, int i, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: functionsByName");
        }
        if ((i2 & 2) != 0) {
            i = lexicalScopeStorage.addedDescriptors.size();
        }
        return lexicalScopeStorage.functionsByName(name2, i);
    }

    protected final List<FunctionDescriptor> functionsByName(Name name2, int descriptorLimit) {
        Intrinsics.checkNotNullParameter(name2, "name");
        if (descriptorLimit == 0) {
            return CollectionsKt.emptyList();
        }
        Map<Name, IntList> map = this.functionsByName;
        for (IntList prev = map != null ? map.get(name2) : null; prev != null; prev = prev.getPrev()) {
            if (prev.getLast() < descriptorLimit) {
                return toDescriptors(prev);
            }
        }
        return CollectionsKt.emptyList();
    }

    private final int addDescriptor(DeclarationDescriptor descriptor) {
        this.redeclarationChecker.checkBeforeAddingToScope(this, descriptor);
        this.addedDescriptors.add(descriptor);
        return this.addedDescriptors.size() - 1;
    }

    /* JADX INFO: compiled from: LexicalScopeStorage.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\b\u0002\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0000¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0000¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/LexicalScopeStorage$IntList;", "", "last", "", "prev", "(ILorg/jetbrains/kotlin/resolve/scopes/LexicalScopeStorage$IntList;)V", "getLast", "()I", "getPrev", "()Lorg/jetbrains/kotlin/resolve/scopes/LexicalScopeStorage$IntList;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class IntList {
        private final int last;
        private final IntList prev;

        public IntList(int i, IntList intList) {
            this.last = i;
            this.prev = intList;
        }

        public final int getLast() {
            return this.last;
        }

        public final IntList getPrev() {
            return this.prev;
        }
    }

    private final DeclarationDescriptor descriptorByIndex(int i) {
        return this.addedDescriptors.get(i);
    }

    private final IntList plus(IntList intList, int i) {
        return new IntList(i, intList);
    }

    private final <TDescriptor extends DeclarationDescriptor> List<TDescriptor> toDescriptors(IntList intList) {
        ArrayList arrayList = new ArrayList(1);
        do {
            Intrinsics.checkNotNull(intList);
            DeclarationDescriptor declarationDescriptorDescriptorByIndex = descriptorByIndex(intList.getLast());
            Intrinsics.checkNotNull(declarationDescriptorDescriptorByIndex, "null cannot be cast to non-null type TDescriptor of org.jetbrains.kotlin.resolve.scopes.LexicalScopeStorage.toDescriptors");
            arrayList.add(declarationDescriptorDescriptorByIndex);
            intList = intList.getPrev();
        } while (intList != null);
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public boolean definitelyDoesNotContainName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Map<Name, IntList> map = this.functionsByName;
        if ((map != null ? map.get(name2) : null) == null) {
            Map<Name, IntList> map2 = this.variablesAndClassifiersByName;
            if ((map2 != null ? map2.get(name2) : null) == null) {
                return true;
            }
        }
        return false;
    }
}
