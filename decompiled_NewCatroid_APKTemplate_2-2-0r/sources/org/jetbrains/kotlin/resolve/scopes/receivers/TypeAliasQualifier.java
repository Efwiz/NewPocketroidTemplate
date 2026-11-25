package org.jetbrains.kotlin.resolve.scopes.receivers;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.ChainedMemberScope;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.utils.Printer;

/* JADX INFO: compiled from: Qualifier.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0001\u0017B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0016\u0010\u000b\u001a\u0004\u0018\u00010\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/receivers/TypeAliasQualifier;", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ClassifierQualifier;", "referenceExpression", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "(Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;)V", "getClassDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "classValueReceiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ClassValueReceiver;", "getClassValueReceiver", "()Lorg/jetbrains/kotlin/resolve/scopes/receivers/ClassValueReceiver;", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "getReferenceExpression", "()Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "staticScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "getStaticScope", "()Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "EnumEntriesScope", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeAliasQualifier implements ClassifierQualifier {
    private final ClassDescriptor classDescriptor;
    private final TypeAliasDescriptor descriptor;
    private final KtSimpleNameExpression referenceExpression;

    public TypeAliasQualifier(KtSimpleNameExpression referenceExpression, TypeAliasDescriptor descriptor, ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(referenceExpression, "referenceExpression");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        this.referenceExpression = referenceExpression;
        this.descriptor = descriptor;
        this.classDescriptor = classDescriptor;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.receivers.Qualifier
    public KtSimpleNameExpression getReferenceExpression() {
        return this.referenceExpression;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.receivers.QualifierReceiver
    public TypeAliasDescriptor getDescriptor() {
        return this.descriptor;
    }

    public final ClassDescriptor getClassDescriptor() {
        return this.classDescriptor;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.receivers.QualifierReceiver
    public ClassValueReceiver getClassValueReceiver() {
        KotlinType classValueType = DescriptorUtilsKt.getClassValueType(this.classDescriptor);
        if (classValueType != null) {
            return new ClassValueReceiver(this, classValueType, null, 4, null);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.receivers.QualifierReceiver
    public MemberScope getStaticScope() {
        if (DescriptorUtils.isEnumClass(this.classDescriptor)) {
            ChainedMemberScope.Companion companion = ChainedMemberScope.INSTANCE;
            String str = "Static scope for typealias " + getDescriptor().getShortName();
            MemberScope staticScope = this.classDescriptor.getStaticScope();
            Intrinsics.checkNotNullExpressionValue(staticScope, "getStaticScope(...)");
            return companion.create(str, staticScope, new EnumEntriesScope());
        }
        MemberScope staticScope2 = this.classDescriptor.getStaticScope();
        Intrinsics.checkNotNullExpressionValue(staticScope2, "getStaticScope(...)");
        return staticScope2;
    }

    /* JADX INFO: compiled from: Qualifier.kt */
    @Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J*\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\r2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00100\u000fH\u0016J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/receivers/TypeAliasQualifier$EnumEntriesScope;", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScopeImpl;", "(Lorg/jetbrains/kotlin/resolve/scopes/receivers/TypeAliasQualifier;)V", "getContributedClassifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "name", "Lorg/jetbrains/kotlin/name/Name;", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "getContributedDescriptors", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "kindFilter", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", "nameFilter", "Lkotlin/Function1;", "", "printScopeStructure", "", "p", "Lorg/jetbrains/kotlin/utils/Printer;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class EnumEntriesScope extends MemberScopeImpl {
        public EnumEntriesScope() {
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
        public Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter) {
            Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
            Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
            Collection<DeclarationDescriptor> contributedDescriptors = TypeAliasQualifier.this.getClassDescriptor().getUnsubstitutedInnerClassesScope().getContributedDescriptors(kindFilter, nameFilter);
            ArrayList arrayList = new ArrayList();
            for (Object obj : contributedDescriptors) {
                if (DescriptorUtils.isEnumEntry((DeclarationDescriptor) obj)) {
                    arrayList.add(obj);
                }
            }
            return arrayList;
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
        /* JADX INFO: renamed from: getContributedClassifier */
        public ClassifierDescriptor mo7465getContributedClassifier(Name name2, LookupLocation location) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(location, "location");
            ClassifierDescriptor contributedClassifier = TypeAliasQualifier.this.getClassDescriptor().getUnsubstitutedInnerClassesScope().mo7465getContributedClassifier(name2, location);
            if (contributedClassifier == null || !DescriptorUtils.isEnumEntry(contributedClassifier)) {
                return null;
            }
            return contributedClassifier;
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope
        /* JADX INFO: renamed from: printScopeStructure */
        public void mo6105printScopeStructure(Printer p) {
            Intrinsics.checkNotNullParameter(p, "p");
            p.println(getClass().getSimpleName(), " {");
            p.pushIndent();
            p.println("descriptor = ", TypeAliasQualifier.this.getDescriptor());
            p.popIndent();
            p.println("}");
        }
    }
}
