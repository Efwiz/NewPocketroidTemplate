package org.jetbrains.kotlin.com.intellij.openapi.vfs.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileTooBigException;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtil;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtilRt;
import org.jetbrains.kotlin.com.intellij.util.io.ResourceHandle;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ZipHandlerBase extends ArchiveHandler {
    private static final Logger LOG = Logger.getInstance((Class<?>) ZipHandlerBase.class);
    public static volatile boolean USE_CRC_INSTEAD_OF_TIMESTAMP = getUseCrcInsteadOfTimestampPropertyValue();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 3 || i == 8 || i == 5 || i == 6) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 1 || i == 3 || i == 8 || i == 5 || i == 6) ? 3 : 2];
        if (i == 1) {
            objArr[0] = "path";
        } else if (i == 3) {
            objArr[0] = ArchiveStreamFactory.ZIP;
        } else if (i == 8 || i == 5 || i == 6) {
            objArr[0] = "relativePath";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/impl/ZipHandlerBase";
        }
        switch (i) {
            case 1:
            case 3:
            case 5:
            case 6:
            case 8:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/impl/ZipHandlerBase";
                break;
            case 2:
                objArr[1] = "createEntriesMap";
                break;
            case 4:
                objArr[1] = "buildEntryMapForZipFile";
                break;
            case 7:
                objArr[1] = "contentsToByteArray";
                break;
            case 9:
            case 10:
                objArr[1] = "getInputStream";
                break;
            default:
                objArr[1] = "getArchiveCrcHashes";
                break;
        }
        if (i == 1) {
            objArr[2] = "<init>";
        } else if (i == 3) {
            objArr[2] = "buildEntryMapForZipFile";
        } else if (i == 8) {
            objArr[2] = "getInputStream";
        } else if (i == 5) {
            objArr[2] = "getEntryCrc";
        } else if (i == 6) {
            objArr[2] = "contentsToByteArray";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3 && i != 8 && i != 5 && i != 6) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    protected abstract ResourceHandle<ZipFile> acquireZipHandle() throws IOException;

    protected abstract long getEntryFileStamp();

    private static boolean getUseCrcInsteadOfTimestampPropertyValue() {
        return Boolean.getBoolean("zip.handler.uses.crc.instead.of.timestamp");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ZipHandlerBase(String str) {
        super(str);
        if (str == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.impl.ArchiveHandler
    protected Map<String, ArchiveHandler.EntryInfo> createEntriesMap() throws IOException {
        ResourceHandle<ZipFile> resourceHandleAcquireZipHandle = acquireZipHandle();
        try {
            Map<String, ArchiveHandler.EntryInfo> mapBuildEntryMapForZipFile = buildEntryMapForZipFile(resourceHandleAcquireZipHandle.get());
            if (resourceHandleAcquireZipHandle != null) {
                resourceHandleAcquireZipHandle.close();
            }
            if (mapBuildEntryMapForZipFile == null) {
                $$$reportNull$$$0(2);
            }
            return mapBuildEntryMapForZipFile;
        } catch (Throwable th) {
            if (resourceHandleAcquireZipHandle != null) {
                try {
                    resourceHandleAcquireZipHandle.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    protected Map<String, ArchiveHandler.EntryInfo> buildEntryMapForZipFile(ZipFile zipFile) {
        if (zipFile == null) {
            $$$reportNull$$$0(3);
        }
        ZipEntryMap zipEntryMap = new ZipEntryMap(zipFile.size());
        Enumeration<? extends ZipEntry> enumerationEntries = zipFile.entries();
        while (enumerationEntries.hasMoreElements()) {
            final ZipEntry zipEntryNextElement = enumerationEntries.nextElement();
            processEntry(zipEntryMap, LOG, zipEntryNextElement.getName(), zipEntryNextElement.isDirectory() ? null : new BiFunction() { // from class: org.jetbrains.kotlin.com.intellij.openapi.vfs.impl.ZipHandlerBase$$ExternalSyntheticLambda0
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return this.f$0.m6513xeb294359(zipEntryNextElement, (ArchiveHandler.EntryInfo) obj, (String) obj2);
                }
            });
        }
        return zipEntryMap;
    }

    /* JADX INFO: renamed from: lambda$buildEntryMapForZipFile$0$org-jetbrains-kotlin-com-intellij-openapi-vfs-impl-ZipHandlerBase, reason: not valid java name */
    /* synthetic */ ArchiveHandler.EntryInfo m6513xeb294359(ZipEntry zipEntry, ArchiveHandler.EntryInfo entryInfo, String str) {
        return new ArchiveHandler.EntryInfo(str, false, zipEntry.getSize(), USE_CRC_INSTEAD_OF_TIMESTAMP ? zipEntry.getCrc() : getEntryFileStamp(), entryInfo);
    }

    public byte[] contentsToByteArray(String str) throws IOException {
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        ResourceHandle<ZipFile> resourceHandleAcquireZipHandle = acquireZipHandle();
        try {
            ZipFile zipFile = resourceHandleAcquireZipHandle.get();
            ZipEntry entry = zipFile.getEntry(str);
            if (entry != null) {
                long size = entry.getSize();
                if (FileUtilRt.isTooLarge(size)) {
                    throw new FileTooBigException(getFile() + "!/" + str);
                }
                InputStream inputStream = zipFile.getInputStream(entry);
                if (inputStream != null) {
                    try {
                        byte[] bArrLoadBytes = FileUtil.loadBytes(inputStream, (int) size);
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        if (resourceHandleAcquireZipHandle != null) {
                            resourceHandleAcquireZipHandle.close();
                        }
                        if (bArrLoadBytes == null) {
                            $$$reportNull$$$0(7);
                        }
                        return bArrLoadBytes;
                    } catch (Throwable th) {
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            if (resourceHandleAcquireZipHandle != null) {
                resourceHandleAcquireZipHandle.close();
            }
            throw new FileNotFoundException(getFile() + "!/" + str);
        } catch (Throwable th3) {
            if (resourceHandleAcquireZipHandle != null) {
                try {
                    resourceHandleAcquireZipHandle.close();
                } catch (Throwable th4) {
                    th3.addSuppressed(th4);
                }
            }
            throw th3;
        }
    }
}
