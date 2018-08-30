package ch.cern.cms.daq.soundsystem;

import ch.cern.cms.daq.soundsystem.persistence.SoundRecord;
import ch.cern.cms.daq.soundsystem.persistence.SoundRecordRepository;
import ch.cern.cms.daq.soundsystem.playing.SoundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SoundSystemController {

    @Autowired
    SoundService soundService;

    @Autowired
    SoundRecordRepository soundRecordRepository;

    Logger logger = LoggerFactory.getLogger(SoundSystemController.class);

    @RequestMapping(value="/say", method = RequestMethod.POST)
    public String say(@RequestBody String textToSpeach){

        String result = soundService.say(textToSpeach);

        logger.info("On request to /say with " + textToSpeach + " responding: " + result);
        return result;
    }

    @RequestMapping(value="/play", method = RequestMethod.POST)
    public String play(@RequestBody String jingle){

        String result = soundService.play(jingle);
        logger.info("On request to /play with " + jingle + " responding: " + result);
        return result;
    }

    @RequestMapping(value="/records", method = RequestMethod.GET)
    public List<SoundRecord> records(){

        List<SoundRecord> all = soundRecordRepository.findAll();
        return all;
    }

}
