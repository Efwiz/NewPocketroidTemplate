package org.jetbrains.kotlin.resolve.scopes;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorWithDeprecation;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.utils.Printer;

/* JADX INFO: compiled from: MemberScope.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u0000 \u00142\u00020\u0001:\u0002\u0014\u0015J\u0010\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003H&J#\u0010\u0005\u001a\r\u0012\t\u0012\u00070\u0007¢\u0006\u0002\b\b0\u00062\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bH&J#\u0010\f\u001a\r\u0012\t\u0012\u00070\r¢\u0006\u0002\b\b0\u00062\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bH&J\u000e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H&J\u000e\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H&J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H&¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "Lorg/jetbrains/kotlin/resolve/scopes/ResolutionScope;", "getClassifierNames", "", "Lorg/jetbrains/kotlin/name/Name;", "getContributedFunctions", "", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "Lkotlin/jvm/JvmWildcard;", "name", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "getContributedVariables", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getFunctionNames", "getVariableNames", "printScopeStructure", "", "p", "Lorg/jetbrains/kotlin/utils/Printer;", "Companion", "Empty", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface MemberScope extends ResolutionScope {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    Set<Name> getClassifierNames();

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    Collection<? extends SimpleFunctionDescriptor> getContributedFunctions(Name name2, LookupLocation location);

    @Override // org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    Collection<? extends PropertyDescriptor> getContributedVariables(Name name2, LookupLocation location);

    Set<Name> getFunctionNames();

    Set<Name> getVariableNames();

    /* JADX INFO: renamed from: printScopeStructure */
    void mo6105printScopeStructure(Printer p);

    /* JADX INFO: compiled from: MemberScope.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static boolean definitelyDoesNotContainName(MemberScope memberScope, Name name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            return ResolutionScope.DefaultImpls.definitelyDoesNotContainName(memberScope, name2);
        }

        public static DescriptorWithDeprecation<ClassifierDescriptor> getContributedClassifierIncludeDeprecated(MemberScope memberScope, Name name2, LookupLocation location) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(location, "location");
            return ResolutionScope.DefaultImpls.getContributedClassifierIncludeDeprecated(memberScope, name2, location);
        }

        public static void recordLookup(MemberScope memberScope, Name name2, LookupLocation location) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(location, "location");
            ResolutionScope.DefaultImpls.recordLookup(memberScope, name2, location);
        }
    }

    /* JADX INFO: compiled from: MemberScope.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u000e\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\bH\u0016J\u000e\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\bH\u0016J\u000e\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\bH\u0016J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/MemberScope$Empty;", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScopeImpl;", "()V", "definitelyDoesNotContainName", "", "name", "Lorg/jetbrains/kotlin/name/Name;", "getClassifierNames", "", "getFunctionNames", "getVariableNames", "printScopeStructure", "", "p", "Lorg/jetbrains/kotlin/utils/Printer;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Empty extends MemberScopeImpl {
        public static final Empty INSTANCE = new Empty();

        @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
        public boolean definitelyDoesNotContainName(Name name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            return true;
        }

        private Empty() {
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope
        /* JADX INFO: renamed from: printScopeStructure */
        public void mo6105printScopeStructure(Printer p) {
            Intrinsics.checkNotNullParameter(p, "p");
            p.println("Empty member scope");
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope
        public Set<Name> getFunctionNames() {
            return SetsKt.emptySet();
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope
        public Set<Name> getVariableNames() {
            return SetsKt.emptySet();
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope
        public Set<Name> getClassifierNames() {
            return SetsKt.emptySet();
        }
    }

    /* JADX INFO: compiled from: MemberScope.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001d\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/MemberScope$Companion;", "", "()V", "ALL_NAME_FILTER", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/name/Name;", "", "getALL_NAME_FILTER", "()Lkotlin/jvm/functions/Function1;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static final Function1<Name, Boolean> ALL_NAME_FILTER = new Function1<Name, Boolean>() { // from class: org.jetbrains.kotlin.resolve.scopes.MemberScope$Companion$ALL_NAME_FILTER$1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Name it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return true;
            }
        };

        private Companion() {
        }

        public final Function1<Name, Boolean> getALL_NAME_FILTER() {
            return ALL_NAME_FILTER;
        }
    }
}
