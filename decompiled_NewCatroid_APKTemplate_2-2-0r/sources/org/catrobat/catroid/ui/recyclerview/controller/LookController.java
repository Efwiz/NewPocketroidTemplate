package org.catrobat.catroid.ui.recyclerview.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.io.StorageOperations;
import org.catrobat.catroid.ui.controller.BackpackListManager;
import org.catrobat.catroid.ui.recyclerview.util.UniqueNameProvider;
import org.catrobat.catroid.utils.Utils;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/controller/LookController.class
  classes14.dex
 */
/* JADX INFO: compiled from: LookController.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J$\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u00062\b\u0010\b\u001a\u0004\u0018\u00010\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bJ\u0010\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0006J$\u0010\u000f\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u00062\b\u0010\b\u001a\u0004\u0018\u00010\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bJ\u0012\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\tH\u0002J\u0010\u0010\u0013\u001a\u00020\u00062\b\u0010\u0014\u001a\u0004\u0018\u00010\u0006J\u001a\u0010\u0015\u001a\u00020\u00062\b\u0010\u0014\u001a\u0004\u0018\u00010\u00062\b\u0010\n\u001a\u0004\u0018\u00010\u000bJ$\u0010\u0016\u001a\u00020\u00062\b\u0010\u0017\u001a\u0004\u0018\u00010\u00062\b\u0010\b\u001a\u0004\u0018\u00010\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bJ$\u0010\u0018\u001a\u00020\u00062\b\u0010\u0017\u001a\u0004\u0018\u00010\u00062\b\u0010\b\u001a\u0004\u0018\u00010\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bJ\u0016\u0010\u0019\u001a\u00020\u001a*\u00020\u00112\b\u0010\u001b\u001a\u0004\u0018\u00010\u0011H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/controller/LookController;", "", "()V", "uniqueNameProvider", "Lorg/catrobat/catroid/ui/recyclerview/util/UniqueNameProvider;", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "Lorg/catrobat/catroid/common/LookData;", "lookToCopy", "dstScene", "Lorg/catrobat/catroid/content/Scene;", "dstSprite", "Lorg/catrobat/catroid/content/Sprite;", "delete", "", "lookToDelete", "findOrCopy", "getImageDir", "Ljava/io/File;", "scene", ConfigConstants.CONFIG_PACK_SECTION, "lookToPack", "packForSprite", "unpack", "lookToUnpack", "unpackForSprite", "checksumEquals", "", "file", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LookController {
    private final UniqueNameProvider uniqueNameProvider = new UniqueNameProvider();

    public final LookData copy(LookData lookToCopy, Scene dstScene, Sprite dstSprite) throws IOException {
        return new LookData(this.uniqueNameProvider.getUniqueNameInNameables(lookToCopy != null ? lookToCopy.f512name : null, dstSprite != null ? dstSprite.getLookList() : null), StorageOperations.copyFileToDir(lookToCopy != null ? lookToCopy.getFile() : null, dstScene != null ? getImageDir(dstScene) : null));
    }

    public final LookData findOrCopy(LookData lookToCopy, Scene dstScene, Sprite dstSprite) throws IOException {
        List<LookData> lookList;
        List<LookData> lookList2;
        LookData lookData = null;
        Object obj = null;
        lookData = null;
        if (dstSprite != null && (lookList2 = dstSprite.getLookList()) != null) {
            for (Object obj2 : lookList2) {
                File file = ((LookData) obj2).getFile();
                Intrinsics.checkNotNullExpressionValue(file, "getFile(...)");
                if (checksumEquals(file, lookToCopy != null ? lookToCopy.getFile() : null)) {
                    obj = obj2;
                    break;
                }
            }
            lookData = (LookData) obj;
        }
        if (lookData != null) {
            return lookData;
        }
        LookData lookDataCopy = copy(lookToCopy, dstScene, dstSprite);
        if (dstSprite != null && (lookList = dstSprite.getLookList()) != null) {
            lookList.add(lookDataCopy);
        }
        return lookDataCopy;
    }

    public final void delete(LookData lookToDelete) throws IOException {
        File file;
        if (lookToDelete == null || (file = lookToDelete.getFile()) == null || !file.exists()) {
            return;
        }
        StorageOperations.deleteFile(lookToDelete.getFile());
    }

    public final LookData pack(LookData lookToPack) throws IOException {
        return new LookData(this.uniqueNameProvider.getUniqueNameInNameables(lookToPack != null ? lookToPack.f512name : null, BackpackListManager.getInstance().getBackpackedLooks()), StorageOperations.copyFileToDir(lookToPack != null ? lookToPack.getFile() : null, BackpackListManager.getInstance().backpackImageDirectory));
    }

    public final LookData packForSprite(LookData lookToPack, Sprite dstSprite) throws IOException {
        LookData lookData;
        List<LookData> lookList;
        List<LookData> lookList2;
        Object next;
        File file;
        if (dstSprite == null || (lookList2 = dstSprite.getLookList()) == null) {
            lookData = null;
        } else {
            Iterator<T> it = lookList2.iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                file = ((LookData) next).getFile();
                Intrinsics.checkNotNullExpressionValue(file, "getFile(...)");
            } while (!checksumEquals(file, lookToPack != null ? lookToPack.getFile() : null));
            lookData = (LookData) next;
        }
        if (lookData != null) {
            return lookData;
        }
        LookData lookData2 = new LookData(lookToPack != null ? lookToPack.f512name : null, StorageOperations.copyFileToDir(lookToPack != null ? lookToPack.getFile() : null, BackpackListManager.getInstance().backpackImageDirectory));
        if (dstSprite != null && (lookList = dstSprite.getLookList()) != null) {
            lookList.add(lookData2);
        }
        return lookData2;
    }

    public final LookData unpack(LookData lookToUnpack, Scene dstScene, Sprite dstSprite) throws IOException {
        return new LookData(this.uniqueNameProvider.getUniqueNameInNameables(lookToUnpack != null ? lookToUnpack.f512name : null, dstSprite != null ? dstSprite.getLookList() : null), StorageOperations.copyFileToDir(lookToUnpack != null ? lookToUnpack.getFile() : null, dstScene != null ? getImageDir(dstScene) : null));
    }

    public final LookData unpackForSprite(LookData lookToUnpack, Scene dstScene, Sprite dstSprite) throws IOException {
        List<LookData> lookList;
        List<LookData> lookList2;
        LookData lookData = null;
        Object obj = null;
        lookData = null;
        if (dstSprite != null && (lookList2 = dstSprite.getLookList()) != null) {
            for (Object obj2 : lookList2) {
                File file = ((LookData) obj2).getFile();
                Intrinsics.checkNotNullExpressionValue(file, "getFile(...)");
                if (checksumEquals(file, lookToUnpack != null ? lookToUnpack.getFile() : null)) {
                    obj = obj2;
                    break;
                }
            }
            lookData = (LookData) obj;
        }
        if (lookData != null) {
            return lookData;
        }
        LookData lookDataUnpack = unpack(lookToUnpack, dstScene, dstSprite);
        if (dstSprite != null && (lookList = dstSprite.getLookList()) != null) {
            lookList.add(lookDataUnpack);
        }
        return lookDataUnpack;
    }

    private final File getImageDir(Scene scene) {
        return new File(scene != null ? scene.getDirectory() : null, Constants.IMAGE_DIRECTORY_NAME);
    }

    private final boolean checksumEquals(File file, File file2) {
        if (file2 == null) {
            return true;
        }
        return Intrinsics.areEqual(Utils.md5Checksum(file), Utils.md5Checksum(file2));
    }
}
