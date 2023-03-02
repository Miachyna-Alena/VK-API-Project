package forms;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class NewsForm extends Form {
    private final IButton btnMY_PAGE = getElementFactory().getButton(By.xpath("//li[@id= 'l_pr']"), "\"My Page\" button");

    public NewsForm() {
        super(By.xpath("//div[contains(@class, 'page_block stories')]"), "\"News\" form");
    }

    public void clickMyPageButton() {
        btnMY_PAGE.state().waitForClickable();
        btnMY_PAGE.clickAndWait();
    }
}
