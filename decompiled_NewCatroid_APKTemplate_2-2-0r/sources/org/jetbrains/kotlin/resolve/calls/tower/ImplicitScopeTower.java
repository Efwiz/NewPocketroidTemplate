package org.jetbrains.kotlin.resolve.calls.tower;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.sequences.Sequence;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;
import org.jetbrains.kotlin.resolve.scopes.SyntheticScopes;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;
import org.jetbrains.kotlin.resolve.scopes.utils.ScopeUtilsKt;
import org.jetbrains.kotlin.types.TypeApproximator;

/* JADX INFO: compiled from: ImplicitScopeTower.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u000e\u0010$\u001a\b\u0012\u0004\u0012\u00020&0%H\u0016J\u0016\u0010'\u001a\b\u0012\u0004\u0012\u00020)0(2\u0006\u0010*\u001a\u00020\u0015H&J\u0012\u0010+\u001a\u0004\u0018\u00010)2\u0006\u0010*\u001a\u00020\u0015H&J\u0012\u0010,\u001a\u0004\u0018\u00010-2\u0006\u0010.\u001a\u00020-H&JH\u0010/\u001a\b\u0012\u0004\u0012\u000201002\u0006\u00102\u001a\u0002032\u0006\u0010.\u001a\u00020-2\f\u00104\u001a\b\u0012\u0004\u0012\u000201002\u0006\u0010\u0018\u001a\u00020\u00192\b\u00105\u001a\u0004\u0018\u00010)2\b\u00106\u001a\u0004\u0018\u00010)H&JH\u00107\u001a\b\u0012\u0004\u0012\u000208002\u0006\u00102\u001a\u0002032\u0006\u0010.\u001a\u00020-2\f\u00104\u001a\b\u0012\u0004\u0012\u000208002\u0006\u0010\u0018\u001a\u00020\u00192\b\u00105\u001a\u0004\u0018\u00010)2\b\u00106\u001a\u0004\u0018\u00010)H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0012\u0010\u000e\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u0005R\u0012\u0010\u000f\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0005R\u0012\u0010\u0010\u001a\u00020\u0011X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0012\u0010\u0014\u001a\u00020\u0015X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0012\u0010\u0018\u001a\u00020\u0019X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u0012\u0010\u001c\u001a\u00020\u001dX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001fR\u0012\u0010 \u001a\u00020!X¦\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010#ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u00069À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "", "areContextReceiversEnabled", "", "getAreContextReceiversEnabled", "()Z", "dynamicScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "getDynamicScope", "()Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "implicitsResolutionFilter", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitsExtensionsResolutionFilter;", "getImplicitsResolutionFilter", "()Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitsExtensionsResolutionFilter;", "isDebuggerContext", "isNewInferenceEnabled", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "lexicalScope", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "getLexicalScope", "()Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "getLocation", "()Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "syntheticScopes", "Lorg/jetbrains/kotlin/resolve/scopes/SyntheticScopes;", "getSyntheticScopes", "()Lorg/jetbrains/kotlin/resolve/scopes/SyntheticScopes;", "typeApproximator", "Lorg/jetbrains/kotlin/types/TypeApproximator;", "getTypeApproximator", "()Lorg/jetbrains/kotlin/types/TypeApproximator;", "allScopesWithImplicitsResolutionInfo", "Lkotlin/sequences/Sequence;", "Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeWithImplicitsExtensionsResolutionInfo;", "getContextReceivers", "", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "scope", "getImplicitReceiver", "getNameForGivenImportAlias", "Lorg/jetbrains/kotlin/name/Name;", "name", "interceptFunctionCandidates", "", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "resolutionScope", "Lorg/jetbrains/kotlin/resolve/scopes/ResolutionScope;", "initialResults", "dispatchReceiver", "extensionReceiver", "interceptVariableCandidates", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ImplicitScopeTower {
    boolean getAreContextReceiversEnabled();

    List<ReceiverValueWithSmartCastInfo> getContextReceivers(LexicalScope scope);

    MemberScope getDynamicScope();

    ReceiverValueWithSmartCastInfo getImplicitReceiver(LexicalScope scope);

    ImplicitsExtensionsResolutionFilter getImplicitsResolutionFilter();

    LanguageVersionSettings getLanguageVersionSettings();

    LexicalScope getLexicalScope();

    LookupLocation getLocation();

    Name getNameForGivenImportAlias(Name name2);

    SyntheticScopes getSyntheticScopes();

    TypeApproximator getTypeApproximator();

    Collection<FunctionDescriptor> interceptFunctionCandidates(ResolutionScope resolutionScope, Name name2, Collection<? extends FunctionDescriptor> initialResults, LookupLocation location, ReceiverValueWithSmartCastInfo dispatchReceiver, ReceiverValueWithSmartCastInfo extensionReceiver);

    Collection<VariableDescriptor> interceptVariableCandidates(ResolutionScope resolutionScope, Name name2, Collection<? extends VariableDescriptor> initialResults, LookupLocation location, ReceiverValueWithSmartCastInfo dispatchReceiver, ReceiverValueWithSmartCastInfo extensionReceiver);

    boolean isDebuggerContext();

    boolean isNewInferenceEnabled();

    default Sequence<ScopeWithImplicitsExtensionsResolutionInfo> allScopesWithImplicitsResolutionInfo() {
        return getImplicitsResolutionFilter().getScopesWithInfo(ScopeUtilsKt.getParentsWithSelf(getLexicalScope()));
    }
}
