package shorts;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


class validator {
    public static UrlValidator urlValidator = new UrlValidator();
    public static boolean isValid(String url) {
        return urlValidator.isValid(url);
    }
}

@Controller
public class TableController {

    @Autowired
    TableRepository repository;

    @GetMapping("/short")
    public String shortForm(Model model) {
        model.addAttribute("newUrl", new NewUrl(""));
        model.addAttribute("resUrl", new NewUrl(""));
        model.addAttribute("div", false);
        model.addAttribute("divRes", false);
        return "short";
    }

    @PostMapping("/short")
    public String shortAdd(@ModelAttribute NewUrl newUrl, Model model) {
        if (validator.isValid(newUrl.getUrl())) {
            List<TableStorage> res = repository.findByFirstName(newUrl.getUrl());
            if (res.isEmpty()) {
                TableStorage url = new TableStorage(newUrl.getUrl());
                repository.save(url);
                model.addAttribute("resUrl", new NewUrl("http://localhost:8080/short/res?id="+url.getId()));
            } else {
                model.addAttribute("resUrl", new NewUrl("http://localhost:8080/short/res?id=" + res.get(0).getId()));
            }
        } else {
            model.addAttribute("resUrl", new NewUrl(""));
        }

        model.addAttribute("newUrl", new NewUrl(""));
        model.addAttribute("div", validator.isValid(newUrl.getUrl()));
        model.addAttribute("divRes", true);
        return "short";
    }

    @GetMapping("/short/table")
    public String tableForm(Model model) {
        Iterable<TableStorage> table = repository.findAll();
        model.addAttribute("table", table);
        return "table";
    }

    @RequestMapping("/short/res")
    public String tableRes(@RequestParam(value = "id", required = false, defaultValue = "0") long id, Model model) {
        try {
            TableStorage res = repository.findById(id);
            return "redirect:" + res.getFirstName();
        } catch (Exception e) { }
        return "result";
    }

    @RequestMapping("/short/del")
    public String tableDelete(@RequestParam(value = "id", required = false, defaultValue = "0") long id, Model model) {
        try {
            repository.deleteById(id);
        } catch (Exception e) { }
        Iterable<TableStorage> table = repository.findAll();
        model.addAttribute("table", table);
        return "table";
    }
}