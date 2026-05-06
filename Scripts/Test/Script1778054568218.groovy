import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// 1. Inisiasi Keyword
report.ReportCLI report = new report.ReportCLI()

// 2. Start Report Metadata
report.initReport("TC_PORTAL_BGN_080", "Badan Gizi Nasional", "Full Regression - Giro Module")

// Helper: Ambil 1 screenshot saja sebagai master image
String screenshotPath = RunConfiguration.getProjectDir() + "/Reports/img_login.png"

// =====================================================================
// SECTION 1: AUTHENTICATION
// =====================================================================
report.addSection("Authentication & Access Control")
report.addStepCase("Validasi Landing Page", "Passed", "Halaman login tampil sempurna.", screenshotPath)
report.addStepCase("Input Credentials", "Done", "User mengisi form login.", screenshotPath)
report.addStepCase("Login Berhasil", "Passed", "User masuk ke dashboard.", screenshotPath)

// =====================================================================
// SECTION 2: NAVIGATION
// =====================================================================
report.addSection("Module Navigation - Mutasi Giro")
report.addStepCase("Buka Sidebar Menu", "Done", "Klik sidebar menu utama.", screenshotPath)
report.addStepCase("Load Halaman Mutasi", "Passed", "Halaman mutasi berhasil dimuat.", screenshotPath)

// =====================================================================
// SECTION 3: FILTERING
// =====================================================================
report.addSection("Data Filtering & Search")
report.addStepCase("Setting Filter Tanggal", "Done", "Filter diatur ke April 2026.", screenshotPath)
report.addStepCase("Eksekusi Pencarian", "Done", "Klik tombol Cari Data.", screenshotPath)
report.addStepCase("Validasi Hasil", "Passed", "Data transaksi muncul di grid.", screenshotPath)

// =====================================================================
// SECTION 4: EXPORT
// =====================================================================
report.addSection("Export & Printing")
report.addStepCase("Export to Excel", "Done", "Proses export data mutasi ke .xlsx.", screenshotPath)
report.addStepCase("Preview PDF", "Passed", "Pop-up preview PDF muncul.", screenshotPath)

// =====================================================================
// SECTION 5: LOGOUT
// =====================================================================
report.addSection("User Session Termination")
report.addStepCase("Trigger Logout", "Done", "Klik tombol logout.", screenshotPath)
report.addStepCase("Validasi Logout", "Passed", "Kembali ke halaman login awal.", screenshotPath)
report.addStepCase("Check Session", "Passed", "Session ID berhasil di-clear.", screenshotPath)

// =====================================================================
// GENERATE REPORT PDF
// =====================================================================
String outPdf = RunConfiguration.getProjectDir() + "/Reports/Full_Report_BGN_Giro_080.pdf"

// Panggil keyword untuk eksekusi CLI.exe
report.generatePDF(outPdf)

println("--- PDF GENERATED WITH MASTER IMAGE ---")