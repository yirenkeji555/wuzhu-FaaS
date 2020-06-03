package com.tianxiao.fass.runtime.processor.manager;

import com.sun.tools.javac.util.ServiceLoader;
import com.tianxiao.fass.runtime.processor.BeanDefinitionsAfterProcessor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ServiceLoaderBeanProcessorManager implements BeanProcessorManager {
    private final static List<BeanDefinitionsAfterProcessor> processors = new ArrayList<>();
    public static void init() {
        ServiceLoader<BeanDefinitionsAfterProcessor> beforeProcessors = ServiceLoader.load(BeanDefinitionsAfterProcessor.class);
        if (beforeProcessors != null) {
            Iterator<BeanDefinitionsAfterProcessor> iterator = beforeProcessors.iterator();
            while (iterator.hasNext()) {
                BeanDefinitionsAfterProcessor next = iterator.next();
                processors.add(next);
            }
            processors.sort((o1, o2) -> {
                int i = o1.order() - o2.order();
                if (i >= 0) {
                    return -1;
                } else {
                    return 1;
                }
            });
        }
    }

    public static void destroy() {
        processors.clear();
    }

    public List<BeanDefinitionsAfterProcessor> getAfterProcessors() {
        return processors;
    }
}
