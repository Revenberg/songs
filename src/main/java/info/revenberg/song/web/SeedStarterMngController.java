/*
 * =============================================================================
 * 
 *   Copyright (c) 2011-2016, The THYMELEAF team (http://www.thymeleaf.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */
package info.revenberg.song.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import info.revenberg.song.business.entities.SeedStarter;
import info.revenberg.song.business.entities.Variety;
import info.revenberg.song.business.services.SeedStarterService;
import info.revenberg.song.business.services.VarietyService;
import info.revenberg.song.domain.Bundle;
import info.revenberg.song.domain.Guest;
import info.revenberg.song.domain.Song;
import info.revenberg.song.domain.Vers;

@Controller
public class SeedStarterMngController {
    
    @Autowired
    private VarietyService varietyService;

    @Autowired
    private SeedStarterService seedStarterService;

    public SeedStarterMngController() {
        super();
    }

    @ModelAttribute("guest")
    public Guest prepareGuestModel() {
        return new Guest();
    }

    @RequestMapping(value = "/songs/{bundleid}", method = RequestMethod.GET)
    public String showSongsList(Model model, @PathVariable("bundleid") long bundleid) {
        model.addAttribute("songs", this.seedStarterService.findAllSongs(bundleid));        
        return "seedstartermng :: resultsListSongs";
    }

    @RequestMapping(value = "/verses/{songid}", method = RequestMethod.GET)
    public String showVersesList(Model model, @PathVariable("songid") long songid) {
        model.addAttribute("verses", this.seedStarterService.findAllVerses(songid));
        return "seedstartermng :: resultsListVerses";
    }

    @ModelAttribute("allBundles")
    public List<Bundle> allBundles() {
        return this.seedStarterService.findAllBundle();
    }

    @ModelAttribute("allSongs")
    public List<Song> allSongs() {
        return this.seedStarterService.findAllSongs(2);
    }

    @ModelAttribute("allVerses")
    public List<Vers> allVerses() {
        System.out.println("!!!!!!!!!!!!! allVerses !!!!!!!!!!!!!!!!!");
        System.out.println(this.seedStarterService.findAllVerses(2));
        System.out.println("!!!!!!!!!!! allVerses !!!!!!!!!!!!!!!!!!!");        
        return this.seedStarterService.findAllVerses(2);
    }
/*
    @ModelAttribute("versesvalue")
    public List<String> versesvalue() {
        System.out.println("?????????????????????????????????????????????");        
        return new ArrayList<String>();
    }
*/
    @ModelAttribute("allVarieties")
    public List<Variety> populateVarieties() {
        return this.varietyService.findAll();
    }

    @ModelAttribute("allSeedStarters")
    public List<SeedStarter> populateSeedStarters() {
        return this.seedStarterService.findAll();
    }

    @RequestMapping({ "/", "/seedstartermng" })
    public String showSeedstarters(final SeedStarter seedStarter) {
        seedStarter.setDatePlanted(Calendar.getInstance().getTime());        
   //     seedStarter.setVersesvalue(new ArrayList<String>());   
        return "seedstartermng";
    }

    @RequestMapping(value = "/seedstartermng", params = { "save" })
    public String saveSeedstarter(final SeedStarter seedStarter, final BindingResult bindingResult,
            final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "seedstartermng";
        }
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(seedStarter.toString());
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        //this.seedStarterService.add(seedStarter);
        model.clear();
        return "redirect:/seedstartermng";
    }

    @RequestMapping(value = "/seedstartermng", params = { "addRow" })
    public String addRow(final SeedStarter seedStarter, final BindingResult bindingResult) {
        //seedStarter.getRows().add(new Row());
        return "seedstartermng";
    }

    @RequestMapping(value = "/seedstartermng", params = { "removeRow" })
    public String removeRow(final SeedStarter seedStarter, final BindingResult bindingResult,
            final HttpServletRequest req) {
        final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
        seedStarter.getRows().remove(rowId.intValue());
        return "seedstartermng";
    }

}
