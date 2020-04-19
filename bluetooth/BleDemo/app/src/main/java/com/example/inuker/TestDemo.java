package com.example.inuker;

import java.util.concurrent.CountDownLatch;

/**
 * @NAME: TestDemo
 * @Des:
 * @USER: shiming
 * @DATE: 2020/4/19 13:30
 * @PROJECT_NAME: BleDemo
 */
public class TestDemo {

    interface CallBack {
        void onCallBack(int status);
    }

    static void asynCall(String mac, final CallBack callBack) {
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {

                }
                if (callBack != null) {
                    callBack.onCallBack(0);
                }
            }
        }.start();
    }


    /**
     * 同步 注意syncCall不能再ui里面做，因为可以挂起当前线程
     */
    static void syncCall(String mac, final CallBack callBack) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        asynCall(mac, new CallBack() {
            @Override
            public void onCallBack(final int status) {
                if (callBack != null) {
                    callBack.onCallBack(status);
                }
                countDownLatch.countDown();
            }
        });
        try {
            countDownLatch.await();
        } catch (Exception e) {

        }
    }


    public static void main() {
        asynCall("Heloo", new CallBack() {
            @Override
            public void onCallBack(int status) {
                System.out.println("shiming asynCall" + 0);
            }
        });

        System.out.println("shiming main");

        syncCall("H", new CallBack() {
            @Override
            public void onCallBack(int status) {
                System.out.println("shiming syncCall" + status);
            }
        });
        System.out.println("shiming main sync");
    }

}
