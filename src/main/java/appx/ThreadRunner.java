package appx;


import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import appx.utils.exceptions.DetectionException;
import com.google.common.util.concurrent.ThreadFactoryBuilder;


public class ThreadRunner {
    private ExecutorService exec;
    private ExecutorCompletionService<Boolean> runner;

    private Map<String, DetectionException> currentExceptions;

    public ThreadRunner() {
        exec = Executors.newFixedThreadPool(1,
                                            new ThreadFactoryBuilder().setNameFormat("thread-%d").setDaemon(true)
                                                                      .build());
        this.runner = new ExecutorCompletionService<Boolean>(exec);
        this.currentExceptions = new ConcurrentHashMap<>();
    }

    public Future<Boolean> compute(Callable<Boolean> task) {
        return runner.submit(task);
    }

    public void shutDown() {
        exec.shutdown();
    }

    Optional<Boolean> checkResult() {
        try {
            Future<Boolean> result = runner.poll();
            if (result != null) {
                return Optional.of(result.get());
            }
        } catch (Throwable e) {
            Throwable cause = e.getCause();
            if (cause instanceof DetectionException) {
                DetectionException adException = (DetectionException) cause;
                currentExceptions.put(adException.getDetectorId(), adException);
            }
        }
        return Optional.empty();
    }

    public Optional<DetectionException> fetchException(String adID) {
        checkResult();
        if (currentExceptions.containsKey(adID)) {
            return Optional.of(currentExceptions.remove(adID));
        }
        return Optional.empty();
    }
}
 