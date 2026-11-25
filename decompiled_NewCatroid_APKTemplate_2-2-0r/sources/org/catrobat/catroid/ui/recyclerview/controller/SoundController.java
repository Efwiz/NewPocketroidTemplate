package org.catrobat.catroid.ui.recyclerview.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.SoundInfo;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.io.StorageOperations;
import org.catrobat.catroid.ui.controller.BackpackListManager;
import org.catrobat.catroid.ui.recyclerview.util.UniqueNameProvider;
import org.catrobat.catroid.utils.Utils;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/controller/SoundController.class
  classes14.dex
 */
/* JADX INFO: compiled from: SoundController.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\t\u001a\u0004\u0018\u00010\bH\u0002J$\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u000b2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010J\u0010\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u000bJ$\u0010\u0014\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u000b2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010J\u0012\u0010\u0015\u001a\u00020\b2\b\u0010\u0016\u001a\u0004\u0018\u00010\u000eH\u0002J\u0010\u0010\u0017\u001a\u00020\u000b2\b\u0010\u0018\u001a\u0004\u0018\u00010\u000bJ\u001a\u0010\u0019\u001a\u00020\u000b2\b\u0010\u0018\u001a\u0004\u0018\u00010\u000b2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010J$\u0010\u001a\u001a\u00020\u000b2\b\u0010\u001b\u001a\u0004\u0018\u00010\u000b2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010J$\u0010\u001c\u001a\u00020\u000b2\b\u0010\u001b\u001a\u0004\u0018\u00010\u000b2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/controller/SoundController;", "", "()V", "uniqueNameProvider", "Lorg/catrobat/catroid/ui/recyclerview/util/UniqueNameProvider;", "compareByChecksum", "", "file1", "Ljava/io/File;", "file2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "Lorg/catrobat/catroid/common/SoundInfo;", "soundToCopy", "dstScene", "Lorg/catrobat/catroid/content/Scene;", "dstSprite", "Lorg/catrobat/catroid/content/Sprite;", "delete", "", "soundToDelete", "findOrCopy", "getSoundDir", "scene", ConfigConstants.CONFIG_PACK_SECTION, "soundToPack", "packForSprite", "unpack", "soundToUnpack", "unpackForSprite", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SoundController {
    private final UniqueNameProvider uniqueNameProvider = new UniqueNameProvider();

    public final SoundInfo copy(SoundInfo soundToCopy, Scene dstScene, Sprite dstSprite) throws IOException {
        return new SoundInfo(this.uniqueNameProvider.getUniqueNameInNameables(soundToCopy != null ? soundToCopy.f517name : null, dstSprite != null ? dstSprite.getSoundList() : null), StorageOperations.copyFileToDir(soundToCopy != null ? soundToCopy.getFile() : null, getSoundDir(dstScene)));
    }

    public final SoundInfo findOrCopy(SoundInfo soundToCopy, Scene dstScene, Sprite dstSprite) throws IOException {
        List<SoundInfo> soundList;
        List<SoundInfo> soundList2;
        if (dstSprite != null && (soundList2 = dstSprite.getSoundList()) != null) {
            for (SoundInfo soundInfo : soundList2) {
                if (compareByChecksum(soundInfo.getFile(), soundToCopy != null ? soundToCopy.getFile() : null)) {
                    Intrinsics.checkNotNull(soundInfo);
                    return soundInfo;
                }
            }
        }
        SoundInfo soundInfoCopy = copy(soundToCopy, dstScene, dstSprite);
        if (dstSprite != null && (soundList = dstSprite.getSoundList()) != null) {
            soundList.add(soundInfoCopy);
        }
        return soundInfoCopy;
    }

    public final void delete(SoundInfo soundToDelete) throws IOException {
        StorageOperations.deleteFile(soundToDelete != null ? soundToDelete.getFile() : null);
    }

    public final SoundInfo pack(SoundInfo soundToPack) throws IOException {
        return new SoundInfo(this.uniqueNameProvider.getUniqueNameInNameables(soundToPack != null ? soundToPack.f517name : null, BackpackListManager.getInstance().getBackpackedSounds()), StorageOperations.copyFileToDir(soundToPack != null ? soundToPack.getFile() : null, BackpackListManager.getInstance().backpackSoundDirectory));
    }

    public final SoundInfo packForSprite(SoundInfo soundToPack, Sprite dstSprite) throws IOException {
        List<SoundInfo> soundList;
        List<SoundInfo> soundList2;
        if (dstSprite != null && (soundList2 = dstSprite.getSoundList()) != null) {
            for (SoundInfo soundInfo : soundList2) {
                if (compareByChecksum(soundInfo.getFile(), soundToPack != null ? soundToPack.getFile() : null)) {
                    Intrinsics.checkNotNull(soundInfo);
                    return soundInfo;
                }
            }
        }
        SoundInfo soundInfo2 = new SoundInfo(soundToPack != null ? soundToPack.f517name : null, StorageOperations.copyFileToDir(soundToPack != null ? soundToPack.getFile() : null, BackpackListManager.getInstance().backpackSoundDirectory));
        if (dstSprite != null && (soundList = dstSprite.getSoundList()) != null) {
            soundList.add(soundInfo2);
        }
        return soundInfo2;
    }

    public final SoundInfo unpack(SoundInfo soundToUnpack, Scene dstScene, Sprite dstSprite) throws IOException {
        return new SoundInfo(this.uniqueNameProvider.getUniqueNameInNameables(soundToUnpack != null ? soundToUnpack.f517name : null, dstSprite != null ? dstSprite.getSoundList() : null), StorageOperations.copyFileToDir(soundToUnpack != null ? soundToUnpack.getFile() : null, getSoundDir(dstScene)));
    }

    public final SoundInfo unpackForSprite(SoundInfo soundToUnpack, Scene dstScene, Sprite dstSprite) throws IOException {
        List<SoundInfo> soundList;
        List<SoundInfo> soundList2;
        if (dstSprite != null && (soundList2 = dstSprite.getSoundList()) != null) {
            for (SoundInfo soundInfo : soundList2) {
                if (compareByChecksum(soundInfo.getFile(), soundToUnpack != null ? soundToUnpack.getFile() : null)) {
                    Intrinsics.checkNotNull(soundInfo);
                    return soundInfo;
                }
            }
        }
        SoundInfo soundInfoUnpack = unpack(soundToUnpack, dstScene, dstSprite);
        if (dstSprite != null && (soundList = dstSprite.getSoundList()) != null) {
            soundList.add(soundInfoUnpack);
        }
        return soundInfoUnpack;
    }

    private final File getSoundDir(Scene scene) {
        return new File(scene != null ? scene.getDirectory() : null, Constants.SOUND_DIRECTORY_NAME);
    }

    private final boolean compareByChecksum(File file1, File file2) {
        if (file1 == null || file2 == null) {
            return true;
        }
        return Intrinsics.areEqual(Utils.md5Checksum(file1), Utils.md5Checksum(file2));
    }
}
