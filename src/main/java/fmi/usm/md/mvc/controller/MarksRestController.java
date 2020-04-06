package fmi.usm.md.mvc.controller;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class MarksRestController {

    @Autowired
    ServletContext context;

    @RequestMapping(value = "/api/get_marks", method = POST)
    public LinkedHashMap<String, Object> getMyMarks(@RequestParam(name = "id") String id) {

        String osProp = System.getProperty("os.name");
        String os;
        StringBuilder sb = new StringBuilder();

        if (osProp.contains("Linux")) {
            os = "Linux";
        }
        else {

            int i = 0;
            while (osProp.charAt(i) != ' ') {
                sb.append(osProp.charAt(i));
                i++;
            }

            if (sb.toString().equals("Windows"))
                os = ".exe";
            else
                os = sb.toString();
        }

        String driversPath = context.getRealPath("") + "static/drivers/";

        WebDriver driver;

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setJavascriptEnabled(true); // not really needed: JS enabled by default
        String driverName = "phantomjs" + os;
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, driversPath + driverName);

        driver = new PhantomJSDriver(caps);
        driver.get("http://crd.usm.md/student");

        WebElement txtCodperson = driver.findElement(By.id("txtCodperson"));
        txtCodperson.sendKeys(id);

        WebElement login = driver.findElement(By.id("btLogin"));
        login.click();

        String pagesource = driver.getPageSource();

        driver.quit();

        Pattern pDisciplina = Pattern.compile("<span class=\"(.*?)<");
        Matcher mDisciplina = pDisciplina.matcher(pagesource);

        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("status", "success");

        int nr_semestru = 1;
        int nr_disciplina = 1;
        int max_discipline;

        ArrayList<Object> discipline7or8 = new ArrayList<>();

        LinkedList<Object> semestre = new LinkedList<>();

        while (mDisciplina.find()) {

            Map<String, Object> tmpDisciplinaMap = new TreeMap<>();
            tmpDisciplinaMap.put("denumire", mDisciplina.group(1).replaceFirst("\">", ""));
            mDisciplina.find();
            tmpDisciplinaMap.put("nota", mDisciplina.group(1).replaceFirst("\">", ""));
            tmpDisciplinaMap.put("idDisciplina", nr_disciplina);

            discipline7or8.add(tmpDisciplinaMap);

            if (nr_semestru == 2)
                max_discipline = 8;
            else if (nr_semestru == 5)
                max_discipline = 6;
            else
                max_discipline = 7;

            if (nr_disciplina == max_discipline) {
                LinkedHashMap<String, Object> semestru = new LinkedHashMap<>();
                semestru.put("idSemestru", nr_semestru);
                semestru.put("discipline", discipline7or8);

                nr_semestru++;
                nr_disciplina = 0;
                semestre.add(semestru);
                discipline7or8 = new ArrayList<>();
            }

            nr_disciplina++;
        }

        //Pentru semestru curent primim obiectele ramase < 7, daca sunt
        if (!discipline7or8.isEmpty()) {
            LinkedHashMap<String, Object> semestru = new LinkedHashMap<>();
            semestru.put("idSemestru", nr_semestru);
            semestru.put("discipline", discipline7or8);
            semestre.add(semestru);
        }

        map.put("semestre", semestre);
        return map;
    }
}
