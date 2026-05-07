package report

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.configuration.RunConfiguration
import groovy.json.JsonOutput
import java.io.File

public class ReportCLI {

    private Map reportData = [:]

    @Keyword
    def initReport(String tcId, String projectName, String docType) {
        reportData = [
            tcId       : tcId,
            projectName: projectName,
            docType    : docType,
            prepDate   : new Date().format("dd-MM-yyyy_HH:mm:ss"),
            steps      : []
        ]
    }

    @Keyword
    def addSection(String sectionName) {
        int nextNum = reportData.steps.count { it.level == 1 } + 1
        reportData.steps.add([
            level : 1, 
            number: String.valueOf(nextNum), 
            name  : sectionName
        ])
    }

    @Keyword
    def addStepCase(String stepName, String status, String notes, String imagePath) {
        def sections = reportData.steps.findAll { it.level == 1 }
        
        if (sections.isEmpty()) {
            return
        }

        String parentNum = sections.last().number
        int childCount = reportData.steps.count { it.level == 2 && it.number.startsWith("${parentNum}.") }

        reportData.steps.add([
            level    : 2,
            number   : "${parentNum}.${childCount + 1}",
            name     : stepName,
            status   : status,
            notes    : notes,
            imagePath: imagePath
        ])
    }

    @Keyword
    def generatePDF(String outputPdfPath) {
        String projectDir = RunConfiguration.getProjectDir()
        String jsonPath = "${projectDir}/temp_report.json"
        
        // Menulis data ke JSON sementara
        new File(jsonPath).write(JsonOutput.toJson(reportData))

        // Path ke executable CLI
        String exePath = "${projectDir}/Tools/cli.exe"
        
        // Menangani path yang mengandung spasi dengan tanda kutip ganda ekstra
        String command = "cmd.exe /c \"\"${exePath}\" \"${jsonPath}\" \"${outputPdfPath}\"\""

        println("🚀 Menjalankan Generator PDF...")
        Process process = Runtime.getRuntime().exec(command)
        process.waitFor()

        if (process.exitValue() == 0) {
            println("✅ PDF Berhasil Dibuat: ${outputPdfPath}")
            new File(jsonPath).delete() // Bersihkan temp file
        } else {
            String errorMessage = process.getErrorStream().getText()
            System.err.println("❌ Gagal membuat PDF: ${errorMessage}")
        }
    }
}