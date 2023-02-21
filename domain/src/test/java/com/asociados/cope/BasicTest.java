package com.asociados.cope;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

public class BasicTest {
    private static final String WAS_RELEASED = "Was released";
    private static final String EXCEPTION_EXPECTED = "Exception expected";

    public static <T> void assertThrows(Supplier<T> supplier, Class<? extends Exception> exception, String message) {
        try {
            supplier.get();

            fail();
        } catch (Exception e) {
            assertTrue(exception.isInstance(e),
                    EXCEPTION_EXPECTED
                            + exception.getCanonicalName()
                            + WAS_RELEASED
                            + e.getClass().getCanonicalName());

            assertEquals(e.getMessage(), message);
        }
    }

    public static void assertThrows(Thunk thunk, Class<? extends Exception> exception, String message) {
        try {
            thunk.execute();

            fail();
        } catch (Exception e) {
            assertTrue(exception.isInstance(e),
                    EXCEPTION_EXPECTED
                            + exception.getCanonicalName()
                            + WAS_RELEASED
                            + e.getClass().getCanonicalName());

            assertTrue(e.getMessage().contains(message));
        }
    }

    @FunctionalInterface
    public interface Thunk {
        void execute();
    }
}