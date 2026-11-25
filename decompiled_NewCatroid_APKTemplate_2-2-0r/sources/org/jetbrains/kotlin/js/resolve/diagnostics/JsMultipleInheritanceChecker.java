package org.jetbrains.kotlin.js.resolve.diagnostics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;

/* JADX INFO: compiled from: JsMultipleInheritanceChecker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/js/resolve/diagnostics/JsMultipleInheritanceChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "fqNames", "", "Lorg/jetbrains/kotlin/name/FqNameUnsafe;", "simpleNames", "", "Lorg/jetbrains/kotlin/name/Name;", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsMultipleInheritanceChecker implements DeclarationChecker {
    public static final JsMultipleInheritanceChecker INSTANCE = new JsMultipleInheritanceChecker();
    private static final List<FqNameUnsafe> fqNames;
    private static final Set<Name> simpleNames;

    private JsMultipleInheritanceChecker() {
    }

    static {
        List<FqNameUnsafe> listListOf = CollectionsKt.listOf((Object[]) new FqNameUnsafe[]{new FqNameUnsafe("kotlin.CharSequence.get"), new FqNameUnsafe("kotlin.collections.CharIterator.nextChar")});
        fqNames = listListOf;
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<T> it = listListOf.iterator();
        while (it.hasNext()) {
            linkedHashSet.add(((FqNameUnsafe) it.next()).shortName());
        }
        simpleNames = linkedHashSet;
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (descriptor instanceof ClassDescriptor) {
            MemberScope unsubstitutedMemberScope = ((ClassDescriptor) descriptor).getUnsubstitutedMemberScope();
            Intrinsics.checkNotNullExpressionValue(unsubstitutedMemberScope, "getUnsubstitutedMemberScope(...)");
            Collection contributedDescriptors$default = ResolutionScope.DefaultImpls.getContributedDescriptors$default(unsubstitutedMemberScope, null, new Function1<Name, Boolean>() { // from class: org.jetbrains.kotlin.js.resolve.diagnostics.JsMultipleInheritanceChecker.check.1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(Name it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(JsMultipleInheritanceChecker.simpleNames.contains(it));
                }
            }, 1, null);
            ArrayList<CallableMemberDescriptor> arrayList = new ArrayList();
            for (Object obj : contributedDescriptors$default) {
                if (obj instanceof CallableMemberDescriptor) {
                    arrayList.add(obj);
                }
            }
            for (CallableMemberDescriptor callableMemberDescriptor : arrayList) {
                if (callableMemberDescriptor.getOverriddenDescriptors().size() > 1) {
                    Collection<? extends CallableMemberDescriptor> overriddenDescriptors = callableMemberDescriptor.getOverriddenDescriptors();
                    Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
                    Collection<? extends CallableMemberDescriptor> collection = overriddenDescriptors;
                    if (!collection.isEmpty()) {
                        for (CallableMemberDescriptor callableMemberDescriptor2 : collection) {
                            List<FqNameUnsafe> list = fqNames;
                            Intrinsics.checkNotNull(callableMemberDescriptor2);
                            if (list.contains(DescriptorUtilsKt.getFqNameUnsafe(callableMemberDescriptor2))) {
                                context.getTrace().report(ErrorsJs.WRONG_MULTIPLE_INHERITANCE.on(declaration, callableMemberDescriptor));
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
}
