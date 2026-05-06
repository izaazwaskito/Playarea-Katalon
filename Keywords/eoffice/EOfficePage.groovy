package eoffice

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.keyword.internal.WebUIAbstractKeyword

import internal.GlobalVariable

public class EOfficePage {

	protected createTestObject(String xpath) {
		return new TestObject().addProperty("xpath", ConditionType.EQUALS, xpath)
	}

	private TestObject getInputUsername() {
		return createTestObject("//input[@id='username']")
	}
	
	private TestObject getInputPassword() {
		return createTestObject("//input[@id='password']")
	}
	
	private TestObject getButtonLogin() {
		return createTestObject("//input[@id='SubmitButton']")
	}
	
	private TestObject getButtonAbsensi() {
		return createTestObject("//img[@src='/Content/portal/absensi.png']")
	}
	
	private TestObject getButtonHadir() {
		return createTestObject("//a[text()='Hadir ']")
	}

	private TestObject getInputTitle() {
		return createTestObject("//input[@id='Title']")
	}

	private TestObject getButtonSelectDate() {
		return createTestObject("//span[@role='button' and @aria-controls='DateFrom_dateview']")
	}
	
	private TestObject getButtonJamHadir() {
		return createTestObject("//span[@role='button' and @aria-controls='Hour_timeview']")
	}
	
	private TestObject getButtonJamPulang() {
		return createTestObject("//span[@role='button' and @aria-controls='JamPulang_timeview']")
	}
	
	private TestObject SelectTime(String Time) {
		return createTestObject("//li[@role='option' and text()='${Time}']")
	}

	private TestObject SelectDate(String Day, String Month) {
		return createTestObject("//a[@title='${Day} ${Month} 2026']")
	}
	
	private TestObject getRadioLainnya() {
		return createTestObject("//input[@value ='Lainnya']")
	}
	
	private TestObject getTextAreaAlasan() {
		return createTestObject("//textarea[@id='Reason']")
	}
	
	private TestObject getButtonSaksi() {
		return createTestObject("//span[ @aria-activedescendant='WitnessId_option_selected']")
	}
	
	private TestObject getButtonReviewer2() {
		return createTestObject("//span[ @aria-activedescendant='ReviewerId2_option_selected']")
	}
	
	
	
	public Login(String URL) {
		WebUI.openBrowser(URL)
		WebUI.maximizeWindow()
		
		WebUI.waitForElementPresent(inputUsername, 10)
		WebUI.setText(inputUsername, "901233")
		WebUI.setText(inputPassword, "Vdsfdatcd432*")
		WebUI.click(buttonLogin)
		
		WebUI.waitForElementPresent(buttonAbsensi, 4)
		WebUI.click(buttonAbsensi)
		
		WebUI.waitForElementPresent(buttonHadir, 4)
	}

	public Absensi(int Year=2026, int Month=4, String MonthName="April", List liburNasional =[3, 20, 28]) {

		YearMonth yearMonthObject = YearMonth.of(Year, Month)
		int daysInMonth = yearMonthObject.lengthOfMonth()

		for (int day = 1; day <= daysInMonth; day++) {
			LocalDate date = LocalDate.of(Year, Month, day)
			DayOfWeek dayOfWeek = date.getDayOfWeek()
			
			if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY || liburNasional.contains(day)) {
				println("Tanggal " + day + " adalah weekend (" + dayOfWeek + "), di-skip wkwk.")
				continue	
			}
			
			WebUI.click(buttonHadir)
			WebUI.setText(inputTitle, "HADIR")
			WebUI.click(buttonSelectDate)

			String dayString = String.format("%02d", day)

			String expectedTitle = "${dayString} ${MonthName} ${Year}"
			println("Memproses hari kerja: " + expectedTitle)
			
			WebUI.click(SelectDate(dayString, MonthName))
			WebUI.delay(2)
			
			WebUI.click(buttonJamHadir)
			WebUI.scrollToElement(SelectTime("8:00"), 2)
			WebUI.click(SelectTime("8:00"))
			
			WebUI.click(buttonJamPulang)
			WebUI.scrollToElement(SelectTime("18:00"), 2)
			WebUI.click(SelectTime("18:00"))
			
			WebUI.click(radioLainnya)
			
			WebUI.setText(textAreaAlasan, "HADIR")
			
			WebUI.click(buttonSaksi)
			WebUI.scrollToElement(SelectTime("63945 - THOMAS GUNAWAN SARDJONO"), 2)
			WebUI.click(SelectTime("63945 - THOMAS GUNAWAN SARDJONO"))
			
			WebUI.click(buttonReviewer2)
			WebUI.scrollToElement(SelectTime("SILVESTER KEVIN DEWANGGA K(57874)"), 2)
			WebUI.click(SelectTime("SILVESTER KEVIN DEWANGGA K(57874)"))
		}
	}
}
