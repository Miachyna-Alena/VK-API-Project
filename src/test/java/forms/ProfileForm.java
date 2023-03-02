package forms;

import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class ProfileForm extends Form {
    public ProfileForm() {
        super(By.xpath("//div[@class='ProfileWrapper']"), "\"Profile\" form");
    }
}
