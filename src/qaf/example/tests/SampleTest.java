package qaf.example.tests;

import static com.infostretch.automation.step.CommonStep.get;
import static com.infostretch.automation.step.CommonStep.verifyLinkWithPartialTextPresent;
import static qaf.example.steps.StepsLibrary.searchFor;
import org.testng.annotations.Test;

import com.infostretch.automation.ui.WebDriverTestCase;

public class SampleTest extends WebDriverTestCase {

	@Test
	public void testGoogleSearch() {
		get("/");
		searchFor("qaf github infostretch");
		verifyLinkWithPartialTextPresent("qaf");
	}
}
