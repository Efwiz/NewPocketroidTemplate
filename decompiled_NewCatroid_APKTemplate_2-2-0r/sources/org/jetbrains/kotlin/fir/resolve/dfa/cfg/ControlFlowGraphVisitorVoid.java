package org.jetbrains.kotlin.fir.resolve.dfa.cfg;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ControlFlowGraphVisitorVoid.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0084\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010\t\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\nH\u0016J\u0018\u0010\t\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\n2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010\u000b\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\fH\u0016J\u0018\u0010\u000b\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\f2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010\r\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u000eH\u0016J\u0018\u0010\r\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u000e2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0010H\u0016J\u0018\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00102\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010\u0011\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0012H\u0016J\u0018\u0010\u0011\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00122\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0014H\u0016J\u0018\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00142\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010\u0015\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0016H\u0016J\u0018\u0010\u0015\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00162\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010\u0017\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0018H\u0016J\u0018\u0010\u0017\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00182\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010\u0019\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u001aH\u0016J\u0018\u0010\u0019\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u001a2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010\u001b\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u001cH\u0016J\u0018\u0010\u001b\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u001c2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010\u001d\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u001eH\u0016J\u0018\u0010\u001d\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u001e2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010\u001f\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020 H\u0016J\u0018\u0010\u001f\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020 2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010!\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\"H\u0016J\u0018\u0010!\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\"2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010#\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020$H\u0016J\u0018\u0010#\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020$2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010%\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020&H\u0016J\u0018\u0010%\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020&2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010'\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020(H\u0016J\u0018\u0010'\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020(2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010)\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020*H\u0016J\u0018\u0010)\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020*2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010+\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020,H\u0016J\u0018\u0010+\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020,2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010-\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020.H\u0016J\u0018\u0010-\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020.2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010/\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u000200H\u0016J\u0018\u0010/\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u0002002\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u00101\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u000202H\u0016J\u0018\u00101\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u0002022\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u00103\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u000204H\u0016J\u0018\u00103\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u0002042\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u00105\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u000206H\u0016J\u0018\u00105\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u0002062\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u00107\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u000208H\u0016J\u0018\u00107\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u0002082\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u00109\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020:H\u0016J\u0018\u00109\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020:2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010;\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020<H\u0016J\u0018\u0010;\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020<2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010=\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020>H\u0016J\u0018\u0010=\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020>2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010?\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020@H\u0016J\u0018\u0010?\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020@2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010A\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020BH\u0016J\u0018\u0010A\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020B2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010C\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020DH\u0016J\u0018\u0010C\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020D2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010E\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020FH\u0016J\u0018\u0010E\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020F2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010G\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020HH\u0016J\u0018\u0010G\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020H2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010I\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020JH\u0016J\u0018\u0010I\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020J2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010K\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020LH\u0016J\u0018\u0010K\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020L2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010M\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020NH\u0016J\u0018\u0010M\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020N2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010O\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020PH\u0016J\u0018\u0010O\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020P2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0014\u0010Q\u001a\u00020\u00022\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030RH&J\u001c\u0010Q\u001a\u00020\u00022\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030R2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010S\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020TH\u0016J\u0018\u0010S\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020T2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010U\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020VH\u0016J\u0018\u0010U\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020V2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010W\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020XH\u0016J\u0018\u0010W\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020X2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010Y\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020ZH\u0016J\u0018\u0010Y\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020Z2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010[\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\\H\u0016J\u0018\u0010[\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\\2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010]\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020^H\u0016J\u0018\u0010]\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020^2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010_\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020`H\u0016J\u0018\u0010_\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020`2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010a\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020bH\u0016J\u0018\u0010a\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020b2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010c\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020dH\u0016J\u0018\u0010c\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020d2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010e\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020fH\u0016J\u0018\u0010e\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020f2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010g\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020hH\u0016J\u0018\u0010g\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020h2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010i\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020jH\u0016J\u0018\u0010i\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020j2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010k\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020lH\u0016J\u0018\u0010k\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020l2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010m\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020nH\u0016J\u0018\u0010m\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020n2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010o\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020pH\u0016J\u0018\u0010o\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020p2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010q\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020rH\u0016J\u0018\u0010q\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020r2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010s\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020tH\u0016J\u0018\u0010s\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020t2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010u\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020vH\u0016J\u0018\u0010u\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020v2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010w\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020xH\u0016J\u0018\u0010w\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020x2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010y\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020zH\u0016J\u0018\u0010y\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020z2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010{\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020|H\u0016J\u0018\u0010{\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020|2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0010\u0010}\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020~H\u0016J\u0018\u0010}\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020~2\b\u0010\b\u001a\u0004\u0018\u00010\u0003J\u0011\u0010\u007f\u001a\u00020\u00022\u0007\u0010\u0006\u001a\u00030\u0080\u0001H\u0016J\u0019\u0010\u007f\u001a\u00020\u00022\u0007\u0010\u0006\u001a\u00030\u0080\u00012\b\u0010\b\u001a\u0004\u0018\u00010\u0003¨\u0006\u0081\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraphVisitorVoid;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ControlFlowGraphVisitor;", "", "", "()V", "visitAnonymousFunctionExpressionNode", "node", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/AnonymousFunctionExpressionNode;", "data", "visitBinaryAndEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BinaryAndEnterNode;", "visitBinaryAndEnterRightOperandNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BinaryAndEnterRightOperandNode;", "visitBinaryAndExitLeftOperandNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BinaryAndExitLeftOperandNode;", "visitBinaryAndExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BinaryAndExitNode;", "visitBinaryOrEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BinaryOrEnterNode;", "visitBinaryOrEnterRightOperandNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BinaryOrEnterRightOperandNode;", "visitBinaryOrExitLeftOperandNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BinaryOrExitLeftOperandNode;", "visitBinaryOrExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BinaryOrExitNode;", "visitBlockEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BlockEnterNode;", "visitBlockExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/BlockExitNode;", "visitCatchClauseEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CatchClauseEnterNode;", "visitCatchClauseExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CatchClauseExitNode;", "visitCheckNotNullCallNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CheckNotNullCallNode;", "visitComparisonExpressionNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ComparisonExpressionNode;", "visitConstExpressionNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ConstExpressionNode;", "visitDelegateExpressionExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/DelegateExpressionExitNode;", "visitDelegatedConstructorCallNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/DelegatedConstructorCallNode;", "visitEnterSafeCallNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EnterSafeCallNode;", "visitEqualityOperatorCallNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/EqualityOperatorCallNode;", "visitExitSafeCallNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ExitSafeCallNode;", "visitFinallyBlockEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FinallyBlockEnterNode;", "visitFinallyBlockExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FinallyBlockExitNode;", "visitFunctionCallNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FunctionCallNode;", "visitFunctionEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FunctionEnterNode;", "visitFunctionExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FunctionExitNode;", "visitInitBlockEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/InitBlockEnterNode;", "visitInitBlockExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/InitBlockExitNode;", "visitJumpNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/JumpNode;", "visitLocalFunctionDeclarationNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LocalFunctionDeclarationNode;", "visitLoopBlockEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopBlockEnterNode;", "visitLoopBlockExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopBlockExitNode;", "visitLoopConditionEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopConditionEnterNode;", "visitLoopConditionExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopConditionExitNode;", "visitLoopEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopEnterNode;", "visitLoopExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/LoopExitNode;", "visitMergePostponedLambdaExitsNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/MergePostponedLambdaExitsNode;", "visitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode;", "visitPostponedLambdaExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/PostponedLambdaExitNode;", "visitPropertyInitializerEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/PropertyInitializerEnterNode;", "visitPropertyInitializerExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/PropertyInitializerExitNode;", "visitQualifiedAccessNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/QualifiedAccessNode;", "visitResolvedQualifierNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ResolvedQualifierNode;", "visitSplitPostponedLambdasNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/SplitPostponedLambdasNode;", "visitStringConcatenationCallNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/StringConcatenationCallNode;", "visitStubNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/StubNode;", "visitThrowExceptionNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/ThrowExceptionNode;", "visitTryExpressionEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/TryExpressionEnterNode;", "visitTryExpressionExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/TryExpressionExitNode;", "visitTryMainBlockEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/TryMainBlockEnterNode;", "visitTryMainBlockExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/TryMainBlockExitNode;", "visitTypeOperatorCallNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/TypeOperatorCallNode;", "visitVariableAssignmentNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/VariableAssignmentNode;", "visitVariableDeclarationNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/VariableDeclarationNode;", "visitWhenBranchConditionEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenBranchConditionEnterNode;", "visitWhenBranchConditionExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenBranchConditionExitNode;", "visitWhenBranchResultEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenBranchResultEnterNode;", "visitWhenBranchResultExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenBranchResultExitNode;", "visitWhenEnterNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenEnterNode;", "visitWhenExitNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenExitNode;", "visitWhenSyntheticElseBranchNode", "Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/WhenSyntheticElseBranchNode;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ControlFlowGraphVisitorVoid extends ControlFlowGraphVisitor {
    public abstract void visitNode(CFGNode<?> node);

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitAnonymousFunctionExpressionNode(AnonymousFunctionExpressionNode anonymousFunctionExpressionNode, Object obj) {
        visitAnonymousFunctionExpressionNode(anonymousFunctionExpressionNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitBinaryAndEnterNode(BinaryAndEnterNode binaryAndEnterNode, Object obj) {
        visitBinaryAndEnterNode(binaryAndEnterNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitBinaryAndEnterRightOperandNode(BinaryAndEnterRightOperandNode binaryAndEnterRightOperandNode, Object obj) {
        visitBinaryAndEnterRightOperandNode(binaryAndEnterRightOperandNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitBinaryAndExitLeftOperandNode(BinaryAndExitLeftOperandNode binaryAndExitLeftOperandNode, Object obj) {
        visitBinaryAndExitLeftOperandNode(binaryAndExitLeftOperandNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitBinaryAndExitNode(BinaryAndExitNode binaryAndExitNode, Object obj) {
        visitBinaryAndExitNode(binaryAndExitNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitBinaryOrEnterNode(BinaryOrEnterNode binaryOrEnterNode, Object obj) {
        visitBinaryOrEnterNode(binaryOrEnterNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitBinaryOrEnterRightOperandNode(BinaryOrEnterRightOperandNode binaryOrEnterRightOperandNode, Object obj) {
        visitBinaryOrEnterRightOperandNode(binaryOrEnterRightOperandNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitBinaryOrExitLeftOperandNode(BinaryOrExitLeftOperandNode binaryOrExitLeftOperandNode, Object obj) {
        visitBinaryOrExitLeftOperandNode(binaryOrExitLeftOperandNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitBinaryOrExitNode(BinaryOrExitNode binaryOrExitNode, Object obj) {
        visitBinaryOrExitNode(binaryOrExitNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitBlockEnterNode(BlockEnterNode blockEnterNode, Object obj) {
        visitBlockEnterNode(blockEnterNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitBlockExitNode(BlockExitNode blockExitNode, Object obj) {
        visitBlockExitNode(blockExitNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitCatchClauseEnterNode(CatchClauseEnterNode catchClauseEnterNode, Object obj) {
        visitCatchClauseEnterNode(catchClauseEnterNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitCatchClauseExitNode(CatchClauseExitNode catchClauseExitNode, Object obj) {
        visitCatchClauseExitNode(catchClauseExitNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitCheckNotNullCallNode(CheckNotNullCallNode checkNotNullCallNode, Object obj) {
        visitCheckNotNullCallNode(checkNotNullCallNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitComparisonExpressionNode(ComparisonExpressionNode comparisonExpressionNode, Object obj) {
        visitComparisonExpressionNode(comparisonExpressionNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitConstExpressionNode(ConstExpressionNode constExpressionNode, Object obj) {
        visitConstExpressionNode(constExpressionNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitDelegateExpressionExitNode(DelegateExpressionExitNode delegateExpressionExitNode, Object obj) {
        visitDelegateExpressionExitNode(delegateExpressionExitNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitDelegatedConstructorCallNode(DelegatedConstructorCallNode delegatedConstructorCallNode, Object obj) {
        visitDelegatedConstructorCallNode(delegatedConstructorCallNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitEnterSafeCallNode(EnterSafeCallNode enterSafeCallNode, Object obj) {
        visitEnterSafeCallNode(enterSafeCallNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitEqualityOperatorCallNode(EqualityOperatorCallNode equalityOperatorCallNode, Object obj) {
        visitEqualityOperatorCallNode(equalityOperatorCallNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitExitSafeCallNode(ExitSafeCallNode exitSafeCallNode, Object obj) {
        visitExitSafeCallNode(exitSafeCallNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitFinallyBlockEnterNode(FinallyBlockEnterNode finallyBlockEnterNode, Object obj) {
        visitFinallyBlockEnterNode(finallyBlockEnterNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitFinallyBlockExitNode(FinallyBlockExitNode finallyBlockExitNode, Object obj) {
        visitFinallyBlockExitNode(finallyBlockExitNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitFunctionCallNode(FunctionCallNode functionCallNode, Object obj) {
        visitFunctionCallNode(functionCallNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitFunctionEnterNode(FunctionEnterNode functionEnterNode, Object obj) {
        visitFunctionEnterNode(functionEnterNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitFunctionExitNode(FunctionExitNode functionExitNode, Object obj) {
        visitFunctionExitNode(functionExitNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitInitBlockEnterNode(InitBlockEnterNode initBlockEnterNode, Object obj) {
        visitInitBlockEnterNode(initBlockEnterNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitInitBlockExitNode(InitBlockExitNode initBlockExitNode, Object obj) {
        visitInitBlockExitNode(initBlockExitNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitJumpNode(JumpNode jumpNode, Object obj) {
        visitJumpNode(jumpNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitLocalFunctionDeclarationNode(LocalFunctionDeclarationNode localFunctionDeclarationNode, Object obj) {
        visitLocalFunctionDeclarationNode(localFunctionDeclarationNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitLoopBlockEnterNode(LoopBlockEnterNode loopBlockEnterNode, Object obj) {
        visitLoopBlockEnterNode(loopBlockEnterNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitLoopBlockExitNode(LoopBlockExitNode loopBlockExitNode, Object obj) {
        visitLoopBlockExitNode(loopBlockExitNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitLoopConditionEnterNode(LoopConditionEnterNode loopConditionEnterNode, Object obj) {
        visitLoopConditionEnterNode(loopConditionEnterNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitLoopConditionExitNode(LoopConditionExitNode loopConditionExitNode, Object obj) {
        visitLoopConditionExitNode(loopConditionExitNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitLoopEnterNode(LoopEnterNode loopEnterNode, Object obj) {
        visitLoopEnterNode(loopEnterNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitLoopExitNode(LoopExitNode loopExitNode, Object obj) {
        visitLoopExitNode(loopExitNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitMergePostponedLambdaExitsNode(MergePostponedLambdaExitsNode mergePostponedLambdaExitsNode, Object obj) {
        visitMergePostponedLambdaExitsNode(mergePostponedLambdaExitsNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitNode(CFGNode cFGNode, Object obj) {
        visitNode((CFGNode<?>) cFGNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitPostponedLambdaExitNode(PostponedLambdaExitNode postponedLambdaExitNode, Object obj) {
        visitPostponedLambdaExitNode(postponedLambdaExitNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitPropertyInitializerEnterNode(PropertyInitializerEnterNode propertyInitializerEnterNode, Object obj) {
        visitPropertyInitializerEnterNode(propertyInitializerEnterNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitPropertyInitializerExitNode(PropertyInitializerExitNode propertyInitializerExitNode, Object obj) {
        visitPropertyInitializerExitNode(propertyInitializerExitNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitQualifiedAccessNode(QualifiedAccessNode qualifiedAccessNode, Object obj) {
        visitQualifiedAccessNode(qualifiedAccessNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitResolvedQualifierNode(ResolvedQualifierNode resolvedQualifierNode, Object obj) {
        visitResolvedQualifierNode(resolvedQualifierNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitSplitPostponedLambdasNode(SplitPostponedLambdasNode splitPostponedLambdasNode, Object obj) {
        visitSplitPostponedLambdasNode(splitPostponedLambdasNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitStringConcatenationCallNode(StringConcatenationCallNode stringConcatenationCallNode, Object obj) {
        visitStringConcatenationCallNode(stringConcatenationCallNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitStubNode(StubNode stubNode, Object obj) {
        visitStubNode(stubNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitThrowExceptionNode(ThrowExceptionNode throwExceptionNode, Object obj) {
        visitThrowExceptionNode(throwExceptionNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitTryExpressionEnterNode(TryExpressionEnterNode tryExpressionEnterNode, Object obj) {
        visitTryExpressionEnterNode(tryExpressionEnterNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitTryExpressionExitNode(TryExpressionExitNode tryExpressionExitNode, Object obj) {
        visitTryExpressionExitNode(tryExpressionExitNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitTryMainBlockEnterNode(TryMainBlockEnterNode tryMainBlockEnterNode, Object obj) {
        visitTryMainBlockEnterNode(tryMainBlockEnterNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitTryMainBlockExitNode(TryMainBlockExitNode tryMainBlockExitNode, Object obj) {
        visitTryMainBlockExitNode(tryMainBlockExitNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitTypeOperatorCallNode(TypeOperatorCallNode typeOperatorCallNode, Object obj) {
        visitTypeOperatorCallNode(typeOperatorCallNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitVariableAssignmentNode(VariableAssignmentNode variableAssignmentNode, Object obj) {
        visitVariableAssignmentNode(variableAssignmentNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitVariableDeclarationNode(VariableDeclarationNode variableDeclarationNode, Object obj) {
        visitVariableDeclarationNode(variableDeclarationNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitWhenBranchConditionEnterNode(WhenBranchConditionEnterNode whenBranchConditionEnterNode, Object obj) {
        visitWhenBranchConditionEnterNode(whenBranchConditionEnterNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitWhenBranchConditionExitNode(WhenBranchConditionExitNode whenBranchConditionExitNode, Object obj) {
        visitWhenBranchConditionExitNode(whenBranchConditionExitNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitWhenBranchResultEnterNode(WhenBranchResultEnterNode whenBranchResultEnterNode, Object obj) {
        visitWhenBranchResultEnterNode(whenBranchResultEnterNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitWhenBranchResultExitNode(WhenBranchResultExitNode whenBranchResultExitNode, Object obj) {
        visitWhenBranchResultExitNode(whenBranchResultExitNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitWhenEnterNode(WhenEnterNode whenEnterNode, Object obj) {
        visitWhenEnterNode(whenEnterNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitWhenExitNode(WhenExitNode whenExitNode, Object obj) {
        visitWhenExitNode(whenExitNode, (Void) obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphVisitor
    public /* bridge */ /* synthetic */ Object visitWhenSyntheticElseBranchNode(WhenSyntheticElseBranchNode whenSyntheticElseBranchNode, Object obj) {
        visitWhenSyntheticElseBranchNode(whenSyntheticElseBranchNode, (Void) obj);
        return Unit.INSTANCE;
    }

    public void visitFunctionEnterNode(FunctionEnterNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitFunctionExitNode(FunctionExitNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitLocalFunctionDeclarationNode(LocalFunctionDeclarationNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitSplitPostponedLambdasNode(SplitPostponedLambdasNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitPostponedLambdaExitNode(PostponedLambdaExitNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitMergePostponedLambdaExitsNode(MergePostponedLambdaExitsNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitAnonymousFunctionExpressionNode(AnonymousFunctionExpressionNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitPropertyInitializerEnterNode(PropertyInitializerEnterNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitPropertyInitializerExitNode(PropertyInitializerExitNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitDelegateExpressionExitNode(DelegateExpressionExitNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitInitBlockEnterNode(InitBlockEnterNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitInitBlockExitNode(InitBlockExitNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitBlockEnterNode(BlockEnterNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitBlockExitNode(BlockExitNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitWhenEnterNode(WhenEnterNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitWhenExitNode(WhenExitNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitWhenBranchConditionEnterNode(WhenBranchConditionEnterNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitWhenBranchConditionExitNode(WhenBranchConditionExitNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitWhenBranchResultEnterNode(WhenBranchResultEnterNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitWhenBranchResultExitNode(WhenBranchResultExitNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitWhenSyntheticElseBranchNode(WhenSyntheticElseBranchNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitLoopEnterNode(LoopEnterNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitLoopBlockEnterNode(LoopBlockEnterNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitLoopBlockExitNode(LoopBlockExitNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitLoopConditionEnterNode(LoopConditionEnterNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitLoopConditionExitNode(LoopConditionExitNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitLoopExitNode(LoopExitNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitTryExpressionEnterNode(TryExpressionEnterNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitTryMainBlockEnterNode(TryMainBlockEnterNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitTryMainBlockExitNode(TryMainBlockExitNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitCatchClauseEnterNode(CatchClauseEnterNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitCatchClauseExitNode(CatchClauseExitNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitFinallyBlockEnterNode(FinallyBlockEnterNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitFinallyBlockExitNode(FinallyBlockExitNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitTryExpressionExitNode(TryExpressionExitNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitBinaryAndEnterNode(BinaryAndEnterNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitBinaryAndExitLeftOperandNode(BinaryAndExitLeftOperandNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitBinaryAndEnterRightOperandNode(BinaryAndEnterRightOperandNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitBinaryAndExitNode(BinaryAndExitNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitBinaryOrEnterNode(BinaryOrEnterNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitBinaryOrExitLeftOperandNode(BinaryOrExitLeftOperandNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitBinaryOrEnterRightOperandNode(BinaryOrEnterRightOperandNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitBinaryOrExitNode(BinaryOrExitNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitTypeOperatorCallNode(TypeOperatorCallNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitComparisonExpressionNode(ComparisonExpressionNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitEqualityOperatorCallNode(EqualityOperatorCallNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitJumpNode(JumpNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitConstExpressionNode(ConstExpressionNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitCheckNotNullCallNode(CheckNotNullCallNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitQualifiedAccessNode(QualifiedAccessNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitResolvedQualifierNode(ResolvedQualifierNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitFunctionCallNode(FunctionCallNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitDelegatedConstructorCallNode(DelegatedConstructorCallNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitStringConcatenationCallNode(StringConcatenationCallNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitThrowExceptionNode(ThrowExceptionNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitStubNode(StubNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitVariableDeclarationNode(VariableDeclarationNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitVariableAssignmentNode(VariableAssignmentNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitEnterSafeCallNode(EnterSafeCallNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public void visitExitSafeCallNode(ExitSafeCallNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public final void visitNode(CFGNode<?> node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitNode(node);
    }

    public final void visitFunctionEnterNode(FunctionEnterNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitFunctionEnterNode(node);
    }

    public final void visitFunctionExitNode(FunctionExitNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitFunctionExitNode(node);
    }

    public final void visitLocalFunctionDeclarationNode(LocalFunctionDeclarationNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitLocalFunctionDeclarationNode(node);
    }

    public final void visitSplitPostponedLambdasNode(SplitPostponedLambdasNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitSplitPostponedLambdasNode(node);
    }

    public final void visitPostponedLambdaExitNode(PostponedLambdaExitNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitPostponedLambdaExitNode(node);
    }

    public final void visitMergePostponedLambdaExitsNode(MergePostponedLambdaExitsNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitMergePostponedLambdaExitsNode(node);
    }

    public final void visitAnonymousFunctionExpressionNode(AnonymousFunctionExpressionNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitAnonymousFunctionExpressionNode(node);
    }

    public final void visitPropertyInitializerEnterNode(PropertyInitializerEnterNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitPropertyInitializerEnterNode(node);
    }

    public final void visitPropertyInitializerExitNode(PropertyInitializerExitNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitPropertyInitializerExitNode(node);
    }

    public final void visitDelegateExpressionExitNode(DelegateExpressionExitNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitDelegateExpressionExitNode(node);
    }

    public final void visitInitBlockEnterNode(InitBlockEnterNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitInitBlockEnterNode(node);
    }

    public final void visitInitBlockExitNode(InitBlockExitNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitInitBlockExitNode(node);
    }

    public final void visitBlockEnterNode(BlockEnterNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitBlockEnterNode(node);
    }

    public final void visitBlockExitNode(BlockExitNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitBlockExitNode(node);
    }

    public final void visitWhenEnterNode(WhenEnterNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitWhenEnterNode(node);
    }

    public final void visitWhenExitNode(WhenExitNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitWhenExitNode(node);
    }

    public final void visitWhenBranchConditionEnterNode(WhenBranchConditionEnterNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitWhenBranchConditionEnterNode(node);
    }

    public final void visitWhenBranchConditionExitNode(WhenBranchConditionExitNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitWhenBranchConditionExitNode(node);
    }

    public final void visitWhenBranchResultEnterNode(WhenBranchResultEnterNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitWhenBranchResultEnterNode(node);
    }

    public final void visitWhenBranchResultExitNode(WhenBranchResultExitNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitWhenBranchResultExitNode(node);
    }

    public final void visitWhenSyntheticElseBranchNode(WhenSyntheticElseBranchNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitWhenSyntheticElseBranchNode(node);
    }

    public final void visitLoopEnterNode(LoopEnterNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitLoopEnterNode(node);
    }

    public final void visitLoopBlockEnterNode(LoopBlockEnterNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitLoopBlockEnterNode(node);
    }

    public final void visitLoopBlockExitNode(LoopBlockExitNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitLoopBlockExitNode(node);
    }

    public final void visitLoopConditionEnterNode(LoopConditionEnterNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitLoopConditionEnterNode(node);
    }

    public final void visitLoopConditionExitNode(LoopConditionExitNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitLoopConditionExitNode(node);
    }

    public final void visitLoopExitNode(LoopExitNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitLoopExitNode(node);
    }

    public final void visitTryExpressionEnterNode(TryExpressionEnterNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitTryExpressionEnterNode(node);
    }

    public final void visitTryMainBlockEnterNode(TryMainBlockEnterNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitTryMainBlockEnterNode(node);
    }

    public final void visitTryMainBlockExitNode(TryMainBlockExitNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitTryMainBlockExitNode(node);
    }

    public final void visitCatchClauseEnterNode(CatchClauseEnterNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitCatchClauseEnterNode(node);
    }

    public final void visitCatchClauseExitNode(CatchClauseExitNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitCatchClauseExitNode(node);
    }

    public final void visitFinallyBlockEnterNode(FinallyBlockEnterNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitFinallyBlockEnterNode(node);
    }

    public final void visitFinallyBlockExitNode(FinallyBlockExitNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitFinallyBlockExitNode(node);
    }

    public final void visitTryExpressionExitNode(TryExpressionExitNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitTryExpressionExitNode(node);
    }

    public final void visitBinaryAndEnterNode(BinaryAndEnterNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitBinaryAndEnterNode(node);
    }

    public final void visitBinaryAndExitLeftOperandNode(BinaryAndExitLeftOperandNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitBinaryAndExitLeftOperandNode(node);
    }

    public final void visitBinaryAndEnterRightOperandNode(BinaryAndEnterRightOperandNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitBinaryAndEnterRightOperandNode(node);
    }

    public final void visitBinaryAndExitNode(BinaryAndExitNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitBinaryAndExitNode(node);
    }

    public final void visitBinaryOrEnterNode(BinaryOrEnterNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitBinaryOrEnterNode(node);
    }

    public final void visitBinaryOrExitLeftOperandNode(BinaryOrExitLeftOperandNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitBinaryOrExitLeftOperandNode(node);
    }

    public final void visitBinaryOrEnterRightOperandNode(BinaryOrEnterRightOperandNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitBinaryOrEnterRightOperandNode(node);
    }

    public final void visitBinaryOrExitNode(BinaryOrExitNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitBinaryOrExitNode(node);
    }

    public final void visitTypeOperatorCallNode(TypeOperatorCallNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitTypeOperatorCallNode(node);
    }

    public final void visitEqualityOperatorCallNode(EqualityOperatorCallNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitEqualityOperatorCallNode(node);
    }

    public final void visitComparisonExpressionNode(ComparisonExpressionNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitComparisonExpressionNode(node);
    }

    public final void visitJumpNode(JumpNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitJumpNode(node);
    }

    public final void visitConstExpressionNode(ConstExpressionNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitConstExpressionNode(node);
    }

    public final void visitCheckNotNullCallNode(CheckNotNullCallNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitCheckNotNullCallNode(node);
    }

    public final void visitQualifiedAccessNode(QualifiedAccessNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitQualifiedAccessNode(node);
    }

    public final void visitResolvedQualifierNode(ResolvedQualifierNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitResolvedQualifierNode(node);
    }

    public final void visitFunctionCallNode(FunctionCallNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitFunctionCallNode(node);
    }

    public final void visitDelegatedConstructorCallNode(DelegatedConstructorCallNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitDelegatedConstructorCallNode(node);
    }

    public final void visitStringConcatenationCallNode(StringConcatenationCallNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitStringConcatenationCallNode(node);
    }

    public final void visitThrowExceptionNode(ThrowExceptionNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitThrowExceptionNode(node);
    }

    public final void visitStubNode(StubNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitStubNode(node);
    }

    public final void visitVariableDeclarationNode(VariableDeclarationNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitVariableDeclarationNode(node);
    }

    public final void visitVariableAssignmentNode(VariableAssignmentNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitVariableAssignmentNode(node);
    }

    public final void visitEnterSafeCallNode(EnterSafeCallNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitEnterSafeCallNode(node);
    }

    public final void visitExitSafeCallNode(ExitSafeCallNode node, Void data) {
        Intrinsics.checkNotNullParameter(node, "node");
        visitExitSafeCallNode(node);
    }
}
