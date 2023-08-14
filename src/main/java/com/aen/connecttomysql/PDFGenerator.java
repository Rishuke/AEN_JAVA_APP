package com.aen.connecttomysql;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.Chart;
import javafx.scene.image.WritableImage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import javafx.scene.control.TableView;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class PDFGenerator {

    private static final int IMAGE_WIDTH = 600;  // Image width
    private static final int IMAGE_HEIGHT = 200; // Image height

    public void generatePDF(List<Node> charts1, List<Node> charts2, List<Node> charts3) {
        PDDocument document = new PDDocument();
        try {
            renderNodesToPDF(charts1, document);
            renderNodesToPDF(charts2, document);
            renderNodesToPDF(charts3, document);

            document.save("AEN_RISHI.pdf");
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

    private void renderNodesToPDF(List<Node> nodes, PDDocument document) throws IOException {
        if (nodes == null || nodes.isEmpty()) {
            return;
        }

        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        int yPosition = 600; // Initiate y position

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        for (Node node : nodes) {
            BufferedImage nodeImage = createImageFromNode(node);
            if (nodeImage != null) {
                nodeImage = resizeImage(nodeImage); // Resize the image
                PDImageXObject pdImage = LosslessFactory.createFromImage(document, nodeImage);
                contentStream.drawImage(pdImage, 0, yPosition);
                yPosition -= 275; // Adjust y position for the next node
            } else {
                System.out.println("Failed to create image from node.");
            }
        }

        contentStream.close();
    }

    private BufferedImage createImageFromNode(Node node) {
        if (node != null) {
            WritableImage writableImage = node.snapshot(new SnapshotParameters(), null);
            return SwingFXUtils.fromFXImage(writableImage, null);
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

