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

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class PDFGenerator {

    private static final int IMAGE_WIDTH = 600;  // Image width
    private static final int IMAGE_HEIGHT = 200; // Image height

    public void generatePDF(List<Chart> charts,List<Chart> charts2,List<Chart> charts3) {
        PDDocument document = new PDDocument();
        try {
            PDPage page = new PDPage();
            document.addPage(page);
            int yPosition = 600; // Initiate y position

            PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true);

            // Add graphs from first list

                for (Chart chart : charts) {
                    if(chart != null) {
                        BufferedImage bufferedImage = createImageFromChart(chart);
                        if (bufferedImage != null) {
                            bufferedImage = resizeImage(bufferedImage); // Resize the image
                            PDImageXObject pdImage = LosslessFactory.createFromImage(document, bufferedImage);
                            contentStream.drawImage(pdImage, 0, yPosition);
                            yPosition -= 275; // Adjust y position for the next graph
                        } else {
                            System.out.println("Failed to create image from chart.");
                        }
                    } else {
                        System.out.println("Chart in 'charts' list is null.");
                    }
                }
                contentStream.close();


            PDPage page2 = new PDPage();
            document.addPage(page2);
            yPosition = 600; // Initiate y position

            contentStream = new PDPageContentStream(document, page2, PDPageContentStream.AppendMode.APPEND, true);

            // Add graphs from second list

                for (Chart chart : charts2) {
                    if(chart != null) {
                        BufferedImage bufferedImage = createImageFromChart(chart);
                        if (bufferedImage != null) {
                            bufferedImage = resizeImage(bufferedImage); // Resize the image
                            PDImageXObject pdImage = LosslessFactory.createFromImage(document, bufferedImage);
                            contentStream.drawImage(pdImage, 0, yPosition);
                            yPosition -= 275; // Adjust y position for the next graph
                        } else {
                            System.out.println("Failed to create image from chart.");
                        }
                    } else {
                        System.out.println("Chart in 'charts2' list is null.");
                    }
                }

                 contentStream.close();


            PDPage page3 = new PDPage();
            document.addPage(page3);

            yPosition = 600; // Initiate y position

            contentStream = new PDPageContentStream(document, page3, PDPageContentStream.AppendMode.APPEND, true);
            // Add graphs from third list

                for (Chart chart : charts3) {
                    if(chart != null) {
                        BufferedImage bufferedImage = createImageFromChart(chart);
                        if (bufferedImage != null) {
                            bufferedImage = resizeImage(bufferedImage); // Resize the image
                            PDImageXObject pdImage = LosslessFactory.createFromImage(document, bufferedImage);
                            contentStream.drawImage(pdImage, 0, yPosition);
                            yPosition -= 275; // Adjust y position for the next graph
                        } else {
                            System.out.println("Failed to create image from chart.");
                        }
                    } else {
                        System.out.println("Chart in 'charts3' list is null.");
                    }
                }

                contentStream.close();


            contentStream.close();

            document.save("CookMaster.pdf");
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
        if(chart != null) {
            WritableImage image = chart.snapshot(new SnapshotParameters(), null);
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
            return bufferedImage;
        }
        return null;
    }

    private BufferedImage resizeImage(BufferedImage originalImage) {
        BufferedImage resizedImage = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, originalImage.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, null);
        g.dispose();
        return resizedImage;
    }
}


