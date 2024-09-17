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
		BasicAuthPage.loginFailWithNullPassword("smoulickn@gmail.com");
    }
    
    @Test(priority = 4)
    public void TC_LoginWithWrongEmail(){
		BasicAuthPage.loginFailWithEmailDoesNotExist("Bal@chal.com", "Chal");
    }
    
    @Test(priority = 5)
    public void TC_LoginWithWrongPassword(){
		BasicAuthPage.loginFailWithIncorrectPassword("sde.sayan@gmail.com", "Chal");
    }
    
    @Test(priority = 6)
    public void TC_LoginWithValidCredential(){
		BasicAuthPage.loginSuccessWithValidCredentials("sde.sayan@gmail.com", "a1@b2#c3$d4");
    }
    

}