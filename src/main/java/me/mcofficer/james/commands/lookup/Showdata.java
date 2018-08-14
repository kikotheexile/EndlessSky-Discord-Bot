package me.mcofficer.james.commands.lookup;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import me.mcofficer.esparser.DataNode;
import me.mcofficer.james.Util;
import me.mcofficer.james.tools.Lookups;

import java.util.List;

public class Showdata extends Command {

    private final Lookups lookups;

    public Showdata(Lookups lookups) {
        name = "showdata";
        help = "Outputs the data associated with <query>.";
        arguments = "<query>";
        this.lookups = lookups;
    }

    @Override
    protected void execute(CommandEvent event) {
        List<DataNode> matches = lookups.getNodesByString(event.getArgs());

        if (matches.size() < 1)
            event.reply("Found no matches for `" + event.getArgs() + "`!");
        else if (matches.size() == 1)
            event.reply(createShowdataMessage(matches.get(0)));
        else
            Util.displayNodeSearchResults(matches, event, (((message, integer) -> event.reply(createShowdataMessage(matches.get(integer - 1))))));
    }

    private String createShowdataMessage(DataNode node) {
        return "```" + lookups.getNodeAsText(node) + "```";
    }
}
