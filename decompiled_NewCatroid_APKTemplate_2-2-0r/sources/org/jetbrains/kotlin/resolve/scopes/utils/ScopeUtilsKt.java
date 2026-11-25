package org.jetbrains.kotlin.resolve.scopes.utils;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorWithDeprecation;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PackageViewDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.HierarchicalScope;
import org.jetbrains.kotlin.resolve.scopes.ImportingScope;
import org.jetbrains.kotlin.resolve.scopes.LexicalChainedScope;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.LexicalScopeImpl;
import org.jetbrains.kotlin.resolve.scopes.LexicalScopeKind;
import org.jetbrains.kotlin.resolve.scopes.LexicalWritableScope;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.utils.Printer;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: ScopeUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0094\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0007\u001a\"\u0010\u0007\u001a\u0004\u0018\u00010\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\b\u001a\u0012\u0010\f\u001a\u00020\r*\u00020\r2\u0006\u0010\u000e\u001a\u00020\b\u001a\u0018\u0010\u000f\u001a\u00020\r*\u00020\r2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\b0\n\u001a\u001a\u0010\u0011\u001a\u00020\u0012*\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0016\u001a:\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00190\u0018\"\b\b\u0000\u0010\u0019*\u00020\u001a*\u00020\u00022\u0018\u0010\u001b\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00190\u00180\u001cH\u0086\bø\u0001\u0000\u001a:\u0010\u001d\u001a\b\u0012\u0004\u0012\u0002H\u00190\u0018\"\b\b\u0000\u0010\u0019*\u00020\u001a*\u00020\u00022\u0018\u0010\u001b\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00190\u00180\u001cH\u0086\bø\u0001\u0000\u001a:\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00130\u0018*\u00020\u00022\b\b\u0002\u0010\u001f\u001a\u00020 2\u0014\b\u0002\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020\u00120\u001c2\b\b\u0002\u0010#\u001a\u00020\u0012\u001a3\u0010$\u001a\b\u0012\u0004\u0012\u0002H\u00190\n\"\b\b\u0000\u0010\u0019*\u00020\u001a*\u00020\u00022\u0014\u0010\u001b\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u0001H\u00190\u001cH\u0082\b\u001a \u0010%\u001a\b\u0012\u0004\u0012\u00020&0\u0018*\u00020\u00022\u0006\u0010'\u001a\u00020\"2\u0006\u0010\u0015\u001a\u00020\u0016\u001a \u0010(\u001a\b\u0012\u0004\u0012\u00020)0\u0018*\u00020\u00022\u0006\u0010'\u001a\u00020\"2\u0006\u0010\u0015\u001a\u00020\u0016\u001a\u0014\u0010*\u001a\u00020\r*\u00020\r2\b\u0010+\u001a\u0004\u0018\u00010,\u001a\u001c\u0010-\u001a\u0004\u0018\u00010.*\u00020\u00022\u0006\u0010'\u001a\u00020\"2\u0006\u0010\u0015\u001a\u00020\u0016\u001a\"\u0010/\u001a\n\u0012\u0004\u0012\u00020.\u0018\u000100*\u00020\u00022\u0006\u0010'\u001a\u00020\"2\u0006\u0010\u0015\u001a\u00020\u0016\u001a7\u00101\u001a\u0004\u0018\u0001H\u0019\"\b\b\u0000\u0010\u0019*\u00020\u001a*\u00020\u00022\u0014\u00102\u001a\u0010\u0012\u0004\u0012\u00020\b\u0012\u0006\u0012\u0004\u0018\u0001H\u00190\u001cH\u0086\bø\u0001\u0000¢\u0006\u0002\u00103\u001a7\u00104\u001a\u0004\u0018\u0001H\u0019\"\b\b\u0000\u0010\u0019*\u00020\u001a*\u00020\u00022\u0014\u00102\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u0001H\u00190\u001cH\u0086\bø\u0001\u0000¢\u0006\u0002\u00103\u001a2\u00105\u001a\u0004\u0018\u00010&*\u00020\u00022\u0006\u0010'\u001a\u00020\"2\u0006\u0010\u0015\u001a\u00020\u00162\u0014\b\u0002\u00106\u001a\u000e\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u00020\u00120\u001c\u001a\u0016\u00107\u001a\u0004\u0018\u00010)*\u00020\r2\u0006\u0010'\u001a\u00020\"H\u0007\u001a\u0014\u00108\u001a\u0004\u0018\u000109*\u00020\u00022\u0006\u0010'\u001a\u00020\"\u001a2\u0010:\u001a\u0004\u0018\u00010)*\u00020\u00022\u0006\u0010'\u001a\u00020\"2\u0006\u0010\u0015\u001a\u00020\u00162\u0014\b\u0002\u00106\u001a\u000e\u0012\u0004\u0012\u00020)\u0012\u0004\u0012\u00020\u00120\u001c\u001a\u0018\u0010;\u001a\b\u0012\u0004\u0012\u00020\u00130\u0018*\u00020\r2\u0006\u0010<\u001a\u00020\"\u001a\u0010\u0010=\u001a\b\u0012\u0004\u0012\u00020,0\n*\u00020\r\u001a\u0018\u0010>\u001a\u00020\b*\u00020?2\n\b\u0002\u0010@\u001a\u0004\u0018\u00010\bH\u0007\u001a$\u0010A\u001a\u00020B*\u00020\u00022\u0012\u0010C\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020B0\u001cH\u0086\bø\u0001\u0000\u001a\u0014\u0010D\u001a\u00020\r*\u00020\r2\b\u0010E\u001a\u0004\u0018\u00010\b\u001a\n\u0010F\u001a\u00020\u0002*\u00020\u0002\u001a\u0014\u0010G\u001a\u00020\b*\u00020\b2\b\u0010H\u001a\u0004\u0018\u00010\b\"\u001b\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u001b\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0004\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006I"}, d2 = {"parents", "Lkotlin/sequences/Sequence;", "Lorg/jetbrains/kotlin/resolve/scopes/HierarchicalScope;", "getParents", "(Lorg/jetbrains/kotlin/resolve/scopes/HierarchicalScope;)Lkotlin/sequences/Sequence;", "parentsWithSelf", "getParentsWithSelf", "chainImportingScopes", "Lorg/jetbrains/kotlin/resolve/scopes/ImportingScope;", "scopes", "", "tail", "addImportingScope", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "importScope", "addImportingScopes", "importScopes", "canBeResolvedWithoutDeprecation", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "scopeForResolution", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "collectAllFromImportingScopes", "", ExifInterface.GPS_DIRECTION_TRUE, "", "collect", "Lkotlin/Function1;", "collectAllFromMeAndParent", "collectDescriptorsFiltered", "kindFilter", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", "nameFilter", "Lorg/jetbrains/kotlin/name/Name;", "changeNamesForAliased", "collectFromMeAndParent", "collectFunctions", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "name", "collectVariables", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "createScopeForDestructuring", "newReceiver", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "findClassifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "findFirstClassifierWithDeprecationStatus", "Lorg/jetbrains/kotlin/descriptors/DescriptorWithDeprecation;", "findFirstFromImportingScopes", "fetch", "(Lorg/jetbrains/kotlin/resolve/scopes/HierarchicalScope;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "findFirstFromMeAndParent", "findFunction", "predicate", "findLocalVariable", "findPackage", "Lorg/jetbrains/kotlin/descriptors/PackageViewDescriptor;", "findVariable", "getDeclarationsByLabel", "labelName", "getImplicitReceiversHierarchy", "memberScopeAsImportingScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "parentScope", "processForMeAndParent", "", "process", "replaceImportingScopes", "importingScopeChain", "takeSnapshot", "withParent", "newParent", "resolution"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ScopeUtilsKt {
    public static final ImportingScope memberScopeAsImportingScope(MemberScope memberScope) {
        Intrinsics.checkNotNullParameter(memberScope, "<this>");
        return memberScopeAsImportingScope$default(memberScope, null, 1, null);
    }

    public static final Sequence<HierarchicalScope> getParentsWithSelf(HierarchicalScope hierarchicalScope) {
        Intrinsics.checkNotNullParameter(hierarchicalScope, "<this>");
        return SequencesKt.generateSequence(hierarchicalScope, new Function1<HierarchicalScope, HierarchicalScope>() { // from class: org.jetbrains.kotlin.resolve.scopes.utils.ScopeUtilsKt$parentsWithSelf$1
            @Override // kotlin.jvm.functions.Function1
            public final HierarchicalScope invoke(HierarchicalScope it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getParent();
            }
        });
    }

    public static final Sequence<HierarchicalScope> getParents(HierarchicalScope hierarchicalScope) {
        Intrinsics.checkNotNullParameter(hierarchicalScope, "<this>");
        return SequencesKt.drop(getParentsWithSelf(hierarchicalScope), 1);
    }

    public static final List<ReceiverParameterDescriptor> getImplicitReceiversHierarchy(LexicalScope lexicalScope) {
        List listPlus;
        Intrinsics.checkNotNullParameter(lexicalScope, "<this>");
        LexicalScope lexicalScope2 = lexicalScope;
        SmartList smartList = null;
        do {
            if (lexicalScope2 instanceof LexicalScope) {
                LexicalScope lexicalScope3 = (LexicalScope) lexicalScope2;
                listPlus = CollectionsKt.plus((Collection) CollectionsKt.listOfNotNull(lexicalScope3.getImplicitReceiver()), (Iterable) lexicalScope3.getContextReceiversGroup());
            } else {
                listPlus = null;
            }
            if (listPlus != null) {
                if (smartList == null) {
                    smartList = new SmartList();
                }
                smartList.add(listPlus);
            }
            lexicalScope2 = lexicalScope2.getParent();
        } while (lexicalScope2 != null);
        SmartList smartListEmptyList = smartList;
        if (smartListEmptyList == null) {
            smartListEmptyList = CollectionsKt.emptyList();
        }
        return CollectionsKt.flatten(smartListEmptyList);
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0031  */
    public static final Collection<DeclarationDescriptor> getDeclarationsByLabel(LexicalScope lexicalScope, Name labelName) {
        List listEmptyList;
        Intrinsics.checkNotNullParameter(lexicalScope, "<this>");
        Intrinsics.checkNotNullParameter(labelName, "labelName");
        LexicalScope lexicalScope2 = lexicalScope;
        Collection<DeclarationDescriptor> collectionConcat = null;
        do {
            if (lexicalScope2 instanceof LexicalScope) {
                LexicalScope lexicalScope3 = (LexicalScope) lexicalScope2;
                if (lexicalScope3.getIsOwnerDescriptorAccessibleByLabel() && Intrinsics.areEqual(lexicalScope3.getOwnerDescriptor().getShortName(), labelName)) {
                    listEmptyList = CollectionsKt.listOf(lexicalScope3.getOwnerDescriptor());
                } else {
                    listEmptyList = CollectionsKt.emptyList();
                }
            } else {
                listEmptyList = CollectionsKt.emptyList();
            }
            collectionConcat = org.jetbrains.kotlin.util.collectionUtils.ScopeUtilsKt.concat(collectionConcat, listEmptyList);
            lexicalScope2 = lexicalScope2.getParent();
        } while (lexicalScope2 != null);
        return collectionConcat == null ? SetsKt.emptySet() : collectionConcat;
    }

    public static /* synthetic */ Collection collectDescriptorsFiltered$default(HierarchicalScope hierarchicalScope, DescriptorKindFilter descriptorKindFilter, Function1 function1, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            descriptorKindFilter = DescriptorKindFilter.ALL;
        }
        if ((i & 2) != 0) {
            function1 = MemberScope.INSTANCE.getALL_NAME_FILTER();
        }
        if ((i & 4) != 0) {
            z = false;
        }
        return collectDescriptorsFiltered(hierarchicalScope, descriptorKindFilter, function1, z);
    }

    public static final Collection<DeclarationDescriptor> collectDescriptorsFiltered(HierarchicalScope hierarchicalScope, DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter, boolean z) {
        Collection<DeclarationDescriptor> contributedDescriptors;
        Intrinsics.checkNotNullParameter(hierarchicalScope, "<this>");
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        if (kindFilter.getKindMask() == 0) {
            return CollectionsKt.emptyList();
        }
        Set setConcat = null;
        do {
            if (hierarchicalScope instanceof ImportingScope) {
                contributedDescriptors = ((ImportingScope) hierarchicalScope).getContributedDescriptors(kindFilter, nameFilter, z);
            } else {
                contributedDescriptors = hierarchicalScope.getContributedDescriptors(kindFilter, nameFilter);
            }
            setConcat = org.jetbrains.kotlin.util.collectionUtils.ScopeUtilsKt.concat(setConcat, contributedDescriptors);
            hierarchicalScope = hierarchicalScope.getParent();
        } while (hierarchicalScope != null);
        if (setConcat == null) {
            setConcat = SetsKt.emptySet();
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : setConcat) {
            DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) obj;
            if (kindFilter.accepts(declarationDescriptor)) {
                Name name2 = declarationDescriptor.getShortName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                if (nameFilter.invoke(name2).booleanValue()) {
                    arrayList.add(obj);
                }
            }
        }
        return arrayList;
    }

    @Deprecated(message = "Use getContributedProperties instead")
    public static final VariableDescriptor findLocalVariable(LexicalScope lexicalScope, Name name2) {
        Intrinsics.checkNotNullParameter(lexicalScope, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        LexicalScope lexicalScope2 = lexicalScope;
        do {
            LexicalScope delegate = lexicalScope2 instanceof LexicalScopeWrapper ? ((LexicalScopeWrapper) lexicalScope2).getDelegate() : lexicalScope2;
            VariableDescriptor variableDescriptor = ((delegate instanceof ImportingScope) || (delegate instanceof LexicalChainedScope)) ? null : (VariableDescriptor) CollectionsKt.singleOrNull(delegate.getContributedVariables(name2, NoLookupLocation.WHEN_GET_LOCAL_VARIABLE));
            if (variableDescriptor != null) {
                return variableDescriptor;
            }
            lexicalScope2 = lexicalScope2.getParent();
        } while (lexicalScope2 != null);
        return null;
    }

    public static final ClassifierDescriptor findClassifier(HierarchicalScope hierarchicalScope, Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(hierarchicalScope, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        do {
            ClassifierDescriptor contributedClassifier = hierarchicalScope.mo7465getContributedClassifier(name2, location);
            if (contributedClassifier != null) {
                return contributedClassifier;
            }
            hierarchicalScope = hierarchicalScope.getParent();
        } while (hierarchicalScope != null);
        return null;
    }

    public static final boolean canBeResolvedWithoutDeprecation(DeclarationDescriptor declarationDescriptor, HierarchicalScope scopeForResolution, LookupLocation location) {
        Boolean boolValueOf;
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(scopeForResolution, "scopeForResolution");
        Intrinsics.checkNotNullParameter(location, "location");
        Iterator<HierarchicalScope> it = getParentsWithSelf(scopeForResolution).iterator();
        do {
            boolean z = false;
            if (!it.hasNext()) {
                return false;
            }
            HierarchicalScope next = it.next();
            boolValueOf = null;
            if (declarationDescriptor instanceof ClassifierDescriptor) {
                Name name2 = ((ClassifierDescriptor) declarationDescriptor).getShortName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                DescriptorWithDeprecation<ClassifierDescriptor> contributedClassifierIncludeDeprecated = next.getContributedClassifierIncludeDeprecated(name2, location);
                if (contributedClassifierIncludeDeprecated != null) {
                    if (Intrinsics.areEqual(contributedClassifierIncludeDeprecated.getDescriptor(), declarationDescriptor) && !contributedClassifierIncludeDeprecated.isDeprecated()) {
                        z = true;
                    }
                    boolValueOf = Boolean.valueOf(z);
                }
            } else if (declarationDescriptor instanceof VariableDescriptor) {
                ImportingScope importingScope = next instanceof ImportingScope ? (ImportingScope) next : null;
                if (importingScope != null) {
                    Name name3 = ((VariableDescriptor) declarationDescriptor).getShortName();
                    Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
                    Collection<? extends VariableDescriptor> contributedVariables = importingScope.getContributedVariables(name3, location);
                    if (contributedVariables != null) {
                        Collection<? extends VariableDescriptor> collection = contributedVariables;
                        if (!collection.isEmpty()) {
                            Iterator<T> it2 = collection.iterator();
                            while (it2.hasNext()) {
                                if (Intrinsics.areEqual((VariableDescriptor) it2.next(), declarationDescriptor)) {
                                    z = true;
                                    break;
                                }
                            }
                        }
                        boolValueOf = Boolean.valueOf(z);
                    }
                }
            } else if (declarationDescriptor instanceof FunctionDescriptor) {
                ImportingScope importingScope2 = next instanceof ImportingScope ? (ImportingScope) next : null;
                if (importingScope2 != null) {
                    Name name4 = ((FunctionDescriptor) declarationDescriptor).getShortName();
                    Intrinsics.checkNotNullExpressionValue(name4, "getName(...)");
                    Collection<? extends FunctionDescriptor> contributedFunctions = importingScope2.getContributedFunctions(name4, location);
                    if (contributedFunctions != null) {
                        Collection<? extends FunctionDescriptor> collection2 = contributedFunctions;
                        if (!collection2.isEmpty()) {
                            Iterator<T> it3 = collection2.iterator();
                            while (it3.hasNext()) {
                                if (Intrinsics.areEqual((FunctionDescriptor) it3.next(), declarationDescriptor)) {
                                    z = true;
                                    break;
                                }
                            }
                        }
                        boolValueOf = Boolean.valueOf(z);
                    }
                }
            }
        } while (!Intrinsics.areEqual((Object) boolValueOf, (Object) true));
        return true;
    }

    public static final DescriptorWithDeprecation<ClassifierDescriptor> findFirstClassifierWithDeprecationStatus(HierarchicalScope hierarchicalScope, Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(hierarchicalScope, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        do {
            DescriptorWithDeprecation<ClassifierDescriptor> contributedClassifierIncludeDeprecated = hierarchicalScope.getContributedClassifierIncludeDeprecated(name2, location);
            if (contributedClassifierIncludeDeprecated != null) {
                return contributedClassifierIncludeDeprecated;
            }
            hierarchicalScope = hierarchicalScope.getParent();
        } while (hierarchicalScope != null);
        return null;
    }

    public static final Collection<VariableDescriptor> collectVariables(HierarchicalScope hierarchicalScope, Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(hierarchicalScope, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        Collection<VariableDescriptor> collectionConcat = null;
        do {
            collectionConcat = org.jetbrains.kotlin.util.collectionUtils.ScopeUtilsKt.concat(collectionConcat, hierarchicalScope.getContributedVariables(name2, location));
            hierarchicalScope = hierarchicalScope.getParent();
        } while (hierarchicalScope != null);
        return collectionConcat == null ? SetsKt.emptySet() : collectionConcat;
    }

    public static final Collection<FunctionDescriptor> collectFunctions(HierarchicalScope hierarchicalScope, Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(hierarchicalScope, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        Collection<FunctionDescriptor> collectionConcat = null;
        do {
            collectionConcat = org.jetbrains.kotlin.util.collectionUtils.ScopeUtilsKt.concat(collectionConcat, hierarchicalScope.getContributedFunctions(name2, location));
            hierarchicalScope = hierarchicalScope.getParent();
        } while (hierarchicalScope != null);
        return collectionConcat == null ? SetsKt.emptySet() : collectionConcat;
    }

    public static /* synthetic */ VariableDescriptor findVariable$default(HierarchicalScope hierarchicalScope, Name name2, LookupLocation lookupLocation, Function1 function1, int i, Object obj) {
        if ((i & 4) != 0) {
            function1 = new Function1<VariableDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.resolve.scopes.utils.ScopeUtilsKt.findVariable.1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(VariableDescriptor it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return true;
                }
            };
        }
        return findVariable(hierarchicalScope, name2, lookupLocation, function1);
    }

    public static final VariableDescriptor findVariable(HierarchicalScope hierarchicalScope, Name name2, LookupLocation location, Function1<? super VariableDescriptor, Boolean> predicate) {
        Object next;
        Intrinsics.checkNotNullParameter(hierarchicalScope, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        do {
            Iterator<T> it = hierarchicalScope.getContributedVariables(name2, location).iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (!predicate.invoke(next).booleanValue());
            VariableDescriptor variableDescriptor = (VariableDescriptor) next;
            if (variableDescriptor != null) {
                return variableDescriptor;
            }
            hierarchicalScope = hierarchicalScope.getParent();
        } while (hierarchicalScope != null);
        return null;
    }

    public static /* synthetic */ FunctionDescriptor findFunction$default(HierarchicalScope hierarchicalScope, Name name2, LookupLocation lookupLocation, Function1 function1, int i, Object obj) {
        if ((i & 4) != 0) {
            function1 = new Function1<FunctionDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.resolve.scopes.utils.ScopeUtilsKt.findFunction.1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(FunctionDescriptor it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return true;
                }
            };
        }
        return findFunction(hierarchicalScope, name2, lookupLocation, function1);
    }

    public static final FunctionDescriptor findFunction(HierarchicalScope hierarchicalScope, Name name2, LookupLocation location, Function1<? super FunctionDescriptor, Boolean> predicate) {
        Object next;
        Intrinsics.checkNotNullParameter(hierarchicalScope, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        do {
            Iterator<T> it = hierarchicalScope.getContributedFunctions(name2, location).iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (!predicate.invoke(next).booleanValue());
            FunctionDescriptor functionDescriptor = (FunctionDescriptor) next;
            if (functionDescriptor != null) {
                return functionDescriptor;
            }
            hierarchicalScope = hierarchicalScope.getParent();
        } while (hierarchicalScope != null);
        return null;
    }

    public static final HierarchicalScope takeSnapshot(HierarchicalScope hierarchicalScope) {
        Intrinsics.checkNotNullParameter(hierarchicalScope, "<this>");
        return hierarchicalScope instanceof LexicalWritableScope ? ((LexicalWritableScope) hierarchicalScope).takeSnapshot() : hierarchicalScope;
    }

    public static /* synthetic */ ImportingScope memberScopeAsImportingScope$default(MemberScope memberScope, ImportingScope importingScope, int i, Object obj) {
        if ((i & 1) != 0) {
            importingScope = null;
        }
        return memberScopeAsImportingScope(memberScope, importingScope);
    }

    public static final ImportingScope memberScopeAsImportingScope(MemberScope memberScope, ImportingScope importingScope) {
        Intrinsics.checkNotNullParameter(memberScope, "<this>");
        return new MemberScopeToImportingScopeAdapter(importingScope, memberScope);
    }

    public static final void processForMeAndParent(HierarchicalScope hierarchicalScope, Function1<? super HierarchicalScope, Unit> process) {
        Intrinsics.checkNotNullParameter(hierarchicalScope, "<this>");
        Intrinsics.checkNotNullParameter(process, "process");
        do {
            process.invoke(hierarchicalScope);
            hierarchicalScope = hierarchicalScope.getParent();
        } while (hierarchicalScope != null);
    }

    public static final <T> Collection<T> collectAllFromMeAndParent(HierarchicalScope hierarchicalScope, Function1<? super HierarchicalScope, ? extends Collection<? extends T>> collect) {
        Intrinsics.checkNotNullParameter(hierarchicalScope, "<this>");
        Intrinsics.checkNotNullParameter(collect, "collect");
        Collection<T> collectionConcat = null;
        do {
            collectionConcat = org.jetbrains.kotlin.util.collectionUtils.ScopeUtilsKt.concat(collectionConcat, collect.invoke(hierarchicalScope));
            hierarchicalScope = hierarchicalScope.getParent();
        } while (hierarchicalScope != null);
        return collectionConcat == null ? SetsKt.emptySet() : collectionConcat;
    }

    public static final <T> T findFirstFromMeAndParent(HierarchicalScope hierarchicalScope, Function1<? super HierarchicalScope, ? extends T> fetch) {
        Intrinsics.checkNotNullParameter(hierarchicalScope, "<this>");
        Intrinsics.checkNotNullParameter(fetch, "fetch");
        do {
            T tInvoke = fetch.invoke(hierarchicalScope);
            if (tInvoke != null) {
                return tInvoke;
            }
            hierarchicalScope = hierarchicalScope.getParent();
        } while (hierarchicalScope != null);
        return null;
    }

    public static final <T> Collection<T> collectAllFromImportingScopes(HierarchicalScope hierarchicalScope, Function1<? super ImportingScope, ? extends Collection<? extends T>> collect) {
        Intrinsics.checkNotNullParameter(hierarchicalScope, "<this>");
        Intrinsics.checkNotNullParameter(collect, "collect");
        Collection<T> collectionConcat = null;
        do {
            collectionConcat = org.jetbrains.kotlin.util.collectionUtils.ScopeUtilsKt.concat(collectionConcat, hierarchicalScope instanceof ImportingScope ? collect.invoke(hierarchicalScope) : CollectionsKt.emptyList());
            hierarchicalScope = hierarchicalScope.getParent();
        } while (hierarchicalScope != null);
        return collectionConcat == null ? SetsKt.emptySet() : collectionConcat;
    }

    public static final <T> T findFirstFromImportingScopes(HierarchicalScope hierarchicalScope, Function1<? super ImportingScope, ? extends T> fetch) {
        Intrinsics.checkNotNullParameter(hierarchicalScope, "<this>");
        Intrinsics.checkNotNullParameter(fetch, "fetch");
        do {
            T tInvoke = hierarchicalScope instanceof ImportingScope ? fetch.invoke(hierarchicalScope) : null;
            if (tInvoke != null) {
                return tInvoke;
            }
            hierarchicalScope = hierarchicalScope.getParent();
        } while (hierarchicalScope != null);
        return null;
    }

    public static final LexicalScope addImportingScopes(LexicalScope lexicalScope, List<? extends ImportingScope> importScopes) {
        Intrinsics.checkNotNullParameter(lexicalScope, "<this>");
        Intrinsics.checkNotNullParameter(importScopes, "importScopes");
        HierarchicalScope hierarchicalScope = null;
        boolean z = false;
        for (HierarchicalScope hierarchicalScope2 : getParentsWithSelf(lexicalScope)) {
            if (hierarchicalScope2 instanceof LexicalScope) {
                z = true;
                hierarchicalScope = hierarchicalScope2;
            }
        }
        if (z) {
            HierarchicalScope hierarchicalScope3 = hierarchicalScope.getParent();
            Intrinsics.checkNotNull(hierarchicalScope3, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.scopes.ImportingScope");
            return replaceImportingScopes(lexicalScope, chainImportingScopes(importScopes, (ImportingScope) hierarchicalScope3));
        }
        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
    }

    public static final LexicalScope addImportingScope(LexicalScope lexicalScope, ImportingScope importScope) {
        Intrinsics.checkNotNullParameter(lexicalScope, "<this>");
        Intrinsics.checkNotNullParameter(importScope, "importScope");
        return addImportingScopes(lexicalScope, CollectionsKt.listOf(importScope));
    }

    public static final ImportingScope withParent(final ImportingScope importingScope, final ImportingScope importingScope2) {
        Intrinsics.checkNotNullParameter(importingScope, "<this>");
        return new ImportingScope() { // from class: org.jetbrains.kotlin.resolve.scopes.utils.ScopeUtilsKt.withParent.1
            @Override // org.jetbrains.kotlin.resolve.scopes.ImportingScope
            public Set<Name> computeImportedNames() {
                return importingScope.computeImportedNames();
            }

            @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
            public boolean definitelyDoesNotContainName(Name name2) {
                Intrinsics.checkNotNullParameter(name2, "name");
                return importingScope.definitelyDoesNotContainName(name2);
            }

            @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
            /* JADX INFO: renamed from: getContributedClassifier */
            public ClassifierDescriptor mo7465getContributedClassifier(Name name2, LookupLocation location) {
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(location, "location");
                return importingScope.mo7465getContributedClassifier(name2, location);
            }

            @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
            public DescriptorWithDeprecation<ClassifierDescriptor> getContributedClassifierIncludeDeprecated(Name name2, LookupLocation location) {
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(location, "location");
                return importingScope.getContributedClassifierIncludeDeprecated(name2, location);
            }

            @Override // org.jetbrains.kotlin.resolve.scopes.ImportingScope, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
            public Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter) {
                Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
                Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
                return importingScope.getContributedDescriptors(kindFilter, nameFilter);
            }

            @Override // org.jetbrains.kotlin.resolve.scopes.ImportingScope
            public Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter, boolean changeNamesForAliased) {
                Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
                Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
                return importingScope.getContributedDescriptors(kindFilter, nameFilter, changeNamesForAliased);
            }

            @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
            public Collection<? extends FunctionDescriptor> getContributedFunctions(Name name2, LookupLocation location) {
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(location, "location");
                return importingScope.getContributedFunctions(name2, location);
            }

            @Override // org.jetbrains.kotlin.resolve.scopes.ImportingScope
            /* JADX INFO: renamed from: getContributedPackage */
            public PackageViewDescriptor mo7443getContributedPackage(Name name2) {
                Intrinsics.checkNotNullParameter(name2, "name");
                return importingScope.mo7443getContributedPackage(name2);
            }

            @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
            public Collection<? extends VariableDescriptor> getContributedVariables(Name name2, LookupLocation location) {
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(location, "location");
                return importingScope.getContributedVariables(name2, location);
            }

            @Override // org.jetbrains.kotlin.resolve.scopes.HierarchicalScope
            public void printStructure(Printer p) {
                Intrinsics.checkNotNullParameter(p, "p");
                importingScope.printStructure(p);
            }

            @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
            /* JADX INFO: renamed from: recordLookup */
            public void mo7479recordLookup(Name name2, LookupLocation location) {
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(location, "location");
                importingScope.mo7479recordLookup(name2, location);
            }

            @Override // org.jetbrains.kotlin.resolve.scopes.HierarchicalScope
            public ImportingScope getParent() {
                return importingScope2;
            }
        };
    }

    public static final LexicalScope replaceImportingScopes(LexicalScope lexicalScope, ImportingScope importingScope) {
        Intrinsics.checkNotNullParameter(lexicalScope, "<this>");
        if (importingScope == null) {
            importingScope = ImportingScope.Empty.INSTANCE;
        }
        if (lexicalScope instanceof LexicalScopeWrapper) {
            return new LexicalScopeWrapper(((LexicalScopeWrapper) lexicalScope).getDelegate(), importingScope);
        }
        return new LexicalScopeWrapper(lexicalScope, importingScope);
    }

    public static final LexicalScope createScopeForDestructuring(LexicalScope lexicalScope, ReceiverParameterDescriptor receiverParameterDescriptor) {
        Intrinsics.checkNotNullParameter(lexicalScope, "<this>");
        return new LexicalScopeImpl(lexicalScope.getParent(), lexicalScope.getOwnerDescriptor(), lexicalScope.getIsOwnerDescriptorAccessibleByLabel(), receiverParameterDescriptor, CollectionsKt.emptyList(), LexicalScopeKind.FUNCTION_HEADER_FOR_DESTRUCTURING, null, null, 192, null);
    }

    public static /* synthetic */ ImportingScope chainImportingScopes$default(List list, ImportingScope importingScope, int i, Object obj) {
        if ((i & 2) != 0) {
            importingScope = null;
        }
        return chainImportingScopes(list, importingScope);
    }

    public static final ImportingScope chainImportingScopes(List<? extends ImportingScope> scopes, ImportingScope importingScope) {
        Intrinsics.checkNotNullParameter(scopes, "scopes");
        for (ImportingScope importingScope2 : CollectionsKt.asReversed(scopes)) {
            importingScope2.getParent();
            importingScope = withParent(importingScope2, importingScope);
        }
        return importingScope;
    }

    public static final PackageViewDescriptor findPackage(HierarchicalScope hierarchicalScope, Name name2) {
        Intrinsics.checkNotNullParameter(hierarchicalScope, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        do {
            PackageViewDescriptor packageViewDescriptorMo7443getContributedPackage = hierarchicalScope instanceof ImportingScope ? ((ImportingScope) hierarchicalScope).mo7443getContributedPackage(name2) : null;
            if (packageViewDescriptorMo7443getContributedPackage != null) {
                return packageViewDescriptorMo7443getContributedPackage;
            }
            hierarchicalScope = hierarchicalScope.getParent();
        } while (hierarchicalScope != null);
        return null;
    }
}
