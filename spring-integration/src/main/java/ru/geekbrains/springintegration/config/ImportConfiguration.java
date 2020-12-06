package ru.geekbrains.springintegration.config;

import java.io.File;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.transformer.FileToStringTransformer;
import org.springframework.messaging.MessageHandler;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class ImportConfiguration {

  @Value("${source.directory.path}")
  private String sourceDirectoryPath;

  @Value("${dest.directory.path}")
  private String destDirectoryPath;

//  private final EntityManagerFactory entityManagerFactory;

  @Bean
  public MessageSource<File> sourceDirectory() {
    FileReadingMessageSource messageSource = new FileReadingMessageSource();
    messageSource.setDirectory(new File(sourceDirectoryPath));
    messageSource.setAutoCreateDirectory(true);
    return messageSource;
  }

  @Bean
  public MessageHandler destDirectory() {
    FileWritingMessageHandler handler = new FileWritingMessageHandler(new File(destDirectoryPath));
    handler.setExpectReply(false);
    handler.setDeleteSourceFiles(true);
    return handler;
  }

  @Bean
  public IntegrationFlow fileMoveFlow() {
    return IntegrationFlows.from(sourceDirectory(),
        conf -> conf.poller(Pollers.fixedDelay(2000)))
        .filter(msg -> ((File) msg).getName().endsWith(".txt"))
        .transform(new FileToStringTransformer())
        .<String, String>transform(String::toUpperCase)
        .handle(destDirectory())
        .get();
  }

/*  @Bean
  public JpaUpdatingOutboundEndpointSpec jpaPersistHandler() {
    return Jpa.outboundAdapter(this.entityManagerFactory)
        .entityClass(Brand.class)
        .persistMode(PersistMode.PERSIST);
  }*/

}
