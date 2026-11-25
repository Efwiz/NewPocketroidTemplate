package org.jf.dexlib2.dexbacked.util;

import java.util.Arrays;
import java.util.Iterator;
import org.jf.dexlib2.AccessFlags;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.dexbacked.DexBackedMethod;
import org.jf.dexlib2.dexbacked.DexBackedMethodImplementation;
import org.jf.dexlib2.dexbacked.DexBuffer;
import org.jf.dexlib2.dexbacked.DexReader;
import org.jf.dexlib2.iface.debug.DebugItem;
import org.jf.dexlib2.iface.debug.EndLocal;
import org.jf.dexlib2.iface.debug.LocalInfo;
import org.jf.dexlib2.immutable.debug.ImmutableEndLocal;
import org.jf.dexlib2.immutable.debug.ImmutableEpilogueBegin;
import org.jf.dexlib2.immutable.debug.ImmutableLineNumber;
import org.jf.dexlib2.immutable.debug.ImmutablePrologueEnd;
import org.jf.dexlib2.immutable.debug.ImmutableRestartLocal;
import org.jf.dexlib2.immutable.debug.ImmutableSetSourceFile;
import org.jf.dexlib2.immutable.debug.ImmutableStartLocal;
import org.jf.util.collection.EmptySet;

/* JADX INFO: loaded from: classes6.dex */
public abstract class DebugInfo implements Iterable<DebugItem> {
    public abstract Iterator<String> getParameterNames(DexReader<?> dexReader);

    public abstract int getSize();

    public static DebugInfo newOrEmpty(DexBackedDexFile dexBackedDexFile, int i, DexBackedMethodImplementation dexBackedMethodImplementation) {
        if (i == 0) {
            return EmptyDebugInfo.INSTANCE;
        }
        return new DebugInfoImpl(dexBackedDexFile, i, dexBackedMethodImplementation);
    }

    private static class EmptyDebugInfo extends DebugInfo {
        public static final EmptyDebugInfo INSTANCE = new EmptyDebugInfo();

        @Override // org.jf.dexlib2.dexbacked.util.DebugInfo
        public int getSize() {
            return 0;
        }

        private EmptyDebugInfo() {
        }

        @Override // java.lang.Iterable
        public Iterator<DebugItem> iterator() {
            return EmptySet.of().iterator();
        }

        @Override // org.jf.dexlib2.dexbacked.util.DebugInfo
        public Iterator<String> getParameterNames(DexReader<?> dexReader) {
            return EmptySet.of().iterator();
        }
    }

    private static class DebugInfoImpl extends DebugInfo {
        private static final LocalInfo EMPTY_LOCAL_INFO = new LocalInfo() { // from class: org.jf.dexlib2.dexbacked.util.DebugInfo.DebugInfoImpl.1
            @Override // org.jf.dexlib2.iface.debug.LocalInfo
            public String getName() {
                return null;
            }

            @Override // org.jf.dexlib2.iface.debug.LocalInfo
            public String getSignature() {
                return null;
            }

            @Override // org.jf.dexlib2.iface.debug.LocalInfo
            public String getType() {
                return null;
            }
        };
        private final int debugInfoOffset;
        public final DexBackedDexFile dexFile;
        private final DexBackedMethodImplementation methodImpl;

        @Override // org.jf.dexlib2.dexbacked.util.DebugInfo
        public /* bridge */ /* synthetic */ Iterator getParameterNames(DexReader dexReader) {
            return getParameterNames((DexReader<?>) dexReader);
        }

        public DebugInfoImpl(DexBackedDexFile dexBackedDexFile, int i, DexBackedMethodImplementation dexBackedMethodImplementation) {
            this.dexFile = dexBackedDexFile;
            this.debugInfoOffset = i;
            this.methodImpl = dexBackedMethodImplementation;
        }

        @Override // java.lang.Iterable
        public Iterator<DebugItem> iterator() {
            LocalInfo localInfo;
            String type;
            DexReader<? extends DexBuffer> dexReader = this.dexFile.getDataBuffer().readerAt(this.debugInfoOffset);
            int bigUleb128 = dexReader.readBigUleb128();
            int registerCount = this.methodImpl.getRegisterCount();
            LocalInfo[] localInfoArr = new LocalInfo[registerCount];
            Arrays.fill(localInfoArr, EMPTY_LOCAL_INFO);
            DexBackedMethod dexBackedMethod = this.methodImpl.method;
            ParameterIterator parameterIterator = new ParameterIterator(dexBackedMethod.getParameterTypes(), dexBackedMethod.getParameterAnnotations(), getParameterNames((DexReader<?>) dexReader));
            int i = 0;
            if (!AccessFlags.STATIC.isSet(this.methodImpl.method.getAccessFlags())) {
                localInfoArr[0] = new LocalInfo() { // from class: org.jf.dexlib2.dexbacked.util.DebugInfo.DebugInfoImpl.2
                    @Override // org.jf.dexlib2.iface.debug.LocalInfo
                    public String getSignature() {
                        return null;
                    }

                    @Override // org.jf.dexlib2.iface.debug.LocalInfo
                    public String getName() {
                        return "this";
                    }

                    @Override // org.jf.dexlib2.iface.debug.LocalInfo
                    public String getType() {
                        return DebugInfoImpl.this.methodImpl.method.getDefiningClass();
                    }
                };
                i = 1;
            }
            while (parameterIterator.hasNext()) {
                localInfoArr[i] = parameterIterator.next();
                i++;
            }
            if (i < registerCount) {
                int i2 = registerCount - 1;
                while (true) {
                    i--;
                    if (i <= -1 || ((type = (localInfo = localInfoArr[i]).getType()) != null && ((type.equals("J") || type.equals("D")) && (i2 = i2 - 1) == i))) {
                        break;
                    }
                    localInfoArr[i2] = localInfo;
                    localInfoArr[i] = EMPTY_LOCAL_INFO;
                    i2--;
                }
            }
            return new VariableSizeLookaheadIterator<DebugItem>(this.dexFile.getDataBuffer(), dexReader.getOffset(), bigUleb128, localInfoArr) { // from class: org.jf.dexlib2.dexbacked.util.DebugInfo.DebugInfoImpl.3
                private int codeAddress = 0;
                private int lineNumber;
                final /* synthetic */ int val$lineNumberStart;
                final /* synthetic */ LocalInfo[] val$locals;

                {
                    this.val$lineNumberStart = bigUleb128;
                    this.val$locals = localInfoArr;
                    this.lineNumber = bigUleb128;
                }

                @Override // org.jf.dexlib2.dexbacked.util.VariableSizeLookaheadIterator
                protected /* bridge */ /* synthetic */ DebugItem readNextItem(DexReader dexReader2) {
                    return readNextItem2((DexReader<?>) dexReader2);
                }

                /* JADX WARN: Code duplicated, block: B:17:0x005d  */
                /* JADX WARN: Code duplicated, block: B:29:0x008f  */
                @Override // org.jf.dexlib2.dexbacked.util.VariableSizeLookaheadIterator
                /* JADX INFO: renamed from: readNextItem, reason: avoid collision after fix types in other method */
                protected DebugItem readNextItem2(DexReader<?> dexReader2) {
                    LocalInfo localInfo2;
                    LocalInfo localInfo3;
                    while (true) {
                        int ubyte = dexReader2.readUbyte();
                        boolean z = true;
                        switch (ubyte) {
                            case 0:
                                return endOfData();
                            case 1:
                                this.codeAddress += dexReader2.readSmallUleb128();
                                break;
                            case 2:
                                this.lineNumber += dexReader2.readSleb128();
                                break;
                            case 3:
                                int smallUleb128 = dexReader2.readSmallUleb128();
                                ImmutableStartLocal immutableStartLocal = new ImmutableStartLocal(this.codeAddress, smallUleb128, DebugInfoImpl.this.dexFile.getStringSection().get(dexReader2.readSmallUleb128() - 1), DebugInfoImpl.this.dexFile.getTypeSection().get(dexReader2.readSmallUleb128() - 1), null);
                                if (smallUleb128 >= 0) {
                                    LocalInfo[] localInfoArr2 = this.val$locals;
                                    if (smallUleb128 < localInfoArr2.length) {
                                        localInfoArr2[smallUleb128] = immutableStartLocal;
                                    }
                                }
                                return immutableStartLocal;
                            case 4:
                                int smallUleb129 = dexReader2.readSmallUleb128();
                                ImmutableStartLocal immutableStartLocal2 = new ImmutableStartLocal(this.codeAddress, smallUleb129, DebugInfoImpl.this.dexFile.getStringSection().get(dexReader2.readSmallUleb128() - 1), DebugInfoImpl.this.dexFile.getTypeSection().get(dexReader2.readSmallUleb128() - 1), DebugInfoImpl.this.dexFile.getStringSection().get(dexReader2.readSmallUleb128() - 1));
                                if (smallUleb129 >= 0) {
                                    LocalInfo[] localInfoArr3 = this.val$locals;
                                    if (smallUleb129 < localInfoArr3.length) {
                                        localInfoArr3[smallUleb129] = immutableStartLocal2;
                                    }
                                }
                                return immutableStartLocal2;
                            case 5:
                                int smallUleb1210 = dexReader2.readSmallUleb128();
                                boolean z2 = false;
                                if (smallUleb1210 < 0) {
                                    localInfo2 = DebugInfoImpl.EMPTY_LOCAL_INFO;
                                    z = false;
                                } else {
                                    LocalInfo[] localInfoArr4 = this.val$locals;
                                    if (smallUleb1210 >= localInfoArr4.length) {
                                        localInfo2 = DebugInfoImpl.EMPTY_LOCAL_INFO;
                                        z = false;
                                    } else {
                                        localInfo2 = localInfoArr4[smallUleb1210];
                                    }
                                }
                                if (localInfo2 instanceof EndLocal) {
                                    localInfo2 = DebugInfoImpl.EMPTY_LOCAL_INFO;
                                } else {
                                    z2 = z;
                                }
                                ImmutableEndLocal immutableEndLocal = new ImmutableEndLocal(this.codeAddress, smallUleb1210, localInfo2.getName(), localInfo2.getType(), localInfo2.getSignature());
                                if (z2) {
                                    this.val$locals[smallUleb1210] = immutableEndLocal;
                                }
                                return immutableEndLocal;
                            case 6:
                                int smallUleb1211 = dexReader2.readSmallUleb128();
                                if (smallUleb1211 < 0) {
                                    localInfo3 = DebugInfoImpl.EMPTY_LOCAL_INFO;
                                } else {
                                    LocalInfo[] localInfoArr5 = this.val$locals;
                                    if (smallUleb1211 >= localInfoArr5.length) {
                                        localInfo3 = DebugInfoImpl.EMPTY_LOCAL_INFO;
                                    } else {
                                        localInfo3 = localInfoArr5[smallUleb1211];
                                    }
                                }
                                ImmutableRestartLocal immutableRestartLocal = new ImmutableRestartLocal(this.codeAddress, smallUleb1211, localInfo3.getName(), localInfo3.getType(), localInfo3.getSignature());
                                if (smallUleb1211 >= 0) {
                                    LocalInfo[] localInfoArr6 = this.val$locals;
                                    if (smallUleb1211 < localInfoArr6.length) {
                                        localInfoArr6[smallUleb1211] = immutableRestartLocal;
                                    }
                                }
                                return immutableRestartLocal;
                            case 7:
                                return new ImmutablePrologueEnd(this.codeAddress);
                            case 8:
                                return new ImmutableEpilogueBegin(this.codeAddress);
                            case 9:
                                return new ImmutableSetSourceFile(this.codeAddress, DebugInfoImpl.this.dexFile.getStringSection().get(dexReader2.readSmallUleb128() - 1));
                            default:
                                int i3 = ubyte - 10;
                                this.codeAddress += i3 / 15;
                                this.lineNumber += (i3 % 15) - 4;
                                return new ImmutableLineNumber(this.codeAddress, this.lineNumber);
                        }
                    }
                }
            };
        }

        @Override // org.jf.dexlib2.dexbacked.util.DebugInfo
        public VariableSizeIterator<String> getParameterNames(DexReader<?> dexReader) {
            if (dexReader == null) {
                dexReader = this.dexFile.getDataBuffer().readerAt(this.debugInfoOffset);
                dexReader.skipUleb128();
            }
            return new VariableSizeIterator<String>(dexReader, dexReader.readSmallUleb128()) { // from class: org.jf.dexlib2.dexbacked.util.DebugInfo.DebugInfoImpl.4
                @Override // org.jf.dexlib2.dexbacked.util.VariableSizeIterator
                protected /* bridge */ /* synthetic */ String readNextItem(DexReader dexReader2, int i) {
                    return readNextItem2((DexReader<?>) dexReader2, i);
                }

                @Override // org.jf.dexlib2.dexbacked.util.VariableSizeIterator
                /* JADX INFO: renamed from: readNextItem, reason: avoid collision after fix types in other method */
                protected String readNextItem2(DexReader<?> dexReader2, int i) {
                    return DebugInfoImpl.this.dexFile.getStringSection().get(dexReader2.readSmallUleb128() - 1);
                }
            };
        }

        @Override // org.jf.dexlib2.dexbacked.util.DebugInfo
        public int getSize() {
            Iterator<DebugItem> it = iterator();
            while (it.hasNext()) {
                it.next();
            }
            return ((VariableSizeLookaheadIterator) it).getReaderOffset() - this.debugInfoOffset;
        }
    }
}
