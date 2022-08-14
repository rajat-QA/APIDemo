package stepDefinations;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class StepDefination {

	
    @Given("^User is on NetBanking landing page$")
    public void user_is_on_netbanking_landing_page() throws Throwable {
        System.out.println("navigated to login url");
        System.out.println("This is clone");
        
//        hhas
//        asdlkj
//        adja
        
        System.out.println("Added for eclipse-workspace 1");
        System.out.println("Added for eclipse-workspace 2");      
        System.out.println("New branch created");
        
    }

    @When("^User login into application with \"([^\"]*)\" and \"([^\"]*)\"$")
    public void user_login_into_application_with_something_and_something(String strArg1, String strArg2) throws Throwable {
        System.out.println(strArg1);
        System.out.println(strArg2);
        System.out.println("This is clone");
    }
    
    @Then("^Home page is populated$")
    public void home_page_is_populated() throws Throwable {
        System.out.println("Validated home page");
        System.out.println("This is clone");
    }

    @Then("Cards displayed are {string}")
    public void cards_displayed_are(String string) {
      System.out.println(string);
      System.out.println("This is clone");
    }

    
}