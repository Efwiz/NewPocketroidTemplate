package org.jetbrains.kotlin.util;

import java.util.Arrays;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: modifierChecks.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\b\u0000\u0018\u00002\u00020\u0001B8\b\u0016\u0012\u0012\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\"\u00020\u0004\u0012\u001b\b\u0002\u0010\u0005\u001a\u0015\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0006¢\u0006\u0002\b\t¢\u0006\u0002\u0010\nB@\b\u0016\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0012\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\"\u00020\u0004\u0012\u001b\b\u0002\u0010\u0005\u001a\u0015\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0006¢\u0006\u0002\b\t¢\u0006\u0002\u0010\rB@\b\u0016\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0012\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\"\u00020\u0004\u0012\u001b\b\u0002\u0010\u0005\u001a\u0015\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0006¢\u0006\u0002\b\t¢\u0006\u0002\u0010\u0010BF\b\u0016\u0012\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\f0\u0012\u0012\u0012\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\"\u00020\u0004\u0012\u001b\b\u0002\u0010\u0005\u001a\u0015\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0006¢\u0006\u0002\b\t¢\u0006\u0002\u0010\u0013BU\b\u0002\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\u000e\u0010\u0011\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u0012\u0012\u0014\u0010\u0014\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0006\u0012\u0012\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\"\u00020\u0004¢\u0006\u0002\u0010\u0015J\u000e\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u0007J\u000e\u0010$\u001a\u00020%2\u0006\u0010#\u001a\u00020\u0007R\u001f\u0010\u0014\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u001b\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003¢\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b\u0018\u0010\u0019R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\f¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0019\u0010\u0011\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 ¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/util/Checks;", "", "checks", "", "Lorg/jetbrains/kotlin/util/Check;", "additionalChecks", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "", "Lkotlin/ExtensionFunctionType;", "([Lorg/jetbrains/kotlin/util/Check;Lkotlin/jvm/functions/Function1;)V", "name", "Lorg/jetbrains/kotlin/name/Name;", "(Lorg/jetbrains/kotlin/name/Name;[Lorg/jetbrains/kotlin/util/Check;Lkotlin/jvm/functions/Function1;)V", "regex", "Lkotlin/text/Regex;", "(Lkotlin/text/Regex;[Lorg/jetbrains/kotlin/util/Check;Lkotlin/jvm/functions/Function1;)V", "nameList", "", "(Ljava/util/Collection;[Lorg/jetbrains/kotlin/util/Check;Lkotlin/jvm/functions/Function1;)V", "additionalCheck", "(Lorg/jetbrains/kotlin/name/Name;Lkotlin/text/Regex;Ljava/util/Collection;Lkotlin/jvm/functions/Function1;[Lorg/jetbrains/kotlin/util/Check;)V", "getAdditionalCheck", "()Lkotlin/jvm/functions/Function1;", "getChecks", "()[Lorg/jetbrains/kotlin/util/Check;", "[Lorg/jetbrains/kotlin/util/Check;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "getNameList", "()Ljava/util/Collection;", "getRegex", "()Lkotlin/text/Regex;", "checkAll", "Lorg/jetbrains/kotlin/util/CheckResult;", "functionDescriptor", "isApplicable", "", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Checks {
    private final Function1<FunctionDescriptor, String> additionalCheck;
    private final Check[] checks;
    private final Name name;
    private final Collection<Name> nameList;
    private final Regex regex;

    /* JADX WARN: Multi-variable type inference failed */
    private Checks(Name name2, Regex regex, Collection<Name> collection, Function1<? super FunctionDescriptor, String> function1, Check... checkArr) {
        this.name = name2;
        this.regex = regex;
        this.nameList = collection;
        this.additionalCheck = function1;
        this.checks = checkArr;
    }

    public final Name getName() {
        return this.name;
    }

    public final Regex getRegex() {
        return this.regex;
    }

    public final Collection<Name> getNameList() {
        return this.nameList;
    }

    public final Function1<FunctionDescriptor, String> getAdditionalCheck() {
        return this.additionalCheck;
    }

    public final Check[] getChecks() {
        return this.checks;
    }

    public final boolean isApplicable(FunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        if (this.name != null && !Intrinsics.areEqual(functionDescriptor.getName(), this.name)) {
            return false;
        }
        if (this.regex != null) {
            String strAsString = functionDescriptor.getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            if (!this.regex.matches(strAsString)) {
                return false;
            }
        }
        Collection<Name> collection = this.nameList;
        return collection == null || collection.contains(functionDescriptor.getName());
    }

    public final CheckResult checkAll(FunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        for (Check check : this.checks) {
            String strInvoke = check.invoke(functionDescriptor);
            if (strInvoke != null) {
                return new CheckResult.IllegalSignature(strInvoke);
            }
        }
        String strInvoke2 = this.additionalCheck.invoke(functionDescriptor);
        if (strInvoke2 != null) {
            return new CheckResult.IllegalSignature(strInvoke2);
        }
        return CheckResult.SuccessCheck.INSTANCE;
    }

    public /* synthetic */ Checks(Check[] checkArr, AnonymousClass1 anonymousClass1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(checkArr, (i & 2) != 0 ? new Function1() { // from class: org.jetbrains.kotlin.util.Checks.1
            @Override // kotlin.jvm.functions.Function1
            public final Void invoke(FunctionDescriptor functionDescriptor) {
                Intrinsics.checkNotNullParameter(functionDescriptor, "$this$null");
                return null;
            }
        } : anonymousClass1);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public Checks(Check[] checks, Function1<? super FunctionDescriptor, String> additionalChecks) {
        this((Name) null, (Regex) null, (Collection<Name>) null, additionalChecks, (Check[]) Arrays.copyOf(checks, checks.length));
        Intrinsics.checkNotNullParameter(checks, "checks");
        Intrinsics.checkNotNullParameter(additionalChecks, "additionalChecks");
    }

    public /* synthetic */ Checks(Name name2, Check[] checkArr, AnonymousClass2 anonymousClass2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(name2, checkArr, (Function1<? super FunctionDescriptor, String>) ((i & 4) != 0 ? new Function1() { // from class: org.jetbrains.kotlin.util.Checks.2
            @Override // kotlin.jvm.functions.Function1
            public final Void invoke(FunctionDescriptor functionDescriptor) {
                Intrinsics.checkNotNullParameter(functionDescriptor, "$this$null");
                return null;
            }
        } : anonymousClass2));
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public Checks(Name name2, Check[] checks, Function1<? super FunctionDescriptor, String> additionalChecks) {
        this(name2, (Regex) null, (Collection<Name>) null, additionalChecks, (Check[]) Arrays.copyOf(checks, checks.length));
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(checks, "checks");
        Intrinsics.checkNotNullParameter(additionalChecks, "additionalChecks");
    }

    public /* synthetic */ Checks(Regex regex, Check[] checkArr, AnonymousClass3 anonymousClass3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(regex, checkArr, (Function1<? super FunctionDescriptor, String>) ((i & 4) != 0 ? new Function1() { // from class: org.jetbrains.kotlin.util.Checks.3
            @Override // kotlin.jvm.functions.Function1
            public final Void invoke(FunctionDescriptor functionDescriptor) {
                Intrinsics.checkNotNullParameter(functionDescriptor, "$this$null");
                return null;
            }
        } : anonymousClass3));
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public Checks(Regex regex, Check[] checks, Function1<? super FunctionDescriptor, String> additionalChecks) {
        this((Name) null, regex, (Collection<Name>) null, additionalChecks, (Check[]) Arrays.copyOf(checks, checks.length));
        Intrinsics.checkNotNullParameter(regex, "regex");
        Intrinsics.checkNotNullParameter(checks, "checks");
        Intrinsics.checkNotNullParameter(additionalChecks, "additionalChecks");
    }

    public /* synthetic */ Checks(Collection collection, Check[] checkArr, AnonymousClass4 anonymousClass4, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((Collection<Name>) collection, checkArr, (Function1<? super FunctionDescriptor, String>) ((i & 4) != 0 ? new Function1() { // from class: org.jetbrains.kotlin.util.Checks.4
            @Override // kotlin.jvm.functions.Function1
            public final Void invoke(FunctionDescriptor functionDescriptor) {
                Intrinsics.checkNotNullParameter(functionDescriptor, "$this$null");
                return null;
            }
        } : anonymousClass4));
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public Checks(Collection<Name> nameList, Check[] checks, Function1<? super FunctionDescriptor, String> additionalChecks) {
        this((Name) null, (Regex) null, nameList, additionalChecks, (Check[]) Arrays.copyOf(checks, checks.length));
        Intrinsics.checkNotNullParameter(nameList, "nameList");
        Intrinsics.checkNotNullParameter(checks, "checks");
        Intrinsics.checkNotNullParameter(additionalChecks, "additionalChecks");
    }
}
