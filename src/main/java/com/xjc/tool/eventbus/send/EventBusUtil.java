package com.xjc.tool.eventbus.send;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.xjc.tool.eventbus.event.EventBusModel;
import com.xjc.tool.eventbus.listener.EventListener;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Version 1.0
 * @ClassName EventBusUtil
 * @Author jiachenXu
 * @Date 2021/7/9
 * @Description 事件总线
 */
public class EventBusUtil {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(4);

    private EventBus eventBus;

    private AsyncEventBus asyncEventBus;

    public EventBus getEventBus() {
        if (Objects.isNull(eventBus)) {
            synchronized (EventBus.class) {
                if (Objects.isNull(eventBus)) {
                    eventBus = new EventBus();
                }
            }
        }
        return eventBus;
    }

    public AsyncEventBus getAsyncEventBus() {
        if (Objects.isNull(asyncEventBus)) {
            synchronized (EventBus.class) {
                if (Objects.isNull(asyncEventBus)) {
                    asyncEventBus = new AsyncEventBus(executorService);
                }
            }
        }
        return asyncEventBus;
    }

    public void register(EventListener eventListener) {
        if (eventBus != null) {
            eventBus.register(eventListener);
        }

        if (asyncEventBus != null) {
            asyncEventBus.register(eventListener);
        }

    }

    public void unRegister(EventListener eventListener) {
        if (eventBus != null) {
            eventBus.unregister(eventListener);
        }

        if (asyncEventBus != null) {
            asyncEventBus.unregister(eventListener);
        }
    }

    public void syncSend(EventBusModel eventBusModel) {
        eventBus.post(eventBusModel);
    }

    public void asyncSend(EventBusModel eventBusModel) {
        asyncEventBus.post(eventBusModel);
    }


}
