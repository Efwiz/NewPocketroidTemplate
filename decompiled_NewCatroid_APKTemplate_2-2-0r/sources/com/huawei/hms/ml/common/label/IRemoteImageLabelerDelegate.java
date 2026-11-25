package com.huawei.hms.ml.common.label;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.huawei.hms.feature.dynamic.IObjectWrapper;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public interface IRemoteImageLabelerDelegate extends IInterface {
    public static final String DESCRIPTOR = "com.huawei.hms.ml.common.label.IRemoteImageLabelerDelegate";

    public static class Default implements IRemoteImageLabelerDelegate {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.huawei.hms.ml.common.label.IRemoteImageLabelerDelegate
        public int destroy() throws RemoteException {
            return 0;
        }

        @Override // com.huawei.hms.ml.common.label.IRemoteImageLabelerDelegate
        public List<ImageLabelParcel> detect(Bundle bundle, ImageLabelerFrameParcel imageLabelerFrameParcel, ImageLabelerOptionsParcel imageLabelerOptionsParcel) throws RemoteException {
            return null;
        }

        @Override // com.huawei.hms.ml.common.label.IRemoteImageLabelerDelegate
        public int initialize(IObjectWrapper iObjectWrapper, ImageLabelerOptionsParcel imageLabelerOptionsParcel) throws RemoteException {
            return 0;
        }
    }

    int destroy() throws RemoteException;

    List<ImageLabelParcel> detect(Bundle bundle, ImageLabelerFrameParcel imageLabelerFrameParcel, ImageLabelerOptionsParcel imageLabelerOptionsParcel) throws RemoteException;

    int initialize(IObjectWrapper iObjectWrapper, ImageLabelerOptionsParcel imageLabelerOptionsParcel) throws RemoteException;

    public static abstract class Stub extends Binder implements IRemoteImageLabelerDelegate {
        static final int TRANSACTION_destroy = 3;
        static final int TRANSACTION_detect = 2;
        static final int TRANSACTION_initialize = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IRemoteImageLabelerDelegate.DESCRIPTOR);
        }

        public static IRemoteImageLabelerDelegate asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IRemoteImageLabelerDelegate.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRemoteImageLabelerDelegate)) {
                return (IRemoteImageLabelerDelegate) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1598968902) {
                parcel2.writeString(IRemoteImageLabelerDelegate.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                parcel.enforceInterface(IRemoteImageLabelerDelegate.DESCRIPTOR);
                int iInitialize = initialize(IObjectWrapper.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt() != 0 ? ImageLabelerOptionsParcel.CREATOR.createFromParcel(parcel) : null);
                parcel2.writeNoException();
                parcel2.writeInt(iInitialize);
                return true;
            }
            if (i == 2) {
                parcel.enforceInterface(IRemoteImageLabelerDelegate.DESCRIPTOR);
                List<ImageLabelParcel> listDetect = detect(parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? ImageLabelerFrameParcel.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? ImageLabelerOptionsParcel.CREATOR.createFromParcel(parcel) : null);
                parcel2.writeNoException();
                parcel2.writeTypedList(listDetect);
                return true;
            }
            if (i == 3) {
                parcel.enforceInterface(IRemoteImageLabelerDelegate.DESCRIPTOR);
                int iDestroy = destroy();
                parcel2.writeNoException();
                parcel2.writeInt(iDestroy);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IRemoteImageLabelerDelegate {
            public static IRemoteImageLabelerDelegate sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteImageLabelerDelegate.DESCRIPTOR;
            }

            @Override // com.huawei.hms.ml.common.label.IRemoteImageLabelerDelegate
            public int initialize(IObjectWrapper iObjectWrapper, ImageLabelerOptionsParcel imageLabelerOptionsParcel) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteImageLabelerDelegate.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iObjectWrapper != null ? iObjectWrapper.asBinder() : null);
                    if (imageLabelerOptionsParcel != null) {
                        parcelObtain.writeInt(1);
                        imageLabelerOptionsParcel.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().initialize(iObjectWrapper, imageLabelerOptionsParcel);
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.ml.common.label.IRemoteImageLabelerDelegate
            public List<ImageLabelParcel> detect(Bundle bundle, ImageLabelerFrameParcel imageLabelerFrameParcel, ImageLabelerOptionsParcel imageLabelerOptionsParcel) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteImageLabelerDelegate.DESCRIPTOR);
                    if (bundle != null) {
                        parcelObtain.writeInt(1);
                        bundle.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (imageLabelerFrameParcel != null) {
                        parcelObtain.writeInt(1);
                        imageLabelerFrameParcel.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (imageLabelerOptionsParcel != null) {
                        parcelObtain.writeInt(1);
                        imageLabelerOptionsParcel.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().detect(bundle, imageLabelerFrameParcel, imageLabelerOptionsParcel);
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(ImageLabelParcel.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.ml.common.label.IRemoteImageLabelerDelegate
            public int destroy() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteImageLabelerDelegate.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().destroy();
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IRemoteImageLabelerDelegate iRemoteImageLabelerDelegate) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iRemoteImageLabelerDelegate == null) {
                return false;
            }
            Proxy.sDefaultImpl = iRemoteImageLabelerDelegate;
            return true;
        }

        public static IRemoteImageLabelerDelegate getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
