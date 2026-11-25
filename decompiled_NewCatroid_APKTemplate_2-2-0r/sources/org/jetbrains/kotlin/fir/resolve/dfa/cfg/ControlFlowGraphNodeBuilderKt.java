package org.jetbrains.kotlin.fir.resolve.dfa.cfg;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousInitializer;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirCodeFragment;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirScript;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.expressions.FirAnonymousFunctionExpression;
import org.jetbrains.kotlin.fir.expressions.FirAnonymousObjectExpression;
import org.jetbrains.kotlin.fir.expressions.FirBinaryLogicExpression;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess;
import org.jetbrains.kotlin.fir.expressions.FirCatch;
import org.jetbrains.kotlin.fir.expressions.FirCheckNotNullCall;
import org.jetbrains.kotlin.fir.expressions.FirComparisonExpression;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.expressions.FirElvisExpression;
import org.jetbrains.kotlin.fir.expressions.FirEqualityOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirGetClassCall;
import org.jetbrains.kotlin.fir.expressions.FirJump;
import org.jetbrains.kotlin.fir.expressions.FirLoop;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.expressions.FirSafeCallExpression;
import org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirStringConcatenationCall;
import org.jetbrains.kotlin.fir.expressions.FirThrowExpression;
import org.jetbrains.kotlin.fir.expressions.FirTryExpression;
import org.jetbrains.kotlin.fir.expressions.FirTypeOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirVariableAssignment;
import org.jetbrains.kotlin.fir.expressions.FirWhenBranch;
import org.jetbrains.kotlin.fir.expressions.FirWhenExpression;
import org.jetbrains.kotlin.fir.expressions.FirWhenSubjectExpression;

/* JADX INFO: compiled from: ControlFlowGraphNodeBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000°\u0005\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0012\u0010\u0005\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0007\u001a\u0012\u0010\b\u001a\u00020\t*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\n\u001a\u0012\u0010\u000b\u001a\u00020\f*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\r\u001a\u0012\u0010\u000e\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\r\u001a\u0012\u0010\u0010\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\r\u001a\u0012\u0010\u0012\u001a\u00020\u0013*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\r\u001a\u0012\u0010\u0014\u001a\u00020\u0015*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\r\u001a\u0012\u0010\u0016\u001a\u00020\u0017*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\r\u001a\u0012\u0010\u0018\u001a\u00020\u0019*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\r\u001a\u0012\u0010\u001a\u001a\u00020\u001b*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\r\u001a\u0012\u0010\u001c\u001a\u00020\u001d*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u001e\u001a\u0012\u0010\u001f\u001a\u00020 *\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u001e\u001a\u0012\u0010!\u001a\u00020\"*\u00020\u00022\u0006\u0010\u0003\u001a\u00020#\u001a\u0012\u0010$\u001a\u00020%*\u00020\u00022\u0006\u0010\u0003\u001a\u00020&\u001a\u0012\u0010'\u001a\u00020(*\u00020\u00022\u0006\u0010\u0003\u001a\u00020&\u001a\u0012\u0010)\u001a\u00020**\u00020\u00022\u0006\u0010\u0003\u001a\u00020+\u001a\u0012\u0010,\u001a\u00020-*\u00020\u00022\u0006\u0010\u0003\u001a\u00020.\u001a\u0012\u0010/\u001a\u000200*\u00020\u00022\u0006\u0010\u0003\u001a\u00020.\u001a\u0012\u00101\u001a\u000202*\u00020\u00022\u0006\u0010\u0003\u001a\u000203\u001a\u0012\u00104\u001a\u000205*\u00020\u00022\u0006\u0010\u0003\u001a\u000203\u001a\u0012\u00106\u001a\u000207*\u00020\u00022\u0006\u0010\u0003\u001a\u000208\u001a\u0016\u00109\u001a\u00020:*\u00020\u00022\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030;\u001a\u0012\u0010<\u001a\u00020=*\u00020\u00022\u0006\u0010\u0003\u001a\u00020>\u001a\u0012\u0010?\u001a\u00020@*\u00020\u00022\u0006\u0010\u0003\u001a\u00020A\u001a\u0012\u0010B\u001a\u00020C*\u00020\u00022\u0006\u0010\u0003\u001a\u00020D\u001a\u0012\u0010E\u001a\u00020F*\u00020\u00022\u0006\u0010\u0003\u001a\u00020D\u001a\u0012\u0010G\u001a\u00020H*\u00020\u00022\u0006\u0010\u0003\u001a\u00020D\u001a\u0012\u0010I\u001a\u00020J*\u00020\u00022\u0006\u0010\u0003\u001a\u00020D\u001a\u0012\u0010K\u001a\u00020L*\u00020\u00022\u0006\u0010\u0003\u001a\u00020M\u001a\u0012\u0010N\u001a\u00020O*\u00020\u00022\u0006\u0010\u0003\u001a\u00020P\u001a\u0012\u0010Q\u001a\u00020R*\u00020\u00022\u0006\u0010\u0003\u001a\u00020M\u001a\u0012\u0010S\u001a\u00020T*\u00020\u00022\u0006\u0010\u0003\u001a\u00020U\u001a\u0012\u0010V\u001a\u00020W*\u00020\u00022\u0006\u0010\u0003\u001a\u00020M\u001a\u0012\u0010X\u001a\u00020Y*\u00020\u00022\u0006\u0010\u0003\u001a\u00020P\u001a\u0012\u0010Z\u001a\u00020[*\u00020\u00022\u0006\u0010\u0003\u001a\u00020M\u001a\n\u0010\\\u001a\u00020]*\u00020\u0002\u001a\u0012\u0010^\u001a\u00020_*\u00020\u00022\u0006\u0010\u0003\u001a\u00020`\u001a\u0012\u0010a\u001a\u00020b*\u00020\u00022\u0006\u0010\u0003\u001a\u00020`\u001a\u0012\u0010c\u001a\u00020d*\u00020\u00022\u0006\u0010\u0003\u001a\u00020e\u001a\u0012\u0010f\u001a\u00020g*\u00020\u00022\u0006\u0010\u0003\u001a\u00020e\u001a\u0012\u0010h\u001a\u00020i*\u00020\u00022\u0006\u0010\u0003\u001a\u00020j\u001a\u0012\u0010k\u001a\u00020l*\u00020\u00022\u0006\u0010\u0003\u001a\u00020m\u001a\u0012\u0010n\u001a\u00020o*\u00020\u00022\u0006\u0010\u0003\u001a\u00020m\u001a\u0012\u0010p\u001a\u00020q*\u00020\u00022\u0006\u0010\u0003\u001a\u00020r\u001a\u0012\u0010s\u001a\u00020t*\u00020\u00022\u0006\u0010\u0003\u001a\u00020u\u001a\u0012\u0010v\u001a\u00020w*\u00020\u00022\u0006\u0010\u0003\u001a\u00020u\u001a\u0016\u0010x\u001a\u00020y*\u00020\u00022\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030z\u001a\u0012\u0010{\u001a\u00020|*\u00020\u00022\u0006\u0010\u0003\u001a\u00020}\u001a\u0012\u0010~\u001a\u00020\u007f*\u00020\u00022\u0006\u0010\u0003\u001a\u00020m\u001a\u0015\u0010\u0080\u0001\u001a\u00030\u0081\u0001*\u00020\u00022\u0007\u0010\u0003\u001a\u00030\u0082\u0001\u001a\u0015\u0010\u0083\u0001\u001a\u00030\u0084\u0001*\u00020\u00022\u0007\u0010\u0003\u001a\u00030\u0082\u0001\u001a\u001e\u0010\u0085\u0001\u001a\u00030\u0086\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020>2\b\u0010\u0087\u0001\u001a\u00030\u0082\u0001\u001a\u0014\u0010\u0088\u0001\u001a\u00030\u0089\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020>\u001a\u0015\u0010\u008a\u0001\u001a\u00030\u008b\u0001*\u00020\u00022\u0007\u0010\u0003\u001a\u00030\u0082\u0001\u001a\u0015\u0010\u008c\u0001\u001a\u00030\u008d\u0001*\u00020\u00022\u0007\u0010\u0003\u001a\u00030\u0082\u0001\u001a\u0015\u0010\u008e\u0001\u001a\u00030\u008f\u0001*\u00020\u00022\u0007\u0010\u0003\u001a\u00030\u0090\u0001\u001a\u0014\u0010\u0091\u0001\u001a\u00030\u0092\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0015\u0010\u0093\u0001\u001a\u00030\u0094\u0001*\u00020\u00022\u0007\u0010\u0003\u001a\u00030\u0095\u0001\u001a\u0015\u0010\u0096\u0001\u001a\u00030\u0097\u0001*\u00020\u00022\u0007\u0010\u0003\u001a\u00030\u0095\u0001\u001a\u0015\u0010\u0098\u0001\u001a\u00030\u0099\u0001*\u00020\u00022\u0007\u0010\u0003\u001a\u00030\u009a\u0001\u001a\u0015\u0010\u009b\u0001\u001a\u00030\u009c\u0001*\u00020\u00022\u0007\u0010\u0003\u001a\u00030\u009d\u0001\u001a\u0015\u0010\u009e\u0001\u001a\u00030\u009f\u0001*\u00020\u00022\u0007\u0010\u0003\u001a\u00030 \u0001\u001a\u0015\u0010¡\u0001\u001a\u00030¢\u0001*\u00020\u00022\u0007\u0010\u0003\u001a\u00030 \u0001\u001a\u0015\u0010£\u0001\u001a\u00030¤\u0001*\u00020\u00022\u0007\u0010\u0003\u001a\u00030¥\u0001\u001a&\u0010¦\u0001\u001a\u00030§\u0001*\u00020\u00022\u0007\u0010\u0003\u001a\u00030¨\u00012\u000f\u0010©\u0001\u001a\n\u0012\u0005\u0012\u00030«\u00010ª\u0001\u001a\u0015\u0010¬\u0001\u001a\u00030\u00ad\u0001*\u00020\u00022\u0007\u0010\u0003\u001a\u00030®\u0001\u001a\f\u0010¯\u0001\u001a\u00030°\u0001*\u00020\u0002\u001a\u0015\u0010±\u0001\u001a\u00030²\u0001*\u00020\u00022\u0007\u0010\u0003\u001a\u00030³\u0001\u001a\u0014\u0010´\u0001\u001a\u00030µ\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020e\u001a\u0014\u0010¶\u0001\u001a\u00030·\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020e\u001a\u0014\u0010¸\u0001\u001a\u00030¹\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020e\u001a\u0014\u0010º\u0001\u001a\u00030»\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020e\u001a\u0015\u0010¼\u0001\u001a\u00030½\u0001*\u00020\u00022\u0007\u0010\u0003\u001a\u00030¾\u0001\u001a\u0015\u0010¿\u0001\u001a\u00030À\u0001*\u00020\u00022\u0007\u0010\u0003\u001a\u00030Á\u0001\u001a\u0015\u0010Â\u0001\u001a\u00030Ã\u0001*\u00020\u00022\u0007\u0010\u0003\u001a\u00030\u0095\u0001\u001a\u0015\u0010Ä\u0001\u001a\u00030Å\u0001*\u00020\u00022\u0007\u0010\u0003\u001a\u00030Æ\u0001\u001a\u0015\u0010Ç\u0001\u001a\u00030È\u0001*\u00020\u00022\u0007\u0010\u0003\u001a\u00030Æ\u0001\u001a\u0015\u0010É\u0001\u001a\u00030Ê\u0001*\u00020\u00022\u0007\u0010\u0003\u001a\u00030Æ\u0001\u001a\u0015\u0010Ë\u0001\u001a\u00030Ì\u0001*\u00020\u00022\u0007\u0010\u0003\u001a\u00030Æ\u0001\u001a\u0015\u0010Í\u0001\u001a\u00030Î\u0001*\u00020\u00022\u0007\u0010\u0003\u001a\u00030Ï\u0001\u001a\u0015\u0010Ð\u0001\u001a\u00030Ñ\u0001*\u00020\u00022\u0007\u0010\u0003\u001a\u00030Ï\u0001\u001a\u0015\u0010Ò\u0001\u001a\u00030Ó\u0001*\u00020\u00022\u0007\u0010\u0003\u001a\u00030Ô\u0001\u001a\u0015\u0010Õ\u0001\u001a\u00030Ö\u0001*\u00020\u00022\u0007\u0010\u0003\u001a\u00030Ï\u0001¨\u0006×\u0001"}, d2 = {"createAnonymousFunctionExpressionNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/AnonymousFunctionExpressionNode;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraphBuilder;", "fir", "Lorg/jetbrains/kotlin/fir/expressions/FirAnonymousFunctionExpression;", "createAnonymousObjectEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/AnonymousObjectEnterNode;", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousObject;", "createAnonymousObjectExpressionExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/AnonymousObjectExpressionExitNode;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnonymousObjectExpression;", "createBinaryAndEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BinaryAndEnterNode;", "Lorg/jetbrains/kotlin/fir/expressions/FirBinaryLogicExpression;", "createBinaryAndEnterRightOperandNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BinaryAndEnterRightOperandNode;", "createBinaryAndExitLeftOperandNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BinaryAndExitLeftOperandNode;", "createBinaryAndExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BinaryAndExitNode;", "createBinaryOrEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BinaryOrEnterNode;", "createBinaryOrEnterRightOperandNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BinaryOrEnterRightOperandNode;", "createBinaryOrExitLeftOperandNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BinaryOrExitLeftOperandNode;", "createBinaryOrExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BinaryOrExitNode;", "createBlockEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BlockEnterNode;", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "createBlockExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BlockExitNode;", "createCallableReferenceNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CallableReferenceNode;", "Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;", "createCatchClauseEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CatchClauseEnterNode;", "Lorg/jetbrains/kotlin/fir/expressions/FirCatch;", "createCatchClauseExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CatchClauseExitNode;", "createCheckNotNullCallNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CheckNotNullCallNode;", "Lorg/jetbrains/kotlin/fir/expressions/FirCheckNotNullCall;", "createClassEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ClassEnterNode;", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "createClassExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ClassExitNode;", "createCodeFragmentEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CodeFragmentEnterNode;", "Lorg/jetbrains/kotlin/fir/declarations/FirCodeFragment;", "createCodeFragmentExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CodeFragmentExitNode;", "createComparisonExpressionNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ComparisonExpressionNode;", "Lorg/jetbrains/kotlin/fir/expressions/FirComparisonExpression;", "createConstExpressionNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ConstExpressionNode;", "Lorg/jetbrains/kotlin/fir/expressions/FirConstExpression;", "createDelegateExpressionExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/DelegateExpressionExitNode;", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "createDelegatedConstructorCallNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/DelegatedConstructorCallNode;", "Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;", "createElvisExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ElvisExitNode;", "Lorg/jetbrains/kotlin/fir/expressions/FirElvisExpression;", "createElvisLhsExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ElvisLhsExitNode;", "createElvisLhsIsNotNullNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ElvisLhsIsNotNullNode;", "createElvisRhsEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ElvisRhsEnterNode;", "createEnterDefaultArgumentsNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EnterDefaultArgumentsNode;", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "createEnterSafeCallNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EnterSafeCallNode;", "Lorg/jetbrains/kotlin/fir/expressions/FirSafeCallExpression;", "createEnterValueParameterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EnterValueParameterNode;", "createEqualityOperatorCallNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EqualityOperatorCallNode;", "Lorg/jetbrains/kotlin/fir/expressions/FirEqualityOperatorCall;", "createExitDefaultArgumentsNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ExitDefaultArgumentsNode;", "createExitSafeCallNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ExitSafeCallNode;", "createExitValueParameterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ExitValueParameterNode;", "createFakeExpressionEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FakeExpressionEnterNode;", "createFieldInitializerEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FieldInitializerEnterNode;", "Lorg/jetbrains/kotlin/fir/declarations/FirField;", "createFieldInitializerExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FieldInitializerExitNode;", "createFinallyBlockEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FinallyBlockEnterNode;", "Lorg/jetbrains/kotlin/fir/expressions/FirTryExpression;", "createFinallyBlockExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FinallyBlockExitNode;", "createFunctionCallNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FunctionCallNode;", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "createFunctionEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FunctionEnterNode;", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "createFunctionExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FunctionExitNode;", "createGetClassCallNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/GetClassCallNode;", "Lorg/jetbrains/kotlin/fir/expressions/FirGetClassCall;", "createInitBlockEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/InitBlockEnterNode;", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousInitializer;", "createInitBlockExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/InitBlockExitNode;", "createJumpNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/JumpNode;", "Lorg/jetbrains/kotlin/fir/expressions/FirJump;", "createLocalClassExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LocalClassExitNode;", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "createLocalFunctionDeclarationNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LocalFunctionDeclarationNode;", "createLoopBlockEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopBlockEnterNode;", "Lorg/jetbrains/kotlin/fir/expressions/FirLoop;", "createLoopBlockExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopBlockExitNode;", "createLoopConditionEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopConditionEnterNode;", "loop", "createLoopConditionExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopConditionExitNode;", "createLoopEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopEnterNode;", "createLoopExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopExitNode;", "createMergePostponedLambdaExitsNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/MergePostponedLambdaExitsNode;", "Lorg/jetbrains/kotlin/fir/FirElement;", "createPostponedLambdaExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/PostponedLambdaExitNode;", "createPropertyInitializerEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/PropertyInitializerEnterNode;", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "createPropertyInitializerExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/PropertyInitializerExitNode;", "createQualifiedAccessNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/QualifiedAccessNode;", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "createResolvedQualifierNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ResolvedQualifierNode;", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;", "createScriptEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ScriptEnterNode;", "Lorg/jetbrains/kotlin/fir/declarations/FirScript;", "createScriptExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ScriptExitNode;", "createSmartCastExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/SmartCastExpressionExitNode;", "Lorg/jetbrains/kotlin/fir/expressions/FirSmartCastExpression;", "createSplitPostponedLambdasNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/SplitPostponedLambdasNode;", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "lambdas", "", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", "createStringConcatenationCallNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/StringConcatenationCallNode;", "Lorg/jetbrains/kotlin/fir/expressions/FirStringConcatenationCall;", "createStubNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/StubNode;", "createThrowExceptionNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ThrowExceptionNode;", "Lorg/jetbrains/kotlin/fir/expressions/FirThrowExpression;", "createTryExpressionEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/TryExpressionEnterNode;", "createTryExpressionExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/TryExpressionExitNode;", "createTryMainBlockEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/TryMainBlockEnterNode;", "createTryMainBlockExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/TryMainBlockExitNode;", "createTypeOperatorCallNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/TypeOperatorCallNode;", "Lorg/jetbrains/kotlin/fir/expressions/FirTypeOperatorCall;", "createVariableAssignmentNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/VariableAssignmentNode;", "Lorg/jetbrains/kotlin/fir/expressions/FirVariableAssignment;", "createVariableDeclarationNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/VariableDeclarationNode;", "createWhenBranchConditionEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenBranchConditionEnterNode;", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenBranch;", "createWhenBranchConditionExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenBranchConditionExitNode;", "createWhenBranchResultEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenBranchResultEnterNode;", "createWhenBranchResultExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenBranchResultExitNode;", "createWhenEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenEnterNode;", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenExpression;", "createWhenExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenExitNode;", "createWhenSubjectExpressionExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenSubjectExpressionExitNode;", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenSubjectExpression;", "createWhenSyntheticElseBranchNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenSyntheticElseBranchNode;", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ControlFlowGraphNodeBuilderKt {
    public static final StubNode createStubNode(ControlFlowGraphBuilder controlFlowGraphBuilder) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        return new StubNode(controlFlowGraphBuilder.getCurrentGraph(), controlFlowGraphBuilder.getLevelCounter());
    }

    public static final FakeExpressionEnterNode createFakeExpressionEnterNode(ControlFlowGraphBuilder controlFlowGraphBuilder) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        return new FakeExpressionEnterNode(controlFlowGraphBuilder.getCurrentGraph(), controlFlowGraphBuilder.getLevelCounter());
    }

    public static final LoopExitNode createLoopExitNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirLoop fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new LoopExitNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final LoopEnterNode createLoopEnterNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirLoop fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new LoopEnterNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final InitBlockEnterNode createInitBlockEnterNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirAnonymousInitializer fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new InitBlockEnterNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final InitBlockExitNode createInitBlockExitNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirAnonymousInitializer fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new InitBlockExitNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final TypeOperatorCallNode createTypeOperatorCallNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirTypeOperatorCall fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new TypeOperatorCallNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final EqualityOperatorCallNode createEqualityOperatorCallNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirEqualityOperatorCall fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new EqualityOperatorCallNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final WhenBranchConditionExitNode createWhenBranchConditionExitNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirWhenBranch fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new WhenBranchConditionExitNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final JumpNode createJumpNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirJump<?> fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new JumpNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final CheckNotNullCallNode createCheckNotNullCallNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirCheckNotNullCall fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new CheckNotNullCallNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final QualifiedAccessNode createQualifiedAccessNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirQualifiedAccessExpression fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new QualifiedAccessNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final ResolvedQualifierNode createResolvedQualifierNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirResolvedQualifier fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new ResolvedQualifierNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final BlockEnterNode createBlockEnterNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirBlock fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new BlockEnterNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final BlockExitNode createBlockExitNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirBlock fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new BlockExitNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final PropertyInitializerExitNode createPropertyInitializerExitNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirProperty fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new PropertyInitializerExitNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final PropertyInitializerEnterNode createPropertyInitializerEnterNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirProperty fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new PropertyInitializerEnterNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final DelegateExpressionExitNode createDelegateExpressionExitNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirExpression fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new DelegateExpressionExitNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final FieldInitializerExitNode createFieldInitializerExitNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirField fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new FieldInitializerExitNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final FieldInitializerEnterNode createFieldInitializerEnterNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirField fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new FieldInitializerEnterNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final FunctionEnterNode createFunctionEnterNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirFunction fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new FunctionEnterNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final FunctionExitNode createFunctionExitNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirFunction fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new FunctionExitNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final LocalFunctionDeclarationNode createLocalFunctionDeclarationNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirFunction fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new LocalFunctionDeclarationNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final BinaryOrEnterNode createBinaryOrEnterNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirBinaryLogicExpression fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new BinaryOrEnterNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final BinaryOrExitLeftOperandNode createBinaryOrExitLeftOperandNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirBinaryLogicExpression fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new BinaryOrExitLeftOperandNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final BinaryOrExitNode createBinaryOrExitNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirBinaryLogicExpression fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new BinaryOrExitNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final BinaryAndExitNode createBinaryAndExitNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirBinaryLogicExpression fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new BinaryAndExitNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final BinaryAndEnterNode createBinaryAndEnterNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirBinaryLogicExpression fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new BinaryAndEnterNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final WhenBranchConditionEnterNode createWhenBranchConditionEnterNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirWhenBranch fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new WhenBranchConditionEnterNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final WhenEnterNode createWhenEnterNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirWhenExpression fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new WhenEnterNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final WhenExitNode createWhenExitNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirWhenExpression fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new WhenExitNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final WhenBranchResultExitNode createWhenBranchResultExitNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirWhenBranch fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new WhenBranchResultExitNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final WhenSyntheticElseBranchNode createWhenSyntheticElseBranchNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirWhenExpression fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new WhenSyntheticElseBranchNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final WhenBranchResultEnterNode createWhenBranchResultEnterNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirWhenBranch fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new WhenBranchResultEnterNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final LoopConditionExitNode createLoopConditionExitNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirExpression fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new LoopConditionExitNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final LoopConditionEnterNode createLoopConditionEnterNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirExpression fir, FirLoop loop) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        Intrinsics.checkNotNullParameter(loop, "loop");
        return new LoopConditionEnterNode(controlFlowGraphBuilder.getCurrentGraph(), fir, loop, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final LoopBlockEnterNode createLoopBlockEnterNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirLoop fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new LoopBlockEnterNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final LoopBlockExitNode createLoopBlockExitNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirLoop fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new LoopBlockExitNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final FunctionCallNode createFunctionCallNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirFunctionCall fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new FunctionCallNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final CallableReferenceNode createCallableReferenceNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirCallableReferenceAccess fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new CallableReferenceNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final GetClassCallNode createGetClassCallNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirGetClassCall fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new GetClassCallNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final DelegatedConstructorCallNode createDelegatedConstructorCallNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirDelegatedConstructorCall fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new DelegatedConstructorCallNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final StringConcatenationCallNode createStringConcatenationCallNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirStringConcatenationCall fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new StringConcatenationCallNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final VariableAssignmentNode createVariableAssignmentNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirVariableAssignment fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new VariableAssignmentNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final ElvisLhsIsNotNullNode createElvisLhsIsNotNullNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirElvisExpression fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new ElvisLhsIsNotNullNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final ElvisRhsEnterNode createElvisRhsEnterNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirElvisExpression fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new ElvisRhsEnterNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final ElvisLhsExitNode createElvisLhsExitNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirElvisExpression fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new ElvisLhsExitNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final WhenSubjectExpressionExitNode createWhenSubjectExpressionExitNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirWhenSubjectExpression fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new WhenSubjectExpressionExitNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final ElvisExitNode createElvisExitNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirElvisExpression fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new ElvisExitNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final VariableDeclarationNode createVariableDeclarationNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirProperty fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new VariableDeclarationNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final ConstExpressionNode createConstExpressionNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirConstExpression<?> fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new ConstExpressionNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final ThrowExceptionNode createThrowExceptionNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirThrowExpression fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new ThrowExceptionNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final FinallyBlockExitNode createFinallyBlockExitNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirTryExpression fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new FinallyBlockExitNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final FinallyBlockEnterNode createFinallyBlockEnterNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirTryExpression fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new FinallyBlockEnterNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final CatchClauseExitNode createCatchClauseExitNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirCatch fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new CatchClauseExitNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final TryMainBlockExitNode createTryMainBlockExitNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirTryExpression fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new TryMainBlockExitNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final TryMainBlockEnterNode createTryMainBlockEnterNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirTryExpression fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new TryMainBlockEnterNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final CatchClauseEnterNode createCatchClauseEnterNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirCatch fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new CatchClauseEnterNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final TryExpressionEnterNode createTryExpressionEnterNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirTryExpression fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new TryExpressionEnterNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final TryExpressionExitNode createTryExpressionExitNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirTryExpression fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new TryExpressionExitNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final BinaryAndExitLeftOperandNode createBinaryAndExitLeftOperandNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirBinaryLogicExpression fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new BinaryAndExitLeftOperandNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final BinaryAndEnterRightOperandNode createBinaryAndEnterRightOperandNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirBinaryLogicExpression fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new BinaryAndEnterRightOperandNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final BinaryOrEnterRightOperandNode createBinaryOrEnterRightOperandNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirBinaryLogicExpression fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new BinaryOrEnterRightOperandNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final ExitSafeCallNode createExitSafeCallNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirSafeCallExpression fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new ExitSafeCallNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final EnterSafeCallNode createEnterSafeCallNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirSafeCallExpression fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new EnterSafeCallNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final PostponedLambdaExitNode createPostponedLambdaExitNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirAnonymousFunctionExpression fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new PostponedLambdaExitNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final SplitPostponedLambdasNode createSplitPostponedLambdasNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirStatement fir, List<? extends FirAnonymousFunction> lambdas) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        Intrinsics.checkNotNullParameter(lambdas, "lambdas");
        return new SplitPostponedLambdasNode(controlFlowGraphBuilder.getCurrentGraph(), fir, lambdas, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final MergePostponedLambdaExitsNode createMergePostponedLambdaExitsNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirElement fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new MergePostponedLambdaExitsNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final AnonymousFunctionExpressionNode createAnonymousFunctionExpressionNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirAnonymousFunctionExpression fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new AnonymousFunctionExpressionNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final AnonymousObjectEnterNode createAnonymousObjectEnterNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirAnonymousObject fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new AnonymousObjectEnterNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final AnonymousObjectExpressionExitNode createAnonymousObjectExpressionExitNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirAnonymousObjectExpression fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new AnonymousObjectExpressionExitNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final ScriptEnterNode createScriptEnterNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirScript fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new ScriptEnterNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final ScriptExitNode createScriptExitNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirScript fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new ScriptExitNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final CodeFragmentEnterNode createCodeFragmentEnterNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirCodeFragment fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new CodeFragmentEnterNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final CodeFragmentExitNode createCodeFragmentExitNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirCodeFragment fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new CodeFragmentExitNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final ClassEnterNode createClassEnterNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirClass fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new ClassEnterNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final ClassExitNode createClassExitNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirClass fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new ClassExitNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final LocalClassExitNode createLocalClassExitNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirRegularClass fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new LocalClassExitNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final EnterValueParameterNode createEnterValueParameterNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirValueParameter fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new EnterValueParameterNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final EnterDefaultArgumentsNode createEnterDefaultArgumentsNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirValueParameter fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new EnterDefaultArgumentsNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final ExitDefaultArgumentsNode createExitDefaultArgumentsNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirValueParameter fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new ExitDefaultArgumentsNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final ExitValueParameterNode createExitValueParameterNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirValueParameter fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new ExitValueParameterNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final ComparisonExpressionNode createComparisonExpressionNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirComparisonExpression fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new ComparisonExpressionNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }

    public static final SmartCastExpressionExitNode createSmartCastExitNode(ControlFlowGraphBuilder controlFlowGraphBuilder, FirSmartCastExpression fir) {
        Intrinsics.checkNotNullParameter(controlFlowGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fir, "fir");
        return new SmartCastExpressionExitNode(controlFlowGraphBuilder.getCurrentGraph(), fir, controlFlowGraphBuilder.getLevelCounter());
    }
}
