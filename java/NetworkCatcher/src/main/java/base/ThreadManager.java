package base;

import com.sun.istack.internal.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadManager {

    private volatile static ThreadManager sInstance;

    @NotNull
    public static ThreadManager i() {
        if (sInstance == null) {
            synchronized (ThreadManager.class) {
                if (sInstance == null) {
                    sInstance = new ThreadManager();
                }
            }
        }
        return sInstance;
    }

    @NotNull
    private final ExecutorService mExecutorPool;

    private ThreadManager() {
        mExecutorPool = createExecutorService();
    }

    @NotNull
    protected ExecutorService createExecutorService() {
        return Executors.newCachedThreadPool();
    }

    public void post(@NotNull Runnable runnable) {
        mExecutorPool.submit(runnable);
    }

    public void postMain(@NotNull Runnable runnable) {
    }
}
