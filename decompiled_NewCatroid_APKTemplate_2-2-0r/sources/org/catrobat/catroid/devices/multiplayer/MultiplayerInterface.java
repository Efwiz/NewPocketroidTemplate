package org.catrobat.catroid.devices.multiplayer;

import java.io.InputStream;
import java.io.OutputStream;
import kotlin.Metadata;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.formulaeditor.UserVariable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/multiplayer/MultiplayerInterface.class
  classes14.dex
 */
/* JADX INFO: compiled from: MultiplayerInterface.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&J\u0012\u0010\u0006\u001a\u00020\u00032\b\u0010\u0007\u001a\u0004\u0018\u00010\bH&J\u001c\u0010\t\u001a\u00020\u00032\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rH&¨\u0006\u000e"}, d2 = {"Lorg/catrobat/catroid/devices/multiplayer/MultiplayerInterface;", "Lorg/catrobat/catroid/bluetooth/base/BluetoothDevice;", "getChangedMultiplayerVariables", "", "receivedData", "", "sendChangedMultiplayerVariables", "multiplayerVariable", "Lorg/catrobat/catroid/formulaeditor/UserVariable;", "setStreams", "inputStream", "Ljava/io/InputStream;", "outputStream", "Ljava/io/OutputStream;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface MultiplayerInterface extends BluetoothDevice {
    void getChangedMultiplayerVariables(byte[] receivedData);

    void sendChangedMultiplayerVariables(UserVariable multiplayerVariable);

    void setStreams(InputStream inputStream, OutputStream outputStream);
}
