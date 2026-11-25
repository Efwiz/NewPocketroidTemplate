package org.jetbrains.kotlin.fir.builder;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.KtSourceElementKind;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.fir.FirFunctionTarget;
import org.jetbrains.kotlin.fir.FirLabel;
import org.jetbrains.kotlin.fir.FirLoopTarget;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.builder.FirOuterClassTypeParameterRefBuilder;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.util.PrivateForInline;

/* JADX INFO: compiled from: Context.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u008e\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002:\u0001^B\u0005¢\u0006\u0002\u0010\u0003J\u000e\u0010F\u001a\u00020G2\u0006\u0010H\u001a\u00020\u0006J\u001c\u0010I\u001a\u00020G2\u0006\u0010J\u001a\u00020\u001c2\f\u0010K\u001a\b\u0012\u0004\u0012\u00020L0\u0005J\u0006\u0010M\u001a\u00020GJ\u0018\u0010N\u001a\u00020G2\u0006\u0010O\u001a\u00020\u00022\b\u0010P\u001a\u0004\u0018\u00010\u0002J\u0010\u0010Q\u001a\u0004\u0018\u00010\u00062\u0006\u0010R\u001a\u00020\u0002J\u0006\u0010S\u001a\u00020GJ\u001c\u0010T\u001a\u00020G2\u0006\u0010U\u001a\u00020\u001c2\f\u0010V\u001a\b\u0012\u0004\u0012\u00020L02J\u0010\u0010W\u001a\u00020G2\b\u0010X\u001a\u0004\u0018\u00010\u0002J7\u0010Y\u001a\u0002H\u0001\"\u0004\b\u0001\u0010\u00012\u0006\u0010H\u001a\u00020\u00062\b\u0010Z\u001a\u0004\u0018\u00010\u00022\f\u0010[\u001a\b\u0012\u0004\u0012\u0002H\u00010\\H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010]R\"\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00058\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0007\u0010\u0003\u001a\u0004\b\b\u0010\tR\u001d\u0010\n\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\f0\u000b¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0019\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\tR\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\tR\u001a\u0010\u0015\u001a\u00020\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001b\u001a\u00020\u001cX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u0011\u0010!\u001a\u00020\"8F¢\u0006\u0006\u001a\u0004\b#\u0010$R\u0017\u0010%\u001a\b\u0012\u0004\u0012\u00020&0\u0005¢\u0006\b\n\u0000\u001a\u0004\b'\u0010\tR\u0017\u0010(\u001a\b\u0012\u0004\u0012\u00020)0\u0005¢\u0006\b\n\u0000\u001a\u0004\b*\u0010\tR&\u0010+\u001a\u0004\u0018\u00010\u00028\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b,\u0010\u0003\u001a\u0004\b-\u0010.\"\u0004\b/\u00100R\u001d\u00101\u001a\b\u0012\u0004\u0012\u00020\u0006028F¢\u0006\f\u0012\u0004\b3\u0010\u0003\u001a\u0004\b4\u0010\tR\u0017\u00105\u001a\b\u0012\u0004\u0012\u0002060\u0005¢\u0006\b\n\u0000\u001a\u0004\b7\u0010\tR\u001c\u00108\u001a\u0004\u0018\u000109X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b:\u0010;\"\u0004\b<\u0010=R\u001a\u0010>\u001a\u00020\u001cX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b?\u0010\u001e\"\u0004\b@\u0010 R\u0011\u0010A\u001a\u00020\u001c8F¢\u0006\u0006\u001a\u0004\bB\u0010\u001eR\u001a\u0010C\u001a\u00020\u0016X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\bD\u0010\u0018\"\u0004\bE\u0010\u001a\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006_"}, d2 = {"Lorg/jetbrains/kotlin/fir/builder/Context;", ExifInterface.GPS_DIRECTION_TRUE, "", "()V", "_firLabels", "", "Lorg/jetbrains/kotlin/fir/FirLabel;", "get_firLabels$annotations", "get_firLabels", "()Ljava/util/List;", "arraySetArgument", "", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getArraySetArgument", "()Ljava/util/Map;", "calleeNamesForLambda", "Lorg/jetbrains/kotlin/name/Name;", "getCalleeNamesForLambda", "capturedTypeParameters", "Lorg/jetbrains/kotlin/fir/builder/Context$StatusFirTypeParameterSymbolList;", "getCapturedTypeParameters", "className", "Lorg/jetbrains/kotlin/name/FqName;", "getClassName", "()Lorg/jetbrains/kotlin/name/FqName;", "setClassName", "(Lorg/jetbrains/kotlin/name/FqName;)V", "containerIsExpect", "", "getContainerIsExpect", "()Z", "setContainerIsExpect", "(Z)V", "currentClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "getCurrentClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "dispatchReceiverTypesStack", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "getDispatchReceiverTypesStack", "firFunctionTargets", "Lorg/jetbrains/kotlin/fir/FirFunctionTarget;", "getFirFunctionTargets", "firLabelUserNode", "getFirLabelUserNode$annotations", "getFirLabelUserNode", "()Ljava/lang/Object;", "setFirLabelUserNode", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, "firLabels", "", "getFirLabels$annotations", "getFirLabels", "firLoopTargets", "Lorg/jetbrains/kotlin/fir/FirLoopTarget;", "getFirLoopTargets", "forcedElementSourceKind", "Lorg/jetbrains/kotlin/KtSourceElementKind;", "getForcedElementSourceKind", "()Lorg/jetbrains/kotlin/KtSourceElementKind;", "setForcedElementSourceKind", "(Lorg/jetbrains/kotlin/KtSourceElementKind;)V", "forcedLocalContext", "getForcedLocalContext", "setForcedLocalContext", "inLocalContext", "getInLocalContext", "packageFqName", "getPackageFqName", "setPackageFqName", "addNewLabel", "", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "appendOuterTypeParameters", "ignoreLastLevel", "typeParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "dropLastLabel", "forwardLabelUsagePermission", "currentUserNode", "newUserNode", "getLastLabel", "currentNode", "popFirTypeParameters", "pushFirTypeParameters", "isInnerOrLocal", "parameters", "setNewLabelUserNode", "useNode", "withNewLabel", "userNode", "block", "Lkotlin/Function0;", "(Lorg/jetbrains/kotlin/fir/FirLabel;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "StatusFirTypeParameterSymbolList", "raw-fir.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Context<T> {
    private final List<FirLabel> _firLabels;
    private final Map<T, FirExpression> arraySetArgument;
    private final List<Name> calleeNamesForLambda;
    private final List<StatusFirTypeParameterSymbolList> capturedTypeParameters;
    private FqName className;
    private boolean containerIsExpect;
    private final List<ConeClassLikeType> dispatchReceiverTypesStack;
    private final List<FirFunctionTarget> firFunctionTargets;
    private Object firLabelUserNode;
    private final List<FirLoopTarget> firLoopTargets;
    private KtSourceElementKind forcedElementSourceKind;
    private boolean forcedLocalContext;
    public FqName packageFqName;

    @PrivateForInline
    public static /* synthetic */ void getFirLabelUserNode$annotations() {
    }

    public static /* synthetic */ void getFirLabels$annotations() {
    }

    @PrivateForInline
    public static /* synthetic */ void get_firLabels$annotations() {
    }

    public Context() {
        FqName ROOT = FqName.ROOT;
        Intrinsics.checkNotNullExpressionValue(ROOT, "ROOT");
        this.className = ROOT;
        this.firFunctionTargets = new ArrayList();
        this.calleeNamesForLambda = new ArrayList();
        this._firLabels = new ArrayList();
        this.firLoopTargets = new ArrayList();
        this.capturedTypeParameters = new ArrayList();
        this.arraySetArgument = new LinkedHashMap();
        this.dispatchReceiverTypesStack = new ArrayList();
    }

    public final FqName getPackageFqName() {
        FqName fqName = this.packageFqName;
        if (fqName != null) {
            return fqName;
        }
        Intrinsics.throwUninitializedPropertyAccessException("packageFqName");
        return null;
    }

    public final void setPackageFqName(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "<set-?>");
        this.packageFqName = fqName;
    }

    public final FqName getClassName() {
        return this.className;
    }

    public final void setClassName(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "<set-?>");
        this.className = fqName;
    }

    public final boolean getForcedLocalContext() {
        return this.forcedLocalContext;
    }

    public final void setForcedLocalContext(boolean z) {
        this.forcedLocalContext = z;
    }

    public final boolean getInLocalContext() {
        return this.forcedLocalContext || !this.firFunctionTargets.isEmpty();
    }

    public final ClassId getCurrentClassId() {
        return new ClassId(getPackageFqName(), this.className, getInLocalContext());
    }

    public final List<FirFunctionTarget> getFirFunctionTargets() {
        return this.firFunctionTargets;
    }

    public final List<Name> getCalleeNamesForLambda() {
        return this.calleeNamesForLambda;
    }

    public final List<FirLabel> get_firLabels() {
        return this._firLabels;
    }

    public final List<FirLabel> getFirLabels() {
        return this._firLabels;
    }

    public final Object getFirLabelUserNode() {
        return this.firLabelUserNode;
    }

    public final void setFirLabelUserNode(Object obj) {
        this.firLabelUserNode = obj;
    }

    public final List<FirLoopTarget> getFirLoopTargets() {
        return this.firLoopTargets;
    }

    public final List<StatusFirTypeParameterSymbolList> getCapturedTypeParameters() {
        return this.capturedTypeParameters;
    }

    public final Map<T, FirExpression> getArraySetArgument() {
        return this.arraySetArgument;
    }

    public final KtSourceElementKind getForcedElementSourceKind() {
        return this.forcedElementSourceKind;
    }

    public final void setForcedElementSourceKind(KtSourceElementKind ktSourceElementKind) {
        this.forcedElementSourceKind = ktSourceElementKind;
    }

    public final List<ConeClassLikeType> getDispatchReceiverTypesStack() {
        return this.dispatchReceiverTypesStack;
    }

    public final boolean getContainerIsExpect() {
        return this.containerIsExpect;
    }

    public final void setContainerIsExpect(boolean z) {
        this.containerIsExpect = z;
    }

    public final void pushFirTypeParameters(boolean isInnerOrLocal, List<? extends FirTypeParameterRef> parameters) {
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        List<StatusFirTypeParameterSymbolList> list = this.capturedTypeParameters;
        List<? extends FirTypeParameterRef> list2 = parameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add(((FirTypeParameterRef) it.next()).getSymbol());
        }
        list.add(new StatusFirTypeParameterSymbolList(isInnerOrLocal, arrayList));
    }

    public final void popFirTypeParameters() {
        List<StatusFirTypeParameterSymbolList> list = this.capturedTypeParameters;
        list.remove(CollectionsKt.getLastIndex(list));
    }

    public final void appendOuterTypeParameters(boolean ignoreLastLevel, List<FirTypeParameterRef> typeParameters) {
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        for (int lastIndex = CollectionsKt.getLastIndex(this.capturedTypeParameters); -1 < lastIndex; lastIndex--) {
            StatusFirTypeParameterSymbolList statusFirTypeParameterSymbolList = this.capturedTypeParameters.get(lastIndex);
            if (lastIndex < CollectionsKt.getLastIndex(this.capturedTypeParameters) || !ignoreLastLevel) {
                for (FirTypeParameterSymbol firTypeParameterSymbol : statusFirTypeParameterSymbolList.getList()) {
                    FirOuterClassTypeParameterRefBuilder firOuterClassTypeParameterRefBuilder = new FirOuterClassTypeParameterRefBuilder();
                    firOuterClassTypeParameterRefBuilder.setSymbol(firTypeParameterSymbol);
                    typeParameters.add(firOuterClassTypeParameterRefBuilder.build());
                }
            }
            if (!statusFirTypeParameterSymbolList.isInnerOrLocal()) {
                return;
            }
        }
    }

    public final FirLabel getLastLabel(Object currentNode) {
        Intrinsics.checkNotNullParameter(currentNode, "currentNode");
        if (Intrinsics.areEqual(this.firLabelUserNode, currentNode)) {
            return (FirLabel) CollectionsKt.last((List) getFirLabels());
        }
        return null;
    }

    public final void addNewLabel(FirLabel label) {
        Intrinsics.checkNotNullParameter(label, "label");
        this._firLabels.add(label);
    }

    public final void setNewLabelUserNode(Object useNode) {
        this.firLabelUserNode = useNode;
    }

    public final void dropLastLabel() {
        CollectionsKt.removeLast(this._firLabels);
        this.firLabelUserNode = null;
    }

    public final <T> T withNewLabel(FirLabel label, Object userNode, Function0<? extends T> block) {
        Intrinsics.checkNotNullParameter(label, "label");
        Intrinsics.checkNotNullParameter(block, "block");
        addNewLabel(label);
        setNewLabelUserNode(userNode);
        try {
            return block.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            dropLastLabel();
            InlineMarker.finallyEnd(1);
        }
    }

    public final void forwardLabelUsagePermission(Object currentUserNode, Object newUserNode) {
        Intrinsics.checkNotNullParameter(currentUserNode, "currentUserNode");
        if (Intrinsics.areEqual(currentUserNode, this.firLabelUserNode)) {
            this.firLabelUserNode = newUserNode;
        }
    }

    /* JADX INFO: compiled from: Context.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J#\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u00032\b\u0010\u000f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\bR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/fir/builder/Context$StatusFirTypeParameterSymbolList;", "", "isInnerOrLocal", "", SchemaSymbols.ATTVAL_LIST, "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "(ZLjava/util/List;)V", "()Z", "getList", "()Ljava/util/List;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "raw-fir.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class StatusFirTypeParameterSymbolList {
        private final boolean isInnerOrLocal;
        private final List<FirTypeParameterSymbol> list;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ StatusFirTypeParameterSymbolList copy$default(StatusFirTypeParameterSymbolList statusFirTypeParameterSymbolList, boolean z, List list, int i, Object obj) {
            if ((i & 1) != 0) {
                z = statusFirTypeParameterSymbolList.isInnerOrLocal;
            }
            if ((i & 2) != 0) {
                list = statusFirTypeParameterSymbolList.list;
            }
            return statusFirTypeParameterSymbolList.copy(z, list);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final boolean getIsInnerOrLocal() {
            return this.isInnerOrLocal;
        }

        public final List<FirTypeParameterSymbol> component2() {
            return this.list;
        }

        public final StatusFirTypeParameterSymbolList copy(boolean isInnerOrLocal, List<FirTypeParameterSymbol> list) {
            Intrinsics.checkNotNullParameter(list, "list");
            return new StatusFirTypeParameterSymbolList(isInnerOrLocal, list);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof StatusFirTypeParameterSymbolList)) {
                return false;
            }
            StatusFirTypeParameterSymbolList statusFirTypeParameterSymbolList = (StatusFirTypeParameterSymbolList) other;
            return this.isInnerOrLocal == statusFirTypeParameterSymbolList.isInnerOrLocal && Intrinsics.areEqual(this.list, statusFirTypeParameterSymbolList.list);
        }

        public int hashCode() {
            return (Boolean.hashCode(this.isInnerOrLocal) * 31) + this.list.hashCode();
        }

        public String toString() {
            return "StatusFirTypeParameterSymbolList(isInnerOrLocal=" + this.isInnerOrLocal + ", list=" + this.list + ')';
        }

        public StatusFirTypeParameterSymbolList(boolean z, List<FirTypeParameterSymbol> list) {
            Intrinsics.checkNotNullParameter(list, "list");
            this.isInnerOrLocal = z;
            this.list = list;
        }

        public /* synthetic */ StatusFirTypeParameterSymbolList(boolean z, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(z, (i & 2) != 0 ? CollectionsKt.emptyList() : list);
        }

        public final List<FirTypeParameterSymbol> getList() {
            return this.list;
        }

        public final boolean isInnerOrLocal() {
            return this.isInnerOrLocal;
        }
    }
}
