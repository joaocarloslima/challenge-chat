package br.com.fiap.challengechat.service;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.ParagraphPdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
public class IngestionService {

    private final PgVectorStore vectorStore;

    public IngestionService(PgVectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void init(){
        String pdf = "classpath:docs/challenge.pdf";
        ParagraphPdfDocumentReader pdfRead = new ParagraphPdfDocumentReader(pdf);
        TokenTextSplitter splitter = new TokenTextSplitter();
        List<Document> documents = splitter.apply(pdfRead.get());
        vectorStore.accept(documents);
    }
}
