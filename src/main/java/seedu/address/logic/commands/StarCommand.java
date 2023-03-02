package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;


public class StarCommand extends Command {

    public static final String COMMAND_WORD = "star";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Stars the person according to the Contact ID provided by user input.\n "
            + "Parameters: INDEX (must be positive integer).\n"
            + "Example: " + COMMAND_WORD + "1";

    public static final String MESSAGE_NOT_EXISTS = "User doesn't exist in the address book";

    public static final String MESSAGE_ISSTARRED = "User is already starred in this address book";

    public static final String MESSAGE_STAR_PERSON_SUCCESS = "Starred Person: %1$s";

    public final Index index;

    public StarCommand(Index index) {
        this.index = index;
    }

    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_NOT_EXISTS);
        }

        Person personToStar = lastShownList.get(index.getZeroBased());

        if (personToStar.getStarred()) {
            throw new CommandException(MESSAGE_ISSTARRED);
        }

        Person starredPerson = personToStar.star();

        model.setPerson(personToStar, starredPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_STAR_PERSON_SUCCESS, starredPerson));


    }


}
