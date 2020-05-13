package bot;

import java.util.List;
import java.io.File;
import java.util.Arrays;
import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;

import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;

public class App {

  private static String answer = "";

  public static void main(String[] args) {
    // https://discordapp.com/oauth2/authorize?client_id=0000000000000000000000&scope=bot
    DiscordClientBuilder builder = DiscordClientBuilder
        .create("000000000000000000000000000000000000000000000000000000");

    DiscordClient client = builder.build();

    client.getEventDispatcher().on(ReadyEvent.class).subscribe(event -> {
      User self = event.getSelf();
      System.out.println(String.format("Logged in as %s#%s", self.getUsername(), self.getDiscriminator()));
    });

    client.getEventDispatcher().on(MessageCreateEvent.class).map(MessageCreateEvent::getMessage)
        .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
        .filter(message -> message.getContent().orElse("").equalsIgnoreCase("!adivinhe")).flatMap(Message::getChannel)
        .flatMap(channel -> channel.createMessage("Adivinhe...\n\n```" + getRiddle() + "```\n\nUse !solucionar para ver a resposta")).subscribe();

      client.getEventDispatcher().on(MessageCreateEvent.class).map(MessageCreateEvent::getMessage)
      .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
      .filter(message -> message.getContent().orElse("").equalsIgnoreCase("!solucionar")).flatMap(Message::getChannel)
      .flatMap(channel -> channel.createMessage("```Resposta: " + answer+"```")).subscribe();

    client.login().block();
  }

  public static String getRiddle() {
    Adivinha a = null;
    answer = "";
    try {
      // create object mapper instance
      ObjectMapper mapper = new ObjectMapper();
  
      File file = new File(
        App.class.getClassLoader().getResource("adivinhas.json").getFile()
    );
      List<Adivinha> adivinhasFromJSON = Arrays.asList(mapper.readValue(file, Adivinha[].class));
      Random r = new Random();
      int umaAdivinha = r.nextInt(adivinhasFromJSON.size());

      a = adivinhasFromJSON.get(umaAdivinha);       
  } catch (Exception ex) {
      ex.printStackTrace();
  }
    answer = a.getResposta();

    return a.getPergunta();
  }

}
