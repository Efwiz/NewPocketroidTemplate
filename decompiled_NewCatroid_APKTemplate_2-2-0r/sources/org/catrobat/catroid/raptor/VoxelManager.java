package org.catrobat.catroid.raptor;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/VoxelManager.class
  classes14.dex
 */
/* JADX INFO: compiled from: VoxelManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\u0018\u0000 \u00042\u00020\u0001:\u0004\u0003\u0004\u0005\u0006B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0007"}, d2 = {"Lorg/catrobat/catroid/raptor/VoxelManager;", "", "()V", "BlockConfig", "Companion", "VoxelBuffer", "VoxelShape", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class VoxelManager {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final ConcurrentHashMap<String, VoxelBuffer> worlds = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Short, BlockConfig> blockRegistry = new ConcurrentHashMap<>();

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/VoxelManager$VoxelShape.class
      classes14.dex
     */
    /* JADX INFO: compiled from: VoxelManager.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\n\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n¨\u0006\u000b"}, d2 = {"Lorg/catrobat/catroid/raptor/VoxelManager$VoxelShape;", "", "(Ljava/lang/String;I)V", "CUBE", "CROSS", "FLOOR", "CEILING", "WALL_NORTH", "WALL_SOUTH", "WALL_EAST", "WALL_WEST", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum VoxelShape {
        CUBE,
        CROSS,
        FLOOR,
        CEILING,
        WALL_NORTH,
        WALL_SOUTH,
        WALL_EAST,
        WALL_WEST;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<VoxelShape> getEntries() {
            return $ENTRIES;
        }
    }

    @JvmStatic
    public static final short getBlockAbsolute(int i, int i2, int i3) {
        return INSTANCE.getBlockAbsolute(i, i2, i3);
    }

    @JvmStatic
    public static final BlockConfig getConfig(short s) {
        return INSTANCE.getConfig(s);
    }

    @JvmStatic
    public static final void setPhysical(short s, boolean z) {
        INSTANCE.setPhysical(s, z);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/VoxelManager$BlockConfig.class
      classes14.dex
     */
    /* JADX INFO: compiled from: VoxelManager.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0012\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B7\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\b¢\u0006\u0002\u0010\nJ\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0014\u001a\u00020\bHÆ\u0003J\t\u0010\u0015\u001a\u00020\bHÆ\u0003J;\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\bHÆ\u0001J\u0013\u0010\u0017\u001a\u00020\b2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0019\u001a\u00020\u0005HÖ\u0001J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001R\u0011\u0010\t\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000f¨\u0006\u001c"}, d2 = {"Lorg/catrobat/catroid/raptor/VoxelManager$BlockConfig;", "", "shape", "Lorg/catrobat/catroid/raptor/VoxelManager$VoxelShape;", "texX", "", "texY", "isTransparent", "", "isPhysical", "(Lorg/catrobat/catroid/raptor/VoxelManager$VoxelShape;IIZZ)V", "()Z", "getShape", "()Lorg/catrobat/catroid/raptor/VoxelManager$VoxelShape;", "getTexX", "()I", "getTexY", "component1", "component2", "component3", "component4", "component5", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class BlockConfig {
        private final boolean isPhysical;
        private final boolean isTransparent;
        private final VoxelShape shape;
        private final int texX;
        private final int texY;

        public BlockConfig() {
            this(null, 0, 0, false, false, 31, null);
        }

        public static /* synthetic */ BlockConfig copy$default(BlockConfig blockConfig, VoxelShape voxelShape, int i, int i2, boolean z, boolean z2, int i3, Object obj) {
            if ((i3 & 1) != 0) {
                voxelShape = blockConfig.shape;
            }
            if ((i3 & 2) != 0) {
                i = blockConfig.texX;
            }
            int i4 = i;
            if ((i3 & 4) != 0) {
                i2 = blockConfig.texY;
            }
            int i5 = i2;
            if ((i3 & 8) != 0) {
                z = blockConfig.isTransparent;
            }
            boolean z3 = z;
            if ((i3 & 16) != 0) {
                z2 = blockConfig.isPhysical;
            }
            return blockConfig.copy(voxelShape, i4, i5, z3, z2);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final VoxelShape getShape() {
            return this.shape;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final int getTexX() {
            return this.texX;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final int getTexY() {
            return this.texY;
        }

        /* JADX INFO: renamed from: component4, reason: from getter */
        public final boolean getIsTransparent() {
            return this.isTransparent;
        }

        /* JADX INFO: renamed from: component5, reason: from getter */
        public final boolean getIsPhysical() {
            return this.isPhysical;
        }

        public final BlockConfig copy(VoxelShape shape, int texX, int texY, boolean isTransparent, boolean isPhysical) {
            Intrinsics.checkNotNullParameter(shape, "shape");
            return new BlockConfig(shape, texX, texY, isTransparent, isPhysical);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof BlockConfig)) {
                return false;
            }
            BlockConfig blockConfig = (BlockConfig) other;
            return this.shape == blockConfig.shape && this.texX == blockConfig.texX && this.texY == blockConfig.texY && this.isTransparent == blockConfig.isTransparent && this.isPhysical == blockConfig.isPhysical;
        }

        public int hashCode() {
            return (((((((this.shape.hashCode() * 31) + Integer.hashCode(this.texX)) * 31) + Integer.hashCode(this.texY)) * 31) + Boolean.hashCode(this.isTransparent)) * 31) + Boolean.hashCode(this.isPhysical);
        }

        public String toString() {
            return "BlockConfig(shape=" + this.shape + ", texX=" + this.texX + ", texY=" + this.texY + ", isTransparent=" + this.isTransparent + ", isPhysical=" + this.isPhysical + Constant.AFTER_QUTO;
        }

        public BlockConfig(VoxelShape shape, int i, int i2, boolean z, boolean z2) {
            Intrinsics.checkNotNullParameter(shape, "shape");
            this.shape = shape;
            this.texX = i;
            this.texY = i2;
            this.isTransparent = z;
            this.isPhysical = z2;
        }

        public /* synthetic */ BlockConfig(VoxelShape voxelShape, int i, int i2, boolean z, boolean z2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
            this((i3 & 1) != 0 ? VoxelShape.CUBE : voxelShape, (i3 & 2) != 0 ? 0 : i, (i3 & 4) != 0 ? 0 : i2, (i3 & 8) == 0 ? z : false, (i3 & 16) != 0 ? true : z2);
        }

        public final VoxelShape getShape() {
            return this.shape;
        }

        public final int getTexX() {
            return this.texX;
        }

        public final int getTexY() {
            return this.texY;
        }

        public final boolean isTransparent() {
            return this.isTransparent;
        }

        public final boolean isPhysical() {
            return this.isPhysical;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/VoxelManager$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: VoxelManager.kt */
    @Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0016\n\u0002\u0010\u0005\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J&\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u000eJ>\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\u000eJ\u000e\u0010\u0018\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\bJ \u0010\u0019\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u000eH\u0007J&\u0010\u001a\u001a\u00020\b2\u0006\u0010\u001b\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u000eJ\u0010\u0010\u001c\u001a\u0004\u0018\u00010\t2\u0006\u0010\f\u001a\u00020\bJ\u0010\u0010\u001d\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0005H\u0007J.\u0010\u001e\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\b2\u0006\u0010\u001f\u001a\u00020\b2\u0006\u0010 \u001a\u00020\b2\u0006\u0010!\u001a\u00020\b2\u0006\u0010\"\u001a\u00020\bJ8\u0010#\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u000e2\u0006\u0010$\u001a\u00020\u00052\b\b\u0002\u0010\u001f\u001a\u00020%J\u0018\u0010&\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00052\u0006\u0010'\u001a\u00020(H\u0007J\u0016\u0010)\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00052\u0006\u0010*\u001a\u00020(R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006+"}, d2 = {"Lorg/catrobat/catroid/raptor/VoxelManager$Companion;", "", "()V", "blockRegistry", "Ljava/util/concurrent/ConcurrentHashMap;", "", "Lorg/catrobat/catroid/raptor/VoxelManager$BlockConfig;", "worlds", "", "Lorg/catrobat/catroid/raptor/VoxelManager$VoxelBuffer;", "configureBlock", "", "id", "shapeInt", "", "texX", "texY", "createWorld", "x", "y", CompressorStreamFactory.Z, "wx", "wy", "wz", "deleteWorld", "getBlockAbsolute", "getBlockInfo", "worldId", "getBuffer", "getConfig", "loadFromString", "data", "delimX", "delimY", "delimZ", "setBlock", ModuleXmlParser.TYPE, "", "setPhysical", "phys", "", "setTransparent", "isTransp", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void createWorld(String id2, int x, int y, int z, int wx, int wy, int wz) {
            Intrinsics.checkNotNullParameter(id2, "id");
            VoxelManager.worlds.put(id2, new VoxelBuffer(x, y, z, wx, wy, wz));
        }

        public final VoxelBuffer getBuffer(String id2) {
            Intrinsics.checkNotNullParameter(id2, "id");
            return (VoxelBuffer) VoxelManager.worlds.get(id2);
        }

        public final void configureBlock(short id2, int shapeInt, int texX, int texY) {
            VoxelShape[] voxelShapeArrValues = VoxelShape.values();
            VoxelManager.blockRegistry.put(Short.valueOf(id2), new BlockConfig((shapeInt < 0 || shapeInt > ArraysKt.getLastIndex(voxelShapeArrValues)) ? VoxelShape.CUBE : voxelShapeArrValues[shapeInt], texX, texY, false, false, 24, null));
        }

        @JvmStatic
        public final BlockConfig getConfig(short id2) {
            BlockConfig blockConfig = (BlockConfig) VoxelManager.blockRegistry.get(Short.valueOf(id2));
            if (blockConfig != null) {
                return blockConfig;
            }
            return new BlockConfig(null, 0, 0, false, false, 31, null);
        }

        public static /* synthetic */ void setBlock$default(Companion companion, String str, int i, int i2, int i3, short s, byte b, int i4, Object obj) {
            if ((i4 & 32) != 0) {
                b = 0;
            }
            companion.setBlock(str, i, i2, i3, s, b);
        }

        public final void setBlock(String id2, int x, int y, int z, short type, byte data) {
            Intrinsics.checkNotNullParameter(id2, "id");
            VoxelBuffer voxelBuffer = (VoxelBuffer) VoxelManager.worlds.get(id2);
            if (voxelBuffer != null) {
                voxelBuffer.set(x, y, z, type);
                voxelBuffer.setData(x, y, z, data);
            }
        }

        @JvmStatic
        public final void setPhysical(short id2, boolean phys) {
            BlockConfig blockConfig = (BlockConfig) VoxelManager.blockRegistry.get(Short.valueOf(id2));
            if (blockConfig == null) {
                blockConfig = new BlockConfig(null, 0, 0, false, false, 31, null);
            }
            BlockConfig blockConfig2 = blockConfig;
            VoxelManager.blockRegistry.put(Short.valueOf(id2), BlockConfig.copy$default(blockConfig2, null, 0, 0, false, phys, 15, null));
        }

        @JvmStatic
        public final short getBlockAbsolute(int x, int y, int z) {
            for (VoxelBuffer voxelBuffer : VoxelManager.worlds.values()) {
                if (x >= voxelBuffer.getWorldX() && x < voxelBuffer.getWorldX() + voxelBuffer.getSizeX() && y >= voxelBuffer.getWorldY() && y < voxelBuffer.getWorldY() + voxelBuffer.getSizeY() && z >= voxelBuffer.getWorldZ() && z < voxelBuffer.getWorldZ() + voxelBuffer.getSizeZ()) {
                    return voxelBuffer.get(x - voxelBuffer.getWorldX(), y - voxelBuffer.getWorldY(), z - voxelBuffer.getWorldZ());
                }
            }
            return (short) 0;
        }

        public final String getBlockInfo(String worldId, int x, int y, int z) {
            Intrinsics.checkNotNullParameter(worldId, "worldId");
            VoxelBuffer voxelBuffer = (VoxelBuffer) VoxelManager.worlds.get(worldId);
            if (voxelBuffer == null) {
                return "0\n0";
            }
            return ((int) voxelBuffer.get(x, y, z)) + "\n" + ((int) voxelBuffer.getData(x, y, z));
        }

        public final void loadFromString(String id2, String data, String delimX, String delimY, String delimZ) {
            Intrinsics.checkNotNullParameter(id2, "id");
            Intrinsics.checkNotNullParameter(data, "data");
            Intrinsics.checkNotNullParameter(delimX, "delimX");
            Intrinsics.checkNotNullParameter(delimY, "delimY");
            Intrinsics.checkNotNullParameter(delimZ, "delimZ");
            VoxelBuffer voxelBuffer = (VoxelBuffer) VoxelManager.worlds.get(id2);
            if (voxelBuffer == null) {
                return;
            }
            List listSplit$default = StringsKt.split$default((CharSequence) data, new String[]{delimY}, false, 0, 6, (Object) null);
            int iMin = Math.min(listSplit$default.size(), voxelBuffer.getSizeY());
            for (int i = 0; i < iMin; i++) {
                List listSplit$default2 = StringsKt.split$default((CharSequence) listSplit$default.get(i), new String[]{delimZ}, false, 0, 6, (Object) null);
                int iMin2 = Math.min(listSplit$default2.size(), voxelBuffer.getSizeZ());
                for (int i2 = 0; i2 < iMin2; i2++) {
                    List listSplit$default3 = StringsKt.split$default((CharSequence) listSplit$default2.get(i2), new String[]{delimX}, false, 0, 6, (Object) null);
                    int iMin3 = Math.min(listSplit$default3.size(), voxelBuffer.getSizeX());
                    for (int i3 = 0; i3 < iMin3; i3++) {
                        Short shortOrNull = StringsKt.toShortOrNull((String) listSplit$default3.get(i3));
                        voxelBuffer.getTypeData()[i3 + (voxelBuffer.getSizeX() * i) + (voxelBuffer.getSizeX() * i2 * voxelBuffer.getSizeY())] = shortOrNull != null ? shortOrNull.shortValue() : (short) 0;
                    }
                }
            }
        }

        public final void deleteWorld(String id2) {
            Intrinsics.checkNotNullParameter(id2, "id");
            VoxelManager.worlds.remove(id2);
        }

        public final void setTransparent(short id2, boolean isTransp) {
            BlockConfig blockConfig = (BlockConfig) VoxelManager.blockRegistry.get(Short.valueOf(id2));
            if (blockConfig == null) {
                blockConfig = new BlockConfig(null, 0, 0, false, false, 31, null);
            }
            BlockConfig blockConfig2 = blockConfig;
            VoxelManager.blockRegistry.put(Short.valueOf(id2), BlockConfig.copy$default(blockConfig2, null, 0, 0, isTransp, false, 23, null));
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/VoxelManager$VoxelBuffer.class
      classes14.dex
     */
    /* JADX INFO: compiled from: VoxelManager.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0012\n\u0002\b\u0007\n\u0002\u0010\u0017\n\u0002\b\u0006\n\u0002\u0010\n\n\u0002\b\u0004\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003¢\u0006\u0002\u0010\tJ\u001e\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u00032\u0006\u0010\u001c\u001a\u00020\u00032\u0006\u0010\u001d\u001a\u00020\u0003J\u001e\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u001b\u001a\u00020\u00032\u0006\u0010\u001c\u001a\u00020\u00032\u0006\u0010\u001d\u001a\u00020\u0003J&\u0010 \u001a\u00020!2\u0006\u0010\u001b\u001a\u00020\u00032\u0006\u0010\u001c\u001a\u00020\u00032\u0006\u0010\u001d\u001a\u00020\u00032\u0006\u0010\"\u001a\u00020\u001aJ&\u0010#\u001a\u00020!2\u0006\u0010\u001b\u001a\u00020\u00032\u0006\u0010\u001c\u001a\u00020\u00032\u0006\u0010\u001d\u001a\u00020\u00032\u0006\u0010$\u001a\u00020\u001fR\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u0011\u0010\u0012\u001a\u00020\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u000fR\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u000fR\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u000f¨\u0006%"}, d2 = {"Lorg/catrobat/catroid/raptor/VoxelManager$VoxelBuffer;", "", "sizeX", "", "sizeY", "sizeZ", "worldX", "worldY", "worldZ", "(IIIIII)V", "metaData", "", "getMetaData", "()[B", "getSizeX", "()I", "getSizeY", "getSizeZ", "typeData", "", "getTypeData", "()[S", "getWorldX", "getWorldY", "getWorldZ", "get", "", "x", "y", CompressorStreamFactory.Z, "getData", "", FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "", ModuleXmlParser.TYPE, "setData", "data", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class VoxelBuffer {
        private final byte[] metaData;
        private final int sizeX;
        private final int sizeY;
        private final int sizeZ;
        private final short[] typeData;
        private final int worldX;
        private final int worldY;
        private final int worldZ;

        public VoxelBuffer(int i, int i2, int i3, int i4, int i5, int i6) {
            this.sizeX = i;
            this.sizeY = i2;
            this.sizeZ = i3;
            this.worldX = i4;
            this.worldY = i5;
            this.worldZ = i6;
            this.typeData = new short[i * i2 * i3];
            this.metaData = new byte[i * i2 * i3];
        }

        public final int getSizeX() {
            return this.sizeX;
        }

        public final int getSizeY() {
            return this.sizeY;
        }

        public final int getSizeZ() {
            return this.sizeZ;
        }

        public final int getWorldX() {
            return this.worldX;
        }

        public final int getWorldY() {
            return this.worldY;
        }

        public final int getWorldZ() {
            return this.worldZ;
        }

        public final short[] getTypeData() {
            return this.typeData;
        }

        public final byte[] getMetaData() {
            return this.metaData;
        }

        public final void set(int x, int y, int z, short type) {
            int i;
            int i2;
            if (x < 0 || x >= (i = this.sizeX) || y < 0 || y >= (i2 = this.sizeY) || z < 0 || z >= this.sizeZ) {
                return;
            }
            this.typeData[x + (y * i) + (z * i * i2)] = type;
        }

        public final short get(int x, int y, int z) {
            int i;
            int i2;
            if (x < 0 || x >= (i = this.sizeX) || y < 0 || y >= (i2 = this.sizeY) || z < 0 || z >= this.sizeZ) {
                return (short) 0;
            }
            return this.typeData[x + (y * i) + (z * i * i2)];
        }

        public final void setData(int x, int y, int z, byte data) {
            int i;
            int i2;
            if (x < 0 || x >= (i = this.sizeX) || y < 0 || y >= (i2 = this.sizeY) || z < 0 || z >= this.sizeZ) {
                return;
            }
            this.metaData[x + (y * i) + (z * i * i2)] = data;
        }

        public final byte getData(int x, int y, int z) {
            int i;
            int i2;
            if (x < 0 || x >= (i = this.sizeX) || y < 0 || y >= (i2 = this.sizeY) || z < 0 || z >= this.sizeZ) {
                return (byte) 0;
            }
            return this.metaData[x + (y * i) + (z * i * i2)];
        }
    }
}
