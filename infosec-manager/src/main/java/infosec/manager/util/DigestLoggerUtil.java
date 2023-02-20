/*
 * Ant Group
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package infosec.manager.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yunzhi
 * @version DigestLoggerUil.java, v 0.1 2023年02月20日 17:02 yunzhi
 */
public class DigestLoggerUtil {

    private static final ThreadLocal<List<Object>> threadLocal = ThreadLocal.withInitial(ArrayList::new);

    public static void addParams(Object... args) {
        threadLocal.get().addAll(List.of(args));
    }

    public static List<Object> getArgs() {
        return threadLocal.get();
    }

    public static void clear() {
        threadLocal.remove();
    }
}