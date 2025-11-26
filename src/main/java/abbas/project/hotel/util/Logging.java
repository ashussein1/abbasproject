package abbas.project.hotel.util;

import java.io.IOException;
import java.util.logging.*;

public final class Logging {

    private static final Logger ROOT_LOGGER = Logger.getLogger("hotel");

    static {
        try {
            // 1MB per file, keep 10
            FileHandler handler = new FileHandler("system_logs.%g.log",
                    1024 * 1024, 10, true);
            handler.setFormatter(new SimpleFormatter());
            ROOT_LOGGER.addHandler(handler);
            ROOT_LOGGER.setLevel(Level.INFO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Logging() {}

    public static Logger getLogger(Class<?> type) {
        return Logger.getLogger("hotel." + type.getSimpleName());
    }
}
