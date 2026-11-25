package org.eclipse.jgit.internal.storage.pack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.revwalk.AddUnseenToBitmapFilter;
import org.eclipse.jgit.internal.storage.file.BitmapIndexImpl;
import org.eclipse.jgit.internal.storage.file.PackBitmapIndexBuilder;
import org.eclipse.jgit.internal.storage.file.PackBitmapIndexRemapper;
import org.eclipse.jgit.lib.BitmapIndex;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.revwalk.BitmapWalker;
import org.eclipse.jgit.revwalk.ObjectWalk;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevFlag;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.storage.pack.PackConfig;
import org.eclipse.jgit.util.BlockList;
import org.eclipse.jgit.util.SystemReader;

/* JADX INFO: loaded from: classes13.dex */
class PackWriterBitmapPreparer {
    private static final int DAY_IN_SECONDS = 86400;
    private static final int DISTANCE_THRESHOLD = 2000;
    private static final Comparator<RevCommit> ORDER_BY_REVERSE_TIMESTAMP = new Comparator() { // from class: org.eclipse.jgit.internal.storage.pack.PackWriterBitmapPreparer$$ExternalSyntheticLambda0
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return Integer.signum(((RevCommit) obj2).getCommitTime() - ((RevCommit) obj).getCommitTime());
        }
    };
    private final BitmapIndexImpl bitmapIndex;
    private final PackBitmapIndexRemapper bitmapRemapper;
    private final BitmapIndexImpl commitBitmapIndex;
    private final int contiguousCommitCount;
    private final int distantCommitSpan;
    private final int excessiveBranchCount;
    private final long inactiveBranchTimestamp;
    private final ProgressMonitor pm;
    private final ObjectReader reader;
    private final int recentCommitCount;
    private final int recentCommitSpan;
    private final Set<? extends ObjectId> want;
    private final PackBitmapIndexBuilder writeBitmaps;

    PackWriterBitmapPreparer(ObjectReader objectReader, PackBitmapIndexBuilder packBitmapIndexBuilder, ProgressMonitor progressMonitor, Set<? extends ObjectId> set, PackConfig packConfig) throws IOException {
        this.reader = objectReader;
        this.writeBitmaps = packBitmapIndexBuilder;
        this.pm = progressMonitor;
        this.want = set;
        this.commitBitmapIndex = new BitmapIndexImpl(packBitmapIndexBuilder);
        PackBitmapIndexRemapper packBitmapIndexRemapperNewPackBitmapIndex = PackBitmapIndexRemapper.newPackBitmapIndex(objectReader.getBitmapIndex(), packBitmapIndexBuilder);
        this.bitmapRemapper = packBitmapIndexRemapperNewPackBitmapIndex;
        this.bitmapIndex = new BitmapIndexImpl(packBitmapIndexRemapperNewPackBitmapIndex);
        this.contiguousCommitCount = packConfig.getBitmapContiguousCommitCount();
        this.recentCommitCount = packConfig.getBitmapRecentCommitCount();
        this.recentCommitSpan = packConfig.getBitmapRecentCommitSpan();
        this.distantCommitSpan = packConfig.getBitmapDistantCommitSpan();
        this.excessiveBranchCount = packConfig.getBitmapExcessiveBranchCount();
        this.inactiveBranchTimestamp = (SystemReader.getInstance().getCurrentTime() / 1000) - ((long) (packConfig.getBitmapInactiveBranchAgeInDays() * DAY_IN_SECONDS));
    }

    /* JADX WARN: Code duplicated, block: B:100:0x0240  */
    /* JADX WARN: Code duplicated, block: B:101:0x0241  */
    /* JADX WARN: Code duplicated, block: B:103:0x0247  */
    /* JADX WARN: Code duplicated, block: B:106:0x0251 A[LOOP:5: B:83:0x01c1->B:106:0x0251, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:80:0x01a4  */
    /* JADX WARN: Code duplicated, block: B:81:0x01a6  */
    /* JADX WARN: Code duplicated, block: B:89:0x01d4 A[Catch: all -> 0x028b, TryCatch #3 {all -> 0x028b, blocks: (B:5:0x0010, B:6:0x0048, B:9:0x0050, B:10:0x0056, B:15:0x0063, B:16:0x0072, B:17:0x009f, B:19:0x00a5, B:20:0x00ab, B:22:0x00b1, B:26:0x00c6, B:27:0x00d5, B:28:0x00ee, B:30:0x00f4, B:32:0x0104, B:36:0x010d, B:40:0x0118, B:41:0x011e, B:46:0x0135, B:47:0x0139, B:50:0x0142, B:44:0x0125, B:51:0x014c, B:54:0x0156, B:78:0x019c, B:82:0x01a7, B:83:0x01c1, B:85:0x01c7, B:86:0x01cc, B:89:0x01d4, B:90:0x01dc, B:94:0x01eb, B:95:0x0212, B:98:0x0234, B:63:0x0176, B:67:0x017f, B:70:0x0186, B:107:0x0257, B:108:0x0274), top: B:137:0x0010 }] */
    /* JADX WARN: Code duplicated, block: B:92:0x01e8  */
    /* JADX WARN: Code duplicated, block: B:93:0x01ea  */
    /* JADX WARN: Code duplicated, block: B:95:0x0212 A[Catch: all -> 0x028b, TryCatch #3 {all -> 0x028b, blocks: (B:5:0x0010, B:6:0x0048, B:9:0x0050, B:10:0x0056, B:15:0x0063, B:16:0x0072, B:17:0x009f, B:19:0x00a5, B:20:0x00ab, B:22:0x00b1, B:26:0x00c6, B:27:0x00d5, B:28:0x00ee, B:30:0x00f4, B:32:0x0104, B:36:0x010d, B:40:0x0118, B:41:0x011e, B:46:0x0135, B:47:0x0139, B:50:0x0142, B:44:0x0125, B:51:0x014c, B:54:0x0156, B:78:0x019c, B:82:0x01a7, B:83:0x01c1, B:85:0x01c7, B:86:0x01cc, B:89:0x01d4, B:90:0x01dc, B:94:0x01eb, B:95:0x0212, B:98:0x0234, B:63:0x0176, B:67:0x017f, B:70:0x0186, B:107:0x0257, B:108:0x0274), top: B:137:0x0010 }] */
    /* JADX WARN: Code duplicated, block: B:97:0x0232 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:98:0x0234 A[Catch: all -> 0x028b, TryCatch #3 {all -> 0x028b, blocks: (B:5:0x0010, B:6:0x0048, B:9:0x0050, B:10:0x0056, B:15:0x0063, B:16:0x0072, B:17:0x009f, B:19:0x00a5, B:20:0x00ab, B:22:0x00b1, B:26:0x00c6, B:27:0x00d5, B:28:0x00ee, B:30:0x00f4, B:32:0x0104, B:36:0x010d, B:40:0x0118, B:41:0x011e, B:46:0x0135, B:47:0x0139, B:50:0x0142, B:44:0x0125, B:51:0x014c, B:54:0x0156, B:78:0x019c, B:82:0x01a7, B:83:0x01c1, B:85:0x01c7, B:86:0x01cc, B:89:0x01d4, B:90:0x01dc, B:94:0x01eb, B:95:0x0212, B:98:0x0234, B:63:0x0176, B:67:0x017f, B:70:0x0186, B:107:0x0257, B:108:0x0274), top: B:137:0x0010 }] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v3 */
    /* JADX WARN: Type inference failed for: r11v4 */
    /* JADX WARN: Type inference failed for: r11v6 */
    /* JADX WARN: Type inference failed for: r19v3 */
    /* JADX WARN: Type inference failed for: r25v1 */
    /* JADX WARN: Type inference failed for: r25v3 */
    /* JADX WARN: Type inference failed for: r25v5 */
    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v12 */
    /* JADX WARN: Type inference failed for: r2v15 */
    /* JADX WARN: Type inference failed for: r2v16 */
    /* JADX WARN: Type inference failed for: r2v24 */
    /* JADX WARN: Type inference failed for: r2v28 */
    /* JADX WARN: Type inference failed for: r2v29 */
    /* JADX WARN: Type inference failed for: r6v15 */
    Collection<BitmapCommit> selectCommits(int i, Set<? extends ObjectId> set) throws IOException {
        Throwable th;
        BitmapIndexImpl.CompressedBitmapBuilder compressedBitmapBuilder;
        int i2;
        boolean z;
        BitmapIndexImpl.CompressedBitmapBuilder compressedBitmapBuilderNewBitmapBuilder;
        List arrayList;
        boolean z2;
        int i3;
        this = this;
        try {
            RevWalk revWalk = new RevWalk(this.reader);
            try {
                RevWalk revWalk2 = new RevWalk(this.reader);
                try {
                    boolean z3 = false;
                    this.pm.beginTask(JGitText.get().selectingCommits, 0);
                    revWalk.setRetainBody(false);
                    CommitSelectionHelper commitSelectionHelperCaptureOldAndNewCommits = this.captureOldAndNewCommits(revWalk, i, set);
                    this.pm.endTask();
                    int commitCount = commitSelectionHelperCaptureOldAndNewCommits.getCommitCount();
                    int i4 = 1;
                    BlockList blockList = new BlockList(commitSelectionHelperCaptureOldAndNewCommits.reusedCommits.size() + (commitCount / this.recentCommitSpan) + 1);
                    Iterator<BitmapCommit> it = commitSelectionHelperCaptureOldAndNewCommits.reusedCommits.iterator();
                    while (it.hasNext()) {
                        CommitSelectionHelper commitSelectionHelper = commitSelectionHelperCaptureOldAndNewCommits;
                        ?? r19 = z3 ? 1 : 0;
                        int i5 = i4;
                        blockList.add(it.next());
                        this = this;
                        i4 = i5;
                        z3 = r19 == true ? 1 : 0;
                        commitSelectionHelperCaptureOldAndNewCommits = commitSelectionHelper;
                    }
                    if (commitCount == 0) {
                        Iterator<? extends ObjectId> it2 = commitSelectionHelperCaptureOldAndNewCommits.newWants.iterator();
                        while (it2.hasNext()) {
                            blockList.add(new BitmapCommit(it2.next(), z3, z3 ? 1 : 0));
                        }
                        revWalk2.close();
                        revWalk.close();
                        return blockList;
                    }
                    this.pm.beginTask(JGitText.get().selectingCommits, commitCount);
                    int size = this.want.size();
                    BitmapIndexImpl.CompressedBitmapBuilder compressedBitmapBuilderNewBitmapBuilder2 = this.commitBitmapIndex.newBitmapBuilder();
                    compressedBitmapBuilderNewBitmapBuilder2.or((BitmapIndex.Bitmap) commitSelectionHelperCaptureOldAndNewCommits.reusedCommitsBitmap);
                    revWalk2.setRetainBody(z3);
                    revWalk2.setRevFilter(new NotInBitmapFilter(compressedBitmapBuilderNewBitmapBuilder2));
                    Iterator<RevCommit> it3 = commitSelectionHelperCaptureOldAndNewCommits.newWantsByNewest.iterator();
                    while (it3.hasNext()) {
                        RevCommit next = it3.next();
                        BitmapIndexImpl.CompressedBitmapBuilder compressedBitmapBuilderNewBitmapBuilder3 = this.commitBitmapIndex.newBitmapBuilder();
                        revWalk2.markStart((RevCommit) revWalk2.peel(revWalk2.parseAny(next)));
                        while (true) {
                            RevCommit next2 = revWalk2.next();
                            if (next2 == null) {
                                break;
                            }
                            CommitSelectionHelper commitSelectionHelper2 = commitSelectionHelperCaptureOldAndNewCommits;
                            int i6 = i4;
                            compressedBitmapBuilderNewBitmapBuilder3.addObject(next2, i6);
                            this = this;
                            size = size;
                            i4 = i6;
                            compressedBitmapBuilderNewBitmapBuilder2 = compressedBitmapBuilderNewBitmapBuilder2;
                            z3 = z3 ? 1 : 0;
                            it3 = it3;
                            commitSelectionHelperCaptureOldAndNewCommits = commitSelectionHelper2;
                        }
                        int iCardinality = compressedBitmapBuilderNewBitmapBuilder3.cardinality();
                        compressedBitmapBuilderNewBitmapBuilder2.or((BitmapIndex.Bitmap) compressedBitmapBuilderNewBitmapBuilder3);
                        ArrayList<List> arrayList2 = new ArrayList();
                        ?? r11 = (size <= this.excessiveBranchCount || this.isRecentCommit(next)) ? i4 : z3;
                        int iNextSpan = this.nextSpan(iCardinality);
                        ?? r2 = iNextSpan == this.distantCommitSpan ? i4 : z3;
                        int i7 = -1;
                        for (RevCommit revCommit : commitSelectionHelperCaptureOldAndNewCommits) {
                            ?? r25 = r2;
                            int i8 = (iCardinality - i7) - 1;
                            if (i8 == 0) {
                                break;
                            }
                            if (compressedBitmapBuilderNewBitmapBuilder3.contains(revCommit)) {
                                i7++;
                                iNextSpan--;
                                int i9 = size;
                                this.pm.update(i4);
                                if (!commitSelectionHelperCaptureOldAndNewCommits.newWants.remove(revCommit)) {
                                    compressedBitmapBuilder = compressedBitmapBuilderNewBitmapBuilder2;
                                    int i10 = iNextSpan >= 0 ? i4 : 0;
                                    int i11 = revCommit.getParentCount() > i4 ? i4 : 0;
                                    if (iNextSpan > (-this.recentCommitSpan) && ((r11 == 0 || i8 > this.contiguousCommitCount) && i8 != 1 && (i10 != 0 || i11 == 0))) {
                                        r2 = r25 == true ? 1 : 0;
                                        size = i9;
                                        compressedBitmapBuilderNewBitmapBuilder2 = compressedBitmapBuilder;
                                        i4 = 1;
                                    }
                                } else {
                                    compressedBitmapBuilder = compressedBitmapBuilderNewBitmapBuilder2;
                                    if (iNextSpan > 0) {
                                        i2 = 0;
                                    }
                                    iNextSpan = this.nextSpan(i8);
                                    if (iNextSpan == this.distantCommitSpan) {
                                        z = true;
                                    } else {
                                        z = false;
                                    }
                                    compressedBitmapBuilderNewBitmapBuilder = this.commitBitmapIndex.newBitmapBuilder();
                                    revWalk.reset();
                                    revWalk.markStart(revCommit);
                                    boolean z4 = z;
                                    Iterator<RevCommit> it4 = it3;
                                    revWalk.setRevFilter(new AddUnseenToBitmapFilter(commitSelectionHelperCaptureOldAndNewCommits.reusedCommitsBitmap, compressedBitmapBuilderNewBitmapBuilder));
                                    while (revWalk.next() != null) {
                                        this = this;
                                    }
                                    arrayList = null;
                                    for (List list : arrayList2) {
                                        CommitSelectionHelper commitSelectionHelper3 = commitSelectionHelperCaptureOldAndNewCommits;
                                        if (compressedBitmapBuilderNewBitmapBuilder.contains((BitmapCommit) list.get(list.size() - 1))) {
                                            if (arrayList != null) {
                                                i3 = i8;
                                                if (arrayList.size() < list.size()) {
                                                }
                                                i8 = i3;
                                                commitSelectionHelperCaptureOldAndNewCommits = commitSelectionHelper3;
                                            } else {
                                                i3 = i8;
                                            }
                                            arrayList = list;
                                            i8 = i3;
                                            commitSelectionHelperCaptureOldAndNewCommits = commitSelectionHelper3;
                                        } else {
                                            i3 = i8;
                                        }
                                        i8 = i3;
                                        commitSelectionHelperCaptureOldAndNewCommits = commitSelectionHelper3;
                                    }
                                    if (arrayList == null) {
                                        arrayList = new ArrayList();
                                        arrayList2.add(arrayList);
                                    }
                                    BitmapCommit.Builder flags = BitmapCommit.newBuilder(revCommit).setFlags(i2);
                                    if (i8 >= 2000) {
                                        z2 = true;
                                    } else {
                                        z2 = false;
                                    }
                                    arrayList.add(flags.setAddToIndex(z2).setReuseWalker(!arrayList.isEmpty()).build());
                                    this.writeBitmaps.addBitmap(revCommit, compressedBitmapBuilderNewBitmapBuilder, 0);
                                    r2 = z4;
                                    compressedBitmapBuilderNewBitmapBuilder2 = compressedBitmapBuilder;
                                    it3 = it4;
                                    i4 = 1;
                                    size = i9;
                                }
                                i2 = r25 == true ? 1 : 0;
                                iNextSpan = this.nextSpan(i8);
                                if (iNextSpan == this.distantCommitSpan) {
                                    z = true;
                                } else {
                                    z = false;
                                }
                                compressedBitmapBuilderNewBitmapBuilder = this.commitBitmapIndex.newBitmapBuilder();
                                revWalk.reset();
                                revWalk.markStart(revCommit);
                                boolean z5 = z;
                                Iterator<RevCommit> it5 = it3;
                                revWalk.setRevFilter(new AddUnseenToBitmapFilter(commitSelectionHelperCaptureOldAndNewCommits.reusedCommitsBitmap, compressedBitmapBuilderNewBitmapBuilder));
                                while (revWalk.next() != null) {
                                    this = this;
                                }
                                arrayList = null;
                                while (r8.hasNext()) {
                                    CommitSelectionHelper commitSelectionHelper4 = commitSelectionHelperCaptureOldAndNewCommits;
                                    if (compressedBitmapBuilderNewBitmapBuilder.contains((BitmapCommit) list.get(list.size() - 1))) {
                                        if (arrayList != null) {
                                            i3 = i8;
                                            if (arrayList.size() < list.size()) {
                                            }
                                            i8 = i3;
                                            commitSelectionHelperCaptureOldAndNewCommits = commitSelectionHelper4;
                                        } else {
                                            i3 = i8;
                                        }
                                        arrayList = list;
                                        i8 = i3;
                                        commitSelectionHelperCaptureOldAndNewCommits = commitSelectionHelper4;
                                    } else {
                                        i3 = i8;
                                    }
                                    i8 = i3;
                                    commitSelectionHelperCaptureOldAndNewCommits = commitSelectionHelper4;
                                }
                                if (arrayList == null) {
                                    arrayList = new ArrayList();
                                    arrayList2.add(arrayList);
                                }
                                BitmapCommit.Builder flags2 = BitmapCommit.newBuilder(revCommit).setFlags(i2);
                                if (i8 >= 2000) {
                                    z2 = true;
                                } else {
                                    z2 = false;
                                }
                                arrayList.add(flags2.setAddToIndex(z2).setReuseWalker(!arrayList.isEmpty()).build());
                                this.writeBitmaps.addBitmap(revCommit, compressedBitmapBuilderNewBitmapBuilder, 0);
                                r2 = z5;
                                compressedBitmapBuilderNewBitmapBuilder2 = compressedBitmapBuilder;
                                it3 = it5;
                                i4 = 1;
                                size = i9;
                            } else {
                                r2 = r25 == true ? 1 : 0;
                            }
                        }
                        Iterator it6 = arrayList2.iterator();
                        while (it6.hasNext()) {
                            blockList.addAll((List) it6.next());
                        }
                        z3 = false;
                    }
                    Iterator<? extends ObjectId> it7 = commitSelectionHelperCaptureOldAndNewCommits.newWants.iterator();
                    while (it7.hasNext()) {
                        blockList.add(new BitmapCommit(it7.next(), z3, z3 ? 1 : 0));
                    }
                    this.writeBitmaps.resetBitmaps(blockList.size());
                    this.pm.endTask();
                    revWalk2.close();
                    revWalk.close();
                    return blockList;
                } catch (Throwable th2) {
                    th = th2;
                    try {
                        revWalk2.close();
                        throw th;
                    } catch (Throwable th3) {
                        th = th3;
                        if (th == null) {
                            th = th;
                        } else if (th != th) {
                            try {
                                th.addSuppressed(th);
                            } catch (Throwable th4) {
                                th = th4;
                                if (th == null) {
                                    throw th;
                                }
                                if (th == th) {
                                    throw th;
                                }
                                th.addSuppressed(th);
                                throw th;
                            }
                        }
                        revWalk.close();
                        throw th;
                    }
                }
            } catch (Throwable th5) {
                th = th5;
                th = null;
            }
        } catch (Throwable th6) {
            th = th6;
            th = null;
        }
    }

    private boolean isRecentCommit(RevCommit revCommit) {
        return ((long) revCommit.getCommitTime()) > this.inactiveBranchTimestamp;
    }

    private static class NotInBitmapFilter extends RevFilter {
        private final BitmapIndex.BitmapBuilder bitmap;

        @Override // org.eclipse.jgit.revwalk.filter.RevFilter
        public final boolean requiresCommitBody() {
            return false;
        }

        NotInBitmapFilter(BitmapIndex.BitmapBuilder bitmapBuilder) {
            this.bitmap = bitmapBuilder;
        }

        @Override // org.eclipse.jgit.revwalk.filter.RevFilter
        public final boolean include(RevWalk revWalk, RevCommit revCommit) {
            if (!this.bitmap.contains(revCommit)) {
                return true;
            }
            for (RevCommit revCommit2 : revCommit.getParents()) {
                revCommit2.add(RevFlag.SEEN);
            }
            return false;
        }

        @Override // org.eclipse.jgit.revwalk.filter.RevFilter
        public final NotInBitmapFilter clone() {
            throw new UnsupportedOperationException();
        }
    }

    private CommitSelectionHelper captureOldAndNewCommits(RevWalk revWalk, int i, Set<? extends ObjectId> set) throws IOException {
        BitmapIndexImpl.CompressedBitmapBuilder compressedBitmapBuilderNewBitmapBuilder = this.commitBitmapIndex.newBitmapBuilder();
        ArrayList arrayList = new ArrayList();
        for (PackBitmapIndexRemapper.Entry entry : this.bitmapRemapper) {
            if ((entry.getFlags() & 1) == 1) {
                RevObject revObjectPeel = revWalk.peel(revWalk.parseAny(entry));
                if (revObjectPeel instanceof RevCommit) {
                    RevCommit revCommit = (RevCommit) revObjectPeel;
                    arrayList.add(new BitmapCommit(revCommit, false, entry.getFlags()));
                    if (!compressedBitmapBuilderNewBitmapBuilder.contains(revCommit)) {
                        PackBitmapIndexRemapper packBitmapIndexRemapper = this.bitmapRemapper;
                        compressedBitmapBuilderNewBitmapBuilder.or((BitmapIndex.Bitmap) new BitmapIndexImpl.CompressedBitmap(packBitmapIndexRemapper.ofObjectType(packBitmapIndexRemapper.getBitmap(revCommit), 1), this.commitBitmapIndex));
                    }
                }
            }
        }
        ArrayList arrayList2 = new ArrayList(this.want.size());
        HashSet hashSet = new HashSet(this.want.size());
        Iterator<? extends ObjectId> it = this.want.iterator();
        while (it.hasNext()) {
            RevObject revObjectPeel2 = revWalk.peel(revWalk.parseAny(it.next()));
            if ((revObjectPeel2 instanceof RevCommit) && !compressedBitmapBuilderNewBitmapBuilder.contains(revObjectPeel2) && !set.contains(revObjectPeel2)) {
                RevCommit revCommit2 = (RevCommit) revObjectPeel2;
                revWalk.markStart(revCommit2);
                hashSet.add(revCommit2);
                arrayList2.add(revCommit2);
            }
        }
        revWalk.setRevFilter(new NotInBitmapFilter(compressedBitmapBuilderNewBitmapBuilder));
        RevCommit[] revCommitArr = new RevCommit[i];
        int i2 = i;
        while (true) {
            RevCommit next = revWalk.next();
            if (next == null || i2 <= 0) {
                break;
            }
            i2--;
            revCommitArr[i2] = next;
            this.pm.update(1);
        }
        Collections.sort(arrayList2, ORDER_BY_REVERSE_TIMESTAMP);
        return new CommitSelectionHelper(hashSet, revCommitArr, i2, arrayList2, compressedBitmapBuilderNewBitmapBuilder, arrayList);
    }

    int nextSpan(int i) {
        if (i < 0) {
            throw new IllegalArgumentException();
        }
        int i2 = this.recentCommitCount;
        if (i <= i2) {
            return this.recentCommitSpan;
        }
        return Math.max(Math.min(i - i2, this.distantCommitSpan), this.recentCommitSpan);
    }

    BitmapWalker newBitmapWalker() {
        return new BitmapWalker(new ObjectWalk(this.reader), this.bitmapIndex, null);
    }

    private static final class CommitSelectionHelper implements Iterable<RevCommit> {
        final int newCommitStartPos;
        final RevCommit[] newCommitsByOldest;
        final Set<? extends ObjectId> newWants;
        final List<RevCommit> newWantsByNewest;
        final List<BitmapCommit> reusedCommits;
        final BitmapIndex.BitmapBuilder reusedCommitsBitmap;

        CommitSelectionHelper(Set<? extends ObjectId> set, RevCommit[] revCommitArr, int i, List<RevCommit> list, BitmapIndex.BitmapBuilder bitmapBuilder, List<BitmapCommit> list2) {
            this.newWants = set;
            this.newCommitsByOldest = revCommitArr;
            this.newCommitStartPos = i;
            this.newWantsByNewest = list;
            this.reusedCommitsBitmap = bitmapBuilder;
            this.reusedCommits = list2;
        }

        @Override // java.lang.Iterable
        public Iterator<RevCommit> iterator() {
            return new Iterator<RevCommit>() { // from class: org.eclipse.jgit.internal.storage.pack.PackWriterBitmapPreparer.CommitSelectionHelper.1
                int pos;

                {
                    this.pos = CommitSelectionHelper.this.newCommitStartPos;
                }

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return this.pos < CommitSelectionHelper.this.newCommitsByOldest.length;
                }

                @Override // java.util.Iterator
                public RevCommit next() {
                    RevCommit[] revCommitArr = CommitSelectionHelper.this.newCommitsByOldest;
                    int i = this.pos;
                    this.pos = i + 1;
                    return revCommitArr[i];
                }

                @Override // java.util.Iterator
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }

        int getCommitCount() {
            return this.newCommitsByOldest.length - this.newCommitStartPos;
        }
    }
}
