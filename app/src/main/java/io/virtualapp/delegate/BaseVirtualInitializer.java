package io.virtualapp.delegate;

import static io.virtualapp.XApp.XPOSED_INSTALLER_PACKAGE;

import android.app.Application;
import android.util.Log;

import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.os.VEnvironment;

import jonathanfinerty.once.Once;

/**
 * @author weishu
 * @date 2019/2/25.
 */
public class BaseVirtualInitializer extends VirtualCore.VirtualInitializer {

    protected Application application;
    protected VirtualCore virtualCore;

    public BaseVirtualInitializer(Application application, VirtualCore core) {
        this.application = application;
        this.virtualCore = core;
    }

    @Override
    public void onMainProcess() {
        Once.initialise(application);
    }

    @Override
    public void onVirtualProcess() {

        Log.d("BaseVirtualInitializer", "Hello onVirtualProcess here 006");
        virtualCore.setCrashHandler(new BaseCrashHandler());

        //listener components
        virtualCore.setComponentDelegate(new MyComponentDelegate());
        //fake phone imei,macAddress,BluetoothAddress
        virtualCore.setPhoneInfoDelegate(new MyPhoneInfoDelegate());
        //fake task description's icon and title
        virtualCore.setTaskDescriptionDelegate(new MyTaskDescriptionDelegate());

        // ensure the logcat service alive when every virtual process start.
       // LogcatService.start(application, VEnvironment.getDataUserPackageDirectory(0, XPOSED_INSTALLER_PACKAGE));
    }

    @Override
    public void onServerProcess() {
        Log.d("BaseVirtualInitializer", "Hello onVirtualProcess here 007");
        virtualCore.setAppRequestListener(new MyAppRequestListener(application));
        virtualCore.addVisibleOutsidePackage("com.tencent.mobileqq");
        virtualCore.addVisibleOutsidePackage("com.tencent.mobileqqi");
        virtualCore.addVisibleOutsidePackage("com.tencent.minihd.qq");
        virtualCore.addVisibleOutsidePackage("com.tencent.qqlite");
        virtualCore.addVisibleOutsidePackage("com.facebook.katana");
        virtualCore.addVisibleOutsidePackage("com.whatsapp");
        virtualCore.addVisibleOutsidePackage("com.tencent.mm");
        virtualCore.addVisibleOutsidePackage("com.immomo.momo");
    }


}
