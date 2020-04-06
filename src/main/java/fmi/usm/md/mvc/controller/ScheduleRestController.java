package fmi.usm.md.mvc.controller;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class ScheduleRestController {

    @Autowired
    ServletContext context;

    /*
    *
    * @return JSON Object with weekly schedule
    *
    * @groupName ex: IA1602rom
    * @subgroup [I, II]
    * @scheduleType [weekly, exam]
    *
     */
    @RequestMapping(value = "/api/get_schedule", method = GET, produces = "application/json")
    @ResponseBody
    public String getMySchedule(@RequestParam(name = "groupName") String groupName,
                                @RequestParam(name = "subGroup") String subGroup,
                                @RequestParam(name = "scheduleType") String scheduleType) throws IOException {

        File jsonFile = new File(context.getRealPath("")
                + "static/json/" + scheduleType + "/" + groupName + ".json");

        if (!jsonFile.exists())
            return "Panacand nu este orarul pentru grupa: " + groupName;

        String jsonString = FileUtils.readFileToString(jsonFile, CharEncoding.UTF_8);

        jsonString = jsonString.replaceFirst("\\[", "{\"orar\":\\[");
        jsonString = jsonString.replaceFirst("]$", "]}");

        String response = "";
        try {
            JSONObject jObj = new JSONObject(jsonString);
            JSONArray saptamina_jArray = jObj.getJSONArray("orar");

            JSONArray lectiiFiltrateDinZi = new JSONArray();
            JSONObject ziCuLectiiFiltrate = new JSONObject();
            JSONArray saptamanaCuZileFiltrate = new JSONArray();

            JSONArray examLectiiFiltrate = new JSONArray();

            JSONObject rezultat = new JSONObject();

            for (int i = 0; i < saptamina_jArray.length(); i++) {
                JSONObject zi_jObject = saptamina_jArray.getJSONObject(i);

                if (scheduleType.equals("exam")) {
                    if (zi_jObject.get("subgrupa").equals(subGroup) || zi_jObject.get("subgrupa").equals("-")) {
                        examLectiiFiltrate.put(zi_jObject);
                    }
                }
                else {
                    JSONArray lectii_jArray = zi_jObject.getJSONArray("lectii");
                    for (int j = 0; j < lectii_jArray.length(); j++) {
                        JSONObject lectie_jObect = lectii_jArray.getJSONObject(j);
                        if (lectie_jObect.get("subgrupa").equals(subGroup) || lectie_jObect.get("subgrupa").equals("-")) {
                            lectie_jObect.remove("subgrupa");
                            lectiiFiltrateDinZi.put(lectie_jObect);
                        }
                    }
                    ziCuLectiiFiltrate.put("numeZi", zi_jObject.get("numeZi"));
                    ziCuLectiiFiltrate.put("lectii", lectiiFiltrateDinZi);
                    lectiiFiltrateDinZi = new JSONArray();

                    saptamanaCuZileFiltrate.put(ziCuLectiiFiltrate);
                    ziCuLectiiFiltrate = new JSONObject();
                }
            }
            if (scheduleType.equals("exam")) {
                rezultat.put("orar", examLectiiFiltrate);
                response = rezultat.toString();
                return response;
            }
            rezultat.put("orar", saptamanaCuZileFiltrate);
            response = rezultat.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return response;
    }
}
