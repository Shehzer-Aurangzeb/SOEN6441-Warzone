import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages({
        "adapters",
        "controllers",
        "models",
        "utils",
        "views"
})
public class TestSuite {
}
