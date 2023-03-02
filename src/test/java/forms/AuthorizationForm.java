package forms;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class AuthorizationForm extends Form {
    private final ITextBox txbPASSWORD = getElementFactory().getTextBox(By.xpath("//input[@type='password']"), "\"Password\" text box");
    private final IButton btnCONTINUE = getElementFactory().getButton(By.xpath("//button[@type='submit']"), "\"Continue\" button");

    public AuthorizationForm() {
        super(By.xpath("//div[@class='vkuiAppRoot']"), "\"Authorization\" form");
    }

    public void inputPassword(String password) {
        txbPASSWORD.clearAndType(password);
    }

    public void clickContinueButton() {
        btnCONTINUE.clickAndWait();
    }
}
