package org.jetbrains.kotlin.scripting.resolve;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Iterator;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.impl.ClassConstructorDescriptorImpl;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclarationEntry;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.lazy.ResolveSession;
import org.jetbrains.kotlin.resolve.lazy.declarations.ClassMemberDeclarationProvider;
import org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassMemberScope;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: LazyScriptClassMemberScope.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 $2\u00020\u0001:\u0001$B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ:\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\f2\u0006\u0010\u0016\u001a\u00020\u00172\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u001a0\u00192\u0006\u0010\u001b\u001a\u00020\u001cH\u0014J\u001e\u0010\u001d\u001a\u00020\u00132\u0006\u0010\u001e\u001a\u00020\r2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u001f0\fH\u0014J\u001e\u0010 \u001a\u00020\u00132\u0006\u0010\u001e\u001a\u00020\r2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u001f0\fH\u0014J\u000e\u0010!\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0016J\b\u0010\"\u001a\u00020#H\u0014R!\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/scripting/resolve/LazyScriptClassMemberScope;", "Lorg/jetbrains/kotlin/resolve/lazy/descriptors/LazyClassMemberScope;", "resolveSession", "Lorg/jetbrains/kotlin/resolve/lazy/ResolveSession;", "declarationProvider", "Lorg/jetbrains/kotlin/resolve/lazy/declarations/ClassMemberDeclarationProvider;", "scriptDescriptor", "Lorg/jetbrains/kotlin/scripting/resolve/LazyScriptDescriptor;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "(Lorg/jetbrains/kotlin/resolve/lazy/ResolveSession;Lorg/jetbrains/kotlin/resolve/lazy/declarations/ClassMemberDeclarationProvider;Lorg/jetbrains/kotlin/scripting/resolve/LazyScriptDescriptor;Lorg/jetbrains/kotlin/resolve/BindingTrace;)V", "_variableNames", "", "Lorg/jetbrains/kotlin/name/Name;", "get_variableNames", "()Ljava/util/Set;", "_variableNames$delegate", "Lkotlin/Lazy;", "collectDescriptorsFromDestructingDeclaration", "", "result", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "declaration", "Lorg/jetbrains/kotlin/psi/KtDestructuringDeclaration;", "nameFilter", "Lkotlin/Function1;", "", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "createPropertiesFromPrimaryConstructorParameters", "name", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getNonDeclaredProperties", "getVariableNames", "resolvePrimaryConstructor", "Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "Companion", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LazyScriptClassMemberScope extends LazyClassMemberScope {
    public static final String IMPLICIT_RECEIVER_PARAM_NAME_PREFIX = "$$implicitReceiver";
    public static final String IMPORTED_SCRIPT_PARAM_NAME_PREFIX = "$$importedScript";

    /* JADX INFO: renamed from: _variableNames$delegate, reason: from kotlin metadata */
    private final Lazy _variableNames;
    private final LazyScriptDescriptor scriptDescriptor;

    @Override // org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassMemberScope
    protected void createPropertiesFromPrimaryConstructorParameters(Name name2, Set<PropertyDescriptor> result) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(result, "result");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LazyScriptClassMemberScope(ResolveSession resolveSession, ClassMemberDeclarationProvider declarationProvider, LazyScriptDescriptor scriptDescriptor, BindingTrace trace) {
        super(resolveSession, declarationProvider, scriptDescriptor, trace, null, null, 48, null);
        Intrinsics.checkNotNullParameter(resolveSession, "resolveSession");
        Intrinsics.checkNotNullParameter(declarationProvider, "declarationProvider");
        Intrinsics.checkNotNullParameter(scriptDescriptor, "scriptDescriptor");
        Intrinsics.checkNotNullParameter(trace, "trace");
        this.scriptDescriptor = scriptDescriptor;
        this._variableNames = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<Set<Name>>() { // from class: org.jetbrains.kotlin.scripting.resolve.LazyScriptClassMemberScope$_variableNames$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<Name> invoke() {
                Set<Name> variableNames = super/*org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassMemberScope*/.getVariableNames();
                LazyScriptClassMemberScope lazyScriptClassMemberScope = this.this$0;
                Iterator<T> it = lazyScriptClassMemberScope.scriptDescriptor.getScriptProvidedProperties().iterator();
                while (it.hasNext()) {
                    Name name2 = ((PropertyDescriptor) it.next()).getShortName();
                    Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                    variableNames.add(name2);
                }
                Name nameResultFieldName = lazyScriptClassMemberScope.scriptDescriptor.resultFieldName();
                if (nameResultFieldName != null) {
                    variableNames.add(nameResultFieldName);
                }
                return variableNames;
            }
        });
    }

    private final Set<Name> get_variableNames() {
        return (Set) this._variableNames.getValue();
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassMemberScope
    protected ClassConstructorDescriptor resolvePrimaryConstructor() {
        ClassConstructorDescriptorImpl constructor = this.scriptDescriptor.getScriptPrimaryConstructorWithParams$kotlin_scripting_compiler_impl().invoke().getConstructor();
        setDeferredReturnType(constructor);
        return constructor;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassMemberScope, org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope
    public Set<Name> getVariableNames() {
        return get_variableNames();
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassMemberScope, org.jetbrains.kotlin.resolve.lazy.descriptors.AbstractLazyMemberScope
    protected void getNonDeclaredProperties(Name name2, Set<PropertyDescriptor> result) {
        ReplResultPropertyDescriptor resultValue;
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(result, "result");
        super.getNonDeclaredProperties(name2, result);
        if (Intrinsics.areEqual(this.scriptDescriptor.resultFieldName(), name2) && (resultValue = this.scriptDescriptor.getResultValue()) != null) {
            result.add(resultValue);
        }
        for (PropertyDescriptor propertyDescriptor : this.scriptDescriptor.getScriptProvidedProperties()) {
            if (Intrinsics.areEqual(propertyDescriptor.getShortName(), name2)) {
                result.add(propertyDescriptor);
            }
        }
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.descriptors.AbstractLazyMemberScope
    protected void collectDescriptorsFromDestructingDeclaration(Set<DeclarationDescriptor> result, KtDestructuringDeclaration declaration, Function1<? super Name, Boolean> nameFilter, LookupLocation location) {
        Intrinsics.checkNotNullParameter(result, "result");
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        Intrinsics.checkNotNullParameter(location, "location");
        Iterator<KtDestructuringDeclarationEntry> it = declaration.getEntries().iterator();
        while (it.hasNext()) {
            Name nameAsSafeName = it.next().getNameAsSafeName();
            Intrinsics.checkNotNullExpressionValue(nameAsSafeName, "getNameAsSafeName(...)");
            if (nameFilter.invoke(nameAsSafeName).booleanValue() && !Intrinsics.areEqual(nameAsSafeName.getIdentifierOrNullIfSpecial(), "_")) {
                result.addAll(getContributedVariables(nameAsSafeName, location));
            }
        }
    }
}
