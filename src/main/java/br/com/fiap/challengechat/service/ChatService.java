package br.com.fiap.challengechat.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.content.Media;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

@Service
public class ChatService {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    public ChatService(ChatClient.Builder builder, PgVectorStore vectorStore) {
        this.chatClient = builder
                .defaultAdvisors(
                        new QuestionAnswerAdvisor(vectorStore)
                )
                .build();
        this.vectorStore = vectorStore;
    }

    public String callChat(String message){
        var imageResource = new ClassPathResource("/uploads/upload.png");
        Media image = new Media(MimeTypeUtils.IMAGE_PNG, imageResource);

        return chatClient.prompt()
                .user( p -> p.text(message).media(image))
                .call()
                .content();
    }
}
