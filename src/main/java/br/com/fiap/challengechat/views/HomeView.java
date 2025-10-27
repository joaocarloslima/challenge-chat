package br.com.fiap.challengechat.views;

import br.com.fiap.challengechat.service.ChatService;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.messages.MessageInput;
import com.vaadin.flow.component.messages.MessageList;
import com.vaadin.flow.component.messages.MessageListItem;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.streams.UploadHandler;

import java.io.File;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;

@Route("")
public class HomeView extends VerticalLayout {

    private final MessageList messageList = new MessageList();
    private final MessageInput messageInput = new MessageInput();
    private final ChatService chatService;
    private File baseDir = new File("src/main/resources/uploads/");

    public HomeView(ChatService chatService) {

        messageInput.setWidthFull();
        messageList.setSizeFull();
        setHeightFull();

        var handler = UploadHandler.toFile(
                (metadata, file) -> Notification.show("arquivo recebido"),
                (metadata) -> new File(baseDir, "upload.png"));

        Upload upload = new Upload(handler);
        messageInput.addSubmitListener(e -> sendMessage(e.getValue()));

        add(new H1("Challenge Chat"));
        add(messageList);
        add(upload);
        add(messageInput);
        this.chatService = chatService;
    }

    private void sendMessage(String message) {
        addMessage(message, "Aluno");

        CompletableFuture.supplyAsync(() -> chatService.callChat(message))
                .thenAccept( response -> {
                    getUI().ifPresent(ui -> ui.access(() -> addMessage(response, "Suporte")));
                });
    }

    private void addMessage(String message, String Aluno) {
        messageList.addItem(new MessageListItem(
                message,
                Instant.now(),
                Aluno
        ));
    }
}
