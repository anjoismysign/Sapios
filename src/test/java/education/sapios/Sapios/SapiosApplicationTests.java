package education.sapios.Sapios;

import org.junit.Rule;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SapiosApplicationTests {

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Test
    public void testMain() {
        exit.expectSystemExitWithStatus(0);

        // Call the main method
        SapiosApplication.main(new String[]{});
    }

}
