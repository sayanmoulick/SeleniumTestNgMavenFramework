package SeleniumTestNgMavenFramework;


import org.testng.annotations.Test;
import org.testng.annotations.Test;

import common.BaseTest;

import pageObjects.BasicAuthPage;
public class BasicAuthTest extends BaseTest {

	@Test(priority = 1)
    public void TC_VerifyLoginTitlePage(){
		BasicAuthPage.openLoginPage();
    }

    @Test(priority = 2)
    public void TC_LoginWithoutEmail() {
    	BasicAuthPage.loginFailWithEmailNull();
    }
    
    
    @Test(priority = 3)
    public void TC_LoginWithoutPassword(){
		BasicAuthPage.loginFailWithNullPassword("sn@gmail.com");
    }
    
    @Test(priority = 4)
    public void TC_LoginWithWrongEmail(){
		BasicAuthPage.loginFailWithEmailDoesNotExist("Ba@cal.com", "Cal");
    }
    
    @Test(priority = 5)
    public void TC_LoginWithWrongPassword(){
		BasicAuthPage.loginFailWithIncorrectPassword("sn@gmail.com", "Cal");
    }
    
    @Test(priority = 6)
    public void TC_LoginWithValidCredential(){
		BasicAuthPage.loginSuccessWithValidCredentials("sn@gmail.com", "myCred@12354");
    }
    

}