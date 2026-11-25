package org.catrobat.catroid.nfc;

import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Parcelable;
import android.util.Log;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.content.EventWrapper;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.eventids.EventId;
import org.catrobat.catroid.content.eventids.NfcEventId;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/nfc/NfcHandler.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class NfcHandler {
    private static final String TAG = "NfcHandler";
    private static Tag lastTag;
    private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");
    private static String nfcTagId = "0x0";
    private static String nfcTagMessage = "";

    private static boolean payloadStartContainsText(byte payloadStart) {
        return payloadStart != 1;
    }

    private NfcHandler() {
    }

    public static void processIntent(Intent intent) {
        if (intent == null) {
            return;
        }
        Tag tag = (Tag) intent.getParcelableExtra("android.nfc.extra.TAG");
        if (tag != null) {
            lastTag = tag;
        }
        String tagIdFromIntent = getTagIdFromIntent(intent);
        if (tagIdFromIntent != null) {
            setLastNfcTagId(tagIdFromIntent);
            setLastNfcTagMessage(getMessageFromIntent(intent));
            fireNfcEvents(tagIdFromIntent);
        }
    }

    public static synchronized String getTagTechnologies() {
        Tag tag = lastTag;
        if (tag == null) {
            return "";
        }
        return Arrays.toString(tag.getTechList());
    }

    public static synchronized int getTagMemorySize() {
        Tag tag = lastTag;
        if (tag == null) {
            return 0;
        }
        try {
            MifareClassic mifareClassic = MifareClassic.get(tag);
            if (mifareClassic != null) {
                mifareClassic.connect();
                int size = mifareClassic.getSize();
                mifareClassic.close();
                return size;
            }
            Ndef ndef = Ndef.get(lastTag);
            if (ndef != null) {
                ndef.connect();
                int maxSize = ndef.getMaxSize();
                ndef.close();
                return maxSize;
            }
            return 0;
        } catch (IOException e) {
            Log.e(TAG, "Failed to get tag size", e);
        }
    }

    public static synchronized boolean isTagWritable() {
        Tag tag = lastTag;
        if (tag == null) {
            return false;
        }
        try {
            Ndef ndef = Ndef.get(tag);
            if (ndef != null) {
                ndef.connect();
                boolean zIsWritable = ndef.isWritable();
                ndef.close();
                return zIsWritable;
            }
            return NdefFormatable.get(lastTag) != null;
        } catch (IOException e) {
            Log.e(TAG, "Failed to check if tag is writable", e);
            return false;
        }
    }

    public static synchronized String getTagType() {
        Tag tag = lastTag;
        if (tag == null) {
            return "Unknown";
        }
        try {
            MifareClassic mifareClassic = MifareClassic.get(tag);
            if (mifareClassic != null) {
                mifareClassic.connect();
                int type = mifareClassic.getType();
                if (type == 0) {
                    return "MIFARE Classic";
                }
                if (type == 1) {
                    return "MIFARE Plus";
                }
                if (type == 2) {
                    return "MIFARE Pro";
                }
                return "MIFARE Unknown";
            }
            Ndef ndef = Ndef.get(lastTag);
            if (ndef != null) {
                return "NDEF (" + ndef.getType() + Constant.AFTER_QUTO;
            }
            String[] techList = lastTag.getTechList();
            if (techList != null && techList.length > 0) {
                String str = techList[0];
                return str.substring(str.lastIndexOf(46) + 1);
            }
            return "Generic Tag";
        } catch (Exception e) {
            Log.e(TAG, "Failed to get tag type", e);
        }
    }

    public static String getTagIdFromIntent(Intent intent) {
        if (!"android.nfc.action.TAG_DISCOVERED".equals(intent.getAction()) && !"android.nfc.action.NDEF_DISCOVERED".equals(intent.getAction()) && !"android.nfc.action.TECH_DISCOVERED".equals(intent.getAction())) {
            return null;
        }
        String strValueOf = String.valueOf(byteArrayToHex(intent.getByteArrayExtra("android.nfc.extra.ID")));
        setLastNfcTagId(strValueOf);
        Log.d(TAG, "read successful. uid = int: " + strValueOf);
        return strValueOf;
    }

    public static String getMessageFromIntent(Intent intent) {
        Parcelable[] parcelableArrayExtra;
        if (("android.nfc.action.TAG_DISCOVERED".equals(intent.getAction()) || "android.nfc.action.NDEF_DISCOVERED".equals(intent.getAction()) || "android.nfc.action.TECH_DISCOVERED".equals(intent.getAction())) && (parcelableArrayExtra = intent.getParcelableArrayExtra("android.nfc.extra.NDEF_MESSAGES")) != null) {
            try {
                NdefMessage[] ndefMessageArr = new NdefMessage[parcelableArrayExtra.length];
                for (int i = 0; i < parcelableArrayExtra.length; i++) {
                    ndefMessageArr[i] = (NdefMessage) parcelableArrayExtra[i];
                }
                if (ndefMessageArr[0] != null) {
                    StringBuilder sb = new StringBuilder();
                    byte[] payload = ndefMessageArr[0].getRecords()[0].getPayload();
                    if (payload.length > 0) {
                        for (int i2 = !payloadStartContainsText(payload[0]) ? 1 : 0; i2 < payload.length; i2++) {
                            sb.append((char) payload[i2]);
                        }
                    }
                    return sb.toString();
                }
            } catch (Exception e) {
                Log.e(TAG, "Error parsing NDEF message, returning empty.", e);
            }
        }
        return "";
    }

    private static void fireNfcEvents(String uid) {
        EventWrapper eventWrapper = new EventWrapper(new NfcEventId(uid), false);
        EventWrapper eventWrapper2 = new EventWrapper(new EventId(5), false);
        Project currentProject = ProjectManager.getInstance().getCurrentProject();
        currentProject.fireToAllSprites(eventWrapper);
        currentProject.fireToAllSprites(eventWrapper2);
    }

    public static Object getLastNfcTagMessage() {
        return nfcTagMessage;
    }

    public static String getLastNfcTagId() {
        return nfcTagId;
    }

    public static void setLastNfcTagId(String tagID) {
        nfcTagId = tagID;
    }

    public static void setLastNfcTagMessage(String message) {
        nfcTagMessage = message;
    }

    public static String byteArrayToHex(byte[] a) {
        if (a == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : a) {
            sb.append(String.format("%02x", Integer.valueOf(b & 255)));
        }
        return sb.toString();
    }

    public static void writeTag(Tag tag, NdefMessage message) {
        if (tag != null) {
            try {
                Ndef ndef = Ndef.get(tag);
                if (ndef == null) {
                    NdefFormatable ndefFormatable = NdefFormatable.get(tag);
                    if (ndefFormatable != null) {
                        ndefFormatable.connect();
                        ndefFormatable.format(message);
                        ndefFormatable.close();
                    }
                } else {
                    ndef.connect();
                    ndef.writeNdefMessage(message);
                    ndef.close();
                }
            } catch (FormatException | IOException e) {
                Log.d(TAG, "Couldn't create message", e);
            }
        }
    }

    public static NdefMessage createMessage(String message, int spinnerSelection) {
        NdefRecord ndefRecord;
        NdefRecord ndefRecord2;
        NdefRecord ndefRecordCreateExternal;
        switch (spinnerSelection) {
            case 0:
                Charset charset = UTF8_CHARSET;
                ndefRecord = new NdefRecord((short) 2, "text/plain".getBytes(charset), new byte[0], message.getBytes(charset));
                ndefRecordCreateExternal = ndefRecord;
                break;
            case 1:
                byte[] bytes = deleteProtocolPrefixIfExist(message).getBytes(UTF8_CHARSET);
                byte[] bArr = new byte[bytes.length + 1];
                bArr[0] = 3;
                System.arraycopy(bytes, 0, bArr, 1, bytes.length);
                ndefRecord2 = new NdefRecord((short) 1, NdefRecord.RTD_URI, new byte[0], bArr);
                ndefRecordCreateExternal = ndefRecord2;
                break;
            case 2:
                byte[] bytes2 = deleteProtocolPrefixIfExist(message).getBytes(UTF8_CHARSET);
                byte[] bArr2 = new byte[bytes2.length + 1];
                bArr2[0] = 4;
                System.arraycopy(bytes2, 0, bArr2, 1, bytes2.length);
                ndefRecord2 = new NdefRecord((short) 1, NdefRecord.RTD_URI, new byte[0], bArr2);
                ndefRecordCreateExternal = ndefRecord2;
                break;
            case 3:
                Charset charset2 = UTF8_CHARSET;
                ndefRecord = new NdefRecord((short) 4, "nfclab.com:smsService".getBytes(charset2), new byte[0], ("sms:" + message + "?body=SMS from Catrobat").getBytes(charset2));
                ndefRecordCreateExternal = ndefRecord;
                break;
            case 4:
                byte[] bytes3 = message.getBytes(UTF8_CHARSET);
                byte[] bArr3 = new byte[bytes3.length + 1];
                bArr3[0] = 5;
                System.arraycopy(bytes3, 0, bArr3, 1, bytes3.length);
                ndefRecord2 = new NdefRecord((short) 1, NdefRecord.RTD_URI, new byte[0], bArr3);
                ndefRecordCreateExternal = ndefRecord2;
                break;
            case 5:
                byte[] bytes4 = message.getBytes(UTF8_CHARSET);
                byte[] bArr4 = new byte[bytes4.length + 1];
                bArr4[0] = 6;
                System.arraycopy(bytes4, 0, bArr4, 1, bytes4.length);
                ndefRecord2 = new NdefRecord((short) 1, NdefRecord.RTD_URI, new byte[0], bArr4);
                ndefRecordCreateExternal = ndefRecord2;
                break;
            case 6:
                ndefRecordCreateExternal = NdefRecord.createExternal("catrobat.com", Constants.FLAVOR_POCKET_CODE, message.getBytes(UTF8_CHARSET));
                break;
            case 7:
                ndefRecordCreateExternal = new NdefRecord((short) 0, new byte[0], new byte[0], new byte[0]);
                break;
            default:
                ndefRecordCreateExternal = NdefRecord.createUri(message);
                break;
        }
        return new NdefMessage(new NdefRecord[]{ndefRecordCreateExternal});
    }

    public static String deleteProtocolPrefixIfExist(String url) {
        return url.replaceFirst("^\\w+://", "");
    }
}
