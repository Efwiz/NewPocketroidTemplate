package org.jetbrains.kotlin.fir;

import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.container.DataStructuresKt;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.SupertypeSupplier;
import org.jetbrains.kotlin.fir.resolve.SupertypeUtilsKt;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirBackingFieldSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCodeFragmentSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertyAccessorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirScriptSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.utils.exceptions.FirExceptionUtilsKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.util.AbstractArrayMapOwner;
import org.jetbrains.kotlin.util.ArrayMapAccessor;
import org.jetbrains.kotlin.util.NullableArrayMapAccessor;
import org.jetbrains.kotlin.util.TypeRegistry;
import org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilder;
import org.jetbrains.kotlin.utils.exceptions.KotlinIllegalArgumentExceptionWithAttachments;

/* JADX INFO: compiled from: FirVisibilityChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000X\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0016\u0010\u0012\u001a\u0004\u0018\u00010\u0013*\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0007H\u0002\u001a6\u0010\u0012\u001a\u0004\u0018\u00010\u0013*\u00020\u00152\u0006\u0010\u0014\u001a\u00020\u00072\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u00012\u0006\u0010\u001a\u001a\u00020\u001bH\u0002\u001a\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u001d*\u0006\u0012\u0002\b\u00030\u001e\u001a\u000e\u0010\u001f\u001a\u00020 *\u0006\u0012\u0002\b\u00030\u001e\u001a<\u0010!\u001a\n\u0012\u0004\u0012\u00020\u0013\u0018\u00010\"*\u00020\u00152\u0006\u0010\u0014\u001a\u00020\u00072\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00190\u00012\b\b\u0002\u0010\u001a\u001a\u00020\u001b\"\u001b\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"!\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u00020\u00078FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\t\"\u001f\u0010\f\u001a\u00020\r*\u00020\u00078FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u000e\u0010\u000f¨\u0006$"}, d2 = {"allDependsOnDependencies", "", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "getAllDependsOnDependencies", "(Lorg/jetbrains/kotlin/fir/FirModuleData;)Ljava/util/List;", "moduleVisibilityChecker", "Lorg/jetbrains/kotlin/fir/FirModuleVisibilityChecker;", "Lorg/jetbrains/kotlin/fir/FirSession;", "getModuleVisibilityChecker", "(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/FirModuleVisibilityChecker;", "moduleVisibilityChecker$delegate", "Lorg/jetbrains/kotlin/util/NullableArrayMapAccessor;", "visibilityChecker", "Lorg/jetbrains/kotlin/fir/FirVisibilityChecker;", "getVisibilityChecker", "(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/FirVisibilityChecker;", "visibilityChecker$delegate", "Lorg/jetbrains/kotlin/util/ArrayMapAccessor;", "containingNonLocalClass", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "session", "Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;", "dispatchReceiver", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "containingUseSiteDeclarations", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "supertypeSupplier", "Lorg/jetbrains/kotlin/fir/resolve/SupertypeSupplier;", "getOwnerLookupTag", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "isVariableOrNamedFunction", "", "parentDeclarationSequence", "Lkotlin/sequences/Sequence;", "containingDeclarations", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirVisibilityCheckerKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(FirVisibilityCheckerKt.class, "providers"), "moduleVisibilityChecker", "getModuleVisibilityChecker(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/FirModuleVisibilityChecker;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(FirVisibilityCheckerKt.class, "providers"), "visibilityChecker", "getVisibilityChecker(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/FirVisibilityChecker;"))};
    private static final NullableArrayMapAccessor moduleVisibilityChecker$delegate = FirSession.INSTANCE.generateNullableAccessor(Reflection.getOrCreateKotlinClass(FirModuleVisibilityChecker.class));
    private static final ArrayMapAccessor visibilityChecker$delegate = TypeRegistry.generateAccessor$default(FirSession.INSTANCE, Reflection.getOrCreateKotlinClass(FirVisibilityChecker.class), (Object) null, 2, (Object) null);

    public static final List<FirModuleData> getAllDependsOnDependencies(FirModuleData firModuleData) {
        Intrinsics.checkNotNullParameter(firModuleData, "<this>");
        return DataStructuresKt.topologicalSort$default(firModuleData.getDependsOnDependencies(), false, new Function1<FirModuleData, Iterable<? extends FirModuleData>>() { // from class: org.jetbrains.kotlin.fir.FirVisibilityCheckerKt$allDependsOnDependencies$1
            @Override // kotlin.jvm.functions.Function1
            public final Iterable<FirModuleData> invoke(FirModuleData it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getDependsOnDependencies();
            }
        }, 2, null);
    }

    public static final FirModuleVisibilityChecker getModuleVisibilityChecker(FirSession firSession) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        return (FirModuleVisibilityChecker) moduleVisibilityChecker$delegate.getValue((AbstractArrayMapOwner) firSession, $$delegatedProperties[0]);
    }

    public static final FirVisibilityChecker getVisibilityChecker(FirSession firSession) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        return (FirVisibilityChecker) visibilityChecker$delegate.getValue((AbstractArrayMapOwner) firSession, $$delegatedProperties[1]);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final ConeClassLikeLookupTag getOwnerLookupTag(FirBasedSymbol<?> firBasedSymbol) {
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        if (firBasedSymbol instanceof FirBackingFieldSymbol) {
            return getOwnerLookupTag(((FirBackingField) ((FirBackingFieldSymbol) firBasedSymbol).getFir()).getPropertySymbol());
        }
        if (firBasedSymbol instanceof FirClassLikeSymbol) {
            return ClassMembersKt.getContainingClassLookupTag((FirClassLikeSymbol) firBasedSymbol);
        }
        if (firBasedSymbol instanceof FirCallableSymbol) {
            return ClassMembersKt.containingClassLookupTag((FirCallableSymbol<?>) firBasedSymbol);
        }
        if (firBasedSymbol instanceof FirScriptSymbol ? true : firBasedSymbol instanceof FirCodeFragmentSymbol) {
            return null;
        }
        KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Unsupported owner search for " + firBasedSymbol.getFir().getClass(), null);
        ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
        FirExceptionUtilsKt.withFirEntry(exceptionAttachmentBuilder, "ownerDeclaration", firBasedSymbol.getFir());
        kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
        throw kotlinIllegalArgumentExceptionWithAttachments;
    }

    public static final boolean isVariableOrNamedFunction(FirBasedSymbol<?> firBasedSymbol) {
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        return (firBasedSymbol instanceof FirVariableSymbol) || (firBasedSymbol instanceof FirNamedFunctionSymbol) || (firBasedSymbol instanceof FirPropertyAccessorSymbol);
    }

    public static /* synthetic */ Sequence parentDeclarationSequence$default(FirMemberDeclaration firMemberDeclaration, FirSession firSession, FirExpression firExpression, List list, SupertypeSupplier supertypeSupplier, int i, Object obj) {
        if ((i & 8) != 0) {
            supertypeSupplier = SupertypeSupplier.Default.INSTANCE;
        }
        return parentDeclarationSequence(firMemberDeclaration, firSession, firExpression, list, supertypeSupplier);
    }

    public static final Sequence<FirClassLikeDeclaration> parentDeclarationSequence(FirMemberDeclaration firMemberDeclaration, final FirSession session, FirExpression firExpression, List<? extends FirDeclaration> containingDeclarations, SupertypeSupplier supertypeSupplier) {
        Intrinsics.checkNotNullParameter(firMemberDeclaration, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(containingDeclarations, "containingDeclarations");
        Intrinsics.checkNotNullParameter(supertypeSupplier, "supertypeSupplier");
        FirClassLikeDeclaration firClassLikeDeclarationContainingNonLocalClass = containingNonLocalClass(firMemberDeclaration, session, firExpression, containingDeclarations, supertypeSupplier);
        if (firClassLikeDeclarationContainingNonLocalClass == null) {
            return null;
        }
        return SequencesKt.generateSequence(firClassLikeDeclarationContainingNonLocalClass, new Function1<FirClassLikeDeclaration, FirClassLikeDeclaration>() { // from class: org.jetbrains.kotlin.fir.FirVisibilityCheckerKt.parentDeclarationSequence.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final FirClassLikeDeclaration invoke(FirClassLikeDeclaration it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return FirVisibilityCheckerKt.containingNonLocalClass(it, session);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final FirClassLikeDeclaration containingNonLocalClass(FirMemberDeclaration firMemberDeclaration, FirSession firSession, FirExpression firExpression, List<? extends FirDeclaration> list, SupertypeSupplier supertypeSupplier) {
        FirClass firClass;
        FirClassLikeSymbol<?> symbol;
        ConeClassLikeType coneClassLikeTypeDispatchReceiverClassTypeOrNull;
        ConeClassLikeLookupTag coneClassLikeLookupTagFindClassRepresentation;
        FirClassLikeSymbol<?> symbol2;
        FirClassLikeDeclaration firClassLikeDeclaration;
        if (!(firMemberDeclaration instanceof FirCallableDeclaration)) {
            if (firMemberDeclaration instanceof FirClassLikeDeclaration) {
                return containingNonLocalClass((FirClassLikeDeclaration) firMemberDeclaration, firSession);
            }
            throw new NoWhenBranchMatchedException();
        }
        if (firExpression != null && (coneClassLikeTypeDispatchReceiverClassTypeOrNull = ClassMembersKt.dispatchReceiverClassTypeOrNull((FirCallableDeclaration) firMemberDeclaration)) != null && (coneClassLikeLookupTagFindClassRepresentation = LookupTagUtilsKt.findClassRepresentation(FirTypeUtilsKt.getConeType(firExpression.getTypeRef()), coneClassLikeTypeDispatchReceiverClassTypeOrNull, firSession)) != null && (symbol2 = LookupTagUtilsKt.toSymbol(coneClassLikeLookupTagFindClassRepresentation, firSession)) != null && (firClassLikeDeclaration = (FirClassLikeDeclaration) symbol2.getFir()) != null) {
            return firClassLikeDeclaration;
        }
        ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag((FirCallableDeclaration) firMemberDeclaration);
        FirClass firClass2 = null;
        FirClassLikeDeclaration firClassLikeDeclaration2 = (coneClassLikeLookupTagContainingClassLookupTag == null || (symbol = LookupTagUtilsKt.toSymbol(coneClassLikeLookupTagContainingClassLookupTag, firSession)) == null) ? null : (FirClassLikeDeclaration) symbol.getFir();
        if (!firMemberDeclaration.getStatus().isStatic() || firClassLikeDeclaration2 == null) {
            return firClassLikeDeclaration2;
        }
        for (FirDeclaration firDeclaration : list) {
            if (firDeclaration instanceof FirClass) {
                if (!SupertypeUtilsKt.isSubclassOf$default((FirClass) firDeclaration, coneClassLikeLookupTagContainingClassLookupTag, firSession, false, supertypeSupplier, false, 16, null)) {
                    firDeclaration = null;
                }
                firClass = (FirClass) firDeclaration;
            } else {
                firClass = null;
            }
            if (firClass != null) {
                firClass2 = firClass;
                break;
            }
        }
        return firClass2 != null ? firClass2 : firClassLikeDeclaration2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final FirClassLikeDeclaration containingNonLocalClass(FirClassLikeDeclaration firClassLikeDeclaration, FirSession firSession) {
        ClassId outerClassId;
        FirClassLikeSymbol<?> classLikeSymbolByClassId;
        if (!(firClassLikeDeclaration instanceof FirClass)) {
            if (firClassLikeDeclaration instanceof FirTypeAlias) {
                return null;
            }
            throw new NoWhenBranchMatchedException();
        }
        if (firClassLikeDeclaration.getSymbol().getClassId().isLocal() || (outerClassId = FirDeclarationUtilKt.getClassId(firClassLikeDeclaration).getOuterClassId()) == null || (classLikeSymbolByClassId = FirSymbolProviderKt.getSymbolProvider(firSession).getClassLikeSymbolByClassId(outerClassId)) == null) {
            return null;
        }
        return (FirClassLikeDeclaration) classLikeSymbolByClassId.getFir();
    }
}
