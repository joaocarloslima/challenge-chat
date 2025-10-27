package br.com.fiap.challengechat;

import com.vaadin.flow.component.page.Push;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.component.page.AppShellConfigurator;

@SpringBootApplication
@Theme("my-theme")
@Push
public class ChallengeChatApplication implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(ChallengeChatApplication.class, args);
    }
}
