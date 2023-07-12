package com.wicookin.connecttomysql;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.Chart;
import javafx.scene.image.WritableImage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class PDFGenerator {

    public void generatePDF(List<Chart> charts) {
        PDDocument document = new PDDocument();
        try {
            for (Chart chart : charts) {
                if (chart != null) {
                    PDPage page = new PDPage();
                    document.addPage(page);

                    // Create an image from the chart
                    BufferedImage bufferedImage = createImageFromChart(chart);
                    PDImageXObject pdImage = LosslessFactory.createFromImage(document, bufferedImage);

                    try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                        contentStream.drawImage(pdImage, 20, 20);
                    }
                }
            }

            document.save("report.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private BufferedImage createImageFromChart(Chart chart) {
        WritableImage image = chart.snapshot(new SnapshotParameters(), null);
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);

        return bufferedImage;
    }
}
