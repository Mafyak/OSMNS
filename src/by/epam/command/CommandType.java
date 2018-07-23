package by.epam.command;

import by.epam.command.admin.*;
import by.epam.command.hr.AddMyCompany;
import by.epam.command.hr.AddNewReviewCommand;
import by.epam.command.hr.ShowBySSNCommand;
import by.epam.command.hr.ShowHrByName;

public enum CommandType {
    LOGIN {
        {
            this.command = new LoginCommand();
        }
    }, LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    }, SHOW_COMPANY_NAME_COLLISIONS {
        {
            this.command = new ShowCompNameCollisionCommand();
        }
    }, REMOVE_HR {
        {
            this.command = new RemoveHrCommand();
        }
    }, SHOW_ALL_REVIEWS {
        {
            this.command = new ShowAllReviewsCommand();
        }
    }, SHOW_UNCONFIRMED_REVIEWS {
        {
            this.command = new ShowUnconfirmedReviewsCommand();
        }
    }, CONFIRM_RATING {
        {
            this.command = new ConfirmRatingCommand();
        }
    }, REMOVE_COMPANY {
        {
            this.command = new RemoveCompanyCommand();
        }
    }, MERGE_COMPANY {
        {
            this.command = new MergeCompanyCommand();
        }
    }, EMPTY {
        {
            this.command = new LogoutCommand();
        }
    }, SHOW_BY_SSN {
        {
            this.command = new ShowBySSNCommand();
        }
    }, DELETE_RATING {
        {
            this.command = new DeleteRatingCommand();
        }
    }, SHOW_HR_BY_NAME {
        {
            this.command = new ShowHrByName();
        }
    }, ADD_REVIEW {
        {
            this.command = new AddNewReviewCommand();
        }
    }, ADD_MY_COMPANY {
        {
            this.command = new AddMyCompany();
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
