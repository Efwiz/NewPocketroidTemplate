package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.fir.analysis.CheckersComponentInternal;
import org.jetbrains.kotlin.fir.analysis.cfa.AbstractFirPropertyInitializationChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.cfa.FirControlFlowChecker;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousInitializer;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;

/* JADX INFO: compiled from: DeclarationCheckers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0088\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u0000 ¥\u00012\u00020\u0001:\u0002¥\u0001B\u0005¢\u0006\u0002\u0010\u0002R1\u0010\u0003\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00060\u0005j\u0002`\u00070\u00048@X\u0081\u0084\u0002¢\u0006\u0012\n\u0004\b\u000b\u0010\f\u0012\u0004\b\b\u0010\u0002\u001a\u0004\b\t\u0010\nR1\u0010\r\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u000e0\u0005j\u0002`\u000f0\u00048@X\u0081\u0084\u0002¢\u0006\u0012\n\u0004\b\u0012\u0010\f\u0012\u0004\b\u0010\u0010\u0002\u001a\u0004\b\u0011\u0010\nR1\u0010\u0013\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00140\u0005j\u0002`\u00150\u00048@X\u0081\u0084\u0002¢\u0006\u0012\n\u0004\b\u0018\u0010\f\u0012\u0004\b\u0016\u0010\u0002\u001a\u0004\b\u0017\u0010\nR1\u0010\u0019\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u001a0\u0005j\u0002`\u001b0\u00048@X\u0081\u0084\u0002¢\u0006\u0012\n\u0004\b\u001e\u0010\f\u0012\u0004\b\u001c\u0010\u0002\u001a\u0004\b\u001d\u0010\nR1\u0010\u001f\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020 0\u0005j\u0002`!0\u00048@X\u0081\u0084\u0002¢\u0006\u0012\n\u0004\b$\u0010\f\u0012\u0004\b\"\u0010\u0002\u001a\u0004\b#\u0010\nR1\u0010%\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020&0\u0005j\u0002`'0\u00048@X\u0081\u0084\u0002¢\u0006\u0012\n\u0004\b*\u0010\f\u0012\u0004\b(\u0010\u0002\u001a\u0004\b)\u0010\nR1\u0010+\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020,0\u0005j\u0002`-0\u00048@X\u0081\u0084\u0002¢\u0006\u0012\n\u0004\b0\u0010\f\u0012\u0004\b.\u0010\u0002\u001a\u0004\b/\u0010\nR1\u00101\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u0002020\u0005j\u0002`30\u00048@X\u0081\u0084\u0002¢\u0006\u0012\n\u0004\b6\u0010\f\u0012\u0004\b4\u0010\u0002\u001a\u0004\b5\u0010\nR1\u00107\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u0002080\u0005j\u0002`90\u00048@X\u0081\u0084\u0002¢\u0006\u0012\n\u0004\b<\u0010\f\u0012\u0004\b:\u0010\u0002\u001a\u0004\b;\u0010\nR1\u0010=\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020>0\u0005j\u0002`?0\u00048@X\u0081\u0084\u0002¢\u0006\u0012\n\u0004\bB\u0010\f\u0012\u0004\b@\u0010\u0002\u001a\u0004\bA\u0010\nR1\u0010C\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020D0\u0005j\u0002`E0\u00048@X\u0081\u0084\u0002¢\u0006\u0012\n\u0004\bH\u0010\f\u0012\u0004\bF\u0010\u0002\u001a\u0004\bG\u0010\nR1\u0010I\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020J0\u0005j\u0002`K0\u00048@X\u0081\u0084\u0002¢\u0006\u0012\n\u0004\bN\u0010\f\u0012\u0004\bL\u0010\u0002\u001a\u0004\bM\u0010\nR1\u0010O\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020P0\u0005j\u0002`Q0\u00048@X\u0081\u0084\u0002¢\u0006\u0012\n\u0004\bT\u0010\f\u0012\u0004\bR\u0010\u0002\u001a\u0004\bS\u0010\nR1\u0010U\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020V0\u0005j\u0002`W0\u00048@X\u0081\u0084\u0002¢\u0006\u0012\n\u0004\bZ\u0010\f\u0012\u0004\bX\u0010\u0002\u001a\u0004\bY\u0010\nR1\u0010[\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\\0\u0005j\u0002`]0\u00048@X\u0081\u0084\u0002¢\u0006\u0012\n\u0004\b`\u0010\f\u0012\u0004\b^\u0010\u0002\u001a\u0004\b_\u0010\nR1\u0010a\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020b0\u0005j\u0002`c0\u00048@X\u0081\u0084\u0002¢\u0006\u0012\n\u0004\bf\u0010\f\u0012\u0004\bd\u0010\u0002\u001a\u0004\be\u0010\nR1\u0010g\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020h0\u0005j\u0002`i0\u00048@X\u0081\u0084\u0002¢\u0006\u0012\n\u0004\bl\u0010\f\u0012\u0004\bj\u0010\u0002\u001a\u0004\bk\u0010\nR1\u0010m\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020n0\u0005j\u0002`o0\u00048@X\u0081\u0084\u0002¢\u0006\u0012\n\u0004\br\u0010\f\u0012\u0004\bp\u0010\u0002\u001a\u0004\bq\u0010\nR1\u0010s\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020t0\u0005j\u0002`u0\u00048@X\u0081\u0084\u0002¢\u0006\u0012\n\u0004\bx\u0010\f\u0012\u0004\bv\u0010\u0002\u001a\u0004\bw\u0010\nR$\u0010y\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00060\u0005j\u0002`\u00070\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bz\u0010\nR$\u0010{\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u000e0\u0005j\u0002`\u000f0\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b|\u0010\nR$\u0010}\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00140\u0005j\u0002`\u00150\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b~\u0010\nR%\u0010\u007f\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u001a0\u0005j\u0002`\u001b0\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0080\u0001\u0010\nR&\u0010\u0081\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020 0\u0005j\u0002`!0\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0082\u0001\u0010\nR&\u0010\u0083\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020&0\u0005j\u0002`'0\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0084\u0001\u0010\nR&\u0010\u0085\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020,0\u0005j\u0002`-0\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0086\u0001\u0010\nR&\u0010\u0087\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u0002020\u0005j\u0002`30\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0088\u0001\u0010\nR&\u0010\u0089\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u0002080\u0005j\u0002`90\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u008a\u0001\u0010\nR\u001d\u0010\u008b\u0001\u001a\t\u0012\u0005\u0012\u00030\u008c\u00010\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u008d\u0001\u0010\nR&\u0010\u008e\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020>0\u0005j\u0002`?0\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u008f\u0001\u0010\nR&\u0010\u0090\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020D0\u0005j\u0002`E0\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0091\u0001\u0010\nR&\u0010\u0092\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020J0\u0005j\u0002`K0\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0093\u0001\u0010\nR&\u0010\u0094\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020P0\u0005j\u0002`Q0\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0095\u0001\u0010\nR&\u0010\u0096\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020V0\u0005j\u0002`W0\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0097\u0001\u0010\nR&\u0010\u0098\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\\0\u0005j\u0002`]0\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0099\u0001\u0010\nR&\u0010\u009a\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020b0\u0005j\u0002`c0\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u009b\u0001\u0010\nR&\u0010\u009c\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020h0\u0005j\u0002`i0\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u009d\u0001\u0010\nR&\u0010\u009e\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020n0\u0005j\u0002`o0\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u009f\u0001\u0010\nR&\u0010 \u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020t0\u0005j\u0002`u0\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b¡\u0001\u0010\nR\u001d\u0010¢\u0001\u001a\t\u0012\u0005\u0012\u00030£\u00010\u0004X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b¤\u0001\u0010\n¨\u0006¦\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/DeclarationCheckers;", "", "()V", "allAnonymousFunctionCheckers", "", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirAnonymousFunctionChecker;", "getAllAnonymousFunctionCheckers$checkers$annotations", "getAllAnonymousFunctionCheckers$checkers", "()Ljava/util/Set;", "allAnonymousFunctionCheckers$delegate", "Lkotlin/Lazy;", "allAnonymousInitializerCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousInitializer;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirAnonymousInitializerChecker;", "getAllAnonymousInitializerCheckers$checkers$annotations", "getAllAnonymousInitializerCheckers$checkers", "allAnonymousInitializerCheckers$delegate", "allAnonymousObjectCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousObject;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirAnonymousObjectChecker;", "getAllAnonymousObjectCheckers$checkers$annotations", "getAllAnonymousObjectCheckers$checkers", "allAnonymousObjectCheckers$delegate", "allBackingFieldCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBackingFieldChecker;", "getAllBackingFieldCheckers$checkers$annotations", "getAllBackingFieldCheckers$checkers", "allBackingFieldCheckers$delegate", "allBasicDeclarationCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBasicDeclarationChecker;", "getAllBasicDeclarationCheckers$checkers$annotations", "getAllBasicDeclarationCheckers$checkers", "allBasicDeclarationCheckers$delegate", "allCallableDeclarationCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirCallableDeclarationChecker;", "getAllCallableDeclarationCheckers$checkers$annotations", "getAllCallableDeclarationCheckers$checkers", "allCallableDeclarationCheckers$delegate", "allClassCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirClassChecker;", "getAllClassCheckers$checkers$annotations", "getAllClassCheckers$checkers", "allClassCheckers$delegate", "allClassLikeCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirClassLikeChecker;", "getAllClassLikeCheckers$checkers$annotations", "getAllClassLikeCheckers$checkers", "allClassLikeCheckers$delegate", "allConstructorCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirConstructorChecker;", "getAllConstructorCheckers$checkers$annotations", "getAllConstructorCheckers$checkers", "allConstructorCheckers$delegate", "allEnumEntryCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirEnumEntry;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirEnumEntryChecker;", "getAllEnumEntryCheckers$checkers$annotations", "getAllEnumEntryCheckers$checkers", "allEnumEntryCheckers$delegate", "allFileCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirFileChecker;", "getAllFileCheckers$checkers$annotations", "getAllFileCheckers$checkers", "allFileCheckers$delegate", "allFunctionCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirFunctionChecker;", "getAllFunctionCheckers$checkers$annotations", "getAllFunctionCheckers$checkers", "allFunctionCheckers$delegate", "allPropertyAccessorCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirPropertyAccessorChecker;", "getAllPropertyAccessorCheckers$checkers$annotations", "getAllPropertyAccessorCheckers$checkers", "allPropertyAccessorCheckers$delegate", "allPropertyCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirPropertyChecker;", "getAllPropertyCheckers$checkers$annotations", "getAllPropertyCheckers$checkers", "allPropertyCheckers$delegate", "allRegularClassCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirRegularClassChecker;", "getAllRegularClassCheckers$checkers$annotations", "getAllRegularClassCheckers$checkers", "allRegularClassCheckers$delegate", "allSimpleFunctionCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirSimpleFunctionChecker;", "getAllSimpleFunctionCheckers$checkers$annotations", "getAllSimpleFunctionCheckers$checkers", "allSimpleFunctionCheckers$delegate", "allTypeAliasCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirTypeAliasChecker;", "getAllTypeAliasCheckers$checkers$annotations", "getAllTypeAliasCheckers$checkers", "allTypeAliasCheckers$delegate", "allTypeParameterCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirTypeParameterChecker;", "getAllTypeParameterCheckers$checkers$annotations", "getAllTypeParameterCheckers$checkers", "allTypeParameterCheckers$delegate", "allValueParameterCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirValueParameterChecker;", "getAllValueParameterCheckers$checkers$annotations", "getAllValueParameterCheckers$checkers", "allValueParameterCheckers$delegate", "anonymousFunctionCheckers", "getAnonymousFunctionCheckers", "anonymousInitializerCheckers", "getAnonymousInitializerCheckers", "anonymousObjectCheckers", "getAnonymousObjectCheckers", "backingFieldCheckers", "getBackingFieldCheckers", "basicDeclarationCheckers", "getBasicDeclarationCheckers", "callableDeclarationCheckers", "getCallableDeclarationCheckers", "classCheckers", "getClassCheckers", "classLikeCheckers", "getClassLikeCheckers", "constructorCheckers", "getConstructorCheckers", "controlFlowAnalyserCheckers", "Lorg/jetbrains/kotlin/fir/analysis/checkers/cfa/FirControlFlowChecker;", "getControlFlowAnalyserCheckers", "enumEntryCheckers", "getEnumEntryCheckers", "fileCheckers", "getFileCheckers", "functionCheckers", "getFunctionCheckers", "propertyAccessorCheckers", "getPropertyAccessorCheckers", "propertyCheckers", "getPropertyCheckers", "regularClassCheckers", "getRegularClassCheckers", "simpleFunctionCheckers", "getSimpleFunctionCheckers", "typeAliasCheckers", "getTypeAliasCheckers", "typeParameterCheckers", "getTypeParameterCheckers", "valueParameterCheckers", "getValueParameterCheckers", "variableAssignmentCfaBasedCheckers", "Lorg/jetbrains/kotlin/fir/analysis/cfa/AbstractFirPropertyInitializationChecker;", "getVariableAssignmentCfaBasedCheckers", "Companion", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class DeclarationCheckers {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final DeclarationCheckers EMPTY = new DeclarationCheckers() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers$Companion$EMPTY$1
    };
    private final Set<FirDeclarationChecker<FirDeclaration>> basicDeclarationCheckers = SetsKt.emptySet();
    private final Set<FirDeclarationChecker<FirCallableDeclaration>> callableDeclarationCheckers = SetsKt.emptySet();
    private final Set<FirDeclarationChecker<FirFunction>> functionCheckers = SetsKt.emptySet();
    private final Set<FirDeclarationChecker<FirSimpleFunction>> simpleFunctionCheckers = SetsKt.emptySet();
    private final Set<FirDeclarationChecker<FirProperty>> propertyCheckers = SetsKt.emptySet();
    private final Set<FirDeclarationChecker<FirClassLikeDeclaration>> classLikeCheckers = SetsKt.emptySet();
    private final Set<FirDeclarationChecker<FirClass>> classCheckers = SetsKt.emptySet();
    private final Set<FirDeclarationChecker<FirRegularClass>> regularClassCheckers = SetsKt.emptySet();
    private final Set<FirDeclarationChecker<FirConstructor>> constructorCheckers = SetsKt.emptySet();
    private final Set<FirDeclarationChecker<FirFile>> fileCheckers = SetsKt.emptySet();
    private final Set<FirDeclarationChecker<FirTypeParameter>> typeParameterCheckers = SetsKt.emptySet();
    private final Set<FirDeclarationChecker<FirTypeAlias>> typeAliasCheckers = SetsKt.emptySet();
    private final Set<FirDeclarationChecker<FirAnonymousFunction>> anonymousFunctionCheckers = SetsKt.emptySet();
    private final Set<FirDeclarationChecker<FirPropertyAccessor>> propertyAccessorCheckers = SetsKt.emptySet();
    private final Set<FirDeclarationChecker<FirBackingField>> backingFieldCheckers = SetsKt.emptySet();
    private final Set<FirDeclarationChecker<FirValueParameter>> valueParameterCheckers = SetsKt.emptySet();
    private final Set<FirDeclarationChecker<FirEnumEntry>> enumEntryCheckers = SetsKt.emptySet();
    private final Set<FirDeclarationChecker<FirAnonymousObject>> anonymousObjectCheckers = SetsKt.emptySet();
    private final Set<FirDeclarationChecker<FirAnonymousInitializer>> anonymousInitializerCheckers = SetsKt.emptySet();
    private final Set<FirControlFlowChecker> controlFlowAnalyserCheckers = SetsKt.emptySet();
    private final Set<AbstractFirPropertyInitializationChecker> variableAssignmentCfaBasedCheckers = SetsKt.emptySet();

    /* JADX INFO: renamed from: allBasicDeclarationCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allBasicDeclarationCheckers = LazyKt.lazy(new Function0<Set<? extends FirDeclarationChecker<? super FirDeclaration>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers$allBasicDeclarationCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirDeclarationChecker<? super FirDeclaration>> invoke() {
            return this.this$0.getBasicDeclarationCheckers();
        }
    });

    /* JADX INFO: renamed from: allCallableDeclarationCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allCallableDeclarationCheckers = LazyKt.lazy(new Function0<Set<? extends FirDeclarationChecker<? super FirCallableDeclaration>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers$allCallableDeclarationCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirDeclarationChecker<? super FirCallableDeclaration>> invoke() {
            return SetsKt.plus((Set) this.this$0.getCallableDeclarationCheckers(), (Iterable) this.this$0.getBasicDeclarationCheckers());
        }
    });

    /* JADX INFO: renamed from: allFunctionCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allFunctionCheckers = LazyKt.lazy(new Function0<Set<? extends FirDeclarationChecker<? super FirFunction>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers$allFunctionCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirDeclarationChecker<? super FirFunction>> invoke() {
            return SetsKt.plus(SetsKt.plus((Set) this.this$0.getFunctionCheckers(), (Iterable) this.this$0.getCallableDeclarationCheckers()), (Iterable) this.this$0.getBasicDeclarationCheckers());
        }
    });

    /* JADX INFO: renamed from: allSimpleFunctionCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allSimpleFunctionCheckers = LazyKt.lazy(new Function0<Set<? extends FirDeclarationChecker<? super FirSimpleFunction>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers$allSimpleFunctionCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirDeclarationChecker<? super FirSimpleFunction>> invoke() {
            return SetsKt.plus(SetsKt.plus(SetsKt.plus((Set) this.this$0.getSimpleFunctionCheckers(), (Iterable) this.this$0.getFunctionCheckers()), (Iterable) this.this$0.getCallableDeclarationCheckers()), (Iterable) this.this$0.getBasicDeclarationCheckers());
        }
    });

    /* JADX INFO: renamed from: allPropertyCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allPropertyCheckers = LazyKt.lazy(new Function0<Set<? extends FirDeclarationChecker<? super FirProperty>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers$allPropertyCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirDeclarationChecker<? super FirProperty>> invoke() {
            return SetsKt.plus(SetsKt.plus((Set) this.this$0.getPropertyCheckers(), (Iterable) this.this$0.getCallableDeclarationCheckers()), (Iterable) this.this$0.getBasicDeclarationCheckers());
        }
    });

    /* JADX INFO: renamed from: allClassLikeCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allClassLikeCheckers = LazyKt.lazy(new Function0<Set<? extends FirDeclarationChecker<? super FirClassLikeDeclaration>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers$allClassLikeCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirDeclarationChecker<? super FirClassLikeDeclaration>> invoke() {
            return SetsKt.plus((Set) this.this$0.getClassLikeCheckers(), (Iterable) this.this$0.getBasicDeclarationCheckers());
        }
    });

    /* JADX INFO: renamed from: allClassCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allClassCheckers = LazyKt.lazy(new Function0<Set<? extends FirDeclarationChecker<? super FirClass>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers$allClassCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirDeclarationChecker<? super FirClass>> invoke() {
            return SetsKt.plus(SetsKt.plus((Set) this.this$0.getClassCheckers(), (Iterable) this.this$0.getClassLikeCheckers()), (Iterable) this.this$0.getBasicDeclarationCheckers());
        }
    });

    /* JADX INFO: renamed from: allRegularClassCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allRegularClassCheckers = LazyKt.lazy(new Function0<Set<? extends FirDeclarationChecker<? super FirRegularClass>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers$allRegularClassCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirDeclarationChecker<? super FirRegularClass>> invoke() {
            return SetsKt.plus(SetsKt.plus(SetsKt.plus((Set) this.this$0.getRegularClassCheckers(), (Iterable) this.this$0.getClassCheckers()), (Iterable) this.this$0.getClassLikeCheckers()), (Iterable) this.this$0.getBasicDeclarationCheckers());
        }
    });

    /* JADX INFO: renamed from: allConstructorCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allConstructorCheckers = LazyKt.lazy(new Function0<Set<? extends FirDeclarationChecker<? super FirConstructor>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers$allConstructorCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirDeclarationChecker<? super FirConstructor>> invoke() {
            return SetsKt.plus(SetsKt.plus(SetsKt.plus((Set) this.this$0.getConstructorCheckers(), (Iterable) this.this$0.getFunctionCheckers()), (Iterable) this.this$0.getCallableDeclarationCheckers()), (Iterable) this.this$0.getBasicDeclarationCheckers());
        }
    });

    /* JADX INFO: renamed from: allFileCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allFileCheckers = LazyKt.lazy(new Function0<Set<? extends FirDeclarationChecker<? super FirFile>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers$allFileCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirDeclarationChecker<? super FirFile>> invoke() {
            return SetsKt.plus((Set) this.this$0.getFileCheckers(), (Iterable) this.this$0.getBasicDeclarationCheckers());
        }
    });

    /* JADX INFO: renamed from: allTypeParameterCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allTypeParameterCheckers = LazyKt.lazy(new Function0<Set<? extends FirDeclarationChecker<? super FirTypeParameter>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers$allTypeParameterCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirDeclarationChecker<? super FirTypeParameter>> invoke() {
            return SetsKt.plus((Set) this.this$0.getTypeParameterCheckers(), (Iterable) this.this$0.getBasicDeclarationCheckers());
        }
    });

    /* JADX INFO: renamed from: allTypeAliasCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allTypeAliasCheckers = LazyKt.lazy(new Function0<Set<? extends FirDeclarationChecker<? super FirTypeAlias>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers$allTypeAliasCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirDeclarationChecker<? super FirTypeAlias>> invoke() {
            return SetsKt.plus(SetsKt.plus((Set) this.this$0.getTypeAliasCheckers(), (Iterable) this.this$0.getClassLikeCheckers()), (Iterable) this.this$0.getBasicDeclarationCheckers());
        }
    });

    /* JADX INFO: renamed from: allAnonymousFunctionCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allAnonymousFunctionCheckers = LazyKt.lazy(new Function0<Set<? extends FirDeclarationChecker<? super FirAnonymousFunction>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers$allAnonymousFunctionCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirDeclarationChecker<? super FirAnonymousFunction>> invoke() {
            return SetsKt.plus(SetsKt.plus(SetsKt.plus((Set) this.this$0.getAnonymousFunctionCheckers(), (Iterable) this.this$0.getFunctionCheckers()), (Iterable) this.this$0.getCallableDeclarationCheckers()), (Iterable) this.this$0.getBasicDeclarationCheckers());
        }
    });

    /* JADX INFO: renamed from: allPropertyAccessorCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allPropertyAccessorCheckers = LazyKt.lazy(new Function0<Set<? extends FirDeclarationChecker<? super FirPropertyAccessor>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers$allPropertyAccessorCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirDeclarationChecker<? super FirPropertyAccessor>> invoke() {
            return SetsKt.plus(SetsKt.plus(SetsKt.plus((Set) this.this$0.getPropertyAccessorCheckers(), (Iterable) this.this$0.getFunctionCheckers()), (Iterable) this.this$0.getCallableDeclarationCheckers()), (Iterable) this.this$0.getBasicDeclarationCheckers());
        }
    });

    /* JADX INFO: renamed from: allBackingFieldCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allBackingFieldCheckers = LazyKt.lazy(new Function0<Set<? extends FirDeclarationChecker<? super FirBackingField>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers$allBackingFieldCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirDeclarationChecker<? super FirBackingField>> invoke() {
            return SetsKt.plus(SetsKt.plus((Set) this.this$0.getBackingFieldCheckers(), (Iterable) this.this$0.getCallableDeclarationCheckers()), (Iterable) this.this$0.getBasicDeclarationCheckers());
        }
    });

    /* JADX INFO: renamed from: allValueParameterCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allValueParameterCheckers = LazyKt.lazy(new Function0<Set<? extends FirDeclarationChecker<? super FirValueParameter>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers$allValueParameterCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirDeclarationChecker<? super FirValueParameter>> invoke() {
            return SetsKt.plus(SetsKt.plus((Set) this.this$0.getValueParameterCheckers(), (Iterable) this.this$0.getCallableDeclarationCheckers()), (Iterable) this.this$0.getBasicDeclarationCheckers());
        }
    });

    /* JADX INFO: renamed from: allEnumEntryCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allEnumEntryCheckers = LazyKt.lazy(new Function0<Set<? extends FirDeclarationChecker<? super FirEnumEntry>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers$allEnumEntryCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirDeclarationChecker<? super FirEnumEntry>> invoke() {
            return SetsKt.plus(SetsKt.plus((Set) this.this$0.getEnumEntryCheckers(), (Iterable) this.this$0.getCallableDeclarationCheckers()), (Iterable) this.this$0.getBasicDeclarationCheckers());
        }
    });

    /* JADX INFO: renamed from: allAnonymousObjectCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allAnonymousObjectCheckers = LazyKt.lazy(new Function0<Set<? extends FirDeclarationChecker<? super FirAnonymousObject>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers$allAnonymousObjectCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirDeclarationChecker<? super FirAnonymousObject>> invoke() {
            return SetsKt.plus(SetsKt.plus(SetsKt.plus((Set) this.this$0.getAnonymousObjectCheckers(), (Iterable) this.this$0.getClassCheckers()), (Iterable) this.this$0.getClassLikeCheckers()), (Iterable) this.this$0.getBasicDeclarationCheckers());
        }
    });

    /* JADX INFO: renamed from: allAnonymousInitializerCheckers$delegate, reason: from kotlin metadata */
    private final Lazy allAnonymousInitializerCheckers = LazyKt.lazy(new Function0<Set<? extends FirDeclarationChecker<? super FirAnonymousInitializer>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers$allAnonymousInitializerCheckers$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Set<? extends FirDeclarationChecker<? super FirAnonymousInitializer>> invoke() {
            return SetsKt.plus((Set) this.this$0.getAnonymousInitializerCheckers(), (Iterable) this.this$0.getBasicDeclarationCheckers());
        }
    });

    @CheckersComponentInternal
    public static /* synthetic */ void getAllAnonymousFunctionCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllAnonymousInitializerCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllAnonymousObjectCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllBackingFieldCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllBasicDeclarationCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllCallableDeclarationCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllClassCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllClassLikeCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllConstructorCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllEnumEntryCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllFileCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllFunctionCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllPropertyAccessorCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllPropertyCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllRegularClassCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllSimpleFunctionCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllTypeAliasCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllTypeParameterCheckers$checkers$annotations() {
    }

    @CheckersComponentInternal
    public static /* synthetic */ void getAllValueParameterCheckers$checkers$annotations() {
    }

    /* JADX INFO: compiled from: DeclarationCheckers.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/DeclarationCheckers$Companion;", "", "()V", "EMPTY", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/DeclarationCheckers;", "getEMPTY", "()Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/DeclarationCheckers;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final DeclarationCheckers getEMPTY() {
            return DeclarationCheckers.EMPTY;
        }
    }

    public Set<FirDeclarationChecker<FirDeclaration>> getBasicDeclarationCheckers() {
        return this.basicDeclarationCheckers;
    }

    public Set<FirDeclarationChecker<FirCallableDeclaration>> getCallableDeclarationCheckers() {
        return this.callableDeclarationCheckers;
    }

    public Set<FirDeclarationChecker<FirFunction>> getFunctionCheckers() {
        return this.functionCheckers;
    }

    public Set<FirDeclarationChecker<FirSimpleFunction>> getSimpleFunctionCheckers() {
        return this.simpleFunctionCheckers;
    }

    public Set<FirDeclarationChecker<FirProperty>> getPropertyCheckers() {
        return this.propertyCheckers;
    }

    public Set<FirDeclarationChecker<FirClassLikeDeclaration>> getClassLikeCheckers() {
        return this.classLikeCheckers;
    }

    public Set<FirDeclarationChecker<FirClass>> getClassCheckers() {
        return this.classCheckers;
    }

    public Set<FirDeclarationChecker<FirRegularClass>> getRegularClassCheckers() {
        return this.regularClassCheckers;
    }

    public Set<FirDeclarationChecker<FirConstructor>> getConstructorCheckers() {
        return this.constructorCheckers;
    }

    public Set<FirDeclarationChecker<FirFile>> getFileCheckers() {
        return this.fileCheckers;
    }

    public Set<FirDeclarationChecker<FirTypeParameter>> getTypeParameterCheckers() {
        return this.typeParameterCheckers;
    }

    public Set<FirDeclarationChecker<FirTypeAlias>> getTypeAliasCheckers() {
        return this.typeAliasCheckers;
    }

    public Set<FirDeclarationChecker<FirAnonymousFunction>> getAnonymousFunctionCheckers() {
        return this.anonymousFunctionCheckers;
    }

    public Set<FirDeclarationChecker<FirPropertyAccessor>> getPropertyAccessorCheckers() {
        return this.propertyAccessorCheckers;
    }

    public Set<FirDeclarationChecker<FirBackingField>> getBackingFieldCheckers() {
        return this.backingFieldCheckers;
    }

    public Set<FirDeclarationChecker<FirValueParameter>> getValueParameterCheckers() {
        return this.valueParameterCheckers;
    }

    public Set<FirDeclarationChecker<FirEnumEntry>> getEnumEntryCheckers() {
        return this.enumEntryCheckers;
    }

    public Set<FirDeclarationChecker<FirAnonymousObject>> getAnonymousObjectCheckers() {
        return this.anonymousObjectCheckers;
    }

    public Set<FirDeclarationChecker<FirAnonymousInitializer>> getAnonymousInitializerCheckers() {
        return this.anonymousInitializerCheckers;
    }

    public Set<FirControlFlowChecker> getControlFlowAnalyserCheckers() {
        return this.controlFlowAnalyserCheckers;
    }

    public Set<AbstractFirPropertyInitializationChecker> getVariableAssignmentCfaBasedCheckers() {
        return this.variableAssignmentCfaBasedCheckers;
    }

    public final Set<FirDeclarationChecker<FirDeclaration>> getAllBasicDeclarationCheckers$checkers() {
        return (Set) this.allBasicDeclarationCheckers.getValue();
    }

    public final Set<FirDeclarationChecker<FirCallableDeclaration>> getAllCallableDeclarationCheckers$checkers() {
        return (Set) this.allCallableDeclarationCheckers.getValue();
    }

    public final Set<FirDeclarationChecker<FirFunction>> getAllFunctionCheckers$checkers() {
        return (Set) this.allFunctionCheckers.getValue();
    }

    public final Set<FirDeclarationChecker<FirSimpleFunction>> getAllSimpleFunctionCheckers$checkers() {
        return (Set) this.allSimpleFunctionCheckers.getValue();
    }

    public final Set<FirDeclarationChecker<FirProperty>> getAllPropertyCheckers$checkers() {
        return (Set) this.allPropertyCheckers.getValue();
    }

    public final Set<FirDeclarationChecker<FirClassLikeDeclaration>> getAllClassLikeCheckers$checkers() {
        return (Set) this.allClassLikeCheckers.getValue();
    }

    public final Set<FirDeclarationChecker<FirClass>> getAllClassCheckers$checkers() {
        return (Set) this.allClassCheckers.getValue();
    }

    public final Set<FirDeclarationChecker<FirRegularClass>> getAllRegularClassCheckers$checkers() {
        return (Set) this.allRegularClassCheckers.getValue();
    }

    public final Set<FirDeclarationChecker<FirConstructor>> getAllConstructorCheckers$checkers() {
        return (Set) this.allConstructorCheckers.getValue();
    }

    public final Set<FirDeclarationChecker<FirFile>> getAllFileCheckers$checkers() {
        return (Set) this.allFileCheckers.getValue();
    }

    public final Set<FirDeclarationChecker<FirTypeParameter>> getAllTypeParameterCheckers$checkers() {
        return (Set) this.allTypeParameterCheckers.getValue();
    }

    public final Set<FirDeclarationChecker<FirTypeAlias>> getAllTypeAliasCheckers$checkers() {
        return (Set) this.allTypeAliasCheckers.getValue();
    }

    public final Set<FirDeclarationChecker<FirAnonymousFunction>> getAllAnonymousFunctionCheckers$checkers() {
        return (Set) this.allAnonymousFunctionCheckers.getValue();
    }

    public final Set<FirDeclarationChecker<FirPropertyAccessor>> getAllPropertyAccessorCheckers$checkers() {
        return (Set) this.allPropertyAccessorCheckers.getValue();
    }

    public final Set<FirDeclarationChecker<FirBackingField>> getAllBackingFieldCheckers$checkers() {
        return (Set) this.allBackingFieldCheckers.getValue();
    }

    public final Set<FirDeclarationChecker<FirValueParameter>> getAllValueParameterCheckers$checkers() {
        return (Set) this.allValueParameterCheckers.getValue();
    }

    public final Set<FirDeclarationChecker<FirEnumEntry>> getAllEnumEntryCheckers$checkers() {
        return (Set) this.allEnumEntryCheckers.getValue();
    }

    public final Set<FirDeclarationChecker<FirAnonymousObject>> getAllAnonymousObjectCheckers$checkers() {
        return (Set) this.allAnonymousObjectCheckers.getValue();
    }

    public final Set<FirDeclarationChecker<FirAnonymousInitializer>> getAllAnonymousInitializerCheckers$checkers() {
        return (Set) this.allAnonymousInitializerCheckers.getValue();
    }
}
