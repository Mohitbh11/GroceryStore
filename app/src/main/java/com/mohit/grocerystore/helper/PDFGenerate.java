package com.mohit.grocerystore.helper;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import com.mohit.grocerystore.model.Product;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class PDFGenerate extends AppCompatActivity{
    File pdfFile;
    Context context;

    public PDFGenerate(Context context) {
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void createPdf(){
        File docsFolder = new File(context.getExternalCacheDir() + "");
        docsFolder.mkdir();
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i("TAG", "Created a new directory for PDF");
        }
        String pdfname = "statement"+".pdf";
        pdfFile = new File(docsFolder.getAbsolutePath(), pdfname);
        try {
            OutputStream output = new FileOutputStream(pdfFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fillDataPdf(pdfFile);
        } catch (FileNotFoundException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void fillDataPdf(File pdfFile) throws FileNotFoundException, ParseException  {

        PdfWriter writer = new PdfWriter(pdfFile);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        float width1[] = {50f, 100f, 100f, 80f, 60f, 80f, 60f};
        Table table = new Table(width1);
        table.setWidth(UnitValue.createPercentValue(100));
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        table.setTextAlignment(TextAlignment.CENTER);
        table.setPaddingTop(20);
        table.setFixedLayout();
        table.setMarginTop(10);
        table.setPaddingBottom(10);

        table.addHeaderCell(new Cell().add(new Paragraph("id".toUpperCase()).setTextAlignment(TextAlignment.CENTER)).setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE).setBackgroundColor(new DeviceRgb(253, 187, 19)));
        table.addHeaderCell(new Cell().add(new Paragraph("title".toUpperCase()).setTextAlignment(TextAlignment.CENTER)).setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE).setBackgroundColor(new DeviceRgb(253, 187, 19)));
        table.addHeaderCell(new Cell().add(new Paragraph("description".toUpperCase()).setTextAlignment(TextAlignment.CENTER)).setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE).setBackgroundColor(new DeviceRgb(253, 187, 19)));
        table.addHeaderCell(new Cell().add(new Paragraph("attribute".toUpperCase()).setTextAlignment(TextAlignment.CENTER)).setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE).setBackgroundColor(new DeviceRgb(253, 187, 19)));
        table.addHeaderCell(new Cell().add(new Paragraph("price".toUpperCase()).setTextAlignment(TextAlignment.CENTER)).setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE).setBackgroundColor(new DeviceRgb(253, 187, 19)));
        table.addHeaderCell(new Cell().add(new Paragraph("discount".toUpperCase()).setTextAlignment(TextAlignment.CENTER)).setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE).setBackgroundColor(new DeviceRgb(253, 187, 19)));
        table.addHeaderCell(new Cell().add(new Paragraph("image".toUpperCase()).setTextAlignment(TextAlignment.CENTER)).setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE).setBackgroundColor(new DeviceRgb(253, 187, 19)));

        Data data = new Data();
        List<Product> products = new ArrayList<>();
        products.addAll(data.getProductList());
        for (int i = 0; i< products.size(); i++){
            Product product = products.get(i);
            table.addCell(new Cell().add(new Paragraph(product.getId())).setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE).setPaddingBottom(5));
            table.addCell(new Cell().add(new Paragraph(product.getTitle())).setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE).setPaddingBottom(5));
            table.addCell(new Cell().add(new Paragraph(product.getDescription())).setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE).setPaddingBottom(5));
            table.addCell(new Cell().add(new Paragraph(product.getAttribute())).setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE).setPaddingBottom(5));
            table.addCell(new Cell().add(new Paragraph(product.getPrice())).setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE).setPaddingBottom(5));
            table.addCell(new Cell().add(new Paragraph(product.getDiscount())).setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE).setPaddingBottom(5));
            table.addCell(new Cell().add(new Paragraph("")).setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE).setPaddingBottom(5));
        }
        document.add(table);

        document.close();
        Log.d("TAG", "fillDataPdf: created");

    }


}

