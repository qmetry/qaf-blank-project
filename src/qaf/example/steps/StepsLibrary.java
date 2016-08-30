package qaf.example.steps;

import static com.qmetry.qaf.automation.step.CommonStep.click;
import static com.qmetry.qaf.automation.step.CommonStep.sendKeys;

import com.qmetry.qaf.automation.step.QAFTestStep;

public class StepsLibrary {
	/**
	 * @param searchTerm
	 *            : search term to be searched
	 */
	@QAFTestStep(description = "search for {0}")
	public static void searchFor(String searchTerm) {
		sendKeys(searchTerm, "input.search");
		click("button.search");
	}
}
