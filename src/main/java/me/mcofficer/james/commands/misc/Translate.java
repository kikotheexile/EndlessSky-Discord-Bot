package me.mcofficer.james.commands.misc;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import me.mcofficer.james.James;
import me.mcofficer.james.tools.Translator;
import net.dv8tion.jda.api.EmbedBuilder;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

public class Translate extends Command {

    private final Translator translator;

    public Translate(Translator translator) {
        this.translator = translator;
        name = "translate";
        help = "Translates a Query Q from a auto-detected source language to another language T.";
        arguments = "T Q";
        category = James.misc;
    }

    @Override
    protected void execute(CommandEvent event) {
        String[] args = event.getArgs().split(" ");
        String target = args[0];
        String query = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

        try {
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("Translation requested by " + event.getMember().getEffectiveName())
                    .setDescription(translator.translate(null, target, query));
            event.reply(embedBuilder.build());
        }
        catch (IOException e) {
            StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter));
            event.reply("An error occured:\n```" + stringWriter.toString() + "```");
        }
    }
}
