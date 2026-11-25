package org.jetbrains.kotlin.types;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.org.jline.reader.LineReader;
import org.jetbrains.kotlin.types.model.CapturedTypeMarker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.TypeSystemContext;
import org.jetbrains.kotlin.utils.SmartSet;

/* JADX INFO: compiled from: AbstractTypeChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010#\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\b\u0016\u0018\u00002\u00020\u0001:\u0003GHIB5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ)\u0010&\u001a\u0004\u0018\u00010\u00032\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020(2\b\b\u0002\u0010*\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010+J<\u0010,\u001a\u00020\u00032\u0006\u0010-\u001a\u00020\u001b2\u0012\u0010.\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u00030/2\u0012\u00100\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u0002010/H\u0086\bø\u0001\u0000J\u0006\u00102\u001a\u000203J\u0018\u00104\u001a\u00020\u00032\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020(H\u0016J\u0018\u00105\u001a\u0002062\u0006\u0010'\u001a\u00020\u001b2\u0006\u0010)\u001a\u000207H\u0016J\u0006\u00108\u001a\u000203J\u000e\u00109\u001a\u00020\u00032\u0006\u0010:\u001a\u00020(J\u000e\u0010;\u001a\u00020(2\u0006\u0010:\u001a\u00020(J\u000e\u0010<\u001a\u00020(2\u0006\u0010:\u001a\u00020(J!\u0010=\u001a\u00020\u00032\u0017\u0010>\u001a\u0013\u0012\u0004\u0012\u00020?\u0012\u0004\u0012\u0002030/¢\u0006\u0002\b@H\u0016J:\u0010A\u001a\u0002HB\"\u0004\b\u0000\u0010B2\u0006\u0010C\u001a\u00020(2\u0017\u0010D\u001a\u0013\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u0002HB0/¢\u0006\u0002\b@H\u0080\bø\u0001\u0000¢\u0006\u0004\bE\u0010FR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u000eR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R.\u0010\u001c\u001a\n\u0012\u0004\u0012\u00020\u001b\u0018\u00010\u001a2\u000e\u0010\u0019\u001a\n\u0012\u0004\u0012\u00020\u001b\u0018\u00010\u001a@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u000e\u0010\u001f\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R.\u0010!\u001a\n\u0012\u0004\u0012\u00020\u001b\u0018\u00010 2\u000e\u0010\u0019\u001a\n\u0012\u0004\u0012\u00020\u001b\u0018\u00010 @BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b$\u0010%\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006J"}, d2 = {"Lorg/jetbrains/kotlin/types/TypeCheckerState;", "", "isErrorTypeEqualsToAnything", "", "isStubTypeEqualsToAnything", "allowedTypeVariable", "typeSystemContext", "Lorg/jetbrains/kotlin/types/model/TypeSystemContext;", "kotlinTypePreparator", "Lorg/jetbrains/kotlin/types/AbstractTypePreparator;", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/AbstractTypeRefiner;", "(ZZZLorg/jetbrains/kotlin/types/model/TypeSystemContext;Lorg/jetbrains/kotlin/types/AbstractTypePreparator;Lorg/jetbrains/kotlin/types/AbstractTypeRefiner;)V", "getAllowedTypeVariable", "()Z", "argumentsDepth", "", "getArgumentsDepth", "()I", "setArgumentsDepth", "(I)V", "getKotlinTypePreparator", "()Lorg/jetbrains/kotlin/types/AbstractTypePreparator;", "getKotlinTypeRefiner", "()Lorg/jetbrains/kotlin/types/AbstractTypeRefiner;", "<set-?>", "Ljava/util/ArrayDeque;", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "supertypesDeque", "getSupertypesDeque", "()Ljava/util/ArrayDeque;", "supertypesLocked", "", "supertypesSet", "getSupertypesSet", "()Ljava/util/Set;", "getTypeSystemContext", "()Lorg/jetbrains/kotlin/types/model/TypeSystemContext;", "addSubtypeConstraint", "subType", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "superType", "isFromNullabilityConstraint", "(Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;Z)Ljava/lang/Boolean;", "anySupertype", "start", "predicate", "Lkotlin/Function1;", "supertypesPolicy", "Lorg/jetbrains/kotlin/types/TypeCheckerState$SupertypesPolicy;", LineReader.CLEAR, "", "customIsSubtypeOf", "getLowerCapturedTypePolicy", "Lorg/jetbrains/kotlin/types/TypeCheckerState$LowerCapturedTypePolicy;", "Lorg/jetbrains/kotlin/types/model/CapturedTypeMarker;", "initialize", "isAllowedTypeVariable", ModuleXmlParser.TYPE, "prepareType", "refineType", "runForkingPoint", "block", "Lorg/jetbrains/kotlin/types/TypeCheckerState$ForkPointContext;", "Lkotlin/ExtensionFunctionType;", "runWithArgumentsSettings", ExifInterface.GPS_DIRECTION_TRUE, "subArgument", "f", "runWithArgumentsSettings$compiler_common", "(Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "ForkPointContext", "LowerCapturedTypePolicy", "SupertypesPolicy", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class TypeCheckerState {
    private final boolean allowedTypeVariable;
    private int argumentsDepth;
    private final boolean isErrorTypeEqualsToAnything;
    private final boolean isStubTypeEqualsToAnything;
    private final AbstractTypePreparator kotlinTypePreparator;
    private final AbstractTypeRefiner kotlinTypeRefiner;
    private ArrayDeque<SimpleTypeMarker> supertypesDeque;
    private boolean supertypesLocked;
    private Set<SimpleTypeMarker> supertypesSet;
    private final TypeSystemContext typeSystemContext;

    /* JADX INFO: compiled from: AbstractTypeChecker.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/types/TypeCheckerState$LowerCapturedTypePolicy;", "", "(Ljava/lang/String;I)V", "CHECK_ONLY_LOWER", "CHECK_SUBTYPE_AND_LOWER", "SKIP_LOWER", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum LowerCapturedTypePolicy {
        CHECK_ONLY_LOWER,
        CHECK_SUBTYPE_AND_LOWER,
        SKIP_LOWER;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<LowerCapturedTypePolicy> getEntries() {
            return $ENTRIES;
        }
    }

    public Boolean addSubtypeConstraint(KotlinTypeMarker subType, KotlinTypeMarker superType, boolean isFromNullabilityConstraint) {
        Intrinsics.checkNotNullParameter(subType, "subType");
        Intrinsics.checkNotNullParameter(superType, "superType");
        return null;
    }

    public boolean customIsSubtypeOf(KotlinTypeMarker subType, KotlinTypeMarker superType) {
        Intrinsics.checkNotNullParameter(subType, "subType");
        Intrinsics.checkNotNullParameter(superType, "superType");
        return true;
    }

    public TypeCheckerState(boolean z, boolean z2, boolean z3, TypeSystemContext typeSystemContext, AbstractTypePreparator kotlinTypePreparator, AbstractTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(typeSystemContext, "typeSystemContext");
        Intrinsics.checkNotNullParameter(kotlinTypePreparator, "kotlinTypePreparator");
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        this.isErrorTypeEqualsToAnything = z;
        this.isStubTypeEqualsToAnything = z2;
        this.allowedTypeVariable = z3;
        this.typeSystemContext = typeSystemContext;
        this.kotlinTypePreparator = kotlinTypePreparator;
        this.kotlinTypeRefiner = kotlinTypeRefiner;
    }

    /* JADX INFO: renamed from: isErrorTypeEqualsToAnything, reason: from getter */
    public final boolean getIsErrorTypeEqualsToAnything() {
        return this.isErrorTypeEqualsToAnything;
    }

    /* JADX INFO: renamed from: isStubTypeEqualsToAnything, reason: from getter */
    public final boolean getIsStubTypeEqualsToAnything() {
        return this.isStubTypeEqualsToAnything;
    }

    public final boolean getAllowedTypeVariable() {
        return this.allowedTypeVariable;
    }

    public final TypeSystemContext getTypeSystemContext() {
        return this.typeSystemContext;
    }

    public final AbstractTypePreparator getKotlinTypePreparator() {
        return this.kotlinTypePreparator;
    }

    public final AbstractTypeRefiner getKotlinTypeRefiner() {
        return this.kotlinTypeRefiner;
    }

    public final KotlinTypeMarker refineType(KotlinTypeMarker type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return this.kotlinTypeRefiner.refineType(type);
    }

    public final KotlinTypeMarker prepareType(KotlinTypeMarker type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return this.kotlinTypePreparator.prepareType(type);
    }

    protected final int getArgumentsDepth() {
        return this.argumentsDepth;
    }

    protected final void setArgumentsDepth(int i) {
        this.argumentsDepth = i;
    }

    public final <T> T runWithArgumentsSettings$compiler_common(KotlinTypeMarker subArgument, Function1<? super TypeCheckerState, ? extends T> f) {
        Intrinsics.checkNotNullParameter(subArgument, "subArgument");
        Intrinsics.checkNotNullParameter(f, "f");
        if (this.argumentsDepth <= 100) {
            this.argumentsDepth++;
            T tInvoke = f.invoke(this);
            this.argumentsDepth--;
            return tInvoke;
        }
        throw new IllegalStateException(("Arguments depth is too high. Some related argument: " + subArgument).toString());
    }

    public LowerCapturedTypePolicy getLowerCapturedTypePolicy(SimpleTypeMarker subType, CapturedTypeMarker superType) {
        Intrinsics.checkNotNullParameter(subType, "subType");
        Intrinsics.checkNotNullParameter(superType, "superType");
        return LowerCapturedTypePolicy.CHECK_SUBTYPE_AND_LOWER;
    }

    public static /* synthetic */ Boolean addSubtypeConstraint$default(TypeCheckerState typeCheckerState, KotlinTypeMarker kotlinTypeMarker, KotlinTypeMarker kotlinTypeMarker2, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: addSubtypeConstraint");
        }
        if ((i & 4) != 0) {
            z = false;
        }
        return typeCheckerState.addSubtypeConstraint(kotlinTypeMarker, kotlinTypeMarker2, z);
    }

    public boolean runForkingPoint(Function1<? super ForkPointContext, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        ForkPointContext.Default r0 = new ForkPointContext.Default();
        block.invoke(r0);
        return r0.getResult();
    }

    /* JADX INFO: compiled from: AbstractTypeChecker.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001:\u0001\u0007J\u0016\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/types/TypeCheckerState$ForkPointContext;", "", "fork", "", "block", "Lkotlin/Function0;", "", "Default", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface ForkPointContext {
        void fork(Function0<Boolean> block);

        /* JADX INFO: compiled from: AbstractTypeChecker.kt */
        @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\fH\u0016R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/types/TypeCheckerState$ForkPointContext$Default;", "Lorg/jetbrains/kotlin/types/TypeCheckerState$ForkPointContext;", "()V", "result", "", "getResult", "()Z", "setResult", "(Z)V", "fork", "", "block", "Lkotlin/Function0;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Default implements ForkPointContext {
            private boolean result;

            public final boolean getResult() {
                return this.result;
            }

            public final void setResult(boolean z) {
                this.result = z;
            }

            @Override // org.jetbrains.kotlin.types.TypeCheckerState.ForkPointContext
            public void fork(Function0<Boolean> block) {
                Intrinsics.checkNotNullParameter(block, "block");
                if (this.result) {
                    return;
                }
                this.result = block.invoke().booleanValue();
            }
        }
    }

    public final ArrayDeque<SimpleTypeMarker> getSupertypesDeque() {
        return this.supertypesDeque;
    }

    public final Set<SimpleTypeMarker> getSupertypesSet() {
        return this.supertypesSet;
    }

    public final void initialize() {
        this.supertypesLocked = true;
        if (this.supertypesDeque == null) {
            this.supertypesDeque = new ArrayDeque<>(4);
        }
        if (this.supertypesSet == null) {
            this.supertypesSet = SmartSet.INSTANCE.create();
        }
    }

    public final void clear() {
        ArrayDeque<SimpleTypeMarker> arrayDeque = this.supertypesDeque;
        Intrinsics.checkNotNull(arrayDeque);
        arrayDeque.clear();
        Set<SimpleTypeMarker> set = this.supertypesSet;
        Intrinsics.checkNotNull(set);
        set.clear();
        this.supertypesLocked = false;
    }

    public final boolean anySupertype(SimpleTypeMarker start, Function1<? super SimpleTypeMarker, Boolean> predicate, Function1<? super SimpleTypeMarker, ? extends SupertypesPolicy> supertypesPolicy) {
        Intrinsics.checkNotNullParameter(start, "start");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        Intrinsics.checkNotNullParameter(supertypesPolicy, "supertypesPolicy");
        if (predicate.invoke(start).booleanValue()) {
            return true;
        }
        initialize();
        ArrayDeque<SimpleTypeMarker> supertypesDeque = getSupertypesDeque();
        Intrinsics.checkNotNull(supertypesDeque);
        Set<SimpleTypeMarker> supertypesSet = getSupertypesSet();
        Intrinsics.checkNotNull(supertypesSet);
        supertypesDeque.push(start);
        while (!supertypesDeque.isEmpty()) {
            if (supertypesSet.size() > 1000) {
                throw new IllegalStateException(("Too many supertypes for type: " + start + ". Supertypes = " + CollectionsKt.joinToString$default(supertypesSet, null, null, null, 0, null, null, 63, null)).toString());
            }
            SimpleTypeMarker simpleTypeMarkerPop = supertypesDeque.pop();
            Intrinsics.checkNotNull(simpleTypeMarkerPop);
            if (supertypesSet.add(simpleTypeMarkerPop)) {
                SupertypesPolicy supertypesPolicyInvoke = supertypesPolicy.invoke(simpleTypeMarkerPop);
                if (Intrinsics.areEqual(supertypesPolicyInvoke, SupertypesPolicy.None.INSTANCE)) {
                    supertypesPolicyInvoke = null;
                }
                SupertypesPolicy supertypesPolicy2 = supertypesPolicyInvoke;
                if (supertypesPolicy2 == null) {
                    continue;
                } else {
                    TypeSystemContext typeSystemContext = getTypeSystemContext();
                    Iterator<KotlinTypeMarker> it = typeSystemContext.supertypes(typeSystemContext.typeConstructor(simpleTypeMarkerPop)).iterator();
                    while (it.hasNext()) {
                        SimpleTypeMarker simpleTypeMarkerMo7474transformType = supertypesPolicy2.mo7474transformType(this, it.next());
                        if (predicate.invoke(simpleTypeMarkerMo7474transformType).booleanValue()) {
                            clear();
                            return true;
                        }
                        supertypesDeque.add(simpleTypeMarkerMo7474transformType);
                    }
                }
            }
        }
        clear();
        return false;
    }

    /* JADX INFO: compiled from: AbstractTypeChecker.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0004\t\n\u000b\fB\u0007\b\u0004¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH&\u0082\u0001\u0004\r\u000e\u000f\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/types/TypeCheckerState$SupertypesPolicy;", "", "()V", "transformType", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "state", "Lorg/jetbrains/kotlin/types/TypeCheckerState;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "DoCustomTransform", "LowerIfFlexible", "None", "UpperIfFlexible", "Lorg/jetbrains/kotlin/types/TypeCheckerState$SupertypesPolicy$DoCustomTransform;", "Lorg/jetbrains/kotlin/types/TypeCheckerState$SupertypesPolicy$LowerIfFlexible;", "Lorg/jetbrains/kotlin/types/TypeCheckerState$SupertypesPolicy$None;", "Lorg/jetbrains/kotlin/types/TypeCheckerState$SupertypesPolicy$UpperIfFlexible;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class SupertypesPolicy {
        public /* synthetic */ SupertypesPolicy(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: transformType */
        public abstract SimpleTypeMarker mo7474transformType(TypeCheckerState state, KotlinTypeMarker type);

        private SupertypesPolicy() {
        }

        /* JADX INFO: compiled from: AbstractTypeChecker.kt */
        @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/types/TypeCheckerState$SupertypesPolicy$None;", "Lorg/jetbrains/kotlin/types/TypeCheckerState$SupertypesPolicy;", "()V", "transformType", "", "state", "Lorg/jetbrains/kotlin/types/TypeCheckerState;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class None extends SupertypesPolicy {
            public static final None INSTANCE = new None();

            private None() {
                super(null);
            }

            @Override // org.jetbrains.kotlin.types.TypeCheckerState.SupertypesPolicy
            /* JADX INFO: renamed from: transformType, reason: collision with other method in class */
            public /* bridge */ /* synthetic */ SimpleTypeMarker mo7474transformType(TypeCheckerState typeCheckerState, KotlinTypeMarker kotlinTypeMarker) {
                return (SimpleTypeMarker) transformType(typeCheckerState, kotlinTypeMarker);
            }

            public Void transformType(TypeCheckerState state, KotlinTypeMarker type) {
                Intrinsics.checkNotNullParameter(state, "state");
                Intrinsics.checkNotNullParameter(type, "type");
                throw new UnsupportedOperationException("Should not be called");
            }
        }

        /* JADX INFO: compiled from: AbstractTypeChecker.kt */
        @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/types/TypeCheckerState$SupertypesPolicy$UpperIfFlexible;", "Lorg/jetbrains/kotlin/types/TypeCheckerState$SupertypesPolicy;", "()V", "transformType", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "state", "Lorg/jetbrains/kotlin/types/TypeCheckerState;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class UpperIfFlexible extends SupertypesPolicy {
            public static final UpperIfFlexible INSTANCE = new UpperIfFlexible();

            private UpperIfFlexible() {
                super(null);
            }

            @Override // org.jetbrains.kotlin.types.TypeCheckerState.SupertypesPolicy
            /* JADX INFO: renamed from: transformType */
            public SimpleTypeMarker mo7474transformType(TypeCheckerState state, KotlinTypeMarker type) {
                Intrinsics.checkNotNullParameter(state, "state");
                Intrinsics.checkNotNullParameter(type, "type");
                return state.getTypeSystemContext().upperBoundIfFlexible(type);
            }
        }

        /* JADX INFO: compiled from: AbstractTypeChecker.kt */
        @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/types/TypeCheckerState$SupertypesPolicy$LowerIfFlexible;", "Lorg/jetbrains/kotlin/types/TypeCheckerState$SupertypesPolicy;", "()V", "transformType", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "state", "Lorg/jetbrains/kotlin/types/TypeCheckerState;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class LowerIfFlexible extends SupertypesPolicy {
            public static final LowerIfFlexible INSTANCE = new LowerIfFlexible();

            private LowerIfFlexible() {
                super(null);
            }

            @Override // org.jetbrains.kotlin.types.TypeCheckerState.SupertypesPolicy
            /* JADX INFO: renamed from: transformType */
            public SimpleTypeMarker mo7474transformType(TypeCheckerState state, KotlinTypeMarker type) {
                Intrinsics.checkNotNullParameter(state, "state");
                Intrinsics.checkNotNullParameter(type, "type");
                return state.getTypeSystemContext().lowerBoundIfFlexible(type);
            }
        }

        /* JADX INFO: compiled from: AbstractTypeChecker.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/types/TypeCheckerState$SupertypesPolicy$DoCustomTransform;", "Lorg/jetbrains/kotlin/types/TypeCheckerState$SupertypesPolicy;", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static abstract class DoCustomTransform extends SupertypesPolicy {
            public DoCustomTransform() {
                super(null);
            }
        }
    }

    public final boolean isAllowedTypeVariable(KotlinTypeMarker type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return this.allowedTypeVariable && this.typeSystemContext.isTypeVariableType(type);
    }
}
