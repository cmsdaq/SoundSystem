package ch.cern.cms.daq.soundsystem.playing;

import ch.cern.cms.daq.soundsystem.persistence.SoundRecord;
import ch.cern.cms.daq.soundsystem.persistence.SoundRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


@Service
public class SoundService {

    Logger logger = LoggerFactory.getLogger(SoundService.class);

    ExecutorService executorService = Executors.newFixedThreadPool(1);

    @Autowired
    JingleManager jingleManager;

    @Autowired
    SystemCommandExecutor systemCommandExecutor;

    @Autowired
    SoundRecordRepository repository;

    public String say(String text) {


        SoundRecord soundRecord = new SoundRecord();
        soundRecord.setRequestType("say");
        soundRecord.setRequestBody(text);
        soundRecord.setCalled(new Date());
        repository.save(soundRecord);

        SoundTask soundTask = new SoundTask(systemCommandExecutor, text,null, soundRecord, repository);


        Future future = executorService.submit(soundTask);
        logger.trace("Task scheduled: " + soundTask);

        String response = "Scheduled to say: " + text;

        soundRecord.setResponse(response);

        repository.save(soundRecord);

        return response;
    }

    public String play(String filename) {

        SoundRecord soundRecord = new SoundRecord();
        soundRecord.setRequestType("play");
        soundRecord.setRequestBody(filename);
        soundRecord.setCalled(new Date());
        repository.save(soundRecord);


        String response = null;

        try {
            String absolutePath = jingleManager.getAbsolutePathToJingleByFilename(filename);
            SoundTask soundTask = new SoundTask(systemCommandExecutor,null, absolutePath, soundRecord, repository);
            executorService.submit(soundTask);
            logger.trace("Task scheduled: " + soundTask);
            response = "Scheduled to play jingle: " + filename;
        } catch(RuntimeException e){
            response = e.getMessage();
        }

        soundRecord.setResponse(response);
        repository.save(soundRecord);
        return response;

    }
}
