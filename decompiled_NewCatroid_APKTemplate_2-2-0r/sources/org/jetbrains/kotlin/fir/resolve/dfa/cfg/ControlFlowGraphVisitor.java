package org.jetbrains.kotlin.fir.resolve.dfa.cfg;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ControlFlowGraphVisitor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000°\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u0001*\u0006\b\u0001\u0010\u0002 \u00002\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004J\u001d\u0010\u0005\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\tJ\u001d\u0010\n\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\fJ\u001d\u0010\r\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020\u000e2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u000fJ\u001d\u0010\u0010\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020\u00112\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u0012J\u001d\u0010\u0013\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020\u00142\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u0015J\u001d\u0010\u0016\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020\u00172\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u0018J\u001d\u0010\u0019\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020\u001a2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u001bJ\u001d\u0010\u001c\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020\u001d2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u001eJ\u001d\u0010\u001f\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020 2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010!J\u001d\u0010\"\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020#2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010$J\u001d\u0010%\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020&2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010'J\u001d\u0010(\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020)2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010*J\u001d\u0010+\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020,2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010-J\u001d\u0010.\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020/2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u00100J\u001d\u00101\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u0002022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u00103J\u001d\u00104\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u0002052\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u00106J\u001d\u00107\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u0002082\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u00109J\u001d\u0010:\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020;2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010<J\u001d\u0010=\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020>2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010?J\u001d\u0010@\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020A2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010BJ\u001d\u0010C\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020D2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010EJ\u001d\u0010F\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020G2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010HJ\u001d\u0010I\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020J2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010KJ\u001d\u0010L\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020M2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010NJ\u001d\u0010O\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020P2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010QJ\u001d\u0010R\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020S2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010TJ\u001d\u0010U\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020V2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010WJ\u001d\u0010X\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020Y2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010ZJ\u001d\u0010[\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020\\2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010]J\u001d\u0010^\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020_2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010`J\u001d\u0010a\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020b2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010cJ\u001d\u0010d\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020e2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010fJ\u001d\u0010g\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020h2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010iJ\u001d\u0010j\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020k2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010lJ\u001d\u0010m\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020n2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010oJ\u001d\u0010p\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020q2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010rJ\u001d\u0010s\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020t2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010uJ\u001d\u0010v\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020w2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010xJ\u001d\u0010y\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020z2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010{J\u001d\u0010|\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020}2\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010~J\u001f\u0010\u007f\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030\u0080\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0081\u0001J \u0010\u0082\u0001\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030\u0083\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0084\u0001J \u0010\u0085\u0001\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030\u0086\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0087\u0001J \u0010\u0088\u0001\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030\u0089\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u008a\u0001J \u0010\u008b\u0001\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030\u008c\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u008d\u0001J \u0010\u008e\u0001\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030\u008f\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0090\u0001J \u0010\u0091\u0001\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030\u0092\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0093\u0001J \u0010\u0094\u0001\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030\u0095\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0096\u0001J \u0010\u0097\u0001\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030\u0098\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0099\u0001J \u0010\u009a\u0001\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030\u009b\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u009c\u0001J \u0010\u009d\u0001\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030\u009e\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u009f\u0001J \u0010 \u0001\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030¡\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010¢\u0001J \u0010£\u0001\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030¤\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010¥\u0001J \u0010¦\u0001\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030§\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010¨\u0001J \u0010©\u0001\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030ª\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010«\u0001J \u0010¬\u0001\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030\u00ad\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010®\u0001J$\u0010¯\u0001\u001a\u00028\u00002\u000b\u0010\u0006\u001a\u0007\u0012\u0002\b\u00030°\u00012\u0006\u0010\b\u001a\u00028\u0001H&¢\u0006\u0003\u0010±\u0001J \u0010²\u0001\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030³\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010´\u0001J \u0010µ\u0001\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030¶\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010·\u0001J \u0010¸\u0001\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030¹\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010º\u0001J \u0010»\u0001\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030¼\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010½\u0001J \u0010¾\u0001\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030¿\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010À\u0001J \u0010Á\u0001\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030Â\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Ã\u0001J \u0010Ä\u0001\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030Å\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Æ\u0001J \u0010Ç\u0001\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030È\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010É\u0001J \u0010Ê\u0001\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030Ë\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Ì\u0001J \u0010Í\u0001\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030Î\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Ï\u0001J \u0010Ð\u0001\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030Ñ\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Ò\u0001J \u0010Ó\u0001\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030Ô\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Õ\u0001J \u0010Ö\u0001\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030×\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Ø\u0001J \u0010Ù\u0001\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030Ú\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Û\u0001J \u0010Ü\u0001\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030Ý\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010Þ\u0001J \u0010ß\u0001\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030à\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010á\u0001J \u0010â\u0001\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030ã\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ä\u0001J \u0010å\u0001\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030æ\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ç\u0001J \u0010è\u0001\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030é\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ê\u0001J \u0010ë\u0001\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030ì\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010í\u0001J \u0010î\u0001\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030ï\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ð\u0001J \u0010ñ\u0001\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030ò\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ó\u0001J \u0010ô\u0001\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030õ\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ö\u0001J \u0010÷\u0001\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030ø\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ù\u0001J \u0010ú\u0001\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030û\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ü\u0001J \u0010ý\u0001\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030þ\u00012\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010ÿ\u0001J \u0010\u0080\u0002\u001a\u00028\u00002\u0007\u0010\u0006\u001a\u00030\u0081\u00022\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0003\u0010\u0082\u0002¨\u0006\u0083\u0002"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraphVisitor;", "R", "D", "", "()V", "visitAnonymousFunctionExpressionNode", "node", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/AnonymousFunctionExpressionNode;", "data", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/AnonymousFunctionExpressionNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitAnonymousObjectEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/AnonymousObjectEnterNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/AnonymousObjectEnterNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitAnonymousObjectExpressionExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/AnonymousObjectExpressionExitNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/AnonymousObjectExpressionExitNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitBinaryAndEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BinaryAndEnterNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BinaryAndEnterNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitBinaryAndEnterRightOperandNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BinaryAndEnterRightOperandNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BinaryAndEnterRightOperandNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitBinaryAndExitLeftOperandNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BinaryAndExitLeftOperandNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BinaryAndExitLeftOperandNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitBinaryAndExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BinaryAndExitNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BinaryAndExitNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitBinaryOrEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BinaryOrEnterNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BinaryOrEnterNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitBinaryOrEnterRightOperandNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BinaryOrEnterRightOperandNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BinaryOrEnterRightOperandNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitBinaryOrExitLeftOperandNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BinaryOrExitLeftOperandNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BinaryOrExitLeftOperandNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitBinaryOrExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BinaryOrExitNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BinaryOrExitNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitBlockEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BlockEnterNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BlockEnterNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitBlockExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BlockExitNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BlockExitNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitCallableReferenceNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CallableReferenceNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CallableReferenceNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitCatchClauseEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CatchClauseEnterNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CatchClauseEnterNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitCatchClauseExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CatchClauseExitNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CatchClauseExitNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitCheckNotNullCallNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CheckNotNullCallNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CheckNotNullCallNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitClassEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ClassEnterNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ClassEnterNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitClassExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ClassExitNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ClassExitNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitCodeFragmentEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CodeFragmentEnterNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CodeFragmentEnterNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitCodeFragmentExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CodeFragmentExitNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CodeFragmentExitNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitComparisonExpressionNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ComparisonExpressionNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ComparisonExpressionNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitConstExpressionNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ConstExpressionNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ConstExpressionNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitDelegateExpressionExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/DelegateExpressionExitNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/DelegateExpressionExitNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitDelegatedConstructorCallNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/DelegatedConstructorCallNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/DelegatedConstructorCallNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitElvisExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ElvisExitNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ElvisExitNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitElvisLhsExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ElvisLhsExitNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ElvisLhsExitNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitElvisLhsIsNotNullNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ElvisLhsIsNotNullNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ElvisLhsIsNotNullNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitElvisRhsEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ElvisRhsEnterNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ElvisRhsEnterNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitEnterDefaultArgumentsNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EnterDefaultArgumentsNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EnterDefaultArgumentsNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitEnterSafeCallNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EnterSafeCallNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EnterSafeCallNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitEnterValueParameterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EnterValueParameterNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EnterValueParameterNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitEqualityOperatorCallNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EqualityOperatorCallNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EqualityOperatorCallNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitExitDefaultArgumentsNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ExitDefaultArgumentsNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ExitDefaultArgumentsNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitExitSafeCallNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ExitSafeCallNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ExitSafeCallNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitExitValueParameterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ExitValueParameterNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ExitValueParameterNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitFieldInitializerEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FieldInitializerEnterNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FieldInitializerEnterNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitFieldInitializerExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FieldInitializerExitNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FieldInitializerExitNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitFinallyBlockEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FinallyBlockEnterNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FinallyBlockEnterNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitFinallyBlockExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FinallyBlockExitNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FinallyBlockExitNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitFunctionCallNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FunctionCallNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FunctionCallNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitFunctionEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FunctionEnterNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FunctionEnterNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitFunctionExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FunctionExitNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FunctionExitNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitGetClassCallNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/GetClassCallNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/GetClassCallNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitInitBlockEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/InitBlockEnterNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/InitBlockEnterNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitInitBlockExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/InitBlockExitNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/InitBlockExitNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitJumpNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/JumpNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/JumpNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitLocalClassExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LocalClassExitNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LocalClassExitNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitLocalFunctionDeclarationNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LocalFunctionDeclarationNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LocalFunctionDeclarationNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitLoopBlockEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopBlockEnterNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopBlockEnterNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitLoopBlockExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopBlockExitNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopBlockExitNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitLoopConditionEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopConditionEnterNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopConditionEnterNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitLoopConditionExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopConditionExitNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopConditionExitNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitLoopEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopEnterNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopEnterNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitLoopExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopExitNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopExitNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitMergePostponedLambdaExitsNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/MergePostponedLambdaExitsNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/MergePostponedLambdaExitsNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitPostponedLambdaExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/PostponedLambdaExitNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/PostponedLambdaExitNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitPropertyInitializerEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/PropertyInitializerEnterNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/PropertyInitializerEnterNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitPropertyInitializerExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/PropertyInitializerExitNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/PropertyInitializerExitNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitQualifiedAccessNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/QualifiedAccessNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/QualifiedAccessNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitResolvedQualifierNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ResolvedQualifierNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ResolvedQualifierNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitScriptEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ScriptEnterNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ScriptEnterNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitScriptExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ScriptExitNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ScriptExitNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitSmartCastExpressionExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/SmartCastExpressionExitNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/SmartCastExpressionExitNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitSplitPostponedLambdasNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/SplitPostponedLambdasNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/SplitPostponedLambdasNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitStringConcatenationCallNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/StringConcatenationCallNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/StringConcatenationCallNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitStubNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/StubNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/StubNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitThrowExceptionNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ThrowExceptionNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ThrowExceptionNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitTryExpressionEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/TryExpressionEnterNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/TryExpressionEnterNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitTryExpressionExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/TryExpressionExitNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/TryExpressionExitNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitTryMainBlockEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/TryMainBlockEnterNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/TryMainBlockEnterNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitTryMainBlockExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/TryMainBlockExitNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/TryMainBlockExitNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitTypeOperatorCallNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/TypeOperatorCallNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/TypeOperatorCallNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitVariableAssignmentNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/VariableAssignmentNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/VariableAssignmentNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitVariableDeclarationNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/VariableDeclarationNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/VariableDeclarationNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitWhenBranchConditionEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenBranchConditionEnterNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenBranchConditionEnterNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitWhenBranchConditionExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenBranchConditionExitNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenBranchConditionExitNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitWhenBranchResultEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenBranchResultEnterNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenBranchResultEnterNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitWhenBranchResultExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenBranchResultExitNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenBranchResultExitNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitWhenEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenEnterNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenEnterNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitWhenExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenExitNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenExitNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitWhenSubjectExpressionExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenSubjectExpressionExitNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenSubjectExpressionExitNode;Ljava/lang/Object;)Ljava/lang/Object;", "visitWhenSyntheticElseBranchNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenSyntheticElseBranchNode;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenSyntheticElseBranchNode;Ljava/lang/Object;)Ljava/lang/Object;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ControlFlowGraphVisitor<R, D> {
    public abstract R visitNode(CFGNode<?> node, D data);

    public R visitFunctionEnterNode(FunctionEnterNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitFunctionExitNode(FunctionExitNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitLocalFunctionDeclarationNode(LocalFunctionDeclarationNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitEnterValueParameterNode(EnterValueParameterNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitEnterDefaultArgumentsNode(EnterDefaultArgumentsNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitExitDefaultArgumentsNode(ExitDefaultArgumentsNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitExitValueParameterNode(ExitValueParameterNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitSplitPostponedLambdasNode(SplitPostponedLambdasNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitPostponedLambdaExitNode(PostponedLambdaExitNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitMergePostponedLambdaExitsNode(MergePostponedLambdaExitsNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitAnonymousFunctionExpressionNode(AnonymousFunctionExpressionNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitAnonymousObjectEnterNode(AnonymousObjectEnterNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitAnonymousObjectExpressionExitNode(AnonymousObjectExpressionExitNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitClassEnterNode(ClassEnterNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitClassExitNode(ClassExitNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitLocalClassExitNode(LocalClassExitNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitScriptEnterNode(ScriptEnterNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitScriptExitNode(ScriptExitNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitCodeFragmentEnterNode(CodeFragmentEnterNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitCodeFragmentExitNode(CodeFragmentExitNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitPropertyInitializerEnterNode(PropertyInitializerEnterNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitPropertyInitializerExitNode(PropertyInitializerExitNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitDelegateExpressionExitNode(DelegateExpressionExitNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitFieldInitializerEnterNode(FieldInitializerEnterNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitFieldInitializerExitNode(FieldInitializerExitNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitInitBlockEnterNode(InitBlockEnterNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitInitBlockExitNode(InitBlockExitNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitBlockEnterNode(BlockEnterNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitBlockExitNode(BlockExitNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitWhenEnterNode(WhenEnterNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitWhenExitNode(WhenExitNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitWhenBranchConditionEnterNode(WhenBranchConditionEnterNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitWhenBranchConditionExitNode(WhenBranchConditionExitNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitWhenBranchResultEnterNode(WhenBranchResultEnterNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitWhenBranchResultExitNode(WhenBranchResultExitNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitWhenSyntheticElseBranchNode(WhenSyntheticElseBranchNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitLoopEnterNode(LoopEnterNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitLoopBlockEnterNode(LoopBlockEnterNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitLoopBlockExitNode(LoopBlockExitNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitLoopConditionEnterNode(LoopConditionEnterNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitLoopConditionExitNode(LoopConditionExitNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitLoopExitNode(LoopExitNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitTryExpressionEnterNode(TryExpressionEnterNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitTryMainBlockEnterNode(TryMainBlockEnterNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitTryMainBlockExitNode(TryMainBlockExitNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitCatchClauseEnterNode(CatchClauseEnterNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitCatchClauseExitNode(CatchClauseExitNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitFinallyBlockEnterNode(FinallyBlockEnterNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitFinallyBlockExitNode(FinallyBlockExitNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitTryExpressionExitNode(TryExpressionExitNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitBinaryAndEnterNode(BinaryAndEnterNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitBinaryAndExitLeftOperandNode(BinaryAndExitLeftOperandNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitBinaryAndEnterRightOperandNode(BinaryAndEnterRightOperandNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitBinaryAndExitNode(BinaryAndExitNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitBinaryOrEnterNode(BinaryOrEnterNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitBinaryOrExitLeftOperandNode(BinaryOrExitLeftOperandNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitBinaryOrEnterRightOperandNode(BinaryOrEnterRightOperandNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitBinaryOrExitNode(BinaryOrExitNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitTypeOperatorCallNode(TypeOperatorCallNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitComparisonExpressionNode(ComparisonExpressionNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitEqualityOperatorCallNode(EqualityOperatorCallNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitJumpNode(JumpNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitConstExpressionNode(ConstExpressionNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitCheckNotNullCallNode(CheckNotNullCallNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitQualifiedAccessNode(QualifiedAccessNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitResolvedQualifierNode(ResolvedQualifierNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitFunctionCallNode(FunctionCallNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitCallableReferenceNode(CallableReferenceNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitGetClassCallNode(GetClassCallNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitDelegatedConstructorCallNode(DelegatedConstructorCallNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitStringConcatenationCallNode(StringConcatenationCallNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitThrowExceptionNode(ThrowExceptionNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitStubNode(StubNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitVariableDeclarationNode(VariableDeclarationNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitVariableAssignmentNode(VariableAssignmentNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitEnterSafeCallNode(EnterSafeCallNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitExitSafeCallNode(ExitSafeCallNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitWhenSubjectExpressionExitNode(WhenSubjectExpressionExitNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitElvisLhsExitNode(ElvisLhsExitNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitElvisLhsIsNotNullNode(ElvisLhsIsNotNullNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitElvisRhsEnterNode(ElvisRhsEnterNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitElvisExitNode(ElvisExitNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }

    public R visitSmartCastExpressionExitNode(SmartCastExpressionExitNode node, D data) {
        Intrinsics.checkNotNullParameter(node, "node");
        return visitNode(node, data);
    }
}
