package org.jetbrains.kotlin.types.expressions.unqualifiedSuper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtDotQualifiedExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtSuperExpression;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: unqualifiedSuper.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000X\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u001a\u001e\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002\u001a\u001e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002\u001a\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\rH\u0002\u001a\u0010\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u0016H\u0002\u001a\u0018\u0010\u0017\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\u000b2\u0006\u0010\u0019\u001a\u00020\tH\u0002\u001a\u001c\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u00162\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000b0\b\u001a$\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u000b0\b2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000b0\b2\u0006\u0010\u0013\u001a\u00020\rH\u0002\u001aK\u0010\u001d\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\b\u0012\u0004\u0012\u00020\u00100\u001e2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000b0\b2\u0006\u0010\u001f\u001a\u00020\u00102\u0018\u0010 \u001a\u0014\u0012\u0004\u0012\u00020\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0!H\u0082\b\u001a$\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u000b0\b2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000b0\b2\u0006\u0010#\u001a\u00020\rH\u0002\u001a8\u0010$\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\b\u0012\u0004\u0012\u00020\u00100\u001e2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000b0\b2\u0006\u0010\u0013\u001a\u00020\r2\u0006\u0010%\u001a\u00020\u000bH\u0002\u001a6\u0010&\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\b\u0012\u0004\u0012\u00020\u00100\u001e2\u0006\u0010\u0015\u001a\u00020\u00162\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000b0\b2\u0006\u0010%\u001a\u00020\u000b\u001a\f\u0010'\u001a\u00020\u0010*\u00020\u000bH\u0002\"*\u0010\u0000\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006("}, d2 = {"ARITY_OF_METHODS_OF_ANY", "Ljava/util/HashMap;", "", "", "Lkotlin/collections/HashMap;", "LOOKUP_LOCATION", "Lorg/jetbrains/kotlin/incremental/components/NoLookupLocation;", "getFunctionMembers", "", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "name", "Lorg/jetbrains/kotlin/name/Name;", "getPropertyMembers", "isCallingMethodOfAny", "", "callExpression", "Lorg/jetbrains/kotlin/psi/KtCallExpression;", "calleeName", "isCallingMethodOfAnyWithSuper", "superExpression", "Lorg/jetbrains/kotlin/psi/KtSuperExpression;", "isConcreteMember", "supertype", "memberDescriptor", "isPossiblyAmbiguousUnqualifiedSuper", "supertypes", "resolveSupertypesByCalleeName", "resolveSupertypesByMembers", "Lkotlin/Pair;", "allowNonConcreteInterfaceMembers", "getMembers", "Lkotlin/Function1;", "resolveSupertypesByPropertyName", "propertyName", "resolveSupertypesForMethodOfAny", SchemaSymbols.ATTVAL_ANYTYPE, "resolveUnqualifiedSuperFromExpressionContext", "isInterface", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class UnqualifiedSuperKt {
    private static final HashMap<String, Integer> ARITY_OF_METHODS_OF_ANY = MapsKt.hashMapOf(TuplesKt.to("hashCode", 0), TuplesKt.to(Namer.EQUALS_METHOD_NAME, 1), TuplesKt.to("toString", 0));
    private static final NoLookupLocation LOOKUP_LOCATION = NoLookupLocation.WHEN_GET_SUPER_MEMBERS;

    public static final Pair<Collection<KotlinType>, Boolean> resolveUnqualifiedSuperFromExpressionContext(KtSuperExpression superExpression, Collection<? extends KotlinType> supertypes, KotlinType anyType) {
        Intrinsics.checkNotNullParameter(superExpression, "superExpression");
        Intrinsics.checkNotNullParameter(supertypes, "supertypes");
        Intrinsics.checkNotNullParameter(anyType, "anyType");
        PsiElement parent = superExpression.getLightParent();
        if (parent instanceof KtDotQualifiedExpression) {
            KtExpression selectorExpression = ((KtDotQualifiedExpression) parent).getSelectorExpression();
            if (selectorExpression instanceof KtCallExpression) {
                KtCallExpression ktCallExpression = (KtCallExpression) selectorExpression;
                KtExpression calleeExpression = ktCallExpression.getCalleeExpression();
                if (calleeExpression instanceof KtSimpleNameExpression) {
                    Name referencedNameAsName = ((KtSimpleNameExpression) calleeExpression).getReferencedNameAsName();
                    if (isCallingMethodOfAny(ktCallExpression, referencedNameAsName)) {
                        return resolveSupertypesForMethodOfAny(supertypes, referencedNameAsName, anyType);
                    }
                    return TuplesKt.to(resolveSupertypesByCalleeName(supertypes, referencedNameAsName), false);
                }
            } else if (selectorExpression instanceof KtSimpleNameExpression) {
                return TuplesKt.to(resolveSupertypesByPropertyName(supertypes, ((KtSimpleNameExpression) selectorExpression).getReferencedNameAsName()), false);
            }
        }
        return TuplesKt.to(CollectionsKt.emptyList(), false);
    }

    private static final boolean isCallingMethodOfAny(KtCallExpression ktCallExpression, Name name2) {
        HashMap<String, Integer> map = ARITY_OF_METHODS_OF_ANY;
        String strAsString = name2.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        Integer num = map.get(strAsString);
        if (num == null) {
            num = -1;
        }
        return num.intValue() == ktCallExpression.getValueArguments().size();
    }

    public static final boolean isPossiblyAmbiguousUnqualifiedSuper(KtSuperExpression superExpression, Collection<? extends KotlinType> supertypes) {
        Intrinsics.checkNotNullParameter(superExpression, "superExpression");
        Intrinsics.checkNotNullParameter(supertypes, "supertypes");
        if (supertypes.size() <= 1) {
            return supertypes.size() == 1 && isInterface((KotlinType) CollectionsKt.single(supertypes)) && isCallingMethodOfAnyWithSuper(superExpression);
        }
        return true;
    }

    private static final boolean isCallingMethodOfAnyWithSuper(KtSuperExpression ktSuperExpression) {
        PsiElement parent = ktSuperExpression.getLightParent();
        if (!(parent instanceof KtDotQualifiedExpression)) {
            return false;
        }
        KtExpression selectorExpression = ((KtDotQualifiedExpression) parent).getSelectorExpression();
        if (!(selectorExpression instanceof KtCallExpression)) {
            return false;
        }
        KtCallExpression ktCallExpression = (KtCallExpression) selectorExpression;
        KtExpression calleeExpression = ktCallExpression.getCalleeExpression();
        if (calleeExpression instanceof KtSimpleNameExpression) {
            return isCallingMethodOfAny(ktCallExpression, ((KtSimpleNameExpression) calleeExpression).getReferencedNameAsName());
        }
        return false;
    }

    private static final boolean isInterface(KotlinType kotlinType) {
        ClassDescriptor classDescriptor = TypeUtils.getClassDescriptor(kotlinType);
        return (classDescriptor != null ? classDescriptor.getKind() : null) == ClassKind.INTERFACE;
    }

    private static final Collection<CallableMemberDescriptor> getFunctionMembers(KotlinType kotlinType, Name name2) {
        return kotlinType.getMemberScope().getContributedFunctions(name2, LOOKUP_LOCATION);
    }

    private static final Collection<CallableMemberDescriptor> getPropertyMembers(KotlinType kotlinType, Name name2) {
        Collection<? extends PropertyDescriptor> contributedVariables = kotlinType.getMemberScope().getContributedVariables(name2, LOOKUP_LOCATION);
        SmartList smartList = new SmartList();
        Iterator<T> it = contributedVariables.iterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (next != null ? next instanceof CallableMemberDescriptor : true) {
                smartList.add(next);
            }
        }
        return smartList;
    }

    private static final boolean isConcreteMember(KotlinType kotlinType, CallableMemberDescriptor callableMemberDescriptor) {
        KotlinType type;
        ClassDescriptor classDescriptor;
        if (callableMemberDescriptor.getModality() == Modality.ABSTRACT) {
            return false;
        }
        ClassDescriptor classDescriptor2 = TypeUtils.getClassDescriptor(kotlinType);
        CallableMemberDescriptor.Kind kind = callableMemberDescriptor.getKind();
        Intrinsics.checkNotNullExpressionValue(kind, "getKind(...)");
        if ((classDescriptor2 != null ? classDescriptor2.getKind() : null) != ClassKind.INTERFACE || kind != CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
            return true;
        }
        ReceiverParameterDescriptor dispatchReceiverParameter = callableMemberDescriptor.mo6753getDispatchReceiverParameter();
        if (dispatchReceiverParameter == null || (type = dispatchReceiverParameter.getType()) == null || (classDescriptor = TypeUtils.getClassDescriptor(type)) == null) {
            return false;
        }
        return !KotlinBuiltIns.isAny(classDescriptor);
    }

    private static final Pair<Collection<KotlinType>, Boolean> resolveSupertypesForMethodOfAny(Collection<? extends KotlinType> collection, Name name2, KotlinType kotlinType) {
        Pair pair;
        ClassKind kind;
        KotlinType type;
        SmartList smartList = new SmartList();
        SmartList smartList2 = new SmartList();
        for (KotlinType kotlinType2 : collection) {
            Collection<CallableMemberDescriptor> functionMembers = getFunctionMembers(kotlinType2, name2);
            if (!functionMembers.isEmpty()) {
                Collection<CallableMemberDescriptor> collection2 = functionMembers;
                boolean z = collection2 instanceof Collection;
                if (!z || !collection2.isEmpty()) {
                    Iterator<T> it = collection2.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (isConcreteMember(kotlinType2, (CallableMemberDescriptor) it.next())) {
                                smartList.add(kotlinType2);
                            }
                        }
                    }
                }
                if (!z || !collection2.isEmpty()) {
                    Iterator<T> it2 = collection2.iterator();
                    while (it2.hasNext()) {
                        ReceiverParameterDescriptor dispatchReceiverParameter = ((CallableMemberDescriptor) it2.next()).mo6753getDispatchReceiverParameter();
                        if (dispatchReceiverParameter != null && (type = dispatchReceiverParameter.getType()) != null) {
                            Intrinsics.checkNotNull(type);
                            if (!TypeUtilsKt.isAny(type)) {
                                smartList2.add(kotlinType2);
                                break;
                            }
                        }
                    }
                }
            }
        }
        CollectionsKt.removeAll((List) smartList, (Function1) new UnqualifiedSuperKt$resolveSupertypesByMembers$3(smartList2));
        if (!smartList.isEmpty()) {
            pair = TuplesKt.to(smartList, false);
        } else {
            ArrayList arrayList = new ArrayList();
            for (Object obj : smartList2) {
                ClassDescriptor classDescriptor = TypeUtils.getClassDescriptor((KotlinType) obj);
                if (classDescriptor != null && (kind = classDescriptor.getKind()) != null) {
                    Intrinsics.checkNotNull(kind);
                    if (kind == ClassKind.CLASS) {
                        arrayList.add(obj);
                    }
                }
            }
            pair = TuplesKt.to(arrayList, true);
        }
        List listListOf = (Collection) pair.component1();
        boolean zBooleanValue = ((Boolean) pair.component2()).booleanValue();
        if (listListOf.isEmpty()) {
            listListOf = CollectionsKt.listOf(kotlinType);
        }
        return TuplesKt.to(listListOf, Boolean.valueOf(zBooleanValue));
    }

    private static final Collection<KotlinType> resolveSupertypesByCalleeName(Collection<? extends KotlinType> collection, Name name2) {
        Pair pair;
        KotlinType type;
        SmartList smartList = new SmartList();
        SmartList smartList2 = new SmartList();
        for (KotlinType kotlinType : collection) {
            List listPlus = CollectionsKt.plus((Collection) getFunctionMembers(kotlinType, name2), (Iterable) getPropertyMembers(kotlinType, name2));
            if (!listPlus.isEmpty()) {
                List list = listPlus;
                boolean z = list instanceof Collection;
                if (!z || !list.isEmpty()) {
                    Iterator it = list.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (isConcreteMember(kotlinType, (CallableMemberDescriptor) it.next())) {
                                smartList.add(kotlinType);
                            }
                        }
                    }
                }
                if (!z || !list.isEmpty()) {
                    Iterator it2 = list.iterator();
                    while (it2.hasNext()) {
                        ReceiverParameterDescriptor dispatchReceiverParameter = ((CallableMemberDescriptor) it2.next()).mo6753getDispatchReceiverParameter();
                        if (dispatchReceiverParameter != null && (type = dispatchReceiverParameter.getType()) != null) {
                            Intrinsics.checkNotNull(type);
                            if (!TypeUtilsKt.isAny(type)) {
                                smartList2.add(kotlinType);
                                break;
                            }
                        }
                    }
                }
            }
        }
        CollectionsKt.removeAll((List) smartList, (Function1) new UnqualifiedSuperKt$resolveSupertypesByMembers$3(smartList2));
        if (!smartList.isEmpty()) {
            pair = TuplesKt.to(smartList, false);
        } else {
            pair = TuplesKt.to(smartList2, false);
        }
        return (Collection) pair.getFirst();
    }

    private static final Collection<KotlinType> resolveSupertypesByPropertyName(Collection<? extends KotlinType> collection, Name name2) {
        Pair pair;
        KotlinType type;
        SmartList smartList = new SmartList();
        SmartList smartList2 = new SmartList();
        for (KotlinType kotlinType : collection) {
            Collection<CallableMemberDescriptor> propertyMembers = getPropertyMembers(kotlinType, name2);
            if (!propertyMembers.isEmpty()) {
                Collection<CallableMemberDescriptor> collection2 = propertyMembers;
                boolean z = collection2 instanceof Collection;
                if (!z || !collection2.isEmpty()) {
                    Iterator<T> it = collection2.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (isConcreteMember(kotlinType, (CallableMemberDescriptor) it.next())) {
                                smartList.add(kotlinType);
                            }
                        }
                    }
                }
                if (!z || !collection2.isEmpty()) {
                    Iterator<T> it2 = collection2.iterator();
                    while (it2.hasNext()) {
                        ReceiverParameterDescriptor dispatchReceiverParameter = ((CallableMemberDescriptor) it2.next()).mo6753getDispatchReceiverParameter();
                        if (dispatchReceiverParameter != null && (type = dispatchReceiverParameter.getType()) != null) {
                            Intrinsics.checkNotNull(type);
                            if (!TypeUtilsKt.isAny(type)) {
                                smartList2.add(kotlinType);
                                break;
                            }
                        }
                    }
                }
            }
        }
        CollectionsKt.removeAll((List) smartList, (Function1) new UnqualifiedSuperKt$resolveSupertypesByMembers$3(smartList2));
        if (!smartList.isEmpty()) {
            pair = TuplesKt.to(smartList, false);
        } else {
            pair = TuplesKt.to(smartList2, false);
        }
        return (Collection) pair.getFirst();
    }
}
