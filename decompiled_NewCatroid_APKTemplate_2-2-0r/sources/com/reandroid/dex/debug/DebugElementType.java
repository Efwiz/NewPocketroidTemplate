package com.reandroid.dex.debug;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.base.BlockCreator;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.dex.debug.DebugElement;
import com.reandroid.dex.smali.SmaliDirective;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class DebugElementType<T extends DebugElement> implements BlockCreator<T> {
    public static final DebugElementType<DebugAdvanceLine> ADVANCE_LINE;
    public static final DebugElementType<DebugAdvancePc> ADVANCE_PC;
    public static final DebugElementType<DebugEndLocal> END_LOCAL;
    public static final DebugElementType<DebugEndSequence> END_SEQUENCE;
    public static final DebugElementType<DebugEpilogue> EPILOGUE;
    public static final DebugElementType<DebugLineNumber> LINE_NUMBER;
    public static final DebugElementType<DebugPrologue> PROLOGUE;
    public static final DebugElementType<DebugRestartLocal> RESTART_LOCAL;
    public static final DebugElementType<DebugSetSourceFile> SET_SOURCE_FILE;
    public static final DebugElementType<DebugStartLocal> START_LOCAL;
    public static final DebugElementType<DebugStartLocalExtended> START_LOCAL_EXTENDED;
    public static final DebugElementType<?>[] VALUES;
    private final BlockCreator<T> creator;
    private final SmaliDirective directive;
    private final int flag;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f238name;

    public boolean is(DebugElementType<?> debugElementType) {
        return debugElementType == this;
    }

    static {
        VALUES = new DebugElementType[]{debugElementType, debugElementType, debugElementType, debugElementType, debugElementType, debugElementType, debugElementType, debugElementType, debugElementType, debugElementType, debugElementType};
        DebugElementType<DebugEndSequence> debugElementType = new DebugElementType<>("END_SEQUENCE", 0, new BlockCreator() { // from class: com.reandroid.dex.debug.DebugElementType$$ExternalSyntheticLambda0
            @Override // com.reandroid.arsc.base.BlockCreator
            public final Block newInstance() {
                return DebugEndSequence.INSTANCE;
            }
        });
        END_SEQUENCE = debugElementType;
        DebugElementType<DebugAdvancePc> debugElementType2 = new DebugElementType<>("ADVANCE_PC", 1, new BlockCreator() { // from class: com.reandroid.dex.debug.DebugElementType$$ExternalSyntheticLambda3
            @Override // com.reandroid.arsc.base.BlockCreator
            public final Block newInstance() {
                return new DebugAdvancePc();
            }
        });
        ADVANCE_PC = debugElementType2;
        DebugElementType<DebugAdvanceLine> debugElementType3 = new DebugElementType<>("ADVANCE_LINE", 2, new BlockCreator() { // from class: com.reandroid.dex.debug.DebugElementType$$ExternalSyntheticLambda4
            @Override // com.reandroid.arsc.base.BlockCreator
            public final Block newInstance() {
                return new DebugAdvanceLine();
            }
        });
        ADVANCE_LINE = debugElementType3;
        DebugElementType<DebugStartLocal> debugElementType4 = new DebugElementType<>("START_LOCAL", SmaliDirective.LOCAL, 3, new BlockCreator() { // from class: com.reandroid.dex.debug.DebugElementType$$ExternalSyntheticLambda5
            @Override // com.reandroid.arsc.base.BlockCreator
            public final Block newInstance() {
                return new DebugStartLocal();
            }
        });
        START_LOCAL = debugElementType4;
        DebugElementType<DebugStartLocalExtended> debugElementType5 = new DebugElementType<>("START_LOCAL_EXTENDED", SmaliDirective.LOCAL, 4, new BlockCreator() { // from class: com.reandroid.dex.debug.DebugElementType$$ExternalSyntheticLambda6
            @Override // com.reandroid.arsc.base.BlockCreator
            public final Block newInstance() {
                return new DebugStartLocalExtended();
            }
        });
        START_LOCAL_EXTENDED = debugElementType5;
        DebugElementType<DebugEndLocal> debugElementType6 = new DebugElementType<>("END_LOCAL", SmaliDirective.END_LOCAL, 5, new BlockCreator() { // from class: com.reandroid.dex.debug.DebugElementType$$ExternalSyntheticLambda7
            @Override // com.reandroid.arsc.base.BlockCreator
            public final Block newInstance() {
                return new DebugEndLocal();
            }
        });
        END_LOCAL = debugElementType6;
        DebugElementType<DebugRestartLocal> debugElementType7 = new DebugElementType<>("RESTART_LOCAL", SmaliDirective.RESTART_LOCAL, 6, new BlockCreator() { // from class: com.reandroid.dex.debug.DebugElementType$$ExternalSyntheticLambda8
            @Override // com.reandroid.arsc.base.BlockCreator
            public final Block newInstance() {
                return new DebugRestartLocal();
            }
        });
        RESTART_LOCAL = debugElementType7;
        DebugElementType<DebugPrologue> debugElementType8 = new DebugElementType<>("PROLOGUE", SmaliDirective.PROLOGUE, 7, new BlockCreator() { // from class: com.reandroid.dex.debug.DebugElementType$$ExternalSyntheticLambda9
            @Override // com.reandroid.arsc.base.BlockCreator
            public final Block newInstance() {
                return new DebugPrologue();
            }
        });
        PROLOGUE = debugElementType8;
        DebugElementType<DebugEpilogue> debugElementType9 = new DebugElementType<>("EPILOGUE", SmaliDirective.EPILOGUE, 8, new BlockCreator() { // from class: com.reandroid.dex.debug.DebugElementType$$ExternalSyntheticLambda10
            @Override // com.reandroid.arsc.base.BlockCreator
            public final Block newInstance() {
                return new DebugEpilogue();
            }
        });
        EPILOGUE = debugElementType9;
        DebugElementType<DebugSetSourceFile> debugElementType10 = new DebugElementType<>("SET_SOURCE_FILE", SmaliDirective.SET_SOURCE_FILE, 9, new BlockCreator() { // from class: com.reandroid.dex.debug.DebugElementType$$ExternalSyntheticLambda1
            @Override // com.reandroid.arsc.base.BlockCreator
            public final Block newInstance() {
                return new DebugSetSourceFile();
            }
        });
        SET_SOURCE_FILE = debugElementType10;
        DebugElementType<DebugLineNumber> debugElementType11 = new DebugElementType<>("LINE_NUMBER", SmaliDirective.LINE, 10, new BlockCreator() { // from class: com.reandroid.dex.debug.DebugElementType$$ExternalSyntheticLambda2
            @Override // com.reandroid.arsc.base.BlockCreator
            public final Block newInstance() {
                return new DebugLineNumber();
            }
        });
        LINE_NUMBER = debugElementType11;
    }

    private DebugElementType(String str, SmaliDirective smaliDirective, int i, BlockCreator<T> blockCreator) {
        this.f238name = str;
        this.directive = smaliDirective;
        this.flag = i;
        this.creator = blockCreator;
    }

    private DebugElementType(String str, int i, BlockCreator<T> blockCreator) {
        this(str, null, i, blockCreator);
    }

    public String getName() {
        return this.f238name;
    }

    public SmaliDirective getSmaliDirective() {
        return this.directive;
    }

    public int getFlag() {
        return this.flag;
    }

    @Override // com.reandroid.arsc.base.BlockCreator
    public T newInstance() {
        return (T) this.creator.newInstance();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.flag == ((DebugElementType) obj).flag;
    }

    public int hashCode() {
        return this.flag;
    }

    public String toString() {
        SmaliDirective smaliDirective = this.directive;
        if (smaliDirective != null) {
            return smaliDirective.toString();
        }
        return this.f238name;
    }

    public static DebugElementType<?> readFlag(BlockReader blockReader) throws IOException {
        int i = blockReader.read();
        blockReader.offset(-1);
        return fromFlag(i);
    }

    public static DebugElementType<?> fromFlag(int i) {
        int i2 = i & 255;
        if (i2 > 10) {
            i2 = 10;
        }
        return VALUES[i2];
    }
}
