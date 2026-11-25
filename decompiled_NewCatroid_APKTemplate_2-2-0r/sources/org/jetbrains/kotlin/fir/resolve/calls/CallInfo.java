package org.jetbrains.kotlin.fir.resolve.calls;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.expressions.FirArgumentList;
import org.jetbrains.kotlin.fir.expressions.FirEmptyArgumentList;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCallOrigin;
import org.jetbrains.kotlin.fir.expressions.builder.FirArgumentListBuilder;
import org.jetbrains.kotlin.fir.resolve.DoubleColonLHS;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilder;

/* JADX INFO: compiled from: CallInfo.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b2\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001B\u009d\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f\u0012\u0006\u0010\u0011\u001a\u00020\u0012\u0012\u0006\u0010\u0013\u001a\u00020\u0014\u0012\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u000f\u0012\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u0018\u0012\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u001a\u0012\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u001c\u0012\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u001e\u0012\b\b\u0002\u0010\u001f\u001a\u00020 ¢\u0006\u0002\u0010!J\t\u0010D\u001a\u00020\u0003HÆ\u0003J\u000f\u0010E\u001a\b\u0012\u0004\u0012\u00020\u00160\u000fHÆ\u0003J\u000b\u0010F\u001a\u0004\u0018\u00010\u0018HÆ\u0003J\u000b\u0010G\u001a\u0004\u0018\u00010\u001aHÆ\u0003J\u000b\u0010H\u001a\u0004\u0018\u00010\u001cHÆ\u0003J\u000b\u0010I\u001a\u0004\u0018\u00010\u001eHÆ\u0003J\t\u0010J\u001a\u00020 HÆ\u0003J\t\u0010K\u001a\u00020\u0005HÆ\u0003J\t\u0010L\u001a\u00020\u0007HÆ\u0003J\u000b\u0010M\u001a\u0004\u0018\u00010\tHÆ\u0003J\t\u0010N\u001a\u00020\u000bHÆ\u0003J\t\u0010O\u001a\u00020\rHÆ\u0003J\u000f\u0010P\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fHÆ\u0003J\t\u0010Q\u001a\u00020\u0012HÆ\u0003J\t\u0010R\u001a\u00020\u0014HÆ\u0003Jµ\u0001\u0010S\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\r2\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\b\b\u0002\u0010\u0011\u001a\u00020\u00122\b\b\u0002\u0010\u0013\u001a\u00020\u00142\u000e\b\u0002\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u000f2\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u00182\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\b\b\u0002\u0010\u001f\u001a\u00020 HÆ\u0001J\u0013\u0010T\u001a\u00020\r2\b\u0010U\u001a\u0004\u0018\u00010VHÖ\u0003J\t\u0010W\u001a\u00020#HÖ\u0001J\u0010\u0010X\u001a\u00020\u00002\b\u0010\b\u001a\u0004\u0018\u00010\tJ\u0006\u0010Y\u001a\u00020\u0000J\t\u0010Z\u001a\u00020[HÖ\u0001J\u000e\u0010\\\u001a\u00020\u00002\u0006\u0010]\u001a\u00020\tR\u0011\u0010\"\u001a\u00020#8F¢\u0006\u0006\u001a\u0004\b$\u0010%R\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b&\u0010'R\u0017\u0010(\u001a\b\u0012\u0004\u0012\u00020\t0\u000f8F¢\u0006\u0006\u001a\u0004\b)\u0010*R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b-\u0010.R\u0013\u0010\u0017\u001a\u0004\u0018\u00010\u0018¢\u0006\b\n\u0000\u001a\u0004\b/\u00100R\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u000f¢\u0006\b\n\u0000\u001a\u0004\b1\u0010*R\u0014\u0010\u0013\u001a\u00020\u0014X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b2\u00103R\u0013\u0010\u0019\u001a\u0004\u0018\u00010\u001a¢\u0006\b\n\u0000\u001a\u0004\b4\u00105R\u0016\u0010\b\u001a\u0004\u0018\u00010\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b6\u00107R\u0014\u0010\f\u001a\u00020\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u00108R\u0013\u0010\u001d\u001a\u0004\u0018\u00010\u001e¢\u0006\b\n\u0000\u001a\u0004\b9\u0010:R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b;\u0010<R\u0011\u0010\u001f\u001a\u00020 ¢\u0006\b\n\u0000\u001a\u0004\b=\u0010>R\u0013\u0010\u001b\u001a\u0004\u0018\u00010\u001c¢\u0006\b\n\u0000\u001a\u0004\b?\u0010@R\u0011\u0010\u0011\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\bA\u0010BR\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f¢\u0006\b\n\u0000\u001a\u0004\bC\u0010*¨\u0006^"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "Lorg/jetbrains/kotlin/fir/resolve/calls/AbstractCallInfo;", "callSite", "Lorg/jetbrains/kotlin/fir/FirElement;", "callKind", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallKind;", "name", "Lorg/jetbrains/kotlin/name/Name;", "explicitReceiver", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "argumentList", "Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "isImplicitInvoke", "", "typeArguments", "", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "containingFile", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "containingDeclarations", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "candidateForCommonInvokeReceiver", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "expectedType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "outerCSBuilder", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilder;", "lhs", "Lorg/jetbrains/kotlin/fir/resolve/DoubleColonLHS;", "origin", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCallOrigin;", "(Lorg/jetbrains/kotlin/fir/FirElement;Lorg/jetbrains/kotlin/fir/resolve/calls/CallKind;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;ZLjava/util/List;Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/declarations/FirFile;Ljava/util/List;Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilder;Lorg/jetbrains/kotlin/fir/resolve/DoubleColonLHS;Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCallOrigin;)V", "argumentCount", "", "getArgumentCount", "()I", "getArgumentList", "()Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", "arguments", "getArguments", "()Ljava/util/List;", "getCallKind", "()Lorg/jetbrains/kotlin/fir/resolve/calls/CallKind;", "getCallSite", "()Lorg/jetbrains/kotlin/fir/FirElement;", "getCandidateForCommonInvokeReceiver", "()Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "getContainingDeclarations", "getContainingFile", "()Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "getExpectedType", "()Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "getExplicitReceiver", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "()Z", "getLhs", "()Lorg/jetbrains/kotlin/fir/resolve/DoubleColonLHS;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCallOrigin;", "getOuterCSBuilder", "()Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilder;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "getTypeArguments", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "replaceExplicitReceiver", "replaceWithVariableAccess", "toString", "", "withReceiverAsArgument", "receiverExpression", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class CallInfo extends AbstractCallInfo {
    private final FirArgumentList argumentList;
    private final CallKind callKind;
    private final FirElement callSite;
    private final Candidate candidateForCommonInvokeReceiver;
    private final List<FirDeclaration> containingDeclarations;
    private final FirFile containingFile;
    private final ConeKotlinType expectedType;
    private final FirExpression explicitReceiver;
    private final boolean isImplicitInvoke;
    private final DoubleColonLHS lhs;
    private final Name name;
    private final FirFunctionCallOrigin origin;
    private final ConstraintSystemBuilder outerCSBuilder;
    private final FirSession session;
    private final List<FirTypeProjection> typeArguments;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ CallInfo copy$default(CallInfo callInfo, FirElement firElement, CallKind callKind, Name name2, FirExpression firExpression, FirArgumentList firArgumentList, boolean z, List list, FirSession firSession, FirFile firFile, List list2, Candidate candidate, ConeKotlinType coneKotlinType, ConstraintSystemBuilder constraintSystemBuilder, DoubleColonLHS doubleColonLHS, FirFunctionCallOrigin firFunctionCallOrigin, int i, Object obj) {
        return callInfo.copy((i & 1) != 0 ? callInfo.callSite : firElement, (i & 2) != 0 ? callInfo.callKind : callKind, (i & 4) != 0 ? callInfo.name : name2, (i & 8) != 0 ? callInfo.explicitReceiver : firExpression, (i & 16) != 0 ? callInfo.argumentList : firArgumentList, (i & 32) != 0 ? callInfo.isImplicitInvoke : z, (i & 64) != 0 ? callInfo.typeArguments : list, (i & 128) != 0 ? callInfo.session : firSession, (i & 256) != 0 ? callInfo.containingFile : firFile, (i & 512) != 0 ? callInfo.containingDeclarations : list2, (i & 1024) != 0 ? callInfo.candidateForCommonInvokeReceiver : candidate, (i & 2048) != 0 ? callInfo.expectedType : coneKotlinType, (i & 4096) != 0 ? callInfo.outerCSBuilder : constraintSystemBuilder, (i & 8192) != 0 ? callInfo.lhs : doubleColonLHS, (i & 16384) != 0 ? callInfo.origin : firFunctionCallOrigin);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final FirElement getCallSite() {
        return this.callSite;
    }

    public final List<FirDeclaration> component10() {
        return this.containingDeclarations;
    }

    /* JADX INFO: renamed from: component11, reason: from getter */
    public final Candidate getCandidateForCommonInvokeReceiver() {
        return this.candidateForCommonInvokeReceiver;
    }

    /* JADX INFO: renamed from: component12, reason: from getter */
    public final ConeKotlinType getExpectedType() {
        return this.expectedType;
    }

    /* JADX INFO: renamed from: component13, reason: from getter */
    public final ConstraintSystemBuilder getOuterCSBuilder() {
        return this.outerCSBuilder;
    }

    /* JADX INFO: renamed from: component14, reason: from getter */
    public final DoubleColonLHS getLhs() {
        return this.lhs;
    }

    /* JADX INFO: renamed from: component15, reason: from getter */
    public final FirFunctionCallOrigin getOrigin() {
        return this.origin;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final CallKind getCallKind() {
        return this.callKind;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final Name getName() {
        return this.name;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final FirExpression getExplicitReceiver() {
        return this.explicitReceiver;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final FirArgumentList getArgumentList() {
        return this.argumentList;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final boolean getIsImplicitInvoke() {
        return this.isImplicitInvoke;
    }

    public final List<FirTypeProjection> component7() {
        return this.typeArguments;
    }

    /* JADX INFO: renamed from: component8, reason: from getter */
    public final FirSession getSession() {
        return this.session;
    }

    /* JADX INFO: renamed from: component9, reason: from getter */
    public final FirFile getContainingFile() {
        return this.containingFile;
    }

    public final CallInfo copy(FirElement callSite, CallKind callKind, Name name2, FirExpression explicitReceiver, FirArgumentList argumentList, boolean isImplicitInvoke, List<? extends FirTypeProjection> typeArguments, FirSession session, FirFile containingFile, List<? extends FirDeclaration> containingDeclarations, Candidate candidateForCommonInvokeReceiver, ConeKotlinType expectedType, ConstraintSystemBuilder outerCSBuilder, DoubleColonLHS lhs, FirFunctionCallOrigin origin) {
        Intrinsics.checkNotNullParameter(callSite, "callSite");
        Intrinsics.checkNotNullParameter(callKind, "callKind");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(argumentList, "argumentList");
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(containingFile, "containingFile");
        Intrinsics.checkNotNullParameter(containingDeclarations, "containingDeclarations");
        Intrinsics.checkNotNullParameter(origin, "origin");
        return new CallInfo(callSite, callKind, name2, explicitReceiver, argumentList, isImplicitInvoke, typeArguments, session, containingFile, containingDeclarations, candidateForCommonInvokeReceiver, expectedType, outerCSBuilder, lhs, origin);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CallInfo)) {
            return false;
        }
        CallInfo callInfo = (CallInfo) other;
        return Intrinsics.areEqual(this.callSite, callInfo.callSite) && Intrinsics.areEqual(this.callKind, callInfo.callKind) && Intrinsics.areEqual(this.name, callInfo.name) && Intrinsics.areEqual(this.explicitReceiver, callInfo.explicitReceiver) && Intrinsics.areEqual(this.argumentList, callInfo.argumentList) && this.isImplicitInvoke == callInfo.isImplicitInvoke && Intrinsics.areEqual(this.typeArguments, callInfo.typeArguments) && Intrinsics.areEqual(this.session, callInfo.session) && Intrinsics.areEqual(this.containingFile, callInfo.containingFile) && Intrinsics.areEqual(this.containingDeclarations, callInfo.containingDeclarations) && Intrinsics.areEqual(this.candidateForCommonInvokeReceiver, callInfo.candidateForCommonInvokeReceiver) && Intrinsics.areEqual(this.expectedType, callInfo.expectedType) && Intrinsics.areEqual(this.outerCSBuilder, callInfo.outerCSBuilder) && Intrinsics.areEqual(this.lhs, callInfo.lhs) && this.origin == callInfo.origin;
    }

    public int hashCode() {
        int iHashCode = ((((this.callSite.hashCode() * 31) + this.callKind.hashCode()) * 31) + this.name.hashCode()) * 31;
        FirExpression firExpression = this.explicitReceiver;
        int iHashCode2 = (((((((((((((iHashCode + (firExpression == null ? 0 : firExpression.hashCode())) * 31) + this.argumentList.hashCode()) * 31) + Boolean.hashCode(this.isImplicitInvoke)) * 31) + this.typeArguments.hashCode()) * 31) + this.session.hashCode()) * 31) + this.containingFile.hashCode()) * 31) + this.containingDeclarations.hashCode()) * 31;
        Candidate candidate = this.candidateForCommonInvokeReceiver;
        int iHashCode3 = (iHashCode2 + (candidate == null ? 0 : candidate.hashCode())) * 31;
        ConeKotlinType coneKotlinType = this.expectedType;
        int iHashCode4 = (iHashCode3 + (coneKotlinType == null ? 0 : coneKotlinType.hashCode())) * 31;
        ConstraintSystemBuilder constraintSystemBuilder = this.outerCSBuilder;
        int iHashCode5 = (iHashCode4 + (constraintSystemBuilder == null ? 0 : constraintSystemBuilder.hashCode())) * 31;
        DoubleColonLHS doubleColonLHS = this.lhs;
        return ((iHashCode5 + (doubleColonLHS != null ? doubleColonLHS.hashCode() : 0)) * 31) + this.origin.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("CallInfo(callSite=");
        sb.append(this.callSite).append(", callKind=").append(this.callKind).append(", name=").append(this.name).append(", explicitReceiver=").append(this.explicitReceiver).append(", argumentList=").append(this.argumentList).append(", isImplicitInvoke=").append(this.isImplicitInvoke).append(", typeArguments=").append(this.typeArguments).append(", session=").append(this.session).append(", containingFile=").append(this.containingFile).append(", containingDeclarations=").append(this.containingDeclarations).append(", candidateForCommonInvokeReceiver=").append(this.candidateForCommonInvokeReceiver).append(", expectedType=");
        sb.append(this.expectedType).append(", outerCSBuilder=").append(this.outerCSBuilder).append(", lhs=").append(this.lhs).append(", origin=").append(this.origin).append(')');
        return sb.toString();
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.AbstractCallInfo
    public FirElement getCallSite() {
        return this.callSite;
    }

    public final CallKind getCallKind() {
        return this.callKind;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.AbstractCallInfo
    public Name getName() {
        return this.name;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.AbstractCallInfo
    public FirExpression getExplicitReceiver() {
        return this.explicitReceiver;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.AbstractCallInfo
    public FirArgumentList getArgumentList() {
        return this.argumentList;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.AbstractCallInfo
    public boolean isImplicitInvoke() {
        return this.isImplicitInvoke;
    }

    public final List<FirTypeProjection> getTypeArguments() {
        return this.typeArguments;
    }

    public final FirSession getSession() {
        return this.session;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.AbstractCallInfo
    public FirFile getContainingFile() {
        return this.containingFile;
    }

    public final List<FirDeclaration> getContainingDeclarations() {
        return this.containingDeclarations;
    }

    public final Candidate getCandidateForCommonInvokeReceiver() {
        return this.candidateForCommonInvokeReceiver;
    }

    public final ConeKotlinType getExpectedType() {
        return this.expectedType;
    }

    public final ConstraintSystemBuilder getOuterCSBuilder() {
        return this.outerCSBuilder;
    }

    public final DoubleColonLHS getLhs() {
        return this.lhs;
    }

    public /* synthetic */ CallInfo(FirElement firElement, CallKind callKind, Name name2, FirExpression firExpression, FirArgumentList firArgumentList, boolean z, List list, FirSession firSession, FirFile firFile, List list2, Candidate candidate, ConeKotlinType coneKotlinType, ConstraintSystemBuilder constraintSystemBuilder, DoubleColonLHS doubleColonLHS, FirFunctionCallOrigin firFunctionCallOrigin, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firElement, callKind, name2, firExpression, firArgumentList, z, list, firSession, firFile, list2, (i & 1024) != 0 ? null : candidate, (i & 2048) != 0 ? null : coneKotlinType, (i & 4096) != 0 ? null : constraintSystemBuilder, (i & 8192) != 0 ? null : doubleColonLHS, (i & 16384) != 0 ? FirFunctionCallOrigin.Regular : firFunctionCallOrigin);
    }

    public final FirFunctionCallOrigin getOrigin() {
        return this.origin;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public CallInfo(FirElement callSite, CallKind callKind, Name name2, FirExpression firExpression, FirArgumentList argumentList, boolean z, List<? extends FirTypeProjection> typeArguments, FirSession session, FirFile containingFile, List<? extends FirDeclaration> containingDeclarations, Candidate candidate, ConeKotlinType coneKotlinType, ConstraintSystemBuilder constraintSystemBuilder, DoubleColonLHS doubleColonLHS, FirFunctionCallOrigin origin) {
        Intrinsics.checkNotNullParameter(callSite, "callSite");
        Intrinsics.checkNotNullParameter(callKind, "callKind");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(argumentList, "argumentList");
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(containingFile, "containingFile");
        Intrinsics.checkNotNullParameter(containingDeclarations, "containingDeclarations");
        Intrinsics.checkNotNullParameter(origin, "origin");
        this.callSite = callSite;
        this.callKind = callKind;
        this.name = name2;
        this.explicitReceiver = firExpression;
        this.argumentList = argumentList;
        this.isImplicitInvoke = z;
        this.typeArguments = typeArguments;
        this.session = session;
        this.containingFile = containingFile;
        this.containingDeclarations = containingDeclarations;
        this.candidateForCommonInvokeReceiver = candidate;
        this.expectedType = coneKotlinType;
        this.outerCSBuilder = constraintSystemBuilder;
        this.lhs = doubleColonLHS;
        this.origin = origin;
    }

    public final List<FirExpression> getArguments() {
        return getArgumentList().getArguments();
    }

    public final int getArgumentCount() {
        return getArguments().size();
    }

    public final CallInfo replaceWithVariableAccess() {
        return copy$default(this, null, CallKind.VariableAccess.INSTANCE, null, null, FirEmptyArgumentList.INSTANCE, false, CollectionsKt.emptyList(), null, null, null, null, null, null, null, null, 32685, null);
    }

    public final CallInfo replaceExplicitReceiver(FirExpression explicitReceiver) {
        return copy$default(this, null, null, null, explicitReceiver, null, false, null, null, null, null, null, null, null, null, null, 32759, null);
    }

    public final CallInfo withReceiverAsArgument(FirExpression receiverExpression) {
        Intrinsics.checkNotNullParameter(receiverExpression, "receiverExpression");
        FirArgumentListBuilder firArgumentListBuilder = new FirArgumentListBuilder();
        firArgumentListBuilder.getArguments().add(receiverExpression);
        CollectionsKt.addAll(firArgumentListBuilder.getArguments(), getArgumentList().getArguments());
        Unit unit = Unit.INSTANCE;
        return copy$default(this, null, null, null, null, firArgumentListBuilder.build(), false, null, null, null, null, null, null, null, null, null, 32751, null);
    }
}
