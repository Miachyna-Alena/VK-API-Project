package forms;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class VkIDForm extends Form {
    private final ITextBox txbLOGIN = getElementFactory().getTextBox(By.id("index_email"), "\"Login\" text box");
    private final IButton btnSIGN_IN = getElementFactory().getButton(By.xpath("//button[contains(@class, 'signInButton')]"), "\"Sign In\" button");

    public VkIDForm() {
        super(By.xpath("//form[contains(@class, 'VkIdForm')]"), "\"VkId\" form");
    }

    public void inputLogin(String login) {
        txbLOGIN.clearAndType(login);
    }

    public void clickSignInButton() {
        btnSIGN_IN.clickAndWait();
    }
}
