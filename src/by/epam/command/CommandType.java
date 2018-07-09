package by.epam.command;

public enum CommandType {
    LOGIN {
        {
            this.command = new LoginCommand();
        }
    }, LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    }, EMPTY {
        {
            this.command = new LogoutCommand();
        }
    }, CHANGE_LANGUAGE {
        {
            this.command = new ChangeLanguageCommand();
        }
    }, REGISTER {
        {
            this.command = new RegisterCommand();
        }
    };
    Command command;

    public Command getCurrentCommand() {
        return command;
    }
}
