package ch.cern.cms.daq.soundsystem.playing;

import ch.cern.cms.daq.soundsystem.persistence.SoundRecord;
import ch.cern.cms.daq.soundsystem.persistence.SoundRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class SoundTask implements Runnable {

    String textToSpeach;

    String fileToPlay;

    String textToSpeachCommand;

    String fileToPlayCommand;

    SystemCommandExecutor systemCommandExecutor;

    SoundRecord soundRecord;

    SoundRecordRepository repository;

    Logger logger = LoggerFactory.getLogger(SoundTask.class);

    public SoundTask(SystemCommandExecutor systemCommandExecutor, String textToSpeach, String fileToPlay, SoundRecord soundRecord, SoundRecordRepository repository) {
        this.systemCommandExecutor = systemCommandExecutor;
        this.textToSpeach = textToSpeach;
        this.fileToPlay = fileToPlay;
        this.soundRecord = soundRecord;
        this.repository = repository;

        if (textToSpeach != null) {
            this.textToSpeachCommand = "say -v Alex " + textToSpeach;
        }
        if (fileToPlay != null) {
            this.fileToPlayCommand = "afplay " + fileToPlay;
        }

    }

    @Override
    public void run() {

        soundRecord.setExecutionStarted(new Date());
        repository.save(soundRecord);

        if (textToSpeachCommand != null) {
            logger.info("Saying: " + textToSpeach);
            systemCommandExecutor.execute(textToSpeachCommand);
        }
        if (fileToPlayCommand != null){
            logger.info("Playing: " + fileToPlay);
            systemCommandExecutor.execute(fileToPlayCommand);
        }
        soundRecord.setExecutionFinished(new Date());
        repository.save(soundRecord);


    }

    @Override
    public String toString() {
        return "SoundTask{" +
                "textToSpeachCommand='" + textToSpeachCommand + '\'' +
                ", fileToPlayCommand='" + fileToPlayCommand + '\'' +
                '}';
    }
}
