/*******************************************************************************
 * QMetry Automation Framework provides a powerful and versatile platform to
 * author
 * Automated Test Cases in Behavior Driven, Keyword Driven or Code Driven
 * approach
 * Copyright 2016 Infostretch Corporation
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT
 * OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE
 * You should have received a copy of the GNU General Public License along with
 * this program in the name of LICENSE.txt in the root folder of the
 * distribution. If not, see https://opensource.org/licenses/gpl-3.0.html
 * See the NOTICE.TXT file in root folder of this source files distribution
 * for additional information regarding copyright ownership and licenses
 * of other open source software / files used by QMetry Automation Framework.
 * For any inquiry or need additional information, please contact
 * support-qaf@infostretch.com
 *******************************************************************************/

package qaf.example.listener;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.LogFactoryImpl;
import org.openqa.selenium.remote.DriverCommand;

import com.qmetry.qaf.automation.core.QAFListenerAdapter;
import com.qmetry.qaf.automation.ui.webdriver.CommandTracker;
import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebDriver;

/**
 * This listener can be used to have locator compatibility for UIAutomation and
 * XCUITest automation frameworks. In order to use this listener register this listener by setting listener property.
 * <code>
 * qaf.listeners=qaf.example.IosLocatorCompatibilityListener
 * </code>
 *
 * @author chirag.jayswal
 *
 */
public class IosLocatorCompatibilityListener extends QAFListenerAdapter {
	private final Log logger = LogFactoryImpl.getLog(IosLocatorCompatibilityListener.class);

	@Override
	public void beforeCommand(QAFExtendedWebDriver driver, CommandTracker commandTracker) {
		if (commandTracker.getCommand().equalsIgnoreCase(DriverCommand.FIND_ELEMENT)
				|| commandTracker.getCommand().equalsIgnoreCase(DriverCommand.FIND_ELEMENTS)
				|| commandTracker.getCommand().equalsIgnoreCase(DriverCommand.FIND_CHILD_ELEMENT)
				|| commandTracker.getCommand().equalsIgnoreCase(DriverCommand.FIND_CHILD_ELEMENTS)) {

			String version = driver.getCapabilities().getVersion();
			if (is9OrBelow(version)) {
				ensureIos9Compatibility(commandTracker.getParameters());
			} else {
				ensureIos11Compatibility(commandTracker.getParameters());
			}
		}
	}

	private boolean is9OrBelow(String ver) {
		return Integer.parseInt(ver.substring(0, 1)) <= 9;
	}

	private void ensureIos9Compatibility(Map<String, Object> parameters) {
		String loc = (String) parameters.get("value");

		boolean migrated = false;
		for (IosElementType elementType : IosElementType.values()) {
			if (loc.contains(elementType.toXCUIElement())) {
				loc = loc.replaceAll(elementType.toXCUIElement(), elementType.name());
				migrated = true;
			}
		}
		if (migrated) {
			logger.info(
					"[IOS9 compatibility] Migrating locator from [" + parameters.get("value") + "] to [" + loc + "]");
			parameters.put("value", "loc");
		}
	}

	private void ensureIos11Compatibility(Map<String, Object> parameters) {
		String loc = (String) parameters.get("value");
		boolean migrated = false;

		for (IosElementType elementType : IosElementType.values()) {
			if (loc.contains(elementType.name())) {
				loc = loc.replaceAll(elementType.name(), elementType.toXCUIElement());
				migrated = true;
			}
		}
		if (migrated) {
			logger.info(
					"[IOS9 compatibility] Migrating locator from [" + parameters.get("value") + "] to [" + loc + "]");
			parameters.put("value", "loc");
		}
	}

	private enum IosElementType {

		UIApplication,

		UIAActionSheet("XCUIElementTypeSheet"),

		UIAActivityIndicator,

		UIAAlert,

		UIAButton,

		UIACollectionCell("XCUIElementTypeCell"),

		UIACollectionView,

		UIAEditingMenu("XCUIElementTypeMenu"),

		UIAElement("XCUIElementTypeAny"),

		UIAImage,

		UIAKey,

		UIAKeyboard,

		UIALink,

		UIAMapView("XCUIElementTypeMap"),

		UIANavigationBar,

		UIAPageIndicator,

		UIAPicker,

		UIAPickerWheel,

		UIAPopover,

		UIAProgressIndicator,

		UIAScrollView,

		UIASearchBar("XCUIElementTypeSearchField"),

		UIASecureTextField,

		UIASegmentedControl,

		UIASlider,

		UIAStaticText,

		UIAStatusBar,

		UIASwitch,

		UIATabBar,

		// XCUIElementTypeTableRow
		UIATableCell("XCUIElementTypeTableColumn"),

		UIATableGroup("XCUIElementTypeOther"),

		UIATableView("XCUIElementTypeTable"),

		UIATextField,

		UIATextView,

		UIAToolbar,

		UIAWebView,

		UIAWindow;
		private String xCUIElement;

		IosElementType(String xCUIElement) {
			this.xCUIElement = xCUIElement;
		}

		IosElementType() {
			this.xCUIElement = name().replace("UIA", "XCUIElementType");
		}

		public String toXCUIElement() {
			return xCUIElement;
		}
	}
}
