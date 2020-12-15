package ru.javawebinar.topjava.web.user;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.IllegalRequestDataException;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.UserFormValidator;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/admin/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminUIController extends AbstractUserController {

//    @Autowired
//    UserFormValidator userFormValidator;
//
//    @InitBinder
//    protected void initBinder(WebDataBinder binder) {
//        binder.setValidator(userFormValidator);
//    }

    @Override
    @GetMapping
    public List<User> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/{id}")
    public User get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createOrUpdate(@Valid UserTo userTo, BindingResult result) {


       User user = null;
        try {
           user = super.getByMail(userTo.getEmail());
        }catch (NotFoundException e){

        }
        if (user != null) {
            if (user.getEmail().equals(userTo.getEmail())){

              throw new IllegalRequestDataException("User with this email already exists");
            }

        }
        ValidationUtil.errorBuilder(result);


        if (userTo.isNew()) {
            super.create(userTo);
        } else {
            super.update(userTo, userTo.id());
        }
    }



    @Override
    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enable(@PathVariable int id, @RequestParam boolean enabled) {
        super.enable(id, enabled);
    }
}
