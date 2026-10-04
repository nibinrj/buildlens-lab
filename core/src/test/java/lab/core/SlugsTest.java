package lab.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

// Stable tests: these should pass on every build.
class SlugsTest {

    @ParameterizedTest
    @CsvSource({
        "'Hello World', hello-world",
        "'  Trim me  ', trim-me",
        "'CI: flaky tests!', ci-flaky-tests",
    })
    void makesSlugs(String title, String slug) {
        assertEquals(slug, Slugs.of(title));
    }
}
